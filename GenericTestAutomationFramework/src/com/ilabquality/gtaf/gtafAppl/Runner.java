package com.ilabquality.gtaf.gtafAppl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Properties;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.controller.gui.GUIController;
import com.ilabquality.gtaf.controller.testpack.TestPackController;
import com.ilabquality.gtaf.guitests.GUITestFactory;
import com.ilabquality.gtaf.packmanager.PackManagerFactory;
import com.ilabquality.gtaf.testsuite.Variable;
import com.ilabquality.gtaf.utils.rest.BasicAuthRestService;
import com.ilabquality.gtaf.utils.rest.KeyCloakRestService;
import com.ilabquality.gtaf.utils.rest.interfaces.IRestService;

public class Runner {
private static boolean isCLI = false;
private static boolean hasInit = false;
public static int cliExitCode= 0;
public static String seleniumMode;
private static Hashtable<String,ArrayList<Variable>> runTimeVariables = new Hashtable<>();
public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd-MMM-yyyy");
public static IRestService serviceConnector;
public enum TradeType {
						EMAIL,
						CHANNEL_FOBGI_ISSUE,
						SWIFT_HAND_DELIVERED,
						CHANNEL_LOBGI_ISSUE,
						CHANNEL_LC_ISSUE,
						GENERIC_AMEND
					 };
/**
 * @param args
 * @throws Exception
 */
	public static void main(String[] args) throws Exception {
		System.out.println(args.length);
		//initAuthorizationProvider();
		// -Dprism.order=j2d NVidia Drivers wonky, if it crashes on launch with a *prism* error, add this to the VM Args. 

		try{
			 String[] cmds = args.toString().split("-");
			 LoggerFactory.getLogger(Runner.class).info("Parsing Commands.");
			 System.out.println("1");
			 for ( int j = 0; j < cmds.length;j++){
				 LoggerFactory.getLogger(Runner.class).info(cmds[j]);
				 System.out.println(cmds[j].toString());
			 }
			 	isCLI  = true;
				LoggerFactory.getLogger(Runner.class).info((String)args[0]);
				for (int i =0 ; i < args.length; i++){
					String[] stuffs = args[i].split("=");
					String cmd=stuffs[0];
					String val = stuffs[1];
					switch (cmd.toLowerCase()){
					case "-runpack":{
						 LoggerFactory.getLogger(Runner.class).info("RunPack");
						preInit(null);
						 LoggerFactory.getLogger(Runner.class).info(val);
						 LoggerFactory.getLogger(Runner.class).info("Instantiating controller");
						TestPackController tpc = new TestPackController(null, val);
						 LoggerFactory.getLogger(Runner.class).info("Executing");
						tpc.doAction();
						 LoggerFactory.getLogger(Runner.class).info("Done");
						break;
					}
					case "-runselenium":{
						 LoggerFactory.getLogger(Runner.class).info("RunSelenium");
						 System.out.println("RunSelenium");
						preInit(null);
						 LoggerFactory.getLogger(Runner.class).info(val);
						 LoggerFactory.getLogger(Runner.class).info("Instantiating controller");
						GUIController gc = new GUIController(null, new GUITestFactory().getGUITestSuite(val));
						 LoggerFactory.getLogger(Runner.class).info("Executing");
						 gc.run();
						 LoggerFactory.getLogger(Runner.class).info("Done");
						break;
					}
						case "-listpacks":{
							preInit(null);
							listPacks();
							break;
						}
						case "-config.file":{
							 LoggerFactory.getLogger(Runner.class).info("Set config File");
							preInit(val);
							break;
						}
						default:{
							clearScreenForCLI(args);
						}	
					}
				}
		}catch(Exception e){
			e.printStackTrace();
			 LoggerFactory.getLogger(Runner.class).info("Unable to parse arguments,launching GUI");
			isCLI = false;
			preInit(null);
			GTAFApplication gtaf= new GTAFApplication();
			gtaf.doLaunch(args);
		}
		LoggerFactory.getLogger(Runner.class).info("Stopped.");
	System.exit(cliExitCode);
	}
	
	private static void preInit(String fileName){
		if (!hasInit ){
			hasInit = true;
			Runner.getRuntimeVariables().clear();
			System.out.println("PreInit");
			//Load properties
			Properties prop = new Properties();
			InputStream input = null;
			try {
				if ( fileName == null){
					input = String.class.getResourceAsStream("/Auto_Test.properties");
				}else{
					input = new FileInputStream(fileName);
				}
				// load properties file
				prop.load(input); 
				Runner.getRuntimeVariables().put("@{ServicesServerIP}", new ArrayList<Variable>(Arrays.asList(new Variable("@{ServicesServerIP}",prop.getProperty("ServicesServerIP"),true))));
				Runner.getRuntimeVariables().put("@{ServicesServerPort}",new ArrayList<Variable>(Arrays.asList(new Variable("@{ServicesServerPort}",prop.getProperty("ServicesServerPort"),true))));
				Runner.getRuntimeVariables().put("@{WorkFlowServerIP}", new ArrayList<Variable>(Arrays.asList(new Variable("@{WorkFlowServerIP}",prop.getProperty("WorkFlowServerIP"),true))));
				Runner.getRuntimeVariables().put("@{WorkFlowServerPort}",new ArrayList<Variable>(Arrays.asList(new Variable("@{WorkFlowServerPort}",prop.getProperty("WorkFlowServerPort"),true))));
				Runner.getRuntimeVariables().put("@{UIServerIP}", new ArrayList<Variable>(Arrays.asList(new Variable("@{UIServerIP}",prop.getProperty("UIServerIP"),true))));
				Runner.getRuntimeVariables().put("@{UIServerPort}",new ArrayList<Variable>(Arrays.asList(new Variable("@{UIServerPort}",prop.getProperty("UIServerPort"),true))));
				Runner.getRuntimeVariables().put("@{TestDBServerIP}",new ArrayList<Variable>(Arrays.asList(new Variable("@{TestDBServerIP}",prop.getProperty("TestDBServerIP"),true))));
				Runner.getRuntimeVariables().put("@{TestDBServerPort}",new ArrayList<Variable>(Arrays.asList(new Variable("@{TestDBServerPort}",prop.getProperty("TestDBServerPort"),true))));
				Runner.getRuntimeVariables().put("@{TestDBName}",new ArrayList<Variable>(Arrays.asList(new Variable("@{TestDBName}",prop.getProperty("TestDBName"),true))));
				Runner.getRuntimeVariables().put("@{TestDBUserName}",new ArrayList<Variable>(Arrays.asList(new Variable("@{TestDBUserName}",prop.getProperty("TestDBUserName"),true))));
				Runner.getRuntimeVariables().put("@{TestDBUserPassword}",new ArrayList<Variable>(Arrays.asList(new Variable("@{TestDBUserPassword}",prop.getProperty("TestDBUserPassword"),true))));
				Runner.getRuntimeVariables().put("@{ServiceCallFailTimeout}",new ArrayList<Variable>(Arrays.asList(new Variable("@{ServiceCallFailTimeout}",prop.getProperty("ServiceCallFailTimeout"),true))));
				
				//KeyCloak Items
				Runner.getRuntimeVariables().put("@{KeyCloakServerIP}",new ArrayList<Variable>(Arrays.asList(new Variable("@{KeyCloakServerIP}",prop.getProperty("KeyCloakServerIP"),true))));
				Runner.getRuntimeVariables().put("@{KeyCloakServerPort}",new ArrayList<Variable>(Arrays.asList(new Variable("@{KeyCloakServerPort}",prop.getProperty("KeyCloakServerPort"),true))));

				//Selenium Items
				Runner.getRuntimeVariables().put("@{SeleniumMode}",new ArrayList<Variable>(Arrays.asList(new Variable("@{SeleniumMode}",prop.getProperty("SeleniumMode"),true))));
				Runner.getRuntimeVariables().put("@{WebdriverLocation}",new ArrayList<Variable>(Arrays.asList(new Variable("@{WebdriverLocation}",prop.getProperty("WebdriverLocation"),true))));
				Runner.getRuntimeVariables().put("@{SeleniumHUBUrl}",new ArrayList<Variable>(Arrays.asList(new Variable("@{SeleniumHUBUrl}",prop.getProperty("SeleniumHUBUrl"),true))));
				Runner.getRuntimeVariables().put("@{SeleniumBrowser}",new ArrayList<Variable>(Arrays.asList(new Variable("@{SeleniumBrowser}",prop.getProperty("SeleniumBrowser"),true))));
				Runner.getRuntimeVariables().put("@{SeleniumObjectMap}",new ArrayList<Variable>(Arrays.asList(new Variable("@{SeleniumObjectMap}",prop.getProperty("SeleniumObjectMap"),true))));
				Runner.getRuntimeVariables().put("@{SeleniumBaseURL}",new ArrayList<Variable>(Arrays.asList(new Variable("@{SeleniumBaseURL}",prop.getProperty("SeleniumBaseURL"),true))));
				
				
				
				//Email items
				Runner.getRuntimeVariables().put("@{EmailServerIP}",new ArrayList<Variable>(Arrays.asList(new Variable("@{EmailServerIP}",prop.getProperty("EmailServerIP"),true))));
				Runner.getRuntimeVariables().put("@{EmailServerPort}",new ArrayList<Variable>(Arrays.asList(new Variable("@{EmailServerPort}",prop.getProperty("EmailServerPort"),true))));
				Runner.getRuntimeVariables().put("@{EmailServerUserName}",new ArrayList<Variable>(Arrays.asList(new Variable("@{EmailServerUserName}",prop.getProperty("EmailServerUserName"),true))));
				Runner.getRuntimeVariables().put("@{EmailServerPassword}",new ArrayList<Variable>(Arrays.asList(new Variable("@{EmailServerPassword}",prop.getProperty("EmailServerPassword"),true))));
				Runner.getRuntimeVariables().put("@{EmailAccountAddress}",new ArrayList<Variable>(Arrays.asList(new Variable("@{EmailAccountAddress}",prop.getProperty("EmailAccountAddress"),true))));
				Runner.getRuntimeVariables().put("@{AuthenticationProvider}",new ArrayList<Variable>(Arrays.asList(new Variable("@{AuthenticationProvider}",prop.getProperty("AuthenticationProvider"),true))));
				Runner.getRuntimeVariables().put("@{EmailAttachmentFilePath}",new ArrayList<Variable>(Arrays.asList(new Variable("@{EmailAttachmentFilePath}",prop.getProperty("EmailAttachmentFilePath"),true))));
				Runner.getRuntimeVariables().put("@{MustSendEmailToInitiateTests}",new ArrayList<Variable>(Arrays.asList(new Variable("@{MustSendEmailToInitiateTests}",prop.getProperty("MustSendEmailToInitiateTests"),true))));
				
				
				
			//	System.out.println(Runner.getRuntimeVariables().toString());
				//System.out.println(Runner.getRuntimeVariables().get("@{MustSendEmailToInitiateTests}"));
			} catch (IOException ex) {
				ex.printStackTrace();
				System.exit(2);
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			serviceConnector = Runner.initAuthorizationProvider();
			System.out.println("Service connector is set to type " + serviceConnector);
		}
	}
	
	private static void listPacks(){
		clearScreenForCLI(null);
		int counter = 1;
		System.out.println("Available Packs for Execution");
		for ( String p : new PackManagerFactory().getPackNames()){
			System.out.println(counter + ".\t" + p);
			counter++;
		}
		System.out.println("**************************************************************************************************|");
	}
	
	/******************************************************************************************************************************************************************************/
	private static void clearScreenForCLI(String[] args){
		System.out.print("\033[H\033[2J");
		System.out.flush();
		System.out.println("|*************************************************************************************************|");
		System.out.println("|**********************************Pangea Test Automation Framework*******************************|");
		System.out.println("|*************************************************************************************************|");
		System.out.println("");
		System.out.println("Invalid Option - " + (String)args[0]);
		System.out.println("");
		System.out.println("Supported options are");
		System.out.println("         1. RunPack        - Execute a predefined pack");
		System.out.println("            1.1 %PackName% - Execute the pack defined in the %PackName% variable.");
		System.out.println("         2. ListPacks=All      - Display a list of current packs.");
		System.out.println("e.g. java -jar Auto_Test.jar runpack \"Validate SA Services\"");
		System.out.println("**************************************************************************************************|");;
	}
	
	public static IRestService initAuthorizationProvider(){
		IRestService svc = null;
		switch (Runner.getRuntimeVariables().get("@{AuthenticationProvider}").get(0).getLiveValue()){
			case "KeyCloak":{
				svc = new KeyCloakRestService();
				System.out.println("Using KeyCloak Authorization");
				break;
			}
			case "BasicAuth":{
				svc = new BasicAuthRestService();
				System.out.println("Using Basic Authentication");
				break;
			}
			case"xx":{
				//Any further provider implementations. 
			}
			default:{
				svc = new BasicAuthRestService();
				System.out.println("No Authorization requested, defaulting to Basic Auth.");
				break;
			}
		}
		return svc;
	}
	
	public static synchronized Hashtable<String,ArrayList<Variable>> getRuntimeVariables(){
		return Runner.runTimeVariables;
	}
	
	
	

}
