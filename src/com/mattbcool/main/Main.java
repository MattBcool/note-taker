package com.mattbcool.main;

import com.mattbcool.data.DataHandler;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application
{
	public static DataHandler dataHandler;
	public static Stage primaryStage;
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception
	{
		dataHandler = new DataHandler();
		Main.primaryStage = primaryStage;
		
		dataHandler.notes = dataHandler.getNotesFromFiles(dataHandler.notes);
		
		// ADDING MAIN STAGE //
		Pane pane = dataHandler.windowHandler.setupMainScene(new Pane());
		dataHandler.mainScene = new Scene(pane, dataHandler.width, dataHandler.height);
		primaryStage.setScene(dataHandler.mainScene);
		
		primaryStage.setTitle("Note Taker");
		primaryStage.show();
		
		ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
		dataHandler.windowHandler.handleResize((int) primaryStage.getHeight(), (int) primaryStage.getWidth());

	    primaryStage.widthProperty().addListener(stageSizeListener);
	    primaryStage.heightProperty().addListener(stageSizeListener); 
		
		primaryStage.setOnCloseRequest(event -> {
		    dataHandler.saveNotes();
		});
	}
}
