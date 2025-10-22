package com.github.chronos2011.fretter.options;

import com.github.chronos2011.fretter.domain.Board;
import com.github.chronos2011.fretter.domain.library.Tuning;
import com.github.chronos2011.fretter.options.ApplicationOptions.Operation;

/**
 * Class BoardOptions groups options that apply to the fretboard in all modes of {@link Operation}.
 */
public class BoardOptions {
	/** {@link Tuning} of the {@link Board} */
	public Tuning tuning;
	/** String indicating the {@link Tuning} name originally selected by the user */
	public String tuningName;
	/** Number of frets on the {@link Board}; the instrument nut is effectively adding another fret */
	public int fretCount;
}
