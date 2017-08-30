package com.ilabquality.gtaf.guitests.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class SeleniumListener implements WebDriverEventListener {
	GuiTestReportSingleTest activeTest;
	TestStep currentStep;
	private static int waitTimer = 150;
	///////////////////////////////////////////////////////////////////////////////////////////
	public void beforeClickOn(WebElement arg0, WebDriver arg1) {
		try {
			Thread.sleep(waitTimer);
		} catch (InterruptedException e) {
			// 
			e.printStackTrace();
		}
//		currentStep = new TestStep();
//		currentStep.setStepName(("Click on the " + arg0.toString().split("->")[1] + " object.").replace("]", ""));
//		//System.out.println("About to click on the " + arg0.toString());
//		currentStep.setTestStepImage(takeScreenshot(arg1));
	}
	public void afterClickOn(WebElement arg0, WebDriver arg1) {
		try {
			Thread.sleep(waitTimer);
		} catch (InterruptedException e) {
			// 
			e.printStackTrace();
		}
//		sleep();
//		currentStep.setTestStepImage(takeScreenshot(arg1));
//		currentStep.setPassed(true);
//		activeTest.addTestStep(currentStep);
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	public void beforeNavigateTo(String arg0, WebDriver arg1) {
		try {
			Thread.sleep(waitTimer);
		} catch (InterruptedException e) {
			// 
			e.printStackTrace();
		}
//		currentStep= new TestStep();
//		currentStep.setStepName("beforeNavigateToNavigate to " + arg0);
	}
	public void afterNavigateTo(String arg0, WebDriver arg1) {
		try {
			Thread.sleep(waitTimer);
		} catch (InterruptedException e) {
			// 
			e.printStackTrace();
		}
//		sleep();
//		currentStep.setTestStepImage(takeScreenshot(arg1));
//		currentStep.setPassed(true);
//		activeTest.addTestStep(currentStep);
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	public void onException(Throwable arg0, WebDriver arg1) {
		currentStep.setPassed(false);
		currentStep.setOptionalFailureMessage(arg0.getMessage());
		currentStep.setTestStepImage(takeScreenshot(arg1));
		System.out.println("SeleniumException ::\n" + arg0.getMessage());
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
		try {
			Thread.sleep(waitTimer);
		} catch (InterruptedException e) {
			// 
			e.printStackTrace();
		}
//		
//		if ( arg2 == null ){
//			currentStep= new TestStep();
//			currentStep.setStepName("Clear the " + arg0 +  " element.");
//		}else{
//			currentStep= new TestStep();
//			currentStep.setStepName("Enter the text["+ arg2[0].toString() +"] into the " + arg0 +  " element.");
//		}
	}

	public void afterChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
		try {
			Thread.sleep(waitTimer);
		} catch (InterruptedException e) {
			// 
			e.printStackTrace();
		}
//		sleep();
//		currentStep.setTestStepImage(takeScreenshot(arg1));
//		currentStep.setPassed(true);
//		activeTest.addTestStep(currentStep);
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void beforeNavigateRefresh(WebDriver arg0) {	try {
		Thread.sleep(waitTimer);
	} catch (InterruptedException e) {
		// 
		e.printStackTrace();
	}}
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1) {	try {
		Thread.sleep(waitTimer);
	} catch (InterruptedException e) {
		// 
		e.printStackTrace();
	}}

	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {	try {
		Thread.sleep(waitTimer);
	} catch (InterruptedException e) {
		// 
		e.printStackTrace();
	}}
	public void afterNavigateBack(WebDriver arg0) {	try {
		Thread.sleep(waitTimer);
	} catch (InterruptedException e) {
		// 
		e.printStackTrace();
	}}
	public void afterNavigateForward(WebDriver arg0) {	try {
		Thread.sleep(waitTimer);
	} catch (InterruptedException e) {
		// 
		e.printStackTrace();
	}}
	
	public void afterScript(String arg0, WebDriver arg1) {	try {
		Thread.sleep(waitTimer);
	} catch (InterruptedException e) {
		// 
		e.printStackTrace();
	}}
	public void afterNavigateRefresh(WebDriver arg0) {	try {
		Thread.sleep(waitTimer);
	} catch (InterruptedException e) {
		// 
		e.printStackTrace();
	}}
	public void beforeNavigateBack(WebDriver arg0) {	try {
		Thread.sleep(waitTimer);
	} catch (InterruptedException e) {
		// 
		e.printStackTrace();
	}}
	public void beforeNavigateForward(WebDriver arg0) {	try {
		Thread.sleep(waitTimer);
	} catch (InterruptedException e) {
		// 
		e.printStackTrace();
	}}
	public void beforeScript(String arg0, WebDriver arg1) {	try {
		Thread.sleep(waitTimer);
	} catch (InterruptedException e) {
		// 
		e.printStackTrace();
	}}
	public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {	try {
		Thread.sleep(waitTimer);
	} catch (InterruptedException e) {
		// 
		e.printStackTrace();
	}}
	public String takeScreenshot(WebDriver driver){
		try {
			Thread.sleep(waitTimer);
		} catch (InterruptedException e) {
			// 
			e.printStackTrace();
		}
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
	}
	
	public TestStep getActiveStep(){
		return this.currentStep;
	}
	
	public void sleep(){
		try{
			Thread.currentThread();
			Thread.sleep(waitTimer);
		}catch(Exception e){
			
		}
	}
	public void setActiveTest(GuiTestReportSingleTest t) {
		this.activeTest = t;
	}
}
