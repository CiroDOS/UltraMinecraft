package guest.mojang.render.screen;

import static guest.mojang.render.RenderDragon.bindTexture;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Calendar;

import javax.swing.JFrame;

import guest.mojang.render.RenderDragon;
import net.ultraminecraft.config.GameConfig;
import net.ultraminecraft.util.Resource;

public class MenuScreen extends Screen {

	private final Image logo = bindTexture(
			"\\gui\\title\\" + ((GameConfig.LOGO_ID == 5) ? "minceraft" : "minecraft") + ".png");

	public static final Resource RESOURCE = Resource.get("menu_screen");
	
	public MenuScreen(JFrame frame) {
		super(frame);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);

		if (!GameConfig.UNICODE_FONT)
			g.setFont(new Font("Consolas", 0, 16));

		for (int i = 0; i < frame.getWidth(); i += 128) {
			for (int j = 0; j < frame.getHeight(); j += 128) {
				g.drawImage(RenderDragon.bindTexture("\\gui\\dark_dirt_background.png"), i, j, 128, 128, null);
			}
		}

		g.drawImage(bindTexture("\\gui\\button.png"), (frame.getWidth() - 400) / 2, frame.getHeight() / 2 - 50, 400, 45,
				null);

		g.drawImage(bindTexture("\\gui\\button.png"), (frame.getWidth() - 400) / 2, frame.getHeight() / 2, 400, 45,
				null);

		g.drawImage(logo, (frame.getWidth() - 700) / 2, (GameConfig.DEFAULT_RESOLUTION.height > frame.getHeight()) ? 10 : 30,
				700, 200, null);

		g.setColor(Color.WHITE);

		String copyright = "Copyright (C) 2023 Ciro Diaz";

		if (Calendar.getInstance().get(1) != 2023) {
			copyright = String.format("Copyright (C) 2023-%i Ciro Diaz",
					new Object[] { Integer.valueOf(Calendar.getInstance().get(1)) });
		}
		g.drawString(copyright, frame.getWidth() - 300, frame.getHeight() - 70);
		g.drawString(GameConfig.NAME + ' ' + GameConfig.VERSION + ' ' + GameConfig.VERSION_TYPE.toString(), 15, frame.getHeight() - 70);

		g.setFont(new Font("Consolas", 0, 20));
		g.drawString("Singleplayer", frame.getWidth() / 2 - 70, frame.getHeight() / 2 - 23);
		g.drawString("Multiplayer", frame.getWidth() / 2 - 70, frame.getHeight() / 2 + 27);

	}

}
