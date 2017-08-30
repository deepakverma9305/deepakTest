package com.ilabquality.gtaf.testsuite;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;
import org.testng.log4testng.Logger;

import com.ilabquality.gtaf.gtafAppl.GTAFApplController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.utils.email.GTAFEmailer;

public class Test {
	private String testName;
	private int executionIndex;
	private String testDescription;
	private String webServiceURL;
	private String liveURL;
	private String callResult;	
	private String liveCallResult="";	
	private boolean hasPassed = true;
	private String verb;
	private int id;
	private int testSuiteID;
	private String postData;
	private ArrayList<TestValidation> validations = new ArrayList<TestValidation>();
	private ArrayList<Variable> variables = new ArrayList<Variable>();
	private TestSuite parentSuite;
	private int delay;
	private String executionUserName;
	private String executionPassword;
	private DateTime startTime;
	private DateTime endTime;
	private double duration=0;
	public static final Charset UTF_8 = Charset.forName("UTF-8");

	public TestValidation getValidation(String text){
		TestValidation returnVal = null;
		for ( TestValidation tv : getValidations()){
			if ( tv.getExpectedResult().equalsIgnoreCase(text)){
				returnVal =  tv;
			}
		}
		return returnVal;
	}
	
	public String getLiveCallResult() {
		
		return liveCallResult;
	}

	public void setLiveCallResult(String liveCallResult) {
		this.liveCallResult = liveCallResult;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public int getExecutionIndex() {
		return executionIndex;
	}
	public void setExecutionIndex(int executionIndex) {
		this.executionIndex = executionIndex;
	}
	public String getTestDescription() {
		return testDescription;
	}
	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}
	public String getWebServiceURL() {
		
		return webServiceURL;
	}
	public void setWebServiceURL(String webServiceURL) {
		this.webServiceURL = webServiceURL;
	}
	public String getCallResult() {
		return (callResult == null?"null":callResult);
	}
	public void setCallResult(String callResult) {
		this.callResult = callResult;
	}
	public String getVerb() {
		return ( verb == null ? "GET":verb);
	}
	public void setVerb(String verb) {
		this.verb = verb;
	}
	
	public TestSuite getParentSuite(){
		return this.parentSuite;
	}
	public ArrayList<TestValidation> getValidations() {
		return validations;
	}
	public void setValidations(ArrayList<TestValidation> validations) {
		this.validations = validations;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPostData() {
		return ( postData == null?"":postData.replace("'", "\""));
	}
	public void setPostData(String postData) {
		this.postData = postData;
	}
	public void performTest(GTAFApplController parent) throws InterruptedException {
		int g=0;
		startTime = new DateTime(DateTime.now());
		if ( getWebServiceURL().contains("product/types")){
			endTime = new DateTime(DateTime.now());
			setCallResult("SKIPPED Product Types!!!!");
			setLiveCallResult("SKIPPED Product Types!!!!");
			return;
		}
			String callResult = "";
			System.out.println("****************************\nStarting test " + getId() + ":" + getExecutionIndex() + "::::::::::::::::"+getTestName()	+"\n***********************************\n");
			switch (getVerb().toLowerCase().trim() ){
				case "get":{
						parentSuite.getVariablesHelper().substituteURLs();
						startTime = new DateTime(DateTime.now());
						callResult = Runner.serviceConnector.callService(getLiveURL(),getExecutionUserName(),getExecutionPassword());
						endTime = new DateTime(DateTime.now());
						if ( callResult.contains("java.net.SocketTimeoutException:connecttimedout") || callResult.contains("java.net.ConnectException:Connectionrefused")){
							if ( parent != null ){
								parent.appendLog("Unable to connect to the test server ["+ Runner.getRuntimeVariables().get("@{ServicesServerIP}").get(0).getLiveValue()+"]");
								parent.appendLog("Aborting...");
								Runner.cliExitCode = 2;
							}else{
								LoggerFactory.getLogger(GTAFApplController.class).error("Unable to connect to the test server ["+ Runner.getRuntimeVariables().get("@{ServicesServerIP}").get(0).getLiveValue()+"]");
								LoggerFactory.getLogger(GTAFApplController.class).error("Aborting...");
								Runner.cliExitCode = 2;
							}
							parentSuite.setStopSuiteExecution(true);
							if (parent != null)
								parent.setTaskDisplayer("Idle...");
							return;
						}else{
							parentSuite.getVariablesHelper().substituteLiveValues(callResult);
							setLiveCallResult(callResult);
						}
					break;
				}
				case "post":{
					System.out.println("TCID : " + getId() + ":::::" );//+  parentSuite.getActiveVariables().toString());
					String tempPostData = getPostData();
					parentSuite.getVariablesHelper().substituteURLs();
						if  (getLiveURL().endsWith("restart") ){
							if( parent != null ){
								parent.appendLog("Restart Triggered");
							}else{
								LoggerFactory.getLogger(GTAFApplController.class).info("Restart Triggered");
							}
						}
						if  (getLiveURL().endsWith("health") ){
							int counter = 0;
							try{
								int svcFailTimeout = 60;
								try {
									svcFailTimeout = Integer.parseInt(Runner.getRuntimeVariables().get("@{ServiceCallFailTimeout}").get(0).getLiveValue());
								} catch (NumberFormatException e) {
									// 
									Logger.getLogger(Runner.class).error("The variable for @{ServiceCallFailTimeout} could not be parsed. Please check the parameters in the config file. Defaulting to 60 seconds.",e );
								}
								String result =  Runner.serviceConnector.callPostService(getLiveURL(),"",getExecutionUserName(),getExecutionPassword());
								while (!result.contains("\"status\":\"UP\"")){
									if ( result.contains("form id=\"kc-form-login\"")){
										getParentSuite().setStopSuiteExecution(true);
										
										if (parent != null ){
											parent.appendLog("Logon page detected on health check. This service should be excluded from KeyCloak. Aborting...");
											parent.showErrorAlert("Logon page detected on health check. This service should be excluded from KeyCloak. Aborting...");
										}else{
											LoggerFactory.getLogger(GTAFApplController.class).error("Logon page detected on health check. This service should be excluded from KeyCloak. Aborting...");
										}
										
										return;
									}
									if(parent != null ){
										parent.setTaskDisplayer("Waiting for Pangea App to start..." + counter);
									}
									Thread.currentThread();
									Thread.sleep(1000);
									counter++;
									if ( counter == svcFailTimeout){
										getParentSuite().setStopSuiteExecution(true);
										if (parent != null){
											parent.appendLog("Timeout Exceeded [" + svcFailTimeout + "], aborting...");
										return;
										}else{
											LoggerFactory.getLogger(GTAFApplController.class).error("Timeout Exceeded ["+svcFailTimeout+"], aborting...");
										}
									}
									result =  Runner.serviceConnector.callPostService(getLiveURL(),"",getExecutionUserName(),getExecutionPassword());
								}
							}catch(InterruptedException ie){
								LoggerFactory.getLogger(Runner.class).info("Wait time for Application Start interrupted. ",ie);
								throw(ie);
								//return;
							}
							if (parent != null){
								parent.appendLog("System is UP, continuing...");
								parent.setTaskDisplayer("Processing Pack");
							}else{
								LoggerFactory.getLogger(GTAFApplController.class).info("System is UP, continuing...");
							}
							return;
						}
						else if (getLiveURL().contains("SEND_EMPTY_EMAIL_TO_QUERY")){
							startTime = new DateTime(DateTime.now());
							GTAFEmailer emailer = new GTAFEmailer("EMPTY email, Straight to query") {
								
								@Override
								public String getBodyContent() {
									return "This is an empty email and should go straight to query";
								}
							};
							emailer.send();
							emailer.check();
							endTime = new DateTime(DateTime.now());
						}
						else{
							tempPostData = parentSuite.getVariablesHelper().substituteLiveValues(getPostData());
							startTime = new DateTime(DateTime.now());
							callResult = Runner.serviceConnector.callPostService(getLiveURL(), tempPostData,getExecutionUserName(),getExecutionPassword());
							endTime = new DateTime(DateTime.now());
						}
						if ( callResult.contains("java.net.SocketTimeoutException:connecttimedout") || callResult.contains("java.net.ConnectException:Connectionrefused")){
							if (parent != null){
								parent.appendLog("Unable to connect to the test server ["+ Runner.getRuntimeVariables().get("@{ServicesServerIP}").get(0).getLiveValue()+"]");
								parent.appendLog("Aborting...");
								parentSuite.setStopSuiteExecution(true);
							}else{
								LoggerFactory.getLogger(GTAFApplController.class).error("Unable to connect to the test server ["+ Runner.getRuntimeVariables().get("@{ServicesServerIP}").get(0).getLiveValue()+"]");
								LoggerFactory.getLogger(GTAFApplController.class).error("Aborting...");
								
							}
							if ( getValidations().size() == 0 ){
								getValidations().add(new TestValidation("Pass",callResult,false,getId(),this));
							}else{
								
							}
							parentSuite.setStopSuiteExecution(true);
							return;
						}
						parentSuite.getVariablesHelper().substituteLiveValues(callResult);
						setLiveCallResult(callResult);
					break;
				}
			}
			if ( getValidations().size() == 0 && callResult.contains("Exception"))
				getValidations().add(new TestValidation("Exception!!!!",callResult,false,getId(),this));
			setCallResult(callResult);
			for  ( TestValidation v : getValidations() ){
				v.setActualResult(getLiveCallResult());
				int i=0;
				v.validate();
				
			}
			//Final string checks and modifications. 
			ArrayList<String> sb = new ArrayList<>();
			for (TestValidation tv : getValidations()){
				if  (tv.isRegularExpression())
					sb.add(tv.getExpectedResult());
			}
			sb.add("[\"][a-zA-Z]*[\"][:]\\[");
			sb.add("\"[a-zA-Z]*\":\\{");
			
		
			for (String s : sb){
				doRegexMatches(s);
			}
			if  (validateZeroStringInCallResult(getLiveCallResult())){
				setLiveCallResult("");
			}
			if ( getWebServiceURL().contains("/auth/realms/") || getLiveCallResult().contains("Test BGi Template")){
				setLiveCallResult("");
			}
			setLiveCallResult(getLiveCallResult().replace("{","").replace("}", "").replace("]", "").replace("[", "").replace(",", "").replace("\"",""));
			
			setDuration((endTime.getMillis() - startTime.getMillis()) / 1000d);
			System.out.println("****************************\nEnding test " + getId() +"\n***********************************\n");
	}

	private boolean validateZeroStringInCallResult(String val){
		boolean returnValue = false;
		try {
			//notify:return function()return  hasDivider:return function()return true
			val = val.replace(".", "").replace(",","").replace("[","").replace("{","").replace("]","").replace("$rootScope.notifyCount", "")
								.replace("function()", "").replace("return true","").replace("hasDivider:", "").replace("notify:", "");
			Integer.parseInt(val);
			returnValue = true;//Will be true if conversion succeeds, i.e. if the value coming into the function is [{0},.00.000.0.0000000 <-- the remainders from the string replaces on the double values. 
		} catch (Exception e) {
		}
		return returnValue;
	}
	private void doRegexMatches(String patternS) {
		Pattern pattern = Pattern.compile(patternS);
		Matcher matcher = pattern.matcher(getLiveCallResult());
		while(matcher.find()) {
		   // System.out.println(matcher.group(0));
		  //  System.out.println("Removing " + matcher.group(0) + " from set");
		    setLiveCallResult(getLiveCallResult().replace(matcher.group(0),""));
		}
	}
	public String getReportData() {
		StringBuilder sb = new StringBuilder();
		try{
		String divID = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
		String imgID = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
		
		if ( !getLiveCallResult().isEmpty() && !getLiveCallResult().contains("realm-public-key")){
				hasPassed = false;
		}
		String trStyle = (hasPassed)?"background-color:lightgreen;":"background-color: rgba(216, 7, 27, 0.3)";
		sb.append("<table style=\"width:100%;\" cols=6>\n" +				
					"<tr style=\""+trStyle+"\">\n"
					+ "<td width = \"1%\" style=\"background-color:white\"><img id = \"" + imgID +"\" src=\"" + ( hasPassed?"plus.png":"minus.png") +"\" onclick=\"expander('"+divID+"','"+imgID+"')\" ></td>\n"
					+ "<td width=\"90%\" class=\"header\" style=\"align:left;\">TID : " + getId() + " EID : " + getExecutionIndex() + "  "+getTestName()+" - "+getTestDescription()+" using ["+ getLiveURL()+"] and a method of [" + getVerb() +"]"
							+ "</td>\n"
					+ "<td width=\"9%\" class=\"header\" style=\"align:right;\">TTE : " + getDuration() + " seconds</td>\n"
							
				+  "</tr>\n" +
				"</table>\n");
		
		sb.append("<div id=\""+divID+"\" "+ ( hasPassed?"style=\"display:none;\"":"")+" <!-- Start Test DIV -->");
		sb.append("	<table width=\"100%\" style=\"table-layout: fixed;\">" +				
						"<tr>"+
							"<td style=\"background-color:white\"><img src=\"blank.png\"/></td>"+
							"<td class=\"header\" width=\"5%\">Validation ID</td>" + 
							"<td class=\"header\" width=\"10%\">Associated Test Case</td>" + 
							"<td class=\"header\" width=\"40%\">Expected Result</td>" + 
							"<td class=\"header\" width=\"40%\">Actual Result</td>" + 
							"<td class=\"header\" width=\"5%\">Test Result</td>" + 
						"</tr>");
		
		for ( TestValidation tv : getValidations()){
			String styleTag = "tdStyle";
			trStyle = (tv.hasPassed())?"background-color:white;":"background-color: rgba(216, 7, 27, 0.3)";
			sb.append("<trstyle = \""+trStyle+"\">");
			sb.append("<td style=\"background-color:white\"></td>");
				sb.append("<td class = \""+ styleTag +"\">" + tv.getId() + "</td>");
				sb.append("<td class = \""+ styleTag +"\">" + (tv.getAssociatedTest() == null ? "N/A" : tv.getAssociatedTest().getTestName()) + "</td>");
				if ( tv.isRegularExpression()){
					sb.append("<td class = \""+ (tv.hasPassed()?"tdStyle":"tdErrorStyle") +"\"><p style=\"color:#00F;\">Regular Expression</p><p>" + tv.getLiveExpectedResult() + "</p></td>");
					sb.append("<td class = \""+ (tv.hasPassed()?"tdStyle":"tdErrorStyle") +"\">" + tv.getActualResult() + "</td>");
				}else{
					sb.append("<td class = \""+ (tv.hasPassed()?"tdStyle":"tdErrorStyle") +"\">" + tv.getLiveExpectedResult() + "</td>");
					sb.append("<td class = \""+ (tv.hasPassed()?"tdStyle":"tdErrorStyle") +"\">" + (tv.hasPassed()?tv.getLiveExpectedResult():getLiveCallResult()) + "</td>");
				}
				sb.append("<td class = \""+ styleTag +"\"><img src=\""+ (tv.hasPassed()?"tick.jpg":"cross.jpg")+"\"/></td>");
			sb.append("</tr>");
					
		}
		sb.append("<tr><td/></tr>"
					+ "<tr><td><img src=\"blank.png\"/></td>"
					+ "		<td colspan=\"3\" class=\"header\" > Variable  </td>"
					+ "		<td colspan=\"3\" class=\"header\" > Variable Value </td>"
					+ "</tr>");
		for ( Variable v : getVariables()){
			sb.append("<tr><td><img src=\"blank.png\"/></td>"
					+ "	<td colspan=\"2\" align=\"center\">" + (v.isSystemRuntimeVariable()?"System Config": v.getVariableAction()) + "</td>"
					+ "	<td colspan=\"2\" align=\"center\">" + v.getVariableName() + "</td>"
					+ "<td colspan=\"2\" align=\"center\">" + v.getLiveValue() + "</td>"
					+ "</tr>");
		}
		sb.append("<tr><td colspan=\"6\">" + getLiveCallResult() +"</td></tr>");
		sb.append("</table></div><br><br><!-- End Test Div -->");//Close off Test
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	public int getTestSuiteID() {
		return testSuiteID;
	}
	public void setTestSuiteID(int testSuiteID) {
		this.testSuiteID = testSuiteID;
	}
	public void setParentSuite(TestSuite t) {
		this.parentSuite = t;
	}
	public ArrayList<Variable> getVariables() {
		return variables;
	}
	public void setVariables(ArrayList<Variable> variables) {
		this.variables = variables;
	}
	
	public boolean containsVar(String var){
		boolean returnValue = false;
		for  (Variable v : getVariables()){
			if ( v.getVariableName().equalsIgnoreCase(var)){
				returnValue = true;
			}
		}
		return returnValue;
	}

	public String getLiveURL() {
		return liveURL;
	}
	public void setLiveURL(String liveURL) {
		this.liveURL = liveURL;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	public String getExecutionUserName() {
		return executionUserName;
	}
	public void setExecutionUserName(String executionUserName) {
		this.executionUserName = executionUserName;
	}
	public String getExecutionPassword() {
		return executionPassword;
	}
	public void setExecutionPassword(String executionPassword) {
		this.executionPassword = executionPassword;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}

	public boolean hasPassed() {
		return hasPassed;
	}

	public void setHasPassed(boolean hasPassed) {
		this.hasPassed = hasPassed;
	}
}
