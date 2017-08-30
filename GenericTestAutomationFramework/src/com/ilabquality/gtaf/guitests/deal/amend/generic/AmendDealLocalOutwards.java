package com.ilabquality.gtaf.guitests.deal.amend.generic;

import java.util.UUID;

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
import com.ilabquality.gtaf.guitests.helpers.PageObjectNotFoundException;
import com.ilabquality.gtaf.guitests.helpers.TestStep;
import com.ilabquality.gtaf.guitests.logon.LogonTests;

public class AmendDealLocalOutwards extends GUITest{

	private GuiTestReportSuite gt;

	public AmendDealLocalOutwards(GUIController controller) {
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
			
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction aa = 
					new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction(controller);
			aa.setMap(getMap());
			aa.setServer(sl, driver, webDriver);
			try{
				gt.addTest(lt.logonBasic("john","john"));
				gt.addTest(aa.noEscalationsRelease("Local Outward BGI","Issue","Bid/Tender Bond"));
				gt.addTest(lt.logoutBasic());
				
				gt.addTest(lt.logonBasic("john","john"));
				gt.addTest(amendDealReleaseInProduction("Local Outward BGI","Amend","Bid/Tender Bond",aa.getProdRef()));
				gt.addTest(lt.logoutBasic());
			}catch(Exception e){
				e.printStackTrace();
			}
			//resetEnv(gt);
		}catch(Exception e){
			gt.addTest(getActiveTest());
			e.printStackTrace();
		}
	}

	public GuiTestReportSingleTest amendDealReleaseInProduction(String productType,String step, String productSubType,  String prodRef) {
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Amend a deal and Release - Gold Case(" + productType+":" + productSubType +":" + step + " - AmendDealLocalOutwards)" );
		System.out.println("Amend - amendDeal - Gold Case(" + productType+":" + productSubType +":" + step+")");
		getActiveTest().setTestName("Amend LOGBI");
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
			boolean isEnabled = driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.AmendNextButton").getBy()).isEnabled();
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
			driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.AmendNextButton").getBy()).click();
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
			
			if ( !productType.equalsIgnoreCase("Inward BGI") && !productType.equalsIgnoreCase("Export LC")){
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
			}
			
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
				ts.setStepName("Validate the value 'Open Ended' in the Expiry Type Select");
				activeStep = ts;
				String val = new Select(driver.findElement(expiryInformation.getPageObjectByID("ExpiryInformation.ExpiryTypeSelect").getBy())).getFirstSelectedOption().getText();
				if (val.equalsIgnoreCase("Open Ended")){
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
				}else{
					ts.setPassed(false);
					ts.setOptionalFailureMessage("Expected 'Open Ended' in Expiry Type. Value is " + val);
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
			ts.setStepName("Select the value 'Increase' from the 'Adjustment Type' select.");
			activeStep = ts;
			new Select(driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.AdjustmentTypeSelect").getBy())).selectByVisibleText("Increase");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			ts= new TestStep();
			ts.setStepName("Enter an amount (1000) into the 'Adjustment Amount' field.");
			activeStep = ts;
			driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.AdjustmentAmountField").getBy()).sendKeys("1000");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			ts= new TestStep();
			ts.setStepName("Validate the 'USD' currency is correctly pulled through from the original deal.");
			activeStep = ts;
			val = new Select (driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.CurrencyTypeSelect").getBy())).getFirstSelectedOption().getText();
			if ( val.equalsIgnoreCase("USD - United States dollar")){
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}else{
				ts.setPassed(false);
				ts.setOptionalFailureMessage("Currency is incorrect. Expected[USD - United States dollar], actual["+val+"]");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}
			
			ts= new TestStep();
			ts.setStepName("Click Proceed");
			activeStep = ts;
			driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.ProceedButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			Page mainParties = getPage("MainParties");
			ts= new TestStep();
			ts.setStepName("Validate the selected beneficiary is the same as the one used to create.");
			activeStep = ts;
			val = driver.findElement(mainParties.getPageObjectByID("MainParties.SelectedBeneficiaryField").getBy()).getText();
			if ( val.equalsIgnoreCase("CitiBank")){
				ts.setPassed(true);
				ts.setOptionalFailureMessage("Beneficiary correctly validated");
				getActiveTest().addTestStep(ts,driver);
			}else{
				ts.setPassed(false);
				ts.setOptionalFailureMessage("Beneficiary is incorrect. Expected ['CitiBank'], actual [" + val +"]");
				getActiveTest().addTestStep(ts,driver);
			}
		
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(mainParties.getPageObjectByID("MainParties.NextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			sleep(1000);
			
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
			Page deliveryInformation = getPage("DeliveryInformation");
			if ( !productType.equalsIgnoreCase("Import LC") && !productType.equalsIgnoreCase("Inward BGI")
					&& !productType.equalsIgnoreCase("Export LC")){
				ts= new TestStep();
				ts.setStepName("Confirm the 'Standard Wording' Radio is selected as per the Create.");
				activeStep = ts;
				
				boolean isSelected = driver.findElement(deliveryInformation.getPageObjectByID("DeliveryInformation.ContractWordingStandard").getBy()).isSelected();
				if ( isSelected ){
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
				}else{
					ts.setPassed(false);
					ts.setOptionalFailureMessage("Expected 'Standard Wording' to be selected, it was not");
					ts.setTestStepImage(takeScreenshot(driver));
					getActiveTest().addTestStep(ts,driver);
				}
				
				ts= new TestStep();
				ts.setStepName("Click Next");
				activeStep = ts;
				driver.findElement(deliveryInformation.getPageObjectByID("DeliveryInformation.NextButton").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}
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
			
			sleep(1);
			
			ts= new TestStep();
			ts.setStepName("Clear the 'Sequence Number' text Field");
			activeStep = ts;
			driver.findElement(reviewPage.getPageObjectByID("Review.ProductionReferenceSequenceField").getBy()).clear();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			ts= new TestStep();
			ts.setStepName("Enter the value '2' into the 'Sequence Number' text field.");
			activeStep = ts;
			driver.findElement(reviewPage.getPageObjectByID("Review.ProductionReferenceSequenceField").getBy()).sendKeys("2");
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
		}
		catch(Exception e){
			activeStep.setPassed(false);
			activeStep.setOptionalFailureMessage(e.getMessage());
			activeStep.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(activeStep,driver);
			return getActiveTest();
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
