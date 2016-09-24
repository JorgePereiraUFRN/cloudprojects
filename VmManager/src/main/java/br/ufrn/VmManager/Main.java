package br.ufrn.VmManager;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.Logger;

import br.ufrn.VmManager.Dao.StorageMetricDaoInterface;
import br.ufrn.VmManager.Dao.StorageMetricsDao;
import br.ufrn.VmManager.Dao.VirtualMachineDao;
import br.ufrn.VmManager.Dao.VirtualMachineDaoInterface;
import br.ufrn.VmManager.Exceptions.DaoException;
import br.ufrn.VmManager.StorageFacade.StorageFacade;
import br.ufrn.VmManager.StorageFacade.StorageFacadeInterface;
import br.ufrn.VmManager.VmFacade.VmFacade;
import br.ufrn.VmManager.VmFacade.VmFacadeInterface;
import br.ufrn.VmManager.model.IpAddress;
import br.ufrn.VmManager.model.StorageMetric;
import br.ufrn.VmManager.model.VirtualMachine;
import br.ufrn.VmManager.model.VmState;

public class Main {
	
	private static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) throws DaoException {

		createInitialVM();
		createInitialStorageMachine();
		startStorageFacade();
		startVmFacade();
	}

	
	private static void createInitialVM() throws DaoException{
		IpAddress ip = new IpAddress("192.168.0.2", false);	
		VirtualMachine vm = new VirtualMachine();
		vm.setIpAddress(ip);
		
		VirtualMachineDaoInterface vmDao = new VirtualMachineDao();
		if(vmDao.search(vm) == null ){
			vm.setState(VmState.RUNNING);
			vm.setTemplate("template");
			vmDao.save(vm);
		}
		
		
	}
	
	private static void createInitialStorageMachine() throws DaoException{
		StorageMetric stmeMetric = new StorageMetric("192.168.0.2", null, null);
		StorageMetricDaoInterface stDao = new StorageMetricsDao();
		
		if(stDao.search(stmeMetric) == null){
			stDao.save(stmeMetric);
		}
	}
	
	private static void startStorageFacade() {
		try {

			int RMIPortNum = 2000;
			startRegistry(RMIPortNum);
			StorageFacadeInterface exportedObj = new StorageFacade();
			String registryURL = "rmi://localhost:" + RMIPortNum
					+ "/StorageFacade";
			Naming.rebind(registryURL, exportedObj);
			logger.info("StorageFacade started: "+registryURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void startVmFacade() {
		try {

			int RMIPortNum = 2001;
			startRegistry(RMIPortNum);
			VmFacadeInterface exportedObj = new VmFacade();
			String registryURL = "rmi://localhost:" + RMIPortNum
					+ "/VmFacade";
			Naming.rebind(registryURL, exportedObj);
			logger.info("VmFacade started: "+registryURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void startRegistry(int RMIPortNum) throws RemoteException {
		try {
			Registry registry = LocateRegistry.getRegistry(RMIPortNum);
			registry.list();
		} catch (RemoteException e) {
			// No valid registry at that port.
			Registry registry = LocateRegistry.createRegistry(RMIPortNum);
		}
	} // end startRegistry
}
