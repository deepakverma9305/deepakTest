package com.ilabquality.gtaf.packmanager;

import java.util.ArrayList;

public class Pack {

	private int id;
	private String packName;
	private ArrayList<TestPackTestSuite> packData = new ArrayList<TestPackTestSuite>();
	
	public void addSuiteData(TestPackTestSuite suite){
		this.packData.add(suite);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}

	public ArrayList<TestPackTestSuite> getPackData() {
		return packData;
	}

	public void setPackData(ArrayList<TestPackTestSuite> packData) {
		this.packData = packData;
	}

	public void removeSuiteData(int id){
		this.packData.remove(id);
	}
	

	
}
