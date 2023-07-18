package net.ultraminecraft.world;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sibermatica.util.logging.Logger;

import com.guest.sound.Sound;

import net.ultraminecraft.config.Workspace;
import net.ultraminecraft.entity.Entity;
import net.ultraminecraft.util.Resource;
import net.ultraminecraft.util.ScreenResource;

public class Level {

	private List<Entity> entities = new ArrayList<>();
	private static final Logger LOGGER = new Logger(
			"[${HOUR_24}:${MINUTE}:${SECOND}] [${CLASS_SIMPLE_NAME}/$Upper{LOGGING_LEVEL}]: ${MESSAGE}");

	private static Level currentLevel = null;
	private boolean openInLAN = false;
	private GameRules gameRules = GameRules.defaultConfig();

	private ObjectOutputStream levelWriter;
	private ObjectInputStream levelReader;

	private ByteArrayOutputStream chat = new ByteArrayOutputStream();
	private ByteArrayOutputStream old_chat = chat;

	public static final float GRAVITY = 0.3f; // TODO: Test the gravity
	public static final String WORLD_EXTENSION = ".epk"; // TODO: Test the gravity

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

	public Level(String name) {
		try {
			levelWriter = new ObjectOutputStream(
					new FileOutputStream(Workspace.WORLD_DIRECTORY + "\\" + name + WORLD_EXTENSION));
			levelReader = new ObjectInputStream(
					new FileInputStream(Workspace.WORLD_DIRECTORY + "\\" + name + WORLD_EXTENSION));
		} catch (IOException ignored) {
		}

		ScreenResource.setActiveWindow(ScreenResource.WORLD_SCREEN);
		Level.currentLevel = this;
	}

	public Level(String name, GameRules gameRules) {
		this(name);
		this.gameRules = gameRules;
	}

	public static Level create(String name) {
		return new Level(name);
	}

	public static Level create(String name, GameRules gameRules) {
		return new Level(name, gameRules);
	}

	public static Level currentLevel() {
		return !ScreenResource.getActiveWindow().equals(ScreenResource.WORLD_SCREEN) ? null : Level.currentLevel;
	}

	public void save() {
		try {
			levelWriter.writeObject(Level.currentLevel);
			close();
		} catch (IOException e) {
			LOGGER.error("Cannot save world because: " + e.getMessage(), Level.class);
		}
	}

	public static void open(String path) throws IOException {

		// Create & Configure World
		try {
			// Set world streams
			Level.currentLevel().levelWriter = new ObjectOutputStream(new FileOutputStream(path));
			Level.currentLevel().levelReader = new ObjectInputStream(new FileInputStream(path));

			Level.currentLevel = (Level) Level.currentLevel().levelReader.readObject();

		} catch (ClassNotFoundException ignored) {
			LOGGER.error("Cannot create world or open world file.", Level.class);
		}

	}

	public boolean addEntity(Entity entity) {
		return entities.add(entity);
	}

	public boolean removeEntity(Entity entity) {
		return entities.remove(entity);
	}

	public Entity nearestEntity(float x, float y, float z) {
		return nearEntities(x, y, z)[0];
	}

	public Entity[] nearEntities(float x, float y, float z) {

		Map<Float, Entity> entityMap = new HashMap<>();
		List<Float> scores = new ArrayList<>();

		for (int i = 0; i < entities.size(); i++) {

			Entity currentEntity = entities.get(i);

			float distance_x = currentEntity.getX() - x;
			float distance_y = currentEntity.getY() - y;
			float distance_z = currentEntity.getZ() - z;

			float score = distance_x + distance_y + distance_z;
			entityMap.put(score, currentEntity);
			scores.add(score);
		}

		Object[] parsed_scores = scores.toArray();
		Arrays.sort(parsed_scores, Collections.reverseOrder());

		List<Entity> entityList = new ArrayList<>();

		for (Object score : parsed_scores)
			entityList.add(entityMap.get((float) score));

		return entityList.toArray(new Entity[] {});
	}

	public Entity entityAt(float x, float y, float z) {

		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.getX() == x && entity.getY() == y && entity.getZ() == z) {
				return entity;
			}
		}

		return null;
	}

	public void playSound(String strSoundResource, float x, float y, float z, double pitch, boolean normal) {
		Resource sound_res = Resource.get(strSoundResource.split(":")[0], strSoundResource.split(":")[1]);
		try {
			// Sound founder
			FileInputStream fis = new FileInputStream(switch(sound_res.toString()) {
				default -> "";
			});
			
			boolean errorCode = false;
			
			while(!normal && errorCode)
				errorCode = !new Sound(fis).calculateByEntities(entities).playSound(pitch);
		} catch(IOException e) {
			LOGGER.error("Cannot found sound " + sound_res.toString(), Level.class);
		}
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

		if (chat.toByteArray().length != 0)
			LOGGER.info("[CLIENT] " + chat.toString(), Level.class);

		try {
			old_chat.write(chat.toByteArray());
		} catch (IOException ignored) {
		}

		chat = new ByteArrayOutputStream();
	}

	public PrintStream getChat() {
		return new PrintStream(chat);
	}

	public void close() throws IOException {
		// Unbound all i/o streams

		levelReader.close();
		levelWriter.close();

		levelReader = null;
		levelWriter = null;
		Level.currentLevel = null;

		// Switch screen
		ScreenResource.setActiveWindow(ScreenResource.MENU_SCREEN);
	}

}
