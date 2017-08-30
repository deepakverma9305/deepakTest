package com.ilabquality.gtaf.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.Runner;

public class PostgresDBWrapper {
	Connection connection = null;
	Statement stmt;
	PreparedStatement pstmt;
	public PostgresDBWrapper(){}
	public void connect(String db) {
		try{
		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection(
		   "jdbc:postgresql://10.110.52.189:5432/"+db,(db.equalsIgnoreCase("prototype")?"postgres":db), "sa");
		LoggerFactory.getLogger(Runner.class).debug("Connected to " + db);
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
	}
	
	public void connectToTest() {//db PangeaTest
		try{
		Class.forName("org.postgresql.Driver");
//		Logger.getLogger(Runner.class).debug(Runner.getRuntimeVariables().get("@{TestDBServerIP}").get(0).getLiveValue());
//		Logger.getLogger(Runner.class).debug(Runner.getRuntimeVariables().get("@{TestDBServerPort}").get(0).getLiveValue());
//		Logger.getLogger(Runner.class).debug(Runner.getRuntimeVariables().get("@{TestDBName}").get(0).getLiveValue());
//		Logger.getLogger(Runner.class).debug(Runner.getRuntimeVariables().get("@{TestDBUserName}").get(0).getLiveValue() );
//		Logger.getLogger(Runner.class).debug(Runner.getRuntimeVariables().get("@{TestDBUserPassword}").get(0).getLiveValue());
//		Logger.getLogger(Runner.class).debug("DB String \n" + "jdbc:postgresql://" + Runner.getRuntimeVariables().get("@{TestDBServerIP}").get(0).getLiveValue() + 
//	   			":"+Runner.getRuntimeVariables().get("@{TestDBServerPort}").get(0).getLiveValue() +
//	   			"/"+Runner.getRuntimeVariables().get("@{TestDBName}").get(0).getLiveValue() +"  :" +  
//	   			Runner.getRuntimeVariables().get("@{TestDBUserName}").get(0).getLiveValue() + "   :" +  Runner.getRuntimeVariables().get("@{TestDBUserPassword}").get(0).getLiveValue());
		
		connection = DriverManager.getConnection(
				   "jdbc:postgresql://" + Runner.getRuntimeVariables().get("@{TestDBServerIP}").get(0).getLiveValue() + 
				   			":"+Runner.getRuntimeVariables().get("@{TestDBServerPort}").get(0).getLiveValue() +
				   			"/"+Runner.getRuntimeVariables().get("@{TestDBName}").get(0).getLiveValue() , 
				   			Runner.getRuntimeVariables().get("@{TestDBUserName}").get(0).getLiveValue() , Runner.getRuntimeVariables().get("@{TestDBUserPassword}").get(0).getLiveValue());
		LoggerFactory.getLogger(Runner.class).info("Connected to " + Runner.getRuntimeVariables().get("@{TestDBName}").get(0).getLiveValue() );
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
			e.printStackTrace();
			System.exit(2);
			
		}
	}
	
	public PreparedStatement setPreparedStatement(String sql) throws SQLException{
		if ( pstmt == null)
				pstmt = connection.prepareStatement(sql);
		return pstmt;
	}

	public ResultSet executeQuery(String sql) throws SQLException{
		stmt = connection.createStatement();
		return  stmt.executeQuery(sql);
	}
	public void executeUpdate(String sql) throws SQLException{
		if ( stmt == null ) 
			stmt = connection.createStatement();
		stmt.executeUpdate(sql);
	}
	
	public void addToBatch(String sql) throws SQLException{
		if ( stmt == null ) {
			stmt = connection.createStatement();
		}
		stmt.addBatch(sql);
	
	}
	public void executeBatchUpdate() throws SQLException{
		try{
			if ( stmt == null ) {
				stmt = connection.createStatement();
			}
			stmt.executeBatch();
			stmt.clearBatch();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getCause());
			throw e;
		}
	}
	
	public void close() {
		try {
			if (!stmt.isClosed())
				stmt.close();
			if  (!connection.isClosed())
				connection.close();
		} 
		catch(Exception e){LoggerFactory.getLogger(Runner.class).error("Exception!", e);}
	//	LoggerFactory.getLogger(AbsaDriver.class).info("Connection Closed");
		}
	
}
