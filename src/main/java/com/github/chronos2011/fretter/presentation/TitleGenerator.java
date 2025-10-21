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

		String tuning = options.tuning.getName();
		return String.format("Board (%s tuning, %d frets)", tuning, options.fretCount);
	}

	private String generateScaleTitle() {
		String tuning = applicationOptions.boardOptions.tuning.getName();
		String root = applicationOptions.scaleOptions.pitchClass.getNoteName();
		String scale = applicationOptions.scaleOptions.scale.getName();
		return String.format("Scale %s %s (%s tuning)", root, scale, tuning);
	}

	private String generateArpeggioTitle() {
		String tuning = applicationOptions.boardOptions.tuning.getName();
		String root = applicationOptions.arpeggioOptions.pitchClass.getNoteName();
		String chord = applicationOptions.arpeggioOptions.chord.getName();
		return String.format("Arpeggio %s %s (%s tuning)", root, chord, tuning);
	}

	private String generateChordTitle() {
		String tuning = applicationOptions.boardOptions.tuning.getName();
		String root = applicationOptions.chordOptions.pitchClass.getNoteName();
		String chord = applicationOptions.chordOptions.chord.getName();
		return String.format("Chord %s %s (%s tuning)", root, chord, tuning);
	}
}
