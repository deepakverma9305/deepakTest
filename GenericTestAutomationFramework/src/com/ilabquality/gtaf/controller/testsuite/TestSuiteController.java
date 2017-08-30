package com.ilabquality.gtaf.controller.testsuite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.GTAFApplController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.reporting.testpack.TestPackReport;
import com.ilabquality.gtaf.testsuite.Test;
import com.ilabquality.gtaf.testsuite.TestSuite;

public class TestSuiteController implements Runnable {

	private GTAFApplController parent;
	private TestSuite suite;
	private Test stopAtTest;
	public TestSuiteController(GTAFApplController parent, TestSuite suite, Test test){
		int debug=0;
		this.parent = parent;
		this.suite = suite;
		this.stopAtTest = test;
	}
	
	public void doAction(){
		int debug=0;
		try {
			suite.setStopSuiteExecution(false);
			DateTime startTime = DateTime.now();
			double start = startTime.getMillis();
			parent.appendLog("Retrieving Test Suite");
			TestSuite reportSuite = new TestSuite();
			reportSuite.setTestSuiteDescription(suite.getTestSuiteDescription());
			reportSuite.setTestSuiteID(0);
			reportSuite.setTestSuiteName(suite.getTestSuiteName());
			parent.appendLog("Iterating until test " + stopAtTest.getTestName());
			parent.setProgressMaximum(suite.getSuiteData().size());
			for  (Test test : suite.getSuiteData()){
				if (!suite.mustStopSuiteExecution()){
					if ( test.getTestName().equalsIgnoreCase(stopAtTest.getTestName())){
						parent.appendLog("Stopping iteration");
						break;
					}else{
						if ( test.getDelay() > 0 ){
							parent.appendLog("Test " + test.getId() + " - " + test.getTestName() + " has a delay, waiting " + test.getDelay() + " seconds");
							Thread.currentThread();
							Thread.sleep(test.getDelay() * 1000);
						}
						suite.setSelectedTest(test);
						test.setParentSuite(suite);
						test.performTest(parent);
						reportSuite.getSuiteData().add(test);
					}
				}else{
					break;
				}
				parent.incrementProgress();
			}
			DateTime endTime = DateTime.now();
			double end = endTime.getMillis();
			double duration = (end - start) / 1000d;
			ArrayList<TestSuite> al = new ArrayList<>();
			al.add(reportSuite);
			TestPackReport tpr = new TestPackReport(al,DateTimeFormat.forPattern("YYYY-MM-dd hh:mm:ss").print(startTime),DateTimeFormat.forPattern("YYYY-MM-dd hh:mm:ss").print(endTime),duration," Run to Here");
			tpr.generateAndShow();
			parent.appendLog("Ended Run at "  + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
			parent.setTaskDisplayer("Idle...");
			parent.resetProgress();
		} catch (Exception e) {
			
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
	}

	@Override
	public void run() {
		doAction();
		
	}
	
}
