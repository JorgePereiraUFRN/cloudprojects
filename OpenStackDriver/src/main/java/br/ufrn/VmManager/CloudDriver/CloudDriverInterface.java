package br.ufrn.VmManager.CloudDriver;

import br.ufrn.VmManager.Exceptions.CloudException;
import br.ufrn.VmManager.model.Storage;
import br.ufrn.VmManager.model.VirtualMachine;

public interface CloudDriverInterface {

	public VirtualMachine createVM() throws CloudException;
	
	public void deleteVM(VirtualMachine vm) throws CloudException;
	
	public Storage alocateStorage() throws CloudException;
}
