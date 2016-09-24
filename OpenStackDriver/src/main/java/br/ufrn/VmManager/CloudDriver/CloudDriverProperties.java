package br.ufrn.VmManager.CloudDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CloudDriverProperties {

	private static String tenantName;
	private static String userName;
	private static String credential;
	private static String endpoint;
	private static String imageId;
	private static String flavorId;

	private static CloudDriverProperties properties;

	private CloudDriverProperties() {

	}

	public static synchronized CloudDriverProperties getInstance() {

		if (properties == null) {
			loadConfFile();
		}
		return properties;
	}

	private static void loadConfFile() {
		Properties prop = new Properties();
		InputStream input = null;
		properties = new CloudDriverProperties();

		try {

			input = new FileInputStream("OpenstackDriver.properties");

			// load a properties file
			prop.load(input);

			tenantName = prop.getProperty("tenantName");
			userName = prop.getProperty("userName");
			credential = prop.getProperty("credential");
			endpoint = prop.getProperty("endpoint");
			imageId = prop.getProperty("imageId");
			flavorId = prop.getProperty("flavorId");

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

	public static String getTenantName() {
		return tenantName;
	}

	public static void setTenantName(String tenantName) {
		CloudDriverProperties.tenantName = tenantName;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		CloudDriverProperties.userName = userName;
	}

	public static String getCredential() {
		return credential;
	}

	public static void setCredential(String credential) {
		CloudDriverProperties.credential = credential;
	}

	public static String getEndpoint() {
		return endpoint;
	}

	public static void setEndpoint(String endpoint) {
		CloudDriverProperties.endpoint = endpoint;
	}

	public static String getImageId() {
		return imageId;
	}

	public static void setImageId(String imageId) {
		CloudDriverProperties.imageId = imageId;
	}

	public static String getFlavorId() {
		return flavorId;
	}

	public static void setFlavorId(String flavorId) {
		CloudDriverProperties.flavorId = flavorId;
	}

	public static CloudDriverProperties getProperties() {
		return properties;
	}

	public static void setProperties(CloudDriverProperties properties) {
		CloudDriverProperties.properties = properties;
	}
	
	
	
}
