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

public class CustomerNotKYCed extends GUITest {
	public CustomerNotKYCed(GUIController controller){
		super(controller);
	}
	@Override
	public void runTests(GuiTestReportSuite gt) {
		
		this.baseUrl = Runner.getRuntimeVariables().get("@{SeleniumBaseURL}").get(0).getLiveValue();
		resetEnv(gt);
		driver.manage().window().maximize();
		driver.get(baseUrl);
		LogonTests lt = new LogonTests(controller);
		lt.setMap(getMap());
		lt.setServer(sl, driver, webDriver);
		gt.addTest(lt.logonBasic("john","john"));
		
		gt.addTest(customerNotKYCed());
		gt.addTest(lt.logoutBasic());
	}

	public GuiTestReportSingleTest customerNotKYCed()  {
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Validate the presence of the customer fields.");
		getActiveTest().setTestName("Customer - Field Presence");
		TestStep activeStep = null;
		int d=1;
		try{
			Page landingPage = getPage("LandingPage");
			TestStep ts= new TestStep();
			createTrade(TradeType.EMAIL, "Local Outward BGI");
					
			ts= new TestStep();
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
			ts.setStepName("Enter 'Jan' into the customer search field");
			activeStep = ts;
			driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.NameSearchField").getBy()).sendKeys("Jan");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click the 'Jan' result");
			activeStep = ts;
			driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.SearchPopup").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			waitForExistance(customerSearch.getPageObjectByID("CustomerSearch.NextButton").getBy());
			
			ts= new TestStep();
			ts.setStepName("Select 'No' on the KYC search result.");
			activeStep = ts;
			new Select (driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.CustomerKYCSelect").getBy())).selectByVisibleText("No");
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Click the 'Next' button. ");
			activeStep = ts;
			driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.NextButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			try{
				Thread.sleep(500);
			}
			catch(Exception e){
				
			}
			ts= new TestStep();
			ts.setStepName("Validate the 'Customer Non-Compliant' popup message is displayed.");
			activeStep = ts;
			try{
				driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.NonCompliantHeader").getBy()).isDisplayed();
				ts.setPassed(true);
				getActiveTest().addTestStep(ts,driver);
			}catch(Exception e){
				ts.setPassed(false);
				ts.setTestStepImage(takeScreenshot(driver));
				ts.setOptionalFailureMessage("Customer Non-Compliant UI was not displayed");
				getActiveTest().addTestStep(ts,driver);
			}
			
			if ( !activeStep.hasPassed()){
				return getActiveTest();
			}
			
			ts= new TestStep();
			ts.setStepName("Click the 'OK' button on the Customer Non-Compliant UI. ");
			activeStep = ts;
			driver.findElement(customerSearch.getPageObjectByID("CustomerSearch.NonCompliantOKButton").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
		
			try{
				Thread.sleep(1000);
			}
			catch(Exception e){
				
			}
			ts= new TestStep();
			ts.setStepName("Open the 'New' queue");
			activeStep = ts;
			driver.findElement(landingPage.getPageObjectByID("LandingPage.NewQueue").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			try{
				Thread.sleep(1000);
			}catch(Exception e){}
			
			ts= new TestStep();
			ts.setStepName("Open the 'Follow Up' queue");
			activeStep = ts;
			driver.findElement(landingPage.getPageObjectByID("LandingPage.FollowUpQueue").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			
			ts= new TestStep();
			ts.setStepName("Validate the new task exists in the follow up queue.");
			activeStep = ts;
			try{
				driver.findElement(landingPage.getPageObjectByID("LandingPage.Claim").getBy()).isDisplayed();
				ts.setPassed(true);
				ts.setTestStepImage(takeScreenshot(driver));
				ts.setOptionalFailureMessage("The follow up task created by the KYC-No selection on the customer search screen was created. ");
				getActiveTest().addTestStep(ts,driver);
			}catch(Exception e){
				ts.setPassed(false);
				ts.setTestStepImage(takeScreenshot(driver));
				ts.setOptionalFailureMessage("The follow up task created by the KYC-No selection on the customer search screen was not created. ");
				getActiveTest().addTestStep(ts,driver);
			}
			
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
