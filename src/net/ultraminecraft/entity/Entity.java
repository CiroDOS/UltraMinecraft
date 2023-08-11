package net.ultraminecraft.entity;

import net.ultraminecraft.world.Direction;
import net.ultraminecraft.world.Level;

public abstract class Entity {

	public static final float DEFAULT_VELOCITY = 0.0f;
	
	protected final float maxhealth;
	protected final float height;
	protected float health = 1.0f;
	protected float damageLevel = 0.1f;
	
	protected FriendshipLevel friendshipLvl = FriendshipLevel.NEUTRAL;
	protected boolean canBeDomesticated = false;
	protected boolean domesticated = false;

	protected float x = 0.0f;
	protected float y = 0.0f;
	protected float z = 0.0f;

	protected boolean falling = false;
	protected boolean destroyed;
	protected Direction direction = Direction.NORTH;

	protected float velocity = Entity.DEFAULT_VELOCITY;

	private String denominator;
	private final String type;

	public Entity(String name, String type, float maxhealth, float height) {
		if (name != null)
			this.denominator = name;
		else
			this.denominator = type;

		this.maxhealth = maxhealth;
		this.type = type;
		this.height = height;
	}

	enum FriendshipLevel {
		PEACEFUL, NEUTRAL, ENEMY
	}

	protected final void onAttack(Entity entity, float damage, String when) {
		if (damage > health) {
			boolean existsWhenCondition = !when.isEmpty() || when == null;
			StringBuilder deathMessage = new StringBuilder();

			deathMessage.append(
					String.format("%s has been killed by %s", this.getDenomination(), entity.getDenomination()));

			if (existsWhenCondition)
				deathMessage.append(String.format(" when %s", when));

			kill(deathMessage.toString());
		}
	}

	public final String getDenomination() {
		return this.denominator == null ? this.type : this.denominator;
	}

	public final void kill(String deathMessage) {
		setHealth(0.0f);
		Level.currentLevel().getChat().println(deathMessage);
	}

	public float damage(Entity target) {
		if (friendshipLvl.equals(FriendshipLevel.PEACEFUL))
			return 0.0f;
		target.onAttack(this, damageLevel, null);

		return this.damageLevel;
	}
	
	public final void setHealth(float health) {
		this.health = health;
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
	
	public final void setCoordinates(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public final void destroy() {
		Level.currentLevel().removeEntity(this);
		destroyed = true;
	}
	
	public final float getVelocity() {
		return velocity;
	}
	
	public final boolean isFalling() {
		return falling;
	}

	public final void startFalling() {
		this.falling = true;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public Direction facingAt() {
		return direction;
	}
	
	public void faceAt(Direction direction) {
		this.direction = direction;
	}

	public abstract void onTick();
	public abstract void inGround();

}
