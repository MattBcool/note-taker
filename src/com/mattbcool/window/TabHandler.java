package com.mattbcool.window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.mattbcool.main.Main;
import com.mattbcool.storage.Note;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class TabHandler
{
	public TabPane tabPane;
	public HashMap<Tab, UUID> tabs;
	
	public TabHandler()
	{
		tabPane = new TabPane();
		tabs = new HashMap<Tab, UUID>();
	}
	
	public BorderPane createTabList(TabPane tabPane)
	{
		if(!Main.dataHandler.displayingNote) return new BorderPane();
		if(tabPane.getTabs().size() == 0)
		{
			Note note = Main.dataHandler.getNoteByUUID(Main.dataHandler.displayedNote);
			Tab newTab = new Tab("+");
		    newTab.setClosable(false);
		    tabPane.getTabs().add(newTab);
		    createAndSelectNewTab(note, tabPane, note.name);

		    tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>()
		    {
		    	@Override
		    	public void changed(ObservableValue<? extends Tab> observable, Tab oldSelectedTab, Tab newSelectedTab)
		    	{
		    		if(newSelectedTab == newTab)
		    		{
		    			UUID uuid = UUID.randomUUID();
		    			Note newNote = new Note("New Note", uuid, new ArrayList<String>());
		    			Main.dataHandler.notes.add(newNote);
		    			Main.dataHandler.selectNoteToView(newNote);
		    			createAndSelectNewTab(newNote, tabPane, newNote.name);
		    			Main.dataHandler.windowHandler.setupNoteScene(new ScrollPane());
		    			Main.dataHandler.menuBar = Main.dataHandler.displayAssetHandler.createMenuBar();
		    		}
		    		else
		    		{
		    			for(Tab tab : tabs.keySet())
		    			{
		    				if(newSelectedTab.idProperty() == tab.idProperty())
		    				{
		    					Main.dataHandler.saveNote(Main.dataHandler.getNoteByUUID(Main.dataHandler.displayedNote));
		    					Main.dataHandler.selectNoteToView(Main.dataHandler.getNoteByUUID(tabs.get(tab)));
		    					Main.dataHandler.windowHandler.setupNoteScene(new ScrollPane());
		    					Main.dataHandler.menuBar = Main.dataHandler.displayAssetHandler.createMenuBar();
		    					break;
		    				}
		    			}
		    		}
		    	}
		    });
		}

	    final BorderPane root = new BorderPane();
	    root.setCenter(tabPane);
	    return root;
	}
	
	public Tab createAndSelectNewTab(Note note, TabPane tabPane, String name)
	{
		Main.dataHandler.saveNote(Main.dataHandler.getNoteByUUID(Main.dataHandler.displayedNote));
		Main.dataHandler.displayedNote = note.uuid;
		
		
		Tab tab = new Tab("");
		tab.setClosable(false);
		ObservableList<Tab> tabList = tabPane.getTabs();
	    tab.closableProperty().bind(Bindings.size(tabList).greaterThan(2));
		Label label = new Label();
		label.setText(name);
		tab.setGraphic(label);
		
		// Handles saving when closing a tab
		tab.setOnCloseRequest(e -> {
		    Main.dataHandler.saveNote(note);
		    tabs.remove(tab);
		});

		TextField textField = new TextField();
		label.setOnMouseClicked(event -> {
			if(event.getClickCount() == 2)
			{
				textField.setText(label.getText());
				tab.setGraphic(textField);
				textField.selectAll();
				textField.requestFocus();
			}
		});

		textField.setOnAction(event -> {
			label.setText(textField.getText());
			note.name = textField.getText();
			tab.setGraphic(label);
		});

		textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue)
			{
				label.setText(textField.getText());
				note.name = textField.getText();
				tab.setGraphic(label);
			}
		});
		
		tabList.add(tabList.size() - 1, tab);
	    tabPane.getSelectionModel().select(tab);
	    tabs.put(tab, note.uuid);
		return tab;
	}
}
