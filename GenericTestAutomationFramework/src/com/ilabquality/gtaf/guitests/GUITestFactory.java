package com.ilabquality.gtaf.guitests;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ilabquality.gtaf.utils.database.PostgresDBWrapper;
import com.ilabquality.gtaf.utils.sql.SQL;

public class GUITestFactory {

	public ArrayList<GUITestSuite> getGUITestSuites() throws SQLException{
		ArrayList<GUITestSuite> suites = new ArrayList<GUITestSuite>();
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		ResultSet rs = db.executeQuery(SQL.GET_GUI_TEST_SUITES);
		while(rs.next()){
			GUITestSuite ts = new GUITestSuite();
			ts.setId(rs.getInt("ID"));
			ts.setName(rs.getString("TestSuiteName"));
			suites.add(ts);
		}
		return suites;
	}
	
	public GUITestSuite getGUITestSuite(String testSuite) throws SQLException{
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		ResultSet rs = db.executeQuery(SQL.GET_GUI_TEST_SUITE.replace("%NAME%", testSuite));
		GUITestSuite gts = new GUITestSuite();
		boolean hasSetSuiteDetails = false;
		while(rs.next()){
			if ( !hasSetSuiteDetails){
				int testSuiteID = rs.getInt("TestSuiteID");
				String testSuiteName = rs.getString("TestSuiteName");
				gts.setName(testSuiteName);
				gts.setId(testSuiteID);
				hasSetSuiteDetails = true;
			}
			
			GUITestDef gtd = new GUITestDef();
			gtd.setId(rs.getInt("GuiTestDefID"));
			gtd.setGuiTestSuiteID(gts.getId());
			gtd.setGuiTestCaseID(rs.getInt("TestCaseID"));
			gtd.setExecutionOrder(rs.getInt("TestCaseExecutionOrder"));
			gtd.setClazzName(rs.getString("TestCaseClassName"));
			gtd.setTestName(rs.getString("TestCaseName"));
			gts.getSuiteData().add(gtd);
		}
		db.close();
		return gts;
	}

	public void saveTest(String key, String string) throws SQLException {
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		String insert  = "insert into pangeatest.guitestcase values(( select nextval('pangeatest.guitestcase_id_seq')),'" + key +"','"+string+"')" ;
		System.out.println(insert);
		db.executeUpdate(insert);
	}

	public void deleteTest(String selItem) throws SQLException {
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		String delete = "delete from pangeatest.guitestcase where name = '"+selItem+"'";
		System.out.println(delete);
		db.executeUpdate(delete);
		
	}

	public ArrayList<GUITestDef> getAllTests() throws SQLException {
		ArrayList<GUITestDef> returnList = new ArrayList<>();
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		ResultSet rs = db.executeQuery(SQL.GET_GUI_TESTS);
		while(rs.next()){
			int id = rs.getInt("ID");
			GUITestDef gtd = new GUITestDef();
			gtd.setId(id);
			gtd.setGuiTestCaseID(id);
			gtd.setClazzName(rs.getString("clazzname"));
			gtd.setTestName(rs.getString("name"));
			returnList.add(gtd);
		}
		db.close();
		return returnList;
	}

	public void deleteSuite(String selectedItem) throws SQLException {
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		String delete = "delete from pangeatest.guitestpacktestsuite where suiteid = (select id from pangeatest.guitestsuite where testsuitename = '"+selectedItem+"')";
		System.out.println(delete);
		db.executeUpdate(delete);
		delete = "delete from pangeatest.guitestsuite where testsuitename = '"+selectedItem+"'";
		System.out.println(delete);
		db.executeUpdate(delete);
		
	}

	public GUITestDef getGuiTestDef(String buttonText) throws SQLException {
		GUITestDef gtd = new GUITestDef();
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		ResultSet rs = db.executeQuery("Select * from pangeatest.guitestcase where name ='" + buttonText.substring(0,buttonText.indexOf(" -")) + "'");
		while(rs.next()){
			int id = rs.getInt("ID");
			gtd.setId(id);
			gtd.setGuiTestCaseID(id);
			gtd.setClazzName(rs.getString("clazzname"));
			gtd.setTestName(rs.getString("name"));
		}
		db.close();
		return gtd;
	}

	public void saveSuite(GUITestSuite selectedGUITestSuite) throws SQLException {
		int debug=0;
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		
		//clear out old data
		
		db.executeUpdate("delete from pangeatest.guitestpacktestsuite where suiteid = (select id from pangeatest.guitestsuite where testsuitename = '" + selectedGUITestSuite.getName() + "')");
		db.executeUpdate("delete from pangeatest.guitestsuite where testsuitename = '" + selectedGUITestSuite.getName() + "'");
		//insert new data
		String insertSuite = "insert into pangeatest.guitestsuite values (( select nextval('pangeatest.guitestsuite_id_seq')), '"+ selectedGUITestSuite.getName() +"')";
		db.executeUpdate(insertSuite);
		int counter = 0;
		for ( GUITestDef gtd : selectedGUITestSuite.getSuiteData()){
			String gtdInsert = "insert into pangeatest.guitestpacktestsuite values (( select nextval('pangeatest.guitestpacktestsuite_id_seq')), (select currval('pangeatest.guitestsuite_id_seq')) , " + gtd.getGuiTestCaseID() +", "+counter+")";
			db.executeUpdate(gtdInsert);
			counter++;
		}
	}

	public boolean isUnique(String text) throws SQLException {
		boolean isUnique = true;
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		ResultSet rs = db.executeQuery("Select * from pangeatest.guitestsuite where testsuitename ='" + text + "'");
		if (rs.next()){
			isUnique = false;
		}
		return isUnique;
	}
	
}
