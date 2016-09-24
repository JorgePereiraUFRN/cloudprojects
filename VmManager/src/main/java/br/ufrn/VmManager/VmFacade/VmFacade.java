package br.ufrn.VmManager.VmFacade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import br.ufrn.VmManager.VmController.VmController;
import br.ufrn.VmManager.model.VmMetric;

public class VmFacade extends UnicastRemoteObject implements VmFacadeInterface {

	private VmController vmController;
	private static final long serialVersionUID = 1L;
	
	public VmFacade() throws RemoteException {
		super();
		vmController = VmController.getInstance();
		
	}

	public void informVmMetric(String vmIp, VmMetric metrics)
			throws RemoteException {
		
		vmController.analyzeMetrics(vmIp, metrics);
		
	}

	
}
