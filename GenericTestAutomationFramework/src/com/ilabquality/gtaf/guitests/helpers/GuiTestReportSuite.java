package com.ilabquality.gtaf.guitests.helpers;

import java.util.ArrayList;
import java.util.UUID;

import org.joda.time.DateTime;

import com.ilabquality.gtaf.gtafAppl.Runner;

public class GuiTestReportSuite {

	private String suiteName;
	//private Hashtable<Integer,GuiTestReportSingleTest> tests  = new Hashtable<Integer,GuiTestReportSingleTest> ();
	private ArrayList<GuiTestReportSingleTest> tests  = new ArrayList<GuiTestReportSingleTest> ();
	private StringBuilder reportContent = new StringBuilder();
	public static int TEST_COUNTER=0;
	private DateTime startTime,endTime;
	
	public GuiTestReportSuite(){
		this.startTime = DateTime.now();
	}
	public DateTime getStartTime(){
		return this.startTime;
	}
	public ArrayList<GuiTestReportSingleTest> getTests() {
		return tests;
	}
	public void setTestst(ArrayList<GuiTestReportSingleTest>  tests) {
		this.tests = tests;
	}
	public String getSuiteName() {
		return suiteName;
	}
	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}
	
	public void addTest(GuiTestReportSingleTest grst){
		this.tests.add(grst);
	}
	
	public boolean hasPassed(){
		boolean returnValue = false;
		for ( GuiTestReportSingleTest test : tests){
			if ( test == null ){
				System.out.println("NULL Found");
				continue;
			}
			if ( !test.hasPassed()){
				returnValue = false;
				Runner.cliExitCode = 2;//Fail the Regression Stage in Jenkings.
				break;
			}
		}
		return returnValue;
	}
	
	
	public String getReportContent(){
		String divID = UUID.randomUUID().toString().replace("-","").substring(0,8);
		String imgID = UUID.randomUUID().toString().replace("-","").substring(0,8);
		String divID1 = UUID.randomUUID().toString().replace("-","").substring(0,8);
		String imgID1 = UUID.randomUUID().toString().replace("-","").substring(0,8);
		reportContent.append("<table>");
		reportContent.append("<tr >");
			reportContent.append("<td>"
									+ "	<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAIAAABv85FHAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAkSURBVBhXY/iPBBgYULlQGgyoJYcPQNWAAToXSoMBVeT+/wcAJ/6/QbG4DZMAAAAASUVORK5CYII=\"  onclick=\"expander('"+divID+"','"+imgID+"')\" id=\"" + imgID +"\" >" + 
								"</td>" + 
									"<td>" + getSuiteName() +"</td>" +
									"<td>" + hasPassed() +"</td>" + 
							"</tr>"	+
							"</table>");
			reportContent.append("<div id = '" +divID + "' style='display:none;'>");
			int counter = 0;
		for (GuiTestReportSingleTest ts : tests){
			
			reportContent.append("				<table width=\"100%>\"");
						reportContent.append("<tr  colspan=3 " + (ts.hasPassed()?"style=\"background-color:lightgreen;\"":"style=\"background-color:red;\"") + ">");
							reportContent.append("<td width=\"1%\">"
												+ "	<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAIAAABv85FHAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAkSURBVBhXY/iPBBgYULlQGgyoJYcPQNWAAToXSoMBVeT+/wcAJ/6/QbG4DZMAAAAASUVORK5CYII=\"  "
													+"onclick=\"expander('"+divID1+"','"+imgID1+"')\" id=\"" + imgID1 +"\" >"+
												"</td>");
			reportContent.append("				<td width=\"2%\">" + counter +"</td>");
			reportContent.append("				<td width=\"20%\">" + ts.getTestName() +"</td>");
			reportContent.append("				<td width=\"78%\">" + ts.getTestDescription() +"</td>");	
			reportContent.append("			</tr>"
									  +"</table>");
		
			
			reportContent.append("		<div id = '" + divID1 + "' style='display:none;'><!-- dafuq-->");
			reportContent.append("			<table width=100% class='table'><tr><td>Step Number</td><td>Step Action</td><td>Step Result</td></tr>");
			reportContent.append(ts.getReportContent());
			reportContent.append("			</table>"
										+"</div>");
			divID = UUID.randomUUID().toString().replace("-","").substring(0,8);
			divID1 = UUID.randomUUID().toString().replace("-","").substring(0,8);
			counter++;
			
		}
			reportContent.append("</div>");
		return reportContent.toString();
	}
	
	public DateTime getEndTime(){
		return this.endTime;
	}
	public void setEndTime(DateTime endTime){
		this.endTime = endTime;
	}
}
