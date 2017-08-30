package com.ilabquality.gtaf.guitests.deal.iss;

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

public class CreateDeal_Generic extends GUITest {
	private GuiTestReportSuite gt;

	public CreateDeal_Generic(GUIController controller){
		super(controller);
	}
	@Override
	public void runTests(GuiTestReportSuite gt) {
		
		this.gt = gt;
		int dd=0;
		try{
			String str = Runner.DATE_FORMAT.print(DateTime.now());
			
			this.baseUrl = Runner.getRuntimeVariables().get("@{SeleniumBaseURL}").get(0).getLiveValue();
			resetEnv(gt);
			driver.manage().window().maximize();
			driver.get(baseUrl);
			LogonTests lt = new LogonTests(controller);
			lt.setMap(getMap());
			lt.setServer(sl, driver, webDriver);
			
			
			DealPOJO d = new DealPOJO();
			d.setProductType("Foreign Outward BGI");
			d.setStep("Issue");
			d.setProductSubType("Bid/Tender Bond");
			d.setCustomer("Jan");
			d.setCustomerKYC(true);
			d.setAppCheckCorrectnessNo(true);
			d.setAppChecksCorrectnessDueDate(str);
			d.setAppChecksCorrectnessNotes("SeleniumAppCorrectness");
			d.setAppChecksAMLNo(true);
			d.setAppChecksAMLDueDate(str);
			d.setAppChecksAMLNotes("SeleniumAML");
			d.setAppCheckCorrectnessNo(true);
			d.setAppChecksIndemnitiesNo(true);
			d.setAppChecksIndemnitiesDueDate(str);
			d.setAppChecksIndemnitiesNotes("SeleniumIndemnities");
			d.setAppChecksJurisdictionNo(true);
			d.setAppChecksSignatureNo(true);
			d.setExpiryType("Open Ended");
			d.setDealAmount(1000000);
			d.setCurrency("USD - United States dollar");
			d.setDealBeneficiary("CitiBank");
			d.setDealCompletedOnProductionYes(true);
			d.setSanctionsCheckCompletedNo(true);
			d.setSanctionsReferenceNumber("SeleniumSanctionsRef");
			d.setDeliveryInfoStandard(true);
			d.setRelevantCharges(true);
			d.setRecoverySchedule(true);
			d.setReviewedOnProduction(true);
			d.setDealProductionReferenceNumber("SeleniumProdRef");
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(createDealGeneric(d));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
		}catch(Exception e){
			gt.addTest(getActiveTest());
			e.printStackTrace();
		}

	}

	public GuiTestReportSingleTest createDealGeneric(DealPOJO deal ){
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Create a deal and Release - Gold Case(" + deal.getProductType()+":" + deal.getProductSubType() +":" + deal.getStep() );
		getActiveTest().setTestName("createDealGeneric");
		System.out.println("createDealGeneric - Gold Case(" + deal.getProductType()+":" + deal.getProductSubType() +":" +  deal.getStep() );
		TestStep activeStep = null;
		try{
			Page landingPage = getPage("LandingPage");
			
			//createTrade(TradeType.EMAIL,productType);
			
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
			ts.setStepName("Select Product - Foreign Outward BGI");
			activeStep = ts;
			new Select(driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.ProductType").getBy())).selectByVisibleText(deal.getProductType());
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Select Issue");
			activeStep = ts;
			new Select(driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.Step").getBy())).selectByVisibleText(deal.getStep());
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Select the Product Sub Type - " + deal.getProductSubType());
			activeStep = ts;
			new Select(driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.ProductSubType").getBy())).selectByVisibleText(deal.getProductSubType());
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
			ts.setStepName("Enter "+deal.getCustomer()+" in the Search Criteria Field");
			activeStep = ts;
			driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.NameSearchField").getBy()).sendKeys(deal.getCustomer());
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
	
			ts= new TestStep();
			ts.setStepName("Select "+deal.getCustomer()+" from the Search Popup");
			activeStep = ts;
			driver.findElement(By.linkText(deal.getCustomer())).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Mark the customer as KYC Yes");
			activeStep = ts;
			new Select(driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.CustomerKYCSelect").getBy())).selectByVisibleText(deal.isCustomerKYC()?"Yes":"No");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.NextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			Page appChecks = getPage("ApplicationChecks");
			
			if ( deal.isAppCheckCorrectnessYes()){
				ts= new TestStep();
				ts.setStepName("Click Yes on the App Check - correctness");
				activeStep = ts;
				driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.CorrectnessYes").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}else{
				if ( deal.isAppCheckCorrectnessNo()){
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
					js.executeScript("document.getElementById('" + appChecks.getPageObjectByID("ApplicationChecks.CorrectnessDueDate").getLocator()+"').removeAttribute('readonly')");
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
					
					ts= new TestStep();
					ts.setStepName("Enter the value [" + deal.getAppChecksCorrectnessDueDate() + "] in the due date field by using the JSExecutor");
					activeStep = ts;
					driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.CorrectnessDueDate").getBy()).sendKeys(deal.getAppChecksCorrectnessDueDate());
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
					
					ts= new TestStep();
					ts.setStepName("Enter the value [" + deal.getAppChecksCorrectnessNotes() + "] in the 'Notes' field by using the JSExecutor");
					activeStep = ts;
					driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.CorrectnessNotes").getBy()).sendKeys(deal.getAppChecksCorrectnessNotes());
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
					
				}
			}
			
			if ( deal.isAppChecksSignatureYes()){
				ts= new TestStep();
				ts.setStepName("Click Yes on the App Check - signature");
				activeStep = ts;
				driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.SignatureYes").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}else{
				if ( deal.isAppCheckCorrectnessNo()){
					ts= new TestStep();
					ts.setStepName("Click No on the App Check - signature");
					activeStep = ts;
					driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.SignatureNo").getBy()).click();
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
				}
			}
			
			if ( deal.isAppChecksIndemnitiesYes()){
				ts= new TestStep();
				ts.setStepName("Click Yes on the App Check - indemnities");
				activeStep = ts;
				driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.IndemnitiesYes").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}else{
				if ( deal.isAppChecksIndemnitiesNo()){
					ts= new TestStep();
					ts.setStepName("Click No on the App Check - indemnities");
					activeStep = ts;
					driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.IndemnitiesNo").getBy()).click();
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
					
					ts= new TestStep();
					ts.setStepName("Enable entry of the due date field by using the JSExecutor");
					activeStep = ts;
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("document.getElementById('" + appChecks.getPageObjectByID("ApplicationChecks.IndemnitiesDueDate").getLocator()+"').removeAttribute('readonly')");
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
					
					ts= new TestStep();
					ts.setStepName("Enter the value [" + deal.getAppChecksIndemnitiesDueDate() + "] in the due date field by using the JSExecutor");
					activeStep = ts;
					driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.IndemnitiesDueDate").getBy()).sendKeys(deal.getAppChecksIndemnitiesDueDate());
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
					
					ts= new TestStep();
					ts.setStepName("Enter the value [" + deal.getAppChecksIndemnitiesNotes() + "] in the 'Notes' field by using the JSExecutor");
					activeStep = ts;
					driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.IndemnitiesNotes").getBy()).sendKeys(deal.getAppChecksIndemnitiesNotes());
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
				}
			}
			
			if ( deal.isAppChecksAMLYes()){
				ts= new TestStep();
				ts.setStepName("Click Yes on the App Check - AML");
				activeStep = ts;
				driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.AMLYes").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
			}else{
				if ( deal.isAppChecksAMLNo()){
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
					js.executeScript("document.getElementById('" + appChecks.getPageObjectByID("ApplicationChecks.AMLDueDate").getLocator()+"').removeAttribute('readonly')");
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
					
					ts= new TestStep();
					ts.setStepName("Enter the value [" + deal.getAppChecksAMLDueDate() + "] in the due date field by using the JSExecutor");
					activeStep = ts;
					driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.AMLDueDate").getBy()).sendKeys( deal.getAppChecksAMLDueDate() );
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
					
					ts= new TestStep();
					ts.setStepName("Enter the value [" + deal.getAppChecksAMLNotes() + "] in the 'Notes' field by using the JSExecutor");
					activeStep = ts;
					driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.AMLNotes").getBy()).sendKeys(deal.getAppChecksAMLNotes());
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
				}
			}
			
			if ( deal.isAppChecksJurisdictionYes()){
				ts= new TestStep();
				ts.setStepName("Click Yes on the App Check - jurisdiction");
				activeStep = ts;
				driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.JurisdictionYes").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}else{
				if ( deal.isAppChecksJurisdictionNo()){
					ts= new TestStep();
					ts.setStepName("Click No on the App Check - jurisdiction");
					activeStep = ts;
					driver.findElement(appChecks.getPageObjectByID("ApplicationChecks.JurisdictionNo").getBy()).click();
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
				}
			}
			
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
			ts.setStepName("Set the expiry type to Open Ended");
			activeStep = ts;
			new Select(driver.findElement(expiryInformation.getPageObjectByID("ExpiryInformation.ExpiryTypeSelect").getBy())).selectByVisibleText(deal.getExpiryType());
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			if ( !deal.getExpiryType().equalsIgnoreCase("Open Ended")){
				if ( deal.getExpiryType().equalsIgnoreCase("Fixed")){
					ts= new TestStep();
					ts.setStepName("Enable entry of the due date field by using the JSExecutor");
					activeStep = ts;
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("document.getElementById('" + expiryInformation.getPageObjectByID("ExpiryInformation.ExpiryDate").getLocator()+"').removeAttribute('readonly')");
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
					
					ts= new TestStep();
					ts.setStepName("Enter the value [" + deal.getExpiryDate() + "] in the due date field by using the JSExecutor");
					activeStep = ts;
					driver.findElement(expiryInformation.getPageObjectByID("ExpiryInformation.ExpiryDate").getBy()).sendKeys(deal.getExpiryDate());
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
				}else{
					System.out.println("*****************************UNSUPPORTED DEAL EXPIRY TYPE - " + deal.getExpiryType());
				}
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
			ts.setStepName("Enter an amount ("+deal.getDealAmount()+") into the amount field.");
			activeStep = ts;
			driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.AmountField").getBy()).sendKeys(deal.getDealAmount()+"");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Select the "+deal.getCurrency()+" currency value");
			activeStep = ts;
			new Select(driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.CurrencyTypeSelect").getBy())).selectByVisibleText(deal.getCurrency());
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			if ( deal.isDealCashCoverYes()){
				ts= new TestStep();
				ts.setStepName("Click the 'Cash Cover' Yes radio button");
				activeStep = ts;
				driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.CashCoverYes").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
			}else{
				if ( deal.isDealCashCoverNo()){
					ts= new TestStep();
					ts.setStepName("Click the 'Cash Cover' No radio button");
					activeStep = ts;
					driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.CashCoverNo").getBy()).click();
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
				}
			}
			
			if ( deal.isDealCreditYes()){
				ts= new TestStep();
				ts.setStepName("Click the 'Credit Required' Yes radio button");
				activeStep = ts;
				driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.CreditYes").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
				ts= new TestStep();
				ts.setStepName("Enable entry of the due date field by using the JSExecutor");
				activeStep = ts;
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("document.getElementById('" + financialInformation.getPageObjectByID("FinancialInformation.CreditDueDate").getLocator()+"').removeAttribute('readonly')");
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
				DateTime dt = new DateTime();
				String str = Runner.DATE_FORMAT.print(dt);
				ts= new TestStep();
				ts.setStepName("Enter tomorrows date into the newly enabled field.");
				activeStep = ts;
				driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.CreditDueDate").getBy()).sendKeys(str);
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
				ts= new TestStep();
				ts.setStepName("Enter a note in the 'Credit Note' field");
				activeStep = ts;
				driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.CreditNoteField").getBy()).sendKeys(deal.getDealCreditNotes());
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
				
			}else{
				if ( deal.isDealCashCoverNo()){
					ts= new TestStep();
					ts.setStepName("Click the 'Credit Required' No radio button");
					activeStep = ts;
					driver.findElement(financialInformation.getPageObjectByID("FinancialInformation.CreditNo").getBy()).click();
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
				}
			}
			
			
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
			ts.setStepName("Enter the value ["+ deal.getDealBeneficiary() +"] to bring up the search list. ");
			activeStep = ts;
			driver.findElement(mainParties.getPageObjectByID("MainParties.BeneficiarySearchField").getBy()).sendKeys(deal.getDealBeneficiary());
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			try {
				Thread.sleep(250);
			} catch (Exception e) {
				
			}
			ts= new TestStep();
			ts.setStepName("Select the '"+deal.getDealBeneficiary()+"' value from the search results.");
			activeStep = ts;
			driver.findElement(By.linkText(deal.getDealBeneficiary())).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			try {
				Thread.sleep(150);
			} catch (Exception e) {
			}
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(mainParties.getPageObjectByID("MainParties.NextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			Page sanctionsPage = getPage("Sanctions");
			
			if ( deal.isSanctionsCheckCompletedYes()){
				ts= new TestStep();
				ts.setStepName("Check the 'Did you complete a sanction check on Production?' Yes radio button");
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
			}else{
				if ( deal.isSanctionsCheckCompletedNo()){
					ts= new TestStep();
					ts.setStepName("Select the 'Did you complete a sanction check on Production?' No radio button");
					activeStep = ts;
					driver.findElement(sanctionsPage.getPageObjectByID("Sanctions.CompletedOnProductionRadioNo").getBy()).click();
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
					
					ts= new TestStep();
					ts.setStepName("Enable entry of the due date field by using the JSExecutor");
					activeStep = ts;
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("document.getElementById('"+ sanctionsPage.getPageObjectByID("Sanctions.DueDateField").getLocator()+"').removeAttribute('readonly')");
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
					
					ts= new TestStep();
					ts.setStepName("Enter the date ["+ deal.getDealSanctionsDueDate()+"] into the 'Due Date' field.");
					activeStep = ts;
					driver.findElement(sanctionsPage.getPageObjectByID("Sanctions.DueDateField").getBy()).sendKeys(deal.getDealSanctionsDueDate());
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
					
				}else{
					System.out.println("********************Unsupported Sanction Type*************************** ");
				}
			
			}
			
			
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
			
			if ( deal.isDeliveryInfoStandard()){
				ts= new TestStep();
				ts.setStepName("Select the 'Standard Wording' Radio Button");
				activeStep = ts;
				Thread.sleep(1000);
				driver.findElement(deliveryInformation.getPageObjectByID("DeliveryInformation.ContractWordingStandard").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}else{
				if ( deal.isDeliveryInfoNonStandard()){
					ts= new TestStep();
					ts.setStepName("Select the 'Non Standard Wording' Radio Button");
					activeStep = ts;
					Thread.sleep(1000);
					driver.findElement(deliveryInformation.getPageObjectByID("DeliveryInformation.ContractWordingNonStandard").getBy()).click();
					ts.setPassed(true);
					getActiveTest().addTestStep(ts,driver);
				}
			}
			
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(deliveryInformation.getPageObjectByID("DeliveryInformation.NextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			try {
				Thread.sleep(awsSleepTime);
			} catch (Exception e) {}
			Page charges = getPage("Charges");
			
			if (deal.isRelevantCharges()){
				ts= new TestStep();
				ts.setStepName("Select the 'Relevant charges' checkbox");
				activeStep = ts;
				driver.findElement(charges.getPageObjectByID("Charges.RelevantChargesCheckBox").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}
			
			
			if ( deal.isRecoverySchedule()){
				ts= new TestStep();
				ts.setStepName("Select the 'Recovery schedule ' checkbox");
				activeStep = ts;
				driver.findElement(charges.getPageObjectByID("Charges.RecoveryScheduleCheckBox").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}
			
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
			
			if ( deal.isReviewedOnProduction()){
				ts= new TestStep();
				ts.setStepName("Select the 'Did you review transaction on Production?' checkbox");
				activeStep = ts;
				driver.findElement(reviewPage.getPageObjectByID("Review.ReviewTransactionOnProductionCheckBox").getBy()).click();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}

			ts= new TestStep();
			ts.setStepName("Clear the 'Production Reference Number' text Field");
			activeStep = ts;
			driver.findElement(reviewPage.getPageObjectByID("Review.ProductionReferenceNumberField").getBy()).clear();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Enter the value '" + deal.getDealProductionReferenceNumber() + "' into the 'Production Reference Number' text field.");
			activeStep = ts;
			driver.findElement(reviewPage.getPageObjectByID("Review.ProductionReferenceNumberField").getBy()).sendKeys(deal.getDealProductionReferenceNumber());
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(reviewPage.getPageObjectByID("Review.ProceedButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
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
