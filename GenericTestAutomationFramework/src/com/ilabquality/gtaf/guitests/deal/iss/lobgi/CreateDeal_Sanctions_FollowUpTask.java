package com.ilabquality.gtaf.guitests.deal.iss.lobgi;

import com.ilabquality.gtaf.controller.gui.GUIController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.guitests.helpers.GUITest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSingleTest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSuite;
import com.ilabquality.gtaf.guitests.logon.LogonTests;

public class CreateDeal_Sanctions_FollowUpTask extends GUITest {

	private GuiTestReportSuite gt;

	public CreateDeal_Sanctions_FollowUpTask(GUIController controller) {
		super(controller);
		
	}

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
			
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Sanctions_FollowUpTask aa= 
					new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Sanctions_FollowUpTask(controller);
			aa.setMap(getMap());
			aa.setServer(sl, driver, webDriver);
			
			gt.addTest(lt.logonBasic("john","john"));
			
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Payment Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Performance Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Advance Payment Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Labour Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Shipping Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Maintenance Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Customs Bond"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Retention Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Immigration Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Security Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Financial Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Trade Licence Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Avalisation"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Court Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Lease Guarantee (villa IRS)"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Rental Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Credit Facilities"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Study Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Preference Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Retainage Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.sanctionsFollowUpTask("Local Outward BGI","Issue","Airline Agent Guarantee"));
			gt.addTest(lt.logoutBasic());
			
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
