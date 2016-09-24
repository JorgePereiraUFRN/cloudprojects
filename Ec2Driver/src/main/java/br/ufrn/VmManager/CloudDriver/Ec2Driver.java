package br.ufrn.VmManager.CloudDriver;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import br.ufrn.VmManager.Exceptions.CloudException;
import br.ufrn.VmManager.model.IpAddress;
import br.ufrn.VmManager.model.Storage;
import br.ufrn.VmManager.model.VirtualMachine;
import br.ufrn.VmManager.model.VmState;

public class Ec2Driver implements CloudDriverInterface {

	private Ec2Actions ec2 = new Ec2Actions();

	public Ec2Driver() {
		// TODO Auto-generated constructor stub
	}

	private static String getChildTagValue(Element elem, String tagName)
			throws Exception {
		NodeList children = elem.getElementsByTagName(tagName);
		String result = null;

		if (children == null) {
			return result;
		}

		Element child = (Element) children.item(0);

		if (child == null) {
			return result;
		}

		result = child.getTextContent().trim();

		return result;
	}

	private IpAddress getIpAddres(String VmId) throws CloudException {

		IpAddress ip = null;

		DocumentBuilder db;

		try {
			String response = ec2.DescribeInstances(new String[] { VmId });

			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(response));
			Document doc = db.parse(is);

			NodeList nodes = doc.getElementsByTagName("instancesSet");

			Element element = (Element) nodes.item(0);

			String ipNumber = getChildTagValue(element, "ipAddress");

			if (ipNumber != null && !ipNumber.equals("")) {
				ip = new IpAddress();
				ip.setIp(ipNumber);
				ip.setPublic(false);

				return ip;

			}

		} catch (ParserConfigurationException e) {
			throw new CloudException(e.getMessage());
		} catch (Exception e) {
			throw new CloudException(e.getMessage());
		}

		return null;
	}

	private VirtualMachine getVM(Element element) throws CloudException {

		VirtualMachine vm = null;
		try {
			vm = new VirtualMachine();
			vm.setId(getChildTagValue(element, "instanceId"));
			vm.setImage(getChildTagValue(element, "imageId"));
			vm.setTemplate(getChildTagValue(element, "instanceType"));

			IpAddress ip = null;
			while (ip == null) {
				ip = getIpAddres(vm.getId());
				// TODO pode gerar loop infinito, rever depois
				Thread.sleep(5 * 1000);
			}

			vm.setIpAddress(ip);
			vm.setState(VmState.RUNNING);

		} catch (Exception e) {
			throw new CloudException(e.getMessage());
		}

		return vm;
	}

	public VirtualMachine createVM() throws CloudException {
		VirtualMachine virtualMachine = null;

		DocumentBuilder db;

		try {
			String response = ec2.runInstances();

			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(response));
			Document doc = db.parse(is);

			NodeList nodes = doc.getElementsByTagName("instancesSet");

			Element element = (Element) nodes.item(0);

			virtualMachine = getVM(element);

			return virtualMachine;

		} catch (ParserConfigurationException e) {
			throw new CloudException(e.getMessage());
		} catch (Exception e) {
			throw new CloudException(e.getMessage());
		}
	}

	public void deleteVM(VirtualMachine vm) throws CloudException {
		try {
			String resp = ec2.terminateInstances(new String[] { vm.getId() });
		} catch (Exception e) {
			throw new CloudException(e.getMessage());
		}

	}

	public Storage alocateStorage() throws CloudException {
		// TODO Auto-generated method stub
		return null;
	}

}
