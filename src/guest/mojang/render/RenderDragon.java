package guest.mojang.render;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import guest.mojang.render.screen.GameWorldScreen;
import guest.mojang.render.screen.MenuScreen;
import net.ultraminecraft.config.GameConfig;
import net.ultraminecraft.config.Workspace;
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
		} else if (ScreenResource.getActiveWindow().equals(ScreenResource.GAME_WORLD)) {
			new GameWorldScreen(frame).render(g);
		}
	}

	public void start() {
		frame.setMinimumSize(GameConfig.DEFAULT_RESOLUTION);
		frame.setPreferredSize(GameConfig.DEFAULT_RESOLUTION);
		frame.setSize(GameConfig.DEFAULT_RESOLUTION);
		frame.setIconImage(bindTexture("\\gui\\release_icon.png"));
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
			image = ImageIO.read(new File(Workspace.ASSETS_DIRECTORY + "\\minecraft\\textures" + filepath));
		} catch (IOException ignored) {
			InputStream in = new ByteArrayInputStream(Resource.MISSING_TEXTURE);
			try {
				image = ImageIO.read(in);
			} catch (IOException GGForYOU) {
				CrashReport.launchCorruptionMessage();
			}
		}

		return image;
	}

	public static Image bindExternalTexture(String filepath) {
		Image image = null;
		try {
			image = ImageIO.read(new File(filepath));
		} catch (IOException ignored) {
			InputStream in = new ByteArrayInputStream(Resource.MISSING_TEXTURE);
			try {
				image = ImageIO.read(in);
			} catch (IOException GGForYOU) {
				CrashReport.launchCorruptionMessage();
			}
		}

		return image;
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
