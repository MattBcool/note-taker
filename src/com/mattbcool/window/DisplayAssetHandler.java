package com.mattbcool.window;

import com.mattbcool.main.Main;
import com.mattbcool.storage.Note;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class DisplayAssetHandler
{
	public StackPane createNoteButton(Note note, double noteSize)
	{
		note.setupNotePreview();
		//double x = ((Main.dataHandler.width / 25d) * noteID) + ((noteID - 1d) * noteSize), y = (Main.dataHandler.height / 2d) - (noteSize / 2d); Old note positions
		StackPane stackPane = new StackPane();
		note.noteButton = new Button("");
		ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/res/stickynote.png"), noteSize, noteSize, true, true));
		image.setOpacity(0.5);
		note.noteButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e)
			{
				image.setFitHeight(noteSize * 1.1d);
				image.setFitWidth(noteSize * 1.1d);
				image.setOpacity(1);
			}});
		note.noteButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e)
			{
				image.setFitHeight(noteSize);
				image.setFitWidth(noteSize);
				image.setOpacity(0.5);
			}});
		note.noteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
				{
					@Override
					public void handle(MouseEvent e)
					{
						if(e.getButton() == MouseButton.SECONDARY || e.isControlDown())
						{
							ContextMenu contextMenu = new ContextMenu();
							MenuItem delete = new MenuItem("Delete");
							contextMenu.getItems().addAll(delete);
							delete.setOnAction(new EventHandler<ActionEvent>()
							{
							    @Override
							    public void handle(ActionEvent event)
							    {
							    	Main.dataHandler.notes.remove(note);
							    	Main.dataHandler.rwfile.removeFile("/Users/mattbcool/Desktop/notes/" + note.uuid.toString() + ".txt");
							    	Main.dataHandler.windowHandler.resetMainView();
							    }
							});
							contextMenu.show(stackPane, e.getScreenX(), e.getScreenY());
						}
					}
			
				});
		note.noteButton.getStylesheets().add(getClass().getResource("/css/CustomButtons.css").toExternalForm());
		note.noteButton.setPrefSize(noteSize, noteSize);
		note.noteButton.setOnAction(Main.dataHandler.buttonListener);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setPrefSize(noteSize, noteSize);
		
		borderPane.setCenter(note.titleBox);
		stackPane.getChildren().addAll(image, borderPane, note.noteButton);
		return stackPane;
	}
	
	public void setupTextDisplay()
	{
		Main.dataHandler.noteText.setPrefSize(Main.dataHandler.width, Main.dataHandler.height);
		Main.dataHandler.noteText.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
		    @Override
		    public void handle(KeyEvent keyEvent) 
		    {
		    	KeyCode key = keyEvent.getCode();
		        if(key == KeyCode.ESCAPE)
		        {
		        	// Handles saving of current opened tab when exiting editing scene
		        	Main.dataHandler.saveNote(Main.dataHandler.getNoteByUUID(Main.dataHandler.displayedNote));
		        	Main.dataHandler.displayingNote = false;
		        	Main.dataHandler.displayedNote = null;
		        	Main.dataHandler.tabHandler.tabPane = new TabPane();
		        	Main.dataHandler.tabHandler.tabs.clear();
		        	Main.dataHandler.windowHandler.resetMainView();
		        }
		        if(keyEvent.isControlDown() && key == KeyCode.S)
		        {
		        	Note note = Main.dataHandler.getNoteByUUID(Main.dataHandler.displayedNote);
		        	note.setupFileWrite();
		        	System.out.println("Saving to file"); //TODO Add way to display saved text to program
		        }
		    }
		});
	}
	
	public MenuBar createMenuBar()
	{
		System.out.println("<--- Recreating Menu --->");
		Menu menu = new Menu("File");
		MenuItem menuItem1 = new MenuItem("Copy");
		MenuItem menuItem2 = new MenuItem("Paste");
		
		Menu openSub = new Menu("Open");
		System.out.println(Main.dataHandler.tabHandler.tabs.values().toString());
		
		boolean displaying = false;
		for(Note note : Main.dataHandler.notes)
		{
			if(!Main.dataHandler.tabHandler.tabs.values().contains(note.uuid))
			{
				displaying = true;
				MenuItem menuItem = new MenuItem("\"" + note.name + "\"");
				
				menuItem.setOnAction(new EventHandler<ActionEvent>()
				{
			         @Override public void handle(ActionEvent e)
			         {
			        	 Main.dataHandler.tabHandler.createAndSelectNewTab(note, Main.dataHandler.tabHandler.tabPane, note.name);
			        	 Main.dataHandler.selectNoteToView(note);
			         }
			     });
				
				openSub.getItems().add(menuItem);
			}
		}
		if(!displaying)
		{
			openSub.getItems().add(new MenuItem("No results found."));
		}

		menu.getItems().add(openSub);
		menu.getItems().add(menuItem1);
		menu.getItems().add(menuItem2);

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(menu);
		
		return menuBar;
	}
	
	public double getConvertedFontSize(int fontSize)
	{
		return ((double)fontSize * (Main.dataHandler.height * Main.dataHandler.width) / (1280d * 720d));
	}
}
