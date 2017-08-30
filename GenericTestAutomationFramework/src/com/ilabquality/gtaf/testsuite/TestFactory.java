package com.ilabquality.gtaf.testsuite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ilabquality.gtaf.utils.database.PostgresDBWrapper;
import com.ilabquality.gtaf.utils.sql.SQL;

public class TestFactory {

	public ArrayList<String> getTestNames() throws SQLException{
		ArrayList<String> testNames = new ArrayList<String>();
		
			PostgresDBWrapper db = new PostgresDBWrapper();
			db.connectToTest();
			ResultSet rs = db.executeQuery(SQL.GET_TESTS);
			while(rs.next()){
				testNames.add(rs.getString("TestName"));
			}
			db.close();
	
		return testNames;
	}
}
