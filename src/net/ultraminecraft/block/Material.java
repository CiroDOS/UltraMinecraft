package net.ultraminecraft.block;

public enum Material {
	STONE(1.2f, 1.5f), NETHERITE(3.0f, 10.0f), METAL(2.0f, 6.0f);

	private float weight;
	private float breakDelay;

	Material(float weight, float breakDelay) {
		this.weight = weight;
		this.breakDelay = breakDelay;
	}

	public float getWeight() {
		return weight;
	}
	
	public float getDelay() {
		return breakDelay;
	}
}
