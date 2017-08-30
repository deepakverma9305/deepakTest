package com.ilabquality.gtaf.guitests.helpers;

import org.openqa.selenium.By;

public class PageObject {
	private By by;
	private String locator;
	private String id;
	private String maxLength;
	private String type;
	
	public By getBy() {
		return by;
	}
	public void setBy(By by) {
		this.by = by;
	}
	public String getLocator() {
		return locator;
	}
	public void setLocator(String locator) {
		this.locator = locator;
	}
	public String getId() {
		return id;
	}
	public void setId(String name) {
		this.id = name;
	}
	
	public String toString(){
		return 
				"\nPageObject: Name : " + getId() + "\n By:" + by.toString()+"\n maxLength:" + getMaxLength() +"\n Type : " + getType();
	}
	public String getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	public void setType(String type) {
		this.type= type;
	}
	
	public String getType(){
		return this.type;
	}
}
