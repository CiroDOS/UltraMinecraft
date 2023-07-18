package com.guest.render.screen;

import java.awt.Graphics;

import javax.swing.JFrame;

public abstract class Screen {
	
	protected final JFrame frame;
	
	public Screen(JFrame frame) {
		this.frame = frame;
	}
	
	public abstract void render(Graphics g);
	
}
