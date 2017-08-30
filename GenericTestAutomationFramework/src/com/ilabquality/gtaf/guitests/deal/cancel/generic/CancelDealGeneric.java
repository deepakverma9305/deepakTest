package com.ilabquality.gtaf.guitests.deal.cancel.generic;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
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

public class CancelDealGeneric extends GUITest {

	private GuiTestReportSuite gt;

	public CancelDealGeneric(GUIController controller) {
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

	public void doAction(LogonTests lt, String product, String prodSubType) {
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
			gt.addTest(cancelDealLocalOutwards(product,"Cancel",prodSubType,aa.getProdRef()));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
		} catch (InterruptedException e) {
			gt.addTest(getActiveTest());
			e.printStackTrace();
		}
	}
	public GuiTestReportSingleTest cancelDealLocalOutwards(String productType, String step,String productSubType, String prodRef){
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Cancel a deal and Release - Gold Case(" + productType+":" + productSubType +":" + step + " - cancelDealLocalOutwards)" );
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
			ts.setStepName("Click Yes on the App Check - jurisdiction");
			activeStep = ts;
			driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.JurisdictionYes").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			sleep(500);
			ts= new TestStep();
			ts.setStepName("Click Proceed on the Application Checks Page");
			activeStep = ts;
			driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.ProceedButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
sleep(500);
			Page charges = getPage("Charges");
			ts= new TestStep();
			ts.setStepName("Click Proceed on the Charges Page");
			activeStep = ts;
			driver.findElement(charges.getPageObjectByID("Charges.ProceedButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			sleep(500);
			try {
				Thread.sleep(awsSleepTime);
			} catch (Exception e) {}
			Page reviewPage = getPage("Review");
			ts= new TestStep();
			ts.setStepName("Select the 'Did you review transaction on Production?' checkbox");
			activeStep = ts;
			driver.findElement(reviewPage.getPageObjectByID("Review.ReviewTransactionOnProductionCheckBox").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			sleep(1);
			
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(reviewPage.getPageObjectByID("Review.ProceedButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			GuiTestReportSingleTest at = getActiveTest();
			at.addTestSteps( new LogonTests(this.controller, getMap(), sl, driver, webDriver).logoutBasic());
			at.addTestSteps( new LogonTests(this.controller, getMap(), sl, driver, webDriver).logonBasic("jack", "jack"));
			setActiveTest(at);
			sleep(500);
			ts= new TestStep();
			ts.setStepName("Claim the task");
			activeStep = ts;
			driver.findElement(By.id("tl_release_" +caseID +"_claimTaskBtn")).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			Page dealRelease = getPage("DealRelease");
			
			ts= new TestStep();
			ts.setStepName("Validate the value ["+prodRef+"] exists in the 'Production Reference Number' field.");
			activeStep = ts;
			val = driver.findElement(dealRelease.getPageObjectByID("DealRelease.ProductionReferenceNumberField").getBy()).getAttribute("value");
			if ( val.equalsIgnoreCase(prodRef)){
				ts.setPassed(true);
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}else{
				ts.setPassed(false);
				ts.setOptionalFailureMessage("The value in the 'Production Reference Number' field["+val+"]+ does not tie up with the expected value["+prodRef+"]");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}
		
			ts= new TestStep();
			ts.setStepName("Validate the value [CAN] exists in the 'Step' field.");
			activeStep = ts;
			val = driver.findElement(dealRelease.getPageObjectByID("DealRelease.StepCodeField").getBy()).getAttribute("value");;
			if ( val.equalsIgnoreCase("CAN")){
				ts.setPassed(true);
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}else{
				ts.setPassed(false);
				ts.setOptionalFailureMessage("The value in the 'Step' field["+val+"]+ does not tie up with the expected value[CAN]");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}
		
			
			
			
			ts= new TestStep();
			ts.setStepName("Click the 'Release In Production' radio button");
			activeStep = ts;
			driver.findElement(dealRelease.getPageObjectByID("DealRelease.ReleaseInProductionRadioButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Focus on the Proceed button.");
			activeStep = ts;
			 new Actions(driver).moveToElement(driver.findElement(dealRelease.getPageObjectByID("DealRelease.ProceedButton").getBy())).perform();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Scroll down the page.");
			activeStep = ts;
			((JavascriptExecutor) driver).executeScript("scroll(0,450)");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click the Proceed button.");
			activeStep = ts;
			driver.findElement(dealRelease.getPageObjectByID("DealRelease.ProceedButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);

			ts= new TestStep();
			ts.setStepName("Wait for the Result of the POST - Expected result is 'Channel Updated'");
			activeStep = ts;
			if  (waitForExistance(By.cssSelector("h3.ng-binding.ng-scope"))){
				String s = driver.findElement(By.cssSelector("h3.ng-binding.ng-scope")).getText();
				if (  s.equalsIgnoreCase("Channel Updated")){
					ts.setPassed(true);
					ts.setTestStepImage(takeScreenshot(driver));
					System.out.println("Took Screenshot");
				}else{
					ts.setPassed(false);
					ts.setOptionalFailureMessage("Expected : Channel Updated, Actual : " + s);
					ts.setTestStepImage(takeScreenshot(driver));
				}
			}
			getActiveTest().addTestStep(ts,driver);
			
			
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
