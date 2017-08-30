package com.ilabquality.gtaf.gtafgui.environmentManager;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.GTAFApplController;
import com.ilabquality.gtaf.gtafAppl.GTAFApplication;
import com.ilabquality.gtaf.gtafAppl.Runner;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

public class EnvironmentManager extends JDialog implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AnchorPane _root;
	private Scene _scene;
	boolean _isEdit = false;
	public EnvironmentManager( GTAFApplication parent ){
		this.setTitle("Manage Environments");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(GTAFApplController.class.getResource("Absa.png")));
		final JFXPanel fxPanel = new JFXPanel();
        this.add(fxPanel);
        this.setSize(new Dimension(750,600));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
       });	
	}
	
	/**
	 * This method is invoked on the JavaFX thread
	 * @param fxPanel
	 */
    private  void initFX(JFXPanel fxPanel) {
    	System.out.println("initfx");
        Scene scene = createScene();
        fxPanel.setScene(scene);
        initEventHandlers();
    }
    
    /**
     * Initiate Scene
     */  
    @SuppressWarnings("static-access")
	private Scene createScene() {
    	System.out.println("CreateScene");
    	try{
    		FXMLLoader _loader = new FXMLLoader();
    		_root = _loader.load(EnvironmentManager.class.getResource("EnvironmentManager.fxml"));
    	}catch(Exception e){
    		LoggerFactory.getLogger(Runner.class).error("Exception!", e);
    	}
        _scene  =  new  Scene(_root);
        return (_scene);
    }
	    @SuppressWarnings("unchecked")
		private void initEventHandlers(){
          ((ComboBox<String>) getFXMLComboObjectByID("environmentsCombo")).valueProperty().addListener(new ChangeListener<String>() {
              @Override public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, String t, String t1) {
            	  populateEnvironmentDetails(t1);
                }
            });

          
          ((Button) getFXMLObjectByID("envManageSelectFolderButton")).setOnAction(new EventHandler<javafx.event.ActionEvent>() {
    		    @Override public void handle(javafx.event.ActionEvent e) {
    		       doButtonAction(e);
    		    }
            });
          ((Button) getFXMLObjectByID("addEnvButton")).setOnAction(new EventHandler<javafx.event.ActionEvent>() {
  		    @Override public void handle(javafx.event.ActionEvent e) {
  		       doButtonAction(e);
  		    }
          });
          ((Button) getFXMLObjectByID("newEnvNameSaveButton")).setOnAction(new EventHandler<javafx.event.ActionEvent>() {
  		    @Override public void handle(javafx.event.ActionEvent e) {
  		       doButtonAction(e);
  		    }
          });
          ((Button) getFXMLObjectByID("saveQueueToEnvButton")).setOnAction(new EventHandler<javafx.event.ActionEvent>() {
  		    @Override public void handle(javafx.event.ActionEvent e) {
  		       doButtonAction(e);
  		    }
          });  
          ((Button) getFXMLObjectByID("saveButton")).setOnAction(new EventHandler<javafx.event.ActionEvent>() {
  		    @Override public void handle(javafx.event.ActionEvent e) {
  		       doButtonAction(e);
  		    }
          });        
	        ((Button) getFXMLObjectByID("deleteQueueButton")).setOnAction(new EventHandler<javafx.event.ActionEvent>() {
			    @Override public void handle(javafx.event.ActionEvent e) {
				       doButtonAction(e);
				    }
		        });  
          
          
          
          ((ListView<String>) getFXMLObjectByID("queueListView")).setOnMouseClicked(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	if ( ((ListView<String>) getFXMLObjectByID("queueListView")).getSelectionModel().getSelectedItem() != null ){
//	            		_isEdit = true;
//	            		String selectedQueue  = ((ListView<String>) getFXMLObjectByID("queueListView")).getSelectionModel().getSelectedItem().toString();
//		            	QueueDetail qd = EnvironmentFactory.getQueueDetailsFromEnvironment(_activeEnvironment, selectedQueue);
//		            	( (TextField) getFXMLObjectByID("newQueueNameText")).setText(qd.getName());
//		            	( (TextField) getFXMLObjectByID("newQueueFriendlyNameText")).setText(qd.getFriendlyName());
//		            	( (CheckBox) getFXMLObjectByID("newQueueIsUrgentCheck")).setSelected( qd.isUrgent());
		            	
	            	}else{
	            		_isEdit = false;
	            		( (TextField) getFXMLObjectByID("newQueueNameText")).setText("");
		            	( (TextField) getFXMLObjectByID("newQueueFriendlyNameText")).setText("");
		            	( (CheckBox) getFXMLObjectByID("newQueueIsUrgentCheck")).setSelected( false );
	            	}
	            }
      	});	

	    }
	    
	   
	    

		/**
	     * Gets the FXML Object associated with this instance.
	     * @return  returnObject associated with this instance.
	     */
	 	private Object getFXMLObjectByID(String id){
	 		Object returnObject = _root.lookup("#"+id);
	 		return returnObject;		
	 	}
	 	
		/**
	 	* Gets the FXML Combo Object associated with this instance.
	 	* @return  return Combo Box root lookup associated with this instance.
	 	*/
	 	@SuppressWarnings("unchecked")
	 	private ComboBox<String> getFXMLComboObjectByID(String id){
	 		return (ComboBox<String>)_root.lookup("#" + id);
	 	}
	 	
		public void doButtonAction(javafx.event.ActionEvent e){
			System.out.println(e.getSource());
			if ( e.getSource() instanceof Button ){
				
				String buttonText = ( (Button) e.getSource()).getId();
				System.out.println("Button Text is " + buttonText);
				if ( buttonText.equalsIgnoreCase("addEnvButton")){
					( (TextField) getFXMLObjectByID("newEnvNameText")).setVisible(true);
					( (Button) getFXMLObjectByID("newEnvNameSaveButton")).setVisible(true);
				
					
				}
				else if ( buttonText.equalsIgnoreCase("newEnvNameSaveButton")){
//					( (TextField) getFXMLObjectByID("newEnvNameText")).setVisible(false);
//					( (Button) getFXMLObjectByID("newEnvNameSaveButton")).setVisible(false);
//					EnvironmentFactory.saveEnvironment(( (TextField) getFXMLObjectByID("newEnvNameText")).getText());
//					( (ComboBox<String>)getFXMLComboObjectByID("environmentsCombo")).getItems().add(( (TextField) getFXMLObjectByID("newEnvNameText")).getText());
//					( (ComboBox<String>)getFXMLComboObjectByID("environmentsCombo")).getSelectionModel().select(( (TextField) getFXMLObjectByID("newEnvNameText")).getText());
//					( (TextField) getFXMLObjectByID("newEnvNameText")).clear();
				}				
				else if ( buttonText.equalsIgnoreCase("envManageSelectFolderButton")){
					 DirectoryChooser chooser = new DirectoryChooser();
					  chooser.setInitialDirectory(new File("C:\\Automation\\XMLFiles"));
		    		  File f = chooser.showDialog(_scene.getWindow());
		    		  ( (TextField) getFXMLObjectByID("trLocalFolderText")).setText(f.getPath());
		    		  saveRepository();
				}
				
				
				
				
				else if ( buttonText.equalsIgnoreCase("saveQueueToEnvButton")){
					if ( _isEdit ){
//						String oldName = ( (ListView<String>) getFXMLObjectByID("queueListView")).getSelectionModel().getSelectedItem();
//						EnvironmentFactory.updateQueueDetails(_activeEnvironment, oldName,( (TextField) getFXMLObjectByID("newQueueNameText")).getText(),
//															( (TextField) getFXMLObjectByID("newQueueFriendlyNameText")).getText()	,
//															( (CheckBox) getFXMLObjectByID("newQueueIsUrgentCheck")).isSelected());
//						_isEdit = false;
//						( (TextField) getFXMLObjectByID("newQueueNameText")).clear();
//						( (TextField) getFXMLObjectByID("newQueueFriendlyNameText")).clear();
//						( (CheckBox) getFXMLObjectByID("newQueueIsUrgentCheck")).setSelected(false);
//						populateEnvironmentDetails(_activeEnvironment);
//						JOptionPane.showMessageDialog(this, "Saved!");
					}else{
//						QueueDetail qd = new QueueDetail();
//						qd.setName(( (TextField) getFXMLObjectByID("newQueueNameText")).getText());
//						qd.setFriendlyName(( (TextField) getFXMLObjectByID("newQueueFriendlyNameText")).getText());
//						qd.setIsUrgent( ( (CheckBox) getFXMLObjectByID("newQueueIsUrgentCheck")).isSelected());
//						EnvironmentFactory.addQueueToEnvironment(_activeEnvironment, qd);
//						(( ListView<String>) getFXMLObjectByID("queueListView")).getItems().add(qd.getName());
//						( (TextField) getFXMLObjectByID("newQueueNameText")).clear();
//						( (TextField) getFXMLObjectByID("newQueueFriendlyNameText")).clear();
//						( (CheckBox) getFXMLObjectByID("newQueueIsUrgentCheck")).setSelected(false);
//						populateEnvironmentDetails(_activeEnvironment);
//						JOptionPane.showMessageDialog(this, "Saved!");
					}
				}else if ( buttonText.equalsIgnoreCase("saveButton")){
					//Save bottom details to environment. 
					saveEnvironment();
					saveRepository();
					JOptionPane.showMessageDialog(this, "Saved!");
				}
				else if ( buttonText.equalsIgnoreCase("deleteQueueButton")){
//					//Save bottom details to environment. 
//					EnvironmentFactory.deleteQueue(_activeEnvironment,(( ListView<String>) getFXMLObjectByID("queueListView")).getSelectionModel().getSelectedItem());
//					(( ListView<String>) getFXMLObjectByID("queueListView")).getItems().remove((( ListView<String>) getFXMLObjectByID("queueListView")).getSelectionModel().getSelectedItem());
//					( (TextField) getFXMLObjectByID("newQueueNameText")).clear();
//					( (TextField) getFXMLObjectByID("newQueueFriendlyNameText")).clear();
				}
				
				
				
			}
		}
		
	private void saveRepository() {
	}

	@SuppressWarnings("unchecked")
	private void populateEnvironmentDetails(String t1) {
		(( ListView<String>) getFXMLObjectByID("queueListView")).getItems().clear();
		( (TextField) getFXMLObjectByID("newQueueNameText")).clear();
		( (TextField) getFXMLObjectByID("newQueueFriendlyNameText")).clear();
		( (CheckBox) getFXMLObjectByID("newQueueIsUrgentCheck")).setSelected(false);
		try{
			System.out.println("Populating env " + t1 );
			if  (!t1.equalsIgnoreCase("-None selected-")){
			}
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		
	}

	public void saveEnvironment(){
		try{
			if ( ((ComboBox<String>) getFXMLComboObjectByID("environmentsCombo")).getSelectionModel().getSelectedItem() == null 
					|| ((ComboBox<String>) getFXMLComboObjectByID("environmentsCombo")).getSelectionModel().getSelectedItem().toString().equalsIgnoreCase("-None selected-")){
				JOptionPane.showMessageDialog(this, "No Environment selected for Save.");
				return;
			}
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
	}
		@Override
		public void run() {
			
			
		} 

}
