package net.ultraminecraft.entity;

public class ArrowEntity extends Entity {

	public ArrowEntity() {
		super(null, "Arrow", -1.0f);
	}
	
	@Override
	public void onTick() {
		// TODO: Calculate arrow direction
	}
	
	public void shoot(float x, float y, float z, float firepower) {
		float time = 16;
	}

}
