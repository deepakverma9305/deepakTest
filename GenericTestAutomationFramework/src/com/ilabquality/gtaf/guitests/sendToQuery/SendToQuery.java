package com.ilabquality.gtaf.guitests.sendToQuery;

import java.awt.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.ilabquality.gtaf.controller.gui.GUIController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.gtafAppl.Runner.TradeType;
import com.ilabquality.gtaf.guitests.helpers.GUITest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSingleTest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSuite;
import com.ilabquality.gtaf.guitests.helpers.Page;
import com.ilabquality.gtaf.guitests.helpers.TestStep;
import com.ilabquality.gtaf.guitests.logon.LogonTests;

public class SendToQuery extends GUITest {
	String baseUrl = "";
	public SendToQuery(GUIController controller){
		super(controller);
	}
	
	public GuiTestReportSingleTest sendDummyToQuery(TradeType t, String product){
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Send a newly created item to the Query Queue. Claim and close. ");
		getActiveTest().setTestName("DummyToQueryAndClose_WIP " + t + "-" + product);
		TestStep activeStep = null;
		try{
			Page landingPage = getPage("LandingPage");
			TestStep ts = new TestStep();
			createTrade(t,product);
			activeStep = ts;
			ts.setStepName("Click on the New Queue Header");
			driver.findElement(By.linkText("New")).click();
			ts.setPassed(true);
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
			
			ts = new TestStep();
			activeStep = ts;
			ts.setStepName("Click on the Follow Up Queue Header");
			driver.findElement(landingPage.getPageObjectByID("LandingPage.FollowUpQueue").getBy()).click();
			ts.setPassed(true);
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
			
			ts = new TestStep();
			activeStep = ts;
			ts.setStepName("Click on the New Queue Header");
			driver.findElement(landingPage.getPageObjectByID("LandingPage.NewQueue").getBy()).click();
			ts.setPassed(true);
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
			System.err.println("CaseID is " + caseID);
			ts = new TestStep();
			ts.setStepName("Click on the claim button");
			activeStep = ts;
			driver.findElement(By.id("tl_new_" +caseID +"_claimTaskBtn")).click();
			ts.setPassed(true);
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
			
			Page caseProductInformation = getPage("CaseProductInformation");
			if ( t != TradeType.GENERIC_AMEND){
				ts= new TestStep();
				ts.setStepName("Select Product - Foreign Outward BGI");
				activeStep = ts;
				new Select(driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.ProductType").getBy())).selectByVisibleText("Foreign Outward BGI");
				ts.setPassed(true);
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
				
				ts= new TestStep();
				ts.setStepName("Select Issue");
				activeStep = ts;
				new Select(driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.Step").getBy())).selectByVisibleText("Issue");
				ts.setPassed(true);
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
				
				ts= new TestStep();
				ts.setStepName("Select the Product Sub Type - " + "Bid/Tender Bond");
				activeStep = ts;
				new Select(driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.ProductSubType").getBy())).selectByVisibleText("Bid/Tender Bond");
				ts.setPassed(true);
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}else{
				ts= new TestStep();
				ts.setStepName("Validate Product - " + product);
				activeStep = ts;
				String val = new Select(driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.ProductType").getBy())).getFirstSelectedOption().getText();
				System.out.println(val);
				if ( val.equalsIgnoreCase(product)){
					ts.setPassed(true);
					ts.setTestStepImage(takeScreenshot(driver));
					getActiveTest().addTestStep(ts,driver);
				}else{
					ts.setPassed(false);
					ts.setOptionalFailureMessage("Value retrieved was " + val);
					ts.setTestStepImage(takeScreenshot(driver));
					getActiveTest().addTestStep(ts,driver);
				}
				ts= new TestStep();
				ts.setStepName("Validate Step - 'Amend'");
				activeStep = ts;
				val = new Select(driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.Step").getBy())).getFirstSelectedOption().getText();
				
				if ( val.trim().equalsIgnoreCase("Amend")){
					ts.setPassed(true);
					ts.setTestStepImage(takeScreenshot(driver));
					getActiveTest().addTestStep(ts,driver);
				}else{
					ts.setPassed(false);
					ts.setOptionalFailureMessage("Value retrieved was " + val);
					ts.setTestStepImage(takeScreenshot(driver));
					getActiveTest().addTestStep(ts,driver);
				}
				ts= new TestStep();
				ts.setStepName("Validate Reference - '123'" );
				activeStep = ts;
				val = driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.AmendReferenceSearchField").getBy()).getAttribute("value");
				if ( val.trim().equalsIgnoreCase("123")){
					ts.setPassed(true);
					ts.setTestStepImage(takeScreenshot(driver));
					getActiveTest().addTestStep(ts,driver);
				}else{
					ts.setPassed(false);
					ts.setOptionalFailureMessage("Value retrieved was " + val);
					ts.setTestStepImage(takeScreenshot(driver));
					getActiveTest().addTestStep(ts,driver);
				}
			}
		
			
			
			ts = new TestStep();
			ts.setStepName("Click on the Raise Query Button");
			activeStep = ts;
			driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.RaiseQueryButton").getBy()).click();
			ts.setPassed(true);
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
			
			ts = new TestStep();
			ts.setStepName("Click on the Queries queue");
			activeStep = ts;
			driver.findElement(landingPage.getPageObjectByID("LandingPage.QueriesQueue").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts = new TestStep();
			ts.setStepName("Click on the Open Query button");
			activeStep = ts;
			driver.findElement(landingPage.getPageObjectByID("LandingPage.OpenQueryButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			sleep(2);
			
			Page queryPage = getPage("Query");
			ts = new TestStep();
			ts.setStepName("Click on the textarea, clear it.");
			activeStep = ts;
			driver.findElement(queryPage.getPageObjectByID("Query.Comments").getBy()).clear();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts = new TestStep();
			ts.setStepName("Enter the value 'Selenium Query' into the 'Comments' field.");
			activeStep = ts;
			driver.findElement(queryPage.getPageObjectByID("Query.Comments").getBy()).sendKeys("Selenium Query");
			ts.setPassed(true);
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
			
			ts = new TestStep();
			ts.setStepName("Click the 'Close Query' button.");
			activeStep = ts;
			driver.findElement(queryPage.getPageObjectByID("Query.CloseQueryButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts = new TestStep();
			ts.setStepName("Validate the system returns to the Task list by checking the presence of the 'Manual Trades' button.");
			activeStep = ts;
			if ( waitForExistance(queryPage.getPageObjectByID("Query.CloseQueryButton").getBy())){
				ts.setPassed(true);
				ts.setTestStepImage(takeScreenshot(driver));
				ts.setOptionalFailureMessage("The system returned to the task list correctly.");
				getActiveTest().addTestStep(ts,driver);
			}else{
				ts.setPassed(false);
				ts.setTestStepImage(takeScreenshot(driver));
				ts.setOptionalFailureMessage("The system did not return to the task list correctly.");
			}
		}
		catch(Exception e){
			activeStep.setPassed(false);
			activeStep.setOptionalFailureMessage(e.getMessage());
			activeStep.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(activeStep,driver);
		}
		return getActiveTest();
	}
	
	
	@Override
	public void runTests(GuiTestReportSuite gt) {
		this.baseUrl = Runner.getRuntimeVariables().get("@{SeleniumBaseURL}").get(0).getLiveValue();
		GuiTestReportSingleTest gtrst = new GuiTestReportSingleTest();
		gtrst.setTestName("ResetENV");
		gtrst.setTestDescription("Reset the test environment using endpoints");
		TestStep ts = new TestStep();
		ts.setStepName("Reset the test environment");
		
		Runner.serviceConnector.resetEnvironment();
		ts.setPassed(true);
		gtrst.addTestStep(ts,driver);
		gt.addTest(gtrst);
		driver.manage().window().maximize();
		driver.get(baseUrl);
		LogonTests lt = new LogonTests(controller);
		lt.setServer(sl, driver, webDriver);
		lt.setMap(getMap());

		gt.addTest(lt.logonBasic("john","john"));
		gt.addTest(sendDummyToQuery(TradeType.EMAIL,"Foreign Outward BGI"));
		gt.addTest(lt.logoutBasic());
		resetEnv(gt);
		gt.addTest(lt.logonBasic("john","john"));
		gt.addTest(sendDummyToQuery(TradeType.GENERIC_AMEND,"Foreign Outward BGI"));
		gt.addTest(lt.logoutBasic());
		resetEnv(gt);
		gt.addTest(lt.logonBasic("john","john"));
		gt.addTest(sendDummyToQuery(TradeType.CHANNEL_FOBGI_ISSUE,"Foreign Outward BGI"));
		gt.addTest(lt.logoutBasic());
		resetEnv(gt);
		gt.addTest(lt.logonBasic("john","john"));
		gt.addTest(sendDummyToQuery(TradeType.GENERIC_AMEND,"Import LC"));
		gt.addTest(lt.logoutBasic());
		resetEnv(gt);
		gt.addTest(lt.logonBasic("john","john"));
		gt.addTest(sendDummyToQuery(TradeType.CHANNEL_LC_ISSUE,"Import LC"));
		gt.addTest(lt.logoutBasic());
		resetEnv(gt);
		gt.addTest(lt.logonBasic("john","john"));
		gt.addTest(sendDummyToQuery(TradeType.GENERIC_AMEND,"Local Outward BGI"));
		gt.addTest(lt.logoutBasic());
		resetEnv(gt);
		gt.addTest(lt.logonBasic("john","john"));
		gt.addTest(sendDummyToQuery(TradeType.CHANNEL_LOBGI_ISSUE,"Local Outward BGI"));
		gt.addTest(lt.logoutBasic());
		resetEnv(gt);
		gt.addTest(lt.logonBasic("john","john"));
		gt.addTest(sendDummyToQuery(TradeType.SWIFT_HAND_DELIVERED,"Local Outward BGI"));
		gt.addTest(lt.logoutBasic());
		resetEnv(gt);

	}

	@Override
	public GuiTestReportSingleTest validateFieldPresence()  {
		
		return null;
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
