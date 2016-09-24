package br.ufrn.storageMonitor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class StorageProperties {

	private static Float storageThreshold;

	private static StorageProperties storageProperties;
	private static String networkInterface;
	private static String StorageFacadeURI;

	private StorageProperties() {

	}

	public static synchronized StorageProperties getInstance() {

		if (storageProperties == null) {
			loadConfFile();
		}
		return storageProperties;
	}

	private static void loadConfFile() {
		Properties prop = new Properties();
		InputStream input = null;
		storageProperties = new StorageProperties();

		try {

			input = new FileInputStream("StorageMonitor.properties");

			// load a properties file
			prop.load(input);

			storageThreshold = Float.parseFloat(prop
					.getProperty("StorageThreshold"));
			
			networkInterface = prop.getProperty("networkInterface");
			StorageFacadeURI = prop.getProperty("StorageFacadeURI");
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

	public static Float getStorageThreshold() {
		return storageThreshold;
	}

	public static void setStorageThreshold(Float storageThreshold) {
		StorageProperties.storageThreshold = storageThreshold;
	}

	public static String getNetworkInterface() {
		return networkInterface;
	}

	public static void setNetworkInterface(String networkInterface) {
		StorageProperties.networkInterface = networkInterface;
	}

	public static String getStorageFacadeURI() {
		return StorageFacadeURI;
	}

	public static void setStorageFacadeURI(String storageFacadeURI) {
		StorageFacadeURI = storageFacadeURI;
	}
	
	

}
