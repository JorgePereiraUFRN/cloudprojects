package br.ufrn.VmManager.CloudDriver;

import br.ufrn.VmManager.Exceptions.CloudException;
import br.ufrn.VmManager.model.VirtualMachine;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws CloudException, InterruptedException {
		
		CloudDriverInterface ec2 = new Ec2Driver();
		
		VirtualMachine vm = ec2.createVM();
		System.out.println(vm);
		
		Thread.sleep(35 * 1000);
		
		ec2.deleteVM(vm);

	}

}
