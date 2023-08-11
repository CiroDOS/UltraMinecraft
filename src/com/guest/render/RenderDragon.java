package com.guest.render;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.guest.render.screen.MenuScreen;
import com.guest.render.screen.WorldScreen;

import net.ultraminecraft.config.GameConfig;
import net.ultraminecraft.crash.CrashReport;
import net.ultraminecraft.util.Resource;
import net.ultraminecraft.util.ScreenResource;

public class RenderDragon extends Canvas {
	private static final long serialVersionUID = 1L;
	private static JFrame frame = new JFrame("UltraMinecraft 1.0");

	private boolean started = false;

	public void paint(Graphics g) {
		if (ScreenResource.getActiveWindow().equals(ScreenResource.MENU_SCREEN)) {
			new MenuScreen(frame).render(g);
		} else if (ScreenResource.getActiveWindow().equals(ScreenResource.WORLD_SCREEN)) {
			new WorldScreen(frame).render(g);
		}
	}

	public void start() {
		frame.setMinimumSize(GameConfig.DEFAULT_RESOLUTION);
		frame.setPreferredSize(GameConfig.DEFAULT_RESOLUTION);
		frame.setSize(GameConfig.DEFAULT_RESOLUTION);
		frame.setIconImage(bindTexture("/gui/release_icon.png"));
		frame.setLayout(new BorderLayout());
		frame.add(this);
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
		frame.pack();

		center(frame);
		started = true;
	}

	public static Image bindTexture(String filepath) {
		Image image = null;
		try {
			image = ImageIO.read(RenderDragon.class.getClassLoader().getResourceAsStream("assets/minecraft/textures" + filepath));
		} catch (Exception ignored) {
			InputStream in = new ByteArrayInputStream(Resource.MISSING_TEXTURE);
			try {
				image = ImageIO.read(in);
			} catch (IOException GGForYOU) {
				CrashReport.launchCorruptionMessage();
			}
		}

		return image;
	}

	public static void drawText(Graphics g, String text, int x, int y, int size) {
		final int size_px = size / 3;
		final int fontspace = Math.round(5.5f) * size_px;
		int cx = x;

		int x1 = 0, y1 = 0, x2 = 0, y2 = 0;

		for (char c : text.toCharArray()) {
			switch (c) {
			case 'A':
				x1 = 8;
				y1 = 33;
				x2 = 11;
				y2 = 38;
				break;

			default:
				x1 = 120;
				y1 = 25;
				x2 = 123;
				y2 = 31;
			}

			g.drawImage(bindTexture("/font/ascii.png"), cx, y,
					cx + (x + (x2 - x1)) * size_px, (y + (y2 - y1)) * size_px, x1, y1, x2, y2, frame);
			cx += x + fontspace;

		}
	}

	public Graphics forceGraphics() {
		if (frame.getGraphics() != null)
			return frame.getGraphics();
		if (getGraphics() != null)
			return getGraphics();
		return null;
	}

	public void render() {
		paint(forceGraphics());
	}

	public JFrame getFrame() {
		if (!started)
			throw new IllegalStateException();

		return frame;
	}

	private void center(JFrame window) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((screen.getWidth() - window.getWidth()) / 2.0D);
		int y = (int) ((screen.getHeight() - window.getHeight()) / 2.0D);
		window.setLocation(x, y);
	}
}
