package com.ilabquality.gtaf.reporting.rest;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.reporting.report.Report;
import com.ilabquality.gtaf.result.callService.CallServiceResult;

public class CallServiceReport extends Report{
	private ArrayList<CallServiceResult> _reportContent;

	@Override
	public void generateAndShow() {
		_body.setLength(0);
		_body.append(prepareHeaders("Service Status Report"));
		try {
			FileWriter fw = new FileWriter(new File(get_htmlFileName()));
			int totalTests = _reportContent.size();
			int totalPassed = 0;
			int totalFailed = 0;
			for ( CallServiceResult record : _reportContent){
				if ( record.isAlive() ){
					totalPassed++;
				}else{
					totalFailed++;
				}
			}
			
			
			_body.append("<body style='margin:0 auto;border: solid 1px gray;font-family: Arial;font-size:12px;margin-top:10px;'>");
			_body.append("<div>"
					+ "	<table style='width:100%;'>"
						+ "	<tr>"
							+ "<td>"
							+ "		<h1 style='text-align:center;'>Pangea Service Status</h1></td>"
						+ "</tr>"  
						+ "	<tr>"
						+ "		<td><h2 style='text-align:center'>Checked on : " +new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(new Date())  + "</h2></td>"
					+ "	</tr>"
					+"  <tr>"
					+ "		<td class='header'> Total Services Checked : " + totalTests + " Alive : " + totalPassed + " Dead : " + totalFailed + "</td>"
					+ "</tr>"
					+ " </table>"
					+ "</div>" );
			_body.append("<div>"
					+ "	<table style='width:100%;' >" +				
							"<tr>"+
							"<td class='header'>Row ID</td>" + 
							 	"<td class='header'>Service Name</td>" + 
								"<td class='header' >Status</td>" + 
							 "</tr>");
			int counter = 1;
			for (CallServiceResult sr : _reportContent ) {
				String styleTag = "tdStyle";
				String trStyle = sr.isAlive() ?"background-color:lightgreen;":"background-color: rgba(216, 7, 27, 0.3)";
				_body.append("<tr style = '"+trStyle+"'>");
									_body.append("<td class = '"+ styleTag +"'>" + counter + "</td>");
									_body.append("<td class = '"+ styleTag +"'>" + sr.getWebServiceName()+ "</td>");
									
									_body.append("<td class = '"+ styleTag +"'>"+ (sr.isAlive()?"Alive":"Dead") +" </td>"
							+ "</tr>");
								counter++;
			}
			_body.append("</table></div>" );
			_body.append("</body></html>");
			fw.write(_body.toString());
			fw.close();
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(get_htmlFileName()));
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		
	}

	public ArrayList<CallServiceResult> get_reportContent() {
		return _reportContent;
	}

	public void set_reportContent(ArrayList<CallServiceResult> _reportContent) {
		this._reportContent = _reportContent;
	}

	@Override
	public StringBuilder getMultiReportContent() {
		StringBuilder returnBuffer = new StringBuilder();
		
		returnBuffer.append("<table style='width:100%;' >" +				
						"<tr>"+
						"<td class='header'>Row ID</td>" + 
						 	"<td class='header'>Service Name</td>" + 
							"<td class='header' >Status</td>" + 
						 "</tr>");
		int counter = 1;
		for (CallServiceResult sr : _reportContent ) {
			String styleTag = "tdStyle";
			String trStyle = sr.isAlive() ?"background-color:lightgreen;":"background-color: rgba(216, 7, 27, 0.3)";
			returnBuffer.append("<tr style = '"+trStyle+"'>");
			returnBuffer.append("<td class = '"+ styleTag +"'>" + counter + "</td>");
			returnBuffer.append("<td class = '"+ styleTag +"'>" + sr.getWebServiceName()+ "</td>");
								
			returnBuffer.append("<td class = '"+ styleTag +"'>"+ (sr.isAlive()?"Alive":"Dead") +" </td>"
						+ "</tr>");
							counter++;
		}
		returnBuffer.append("</table>" );
		return returnBuffer;
	}

}
