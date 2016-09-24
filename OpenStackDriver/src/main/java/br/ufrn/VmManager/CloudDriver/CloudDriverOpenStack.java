package br.ufrn.VmManager.CloudDriver;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;

import br.ufrn.VmManager.Exceptions.CloudException;
import br.ufrn.VmManager.model.IpAddress;
import br.ufrn.VmManager.model.Storage;
import br.ufrn.VmManager.model.VirtualMachine;
import br.ufrn.VmManager.model.VmState;

public class CloudDriverOpenStack implements CloudDriverInterface {
	private final NovaApi novaApi;
	private final ServerApi serverApi;
	private final CloudDriverProperties properties;

	public CloudDriverOpenStack() {

		Iterable<Module> modules = ImmutableSet
				.<Module> of(new SLF4JLoggingModule());

		properties = CloudDriverProperties.getInstance();
		
		String provider = "openstack-nova";
		String identity = properties.getTenantName()+":"+properties.getUserName(); // tenantName:userName
		String credential = properties.getCredential();

		novaApi = ContextBuilder.newBuilder(provider)
				.endpoint(properties.getEndpoint())
				.credentials(identity, credential).modules(modules)
				.buildApi(NovaApi.class);

		serverApi = novaApi.getServerApi("RegionOne");

		
	}

	public VirtualMachine createVM() throws CloudException {

		VirtualMachine vm = null;
		try {

			vm = new VirtualMachine();

			String imageId = properties.getImageId();
			String flavorId = properties.getFlavorId();
			ServerCreated ser = serverApi.create("istance jclouds", imageId,
					flavorId);

			Server server = serverApi.get(ser.getId());

			;
			while (server.getStatus().value().equals("ACTIVE") == false) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
				}
				server = serverApi.get(ser.getId());
			}

			vm.setId(server.getId());
			vm.setImage(imageId);
			vm.setState(VmState.RUNNING);
			vm.setTemplate(flavorId);
			IpAddress ipAddress = new IpAddress();
			ipAddress.setIp(server.getAddresses().keys().iterator().next());
			ipAddress.setPublic(false);
			vm.setIpAddress(ipAddress);
		} catch (Exception e) {
			throw new CloudException(e.getMessage());
		}

		return vm;
	}

	public void deleteVM(VirtualMachine vm) throws CloudException {
		try {
			serverApi.delete(vm.getId());
		} catch (Exception e) {
			throw new CloudException(e.getMessage());
		}
	}

	public Storage alocateStorage() throws CloudException {
		// TODO Auto-generated method stub
		return null;
	}

}
