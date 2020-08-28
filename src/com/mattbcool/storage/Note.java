package com.mattbcool.storage;

import java.util.ArrayList;
import java.util.UUID;

import com.mattbcool.main.Main;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Note
{
	public UUID uuid;
	public String name;
	public ArrayList<String> noteText;
	
	public Button noteButton;
	public ImageView noteImage = new ImageView(Main.dataHandler.imageHandler.stickyNote);
	public Text titleBox;
	
	public Note(String name, UUID uuid, ArrayList<String> noteText)
	{
		this.uuid = uuid;
		this.noteText = noteText;
		setName(name);
		setupNotePreview();
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setupFileRead()
	{
		noteText = Main.dataHandler.rwfile.readFromFile("/Users/mattbcool/Desktop/notes/" + uuid.toString() + ".txt");
	}
	
	public void setupFileWrite()
	{
		ArrayList<String> data = new ArrayList<String>();
		data.add(name);
		data.addAll(noteText);
		Main.dataHandler.rwfile.writeToFile("/Users/mattbcool/Desktop/notes/" + uuid.toString() + ".txt", data);
	}
	
	public void setupNotePreview()
	{
		//noteButton = new Button("", new ImageView(Main.dataHandler.imageHandler.stickyNote));
		String preview = "";
		if(noteText.size() > 0)
		{
			int size = noteText.get(0).length();
			if(size >= 8)
			{
				size = 8;
			}
			preview = noteText.get(0).substring(0, size);
		}
		titleBox = new Text(name + "\n \n \n" + preview);
		titleBox.setFont(Font.font("Calibri", FontWeight.BOLD, Main.dataHandler.displayAssetHandler.getConvertedFontSize(12)));
	}
}
