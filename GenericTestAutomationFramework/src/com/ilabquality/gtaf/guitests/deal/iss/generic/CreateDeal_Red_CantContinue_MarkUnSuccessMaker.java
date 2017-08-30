package com.ilabquality.gtaf.guitests.deal.iss.generic;

import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class CreateDeal_Red_CantContinue_MarkUnSuccessMaker extends GUITest {

	private GuiTestReportSuite gt;
	public CreateDeal_Red_CantContinue_MarkUnSuccessMaker(GUIController controller){
		super(controller);
	}
	@Override
	public void runTests(GuiTestReportSuite gt) {
		
		this.gt = gt;
		int d=0;
		try{
			this.baseUrl = Runner.getRuntimeVariables().get("@{SeleniumBaseURL}").get(0).getLiveValue();
			resetEnv(gt);
			driver.manage().window().maximize();
			driver.get(baseUrl);
			LogonTests lt = new LogonTests(controller);
			lt.setMap(getMap());
			lt.setServer(sl, driver, webDriver);
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);

			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Payment Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Performance Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Advance Payment Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Labour Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Shipping Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Maintenance Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Customs Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Retention Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Immigration Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Security Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Financial Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Trade Licence Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Avalisation"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Court Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Lease Guarantee (villa IRS)"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Rental Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Credit Facilities"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Study Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Preference Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Retainage Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Airline Agent Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
		}catch(Exception e){
			gt.addTest(getActiveTest());
			e.printStackTrace();
		}

	}
	
	public GuiTestReportSingleTest redEscalationMarkAsUnsuccessfulMaker(String productType, String step, String productSubType) throws InterruptedException{
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Create a deal and Release - Gold Case(" + productType+":" + productSubType +":" + step + "- CreateDeal_Red_CantContinue_MarkUnSuccessMaker)" );
		System.out.println("CreateDeal_Red_CantContinue_MarkUnSuccessMaker - Gold Case(" + productType+":" + productSubType +":" + step+")");
		getActiveTest().setTestName("CreateAndReleaseDeal");
		TestStep activeStep = null;
		try{
			Page landingPage = getPage("LandingPage");
			
			createTrade(TradeType.EMAIL,productType);
			
			TestStep ts= new TestStep();
			ts.setStepName("Open the Follow Up Queue");
			activeStep = ts;
			driver.findElement(landingPage.getPageObjectByID("LandingPage.FollowUpQueue").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Open the New Queue");
			activeStep = ts;
			driver.findElement(landingPage.getPageObjectByID("LandingPage.NewQueue").getBy()).click();;
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Claim the task");
			activeStep = ts;
			driver.findElement(By.id("tl_new_" +caseID +"_claimTaskBtn")).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			Page caseProductInformation = getPage("CaseProductInformation");
			ts= new TestStep();
			ts.setStepName("Select Product - " + productType);
			activeStep = ts;
			new Select(driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.ProductType").getBy())).selectByVisibleText(productType);
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Select Issue");
			activeStep = ts;
			new Select(driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.Step").getBy())).selectByVisibleText(step);
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Select the Product Sub Type - " + productSubType);
			activeStep = ts;
			new Select(driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.ProductSubType").getBy())).selectByVisibleText(productSubType);
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.NextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			Page customerSearch = getPage("CustomerSearch");
			ts= new TestStep();
			ts.setStepName("Clear the search criteria field");
			activeStep = ts;
			driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.NameSearchField").getBy()).clear();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			if ( productType.equalsIgnoreCase("Inward BGI") || productType.equalsIgnoreCase("Export LC")){
				ts= new TestStep();
				ts.setStepName("Enter 'CitiBank' in the Search Criteria Field");
				activeStep = ts;
				driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.NameSearchField").getBy()).sendKeys("CitiBank");
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
				ts= new TestStep();
				ts.setStepName("Select 'CitiBank' from the Search Popup");
				activeStep = ts;
				driver.findElement(By.linkText("CitiBank")).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}else{
				ts= new TestStep();
				ts.setStepName("Enter Jan in the Search Criteria Field");
				activeStep = ts;
				driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.NameSearchField").getBy()).sendKeys("jan");
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
				ts= new TestStep();
				ts.setStepName("Select Jan from the Search Popup");
				activeStep = ts;
				driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.SearchPopup").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}
			
			
			ts= new TestStep();
			ts.setStepName("Mark the customer as KYC Yes");
			activeStep = ts;
			new Select(driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.CustomerKYCSelect").getBy())).selectByVisibleText("Yes");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.NextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			//Escalation creation by selecting RED.
			Page appChecks = getPage("ApplicationChecks");
			if ( !productType.equalsIgnoreCase("Inward BGI") && !productType.equalsIgnoreCase("Export LC")){
				ts= new TestStep();
				ts.setStepName("Click No on the App Check - correctness");
				activeStep = ts;
				driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.CorrectnessNo").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
				ts= new TestStep();
				ts.setStepName("Enable entry of the due date field by using the JSExecutor");
				activeStep = ts;
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("document.getElementById('"+ appChecks.getPageObjectByID("ApplicationChecks.CorrectnessDueDate").getLocator()+"').removeAttribute('readonly')");
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
				DateTime dt = DateTime.now().plusDays(1);
				String str = Runner.DATE_FORMAT.print(dt);
				 
				ts= new TestStep();
				ts.setStepName("Enter tomorrow as the Due Date");
				activeStep = ts;
				driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.CorrectnessDueDate").getBy()).sendKeys(str);
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
				ts= new TestStep();
				ts.setStepName("Enter a note in the 'Note' field. ");
				activeStep = ts;
				driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.CorrectnessNotes").getBy()).sendKeys("Selenium Task");
				ts.setPassed(true);
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);

			}else{
				ts= new TestStep();
				ts.setStepName("Click No on the App Check - AML");
				activeStep = ts;
				driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.AMLNo").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
				ts= new TestStep();
				ts.setStepName("Enable entry of the due date field by using the JSExecutor");
				activeStep = ts;
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("document.getElementById('"+ appChecks.getPageObjectByID("ApplicationChecks.AMLDueDate").getLocator()+"').removeAttribute('readonly')");
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
				DateTime dt = new DateTime();
				String str = Runner.DATE_FORMAT.print(dt);
				 
				ts= new TestStep();
				ts.setStepName("Enter tomorrow as the Due Date");
				activeStep = ts;
				driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.AMLDueDate").getBy()).sendKeys(str);
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
				ts= new TestStep();
				ts.setStepName("Enter a note in the 'Note' field. ");
				activeStep = ts;
				driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.AMLNotes").getBy()).sendKeys("Selenium Task");
				ts.setPassed(true);
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);

			}
			ts= new TestStep();
			ts.setStepName("Click the 'Send to follow up' button. ");
			activeStep = ts;
			driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.SendToFollowUpButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				
			}
			
			ts= new TestStep();
			ts.setStepName("Open the 'New' queue");
			activeStep = ts;
			driver.findElement(landingPage.getPageObjectByID("LandingPage.NewQueue").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Open the 'Follow Up' queue.");
			activeStep = ts;
			driver.findElement(landingPage.getPageObjectByID("LandingPage.FollowUpQueue").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Validate the new Follow up task is created and is present in the 'Follow Up' queue.");
			activeStep = ts;
			
			try{
				driver.findElement(landingPage.getPageObjectByID("LandingPage.Claim").getBy()).isDisplayed();
				ts.setPassed(true);
				ts.setOptionalFailureMessage("Follow up task created for RED escalation");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}catch(Exception e){
				ts.setPassed(false);
				ts.setOptionalFailureMessage("Follow up task not created for RED escalation");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}
			
			try{
				Thread.sleep(1000);
			}
			catch(Exception e){
				
			}
			ts= new TestStep();
			ts.setStepName("Open the follow up task.");
			activeStep = ts;
			driver.findElement(By.id("tl_escalated_" +caseID +"_claimTaskBtn")).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click the 'Mark as unsuccessful' button.");
			activeStep = ts;
			driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.MarkAsUnsuccesfulButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click the 'OK' button. ");
			activeStep = ts;
			driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.MarkAsUnsuccesfulButtonOK").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			sleep(2);
			
			ts= new TestStep();
			ts.setStepName("Navigate to the 'New' Queue");
			activeStep = ts;
			driver.findElement(landingPage.getPageObjectByID("LandingPage.NewQueue").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Navigate to the 'Unsuccesful Queue'");
			activeStep = ts;
			driver.findElement(landingPage.getPageObjectByID("LandingPage.UnsuccessfulQueue").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			sleep(1);
			
			ts= new TestStep();
			ts.setStepName("Validate the item exists in the Unsuccesful Queue");
			activeStep = ts;
			try{
				driver.findElement(landingPage.getPageObjectByID("LandingPage.Claim").getBy()).isDisplayed();
				ts.setPassed(true);
				ts.setOptionalFailureMessage("Red Escalation that was sent to unsuccessful by the MAKER exists in the correct Queue");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}catch(Exception e){
				driver.findElement(landingPage.getPageObjectByID("LandingPage.Claim").getBy()).isDisplayed();
				ts.setPassed(false);
				ts.setOptionalFailureMessage("Red Escalation that was sent to unsuccessful by the MAKER DID NOT EXIST in the correct Queue");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
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
	public GuiTestReportSingleTest validateFieldPresence() {
		
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
