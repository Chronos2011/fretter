package com.github.chronos2011.fretter.presentation;

import com.github.chronos2011.fretter.options.ApplicationOptions;
import com.github.chronos2011.fretter.options.BoardOptions;

/**
 * Class TitleGenerator generates diagram titles.
 */
public class TitleGenerator {
	protected final ApplicationOptions applicationOptions;

	/**
	 * Creates a new TitleGenerator.
	 *
	 * @param applicationOptions the {@link ApplicationOptions} to be used
	 */
	public TitleGenerator(ApplicationOptions applicationOptions) {
		this.applicationOptions = applicationOptions;
	}

	/**
	 * Generate the title.
	 *
	 * @return the title generated
	 */
	public String generateTitle() {
		switch (applicationOptions.operation) {
			case BOARD:
				return generateBoardTitle();
			case SCALE:
				return generateScaleTitle();
			case ARPEGGIO:
				return generateArpeggioTitle();
			case CHORD:
				return generateChordTitle();
			default:
				return "";
		}
	}

	private String generateBoardTitle() {
		BoardOptions options = applicationOptions.boardOptions;

		String tuning = options.tuning.toString();
		return String.format("Board (%s tuning, %d frets)", tuning, options.fretCount);
	}

	private String generateScaleTitle() {
		String tuning = applicationOptions.boardOptions.tuning.toString();
		String root = applicationOptions.scaleOptions.pitch.getName();
		String scale = applicationOptions.scaleOptions.scale.toString();
		return String.format("Scale %s %s (%s tuning)", root, scale, tuning);
	}

	private String generateArpeggioTitle() {
		String tuning = applicationOptions.boardOptions.tuning.toString();
		String root = applicationOptions.arpeggioOptions.pitch.getName();
		String chord = applicationOptions.arpeggioOptions.chord.toString();
		return String.format("Arpeggio %s %s (%s tuning)", root, chord, tuning);
	}

	private String generateChordTitle() {
		String tuning = applicationOptions.boardOptions.tuning.toString();
		String root = applicationOptions.chordOptions.pitch.getName();
		String chord = applicationOptions.chordOptions.chord.toString();
		return String.format("Chord %s %s (%s tuning)", root, chord, tuning);
	}
}
