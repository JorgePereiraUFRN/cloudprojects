package br.ufrn.VmMonitor.Metrics;

import java.util.Date;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import br.ufrn.VmManager.model.VmMetric;

public class MetricsMachine {

	private Sigar SIGAR = new Sigar();

	public double getCpuUsage() throws SigarException {

		return SIGAR.getCpuPerc().getUser();
	}

	public double getMemoryUsage() throws SigarException {

		return SIGAR.getMem().getUsedPercent() / 100;
	}

	
	public long getTxBytes() throws SigarException{
		long total = 0;
		String [] interfaces = SIGAR.getNetInterfaceList();
		for(String netI : interfaces){
			total += SIGAR.getNetInterfaceStat(netI).getTxBytes();
		}
		
		return total;
	}
	
	public long getRxBytes() throws SigarException{
		
		long total = 0;
		String [] interfaces = SIGAR.getNetInterfaceList();
		
		for(String netI : interfaces){
			total += SIGAR.getNetInterfaceStat(netI).getRxBytes();
		}
		
		return total;
	}

	public double getStorageUsage() throws SigarException {

		FileSystem[] fileSystems = SIGAR.getFileSystemList();

		int cont = 0;
		double soma = 0;

		for (FileSystem f : fileSystems) {
			if (f.getType() == 2) {
				FileSystemUsage fUsage = SIGAR.getFileSystemUsage(f
						.getDirName());
				soma += fUsage.getUsePercent();
				cont++;
			}
		}

		if (cont > 0) {
			return soma / cont;
		} else {
			return 0;
		}

	}

	public VmMetric getAllMetrics() throws SigarException {
		
		VmMetric metrics = new VmMetric();
		
		metrics.setCpuUsage(getCpuUsage());
		
		metrics.setMemoryUsage(getMemoryUsage());
		metrics.setStorageUsage(getStorageUsage());
		
		metrics.setTimestamp(new Date());
		
		return metrics;

	}

}
