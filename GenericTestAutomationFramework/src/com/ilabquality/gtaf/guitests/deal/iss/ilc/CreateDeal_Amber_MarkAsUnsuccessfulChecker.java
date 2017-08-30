package com.ilabquality.gtaf.guitests.deal.iss.ilc;

import com.ilabquality.gtaf.controller.gui.GUIController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.guitests.helpers.GUITest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSingleTest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSuite;
import com.ilabquality.gtaf.guitests.logon.LogonTests;

public class CreateDeal_Amber_MarkAsUnsuccessfulChecker extends GUITest {

	private GuiTestReportSuite gt;
	public CreateDeal_Amber_MarkAsUnsuccessfulChecker(GUIController controller){
		super(controller);
	}
	@Override
	public void runTests(GuiTestReportSuite gt) {
		
		int d=0;
		this.gt = gt;
		try{
			this.baseUrl = Runner.getRuntimeVariables().get("@{SeleniumBaseURL}").get(0).getLiveValue();
			resetEnv(gt);
			driver.manage().window().maximize();
			driver.get(baseUrl);
			LogonTests lt = new LogonTests(controller);
			lt.setMap(getMap());
			lt.setServer(sl, driver, webDriver);
			
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Amber_MarkAsUnsuccessfulChecker aa = 
					new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Amber_MarkAsUnsuccessfulChecker(controller);
			aa.setMap(getMap());
			aa.setServer(sl, driver, webDriver);
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.amberEscalationMarkAsUnsuccesfulChecker("Import LC","Issue","Import LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
		}catch(Exception e){
			gt.addTest(getActiveTest());
			e.printStackTrace();
		}
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
