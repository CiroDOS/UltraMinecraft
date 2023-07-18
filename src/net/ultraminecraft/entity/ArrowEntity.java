package net.ultraminecraft.entity;

import net.ultraminecraft.world.Level;

public class ArrowEntity extends Entity {

	private float target_x = 0.0f;
	private float target_y = 0.0f;
	private float target_z = 0.0f;
	private boolean mission = false;

	

	public ArrowEntity() {
		super(null, "Arrow", -1.0f, 0.9f);
	}

	@Override
	public void onTick() {
		if (!isDestroyed())
			return;
		
		if (!onMission())
			return;

		if (getVelocity() <= 0.0f) {
			this.mission = false;
			this.falling = false;
			destroy();
		}

		if (isFalling()) {
			velocity -= Level.GRAVITY * getWeight();
		}

		if (x == target_x && y == target_y && z == target_z) {
			Entity target = Level.currentLevel().entityAt(x, y, z);
			
			if (target != null) {
				target.onAttack(this, 5.0f, null);
			}
			
		}

		// Move the arrow around the environment
		for (String axis : facingAt().getAxis().split(";")) {
			switch (axis) {
				case "X+":
					setX(getX() + getVelocity());
					break;
				case "X-":
					setX(getX() - getVelocity());
					break;
				case "Z+":
					setZ(getZ() + getVelocity());
					break;
				case "Z-":
					setZ(getZ() - getVelocity());
					break;
			}
		}
		
		System.out.println("Destroyed: " + (isDestroyed() ? "Yes" : "No"));
		System.out.println();
		System.out.println("X: " + getX() + ", Y: " + getY() + ", Z: " + getZ() + ", V: " + getVelocity());
		
		this.startFalling();
	}

	private boolean onMission() {
		return mission;
	}

	public void inGround() {
		destroy();
	}

	public void shoot(float x, float y, float z, float power) {
		this.target_x = x;
		this.target_y = y;
		this.target_z = z;
		this.mission = true;
		this.velocity = power * ArrowEntity.DEFAULT_VELOCITY;
	}

}
