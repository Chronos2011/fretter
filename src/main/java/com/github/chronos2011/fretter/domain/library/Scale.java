package com.github.chronos2011.fretter.domain.library;

import static com.github.chronos2011.fretter.util.ListUtil.ul;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Enumeration Scale enumerates the various scales available for diagram generation. Programmatically these are the same
 * as as {@link Chord}s, but we keep them apart for domain reasons.
 */
public enum Scale implements Nameable {
	// TODO it seems that Wikipedia is highly unreliable - look for another source
	/*
	 * Intervals are generated automatically from constant name; constant names must be composed of a list of steps (use
	 * 'N' for 0, 'T' for 10, 'E' for 11).
	 */
	// 12-tone
	N123456789TE("chromatic", ul()),
	// 9-tone
	N234579TE("blues nonatonic", ul()),
	// 8-tone
	// 7-tone
	N13568T("locrian", ul()),
	N13579T("phrygian", ul()),
	N23578T("minor", ul("minor natural", "aeolian")),
	N23578E("minor harmonic", ul()),
	N23579T("dorian", ul()),
	N23579E("minor melodic", ul()),
	N24578E("major harmonic", ul()),
	N24579T("mixolydian", ul()),
	N24579E("major", ul("major natural", "ionian")),
	N24679E("lydian", ul()),
	// 5-tone
	N2479("major pentatonic", ul("ryo")),
	N2579("major blues pentatonic", ul("ritsu", "yo")),
	N257T("suspended pentatonic", ul()),
	N357T("minor pentatonic", ul("minyo")),
	N358T("minor blues pentatonic", ul()),
	//
	// TODO check modes
	N24579("major hexatonic", ul()),
	N2357T("minor hexatonic", ul()),
	//
	N3567T("minor blues hexatonic", ul()),
	N23479("major blues hexatonic", ul()),
	//
	N24579TE("bebop dominant", ul()),
	N245789E("bebop major", ul()),
	N235789E("bebop melodic minor", ul()),
	N23578TE("bebop harmonic minor", ul()),
	N24568TE("bebop seventh flat five diminished", ul()),
	// European
	N14578T("flamenco", ul()),
	N13579E("neapolitan major", ul()),
	N13578E("neapolitan minor", ul()),
	N14679T("romanian major", ul()),
	N23679T("romanian minor", ul()),
	N34679T("hungarian major", ul()),
	N23678E("hungarian minor", ul()),
	N2469T("prometheus", ul()),
	// Japanese scales
	N13578T("in", ul()),
	N156T("iwato", ul()),
	N157T("insen", ul("miyako bushi")),
	N467E("hirajoshi", ul()),
	// World
	N14568E("persian", ul()),
	// Various
	N14578E("double harmonic", ul()),
	N2468T("whole tone", ul()),
	N134679T("diminished half", ul()),
	N235689E("diminished whole", ul()),
	N1467T("tritone", ul()),
	N12678("tritone two semitone", ul());

	/**
	 * Returns a Scale from standard or alternative name.
	 *
	 * @param name the standard or alternative name of a Scale
	 * @return the corresponding Scale
	 */
	public static Scale fromName(String name) {
		for (Scale scale : Scale.values()) {
			if (scale.name.equalsIgnoreCase(name))
				return scale;
			for (String alternativeName : scale.alternativeNames)
				if (alternativeName.equalsIgnoreCase(name))
					return scale;
		}
		return null;
	}

	/** Name of the Scale */
	public final String name;
	/** Alternative names of the Scale */
	public final List<String> alternativeNames;
	/** List of intervals (in half-steps) the Scale is composed of */
	public final List<Interval> intervalList;

	private Scale(String name, List<String> alternativeNames) {
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
