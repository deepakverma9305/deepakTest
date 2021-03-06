package com.ilabquality.gtaf.controller.testpack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.GTAFApplController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.packmanager.Pack;
import com.ilabquality.gtaf.packmanager.PackManagerFactory;
import com.ilabquality.gtaf.packmanager.TestPackTestSuite;
import com.ilabquality.gtaf.reporting.testpack.TestPackReport;
import com.ilabquality.gtaf.testsuite.Test;
import com.ilabquality.gtaf.testsuite.TestSuite;
import com.ilabquality.gtaf.testsuite.TestSuiteFactory;
import com.ilabquality.gtaf.utils.email.GTAFEmailer;

public class TestPackController implements Runnable{

	private String testPackForExecution;
	private GTAFApplController parent;
	public static final int MAX_CONNECTIONS=20;
 
	public TestPackController(GTAFApplController parent, String packName){
		this.testPackForExecution = packName;
		this.parent = parent;
		System.out.println(Runner.getRuntimeVariables().get("@{MustSendEmailToInitiateTests}"));
	}
	
	public void doAction() throws InterruptedException{
		DateTime startTime = DateTime.now();
		double start = startTime.getMillis();
		ArrayList<TestSuite> executedSuites = new ArrayList<TestSuite>();
		if ( parent != null )
			parent.setTaskDisplayer("Processing Pack");
		int debug=2;
		try {
			//Get the pack with suite data
			log("Retrieving Test Pack");
			Pack p = new PackManagerFactory().getTestPackTestSuiteData(testPackForExecution);
			log("Retrieving Test Validation counts for pack " + p.getPackName() +".");
			if ( p.getPackName() == null ){
				log("Pack [" +testPackForExecution + "] was not retrieved. Stopping...");
				return;
			}
			System.out.println(Runner.getRuntimeVariables().get("@{MustSendEmailToInitiateTests}").get(0));
			int totalTests = new PackManagerFactory().getPackTestRecordCount(testPackForExecution);
			System.out.println(Runner.getRuntimeVariables().get("@{MustSendEmailToInitiateTests}").get(0));
			log(totalTests + " tests in this pack.");
			System.out.println("Total Tests : " + totalTests);
			System.out.println(Runner.getRuntimeVariables().get("@{MustSendEmailToInitiateTests}").get(0));
			if ( parent != null )
				parent.setProgressMaximum(totalTests);	
			//Iterate through all tests, sending posts.
			boolean connectionError = false;
			for ( TestPackTestSuite tpts : p.getPackData() ){
				TestSuite suite = new TestSuiteFactory().getTestSuiteByID(tpts.getSuiteID());
				log("Retrieved suite details for - " + suite.getTestSuiteName());
				log("Iterating tests and performing validations.");
				System.out.println(Runner.getRuntimeVariables().get("@{MustSendEmailToInitiateTests}").get(0).getLiveValue());
				if(( Boolean.parseBoolean(Runner.getRuntimeVariables().get("@{MustSendEmailToInitiateTests}").get(0).getLiveValue()))){
					GTAFEmailer gtm = new GTAFEmailer("Auto_Test Initiated Email for suite " + suite.getTestSuiteName()) {
						 
						@Override
						public String getBodyContent() {
							return "This email was generated by the Pangea Test Automation Framework.";
						}
					};
					gtm.attachments.add(Runner.getRuntimeVariables().get("@{EmailAttachmentFilePath}").get(0).getLiveValue());
					gtm.send();
				}
				
				for ( Test test : suite.getSuiteData()){
						suite.setSelectedTest(test);
						if ( !suite.mustStopSuiteExecution()){
							if(( Boolean.parseBoolean(Runner.getRuntimeVariables().get("@{MustSendEmailToInitiateTests}").get(0).getLiveValue()))){
							//TODO pick up and insert processinstanceid here, alternatively include search in recorded har.	if ( test.getWebServiceURL())
							}
							test.performTest(parent);
							if ( test.getDelay() > 0 ){
								log("Test has a delay, waiting for " + test.getDelay() + " seconds before continuing.");
								Thread.currentThread();
								Thread.sleep(test.getDelay() * 1000);
							}
					}else{
						Runner.cliExitCode =2;
						connectionError = true;
						break;
					}
						if ( parent != null )
							parent.incrementProgress();
				}
				executedSuites.add(suite);
			}
			DateTime endTime = DateTime.now();
			double end = endTime.getMillis();
			double duration = (end - start) / 1000d;
			System.out.println("Duration " + duration);
			//generate the report. 
			if (! connectionError ) {
				log("Attempting to generate Report");
				generateReport(executedSuites,DateTimeFormat.forPattern("YYYY-MM-dd hh:mm:ss").print(startTime),DateTimeFormat.forPattern("YYYY-MM-dd hh:mm:ss").print(endTime),duration,testPackForExecution);
			}
			log("Done");
			if ( parent != null ){
				parent.appendLog("Ended Run at "  + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
				parent.setTaskDisplayer("Idle...");
				parent.resetProgress();
			}
			p = null;
			
		}catch(InterruptedException ie){
			throw(ie);
		}
		catch (Exception e) {
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
	}

	private void generateReport(ArrayList<TestSuite> executedSuites, String startTime, String endTime, double duration,String packName) {
		TestPackReport tpr = new TestPackReport(executedSuites,startTime,endTime,duration,packName);
		tpr.generateAndShow();
	}
	
	private void log(String message){
		if ( parent != null ){
			parent.appendLog(message);
			System.out.println(message);
		}
		else{
			LoggerFactory.getLogger(Runner.class).info(message);
		}
	}

	@Override
	public void run(){
		try {
			doAction();
		} catch (InterruptedException e) {
			// 
			e.printStackTrace();
			Runner.cliExitCode = 2;
		}		
	}
	
	
	
	
}
