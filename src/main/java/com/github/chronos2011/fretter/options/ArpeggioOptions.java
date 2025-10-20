package com.github.chronos2011.fretter.options;

import com.github.chronos2011.fretter.domain.library.Chord;
import com.github.chronos2011.fretter.domain.library.Pitch;
import com.github.chronos2011.fretter.options.ApplicationOptions.Operation;

/**
 * Class ArpeggioOptions groups options that apply to an arpeggio (in {@link Operation.ARPEGGIO}).
 */
public class ArpeggioOptions {
	/** {@link Chord} to be disassembled into an arpeggio */
	public Chord chord;
	/** {@link Pitch} of the {@link Chord}; the octave is irrelevant */
	public Pitch pitch;
}
