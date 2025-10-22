package com.github.chronos2011.fretter.presentation;

import com.github.chronos2011.fretter.options.ApplicationOptions;
import com.github.chronos2011.fretter.options.ArpeggioOptions;
import com.github.chronos2011.fretter.options.BoardOptions;
import com.github.chronos2011.fretter.options.ChordOptions;

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
		return String.format("Board (%s tuning, %d frets)", getTuningName(), options.fretCount);
	}

	private String generateScaleTitle() {
		String root = applicationOptions.scaleOptions.pitchClass.getNoteName();
		return String.format("Scale %s %s (%s tuning)", root, getScaleName(), getTuningName());
	}

	private String generateArpeggioTitle() {
		ArpeggioOptions options = applicationOptions.arpeggioOptions;
		String root = options.pitchClass.getNoteName();
		String chord = options.chordName != null ? options.chordName : options.chord.getName();
		return String.format("Arpeggio %s %s (%s tuning)", root, chord, getTuningName());
	}

	private String generateChordTitle() {
		ChordOptions options = applicationOptions.chordOptions;
		String root = applicationOptions.chordOptions.pitchClass.getNoteName();
		String chord = options.chordName != null ? options.chordName : options.chord.getName();
		return String.format("Chord %s %s (%s tuning)", root, chord, getTuningName());
	}

	private String getTuningName() {
		if (applicationOptions.boardOptions.tuningName != null)
			return applicationOptions.boardOptions.tuningName;
		else
			return applicationOptions.boardOptions.tuning.getName();
	}

	private String getScaleName() {
		if (applicationOptions.scaleOptions.scaleName != null)
			return applicationOptions.scaleOptions.scaleName;
		else
			return applicationOptions.scaleOptions.scale.getName();
	}
}
