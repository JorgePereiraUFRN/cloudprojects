package br.ufrn.VmManager.Dao;

import br.ufrn.VmManager.model.VirtualMachine;

public class VirtualMachineDao extends GenericDaoDb4o<VirtualMachine> implements VirtualMachineDaoInterface{

	public VirtualMachineDao() {
		super(VirtualMachine.class.getSimpleName());
	}

}