package br.ufrn.VmManager.model;

public class IpAddress {

	private String ip;
	private Boolean isPublic;

	public IpAddress(){
		
	}
	
	public IpAddress(String ip, Boolean isPublic) {
		super();
		this.ip = ip;
		this.isPublic = isPublic;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Boolean isPublic() {
		return isPublic;
	}
	public void setPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	@Override
	public String toString() {
		return "IpAddress [ip=" + ip + ", isPublic=" + isPublic + "]";
	}

	
	
}
