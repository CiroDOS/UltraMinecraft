package net.ultraminecraft.client;

public class Timer {

	public static final short NANO_SECOND = 1000;
	
	public static int frames;
	public static long time;
	public static long ticks = 0;
	
	public static void tick() {
		frames++;
		if (System.currentTimeMillis() > time + NANO_SECOND) {
			if (ticks == 0)
				System.out.println("60 FPS");
			else
				printFPS();
			
			ticks++;
			time = System.currentTimeMillis();
			frames = 0;
		}
	}

	public static void printFPS() {
		System.out.println(frames + " FPS");
	}
	
}
