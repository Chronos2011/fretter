package com.github.chronos2011.fretter.domain;

import com.github.chronos2011.fretter.options.BoardOptions;

/**
 * Class Board defines a fretboard of a fretted instrument.
 */
public class Board {
	/** {@link BoardOptions} of this Board */
	public final BoardOptions boardOptions;
	/** Number of strings of this Board */
	public final int stringCount;
	/** Number of frets of this Board */
	public final int fretCount;
	/** Nested array (string index first, then fret index) of {@link Fret}s composing this Board */
	public final Fret[][] frets;

	/**
	 * Creates a new Board.
	 *
	 * @param boardOptions the {@link BoardOptions} to be used
	 */
	public Board(BoardOptions boardOptions) {
		this.boardOptions = boardOptions;
		this.stringCount = boardOptions.tuning.pitches.size();
		this.fretCount = boardOptions.fretCount;
		this.frets = new Fret[stringCount][fretCount + 1];
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++)
			for (int fretIndex = 0; fretIndex <= fretCount; fretIndex++)
				this.frets[stringIndex][fretIndex] = new Fret(new Position(stringIndex, fretIndex));
	}
}
