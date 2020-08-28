package com.mattbcool.listeners;

import java.util.ArrayList;
import java.util.UUID;

import com.mattbcool.main.Main;
import com.mattbcool.storage.Note;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ButtonListener implements EventHandler<ActionEvent>
{
	@Override
	public void handle(ActionEvent event)
	{
		Object src = event.getSource();
		if(src.equals(Main.dataHandler.windowHandler.addButton))
		{
			Main.dataHandler.notes.add(new Note("New Note", UUID.randomUUID(), new ArrayList<String>()));
			Main.dataHandler.windowHandler.resetMainView();
		}
		for(Note note : Main.dataHandler.notes)
		{
			if(src.equals(note.noteButton))
			{
				Main.dataHandler.selectNoteToView(note);
				return;
			}
		}
	}
}
