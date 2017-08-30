package com.ilabquality.gtaf.gtafgui.variableSelector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.testsuite.Test;
import com.ilabquality.gtaf.testsuite.TestSuite;
import com.ilabquality.gtaf.testsuite.Variable;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class VariablesSelectorController {

	@FXML private ListView<String> availableVariablesList;
	@FXML private TextArea serviceCallText;
	@FXML private ListView<String> testSuiteVariablesList;
	private Object callerObject;
	private TestSuite selectedSuite;
	private int caretPosition;
	public VariablesSelectorController(){
		//System.out.println("VariablesSelectorController constructed");
	}
	
	public void setSelectedSuite(TestSuite t){
		this.selectedSuite = t;
	}
	public void setCaretPosition(int position){
		this.caretPosition = position;
	}
	
	public void initialize(){
		 initEventHandlers();

	}
    
 
	private void initEventHandlers(){
      availableVariablesList.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
    	  public void handle(MouseEvent click) {

    	        if (click.getClickCount() == 2) {
    	        	handleEvent(click);
    	        }
    	    }
        });
      
      
      testSuiteVariablesList.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
    	  public void handle(MouseEvent click) {

  	        if (click.getClickCount() == 2) {
  	        	handleEvent(click);
  	        }
  	    }
      });
    }
	
	public void loadData(){
        Enumeration<String> enu = Runner.getRuntimeVariables().keys();
        while(enu.hasMoreElements()){
        	String key = enu.nextElement();
        	ArrayList<Variable> arr = Runner.getRuntimeVariables().get(key);
        	for ( Variable v : arr ){
	        	if (( v.getVariableOriginalValue() != null && !v.getVariableOriginalValue().equalsIgnoreCase("null")) ){
	        		if (!availableVariablesList.getItems().contains(v.getVariableName() + " -- OV: " + v.getVariableOriginalValue() )) {
	        			availableVariablesList.getItems().add(v.getVariableName() + " -- OV: " + v.getVariableOriginalValue() );
	        		}
	        	}
	        	if ( v.isSystemRuntimeVariable()){
	        		availableVariablesList.getItems().add(v.getVariableName() + " -- OV: " + v.getLiveValue());
	        	}
        	}
        }
        Collections.sort(availableVariablesList.getItems());
        ArrayList<Test> tests = selectedSuite.getSuiteData();
        for  (Test t : tests){
        	ArrayList<Variable> vars = t.getVariables();
        	for (Variable v : vars){
        	   	if ( v.getVariableAction().equalsIgnoreCase(Variable.EXTRACTED)){
	        		if (!testSuiteVariablesList.getItems().contains(v.getVariableName() + " -- OV: " + v.getVariableOriginalValue() )) {
	        			testSuiteVariablesList.getItems().add(v.getVariableName() + " -- OV: " + v.getVariableOriginalValue() );
	        		}
	        	}
        	}
    	}
        Collections.sort(testSuiteVariablesList.getItems());
	}
	    
    @SuppressWarnings("unchecked")
	public void handleEvent(MouseEvent e){
    	String id = ( (ListView<String>)e.getSource()).getId();
    	
    	   //Use ListView's getSelected Item
          String currentItemSelected =  "";
          switch (id) {
          case "availableVariablesList":{
        	  currentItemSelected = availableVariablesList.getSelectionModel().getSelectedItem();
    		  break;
    	  }
          case "testSuiteVariablesList":{
        	  currentItemSelected = testSuiteVariablesList.getSelectionModel().getSelectedItem();
    		  break;
          }
          default:{
        	  currentItemSelected = testSuiteVariablesList.getSelectionModel().getSelectedItem();
    		  break;
    	  }
        	  
        	  
          }
          if ( callerObject instanceof TextField )
          {
        	  TextField tf = (TextField)callerObject;
        	  System.out.println("********************"+tf.getCaretPosition());
        	  System.out.println(currentItemSelected.substring(0, currentItemSelected.indexOf(" ")));
        	  System.out.println(tf.getText().length());
        	  if (tf.getText().length() <= caretPosition)
        		  caretPosition = tf.getText().length() -1;
        	  tf.insertText(caretPosition+1, (currentItemSelected.substring(0, currentItemSelected.indexOf(" "))).replace("@", ""));
        	  Variable v = new Variable();
        	  v.setTestCaseID(selectedSuite.getSelectedTest().getId());
        	  v.setVariableName(currentItemSelected.substring(0, currentItemSelected.indexOf(" ")));
        	  addToRuntimeVariables(v);
          }else if (callerObject instanceof TextArea){
        	  TextArea ta = (TextArea) callerObject;
        	  if (ta.getText().length() <= caretPosition)
        		  caretPosition = ta.getText().length() -1;
        	  ta.insertText(caretPosition+1, (currentItemSelected.substring(0, currentItemSelected.indexOf(" "))).replace("@", ""));
        	  Variable v = new Variable();
        	  v.setTestCaseID(selectedSuite.getSelectedTest().getId());
        	  v.setVariableName(currentItemSelected.substring(0, currentItemSelected.indexOf(" ")));
        	  addToRuntimeVariables(v);
          }
          ((Stage) ( (ListView<String>) e.getSource()).getScene().getWindow()).close();
    }
	    
    private void addToRuntimeVariables(Variable v){
    	 if ( Runner.getRuntimeVariables().containsKey(v.getVariableName())){
    		 Runner.getRuntimeVariables().get(v.getVariableName()).add(v);
	   	  }else{
	   		Runner.getRuntimeVariables().put(v.getVariableName(), new ArrayList<Variable>(Arrays.asList(v)));
	   	  }
    }
	   
	public void setCallingObject(Object o){
		this.callerObject = o;
	}
}
