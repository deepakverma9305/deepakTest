package com.ilabquality.gtaf.gtafgui.variablesManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilabquality.gtaf.gtafAppl.GTAFApplController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.testsuite.IllegalVariableStateException;
import com.ilabquality.gtaf.testsuite.TestSuite;
import com.ilabquality.gtaf.testsuite.Variable;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class VariablesManagerController {

	@FXML private TextArea serviceCallText;
	@FXML private ListView<String> storedTestVariables;
	@FXML private ListView<String> storedSuiteVariables;
	@FXML private Button testButton;
	@FXML private Button saveButton;
	@FXML private Button cancelButton;
	@FXML private Button makePrettyButton;
	@FXML private TextArea fromText;
	@FXML private TextArea toText;
	@FXML private TextField resultText;
	@FXML private TextField saveAsID;
	
	private TestSuite selectedTestSuite;
	private GTAFApplController caller;
	private boolean isEditing = false;
	public void setSelectedTestSuite(TestSuite suite){
		this.selectedTestSuite = suite;
	}

	public void setCaller(GTAFApplController newCaller){
		this.caller = newCaller;
	}
	
	public void initialize(){
		
	        initEventHandlers();
	       
	}
	public void loadData(){
		 String postMode = caller.lblWebServiceVerbAction.getText();
	        if ( selectedTestSuite.getSelectedTest() != null){
		        switch(postMode.toLowerCase()){
			        case "post data":{
			        	serviceCallText.setText(selectedTestSuite.getSelectedTest().getPostData());
			        	break;
			        }
			        case "call result":{
			        	serviceCallText.setText(selectedTestSuite.getSelectedTest().getCallResult());
			        	break;
			        }
		        }
	        }
	        populateLists();
	}
	private void initEventHandlers(){
      testButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
		    @Override public void handle(javafx.event.ActionEvent e) {
		       try {
				doButtonAction(e);
			} catch (IllegalVariableStateException | SQLException e1) {
				LoggerFactory.getLogger(Runner.class).error("Exception!", e1);
			}
		    }
        });
      saveButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
	    @Override public void handle(javafx.event.ActionEvent e) {
	       try {
			doButtonAction(e);
		} catch (IllegalVariableStateException | SQLException e1) {
			LoggerFactory.getLogger(Runner.class).error("Exception!", e1);
		}
	    }
      });
      cancelButton.setOnAction(e ->
			{
				try {
					doButtonAction(e);
				} catch (IllegalVariableStateException e2) {
					// 
					e2.printStackTrace();
				} catch (SQLException e2) {
					// 
					e2.printStackTrace();
				}
			}
		
        ); 
      	makePrettyButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
  		    @Override public void handle(javafx.event.ActionEvent e) {
   		       try {
   		    	doButtonAction(e);
 			} catch (IllegalVariableStateException | SQLException e1) {
 				LoggerFactory.getLogger(Runner.class).error("Exception!", e1);
 			}
   		    }
           });
      storedTestVariables.setOnMouseClicked(new EventHandler<MouseEvent>() {

          @Override
          public void handle(MouseEvent event) {
              if ( event.getButton().equals(MouseButton.SECONDARY)){
            	  System.out.println("RightClick");
            	  ContextMenu cm = new ContextMenu();
            	  MenuItem mi = new MenuItem("Delete");
            	  mi.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            		 
                      @Override
                      public void handle(javafx.event.ActionEvent event) {
                    		 System.out.println("Delete variable");
                    		 deleteVariable(storedTestVariables.getSelectionModel().getSelectedIndex());
                    	  }

					
            	  });
            	  cm.getItems().add(mi);
            	  cm.show( storedTestVariables,event.getScreenX(),event.getScreenY());
              }else if ( event.getButton().equals(MouseButton.PRIMARY)){
            	  fromText.clear();
            	  toText.clear();
            	  saveAsID.clear();
            	  String s =storedTestVariables.getSelectionModel().getSelectedItem().substring(0,storedTestVariables.getSelectionModel().getSelectedItem().indexOf(" "));
            	  ArrayList<Variable> v = selectedTestSuite.getSelectedTest().getVariables();
            	  for ( Variable var : v) {
            		  if ( var.getVariableName().equalsIgnoreCase(s) && var.getVariableAction().equalsIgnoreCase(Variable.EXTRACTED)){
            			  fromText.setText( var.getVariableStartValue());
                    	  toText.setText( var.getVariableEndValue());
                    	  saveAsID.setText( var.getVariableName().replace("@", "").replace("{","").replace("}", ""));
                    	  isEditing = true;	  
            		  }
            	  }
              }
          }});
    }
	
	public void doButtonAction(javafx.event.ActionEvent e) throws IllegalVariableStateException, SQLException{
	LoggerFactory.getLogger(Runner.class).info(e.getSource().toString());
	if ( e.getSource() instanceof Button ){
		
		String buttonText = ( (Button) e.getSource()).getId();
		LoggerFactory.getLogger(Runner.class).info("Button Text is " + buttonText);
		if ( buttonText.equalsIgnoreCase("testButton")){
			
			String startText = fromText.getText().replace("\n", "").replace("\t", "").replace(" ", "");
			String endText = toText.getText().replace("\n", "").replace("\t", "").replace(" " , "");
			String cr = "";
			String postMode = caller.lblWebServiceVerbAction.getText();
	        switch(postMode.toLowerCase()){
		        case "post data":{
		        	cr = selectedTestSuite.getSelectedTest().getPostData().replace("\n", "").replace("\t", "").replace(" ", "");
		        	break;
		        }
		        case "call result":{
		        	cr = selectedTestSuite.getSelectedTest().getCallResult().replace("\n", "").replace("\t", "").replace(" ", "");
		        	break;
		        }
		        default:break;
	        }
			String variableText = StringUtils.substringBetween(cr,startText, endText);
			resultText.setText(variableText);
		}
		else if (buttonText.equalsIgnoreCase("saveButton")){
			if ( !isEditing ){
				if ( Runner.getRuntimeVariables().containsKey("@{" + saveAsID.getText() +"}")){
					new Alert(AlertType.ERROR,"The variable " + saveAsID.getText() + "  already exists.",ButtonType.OK).showAndWait();
					return;
				}
			}
			
				String startText = fromText.getText().replace("\n", "").replace("\t", "").replace(" ", "");
				String endText = toText.getText().replace("\n", "").replace("\t", "").replace(" ", "");
				String cr = "";
				String postMode =caller.lblWebServiceVerbAction.getText();
		        switch(postMode.toLowerCase()){
			        case "post data":{
			        	cr = selectedTestSuite.getSelectedTest().getPostData().replace("\n", "").replace("\t", "").replace(" ", "");
			        	break;
			        }
			        case "call result":{
			        	cr = selectedTestSuite.getSelectedTest().getCallResult().replace("\n", "").replace("\t", "").replace(" ", "");
			        	break;
			        }
			        default:break;
		        }
				String variableText = StringUtils.substringBetween(cr,startText, endText);
				if( !variableText.equalsIgnoreCase(resultText.getText())){
					Alert al = new Alert(AlertType.WARNING);
					al.setHeaderText("Value Mismatch!");
					al.setContentText("The variable in the result field ["+ resultText.getText() + 
							"] does not tie up to the scanned value [" + variableText +"]. \nClick 'OK' to substitute the value with value in the result field, or click 'Cancel' to abort");
					al.getButtonTypes().add(ButtonType.CANCEL);
					Optional<ButtonType> res = al.showAndWait();
					if ( res.get() == ButtonType.OK){
						variableText = resultText.getText();
					}else{
						return;
					}
				}
				String variableName = saveAsID.getText();
	
				if ( variableName == null || variableName.equalsIgnoreCase("")){
					Alert al = new Alert(AlertType.ERROR);
					al.setHeaderText("Invalid input");
					al.setContentText("The value " + variableName + " is empty or invalid.");
					al.showAndWait();
					return;
				}
				if  (!variableName.startsWith("@{")){
					variableName = "@{" + variableName;
				}
				if ( !variableName.endsWith("}")){
					variableName = variableName +"}";
				}
				if ( !isEditing ){
					Variable var = new Variable();
					var.setTestSuiteID( selectedTestSuite.getTestSuiteID() );
					var.setTestCaseID(selectedTestSuite.getSelectedTest().getId());
					var.setVariableName( variableName );
					var.setVariableAction(Variable.EXTRACTED);
					var.setVariableStartValue( startText );
					var.setVariableEndValue( endText );
					var.setVariableOriginalValue( variableText );
					selectedTestSuite.getSelectedTest().getVariables().add(var);
				}else{
					ArrayList<Variable> v = selectedTestSuite.getSelectedTest().getVariables();
	            	for ( Variable var : v) {
		            	 if ( var.getVariableName().equalsIgnoreCase(variableName) && var.getVariableAction().equalsIgnoreCase(Variable.EXTRACTED)){
		 					var.setVariableName( variableName );
							var.setVariableStartValue( startText );
							var.setVariableEndValue( endText );
							var.setVariableOriginalValue( variableText );
							var.setVariableAction(Variable.EXTRACTED);
		            	 }
	            	}
				}
				
				Alert al = new Alert(AlertType.CONFIRMATION,"Would you like to process the suite and replaces all instances of the value[" + startText + variableText +endText +"] with the variable [" + startText+ variableName + endText +"] ?",ButtonType.YES,ButtonType.NO);
				Optional<ButtonType> o = al.showAndWait();
				if (o.get() == ButtonType.YES){
					//String t = startText.concat(variableText).concat(endText);
					selectedTestSuite.replaceValuesInSuite(startText,variableText,endText, variableName);
				}else{
					
				}
			
				if (!storedSuiteVariables.getItems().contains(variableName + " - " + variableText))
						storedSuiteVariables.getItems().add(variableName + " - " + variableText);
				
				if (!storedTestVariables.getItems().contains(variableName + " - " + variableText))
					storedTestVariables.getItems().add(variableName + " - " + variableText);
				isEditing = false;
				//((Button) getFXMLObjectByID("makePrettyButton")).fire();
			
		}else  if (buttonText.equalsIgnoreCase("makePrettyButton")){
			ObjectMapper mapper = new ObjectMapper();
			try{
				if ( !serviceCallText.getText().equalsIgnoreCase("")){
					Object json = mapper.readValue(serviceCallText.getText(), Object.class);
					String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
					serviceCallText.clear();
					serviceCallText.setText(indented);
				}
			}catch(Exception x){	LoggerFactory.getLogger(Runner.class).error("Exception!", x);}
		}else{
			String postMode = caller.lblWebServiceVerbAction.getText();
	        switch(postMode.toLowerCase()){
		        case "post data":{
		        	caller.setCallResultFromVariablesManager(selectedTestSuite.getSelectedTest().getPostData());
		        	break;
		        }
		        case "call result":{
		        	caller.setCallResultFromVariablesManager(selectedTestSuite.getSelectedTest().getCallResult());
		        	break;
		        }
		        default:break;
	        }
	        ((Stage) ( (Button) e.getSource()).getScene().getWindow()).close();
		}
	}
}
	private void populateLists(){
    	storedTestVariables.getItems().clear();
    	storedSuiteVariables.getItems().clear();
        Enumeration<String> enu = Runner.getRuntimeVariables().keys();
			while(enu.hasMoreElements()){
				ArrayList<Variable> arr = Runner.getRuntimeVariables().get(enu.nextElement());
				for (Variable var : arr ){
					if ( var.getTestCaseID() == selectedTestSuite.getSelectedTest().getId() )
					{
						if ( !(storedTestVariables.getItems().contains( var.getVariableName() + " - " + var.getVariableOriginalValue()) &&
			        			var.getVariableOriginalValue() != null && !var.getVariableOriginalValue().equalsIgnoreCase("null"))) 
						{
			        		storedTestVariables.getItems().add(var.getVariableName() + " - " + var.getVariableOriginalValue());
						}
					
						if ( !(storedSuiteVariables.getItems().contains( var.getVariableName() + " - " + var.getVariableOriginalValue())) &&
		        				var.getVariableOriginalValue() != null && !var.getVariableOriginalValue().equalsIgnoreCase("null") )
						{
		            		storedSuiteVariables.getItems().add(var.getVariableName() + " - " + var.getVariableOriginalValue());
						}
						
						if  (var.isSystemRuntimeVariable())
						{
							storedSuiteVariables.getItems().add(var.getVariableName() + " - " + var.getLiveValue());
						}
					}
				}	
				
			}
			Collections.sort(storedSuiteVariables.getItems());
			Collections.sort(storedTestVariables.getItems());
    }
    
	private void deleteVariable(int selectedItemIndex) {
		Enumeration<String> enu = Runner.getRuntimeVariables().keys();
		String selectedItem =  storedTestVariables.getSelectionModel().getSelectedItem();
		while ( enu.hasMoreElements() ){
			String key = enu.nextElement();
			ArrayList<Variable> arr = Runner.getRuntimeVariables().get(key);
			boolean hasFoundAndDeleted = false;
			for ( Variable var : arr ){
				LoggerFactory.getLogger(Runner.class).info(var.getVariableName());
				String selitem = selectedItem.substring(0,selectedItem.indexOf(" "));
				LoggerFactory.getLogger(Runner.class).info("Items to be deleted "  + selitem);
				if ( selitem.equalsIgnoreCase(var.getVariableName())){
					Runner.getRuntimeVariables().remove(key);
					 hasFoundAndDeleted= true;
					 break;
				}
			}
			if ( hasFoundAndDeleted)
				break;
		}
		populateLists();
	}
}
