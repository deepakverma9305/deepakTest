package com.ilabquality.gtaf.guitests;

public class GUITestDef {
	private int guiTestSuiteID;
	private int id;
	private String testName;
	private String clazzName;
	private int guiTestCaseID;
	private int executionOrder;
	public int getGuiTestSuiteID() {
		return guiTestSuiteID;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getClazzName() {
		return clazzName;
	}
	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}
	public void setGuiTestSuiteID(int guiTestSuiteID) {
		this.guiTestSuiteID = guiTestSuiteID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGuiTestCaseID() {
		return guiTestCaseID;
	}
	public void setGuiTestCaseID(int guiTestCaseID) {
		this.guiTestCaseID = guiTestCaseID;
	}
	public int getExecutionOrder() {
		return executionOrder;
	}
	public void setExecutionOrder(int executionOrder) {
		this.executionOrder = executionOrder;
	}
}
