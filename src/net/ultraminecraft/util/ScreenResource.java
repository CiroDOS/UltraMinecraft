package net.ultraminecraft.util;

import com.guest.render.screen.WorldScreen;
import com.guest.render.screen.MenuScreen;
import com.guest.render.screen.Screen;

public final class ScreenResource extends Resource {

	public static final ScreenResource MENU_SCREEN = ScreenResource.get(1, "menu_screen", true);
	public static final ScreenResource WORLD_SCREEN = ScreenResource.get(2, "game_world", true);

	private static ScreenResource activeWindow = MENU_SCREEN;

	private final int id;
	private final String res_name;
	private final String origin = "minecraft";
	private final boolean enabled;

	private ScreenResource(int id, String res_name, boolean enabled) {
		this.id = id;
		this.res_name = res_name;
		this.enabled = enabled;
	}

	public static ScreenResource get(int id, String res_name, boolean enabled) {
		return new ScreenResource(id, res_name, enabled);
	}

	public static ScreenResource getActiveWindow() {
		return activeWindow;
	}

	public static void setActiveWindow(ScreenResource w) {
		if (w == null || w.id == 0) {
			throw new IllegalArgumentException("'w' cannot be null or have a null id");
		}
		activeWindow = w;
	}

	public int getID() {
		return this.id;
	}

	public boolean isEnabled() {
		return this.enabled;
	}
	
	
	public Resource toResource() {
		return Resource.get(origin, res_name);
	}

	public static ScreenResource fromResource(Resource resource) {
		return switch(resource.toString()) {
			case "minecraft:game_world" -> ScreenResource.WORLD_SCREEN;
			case "minecraft:menu_screen" -> ScreenResource.MENU_SCREEN;
			default -> null;
		};
	}
	
	public static ScreenResource toResource(Screen screen) {
		if (screen instanceof WorldScreen)
			return fromResource(WorldScreen.RESOURCE);
		
		if (screen instanceof MenuScreen)
			return fromResource(MenuScreen.RESOURCE);
		
		return null;
	}
}
