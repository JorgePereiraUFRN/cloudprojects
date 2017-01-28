package br.ufrn.VmManager.CloudDriver;

import java.util.ArrayList;
import java.util.Collection;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;

import br.ufrn.VmManager.Exceptions.CloudException;
import br.ufrn.VmManager.model.IpAddress;
import br.ufrn.VmManager.model.Storage;
import br.ufrn.VmManager.model.VirtualMachine;
import br.ufrn.VmManager.model.VmState;

public class Ec2Driver implements CloudDriverInterface {

	public VirtualMachine createVM() throws CloudException {

		VirtualMachine vm = null;
		try {

			CloudDriverProperties properties = CloudDriverProperties
					.getInstance();

			AmazonEC2Client amazonEC2Client = new AmazonEC2Client(
					new BasicAWSCredentials(properties.getAccessKey(),
							properties.getSecretKey()));

			amazonEC2Client.setEndpoint("ec2.us-east-1.amazonaws.com");

			RunInstancesRequest runInstancesRequest = new RunInstancesRequest();

			runInstancesRequest.withImageId(properties.getImageId())
					.withInstanceType(properties.getInstanceType())
					.withMinCount(1).withMaxCount(1)
					.withSubnetId(properties.getSubnetId())
					.withSecurityGroupIds(properties.getSecurityGroupId());

			RunInstancesResult runInstancesResult = amazonEC2Client
					.runInstances(runInstancesRequest);

			vm = new VirtualMachine();

			Instance instance = runInstancesResult.getReservation()
					.getInstances().get(0);

			vm.setId(instance.getInstanceId());
			vm.setImage(instance.getImageId());

			IpAddress private_ip = new IpAddress();
			private_ip.setIp(instance.getPrivateIpAddress());
			private_ip.setPublic(false);
			vm.setIpAddress(private_ip);

			vm.setState(VmState.RUNNING);
			vm.setTemplate(instance.getInstanceType());

		} catch (Exception e) {
			throw new CloudException(e.getMessage());
		}
		return vm;
	}

	public void deleteVM(VirtualMachine vm) throws CloudException {
		try {

			CloudDriverProperties properties = CloudDriverProperties
					.getInstance();

			AmazonEC2Client amazonEC2Client = new AmazonEC2Client(
					new BasicAWSCredentials(properties.getAccessKey(),
							properties.getSecretKey()));

			TerminateInstancesRequest terminateInstancesRequest = new TerminateInstancesRequest();

			Collection<String> instanceIds = new ArrayList<String>();
			instanceIds.add(vm.getId());
			terminateInstancesRequest.setInstanceIds(instanceIds);

			TerminateInstancesResult result = amazonEC2Client
		
					.terminateInstances(terminateInstancesRequest);
			
		} catch (Exception e) {
			throw new CloudException(e.getMessage());
		}

	}

	public Storage alocateStorage() throws CloudException {
		// TODO Auto-generated method stub
		return null;
	}

}
