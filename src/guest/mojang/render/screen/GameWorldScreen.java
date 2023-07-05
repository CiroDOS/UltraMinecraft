package guest.mojang.render.screen;

import static guest.mojang.render.RenderDragon.bindTexture;

import java.awt.Graphics;

import javax.swing.JFrame;

import net.ultraminecraft.util.Resource;

public class GameWorldScreen extends Screen {

	public static final Resource RESOURCE = Resource.get("game_world");
	
	public GameWorldScreen(JFrame frame) {
		super(frame);
	}

	@Override
	public void render(Graphics g) {
		
		for (int i = 0; i < frame.getWidth(); i += 128) {
			for (int j = 0; j < frame.getHeight(); j += 128) {
				g.drawImage(bindTexture("\\gui\\dark_dirt_background.png"), i, j, 128, 128, null);
			}
		}
		
		
	}

}
