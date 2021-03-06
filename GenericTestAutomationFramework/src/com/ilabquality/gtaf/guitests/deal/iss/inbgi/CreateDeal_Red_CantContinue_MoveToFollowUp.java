package com.ilabquality.gtaf.guitests.deal.iss.inbgi;

import com.ilabquality.gtaf.controller.gui.GUIController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.guitests.helpers.GUITest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSingleTest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSuite;
import com.ilabquality.gtaf.guitests.logon.LogonTests;

public class CreateDeal_Red_CantContinue_MoveToFollowUp extends GUITest{

	private GuiTestReportSuite gt;
	public CreateDeal_Red_CantContinue_MoveToFollowUp(GUIController controller){
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
			
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MoveToFollowUp aa = 
					new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MoveToFollowUp(controller);
			aa.setMap(getMap());
			aa.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);

			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Payment Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Performance Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Advance Payment Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Labour Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Shipping Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Maintenance Guarantee"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Customs Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Retention Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Immigration Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Security Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Financial Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Trade Licence Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Avalisation"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Court Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Lease Guarantee (villa IRS)"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Rental Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Credit Facilities"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Study Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Preference Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Retainage Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.redEscalationCannotContinue("Inward BGI","Issue","Airline Agent Guarantee"));
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
