package com.ilabquality.gtaf.utils.rest;

import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.GTAFApplication;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.gtafgui.variablesManager.helper.VariablesHelper;
import com.ilabquality.gtaf.utils.rest.interfaces.IRestService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class KeyCloakRestService implements IRestService{
	 
	 public String callService(String webService, String userName, String password) {
		 if ( webService.contains("/auth/realms/Pangea_Realm/")){
			 //We are doing a logon, call the logon method and set the keycloak wrapper.Any other calls to this realm need to be handled accordingly. 
			 return logon(webService,userName,password);
		 }
		 String returnValue = "No Response";
		 LoggerFactory.getLogger(Runner.class).info("Calling SVC[" + webService + "]");
			try {
				Client client = Client.create();
				client.setConnectTimeout(10000);
				//client.addFilter(new HTTPBasicAuthFilter(userName, password));
				
				WebResource webResource = client
				   .resource(webService);//"http://10.110.52.189:8081/product/classifications");
				ClientResponse response = null;
				if ( webService.contains("setup") || webService.contains("health") || webService.contains("restart")){ //Ensure auth is only applied to the pangeaapp WS calls, not the env restart or health checks.
					response = webResource.accept("application/json").get(ClientResponse.class);//http://10.110.52.189:9090/identityx/users
				}else{
				
					response = webResource.header("Authorization", "Bearer " + GTAFApplication.KEYCLOAK_WRAPPER.getServerToken()).accept("application/json").get(ClientResponse.class);//http://10.110.52.189:9090/identityx/users
				}
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
				  e.printStackTrace();
			  }
			return returnValue;
			}
	 
	 public String callPostService(String webService,String data, String userName, String password) {
		 int d=1;
		 if ( webService.contains("/auth/realms/Pangea_Realm/")){
			 //We are doing a logon, call the logon method and set the keycloak wrapper.
			 return logon(webService,userName,password);
		 }
		 String returnValue = "No Response";
		 LoggerFactory.getLogger(Runner.class).info("Calling SVC[" + webService + "] with postdata " + data.replace("\n", ""));
		 System.out.println("Calling SVC[" + webService + "] with postdata " + data.replace("\n", ""));
			try {
				Client client = Client.create();
				client.setConnectTimeout(10000);
				WebResource webResource = client.resource(webService);//"http://10.110.52.189:8081/product/classifications");
				webResource.getRequestBuilder().header("Content-Type", "application/x-www-form-urlencoded");
				webResource.getRequestBuilder().header("Cache-Control", "no-cache");
				ClientResponse response = null;
				
				if (data.equalsIgnoreCase("")){
					if ( webService.contains("setup") || webService.contains("health") || webService.contains("restart")){ //Ensure auth is only applied to the pangeaapp WS calls, not the env restart or health checks.
						response = webResource.post(ClientResponse.class);//
					}else{
						response = webResource.header("Authorization", "Bearer " + GTAFApplication.KEYCLOAK_WRAPPER.getServerToken()).post(ClientResponse.class);
					}
				}else{
					if ( webService.contains("setup") || webService.contains("health") || webService.contains("restart")){ //Ensure auth is only applied to the pangeaapp WS calls, not the env restart or health checks.
						response = webResource.type("application/json").post(ClientResponse.class,data.replace("\n", ""));//http://10.110.52.189:9090/identityx/users
					}else{
						System.out.println(GTAFApplication.KEYCLOAK_WRAPPER.getServerToken());
						response = webResource.type("application/json").header("Authorization", "Bearer " + GTAFApplication.KEYCLOAK_WRAPPER.getServerToken()).post(ClientResponse.class,data.replace("\n", ""));//http://10.110.52.189:9090/identityx/users
					}
				}
				String output = response.getEntity(String.class);
				if (response.getStatus() != 200 && response.getStatus() != 201) {
				   throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus() + "\n"+ response.getHeaders());
				}else if (response.getStatus() == 401 ){
					  throw new RuntimeException("AUTH Failed : HTTP error code : "
								+ response.getStatus());
				}else if ( output.contains("Log in to Pangea Realm") ){
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
				  e.printStackTrace();

			  }
			return returnValue;
			}
	 public boolean isAlive(String webService) {
		 boolean returnValue = false;
			try {

				Client client = Client.create();	
				client.addFilter(new HTTPBasicAuthFilter("john", "john"));
				client.setConnectTimeout(5);
				WebResource webResource = client
				   .resource(webService);//"http://10.110.52.189:8081/product/classifications");
				ClientResponse response = webResource.accept("application/json")
		                   .get(ClientResponse.class);

				if (response.getStatus() != 200) {
				   throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
				}
				String output = response.getEntity(String.class);
				LoggerFactory.getLogger(Runner.class).info("Output from Server .... \n");
				returnValue = true;
				LoggerFactory.getLogger(Runner.class).info(output);
				client.destroy();
			  } catch (Exception e) {
				  LoggerFactory.getLogger(Runner.class).error("Exception!", e);
				  returnValue = false;
				  e.printStackTrace();
			  }
			LoggerFactory.getLogger(Runner.class).info("Service was " + returnValue);
			return returnValue;
		}
	 
		@Override
		public void resetEnvironment() {
			System.out.println("Resetting Env");
			VariablesHelper vh = new VariablesHelper(null);
			String resetSvc = "";
			String resetWf = "";
			//String restartSvc = "";
			String svcHealth="";
			if  (Runner.getRuntimeVariables().get("@{ServicesServerIP}").get(0).getLiveValue().contains("elasticbeanstalk")){
				resetSvc = vh.substituteURLs_Selenium("http://@{ServicesServerIP}:@{ServicesServerPort}/services/setup");
				resetWf = vh.substituteURLs_Selenium("http://@{WorkFlowServerIP}:@{WorkFlowServerPort}/workflow/setup");
			//	restartSvc = vh.substituteURLs_Selenium("http://@{ServicesServerIP}:@{ServicesServerPort}/services/restart");
				svcHealth = vh.substituteURLs_Selenium("http://@{ServicesServerIP}:@{ServicesServerPort}/services/health");
			}else{
				resetSvc = vh.substituteURLs_Selenium("http://@{ServicesServerIP}:@{ServicesServerPort}/setup");
				resetWf = vh.substituteURLs_Selenium("http://@{WorkFlowServerIP}:@{WorkFlowServerPort}/setup");
				//restartSvc = vh.substituteURLs_Selenium("http://@{ServicesServerIP}:@{ServicesServerPort}/restart");
				svcHealth = vh.substituteURLs_Selenium("http://@{ServicesServerIP}:@{ServicesServerPort}/health");
			}
			 try {
				 System.out.println("Reset Services");
				 callPostService(resetSvc,"", "john", "john");
				 Thread.sleep(2000);
				 System.out.println("Reset workflow"); Thread.sleep(2000);
				 
				 callPostService(resetWf,"", "john", "john");
				 int safetNet = 0;
				 while( !callService(svcHealth,"john", "john").contains("UP") && safetNet < 60 )
				 {
					Thread.sleep(1000);
					safetNet++;
					 System.out.println("Waiting for system to come up");
				 }
			} catch (InterruptedException e) {
			}
			 System.out.println("Done.");
		}
	public String logon(String url, String userName , String password) {
		// String url = "http://10.110.43.105:8095/auth/realms/Pangea_Realm/protocol/openid-connect/token";
		JSONObject output= null;
			try {
				Client client = Client.create();
				client.setConnectTimeout(10000);
				WebResource webResource = client.resource(url);//"http://10.110.52.189:8081/product/classifications");
				webResource.getRequestBuilder().header("Content-Type", "application/x-www-form-urlencoded");
				webResource.getRequestBuilder().header("Cache-Control", "no-cache");
				
				MultivaluedMap<String,String> formData = new MultivaluedMapImpl();
				formData.add("username", userName);
				formData.add("password", password);
				formData.add("grant_type", "password");
				formData.add("client_id", "pangea_app");
				 System.out.println("Calling SVC[" + url + "] with postdata " + formData.toString());
				ClientResponse response = webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class, formData);
				
				if (response.getStatus() != 200 && response.getStatus() != 201) {
				   throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
				}
				output = new JSONObject((String) response.getEntity(String.class));
				
				GTAFApplication.KEYCLOAK_WRAPPER.set(output);
				LoggerFactory.getLogger(Runner.class).info("Output from Server .... \n");
				client.destroy();
			  } catch (Exception e) {
				  LoggerFactory.getLogger(Runner.class).error("Exception!", e);
				  System.out.println("EXCEPTION!!!!!!!!!!!!!!!!!!!"  + e.getCause());
				  e.printStackTrace();
			  }
			return output.toString();
			}
	 
	 public static String callService(String webService) {
		 String returnValue = "No Response";
		 LoggerFactory.getLogger(Runner.class).info("Calling SVC[" + webService + "]");
			try {
				Client client = Client.create();
				client.setConnectTimeout(10000);
				WebResource webResource = client
				   .resource(webService);//"http://10.110.52.189:8081/product/classifications");
				String token = GTAFApplication.KEYCLOAK_WRAPPER.getServerToken();
				//String p = (token.equalsIgnoreCase(GTAFApplication.KEYCLOAK_WRAPPER.))
				System.out.println(token);
				ClientResponse response = webResource
							.accept("application/json")
							.header("Authorization", "Bearer " + token)
							.get(ClientResponse.class);//http://10.110.52.189:9090/identityx/users
				if (response.getStatus() != 200) {
				   throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
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
				  e.printStackTrace();
			  }
			return returnValue;
			}
	 
	 
}
