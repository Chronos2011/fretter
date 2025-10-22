package com.github.chronos2011.fretter.options;

import com.github.chronos2011.fretter.domain.Position;
import com.github.chronos2011.fretter.domain.library.Chord;
import com.github.chronos2011.fretter.domain.library.PitchClass;
import com.github.chronos2011.fretter.options.ApplicationOptions.Operation;

/**
 * Class ChordOptions groups options that apply to a chord (in {@link Operation.CHORD}).
 */
public class ChordOptions {
	/**
	 * Class SanityCheck lists various sanity checks to be applied in {@link Operation.CHORD}.
	 */
	public static class SanityCheck {
		/** Checks whether this Fingering contains at most four different fret indices */
		public boolean atMostFourDifferentFrets = true;
		/** Checks whether this Fingering contains no duplicate pitches */
		public boolean noDuplicatePitches = true;
		/** Checks whether this Fingering contains frets LEFT of a bar */
		public boolean noFretsLeftOfBar = true;
		/** Checks whether this Fingering contains frets TOP LEFT of a bar */
		public boolean noFretsTopLeftOfBar = true;
	}

	/** {@link Chord} to be generated */
	public Chord chord;
	/** String indicating the {@link Chord} name originally selected by the user */
	public String chordName;
	/** {@link PitchClass} of the {@link Chord} */
	public PitchClass pitchClass;
	/** {@link Position} at which the {@link Chord} should be constructed */
	public Position position;
	/** Indicates whether open strings should be accepted */
	public boolean allowOpenStrings;
	/** Indicates whether unused strings should be accepted */
	public boolean allowUnusedStrings;
	/** Maximum width (in frets) of the {@link Chord} */
	public int maxWidth;
	/** Maximum deviation (from {@link ChordOptions#position}) of the {@link Chord} */
	public int maxDeviation;
	/** {@link SanityCheck}s activated */
	public SanityCheck sanityCheck = new SanityCheck();
}
