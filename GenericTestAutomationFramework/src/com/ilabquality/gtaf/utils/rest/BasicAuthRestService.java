package com.ilabquality.gtaf.utils.rest;

import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.gtafgui.variablesManager.helper.VariablesHelper;
import com.ilabquality.gtaf.utils.rest.interfaces.IRestService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class BasicAuthRestService implements IRestService {

	@Override
	public String callService(String webService, String userName, String password) {
		 String returnValue = "No Response";
		 LoggerFactory.getLogger(Runner.class).info("Calling SVC[" + webService + "] with user " + userName +" and password " + password);
			try {
				Client client = Client.create();
				client.setConnectTimeout(10000);
				client.addFilter(new HTTPBasicAuthFilter(userName, password));
				WebResource webResource = client
				   .resource(webService);//"http://10.110.52.189:8081/product/classifications");
				ClientResponse response = null;
				response = webResource.accept("application/json").get(ClientResponse.class);//http://10.110.52.189:9090/identityx/users
				if (response.getStatus() != 200) {
				   throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus() + ":" + response.getHeaders());
				}
			
				String output = response.getEntity(String.class);
				LoggerFactory.getLogger(Runner.class).info("Output from Server .... \n");
				returnValue = output;
				LoggerFactory.getLogger(Runner.class).info(output);
				client.destroy();
			  } catch (Exception e) {
				  LoggerFactory.getLogger(Runner.class).error("Exception!", e);
				  returnValue = "Exception" + e.getCause() + "\n" + e.getMessage();
				  LoggerFactory.getLogger(Runner.class).info("Unable to connect to ::::" + webService);
				//  e.printStackTrace();
			  }
			return returnValue;
	}

	@Override
	public String callPostService(String webService, String data, String userName, String password) {
		 String returnValue = "No Response";
		 LoggerFactory.getLogger(Runner.class).info("Calling SVC[" + webService + "] with postdata " + data.replace("\n", ""));
		 System.out.println("Calling SVC[" + webService + "] with postdata " + data.replace("\n", ""));
			try {
				Client client = Client.create();
				client.setConnectTimeout(10000);
				client.addFilter(new HTTPBasicAuthFilter(userName, password));
				WebResource webResource = client
				   .resource(webService);
				ClientResponse response = null;
				if (data.equalsIgnoreCase("")){
						response = webResource.post(ClientResponse.class);
				}else{
						response = webResource.type("application/json").post(ClientResponse.class,data.replace("\n", ""));
				}
				String output = response.getEntity(String.class);
				if (response.getStatus() != 200 && response.getStatus() != 201) {
				   throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus() + "\n"+ response.getHeaders());
				}else if (response.getStatus() == 401 ){
					  throw new RuntimeException("AUTH Failed : HTTP error code : "
								+ response.getStatus());
				}
				else if ( output.contains("Log in to Pangea Realm") ){
					throw new RuntimeException("Attempted to call a service [" + webService +"] without Authorization." + output);
				}
				
				LoggerFactory.getLogger(Runner.class).info("Output from Server .... \n");
				System.out.println("Output from Server .... \n" + output);
				returnValue = output;
				LoggerFactory.getLogger(Runner.class).info(output);
				//webResource.
				client.destroy();
			  } catch (Exception e) {
				  LoggerFactory.getLogger(Runner.class).error("Exception!", e);
				  returnValue = "Exception" + e.getCause() + "\n" + e.getMessage();
				  System.out.println("EXCEPTION!!!!!!!!!!!!!!!!!!!"  + e.getCause());
				 // e.printStackTrace();
			  }
			return returnValue;
	}

	@Override
	public String logon(String url, String userName, String password) {
		
		throw new RuntimeException("Logon should not be called ona Basic Auth!!");
	}

	@Override
	public void resetEnvironment() {
		System.out.println("Resetting Env");
		VariablesHelper vh = new VariablesHelper(null);
		String resetSvc = "";
		String resetWf = "";
	//	String restartSvc = "";
		String svcHealth="";
		if  (Runner.getRuntimeVariables().get("@{ServicesServerIP}").get(0).getLiveValue().contains("elasticbeanstalk")){
			resetSvc = vh.substituteURLs_Selenium("http://@{ServicesServerIP}:@{ServicesServerPort}/services/setup");
			resetWf = vh.substituteURLs_Selenium("http://@{WorkFlowServerIP}:@{WorkFlowServerPort}/workflow/setup");
	//		restartSvc = vh.substituteURLs_Selenium("http://@{ServicesServerIP}:@{ServicesServerPort}/services/restart");
			svcHealth = vh.substituteURLs_Selenium("http://@{ServicesServerIP}:@{ServicesServerPort}/services/health");
			
		}else{
			resetSvc = vh.substituteURLs_Selenium("http://@{ServicesServerIP}:@{ServicesServerPort}/setup");
			resetWf = vh.substituteURLs_Selenium("http://@{WorkFlowServerIP}:@{WorkFlowServerPort}/setup");
	//		restartSvc = vh.substituteURLs_Selenium("http://@{ServicesServerIP}:@{ServicesServerPort}/restart");
			svcHealth = vh.substituteURLs_Selenium("http://@{ServicesServerIP}:@{ServicesServerPort}/health");
		}
		 try {
			
			 Thread.sleep(2000);
			 callPostService(resetSvc,"", "john", "john");
			 Thread.sleep(2000);
			 System.out.println("Reset Services");
			 callPostService(resetWf,"", "john", "john");
			 System.out.println("Reset workflow"); Thread.sleep(2000);
			 int safetNet = 0;
			 while( !callService(svcHealth,"john", "john").contains("UP") && safetNet < 60 )
			 {
				Thread.sleep(1000);
				safetNet++;
				 System.out.println("Waiting for system to come up");
			 }
		} 
		 catch (InterruptedException e) {
		}
		 System.out.println("Done.");
	}

}
