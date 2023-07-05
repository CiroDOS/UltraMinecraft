package net.ultraminecraft.config;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.sibermatica.jah.util.OptionParser;

import net.ultraminecraft.crash.CrashReport;

public class GameConfig {

	public  static final String                 NAME                    = "UltraMinecraft";
	public  static final String                 VERSION                 = "1.0";
	public  static final GameConfig.VersionType VERSION_TYPE            = VersionType.INDEV;
	public  static final Dimension              DEFAULT_RESOLUTION      = new Dimension(854, 530);
	public  static final int                    LOGO_ID                 = (int) (Math.random() * 100);

	public  static       boolean                UNICODE_FONT            = false;
	public  static       boolean                DEBUG_MODE              = false;
	public  static       int                    RENDER_DISTANCE         = 4;
	public  static       boolean                FML_FORGE;
	public  static       Locale                 LANGUAGE                = Locale.ROOT;

	private static final List<String>           EXPORTED_CONFIGURATIONS = new ArrayList<>();
	private static final List<String>           IMPORTED_CONFIGURATIONS = new ArrayList<>();

	
	static {
		exports("minecraft:render_distance");
		exports("minecraft:language");
		
		imports("minecraft:render_distance");
		imports("minecraft:language");
	}
	
	
	
	public static void parseArguments(String[] args) {
		OptionParser optionParser  = OptionParser.format("-", "--").parse(args);

		GameConfig.DEBUG_MODE      = optionParser.has("debug");
		GameConfig.FML_FORGE       = !optionParser.has("fmlForge");

		Workspace.GAME_DIRECTORY   = (String) optionParser.getOrDefault("gameDir", ".");
		Workspace.ASSETS_DIRECTORY = (String) optionParser.getOrDefault("assetsDir",
				Workspace.GAME_DIRECTORY + "\\assets");
		
		String pathConfigFile = Workspace.GAME_DIRECTORY
				+ (GameConfig.FML_FORGE ? "\\settings.yml" : "\\options.yml");
		
		File configFile = new File(pathConfigFile);
		
		try {
			if (new File(pathConfigFile).exists()) {
				FileInputStream stream = new FileInputStream(pathConfigFile);
				GameConfig.readConfigFile(new String(stream.readAllBytes()));
				stream.close();
			} else {
				configFile.createNewFile();
				PrintStream stream = new PrintStream(configFile);
				stream.print(GameConfig.writeConfigFile());
				stream.close();
			}
		} catch (IOException ignored) {
			CrashReport.launchCorruptionMessage();
		}
	
	}
	
	public static enum VersionType {
		RELEASE("release"), INDEV("indev"), ALPHA("alpha"), BETA("beta"), PRECLASSIC("pre-classic"),
		SNAPSHOT("snapshot");

		private String name;

		VersionType(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}

		public VersionType fromString(String str) {
			return switch (str.toUpperCase()) {
			case "RELEASE" -> VersionType.RELEASE;
			case "INDEV" -> VersionType.INDEV;
			case "ALPHA" -> VersionType.ALPHA;
			case "BETA" -> VersionType.BETA;
			case "PRE-CLASSIC" -> VersionType.PRECLASSIC;
			case "SNAPSHOT" -> VersionType.SNAPSHOT;
			default -> null;
			};
		}

	}

	public static void readConfigFile(String configFile) {

		for (int lines = 0; lines < configFile.split("\n").length; lines++) {
			String currentLine = configFile.split("\n")[lines];
			String property = currentLine.split(":")[0].trim();
			String value = currentLine.split(":")[1].trim();

			switch (property) {
				case "render.distance" -> {
					if (GameConfig.IMPORTED_CONFIGURATIONS.contains("minecraft:render_distance")) {
						try {
							RENDER_DISTANCE = Integer.parseInt(value);
						} catch (Exception ignored) {
							RENDER_DISTANCE = 4;
						}
					}
				}

				case "language" -> {
					if (GameConfig.IMPORTED_CONFIGURATIONS.contains("minecraft:language")) {
						LANGUAGE = Locale.forLanguageTag(value);
					}
				}
			}
		}

	}

	public static String writeConfigFile() {
		StringBuilder file_contents = new StringBuilder();

		if (GameConfig.EXPORTED_CONFIGURATIONS.contains("minecraft:render_distance")) {
			file_contents.append("render.distance: ");
			file_contents.append(RENDER_DISTANCE);
			file_contents.append("\n");
		}

		if (GameConfig.EXPORTED_CONFIGURATIONS.contains("minecraft:language")) {
			file_contents.append("language: ");
			file_contents.append(LANGUAGE.toLanguageTag());
		}
		
		return file_contents.toString();
	}
	
	public static boolean exports(String configuration_id) {
		GameConfig.EXPORTED_CONFIGURATIONS.add(configuration_id);
		return true;
	}

	public static boolean imports(String configuration_id) {
		GameConfig.IMPORTED_CONFIGURATIONS.add(configuration_id);
		return true;
	}

}
