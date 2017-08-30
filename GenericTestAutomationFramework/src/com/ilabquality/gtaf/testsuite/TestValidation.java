package com.ilabquality.gtaf.testsuite;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.ilabquality.gtaf.gtafAppl.Runner;

import javafx.beans.property.SimpleStringProperty;

public class TestValidation {
	private int id;
	private String expectedResult;
	private int testCaseID;
	private String actualResult;
	private boolean hasPassed = false;
	private Test associatedTest;
	private String liveExpectedResult;
	private boolean isRegularExpression;
	private SimpleStringProperty prop;
	
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    
	public TestValidation(){}
	public TestValidation(String expectedResult,String actualResult,boolean hasPassed, int testCaseID, Test associatedTest){
		this.expectedResult = expectedResult;
		this.actualResult = actualResult;
		this.hasPassed = hasPassed;
		this.associatedTest = associatedTest;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExpectedResult() {
		return expectedResult;//.replace("\n", "")
			//	.replace(" ", "")
			//	.replace("'", "")
				//.replace("\\", "");
	}
	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}
	public int getTestCaseID() {
		return testCaseID;
	}
	public void setTestCaseID(int testCaseID) {
		this.testCaseID = testCaseID;
	}
	public String getActualResult() {
		return actualResult;//getAssociatedTest().getCallResult();
	}
	public void setActualResult(String actualResult) {
		this.actualResult = actualResult;
	}
	public void validate() {
		
		if ( getExpectedResult().contains("Test BGi Template")){//Cater for the Test BGi Template text: value where images are Base64 and are translate by the browser. //TODO keep this, might look good. 
			setExpectedResult("");
			hasPassed = true;
			return;
		}
	
		int debug1=7;
		setLiveExpectedResult(getExpectedResult());
		try{
		//Substitute variable in the expected result.
			Enumeration<String> enu = Runner.getRuntimeVariables().keys();
			while(enu.hasMoreElements()){
				String key = enu.nextElement();
				//System.out.println("KEY:" + key);
				if ( getLiveExpectedResult().contains(key)){
					setLiveExpectedResult(getLiveExpectedResult().replace(key,Runner.getRuntimeVariables().get(key).get(0).getLiveValue()));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
//		if ( getLiveExpectedResult().contains("url") || getLiveExpectedResult().contains("Url") || getLiveExpectedResult().contains("URL")){
//			setLiveExpectedResult(getLiveExpectedResult().replace(":" + Runner.getRuntimeVariables().get("@{ServicesServerPort}").get(0).getLiveValue(), "").replace(":" + Runner.getRuntimeVariables().get("@{WorkFlowServerPort}").get(0).getLiveValue(), "")
//												.replace(":" + Runner.getRuntimeVariables().get("@{UIServerPort}").get(0).getLiveValue(), ""));
//		}
		if ( isRegularExpression()){
			 Pattern p = Pattern.compile(getExpectedResult());
			 Matcher m = p.matcher(getActualResult());
			 if(m.find()){
				 setActualResult(m.group(0));
				 //System.out.println(m.group(0));
				 hasPassed = true;
			 }
		}else{
			//byte ptext[] = getAssociatedTest().getLiveCallResult().getBytes(); 
		//	System.out.println(new String(ptext,UTF_8));
		if ( getAssociatedTest().getLiveCallResult().contains(getLiveExpectedResult())){
			hasPassed = true;
			String tmp = StringUtils.replaceOnce(getAssociatedTest().getLiveCallResult(), getLiveExpectedResult() +" ," , "");
			if ( tmp.equalsIgnoreCase(getAssociatedTest().getLiveCallResult())){//No replacements made as yet
				tmp = StringUtils.replaceOnce(tmp, getLiveExpectedResult() +",", "");//14,
			}
			if ( tmp.equalsIgnoreCase(getAssociatedTest().getLiveCallResult())){//No replacements made as yet
				tmp = StringUtils.replaceOnce(tmp, getLiveExpectedResult() +".00,", ""); //14.00
			}
			if ( tmp.equalsIgnoreCase(getAssociatedTest().getLiveCallResult())){//No replacements made as yet
				tmp = StringUtils.replaceOnce(tmp, getLiveExpectedResult() +".000,", "");//14.000
			}
			if ( tmp.equalsIgnoreCase(getAssociatedTest().getLiveCallResult())){//No replacements made as yet
				tmp = StringUtils.replaceOnce(tmp, getLiveExpectedResult() +"}", "");//14}
			}
			if ( tmp.equalsIgnoreCase(getAssociatedTest().getLiveCallResult())){//No replacements made as yet
				tmp = StringUtils.replaceOnce( tmp,"\"" + getLiveExpectedResult() +"\",", "");//"14", 
				int debug=0;
			}			
			if ( tmp.equalsIgnoreCase(getAssociatedTest().getLiveCallResult())){//No replacements made as yet
				tmp = StringUtils.replaceOnce( tmp,"\"" + getLiveExpectedResult() +"\"", "");//"14", 
				int debug=0;
			}
			if ( tmp.equalsIgnoreCase(getAssociatedTest().getLiveCallResult())){//No replacements made as yet
				tmp = StringUtils.replaceOnce( tmp,"[\"" + getLiveExpectedResult() +"\"]", "");//["14"]
			}
			if ( tmp.equalsIgnoreCase(getAssociatedTest().getLiveCallResult())){//No replacements made as yet
				tmp = StringUtils.replaceOnce( tmp, getLiveExpectedResult(), "");
			}
//			if ( tmp.equalsIgnoreCase(getAssociatedTest().getLiveCallResult())){//No replacements made as yet
//				tmp = StringUtils.replaceOnce( tmp, getLiveExpectedResult(), "");
//			}
			
			if ( tmp.equalsIgnoreCase(getAssociatedTest().getLiveCallResult())){//No replacements made as yet
				System.out.println("No Match:::::::::::\n" + tmp+"\n" + getLiveExpectedResult());
			}
			
			getAssociatedTest().setLiveCallResult(tmp);
			
			
		}else if (getAssociatedTest().getLiveCallResult().contains("\\\"")){
			int c = 1;
			getAssociatedTest().setLiveCallResult(getAssociatedTest().getLiveCallResult().replace("\\\"", "\""));
			validate();
		}else{
				//check if numbers, if so, Decimal format
				String[] vals = getExpectedResult().split(",");
				boolean matched = false;
				for ( int i = 0 ; i < vals.length; i ++ ){
					//System.out.println(vals[i]);
					String[] splitItems = vals[i].split(":");
					for ( int j=0;j< splitItems.length;j++){
						try{
							double dd = Double.parseDouble(splitItems[j]);
							DecimalFormat df = new DecimalFormat("#.##");
							df.setMinimumFractionDigits(4);
							String temp = df.format(dd);
							if ( getAssociatedTest().getLiveCallResult().contains(temp)){
								matched = true;
								getAssociatedTest().setLiveCallResult(StringUtils.replaceOnce(getAssociatedTest().getLiveCallResult(), temp , ""));
							}else{
								//Attempt to match , removing a digit from the precision per iteration.
								//System.out.println("Adjusting precision for value"  + splitItems[j]);
								int precision = 3;
								while(!matched && precision >= 0){
									double dd2 = Double.parseDouble(splitItems[j]);
									DecimalFormat df2 = new DecimalFormat("#.##");
									df2.setMinimumFractionDigits(precision);
									String temp2 = df2.format(dd2);
									//System.out.println("Looking for value ::"+ splitItems[0] +":" + temp2 );
									if ( getAssociatedTest().getLiveCallResult().contains(splitItems[0] +":" + temp2)){
										matched = true;
										getAssociatedTest().setLiveCallResult(StringUtils.replaceOnce(getAssociatedTest().getLiveCallResult(), splitItems[0] +":" +temp2 , ""));
									}else{									
										precision--;
									}
									
								}
							}
							//splitItems[j] = df.format(splitItems[j]);
						}catch(java.lang.IllegalArgumentException nfe){
						}
					}
				}
				if (matched ){
					hasPassed = true;
				}else{
				hasPassed = false;
				}
			}
		}
		if ( !hasPassed ){
			Runner.cliExitCode = 2; // Test has failed, set the exit code to 2 for Jenkins CI
			getAssociatedTest().setHasPassed(false);
		}
	}
	public boolean hasPassed() {
		return hasPassed;
	}
	public void setHasPassed(boolean hasPassed) {
		this.hasPassed = hasPassed;
	}
	public Test getAssociatedTest() {
		return associatedTest;
	}
	public void setAssociatedTest(Test associatedTest) {
		this.associatedTest = associatedTest;
	}
	public String getLiveExpectedResult() {
		return ( liveExpectedResult== null?"null":liveExpectedResult);
	}
	public void setLiveExpectedResult(String liveExpectedResult) {
		this.liveExpectedResult = liveExpectedResult;
	}
	public boolean isRegularExpression() {
		return isRegularExpression;
	}
	public void setRegularExpression(boolean isRegularExpression) {
		this.isRegularExpression = isRegularExpression;
	}
	public SimpleStringProperty getProp() {
		return prop;
	}
	public void setProp(SimpleStringProperty prop) {
		this.prop = prop;
	}
	

	
}
