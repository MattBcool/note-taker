package com.mattbcool.window;

import com.mattbcool.main.Main;
import com.mattbcool.storage.Note;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class WindowHandler
{
	public Button addButton;

	public Pane setupMainScene(Pane mainLayout)
	{
		double noteSize = Main.dataHandler.width / 10d;
		
		ListView<StackPane> notes = new ListView<StackPane>();
		notes.getStylesheets().add(getClass().getResource("/css/CustomLists.css").toExternalForm());
		
		for(Note note : Main.dataHandler.notes)
		{
			notes.getItems().add(Main.dataHandler.displayAssetHandler.createNoteButton(note, noteSize));
		}
		
		StackPane stackPane = new StackPane();
		addButton = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/res/add.png"), noteSize, noteSize, true, true)));
		addButton.getStylesheets().add(getClass().getResource("/css/CustomButtons.css").toExternalForm());
		addButton.setOnAction(Main.dataHandler.buttonListener);
		stackPane.getChildren().add(addButton);
		notes.getItems().add(stackPane);
		
		notes.setPrefSize(Main.dataHandler.width, noteSize * 2.5);
		notes.setOrientation(Orientation.HORIZONTAL);
		notes.setFocusTraversable(false);
		notes.setLayoutY(Main.dataHandler.height / 2);
		
		InnerShadow is = new InnerShadow();
		is.setOffsetX(1.0f);
		is.setOffsetY(1.0f);
		
		Text text = new Text("Welcome back, Matthew!");
		text.setEffect(is);
		text.setFill(Color.WHITE);
		text.setFont(Font.font("Calibri", FontWeight.BOLD, Main.dataHandler.displayAssetHandler.getConvertedFontSize(50)));
		
		StackPane mainPane = new StackPane();
		mainPane.setPrefSize(Main.dataHandler.width, Main.dataHandler.height);
		mainPane.setStyle("-fx-background-color: #000000;");
		mainPane.getChildren().addAll(notes, text);
		StackPane.setAlignment(notes, Pos.CENTER);
		StackPane.setAlignment(text, Pos.TOP_CENTER);
		
		mainLayout.getChildren().add(mainPane);
		return mainLayout;
	}
	
	public ScrollPane setupNoteScene(ScrollPane layout)
	{
		return setupNoteScene(layout, Main.dataHandler.tabHandler.createTabList(Main.dataHandler.tabHandler.tabPane), Main.dataHandler.displayAssetHandler.createMenuBar());
	}
	
	public ScrollPane setupNoteScene(ScrollPane layout, MenuBar menuBar)
	{
		return setupNoteScene(layout, Main.dataHandler.tabHandler.createTabList(Main.dataHandler.tabHandler.tabPane), menuBar);
	}
	
	public ScrollPane setupNoteScene(ScrollPane layout, BorderPane borderPane, MenuBar menuBar)
	{
		Main.dataHandler.menuBar = menuBar;
		
		GridPane gridPane = new GridPane();
		Main.dataHandler.displayAssetHandler.setupTextDisplay();
		layout.setContent(Main.dataHandler.noteText);
		layout.setPannable(true);
		borderPane.setPickOnBounds(false);
		gridPane.getChildren().addAll(layout, borderPane, Main.dataHandler.menuBar);
		GridPane.setConstraints(Main.dataHandler.menuBar, 0, 0, 1, 2, HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(borderPane, 0, 2, 1, 2, HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(layout, 0, 4, 1, 2, HPos.CENTER, VPos.CENTER);
		Main.primaryStage.getScene().setRoot(gridPane);
		return layout;
	}
	
	public void resetMainView()
	{
		Pane pane = Main.dataHandler.windowHandler.setupMainScene(new Pane());
		Main.primaryStage.getScene().setRoot(pane);
	}
	
	public void handleResize(int height, int width)
	{
		Main.dataHandler.height = height;
		Main.dataHandler.width = width;
		if(Main.dataHandler.displayingNote)
		{
			Main.dataHandler.displayAssetHandler.setupTextDisplay();
		}
		else
		{
			resetMainView();
		}
	}
}
