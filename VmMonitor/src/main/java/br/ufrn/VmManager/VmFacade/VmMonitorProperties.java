package br.ufrn.VmManager.VmFacade;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class VmMonitorProperties {

	private static Float maxCpuUsage;
	private static Float minCpuUsage;
	private static Float maxMemoryUsage;
	private static Float minMemoryUsage;
	private static Float maxStorageUsage;
	private static Float minStorageUsage;
	private static Float maxNetworkUsage;
	private static Float minNetworkUsage;
	private static String networkInterface;
	
	private static Integer maxVMsNumber;
	private static String VmFacadeURI;

	private static VmMonitorProperties vmThresholds;

	private VmMonitorProperties() {

	}

	public static synchronized VmMonitorProperties getInstance() {

		if (vmThresholds == null) {
			loadConfFile();
		}
		return vmThresholds;
	}

	private static void loadConfFile() {
		Properties prop = new Properties();
		InputStream input = null;
		vmThresholds = new VmMonitorProperties();

		try {

			input = new FileInputStream("VmMonitor.properties");

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
			networkInterface = prop.getProperty("networkInterface");
			VmFacadeURI = prop.getProperty("VmFacadeURI");

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
		VmMonitorProperties.maxVMsNumber = maxVMsNumber;
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
	
	

	public String getNetworkInterface() {
		return networkInterface;
	}

	public void setNetworkInterface(String networkInterface) {
		VmMonitorProperties.networkInterface = networkInterface;
	}

	
	public static String getVmFacadeURI() {
		return VmFacadeURI;
	}

	public static void setVmFacadeURI(String vmFacadeURI) {
		VmFacadeURI = vmFacadeURI;
	}

	@Override
	public String toString() {
		return "VmMonitorProperties [getMaxVMsNumber()=" + getMaxVMsNumber()
				+ ", getMaxCpuUsage()=" + getMaxCpuUsage()
				+ ", getMinCpuUsage()=" + getMinCpuUsage()
				+ ", getMaxMemoryUsage()=" + getMaxMemoryUsage()
				+ ", getMinMemoryUsage()=" + getMinMemoryUsage()
				+ ", getMaxStorageUsage()=" + getMaxStorageUsage()
				+ ", getMinStorageUsage()=" + getMinStorageUsage()
				+ ", getMaxNetworkUsage()=" + getMaxNetworkUsage()
				+ ", getMinNetworkUsage()=" + getMinNetworkUsage()
				+ ", getNetworkInterface()=" + getNetworkInterface() + "]";
	}

	
	
}
