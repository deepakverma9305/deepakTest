package com.ilabquality.gtaf.result.callService;

public class CallServiceResult {
	private String webServiceName;
	private boolean alive;
	
	public CallServiceResult(String name, boolean wasAlive){
		this.webServiceName = name;
		this.alive = wasAlive;
	}
	
	public String getWebServiceName() {
		return webServiceName;
	}
	public void setWebServiceName(String webServiceName) {
		this.webServiceName = webServiceName;
	}
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean wasAlive) {
		this.alive = wasAlive;
	}
	
	
}
