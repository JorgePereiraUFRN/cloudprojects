package br.ufrn.VmManager.StorageFacade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import br.ufrn.VmManager.StorageController.StorageController;
import br.ufrn.VmManager.model.StorageMetric;

public class StorageFacade extends UnicastRemoteObject implements StorageFacadeInterface{

	private StorageController stController;
	private static final long serialVersionUID = 1L;

	public StorageFacade() throws RemoteException {
		super();
		stController = StorageController.getInstance();
	}

	public void informStorageMetric(String vmIpAddress, StorageMetric metric)
			throws RemoteException {
		
		stController.analyzeStorageMetrics(vmIpAddress, metric);
	}

}
