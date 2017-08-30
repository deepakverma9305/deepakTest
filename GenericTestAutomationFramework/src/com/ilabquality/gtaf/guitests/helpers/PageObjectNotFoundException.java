package com.ilabquality.gtaf.guitests.helpers;

public class PageObjectNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PageObjectNotFoundException(String objName){
		super("The Page Object [" + objName + "] could not be located on the current page. Have you loaded the correct page in the script? \n Check the %page%.getPageObject....");
	}
}
