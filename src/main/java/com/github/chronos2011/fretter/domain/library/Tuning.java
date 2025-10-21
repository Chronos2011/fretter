package com.github.chronos2011.fretter.domain.library;

import static com.github.chronos2011.fretter.util.ListUtil.ul;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Enumeration Tuning enumerates the various tunings available in the application. The Tuning of an instrument
 * effectively dictates the number of strings available; the instrument itself does not matter, as long as it is
 * fretted, since one might want to tune a ukelele like a bass guitar.
 */
public enum Tuning implements Nameable {
	/*
	 * Pitches are generated automatically from constant name; constant names must be composed of a list of tone plus
	 * octave.
	 */
	E2A2D3G3("standard bass", ul("EADG")),
	G4C4E4A4("standard ukulele", ul("GCEA")),
	C3G3C3G4C5E5("open C", ul("CGCGCE")),
	D3G3D4G4B4D5("open G", ul("DGDGBD")),
	D3A3D4F4A4D5("open D minor", ul("DADFAD")),
	D3A3D4G4A4D5("Dsus4", ul("DADGAD")),
	D3A3D4G4B4E5("drop D", ul("DADGBE")),
	E3A3D4G4B4E5("standard guitar", ul("EADGBE"));

	/**
	 * Returns a Tuning from standard or alternative name.
	 *
	 * @param name the standard or alternative name of a Tuning
	 * @return the corresponding Tuning
	 */
	public static Tuning fromName(String name) {
		for (Tuning tuning : Tuning.values()) {
			if (tuning.name.equalsIgnoreCase(name))
				return tuning;
			for (String alternativeName : tuning.alternativeNames)
				if (alternativeName.equalsIgnoreCase(name))
					return tuning;
		}
		return null;
	}

	/** Name of the Tuning */
	public final String name;
	/** Alternative names of the Tuning */
	public final List<String> alternativeNames;
	/** List of {@link Pitch}es the Tuning is built from, in common order (lowest to highest except for the ukulele) */
	public final List<Pitch> pitches;

	private Tuning(String name, List<String> alternativeNames) {
		this.pitches = createPitchListFromConstantName();
		this.name = name;
		this.alternativeNames = alternativeNames;
	}

	private List<Pitch> createPitchListFromConstantName() {
		List<Pitch> pitchList = new ArrayList<>();
		String constantName = name();
		for (int index = 0; index < constantName.length(); index += 2) {
			String pitchConstantName = constantName.substring(index, index + 2);
			pitchConstantName = String.format("%c_%c", pitchConstantName.charAt(0), pitchConstantName.charAt(1));
			Pitch pitch = Pitch.from(pitchConstantName);
			assert (pitch != null);
			pitchList.add(pitch);
		}
		return Collections.unmodifiableList(pitchList);
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