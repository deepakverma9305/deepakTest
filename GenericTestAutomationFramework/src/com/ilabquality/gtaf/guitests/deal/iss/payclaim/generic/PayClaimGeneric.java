package com.ilabquality.gtaf.guitests.deal.iss.payclaim.generic;

import java.util.UUID;

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
import com.ilabquality.gtaf.guitests.helpers.PageObjectNotFoundException;
import com.ilabquality.gtaf.guitests.helpers.TestStep;
import com.ilabquality.gtaf.guitests.logon.LogonTests;

public class PayClaimGeneric extends GUITest {

	private GuiTestReportSuite gt;

	public PayClaimGeneric(GUIController controller) {
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
			
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction aa = 
					new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction(controller);
			aa.setMap(getMap());
			aa.setServer(sl, driver, webDriver);
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.noEscalationsRelease("Import LC","Issue","Import LC"));
			gt.addTest(lt.logoutBasic());
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(doDocEx("Import LC","Document Examination","Import LC",aa.getProdRef()));
			gt.addTest(lt.logoutBasic());
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(doPayClaim("Import LC","Pay/Accept","Import LC",aa.getProdRef()));
			gt.addTest(lt.logoutBasic());
			
		}catch(Exception e){
			gt.addTest(getActiveTest());
			e.printStackTrace();
		}
		
	}
	
	public GuiTestReportSingleTest doPayClaim(String productType, String step,String productSubType, String prodRef){
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Perform a Pay/Claim on a deal and Release - Gold Case(" + productType+":" + productSubType +":" + step + " - doPayClaim)" );
		System.out.println("Pay/Claim - doPayClaim - Gold Case(" + productType+":" + productSubType +":" + step+")");
		getActiveTest().setTestName("Pay/Claim " + productType);
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
			ts.setStepName("Enter the reference["+ prodRef + " - " + productSubType + " - DEX 001" +"] for the deal which was just created. - " + productSubType);
			activeStep = ts;
			driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.AmendReferenceSearchField").getBy()).sendKeys(prodRef);
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Select the result from the list.");
			activeStep = ts;
			driver.findElement(By.linkText(prodRef + " - " + productSubType + " - DEX 001" )).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
		
			
			//TODO Validate the table
			// end table validations.
			
			//TODO Validate the state of this button as per the STATUS column value in the result table. 
			
			ts= new TestStep();
			ts.setStepName("Validate the 'Next' button is enabled.");
			activeStep = ts;
			boolean isEnabled = driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.PayClaimNextButton").getBy()).isEnabled();
			if  (isEnabled){
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
			}else{
				ts.setPassed(false);
				ts.setOptionalFailureMessage("The 'Next' button is expected to be enabled, it is disabled");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
				
			}
			
			if ( !ts.hasPassed()){
				//Failed on Next, so cannot continue;
				return getActiveTest();
			}
		
			
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.PayClaimNextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			Page customerSearch = getPage("CustomerSearch");
//TODO Validate the customer is displayed as per the create.
// Endd Validation
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
			
			ts= new TestStep();
			ts.setStepName("Click Yes on the App Check - signature");
			activeStep = ts;
			driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.SignatureYes").getBy()).click();
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
				ts= new TestStep();
				ts.setStepName("Validate the value 'Sight' in the Expiry Type Select");
				activeStep = ts;
				String val = new Select(driver.findElement(expiryInformation.getPageObjectByID("ExpiryInformation.ExpiryTypeSelect").getBy())).getFirstSelectedOption().getText();
				if (val.equalsIgnoreCase("Sight")){
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
				}else{
					ts.setPassed(false);
					ts.setOptionalFailureMessage("Expected 'Sight' in Expiry Type. Value is " + val);
					ts.setTestStepImage(takeScreenshot(driver));
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
			ts.setStepName("Validate the deal value of 1,000 in the amount field. ");
			activeStep = ts;
			val  = driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.AmountField").getBy()).getAttribute("value");
			if ( val.equalsIgnoreCase("1,000.00")){
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				ts.setOptionalFailureMessage("Correct value found in the amount field.");
			}else{
				ts.setPassed(false);
				ts.setOptionalFailureMessage("The value in the amount field is incorrect. Expected[1,000.00] actual ["+val+"]");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}
			
			ts= new TestStep();
			ts.setStepName("Validate the deal currency of 'USD - United States dollar' in the currency field. ");
			activeStep = ts;
			val  = new Select(driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.CurrencyTypeSelect").getBy())).getFirstSelectedOption().getText();
			if ( val.equalsIgnoreCase("USD - United States dollar")){
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				ts.setOptionalFailureMessage("Correct value found in the currency field.");
			}else{
				ts.setPassed(false);
				ts.setOptionalFailureMessage("The value in the currency field is incorrect. Expected[USD - United States dollar] actual ["+val+"]");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}
			
			ts= new TestStep();
			ts.setStepName("Enter '10.01' into the 'Amount' Field.");
			activeStep = ts;
			driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.PaymentAmountField").getBy()).sendKeys("10.01");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click Proceed");
			activeStep = ts;
			driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.ProceedButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			Page beneficiaryPage = getPage("BeneficiaryDetails");
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(beneficiaryPage.getPageObjectByID("BeneficiaryDetails.NextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			
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
			
			
			val = UUID.randomUUID().toString().substring(0,6).replace("-", "");
			ts= new TestStep();
			ts.setStepName("Enter the value 'Selenium " + val + "' into the text field.");
			activeStep = ts;
			driver.findElement(sanctionsPage.getPageObjectByID("Sanctions.ProductionReferenceNumberField").getBy()).sendKeys("Selenium " + val );
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			
			ts= new TestStep();
			ts.setStepName("Click Proceed");
			activeStep = ts;
			driver.findElement(sanctionsPage.getPageObjectByID("Sanctions.ProceedButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			sleep(2);
			Page charges = getPage("Charges");
			ts= new TestStep();
			ts.setStepName("Select the 'Relevant charges' checkbox");
			activeStep = ts;
			driver.findElement(charges.getPageObjectByID("Charges.RelevantChargesCheckBox").getBy()).click();
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
			
			sleep(1);
			
			ts= new TestStep();
			ts.setStepName("Validate the number in the 'Sequence Number' text Field, Should be '001'");
			activeStep = ts;
			val = driver.findElement(reviewPage.getPageObjectByID("Review.ProductionReferenceSequenceField").getBy()).getAttribute("value");
			if ( val.equalsIgnoreCase("001")){
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}else{
				ts.setPassed(false);
				ts.setOptionalFailureMessage("The value in the 'Sequence Number' field is incorrect. Expected [001] actual [" + val + "]");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}
			
			ts= new TestStep();
			ts.setStepName("Click Proceed");
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
			ts.setStepName("Validate the value in the 'Production Reference Number' field. ");
			activeStep = ts;
			val = driver.findElement(dealRelease.getPageObjectByID("DealRelease.ProductionReferenceNumberField").getBy()).getAttribute("value");
			
			if (val.equalsIgnoreCase(prodRef)) {
				ts.setPassed(true);
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
				
			}else{
				ts.setPassed(false);
				ts.setTestStepImage(takeScreenshot(driver));
				ts.setOptionalFailureMessage("The incorrect value was detected in the 'Production Reference Number' field. Expected was["+prodRef+"] actual was ["+val+"]");
				getActiveTest().addTestStep(ts,driver);
			}
			
			ts= new TestStep();
			ts.setStepName("Validate the value in the 'Step' field. ");
			activeStep = ts;
			val = driver.findElement(dealRelease.getPageObjectByID("DealRelease.StepCodeField").getBy()).getAttribute("value");
			
			if (val.equalsIgnoreCase("PAY")) {
				ts.setPassed(true);
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
				
			}else{
				ts.setPassed(false);
				ts.setTestStepImage(takeScreenshot(driver));
				ts.setOptionalFailureMessage("The incorrect value was detected in the 'Production Reference Number' field. Expected was[PAY] actual was ["+val+"]");
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
			((JavascriptExecutor) driver).executeScript("scroll(0,300)");
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

		}
		catch(PageObjectNotFoundException pnfe){
			activeStep.setPassed(false);
			activeStep.setOptionalFailureMessage(pnfe.getMessage());
			activeStep.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(activeStep,driver);
			return getActiveTest();
			
			
		}catch(Exception e){
			activeStep.setPassed(false);
			activeStep.setOptionalFailureMessage(e.getMessage());
			activeStep.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(activeStep,driver);
		}
		return getActiveTest();
	}
	
	public GuiTestReportSingleTest doDocEx(String productType, String step,String productSubType, String prodRef){
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Perform a Doc Ex on a deal and Release - Gold Case(" + productType+":" + productSubType +":" + step + " - doDocEx)" );
		System.out.println("Doc Ex - doDocEx - Gold Case(" + productType+":" + productSubType +":" + step+")");
		getActiveTest().setTestName("DocEx " + productType);
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
			ts.setStepName("Validate the 'Next' button is enabled.");
			activeStep = ts;
			boolean isEnabled = driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.DocExNextButton").getBy()).isEnabled();
			if  (isEnabled){
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
			}else{
				ts.setPassed(false);
				ts.setOptionalFailureMessage("The 'Next' button is expected to be enabled, it is disabled");
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
			driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.DocExNextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			Page customerSearch = getPage("CustomerSearch");
//TODO Validate the customer is displayed as per the create.
// Endd Validation
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
			
			ts= new TestStep();
			ts.setStepName("Click Yes on the App Check - signature");
			activeStep = ts;
			driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.SignatureYes").getBy()).click();
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
			
			ts= new TestStep();
			ts.setStepName("Validate the value 'Sight' in the Expiry Type Select");
			activeStep = ts;
			new Select(driver.findElement(expiryInformation.getPageObjectByID("ExpiryInformation.ExpiryTypeSelect").getBy())).selectByVisibleText("Sight");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
			
			
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(expiryInformation.getPageObjectByID("ExpiryInformation.NextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			Page financialInformation = getPage("FinancialInformation");
			ts= new TestStep();
			ts.setStepName("Validate the deal value of 1,000 in the amount field. ");
			activeStep = ts;
			String val  = driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.AmountField").getBy()).getAttribute("value");
			if ( val.equalsIgnoreCase("1,000.00")){
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				ts.setOptionalFailureMessage("Correct value found in the amount field.");
			}else{
				ts.setPassed(false);
				ts.setOptionalFailureMessage("The value in the amount field is incorrect. Expected[1,000.00] actual ["+val+"]");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}
			
			ts= new TestStep();
			ts.setStepName("Validate the deal currency of 'USD - United States dollar' in the currency field. ");
			activeStep = ts;
			val  = new Select(driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.CurrencyTypeSelect").getBy())).getFirstSelectedOption().getText();
			if ( val.equalsIgnoreCase("USD - United States dollar")){
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				ts.setOptionalFailureMessage("Correct value found in the currency field.");
			}else{
				ts.setPassed(false);
				ts.setOptionalFailureMessage("The value in the currency field is incorrect. Expected[USD - United States dollar] actual ["+val+"]");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}
			
			
			
			ts= new TestStep();
			ts.setStepName("Click Proceed");
			activeStep = ts;
			driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.ProceedButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
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
			
			
			val = UUID.randomUUID().toString().substring(0,6).replace("-", "");
			ts= new TestStep();
			ts.setStepName("Enter the value 'Selenium " + val + "' into the text field.");
			activeStep = ts;
			driver.findElement(sanctionsPage.getPageObjectByID("Sanctions.ProductionReferenceNumberField").getBy()).sendKeys("Selenium " + val );
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			
			ts= new TestStep();
			ts.setStepName("Click Proceed");
			activeStep = ts;
			driver.findElement(sanctionsPage.getPageObjectByID("Sanctions.ProceedButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			sleep(2);
			Page charges = getPage("Charges");
			ts= new TestStep();
			ts.setStepName("Select the 'Relevant charges' checkbox");
			activeStep = ts;
			driver.findElement(charges.getPageObjectByID("Charges.RelevantChargesCheckBox").getBy()).click();
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
			
			sleep(1);
			
			ts= new TestStep();
			ts.setStepName("Clear the 'Sequence Number' text Field");
			activeStep = ts;
			driver.findElement(reviewPage.getPageObjectByID("Review.ProductionReferenceSequenceField").getBy()).clear();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			ts= new TestStep();
			ts.setStepName("Enter the value '001' into the 'Sequence Number' text field.");
			activeStep = ts;
			driver.findElement(reviewPage.getPageObjectByID("Review.ProductionReferenceSequenceField").getBy()).sendKeys("001");
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
			ts.setStepName("Validate the value in the 'Production Reference Number' field. ");
			activeStep = ts;
			val = driver.findElement(dealRelease.getPageObjectByID("DealRelease.ProductionReferenceNumberField").getBy()).getAttribute("value");
			
			if (val.equalsIgnoreCase(prodRef)) {
				ts.setPassed(true);
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
				
			}else{
				ts.setPassed(false);
				ts.setTestStepImage(takeScreenshot(driver));
				ts.setOptionalFailureMessage("The incorrect value was detected in the 'Production Reference Number' field. Expected was["+prodRef+"] actual was ["+val+"]");
				getActiveTest().addTestStep(ts,driver);
			}
			
			ts= new TestStep();
			ts.setStepName("Validate the value in the 'Step' field. ");
			activeStep = ts;
			val = driver.findElement(dealRelease.getPageObjectByID("DealRelease.StepCodeField").getBy()).getAttribute("value");
			
			if (val.equalsIgnoreCase("DEX")) {
				ts.setPassed(true);
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
				
			}else{
				ts.setPassed(false);
				ts.setTestStepImage(takeScreenshot(driver));
				ts.setOptionalFailureMessage("The incorrect value was detected in the 'Production Reference Number' field. Expected was[DEX] actual was ["+val+"]");
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
			((JavascriptExecutor) driver).executeScript("scroll(0,300)");
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

		}
		catch(PageObjectNotFoundException pnfe){
			activeStep.setPassed(false);
			activeStep.setOptionalFailureMessage(pnfe.getMessage());
			activeStep.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(activeStep,driver);
			return getActiveTest();
			
			
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
