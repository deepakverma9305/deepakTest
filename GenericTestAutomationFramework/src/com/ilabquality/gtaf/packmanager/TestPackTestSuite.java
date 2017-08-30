package com.ilabquality.gtaf.packmanager;

public class TestPackTestSuite{
	private int suiteID;
	private int executionID;
	public TestPackTestSuite(int suiteID,int executionID){
		this.setSuiteID(suiteID);
		this.setExecutionID(executionID);
	}
	public int getExecutionID() {
		return executionID;
	}
	public void setExecutionID(int executionID) {
		this.executionID = executionID;
	}
	public int getSuiteID() {
		return suiteID;
	}
	public void setSuiteID(int suiteID) {
		this.suiteID = suiteID;
	}
}
