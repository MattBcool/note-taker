package com.mattbcool.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener
{
	@Override
	public void keyTyped(KeyEvent e)
	{
		System.out.println("Atleast firing");
		if(e.getKeyChar() == KeyEvent.VK_ESCAPE)
		{
			System.out.println("Attempting to undisplay");
			
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}

}
