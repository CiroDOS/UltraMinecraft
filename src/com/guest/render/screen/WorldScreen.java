package com.guest.render.screen;

import static com.guest.render.RenderDragon.bindTexture;

import java.awt.Graphics;

import javax.swing.JFrame;

import net.ultraminecraft.util.Resource;

public class WorldScreen extends Screen {

	public static final Resource RESOURCE = Resource.get("game_world");
	
	public WorldScreen(JFrame frame) {
		super(frame);
	}

	@Override
	public void render(Graphics g) {
		
		for (int i = 0; i < frame.getWidth(); i += 128) {
			for (int j = 0; j < frame.getHeight(); j += 128) {
				g.drawImage(bindTexture("\\gui\\background.png"), i, j, 128, 128, null);
			}
		}
		
		
	}

}
