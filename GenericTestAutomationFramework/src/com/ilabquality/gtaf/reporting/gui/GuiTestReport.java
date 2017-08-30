package com.ilabquality.gtaf.reporting.gui;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.Seconds;

import com.ilabquality.gtaf.gtafgui.reportsViewer.TestReport;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSuite;
import com.ilabquality.gtaf.reporting.report.Report;

public class GuiTestReport extends Report {

	private GuiTestReportSuite test;
	public GuiTestReport(GuiTestReportSuite test) {
		this.test = test;
	}
	@Override
	public void generateAndShow() {
		_body.setLength(0);
		_body.append(prepareHeaders("GUI Test Execution Report"));
		TestReport tr = new TestReport();
		
		Date d = new Date();
		String reportDate = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(d) ;
		tr.setReportDate(new java.sql.Date(d.getTime()));
		tr.setReportName("GUI Test Execution Report " + reportDate);
		try {
			FileWriter fw = new FileWriter(new File( get_htmlFileName()));
			_body.append("<body style=\"margin:0 auto;border: solid 1px gray;font-family: Arial;font-size:12px;margin-top:10px;\">");
			_body.append("<div id=\"ReportHeader\">"
							+ "	<table style=\"width:100%;\">"
								+ "	<tr>"
									+ "<td style=\"width:33%;\">"
										+ "		<img src = \"pangea.jpg\" align=\"left\"></td>"
										+ "<td>"
										+ "		<h2 style=\"text-align:center;\">GUI Test Execution Report </h2></td>"
										+"<td style=\"width:33%;\"><img src = \"AbsaBarclays.png\" align=\"right\"></td>"
								+ "</tr>"  
						+ "	<tr>"
						+ "		<td></td><td><h2 style=\"text-align:center\">Checked on : " + reportDate + "</h2></td>"
					+ "	</tr>"
					+"  <tr>"
					+ "		<td></td><td style=\"text-align:center\"> Execution started at : " + test.getStartTime() + " , finished at " + test.getEndTime() + " with an execution duration of " + Seconds.secondsBetween( test.getStartTime(),test.getEndTime()).getSeconds() + " seconds</td>"
					+ "</tr>"
					+"  <tr>"
					+ "		<td></td><td style=\"text-align:center\"> Total Tests : " + test.getTests().size() +  "</td>"
							+ "<td align=\"right\"><a href=\"#\" onclick=\"expandAll()\">Expand All</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"collapseAll()\">Collapse All </a></td>\n"
					+ "</tr>"
					+ " </table>"
					+ "</div>" );
			
			_body.append(test.getReportContent());
			_body.append("</body></html>");
			fw.write(_body.toString());
			fw.close();
			if ( !System.getProperty("os.name").contains("Windows")){
				java.awt.Desktop.getDesktop().open(new File(get_htmlFileName()));
			}else{
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(get_htmlFileName()));
			}
			tr.setReportFQFileName(get_htmlFileName());
			Thread t = new Thread(tr);
			t.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public StringBuilder getMultiReportContent() {
		
		return null;
	}

}
