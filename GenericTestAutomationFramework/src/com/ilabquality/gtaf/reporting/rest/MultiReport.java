package com.ilabquality.gtaf.reporting.rest;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.reporting.report.Report;

public class MultiReport extends Report {

	private ArrayList<Report> _reports = new ArrayList<Report>();
	@Override
	public void generateAndShow() {
 		_body.setLength(0);
		_body.append(prepareHeaders("Pangea Multi Service Report"));
		try{
			FileWriter fw = new FileWriter(new File(get_htmlFileName()));
			
			_body.append("<body style='margin:0 auto;border: solid 1px gray;font-family: Arial;font-size:12px;margin-top:10px;'>");
			_body.append(""
						+ "	<table style='width:100%;'>"
						+ "	<tr>"
							+ "<td>"
							+ "		<h2 style='text-align:center;'>Pangea Multi Service Report </h2></td>"
						+ "</tr>"  
						+ "	<tr>"
						+ "		<td><h2 style='text-align:center'>Execution Date:  "+new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(new Date())+"</h2></td>"
					+ "	</tr>"
//					+ "	<tr>"
//						+ "<td>"
//					+ "			<h1 style='text-align:center;'>"+_reports.size()+" contained reports</h1>"
//					+ "		</td>"
//					+ "</tr>"  
//					+"  <tr>"
//					+ "		<td class='header'> Total Tests : " + totalTests + " Passed : " + totalPassed + " Failed : " + totalFailed + "</td>"
//					+ "</tr>"
					+ " </table><BR><BR>");
	
			for ( Report r : _reports){
				_body.append("<table style='width:100%;'><tr><td class='h2'>" + r.getMultiReportName()+"</td></tr></table>");
				_body.append(r.getMultiReportContent().toString());
				_body.append("<BR>");
			}
			
			fw.write(_body.toString());
			fw.close();
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(get_htmlFileName()));
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}

	}

	@Override
	public StringBuilder getMultiReportContent() {
		
		return null;
	}
	
	public void addReport(Report r){
		_reports.add(r);
	}

}
