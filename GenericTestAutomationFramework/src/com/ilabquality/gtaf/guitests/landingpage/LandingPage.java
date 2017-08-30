package com.ilabquality.gtaf.guitests.landingpage;

import com.ilabquality.gtaf.controller.gui.GUIController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.guitests.helpers.GUITest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSingleTest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSuite;
import com.ilabquality.gtaf.guitests.helpers.Page;
import com.ilabquality.gtaf.guitests.helpers.TestStep;
import com.ilabquality.gtaf.guitests.logon.LogonTests;

public class LandingPage extends GUITest {

	String baseUrl = "Not Set";
	int safetyNet = 20;
	private GuiTestReportSuite testReportSuite;
	public LandingPage(GUIController controller){
		super(controller);
	}
	
	@Override
	public void runTests(GuiTestReportSuite gt) {
		try{
			this.baseUrl = Runner.getRuntimeVariables().get("@{SeleniumBaseURL}").get(0).getLiveValue();
			System.out.println("BaseURL set to " + baseUrl);
			this.testReportSuite = gt;
			System.out.println("Suite : Landing Page - All Scenarios" );
			
			driver.manage().window().maximize();
			driver.get(baseUrl);
		
			System.out.println("Calling Logon John");
			LogonTests lt = new LogonTests(controller);
			lt.setServer(sl, driver, webDriver);
			lt.setMap(getMap());
			gt.addTest(lt.logonBasic("john","john"));
			System.out.println("validateFieldPresence()");
			gt.addTest(validateFieldPresence());
			System.out.println("validateWIPQueueHeaders()");
			gt.addTest(validateWIPQueueHeaders());
			System.out.println("validateNewQueueHeaders()");
			gt.addTest(validateNewQueueHeaders());
			System.out.println("validateFollowUpQueueHeaders()");
			gt.addTest(validateFollowUpQueueHeaders());
			System.out.println("validateQueriesQueueHeaders()");
			gt.addTest(validateQueriesQueueHeaders());
			System.out.println("validateUnsuccessfulQueueHeaders()");
			gt.addTest(validateUnsuccessfulHeaders());
			System.out.println("validateReleaseQueueHeaders()");
			gt.addTest(validateReleaseHeaders());
			System.out.println("validateCompleteHeaders()");
			gt.addTest(validateCompleteHeaders());
			gt.addTest(lt.logoutBasic());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		

	}

	@Override
	public GuiTestReportSingleTest validateFieldPresence()  {
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Validate the correct queues are present on the page");
		getActiveTest().setTestName("ValidateQueuePresence");
		TestStep activeStep = null;
		try{
			Page landingPage = getPage("LandingPage");
			TestStep ts = new TestStep();
			activeStep = ts;
			ts.setStepName("Validate the 'New' Queue is present on the page");
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.NewQueue").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.NewQueue").getBy()).getText().equalsIgnoreCase("New")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
					ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
	
			ts  = new TestStep();
			activeStep = ts;
			ts.setStepName("Validate the 'Work In Progress' Queue is present on the page");
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.WorkInProgressQueue").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.WorkInProgressQueue").getBy()).getText().equalsIgnoreCase("Work in progress")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			activeStep = ts;
			ts.setStepName("Validate the 'Follow up' Queue is present on the page");
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.FollowUpQueue").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.FollowUpQueue").getBy()).getText().equalsIgnoreCase("Follow up")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			activeStep = ts;
			ts.setStepName("Validate the 'Queries' Queue is present on the page");
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.QueriesQueue").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.QueriesQueue").getBy()).getText().equalsIgnoreCase("Queries")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			activeStep = ts;
			ts.setStepName("Validate the 'Complete' Queue is present on the page");
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.CompleteQueue").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.CompleteQueue").getBy()).getText().equalsIgnoreCase("Complete")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			activeStep = ts;
			ts.setStepName("Validate the 'Unsuccessful' Queue is present on the page");
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueue").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueue").getBy()).getText().equalsIgnoreCase("Unsuccessful")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			activeStep = ts;
			ts.setStepName("Validate the 'Release' Queue is present on the page");
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.ReleaseQueue").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.ReleaseQueue").getBy()).getText().equalsIgnoreCase("Release")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);

		
		}catch(Exception e){
			activeStep.setPassed(false);
			
			activeStep.setOptionalFailureMessage(e.getMessage());
			}
		return getActiveTest();
		
	}

	private GuiTestReportSingleTest validateWIPQueueHeaders(){
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Validate the correct headers are present on the Work In Progress Queue");
		getActiveTest().setTestName("ValidateQueueHeaders_WIP");
		TestStep activeStep = null;
		int d=0;
		try {
			Page landingPage = getPage("LandingPage");
			TestStep ts  = new TestStep();
			activeStep = ts;
			ts.setStepName("Open the 'Work In Progress' Queue");
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.WorkInProgressQueue").getBy()) ){
				driver.findElement(landingPage.getPageObjectByID("LandingPage.WorkInProgressQueue").getBy()).click();
				Thread.sleep(500);
				ts.setPassed(true);
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the REF header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.WIPQueueHeaderRef").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.WIPQueueHeaderRef").getBy()).getText().equalsIgnoreCase("Ref")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Owner header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.WIPQueueHeaderOwner").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.WIPQueueHeaderOwner").getBy()).getText().equalsIgnoreCase("Owner")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Channel header is present on the page");
			activeStep = ts;
			if (waitForExistance(landingPage.getPageObjectByID("LandingPage.WIPQueueHeaderChannel").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.WIPQueueHeaderChannel").getBy()).getText().equalsIgnoreCase("Channel")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Created Date header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.WIPQueueHeaderCreatedDate").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.WIPQueueHeaderCreatedDate").getBy()).getText().equalsIgnoreCase("Created Date")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Due Date header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.WIPQueueHeaderDueDate").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.WIPQueueHeaderDueDate").getBy()).getText().equalsIgnoreCase("Due Date")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Task Status header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.WIPQueueHeaderTaskStatus").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.WIPQueueHeaderTaskStatus").getBy()).getText().equalsIgnoreCase("Task Status")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Action header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.WIPQueueHeaderAction").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.WIPQueueHeaderAction").getBy()).getText().equalsIgnoreCase("Action")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
		
		} catch (Exception e) {
			activeStep.setPassed(false);
			activeStep.setOptionalFailureMessage(e.getMessage());
		}
		return getActiveTest();
	}
	
	private GuiTestReportSingleTest validateNewQueueHeaders(){
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Validate the correct headers are present on the New Queue");
		getActiveTest().setTestName("ValidateQueueHeaders_New");
		TestStep activeStep = null;
		try {
			Page landingPage = getPage("LandingPage");
			TestStep ts = new TestStep();
			activeStep = ts;
			ts.setStepName("Open the 'New' Queue");
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.NewQueue").getBy()) ){
				driver.findElement(landingPage.getPageObjectByID("LandingPage.NewQueue").getBy()).click();
				Thread.sleep(500);
				ts.setPassed(true);
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the REF header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.NewQueueHeaderRef").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.NewQueueHeaderRef").getBy()).getText().equalsIgnoreCase("Ref")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Sender header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.NewQueueHeaderSender").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.NewQueueHeaderSender").getBy()).getText().equalsIgnoreCase("Sender")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Channel header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.NewQueueHeaderChannel").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.NewQueueHeaderChannel").getBy()).getText().equalsIgnoreCase("Channel")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Created Date header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.NewQueueHeaderCreatedDate").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.NewQueueHeaderCreatedDate").getBy()).getText().equalsIgnoreCase("Created Date")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Due Date header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.NewQueueHeaderDueDate").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.NewQueueHeaderDueDate").getBy()).getText().equalsIgnoreCase("Due Date")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Task Status header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.NewQueueHeaderTaskStatus").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.NewQueueHeaderTaskStatus").getBy()).getText().equalsIgnoreCase("Task Status")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Action header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.NewQueueHeaderAction").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.NewQueueHeaderAction").getBy()).getText().equalsIgnoreCase("Action")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
		
		} catch (Exception e) {
			activeStep.setPassed(false);
			activeStep.setOptionalFailureMessage(e.getMessage());
		}
		return getActiveTest();
	}
	
	private GuiTestReportSingleTest validateFollowUpQueueHeaders(){
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Validate the correct headers are present on the Follow up Queue");
		getActiveTest().setTestName("ValidateQueueHeaders_FollowUp");
		TestStep activeStep = null;
		try {
			Page landingPage = getPage("LandingPage");
			TestStep ts = new TestStep();
			activeStep = ts;
			ts.setStepName("Open the 'Follow up' Queue");
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.FollowUpQueue").getBy()) ){
				driver.findElement(landingPage.getPageObjectByID("LandingPage.FollowUpQueue").getBy()).click();
				Thread.sleep(500);
				ts.setPassed(true);
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the REF header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.FollowUpQueueHeaderRef").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.FollowUpQueueHeaderRef").getBy()).getText().equalsIgnoreCase("Ref")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Owner header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.FollowUpQueueHeaderOwner").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.FollowUpQueueHeaderOwner").getBy()).getText().equalsIgnoreCase("Owner")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Channel header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.FollowUpQueueHeaderChannel").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.FollowUpQueueHeaderChannel").getBy()).getText().equalsIgnoreCase("Channel")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Created Date header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.FollowUpQueueHeaderCreatedDate").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.FollowUpQueueHeaderCreatedDate").getBy()).getText().equalsIgnoreCase("Created Date")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Due Date header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.FollowUpQueueHeaderDueDate").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.FollowUpQueueHeaderDueDate").getBy()).getText().equalsIgnoreCase("Due Date")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Task Status header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.FollowUpQueueHeaderTaskStatus").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.FollowUpQueueHeaderTaskStatus").getBy()).getText().equalsIgnoreCase("Task Status")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Target header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.FollowUpQueueHeaderTarget").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.FollowUpQueueHeaderTarget").getBy()).getText().equalsIgnoreCase("Target")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			ts  = new TestStep();
			ts.setStepName("Validate the Action header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.FollowUpQueueHeaderAction").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.FollowUpQueueHeaderAction").getBy()).getText().equalsIgnoreCase("Action")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
		
		} catch (Exception e) {
			activeStep.setPassed(false);
			activeStep.setOptionalFailureMessage(e.getMessage());
		}
		return getActiveTest();
	}
	
	private GuiTestReportSingleTest validateQueriesQueueHeaders(){
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Validate the correct headers are present on the Queries Queue");
		getActiveTest().setTestName("ValidateQueueHeaders_Queries");
		TestStep activeStep = null;
		try {
			Page landingPage = getPage("LandingPage");
			TestStep ts = new TestStep();
			activeStep = ts;
			ts.setStepName("Open the 'Queries' Queue");
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.QueriesQueue").getBy()) ){
				driver.findElement(landingPage.getPageObjectByID("LandingPage.QueriesQueue").getBy()).click();
				Thread.sleep(500);
				ts.setPassed(true);
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the REF header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.QueriesQueueHeaderRef").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.QueriesQueueHeaderRef").getBy()).getText().equalsIgnoreCase("Ref")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Owner header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.QueriesQueueHeaderOwner").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.QueriesQueueHeaderOwner").getBy()).getText().equalsIgnoreCase("Owner")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			
			ts  = new TestStep();
			ts.setStepName("Validate the Sender header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.QueriesQueueHeaderSender").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.QueriesQueueHeaderSender").getBy()).getText().equalsIgnoreCase("Sender")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Channel header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.QueriesQueueHeaderChannel").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.QueriesQueueHeaderChannel").getBy()).getText().equalsIgnoreCase("Channel")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Created Date header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.QueriesQueueHeaderCreatedDate").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.QueriesQueueHeaderCreatedDate").getBy()).getText().equalsIgnoreCase("Created Date")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			ts.setStepName("Validate the Due Date header is present on the page");
			ts  = new TestStep();
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.QueriesQueueHeaderDueDate").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.QueriesQueueHeaderDueDate").getBy()).getText().equalsIgnoreCase("Due Date")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Task Status header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.QueriesQueueHeaderTaskStatus").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.QueriesQueueHeaderTaskStatus").getBy()).getText().equalsIgnoreCase("Task Status")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Action header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.QueriesQueueHeaderAction").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.QueriesQueueHeaderAction").getBy()).getText().equalsIgnoreCase("Action")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
		
		} catch (Exception e) {
			activeStep.setPassed(false);
			activeStep.setOptionalFailureMessage(e.getMessage());
		}
		return getActiveTest();
	}
	
	private GuiTestReportSingleTest validateUnsuccessfulHeaders(){
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Validate the correct headers are present on the Unsuccessful Queue");
		getActiveTest().setTestName("ValidateQueueHeaders_Unsuccessful");
		TestStep activeStep = null;
		try {
			Page landingPage = getPage("LandingPage");
			TestStep ts = new TestStep();
			activeStep = ts;
			ts.setStepName("Open the 'Unsuccessful' Queue");
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueue").getBy()) ){
				driver.findElement(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueue").getBy()).click();
				Thread.sleep(500);
				ts.setPassed(true);
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the REF header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueueHeaderRef").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueueHeaderRef").getBy()).getText().equalsIgnoreCase("Ref")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Owner header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueueHeaderOwner").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueueHeaderOwner").getBy()).getText().equalsIgnoreCase("Owner")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			
			ts  = new TestStep();
			ts.setStepName("Validate the Channel header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueueHeaderChannel").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueueHeaderChannel").getBy()).getText().equalsIgnoreCase("Channel")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Created Date header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueueHeaderCreatedDate").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueueHeaderCreatedDate").getBy()).getText().equalsIgnoreCase("Created Date")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Due Date header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueueHeaderDueDate").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueueHeaderDueDate").getBy()).getText().equalsIgnoreCase("Due Date")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Task Status header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueueHeaderTaskStatus").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueueHeaderTaskStatus").getBy()).getText().equalsIgnoreCase("Task Status")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Action header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueueHeaderAction").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueueHeaderAction").getBy()).getText().equalsIgnoreCase("Action")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
		
		} catch (Exception e) {
			activeStep.setPassed(false);
			activeStep.setOptionalFailureMessage(e.getMessage());
		}
		return getActiveTest();
	}
	
	private GuiTestReportSingleTest validateReleaseHeaders(){
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Validate the correct headers are present on the Release Queue");
		getActiveTest().setTestName("ValidateQueueHeaders_Release");
		TestStep activeStep = null;
		try {
			Page landingPage = getPage("LandingPage");
			TestStep ts = new TestStep();
			activeStep = ts;
			ts.setStepName("Open the 'Release' Queue");
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.ReleaseQueue").getBy()) ){
				driver.findElement(landingPage.getPageObjectByID("LandingPage.ReleaseQueue").getBy()).click();
				Thread.sleep(500);
				ts.setPassed(true);
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the REF header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.ReleaseQueueHeaderRef").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.ReleaseQueueHeaderRef").getBy()).getText().equalsIgnoreCase("Ref")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Owner header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.ReleaseQueueHeaderOwner").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.ReleaseQueueHeaderOwner").getBy()).getText().equalsIgnoreCase("Owner")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			
			ts  = new TestStep();
			ts.setStepName("Validate the Channel header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.ReleaseQueueHeaderChannel").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.ReleaseQueueHeaderChannel").getBy()).getText().equalsIgnoreCase("Channel")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Created Date header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.ReleaseQueueHeaderCreatedDate").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.ReleaseQueueHeaderCreatedDate").getBy()).getText().equalsIgnoreCase("Created Date")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Due Date header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.ReleaseQueueHeaderDueDate").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.ReleaseQueueHeaderDueDate").getBy()).getText().equalsIgnoreCase("Due Date")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Task Status header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.ReleaseQueueHeaderTaskStatus").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.ReleaseQueueHeaderTaskStatus").getBy()).getText().equalsIgnoreCase("Task Status")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Action header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.ReleaseQueueHeaderAction").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.ReleaseQueueHeaderAction").getBy()).getText().equalsIgnoreCase("Action")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
		
		} catch (Exception e) {
			activeStep.setPassed(false);
			activeStep.setOptionalFailureMessage(e.getMessage());
		}
		return getActiveTest();
	}
	
	private GuiTestReportSingleTest validateCompleteHeaders(){
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Validate the correct headers are present on the Complete Queue");
		getActiveTest().setTestName("ValidateQueueHeaders_Complete");
		TestStep activeStep = null;
		try {
			Page landingPage = getPage("LandingPage");
			TestStep ts = new TestStep();
			activeStep = ts;
			ts.setStepName("Open the 'Complete' Queue");
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.CompleteQueue").getBy()) ){
				driver.findElement(landingPage.getPageObjectByID("LandingPage.CompleteQueue").getBy()).click();
				Thread.sleep(500);
				ts.setPassed(true);
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the REF header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.CompleteQueueHeaderRef").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.CompleteQueueHeaderRef").getBy()).getText().equalsIgnoreCase("Ref")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Owner header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.CompleteQueueHeaderOwner").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.CompleteQueueHeaderOwner").getBy()).getText().equalsIgnoreCase("Owner")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			
			ts  = new TestStep();
			ts.setStepName("Validate the Channel header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.CompleteQueueHeaderChannel").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.CompleteQueueHeaderChannel").getBy()).getText().equalsIgnoreCase("Channel")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Created Date header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.CompleteQueueHeaderCreatedDate").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.CompleteQueueHeaderCreatedDate").getBy()).getText().equalsIgnoreCase("Created Date")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Due Date header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.CompleteQueueHeaderDueDate").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.CompleteQueueHeaderDueDate").getBy()).getText().equalsIgnoreCase("Due Date")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Task Status header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.CompleteQueueHeaderTaskStatus").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.CompleteQueueHeaderTaskStatus").getBy()).getText().equalsIgnoreCase("Task Status")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
			ts  = new TestStep();
			ts.setStepName("Validate the Action header is present on the page");
			activeStep = ts;
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.CompleteQueueHeaderAction").getBy()) ){
				if ( driver.findElement(landingPage.getPageObjectByID("LandingPage.CompleteQueueHeaderAction").getBy()).getText().equalsIgnoreCase("Action")){
					ts.setPassed(true);
				}else{
					ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
				}
			}else{
				ts.setPassed(false);ts.setTestStepImage(takeScreenshot(driver));
			}
			getActiveTest().addTestStep(ts,driver);
			
		
		} catch (Exception e) {
			activeStep.setPassed(false);
			activeStep.setOptionalFailureMessage(e.getMessage());
		}
		return getActiveTest();
	}
	
	@Override
	public GuiTestReportSingleTest validateFieldWidths() {
		
		return null;
	}

	@Override
	public GuiTestReportSingleTest validateFieldDefaultStates() {
		
		return null;
	}

	@Override
	public GuiTestReportSingleTest validateFieldInputTypes() {
		
		return null;
	}

	@Override
	public GuiTestReportSingleTest validateDefaultValues() {
		
		return null;
	}

}
