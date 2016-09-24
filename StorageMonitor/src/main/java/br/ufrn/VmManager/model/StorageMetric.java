package br.ufrn.VmManager.model;

import java.io.Serializable;
import java.util.Date;

public class StorageMetric implements Serializable{
	
	private String vmIpAddress;
	private Double storageUsage;
	private Date timestamp;
	
	public StorageMetric(){
		
	}
	
	public StorageMetric(String vmIpAddress, Double storageUsage, Date timestamp) {
		super();
		this.vmIpAddress = vmIpAddress;
		this.storageUsage = storageUsage;
		this.timestamp = timestamp;
	}
	
	public String getVmIpAddress() {
		return vmIpAddress;
	}
	public void setVmIpAddress(String vmIpAddress) {
		this.vmIpAddress = vmIpAddress;
	}
	public Double getStorageUsage() {
		return storageUsage;
	}
	public void setStorageUsage(Double storageUsage) {
		this.storageUsage = storageUsage;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
	

}
