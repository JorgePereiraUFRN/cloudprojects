package br.ufrn.VmManager.CloudDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CloudDriverProperties {

	private static String imageId;
	private static String securityGroup;
	private static String instanceType;
	private static String accessKey;
	private static String secretKey;

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

			// load a properties file
			prop.load(input);
			imageId = prop.getProperty("imageId");
			securityGroup = prop.getProperty("securityGroup");
			instanceType = prop.getProperty("instanceType");
			accessKey = prop.getProperty("accessKey");
			secretKey = prop.getProperty("secretKey");

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

	public static String getSecurityGroup() {
		return securityGroup;
	}

	public static void setSecurityGroup(String securityGroup) {
		CloudDriverProperties.securityGroup = securityGroup;
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

}
