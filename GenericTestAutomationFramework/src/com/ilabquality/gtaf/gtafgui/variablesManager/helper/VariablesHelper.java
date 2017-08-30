package com.ilabquality.gtaf.gtafgui.variablesManager.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.testsuite.TestSuite;
import com.ilabquality.gtaf.testsuite.Variable;

public class VariablesHelper {

	private TestSuite suite;
	public VariablesHelper(TestSuite suite){
		this.suite = suite;
	}
	
	public String substituteLiveValues(String inputString){
		if (inputString != null && inputString.equalsIgnoreCase("")) return "";
		inputString = inputString.replace("\n", "");//.replace(" ", "");
		
		int debug=2;
		try{
			Enumeration<String> enu = Runner.getRuntimeVariables().keys();
			while(enu.hasMoreElements()){
				String key = enu.nextElement();
				//System.out.println(key);
				ArrayList<Variable> arr = Runner.getRuntimeVariables().get(key);
				for (Variable v : arr ){
					//extraction
					if ( (v.getVariableStartValue() != null && !v.getVariableStartValue().equalsIgnoreCase("null")) && v.getTestCaseID() == suite.getSelectedTest().getId() ){ 
						String variableText = StringUtils.substringBetween(inputString,v.getVariableStartValue(), v.getVariableEndValue());
						if ( variableText != null ){
							v.setLiveValue(variableText);
							v.setVariableAction(Variable.EXTRACTED);
							suite.getSelectedTest().getVariables().add(v);
							System.out.println("Extracted variable " + v.getVariableName() + " with value " + variableText + "for test " + v.getTestCaseID());
						}
					}
					//substitution
					if ( inputString.contains(v.getVariableName()) ){ 
						String val =  v.getLiveValue();
						if ( val != null && !"null".equalsIgnoreCase( val )){
							inputString = inputString.replace("@" + v.getVariableName(),val).replace(v.getVariableName(), val);
							v.setVariableAction(Variable.SUBSTITUTED);
//							v.setLiveValue(val);
							suite.getSelectedTest().getVariables().add(v);
							System.out.println("Substituted variable " + v.getVariableName() + " with value " + val);
						}
					}
				}
			}
			int debug2= 0 ;
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Unable to provide Variables - " + e.getMessage() , e);
			e.printStackTrace();
		}
		return inputString;
	}
	
	public void substituteURLs(){
		int debug=1;
		try{
		Enumeration<String> enu = Runner.getRuntimeVariables().keys();
		while(enu.hasMoreElements()){
			String key = enu.nextElement();
			ArrayList<Variable> arr = Runner.getRuntimeVariables().get(key);
			for (Variable v : arr ){
				if (suite.getSelectedTest().getLiveURL() != null && (suite.getSelectedTest().getLiveURL().contains(v.getVariableName()) || suite.getSelectedTest().getLiveURL().contains("@" + v.getVariableName() ) && suite.getSelectedTest().getLiveURL() != null
						&& "null".equalsIgnoreCase(suite.getSelectedTest().getLiveURL()))){
					v.setLiveValue(Runner.getRuntimeVariables().get(key).get(0).getLiveValue());
					System.out.println("detected variable on URL, replacing " + v.getVariableName() + " with value " + v.getLiveValue());
					suite.getSelectedTest().getVariables().add(v);
					suite.getSelectedTest().setLiveURL(suite.getSelectedTest().getLiveURL().replace( v.getVariableName(), v.getLiveValue()));
					
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("LiveURL set to " + suite.getSelectedTest().getLiveURL());
	}
	public String substituteURLs_Selenium(String url){
		try{
		Enumeration<String> enu = Runner.getRuntimeVariables().keys();
		while(enu.hasMoreElements()){
			String key = enu.nextElement();
			ArrayList<Variable> arr = Runner.getRuntimeVariables().get(key);
			for (Variable v : arr ){
				if (url.contains(v.getVariableName()) || url.contains("@" + v.getVariableName() ) ){
					//v.setLiveValue(Runner.getRuntimeVariables().get(key).get(0).getLiveValue());
					System.out.println("detected variable on URL, replacing " + v.getVariableName() + " with value " + v.getLiveValue());
					url = url.replace( v.getVariableName(), v.getLiveValue() );
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}
	public synchronized String getVariableValue(String variable){
		System.out.println("Attempting to retrieve a live value for variable " + variable);
		String returnString = "No Value Retrieved!!!";
		Enumeration<String> enu = Runner.getRuntimeVariables().keys();
		while(enu.hasMoreElements()){
			ArrayList<Variable> arr = Runner.getRuntimeVariables().get(enu.nextElement());
			for (Variable v : arr ){
				if ( v.getVariableName().equalsIgnoreCase(variable)){
					returnString = v.getLiveValue();
				}
			}
		}
		return returnString;
	}
	
	/**
	 * Loads only base variables, any added variables will be removed. 
	 */
	public void refreshVariables(){
		Hashtable<String,ArrayList<Variable>> runTimeVariables1 = new Hashtable<>();
		Enumeration<String> keys = Runner.getRuntimeVariables().keys();
		while ( keys.hasMoreElements()){
			String key = keys.nextElement();
			Variable v = Runner.getRuntimeVariables().get(key).get(0);
			if ( v.isSystemRuntimeVariable()){
				runTimeVariables1.put(key, new ArrayList<Variable>(Arrays.asList(v)));
			}
		}
		
		Runner.getRuntimeVariables().clear();
		Enumeration<String> keyss = runTimeVariables1.keys();
		while ( keyss.hasMoreElements()){
			String key = keyss.nextElement();
			Variable v = runTimeVariables1.get(key).get(0);
			v.setSystemRuntimeVariable(true);
			Runner.getRuntimeVariables().put(key, new ArrayList<Variable>(Arrays.asList(v)));
		}
	}
}
