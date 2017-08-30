package com.ilabquality.gtaf.controller.gui;


import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.LoggerFactory;
import org.testng.log4testng.Logger;

import com.ilabquality.gtaf.gtafAppl.GTAFApplController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.guitests.GUITestDef;
import com.ilabquality.gtaf.guitests.GUITestSuite;
import com.ilabquality.gtaf.guitests.helpers.GUITest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSingleTest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSuite;
import com.ilabquality.gtaf.guitests.helpers.Page;
import com.ilabquality.gtaf.guitests.helpers.SeleniumListener;
import com.ilabquality.gtaf.guitests.helpers.SeleniumObjectMapper;
import com.ilabquality.gtaf.reporting.gui.GuiTestReport;

public class GUIController implements Runnable {

	private GUITestSuite selectedSuite;
	SeleniumListener sl = new SeleniumListener();
	public EventFiringWebDriver driver ;
	public WebDriver webDriver;
	private GTAFApplController parent;
	
	
	public GUIController(GTAFApplController parent, GUITestSuite selectedSuite) {
		this.selectedSuite = selectedSuite;
		this.parent = parent;
	}

	public void doAction()  {
		ArrayList<Page> pages = SeleniumObjectMapper.getPages(Runner.getRuntimeVariables().get("@{SeleniumObjectMap}").get(0).getLiveValue());
		GuiTestReportSuite gtrs = new GuiTestReportSuite();
		gtrs.setSuiteName(selectedSuite.getName());
		if ( parent != null){
			parent.setProgressMaximum(selectedSuite.getSuiteData().size());
			parent.setTaskDisplayer("Running GUI tests");
		}
		try{
			for ( GUITestDef gtd : selectedSuite.getSuiteData()){
				try{
					if ( parent != null)
						parent.appendLog("Instantiating Class " + gtd.getClazzName());
					
					Constructor<?> c = Class.forName(gtd.getClazzName()).getConstructor(this.getClass());
					GUITest gt = (GUITest) c.newInstance(this);
					if ( parent != null)
						parent.appendLog("Executing Tests....");
					gt.setServer(sl,driver,webDriver);
					gt.setMap(pages);
					gt.runTests(gtrs);
					if ( parent != null)
						parent.incrementProgress();
				}
				catch(NoClassDefFoundError ncfde){
					gtrs.addTest(new GuiTestReportSingleTest(ncfde));
				}
				
			}
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
			gtrs.addTest(new GuiTestReportSingleTest(e));
		}
		if ( parent != null)
			parent.resetProgress();
		if ( parent != null)
			parent.appendLog("Done executing suite. Generating Report");
		driver.close();
		gtrs.setEndTime(DateTime.now());
		new GuiTestReport(gtrs).generateAndShow();
		if ( parent != null)
			parent.appendLog("Report Generated");
		if ( parent != null)
			parent.setTaskDisplayer("Done");
	}

	@Override
	public void run() {
		initServer();
		doAction();
		
	}
	
	public void initServer(){
		ChromeOptions co = new ChromeOptions();
		try{
			int debug=1;
		DesiredCapabilities cap  = DesiredCapabilities.chrome();
		String browserName = Runner.getRuntimeVariables().get("@{SeleniumBrowser}").get(0).getLiveValue();
//		switch( browserName.toLowerCase() ){
//			case "chrome":{
//				cap = DesiredCapabilities.chrome();
//				break;
//			}
//			case "ie" :{
//				cap = DesiredCapabilities.internetExplorer();
//				break;
//			}
//			default:{
//				cap = DesiredCapabilities.chrome();
//				break;
//			}
//		}
//		
		
//		Map<String, Object> prefs = new HashMap<String, Object>();
//		//Disable save password popups.
//		prefs.put("credentials_enable_service", false);
//		prefs.put("profile.password_manager_enabled", false);
//		co.setExperimentalOption("prefs", prefs);
//		cap.setCapability(ChromeOptions.CAPABILITY,prefs);
//		
		String seleniumMode = "";
		String driverLocation = "";
		String sHubURL = "";
		
		try {
			seleniumMode = Runner.getRuntimeVariables().get("@{SeleniumMode}").get(0).getLiveValue();
			driverLocation = Runner.getRuntimeVariables().get("@{WebdriverLocation}").get(0).getLiveValue();
			cap.setBrowserName(browserName);
			System.setProperty("webdriver.chrome.driver", driverLocation);
			sHubURL = Runner.getRuntimeVariables().get("@{SeleniumHUBUrl}").get(0).getLiveValue();
		} catch (Exception e) {
			Logger.getLogger(Runner.class).error("Unable to set Selenium Mode and Driver Location.",e );
			Runner.cliExitCode=2;
			return;
		}
		
		switch(seleniumMode.toLowerCase()){
//			case "headless":{
//				driver = new HtmlUnitDriver(BrowserVersion.CHROME,true); // Will not implement this solution as AUT is angular.js. No custom js engines to be used. Discussed with stakeholders and approach approved. 
//				break;
//			}
			case "local":{
				webDriver = new ChromeDriver(co);
				driver = new EventFiringWebDriver(webDriver);
				Logger.getLogger(Runner.class).info("ChromeDriver Created");
				break;
			}	
			case "remote":{
				webDriver = new RemoteWebDriver(new URL(sHubURL), cap);
				driver = new EventFiringWebDriver(webDriver);
				break;
			}
			default:{
				Logger.getLogger(Runner.class).error("Unable to switch Selenium Mode, value was [" + seleniumMode.toLowerCase() +"]");
				Runner.cliExitCode=2;
				return;
			}
		}
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(240, TimeUnit.SECONDS);//AWS Kak
		driver.register(sl);
		}catch(Exception e){
		
			try {
				Logger.getLogger(Runner.class).info("ChromeDriver Creation Failed, Attempting localhost port 9515");
				webDriver = new RemoteWebDriver(new URL("http://localhost:9515"), DesiredCapabilities.chrome());
				driver = new EventFiringWebDriver(webDriver);
				Logger.getLogger(Runner.class).info("ChromeDriver Created");
			} catch (Exception e1) {
				Logger.getLogger(Runner.class).info("ChromeDriver Creation on Localhost Failed, Aborting...");
				e.printStackTrace();
			}
		}
	}
}

