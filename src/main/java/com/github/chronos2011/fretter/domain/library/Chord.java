package com.github.chronos2011.fretter.domain.library;

import static com.github.chronos2011.fretter.util.ListUtil.ul;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Enumeration Chord enumerates the various chords available for diagram generation. Programmatically these are the same
 * as as {@link Scale}s, but we keep them apart for domain reasons.
 */
public enum Chord implements Nameable {
	/*
	 * Intervals are generated automatically from constant name; constant names must be composed of a list of steps (use
	 * 'N' for 0, 'T' for 10, 'E' for 11).
	 */
	N5("power fourth", ul("pow4")),
	N7("power fifth", ul("pow5")),
	N16("viennese prime", ul()),
	N27("suspended second", ul("sus2")),
	N36("diminished", ul("dim")),
	N37("minor", ul("min")),
	N47("major", ul("maj")),
	N48("augmented", ul("aug")),
	N57("suspended fourth", ul("sus4")),
	N6E("viennese inverse", ul()),
	N237("minor add second", ul("min add2")),
	N247("add second", ul("add2")),
	N347("alpha", ul()),
	N369("diminished seventh", ul("dim7")),
	N36T("minor seventh diminished fifth", ul("min7 dim5")),
	N36E("minor diminished fifth major seventh", ul("min dim5 maj7")),
	N378("minor minor sixth", ul("min min6")),
	N379("minor add sixth", ul("min add6")),
	N37T("minor seventh", ul("min7")),
	N37E("minor major seventh", ul("min maj7")),
	N457("add fourth", ul("add4")),
	N46T("dominant seventh diminished fifth", ul("dom7 dim5")),
	N479("add sixth", ul("add6")),
	N47T("dominant seventh", ul("dom7")),
	N47E("major seventh", ul("maj7")),
	N48T("augmented seventh", ul("aug7")),
	N48E("augmented major seventh", ul("aug maj7")),
	N567("dream", ul()),
	N368E("gamma", ul()),
	N369E("beta", ul()),
	N479E("major seventh add 6", ul("maj7/6"));

	/**
	 * Returns a Chord from standard or alternative name.
	 *
	 * @param name the standard or alternative name of a Chord
	 * @return the corresponding Chord
	 */
	public static Chord fromName(String name) {
		for (Chord chord : Chord.values()) {
			if (chord.name.equalsIgnoreCase(name))
				return chord;
			for (String alternativeName : chord.alternativeNames)
				if (alternativeName.equalsIgnoreCase(name))
					return chord;
		}
		return null;
	}

	/** Name of the Chord */
	public final String name;
	/** Alternative names of the Chord */
	public final List<String> alternativeNames;
	/** List of intervals (in half-steps) the Chord is composed of */
	public final List<Interval> intervalList;

	private Chord(String name, List<String> alternativeNames) {
		this.intervalList = createIntervalListFromConstantName();
		this.name = name;
		this.alternativeNames = alternativeNames;
	}

	private List<Interval> createIntervalListFromConstantName() {
		List<Interval> intervalList = new ArrayList<>();
		String constantName = name();
		for (int index = 0; index < constantName.length(); index++) {
			char intervalStepsChar = constantName.charAt(index);
			int intervalSteps = Interval.OUT.steps;
			switch (intervalStepsChar) {
				case 'N':
					intervalSteps = 0;
					break;
				case 'T':
					intervalSteps = 10;
					break;
				case 'E':
					intervalSteps = 11;
					break;
				default:
					intervalSteps = intervalStepsChar - 48;
					break;
			}
			Interval interval = Interval.from(intervalSteps);
			assert (interval != null);
			intervalList.add(interval);
		}
		return Collections.unmodifiableList(intervalList);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<String> getAlternativeNames() {
		return alternativeNames;
	}
}
