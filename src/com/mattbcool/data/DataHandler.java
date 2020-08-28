package com.mattbcool.data;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import com.mattbcool.listeners.ButtonListener;
import com.mattbcool.listeners.KeyboardListener;
import com.mattbcool.main.Main;
import com.mattbcool.storage.Note;
import com.mattbcool.window.DisplayAssetHandler;
import com.mattbcool.window.TabHandler;
import com.mattbcool.window.WindowHandler;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class DataHandler
{
	public double width, height;
	public ScrollPane mainLayout, noteLayout;
	public Scene mainScene;
	public Parent mainParent, noteParent;
	
	public KeyboardListener keyboardListener;
	public ButtonListener buttonListener;

	public DisplayAssetHandler displayAssetHandler;
	public WindowHandler windowHandler;
	public TabHandler tabHandler;
	public ImageHandler imageHandler;
	public RWFile rwfile;
	
	public ArrayList<Note> notes;
	public boolean displayingNote;
	public UUID displayedNote;
	public MenuBar menuBar;
	
	public TextArea noteText;
	
	public DataHandler()
	{
		width = 1280;
		height = 720;
		mainLayout = new ScrollPane();
		noteLayout = new ScrollPane();
		
		keyboardListener = new KeyboardListener();
		buttonListener = new ButtonListener();
		
		displayAssetHandler = new DisplayAssetHandler();
		windowHandler = new WindowHandler();
		tabHandler = new TabHandler();
		imageHandler = new ImageHandler();
		rwfile = new RWFile();
		
		notes = new ArrayList<Note>();
		
		noteText = new TextArea();
	}
	
	public Note getNoteByUUID(UUID uuid)
	{
		for(Note note : notes)
		{
			if(note.uuid.equals(uuid))
			{
				return note;
			}
		}
		return null;
	}
	
	public ArrayList<Note> getNotesFromFiles(ArrayList<Note> notes)
	{
		File folder = new File("/Users/mattbcool/Desktop/notes");
		File[] listOfFiles = folder.listFiles();

		if(listOfFiles != null)
		{
			for(int i = 0; i < listOfFiles.length; i++)
			{
			  if(listOfFiles[i].isFile())
			  {
				String name = "New Note";
				String uuid = listOfFiles[i].getName().replaceAll(".txt", "");
				if(uuid.length() >= 36)
				{
					ArrayList<String> text = Main.dataHandler.rwfile.readFromFile("/Users/mattbcool/Desktop/notes/" + uuid + ".txt");
					if(text.size() > 0)
					{
						for(int lines = 0; lines < text.size(); lines++)
						{
							if(lines < 1)
							{
								name = text.get(lines).replace("\n", "");
								text.remove(lines);
							}
						}
					}
				    notes.add(new Note(name, UUID.fromString(uuid), text));
				}
			  }
			  else if(listOfFiles[i].isDirectory())
			  {
				  //TODO ADD FOLDER SUPPORT
			    System.out.println("Directory " + listOfFiles[i].getName());
			  }
			}
		}
		return notes;
	}
	
	public void saveNotes()
	{
		for(Note note : notes)
		{
			note.setupFileWrite();
		}
	}
	
	public void saveNote(Note note)
	{
		System.out.println("Saving " + note.name);
		note.noteText = new ArrayList<String>();
    	for(String text : noteText.getText().split("\n"))
    	{
    		note.noteText.add(text);
    	}
    	note.setupNotePreview();
	}
	
	public void selectNoteToView(Note note)
	{
		displayedNote = note.uuid;
		String str = "";
		for(String text : note.noteText)
		{
			str += text + "\n";
		}
		noteText.setText(str);
		displayingNote = true;
		windowHandler.setupNoteScene(new ScrollPane());
	}
}
