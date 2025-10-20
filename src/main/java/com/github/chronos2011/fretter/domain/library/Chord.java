package com.github.chronos2011.fretter.domain.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Enumeration Chord enumerates the various chords available for diagram generation. Programmatically these are the same
 * as as {@link Scale}s, but we keep them apart for domain reasons.
 */
public enum Chord {
	VIENNESE_PRIME(0, 1, 6),
	SUS2(0, 2, 7),
	ADD2(0, 2, 4, 7),
	MIN_ADD2(0, 2, 3, 7),
	ALPHA(0, 3, 4, 7),
	DIM(0, 3, 6),
	GAMMA(0, 3, 6, 8, 11),
	DIM7(0, 3, 6, 9),
	BETA(0, 3, 6, 9, 11),
	MIN7_DIM5(0, 3, 6, 10),
	MIN_DIM5_MAJ7(0, 3, 6, 11),
	MIN(0, 3, 7),
	MIN_MIN6(0, 3, 7, 8),
	MIN_ADD6(0, 3, 7, 9),
	MIN7(0, 3, 7, 10),
	MIN_MAJ7(0, 3, 7, 11),
	ADD4(0, 4, 5, 7),
	DOM7_DIM5(0, 4, 6, 10),
	MAJ(0, 4, 7),
	ADD6(0, 4, 7, 9),
	DOM7(0, 4, 7, 10),
	MAJ7(0, 4, 7, 11),
	MAJ76(0, 4, 7, 9, 11),
	AUG(0, 4, 8),
	AUG7(0, 4, 8, 10),
	AUG_MAJ7(0, 4, 8, 11),
	POW4(0, 5),
	DREAM(0, 5, 6, 7),
	SUS4(0, 5, 7),
	VIENNESE_INVERSE(0, 6, 11),
	POW5(0, 7);

	/** List of intervals (in half-steps) the Chord is composed of */
	public final List<Interval> intervalList;

	private Chord(Integer... steps) {
		List<Interval> intervals = new ArrayList<>();
		for (int step : steps)
			intervals.add(Interval.from(step));
		this.intervalList = Collections.unmodifiableList(intervals);
	}
}
