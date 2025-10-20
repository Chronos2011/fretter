package com.github.chronos2011.fretter.domain.library;

/**
 * Enumeration Degree enumerates the scale degrees used in the application.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Degree_(music)">Degree</a>
 */
public enum Degree {
	OUT(-1, "OUT"),
	D01(0, "I"),
	D02(1, "II"),
	D03(2, "III"),
	D04(3, "IV"),
	D05(4, "V"),
	D06(5, "VI"),
	D07(6, "VII"),
	D08(7, "VIII"),
	D09(8, "IX"),
	D10(9, "X"),
	D11(10, "XI"),
	D12(11, "XII");

	/** Chromatic index of the Degree */
	public final int index;
	/** Human-readable symbol of the Degree */
	public final String symbol;

	private Degree(int index, String symbol) {
		this.index = index;
		this.symbol = symbol;
	}

	/**
	 * Returns the Degree for its index.
	 *
	 * @param index the unique index of the Degree
	 * @return the Degree matching the index or null
	 */
	public static Degree from(int index) {
		for (Degree degree : Degree.values())
			if (degree.index == index)
				return degree;
		return null;
	}

	/**
	 * Raises the Degree by a given summand, respecting the maximum index available (which might be 4 in a pentatonic or
	 * 11 in a chromatic scale).
	 *
	 * @param summand  the number of degrees to be raised
	 * @param maxIndex the maximum degree index available (in context of the Scale being used)
	 * @return the raised Degree
	 */
	public Degree raise(int summand, int maxIndex) {
		int newIndex = (this.index + summand) % maxIndex;
		return Degree.from(newIndex);
	}
}
