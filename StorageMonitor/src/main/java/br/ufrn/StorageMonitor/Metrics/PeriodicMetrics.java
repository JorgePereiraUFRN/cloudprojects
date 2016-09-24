package br.ufrn.StorageMonitor.Metrics;

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

import br.ufrn.VmManager.StorageFacade.StorageFacadeInterface;
import br.ufrn.VmManager.model.StorageMetric;
import br.ufrn.storageMonitor.StorageProperties;

public class PeriodicMetrics extends Thread {

	private StorageFacadeInterface storageFacade;
	private Metrics metricsMachine = new Metrics();
	private StorageProperties properties;
	private String vmIpAddress;

	private Date dateTime;

	private Logger logger = Logger.getLogger(PeriodicMetrics.class);

	public PeriodicMetrics(StorageFacadeInterface Facade)
			throws SocketException {
		super();
		this.storageFacade = Facade;
		properties = StorageProperties.getInstance();
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
				Metrics metric = new Metrics();
				StorageMetric storageMetric;

				if (metric.getStorageUsage() > properties.getStorageThreshold()) {
					logger.info("max  storage threshold attained");
					storageMetric = new StorageMetric(vmIpAddress,
							metric.getStorageUsage(), new Date());
					storageFacade.informStorageMetric(vmIpAddress,
							storageMetric);
				} else {
					// caso nenhuma metrica utrapasse o limiar mas o tempo desde
					// o envio da ultima metrica seja superior a 20 min a
					// metrica
					// será enviada mesmo assim afim de que o VmManager mantenha
					// um registro atualizado das metricas

					Date atualDate = new Date();

					long duration = atualDate.getTime() - dateTime.getTime();

					long time = TimeUnit.MILLISECONDS.toSeconds(duration);

					if (time > 30 * 60) {
						storageMetric = new StorageMetric(vmIpAddress,
								metric.getStorageUsage(), new Date());
						storageFacade.informStorageMetric(vmIpAddress,
								storageMetric);
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
