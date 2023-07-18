package net.ultraminecraft.item;

import java.awt.Image;
import java.util.Arrays;

import net.ultraminecraft.block.Material;
import net.ultraminecraft.language.ComponentTranslator;

import static com.guest.render.RenderDragon.*;

public class Item {
	
	public static Item WOODEN_PICKAXE = new Item(ComponentTranslator.translate("minecraft.item:wooden_pickaxe"), null, new Image[] {bindTexture("\\item\\wooden_pickage.png")}, false, true, false, new Material[] {Material.NETHERITE, Material.METAL}, 23);

	protected final String name;
	protected final String[] lore;
	protected final Image[] textures;
	protected final boolean mineral;
	protected final boolean tool;
	protected final boolean unbreakable;
	protected final Material[] cantBreak;
	protected int durability;
	protected final int max_durability;
	
	public Item(String name, String[] lore, Image[] textures, boolean mineral, boolean tool, boolean unbreakable, Material[] cantBreak, int durability, int max_durability) {
		this(name, lore, textures, mineral, tool, unbreakable, cantBreak, max_durability);
		this.durability = durability;
	}
	
	public Item(String name, String[] lore, Image[] textures, boolean mineral, boolean tool, boolean unbreakable, Material[] cantBreak, int max_durability) {
		this.name = name;
		this.textures = textures;
		this.mineral = mineral;
		this.tool = tool;
		this.unbreakable = unbreakable;
		this.cantBreak = cantBreak;
		this.lore = lore;
		this.max_durability = max_durability;
		this.durability = max_durability;
	}
	
	public String getName() {
		return name;
	}
	
	public Image getTextureByIndex(int index) {
		return textures[index];
	}
	
	public Image[] getTextures() {
		return textures;
	}
	
	public boolean isMineral() {
		return mineral;
	}
	
	public boolean isTool() {
		return tool;
	}
	
	public boolean isUnbreakable() {
		return unbreakable;
	}
	
	public Material whatCantBreakByIndex(int index) {
		return cantBreak[index];
	}
	
	public Material[] whatCantBreak() {
		return cantBreak;
	}
	
	public String getLoreByIndex(int index) {
		return lore[index];
	}
	
	public String[] getLores() {
		return lore;
	}
	
	public String getLore() {
		return Arrays.toString(lore);
	}
	
	public String getNBT() {
		return toString();
	}
	
	@Override
	public String toString() {
		return "{ \"display\": {\"name\": \"" + getName() + "\", \"lore\": " + getLore() + "}, \"unbreakable\": " + (isUnbreakable() ? "1b" : "0b") + "}";
	}
}
