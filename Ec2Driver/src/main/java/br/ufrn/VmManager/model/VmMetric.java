package br.ufrn.VmManager.model;

import java.io.Serializable;
import java.util.Date;

public class VmMetric implements Serializable {

	private Double cpuUsage;
	private Double memoryUsage;
	private Double storageUsage;
	private Date timestamp;

	public VmMetric() {

	}

	public VmMetric(Double cpuUsage, Double memoryUsage, Double storageUsage, Date timestamp) {
		super();
		this.cpuUsage = cpuUsage;
		this.memoryUsage = memoryUsage;
		this.storageUsage = storageUsage;

		this.timestamp = timestamp;
	}



	public Double getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(Double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public Double getMemoryUsage() {
		return memoryUsage;
	}

	public void setMemoryUsage(Double memoryUsage) {
		this.memoryUsage = memoryUsage;
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

	@Override
	public String toString() {
		return "VmMetric [cpuUsage=" + cpuUsage + ", memoryUsage="
				+ memoryUsage + ", storageUsage=" + storageUsage
				+ ", timestamp=" + timestamp + "]";
	}

}
