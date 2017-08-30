package com.ilabquality.gtaf.guitests.customer;

import org.openqa.selenium.By;
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

public class CustomerTests extends GUITest {
	String baseUrl="";
	public CustomerTests(GUIController controller){
		super(controller);
	}
	public void runTests(GuiTestReportSuite gt) {
		this.baseUrl = Runner.getRuntimeVariables().get("@{SeleniumBaseURL}").get(0).getLiveValue();
		resetEnv(gt);
		driver.manage().window().maximize();
		driver.get(baseUrl);
		LogonTests lt = new LogonTests(controller);
		lt.setMap(getMap());
		lt.setServer(sl, driver, webDriver);
		gt.addTest(lt.logonBasic("john","john"));
		
		gt.addTest(validateFieldPresence());
		gt.addTest(lt.logoutBasic());


	}

	@Override
	public GuiTestReportSingleTest validateFieldPresence()  {
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Validate the presence of the customer fields.");
		getActiveTest().setTestName("Customer - Field Presence");
		TestStep activeStep = null;
		int d=1;
		try{
			Page landingPage = getPage("LandingPage");
			TestStep ts= new TestStep();
			createTrade(TradeType.EMAIL,"Foreign Outward BGI");
			ts.setStepName("Click Follow Up");
			activeStep = ts;
			driver.findElement(landingPage.getPageObjectByID("LandingPage.FollowUpQueue").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click New");
			activeStep = ts;
			driver.findElement(landingPage.getPageObjectByID("LandingPage.NewQueue").getBy()).click();
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
			new Select(driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.ProductType").getBy())).selectByVisibleText("Foreign Outward BGI");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			
			ts= new TestStep();
			ts.setStepName("Select Issue");
			activeStep = ts;
			new Select(driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.Step").getBy())).selectByVisibleText("Issue");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			
			ts= new TestStep();
			ts.setStepName("Select the Product Sub Type - " + "Bid/Tender Bond");
			activeStep = ts;
			new Select(driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.ProductSubType").getBy())).selectByVisibleText("Bid/Tender Bond");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			
			
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(caseProductInformation.getPageObjectByID("CaseProductInformation.NextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			/**
			 * Customer Screen - First row is Add button
			 */
			Page customerSearch = getPage("CustomerSearch");
			ts= new TestStep();
			ts.setStepName("Click The Add Customer button");
			activeStep = ts;
			driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.AddCustomerButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			Page addCustomer = getPage("AddCustomer");
			ts= new TestStep();
			ts.setStepName("Verify the customer name field is empty");
			activeStep = ts;
			assertEquals("", driver.findElement(addCustomer.getPageObjectByID("AddCustomer.NameField").getBy()).getText(),ts);
			//ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Validate the Customer KYC field contains the Yes/No pairs.");
			activeStep = ts;
			assertEquals("Yes No", driver.findElement(addCustomer.getPageObjectByID("AddCustomer.KYCSelect").getBy()).getText(),ts);
			//ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Validate the Customer Segment drop down contains the Silver/Gold/Platinum/OffShort/OnShore items. ");
			activeStep = ts;
			assertEquals("SilverGoldPlatinumOffshoreOnshore", driver.findElement(addCustomer.getPageObjectByID("AddCustomer.SegmentSelect").getBy()).getText(),ts);
			//ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Validate the Cancel button.");
			activeStep = ts;
			assertEquals("CANCEL", driver.findElement(addCustomer.getPageObjectByID("AddCustomer.CancelButton").getBy()).getText(),ts);
			//ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Clear the Customer Name field ");
			activeStep = ts;
			driver.findElement(addCustomer.getPageObjectByID("AddCustomer.NameField").getBy()).clear();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Enter Selenium1 into the Customer Name Field.");
			activeStep = ts;
			driver.findElement(addCustomer.getPageObjectByID("AddCustomer.NameField").getBy()).sendKeys("selenium1");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Select Yes from the Customer KYC Dropdown.");
			activeStep = ts;
			new Select(driver.findElement(addCustomer.getPageObjectByID("AddCustomer.KYCSelect").getBy())).selectByVisibleText("Yes");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Select Silver from the Customer Segment DropDown.");
			activeStep = ts;
			new Select(driver.findElement(addCustomer.getPageObjectByID("AddCustomer.SegmentSelect").getBy())).selectByVisibleText("Silver");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click Next.");
			activeStep = ts;
			driver.findElement(addCustomer.getPageObjectByID("AddCustomer.NextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			Thread.sleep(1000);
			ts= new TestStep();
			ts.setStepName("Click on the Customer Search Tab");
			activeStep = ts;
			driver.findElement(By.linkText("Customer Search")).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Validate the Selenium1 user is displayed in the Search List.");
			activeStep = ts;
			assertEquals("selenium1", driver.findElement(By.cssSelector("td.ng-binding")).getText(),ts);
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Validate the KYC dropdown contains the Yes/No values.");
			activeStep = ts;
			assertEquals("Yes No", driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.CustomerKYCSelect").getBy()).getText(),ts);
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click the Add Customer button");
			activeStep = ts;
			driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.AddCustomerButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Clear the Customer Name field.");
			activeStep = ts;
			driver.findElement(addCustomer.getPageObjectByID("AddCustomer.NameField").getBy()).clear();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Enter Selenium1 into the field.");
			activeStep = ts;
			driver.findElement(addCustomer.getPageObjectByID("AddCustomer.NameField").getBy()).sendKeys("selenium1");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Select Yes from the KYC Dropdown");
			activeStep = ts;
			new Select(driver.findElement(addCustomer.getPageObjectByID("AddCustomer.KYCSelect").getBy())).selectByVisibleText("Yes");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Select Silver from the Customer Segment DropDown");
			activeStep = ts;
			new Select(driver.findElement(addCustomer.getPageObjectByID("AddCustomer.SegmentSelect").getBy())).selectByVisibleText("Silver");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click Next");
			activeStep = ts;
			driver.findElement(addCustomer.getPageObjectByID("AddCustomer.NextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Validate the error msg (A Customer with the name 'selenium1' already exists!) is displayed and the text is correct.");
			activeStep = ts;
			assertEquals("A Customer with the name 'selenium1' already exists!", driver.findElement(By.cssSelector("div.modal-body.ng-binding")).getText(),ts);
			//ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Validate the OK button.");
			activeStep = ts;
			assertEquals("OK", driver.findElement(By.cssSelector("button.btn.btn-primary")).getText(),ts);
			//ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click the OK Button.");
			activeStep = ts;
			driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

		}
		catch(Exception e){
			activeStep.setPassed(false);
			activeStep.setOptionalFailureMessage(e.getMessage());
			activeStep.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(activeStep,driver);
			e.printStackTrace();
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
