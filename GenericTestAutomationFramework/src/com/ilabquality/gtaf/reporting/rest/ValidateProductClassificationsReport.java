package com.ilabquality.gtaf.reporting.rest;

import com.ilabquality.gtaf.reporting.report.Report;

public class ValidateProductClassificationsReport extends Report {
	//private ArrayList<ProductClassificationResults> _reportContent = new ArrayList<ProductClassificationResults>();
	@Override
	public void generateAndShow() {
//		
//		_body.setLength(0);
//		_body.append(prepareHeaders("ProductClassifications Test Result"));
//		
//		try {
//			FileWriter fw = new FileWriter(new File(get_htmlFileName()));
//		//int totalTests = _reportContent.size();
//			int totalPassed = 0;
//			int totalFailed = 0;
////			for ( ProductClassificationResults record : _reportContent){
////				if ( record.hasPassed() ){
////					totalPassed++;
////				}else{
////					totalFailed++;
////				}
//			}
//			_body.append("<body style='margin:0 auto;border: solid 1px gray;font-family: Arial;font-size:12px;margin-top:10px;'>");
//			_body.append(""
//						+ "	<table style='width:100%;'>"
//						+ "	<tr>"
//							+ "<td>"
//							+ "		<h1 style='text-align:center;'>Service : Product Classifications </h1></td>"
//						+ "</tr>"  
//						+ "	<tr>"
//						+ "		<td><h1 style='text-align:center'>Execution Date:  "+new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(new Date())+"</h1></td>"
//					+ "	</tr>"
//					+ "	<tr>"
//						+ "<td>"
//					+ "			<h1 style='text-align:center;'>"+totalTests+" tests executed in "+duration+" seconds</h1>"
//					+ "		</td>"
//					+ "</tr>"  
//					+"  <tr>"
//					+ "		<td class='header'> Total Tests : " + totalTests + " Passed : " + totalPassed + " Failed : " + totalFailed + "</td>"
//					+ "</tr>"
//					+ " </table>");
//			_body.append( "<table style='width:100%;'>" );
//			_body.append("		<tr>"
//					+ "					<td width='1%'  style=\"background-color:white;\"></td>" +
//								"		<td class='header style=\"width:16.66%;\"'>Field</td><td class='header' style=\"width:16.66%;\">Expected Result</td><td class='header' style=\"width:16.66%;\">Actual Result</td>" +
//								"		<td class='header' style=\"width:16.66%;\">Field</td><td class='header' style=\"width:16.66%;\">Expected Result</td><td class='header' style=\"width:16.66%;\">Actual Result</td>" +
//								"</tr>" );
//			String styleTag = "tdStyle";
//			String rowStyle = "rowData1";
//			String trStyle ="background-color:lightgreen;";
//			for (ProductClassificationResults pc : _reportContent ){
//				styleTag = "tdStyle";
//				if( !pc.hasPassed()){
//					trStyle ="background-color: rgba(216, 7, 27, 0.3);";
//				}else{
//					trStyle ="background-color:lightgreen;";
//				}
//					
//				// Row 1
//				_body.append("<tr class = '"+rowStyle+"' style = \"" + trStyle + "\">");
//				_body.append("		<td width='1%' style=\"background-color:white;\"></td>"
//								+ "	<td class='tdStyle'>ID</td>");
//				if ( ! pc.getExpectedId().equalsIgnoreCase(pc.getActualId())) 
//					styleTag = "tdErrorStyle";				
//				_body.append("	<td class = '"+ styleTag +"'>" + pc.getExpectedId()+ "</td>");
//				_body.append("	<td class = '"+ styleTag +"'>" + pc.getActualId()+ "</td>");
//			
//				_body.append("	<td class='tdStyle'>Description</td>");
//				if ( ! pc.getExpectedDescription().equalsIgnoreCase(pc.getActualDescription()))
//					styleTag = "tdErrorStyle";				
//				_body.append("	<td class = '"+ styleTag +"'>" + pc.getExpectedDescription()+ "</td>\n");
//				_body.append("	<td class = '"+ styleTag +"'>" + pc.getActualDescription() + "</td>");
//				_body.append("</tr>");
//				
//				if (rowStyle.equalsIgnoreCase("rowdata1")){
//					rowStyle = "rowData2";
//				}else{
//					rowStyle = "rowData1";
//				}
//			}
//			_body.append("</table></body></html>");
//			fw.write(_body.toString());
//			fw.close();
//			java.awt.Desktop.getDesktop().browse(java.net.URI.create(get_htmlFileName()));
//		}
//		catch(Exception e){
//			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
//		}
	}

//	public ArrayList<ProductClassificationResults> get_reportContent() {
//		return _reportContent;
//	}
//	
//	public void set_reportContent(ArrayList<ProductClassificationResults> _reportContent) {
//		this._reportContent = _reportContent;
//	}
	
	


	@Override
	public StringBuilder getMultiReportContent() {
		StringBuilder returnBuffer = new StringBuilder();
//		returnBuffer.append( "<table style='width:100%;'>" );
//		returnBuffer.append("		<tr>"
//				+ "					<td width='1%'  style=\"background-color:white;\"></td>" +
//							"		<td class='header style=\"width:16.66%;\"'>Field</td><td class='header' style=\"width:16.66%;\">Expected Result</td><td class='header' style=\"width:16.66%;\">Actual Result</td>" +
//							"		<td class='header' style=\"width:16.66%;\">Field</td><td class='header' style=\"width:16.66%;\">Expected Result</td><td class='header' style=\"width:16.66%;\">Actual Result</td>" +
//							"</tr>" );
//		String styleTag = "tdStyle";
//		String rowStyle = "rowData1";
//		String trStyle ="background-color:lightgreen;";
//		for (ProductClassificationResults pc : _reportContent ){
//			styleTag = "tdStyle";
//			if( !pc.hasPassed()){
//				trStyle ="background-color: rgba(216, 7, 27, 0.3);";
//			}else{
//				trStyle ="background-color:lightgreen;";
//			}
//				
//			// Row 1
//			returnBuffer.append("<tr class = '"+rowStyle+"' style = \"" + trStyle + "\">");
//			returnBuffer.append("		<td width='1%' style=\"background-color:white;\"></td>"
//							+ "	<td class='tdStyle'>ID</td>");
//			if ( ! pc.getExpectedId().equalsIgnoreCase(pc.getActualId())) 
//				styleTag = "tdErrorStyle";				
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + pc.getExpectedId()+ "</td>");
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + pc.getActualId()+ "</td>");
//		
//			returnBuffer.append("	<td class='tdStyle'>Description</td>");
//			if ( ! pc.getExpectedDescription().equalsIgnoreCase(pc.getActualDescription()))
//				styleTag = "tdErrorStyle";				
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + pc.getExpectedDescription()+ "</td>\n");
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + pc.getActualDescription() + "</td>");
//			returnBuffer.append("</tr>");
//			
//			if (rowStyle.equalsIgnoreCase("rowdata1")){
//				rowStyle = "rowData2";
//			}else{
//				rowStyle = "rowData1";
//			}
//		}
//		returnBuffer.append("</table>");
		return returnBuffer;
//	}
	}
}
