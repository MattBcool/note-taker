package com.mattbcool.data;

import javafx.scene.image.Image;

public class ImageHandler
{
	public Image stickyNote;

	public ImageHandler()
	{
		stickyNote = new Image(getClass().getResourceAsStream("/res/stickynote.png"));
	}
}
