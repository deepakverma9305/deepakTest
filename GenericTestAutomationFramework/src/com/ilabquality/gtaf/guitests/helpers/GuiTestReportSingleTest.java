package com.ilabquality.gtaf.guitests.helpers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

public class GuiTestReportSingleTest {
	private String testName;
	private String testDescription;
	private ArrayList<TestStep> testSteps = new ArrayList<TestStep>();
	private StringBuilder reportContent = new StringBuilder();
	
	public GuiTestReportSingleTest(Throwable e){
		setTestName("Exception occured. Cannot instantiate Clazz");
		setTestDescription(e.getCause().getMessage());
	}
	public GuiTestReportSingleTest(){}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()) + " - "  + testName;
	}
	public String getTestDescription() {
		return testDescription;
	}
	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}
	public ArrayList<TestStep> getTestSteps() {
		return testSteps;
	}
	public void setTestSteps(ArrayList<TestStep> testSteps) {
		this.testSteps = testSteps;
	}
	
	public void addTestStep(TestStep ts, WebDriver driver){
		int debug=0;
		LogEntries logs = driver.manage().logs().get("browser");
		//System.out.println("logs.size is" + logs.getAll().size());
		testSteps.add(ts);
		for ( LogEntry le : logs ){
			if ( le.getLevel() == Level.SEVERE ){
				System.out.println("*******************************************************Severe Error Found**************************************************************");
				System.out.println(le.getMessage());
				TestStep tss= new TestStep();
				tss.setStepName("*******************************************************[SEVERE] Console Error Found**************************************************************");
				tss.setPassed(false);
				tss.setTestStepImage(((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64));
				tss.setOptionalFailureMessage(le.getMessage());
				testSteps.add(tss);
			}
		}
		((JavascriptExecutor)driver).executeScript("console.clear()");
		logs = driver.manage().logs().get("browser");
	}
	
	public void addTestSteps(GuiTestReportSingleTest newTest){
		for ( TestStep ts : newTest.getTestSteps()){
			testSteps.add(ts);
		}
	}

	
	public String getReportContent(){
		int debug=2;
		try{
			int counter = 1;
			for (TestStep ts : testSteps)
			{
				reportContent.append(ts.getReportContent(counter));
				counter++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return reportContent.toString();
	}
	public boolean hasPassed() {
		boolean testHasPassed = true;
		for (TestStep nextEl : testSteps){
			if (  nextEl == null || !nextEl.hasPassed()  ){
				testHasPassed = false;
				break;
			}
		}
		return testHasPassed;
	}
	public String getOptionalMessage() {
		String msg = "No Message Defined";
		for (TestStep ts : testSteps){
			if ( ts.hasPassed() == false){
				msg = ts.getOptionalFailureMessage();
				break;
			}
		}
		return msg;
	}
}
