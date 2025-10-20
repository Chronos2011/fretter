package com.github.chronos2011.fretter.domain;

/**
 * Class FretWindow defines frets of the board to be considered.
 */
public class FretWindow {
	/** Index of the first fret to consider */
	public int start;
	/** Index of the last fret to consider */
	public int end;
	/** Flag indicating whether open strings should be considered */
	public boolean includeOpen;

	public void extendBy(FretWindow other) {
		if (other.includeOpen)
			this.includeOpen = true;
		if (other.start < this.start)
			this.start = other.start;
		if (other.end > this.end)
			this.end = other.end;
	}
}
