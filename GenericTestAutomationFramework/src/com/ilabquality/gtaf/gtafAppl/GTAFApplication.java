package com.ilabquality.gtaf.gtafAppl;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class GTAFApplication extends Application
{
	public static boolean isCLI = false;
	public static KeyCloakWrapper KEYCLOAK_WRAPPER = new KeyCloakWrapper();
	VBox root;
	
	public void doLaunch(String[] args){
		try{
			Application.launch(args);
		System.out.println("After launch");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
  @Override
    public void start(Stage primaryStage) throws IOException {
	  System.out.println("start");
        root = FXMLLoader.load(getClass().getResource("GTAFApplication.fxml"));
        Scene scene = new Scene(root, 1250, 1000);
        primaryStage.setTitle("Pangea Test Automation Framework");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

}


