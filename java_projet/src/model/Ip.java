package model;

public class Ip {
	private String ip;
	private String description;
	
	public Ip(String ip, String description) {
		//TODO
		this.ip = ip;
		this.description = description;
	}
	
	public String getIp() {
		return this.ip;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String toString() {
		return this.getIp()+" "+this.getDescription();
	}
}
