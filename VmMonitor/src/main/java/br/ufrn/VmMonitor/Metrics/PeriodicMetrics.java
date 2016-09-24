package br.ufrn.VmMonitor.Metrics;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.hyperic.sigar.SigarException;

import br.ufrn.VmManager.VmFacade.VmMonitorProperties;
import br.ufrn.VmManager.VmFacade.VmFacadeInterface;
import br.ufrn.VmManager.model.VmMetric;

public class PeriodicMetrics extends Thread {

	private VmFacadeInterface vmFacade;
	private MetricsMachine metricsMachine = new MetricsMachine();
	private VmMonitorProperties properties;
	private String vmIpAddress;

	private Date dateTime;

	private Logger logger = Logger.getLogger(PeriodicMetrics.class);

	public PeriodicMetrics(VmFacadeInterface vmFacade) throws SocketException {
		super();
		this.vmFacade = vmFacade;
		properties = VmMonitorProperties.getInstance();
		dateTime = new Date();

		vmIpAddress = getVmIpAddress();

		if (vmIpAddress == null) {
			logger.fatal("Unable to recovery ip address of: "
					+ properties.getNetworkInterface());
		}

	}

	public void run() {

		for (;;) {
			try {
				VmMetric metric = metricsMachine.getAllMetrics();

				if (metric.getCpuUsage() > properties.getMaxCpuUsage()) {
					logger.info("max cpu threshold attained");
					vmFacade.informVmMetric(vmIpAddress, metric);
				} else if (metric.getCpuUsage() < properties.getMinCpuUsage()) {
					logger.info("mim cpu threshold attained");
					vmFacade.informVmMetric(vmIpAddress, metric);
				} else if (metric.getMemoryUsage() > properties
						.getMaxMemoryUsage()) {
					logger.info("max memory threshold attained");
					vmFacade.informVmMetric(vmIpAddress, metric);
				} else if (metric.getMemoryUsage() < properties
						.getMinMemoryUsage()) {
					logger.info("min memory threshold attained");
					vmFacade.informVmMetric(vmIpAddress, metric);
				} else {
					// caso nenhuma metrica utrapasse o limiar mas o tempo desde
					// o envio da ultima metrica seja superior a 5 min a metrica
					// será enviada mesmo assim afim de que o VmManager mantenha
					// um registro atualizado das metricas

					Date atualDate = new Date();

					long duration = atualDate.getTime() - dateTime.getTime();

					long time = TimeUnit.MILLISECONDS.toSeconds(duration);

					if (time > 5 * 60) {
						vmFacade.informVmMetric(vmIpAddress, metric);
						dateTime = new Date();
					}
				}

			} catch (SigarException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Thread.sleep(1 * 60 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private String getVmIpAddress() throws SocketException {

		String interfaceName = properties.getNetworkInterface();
		NetworkInterface networkInterface = NetworkInterface
				.getByName(interfaceName);
		Enumeration<InetAddress> inetAddress = networkInterface
				.getInetAddresses();
		InetAddress currentAddress;
		currentAddress = inetAddress.nextElement();
		while (inetAddress.hasMoreElements()) {
			currentAddress = inetAddress.nextElement();
			if (currentAddress instanceof Inet4Address
					&& !currentAddress.isLoopbackAddress()) {
				return currentAddress.getHostAddress();

			}
		}
		return null;

	}
}
