package br.ufrn.VmManager.CloudDriver;

import br.ufrn.VmManager.Exceptions.CloudException;
import br.ufrn.VmManager.model.VirtualMachine;

public class Main {

	public static void main(String[] args) throws CloudException {
		
		
		CloudDriverInterface cloudDriver = new Ec2Driver();

		
		//System.out.println(cloudDriver.createVM());
		
		VirtualMachine vm = new VirtualMachine();
		
		vm.setId("i-0daf5d97ff855c976");
		
		cloudDriver.deleteVM(vm);

	}

}
