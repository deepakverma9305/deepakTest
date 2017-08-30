package com.ilabquality.gtaf.guitests.helpers;

import java.util.ArrayList;

public class Page {
	private String title;
	private ArrayList<PageObject> pageObjects = new ArrayList<PageObject>();
	private String pageName;
	
	public Page(String pageName,String title){
		this.setPageName(pageName);
		this.setTitle(title);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<PageObject> getPageObjects() {
		return pageObjects;
	}
	public PageObject getPageObjectByID(String name ) throws PageObjectNotFoundException{
		PageObject returnObject = null;
		for (PageObject po : pageObjects){
			if (po.getId().equalsIgnoreCase(name)){
				returnObject = po;
				break;
			}
		}
		if (returnObject == null )
			throw new PageObjectNotFoundException(name);
		return returnObject;
	}
	public void setPageObjects(ArrayList<PageObject> pageObjects) {
		this.pageObjects = pageObjects;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String toString(){
		return 
				"Page:\n Name:" + getPageName() +"\n PageObjects:" + getPageObjects().size();
	}
}
