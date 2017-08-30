package com.ilabquality.gtaf.gtafgui.reportsViewer;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.GTAFApplController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
public class ReportsViewerController {
	

    @FXML
    private ListView<String> availableExecutionsList;

    @FXML
    private TreeView<String> dateTreeView;

    @FXML
    private WebView currentExecutionWebView;
	
	public void initialize(){
		initTreeHandlers();
		initListHandlers();
		//sdateTreeView.getScene().getWindow().centerOnScreen();
	}
	
	
	private void initListHandlers() {
		
		
	}
	private void initTreeHandlers(){
		dateTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
			@Override
			public void changed(
					ObservableValue<? extends TreeItem<String>> arg0,
							TreeItem<String> oldItem, TreeItem<String> newItem) {
				if ( newItem != null && newItem.getValue() != null ){
					LoggerFactory.getLogger(GTAFApplController.class).info("NewItem " + newItem.getValue() );
					if (newItem.getChildren() ==  null || newItem.getChildren().size() == 0){
						//On a date, pick the parent which would be the year, and add the month to populate the list. 
						TreeItem<String> parent = newItem.getParent();
						loadAvailableExecutionsList(parent.getValue() ,  newItem.getValue());
					}
					
				}
			}
		});
       	availableExecutionsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				loadContent(newValue);
			}
       	});
	}
	
	protected void loadContent(String newValue) {
		
		WebEngine webEngine = currentExecutionWebView.getEngine();
		File f = new TestReportFactory().getReportFile(newValue);
		currentExecutionWebView.setContextMenuEnabled(false);
		
	     createContextMenu(currentExecutionWebView,f);
		webEngine.load("file:///" + f.getAbsolutePath());
	}
	private void createContextMenu(WebView webView,File f) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem openEx = new MenuItem("Open Externally");
        openEx.setOnAction(e -> {
			try {
				Desktop.getDesktop().open(f);
			} catch (IOException e1) {
				//
				e1.printStackTrace();
			}
		});
       
        MenuItem reload = new MenuItem("Reload Page");
        reload.setOnAction(e -> {
			currentExecutionWebView.getEngine().reload();
		});
        contextMenu.getItems().addAll(reload,openEx);
        webView.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(webView, e.getScreenX(), e.getScreenY());
            } else {
                contextMenu.hide();
            }
        });
    }

	private void loadAvailableExecutionsList(String year, String month) {
		try {
			ArrayList<String> executionsList = new TestReportFactory().getExecutionsForDate(year, month);
			availableExecutionsList.getItems().clear();
			availableExecutionsList.getItems().addAll(executionsList);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}


	public void loadData() {
		int debug=1;
	   	try {
	   		TreeItem<String> st = new TreeItem<>("Execution Dates");
			ArrayList<Date> dates = new TestReportFactory().getExecutionDates();
			ObservableList<TreeItem<String>> years = st.getChildren();
			for  (Date d : dates ){
				DateTime dt = new DateTime(d);
				boolean isAddedToTree = false;
				for ( TreeItem<String> ti : years){
					if ( ti.getValue().equals(dt.getYear()+"")){
						isAddedToTree = true;
					}
				}
				if (!isAddedToTree)
					years.add( new TreeItem<String>(dt.getYear()+""));
			}
			for  (Date d : dates ){
				DateTime dt = new DateTime(d);
				boolean isAddedToTree = false;
				for ( TreeItem<String> currentYear : years){
					if ( currentYear.getValue().equals(dt.getYear()+"")){
						ObservableList<TreeItem<String>> monthsInYear = currentYear.getChildren();
						for ( TreeItem<String> currMonth : monthsInYear){
							if ( currMonth.getValue().equalsIgnoreCase(dt.monthOfYear().getAsText())){
								isAddedToTree = true;
							}
						}
						if(!isAddedToTree){
							currentYear.getChildren().add(new TreeItem<String>(dt.monthOfYear().getAsText()));
						}
					}
				}
			}
			dateTreeView.setRoot(st);
		} catch (Exception e) {
			//
			e.printStackTrace();
		}
	}
}
