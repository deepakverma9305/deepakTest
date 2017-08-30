package com.ilabquality.gtaf.environment;

public class TestRepository {
	
	private String _name;
	private String _trServerIP;
	private String _trUserName;
	private String _trPassword;
	private String _trSID;
	private String _trLocalFolder;
	
	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}
	/**
	 * @return the _trServerIP
	 */
	public String get_trServerIP() {
		return _trServerIP;
	}
	/**
	 * @return the _trUserName
	 */
	public String get_trUserName() {
		return _trUserName;
	}
	/**
	 * @return the _trPassword
	 */
	public String get_trPassword() {
		return _trPassword;
	}
	/**
	 * @return the _trSID
	 */
	public String get_trSID() {
		return _trSID;
	}
	/**
	 * @param _trServerIP the _trServerIP to set
	 */
	public void set_trServerIP(String _trServerIP) {
		this._trServerIP = _trServerIP;
	}
	/**
	 * @param _trUserName the _trUserName to set
	 */
	public void set_trUserName(String _trUserName) {
		this._trUserName = _trUserName;
	}
	/**
	 * @param _trPassword the _trPassword to set
	 */
	public void set_trPassword(String _trPassword) {
		this._trPassword = _trPassword;
	}
	/**
	 * @param _trSID the _trSID to set
	 */
	public void set_trSID(String _trSID) {
		this._trSID = _trSID;
	}

	public String get_trLocalFolder() {
		return _trLocalFolder;
	}

	public void set_trLocalFolder(String _trLocalFolder) {
		this._trLocalFolder = _trLocalFolder;
	}
}
