package com.ilabquality.gtaf.guitests.logon;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.ilabquality.gtaf.controller.gui.GUIController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.guitests.helpers.GUITest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSingleTest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSuite;
import com.ilabquality.gtaf.guitests.helpers.Page;
import com.ilabquality.gtaf.guitests.helpers.SeleniumListener;
import com.ilabquality.gtaf.guitests.helpers.TestStep;
import com.ilabquality.gtaf.utils.rest.BasicAuthRestService;
import com.ilabquality.gtaf.utils.rest.KeyCloakRestService;
public class LogonTests extends GUITest {

	String baseUrl = "Not Set";
	int safetyNet = 20;
	private GuiTestReportSuite testReportSuite;
	public LogonTests(GUIController controller){
		super(controller);
	}
	
	public LogonTests(GUIController controller, ArrayList<Page> pages, SeleniumListener sl, EventFiringWebDriver driver, WebDriver webDriver){
		super(controller);
		setMap(pages);
		this.sl = sl;
		this.driver = driver;
		this.webDriver = webDriver;
		
	}
	@Override
	public void runTests(GuiTestReportSuite gt) {
		this.baseUrl = Runner.getRuntimeVariables().get("@{SeleniumBaseURL}").get(0).getLiveValue();
		this.testReportSuite = gt;
		driver.manage().window().maximize();
		driver.get(baseUrl);
		testReportSuite.addTest(logonBasic("john","john"));
		testReportSuite.addTest(logoutBasic());
		testReportSuite.addTest(logonBasic("john","john"));
		testReportSuite.addTest(logoutBasic());
	}
	
	public GuiTestReportSingleTest logonBasic(String logonName, String logonPassword){
		System.out.println("logon"+logonName+"Basic");
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Perform a logon with the user " + logonName);
		getActiveTest().setTestName("LogonWith" + logonName);
		TestStep ts = new TestStep();
		ts.setStepName("Logon With " + logonName);
		try {
			Page logon = getPage("Logon");
			if ( Runner.getRuntimeVariables().get("@{AuthenticationProvider}").get(0).getLiveValue().equalsIgnoreCase("BasicAuth"))
			{
				while(!driver.findElement(logon.getPageObjectByID("Logon.UserNameBAuth").getBy()).isDisplayed() && safetyNet < 20 ){
					Thread.currentThread();
					Thread.sleep(2000);
					safetyNet++;
					System.out.println("SafetNet waiting " + safetyNet);
				}
				driver.findElement(logon.getPageObjectByID("Logon.UserNameBAuth").getBy()).clear();
				driver.findElement(logon.getPageObjectByID("Logon.UserNameBAuth").getBy()).sendKeys(logonName);
				driver.findElement(logon.getPageObjectByID("Logon.PasswordBAuth").getBy()).clear();
				driver.findElement(logon.getPageObjectByID("Logon.PasswordBAuth").getBy()).sendKeys(logonPassword);
				driver.findElement(logon.getPageObjectByID("Logon.LoginButtonBAuth").getBy()).click();
				ts.setOptionalFailureMessage("Logon successfull");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
				Thread.sleep(2000);
			}else if ( Runner.getRuntimeVariables().get("@{AuthenticationProvider}").get(0).getLiveValue().equalsIgnoreCase("KeyCloak")){
				driver.findElement(logon.getPageObjectByID("Logon.Logon").getBy()).click();
				driver.findElement(logon.getPageObjectByID("Logon.UserNameKC").getBy()).clear();
				driver.findElement(logon.getPageObjectByID("Logon.UserNameKC").getBy()).sendKeys(logonName);
				driver.findElement(logon.getPageObjectByID("Logon.PasswordKC").getBy()).clear();
				driver.findElement(logon.getPageObjectByID("Logon.PasswordKC").getBy()).sendKeys(logonPassword);
				driver.findElement(logon.getPageObjectByID("Logon.LoginButtonKC").getBy()).click();
				ts.setOptionalFailureMessage("Logon successfull");
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
				Thread.sleep(2000);
			}
		
		} catch (Exception e) {
			ts.setPassed(false);
			ts.setOptionalFailureMessage(e.getMessage());
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
		}
	return getActiveTest();
}
	public GuiTestReportSingleTest logoutBasic() {
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Perform a logout");
		getActiveTest().setTestName("logout");
		TestStep ts = new TestStep();
		ts.setStepName("Logout");
		try{
			while(!driver.findElement(By.linkText("Logout")).isDisplayed() && safetyNet < 20 ){
				Thread.currentThread();
				Thread.sleep(2000);
				safetyNet++;
			}
			safetyNet = 0;
			driver.findElement(By.linkText("Logout")).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
		}catch(Exception e){
			ts.setPassed(false);
			ts.setOptionalFailureMessage(e.getMessage());
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
		}
		return getActiveTest();
	}
	
	
	@Override
	public GuiTestReportSingleTest validateFieldPresence()  {
		try{
			//Cater for the various logon methods
			setActiveTest(new GuiTestReportSingleTest());
			getActiveTest().setTestDescription("Validate the correct Fields are present on the page. ");
			getActiveTest().setTestName("Logon_validateFieldPresence");
			driver.manage().window().maximize();
			System.out.println("Navigating to " + baseUrl);
			driver.get(baseUrl);
			Page logon = getPage("Logon");
			if ( Runner.serviceConnector instanceof BasicAuthRestService){
				TestStep ts = new TestStep();//Manual addition of validation step. 
				try{
					ts.setStepName("Validate the Page Logon is displayed.");
					ts.setPassed(driver.findElement(logon.getPageObjectByID("Logon.logon").getBy()).isDisplayed());
					ts.setTestStepImage(takeScreenshot(driver));
					getActiveTest().addTestStep(ts,driver);
				}catch(Exception e){
					ts.setPassed(false);
					getActiveTest().addTestStep(ts,driver);
				}
				ts = new TestStep();//Manual addition of validation step. 
				try{
					ts.setStepName("Validate the UserName field is displayed.");
					ts.setPassed(driver.findElement(logon.getPageObjectByID("Logon.UserName").getBy()).isDisplayed());
					ts.setTestStepImage(takeScreenshot(driver));
					getActiveTest().addTestStep(ts,driver);
				}catch(Exception e){
					ts.setPassed(false);
					ts.setTestStepImage(takeScreenshot(driver));
					getActiveTest().addTestStep(ts,driver);
				}
					ts = new TestStep();//Manual addition of validation step. 
				try{
					ts.setStepName("Validate the Password field is displayed.");
					ts.setPassed(driver.findElement(logon.getPageObjectByID("Logon.Password").getBy()).isDisplayed());
					ts.setTestStepImage(takeScreenshot(driver));
					getActiveTest().addTestStep(ts,driver);
				}catch(Exception e){
					ts.setPassed(false);
					ts.setTestStepImage(takeScreenshot(driver));
					getActiveTest().addTestStep(ts,driver);
				}
				ts = new TestStep();//Manual addition of validation step. 
				try{
					ts.setStepName("Validate the Login button is displayed.");
					ts.setPassed(driver.findElement(logon.getPageObjectByID("Logon.LoginButton").getBy()).isDisplayed());
					ts.setTestStepImage(takeScreenshot(driver));
					getActiveTest().addTestStep(ts,driver);
				}catch(Exception e){
					ts.setPassed(false);
					ts.setTestStepImage(takeScreenshot(driver));
					getActiveTest().addTestStep(ts,driver);
				}
				
			}else if ( Runner.serviceConnector instanceof KeyCloakRestService ){
				System.out.println("Not implemented = validateFieldPresence - KeyCloakRestService");
			}
		}catch(Exception e){
			// Do nothing. 
		}
		return getActiveTest();
	}
	@Override
	public GuiTestReportSingleTest validateFieldWidths() {
			//ng - maxlength
			setActiveTest(new GuiTestReportSingleTest());
			getActiveTest().setTestDescription("Validate the correct Fields widths. ");
			getActiveTest().setTestName("Logon_validateFieldWidths");
			driver.manage().window().maximize();
			driver.get(baseUrl);
			TestStep ts = new TestStep();//Manual addition of validation step.
			try{
				ts.setStepName("Validate the width of the UserName text field.");
				ts.setPassed(validateFieldWidth("Logon","Logon.UserName"));
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}catch(Exception e){
				ts.setPassed(false);
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}
			ts = new TestStep();//Manual addition of validation step.
			try{
				ts.setStepName("Validate the width of the Password text field.");
				ts.setPassed(validateFieldWidth("Logon","Logon.PAssword"));
				getActiveTest().addTestStep(ts,driver);
			}catch(Exception e){
				ts.setPassed(false);
				ts.setTestStepImage(takeScreenshot(driver));
				getActiveTest().addTestStep(ts,driver);
			}
		return getActiveTest();
	}
	
	@Override
	public GuiTestReportSingleTest validateFieldDefaultStates() {
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Validate the correct Fields states. ");
		getActiveTest().setTestName("Logon_validateFieldDefaultStates");
		driver.manage().window().maximize();
		driver.get(baseUrl);
		TestStep ts = new TestStep();//Manual addition of validation step. 
		try{
			ts.setStepName("Validate the default state of the UserName text field.");
			ts.setPassed(validateFieldState("Logon","Logon.UserName",true));
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
		}catch(Exception e){
			ts.setPassed(false);
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
		}
		ts = new TestStep();//Manual addition of validation step. 
		try{
			ts.setStepName("Validate the default state of the Password text field.");
			ts.setPassed(validateFieldState("Logon","Logon.Password",true));
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
		}catch(Exception e){
			ts.setPassed(false);
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
		}
		return getActiveTest();
	}
	public GuiTestReportSingleTest validateFieldInputTypes() {
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Validate the correct Field types of the fields on the page. ");
		getActiveTest().setTestName("Logon_validateFieldInputTypes");
		driver.manage().window().maximize();
		driver.get(baseUrl);
		TestStep ts = new TestStep();
		try{
			ts.setStepName("Validate the type of the UserName field");
			ts.setPassed(validateFieldType("Logon", "Logon.UserName", "input"));
			getActiveTest().addTestStep(ts,driver);
		}catch(Exception e){
			ts.setPassed(false);
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
		}
		ts = new TestStep();
		try{
			ts.setStepName("Validate the type of the UserName field");
			ts.setPassed(validateFieldType("Logon", "Logon.UserName", "input"));
			getActiveTest().addTestStep(ts,driver);
	
		}catch(Exception e){
			ts.setPassed(false);
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
		}
		return getActiveTest();
	}
	@Override
	public GuiTestReportSingleTest validateDefaultValues() {
		setActiveTest(new GuiTestReportSingleTest());
		getActiveTest().setTestDescription("Validate the correct Fields are present on the page. ");
		getActiveTest().setTestName("Logon_validateFieldPresence");
		driver.manage().window().maximize();
		driver.get(baseUrl);
		TestStep ts = new TestStep();
		try{
			ts.setStepName("Validate the type of the UserName field");
			ts.setPassed(validateDefaultFieldValue("Logon", "Logon.UserName", ""));
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
		}catch(Exception e){
			ts.setPassed(false);
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
		}
		ts = new TestStep();
		try{
			ts.setStepName("Validate the type of the Password field");
			ts.setPassed(validateDefaultFieldValue("Logon", "Logon.Password", ""));
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
		}catch(Exception e){
			ts.setPassed(false);
			ts.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(ts,driver);
		}
		return getActiveTest();
	}
}