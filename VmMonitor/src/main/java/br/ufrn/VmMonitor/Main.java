package br.ufrn.VmMonitor;

import java.net.MalformedURLException;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import br.ufrn.VmManager.VmFacade.VmFacadeInterface;
import br.ufrn.VmManager.VmFacade.VmMonitorProperties;
import br.ufrn.VmMonitor.Metrics.PeriodicMetrics;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws MalformedURLException,
			RemoteException, NotBoundException, SocketException {

		String regURL = VmMonitorProperties.getInstance().getVmFacadeURI();
		VmFacadeInterface vmFacade = (VmFacadeInterface) Naming.lookup(regURL);

		PeriodicMetrics periodicMetrics = new PeriodicMetrics(vmFacade);
		periodicMetrics.start();

	}

}
