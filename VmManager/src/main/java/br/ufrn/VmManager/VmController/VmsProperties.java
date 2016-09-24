package br.ufrn.VmManager.VmController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class VmsProperties {

	private static Float maxCpuUsage;
	private static Float minCpuUsage;
	private static Float maxMemoryUsage;
	private static Float minMemoryUsage;
	private static Float maxStorageUsage;
	private static Float minStorageUsage;
	private static Float maxNetworkUsage;
	private static Float minNetworkUsage;
	
	private static Integer maxVMsNumber;

	private static VmsProperties vmThresholds;

	private VmsProperties() {

	}

	public static synchronized VmsProperties getInstance() {

		if (vmThresholds == null) {
			loadConfFile();
		}
		return vmThresholds;
	}

	private static void loadConfFile() {
		Properties prop = new Properties();
		InputStream input = null;
		vmThresholds = new VmsProperties();

		try {

			input = new FileInputStream("VM.properties");

			// load a properties file
			prop.load(input);

			maxCpuUsage = Float.parseFloat(prop.getProperty("maxCpuUsage"));
			minCpuUsage = Float.parseFloat(prop.getProperty("minCpuUsage"));
			maxMemoryUsage = Float.parseFloat(prop.getProperty("maxMemoryUsage"));
			minMemoryUsage = Float.parseFloat(prop.getProperty("minMemoryUsage"));
			maxStorageUsage = Float.parseFloat(prop.getProperty("maxStorageUsage"));
			minStorageUsage = Float.parseFloat(prop.getProperty("minStorageUsage"));
			maxNetworkUsage = Float.parseFloat(prop.getProperty("maxNetworkUsage"));
			minNetworkUsage = Float.parseFloat(prop.getProperty("minNetworkUsage"));
			maxVMsNumber = Integer.parseInt(prop.getProperty("maxVMsNumber"));
			

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	
	public Integer getMaxVMsNumber() {
		return maxVMsNumber;
	}

	public  void setMaxVMsNumber(Integer maxVMsNumber) {
		VmsProperties.maxVMsNumber = maxVMsNumber;
	}

	public Float getMaxCpuUsage() {
		return maxCpuUsage;
	}

	public void setMaxCpuUsage(Float maxCpuUsage) {
		this.maxCpuUsage = maxCpuUsage;
	}

	public Float getMinCpuUsage() {
		return minCpuUsage;
	}

	public void setMinCpuUsage(Float minCpuUsage) {
		this.minCpuUsage = minCpuUsage;
	}

	public Float getMaxMemoryUsage() {
		return maxMemoryUsage;
	}

	public void setMaxMemoryUsage(Float maxMemoryUsage) {
		this.maxMemoryUsage = maxMemoryUsage;
	}

	public Float getMinMemoryUsage() {
		return minMemoryUsage;
	}

	public void setMinMemoryUsage(Float minMemoryUsage) {
		this.minMemoryUsage = minMemoryUsage;
	}

	public Float getMaxStorageUsage() {
		return maxStorageUsage;
	}

	public void setMaxStorageUsage(Float maxStorageUsage) {
		this.maxStorageUsage = maxStorageUsage;
	}

	public Float getMinStorageUsage() {
		return minStorageUsage;
	}

	public void setMinStorageUsage(Float minStorageUsage) {
		this.minStorageUsage = minStorageUsage;
	}

	public Float getMaxNetworkUsage() {
		return maxNetworkUsage;
	}

	public void setMaxNetworkUsage(Float maxNetworkUsage) {
		this.maxNetworkUsage = maxNetworkUsage;
	}

	public Float getMinNetworkUsage() {
		return minNetworkUsage;
	}

	public void setMinNetworkUsage(Float minNetworkUsage) {
		this.minNetworkUsage = minNetworkUsage;
	}

	@Override
	public String toString() {
		return "VmThresholds [getMaxVMsNumber()=" + getMaxVMsNumber()
				+ ", getMaxCpuUsage()=" + getMaxCpuUsage()
				+ ", getMinCpuUsage()=" + getMinCpuUsage()
				+ ", getMaxMemoryUsage()=" + getMaxMemoryUsage()
				+ ", getMinMemoryUsage()=" + getMinMemoryUsage()
				+ ", getMaxStorageUsage()=" + getMaxStorageUsage()
				+ ", getMinStorageUsage()=" + getMinStorageUsage()
				+ ", getMaxNetworkUsage()=" + getMaxNetworkUsage()
				+ ", getMinNetworkUsage()=" + getMinNetworkUsage() + "]";
	}


	
}
