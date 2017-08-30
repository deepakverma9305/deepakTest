package com.ilabquality.gtaf.guitests.deal.cancel.generic;

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

public class CancelDeal_Red_CantContinue_MarkUnSuccessMaker extends GUITest {

	private GuiTestReportSuite gt;

	public CancelDeal_Red_CantContinue_MarkUnSuccessMaker(GUIController controller) {
		super(controller);
		// TODO Auto-generated constructor stub
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
			
			doAction(lt,"Local Outward BGI","Bid/Tender Bond");
			doAction(lt,"Local Outward BGI","Payment Guarantee");
			doAction(lt,"Local Outward BGI","Performance Guarantee");
			doAction(lt,"Local Outward BGI","Advance Payment Guaranteed");
			doAction(lt,"Local Outward BGI","Labour Guarantee");
			doAction(lt,"Local Outward BGI","Shipping Guarantee");
			doAction(lt,"Local Outward BGI","Maintenance Guarantee");
			doAction(lt,"Local Outward BGI","Customs Bond");
			doAction(lt,"Local Outward BGI","Retention Guarantee");
			doAction(lt,"Local Outward BGI","Immigration Guarantee");
			doAction(lt,"Local Outward BGI","Security Guarantee");
			doAction(lt,"Local Outward BGI","Financial Guarantee");
			doAction(lt,"Local Outward BGI","Trade Licence Guarantee");
			doAction(lt,"Local Outward BGI","Avalisation");
			doAction(lt,"Local Outward BGI","Court Guarantee");
			doAction(lt,"Local Outward BGI","Lease Guarantee (villa IRS)");
			doAction(lt,"Local Outward BGI","Rental Guarantee");
			doAction(lt,"Local Outward BGI","Credit Facilities");
			doAction(lt,"Local Outward BGI","Study Guarantee");
			doAction(lt,"Local Outward BGI","Preference Guarantee");
			doAction(lt,"Local Outward BGI","Retainage Guarantee");
			doAction(lt,"Local Outward BGI","Airline Agent Guarantee");
			doAction(lt,"Local Outward BGI","Bid/Tender Bond");
			doAction(lt,"Local Outward BGI","Payment Guarantee");
			doAction(lt,"Local Outward BGI","Performance Guarantee");
			doAction(lt,"Local Outward BGI","Advance Payment Guaranteed");
			doAction(lt,"Local Outward BGI","Labour Guarantee");
			doAction(lt,"Local Outward BGI","Shipping Guarantee");
			doAction(lt,"Local Outward BGI","Maintenance Guarantee");
			doAction(lt,"Local Outward BGI","Customs Bond");
			doAction(lt,"Local Outward BGI","Retention Guarantee");
			doAction(lt,"Local Outward BGI","Immigration Guarantee");
			doAction(lt,"Local Outward BGI","Security Guarantee");
			doAction(lt,"Local Outward BGI","Financial Guarantee");
			doAction(lt,"Local Outward BGI","Trade Licence Guarantee");
			doAction(lt,"Local Outward BGI","Avalisation");
			doAction(lt,"Local Outward BGI","Court Guarantee");
			doAction(lt,"Local Outward BGI","Lease Guarantee (villa IRS)");
			doAction(lt,"Local Outward BGI","Rental Guarantee");
			doAction(lt,"Local Outward BGI","Credit Facilities");
			doAction(lt,"Local Outward BGI","Study Guarantee");
			doAction(lt,"Local Outward BGI","Preference Guarantee");
			doAction(lt,"Local Outward BGI","Retainage Guarantee");
			doAction(lt,"Local Outward BGI","Airline Agent Guarantee");
			doAction(lt,"Import LC","Import LC");
			doAction(lt,"Export LC","Export LC");
			doAction(lt,"Inward BGI","Bid/Tender Bond");
			doAction(lt,"Inward BGI","Payment Guarantee");
			doAction(lt,"Inward BGI","Performance Guarantee");
			doAction(lt,"Inward BGI","Advance Payment Guaranteed");
			doAction(lt,"Inward BGI","Labour Guarantee");
			doAction(lt,"Inward BGI","Shipping Guarantee");
			doAction(lt,"Inward BGI","Maintenance Guarantee");
			doAction(lt,"Inward BGI","Customs Bond");
			doAction(lt,"Inward BGI","Retention Guarantee");
			doAction(lt,"Inward BGI","Immigration Guarantee");
			doAction(lt,"Inward BGI","Security Guarantee");
			doAction(lt,"Inward BGI","Financial Guarantee");
			doAction(lt,"Inward BGI","Trade Licence Guarantee");
			doAction(lt,"Inward BGI","Avalisation");
			doAction(lt,"Inward BGI","Court Guarantee");
			doAction(lt,"Inward BGI","Lease Guarantee (villa IRS)");
			doAction(lt,"Inward BGI","Rental Guarantee");
			doAction(lt,"Inward BGI","Credit Facilities");
			doAction(lt,"Inward BGI","Study Guarantee");
			doAction(lt,"Inward BGI","Preference Guarantee");
			doAction(lt,"Inward BGI","Retainage Guarantee");
			doAction(lt,"Inward BGI","Airline Agent Guarantee");

		}catch(Exception e){
			gt.addTest(getActiveTest());
			e.printStackTrace();
		}
	}
	
	public void doAction(LogonTests lt, String product,  String prodSubType) {
		com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction aa = 
				new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction(controller);
		aa.setMap(getMap());
		aa.setServer(sl, driver, webDriver);
		try {
			//Create
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.noEscalationsRelease(product,"Issue",prodSubType));
			gt.addTest(lt.logoutBasic());
			//Cancel
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cancelDealDoRedCantContinueEscalation(product,"Cancel",prodSubType,aa.getProdRef()));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
		} catch (InterruptedException e) {
			gt.addTest(getActiveTest());
			e.printStackTrace();
		}
	}

	
	public GuiTestReportSingleTest cancelDealDoRedCantContinueEscalation(String productType, String step, String productSubType,String prodRef)
	{
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Cancel a deal and validate the follow up escalation - Gold Case(" + productType+":" + productSubType +":" + step + " - cancelDealDoRedCantContinueEscalation)" );
		System.out.println("Cancel - cancelDeal - Gold Case(" + productType+":" + productSubType +":" + step+")");
		getActiveTest().setTestName("Cancel " + productType);
		TestStep activeStep = null;
		
		try{
			Page landingPage = getPage("LandingPage");
			
			TestStep ts= new TestStep();
			createTrade(TradeType.EMAIL,productType);
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
			// tl_new_125037_claimTaskBtn
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
			ts.setStepName("Select '"+step+"'");
			activeStep = ts;
			new Select(driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.Step").getBy())).selectByVisibleText(step);
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Enter the reference["+ prodRef + " - " + productSubType + " - ISS" +"] for the deal which was just created. - " + productSubType);
			activeStep = ts;
			driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.AmendReferenceSearchField").getBy()).sendKeys(prodRef);
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Select the result from the list.");
			activeStep = ts;
			driver.findElement(By.linkText(prodRef + " - " + productSubType + " - ISS" )).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
		
			
			//TODO Validate the table
			// end table validations.
			
			//TODO Validate the state of this button as per the STATUS column value in the result table. 
			
			ts= new TestStep();
			ts.setStepName("Validate the 'Cancel' button is enabled.");
			activeStep = ts;
			boolean isEnabled = driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.CancelButton").getBy()).isEnabled();
			if  (isEnabled){
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
			}else{
				ts.setPassed(false);
				ts.setOptionalFailureMessage("The 'Cancel' button is expected to be enabled, it is disabled");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
				
			}
			
			if ( !ts.hasPassed()){
				//Failed on Next, so cannot continue;
				return getActiveTest();
			}
		
			
			ts= new TestStep();
			ts.setStepName("Click "+ step);
			activeStep = ts;
			driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.CancelButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click the 'OK' Button on the cancellation popup.");
			activeStep = ts;
			driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.CancelQueryOKButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			Page customerSearch = getPage("CustomerSearch");
//TODO Validate the customer is displayed as per the create.
// Endd Validation
			ts= new TestStep();
			ts.setStepName("Validate the customer is marked as KYC - 'Yes'");
			activeStep = ts;
			String val = new Select(driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.CustomerKYCSelect").getBy())).getFirstSelectedOption().getText();
			if ( val.equalsIgnoreCase("Yes")){
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}else{
				ts.setPassed(false);
				ts.setOptionalFailureMessage("Expected to see KYC - Yes, value was [" + val +"]");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}
		
			
			ts= new TestStep();
			ts.setStepName("Click Next to proceed");
			activeStep = ts;
			driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.NextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			Page appChecks = getPage("ApplicationChecks");
			sleep(500);
			ts= new TestStep();
			ts.setStepName("Click No on the App Check - jurisdiction");
			activeStep = ts;
			driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.JurisdictionNo").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			ts= new TestStep();
			ts.setStepName("Enable entry of the due date field by using the JSExecutor");
			activeStep = ts;
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementById('"+ appChecks.getPageObjectByID("ApplicationChecks.JurisdictionDueDateField").getLocator()+"').removeAttribute('readonly')");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			DateTime dt = DateTime.now().plusDays(1);
			String str = Runner.DATE_FORMAT.print(dt);
			 
			ts= new TestStep();
			ts.setStepName("Enter tomorrow as the Due Date");
			activeStep = ts;
			driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.JurisdictionDueDateField").getBy()).sendKeys(str);
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Enter a note in the 'Note' field. ");
			activeStep = ts;
			driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.JurisdictionDueDateNoteField").getBy()).sendKeys("Selenium Task");
			ts.setPassed(true);
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
			
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
			driver.findElement(landingPage.getPageObjectByID("LandingPage.Claim").getBy()).click();
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
		}catch(Exception e){
			activeStep.setPassed(false);
			activeStep.setOptionalFailureMessage(e.getMessage());
			activeStep.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(activeStep,driver);
		}
		return getActiveTest();
	}

	@Override
	public GuiTestReportSingleTest validateFieldPresence() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GuiTestReportSingleTest validateFieldWidths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GuiTestReportSingleTest validateFieldDefaultStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GuiTestReportSingleTest validateFieldInputTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GuiTestReportSingleTest validateDefaultValues() {
		// TODO Auto-generated method stub
		return null;
	}

}
