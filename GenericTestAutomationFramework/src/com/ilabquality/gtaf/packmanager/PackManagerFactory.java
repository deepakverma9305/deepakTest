package com.ilabquality.gtaf.packmanager;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.testsuite.TestSuiteFactory;
import com.ilabquality.gtaf.utils.database.PostgresDBWrapper;
import com.ilabquality.gtaf.utils.sql.SQL;

public class PackManagerFactory {

	public Pack getTestPackTestSuiteData(Pack p){
		try{
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery(SQL.GET_TEST_PACK_DATA.replace("%ID%", p.getId()+""));
			while(rs.next()){
				p.addSuiteData(new TestPackTestSuite(rs.getInt("suiteID"), rs.getInt("executionorder")));
			}
			db.close();
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		return p;
	}
	public Pack getTestPackTestSuiteData(String packName){
		Pack p = new Pack();
		int debug=0;
		try{
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery(SQL.GET_TEST_PACK_DATA_BY_NAME.replace("%NAME%", packName));
			while(rs.next()){
				p.addSuiteData(new TestPackTestSuite(rs.getInt("suiteID"), rs.getInt("executionorder")));
				p.setPackName(packName);
				
			}
			db.close();
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		return p;
	}
	public int getPackTestRecordCount(String packName){
		int returnValue = 0;
		try{
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery(SQL.GET_TEST_PACK_TEST_COUNT.replace("%NAME%", packName));
			while(rs.next()){
				returnValue = rs.getInt("RowCount");
			}
			db.close();
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
			System.out.println("\n\n\n" + SQL.GET_TEST_PACK_TEST_COUNT.replace("%NAME%", packName) + "\n\n\n");
		}
		System.out.println("getPackTestRecordCount:" + 	Runner.getRuntimeVariables().get("@{MustSendEmailToInitiateTests}").get(0));
		return returnValue;
	}
	
	
	
	
	public ArrayList<Pack> getPacks(){
		ArrayList<Pack> packs = new ArrayList<Pack>();
		try {
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery(SQL.GET_TEST_PACKS);
			while(rs.next()){
				Pack p = new Pack();
				p.setId(rs.getInt("ID"));
				p.setPackName(rs.getString("packname"));
				packs.add(p);
			}
			for ( Pack p : packs){
				p = getTestPackTestSuiteData(p);
			}
			db.close();
		} catch (Exception e) {
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		return packs;
	}
	public ArrayList<String> getPackNames(){
		ArrayList<String> packs = new ArrayList<String>();
		try {
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery(SQL.GET_TEST_PACKS);
			while(rs.next()){
				packs.add(rs.getString("packname"));
			}
			db.close();
		} catch (Exception e) {
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		return packs;
	}
	
	public String getSuiteNameFromID(int id){
		String returnValue = "Not Found";
		try {
			returnValue = new TestSuiteFactory().getTestSuite(id);
		} catch (Exception e) {
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		return returnValue;
	}

	public void save(Pack p) {
		try{
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			int testPackID = getPackID();
			String insert  = "insert into pangeatest.testpack values (" + testPackID +",'" + p.getPackName() +"')";
			db.executeUpdate(insert);
			for ( TestPackTestSuite tpts : p.getPackData() ){
				int id = getTestPackTestSuiteID();
				
				insert = "insert into pangeatest.testpacktestsuite values (" + id +"," +testPackID +"," + tpts.getSuiteID() + "," + tpts.getExecutionID() +")";
				db.executeUpdate(insert);
			}
			db.executeUpdate("delete from pangeatest.testpack where id = " + p.getId());
			db.executeUpdate("delete from pangeatest.testpacktestsuite where packid = " + p.getId());
			db.close();
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		deletePack(p.getId());
	}
	
	public int getPackID() {
		int returnValue = 666666666;
		try{
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery(SQL.TEST_PACK_SEQ);
			if ( rs.next())
				returnValue = rs.getInt("ID");
			db.close();
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		return returnValue;
	}
	public int getTestPackTestSuiteID() {
		int returnValue = 666666666;
		try{
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery(SQL.TEST_PACK_TEST_SUITE_SEQ);
			if ( rs.next())
				returnValue = rs.getInt("ID");
			db.close();
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		return returnValue;
	}
	public void deletePack(String packName) {
		try{
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			//get Pack ID
			int id = 0;
			ResultSet rs = db.executeQuery("Select id from pangeatest.testpack where packname = '"+ packName +"'");
			if (rs.next()){
				id = rs.getInt("ID");
			}
			db.executeUpdate("Delete from pangeatest.testpack where packname = '" + packName +"'");
			db.executeUpdate("Delete from pangeatest.testpacktestsuite where packid = '" + id +"'");
			db.close();
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		
	}
	public void deletePack(int packID) {
		try{
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			db.executeUpdate("Delete from pangeatest.testpack where packname = '" + packID +"'");
			db.executeUpdate("Delete from pangeatest.testpacktestsuite where packid = '" + packID +"'");
			db.close();
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		
	}
}
