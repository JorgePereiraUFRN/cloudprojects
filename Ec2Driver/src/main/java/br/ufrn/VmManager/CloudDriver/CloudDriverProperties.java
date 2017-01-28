package br.ufrn.VmManager.CloudDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CloudDriverProperties {

	private static String imageId;
	private static String securityGroupId;
	private static String instanceType;
	private static String accessKey;
	private static String secretKey;
	private static String subnetId;

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

			input = new FileInputStream("Ec2Driver.properties");

			prop.load(input);
			imageId = prop.getProperty("imageId");
			securityGroupId = prop.getProperty("securityGroupId");
			instanceType = prop.getProperty("instanceType");
			accessKey = prop.getProperty("accessKey");
			secretKey = prop.getProperty("secretKey");
			subnetId = prop.getProperty("subnetId");

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

	public static String getImageId() {
		return imageId;
	}

	public static void setImageId(String imageId) {
		CloudDriverProperties.imageId = imageId;
	}

	public static String getSecurityGroupId() {
		return securityGroupId;
	}

	public static void setSecurityGroupId(String securityGroupId) {
		CloudDriverProperties.securityGroupId = securityGroupId;
	}

	public static String getInstanceType() {
		return instanceType;
	}

	public static void setInstanceType(String instanceType) {
		CloudDriverProperties.instanceType = instanceType;
	}

	public static String getAccessKey() {
		return accessKey;
	}

	public static void setAccessKey(String accessKey) {
		CloudDriverProperties.accessKey = accessKey;
	}

	public static String getSecretKey() {
		return secretKey;
	}

	public static void setSecretKey(String secretKey) {
		CloudDriverProperties.secretKey = secretKey;
	}

	public static CloudDriverProperties getProperties() {
		return properties;
	}

	public static void setProperties(CloudDriverProperties properties) {
		CloudDriverProperties.properties = properties;
	}

	public static String getSubnetId() {
		return subnetId;
	}

	public static void setSubnetId(String subnetId) {
		CloudDriverProperties.subnetId = subnetId;
	}
	
	

}
