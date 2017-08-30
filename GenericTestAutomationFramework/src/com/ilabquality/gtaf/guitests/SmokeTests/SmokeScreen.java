package com.ilabquality.gtaf.guitests.SmokeTests;

import com.ilabquality.gtaf.controller.gui.GUIController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.gtafAppl.Runner.TradeType;
import com.ilabquality.gtaf.guitests.customer.CustomerTests;
import com.ilabquality.gtaf.guitests.deal.cancel.generic.CancelDealGeneric;
import com.ilabquality.gtaf.guitests.deal.cancel.generic.CancelDeal_Red_CantContinue_MarkUnSuccessMaker;
import com.ilabquality.gtaf.guitests.helpers.GUITest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSingleTest;
import com.ilabquality.gtaf.guitests.helpers.GuiTestReportSuite;
import com.ilabquality.gtaf.guitests.logon.LogonTests;
import com.ilabquality.gtaf.guitests.sendToQuery.SendToQuery;

public class SmokeScreen extends GUITest {
	public SmokeScreen(GUIController controller){
		super(controller);
	}
	@Override
	public void runTests(GuiTestReportSuite gt) {
		try{
			this.baseUrl = Runner.getRuntimeVariables().get("@{SeleniumBaseURL}").get(0).getLiveValue();
			resetEnv(gt);
			driver.manage().window().maximize();
			driver.get(baseUrl);
			/**********************************************/
			LogonTests lt = new LogonTests(controller);
			lt.setMap(getMap());
			lt.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(lt.logoutBasic());
			gt.addTest(lt.logonBasic("jack","jack"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
//			/**********************************************/
			SendToQuery stq = new SendToQuery(controller);
			stq.setMap(getMap());
			stq.setServer(sl, driver, webDriver);
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(stq.sendDummyToQuery(TradeType.EMAIL,"Foreign Outward BGI"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(stq.sendDummyToQuery(TradeType.GENERIC_AMEND,"Foreign Outward BGI"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(stq.sendDummyToQuery(TradeType.CHANNEL_FOBGI_ISSUE,"Foreign Outward BGI"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(stq.sendDummyToQuery(TradeType.GENERIC_AMEND,"Import LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(stq.sendDummyToQuery(TradeType.CHANNEL_LC_ISSUE,"Import LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(stq.sendDummyToQuery(TradeType.GENERIC_AMEND,"Local Outward BGI"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(stq.sendDummyToQuery(TradeType.CHANNEL_LOBGI_ISSUE,"Local Outward BGI"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(stq.sendDummyToQuery(TradeType.SWIFT_HAND_DELIVERED,"Foreign Outward BGI"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
//			/**********************************************/
			CustomerTests ct = new CustomerTests(controller);
			ct.setMap(getMap());
			ct.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(ct.validateFieldPresence());
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
//			/**********************************************/
			
			doForeignOutwards(gt, lt);
			doLocalOutwards(gt, lt);
			doInwardBGI(gt, lt);
			doImportLC(gt,lt);
			doExportLC(gt,lt);
			//doAmendLocalOutwards(gt,lt); //TODO Amend not working correctly yet. 
			doCancellations(gt,lt);
			doExpiries(gt,lt);
			doPayClaim(gt,lt);
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction aa = 
					new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction(controller);
			aa.setMap(getMap());
			aa.setServer(sl, driver, webDriver);
			
			CancelDeal_Red_CantContinue_MarkUnSuccessMaker cdr = new CancelDeal_Red_CantContinue_MarkUnSuccessMaker(controller);
			//Create
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.noEscalationsRelease("Local Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			//Cancel
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdr.cancelDealDoRedCantContinueEscalation("Local Outward BGI","Cancel","Bid/Tender Bond",aa.getProdRef()));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			
		}catch(Exception e){
			gt.addTest(getActiveTest());
			e.printStackTrace();
		}

	}
	private void doPayClaim(GuiTestReportSuite gt, LogonTests lt) {
		try{
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction aa = 
					new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction(controller);
			aa.setMap(getMap());
			aa.setServer(sl, driver, webDriver);
			
			com.ilabquality.gtaf.guitests.deal.iss.payclaim.generic.PayClaimGeneric aaa = 
					new com.ilabquality.gtaf.guitests.deal.iss.payclaim.generic.PayClaimGeneric(controller);
			aaa.setMap(getMap());
			aaa.setServer(sl, driver, webDriver);
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.noEscalationsRelease("Import LC","Issue","Import LC"));
			gt.addTest(lt.logoutBasic());
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aaa.doDocEx("Import LC","Document Examination","Import LC",aa.getProdRef()));
			gt.addTest(lt.logoutBasic());
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aaa.doPayClaim("Import LC","Pay/Accept","Import LC",aa.getProdRef()));
			gt.addTest(lt.logoutBasic());
		}catch(Exception e){
			
		}
		
	}

	private void doExpiries(GuiTestReportSuite gt, LogonTests lt) {
		try{
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction aa = 
					new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction(controller);
			aa.setMap(getMap());
			aa.setServer(sl, driver, webDriver);
			
			com.ilabquality.gtaf.guitests.deal.expiry.generic.ExpireDealGeneric aaa = 
					new com.ilabquality.gtaf.guitests.deal.expiry.generic.ExpireDealGeneric(controller);
			aaa.setMap(getMap());
			aaa.setServer(sl, driver, webDriver);
			
			
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.noEscalationsRelease("Local Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aaa.expireDealLocalOutwards("Local Outward BGI","Cancel","Bid/Tender Bond",aa.getProdRef()));
			gt.addTest(lt.logoutBasic());
			/******************************************/
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.noEscalationsRelease("Foreign Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aaa.expireDealLocalOutwards("Foreign Outward BGI","Cancel","Bid/Tender Bond",aa.getProdRef()));
			gt.addTest(lt.logoutBasic());
			/******************************************/
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.noEscalationsRelease("Import LC","Issue","Import LC"));
			gt.addTest(lt.logoutBasic());
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aaa.expireDealLocalOutwards("Import LC","Cancel","Import LC",aa.getProdRef()));
			gt.addTest(lt.logoutBasic());
			/******************************************/
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.noEscalationsRelease("Export LC","Issue","Export LC"));
			gt.addTest(lt.logoutBasic());
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aaa.expireDealLocalOutwards("Export LC","Cancel","Export LC",aa.getProdRef()));
			gt.addTest(lt.logoutBasic());
			/******************************************/
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.noEscalationsRelease("Inward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aaa.expireDealLocalOutwards("Inward BGI","Cancel","Bid/Tender Bond",aa.getProdRef()));
			gt.addTest(lt.logoutBasic());

		}catch(Exception e){
			
		}
		
	}
	private void doCancellations(GuiTestReportSuite gt, LogonTests lt) {
		try{
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction aa = 
					new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction(controller);
			aa.setMap(getMap());
			aa.setServer(sl, driver, webDriver);
			
			com.ilabquality.gtaf.guitests.deal.cancel.generic.CancelDealGeneric aaa = 
					new com.ilabquality.gtaf.guitests.deal.cancel.generic.CancelDealGeneric(controller);
			aaa.setMap(getMap());
			aaa.setServer(sl, driver, webDriver);
			
			
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.noEscalationsRelease("Local Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aaa.cancelDealLocalOutwards("Local Outward BGI","Cancel","Bid/Tender Bond",aa.getProdRef()));
			gt.addTest(lt.logoutBasic());
			/******************************************/
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.noEscalationsRelease("Foreign Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aaa.cancelDealLocalOutwards("Foreign Outward BGI","Cancel","Bid/Tender Bond",aa.getProdRef()));
			gt.addTest(lt.logoutBasic());
			/******************************************/
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.noEscalationsRelease("Import LC","Issue","Import LC"));
			gt.addTest(lt.logoutBasic());
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aaa.cancelDealLocalOutwards("Import LC","Cancel","Import LC",aa.getProdRef()));
			gt.addTest(lt.logoutBasic());
			/******************************************/
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.noEscalationsRelease("Export LC","Issue","Export LC"));
			gt.addTest(lt.logoutBasic());
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aaa.cancelDealLocalOutwards("Export LC","Cancel","Export LC",aa.getProdRef()));
			gt.addTest(lt.logoutBasic());
			/******************************************/
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aa.noEscalationsRelease("Inward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(aaa.cancelDealLocalOutwards("Inward BGI","Cancel","Bid/Tender Bond",aa.getProdRef()));
			gt.addTest(lt.logoutBasic());

		}catch(Exception e){
			
		}
		
	}
	public void doLocalOutwards(GuiTestReportSuite gt, LogonTests lt)
	{
		try{
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReworkInProduction cdrfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReworkInProduction(controller);
			cdrfo.setMap(getMap());
			cdrfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdrfo.noEscalationsRework("Local Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction cdrrfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction(controller);
			cdrrfo.setMap(getMap());
			cdrrfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdrrfo.noEscalationsRelease("Local Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Amber_CanContinue cdrafo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Amber_CanContinue(controller);
			cdrafo.setMap(getMap());
			cdrafo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdrafo.amberEscalationMarkAsYesOnReleaseAndRelease("Local Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Amber_MarkAsUnsuccessfulChecker cdamfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Amber_MarkAsUnsuccessfulChecker(controller);
			cdamfo.setMap(getMap());
			cdamfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdamfo.amberEscalationMarkAsUnsuccesfulChecker("Local Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MoveToFollowUp cddfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MoveToFollowUp(controller);
			cddfo.setMap(getMap());
			cddfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cddfo.redEscalationCannotContinue("Local Outward BGI","Issue","Payment Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_CreditEscalations cdcefo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_CreditEscalations(controller);
			cdcefo.setMap(getMap());
			cdcefo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdcefo.creditEscalation("Local Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MarkUnSuccessMaker cdmfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MarkUnSuccessMaker(controller);
			cdmfo.setMap(getMap());
			cdmfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdmfo.redEscalationMarkAsUnsuccessfulMaker("Local Outward BGI","Issue","Payment Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_RedAndAmberFollowUpDueDateCheck fudcfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_RedAndAmberFollowUpDueDateCheck(controller);
			fudcfo.setMap(getMap());
			fudcfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(fudcfo.redAndAmberFollowUpDueDateCheck("Local Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Sanctions_FollowUpTask sfutfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Sanctions_FollowUpTask(controller);
			sfutfo.setMap(getMap());
			sfutfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(sfutfo.sanctionsFollowUpTask("Local Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_LegalFollowUpTask lfutfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_LegalFollowUpTask(controller);
			lfutfo.setMap(getMap());
			lfutfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(lfutfo.legalFollowUpTask("Local Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_SanctionsLegalAndCredit slacfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_SanctionsLegalAndCredit(controller);
			slacfo.setMap(getMap());
			slacfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(slacfo.sanctionsLegalAndCreditEscalations("Local Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			
		}catch(Exception e){
			
		}
	}
	
	public void doImportLC(GuiTestReportSuite gt, LogonTests lt)
	{
		try{
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReworkInProduction cdrfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReworkInProduction(controller);
			cdrfo.setMap(getMap());
			cdrfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdrfo.noEscalationsRework("Import LC","Issue","Import LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction cdrrfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction(controller);
			cdrrfo.setMap(getMap());
			cdrrfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdrrfo.noEscalationsRelease("Import LC","Issue","Import LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Amber_CanContinue cdrafo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Amber_CanContinue(controller);
			cdrafo.setMap(getMap());
			cdrafo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdrafo.amberEscalationMarkAsYesOnReleaseAndRelease("Import LC","Issue","Import LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Amber_MarkAsUnsuccessfulChecker cdamfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Amber_MarkAsUnsuccessfulChecker(controller);
			cdamfo.setMap(getMap());
			cdamfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdamfo.amberEscalationMarkAsUnsuccesfulChecker("Import LC","Issue","Import LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MoveToFollowUp cddfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MoveToFollowUp(controller);
			cddfo.setMap(getMap());
			cddfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cddfo.redEscalationCannotContinue("Import LC","Issue","Import LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_CreditEscalations cdcefo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_CreditEscalations(controller);
			cdcefo.setMap(getMap());
			cdcefo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdcefo.creditEscalation("Import LC","Issue","Import LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MarkUnSuccessMaker cdmfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MarkUnSuccessMaker(controller);
			cdmfo.setMap(getMap());
			cdmfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdmfo.redEscalationMarkAsUnsuccessfulMaker("Import LC","Issue","Import LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_RedAndAmberFollowUpDueDateCheck fudcfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_RedAndAmberFollowUpDueDateCheck(controller);
			fudcfo.setMap(getMap());
			fudcfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(fudcfo.redAndAmberFollowUpDueDateCheck("Import LC","Issue","Import LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Sanctions_FollowUpTask sfutfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Sanctions_FollowUpTask(controller);
			sfutfo.setMap(getMap());
			sfutfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(sfutfo.sanctionsFollowUpTask("Import LC","Issue","Import LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_SanctionsLegalAndCredit slacfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_SanctionsLegalAndCredit(controller);
			slacfo.setMap(getMap());
			slacfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(slacfo.sanctionsLegalAndCreditEscalations("Import LC","Issue","Import LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			
		}catch(Exception e){
			
		}
	}

	public void doExportLC(GuiTestReportSuite gt, LogonTests lt)
	{
		try{
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReworkInProduction cdrfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReworkInProduction(controller);
			cdrfo.setMap(getMap());
			cdrfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdrfo.noEscalationsRework("Export LC","Issue","Export LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction cdrrfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction(controller);
			cdrrfo.setMap(getMap());
			cdrrfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdrrfo.noEscalationsRelease("Export LC","Issue","Export LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MoveToFollowUp cddfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MoveToFollowUp(controller);
			cddfo.setMap(getMap());
			cddfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cddfo.redEscalationCannotContinue("Export LC","Issue","Export LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_CreditEscalations cdcefo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_CreditEscalations(controller);
			cdcefo.setMap(getMap());
			cdcefo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdcefo.creditEscalation("Export LC","Issue","Export LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MarkUnSuccessMaker cdmfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MarkUnSuccessMaker(controller);
			cdmfo.setMap(getMap());
			cdmfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdmfo.redEscalationMarkAsUnsuccessfulMaker("Export LC","Issue","Export LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Sanctions_FollowUpTask sfutfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Sanctions_FollowUpTask(controller);
			sfutfo.setMap(getMap());
			sfutfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(sfutfo.sanctionsFollowUpTask("Export LC","Issue","Export LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_SanctionsLegalAndCredit slacfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_SanctionsLegalAndCredit(controller);
			slacfo.setMap(getMap());
			slacfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(slacfo.sanctionsLegalAndCreditEscalations("Export LC","Issue","Export LC"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			
		}catch(Exception e){
			
		}
	}

	public void doInwardBGI(GuiTestReportSuite gt, LogonTests lt)
	{
		try{
//			/**********************************************/
//			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_LegalFollowUpTask lfutfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_LegalFollowUpTask(controller);
//			lfutfo.setMap(getMap());
//			lfutfo.setServer(sl, driver, webDriver);
//			gt.addTest(lt.logonBasic("john","john"));
//			gt.addTest(lfutfo.legalFollowUpTask("Inward BGI","Issue","Bid/Tender Bond"));
//			gt.addTest(lt.logoutBasic());
//			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReworkInProduction cdrfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReworkInProduction(controller);
			cdrfo.setMap(getMap());
			cdrfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdrfo.noEscalationsRework("Inward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction cdrrfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction(controller);
			cdrrfo.setMap(getMap());
			cdrrfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdrrfo.noEscalationsRelease("Inward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MoveToFollowUp cddfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MoveToFollowUp(controller);
			cddfo.setMap(getMap());
			cddfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cddfo.redEscalationCannotContinue("Inward BGI","Issue","Payment Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_CreditEscalations cdcefo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_CreditEscalations(controller);
			cdcefo.setMap(getMap());
			cdcefo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdcefo.creditEscalation("Inward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MarkUnSuccessMaker cdmfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MarkUnSuccessMaker(controller);
			cdmfo.setMap(getMap());
			cdmfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdmfo.redEscalationMarkAsUnsuccessfulMaker("Inward BGI","Issue","Payment Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			
	
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Sanctions_FollowUpTask sfutfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Sanctions_FollowUpTask(controller);
			sfutfo.setMap(getMap());
			sfutfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(sfutfo.sanctionsFollowUpTask("Inward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_SanctionsLegalAndCredit slacfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_SanctionsLegalAndCredit(controller);
			slacfo.setMap(getMap());
			slacfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(slacfo.sanctionsLegalAndCreditEscalations("Inward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			
		}catch(Exception e){
			
		}
	}

	
	
	public void doForeignOutwards(GuiTestReportSuite gt, LogonTests lt){
		try{
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReworkInProduction cdrfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReworkInProduction(controller);
			cdrfo.setMap(getMap());
			cdrfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdrfo.noEscalationsRework("Foreign Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction cdrrfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_ReleaseInProduction(controller);
			cdrrfo.setMap(getMap());
			cdrrfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdrrfo.noEscalationsRelease("Foreign Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Amber_CanContinue cdrafo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Amber_CanContinue(controller);
			cdrafo.setMap(getMap());
			cdrafo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdrafo.amberEscalationMarkAsYesOnReleaseAndRelease("Foreign Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Amber_MarkAsUnsuccessfulChecker cdamfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Amber_MarkAsUnsuccessfulChecker(controller);
			cdamfo.setMap(getMap());
			cdamfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdamfo.amberEscalationMarkAsUnsuccesfulChecker("Foreign Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MoveToFollowUp cddfo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MoveToFollowUp(controller);
			cddfo.setMap(getMap());
			cddfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cddfo.redEscalationCannotContinue("Foreign Outward BGI","Issue","Payment Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_CreditEscalations cdcefo = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_CreditEscalations(controller);
			cdcefo.setMap(getMap());
			cdcefo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdcefo.creditEscalation("Foreign Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MarkUnSuccessMaker cdmfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Red_CantContinue_MarkUnSuccessMaker(controller);
			cdmfo.setMap(getMap());
			cdmfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(cdmfo.redEscalationMarkAsUnsuccessfulMaker("Foreign Outward BGI","Issue","Payment Guarantee"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_RedAndAmberFollowUpDueDateCheck fudcfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_RedAndAmberFollowUpDueDateCheck(controller);
			fudcfo.setMap(getMap());
			fudcfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(fudcfo.redAndAmberFollowUpDueDateCheck("Foreign Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Sanctions_FollowUpTask sfutfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_Sanctions_FollowUpTask(controller);
			sfutfo.setMap(getMap());
			sfutfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(sfutfo.sanctionsFollowUpTask("Foreign Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_LegalFollowUpTask lfutfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_LegalFollowUpTask(controller);
			lfutfo.setMap(getMap());
			lfutfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(lfutfo.legalFollowUpTask("Foreign Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
			/**********************************************/
			com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_SanctionsLegalAndCredit slacfo  = new com.ilabquality.gtaf.guitests.deal.iss.generic.CreateDeal_SanctionsLegalAndCredit(controller);
			slacfo.setMap(getMap());
			slacfo.setServer(sl, driver, webDriver);
			gt.addTest(lt.logonBasic("john","john"));
			gt.addTest(slacfo.sanctionsLegalAndCreditEscalations("Foreign Outward BGI","Issue","Bid/Tender Bond"));
			gt.addTest(lt.logoutBasic());
			resetEnv(gt);
		}catch(Exception e){
			
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
