package net.ultraminecraft.block;

import net.ultraminecraft.item.Item;

public abstract class Block {

	protected final String texture;
	protected final Material material;
	protected final boolean ore;
	protected final Item item;
	protected final Block[] canBePlacedIn;
	protected final Block[] cantBePlacedIn;
	protected float x;
	protected float y;
	protected float z;

	protected Block(String texture, Material material, boolean ore, Block[] canBePlacedIn, Block[] cantBePlacedIn,
			Item item) {
		this.texture = texture;
		this.material = material;
		this.ore = ore;
		this.canBePlacedIn = canBePlacedIn;
		this.cantBePlacedIn = cantBePlacedIn;
		this.item = item;
	}

	public final String getTexture() {
		return texture;
	}

	public final Material getMaterial() {
		return material;
	}

	public final boolean isOre() {
		return ore;
	}

	public final Item getItem() {
		return item;
	}

	public final Block[] getCanBePlacedIn() {
		return canBePlacedIn;
	}

	public final Block getCanBePlacedInByIndex(int index) {
		return canBePlacedIn[index];
	}

	public final Block[] getCantBePlacedIn() {
		return cantBePlacedIn;
	}

	public final Block getCantBePlacedInByIndex(int index) {
		return cantBePlacedIn[index];
	}

	public final void setX(float x) {
		this.x = x;
	}

	public final void setY(float y) {
		this.y = y;
	}

	public final void setZ(float z) {
		this.z = z;
	}

	public final float getX() {
		return x;
	}

	public final float getY() {
		return y;
	}

	public final float getZ() {
		return z;
	}

	public abstract void onDestroy(Item item);

}
