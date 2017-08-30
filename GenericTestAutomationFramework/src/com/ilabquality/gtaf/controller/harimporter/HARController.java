package com.ilabquality.gtaf.controller.harimporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.GTAFApplController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.harimporter.HARImporter;

public class HARController implements Runnable{

	private GTAFApplController parent;
	private String fileName;
	private String suiteName;
	private String suiteDescription;
	private boolean harMustImportGET;
	private boolean harMustImportPOST;
	private String executionUser;
	private String servicesServerIP,servicesServerPort,uiServerIP,uiServerPort,workFlowServerIP,workFlowServerPort;
	private String keyCloakServerIP;
	private String keyCloakServerPort;
	public HARController(GTAFApplController parent, String fileName, String suiteName, String suiteDescription,boolean harMustImportGet, boolean harMustImportPOST, String executionUser,
							String servicesServerIP,String servicesServerPort,String uiServerIP,String uiServerPort,String workFlowServerIP,String workFlowServerPort,String keyCloakServerIP,
							String keyCloakServerPort){
		this.parent = parent;
		this.fileName = fileName;
		this.suiteName = suiteName;
		this.suiteDescription = suiteDescription;
		this.harMustImportGET = harMustImportGet;
		this.harMustImportPOST = harMustImportPOST;
		this.executionUser = executionUser;
		this.servicesServerIP = servicesServerIP;
		this.servicesServerPort = servicesServerPort;
		this.uiServerIP = uiServerIP;
		this.uiServerPort = uiServerPort;
		this.workFlowServerIP = workFlowServerIP;
		this.workFlowServerPort = workFlowServerPort;
		this.keyCloakServerIP = keyCloakServerIP;
		this.keyCloakServerPort = keyCloakServerPort;
	}
	public void doAction() {

	}
	
	private String getFileContents(){
		StringBuilder sb = new StringBuilder();
		try{
			FileReader fr = new FileReader(new File(fileName));
			BufferedReader br = new BufferedReader(fr);
		    String line = br.readLine();
		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    br.close();
		    fr.close();
		}catch(IOException ioe){
			LoggerFactory.getLogger(Runner.class).error("Unable to retrieve HAR file contents.",ioe);
		}
		return sb.toString();
	}
	@Override
	public void run() {
		 
		try {
			HARImporter.doImport(parent,getFileContents(),suiteName,suiteDescription,harMustImportGET,harMustImportPOST,executionUser,servicesServerIP,
								servicesServerPort,uiServerIP,uiServerPort,workFlowServerIP,workFlowServerPort,keyCloakServerIP,keyCloakServerPort);
		} catch (IOException | SQLException e) {
			LoggerFactory.getLogger(Runner.class).error("Unable to perform Import.",e);
		}
	}
}
