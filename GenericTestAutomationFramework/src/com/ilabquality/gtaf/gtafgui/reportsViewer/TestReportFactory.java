package com.ilabquality.gtaf.gtafgui.reportsViewer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.GTAFApplController;
import com.ilabquality.gtaf.utils.database.PostgresDBWrapper;

public class TestReportFactory {

	public void saveTestReport(TestReport report){
		try {
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			File file = new File(report.getReportFQFileName());
			FileInputStream fis = new FileInputStream(file);
			PreparedStatement ps = db.setPreparedStatement("INSERT INTO pangeatest.testreport VALUES ((select nextval('pangeatest.testreport_id_seq')), ?,?,?)");
			ps.setString(1, report.getReportName());
			ps.setDate(2, report.getReportDate());
			ps.setBinaryStream(3, fis, (int)file.length());
			ps.executeUpdate();
			ps.close();
			fis.close();
		} catch (Exception e) {
			LoggerFactory.getLogger(GTAFApplController.class).error("Unable to save test report.",e);
		}
	}
	
	public ArrayList<String> getExecutionsForDate(String year, String month){
		ArrayList<String> returnList = new ArrayList<>();
		try {
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery("Select reportname from pangeatest.testreport where extract(year from rundate) = "+year+" and to_char(to_timestamp(to_char(extract(month from rundate), '999'), 'MM'), 'FMMonth') = '"+month+"' order by id desc");
			while(rs.next()){
				returnList.add(rs.getString("reportname"));
			}
		} catch (Exception e) {
			LoggerFactory.getLogger(GTAFApplController.class).error("Unable to get executions for date.",e);
			e.printStackTrace();
		}
		return returnList;
	}
	
	public ArrayList<Date> getExecutionDates(){
		ArrayList<Date> returnList= new ArrayList<>();
		try {
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery("Select rundate from pangeatest.testreport order by rundate asc");
			while(rs.next()){
				returnList.add(rs.getDate("rundate"));
			}
			
		} catch (Exception e) {
			LoggerFactory.getLogger(GTAFApplController.class).error("Unable to get execution dates.",e);
			e.printStackTrace();
		}
		
		return returnList;
	}
	public String getReportForExecution(int executionID){	
		try {
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
		} catch (Exception e) {
			LoggerFactory.getLogger(GTAFApplController.class).error("Unable to get Report for execution " + executionID +".",e);
			e.printStackTrace();
		}
		return "";
	}

	public File getReportFile(String newValue) {
		File returnFile = null;
		try {
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery("select content from pangeatest.testreport where reportname = '" + newValue + "'");
			while(rs.next()){
				byte[] bytes = rs.getBytes("content");
				String fileName = System.getProperty("java.io.tmpdir").replace("\\", "/") + "RetrievedResults" + newValue.replace(":", "").replace(" ", "").replace("-","") + ".html";
				FileOutputStream fo = new FileOutputStream(fileName);
				fo.write(bytes);
				fo.close();
				returnFile = new File(fileName);
			}
		} catch (Exception e) {
			LoggerFactory.getLogger(GTAFApplController.class).error("Unable to extract erport from the database.",e);
		}
		writeImages();
		return returnFile;
	}
	public void writeImages(){
		try {
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery("select imagename, imagebytes from pangeatest.testreportimages");
			while(rs.next()){
				String name = rs.getString("imagename");
				byte[] bytes = rs.getBytes("imagebytes");
				String fileName = ( System.getProperty("BUILD_NUMBER") == null || 	
									System.getProperty("BUILD_NUMBER").equalsIgnoreCase(""))
												? System.getProperty("java.io.tmpdir").replace("\\", "/") + name :
													name;
				FileOutputStream fo = new FileOutputStream(fileName);
				fo.write(bytes);
				fo.close();
			}
		} catch (Exception e) {
			LoggerFactory.getLogger(GTAFApplController.class).error("Unable to write report images.",e);
		}
	}
	
	@SuppressWarnings("unused")
	private void importImages(String name){
		try {
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			File file = new File("C:/Users/Steve/AppData/Local/Temp/" + name);
			FileInputStream fis = new FileInputStream(file);
			PreparedStatement ps = db.setPreparedStatement("INSERT INTO pangeatest.testreportimages VALUES ((select nextval('pangeatest.testreportimages_id_seq')),'" + name + "', ?)");
			ps.setBinaryStream(1, fis, (int)file.length());
			ps.executeUpdate();
			ps.close();
			fis.close();
		} catch (Exception e) {
			LoggerFactory.getLogger(GTAFApplController.class).error("Unable to save test images.",e);
		}
	}
}
