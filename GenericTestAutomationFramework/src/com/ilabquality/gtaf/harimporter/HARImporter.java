package com.ilabquality.gtaf.harimporter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilabquality.gtaf.gtafAppl.GTAFApplController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.testsuite.Test;
import com.ilabquality.gtaf.testsuite.TestSuite;
import com.ilabquality.gtaf.testsuite.TestSuiteFactory;
import com.ilabquality.gtaf.testsuite.TestValidation;


public class HARImporter {

	public static void main(String[] args) throws IOException, SQLException {

	}
	
	public static void doImport(GTAFApplController parent, String fileContents,String suiteName, String suiteDesc,boolean mustImportGET, boolean mustImportPOST,
								String executionUser,
								String servicesServerIP,String servicesServerPort,String uiServerIP,String uiServerPort,String workFlowServerIP,String workFlowServerPort,
								String keyCloakServerIP,String keyCloakServerPort) throws IOException, SQLException{
		HARfile har = null;
		int debug=0;
		parent.setTaskDisplayer("Importing HAR file");
		
		parent.appendLog("Starting Import");
		parent.appendLog("Attempting to MAP the HAR...");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		har =  mapper.readValue(fileContents, HARfile.class);
		parent.appendLog("Success, preparing headers...");
		
		StringBuilder content = new StringBuilder();
		content.append("Testname^TestDescription^ExecutionOrderIndex^WebServiceUrl^CallResult^TestSuiteID^Verb^PostData\n");
		parent.appendLog("Success, creating Test Suite");
		String csvFileName = "c:/temp/HarImport_" +  (new SimpleDateFormat("yyyyMMddHHmm").format(new java.util.Date()) +".csv");
		TestSuite ts = new TestSuite();
		//ts.setTestSuiteDescription("Har Import - " +  new SimpleDateFormat("yyyyMMddHHmm").format(new java.util.Date()));
		ts.setTestSuiteDescription(suiteDesc);//"Har Import - Full Capture" +  new SimpleDateFormat("yyyyMMddHHmm").format(new java.util.Date()));
		
		ts.setTestSuiteName(suiteName);
		//Add setup call
		//http://10.110.43.105:9091/setup
		Test dbSvc = new Test();
		dbSvc.setExecutionUserName(executionUser);
		dbSvc.setExecutionPassword(executionUser.equalsIgnoreCase("jack")?"jack":"john");
		dbSvc.setCallResult("");
		dbSvc.setExecutionIndex(0);
		dbSvc.setId(-1);
		dbSvc.setDelay(5);
		dbSvc.setParentSuite(ts);
		dbSvc.setPostData("");
		dbSvc.setTestDescription("Reset the Test ENV Database - Services");
		dbSvc.setTestName("ResetEnv_Svc");
		dbSvc.setVerb("POST");
		dbSvc.setWebServiceURL("http://@{ServicesServerIP}:@{ServicesServerPort}/setup");
		//dbSvc.setWebServiceURL("http://@{ServicesServerIP}:@{ServicesServerPort}/services/setup");
			ts.getSuiteData().add(dbSvc);
		
		Test dbWFlow = new Test();
		dbWFlow.setExecutionUserName(executionUser);
		dbWFlow.setExecutionPassword(executionUser.equalsIgnoreCase("jack")?"jack":"john");
		dbWFlow.setCallResult("");
		dbWFlow.setExecutionIndex(1);
		dbWFlow.setId(-1);
		dbWFlow.setParentSuite(ts);
		dbWFlow.setPostData("");
		dbWFlow.setTestDescription("Reset the Test ENV Database - WorkFlow");
		dbWFlow.setTestName("ResetEnv_WorkFlow");
		dbWFlow.setVerb("POST");
		dbWFlow.setWebServiceURL("http://@{WorkFlowServerIP}:@{WorkFlowServerPort}/setup");
		//dbWFlow.setWebServiceURL("http://@{WorkFlowServerIP}:@{WorkFlowServerPort}/workflow/setup");
		ts.getSuiteData().add(dbWFlow);
		
		Test resetApp = new Test();
		resetApp.setExecutionUserName(executionUser);
		resetApp.setExecutionPassword(executionUser.equalsIgnoreCase("jack")?"jack":"john");
		resetApp.setCallResult("");
		resetApp.setExecutionIndex(2);
		resetApp.setId(-1);
		resetApp.setParentSuite(ts);
		resetApp.setPostData("");
		resetApp.setTestDescription("Restart the Application");
		resetApp.setTestName("ResetEnv_Restart");
		resetApp.setVerb("POST");
		resetApp.setWebServiceURL("http://@{ServicesServerIP}:@{ServicesServerPort}/restart");
		//resetApp.setWebServiceURL("http://@{ServicesServerIP}:@{ServicesServerPort}/services/restart");
		resetApp.setDelay(5);
		TestValidation tv = new TestValidation();
		tv.setAssociatedTest(resetApp);
		tv.setExpectedResult("{\"message\":\"Restarting\"}");
		tv.setHasPassed(false);
		tv.setId(-1);
		resetApp.getValidations().add(tv);
		ts.getSuiteData().add(resetApp);
		
		Test appHealth = new Test();
		appHealth.setExecutionUserName(executionUser);
		appHealth.setExecutionPassword(executionUser.equalsIgnoreCase("jack")?"jack":"john");
		appHealth.setCallResult("");
		appHealth.setExecutionIndex(3);
		appHealth.setId(-1);
		appHealth.setParentSuite(ts);
		appHealth.setPostData("");
		appHealth.setTestDescription("Wait for Application to start.");
		appHealth.setTestName("CheckAppAvailable");
		appHealth.setVerb("POST");
		appHealth.setWebServiceURL("http://@{ServicesServerIP}:@{ServicesServerPort}/health");
		//appHealth.setWebServiceURL("http://@{ServicesServerIP}:@{ServicesServerPort}/services/health");
		ts.getSuiteData().add(appHealth);
		
		// Only add the logon to the suite if we are using a keycloak server. TODO Improve this call to be based on the Provider. 
		if ( Runner.getRuntimeVariables().get("@{AuthenticationProvider}").get(0).getLiveValue().equalsIgnoreCase("KeyCloak")){
			Test logonTest = new Test();
			logonTest.setExecutionUserName(executionUser);
			logonTest.setExecutionPassword(executionUser.equalsIgnoreCase("jack")?"jack":"john");
			logonTest.setCallResult("{ " +
										  "\"access_token\" : \"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIwR1Y3eUFWTFk1bnRTc3doOXc1SkxwWXBWTmpkWU05S1F4RGtVUVNRSVZVIn0.eyJqdGkiOiIwMWM3MzRiZC02ODI1LTQ3ZTQtODllNS0wN2U4MThiMDRjMGIiLCJleHAiOjE0OTQ5MTQxMDQsIm5iZiI6MCwiaWF0IjoxNDk0OTEzODA0LCJpc3MiOiJodHRwOi8vMTAuMTEwLjQzLjI4OjgwOTUvYXV0aC9yZWFsbXMvUGFuZ2VhX1JlYWxtIiwiYXVkIjoicGFuZ2VhX2FwcCIsInN1YiI6ImRlNjljN2I5LTNkNjktNGIyMS1iYmM0LTFkMjc4NzUyYTgxNiIsInR5cCI6IkJlYXJlciIsImF6cCI6InBhbmdlYV9hcHAiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiJjNGZhNjEyNC00NzZiLTRlZjYtYmU2YS03ZTUzY2I0ZTYyNjAiLCJhY3IiOiIxIiwiY2xpZW50X3Nlc3Npb24iOiJmMTU2NzIzMi1jMzBmLTQxMDQtYTVkYy1kMWM0YTdhYWM3ZGYiLCJhbGxvd2VkLW9yaWdpbnMiOlsiKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiQ2hlY2tlciIsIkN1c3RvbV9DaGVja19yb2xlXzEiLCJDdXN0b21fQ2hlY2tfcm9sZV8yIiwidHJhZGVvcHMiLCJ1bWFfYXV0aG9yaXphdGlvbiIsIk1ha2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sIm5hbWUiOiJKb2huIE1ha2VyIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiam9obiIsImdpdmVuX25hbWUiOiJKb2huIiwiZmFtaWx5X25hbWUiOiJNYWtlciIsImVtYWlsIjoidGVzdEBwaWV0ZXJzZS5tZSJ9.E45Dy8O7nZiVYXRdHaFXqX3L0smwUFhm_pIwlDYph3lPuQ6jWnlPdf7XgXHfXifeDbyO79mo4uI2BwJ8GUrlGlVlVRUK3lH96foE7C759zYlP1bk4lyIX5vzhELEjz8Nkt6J-DdnB63yoio4VG84ApY0dwVluawXbYKZECGTFottfTWxFuS-lh2YaJEwOid0a4V00HsZSl5CEq4dwiD8dtp4E_cP-CDk7mXBCc_7CIIsVYo5KwpPOyPltvoiZlFiamUseKNQgUkeSVcIgk_lfjTzr3HyRMbd28Zpk6BiCpB3mCvohBKqGZ6S1HUAX4bhD-3bLug1TuFeGjbJdKSHdw\", " + 
										  "\"refresh_token\" : \"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIwR1Y3eUFWTFk1bnRTc3doOXc1SkxwWXBWTmpkWU05S1F4RGtVUVNRSVZVIn0.eyJqdGkiOiIzMjJhOGQzMC00NjhkLTQ1NjQtYmU0MC04Yzc3NDBmZGY3ZGUiLCJleHAiOjE0OTQ5MTU2MDQsIm5iZiI6MCwiaWF0IjoxNDk0OTEzODA0LCJpc3MiOiJodHRwOi8vMTAuMTEwLjQzLjI4OjgwOTUvYXV0aC9yZWFsbXMvUGFuZ2VhX1JlYWxtIiwiYXVkIjoicGFuZ2VhX2FwcCIsInN1YiI6ImRlNjljN2I5LTNkNjktNGIyMS1iYmM0LTFkMjc4NzUyYTgxNiIsInR5cCI6IlJlZnJlc2giLCJhenAiOiJwYW5nZWFfYXBwIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiYzRmYTYxMjQtNDc2Yi00ZWY2LWJlNmEtN2U1M2NiNGU2MjYwIiwiY2xpZW50X3Nlc3Npb24iOiJmMTU2NzIzMi1jMzBmLTQxMDQtYTVkYy1kMWM0YTdhYWM3ZGYiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiQ2hlY2tlciIsIkN1c3RvbV9DaGVja19yb2xlXzEiLCJDdXN0b21fQ2hlY2tfcm9sZV8yIiwidHJhZGVvcHMiLCJ1bWFfYXV0aG9yaXphdGlvbiIsIk1ha2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX19.KJtMPZyQ_VC4LERPM67dxJcu8KpRo-9wF6VV3LRwy10e6u26EIwSKRxn8Q7U0j6czdrpAFQphVkyGpYpc3CPfRL6gzcXPf64zSHF84VwRlfUNXOsPHH0AoN7Hd5fHCTFcRJzks_YyB_ahKcuQPkgaKa6sBIL8N_wB3lL48pwadxmpj64mJXFBDT7z3C0NdDCdQn2p634lFZAqDOHpPgi2wQi0yiXKDpptBqK6XT1sb0JzuQwqtiT6RR7MSg8zxziwOSwigbqv8ME3ZUmIpsxljcy8gHDAt-5mJ3zSMUdX6Ik9NO6Y3upPyZUoN6JOWe-d6G2fyGZ1isMCEbh6AsYsw\"," + 
										  "\"refresh_expires_in\" : 1800," + 
										  "\"not-before-policy\" : 0," + 
										  "\"id_token\" : \"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIwR1Y3eUFWTFk1bnRTc3doOXc1SkxwWXBWTmpkWU05S1F4RGtVUVNRSVZVIn0.eyJqdGkiOiIxMDU4NmE4Ni04MTlmLTQyMmYtOTk3ZS05ZDQyYzRhOTgyNDMiLCJleHAiOjE0OTQ5MTQxMDQsIm5iZiI6MCwiaWF0IjoxNDk0OTEzODA0LCJpc3MiOiJodHRwOi8vMTAuMTEwLjQzLjI4OjgwOTUvYXV0aC9yZWFsbXMvUGFuZ2VhX1JlYWxtIiwiYXVkIjoicGFuZ2VhX2FwcCIsInN1YiI6ImRlNjljN2I5LTNkNjktNGIyMS1iYmM0LTFkMjc4NzUyYTgxNiIsInR5cCI6IklEIiwiYXpwIjoicGFuZ2VhX2FwcCIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6ImM0ZmE2MTI0LTQ3NmItNGVmNi1iZTZhLTdlNTNjYjRlNjI2MCIsImFjciI6IjEiLCJuYW1lIjoiSm9obiBNYWtlciIsInByZWZlcnJlZF91c2VybmFtZSI6ImpvaG4iLCJnaXZlbl9uYW1lIjoiSm9obiIsImZhbWlseV9uYW1lIjoiTWFrZXIiLCJlbWFpbCI6InRlc3RAcGlldGVyc2UubWUifQ.dnZXXZVMDyIZP6nmgG_N5pM0VL__bJL3Aj8Fh5qgFOt1ZnPSrrJ-CAsj6H63EKS_Dm7BJrITtjwz2F-ZYQ_OK73THObFsQTAGsdm6oK1XvQ9dbLEpnSAu-HY6t_1Fi4c5nxpU1rS_I1U5pismYiFlMnYjEbPh16kbPpsOmwxKlxcrNLvlNlGIww0Arnmnbz2D_xKreKHoFOBe2uv8bRvMnbKH7emMxXre6Wi6rd7AmRAqTnNkQTOjZkimNAQEIptJEhs9TkmOWV2jtuDEqpn6T6sP62dvjV2wBceEbv_1L0d4oBUiz554_RtQCzfKHj9EJQaMNry9ssknBclLIUoeQ\"," + 
										  "\"token_type\" : \"bearer\"," + 
										  "\"session_state\" : \"c4fa6124-476b-4ef6-be6a-7e53cb4e6260\"," + 
										  "\"expires_in\" : 300 " + 
									"}"); //Purely added for variable extraction - manual process for now. Can be automated to do this on import. //TODO
			logonTest.setExecutionIndex(4);
			logonTest.setId(-1);
			logonTest.setParentSuite(ts);
			logonTest.setPostData("username="+executionUser+"&password="+executionUser+"&grant_type=password&client_id=pangea_app");//Check this //TODO
			logonTest.setTestDescription("Logon using " + executionUser +".");
			logonTest.setTestName("Logon_" + executionUser);
			logonTest.setVerb("POST");
			logonTest.setWebServiceURL("http://@{KeyCloakServerIP}:@{KeyCloakServerPort}/auth/realms/Pangea_Realm/protocol/openid-connect/token");
			ts.getSuiteData().add(logonTest);
		}
		
//		Alert a = new Alert(AlertType.CONFIRMATION,"Would you like to send the initiation email during this test?",ButtonType.YES,ButtonType.NO);
//		java.util.Optional<ButtonType> res = a.showAndWait();
//		if (res.get() == ButtonType.YES){
//			
//		}
//		
		
		//http://10.110.43.105:8085/job/Pangea.Test.Sandbox/
		
		int size = har.getLog().getEntries().size();
		int totalCounter= 5;
		int executionCounter = 5;
		parent.setProgressMaximum(size);
		parent.appendLog("Reading " + size + " entries");
		
		for ( Entries entry : har.getLog().getEntries() ){
			parent.setTaskDisplayer("Compiling Test suite (" + totalCounter + " of " + size + ")");
			if ( ( entry.getRequest().getMethod().equalsIgnoreCase("get") && mustImportGET )
					|| ( entry.getRequest().getMethod().equalsIgnoreCase("post") && mustImportPOST) ||
					( entry.getRequest().getUrl().contains("custom/query/tasks?searchFilter=&size=13&startIndex=0&status=new") && !entry.getRequest().getMethod().equalsIgnoreCase("options"))){ // <-- Cater for Refresh -->
				if (! entry.getRequest().getUrl().endsWith("html") && 
						! entry.getRequest().getUrl().endsWith(".js") && 
						! entry.getRequest().getUrl().endsWith(".css")&& 
						! entry.getRequest().getUrl().contains(".png")&&
						! entry.getRequest().getUrl().contains("viewAttachment.uri")&&
						! entry.getRequest().getUrl().contains("selectedDocument.url")&&
						! entry.getRequest().getUrl().contains("selectedDocument.link")&&
						! entry.getRequest().getUrl().endsWith(".gif") && 
						! entry.getRequest().getUrl().endsWith("/") && 
						! entry.getRequest().getUrl().contains("assets/css")&& 
						! entry.getRequest().getUrl().contains("assets/fonts")    ){
					Test test = new Test();
					int debug3=2;
					test.setId(-1);
					test.setExecutionUserName(executionUser);
					test.setExecutionPassword(executionUser.equalsIgnoreCase("jack")?"jack":"john");
					test.setCallResult((entry.getResponse().getContent().getText() == null
							?""
							: entry.getResponse().getContent().getText()
										.replace("://" + servicesServerIP + ":" + servicesServerPort, "://@{ServicesServerIP}:@{ServicesServerPort}")
										//.replace("://" + servicesServerIP , "://@{ServicesServerIP}:@{ServicesServerPort}")
										.replace("://" + workFlowServerIP + ":" + workFlowServerPort, "://@{WorkFlowServerIP}:@{WorkFlowServerPort}")
										//.replace("://" + workFlowServerIP , "://@{WorkFlowServerIP}:@{WorkFlowServerPort}")
										.replace("://" + uiServerIP + ":" +  uiServerPort, "://@{UIServerIP}:@{UIServerPort}")
										//.replace("://" + uiServerIP , "://@{UIServerIP}:@{UIServerPort}")
							));
					test.setExecutionIndex(executionCounter);
					System.out.println(test.getCallResult());
					String pData = entry.getRequest().getPostData() == null?"":entry.getRequest().getPostData().getText();
					byte[] bytes = pData.getBytes();
					pData = new String(bytes,"UTF-8");
					String tmp =  pData
											.replace("://" + servicesServerIP + ":" + servicesServerPort, "://@{ServicesServerIP}:@{ServicesServerPort}")
											//.replace("://" + servicesServerIP , "://@{ServicesServerIP}:@{ServicesServerPort}")
											.replace("://" + workFlowServerIP + ":" + workFlowServerPort, "://@{WorkFlowServerIP}:@{WorkFlowServerPort}")
											//.replace("://" + workFlowServerIP , "://@{WorkFlowServerIP}:@{WorkFlowServerPort}")
											.replace("://" + uiServerIP + ":" +  uiServerPort, "://@{UIServerIP}:@{UIServerPort}");
											//.replace("://" + uiServerIP , "://@{UIServerIP}:@{UIServerPort}");
					test.setPostData(tmp);
					test.setTestDescription("Test Description " + test.getExecutionIndex());
					test.setTestName("TestName " + test.getExecutionIndex());
					test.setTestSuiteID(123);
					test.setVerb(entry.getRequest().getMethod().replace("\n", "").replace("\t", "").replace(" " , ""));
					test.setWebServiceURL(entry.getRequest().getUrl()
													.replace("://" + servicesServerIP + ":" + servicesServerPort, "://@{ServicesServerIP}:@{ServicesServerPort}")
													//.replace("://" + servicesServerIP , "://@{ServicesServerIP}:@{ServicesServerPort}")
													.replace("://" + workFlowServerIP + ":" + workFlowServerPort, "://@{WorkFlowServerIP}:@{WorkFlowServerPort}")
												//	.replace("://" + workFlowServerIP , "://@{WorkFlowServerIP}:@{WorkFlowServerPort}")
													.replace("://" + uiServerIP + ":" +  uiServerPort, "://@{UIServerIP}:@{UIServerPort}")
													//.replace("://" + uiServerIP , "://@{UIServerIP}:@{UIServerPort}")
													);
					try{
						if ( test.getCallResult() != null && !"null".equalsIgnoreCase(test.getCallResult()) && !"".equalsIgnoreCase(test.getCallResult())){
							if ( !test.getCallResult().contains("\"name\":\"Test BGi Template\"")){//Skip the "name":"Test BGi Template" section. This section contains Base64Images which translate into images when embedded as a result in the html report.
								if ( !test.getWebServiceURL().contains("Pangea_Realm/protocol/openid-connect/token")){//Skip the KeyCloak Auth, dont validate the Keys. 
									byte[] bytess = test.getCallResult().getBytes();
									test.setCallResult(new String(bytess,"UTF-8"));
									parse(test.getCallResult(),test);
								}
							}
								
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					ts.getSuiteData().add(test);
					content.append("Test " + executionCounter + "^Description " + executionCounter + "^" + executionCounter +"^" + 
							entry.getRequest().getUrl()
														.replace("://" + servicesServerIP + ":" + servicesServerPort, "://@{ServicesServerIP}:@{ServicesServerPort}")
														//.replace("://" + servicesServerIP , "://@{ServicesServerIP}:@{ServicesServerPort}")
														.replace("://" + workFlowServerIP + ":" + workFlowServerPort, "://@{WorkFlowServerIP}:@{WorkFlowServerPort}")
													//	.replace("://" + workFlowServerIP , "://@{WorkFlowServerIP}:@{WorkFlowServerPort}")
														.replace("://" + uiServerIP + ":" +  uiServerPort, "://@{UIServerIP}:@{UIServerPort}")
													//	.replace("://" + uiServerIP , "://@{UIServerIP}:@{UIServerPort}")
														
														 + "^" + (entry.getResponse().getContent().getText() == null
														?""
														: entry.getResponse().getContent().getText()
																)
							+"^77777777^" + entry.getRequest().getMethod()
//							.replace("\n", "")
//							.replace("\t", "")
//							.replace(" " , "") 
							+ "^" + ( entry.getRequest().getPostData() == null?"":entry.getRequest().getPostData().getText())//.replace("\n", "").replace("\t", "").replace(" " , ""))
							+"\n");
					executionCounter++;
					totalCounter++;
				}else{
					totalCounter++;
					parent.setTaskDisplayer("Compiling Test suite (" + totalCounter + " of "+size+")");
				}
			}else{
				totalCounter++;
				parent.setTaskDisplayer("Compiling Test suite (" + totalCounter + " of "+size+")");
			}
			parent.incrementProgress();
			Thread.currentThread();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		parent.appendLog("CSV destination is " + csvFileName);
		parent.appendLog("Writing CSV file with import results");
		BufferedWriter bos = new BufferedWriter(new FileWriter(csvFileName));
		bos.write(content.toString());
		bos.close();
		parent.appendLog("Saving suite");
		new TestSuiteFactory().saveTestSuite(parent, ts);
		parent.appendLog("Import Successful");
		parent.setTaskDisplayer("Idle...");
		
	}

	public static void parse(String json, Test t) throws JsonProcessingException, IOException, JSONException  {
		try {
			JSONObject obj = new JSONObject(json);
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> it = obj.keys();
			while(it.hasNext()){
				Object keey = it.next();
				//System.out.println(keey);
				if ( keey instanceof String){
					String key = (String)keey;
					Object val = obj.get(key);
					if ( val instanceof String){
						if ( !key.toString().contains("realm-public-key"))
						{
							if ( !key.contains("Date") && !key.contains("date") 
									&& !key.contains("Time") && !key.contains("Time") 
									&& !val.toString().contains("function()")){
								TestValidation tv = new TestValidation();
								if ( key.contains("reference")){//"reference":"ZA01091201050"
									val = "[\"][a-zA-Z]*[\"][:][\"][a-zA-Z]*[0-9]*[\"]";
									tv.setExpectedResult(val.toString());
									tv.setRegularExpression(true);
								}else{
									tv.setExpectedResult("\""+key +"\":\"" + val.toString()+"\"");
								}
								tv.setAssociatedTest(t);
								tv.setTestCaseID(t.getId());
								t.getValidations().add(tv);
							}
							else if (!val.toString().contains("function()") && key.contains("id")){
								//Regex ID UUID field. 
							 	TestValidation tv = new TestValidation();
								tv.setAssociatedTest(t);
								tv.setTestCaseID(t.getId());
								tv.setRegularExpression(true);
								val = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";
								tv.setExpectedResult("\""+key +"\":\"" + val +"\"");
								t.getValidations().add(tv);
								System.out.println("Added ID VP - " + key + " with format ::" + val.toString() + ":::::" + t.getWebServiceURL());
							}
							else{
								if (  key.contains("Date") || key.contains("date") || key.contains("Time")|| key.contains("time")  
											&& !val.toString().contains("function()")){
								  	TestValidation tv = new TestValidation();
									tv.setAssociatedTest(t);
									tv.setTestCaseID(t.getId());
									tv.setRegularExpression(true);
									//val = "[0-9]*[-][0-9]*-[0-9]*[T][0-9]*[:][0-9]*[:][0-9]*[.][0-9]*[+][0-9]*:[0-9]*";
									val = "([\\+-]?\\d{4}(?!\\d{2}\\b))((-?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\\"
										+ "d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24\\:?00)([\\.,]\\d+(?!:))?)?(\\17[0-5]\\d([\\.,]\\d+)?)?([zZ]|"
										+ "([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?";
//									if ( key.equalsIgnoreCase("dateOfBirth"))
//										val = "[0-9]*[-][0-9]*-[0-9]*";
									tv.setExpectedResult("\""+key +"\":\"" + val +"\"");
									t.getValidations().add(tv);
									System.out.println("Added date VP - " + key + " with format ::" + val.toString() + ":::::" + t.getWebServiceURL());
								}
							}
						
						}
					}else if (val instanceof JSONObject){
						parse(val.toString(),t);
					}else if (val instanceof JSONArray){
						parse(val.toString(),t);
					}
					else if (val instanceof Integer){
						TestValidation tv = new TestValidation();
						tv.setExpectedResult("\""+key +"\":" + val +"");
						tv.setAssociatedTest(t);
						tv.setTestCaseID(t.getId());
						t.getValidations().add(tv);
					}					
					else if (val instanceof Long){
						TestValidation tv = new TestValidation();
						tv.setExpectedResult("\""+key +"\":" + val +"");
						tv.setAssociatedTest(t);
						tv.setTestCaseID(t.getId());
						t.getValidations().add(tv);
					}
					else if (val instanceof Double){
						DecimalFormat df = new DecimalFormat("#.####");
						df.setMinimumFractionDigits(4);
						val = df.format(val);
						TestValidation tv = new TestValidation();
						tv.setExpectedResult("\""+key +"\":" + val +"");
						tv.setAssociatedTest(t);
						tv.setTestCaseID(t.getId());
						t.getValidations().add(tv);
					}
					else if ( val == null || val.toString().equalsIgnoreCase("null") ){
						TestValidation tv = new TestValidation();
						tv.setExpectedResult("\""+key +"\":null");
						tv.setAssociatedTest(t);
						tv.setTestCaseID(t.getId());
						t.getValidations().add(tv);
					}
					else if ( val instanceof Boolean ){
						TestValidation tv = new TestValidation();
						tv.setExpectedResult("\""+key +"\":" + ( Boolean.parseBoolean(val.toString()) == true ?"true":"false"));
						tv.setAssociatedTest(t);
						tv.setTestCaseID(t.getId());
						t.getValidations().add(tv);
					}
				}else if ( keey instanceof JSONObject){
					parse(keey.toString(),t);
				}else if ( keey instanceof Date){
					//parse(keey.toString(),t);
					System.out.println("DateInstanceOf");
				}else{
					LoggerFactory.getLogger(Runner.class).error("Unrecognized JSON. Investigate");
				}
			}
		} catch (JSONException e) {
			try {
				int debug = 7;
				JSONArray obj = new JSONArray(json);
				
				//System.out.println(obj.getClass());
				for ( int i = 0 ; i < obj.length() ; i ++){
					Object o = obj.get(i);
					//System.out.println(o.getClass());
					if ( o instanceof JSONObject ){
						parse(o.toString(), t);
					}
					else if ( o instanceof String ){
						if ( !o.toString().contains("realm-public-key"))
						{
							if ( !o.toString().contains("date")){
								//Just add the whole string into the validation.
								TestValidation tv = new TestValidation();
								tv.setExpectedResult(o.toString());
								tv.setAssociatedTest(t);
								tv.setTestCaseID(t.getId());
								t.getValidations().add(tv);
							}else{
								System.out.println("Skipped Date VP_2");
							}
						}
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				TestValidation tv = new TestValidation();
				tv.setExpectedResult(json);
				tv.setAssociatedTest(t);
				tv.setTestCaseID(t.getId());
				t.getValidations().add(tv);
			}
		}
	}
}
//System.out.println("Test " + executionCounter + "^Description " + executionCounter + "^" + executionCounter +"^" + 
//entry.getRequest().getUrl()
//							.replace("://" + servicesServerIP + ":" + servicesServerPort, "://@{ServicesServerIP}:@{ServicesServerPort}")
//							.replace("://" + workFlowServerIP + ":" + workFlowServerPort, "://@{WorkFlowServerIP}:@{WorkFlowServerPort}")
//							.replace("://" + uiServerIP + ":" +  uiServerPort, "://@{UIServerIP}:@{UIServerPort}")
//							.replace("\n", "")
//							.replace("\t", "")
//							.replace(" " , "") 
//							+"^" + (entry.getResponse().getContent().getText() == null
//											?""
//											: entry.getResponse().getContent().getText()
//							.replace("\n", "")
//							.replace("\t", "")
//							.replace(" " , ""))
//	+"^77777777^" + entry.getRequest().getMethod()
//						.replace("\n", "")
//						.replace("\t", "")
//						.replace(" " , "") + "^" + ( entry.getRequest().getPostData() == null?"":entry.getRequest().getPostData().getText()
//																																		.replace("\n", "")
//																																		.replace("\t", "")
//																																		//.replace(" " , "")
//																																		));
