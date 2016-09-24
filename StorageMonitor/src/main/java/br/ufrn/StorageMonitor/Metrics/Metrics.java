package br.ufrn.StorageMonitor.Metrics;

import java.util.Date;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;



public class Metrics {

	private Sigar SIGAR = new Sigar();


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

	

}
