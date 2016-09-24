package br.ufrn.VmManager.tests.Dao;

import java.util.Date;
import java.util.List;

import br.ufrn.VmManager.Dao.VirtualMachineDao;
import br.ufrn.VmManager.Dao.VirtualMachineDaoInterface;
import br.ufrn.VmManager.Exceptions.DaoException;
import br.ufrn.VmManager.model.IpAddress;
import br.ufrn.VmManager.model.VirtualMachine;
import br.ufrn.VmManager.model.VmMetric;
import br.ufrn.VmManager.model.VmState;

public class VmDaoTest {

	private static VirtualMachineDaoInterface vmDao = new VirtualMachineDao();
	private static List<VirtualMachine> vms;

	public static void main(String args[]) throws DaoException {

		VirtualMachine vm = new VirtualMachine();

		vm.setImage("image");
		vm.setIpAddress(new IpAddress("127.0.0.1", false));

		vm.setState(VmState.STOPED);
		vm.setTemplate("template");
		vm.setVmMetric(new VmMetric(0.5, 0.4, 0.3, new Date()));

		vmDao.save(vm);

		listVMs();

		VirtualMachine vm2 = vmDao.search(vm);
		
		vm2.setImage("other image");
		vm2.setIpAddress(new IpAddress("10.0.0.1", true));

		vmDao.update(vm2);

		listVMs();

		vmDao.delete(vm);

		listVMs();

	}

	private static void listVMs() throws DaoException {
		vms = vmDao.listAll(VirtualMachine.class);

		System.out.println("listando vms");
		for (VirtualMachine v : vms) {
			System.out.println(v);
		}
	}

}
