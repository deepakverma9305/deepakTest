package com.ilabquality.gtaf.rest;

import java.util.ArrayList;

import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.GTAFApplController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.reporting.rest.CallServiceReport;
import com.ilabquality.gtaf.reporting.rest.MultiReport;
import com.ilabquality.gtaf.result.callService.CallServiceResult;
import com.ilabquality.gtaf.service.serviceTests.RestServiceTest;

public class RestController {

	private String _action;
	private GTAFApplController parent;
	private ArrayList<CallServiceResult> callServiceResult = new ArrayList<CallServiceResult>();
	private ArrayList<RestServiceTest> webServicesToTest = new ArrayList<RestServiceTest>();
	public RestController(GTAFApplController parent, ArrayList<RestServiceTest> webServicesToTest, String action,String xlsFileName) {
		this._action = action;
		this.webServicesToTest = webServicesToTest;
		System.out.println("Controller : Service count is " + webServicesToTest.size());
		this.parent = parent;
	}

	public void doAction()  {
		parent.setTaskDisplayer("Rest Svc Tests starting..");
		parent.setTaskDisplayer("Compiling Objects..");
		switch (_action) {
			case "Call Rest Svc":{
				for ( RestServiceTest webService : webServicesToTest ){
					callServiceResult.add(new CallServiceResult(webService.getName(),webService.isAlive()));
				}
				generateCallSvcReport();
				break;
			}
			case "Validate Rest WS":{
				try{
					for ( RestServiceTest webService : webServicesToTest ){
						switch(webService.getName()){
							case "IdentityXUsers":{
//								DateTime startTime = DateTime.now();
//								usersTestResults = (ArrayList<UsersTestResult>) webService.test();
//								DateTime endTime = DateTime.now();
//								double duration = Seconds.secondsBetween(startTime, endTime).getSeconds();
								break;
							}
							case "Product Classifications":{
//								DateTime startTime = DateTime.now();
//								productClassificationResults = (ArrayList<ProductClassificationResults>) webService.test();
//								DateTime endTime = DateTime.now();
//								double duration = Seconds.secondsBetween(startTime, endTime).getSeconds();
								break;
							}
						}
					}
					generateReport(webServicesToTest);
				}catch(Exception e){
					LoggerFactory.getLogger(Runner.class).error("Exception!", e);
				}
				break;
			}
		}
		parent.setTaskDisplayer("Idle...");
	}
	
	private void generateCallSvcReport() {
		CallServiceReport csr = new CallServiceReport();
		csr.set_reportContent(callServiceResult);
		csr.generateAndShow();

	}
	
	public void generateReport(ArrayList<RestServiceTest> services){
		if ( services.size() == 1 ){
			for ( RestServiceTest rst : services ){// Should only ever generate once. 
				rst.getReport().generateAndShow();
			}
		}else{
			//multiReport, so switch to the multi.
			MultiReport mr = new MultiReport();
			for ( RestServiceTest rst : services ){
				mr.addReport(rst.getReport());
			}
			
			mr.generateAndShow();
			
		}
	
		
	}
}
