package com.ilabquality.gtaf.reporting.testpack;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;

import org.jfree.data.xy.XYSeries;
import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.gtafgui.reportsViewer.TestReport;
import com.ilabquality.gtaf.reporting.report.Report;
import com.ilabquality.gtaf.testsuite.Test;
import com.ilabquality.gtaf.testsuite.TestSuite;
import com.ilabquality.gtaf.testsuite.TestValidation;

public class TestPackReport extends Report {

	private ArrayList<TestSuite> executedSuites;
	private String startTime;
	private String endTime;
	private double duration;
	private String testPackName;

	public TestPackReport(ArrayList<TestSuite> executedSuites, String startTime, String endTime, double duration, String testPackName){
		this.executedSuites = executedSuites;
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = duration;
		this.testPackName = testPackName;
	}
	
	@Override
	public void generateAndShow() {
		_body.setLength(0);
		_body.append(prepareHeaders("Test Pack Execution Report"));
		TestReport tr = new TestReport();
		
		Date d = new Date();
		String reportDate = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(d) ;
		tr.setReportDate(new java.sql.Date(d.getTime()));
		tr.setReportName("Test Pack Execution Report " + reportDate);
		StringBuilder sb = new StringBuilder();
		try {
			FileWriter fw = new FileWriter(new File(get_htmlFileName()));
			int totalTests = 0;
			int totalValidations = 0;
			int totalPassed = 0;
			int totalFailed = 0;
			int counter = 0;
			int debug=0;
			Hashtable<Integer,Double> chartValues = new Hashtable<>();
			chartValues.put(0, 0.0);
			for ( TestSuite suite : executedSuites){
				counter = 0;
			     XYSeries series = new XYSeries(suite.getTestSuiteName());
			     series.add(0,0.0);
				for  (Test test : suite.getSuiteData()){
					series.add(counter,  test.getDuration());
					if ( test.hasPassed())
					{
						totalPassed++;
					}else{
						totalFailed++;
					}
					totalValidations += test.getValidations().size();
					totalTests++;
					counter++;
				}
				addChartData(series);
			}
			
			_body.append("<body style=\"margin:0 auto;border: solid 1px gray;font-family: Arial;font-size:12px;margin-top:10px;\">");
			_body.append("<div id=\"ReportHeader\">"
					+ "	<table style=\"width:100%;\">"
						+ "	<tr>"
						+ "<td style=\"width:33%;\">"
						+ "		<img src = \"pangea.jpg\" align=\"left\"></td>"
						+ "<td>"
						+ "		<h2 style=\"text-align:center;\">Test Pack Execution Report - " + testPackName+"</h2></td>"
						+"<td style=\"width:33%;\"><img src = \"AbsaBarclays.png\" align=\"right\"></td>"
						+ "</tr>"  
						+ "	<tr>"
						+ "		<td></td><td><h2 style=\"text-align:center\">Checked on : " + reportDate + "</h2></td>"
					+ "	</tr>"
					+"  <tr>"
					+ "		<td></td><td style=\"text-align:center\"> Execution started at : " + startTime + " , finished at " + endTime + " with an execution duration of " + duration + " seconds</td>"
					+ "</tr>"
					+"  <tr>"
					+ "		<td></td><td style=\"text-align:center\"> Total Tests : " + totalTests + " with " + totalValidations +" validations.\nPassed : " + totalPassed + " Failed : " + totalFailed + "</td>"
							+ "<td align=\"right\"><a href=\"#\" onclick=\"expandAll()\">Expand All</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"collapseAll()\">Collapse All </a></td>\n"
					+ "</tr>"
					+ " </table>"
					+ "</div>" );
			sb.append(_body.toString());
			fw.write(_body.toString());
			fw.flush();
//			_body.append("<div>"
//					+ "	<table style=\"width:100%;\" >" +				
//							"<tr>"+
//							"<td class=\"header\">Row ID</td>" + 
//							 	"<td class=\"header\">Service Name</td>" + 
//								"<td class=\"header\" >Status</td>" + 
//							 "</tr>");
			
			for ( TestSuite suite : executedSuites){
				//Check if we have passed
				boolean hasPassed = true;
				if (suite.mustStopSuiteExecution()){
					hasPassed = false;//Suite execution ended abnormally.
				}else{
					for  (Test test : suite.getSuiteData()){
						for ( TestValidation tv : test.getValidations()){
							if ( !tv.hasPassed())
							{
								hasPassed = false;
							}
						}
					}
				}
			
				String trStyle = (hasPassed)?"background-color:lightgreen;":"background-color: rgba(216, 7, 27, 0.3)";
				String divID = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
				String imgID = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
				String hdr = "<table style=\"width:100%;\" ><!-- Define Main Service DIV-->\n" +				
						"<tr style=\""+trStyle+"\">\n"
						+ "<td width = \"1%\" style=\"background-color:white\"><img id = \"" + imgID +"\" src=\" " + ( hasPassed?"plus.png":"minus.png") +"\" onclick=\"expander('"+divID+"','"+imgID+"')\" ></td>\n"
						+ "<td class=\"header\" style=\"align:left;\">"+suite.getTestSuiteName()+" - "+suite.getTestSuiteDescription()+"</td>"
								
					+  "</tr>\n" +
					"</table>\n<div id=\""+divID+"\" "+ ( hasPassed?"style=\"display:none;\"":"style=\"display:block;\"") +" > <!!-- Start Tests DIV -->";
				fw.write(hdr);
				sb.append(hdr);
				//fw.write(sb.toString());
				
				for ( Test test : suite.getSuiteData()){
					String s = test.getReportData();
					fw.write(s);
					sb.append(s);
					fw.flush();
				}
				//sb.append("</div>");//Close off TestSuiteDIV
				fw.write("</div>");
				sb.append("</div>");
				//return sb.toString();
				fw.flush();
			}
			try{
				fw.write("<img src = '" + writeChart(chartValues)+"'/>");
			}catch(Exception e){
				e.printStackTrace();
			}
			fw.write("</body></html>");
			sb.append("</body></html>");
			//fw.write(_body.toString());
			fw.close();
			if ( !System.getProperty("os.name").contains("Windows")){
				java.awt.Desktop.getDesktop().open(new File(get_htmlFileName()));
			}else{
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(get_htmlFileName()));
			}
			tr.setReportFQFileName(get_htmlFileName());
			sb.setLength(0);
			Thread t = new Thread(tr);
			t.start();
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}

	}

	@Override
	public StringBuilder getMultiReportContent() {
		
		return new StringBuilder().append("Not implemented");
	}

}
