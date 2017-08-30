package com.ilabquality.gtaf.guitests.helpers;

import java.text.SimpleDateFormat;

public class TestStep {
	private String stepName;
	private boolean hasPassed = true;
	private String testStepImage;
	private String optionalFailureMessage;
	public TestStep(){
	}
	
	public void setStepName(String stepName){
		
		this.stepName = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()) +" - " + stepName;
	}
	public String getStepName() {
		return stepName;
	}
	public boolean hasPassed() {
		return hasPassed;
	}
	public void setPassed(boolean stepResult) {
		this.hasPassed = stepResult;
	
	}
	public String getTestStepImage() {
			return testStepImage;
	}
	public void setTestStepImage(String testStepImage) {
		this.testStepImage = testStepImage;
	}
	
	public String getReportContent(int stepIndex){
		String str = "";
		str = "<tr  " + (hasPassed()?"":"style=\"background-color:red;\"") + ">"
							+ "	<td width=\"6%\">" + stepIndex + "</td>"
							+ "	<td width=\"85%\">" + getStepName() + getOptionalFailureMessage()+"</td>"
							+ "	<td width=\"9%\">" + (hasPassed()?"<img src='tick.jpg'>":"<img src='cross.jpg'>") + "</td> "
			+ "	</tr>"
			+	"<tr> "
			+ "		<td style='background-color:none;'>&nbsp;&nbsp;&nbsp; </td>";
		if (getTestStepImage() != null && !getTestStepImage().equalsIgnoreCase("null") && !getTestStepImage().equalsIgnoreCase("")){
			str = str + "		<td colspan='3'>"
					+ "		<img src = 'data:image/png;base64," +  getTestStepImage() +"' width='100%' height='768'/>"
					+ "	</td>";
				
		}
			str = str + "</tr>";
			return str;
	}

	public void setOptionalFailureMessage(String message) {
		this.optionalFailureMessage = message;
	}
	
	public String getOptionalFailureMessage(){
		return ( this.optionalFailureMessage == null? "" : " - " + this.optionalFailureMessage);
	}
}
