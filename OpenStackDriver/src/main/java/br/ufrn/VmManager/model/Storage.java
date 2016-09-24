package br.ufrn.VmManager.model;

public class Storage {

	private String VmIpAddress;
	private Float storageSize;
	
	
	public String getVmIpAddress() {
		return VmIpAddress;
	}
	public void setVmIpAddress(String vmIpAddress) {
		VmIpAddress = vmIpAddress;
	}
	public Float getStorageSize() {
		return storageSize;
	}
	public void setStorageSize(Float storageSize) {
		this.storageSize = storageSize;
	}
	
}
