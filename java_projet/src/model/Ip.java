package model;

public class Ip {
	private String ip;
	private String other;
	
	public Ip(String ip, String other) {
		//TODO
		this.ip = ip;
		this.other = other;
	}
	
	public String getIp() {
		return ip;
	}
	
	public String getOther() {
		return other;
	}
	
	public String toString() {
		return getIp()+" "+getOther();
	}
}
