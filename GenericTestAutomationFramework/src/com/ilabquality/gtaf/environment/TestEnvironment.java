package com.ilabquality.gtaf.environment;

public class TestEnvironment extends Environment {
	//Database Details
	private String _dbServerIP, _dbSID, _dbUserName, _dbPassword;

	public TestEnvironment(String name){
    	this.setName(name);
    	
	}

	/**
	 * @return the _dbServerIP
	 */
	public String getDBServerIP() {
		return _dbServerIP;
	}
	/**
	 * @param _dbServerIP the _dbServerIP to set
	 */
	public void setDBServerIP(String _dbServerIP) {
		this._dbServerIP = _dbServerIP;
	}
	/**
	 * @return the _dbSID
	 */
	public String getDBSSID() {
		return _dbSID;
	}
	/**
	 * @param _dbSID the _dbSID to set
	 */
	public void setDBSID(String _dbSID) {
		this._dbSID = _dbSID;
	}
	/**
	 * @return the _dbUserName
	 */
	public String getDBUserName() {
		return _dbUserName;
	}
	/**
	 * @param _dbUserName the _dbUserName to set
	 */
	public void setDBUserName(String _dbUserName) {
		this._dbUserName = _dbUserName;
	}
	/**
	 * @return the _dbPassword
	 */
	public String getDBPassword() {
		return _dbPassword;
	}
	/**
	 * @param _dbPassword the _dbPassword to set
	 */
	public void setDBPassword(String _dbPassword) {
		this._dbPassword = _dbPassword;
	}
}