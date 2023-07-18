package net.ultraminecraft.client;

import org.sibermatica.util.logging.Logger;

import com.guest.render.RenderDragon;

import net.ultraminecraft.config.GameConfig;
import net.ultraminecraft.world.Level;

public class Main implements Runnable {

	private static RenderDragon RenderSYSTEM = new RenderDragon();
	private static final Logger LOGGER = new Logger(
			"[${HOUR_24}:${MINUTE}:${SECOND}] [${CLASS_SIMPLE_NAME}/$Upper{LOGGING_LEVEL}]: ${MESSAGE}");

	private Main() {
	}

	public static void launch(String[] args) {
		GameConfig.parseArguments(args);
		new Main().run();
	}

	public static void main(String[] args) {
		Main.launch(args);
	}

	@Override
	public void run() {
		boolean running = true;

		RenderSYSTEM.start();

		if (GameConfig.DEBUG_MODE) {
			LOGGER.debug("[Render Thread] Logo renderized minecraft:logo/"
					+ (GameConfig.LOGO_ID == 5 ? "minceraft.png" : "minecraft.png"), RenderDragon.class);
			LOGGER.technical("[Render Thread] Logo ID - " + GameConfig.LOGO_ID, RenderDragon.class);
		}

		while (running) {
			tick();
			RenderSYSTEM.render();
		}

	}

	private void tick() {
		if (Level.currentLevel() != null)
			Level.currentLevel().onTick();
			
	}

}
