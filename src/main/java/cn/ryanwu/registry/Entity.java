package cn.ryanwu.registry;

public class Entity {
	
	private String service;
	private String node;
	private int type;
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
}
