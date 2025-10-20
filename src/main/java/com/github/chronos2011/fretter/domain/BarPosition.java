package com.github.chronos2011.fretter.domain;

/**
 * Class BarPosition defines the position of a bar fingering.
 */
public class BarPosition {
	/** Index of the lower string of the bar */
	public final int lowerStringIndex;
	/** Index of the upper string of the bar */
	public final int upperStringIndex;
	/** Index of the fret of the bar */
	public final int fretIndex;

	/**
	 * Creates a new BarPosition.
	 *
	 * @param lowerStringIndex index of the lower string of the bar
	 * @param upperStringIndex index of the higher string of the bar
	 * @param fretIndex        index of the {@link Fret} of the bar
	 */
	public BarPosition(int lowerStringIndex, int upperStringIndex, int fretIndex) {
		this.lowerStringIndex = lowerStringIndex;
		this.upperStringIndex = upperStringIndex;
		this.fretIndex = fretIndex;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(lowerStringIndex);
		builder.append("-");
		builder.append(upperStringIndex);
		builder.append(", ");
		builder.append(fretIndex);
		builder.append(")");
		return builder.toString();
	}
}
