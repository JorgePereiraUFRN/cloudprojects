package br.ufrn.VmManager.VmFacade;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.ufrn.VmManager.model.VmMetric;

public interface VmFacadeInterface extends Remote {

	void informVmMetric(String vmIp, VmMetric metrics) throws RemoteException;
}
