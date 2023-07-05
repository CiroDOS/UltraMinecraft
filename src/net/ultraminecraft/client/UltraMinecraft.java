package net.ultraminecraft.client;

import org.sibermatica.util.logging.Logger;

import guest.mojang.render.RenderDragon;
import net.ultraminecraft.config.GameConfig;

public class UltraMinecraft implements Runnable {

	private static RenderDragon RenderSYSTEM = new RenderDragon();
	private static final Logger LOGGER = new Logger(
			"[${HOUR_24}:${MINUTE}:${SECOND}] [${CLASS_SIMPLE_NAME}/$Upper{LOGGING_LEVEL}]: ${MESSAGE}");
	private static final UltraMinecraft INSTANCE = new UltraMinecraft();

	private UltraMinecraft() {
	}

	public static UltraMinecraft getCurrentInstance() {
		return UltraMinecraft.INSTANCE;
	}

	public static void launch(String[] args) {
		GameConfig.parseArguments(args);
		UltraMinecraft.getCurrentInstance().run();
	}

	public static void main(String[] args) {
		UltraMinecraft.launch(args);
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
			this.runStep();
		}

	}

	private void runStep() {
		this.tick();
		RenderSYSTEM.render();
	}

	private void tick() {
	}

}
