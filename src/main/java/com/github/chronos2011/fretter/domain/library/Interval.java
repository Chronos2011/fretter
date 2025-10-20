package com.github.chronos2011.fretter.domain.library;

import com.github.chronos2011.fretter.application.Configuration;

/**
 * Enumeration Interval enumerates the various intervals being used in the application. It does include minor, major,
 * perfect intervals and the tritone only; no augmented, diminished or otherwise exotic intervals are included.
 */
public enum Interval {
	OUT(-1, null, "Out of scale"),
	PER1(0, " 1 ", "Perfect unison"),
	MIN2(1, "\u266D2 ", "Minor second"),
	MAJ2(2, " 2 ", "Major second"),
	MIN3(3, "\u266D3 ", "Minor third"),
	MAJ3(4, " 3 ", "Major third"),
	PER4(5, " 4 ", "Perfect fourth"),
	TRIT(6, "\u266D5 ", "Tritone"),
	PER5(7, " 5 ", "Perfect fifth"),
	MIN6(8, "\u266D6 ", "Minor sixth"),
	MAJ6(9, " 6 ", "Major sixth"),
	MIN7(10, "\u266D7 ", "Minor seventh"),
	MAJ7(11, " 7 ", "Major seventh"),
	PER8(12, " 8 ", "Perfect octave");

	/** Half-steps corresponding to the Interval */
	public final int steps;
	/** Human-readable symbol of the Interval */
	private final String symbol;
	/** Common name of the Interval */
	public final String name;

	private Interval(int steps, String symbol, String name) {
		this.steps = steps;
		this.symbol = symbol;
		this.name = name;
	}

	/**
	 * Returns the Interval corresponding to an amount of half-tone steps.
	 *
	 * @param steps the number of half-tone steps
	 * @return the corresponding Interval
	 */
	public static Interval from(int steps) {
		for (Interval interval : Interval.values())
			if (interval.steps == steps)
				return interval;
		return null;
	}

	/**
	 * Returns the Interval corresponding to a {@link Pitch} difference. Please take note that the order is important:
	 * the distance from the first to the second pitch is classified, so {@code Interval.from(Pitch.C_5, Pitch.G_5)}
	 * will return {@code Interval.PER5}.
	 *
	 * @param pitch0 the start or base {@link Pitch}
	 * @param pitch1 the target {@link Pitch}
	 * @return the Interval corresponding to the difference of the given {@link Pitch}es
	 */
	public static Interval from(Pitch pitch0, Pitch pitch1) {
		return from(Math.floorMod(pitch1.midiIndex - pitch0.midiIndex, 12));
	}

	/**
	 * Returns the human-readable symbol of this Interval.
	 *
	 * @return the human-readable symbol of this Interval
	 */
	public String getSymbol() {
		if (!Configuration.hostIsWindows)
			return symbol;
		else
			return symbol.replace('\u266D', 'b');

	}
}
