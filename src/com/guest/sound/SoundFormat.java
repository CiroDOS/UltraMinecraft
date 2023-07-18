package com.guest.sound;

public class SoundFormat {
	
	public static final SoundFormat WAV = new SoundFormat(SoundHeaders.WAV);
	public static final SoundFormat OGG = new SoundFormat(SoundHeaders.OGG);
	public static final SoundFormat MP3 = new SoundFormat(SoundHeaders.MP3);
	
	public static final byte[] NULL = new byte[] {0x00};
	
	private byte[] header;
	
	public SoundFormat(byte[] header) {
		this.header = header;
	}
	
	public byte[] getHeader() {
		return header;
	}
	
	public byte[] format(byte[] audio_contents) {
		if (!new String(audio_contents).startsWith(new String(header)))
			throw new IllegalArgumentException("The audio isn't valid!");
		
		return new String(audio_contents, header.length, audio_contents.length).getBytes();
	}
}
