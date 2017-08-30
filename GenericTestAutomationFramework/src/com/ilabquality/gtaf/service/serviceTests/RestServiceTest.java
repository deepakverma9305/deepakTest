package com.ilabquality.gtaf.service.serviceTests;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.reporting.report.Report;

public abstract class RestServiceTest  {
	
	private String Url = "";
	private String isAliveURL="";
	//private String expectedResult = "";
	//private String actualResult = "";
	private boolean hasPassed = false;
	private int id;
	private String name = "";
	private String description = "";
	private String serverIP="";
	private String serverPort="";
	private String serverPath="";
	private String className="";
	private String fqPath="";
	private String action="";
	private String jsonClassName="";
	private String jsonClassFQPath="";
	
	
	
	
	public abstract void validateThroughDatabase();
	
	public abstract ArrayList<?> test();
	public abstract boolean validateIsAlive();
	public abstract RestServiceTest copy();
	public abstract ArrayList<?> populateActualResults(String callResult) throws JsonParseException, JsonMappingException, IOException ;
	public abstract ArrayList<?> populateExpectedResults() throws SQLException, Exception ;
	public abstract Report getReport();
	
	
	public RestServiceTest(){}
	public RestServiceTest(String name, String Url, String tempExpectedResult){
		this.name = name;
		this.Url = Url;
		//this.expectedResult = tempExpectedResult;
	}
	
	public String call(){
		return Runner.serviceConnector.callService(getUrl(),"","");
	}

	
	public boolean isAlive(){
		boolean retVal = false;
		if (getIsAliveURL() == null || getIsAliveURL().equalsIgnoreCase("")){
			//	retVal = Runner.serviceConnector.isAlive(getUrl());
		}else{
			//retVal = Runner.serviceConnector.isAlive(getIsAliveURL());
		}
		return retVal;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		if(this.Url.equalsIgnoreCase(""))
			setUrl("http://"+getServerIP()+":" + getServerPort()+"/"+getServerPath());
		return this.Url;
	}
	public void setUrl(String url) {
		System.out.println("Setting URL to \n" + url);
		Url = url;
	}
//	public String getExpectedResult() {
//		return expectedResult;
//	}
//	public void setExpectedResult(String tempExpectedResult) {
//		System.out.println("Setting expected result to \n" + tempExpectedResult);
//		this.expectedResult = tempExpectedResult;
//	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
//	public String getActualResult() {
//		return actualResult;
//	}
//	public void setActualResult(String actualResult) {
//		this.actualResult = actualResult;
//	}
	public boolean hasPassed() {
		return hasPassed;
	}
	public void setHasPassed(boolean hasPassed) {
		this.hasPassed = hasPassed;
	}
	

//	public String getRandomID(){
//		return UUID.randomUUID().toString().substring(0, 7).replace("-", "");
//	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFqPath() {
		return fqPath;
	}

	public void setFqPath(String fqPath) {
		this.fqPath = fqPath;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getJsonClassName() {
		return jsonClassName;
	}

	public void setJsonClassName(String jsonClassName) {
		this.jsonClassName = jsonClassName;
	}

	public String getJsonClassFQPath() {
		return jsonClassFQPath;
	}

	public void setJsonClassFQPath(String jsonClassFQPath) {
		this.jsonClassFQPath = jsonClassFQPath;
	}

	public boolean isHasPassed() {
		return hasPassed;
	}

	public String getIsAliveURL() {
		return isAliveURL;
	}

	public void setIsAliveURL(String isAliveURL) {
		this.isAliveURL = isAliveURL;
	}
	
	
	
}
