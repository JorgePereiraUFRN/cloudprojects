package br.ufrn.storageMonitor;

import java.net.MalformedURLException;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import br.ufrn.StorageMonitor.Metrics.PeriodicMetrics;
import br.ufrn.VmManager.StorageFacade.StorageFacadeInterface;



public class Main {

	public static void main(String args[]) throws MalformedURLException, RemoteException, NotBoundException, SocketException {
		String regURL = StorageProperties.getInstance().getStorageFacadeURI();
		StorageFacadeInterface stFacade =  (StorageFacadeInterface) Naming.lookup(regURL);

		PeriodicMetrics periodicMetrics = new PeriodicMetrics(stFacade);
		periodicMetrics.start();
	}

}
