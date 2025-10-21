package com.github.chronos2011.fretter.options;

import java.util.Collections;
import java.util.List;

import com.github.chronos2011.fretter.domain.library.Nameable;
import com.github.chronos2011.fretter.options.ChordOptions.SanityCheck;

/**
 * Class ApplicationOptions groups all options required for a regular application run.
 */
public class ApplicationOptions {
	/**
	 * Enumeration Operation enumerates the modes of operation of the application.
	 */
	public static enum Operation implements Nameable {
		/**
		 * Renders a fretboard diagram showing all frets.
		 */
		BOARD,
		/**
		 * Renders a scale diagram; there are three types of scale diagram:
		 * <ul>
		 * <li>if a {@link ScaleOptions.DistributionPattern} is given, all frets inside the pattern are shown;</li>
		 * <li>if a {@link ScaleOptions.WorkingWindow} is given, all frets inside the working window are shown;</li>
		 * <li>if neither is given, then all frets in scale and across the complete board are shown.</li>
		 * </ul>
		 */
		SCALE,
		/**
		 * Renders an arpeggio diagram; since there are numerous possible configurations, all frets in the arpeggio and
		 * across the complete board are shown.
		 */
		ARPEGGIO,
		/**
		 * Determines and renders various chord fingerings that conform to a set of {@link SanityCheck}s.
		 */
		CHORD;

		@Override
		public String getName() {
			return name().toLowerCase();
		}

		@Override
		public List<String> getAlternativeNames() {
			return Collections.emptyList();
		}
	}

	public Operation operation;
	public BoardOptions boardOptions;
	public ScaleOptions scaleOptions;
	public ArpeggioOptions arpeggioOptions;
	public ChordOptions chordOptions;
	public RenderOptions renderOptions;
	public int verbosity;
}
