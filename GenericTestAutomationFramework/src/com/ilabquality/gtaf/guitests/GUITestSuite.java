package com.ilabquality.gtaf.guitests;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.ObservableList;

public class GUITestSuite {
	private int id;
	private String name;
	private ArrayList<GUITestDef> suiteData = new ArrayList<GUITestDef>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<GUITestDef> getSuiteData() {
		return suiteData;
	}
	public void setSuiteData(ArrayList<GUITestDef> suiteData) {
		this.suiteData = suiteData;
	}
	public void addTest(String buttonText) throws SQLException {
		GUITestDef gtd = new GUITestFactory().getGuiTestDef(buttonText);
		gtd.setExecutionOrder(-1);
		this.getSuiteData().add(gtd);
	}
	public void addAll(ObservableList<String> items) throws SQLException {
		for ( String s : items ){
			addTest(s);
		}
		
	}
	public void removeTest(String buttonText) {
		GUITestDef gtdToRemove = null;
		for ( GUITestDef gtd : this.getSuiteData()){
			if ( gtd.getTestName().equalsIgnoreCase(buttonText.substring(0,buttonText.indexOf(" -")))){
				gtdToRemove = gtd;
				break;
			}
		}
		this.getSuiteData().remove(gtdToRemove);
	}
	
}
