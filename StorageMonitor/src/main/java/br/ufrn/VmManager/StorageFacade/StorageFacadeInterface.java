package br.ufrn.VmManager.StorageFacade;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.ufrn.VmManager.model.StorageMetric;

public interface StorageFacadeInterface extends Remote{

	void informStorageMetric(String vmIpAddress, StorageMetric metric) throws RemoteException;
}
