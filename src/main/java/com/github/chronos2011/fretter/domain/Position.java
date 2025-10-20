package com.github.chronos2011.fretter.domain;

/**
 * Class Position defines a position of a fretboard.
 */
public class Position {
	/** Constant indicating an undefined index */
	public static final int INDEX_UNDEFINED = -1;
	/** Flag indicating whether the string index is fixed, i.e set */
	public final boolean stringFixed;
	/** Index of the string */
	public final int stringIndex;
	/** Index of the fret */
	public final int fretIndex;

	/**
	 * Creates a new Position with a fret index only, i.e. the string is not fixed.
	 *
	 * @param fretIndex the index of the fret
	 */
	public Position(int fretIndex) {
		this.stringFixed = false;
		this.stringIndex = INDEX_UNDEFINED;
		this.fretIndex = fretIndex;
	}

	/**
	 * Creates a new Position with a string and fret index.
	 *
	 * @param stringIndex the index of the string
	 * @param fretIndex the index of the fret
	 */
	public Position(int stringIndex, int fretIndex) {
		this.stringFixed = true;
		this.stringIndex = stringIndex;
		this.fretIndex = fretIndex;
	}

	@Override
	public boolean equals(Object object) {
		if (object.getClass() != Position.class)
			return false;
		Position other = (Position) object;
		if (other.stringFixed != this.stringFixed)
			return false;
		if (other.stringIndex != this.stringIndex)
			return false;
		if (other.fretIndex != this.fretIndex)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(stringIndex);
		builder.append(", ");
		builder.append(fretIndex);
		builder.append(")");
		return builder.toString();
	}
}
