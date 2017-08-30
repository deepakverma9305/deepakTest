package com.ilabquality.gtaf.testsuite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import com.ilabquality.gtaf.gtafAppl.GTAFApplController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.utils.database.PostgresDBWrapper;
import com.ilabquality.gtaf.utils.sql.SQL;

public class TestSuiteFactory {

	public ArrayList<TestSuite> getTestSuites() throws SQLException{
		ArrayList<TestSuite> suites = new ArrayList<TestSuite>();
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		ResultSet rs = db.executeQuery(SQL.GET_TEST_SUITES);
		while(rs.next()){
			TestSuite ts = new TestSuite();
			ts.setTestSuiteID(rs.getInt("ID"));
			ts.setTestSuiteDescription(rs.getString("TestSuiteDescription"));
			ts.setTestSuiteName(rs.getString("TestSuiteName"));
			suites.add(ts);
		}
		return suites;
	}
	
	public TestSuite getTestSuite(String testSuiteName) throws SQLException{
		TestSuite returnSuite = new TestSuite();
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		ResultSet rs = db.executeQuery(SQL.GET_TEST_SUITES);
		while(rs.next()){
			int id = rs.getInt("ID");
			String desc = rs.getString("TestSuiteDescription");
			String name = rs.getString("TestSuiteName");
			if ( name.equalsIgnoreCase(testSuiteName)){
				returnSuite.setTestSuiteID(id);
				returnSuite.setTestSuiteDescription(desc);
				returnSuite.setTestSuiteName(name);
				break;
			}
		}
		db.close();
		populateTestCasesForTestSuite(returnSuite);
		populateVariablesForSuite(returnSuite);
		
		return returnSuite;
	}
	public TestSuite getTestSuiteByID(int testSuiteID) throws SQLException{
		TestSuite returnSuite = new TestSuite();
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		ResultSet rs = db.executeQuery(SQL.GET_TEST_SUITES);
		while(rs.next()){
			int id = rs.getInt("ID");
			String desc = rs.getString("TestSuiteDescription");
			String name = rs.getString("TestSuiteName");
			if ( id == testSuiteID){
				returnSuite.setTestSuiteID(id);
				returnSuite.setTestSuiteDescription(desc);
				returnSuite.setTestSuiteName(name);
			}
		}
		db.close();
		populateTestCasesForTestSuite(returnSuite);
		populateVariablesForSuite(returnSuite);
		
		return returnSuite;
	}
	public void populateVariablesForSuite(TestSuite t ) throws SQLException{
		t.getVariablesHelper().refreshVariables();
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		ResultSet rs = db.executeQuery(SQL.GET_VARIABLES_FOR_TESTSUITE.replace("%ID%", t.getTestSuiteID()+""));
		while(rs.next()){
			Variable v = new Variable();
			v.setId(rs.getInt("ID"));
			v.setTestSuiteID(rs.getInt("TestSuiteID"));
			v.setTestCaseID(rs.getInt("TestCaseID"));
			v.setVariableName(rs.getString("variableName"));
			v.setVariableStartValue(rs.getString("variableStartValue"));
			v.setVariableEndValue(rs.getString("variableEndValue"));
			v.setVariableOriginalValue(rs.getString("variableoriginalvalue"));
			v.setVariableAction(rs.getString("Action"));
			//t.getTestByID(v.getTestCaseID()).getVariables().add( v );
			if ( Runner.getRuntimeVariables().containsKey(v.getVariableName() )){
				Runner.getRuntimeVariables().get(v.getVariableName()).add(v);
			}else{
				Runner.getRuntimeVariables().put(v.getVariableName(), new ArrayList<Variable>(Arrays.asList(v)));
			}
		}
		System.out.println("populateVariablesForSuite" + Runner.getRuntimeVariables().get("@{MustSendEmailToInitiateTests}"));
		db.close();
	}
	public void populateTestCasesForTestSuite(TestSuite t ) throws SQLException{
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		ResultSet rs = db.executeQuery(SQL.GET_TEST_CASES.replace("%ID%", t.getTestSuiteID()+""));
		
		while(rs.next()){
			Test tc = new Test();
			tc.setId(rs.getInt("ID"));
			tc.setTestName(rs.getString("TestName"));
			tc.setCallResult(rs.getString("CallResult"));
			tc.setExecutionIndex(rs.getInt("ExecutionOrderIndex"));
			tc.setTestDescription(rs.getString("TestDescription"));
			tc.setTestSuiteID(rs.getInt("testsuiteid"));
			tc.setWebServiceURL(rs.getString("WebServiceURL"));
			tc.setLiveURL(tc.getWebServiceURL());
			tc.setVerb(rs.getString("Verb"));
			tc.setDelay(rs.getInt("delay"));
			tc.setExecutionUserName(rs.getString("executionusername"));
			tc.setExecutionPassword(rs.getString("executionpassword"));
			tc.setParentSuite(t);
			tc.setPostData(rs.getString("postdata"));
			t.getSuiteData().add(tc);
		}
		db.close();
		populateValidations(t);
	}
	
	private void populateValidations(TestSuite t) throws SQLException{
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
			ResultSet rs = db.executeQuery(SQL.GET_TEST_CASE_VALIDATIONS.replace("%TESTSUITENAME%", t.getTestSuiteName() +""));
			Hashtable<Integer, List<TestValidation>> validations = new Hashtable<>();
			while(rs.next()){
				TestValidation tv = new TestValidation();
				tv.setId(rs.getInt("ID"));
				tv.setExpectedResult(rs.getString("EXPECTEDRESULT"));
				tv.setTestCaseID(rs.getInt("testcaseid"));
				tv.setRegularExpression(rs.getBoolean("isregularexpression"));
				//tv.setAssociatedTest(tc);
				if ( validations.containsKey(tv.getTestCaseID())){
					validations.get(tv.getTestCaseID()).add(tv);
				}else{
					List<TestValidation> newList = new ArrayList<TestValidation>();
					newList.add(tv);
					validations.put(tv.getTestCaseID(), newList);
				}
			}
			db.close();
			Enumeration<Integer> keys = validations.keys();
			while(keys.hasMoreElements()){
				int tcID = keys.nextElement();
				for ( Test test : t.getSuiteData()){
					if ( test.getId() == tcID ){
						List<TestValidation> tvs = validations.get(tcID);
						for ( TestValidation tVal : tvs){
							tVal.setAssociatedTest(test);
						}
						test.getValidations().addAll(validations.get(tcID));
					}
				}
			}
	}

	public String getTestSuite(int id) throws SQLException{
		String returnSuite = "Not Found";
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		ResultSet rs = db.executeQuery(SQL.GET_TEST_SUITE_NAME.replace("%ID%", id+""));
		if ( rs.next() )
			returnSuite = rs.getString("name");
		db.close();
		return returnSuite;
	}
	
	
	public void saveTestSuite(GTAFApplController parent, TestSuite suite) throws SQLException{
		int debug=0;
		if ( parent != null )
		{
			parent.resetProgress();
			parent.setTaskDisplayer("Preparing suite for save");
			parent.setProgressMaximum(suite.getSuiteData().size());
		}
		int currentSuiteID = suite.getTestSuiteID();
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		db.addToBatch("Insert into PangeaTest.TestSuite values ( ( select nextval('pangeatest.testsuite_id_seq') ),'" + suite.getTestSuiteName().trim() +"','"+ suite.getTestSuiteDescription().trim()+"')");
		int executionIndexCounter = 1 ;
		int testcaseSize = suite.getSuiteData().size();
		for ( Test t : suite.getSuiteData()){
			if ( parent != null )
				parent.setTaskDisplayer("Processing record (" + executionIndexCounter +" of " + testcaseSize + ")");
			db.addToBatch("insert into pangeatest.testcase values ( (select nextval('pangeatest.testcase_id_seq'))  " + 
							 ",' " + t.getTestName().trim() +  "','" + t.getTestDescription().trim() + "'," + executionIndexCounter +",'" +
							 t.getWebServiceURL() +"','" + t.getCallResult().replace("'", "\\''") +"',(select currval('pangeatest.testsuite_id_seq')),'" + 
							 t.getVerb().trim()+"','" + t.getPostData().replace("'", "\\''")+"'," + t.getDelay() +",'" + t.getExecutionUserName() +"','" + t.getExecutionPassword()+"')");
			for ( TestValidation tVal : t.getValidations()){
				//System.out.println(":::::"+tVal.getExpectedResult());
				String p = "insert into pangeatest.testcasevalidation values ( (select nextval('pangeatest.testcasevalidation_id_seq')),'" + 
						tVal.getExpectedResult().replace("'", "''") +"',(select currval('pangeatest.testcase_id_seq')),"+ (tVal.isRegularExpression()?"true":"false")+")";
				//System.out.println(p);
				db.addToBatch(p);
			}
			for ( Variable var : t.getVariables()){
				db.addToBatch("insert into pangeatest.variable values ( (select nextval('pangeatest.variable_id_seq')),"
																	+  "(select currval('pangeatest.testsuite_id_seq')), " + 
																	   "(select currval('pangeatest.testcase_id_seq')),'" + 
																				var.getVariableName() +"','" + var.getVariableStartValue()+
																				"','"+var.getVariableEndValue() +"','" + var.getVariableOriginalValue()+"'"
																						+ ",'" + var.getVariableAction()+"')");
			}
			executionIndexCounter++;
			if ( parent != null ) parent.incrementProgress();
		}
		try{
			if ( parent != null ) {
				parent.resetProgress();
				parent.setProgressMaximum(6);
				parent.appendLog("Inserting Suite Detail");
			}
			db.executeBatchUpdate();
			if ( parent != null ){
				parent.incrementProgress();
				parent.appendLog("Done");
			}
			if ( parent != null ) parent.appendLog("Clearing up old data - Test Suite");
			db.executeUpdate("Delete from pangeatest.testsuite where ID = " +currentSuiteID);
			if ( parent != null ) {
				parent.incrementProgress();
				parent.appendLog("Clearing up old data - Test Cases");
			}
			db.executeUpdate("delete from pangeatest.testcase where testsuiteid = " + currentSuiteID);
			if ( parent != null ) {
				parent.incrementProgress();
				parent.appendLog("Clearing up old data - Validations and Variables");
			}
			db.executeUpdate("delete from pangeatest.testcasevalidation where id in ( select tcv.id from pangeatest.testcasevalidation tcv join pangeatest.testcase tc on tcv.testcaseid = tc.id " +
																						"join pangeatest.testsuite ts on tc.testsuiteid = ts.id where ts.id = "+ currentSuiteID+")");
			db.executeUpdate("delete from pangeatest.variable where testsuiteid = " + suite.getTestSuiteID());
			if ( parent != null ) {
				parent.incrementProgress();
				parent.appendLog("Updating previously linked packs. ");
			}
			db.executeUpdate("Update pangeatest.testpacktestsuite set suiteid = (select currval('pangeatest.testsuite_id_seq')) where suiteid =" + suite.getTestSuiteID());
			if ( parent != null ) {
				parent.appendLog("Saved. ");
				parent.setTaskDisplayer("Idle...");
				parent.resetProgress();
			}
		}catch(Exception e){
			e.printStackTrace();
			if ( parent != null ) {
				parent.appendLog("Exception! Unable to process pack! ");
				parent.resetProgress();
			}
		}
	}
	
	public static void main(String[] args) throws SQLException {
		TestSuiteFactory tsf = new TestSuiteFactory();
		TestSuite ts = new TestSuite();
		ts.setTestSuiteID(1);
		ts.setTestSuiteName("NewName");
		ts.setTestSuiteDescription("Some other desc");
		ts.getSuiteData().add(new Test());
		tsf.saveTestSuite(null,ts);
	}

	public int getNextTCID() throws SQLException {
		int returnValue = 66666;
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery(SQL.TEST_CASE_SEQ);
			if ( rs.next() )
				returnValue =  rs.getInt("ID");
			db.close();	
		return returnValue;
	}
	public int getNextVarID() throws SQLException {
		int returnValue = 66666;
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery(SQL.TEST_VAR_SEQ);
			if ( rs.next() )
				returnValue =  rs.getInt("ID");
			db.close();	
		return returnValue;
	}
	public int getNextTValID() throws SQLException {
		int returnValue = 66666;
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery(SQL.TEST_VALIDATION_SEQ);
			if ( rs.next() )
				returnValue =  rs.getInt("ID");
			db.close();	
		return returnValue;
	}
	public int getNextTSID() throws SQLException {
		int returnValue = 66666;
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery(SQL.TEST_SUITE_SEQ);
			if ( rs.next() )
				returnValue =  rs.getInt("ID");
			db.close();
		return returnValue;
	}
	public void deleteSuite(GTAFApplController parent, TestSuite selectedTestSuite) throws SQLException {
		
		if ( parent != null ){
			parent.setTaskDisplayer("Deleting Suite");
			parent.resetProgress();
			parent.setProgressMaximum(4);
			parent.appendLog("Deleting Suite");
		}
		PostgresDBWrapper db = new PostgresDBWrapper();
		db.connectToTest();
		db.executeUpdate("Delete from pangeatest.testsuite where ID = " +selectedTestSuite.getTestSuiteID());
		if ( parent != null ){
			parent.incrementProgress();
			parent.appendLog("Deleting validations");
		}
		db.executeUpdate("delete from pangeatest.testcasevalidation where id in ( select tcv.id from pangeatest.testcasevalidation tcv join pangeatest.testcase tc on tcv.testcaseid = tc.id " +
				"join pangeatest.testsuite ts on tc.testsuiteid = ts.id where ts.testsuitename = '"+ selectedTestSuite.getTestSuiteName()+"')");
		if ( parent != null ){
			parent.incrementProgress();
			parent.appendLog("Deleting variables");
		}
		db.executeUpdate("delete from pangeatest.variable where testsuiteid = " + selectedTestSuite.getTestSuiteID());
		//Remove any variables that exist in the runtimevariables static field. 
		selectedTestSuite.getVariablesHelper().refreshVariables();
		if ( parent != null ) {
			parent.incrementProgress();
			parent.appendLog("Deleting Test Cases");
		}
		db.executeUpdate("delete from pangeatest.testcase where testsuiteid = " + selectedTestSuite.getTestSuiteID());
		if ( parent != null ) {
			parent.resetProgress();
			parent.appendLog("Done.");
			parent.setTaskDisplayer("Idle...");
		}
		db.close();
	}



	public void prepareUpdatesForVariables(String query,TestSuite suite, String prefix, String variableText,String suffix , String variableName,String column) throws SQLException{
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery(query);
			while(rs.next()){
				int id = rs.getInt("id");
				System.out.println(id);
				String cr = rs.getString(column);
				System.out.println(cr);
				if (query.contains("webserviceurl")){
					if ( cr.contains(variableText)){
						while  (cr.contains(variableText)){
							if ( !suite.getSelectedTest().containsVar(variableName)){
								Variable v = new Variable();
								v.setTestSuiteID(suite.getTestSuiteID());
								v.setVariableStartValue(prefix);
								v.setVariableOriginalValue(variableText);
								v.setVariableEndValue(suffix);
								v.setVariableName(variableName);
								v.setTestCaseID(id);
								suite.getSelectedTest().getVariables().add(v);
								System.out.println("Variable " + variableName + " was added to the test suite for save");
							}
							cr = cr.replace(variableText, variableName);
						}
						suite.getSelectedTest().setWebServiceURL(cr);
					}
				}else{
					
				}
			}
			db.close();
	}


	
	
}
