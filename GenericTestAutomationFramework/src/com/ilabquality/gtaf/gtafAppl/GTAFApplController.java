package com.ilabquality.gtaf.gtafAppl;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilabquality.gtaf.controller.gui.GUIController;
import com.ilabquality.gtaf.controller.harimporter.HARController;
import com.ilabquality.gtaf.controller.testpack.TestPackController;
import com.ilabquality.gtaf.controller.testsuite.TestSuiteController;
import com.ilabquality.gtaf.gtafgui.reportsViewer.ReportsViewerController;
import com.ilabquality.gtaf.gtafgui.variableSelector.VSelectorModifierController;
import com.ilabquality.gtaf.gtafgui.variableSelector.VariablesSelectorController;
import com.ilabquality.gtaf.gtafgui.variablesManager.VariablesManagerController;
import com.ilabquality.gtaf.gtafgui.variablesManager.helper.VariablesHelper;
import com.ilabquality.gtaf.guitests.GUITestDef;
import com.ilabquality.gtaf.guitests.GUITestFactory;
import com.ilabquality.gtaf.guitests.GUITestSuite;
import com.ilabquality.gtaf.packmanager.Pack;
import com.ilabquality.gtaf.packmanager.PackManagerFactory;
import com.ilabquality.gtaf.packmanager.TestPackTestSuite;
import com.ilabquality.gtaf.testsuite.Test;
import com.ilabquality.gtaf.testsuite.TestSuite;
import com.ilabquality.gtaf.testsuite.TestSuiteFactory;
import com.ilabquality.gtaf.testsuite.TestValidation;
import com.ilabquality.gtaf.testsuite.Variable;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
 
public class GTAFApplController {
	/**
	 * *****************************Used Vars********************************
	 * */
	private boolean firstRun = true;
	private Pack selectedTestPack;
	private static Logger logger = LoggerFactory.getLogger(GTAFApplController.class);
	private TestSuite selectedTestSuite;
	private Test selectedTest;
	private boolean isEditingTestSuite;
	private double currentProgressValue = 0.0d;														//  Reference to current ProgressValue	
	private double pbIncrementalValue = 0.0d;															//  Incremental value to use
	private GUITestSuite selectedGUITestSuite;
	private GUITestSuite guiSuiteBeforeSave;
	 
	/*****************************Panels*****************************************/
    @FXML private AnchorPane welcomePane;
    @FXML private AnchorPane absaGuiTestPane;
    @FXML private AnchorPane testSuitePanel;
    @FXML private AnchorPane testPackPanel;
    @FXML private AnchorPane harImporterPanel;
    
    /*****************************HarImporterPanel Objects**********************/
    @FXML private TextField harImporterImportFileTxt;
    @FXML private Button harImporterSelectFileButton;
    @FXML private Button harImporterImportButton;
    @FXML private TextField harImporterTestSuiteNameText;
    @FXML private CheckBox harImportMethodGETCheckbox;
    @FXML private CheckBox harImportMethodPOSTCheckbox;
    @FXML private TextArea harImporterTestSuiteDescTextArea;
    @FXML private TextField harImporterExecuteAsText;
    @FXML private TextField harImporterSvcSvrIP;
    @FXML private TextField harImporterSvcSvrPort;
    @FXML private TextField harImporterWkFlowSvrIP;
    @FXML private TextField harImporterWkFlowSvrPort;
    @FXML private TextField harImporterUISvrIP;
    @FXML private TextField harImporterUISvrPort;
    @FXML private TextField harImporterKCServerIP;
    @FXML private TextField harImporterKCSvrPort;
    
    @FXML private ListView<String> harImporterExclusionsListView;
    
    /******************************Main UI Controls ***************************/
    @FXML private TreeView<String> navigationTree;
    @FXML private MenuBar mainMenuBar;
    @FXML private TextArea outputTextArea;
    @FXML private Label taskDisplayer;
    @FXML private ProgressBar progressBar;
    /******************************GUI Panel **********************************/
    @FXML private ComboBox<String> guiTestSelectedTest;
    @FXML private Button btnRunGUITests;
    @FXML private Button btnGUINewSuite;
    @FXML private Button btnGUIEditSuite;
    @FXML private Button btnGUIDeleteSuite;
    @FXML private Label labelGUINewSuiteName;
    @FXML private TextField textGUINewSuiteName;
    @FXML private ListView<String> listGUILeftList;
    @FXML private ListView<String> listGUIRightList;
    @FXML private Button buttonGUIMoveOneToRight;
    @FXML private Button buttonGUIMoveAllToRight;
    @FXML private Button buttonGUIMoveAllToLeft;
    @FXML private Button buttonGUIMoveOneToLeft;
    @FXML private Button buttonGUIAddTest;
    @FXML private Button buttonGUIDeleteTest;
    @FXML private Button btnGUICancelSuite;
    @FXML private Button btnGUISave;
    
    /******************************Test Pack Panel ****************************/
    @FXML private ComboBox<String> testPackNameCombo;
    @FXML private ListView<String> availableTestSuites;
    @FXML private ListView<String> selectedTestSuites;
    @FXML private Button runPackButton;
    @FXML private Button btnCancelSavePack;
    @FXML private Button btnSavePack;
    @FXML private Button newPackButton;
    @FXML private Button editPackButton;
    @FXML private Button deletePackButton;
    @FXML private Button addSingleSuite;
    @FXML private Button addAllSuites;
    @FXML private Button removeSingleSuite;
    @FXML private Button removeAllSuites;
    @FXML private Button moveSuiteUp;
    @FXML private Button moveSuiteDown;
    @FXML private TextField packNameText;
    
    /******************************Test Suite Panel***************************/
    @FXML private MenuButton testSuiteMenuButton;
    @FXML private MenuButton testCaseMenuButton;
    @FXML private ComboBox<String> testSuiteCombo;
    @FXML private ComboBox<String> testNameCombo;
    @FXML private ComboBox<String> testVerb;
    @FXML private Button saveVariable;
    @FXML private Button btnToHere;
    @FXML private Button btnRunToHere;
    @FXML private Button btnRemoveForValidation;
    @FXML private Button btnUseForValidation;
    @FXML private Button  formatLeftButton;
    @FXML private TextField txtTestSuiteName;
    @FXML private TextField txtTestSuiteID;
    @FXML private TextArea txtTestSuiteDescription;
    @FXML private Button btnTestSuiteAddNew;
    @FXML private Button btnTestSuiteEdit;
    @FXML private Button btnTestSuiteDelete;
    @FXML private Button btnSaveSuite;
    @FXML private Button btnCancelTestSuiteEdit;
    @FXML private Button  btnCallWebService;
	@FXML private TextField txtTestName;
	@FXML private TextField txtExecutionUser;
	@FXML private TextField txtExecutionUserPassword;
	@FXML private TextField txtExecutionIndex;
	@FXML private TextField txtWebServiceURL;
	@FXML private TextArea txtTestDescription;
	@FXML private TextArea textareaCallResult;
	@FXML private TextField testCaseDelayText;
	@FXML private TextField txtTestCaseID;
    @FXML private ListView<String> listViewSelectedValidations;
    @FXML private CheckBox testCaseGETCheckBox;
    @FXML private CheckBox testCasePOSTCheckBox;
    @FXML
	public Label lblWebServiceVerbAction;
    @FXML private TextArea textareaPostData;
    
   
    
    
    
    public void initialize(){
    	welcomePane.setVisible(true);
    	absaGuiTestPane.setVisible(false);
    	testSuitePanel.setVisible(false);
    	harImporterPanel.setVisible(false);
    	harImporterExclusionsListView.getItems().addAll(new String[]{"URL doesnt end with \".js\"",
														   "URL doesnt end with \".css\"",
														   "URL doesnt contain \".png",
														   "URL doesnt contain \"viewAttachment.uri\"",
														   "URL doesnt contain \"selectedDocument.url\"",
														   "URL doesnt contain \"selectedDocument.link\"",
														   "URL doesnt end with \".gif\"",
														   "URL doesnt end with \"/\"",
														   "URL doesnt contain \"assets/css\"",
														   "URL doesnt contain \"assets/fonts"});
    	initTreeView();
    	initEventHandlers();
    }
    
    /**
     * Initiate treeview
     */
    public void initTreeView(){
    	navigationTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
			@Override
			public void changed(
					ObservableValue<? extends TreeItem<String>> arg0,
							TreeItem<String> oldItem, TreeItem<String> newItem) {
				if ( newItem != null && newItem.getValue() != null ){
					LoggerFactory.getLogger(GTAFApplController.class).info("NewItem " + newItem.getValue() );
								swapScene(newItem.getValue());
				}
			}
		});
       	TreeItem<String> root = new TreeItem<>("Automation Items");
       	TreeItem<String> st = new TreeItem<>("Service Tester");
    	TreeItem<String> guiTests = new TreeItem<>("GUI Test Execution");
    	TreeItem<String> suites = new TreeItem<>("Test Suites");
    	TreeItem<String> testPacks = new TreeItem<>("Test Packs");
    	TreeItem<String> harImporter = new TreeItem<String>("HAR Importer");
    	st.getChildren().add(suites);
    	st.getChildren().add(testPacks);
    	root.getChildren().add(st);
    	root.getChildren().add(guiTests);
    	root.getChildren().add(harImporter);
      	root.setExpanded(true);
      	navigationTree.setRoot(root);
  }
    
    /**
     * Initiate Event Handlers
     */
	public void initEventHandlers() {
    	initMenuBarEventHandlers();
    	initGuiHandlers();
    	initTestPackHandlers();
    	initTestSuiteHandlers();
    	initTestHandlers();
    	initTestSuiteInitialStates();
    	initHarImporterHandlers();
    	//setDisabledStyles();
     }
	private void initTestHandlers(){
		MenuItem tcnew_ = new MenuItem("New");
		tcnew_.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					doTestButtonAction(e);
				} catch (SQLException e1) {
					LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);
				}
			}
		});
		MenuItem tcsave = new MenuItem("Save");
		tcsave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					doTestButtonAction(e);
				} catch (SQLException e1) {
					LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);
				}
			}
		});
        tcsave.setOnAction( event -> {
                try {
                    doTestButtonAction(event);
                } catch (SQLException e1) {
                    LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);
                }
        });
		MenuItem tcedit = new MenuItem("Edit");
		tcedit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					doTestButtonAction(e);
				} catch (SQLException e1) {
					LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);
				}
			}
		});	
		MenuItem tccancel = new MenuItem("Cancel");
		tccancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					doTestButtonAction(e);
				} catch (SQLException e1) {
					LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);
				}
			}
		});
		MenuItem tcdelete = new MenuItem("Delete");
		tcdelete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					doTestButtonAction(e);
				} catch (SQLException e1) {
					LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);
				}
			}
		});
		testCaseMenuButton.getItems().addAll(tcnew_,tcsave,tcedit,tccancel,tcdelete);	
		testCaseMenuButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					doTestButtonAction(e);
				} catch (SQLException e1) {
					LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);
				}
			}
		});
		testNameCombo.getItems().clear();
		testNameCombo.getItems().add("--None Selected--");
		testNameCombo.valueProperty().addListener(new ChangeListener<String>() {
	        @Override public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, String t, String t1) {
	        	if ( t1 == null  || t1.equalsIgnoreCase("--None Selected--") || t1.equalsIgnoreCase("Creating new")) {
	        		LoggerFactory.getLogger(GTAFApplController.class).info("testNameCombo none selected.");
	        	
	        		testNameCombo.setDisable(false);
	        		txtTestName.setText("");
	        		txtExecutionUser.setText("");
	        		txtExecutionUserPassword.setText("");
	        		txtExecutionIndex.setText("");
	        		txtWebServiceURL.setText("");
	        		txtTestDescription.setText("");
	        		textareaCallResult.setText("");
	        		testCaseDelayText.setText("0");
	        		txtExecutionUser.setText("");
	        		txtExecutionUserPassword.setText("");
	        		txtTestCaseID.clear();
					txtTestCaseID.setText("");
	        		listViewSelectedValidations.getItems().clear();
	        		if ( t1 != null && !t1.equalsIgnoreCase("Creating new")){
		        		System.out.println("Disabling all menu items. ");
	    				for ( MenuItem mi : testCaseMenuButton.getItems()){
	    						mi.setDisable(true);
	    				}
	        		}
    				testCaseGETCheckBox.setSelected(false);
    				testCasePOSTCheckBox.setSelected(false);
	        		return;
	        	}else{
	        		System.out.println("Populating Exisiting Test Case. isEditingTestSuite is " + isEditingTestSuite);
    				populateTestCaseGUI(t1);
    				if ( isEditingTestSuite ){
	    				for ( MenuItem mi : testCaseMenuButton.getItems()){
	    					if (!mi.getText().matches("New|Edit|Delete")){
	    						mi.setDisable(true);
	    					}else{
	    						mi.setDisable(false);
	    					}
	    				}
	        		}else{
	    				for ( MenuItem mi : testCaseMenuButton.getItems()){
	    						mi.setDisable(true);
	    				}
	        		}
	        	}
	        }
		});
		testVerb.getItems().clear();
		testVerb.getItems().add("None");
		testVerb.getItems().add("GET");
		testVerb.getItems().add("POST");
		testVerb.valueProperty().addListener(new ChangeListener<String>() {
	        @Override public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, String t, String t1) {
	        	if ( t1 == null  || t1.equalsIgnoreCase("None")) {
	        		return;
	        	}else{
	        		if ( "get".equalsIgnoreCase(t1)){
	        			reloadValidations();
	        			lblWebServiceVerbAction.setText("Call Result");
	        			textareaCallResult.setVisible(true);
						textareaPostData.setVisible(false);
	        		}
	        		else if ("post".equalsIgnoreCase(t1)){
	        			lblWebServiceVerbAction.setText("Post Data");
	        			 listViewSelectedValidations.getItems().clear();
	        			 textareaCallResult.setVisible(false);
	        			 textareaPostData.setVisible(true);
	        			 textareaPostData.setDisable(false);
						 
	        		}
	        	}
	        }
		});
		btnCallWebService.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String verb =  selectedTest.getVerb();
//				if  (!txtWebServiceURL.getText().contains("{")){
//					selectedTest.setWebServiceURL(txtWebServiceURL.getText());
//					selectedTest.setLiveURL(txtWebServiceURL.getText());
//				}
				Runner.getRuntimeVariables().put("@{UserName}", new ArrayList<Variable>(Arrays.asList(new Variable("@{UserName}",selectedTest.getExecutionUserName()))));
				Runner.getRuntimeVariables().put("@{Password}", new ArrayList<Variable>(Arrays.asList(new Variable("@{Password}",selectedTest.getExecutionUserName()))));//TODO Implement passwords when available
				
				switch( verb.toLowerCase() ){
					case "get":{
						//postData = "";
						//selectedTest.setPostData(postData);
						new VariablesHelper(selectedTestSuite).substituteURLs();
						String result = Runner.serviceConnector.callService(selectedTest.getLiveURL(),txtExecutionUser.getText(),txtExecutionUserPassword.getText());
						ObjectMapper mapper = new ObjectMapper();
						try{
							Object json = mapper.readValue(result, Object.class);
							String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
							textareaCallResult.clear();
							textareaCallResult.setText(indented);
						}catch(Exception ex){
							textareaCallResult.clear();
							textareaCallResult.setText(result);
							LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", ex);
						}
						break;
					}
					case "post":{
						appendLog("Substituting URLs");
						new VariablesHelper(selectedTestSuite).substituteURLs();
						lblWebServiceVerbAction.setText("Call Result");
						String postData = textareaPostData.getText();
						selectedTest.setPostData(postData);
						appendLog("Substituting any variables in the POST Data");
						selectedTest.setPostData(new VariablesHelper(selectedTestSuite).substituteLiveValues(selectedTest.getPostData()));
						String result = Runner.serviceConnector.callPostService(selectedTest.getLiveURL(),
								selectedTest.getPostData(),txtExecutionUser.getText(),txtExecutionUserPassword.getText());
						LoggerFactory.getLogger(GTAFApplController.class).info("POST REsult" + result);
						if ( result.equalsIgnoreCase("")){
							LoggerFactory.getLogger(GTAFApplController.class).info("POST Returned no Result!");
						}else{
							ObjectMapper mapper = new ObjectMapper();
							try{
								Object json = mapper.readValue(result, Object.class);
								String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
								textareaCallResult.clear();
								textareaCallResult.setText(indented);
							}catch(Exception ex){
								LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", ex);
							}
						}
						appendLog("Call Completed");
						break;
					}
				}
				//Clear out the temporary variables.
				Runner.getRuntimeVariables().remove("@{UserName}");
				Runner.getRuntimeVariables().remove("@{Password}");
				testVerb.getSelectionModel().select("GET");
				
			}
		});
		textareaCallResult.setOnKeyTyped(new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent ke) {
	              LoggerFactory.getLogger(GTAFApplController.class).info("textareaCallResult - Key Pressed: " + ke.getCharacter());
	              if ( ke.getCharacter().equalsIgnoreCase("@")){
	            	  LoggerFactory.getLogger(GTAFApplController.class).info("Showing variable popup");
	            	  showVariablesPopup(ke,textareaCallResult);
	            	 }
	          }
	      });
		textareaPostData.setOnKeyTyped(new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent ke) {
	              LoggerFactory.getLogger(GTAFApplController.class).info("textareaPostData - Key Pressed: " + ke.getCharacter());
	              if ( ke.getCharacter().equalsIgnoreCase("@")){
	            	  LoggerFactory.getLogger(GTAFApplController.class).info("Showing variable popup");
	            	  showVariablesPopup(ke,textareaPostData);
	            	 }
	          }
	      });
		txtWebServiceURL.setOnKeyTyped(new EventHandler<KeyEvent>() {
	        public void handle(KeyEvent ke) {
	            LoggerFactory.getLogger(GTAFApplController.class).info("Key Pressed: " + ke.getCharacter());
	            System.out.println(ke);
	            if ( ke.getCharacter().equalsIgnoreCase("@")){
	          	  LoggerFactory.getLogger(GTAFApplController.class).info("Showing variable popup");
	          	 
	          	  showVariablesPopup(ke,	txtWebServiceURL);
	          	 }
	        }
	    });
		btnRemoveForValidation.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				int listItem = listViewSelectedValidations.getSelectionModel().getSelectedIndex();
				selectedTest.getValidations().remove(listItem);
				listViewSelectedValidations.getItems().remove(listItem);
			}
		});
		btnUseForValidation.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					doTestButtonAction(e);
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		saveVariable.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					doTestButtonAction(e);
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
					
			}
		});
		formatLeftButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
				try{
					System.out.println(textareaCallResult.isVisible());
					if ( textareaCallResult.isVisible()  ){
						Object json = mapper.readValue(textareaCallResult.getText(), Object.class);
						String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
						textareaCallResult.clear();
						textareaCallResult.setText(indented);
					}else{
						Object json =  mapper.readValue(textareaPostData.getText(), Object.class);
						String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
						textareaPostData.clear();
						textareaPostData.setText(indented);
					}
				}catch(Exception x){	LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", x);}
					
			}
		});
		listViewSelectedValidations.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
	    	  public void handle(MouseEvent click) {

	    	        if (click.getClickCount() == 2) {
	    	        	showSelectorModifier();
	    	        }
	    	    }
	        });

		
		testCaseGETCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (testCaseGETCheckBox.isSelected() ){
					testCasePOSTCheckBox.setSelected(false);
					if  (selectedTest != null ){
						selectedTest.setVerb("GET");
						appendLog("Selected Test Verb is now \"GET\"");
						System.out.println("Selected Test Verb is now \"GET\"");
					}
				}
			}
		});
		testCasePOSTCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if ( testCasePOSTCheckBox.isSelected() ){
					testCaseGETCheckBox.setSelected(false);
					if  (selectedTest != null ){
						selectedTest.setVerb("POST");
						appendLog("Selected Test Verb is now \"POST\"");
						System.out.println("Selected Test Verb is now \"POST\"");
					}
				}
			}
		});
		btnRunToHere.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				launchRunToHere();
			}
		});
	}
	private void initHarImporterHandlers(){
		harImporterSelectFileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
					try {
						doHarImporterButtonAction(e);
					} catch (IOException | SQLException e1) {
						LoggerFactory.getLogger(Runner.class).error("Unable to perform Import.",e1);
					}
			}
		});
		harImporterImportButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
					try {
						doHarImporterButtonAction(e);
					} catch (IOException | SQLException e1) {
						LoggerFactory.getLogger(Runner.class).error("Unable to perform Import.",e1);
					}
			}
		});
	}
	private void doHarImporterButtonAction(ActionEvent e) throws IOException, SQLException{
		LoggerFactory.getLogger(Runner.class).info("doHarImporterButtonAction");
		if ( e.getSource() instanceof Button ){
			String buttonText = ( (Button) e.getSource()).getId();
			LoggerFactory.getLogger(Runner.class).info("Button Text is " + buttonText);
			if ( "harImporterSelectFileButton".equalsIgnoreCase(buttonText)){
					FileChooser fd = new FileChooser();
					fd.setSelectedExtensionFilter(new ExtensionFilter("HAR Files", "*.har"));
					File f = fd.showOpenDialog( ( (Button) e.getSource()).getScene().getWindow());
					if ( f != null )
						harImporterImportFileTxt.setText(f.getAbsolutePath());
			}else if ("harImporterImportButton".equalsIgnoreCase(buttonText)){
				if ( !"".equalsIgnoreCase(harImporterImportFileTxt.getText())){
					String suiteName = harImporterTestSuiteNameText.getText();
					String suiteDesc = harImporterTestSuiteDescTextArea.getText();
					boolean importGETMethod = harImportMethodGETCheckbox.isSelected();
					boolean importPOST = harImportMethodPOSTCheckbox.isSelected();
					String user =  harImporterExecuteAsText.getText();
					String serviceServerIP = harImporterSvcSvrIP.getText();
					String serviceServerPort = harImporterSvcSvrPort.getText();
					String workFlowServerIP = harImporterWkFlowSvrIP.getText();
					String workFlowServerPort = harImporterWkFlowSvrPort.getText();
					String uiServerIP = harImporterUISvrIP.getText();
					String uiServerPort = harImporterUISvrPort.getText();
					String kcServerIP = harImporterKCServerIP.getText();
					String kcServerPort = harImporterKCSvrPort.getText();
					if (!"".equalsIgnoreCase(suiteName) && !"".equalsIgnoreCase(suiteDesc) && !"".equalsIgnoreCase(user)){
						logger.info("Starting Controller");
						appendLog("Starting HAR Import at "  + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
						Runnable r = new HARController(this, harImporterImportFileTxt.getText(),suiteName, suiteDesc,importGETMethod,importPOST,user
								, serviceServerIP,serviceServerPort,uiServerIP,uiServerPort,workFlowServerIP,workFlowServerPort,kcServerIP,kcServerPort);
						Thread t = new Thread(r);
						t.start();
						while(t.isAlive()){
							Thread.currentThread();
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								
								e1.printStackTrace();
							}
						}
						appendLog("Ended Run at "  + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
						setTaskDisplayer("Idle...");
						resetProgress();
					}else{
						Alert al = new Alert(AlertType.ERROR);
						al.setHeaderText("Invalid Input");
						al.setContentText("Unable to start Import. Please enter a suite name,description and provide a user to use for execution.");
						al.showAndWait();
						return;
					}
				}
			}
		}
	}
	private void launchRunToHere(){
		logger.info("Starting Controller");
		appendLog("Starting Test Suite execution at "  + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
		Runnable r = new TestSuiteController(this, selectedTestSuite, selectedTest);
		Thread t = new Thread(r);
		t.start();
		
	}
	private void showVariablesPopup(KeyEvent e, Object o){
	  try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(VariablesSelectorController.class.getResource("VariableSelector.fxml"));
		    Parent root = loader.load();
		    VariablesSelectorController vsc = (VariablesSelectorController) loader.getController();
		    vsc.setCallingObject(o);
		    vsc.setSelectedSuite(selectedTestSuite);
		    vsc.loadData();
		    if ( o instanceof TextField ){
		    	 vsc.setCaretPosition(( (TextField)o).getCaretPosition());
		    	  stage.initOwner(
		  		        ((TextField)e.getSource()).getScene().getWindow() );
		    }else if ( o instanceof TextArea){
		    	 vsc.setCaretPosition(( (TextArea)o).getCaretPosition());
		    	  stage.initOwner(
		  		        ((TextArea)e.getSource()).getScene().getWindow() );
		    }
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
	private void populateTestCaseGUI(String selectedTest){
		for ( Test test : selectedTestSuite.getSuiteData() ){
			if ( selectedTest.equalsIgnoreCase(test.getTestName() ) ){
				this.selectedTest = test;
				this.selectedTestSuite.setSelectedTest(test);
				txtTestName.clear();
				txtTestName.setText(test.getTestName());
				txtTestCaseID.clear();
				txtTestCaseID.setText(test.getId()+"");
				txtExecutionIndex.clear();
				txtExecutionIndex.setText(test.getExecutionIndex()+"");
				txtTestDescription.clear();
				txtTestDescription.setText(test.getTestDescription());
				txtWebServiceURL.clear();
				txtWebServiceURL.setText(test.getWebServiceURL());
				testCaseDelayText.clear();
				testCaseDelayText.setText(test.getDelay()+"");
				txtExecutionUser.clear();
				txtExecutionUser.setText(test.getExecutionUserName());
				txtExecutionUserPassword.clear();
				txtExecutionUserPassword.setText(test.getExecutionPassword());
				
				textareaCallResult.clear();
				textareaPostData.clear();
				textareaCallResult.setText(test.getCallResult());
				textareaPostData.setText(test.getPostData());
				
				if ( test.getVerb().equalsIgnoreCase("Post"))
				{
					textareaCallResult.setVisible(false);
					textareaPostData.setVisible(true);
					lblWebServiceVerbAction.setText("Post Data");
					testCaseGETCheckBox.setSelected(false);
					testCasePOSTCheckBox.setSelected(true);
					testVerb.getSelectionModel().select("POST");
					listViewSelectedValidations.getItems().clear();
					
				}else{
					textareaCallResult.setVisible(true);
					textareaPostData.setVisible(false);
					lblWebServiceVerbAction.setText("Call Result");
					listViewSelectedValidations.getItems().clear();
					reloadValidations();
					testCaseGETCheckBox.setSelected(true);
					testCasePOSTCheckBox.setSelected(false);
					testVerb.getSelectionModel().select("GET");
				}
			
			//	LoggerFactory.getLogger(GTAFApplController.class).info("Set verb to " + test.getVerb());
			//	LoggerFactory.getLogger(GTAFApplController.class).info(testVerb.getSelectionModel().getSelectedItem());
				break;
			}
		}	
		btnRunToHere.setDisable(false);
	}
	private void doTestButtonAction(ActionEvent e) throws HeadlessException, SQLException {
		LoggerFactory.getLogger(GTAFApplController.class).info("doTestButtonAction");
		if ( e.getSource() instanceof Button ){
			String buttonText = ( (Button) e.getSource()).getId();
			LoggerFactory.getLogger(GTAFApplController.class).info("Button Text is " + buttonText);
			switch (buttonText){
				case "btnTestsAddNew":
				case "btnTestsEditTest":
				case "btnSaveTest":
				case "btnCancelTestCaseEdit":
				case "btnDeleteTestCase":{
					setTestCaseState(buttonText);
					break;
				}
				case "btnUseForValidation":{
					if ( lblWebServiceVerbAction.getText().equalsIgnoreCase("post data") ){
						Alert al = new Alert(AlertType.ERROR);
						al.setHeaderText("Post Data is being edited.");
						al.setContentText("Validation points can only be added on Server Responses(GET Methods). The currently selected method is POST.");
						al.showAndWait();
						return;
					}
					String selectedText = textareaCallResult.getSelectedText();
						if ( selectedText != null && !"".equalsIgnoreCase(selectedText)){
						TestValidation tv = new TestValidation();
						tv.setAssociatedTest(selectedTest);
						tv.setExpectedResult(selectedText);
						tv.setTestCaseID(selectedTest.getId());
						selectedTest.getValidations().add(tv);
						listViewSelectedValidations.getItems().add(tv.getExpectedResult());
					}
					break;
				}
				case "saveVariable":{
					if (! testNameCombo.getSelectionModel().getSelectedItem().contains("None Selected")){
						   try {
							Stage stage = new Stage();
							FXMLLoader loader = new FXMLLoader(VariablesManagerController.class.getResource("VariablesManager.fxml"));
						    Parent root = loader.load();
						    VariablesManagerController vmc = (VariablesManagerController) loader.getController();
						    vmc.setCaller(this);
						    vmc.setSelectedTestSuite(selectedTestSuite);
						    vmc.loadData();
						    stage.setScene(new Scene(root));
						    stage.setTitle("Variables Management");
						    stage.initModality(Modality.WINDOW_MODAL);
						    stage.initOwner(
						        ((Button)e.getSource()).getScene().getWindow() );
						    stage.show();
						} catch (IOException e1) {
							
							e1.printStackTrace();
						}
					}else{
						new Alert(AlertType.WARNING,"No test selected",ButtonType.OK).showAndWait();
					}
					break;
				}
			}
		}else
			if (e.getSource() instanceof MenuItem){
				String buttonText = ( (MenuItem) e.getSource()).getText();
				LoggerFactory.getLogger(GTAFApplController.class).info("Menu Item Text is " + buttonText);
				switch (buttonText){
					case "New":{
						testCaseMenuButton.setText("Action - New");
						setTestCaseState("btnTestsAddNew");
						break;
					}
					case "Edit":{
						testCaseMenuButton.setText("Action - Edit");
						setTestCaseState("btnTestsEditTest");
						break;
					}
					case "Save":{
						testCaseMenuButton.setText("Action - Save");
						setTestCaseState("btnSaveTest");
						break;
					}
					case "Cancel":{
						testCaseMenuButton.setText("Action - Cancel");
						setTestCaseState("btnCancelTestCaseEdit");
						break;
					}
					case "Delete":{
						testCaseMenuButton.setText("Action - Delete");
						setTestCaseState("btnDeleteTestCase");
						break;
					}
				}
			}
	}
	private void setTestCaseState(String buttonText) throws HeadlessException, SQLException{
		if ( "btnTestsAddNew".equalsIgnoreCase(buttonText)){
			for ( MenuItem mi : testCaseMenuButton.getItems()){
				if (mi.getText().matches("New|Edit|Delete")){
					mi.setDisable(true);
				}else{
					System.out.println("Setting disabled to false for item " + mi.getText());
					mi.setDisable(false);
				}
			}
			btnCallWebService.setDisable(false);
			btnUseForValidation.setDisable(false);
			btnRemoveForValidation.setDisable(false);
			testVerb.setDisable(false);
			
			txtExecutionIndex.setDisable(true);
			txtTestName.setDisable(false);
			txtExecutionUser.setDisable(false);
			txtExecutionUserPassword.setDisable(false);
			txtTestDescription.setDisable(false);
			txtWebServiceURL.setDisable(false);
			textareaCallResult.setDisable(false);
			textareaCallResult.setVisible(true);
			textareaPostData.setVisible(false);
			lblWebServiceVerbAction.setText("Call Result");
			listViewSelectedValidations.setDisable(false);
			txtTestName.setText("");
			txtTestDescription.setText("");
			txtWebServiceURL.setText("");
			textareaCallResult.setText("");
			testCaseDelayText.setDisable(false);
			testCaseGETCheckBox.setDisable(false);
			testCasePOSTCheckBox.setDisable(false);
			
			Test t = new Test();
			t.setCallResult("");
			//t.setExecutionIndex(0);
			t.setId(0);
			t.setTestDescription("");
			t.setTestName("");
			t.setDelay(0);
			int insertionPoint = testNameCombo.getSelectionModel().getSelectedIndex();
			int setSize = selectedTestSuite.getSuiteData().size();
			System.out.println("insertion size : " + insertionPoint);
			System.out.println("setsize : " + setSize);
			if (insertionPoint >= setSize){
				//We are at the end, so append to set
				System.out.println("Appending to set");
				selectedTestSuite.getSuiteData().add(t);
				txtExecutionIndex.setText(selectedTestSuite.getSuiteData().size()+"");
				
			}else{
				//we are somewhere after the first and before the last, so insert. 
				System.out.println("Inserting midway record.");
				selectedTestSuite.getSuiteData().add(insertionPoint, t);
				t.setExecutionIndex(insertionPoint+1);
				txtExecutionIndex.setText((insertionPoint+1 )+"");
			}
			selectedTest = t;
			listViewSelectedValidations.getItems().clear();
			System.out.println("Clearing testNameCombo");
			testNameCombo.getItems().clear();
			System.out.println("Adding New Temporary Item");
			testNameCombo.getItems().add("Creating new");
			System.out.println("Setting temporary item to active");
			testNameCombo.getSelectionModel().select(0);
			testNameCombo.setDisable(true);
		}
		else if ( "btnCancelTestCaseEdit".equalsIgnoreCase(buttonText)){
			for ( MenuItem mi : testCaseMenuButton.getItems()){
				if (mi.getText().matches("Cancel|Save|Edit|Delete")){
					mi.setDisable(true);
				}else{
					mi.setDisable(false);
				}
			}
			String selectedTestItem =  testNameCombo.getSelectionModel().getSelectedItem();
			testNameCombo.getItems().clear();
			testNameCombo.getItems().add("--None Selected--");
			if (selectedTestItem.equalsIgnoreCase("Creating new") || selectedTestItem.equalsIgnoreCase("--None Selected--")  ){
				selectedTestSuite.getSuiteData().remove(selectedTest);
			}else{
				for ( Test t : selectedTestSuite.getSuiteData()){
					testNameCombo.getItems().add(t.getTestName());
				}
				testNameCombo.getSelectionModel().select(selectedTestItem);
			}
			testCaseDelayText.setDisable(true);
			txtExecutionUser.setDisable(true);
			txtExecutionUserPassword.setDisable(true);
			testCaseGETCheckBox.setDisable(true);
			testCasePOSTCheckBox.setDisable(true);
			btnCallWebService.setDisable(true);
			btnUseForValidation.setDisable(true);
			btnRemoveForValidation.setDisable(true);
			formatLeftButton.setDisable(true);
			saveVariable.setDisable(true);
			txtExecutionIndex.setText("");
			txtExecutionIndex.setDisable(true);
			txtTestName.setDisable(true);
			txtTestDescription.setDisable(true);
			txtWebServiceURL.setDisable(true);
			textareaCallResult.setDisable(true);
			textareaPostData.setDisable(true);
			listViewSelectedValidations.setDisable(true);
		}
		else if ( "btnTestsEditTest".equalsIgnoreCase(buttonText)){
			
			System.out.println("EDIT CLICKED");
			for ( MenuItem mi : testCaseMenuButton.getItems()){
				if (mi.getText().matches("New|Edit|Delete")){
					mi.setDisable(true);
				}else{
					mi.setDisable(false);
				}
			}
			testNameCombo.setDisable(true);
			btnCallWebService.setDisable(false);
			btnUseForValidation.setDisable(false);
			btnRemoveForValidation.setDisable(false);
			formatLeftButton.setDisable(false);
			saveVariable.setDisable(false);
			testVerb.setDisable(false);
			txtTestName.setDisable(false);
			txtExecutionUser.setDisable(false);
			txtExecutionUserPassword.setDisable(false);
			testCaseGETCheckBox.setDisable(false);
			testCasePOSTCheckBox.setDisable(false);
			txtTestDescription.setDisable(false);
			txtWebServiceURL.setDisable(false);
			textareaCallResult.setDisable(false);
			textareaPostData.setDisable(false);
			listViewSelectedValidations.setDisable(false);
			testCaseDelayText.setDisable(false);
		}
		else if ( "btnSaveTest".equalsIgnoreCase(buttonText)){
			String testName = txtTestName.getText();
			if ( "".equalsIgnoreCase(testName) ){
				new Alert(AlertType.WARNING,"Unable to save. Please enter a test name.",ButtonType.OK).showAndWait();
				return;
			}
			LoggerFactory.getLogger(GTAFApplController.class).info("Saving Test");
				selectedTest.setTestName(txtTestName.getText());
				selectedTest.setPostData((textareaPostData.getText()));
				selectedTest.setCallResult(textareaCallResult.getText());
				selectedTest.setVerb(testCaseGETCheckBox.isSelected()?"GET":"POST");
				try{
					if (!isEditingTestSuite)
					selectedTest.setExecutionIndex(Integer.parseInt( txtExecutionIndex.getText()));
				}catch(NumberFormatException nfe){
					//Do nothing. 
				}
				selectedTest.setTestDescription(txtTestDescription.getText());
				selectedTest.setWebServiceURL(txtWebServiceURL.getText());
				selectedTest.setExecutionUserName(txtExecutionUser.getText());
				selectedTest.setExecutionPassword(txtExecutionUserPassword.getText());
				
			try {
				selectedTest.setDelay( Integer.parseInt( testCaseDelayText.getText()));
			} catch (NumberFormatException nfe) {
				new Alert(AlertType.WARNING,"Unable to save. Delay is not a valid number.",ButtonType.OK).showAndWait();
				return;
			}
			testNameCombo.getItems().clear();
			testNameCombo.getItems().add("--None Selected--");
				
			for ( Test t : selectedTestSuite.getSuiteData() ){
				testNameCombo.getItems().add(t.getTestName());
			}
			for ( MenuItem mi : testCaseMenuButton.getItems()){
				if (mi.getText().matches("New|Edit|Delete")){
					System.out.println("Enabling Test case Menu Item " + mi.getText());
					mi.setDisable(false);
				}else{
					mi.setDisable(true);
				}
			}
			testNameCombo.getSelectionModel().select(selectedTest.getTestName());
			testCaseDelayText.setDisable(true);
			txtExecutionUser.setDisable(true);
			txtExecutionUserPassword.setDisable(true);
			testCaseGETCheckBox.setDisable(true);
			testCasePOSTCheckBox.setDisable(true);
			//testCaseGETCheckBox.setSelected(false);
			//testCasePOSTCheckBox.setSelected(false);
			btnCallWebService.setDisable(true);
			btnUseForValidation.setDisable(true);
			btnRemoveForValidation.setDisable(true);
			btnRunToHere.setDisable(true);
			txtExecutionIndex.setDisable(true);
			txtTestName.setDisable(true);
			txtTestDescription.setDisable(true);
			txtWebServiceURL.setDisable(true);
			textareaCallResult.setDisable(true);
			textareaPostData.setDisable(true);
			listViewSelectedValidations.setDisable(true);
			 testNameCombo.setDisable(false);
			// isSavingTest = false;
			 new Alert(AlertType.INFORMATION,"Saved Successfully",ButtonType.OK).showAndWait();
		}
		else if ("btnDeleteTestCase".equalsIgnoreCase(buttonText)){
			LoggerFactory.getLogger(GTAFApplController.class).info("Deleting Test " + selectedTest.getTestName());
			Alert al = new Alert(AlertType.CONFIRMATION);
			al.setHeaderText("Delete Test?");
			al.setContentText("Are you sure you want to delete the current test?");
			Optional<ButtonType> bt = al.showAndWait();
			if  (bt.isPresent() && bt.get() == ButtonType.OK){
				selectedTestSuite.getSuiteData().remove(selectedTest);
				testNameCombo.getItems().remove(selectedTest.getTestName());
				testNameCombo.getSelectionModel().select("--None Selected--");
				for ( MenuItem mi : testCaseMenuButton.getItems()){
					if (mi.getText().matches("Edit|Delete|Cancel|Save")){
						mi.setDisable(true);
					}else{
						mi.setDisable(false);
					}
				}
				btnCallWebService.setDisable(true);
				btnUseForValidation.setDisable(true);
				btnRemoveForValidation.setDisable(true);
				btnRunToHere.setDisable(true);
				testCaseGETCheckBox.setSelected(false);
				testCasePOSTCheckBox.setSelected(false);
				testCaseGETCheckBox.setDisable(true);
				testCasePOSTCheckBox.setDisable(true);
				txtExecutionIndex.setText("");
				txtExecutionIndex.setDisable(true);
				txtTestName.setText("");
				txtTestName.setDisable(true);
				txtExecutionUser.setText("");
				txtExecutionUser.setDisable(true);
				txtExecutionUserPassword.setText("");
				txtExecutionUserPassword.setDisable(true);
				txtTestDescription.setText("");
				txtTestDescription.setDisable(true);
				txtWebServiceURL.setText("");
				txtWebServiceURL.setDisable(true);
				
				textareaCallResult.setDisable(true);
				textareaCallResult.setVisible(true);
				
				textareaPostData.setVisible(false);
				lblWebServiceVerbAction.setText("Call Result");
				textareaCallResult.setText("");
				listViewSelectedValidations.getItems().clear();
				listViewSelectedValidations.setDisable(true);
				testCaseDelayText.setDisable(true);
			}
		}
	}
	private void initMenuBarEventHandlers() {
    	for ( Menu m : mainMenuBar.getMenus()){
     		for ( MenuItem mi : m.getItems()){
    			mi.setOnAction(new EventHandler<ActionEvent>() {
    				@Override
    				public void handle(ActionEvent arg0) {
    					doMenuAction(arg0);
    				}
    			});
    		}
    	}
	}
	private void initGuiHandlers() {
		guiTestSelectedTest.valueProperty().addListener(new ChangeListener<String>() {
           

			@Override public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, String t, String t1) {
				System.out.println("t1 is : " + t1);
            	if ( t1 == null  || t1.equalsIgnoreCase("-None Selected-") || t1.equalsIgnoreCase("Creating New")) {
            	//	if ( t1.equalsIgnoreCase("Creating New"))
            	//		return;
            		listGUILeftList.getItems().clear();
            		listGUIRightList.getItems().clear();
            		
            		ArrayList<GUITestDef> allTests = null;
            		try {
						allTests = new GUITestFactory().getAllTests();
					} catch (SQLException e) {
						e.printStackTrace();
					}
            		for ( GUITestDef gtd : allTests ){
            			System.out.println("Adding tests to left list");
            			listGUILeftList.getItems().add(gtd.getTestName() + " - " + gtd.getClazzName());
            		}
            		return;
            	}else{
            		btnGUIEditSuite.setDisable(false);
            		btnGUIDeleteSuite.setDisable(false);
            		btnRunGUITests.setDisable(false);
            		try {
						selectedGUITestSuite = new GUITestFactory().getGUITestSuite(t1);
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
            		listGUILeftList.getItems().clear();
            		listGUIRightList.getItems().clear();
            		ArrayList<GUITestDef> allTests = null;
            		try {
						allTests = new GUITestFactory().getAllTests();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
            		
            		for  (GUITestDef gtd : allTests ){
            			boolean hasFound = false;
            			for (GUITestDef gtdd : selectedGUITestSuite.getSuiteData()){
            				if ( gtd.getTestName().equalsIgnoreCase(gtdd.getTestName())){
            					listGUIRightList.getItems().add(gtd.getTestName() + " - " + gtd.getClazzName());
            					hasFound = true;
            					break;
            				}
            			}
            			if (!hasFound ){
            				listGUILeftList.getItems().add(gtd.getTestName() + " - " + gtd.getClazzName());
            			}
            		}
            		
            	}
            }
    	});
		btnRunGUITests.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				doGUIButtonAction(e);
			}
		});
		buttonGUIAddTest.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				doGUIButtonAction(e);
			}
		});
		
		buttonGUIDeleteTest.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				doGUIButtonAction(e);
			}
		});
		btnGUIEditSuite.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				doGUIButtonAction(e);
			}
		});
		btnGUICancelSuite.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				doGUIButtonAction(e);
			}
		});
		btnGUINewSuite.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				doGUIButtonAction(e);
			}
		});
		
		btnGUISave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				doGUIButtonAction(e);
			}
		});
		
		buttonGUIMoveOneToRight.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				doGUIButtonAction(e);
			}
		});
		buttonGUIMoveAllToRight.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				doGUIButtonAction(e);
			}
		});
		buttonGUIMoveAllToLeft.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				doGUIButtonAction(e);
			}
		});
		buttonGUIMoveOneToLeft.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				doGUIButtonAction(e);
			}
		});
		
		btnGUIDeleteSuite.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				doGUIButtonAction(e);
			}
		});
	}
	private void doMenuAction(ActionEvent arg0){
 		if ( arg0.getSource() instanceof MenuItem ){
 			String menuSelected = ( (MenuItem) arg0.getSource()).getText();
 			switch (menuSelected ){
	 			case "Close":{
	 				System.exit(0);
	 				break;
	 			}
	 			case "Manage Environments":{
	 				//	EnvironmentManager em = new EnvironmentManager(_parent);
	 			        break;
	 			}
	 			case "View Report":{
		 				try {
							Stage stage = new Stage();
							URL c = ReportsViewerController.class.getResource("ReportViewer.fxml");
							FXMLLoader loader = new FXMLLoader(c);
							Parent root = loader.load();
							ReportsViewerController vsc = (ReportsViewerController) loader.getController();
							vsc.loadData();
							stage.setScene(new Scene(root));
							stage.setTitle("View Report");
							stage.initModality(Modality.WINDOW_MODAL);
							stage.initOwner(
							        listViewSelectedValidations.getScene().getWindow() );
							stage.setX(MouseInfo.getPointerInfo().getLocation().getX());
							stage.setY(MouseInfo.getPointerInfo().getLocation().getY());
							stage.setMaximized(true);
							stage.show();
						} catch (HeadlessException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
	 			        break;
	 			}
	 			case "About":{
	 				
	 				Alert al = new Alert(AlertType.INFORMATION);
	 				al.setTitle("About");
	 				al.setHeaderText("Developed for ABSA CIB (Pangea) ");
	 				al.setContentText("Idea,design and implementation by Steven Blignaut <stebli1978@gmail.com>");
	 				al.showAndWait();
 					LoggerFactory.getLogger(GTAFApplController.class).info("About");
 			        break;
 			}
	 			default :{
	 				try{
	 					appendLog("Nothing to do");
	 				}catch(Exception e){	LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e);}
	 				break;
	 			}
 			}
 		}
	}
	private void doGUIButtonAction(ActionEvent e) {
		LoggerFactory.getLogger(GTAFApplController.class).info("doGUIButtonAction");
		if ( e.getSource() instanceof Button ){
			String buttonText = ( (Button) e.getSource()).getId();
			LoggerFactory.getLogger(GTAFApplController.class).info("Button Text is " + buttonText);
			if ( "btnRunGUITests".equalsIgnoreCase(buttonText)){
				Runnable r = (Runnable) new GUIController(this, selectedGUITestSuite);
				Thread t = new Thread(r);
				t.start();
			//	cd.go2();
			}else if ( "buttonGUIAddTest".equalsIgnoreCase(buttonText)){
				// Create the custom dialog.
				Dialog<Pair<String, String>> dialog = new Dialog<>();
				dialog.setTitle("Add GUI Test");
				dialog.setHeaderText("Enter the new test details. ");

				// Set the button types.
				ButtonType saveButtonType = new ButtonType("Save", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

				// Create the username and password labels and fields.
				GridPane grid = new GridPane();
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(20, 150, 10, 10));
				
				TextField testName = new TextField();
				testName.setPromptText("Test Name");
				TextField testClazz = new TextField();
				testClazz.setPromptText("Test Clazz");
				
				grid.add(new Label("Test name:"), 0, 0);
				grid.add(testName, 1, 0);
				grid.add(new Label("Test clazz:"), 0, 1);
				grid.add(testClazz, 1, 1);

				Button b = new Button("Browse...");
				grid.add(b, 2, 1);
				b.setOnAction(event -> {
					FileChooser fileChooser = new FileChooser();
			    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Java File", "*.java"));
			    	 File selectedFile = fileChooser.showOpenDialog(b.getScene().getWindow());
			    	 if (selectedFile == null) {
			    		 Alert al = new Alert(AlertType.ERROR);
			    		 al.setHeaderText("File was Null ");
			    		 al.setContentText("In order to continue, please selected a valid file.");
			    		 al.showAndWait();
			    	 }
			    	 String name = selectedFile.getPath().substring(selectedFile.getPath().lastIndexOf("com\\ilabquality\\gtaf\\guitests"))
			    			 .replace("\\", ".").replaceAll(".java", "");
			    	 testClazz.setText(name);
				});
				// Enable/Disable login button depending on whether a username was entered.
				Node loginButton = dialog.getDialogPane().lookupButton(saveButtonType);
				loginButton.setDisable(true);

				// Do some validation (using the Java 8 lambda syntax).
				testName.textProperty().addListener((observable, oldValue, newValue) -> {
				    loginButton.setDisable(newValue.trim().isEmpty());
				});

				dialog.getDialogPane().setContent(grid);

				// Request focus on the testName field by default.
				Platform.runLater(() -> testName.requestFocus());

				// Convert the result to a testName-testClazz-pair when the login button is clicked.
				dialog.setResultConverter(dialogButton -> {
				    if (dialogButton == saveButtonType) {
				        return new Pair<>(testName.getText(), testClazz.getText());
				    }
				    return null;
				});

				Optional<Pair<String, String>> result = dialog.showAndWait();

				result.ifPresent(testDetails -> {
				    System.out.println("Test Name=" + testDetails.getKey() + ", Test Class=" + testDetails.getValue());
				    try {
						new GUITestFactory().saveTest(testDetails.getKey(), testDetails.getValue());
						listGUILeftList.getItems().add(testDetails.getKey() + " - " + testDetails.getValue());
					} catch (SQLException e1) {
					   new Alert(AlertType.ERROR,"Unable to save test\n " + e1.getMessage()).showAndWait();
					   //e1.printStackTrace();
					}
				});
			}
			else if ("buttonGUIDeleteTest".equalsIgnoreCase(buttonText)){
				String selItem = listGUILeftList.getSelectionModel().getSelectedItem();
				if ( selItem != null & !"".equalsIgnoreCase(selItem)){
					try {
						new GUITestFactory().deleteTest(selItem.substring(0,selItem.indexOf(" -")));
						listGUILeftList.getItems().remove(selItem);
					} catch (SQLException e1) {
						
						new Alert(AlertType.ERROR,"Unable to delete test" + selItem +"\n" + e1.getMessage()).showAndWait();
					}
				}
			}			
			else if ("btnGUINewSuite".equalsIgnoreCase(buttonText)){
				selectedGUITestSuite = new GUITestSuite();
				selectedGUITestSuite.setId(-1);
				System.out.println("New Clicked");
				listGUILeftList.getItems().clear();
				listGUIRightList.getItems().clear();
				labelGUINewSuiteName.setVisible(true);
				textGUINewSuiteName.setDisable(false);
				textGUINewSuiteName.setVisible(true);
				listGUILeftList.setDisable(false);
				listGUIRightList.setDisable(false);
				buttonGUIMoveOneToRight.setDisable(false);
				buttonGUIMoveAllToRight.setDisable(false);
				buttonGUIMoveOneToLeft.setDisable(false);
				buttonGUIMoveAllToLeft.setDisable(false);
				btnGUIDeleteSuite.setDisable(true);
				btnGUICancelSuite.setDisable(false);
				btnGUISave.setDisable(false);
				btnGUISave.setVisible(true);
				btnGUINewSuite.setVisible(false);
				guiTestSelectedTest.getItems().add("Creating New");
				guiTestSelectedTest.getSelectionModel().select("Creating New");
				guiTestSelectedTest.setDisable(true);
			}
			else if ("btnGUIEditSuite".equalsIgnoreCase(buttonText)){
				System.out.println("Edit Clicked");
				btnGUINewSuite.setDisable(true);
				btnGUIDeleteSuite.setDisable(true);
				btnGUICancelSuite.setDisable(false);
				guiTestSelectedTest.setDisable(true);
				labelGUINewSuiteName.setVisible(true);
				textGUINewSuiteName.setDisable(false);
				textGUINewSuiteName.setVisible(true);
				textGUINewSuiteName.setText(guiTestSelectedTest.getSelectionModel().getSelectedItem());
				btnGUISave.setVisible(true);
				btnGUISave.setDisable(false);
				btnGUINewSuite.setVisible(false);
				buttonGUIMoveOneToRight.setDisable(false);
				buttonGUIMoveAllToRight.setDisable(false);
				buttonGUIMoveOneToLeft.setDisable(false);
				buttonGUIMoveAllToLeft.setDisable(false);
				
			}
			else if ("btnGUICancelSuite".equalsIgnoreCase(buttonText)){
				System.out.println("Cancel Clicked");
				selectedGUITestSuite = guiSuiteBeforeSave;
				btnGUINewSuite.setDisable(false);
				btnGUICancelSuite.setDisable(false);
				labelGUINewSuiteName.setVisible(false);
				textGUINewSuiteName.clear();
				textGUINewSuiteName.setDisable(true);
				textGUINewSuiteName.setVisible(false);
				guiTestSelectedTest.getSelectionModel().select("-None Selected-");
				btnGUIEditSuite.setDisable(true);
				btnGUICancelSuite.setDisable(false);
				btnGUISave.setDisable(true);
				btnGUISave.setVisible(false);
				btnGUINewSuite.setVisible(true);
				guiTestSelectedTest.setDisable(false);
				buttonGUIMoveOneToRight.setDisable(true);
				buttonGUIMoveAllToRight.setDisable(true);
				buttonGUIMoveOneToLeft.setDisable(true);
				buttonGUIMoveAllToLeft.setDisable(true);
			}
			else if ("btnGUISave".equalsIgnoreCase(buttonText)){
				System.out.println("Save Clicked");
			
				if ( listGUIRightList.getItems().size() == 0)
				{
					new Alert(AlertType.ERROR,"Unable to save empty suite. Add tests first.",ButtonType.OK).showAndWait();
					return;
				}
				if ( textGUINewSuiteName.getText() == null || textGUINewSuiteName.getText().equalsIgnoreCase("")){
					new Alert(AlertType.ERROR,"Unable to save suite without a name. Add a name first.",ButtonType.OK).showAndWait();
					
					return;
				}
				selectedGUITestSuite.setName(textGUINewSuiteName.getText());
				try {
					new GUITestFactory().saveSuite(selectedGUITestSuite);
				} catch (SQLException e1) {
					
					e1.printStackTrace();
					new Alert(AlertType.ERROR,"Unable to save suite , insert failed. Check the logs. ",ButtonType.OK).showAndWait();
					LoggerFactory.getLogger(GTAFApplController.class).error("Unable to save suite , insert failed. Check the logs. ", e1);
					return;
				}
				
				btnGUINewSuite.setDisable(false);
				btnGUICancelSuite.setDisable(true);
				labelGUINewSuiteName.setVisible(false);
				textGUINewSuiteName.clear();
				textGUINewSuiteName.setDisable(true);
				textGUINewSuiteName.setVisible(false);
				
				guiTestSelectedTest.getSelectionModel().select("-None Selected-");
				guiTestSelectedTest.getItems().remove("Creating New");
				btnGUIEditSuite.setDisable(true);
				btnGUICancelSuite.setDisable(false);
				btnGUISave.setDisable(true);
				btnGUISave.setVisible(false);
				btnGUINewSuite.setVisible(true);
				guiTestSelectedTest.setDisable(false);
				buttonGUIMoveOneToRight.setDisable(true);
				buttonGUIMoveAllToRight.setDisable(true);
				buttonGUIMoveOneToLeft.setDisable(true);
				buttonGUIMoveAllToLeft.setDisable(true);
				
				guiTestSelectedTest.getItems().clear();
				ArrayList<GUITestDef> tests = null;
				try {
					tests = new GUITestFactory().getAllTests();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				for ( GUITestDef gtd :tests ){
					guiTestSelectedTest.getItems().add(gtd.getTestName());
				}
			}	
			else if ("btnGUIDeleteSuite".equalsIgnoreCase(buttonText)){
				System.out.println("Delete Clicked");
				Alert al = new Alert(AlertType.CONFIRMATION,"Are you sure you want to delete the Suite \"" + guiTestSelectedTest.getSelectionModel().getSelectedItem()+"\"?",ButtonType.YES,ButtonType.NO);
				Optional<ButtonType> bt = al.showAndWait();
				if ( bt.get().getText().equals("Yes") ){
					System.out.println("Deleting Suite");
					try {
						new GUITestFactory().deleteSuite(guiTestSelectedTest.getSelectionModel().getSelectedItem());
					} catch (SQLException e1) {
						new Alert(AlertType.ERROR,"Error deleting test suite.",ButtonType.OK).showAndWait();
						LoggerFactory.getLogger(GTAFApplController.class).error("Error deleting test suite.", e1);
					}
				}
				else if ( bt.get().getText().equals("No")){
						System.out.println("Aborting Delete");
				}else{
					System.out.println(bt.get().getText() );
				}
				
			}
			else if ( "buttonGUIMoveOneToRight".equals(buttonText)){
				String selectedItem = listGUILeftList.getSelectionModel().getSelectedItem();
				if ( selectedItem != null ){
					listGUIRightList.getItems().add( selectedItem);
					listGUILeftList.getItems().remove(selectedItem);
					try {
						selectedGUITestSuite.addTest(selectedItem);
					} catch (SQLException e1) {
						new Alert(AlertType.ERROR,"Unable to remove test, please check the logs.",ButtonType.OK).showAndWait();
						LoggerFactory.getLogger(GTAFApplController.class).error("Unable to remove test, please check the logs.", e1);
					}
				}
			}
			else if ( "buttonGUIMoveAllToRight".equals(buttonText)){
				listGUIRightList.getItems().addAll(listGUILeftList.getItems());
			
				try {
					selectedGUITestSuite.addAll(listGUILeftList.getItems());
				} catch (SQLException e1) {
					new Alert(AlertType.ERROR,"Unable to add tests, please check the logs. ",ButtonType.OK).showAndWait();
					e1.printStackTrace();
					LoggerFactory.getLogger(GTAFApplController.class).error("Unable to add tests, please check the logs. ", e1);
				}
				listGUILeftList.getItems().clear();
			}
			else if ("buttonGUIMoveOneToLeft".equals(buttonText)){
				String selectedItem = listGUIRightList.getSelectionModel().getSelectedItem();
				if ( selectedItem != null ){
					listGUILeftList.getItems().add(selectedItem);
					listGUIRightList.getItems().remove(selectedItem);
					selectedGUITestSuite.removeTest(selectedItem);
				}
			}
			else if ( "buttonGUIMoveAllToLeft".equals(buttonText)){
				listGUILeftList.getItems().addAll(listGUIRightList.getItems());
				listGUIRightList.getItems().clear();
				selectedGUITestSuite.getSuiteData().clear();
			}
		}
	}
	
	
	private void initTestPackHandlers(){
		testPackNameCombo.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, String t, String t1) {
            	if ( t1 == null  || t1.equalsIgnoreCase("--None Selected--")) {
            		availableTestSuites.getItems().clear();
            		selectedTestSuites.getItems().clear();
            		runPackButton.setDisable(true);
    				btnCancelSavePack.setDisable(true);
    				btnSavePack.setDisable(true);
    				btnSavePack.setVisible(true);
    				newPackButton.setDisable(false);
    				editPackButton.setDisable(true);
    				deletePackButton.setVisible(false);
    				setPackEditingButtonsState(true);
            		return;
            	}else{
            		availableTestSuites.getItems().clear();
            		selectedTestSuites.getItems().clear();
            		ArrayList<Pack> p = new PackManagerFactory().getPacks();
            		ArrayList<TestSuite> suites = null;
					try {
						suites = new TestSuiteFactory().getTestSuites();
					} catch (SQLException e) {
						LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e);
					}
            		ArrayList<TestSuite> leftList = new ArrayList<TestSuite>();
            		for ( TestSuite ts : suites ){
            			leftList.add(ts);
            		}
            		ArrayList<String> rightList = new ArrayList<String>();
            		for ( Pack pack : p ){
            			if ( pack.getPackName().equalsIgnoreCase(t1)){
            				selectedTestPack = pack;
            				for ( TestPackTestSuite i : pack.getPackData() ){
            					for ( TestSuite ts : suites ){
            						if ( i.getSuiteID() == ts.getTestSuiteID() ){
            							rightList.add(ts.getTestSuiteName());
            							leftList.remove(ts);
            						}
            					}
            				}
            			}
            		}
            		for ( TestSuite ts : leftList ){
            			availableTestSuites.getItems().add(ts.getTestSuiteName());
            		}
            		for  (String s : rightList ){
            			selectedTestSuites.getItems().add(s);
            		}
            		runPackButton.setDisable(false);
    				btnCancelSavePack.setDisable(true);
    				btnSavePack.setVisible(false);
    				
    				newPackButton.setDisable(false);
    				editPackButton.setDisable(false);
    				deletePackButton.setVisible(true);
    				deletePackButton.setDisable(false);
    				packNameText.setText(t1);
            	}
            }
    	});
		newPackButton.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent e) {doPackManagerButtonAction(e);}});
		btnCancelSavePack.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent e) {doPackManagerButtonAction(e);}});
		runPackButton.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent e) {doPackManagerButtonAction(e);}});
		editPackButton.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent e) {doPackManagerButtonAction(e);}});
		deletePackButton.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent e) {doPackManagerButtonAction(e);}});
		addSingleSuite.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent e) {doPackManagerButtonAction(e);}});
		addAllSuites.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent e) {doPackManagerButtonAction(e);}});
		removeSingleSuite.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent e) {doPackManagerButtonAction(e);}});
		removeAllSuites.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent e) {doPackManagerButtonAction(e);}});
		moveSuiteUp.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent e) {doPackManagerButtonAction(e);}});
		moveSuiteDown.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent e) {doPackManagerButtonAction(e);}});
		btnSavePack.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent e) {doPackManagerButtonAction(e);}});
	}
	private void doPackManagerButtonAction(ActionEvent e) {
		LoggerFactory.getLogger(GTAFApplController.class).info("doPackManagerButtonAction");
		if ( e.getSource() instanceof Button ){
			String buttonText = ( (Button) e.getSource()).getId();
			LoggerFactory.getLogger(GTAFApplController.class).info("Button Text is " + buttonText);
			if ( "newPackButton".equalsIgnoreCase(buttonText)){
				testPackNameCombo.getItems().clear();
				testPackNameCombo.getItems().add("--Creating new--");
				runPackButton.setDisable(true);
				btnCancelSavePack.setDisable(false);
				btnSavePack.setDisable(false);
				newPackButton.setDisable(true);
				editPackButton.setDisable(true);
				deletePackButton.setVisible(false);
				setPackEditingButtonsState(false);
				availableTestSuites.setDisable(false);
        		selectedTestSuites.setDisable(false);
				availableTestSuites.getItems().clear();
        		selectedTestSuites.getItems().clear();
        		packNameText.setDisable(false);
        		packNameText.clear();
				selectedTestPack = new Pack();
				//isEditingPack = true;
				ArrayList<TestSuite> suites = null;
				try {
					suites = new TestSuiteFactory().getTestSuites();
				} catch (SQLException e1) {
					LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);
				}
				if ( suites != null ) {
					for (TestSuite ts : suites) {
						availableTestSuites.getItems().add(ts.getTestSuiteName());
					}
				}
			}
			else if (  "addSingleSuite".equalsIgnoreCase(buttonText) ){
				String selectedSuite = availableTestSuites.getSelectionModel().getSelectedItem();
				availableTestSuites.getItems().remove(selectedSuite);
				selectedTestSuites.getItems().add(selectedSuite);
				
			}
			else if (  "btnCancelSavePack".equalsIgnoreCase(buttonText) ){
				runPackButton.setDisable(false);
				
				btnCancelSavePack.setDisable(true);
				btnSavePack.setDisable(true);
				btnSavePack.setVisible(false);
				newPackButton.setDisable(false);
				editPackButton.setDisable(false);
				deletePackButton.setDisable(false);
				deletePackButton.setVisible(true);
				packNameText.setDisable(true);
				availableTestSuites.setDisable(true);
				selectedTestSuites.setDisable(true);
				//Reset for new
	    		editPackButton.setDisable(true);
	    		deletePackButton.setVisible(false);
	    		btnCancelSavePack.setDisable(true);
	    		runPackButton.setDisable(true);
	    		btnSavePack.setDisable(true);
	    		packNameText.setDisable(true);
	    		availableTestSuites.getItems().clear();
				selectedTestSuites.getItems().clear();
				selectedTestSuites.setDisable(true);
				availableTestSuites.setDisable(true);
				testPackNameCombo.getItems().clear();
				testPackNameCombo.getItems().add("--None Selected--");
				testPackNameCombo.getItems().addAll(new PackManagerFactory().getPackNames());
				testPackNameCombo.getSelectionModel().select("--None Selected--");
				setPackEditingButtonsState(true);
			}			
			else if (  "editPackButton".equalsIgnoreCase(buttonText) ){
				//isEditingPack = true;
				runPackButton.setDisable(true);
				btnCancelSavePack.setDisable(false);
				btnSavePack.setDisable(false);
				btnSavePack.setVisible(true);
				newPackButton.setDisable(true);
				editPackButton.setDisable(true);
				deletePackButton.setDisable(true);
				deletePackButton.setVisible(false);
				packNameText.setDisable(false);
				availableTestSuites.setDisable(false);
				selectedTestSuites.setDisable(false);
				setPackEditingButtonsState(false);
			}
			else if (  "deletePackButton".equalsIgnoreCase(buttonText) ){
				Alert al = new Alert(AlertType.CONFIRMATION);
				al.setHeaderText("Delete Pacl?");
				al.setContentText("Are you sure you want to delete the current pack? Any test suites contained will need to be relinked.");
				Optional<ButtonType> bt = al.showAndWait();
				if ( bt.isPresent() && bt.get() == ButtonType.OK){
					runPackButton.setDisable(true);
					btnCancelSavePack.setDisable(true);
					btnSavePack.setDisable(true);
					newPackButton.setDisable(false);
					editPackButton.setDisable(true);
					deletePackButton.setDisable(true);
					packNameText.setText("");
					new PackManagerFactory().deletePack(testPackNameCombo.getSelectionModel().getSelectedItem());
					availableTestSuites.getItems().clear();
					selectedTestSuites.getItems().clear();
					testPackNameCombo.getItems().clear();
					testPackNameCombo.getItems().add("--None Selected--");
					testPackNameCombo.getItems().addAll(new PackManagerFactory().getPackNames());
					testPackNameCombo.getSelectionModel().select("--None Selected--");
					setPackEditingButtonsState(false);
				}
			
			}
			else if (  "removeSingleSuite".equalsIgnoreCase(buttonText) ){
				String selectedSuite = selectedTestSuites.getSelectionModel().getSelectedItem();
				selectedTestSuites.getItems().remove(selectedSuite);
				availableTestSuites.getItems().add(selectedSuite);
			}
			else if (  "addAllSuites".equalsIgnoreCase(buttonText) ){
				selectedTestSuites.getItems().addAll(availableTestSuites.getItems());
				availableTestSuites.getItems().clear();
				
			}
			else if (  "removeAllSuites".equalsIgnoreCase(buttonText) ){
				availableTestSuites.getItems().addAll(selectedTestSuites.getItems());
				selectedTestSuites.getItems().clear();
			}
			else if (  "moveSuiteUp".equalsIgnoreCase(buttonText) ){
				int selectedItemIndex = selectedTestSuites.getSelectionModel().getSelectedIndex();
				if ( selectedItemIndex > 0 ){
					String selectedItem = selectedTestSuites.getSelectionModel().getSelectedItem();
					selectedTestSuites.getItems().remove(selectedItemIndex);
					 selectedTestSuites.getItems().add(selectedItemIndex-1,selectedItem);
					 selectedTestSuites.getSelectionModel().select(selectedItemIndex-1);
				}
			}
			else if (  "moveSuiteDown".equalsIgnoreCase(buttonText) ){
				int selectedItemIndex = selectedTestSuites.getSelectionModel().getSelectedIndex();
				if (selectedItemIndex+1 == selectedTestSuites.getItems().size())
						return;
					String selectedItem = selectedTestSuites.getSelectionModel().getSelectedItem();
					selectedTestSuites.getItems().remove(selectedItemIndex);
				    selectedTestSuites.getItems().add(selectedItemIndex+1,selectedItem);
				    selectedTestSuites.getSelectionModel().select(selectedItemIndex+1);
			}
			else if (  "btnSavePack".equalsIgnoreCase(buttonText) ){
				String packName = packNameText.getText();
				if ( "".equalsIgnoreCase(packName) ){
					new Alert(AlertType.ERROR,"Unable to save. Please enter a pack name.",ButtonType.OK).showAndWait();
					return;
				}
				selectedTestPack.setPackName(packNameText.getText());
				selectedTestPack.getPackData().clear();
				ArrayList<TestSuite> suites = null;
				try {
					suites = new TestSuiteFactory().getTestSuites();
				} catch (SQLException e1) {
					LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);
				}
				ObservableList<String> selectedSuites = selectedTestSuites.getItems();
				int counter = 0;
				if  (selectedSuites != null && suites != null ) {
					for (String s : selectedSuites) {
						for (TestSuite t : suites) {
							if (s.equalsIgnoreCase(t.getTestSuiteName())) {
								selectedTestPack.addSuiteData(new TestPackTestSuite(t.getTestSuiteID(), counter));
								counter++;
							}
						}
					}
				}
				new PackManagerFactory().save(selectedTestPack);
				availableTestSuites.getItems().clear();
				selectedTestSuites.getItems().clear();
				testPackNameCombo.getItems().clear();
				testPackNameCombo.getItems().add("--None Selected--");
				testPackNameCombo.getItems().addAll(new PackManagerFactory().getPackNames());
				testPackNameCombo.getSelectionModel().select("--None Selected--");
				new Alert(AlertType.INFORMATION,"Saved Successfully",ButtonType.OK).showAndWait();
				runPackButton.setDisable(true);
				btnCancelSavePack.setDisable(true);
				btnSavePack.setDisable(true);
				newPackButton.setDisable(false);
				editPackButton.setDisable(true);
				deletePackButton.setDisable(true);
				setPackEditingButtonsState(true);
				availableTestSuites.setDisable(true);
				selectedTestSuites.setDisable(true);
				packNameText.setText("");
				packNameText.setDisable(true);
			}else if ("runPackButton".equalsIgnoreCase(buttonText)){
				appendLog("Starting Controller");
				appendLog("Starting Test Pack Execution Run at "  + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
				Runnable r = new TestPackController(this, testPackNameCombo.getSelectionModel().getSelectedItem());
				Thread t = new Thread(r);
				t.start();
			}
		}
	}
	private void initTestSuiteHandlers(){
		testCaseMenuButton.setDisable(true);
		testSuiteCombo.getItems().clear();
		testSuiteCombo.getItems().add("--None Selected--");
		testSuiteCombo.getSelectionModel().select("--None Selected--");
		testSuiteCombo.valueProperty().addListener(new ChangeListener<String>() {
	        @Override public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, String t, String t1) {
	        	if ( t1 == null  || t1.equalsIgnoreCase("--None Selected--")) {
	        		saveVariable.setDisable(true);
	        	}else{
	        		try {
	        			if( !t1.contains("Creating")){
	        				testCaseMenuButton.setDisable(false);
							populateTestSuiteGUI(populateTestSuite(t1));
							btnRunToHere.setDisable(false);
							for ( MenuItem mi : testSuiteMenuButton.getItems()){
								if (mi.getText().matches("New|Edit|Delete")){
									mi.setDisable(false);
								}else{
									mi.setDisable(true);
								}
							}
							for ( MenuItem mi : testCaseMenuButton.getItems()){
								mi.setDisable(true);
							}
	        			}else{
	        				testCaseMenuButton.setDisable(false);
	        			}
					} catch (SQLException e) {
						LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e);
					}
	        		testNameCombo.setDisable(false);
	        	}
	        }
		});
		MenuItem new_ = new MenuItem("New");
		new_.setOnAction(new EventHandler<ActionEvent>() {@Override	public void handle(ActionEvent e) {	try {doTestSuiteButtonAction(e);} 
						catch (SQLException e1) {LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);}}});
		MenuItem save = new MenuItem("Save");
		save.setOnAction(new EventHandler<ActionEvent>() {@Override	public void handle(ActionEvent e) {	try {doTestSuiteButtonAction(e);} 
						catch (SQLException e1) {LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);}}});
		MenuItem edit = new MenuItem("Edit");
		edit.setOnAction(new EventHandler<ActionEvent>() {@Override	public void handle(ActionEvent e) {	try {doTestSuiteButtonAction(e);}
						catch (SQLException e1) {LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);}}});	
		MenuItem cancel = new MenuItem("Cancel");
		cancel.setOnAction(new EventHandler<ActionEvent>(){@Override public void handle(ActionEvent e) {try {doTestSuiteButtonAction(e);} 
						catch (SQLException e1) {LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);}}});
		MenuItem delete = new MenuItem("Delete");
		delete.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent e) {try {doTestSuiteButtonAction(e);} 
						catch (SQLException e1) {LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);}}});
		testSuiteMenuButton.getItems().addAll(new_,save,edit,cancel,delete);	
		testSuiteMenuButton.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent e) {try {doTestSuiteButtonAction(e);} 
						catch (SQLException e1) {LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);}}});
		btnTestSuiteAddNew.setOnAction(new EventHandler<ActionEvent>() {@Override	public void handle(ActionEvent e) {	try {doTestSuiteButtonAction(e);} 
						catch (SQLException e1) {	LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);}}});
		btnTestSuiteEdit.setOnAction(new EventHandler<ActionEvent>() {@Override	public void handle(ActionEvent e) {	try {doTestSuiteButtonAction(e);} 
						catch (SQLException e1) {	LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);}}});
		btnTestSuiteDelete.setOnAction(new EventHandler<ActionEvent>() {@Override	public void handle(ActionEvent e) {	try {doTestSuiteButtonAction(e);} 
						catch (SQLException e1) {	LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);}}});
		btnSaveSuite.setOnAction(new EventHandler<ActionEvent>() {@Override	public void handle(ActionEvent e) {	try {doTestSuiteButtonAction(e);} 
						catch (SQLException e1) {	LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);}}});
		btnCancelTestSuiteEdit.setOnAction(new EventHandler<ActionEvent>() {@Override	public void handle(ActionEvent e) {	try {doTestSuiteButtonAction(e);} 
						catch (SQLException e1) {	LoggerFactory.getLogger(GTAFApplController.class).error("Exception!", e1);}}});
	}	
	private void setState(String buttonText) throws SQLException{
		if ( "btnTestSuiteAddNew".equalsIgnoreCase(buttonText)){
			clearGUI();
			for ( MenuItem mi : testSuiteMenuButton.getItems()) {
                if (mi.getText().matches("New|Edit|Delete")) {
                    mi.setDisable(true);
                } else {
                    mi.setDisable(false);
                }
            }
			for ( MenuItem mi : testCaseMenuButton.getItems()){
				if (mi.getText().matches("New")){
					mi.setDisable(false);
					
					System.out.println("Can create new TC");
				}
			}
			testSuiteCombo.setDisable(true);
			testSuiteCombo.getItems().clear();
			testSuiteCombo.getItems().add("Creating new...");
			testSuiteCombo.getSelectionModel().select("Creating new...");
			testCaseGETCheckBox.setSelected(false);
			testCasePOSTCheckBox.setSelected(false);
			testCaseGETCheckBox.setDisable(true);
			testCasePOSTCheckBox.setDisable(true);
			txtTestSuiteName.setDisable(false);
			txtTestSuiteID.setDisable(false);
			txtTestSuiteDescription.setDisable(false);
			txtTestSuiteID.setText("New");
			txtTestSuiteID.setDisable(true);
			selectedTestSuite = new TestSuite();
			selectedTest = new Test();
			selectedTestSuite.setSelectedTest(selectedTest);
			isEditingTestSuite = false;
		}
		else if ( "btnTestSuiteEdit".equalsIgnoreCase(buttonText)){
			for ( MenuItem mi : testSuiteMenuButton.getItems()){
				if (mi.getText().matches("New|Edit|Delete")){
					mi.setDisable(true);
				}else{
					mi.setDisable(false);
				}
			}
			testCaseMenuButton.setDisable(false);
			for ( MenuItem mi : testCaseMenuButton.getItems()){
				if (mi.getText().matches("New")){
					mi.setDisable(false);
				}
			}
			txtTestSuiteName.setDisable(false);
			txtTestSuiteID.setDisable(false);
			txtTestSuiteDescription.setDisable(false);
			String selectedTest = testNameCombo.getSelectionModel().getSelectedItem();
			if( selectedTest!= null &&	!selectedTest.equalsIgnoreCase("--None Selected--")){
				for ( MenuItem mi : testCaseMenuButton.getItems()){
					if (mi.getText().matches("Edit|Delete")){
						mi.setDisable(false);
					}
				}
			}
			isEditingTestSuite = true;
			selectedTestSuite.setSelectedTest(this.selectedTest);
		}	
		else if ( "btnCancelTestSuiteEdit".equalsIgnoreCase(buttonText)){
			for ( MenuItem mi : testSuiteMenuButton.getItems()){
				if (mi.getText().matches("Edit|Delete|Save")){
					mi.setDisable(true);
				}else{
					mi.setDisable(false);
				}
			}
			for ( MenuItem mi : testCaseMenuButton.getItems()){
				if (mi.getText().matches("New")){
					mi.setDisable(false);
				}
			}
			txtTestSuiteName.setDisable(false);
			txtTestSuiteID.setDisable(false);
			txtTestSuiteDescription.setDisable(false);
			testNameCombo.getItems().clear();
			testNameCombo.getItems().add("--None Selected--");
			testNameCombo.getSelectionModel().select("--None Selected--");
			textareaCallResult.setDisable(true);
			textareaCallResult.clear();
			textareaPostData.clear();
			textareaPostData.setDisable(true);
			testNameCombo.setDisable(true);
			selectedTestSuite.getSuiteData().remove(selectedTest);
			initTestSuiteInitialStates();
			isEditingTestSuite = false;
			selectedTestSuite.setSelectedTest(null);
		}	
		else if ( "btnTestSuiteDelete".equalsIgnoreCase(buttonText)){
			Alert al = new Alert(AlertType.CONFIRMATION);
			al.setHeaderText("Delete Suite");
			al.setContentText("Are you sure you want to delete the current suite?\nIf this suite is contained in a pack, the pack may become invalid.");
			Optional<ButtonType> bt = al.showAndWait();
			if ( bt.isPresent() && bt.get() == ButtonType.OK){
				for ( MenuItem mi : testSuiteMenuButton.getItems()){
					if (mi.getText().matches("Delete|Cancel")){
						mi.setDisable(true);
					}else{
						mi.setDisable(false);
					}
				}
				TestSuiteFactory tsf = new TestSuiteFactory();
				tsf.deleteSuite(this,selectedTestSuite);
				initTestSuiteInitialStates();
				txtTestSuiteName.setDisable(true);
				txtTestSuiteName.clear();
				txtTestSuiteID.setDisable(true);
				txtTestSuiteID.clear();
				txtTestSuiteDescription.setDisable(true);
				txtTestSuiteDescription.clear();
				testNameCombo.setDisable(true);
				btnRemoveForValidation.setDisable(true);
				btnRunToHere.setDisable(true);
			}
			
		}
		else if ( "btnSaveSuite".equalsIgnoreCase(buttonText)){
			for ( MenuItem mi : testCaseMenuButton.getItems()){
				if ("Save".equalsIgnoreCase(mi.getText())){
					if ( !mi.isDisable() )
						mi.fire();
				}
			}
			for ( MenuItem mi : testSuiteMenuButton.getItems()){
				if (mi.getText().matches("Delete|Cancel")){
					mi.setDisable(true);
				}else{
					mi.setDisable(false);
				}
			}
			selectedTestSuite.setTestSuiteName(txtTestSuiteName.getText());
			selectedTestSuite.setTestSuiteDescription(txtTestSuiteDescription.getText());
			txtTestSuiteName.setDisable(true);
			txtTestSuiteName.clear();
			txtTestSuiteID.setDisable(true);
			txtTestSuiteID.clear();
			txtTestSuiteDescription.setDisable(false);
			txtTestSuiteDescription.clear();
			testSuiteCombo.getItems().clear();
			testSuiteCombo.getItems().add("--None Selected--");
			testSuiteCombo.getSelectionModel().select("--None Selected--");
			new TestSuiteFactory().saveTestSuite(this, selectedTestSuite);
			ArrayList<TestSuite> suites = new TestSuiteFactory().getTestSuites();
			for ( TestSuite tt : suites ){
				testSuiteCombo.getItems().add(tt.getTestSuiteName());
			}
			isEditingTestSuite = false;
			initTestSuiteInitialStates();
			
		}
	}
	private void initTestSuiteInitialStates(){
		LoggerFactory.getLogger(GTAFApplController.class).info("initTestSuiteInitialStates");
		for ( MenuItem mi : testSuiteMenuButton.getItems()){
			if (mi.getText().matches("Edit|Delete|Save|Cancel")){
				mi.setDisable(true);
			}else{
				mi.setDisable(false);
			}
		}
		for ( MenuItem mi : testCaseMenuButton.getItems()){
			if (mi.getText().matches("New|Edit|Cancel|Delete|Save")){
				mi.setDisable(true);
			}
		}
		btnUseForValidation.setDisable(true);
		btnCallWebService.setDisable(true);
		btnCancelTestSuiteEdit.setDisable(true);
		saveVariable.setDisable(true);
		btnRemoveForValidation.setDisable(true);
		testCaseGETCheckBox.setSelected(false);
		testCasePOSTCheckBox.setSelected(false);
		formatLeftButton.setDisable(true);

		txtTestName.setDisable(true);
		txtTestName.setStyle("-fx-opacity: 1.0;");
		txtTestSuiteName.setDisable(true);
		testCaseDelayText.setDisable(true);
		txtExecutionUser.setDisable(true);
		txtExecutionUserPassword.setDisable(true);
		
		txtTestSuiteID.setDisable(true);
		txtTestSuiteDescription.setDisable(true);
		txtTestSuiteDescription.setStyle("-fx-opacity: 1.0;");
		txtExecutionIndex.setDisable(true);
		txtWebServiceURL.setDisable(true);
		txtTestDescription.setDisable(true);
		textareaCallResult.setDisable(true);
		textareaCallResult.clear();
		textareaPostData.clear();
		textareaPostData.setDisable(true);
		listViewSelectedValidations.setDisable(true);
		testSuiteCombo.setDisable(false);
		//testVerb.setDisable(true);
		testNameCombo.getItems().clear();
		testNameCombo.getItems().add("--None Selected--");
		testSuiteCombo.getItems().clear();
		testSuiteCombo.getItems().add("--None Selected--");
		testSuiteCombo.getSelectionModel().select("--None Selected--");
		LoggerFactory.getLogger(Runner.class).info(testSuiteCombo.getSelectionModel().getSelectedItem());
		
		ArrayList<TestSuite> suites = null;
		try {
			suites = new TestSuiteFactory().getTestSuites();
		} catch (SQLException  | NullPointerException e) {
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		if ( suites != null) {
			for (TestSuite tt : suites) {
				testSuiteCombo.getItems().add(tt.getTestSuiteName());
			}
		}
		Collections.sort(testSuiteCombo.getItems());
		testCaseGETCheckBox.setDisable(true);
		testCasePOSTCheckBox.setDisable(true);
		clearGUI();
		//setDisabledStyles();
	}
	private void clearGUI() {
		txtTestName.setText("");
		txtExecutionUser.setText("");
		txtExecutionUserPassword.setText("");
		txtTestSuiteName.setText("");
		txtTestSuiteID.setText("");
		txtTestSuiteDescription.setText("");
		txtExecutionIndex.setText("");
		txtWebServiceURL.setText("");
		txtTestDescription.setText("");
		textareaCallResult.setText("");
		testCaseDelayText.setText("0");
		listViewSelectedValidations.getItems().clear();
		testNameCombo.getSelectionModel().select("--None Selected--");
		testCaseGETCheckBox.setSelected(false);
		testCasePOSTCheckBox.setSelected(false);
	}
	private void doTestSuiteButtonAction(ActionEvent e) throws SQLException {
		LoggerFactory.getLogger(GTAFApplController.class).info("doTestSuiteButtonAction");
		if ( e.getSource() instanceof Button ){
			String buttonText = ( (Button) e.getSource()).getId();
			LoggerFactory.getLogger(GTAFApplController.class).info("Button Text is " + buttonText);
			switch(buttonText){
				case "btnTestSuiteAddNew":
				case "btnTestSuiteEdit":
				case "btnTestSuiteDelete":
				case "btnSaveSuite":
				case "btnCancelTestSuiteEdit":
					setState(buttonText);
			}
		}else if ( e.getSource() instanceof MenuItem){
			String buttonText = ( (MenuItem) e.getSource()).getText();
			LoggerFactory.getLogger(GTAFApplController.class).info("Button Text is " + buttonText);
			switch(buttonText){
				case "New":{
					testSuiteMenuButton.setText("Action - New");
					setState("btnTestSuiteAddNew");
					break;
				}
				case "Edit":{
					testSuiteMenuButton.setText("Action - Edit");
					setState("btnTestSuiteEdit");
					break;
				}
				case "Save":{
					testSuiteMenuButton.setText("Action - Save");
					setState("btnSaveSuite");
					break;
				}	
				case "Delete":{
					testSuiteMenuButton.setText("Action - Delete");
					setState("btnTestSuiteDelete");
					break;
				}
				case "Cancel":{
					testSuiteMenuButton.setText("Action - Cancel");
					setState("btnCancelTestSuiteEdit");
					break;
				}
				default:{}
			}
		}
	}
	private void populateTestSuiteGUI(TestSuite t){
		txtTestSuiteName.clear();
		txtTestSuiteName.setText(t.getTestSuiteName());
		txtTestSuiteID.clear();
		txtTestSuiteID.setText(t.getTestSuiteID()+"");
		txtTestSuiteDescription.clear();
		txtTestSuiteDescription.setText(t.getTestSuiteDescription());
		testNameCombo.getItems().clear();
		testNameCombo.getItems().add("--None Selected--");
		for ( Test tt : t.getSuiteData() ){
			testNameCombo.getItems().add(tt.getTestName());
		}
		saveVariable.setDisable(false);
		testNameCombo.getSelectionModel().select("--None Selected--");
	}
	private TestSuite populateTestSuite(String t1) throws SQLException {
		this.selectedTestSuite = new TestSuiteFactory().getTestSuite(t1);
		return selectedTestSuite;
	}	
	private void setPackEditingButtonsState( boolean disabled ){
		addSingleSuite.setDisable(disabled);
		addAllSuites.setDisable(disabled);
		removeSingleSuite.setDisable(disabled);
		removeAllSuites.setDisable(disabled);
		moveSuiteUp.setDisable(disabled);
		moveSuiteDown.setDisable(disabled);
	}
	/**
     * Switch between the panels on the main UI
     * @param selectedItem Scene to swap to.
     */
	private void swapScene(String selectedItem){
    	LoggerFactory.getLogger(GTAFApplController.class).info("SELECTED TREE ITEM :: " + selectedItem);
    	@SuppressWarnings("unused")
		int debug = 1;
    	switch(selectedItem){
			case "REST Tests":{
	    		welcomePane.setVisible(false);
	    		absaGuiTestPane.setVisible(false);
	    		testSuitePanel.setVisible(false);
	    		testPackPanel.setVisible(false);
	    		harImporterPanel.setVisible(false);
	    		break;
			}
			case "GUI Test Execution":{
	    		welcomePane.setVisible(false);
	    		absaGuiTestPane.setVisible(true);
	    		testSuitePanel.setVisible(false);
	    		testPackPanel.setVisible(false);
	    		harImporterPanel.setVisible(false);
	    		guiTestSelectedTest.getItems().clear();
	    		guiTestSelectedTest.getItems().add("-None Selected-");
	    		
	    		ArrayList<GUITestSuite> suites = null;
				try {
					suites = new GUITestFactory().getGUITestSuites();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	    		for ( GUITestSuite s : suites ){
	    			guiTestSelectedTest.getItems().add(s.getName());
	    		}
	    		
	    		btnGUIEditSuite.setDisable(true);
	    		btnGUIDeleteSuite.setDisable(true);
	    		btnRunGUITests.setDisable(true);
	    		labelGUINewSuiteName.setVisible(false);
	    		textGUINewSuiteName.setVisible(false);
	    		buttonGUIMoveOneToRight.setDisable(true);
	    		buttonGUIMoveAllToRight.setDisable(true);
	    		buttonGUIMoveOneToLeft.setDisable(true);
	    		buttonGUIMoveAllToLeft.setDisable(true);
	    		//Add the current available tests to the list for view. 
	    		listGUILeftList.getItems().clear();
				listGUIRightList.getItems().clear();
				
				btnGUICancelSuite.setDisable(true);
	    		ArrayList<GUITestDef> allTests = null;
				try {
					allTests = new GUITestFactory().getAllTests();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
	    		for ( GUITestDef gtd : allTests ){
	    			listGUILeftList.getItems().add( gtd.getTestName() + " - " + gtd.getClazzName());
	    		}
	    		break;
			}
			case "Test Suites":{
	    		welcomePane.setVisible(false);
	    		absaGuiTestPane.setVisible(false);
	    		testSuitePanel.setVisible(true);
	    		testPackPanel.setVisible(false);
	    		harImporterPanel.setVisible(false);
	    		initTestSuiteInitialStates();
	    		break;
			}
			case "HAR Importer":{
	    		welcomePane.setVisible(false);
	    		absaGuiTestPane.setVisible(false);
	    		testSuitePanel.setVisible(false);
	    		testPackPanel.setVisible(false);
	    		harImporterPanel.setVisible(true);
	    		harImporterTestSuiteNameText.setText("Har Import - " +  new SimpleDateFormat("yyyyMMddHHmm").format(new java.util.Date()));
	    		harImporterTestSuiteDescTextArea.setText("Har Import - " +  new SimpleDateFormat("yyyyMMddHHmm").format(new java.util.Date()));
	    		break;
			}
			case "Test Packs":{
	    		welcomePane.setVisible(false);
	    		absaGuiTestPane.setVisible(false);
	    		testSuitePanel.setVisible(false);
	    		testPackPanel.setVisible(true);
	    		harImporterPanel.setVisible(false);
	    		//Reset for new
	    		editPackButton.setDisable(true);
	    		deletePackButton.setVisible(false);
	    		btnCancelSavePack.setDisable(true);
	    		runPackButton.setDisable(true);
	    		btnSavePack.setDisable(true);
	    		packNameText.setDisable(true);
	    		availableTestSuites.getItems().clear();
				selectedTestSuites.getItems().clear();
				selectedTestSuites.setDisable(true);
				availableTestSuites.setDisable(true);
				testPackNameCombo.getItems().clear();
				testPackNameCombo.getItems().add("--None Selected--");
				testPackNameCombo.getItems().addAll(new PackManagerFactory().getPackNames());
				testPackNameCombo.getSelectionModel().select("--None Selected--");
				setPackEditingButtonsState(true);
	    		break;
			}
			default:{
	    		welcomePane.setVisible(true);
	    		absaGuiTestPane.setVisible(false);
	    		testSuitePanel.setVisible(false);
	    		testPackPanel.setVisible(false);
	    		harImporterPanel.setVisible(false);
	    		break;
			}
    	}
    }    
	

    @FXML 
    protected void handleHARImporterSelectFileButton(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("HAR Files", "*.har"));
    	 File selectedFile = fileChooser.showOpenDialog(harImporterSelectFileButton.getScene().getWindow());
    	 if (selectedFile == null) {
    		 Alert al = new Alert(AlertType.ERROR);
    		 al.setHeaderText("File was Null ");
    		 al.setContentText("In order to continue, please selected a valid file.");
    		 al.showAndWait();
    	 }
    }
    
	/**
 	* Sets the Label Value of this instance.
 	* @param  textToAppend FXML Object by id  of this instance.
 	*/	
 	public void appendLog(final String textToAppend){
 		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				if ( firstRun ) {
		    		firstRun = false;
		    		outputTextArea.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()) + "    " + textToAppend);
		    		outputTextArea.positionCaret(outputTextArea.getText().length());
		    	}else{
		    		outputTextArea.setText(outputTextArea.getText()+"\n" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()) + "    " + textToAppend);
		    		outputTextArea.positionCaret(outputTextArea.getText().length());
		    	}
			}
		});
    
 	}
 	
	/**
	 * Convenience method to update the gui log. 
	 * @param newValue New String to add to the log.
	 */
	public void appendLog(String newValue, Object o)  {
		LoggerFactory.getLogger(GTAFApplController.class).info(newValue);
		appendLog(newValue);
	}
	
	public void reloadValidations() {//
		 listViewSelectedValidations.getItems().clear();
		 for ( TestValidation tv : selectedTest.getValidations()){
			 listViewSelectedValidations.getItems().add(tv.getExpectedResult());
		 }
		// listViewSelectedValidations.refresh();
	}
	private void showSelectorModifier(){
		  try {
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(VSelectorModifierController.class.getResource("vSelectorModifier.fxml"));
			    Parent root = loader.load();
			    VSelectorModifierController vsc = (VSelectorModifierController) loader.getController();
			   vsc.setSelectedTestSuite(selectedTestSuite);
	    		vsc.setSelectedValidation(selectedTest.getValidation(listViewSelectedValidations.getSelectionModel().getSelectedItem()));
			    vsc.setCaller(this);
			    vsc.loadData();
			    stage.setScene(new Scene(root));
			    stage.setTitle("Modify Value");
			    stage.initModality(Modality.WINDOW_MODAL);
			    stage.initOwner(
		  		        listViewSelectedValidations.getScene().getWindow() );
			    stage.setX(MouseInfo.getPointerInfo().getLocation().getX());
			    stage.setY(MouseInfo.getPointerInfo().getLocation().getY());
			    stage.show();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
	}
	
 	public void setTaskDisplayer(final String labelValue){
 		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				  taskDisplayer.setText(labelValue);
			}
		});
 	}
 	
 	public void incrementProgress(){
    	currentProgressValue = currentProgressValue + pbIncrementalValue;
    	Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				//System.out.println("Set progress to " + currentProgressValue);
				progressBar.setProgress(currentProgressValue);
				
			}
		});
 	}
 	/**
 	 * 
 	 */
	public void resetProgress(){
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				progressBar.setProgress(0.00);
		    	currentProgressValue = 0;
			}
		});
 	 	}
	/**
	 * Sets the maximum of the JavaFX ProgressBar
	 * 
	 * @param max
	 *            Maximum size
	 */
	public void setProgressMaximum(double max) {
			pbIncrementalValue = 1.0 / max;
			//System.out.println("Progress Max set to " + pbIncrementalValue);
	}
	
	public void setCallResultFromVariablesManager(String text){
		textareaCallResult.clear();
		textareaCallResult.setText(text);
	}
	
	public void showErrorAlert(String message){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				new Alert(AlertType.ERROR,message).showAndWait();
			}
		});
	
	}
}
