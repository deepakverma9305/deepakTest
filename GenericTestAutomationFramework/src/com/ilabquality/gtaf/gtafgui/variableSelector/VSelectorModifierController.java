package com.ilabquality.gtaf.gtafgui.variableSelector;

import java.awt.MouseInfo;
import java.io.IOException;

import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.GTAFApplController;
import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.testsuite.TestSuite;
import com.ilabquality.gtaf.testsuite.TestValidation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VSelectorModifierController {
	@FXML private TextArea vSelecterModifierText;
	@FXML private Button vSelectorModifierSaveButton;
	@FXML private Button vSelectorModifierCancelButton;
	@FXML private CheckBox regularExpressionCheckBox;
//	private ListView<String> callerObject;
//	private TestSuite selectedSuite;
	private TestSuite selectedSuite;
	private GTAFApplController caller;
	private TestValidation selectedValidation;
	
	
	public VSelectorModifierController(){
	}
	
	/**
	 * This method is invoked on the JavaFX thread
	 */
    public void initialize() {
    	LoggerFactory.getLogger(GTAFApplController.class).info("initfx VM");
        initEventHandlers();
    }
    
    public void loadData(){
    	   
    	   if ( selectedValidation == null ){
    		   new Alert(AlertType.ERROR,"The validation was not found.",ButtonType.OK).show();
    		   
    	   }else{
    		   regularExpressionCheckBox.setSelected(selectedValidation.isRegularExpression());
    		   vSelecterModifierText.setText(selectedValidation.getExpectedResult());
    	   }
    }
    
	private void initEventHandlers(){
		
		vSelectorModifierSaveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				handleButtonEvent(e);
			}
		});
		vSelectorModifierCancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				handleButtonEvent(e);
			}
		});
		
		vSelecterModifierText.setOnKeyTyped(new EventHandler<KeyEvent>() {
	        public void handle(KeyEvent ke) {
	            LoggerFactory.getLogger(Runner.class).info("Key Pressed: " + ke.getCharacter());
	            if ( ke.getCharacter().equalsIgnoreCase("@")){
	            	LoggerFactory.getLogger(Runner.class).info("Showing variable popup");
	            	  try {
	          			Stage stage = new Stage();
	          			FXMLLoader loader = new FXMLLoader(VariablesSelectorController.class.getResource("VariableSelector.fxml"));
	          		    Parent root = loader.load();
	          		    VariablesSelectorController vsc = (VariablesSelectorController) loader.getController();
	          		    vsc.setCallingObject(vSelecterModifierText);
	          		    vsc.setSelectedSuite(selectedSuite);
         		    	vsc.loadData();
	          		    vsc.setCaretPosition(vSelecterModifierText.getCaretPosition());
         		    	
         		    	stage.initOwner((vSelecterModifierText.getScene().getWindow() ));
	          		    stage.setScene(new Scene(root));
	          		    stage.setTitle("Variable Selection");
	          		    stage.initModality(Modality.WINDOW_MODAL);
	          		    stage.setX(MouseInfo.getPointerInfo().getLocation().getX());
	          		    stage.setY(MouseInfo.getPointerInfo().getLocation().getY());
	          		    stage.show();
	          		} catch (IOException e1) {
	          			e1.printStackTrace();
	          		}
	          	 }
	        }
	    });
	}
	    
	private void handleButtonEvent(ActionEvent e){
		LoggerFactory.getLogger(Runner.class).info(e.getSource().toString());
		if ( e.getSource() instanceof Button ){
			String buttonText = ( (Button) e.getSource()).getId();
			LoggerFactory.getLogger(Runner.class).info("Button Text is " + buttonText);
			if ( buttonText.equalsIgnoreCase("vSelectorModifierSaveButton")){
				String newValidationText = vSelecterModifierText.getText();
				selectedValidation.setExpectedResult(newValidationText);
				selectedValidation.setRegularExpression(regularExpressionCheckBox.isSelected());
				caller.reloadValidations();
				 ((Stage) ( (Button) e.getSource()).getScene().getWindow()).close();
			}else{
				 ((Stage) ( (Button) e.getSource()).getScene().getWindow()).close();
			}
		}
	}
 
//	public void setSelectedTestSuite(TestSuite selectedTestSuite) {
//		this.selectedSuite = selectedTestSuite;		
//	}

	public void setSelectedTestSuite(TestSuite selectedTest2) {
		this.selectedSuite = selectedTest2;
	}

	public void setSelectedValidation(TestValidation tv){
		this.selectedValidation = tv;
	}
//	public void setListView(ListView<String> listViewSelectedValidations) {
//		this.callerObject = listViewSelectedValidations;
//	}

	public void setCaller(GTAFApplController gtafApplController) {
		this.caller = gtafApplController;
	}
}
