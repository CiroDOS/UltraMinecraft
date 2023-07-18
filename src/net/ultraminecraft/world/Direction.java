package net.ultraminecraft.world;

public enum Direction {
	NORTHWEST("X-;Z-"), NORTH("-Z"), NORTHEAST("X+;Z-"), EAST("X+"), SOUTHEAST("Z+;X+"), SOUTH("Z+"), SOUTHWEST("X-;Z+"), WEST("X-");

	private String axis;
	
	Direction(String c) {
		this.axis = c;
	}
	
	public String getAxis() {
		return axis;
	}
}
