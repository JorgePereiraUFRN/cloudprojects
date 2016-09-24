package br.ufrn.VmManager.CloudDriver;

import static br.ufrn.VmManager.CloudDriver.SignatureUtils.And;
import static br.ufrn.VmManager.CloudDriver.SignatureUtils.Equals;
import static br.ufrn.VmManager.CloudDriver.SignatureUtils.HMAC_SHA256_ALGORITHM;
import static br.ufrn.VmManager.CloudDriver.SignatureUtils.SIGNATURE_KEYNAME;
import static br.ufrn.VmManager.CloudDriver.SignatureUtils.SIGNATURE_METHOD_KEYNAME;
import static br.ufrn.VmManager.CloudDriver.SignatureUtils.SIGNATURE_VERSION_2;
import static br.ufrn.VmManager.CloudDriver.SignatureUtils.SIGNATURE_VERSION_KEYNAME;
import static br.ufrn.VmManager.CloudDriver.SignatureUtils.UTF_8_Encoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import br.ufrn.VmManager.Exceptions.CloudException;

public class Ec2Actions implements Serializable {

	public static final String HttpMethod = "POST";
	public static final String ServiceEndPoint = "https://ec2.amazonaws.com/";
	public static final String Version = "2014-05-01";
	private static final String ACTION = "Action";
	private static CloudDriverProperties properties;

	private URI serviceEndPoint;

	public Ec2Actions() {
		try {
			serviceEndPoint = new URI(ServiceEndPoint);
			properties = CloudDriverProperties.getInstance();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Map<String, String> getDefaultSiginV2Params() {
		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("Version", Version);
		parameters.put("Timestamp", getFormattedTimestamp());
		parameters.put("AWSAccessKeyId", properties.getAccessKey());
		parameters.put(SIGNATURE_VERSION_KEYNAME, SIGNATURE_VERSION_2);
		parameters.put(SIGNATURE_METHOD_KEYNAME, HMAC_SHA256_ALGORITHM);

		return parameters;
	}

	/**
	 * Formats date as ISO 8601 timestamp
	 */
	private static String getFormattedTimestamp() {
		SimpleDateFormat df = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		return df.format(new Date());
	}

	/**
	 * Creates a URL from the provided key-value pairs
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String generateURL(Map<String, String> params)
			throws UnsupportedEncodingException {
		StringBuilder cbuiUrl = new StringBuilder(ServiceEndPoint + "?");
		for (String key : params.keySet()) {
			cbuiUrl.append(key);
			cbuiUrl.append(Equals);
			cbuiUrl.append(URLEncoder.encode(params.get(key), UTF_8_Encoding));
			cbuiUrl.append(And);
		}
		cbuiUrl.deleteCharAt(cbuiUrl.length() - 1);
		return cbuiUrl.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufrn.dimap.signature.EC2#DescribeInstances(java.lang.String[])
	 */
	public String DescribeInstances(String[] instanceIds) throws CloudException {
		Map<String, String> parameters = getDefaultSiginV2Params();
		parameters.put(ACTION, "DescribeInstances");

		if (instanceIds != null) {
			for (int i = 0; i < instanceIds.length; i++) {
				parameters.put("InstanceId" + "." + (i + 1), instanceIds[i]);
			}
		}

		String response = makeRequest(parameters);

		return response;

	}

	public String runInstances() throws CloudException {


		Map<String, String> parameters = getDefaultSiginV2Params();
		parameters.put(ACTION, "RunInstances");

		parameters.put("ImageId", properties.getImageId());
		parameters.put("MinCount", Integer.toString(1));
		parameters.put("MaxCount", Integer.toString(1));

		parameters.put("SecurityGroup", properties.getSecurityGroup());
		parameters.put("InstanceType", properties.getInstanceType());

		String response = makeRequest(parameters);

		return response;

	}

	public String startIntances(String[] instanceIds) throws CloudException {

		Map<String, String> parameters = getDefaultSiginV2Params();
		parameters.put(ACTION, "StartInstances");

		if (instanceIds != null) {
			for (int i = 0; i < instanceIds.length; i++) {
				parameters.put("InstanceId" + "." + (i + 1), instanceIds[i]);
			}
		}

		String response = makeRequest(parameters);

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufrn.dimap.signature.EC2#stopInstances(java.lang.String[],
	 * boolean)
	 */

	public String stopInstances(String[] instanceIds, boolean force)
			throws CloudException {
		Map<String, String> parameters = getDefaultSiginV2Params();
		parameters.put(ACTION, "StopInstances");

		if (instanceIds != null) {
			for (int i = 0; i < instanceIds.length; i++) {
				parameters.put("InstanceId" + "." + (i + 1), instanceIds[i]);
			}
		}

		if (force) {
			parameters.put("Force", "true");
		}

		String response = makeRequest(parameters);

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufrn.dimap.signature.EC2#rebootInstances(java.lang.String[])
	 */

	public void rebootInstances(String[] instanceIds) throws CloudException {
		Map<String, String> parameters = getDefaultSiginV2Params();
		parameters.put(ACTION, "RebootInstances");

		if (instanceIds != null) {
			for (int i = 0; i < instanceIds.length; i++) {
				parameters.put("InstanceId" + "." + (i + 1), instanceIds[i]);
			}
		}

		String response = makeRequest(parameters);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufrn.dimap.signature.EC2#terminateInstances(java.lang.String[])
	 */

	public String terminateInstances(String[] instanceIds)
			throws CloudException {
		Map<String, String> parameters = getDefaultSiginV2Params();
		parameters.put(ACTION, "TerminateInstances");

		if (instanceIds != null) {
			for (int i = 0; i < instanceIds.length; i++) {
				parameters.put("InstanceId" + "." + (i + 1), instanceIds[i]);
			}
		}

		String response = makeRequest(parameters);

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufrn.dimap.signature.EC2#describeImages(java.lang.String[],
	 * java.lang.String[], java.lang.String[])
	 */

	public String describeImages(String[] imageIds, String[] owners,
			String[] executableBy, String names[]) throws CloudException {

		Map<String, String> parameters = getDefaultSiginV2Params();

		parameters.put("Action", "DescribeImages");

		if (imageIds != null) {
			for (int i = 0; i < imageIds.length; i++) {
				parameters.put("ImageId" + "." + (i + 1), imageIds[i]);
			}
		}

		if (owners != null) {
			for (int i = 0; i < owners.length; i++) {
				parameters.put("Wouner" + "." + (i + 1), owners[i]);
			}
		}

		if (executableBy != null) {
			for (int i = 0; i < executableBy.length; i++) {
				parameters.put("executableBy" + "." + (i + 1), executableBy[i]);
			}
		}
		if (names != null) {
			for (int i = 0; i < names.length; i++) {
				parameters.put("Filter" + "." + (i + 1) + ".Name", names[i]);
			}
		}

		String response = makeRequest(parameters);

		return response;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufrn.dimap.signature.EC2#registerImage(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */

	public void registerImage(String imageLocation, String name,
			String description, String architecture, String rootDeviceName,
			String virtualizationType, String kernelId, String ramdiskId)
			throws CloudException {
		Map<String, String> parameters = getDefaultSiginV2Params();

		parameters.put("Action", "RegisterImage");

		if (imageLocation != null) {
			parameters.put("ImageLocation", imageLocation);
		}

		assert name != null;
		parameters.put("Name", name);

		if (description != null) {
			parameters.put("Description", description);
		}

		if (architecture != null) {
			assert (architecture.equals("i386") || architecture
					.equals("x86_64"));
			parameters.put("Architecture", architecture);
		}

		if (rootDeviceName != null) {
			parameters.put("RootDeviceName", rootDeviceName);
		}

		if (virtualizationType != null) {
			assert (virtualizationType.equals("paravirtual") || virtualizationType
					.equals("hvm"));
			parameters.put("VirtualizationType", virtualizationType);
		}

		if (kernelId != null) {
			parameters.put("KernelId", kernelId);
		}

		if (ramdiskId != null) {
			parameters.put("RamdiskId", ramdiskId);
		}

		String response = makeRequest(parameters);

		System.out.println(response);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufrn.dimap.signature.EC2#deregisterImage(java.lang.String)
	 */

	public void deregisterImage(String imageId) throws CloudException {

		assert imageId != null;

		Map<String, String> parameters = getDefaultSiginV2Params();

		parameters.put("Action", "DeregisterImage");
		parameters.put("ImageId", imageId);

		String response = makeRequest(parameters);

		System.out.println(response);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufrn.dimap.signature.EC2#describeAddresses(java.lang.String[],
	 * java.lang.String[])
	 */

	public String describeAddresses(String[] publicIps, String[] allocationIds)
			throws CloudException {

		Map<String, String> parameters = getDefaultSiginV2Params();

		parameters.put("Action", "DescribeAddresses");

		if (publicIps != null) {
			for (int i = 0; i < publicIps.length; i++) {
				parameters.put("PublicIp" + "." + (i + 1), publicIps[i]);
			}
		}

		if (allocationIds != null) {
			for (int i = 0; i < allocationIds.length; i++) {
				parameters
						.put("AllocationId" + "." + (i + 1), allocationIds[i]);
			}
		}

		String response = makeRequest(parameters);

		return response;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufrn.dimap.signature.EC2#allocateAddress(boolean)
	 */

	public String allocateAddress(boolean vpc) throws CloudException {
		Map<String, String> parameters = getDefaultSiginV2Params();

		parameters.put("Action", "AllocateAddress");

		if (vpc) {
			parameters.put("Domain", "vpc");
		}

		String response = makeRequest(parameters);

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufrn.dimap.signature.EC2#associateAddress(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * boolean)
	 */

	public String associateAddress(String publicIp, String instanceId,
			String allocationId, String networkInterfaceId,
			String privateIpAddress, boolean allowReassociation)
			throws CloudException {
		Map<String, String> parameters = getDefaultSiginV2Params();

		parameters.put("Action", "AssociateAddress");

		if (publicIp != null) {
			parameters.put("PublicIp", publicIp);
		}

		if (instanceId != null) {
			parameters.put("InstanceId", instanceId);
		}

		if (allocationId != null) {
			parameters.put("AllocationId", allocationId);
		}

		if (networkInterfaceId != null) {
			parameters.put("NetworkInterfaceId", networkInterfaceId);
		}

		if (privateIpAddress != null) {
			parameters.put("PrivateIpAddress", privateIpAddress);
		}

		if (allowReassociation) {
			parameters.put("AllowReassociation", "true");
		}

		String response = makeRequest(parameters);

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufrn.dimap.signature.EC2#disassociateAddress(java.lang.String,
	 * java.lang.String)
	 */

	public String disassociateAddress(String publicIp, String associationId)
			throws CloudException {

		Map<String, String> parameters = getDefaultSiginV2Params();

		parameters.put("Action", "DisassociateAddress");

		if (publicIp != null) {
			parameters.put("PublicIp", publicIp);
		}

		if (associationId != null) {
			parameters.put("AssociationId", associationId);
		}

		String response = makeRequest(parameters);

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufrn.dimap.signature.EC2#releaseAddress(java.lang.String,
	 * java.lang.String)
	 */

	public String releaseAddress(String publicIp, String associationId)
			throws CloudException {

		Map<String, String> parameters = getDefaultSiginV2Params();

		parameters.put("Action", "ReleaseAddress");

		if (publicIp != null) {
			parameters.put("PublicIp", publicIp);
		}

		if (associationId != null) {
			parameters.put("AssociationId", associationId);
		}

		String response = makeRequest(parameters);

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufrn.dimap.signature.EC2#describekeyPairs(java.lang.String[])
	 */

	public void describekeyPairs(String[] keyNames) throws CloudException {

		Map<String, String> parameters = getDefaultSiginV2Params();

		parameters.put("Action", "DescribeKeyPairs");

		if (keyNames != null) {
			for (int i = 0; i < keyNames.length; i++) {
				parameters.put("KeyName" + "." + (i + 1), keyNames[i]);
			}
		}

		String response = makeRequest(parameters);

		System.out.println(response);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufrn.dimap.signature.EC2#createKeyPair(java.lang.String)
	 */

	public void createKeyPair(String keyName) throws CloudException {

		assert keyName != null;

		Map<String, String> parameters = getDefaultSiginV2Params();

		parameters.put("Action", "CreateKeyPair");

		parameters.put("KeyName", keyName);

		String response = makeRequest(parameters);

		System.out.println(response);

	}

	private void deleteKeyPair(String keyName) throws CloudException {

		assert keyName != null;

		Map<String, String> parameters = getDefaultSiginV2Params();

		parameters.put("Action", "DeleteKeyPair");

		parameters.put("KeyName", keyName);

		String response = makeRequest(parameters);

		System.out.println(response);
	}

	private String makeRequest(Map<String, String> parameters)
			throws CloudException {

		String signature;
		try {
			signature = SignatureUtils.signParameters(parameters, properties.getSecretKey(),
					HttpMethod, serviceEndPoint.getHost(),
					serviceEndPoint.getPath());

			parameters.put(SIGNATURE_KEYNAME, signature);

			HttpClient httpclient = new DefaultHttpClient();

			HttpPost httpPatch = new HttpPost(generateURL(parameters));

			System.out.println(httpPatch);
			HttpResponse response = httpclient.execute(httpPatch);

			InputStreamReader res = new InputStreamReader(response.getEntity()
					.getContent());

			String resp = "";
			BufferedReader br = new BufferedReader(res);
			String output;

			while ((output = br.readLine()) != null) {
				resp += output;
			}

			System.out.println(resp);
			return resp;
		} catch (SignatureException e) {
			throw new CloudException(e.getMessage());

		} catch (UnsupportedOperationException e) {
			throw new CloudException(e.getMessage());
		} catch (IOException e) {
			throw new CloudException(e.getMessage());
		}
	}

	public String describeInstanceAttribute(String instanceId, String attribute)
			throws CloudException {
		Map<String, String> parameters = getDefaultSiginV2Params();

		parameters.put("Action", "DescribeInstanceAttribute");

		parameters.put("InstanceId", instanceId);
		parameters.put("Attribute", attribute);

		String response = makeRequest(parameters);

		return response;

	}

	

}
