package com.github.chronos2011.fretter.domain.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Enumeration Scale enumerates the various scales available for diagram generation. Programmatically these are the same
 * as as {@link Chord}s, but we keep them apart for domain reasons.
 */
public enum Scale {
	// TODO too many scales have alternative names, we should consider introducing this as a feature
	// TODO it seems that Wikipedia is highly unreliable - look for another source
	CHROMATIC(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11),
	//
	BLUES_NONATONIC(0, 2, 3, 4, 5, 7, 9, 10, 11),
	//
	MAJOR(0, 2, 4, 5, 7, 9, 11),
	MAJOR_HARMONIC(0, 2, 4, 5, 7, 8, 11),
	MINOR_NATURAL(0, 2, 3, 5, 7, 8, 10),
	MINOR_HARMONIC(0, 2, 3, 5, 7, 8, 11),
	MINOR_MELODIC(0, 2, 3, 5, 7, 9, 11),
	//
	IONIAN(0, 2, 4, 5, 7, 9, 11),
	DORIAN(0, 2, 3, 5, 7, 9, 10),
	PHRYGIAN(0, 1, 3, 5, 7, 8, 10),
	LYDIAN(0, 2, 4, 6, 7, 9, 11),
	MIXOLYDIAN(0, 2, 4, 5, 7, 9, 10),
	AEOLIAN(0, 2, 3, 5, 7, 8, 10),
	LOCRIAN(0, 1, 3, 5, 6, 8, 10),
	//
	MAJOR_HEXATONIC(0, 2, 4, 5, 7, 9),
	MINOR_HEXATONIC(0, 2, 3, 5, 7, 10),
	//
	MAJOR_PENTATONIC(0, 2, 4, 7, 9),
	SUSPENDED_PENTATONIC(0, 2, 5, 7, 10),
	MINOR_BLUES_PENTATONIC(0, 3, 5, 8, 10),
	MAJOR_BLUES_PENTATONIC(0, 2, 5, 7, 9),
	MINOR_PENTATONIC(0, 3, 5, 7, 10),
	//
	MINOR_BLUES_HEXATONIC(0, 3, 5, 6, 7, 10),
	MAJOR_BLUES_HEXATONIC(0, 2, 3, 4, 7, 9),
	//
	BEBOP_DOMINANT(0, 2, 4, 5, 7, 9, 10, 11),
	BEBOP_MAJOR(0, 2, 4, 5, 7, 8, 9, 11),
	BEBOP_MELODIC_MINOR(0, 2, 3, 5, 7, 8, 9, 11),
	BEBOP_HARMONIC_MINOR(0, 2, 3, 5, 7, 8, 10, 11),
	BEBOP_SEVENTH_FLAT_FIVE_DIMINISHED(0, 2, 4, 5, 6, 8, 10, 11),
	// European
	FLAMENCO(0, 1, 4, 5, 7, 8, 10),
	NEAPOLITAN_MAJOR(0, 1, 3, 5, 7, 9, 11),
	NEAPOLITAN_MINOR(0, 1, 3, 5, 7, 8, 11),
	ROMANIAN_MAJOR(0, 1, 4, 6, 7, 9, 10),
	ROMANIAN_MINOR(0, 2, 3, 6, 7, 9, 10),
	HUNGARIAN_MAJOR(0, 3, 4, 6, 7, 9, 10),
	HUNGARIAN_MINOR(0, 2, 3, 6, 7, 8, 11),
	PROMETHEUS(0, 2, 4, 6, 9, 10),
	// Japanese scales
	IN(0, 1, 3, 5, 7, 8, 10),
	IWATO(0, 1, 5, 6, 10),
	INSEN(0, 1, 5, 7, 10),
	MIYAKO_BUSHI(0, 1, 5, 7, 10),
	RYO(0, 2, 4, 7, 9),
	RITSU(0, 2, 5, 7, 9),
	YO(0, 2, 5, 7, 9),
	MINYO(0, 3, 5, 7, 10),
	HIRAJOSHI(0, 4, 6, 7, 11),
	// World
	PERSIAN(0, 1, 4, 5, 6, 8, 11),
	// Various
	DOUBLE_HARMONIC(0, 1, 4, 5, 7, 8, 11),
	WHOLE_TONE(0, 2, 4, 6, 8, 10),
	DIMINISHED_HALF(0, 1, 3, 4, 6, 7, 9, 10),
	DIMINISHED_WHOLE(0, 2, 3, 5, 6, 8, 9, 11),
	TRITONE(0, 1, 4, 6, 7, 10),
	TRITONE_TWO_SEMITONE(0, 1, 2, 6, 7, 8);

	/** List of intervals (in half-steps) the Scale is composed of */
	public final List<Interval> intervalList;

	private Scale(Integer... steps) {
		List<Interval> intervals = new ArrayList<>();
		for (int step : steps)
			intervals.add(Interval.from(step));
		this.intervalList = Collections.unmodifiableList(intervals);
	}
}
