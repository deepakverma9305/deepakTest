package com.ilabquality.gtaf.testsuite;

public class Variable {
	private int id;
	private int testSuiteID;
	private String variableName;
	private String variableStartValue;
	private String variableEndValue;
	private String variableOriginalValue;
	private String liveValue;
	private int testcaseid;
	private String variableAction; //Substitution / Extraction
	private boolean isSystemRuntimeVariable = false;
	public static final String SUBSTITUTED = "Substituted";
	public static final String EXTRACTED = "Extracted";
	public Variable (String variableName, String variableValue){
		this.variableName = variableName;
		this.liveValue = variableValue;
	}
	public Variable (String variableName, String variableValue, boolean isSystemRuntime){
		this.variableName = variableName;
		this.liveValue = variableValue;
		this.isSystemRuntimeVariable = isSystemRuntime;
	}
	public Variable (){}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTestSuiteID() {
		return testSuiteID;
	}
	public void setTestSuiteID(int testID) {
		this.testSuiteID = testID;
	}
	public String getVariableName()  {
		return  variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public String getVariableStartValue() {
		return variableStartValue;
	}
	public void setVariableStartValue(String variableValue) {
		this.variableStartValue = variableValue;
	}
	public String getVariableEndValue() {
		return variableEndValue;
	}
	public void setVariableEndValue(String variableEndValue) {
		this.variableEndValue = variableEndValue;
	}
	public String getVariableOriginalValue() {
		return variableOriginalValue;
	}
	public void setVariableOriginalValue(String variableOriginalValue) {
		this.variableOriginalValue = variableOriginalValue;
	}
	public String getLiveValue() {
		return liveValue;
	}
	public void setLiveValue(String liveValue) {
		this.liveValue = liveValue;
	}
	@Override
	public String toString(){
//		return 	"ID : " + id + "\n" +
//				"TestSuiteID : " + testSuiteID + "\n" +
//				"VariableName : " + variableName + "\n" + 
//				"VariableStartValue : " + variableStartValue + "\n" + 
//				"VariableEndValue : " + variableEndValue + "\n" + 
//				"VariableOriginalValue : " +  variableOriginalValue+ "\n" + 
//				"LiveValue : " + liveValue + "\n" ;
		return 	"\nID : " + id  +"\n" +
		" VariableName : " + variableName + "\n" +
		" LiveValue : " + liveValue +"\n" + "\n" + 
		" VariableAction : " + variableAction;
	}
	public int getTestCaseID() {
		return testcaseid;
	}
	public void setTestCaseID(int testcaseid) {
		this.testcaseid = testcaseid;
	}

	public String getVariableAction() {
		return variableAction;
	}

	public void setVariableAction(String variableAction) {
		this.variableAction = variableAction;
	}

	public boolean isSystemRuntimeVariable() {
		return isSystemRuntimeVariable;
	}

	public void setSystemRuntimeVariable(boolean isSystemRuntimeVariable) {
		this.isSystemRuntimeVariable = isSystemRuntimeVariable;
	}
}
