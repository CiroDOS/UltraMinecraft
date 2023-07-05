package net.ultraminecraft.world;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.sibermatica.util.logging.Logger;

import net.ultraminecraft.config.Workspace;
import net.ultraminecraft.entity.Entity;
import net.ultraminecraft.util.ScreenResource;

public class World {

	private List<Entity> entities = new ArrayList<>();
	private static final Logger LOGGER = new Logger(
			"[${HOUR_24}:${MINUTE}:${SECOND}] [${CLASS_SIMPLE_NAME}/$Upper{LOGGING_LEVEL}]: ${MESSAGE}");

	private static World currentWorld = null;
	private boolean openInLAN = false;
	private GameRules gameRules = GameRules.defaultConfig();

	private FileOutputStream worldWriter;
	private FileInputStream worldReader;

	public static final String WORLD_EXTENSION = ".epk";

	public static class GameRules {

		public boolean allowCommands = false;
		public boolean announceAdvancements = true;
		public boolean blockExplosionDropDecay = true;
		public boolean commandBlocksEnabled = true;
		public boolean commandBlockOutput = true;
		public int commandModificationBlockLimit = 32768;
		public boolean doDaylightCycle = true;
		public boolean doEntityDrops = true;
		// TODO: Finish game rules and continue optimizing...

		private GameRules() {
		}

		public static GameRules defaultConfig() {
			return new GameRules();
		}

	}

	public World(String name) {
		try {
			worldWriter = new FileOutputStream(Workspace.WORLD_DIRECTORY + "\\" + name + WORLD_EXTENSION);
			worldReader = new FileInputStream(Workspace.WORLD_DIRECTORY + "\\" + name + WORLD_EXTENSION);
		} catch (IOException ignored) {
			LOGGER.error("Cannot create world or open world file.", World.class);
		}

		ScreenResource.setActiveWindow(ScreenResource.GAME_WORLD);
	}

	public World(String name, GameRules gameRules) {
		this(name);
		this.gameRules = gameRules;
	}

	public static World currentWorld() {
		return !ScreenResource.getActiveWindow().equals(ScreenResource.GAME_WORLD) ? null : World.currentWorld;
	}

	public void saveWorld() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(worldWriter);
			oos.writeObject(World.currentWorld);
			oos.close();
		} catch (IOException e) {
			LOGGER.error("Cannot save world.", World.class);
		}
	}

	public static void openWorld(String path) throws IOException {
		// Create World
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
			World.currentWorld = (World) ois.readObject();
			ois.close();
		} catch(ClassNotFoundException ignored) {
			LOGGER.error("Cannot create world or open world file.", World.class);
		}

		// Set world streams
		World.currentWorld().worldReader = new FileInputStream(path);
		World.currentWorld().worldWriter = new FileOutputStream(path);

		
	}

	public boolean addEntity(Entity entity) {
		return entities.add(entity);
	}

	public boolean removeEntity(Entity entity) {
		return entities.remove(entity);
	}

	public boolean isOpenedInLAN() {
		return this.openInLAN;
	}

	public GameRules getGameRules() {
		return this.gameRules;
	}

	public void onTick() {
		for (Entity entity : entities) {
			entity.onTick();
		}
	}

}
