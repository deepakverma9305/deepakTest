package com.ilabquality.gtaf.guitests.deal.amend.lobgi;

import com.ilabquality.gtaf.controller.gui.GUIController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.guitests.helpers.GUITest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSingleTest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSuite;
import com.ilabquality.gtaf.guitests.logon.LogonTests;

public class AmendDeal_ReleaseInProduction extends GUITest {

	private GuiTestReportSuite gt;

	public AmendDeal_ReleaseInProduction(GUIController controller) {
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
			
			com.ilabquality.gtaf.guitests.deal.amend.generic.AmendDealLocalOutwards aa = 
					new com.ilabquality.gtaf.guitests.deal.amend.generic.AmendDealLocalOutwards(controller);
			aa.setMap(getMap());
			aa.setServer(sl, driver, webDriver);
			
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction aaa = 
					new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction(controller);
			
			aaa.setMap(getMap());
			aaa.setServer(sl, driver, webDriver);
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aaa.noEscalationsRelease("Local Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.amendDealReleaseInProduction("Local Outward BGI","Amend","Bid/Tender Bond",aaa.getProdRef()));
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
