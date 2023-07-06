package net.ultraminecraft.entity;

public abstract class Entity {

	public final float maxhealth;
	public float health = 1.0f;
	public FriendshipLevel friendshipLvl;
	public float damageLevel = 0.1f;
	
	private String denominator;
	private final String type;
	
	public Entity(String name, String type, float maxhealth) {
		if (maxhealth < 1.0f)
			throw new IllegalStateException("health cannot be minor to one");
		
		if (name != null)
			this.denominator = name;
		else
			this.denominator = type;
		
		this.maxhealth = maxhealth;
		this.type = type;
	}
	
	enum FriendshipLevel {
		PEACEFUL, NEUTRAL, ENEMY
	}
	
	public void onAttack(Entity entity, float damage, String when) {
		if (damage > health) {
			boolean existsWhenCondition = !when.isEmpty() || when == null;
			StringBuilder deathMessage = new StringBuilder();
			
			deathMessage.append(String.format("%s has been killed by %s", this.getDenomination(), entity.getDenomination()));
			
			if (existsWhenCondition)
				deathMessage.append(String.format(" when %s", when));
					
			kill(deathMessage.toString());
		}
	}
	
	private String getDenomination() {
		return this.denominator == null ? this.type : this.denominator;
	}

	public void kill(String deathMessage) {
		health = 0;
		
	}
	
	public float damage(Entity target) {
		if (friendshipLvl.equals(FriendshipLevel.PEACEFUL)) return 0.0f;
		target.onAttack(this, damageLevel, null);
		
		return this.damageLevel;
	}

	public abstract void onTick();
	
}
