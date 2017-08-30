package com.ilabquality.gtaf.utils.sql;

public class SQL {
	public static final String TEST_SUITE_SEQ = "select nextval('pangeatest.testsuite_id_seq') as ID";
	public static final String TEST_CASE_SEQ = "select nextval('pangeatest.testcase_id_seq') as ID";
	public static final String TEST_VAR_SEQ = "select nextval('pangeatest.variable_id_seq') as ID";
	
	public static final String TEST_VALIDATION_SEQ = "select nextval('pangeatest.testcasevalidation_id_seq') as ID";
	public static final String GET_TEST_PACKS = "select * from pangeatest.testPack";
	public static final String GET_TEST_PACK_DATA = "select * from pangeatest.testPackTestSuite where packid = %ID% order by executionorder asc";
	public static final String GET_TEST_PACK_DATA_BY_NAME = "select * from pangeatest.testpacktestsuite packdata join pangeatest.testpack pack on pack.id = packdata.packid where pack.packname = '%NAME%' order by packdata.executionorder asc;";
	public static final String GET_TEST_PACK_TEST_COUNT = "select count(*) as rowCount from pangeatest.testpacktestsuite packdata "+ 
																	"join pangeatest.testpack pack on pack.id = packdata.packid "+
																	"join pangeatest.testsuite ts on packdata.suiteid = ts.id "+
																	"join pangeatest.testcase tc on ts.id = tc.testsuiteid "+
																	"where pack.packname = '%NAME%'";
	public static final String INSERT_PACK = "select * from pangeatest.testPackTestSuite where packid = %ID%";
	public static final String TEST_PACK_SEQ = "select nextval('pangeatest.testpack_id_seq') as ID";
	public static final String TEST_PACK_TEST_SUITE_SEQ = "select nextval('pangeatest.testpacktestsuite_id_seq') as ID";
	
	
	
	public static final String GET_SERVICE_DEFINITIONS = "Select * from ServiceDefinitions";
	public static final String GET_PRODUCT_CLASSIFICATIONS="Select * from product_classification";
	
	
	public static final String GET_USERS="Select * from act_id_user";
	
	
	//Test Suites
	/*******************************************/
	public static final String GET_TEST_SUITES="SELECT * FROM PangeaTest.TestSuite";
	public static final String GET_GUI_TEST_SUITES="SELECT * FROM PangeaTest.GUITestSuite order by testsuitename asc";
	
	public static final String GET_GUI_TEST_SUITE="select gtc.id as TestCaseID, gtc.name as TestCaseName, gtc.clazzname as TestCaseClassName, gtpts.id as GuiTestDefID, gtpts.executionorder as TestCaseExecutionOrder, gts.id as TestSuiteID, gts.testsuitename as TestSuiteName" + 
												" from pangeatest.guitestcase gtc "+
												"		join pangeatest.guitestpacktestsuite gtpts on gtc.id = gtpts.guitestcaseid "+
												"		join pangeatest.guitestsuite gts on gts.id = gtpts.suiteid "+
												"	where gts.testsuitename = '%NAME%' "+
												"	order by gtpts.executionorder asc";
	
	public static final String GET_TESTS="SELECT * FROM PangeaTest.Testcase";
	public static final String GET_GUI_TESTS="SELECT * FROM pangeatest.guitestcase order by clazzname asc";
	
	public static final String GET_TEST_SUITE_NAME="SELECT name FROM PangeaTest.TestSuite where id =%ID%";
	
	public static final String GET_TEST_CASES="SELECT * FROM pangeatest.TestCase where TestSuiteID = '%ID%' order by executionorderindex asc";// ON \"PangeaTest\".\"TestSuite\".\"ID\" = \"PangeaTest\".\"TestCase\".\"TestSuiteID\";";
	public static final String GET_COMPLETE_TEST_CASES="SELECT * FROM pangeatest.TestCase where TestSuiteID = '%ID%' order by executionorderindex asc";// ON \"PangeaTest\".\"TestSuite\".\"ID\" = \"PangeaTest\".\"TestCase\".\"TestSuiteID\";";
	public static final String GET_VARIABLES_FOR_TESTSUITE="select * from pangeatest.variable where testsuiteid = %ID%";
	/*******************************************/
	public static final String GET_TEST_CASE_VALIDATIONS = "select tcv.id,tcv.expectedresult,tcv.testcaseid,tcv.isregularexpression from pangeatest.testcasevalidation tcv "+
																"join pangeatest.testcase tc on tc.id = tcv.testcaseid "+
																"join pangeatest.testsuite ts on tc.testsuiteid = ts.id "+
																"where ts.testsuitename = '%TESTSUITENAME%'";
	public static final String REPLACE_TC_VARS_WS = "update pangeatest.testcase set webserviceurl = replace(webserviceurl,'%VARIABLETEXT%','%VARIABLENAME%') where testsuiteid = %SUITEID%";
	public static final String REPLACE_TC_VARS_POST= "update pangeatest.testcase set postdata = replace(postdata,'%VARIABLETEXT%','%VARIABLENAME%') where testsuiteid = %SUITEID%";
	public static final String REPLACE_TC_VARS_GET = "update pangeatest.testcase set callresult = replace(callresult,'%VARIABLETEXT%','%VARIABLENAME%') where testsuiteid = %SUITEID%";
	
}
