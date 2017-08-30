package com.ilabquality.gtaf.guitests.deal.iss.elc;

import com.ilabquality.gtaf.controller.gui.GUIController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.guitests.helpers.GUITest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSingleTest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSuite;
import com.ilabquality.gtaf.guitests.logon.LogonTests;

public class CreateDeal_CreditEscalations extends GUITest {
	public CreateDeal_CreditEscalations(GUIController controller){
		super(controller);
	}
	@Override
	public void runTests(GuiTestReportSuite gt) {
		try{
		
		this.baseUrl = Runner.getRuntimeVariables().get("@{SeleniumBaseURL}").get(0).getLiveValue();
		resetEnv(gt);
		driver.manage().window().maximize();
		driver.get(baseUrl);
		LogonTests lt = new LogonTests(controller);
		lt.setMap(getMap());
		lt.setServer(sl, driver, webDriver);
		
		com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_CreditEscalations aa = 
				new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_CreditEscalations(controller);
		aa.setMap(getMap());
		aa.setServer(sl, driver, webDriver);
		
		gt.addTest(lt.logonBasic("john","john"));
		gt.addTest(aa.creditEscalation("Export LC","Issue","Export LC"));
		gt.addTest(lt.logoutBasic());
		resetEnv(gt);

		}catch(Exception e){
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
