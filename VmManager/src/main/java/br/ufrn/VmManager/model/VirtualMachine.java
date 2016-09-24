package br.ufrn.VmManager.model;



public class VirtualMachine {

	private IpAddress ipAddress;
	private String template;
	private String image;
	private VmState state;
	private VmMetric vmMetric;

	public VirtualMachine() {

	}

	
	public VirtualMachine(IpAddress ipAddress, String template, String image,
			VmState state, VmMetric vmMetric) {
		super();
		this.ipAddress = ipAddress;
		this.template = template;
		this.image = image;
		this.state = state;
		this.vmMetric = vmMetric;
	}


	public VirtualMachine(String template, String image, VmState state) {
		super();
		this.template = template;
		this.image = image;
		this.state = state;
	}

	

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public VmState getState() {
		return state;
	}

	public void setState(VmState state) {
		this.state = state;
	}

	public IpAddress getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(IpAddress ipAddress) {
		this.ipAddress = ipAddress;
	}

	public VmMetric getVmMetric() {
		return vmMetric;
	}

	public void setVmMetric(VmMetric vmMetric) {
		this.vmMetric = vmMetric;
	}

	@Override
	public String toString() {
		return "VirtualMachine [ipAddress=" + ipAddress + ", template="
				+ template + ", image=" + image + ", state=" + state
				+ ", vmMetric=" + vmMetric + "]";
	}

	

}
