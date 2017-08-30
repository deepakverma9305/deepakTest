package com.ilabquality.gtaf.testsuite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import com.ilabquality.gtaf.gtafgui.variablesManager.helper.VariablesHelper;

public class TestSuite {
	private String testSuiteName;
	private int testSuiteID;
	private String testSuiteDescription;
	private ArrayList<Test> suiteData = new ArrayList<Test>();
	private Test selectedTest;
	private boolean stopSuiteExecution = false;
	
	private VariablesHelper variableHelper;
	
	public String getTestSuiteName() {
		return testSuiteName;
	}

	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

	public int getTestSuiteID() {
		return testSuiteID;
	}

	public void setTestSuiteID(int testSuiteID) {
		this.testSuiteID = testSuiteID;
	}

	public String getTestSuiteDescription() {
		return testSuiteDescription;
	}

	public void setTestSuiteDescription(String testSuiteDescription) {
		this.testSuiteDescription = testSuiteDescription;
	}

	public ArrayList<Test> getSuiteData() {
		return suiteData;
	}

	public void setSuiteData(ArrayList<Test> suiteData) {
		this.suiteData = suiteData;
	}
	
	public String toString(){
		return "";
		
	}

	public int getNextExecutionOrderID() {
		return getSuiteData().size() +1;
	}

	public String getReportData(){
		StringBuilder sb = new StringBuilder();
	
		//Check if we have passed
		boolean hasPassed = true;
		for  (Test test : getSuiteData()){
			for ( TestValidation tv : test.getValidations()){
				if ( !tv.hasPassed())
				{
					hasPassed = false;
				}
			}
		}
	
		String trStyle = (hasPassed)?"background-color:lightgreen;":"background-color: rgba(216, 7, 27, 0.3)";
		String divID = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
		String imgID = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
		 	
		sb.append("<table style='width:100%;' ><!-- Define Main Service DIV-->\n" +				
					"<tr style=\""+trStyle+"\">\n"
					+ "<td width = '1%' style='background-color:white'><img id = \"" + imgID +"\" src=' " + ( hasPassed?"plus.png":"minus.png") +"' onclick=\"expander('"+divID+"','"+imgID+"')\"' ></td>\n"
					+ "<td class='header' style='align:left;'>"+getTestSuiteName()+" - "+getTestSuiteDescription()+"</td>\n"
				+  "</tr>\n" +
				"</table>\n");
		
		sb.append("<div id='"+divID+"' "+ ( hasPassed?"style='display:none;'":"") +"> <!-- Start Test DIV -->");
		for ( Test test : getSuiteData()){
			sb.append(test.getReportData());
		}
		sb.append("</div>");//Close off TestSuiteDIV
		return sb.toString();
		
	}

	public Test getSelectedTest() {
		return selectedTest;
	}

	public void setSelectedTest(Test selectedTest) {
		this.selectedTest = selectedTest;
	}
	
	public Test getTestByID(int id){
		Test returnTest = null;
		for ( Test t : getSuiteData()){
			if ( t.getId() == id){
				returnTest = t;
				break;
			}
		}
		return returnTest;
	}

	public boolean mustStopSuiteExecution() {
		return stopSuiteExecution;
	}

	public void setStopSuiteExecution(boolean stopSuiteExecution) {
		this.stopSuiteExecution = stopSuiteExecution;
	}
	
	public VariablesHelper getVariablesHelper(){
		return ( this.variableHelper == null ? new VariablesHelper(this): variableHelper);
	}

	public void reload() throws SQLException {
		this.getSuiteData().clear();
		this.setSuiteData(new TestSuiteFactory().getTestSuite(this.getTestSuiteName()).getSuiteData());
		
	}
	
	public void replaceValuesInSuite(String prefix, String variableText,String suffix , String variableName) throws SQLException {
		//
		//CallResult
		for ( Test test : getSuiteData()){
			String callResult = test.getCallResult();
			if ( callResult.contains(prefix+variableText+suffix) || callResult.contains("\"id\":" + variableText+suffix) || 
					callResult.contains("/tasks/"+variableText) || callResult.contains("/process-instances/" + variableText) || 
					callResult.contains("/executions/" + variableText)){
				if ( getSelectedTest().getId() != test.getId() && getSelectedTest().getExecutionIndex() < test.getExecutionIndex() ){
					while  ( callResult.contains(prefix+variableText+suffix) || 
							callResult.contains("/tasks/"+variableText) || callResult.contains("/process-instances/" + variableText) || 
							callResult.contains("/executions/" + variableText)){
							//We are dealing with a test that appears after the extraction, proceed to substitute.
							Variable v = new Variable();
							v.setTestSuiteID(getTestSuiteID());
							v.setVariableStartValue(prefix);
							v.setVariableOriginalValue(variableText);
							v.setVariableEndValue(suffix);
							v.setVariableName(variableName);
							v.setTestCaseID(test.getId());
							v.setVariableAction(Variable.SUBSTITUTED);
							test.getVariables().add(v);
							System.out.println("[CALLRESULT]Variable " + variableName + " was added to the test suite for save");
							callResult = callResult.replace(prefix+variableText+suffix, prefix+variableName+suffix)
									.replace("\"id\":"+variableText+suffix, "\"id\":"+variableName+suffix) //Handle IDs
									.replace("/tasks/"+variableText, "/tasks/"+variableName)// Cater for Embedded URLs
									.replace("/process-instances/" + variableText, "/process-instances/"+variableName)
									.replace("/executions/" + variableText, "/executions/" + variableName);// Cater for Embedded URLs
						}
				}
				
				test.setCallResult(callResult);
			}
		}
			//PostData
		for ( Test test : getSuiteData()){
			String postData = test.getPostData();
			if ( postData.contains(prefix+variableText+suffix) || postData.contains("\"id\":" + variableText+suffix) || 
					postData.contains("/tasks/"+variableText) || postData.contains("/process-instances/" + variableText) || 
					postData.contains("/executions/" + variableText)){
				if ( getSelectedTest().getId() != test.getId() && getSelectedTest().getExecutionIndex() < test.getExecutionIndex() ){
					while  ( postData.contains(prefix+variableText+suffix) || 
							postData.contains("/tasks/"+variableText) || postData.contains("/process-instances/" + variableText) || 
							postData.contains("/executions/" + variableText)){
							//We are dealing with a test that appears after the extraction, proceed to substitute.
							Variable v = new Variable();
							v.setTestSuiteID(getTestSuiteID());
							v.setVariableStartValue(prefix);
							v.setVariableOriginalValue(variableText);
							v.setVariableEndValue(suffix);
							v.setVariableName(variableName);
							v.setTestCaseID(test.getId());
							v.setVariableAction(Variable.SUBSTITUTED);
							test.getVariables().add(v);
							System.out.println("[POSTDATA]Variable " + variableName + " was added to the test suite for save");
							postData = postData.replace(prefix+variableText+suffix, prefix+variableName+suffix)
									.replace("\"id\":"+variableText+suffix, "\"id\":"+variableName+suffix) //Handle IDs
									.replace("/tasks/"+variableText, "/tasks/"+variableName)// Cater for Embedded URLs
									.replace("/process-instances/" + variableText, "/process-instances/"+variableName)
									.replace("/executions/" + variableText, "/executions/" + variableName);// Cater for Embedded URLs
						}
					
				}
			
				test.setPostData(postData);
			}
		}
			//WebServiceURL
		for ( Test test : getSuiteData()){
			String url = test.getWebServiceURL();
			if ( url.contains("/tasks/"+variableText)){
				if ( getSelectedTest().getId() != test.getId() && getSelectedTest().getExecutionIndex() < test.getExecutionIndex() ){
					while  (url.contains("/tasks/"+variableText)){
							Variable v = new Variable();
							v.setTestSuiteID(getTestSuiteID());
							v.setVariableStartValue(prefix);
							v.setVariableOriginalValue(variableText);
							v.setVariableEndValue(suffix);
							v.setVariableName(variableName);
							v.setTestCaseID(test.getId());
							v.setVariableAction(Variable.SUBSTITUTED);
							test.getVariables().add(v);
							System.out.println("[URL]Variable " + variableName + " was added to the test suite for save");
							url = url.replace(variableText, variableName)
									.replace("/tasks/"+variableText, "/tasks/"+variableName)
									.replace("/process-instances/" + variableText, "/process-instances/"+variableName)
									.replace("/executions/" + variableText, "/executions/" + variableName);// Cater for Embedded URLs
					}
				}
			}
			test.setWebServiceURL(url);
		}
		for ( Test test : getSuiteData()){
			ArrayList<TestValidation> validations = test.getValidations();
			for (TestValidation tv : validations){
				if ( tv.getExpectedResult().equalsIgnoreCase(prefix+variableText+suffix)){
					tv.setExpectedResult(prefix+variableName+suffix);
				}
			}
		}
		
//				prepareUpdatesForVariables("Select id, callresult from pangeatest.testcase where testsuiteid = "    + suite.getTestSuiteID() + " order by executionorderindex asc",suite, prefix, variableText,suffix , variableName,"callresult");
//				prepareUpdatesForVariables("Select id, postdata from pangeatest.testcase where testsuiteid = "      + suite.getTestSuiteID() + " order by executionorderindex asc",suite, prefix, variableText,suffix , variableName,"postdata");
//				prepareUpdatesForVariables("Select id, webserviceurl from pangeatest.testcase where testsuiteid = " + suite.getTestSuiteID() + " order by executionorderindex asc",suite, prefix, variableText,suffix , variableName,"webserviceurl");
//				
//				
//				//prepareUpdatesForVariables("Select id, webserviceurl from pangeatest.testcase where testsuiteid = " + suite.getTestSuiteID(),suite, prefix, "/tasks/" + variableText,suffix , variableName,updates,"webserviceurl");
	}
	
}
