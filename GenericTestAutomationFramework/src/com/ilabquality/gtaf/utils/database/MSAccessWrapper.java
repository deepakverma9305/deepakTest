package com.ilabquality.gtaf.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.service.serviceTests.RestServiceTest;
import com.ilabquality.gtaf.utils.sql.SQL;

public class MSAccessWrapper {

	 Statement st;
	 
	 public MSAccessWrapper(){
		 connect();
	 }
	public void connect(){
		 try
		 {
		  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		  Connection con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=c:/users/steven/desktop/Pangea.mdb;uid=; pwd=password here;");
		  
		   st=con.createStatement();
		  con.close();
		 }
		 catch(Exception ex)
		 {
			 LoggerFactory.getLogger(Runner.class).error("Exception!", ex);
		 }
	}
	
	public ResultSet getServiceDefinitions(){
		connect();
		ResultSet rs= null;
		try{
			 rs = st.executeQuery(SQL.GET_SERVICE_DEFINITIONS);
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		return rs;
	}
	
	private RestServiceTest getDynamicObject(String path, String name) {
		RestServiceTest rst = null;
		try{
				LoggerFactory.getLogger(Runner.class).info(path+"."+name);
				Class<?> c = Class.forName(path+"."+name);
				Object o = c.newInstance();
				rst = (RestServiceTest)o;
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		return rst;
	}
	
	public ArrayList<RestServiceTest> populateServicesFromDB(){
		ArrayList<RestServiceTest> returnList = new ArrayList<RestServiceTest>();
		try{
			ResultSet rs = new MSAccessWrapper().getServiceDefinitions();
			while(rs.next()){
				int id = rs.getInt("ID");
				String  serviceName = rs.getString("ServiceName");
				String serviceDescription = rs.getString("ServiceDescription");
				String serverIP = rs.getString("ServerIP");
				String serverPort = rs.getString("ServerPort");
				String serverPath = rs.getString("ServerPath");
				String className = rs.getString("ClassName");
				String fqPath = rs.getString("FQPath");
				String action = rs.getString("Action");
				String jsonClassName = rs.getString("JSONClassName");
				String jsonClassFQPath = rs.getString("JSONClassFQPath");
				String isAliveURL = rs.getString("IsAliveURL");
				RestServiceTest rt = getDynamicObject(fqPath,className);
				rt.setId(id);
				rt.setName(serviceName);
				rt.setDescription(serviceDescription);
				rt.setServerIP(serverIP);
				rt.setServerPort(serverPort);
				rt.setServerPath(serverPath);
				rt.setClassName(className);
				rt.setFqPath(fqPath);
				rt.setAction(action);
				rt.setJsonClassName(jsonClassName);
				rt.setJsonClassFQPath(jsonClassFQPath);
				rt.setIsAliveURL(isAliveURL);
				returnList.add(rt);
			}
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		return returnList;
	}
	
	public ArrayList<RestServiceTest> populateFirst2ServicesFromDB(){
		ArrayList<RestServiceTest> returnList = new ArrayList<RestServiceTest>();
		try{
			ResultSet rs = new MSAccessWrapper().getServiceDefinitions();
			int counter = 0;
			while(rs.next()){
				if ( counter == 2) break;
				int id = rs.getInt("ID");
				String  serviceName = rs.getString("ServiceName");
				String serviceDescription = rs.getString("ServiceDescription");
				String serverIP = rs.getString("ServerIP");
				String serverPort = rs.getString("ServerPort");
				String serverPath = rs.getString("ServerPath");
				String className = rs.getString("ClassName");
				String fqPath = rs.getString("FQPath");
				String action = rs.getString("Action");
				String jsonClassName = rs.getString("JSONClassName");
				String jsonClassFQPath = rs.getString("JSONClassFQPath");
				
				RestServiceTest rt = getDynamicObject(fqPath,serviceName);
				rt.setId(id);
				rt.setName(serviceName);
				rt.setDescription(serviceDescription);
				rt.setServerIP(serverIP);
				rt.setServerPort(serverPort);
				rt.setServerPath(serverPath);
				rt.setClassName(className);
				rt.setFqPath(fqPath);
				rt.setAction(action);
				rt.setJsonClassName(jsonClassName);
				rt.setJsonClassFQPath(jsonClassFQPath);
				returnList.add(rt);
				counter++;
			}
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		return returnList;
	}
	
}
