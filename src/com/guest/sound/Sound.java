package com.guest.sound;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import net.ultraminecraft.entity.Entity;

public class Sound {

	private final FileInputStream fis;
	private SoundFormat sndFormat = SoundFormat.OGG;

	public Sound(FileInputStream fis) {
		this.fis = fis;
	}

	public Sound(FileInputStream fis, SoundFormat sndFormat) {
		this(fis);
		this.sndFormat = sndFormat;
	}

	public boolean playSound(double pitch) {
		// TODO: Make the sounds play
		try {
			byte[] a = sndFormat.format(fis.readAllBytes());
			int off = 0;
			int len = a.length;
			int keep, k;
			int indexBound = off + len;

			// Find first non-sign (0xff) byte of input
			for (keep = off; keep < indexBound && a[keep] == -1; keep++)
				;

			/*
			 * Allocate output array. If all non-sign bytes are 0x00, we must allocate space
			 * for one extra output byte.
			 */
			for (k = keep; k < indexBound && a[k] == 0; k++)
				;

			int extraByte = (k == indexBound) ? 1 : 0;
			int intLength = ((indexBound - keep + extraByte) + 3) >>> 2;
			int audio_contents[] = new int[intLength];

			/*
			 * Copy one's complement of input into output, leaving extra byte (if it exists)
			 * == 0x00
			 */
			int b = indexBound - 1;
			for (int i = intLength - 1; i >= 0; i--) {
				audio_contents[i] = a[b--] & 0xff;
				int numBytesToTransfer = Math.min(3, b - keep + 1);
				if (numBytesToTransfer < 0)
					numBytesToTransfer = 0;
				for (int j = 8; j <= 8 * numBytesToTransfer; j += 8)
					audio_contents[i] |= ((a[b--] & 0xff) << j);

				// Mask indicates which bits must be complemented
				int mask = -1 >>> (8 * (3 - numBytesToTransfer));
				audio_contents[i] = ~audio_contents[i] & mask;
			}

			// Add one to one's complement to generate two's complement
			for (int i = audio_contents.length - 1; i >= 0; i--) {
				audio_contents[i] = (int) ((audio_contents[i] & 0xffffffffL) + 1);
				if (audio_contents[i] != 0)
					break;
			}

			/*
			 * Some magic process...
			 * 
			 * 
			 */
			fis.close();
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	public boolean playSound() {
		return playSound(0.0d);
	}

	public Sound calculateByEntities(List<Entity> entities) {
		/*
		 * Some magic process... Needs to calculate every entity location for play the
		 * sound in 3D.
		 */
		return this;
	}

}
