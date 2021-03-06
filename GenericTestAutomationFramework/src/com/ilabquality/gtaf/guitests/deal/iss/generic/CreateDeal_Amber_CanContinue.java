package com.ilabquality.gtaf.guitests.deal.iss.generic;

import org.joda.time.DateTime;
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

public class CreateDeal_Amber_CanContinue extends GUITest{

	public CreateDeal_Amber_CanContinue(GUIController controller){
		super(controller);
	}
	private GuiTestReportSuite gt;

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
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Payment Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Performance Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Advance Payment Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Labour Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Shipping Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Maintenance Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Customs Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Retention Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Immigration Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Security Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Financial Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Trade Licence Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Avalisation"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Court Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Lease Guarantee (villa IRS)"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Rental Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Credit Facilities"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Study Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Preference Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Retainage Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Airline Agent Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			
		}catch(Exception e){
			gt.addTest(getActiveTest());
			e.printStackTrace();
		}
		
	}
	public GuiTestReportSingleTest amberEscalationMarkAsYesOnReleaseAndRelease(String productType, String step, String productSubType) throws InterruptedException{
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Create a deal and Release - Gold Case(" + productType+":" + productSubType +":" + step +" - CreateDeal_Amber_CanContinue)");
		System.out.println("amberEscalationMarkAsUnsuccesful - Gold Case(" + productType+":" + productSubType +":" + step+")");
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
			
			Page appChecks = getPage("ApplicationChecks");
			ts= new TestStep();
			ts.setStepName("Click Yes on the App Check - correctness");
			activeStep = ts;
			driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.CorrectnessYes").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			//Escalation creation by selecting AMBER.
			ts= new TestStep();
			ts.setStepName("Click on the App Check - signature - No");
			activeStep = ts;
			driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.SignatureNo").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click Yes on the App Check - indemnities");
			activeStep = ts;
			driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.IndemnitiesYes").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click Yes on the App Check - AML");
			activeStep = ts;
			driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.AMLYes").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click Yes on the App Check - jurisdiction");
			activeStep = ts;
			driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.JurisdictionYes").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click Proceed");
			activeStep = ts;
			driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.ProceedButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			Page dealInformation = getPage("DealInformation");
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(dealInformation.getPageObjectByID("DealInformation.NextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			Page expiryInformation = getPage("ExpiryInformation");
			if ( productType.equalsIgnoreCase("Import LC") || productType.equalsIgnoreCase("Export LC")){
				ts= new TestStep();
				ts.setStepName("Set the expiry type to 'Fixed'");
				activeStep = ts;
				new Select(driver.findElement(expiryInformation.getPageObjectByID("ExpiryInformation.ExpiryTypeSelect").getBy())).selectByVisibleText("Fixed");
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
				ts= new TestStep();
				ts.setStepName("Enable entry of the due date field by using the JSExecutor");
				activeStep = ts;
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("document.getElementById('"+ expiryInformation.getPageObjectByID("ExpiryInformation.ExpiryDate").getLocator()+"').removeAttribute('readonly')");
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
				DateTime dt = DateTime.now().plusDays(1);
				String str = Runner.DATE_FORMAT.print(dt);
				 
				ts= new TestStep();
				ts.setStepName("Enter tomorrow as the Due Date");
				activeStep = ts;
				driver.findElement(expiryInformation.getPageObjectByID("ExpiryInformation.ExpiryDate").getBy()).sendKeys(str);
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
			}else{
				ts= new TestStep();
				ts.setStepName("Set the expiry type to Open Ended");
				activeStep = ts;
				new Select(driver.findElement(expiryInformation.getPageObjectByID("ExpiryInformation.ExpiryTypeSelect").getBy())).selectByVisibleText("Open Ended");
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}
				
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(expiryInformation.getPageObjectByID("ExpiryInformation.NextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			Page financialInformation = getPage("FinancialInformation");
			ts= new TestStep();
			ts.setStepName("Clear the amount field");
			activeStep = ts;
			driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.AmountField").getBy()).clear();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Enter an amount (1000) into the amount field.");
			activeStep = ts;
			driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.AmountField").getBy()).sendKeys("1,000");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Select the USD currency value");
			activeStep = ts;
			new Select(driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.CurrencyTypeSelect").getBy())).selectByVisibleText("USD - United States dollar");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.ProceedButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			Page mainParties = getPage("MainParties");
			ts= new TestStep();
			ts.setStepName("Clear the search field.");
			activeStep = ts;
			driver.findElement(mainParties.getPageObjectByID("MainParties.BeneficiarySearchField").getBy()).clear();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Enter the value 'a' to bring up the search list. ");
			activeStep = ts;
			driver.findElement(mainParties.getPageObjectByID("MainParties.BeneficiarySearchField").getBy()).sendKeys("a");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Select the 'CitiBank' value from the search results.");
			activeStep = ts;
			driver.findElement(mainParties.getPageObjectByID("MainParties.BeneficiarySearchResult").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(mainParties.getPageObjectByID("MainParties.NextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			sleep(2);
			
			Page sanctionsPage = getPage("Sanctions");
			ts= new TestStep();
			ts.setStepName("Select the 'Did you complete a sanction check on Production?' Yes radio button");
			activeStep = ts;
			driver.findElement(sanctionsPage.getPageObjectByID("Sanctions.CompletedOnProductionRadioYes").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Clear the text field.");
			activeStep = ts;
			driver.findElement(sanctionsPage.getPageObjectByID("Sanctions.ProductionReferenceNumberField").getBy()).clear();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Enter the value 'Selenium' into the text field.");
			activeStep = ts;
			driver.findElement(sanctionsPage.getPageObjectByID("Sanctions.ProductionReferenceNumberField").getBy()).sendKeys("Selenium");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(sanctionsPage.getPageObjectByID("Sanctions.ProceedButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			try {
				Thread.sleep(awsSleepTime);
			} catch (Exception e) {
				
			}
			Page deliveryInformation = getPage("DeliveryInformation");
			if (! productType.equalsIgnoreCase("Import LC") && !productType.equalsIgnoreCase("Export LC")){
				ts= new TestStep();
				ts.setStepName("Select the Standard Wording Radio Button");
				activeStep = ts;
				Thread.sleep(1000);
				driver.findElement(deliveryInformation.getPageObjectByID("DeliveryInformation.ContractWordingStandard").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
				ts= new TestStep();
				ts.setStepName("Click Next");
				activeStep = ts;
				driver.findElement(deliveryInformation.getPageObjectByID("DeliveryInformation.NextButton").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}
			
			
			try {
				Thread.sleep(awsSleepTime);
			} catch (Exception e) {}
			Page charges = getPage("Charges");
			ts= new TestStep();
			ts.setStepName("Select the 'Relevant charges' checkbox");
			activeStep = ts;
			driver.findElement(charges.getPageObjectByID("Charges.RelevantChargesCheckBox").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Select the 'Recovery schedule ' checkbox");
			activeStep = ts;
			driver.findElement(charges.getPageObjectByID("Charges.RecoveryScheduleCheckBox").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click Proceed");
			activeStep = ts;
			driver.findElement(charges.getPageObjectByID("Charges.ProceedButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
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
			
			ts= new TestStep();
			ts.setStepName("Clear the 'Production Reference Number' text Field");
			activeStep = ts;
			driver.findElement(reviewPage.getPageObjectByID("Review.ProductionReferenceNumberField").getBy()).clear();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Enter the value 'Selenium2' into the 'Production Reference Number' text field.");
			activeStep = ts;
			driver.findElement(reviewPage.getPageObjectByID("Review.ProductionReferenceNumberField").getBy()).sendKeys("Selenium2");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(reviewPage.getPageObjectByID("Review.ProceedButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			GuiTestReportSingleTest at = getActiveTest();
			at.addTestSteps(new LogonTests(this.controller, getMap(), sl, driver, webDriver).logoutBasic());
			at.addTestSteps(new LogonTests(this.controller, getMap(), sl, driver, webDriver).logonBasic("jack", "jack"));
			setActiveTest(at);
			ts= new TestStep();
			ts.setStepName("Claim the task");
			activeStep = ts;
			driver.findElement(By.id("tl_release_" +caseID +"_claimTaskBtn")).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			Page dealRelease = getPage("DealRelease");
			ts= new TestStep();
			ts.setStepName("Click the 'Release in Production' radio button");
			activeStep = ts;
			driver.findElement(dealRelease.getPageObjectByID("DealRelease.ReleaseInProductionRadioButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click the 'Signature - Yes' radio button (AppChecks) - Deal was captured with Signature - No.");
			activeStep = ts;
			driver.findElement(dealRelease.getPageObjectByID("DealRelease.SignatureYes").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Focus on the 'Proceed' button.");
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
			ts.setStepName("Click the 'Proceed' button and release.");
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
					getActiveTest().addTestStep(ts,driver);
				}else{
					ts.setPassed(false);
					ts.setOptionalFailureMessage("Expected : Channel Updated, Actual : " + s);
					ts.setTestStepImage(takeScreenshot(driver));
					getActiveTest().addTestStep(ts,driver);
				}
			}
			
			 at = getActiveTest();
			at.addTestSteps(new LogonTests(this.controller, getMap(), sl, driver, webDriver).logoutBasic());
			at.addTestSteps(new LogonTests(this.controller, getMap(), sl, driver, webDriver).logonBasic("jack", "jack"));
			setActiveTest(at);
	
			try {
				Thread.sleep(1500);
			} catch (Exception e) {
				
			}
			
			ts= new TestStep();
			ts.setStepName("Open the 'New' Queue");
			activeStep = ts;
			driver.findElement(landingPage.getPageObjectByID("LandingPage.NewQueue").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Open the 'Completed' Queue");
			activeStep = ts;
			driver.findElement(landingPage.getPageObjectByID("LandingPage.CompleteQueue").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			if ( waitForExistance(landingPage.getPageObjectByID("LandingPage.ViewDetailButton").getBy())){
				ts.setPassed(true);
				ts.setTestStepImage(takeScreenshot(driver));
				ts.setOptionalFailureMessage(" - Item is in the completed queue");
				getActiveTest().addTestStep(ts,driver);
			}else{
				ts.setPassed(false);
				ts.setTestStepImage(takeScreenshot(driver));
				ts.setOptionalFailureMessage(" - Item is NOT in the completed queue");
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
