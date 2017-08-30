package com.ilabquality.gtaf.reporting.rest;

public class ValidateUsersReport {
//	private ArrayList<UsersTestResult> _reportContent;
//	private double duration;
//
//	@Override
//	public void generateAndShow() {
//		_body.setLength(0);
//		_body.append(prepareHeaders("IdentityXUsers Test Result"));
//		try {
//			FileWriter fw = new FileWriter(new File(get_htmlFileName()));
//			int totalTests = _reportContent.size();
//			int totalPassed = 0;
//			int totalFailed = 0;
//			for ( UsersTestResult record : _reportContent){
//				if ( record.hasPassed() ){
//					totalPassed++;
//				}else{
//					totalFailed++;
//				}
//			}
//			_body.append("<body style='margin:0 auto;border: solid 1px gray;font-family: Arial;font-size:12px;margin-top:10px;'>");
//			_body.append(""
//						+ "	<table style='width:100%;'>"
//						+ "	<tr>"
//							+ "<td>"
//							+ "		<h1 style='text-align:center;'>Service : IdentityXUsers </h1></td>"
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
//			
//			String serviceID = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
//			String imgID = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
//			String trStyle = (totalFailed==0)?"background-color:lightgreen;":"background-color: rgba(216, 7, 27, 0.3)";
//			_body.append("" +
//					 			"<table style='width:100%;' ><!-- Define Main Service DIV-->\n" +				
//									"<tr style=\""+trStyle+"\">\n<td width = '1%'><img id = \"" + imgID +"\" src='minus.png' onclick=\"expander('"+serviceID+"','"+imgID+"')\" ></td>\n<td class='header' style='align:left;'>IdentityXUsers - Validate the users returned from the WS match the detail in the database</td>\n</tr>\n" +
//								"</table>\n<br>");
//			_body.append("<div id='"+serviceID+"'> <!-- Start Service DIV -->");
//			
//			
//			
//			//String styleTag = "tdStyle";
//		
//					
//			for ( UsersTestResult utr : _reportContent){
//				String styleTag = "tdStyle";
//				String rowStyle = "rowData1";
//				//User ID DIV
//				trStyle = (utr.hasPassed())?"background-color:lightgreen;":"background-color: rgba(216, 7, 27, 0.3)";
//				String userIDDivID = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
//				String img2ID = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
//				_body.append("" +
//			 			"<table style='width:100%;' >" +				
//							"<tr style=\""+trStyle+"\">"
//								+ "<td width = '1%' style=\"background-color:white;\"><img id=\""+img2ID+"\" src='"+ ( utr.hasPassed()?"plus.png":"minus.png" )+"' onclick=\"expander('"+userIDDivID+"','"+img2ID+"')\" ></td>"
//								+ "<td class='header'>Test User : " + utr.getExpectedId() + "</td>"
//						+ "  </tr>"
//						+ "</table>"+
//							"<div id='" + userIDDivID +"' style=\"display:"+( utr.hasPassed()?"none":"block")+";\"> <!-- Create detail DIV -->");
//				
//			//Table Headers
//				_body.append( "<table style='width:100%;'>" );
//				_body.append("		<tr>"
//					+ "					<td width='1%'  style=\"background-color:white;\"></td>" +
//					 			"		<td class='header style=\"width:16.66%;\"'>Field</td><td class='header' style=\"width:16.66%;\">Expected Result</td><td class='header' style=\"width:16.66%;\">Actual Result</td>" +
//					 			"		<td class='header' style=\"width:16.66%;\">Field</td><td class='header' style=\"width:16.66%;\">Expected Result</td><td class='header' style=\"width:16.66%;\">Actual Result</td>" +
//				 					"</tr>" );
//				
//				//Start Data Rows
//				// Row 1
//				_body.append("<tr class = '"+rowStyle+"'>");
//				_body.append("		<td width='1%' style=\"background-color:white;\"></td>"
//								+ "	<td class='tdStyle'>ID</td>");
//				
//				if ( ! utr.getExpectedId().equalsIgnoreCase(utr.getActualId())) 
//					styleTag = "tdErrorStyle";				
//				_body.append("	<td class = '"+ styleTag +"'>" + utr.getExpectedId()+ "</td>");
//				_body.append("	<td class = '"+ styleTag +"'>" + utr.getActualId()+ "</td>");
//			
//				_body.append("	<td class='tdStyle'>First Name</td>");
//				if ( ! utr.getExpectedFirstName().equalsIgnoreCase(utr.getActualFirstName()))
//					styleTag = "tdErrorStyle";				
//				_body.append("	<td class = '"+ styleTag +"'>" + utr.getExpectedFirstName()+ "</td>\n");
//				_body.append("	<td class = '"+ styleTag +"'>" + utr.getActualFirstName() + "</td>");
//				_body.append("</tr>");
//				//Row 2
//				styleTag = "tdStyle";
//				rowStyle = "rowData2";
//				_body.append("<tr class = '"+rowStyle+"'>");
//				_body.append("		<td width='1%' style=\"background-color:white;\"></td>"
//								+ "	<td class='tdStyle'>Last Name</td>");
//				
//				if ( ! utr.getExpectedLastName().equalsIgnoreCase(utr.getActualLastName())) 
//					styleTag = "tdErrorStyle";				
//				_body.append("	<td class = '"+ styleTag +"'>" + utr.getExpectedLastName()+ "</td>");
//				_body.append("	<td class = '"+ styleTag +"'>" + utr.getActualLastName()+ "</td>");
//			
//				_body.append("	<td class='tdStyle'>User URL</td>");
//				if ( ! utr.getExpectedUrl().equalsIgnoreCase(utr.getActualUrl()))
//					styleTag = "tdErrorStyle";				
//				_body.append("	<td class = '"+ styleTag +"'>" + utr.getExpectedUrl()+ "</td>\n");
//				_body.append("	<td class = '"+ styleTag +"'>" + utr.getActualUrl() + "</td>");
//				_body.append("</tr>");
//				//Row 3
//				styleTag = "tdStyle";
//				rowStyle = "rowData1";
//				_body.append("<tr class = '"+rowStyle+"'>");
//				_body.append("		<td width='1%' style=\"background-color:white;\"></td>"
//								+ "	<td class='tdStyle'>Email</td>");
//				
//				if ( ! utr.getExpectedEmail().equalsIgnoreCase(utr.getActualEmail())) 
//					styleTag = "tdErrorStyle";				
//				_body.append("	<td class = '"+ styleTag +"'>" + utr.getExpectedEmail()+ "</td>");
//				_body.append("	<td class = '"+ styleTag +"'>" + utr.getActualEmail()+ "</td>");
//			
//				_body.append("	<td class='tdStyle'>Picture URL</td>");
//				if ( ! utr.getExpectedPictureUrl().equalsIgnoreCase(utr.getActualPictureUrl()))
//					styleTag = "tdErrorStyle";				
//				_body.append("	<td class = '"+ styleTag +"'>" + utr.getExpectedPictureUrl()+ "</td>\n");
//				_body.append("	<td class = '"+ styleTag +"'>" + utr.getActualPictureUrl() + "</td>");
//				_body.append("</tr>");
//				
//				//Row 5
//				styleTag = "tdStyle";
//				rowStyle = "rowData2";
//				_body.append("<tr class = '"+rowStyle+"'>");
//				_body.append("		<td width='1%' style=\"background-color:white;\"></td>"
//								+ "	<td class='tdStyle'>User Name</td>");
//				
//				if ( ! utr.getExpectedUserName().equalsIgnoreCase(utr.getActualUserName())) 
//					styleTag = "tdErrorStyle";				
//				_body.append("	<td class = '"+ styleTag +"'>" + utr.getExpectedUserName()+ "</td>");
//				_body.append("	<td class = '"+ styleTag +"'>" + utr.getActualUserName()+ "</td>");
//			
//				_body.append("	<td class='tdStyle'>Password</td>");
//				if ( ! utr.getExpectedPassword().equalsIgnoreCase(utr.getActualPassword()))
//					styleTag = "tdErrorStyle";				
//				_body.append("	<td class = '"+ styleTag +"'>" + utr.getExpectedPassword()+ "</td>\n");
//				_body.append("	<td class = '"+ styleTag +"'>" + utr.getActualPassword() + "</td>");
//				_body.append("</tr>");
//				
//				_body.append("</table> <!-- Close off the data table -->"
//						+ "</DIV> <!-- Close off the DIV -->");
//			}
//		
//			
//	
//			fw.write(_body.toString());
//			fw.close();
//			java.awt.Desktop.getDesktop().browse(java.net.URI.create(get_htmlFileName()));
//		}catch(Exception e){
//			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
//		}
//	}
//
//	public ArrayList<UsersTestResult> get_reportContent() {
//		return _reportContent;
//	}
//
//	public void set_reportContent(ArrayList<UsersTestResult> _reportContent) {
//		this._reportContent = _reportContent;
//	}
//
//	public void setDuration(double duration) {
//		this.duration=duration;
//	}
//
//	@Override
//	public StringBuilder getMultiReportContent() {
//		StringBuilder returnBuffer = new StringBuilder();
//		int totalTests = _reportContent.size();
//		int totalPassed = 0;
//		int totalFailed = 0;
//		for ( UsersTestResult record : _reportContent){
//			if ( record.hasPassed() ){
//				totalPassed++;
//			}else{
//				totalFailed++;
//			}
//		}
//		String serviceID = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
//		String imgID = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
//		String trStyle = (totalFailed==0)?"background-color:lightgreen;":"background-color: rgba(216, 7, 27, 0.3)";
//		returnBuffer.append("" +
//				 			"<table style='width:100%;' ><!-- Define Main Service DIV-->\n" +				
//								"<tr style=\""+trStyle+"\">\n<td width = '1%'><img id = \"" + imgID +"\" src='minus.png' onclick=\"expander('"+serviceID+"','"+imgID+"')\" ></td>\n<td class='header' style='align:left;'>IdentityXUsers - Validate the users returned from the WS match the detail in the database</td>\n</tr>\n" +
//							"</table>\n<br>");
//		returnBuffer.append("<div id='"+serviceID+"'> <!-- Start Service DIV -->");
//		for ( UsersTestResult utr : _reportContent){
//			String styleTag = "tdStyle";
//			String rowStyle = "rowData1";
//			//User ID DIV
//			trStyle = (utr.hasPassed())?"background-color:lightgreen;":"background-color: rgba(216, 7, 27, 0.3)";
//			String userIDDivID = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
//			String img2ID = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
//			returnBuffer.append("" +
//		 			"<table style='width:100%;' >" +				
//						"<tr style=\""+trStyle+"\">"
//							+ "<td width = '1%' style=\"background-color:white;\"><img id=\""+img2ID+"\" src='"+ ( utr.hasPassed()?"plus.png":"minus.png" )+"' onclick=\"expander('"+userIDDivID+"','"+img2ID+"')\" ></td>"
//							+ "<td class='header'>Test User : " + utr.getExpectedId() + "</td>"
//					+ "  </tr>"
//					+ "</table>"+
//						"<div id='" + userIDDivID +"' style=\"display:"+( utr.hasPassed()?"none":"block")+";\"> <!-- Create detail DIV -->");
//			
//		//Table Headers
//			returnBuffer.append( "<table style='width:100%;'>" );
//			returnBuffer.append("		<tr>"
//				+ "					<td width='1%'  style=\"background-color:white;\"></td>" +
//				 			"		<td class='header style=\"width:16.66%;\"'>Field</td><td class='header' style=\"width:16.66%;\">Expected Result</td><td class='header' style=\"width:16.66%;\">Actual Result</td>" +
//				 			"		<td class='header' style=\"width:16.66%;\">Field</td><td class='header' style=\"width:16.66%;\">Expected Result</td><td class='header' style=\"width:16.66%;\">Actual Result</td>" +
//			 					"</tr>" );
//			
//			//Start Data Rows
//			// Row 1
//			returnBuffer.append("<tr class = '"+rowStyle+"'>");
//			returnBuffer.append("		<td width='1%' style=\"background-color:white;\"></td>"
//							+ "	<td class='tdStyle'>ID</td>");
//			
//			if ( ! utr.getExpectedId().equalsIgnoreCase(utr.getActualId())) 
//				styleTag = "tdErrorStyle";				
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + utr.getExpectedId()+ "</td>");
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + utr.getActualId()+ "</td>");
//		
//			returnBuffer.append("	<td class='tdStyle'>First Name</td>");
//			if ( ! utr.getExpectedFirstName().equalsIgnoreCase(utr.getActualFirstName()))
//				styleTag = "tdErrorStyle";				
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + utr.getExpectedFirstName()+ "</td>\n");
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + utr.getActualFirstName() + "</td>");
//			returnBuffer.append("</tr>");
//			//Row 2
//			styleTag = "tdStyle";
//			rowStyle = "rowData2";
//			returnBuffer.append("<tr class = '"+rowStyle+"'>");
//			returnBuffer.append("		<td width='1%' style=\"background-color:white;\"></td>"
//							+ "	<td class='tdStyle'>Last Name</td>");
//			
//			if ( ! utr.getExpectedLastName().equalsIgnoreCase(utr.getActualLastName())) 
//				styleTag = "tdErrorStyle";				
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + utr.getExpectedLastName()+ "</td>");
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + utr.getActualLastName()+ "</td>");
//		
//			returnBuffer.append("	<td class='tdStyle'>User URL</td>");
//			if ( ! utr.getExpectedUrl().equalsIgnoreCase(utr.getActualUrl()))
//				styleTag = "tdErrorStyle";				
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + utr.getExpectedUrl()+ "</td>\n");
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + utr.getActualUrl() + "</td>");
//			returnBuffer.append("</tr>");
//			//Row 3
//			styleTag = "tdStyle";
//			rowStyle = "rowData1";
//			returnBuffer.append("<tr class = '"+rowStyle+"'>");
//			returnBuffer.append("		<td width='1%' style=\"background-color:white;\"></td>"
//							+ "	<td class='tdStyle'>Email</td>");
//			
//			if ( ! utr.getExpectedEmail().equalsIgnoreCase(utr.getActualEmail())) 
//				styleTag = "tdErrorStyle";				
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + utr.getExpectedEmail()+ "</td>");
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + utr.getActualEmail()+ "</td>");
//		
//			returnBuffer.append("	<td class='tdStyle'>Picture URL</td>");
//			if ( ! utr.getExpectedPictureUrl().equalsIgnoreCase(utr.getActualPictureUrl()))
//				styleTag = "tdErrorStyle";				
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + utr.getExpectedPictureUrl()+ "</td>\n");
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + utr.getActualPictureUrl() + "</td>");
//			returnBuffer.append("</tr>");
//			
//			//Row 5
//			styleTag = "tdStyle";
//			rowStyle = "rowData2";
//			returnBuffer.append("<tr class = '"+rowStyle+"'>");
//			returnBuffer.append("		<td width='1%' style=\"background-color:white;\"></td>"
//							+ "	<td class='tdStyle'>User Name</td>");
//			
//			if ( ! utr.getExpectedUserName().equalsIgnoreCase(utr.getActualUserName())) 
//				styleTag = "tdErrorStyle";				
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + utr.getExpectedUserName()+ "</td>");
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + utr.getActualUserName()+ "</td>");
//		
//			returnBuffer.append("	<td class='tdStyle'>Password</td>");
//			if ( ! utr.getExpectedPassword().equalsIgnoreCase(utr.getActualPassword()))
//				styleTag = "tdErrorStyle";				
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + utr.getExpectedPassword()+ "</td>\n");
//			returnBuffer.append("	<td class = '"+ styleTag +"'>" + utr.getActualPassword() + "</td>");
//			returnBuffer.append("</tr>");
//			
//			returnBuffer.append("</table> <!-- Close off the data table -->"
//					+ "</DIV><!-- Close off the DIV -->");
//		}
//		returnBuffer.append("</DIV>");
//		return returnBuffer;
//	}
}
