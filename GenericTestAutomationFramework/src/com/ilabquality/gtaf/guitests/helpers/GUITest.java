package com.ilabquality.gtaf.guitests.helpers;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.ilabquality.gtaf.controller.gui.GUIController;
import com.ilabquality.gtaf.gtafAppl.GTAFApplication;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.gtafAppl.Runner.TradeType;
import com.ilabquality.gtaf.utils.rest.BasicAuthRestService;
import com.ilabquality.gtaf.utils.rest.KeyCloakRestService;

public abstract class GUITest {
	
	private ArrayList<Page> availablePages;
	public StringBuilder reportContent = new StringBuilder();
	public String testSuiteName  = "";
	public String baseUrl = "";
	public String caseID="NOT SET";
	public int awsSleepTime=0;
	public abstract void runTests(GuiTestReportSuite gt);
	
	public abstract GuiTestReportSingleTest validateFieldPresence() ;
	public abstract GuiTestReportSingleTest validateFieldWidths();
	public abstract GuiTestReportSingleTest validateFieldDefaultStates();
	public abstract GuiTestReportSingleTest validateFieldInputTypes();
	public abstract GuiTestReportSingleTest validateDefaultValues();
	public SeleniumListener sl = new SeleniumListener();
	public EventFiringWebDriver driver ;
	public WebDriver webDriver;
	public GUIController controller;
	public GUITest(GUIController controller){
		this.controller = controller;
	}
	

	public void setMap(ArrayList<Page> pages) {
		this.availablePages = pages;
		
	}
	
	public ArrayList<Page> getMap(){
		return this.availablePages;
	}
	public Page getPage(String name) throws PageObjectNotFoundException{
		if ( availablePages == null )
			throw new PageObjectNotFoundException("Page map not set!");
		Page returnPage = null;
		for ( Page p : availablePages ){
			if ( p.getPageName().equalsIgnoreCase(name)){
				returnPage = p;
				break;
			}
		}
		return returnPage;
	}
	
	public void sleep(int milliz){
		try{
			Thread.currentThread();
			Thread.sleep(milliz);
		}catch(Exception e){
			
		}
	}

	public void setActiveTest(GuiTestReportSingleTest t){
		this.sl.setActiveTest(t);
	}
	
	public GuiTestReportSingleTest getActiveTest(){
		return this.sl.activeTest;
	}
	public TestStep getActiveStep(){
		return this.sl.currentStep;
	}
	public String takeScreenshot(WebDriver driver){
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
	} 
	
	public boolean validateFieldWidth(String pageName, String pageObject){
		boolean returnValue = false;
		try{
			Page page = getPage(pageName);
			String expectedWidth = page.getPageObjectByID(pageObject).getMaxLength();
			String width = driver.findElement(page.getPageObjectByID(pageObject).getBy()).getAttribute("ng-maxlength");
			if ( expectedWidth.equals("none") ){
				if (width == null ){
					returnValue = true;
				}else{
					returnValue = false;
				}
			}else{
				if ( expectedWidth == width) {
					returnValue = true;
				}else{
					returnValue = false;
				}
			}
		}catch(PageObjectNotFoundException ponfe){}
		return returnValue;
	}
	
	public boolean validateFieldState(String pageName, String pageObject, boolean expectedValue){
		boolean returnValue = false;
		try{
			Page page = getPage(pageName);
			boolean actualValue = driver.findElement(page.getPageObjectByID(pageObject).getBy()).isEnabled();
			if ( expectedValue == actualValue ){
				returnValue = true;
			}else{
				returnValue = false;
			}
		}catch(PageObjectNotFoundException ponfe){}
		return returnValue;
	}
	
	public boolean validateFieldType(String pageName, String pageObject, String type){
		boolean returnValue = false;
		try{
			Page page = getPage(pageName);
			String actualValue = driver.findElement(page.getPageObjectByID(pageObject).getBy()).getTagName().toLowerCase();
			System.out.println("ExpectedType : " + type + " Actual : " + actualValue);
			if ( type.equalsIgnoreCase(actualValue) ){
				returnValue = true;
			}else{
				returnValue = false;
			}
		}catch(PageObjectNotFoundException ponfe){}
		return returnValue;
	}
	
	public boolean validateDefaultFieldValue(String pageName, String pageObject, String value){
		boolean returnValue = false;
		try{
			Page page = getPage(pageName);
			String actualValue = driver.findElement(page.getPageObjectByID(pageObject).getBy()).getText();
			if ( value.equalsIgnoreCase(actualValue) ){
				returnValue = true;
			}else{
				returnValue = false;
			}
		}catch(PageObjectNotFoundException ponfe){}
		return returnValue;
	}
	
	public boolean waitForExistance(By by){
		int safetyNet = 0;
		boolean returnValue = true;
		try{
			while(!driver.findElement(by).isDisplayed() && safetyNet < 20 ){
				Thread.currentThread();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					returnValue = false;
				}
				safetyNet++;
				System.out.println("SafetNet waiting " + safetyNet);
			}
		}catch(Exception e){
			returnValue = false;
		}
		return returnValue;
	}

	public void setServer(SeleniumListener sl, EventFiringWebDriver driver, WebDriver webDriver) {
		this.sl = sl;
		this.driver = driver;
		this.webDriver = webDriver;
		
	}
	
	public void assertEquals(String expected, String actual, TestStep ts){
		int d=0;
		if ( expected == null & actual == null){
			ts.setPassed(true);
		}
		
		if ( expected.equalsIgnoreCase(actual)){
			ts.setPassed(true);
		}else{
			String s = expected.replace("\n", "").replace(" ", "");
			String t = actual.replace("\n", "").replace(" ", "");
			if(s.equalsIgnoreCase(t)){
				ts.setPassed( true );
			}else{
				ts.setPassed(false);
			}
		}
	}
	
	public void resetEnv(GuiTestReportSuite gt) {
		System.out.println("Resetting environments");
		GuiTestReportSingleTest gtrst = new GuiTestReportSingleTest();
		gtrst.setTestName("ResetENV");
		gtrst.setTestDescription("Reset the test environment using endpoints");
		TestStep ts = new TestStep();
		ts.setStepName("Reset the test environment");
		
		Runner.serviceConnector.resetEnvironment();
		ts.setPassed(true);
		gtrst.addTestStep(ts,driver);
		gt.addTest(gtrst);
	}

//	public void createChannelFOBGIIssue(){
//		TestStep activeStep = null;
//		try{
//			Page landingPage = getPage("LandingPage");
//			TestStep ts= new TestStep();
//			ts.setStepName("Click on the 'Manual Trades' button");
//			activeStep = ts;
//			driver.findElement(landingPage.getPageObjectByID("LandingPage.InsertNew").getBy()).click();
//			ts.setPassed(true);
//			getActiveTest().addTestStep(ts,driver);
//			
//			sleep(1000);
//			
//			ts= new TestStep();
//			ts.setStepName("Create a dummy 'Channel - FOBGI - Issue' case");
//			activeStep = ts;
//			driver.findElement(landingPage.getPageObjectByID("LandingPage.InsertNewChannel_FOBGI_Issue").getBy()).click();
//			ts.setPassed(true);
//			getActiveTest().addTestStep(ts,driver);
//		}catch(Exception e){
//			activeStep.setPassed(false);
//			activeStep.setOptionalFailureMessage(e.getMessage());
//			activeStep.setTestStepImage(takeScreenshot(driver));
//			getActiveTest().addTestStep(activeStep,driver);
//		}
//	}
	
	public String createTrade(TradeType tradeType,String product){
		String locator = "NOT SET";
		TestStep activeStep = null;
		
		String caseURL = "NOT SET";
		String postData = "NOT SET";
		try{
			if  (tradeType == TradeType.EMAIL){
				locator = "LandingPage.InsertNewEmailCase";
				postData = "{\"subject\":\"A BGI Message\",\"sender\":\"test@test.com\",\"recipients\":\"bgicenter@bgicentre.co.za\",\"incomingAttachmentCount\":1,\"channel\":\"Email\",\"status\":\"new\"}";
				caseURL = ( Runner.getRuntimeVariables().get("@{ServicesServerIP}").get(0).getLiveValue().contains("elasticbeanstalk")
						?
								"http://"+Runner.getRuntimeVariables().get("@{WorkFlowServerIP}").get(0).getLiveValue()+":"+Runner.getRuntimeVariables().get("@{WorkFlowServerPort}").get(0).getLiveValue()+"/workflow/custom/dummy/case"
								:"http://"+Runner.getRuntimeVariables().get("@{WorkFlowServerIP}").get(0).getLiveValue()+":"+Runner.getRuntimeVariables().get("@{WorkFlowServerPort}").get(0).getLiveValue()+"/custom/dummy/case");
			}
			else if (tradeType == TradeType.SWIFT_HAND_DELIVERED){
				locator = "LandingPage.InsertSwiftHandDelivered";
				postData = "{\"subject\":\"A BGI Message\",\"sender\":\"test@test.com\",\"recipients\":\"bgicenter@bgicentre.co.za\",\"incomingAttachmentCount\":1,\"channel\":\"HandDelivered\",\"status\":\"new\"}";
				caseURL = ( Runner.getRuntimeVariables().get("@{ServicesServerIP}").get(0).getLiveValue().contains("elasticbeanstalk")
						?
								"http://"+Runner.getRuntimeVariables().get("@{WorkFlowServerIP}").get(0).getLiveValue()+":"+Runner.getRuntimeVariables().get("@{WorkFlowServerPort}").get(0).getLiveValue()+"/custom/dummy/case"
								:"http://"+Runner.getRuntimeVariables().get("@{WorkFlowServerIP}").get(0).getLiveValue()+":"+Runner.getRuntimeVariables().get("@{WorkFlowServerPort}").get(0).getLiveValue()+"/workflow/custom/dummy/case");
			}
			else if (tradeType == TradeType.CHANNEL_FOBGI_ISSUE){
				locator = "LandingPage.InsertNewChannel_FOBGI_Issue";
			}
			else if (tradeType == TradeType.GENERIC_AMEND){
				locator = "LandingPage.InsertNewChannel_FOBGI_Amend";
				caseURL = ( Runner.getRuntimeVariables().get("@{ServicesServerIP}").get(0).getLiveValue().contains("elasticbeanstalk")
						?
								"http://"+Runner.getRuntimeVariables().get("@{ServicesServerIP}").get(0).getLiveValue()+":"+Runner.getRuntimeVariables().get("@{ServicesServerPort}").get(0).getLiveValue()+"/custom/newcase"
								:"http://"+Runner.getRuntimeVariables().get("@{ServicesServerIP}").get(0).getLiveValue()+":"+Runner.getRuntimeVariables().get("@{ServicesServerPort}").get(0).getLiveValue()+"/services/custom/newcase");
				postData = "{\"applicationRefNo\":\"customer online FO_BGI_Issue X\","
						+ "\"channel\":\"Channel\","
						+ "\"transactionRefNo\":\"D5D43738-CC75-47BF-2EEA-08D49BA72C71\","
						+ "\"productID\":\"FOBGI\","
						+ "\"productStep\":\"Issue\","
						+ "\"productTypeID\":\"Performance Guarantee\","
						+ "\"appName\":\"Jan\",\"appSegment\":\"Onshore\",\"dateCreated\":\"2017-04-20T06:15:16Z\","
						+ "\"beneficiaryName\":\"CitiBank\",\"expiryType\":\"03\",\"expiryDate\":\"2017-06-01T06:15:16Z\",\"amount\":\"44556677.88\",\"currency\":\"USD\","
						+ "\"wordingCheck\":\"Standard\",\"applicationData\":\"{\"TransactionId\":\"D5D43738-CC75-47BF-2EEA-08D49BA72C71\",\"BasicInformationViewModel\":{\"ApplicationStatus\":\"Approved\","
						+ "\"Amount\":67676767.67,\"Currency\":\"AUD\"},\"ApplicantInformationViewModel\":{\"AppName\":\"Hansie Slim\",\"AppAddressLine\":\"155 West\",\"AppSuburb\":\"Sandhurst\",\"AppCity\":\"Sandton\",\"AppProvince\":\"Gauteng\",\"AppCountry\":\"South Africa\",\"AppContactPerson\":\"Piet Pompies\",\"AppContactDesignation\":\"Handyman\",\"AppEmail\":\"abc@cba.com\",\"AppBankBranch\":\"4 Merchant\"},\"BeneficiaryInformationViewModel\":{\"BenName\":\"Citibank\",\"BenAddressLine\":\"22 Katherine\",\"BenSuburb\":\"Parkhurst\",\"BenCity\":\"Sandton\",\"BenProvince\":\"Gauteng\",\"BenCountry\":\"South Africa\",\"BenContactPerson\":\"Structured Lender\",\"BenEmail\":\"lending@katherine.com\",\"BenAccountNumber\":\"12341112222\",\"BenAccountNumberType\":\"cheque\",\"BenABankClearingCode\":\"123\"},\"AdvisingBankInformationViewModel\":{}, \"ProductInformationViewModel\":{\"ProductId\":\"FOBGI\",\"ProductTypeId\":\"Performance Guarantee\",\"ExpiryType\":\"whatever\",\"PlaceofExpiry\":\"place of no return\",\"ExpiryDate\":\"2017-05-26T00:00:00\",\"IssuingBank\":\"BarclaysBank\"},\"DeliveryMethodViewModel\":{\"CommunicationMethod\":\"SWIFT\",\"DeliveryMethod\":\"SWIFT\",\"DeliveryType\":\"Applicant\",\"CommsToDeliveryContact\":\"advisedofIssue\"},\"WordingViewModel\":{\"SourceOfWording\":\"Standard\",\"Wording\":\"lekker\",\"WordingLanguage\":\"English\"},\"SettlementInstructionViewModel\":{\"PrinicipalAmount\":\"applicant\",\"IssuingBankCharge\":\"applicant\",\"ForeignBankCharge\":\"applicant\",\"AppAccountNumber\":\"55556287890\",\"CashMarginAmount\":\"666.55\",\"CashMarginAccount\":\"63524759871\",\"SettlementInstructionNarrative\":\"skdbaskjs\"}}\"}";
			}
			else if (tradeType == TradeType.CHANNEL_LOBGI_ISSUE){
				locator = "LandingPage.InsertNewChannel_LOBGI_Issue";
			}
			else if (tradeType == TradeType.CHANNEL_LC_ISSUE){
				locator = "LandingPage.InsertNewChannel_ImportLC_Issue";
			}
			else{
				throw new TradeTypeNotSpecifiedException("The trade type specified[ "+tradeType+"] is not supported!");
			}
			System.out.println("Creating dummy task with URL = " + caseURL);
//			Page landingPage = getPage("LandingPage");
			TestStep ts= new TestStep();
			ts.setStepName("Create a new "+ locator +" trade by calling the relevant service");
			activeStep = ts;
			int c=2;
			
			
			
			if ( Runner.getRuntimeVariables().get("@{AuthenticationProvider}").get(0).getLiveValue().equalsIgnoreCase("BasicAuth")){
				caseID = new BasicAuthRestService().callPostService(caseURL, postData, "john", "john");
			}else{
				JavascriptExecutor jse = ((JavascriptExecutor) driver);
				
				String token = jse.executeScript("var p = auth.authz; if ( !p  ) {return 'no authz'} else{ return p.token}").toString();
				System.out.println("KeyCloak token is " + token);
				GTAFApplication.KEYCLOAK_WRAPPER.setAccess_token(token);
				caseID = new KeyCloakRestService().callPostService(caseURL, postData, "john", "john");
			
			}
			caseID = caseID.split(",")[0].split(":")[1].replace("\"", "").replace("}", "");//{"processId":"11","currentTaskId":"21"}
//			driver.findElement(landingPage.getPageObjectByID("LandingPage.InsertNew").getBy()).click();
			ts.setPassed(true);
			getActiveTest().addTestStep(ts,driver);
			sleep(1000);
//			ts= new TestStep();
//			ts.setStepName("Create a dummy '"+locator+"' case");
//			activeStep = ts;
//			driver.findElement(landingPage.getPageObjectByID(locator).getBy()).click();
//			ts.setPassed(true);
//			getActiveTest().addTestStep(ts,driver);
//			sleep(1000);
//			
//			if  (tradeType == TradeType.GENERIC_AMEND){
//				ts = new TestStep();
//				ts.setStepName("Clear the 'Production Reference' field");
//				activeStep = ts;
//				driver.findElement(landingPage.getPageObjectByID("LandingPage.AmendProductionReference").getBy()).clear();
//				ts.setPassed(true);
//				getActiveTest().addTestStep(ts,driver);
//				sleep(1000);
//				ts = new TestStep();
//				ts.setStepName("Enter '123' into the 'Production Reference' field");
//				activeStep = ts;
//				driver.findElement(landingPage.getPageObjectByID("LandingPage.AmendProductionReference").getBy()).sendKeys("123");
//				ts.setPassed(true);
//				getActiveTest().addTestStep(ts,driver);
//				sleep(1000);
//				
//				if ( product.equalsIgnoreCase("Foreign Outward BGI")){
//					product = "FO BGI";
//				}else if (product.equalsIgnoreCase("Local Outward BGI")){
//					product = "LO BGI";
//				}
//				
//				
//				ts = new TestStep();
//				ts.setStepName("Select the value of '"+product+"' from the 'Product' list.");
//				activeStep = ts;
//				new Select (driver.findElement(landingPage.getPageObjectByID("LandingPage.AmendProduct").getBy())).selectByVisibleText(product);
//				ts.setPassed(true);
//				getActiveTest().addTestStep(ts,driver);
//				
//				ts = new TestStep();
//				ts.setStepName("Click the 'Go' Button");
//				activeStep = ts;
//				driver.findElement(landingPage.getPageObjectByID("LandingPage.AmendGoButton").getBy()).click();
//				ts.setPassed(true);
//				getActiveTest().addTestStep(ts,driver);
//			}
		}catch(Exception e){
			activeStep.setPassed(false);
			activeStep.setOptionalFailureMessage(e.getMessage());
			activeStep.setTestStepImage(takeScreenshot(driver));
			getActiveTest().addTestStep(activeStep,driver);
		}
		return caseID;
	}
}
