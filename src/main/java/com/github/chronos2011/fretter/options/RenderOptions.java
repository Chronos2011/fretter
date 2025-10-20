package com.github.chronos2011.fretter.options;

import com.github.chronos2011.fretter.domain.Board;
import com.github.chronos2011.fretter.domain.Fret;
import com.github.chronos2011.fretter.domain.FretWindow;
import com.github.chronos2011.fretter.domain.library.Interval;
import com.github.chronos2011.fretter.domain.library.Scale;
import com.github.chronos2011.fretter.options.ApplicationOptions.Operation;

/**
 * Class RenderOptions groups options that apply to the rendering of results.
 */
public class RenderOptions {
    /**
     * Enum FretLabeling enumerates the various fret labeling options available.
     */
    public static enum FretLabeling {
        /** Labels {@link Fret}s with MIDI pitch indices */
        MIDI_PITCH,
        /** Labels {@link Fret}s with human-readable note names */
        NOTE_NAME,
        /** Labels {@link Fret}s with human-readable {@link Interval} symbols */
        INTERVAL_SYMBOL,
        /** Labels {@link Fret}s with the number of half-steps corresponding to an {@link Interval} */
        INTERVAL_STEPS,
        /** Labels {@link Fret}s with a {@link Scale} membership indicator */
        SCALE_MEMBERSHIP;
    }

    /**
     * Defines the {@link Fret}s of the {@link Board} to be rendered. If not given, then the least required part of the
     * {@link Board} will be rendered.
     */
    public FretWindow renderWindow;
    /** {@link FretLabeling} to be used in diagrams */
    public FretLabeling fretLabeling;
    /** Result page (zero-indexed) to be shown in {@link Operation#CHORD} */
    public int page;
    /** Result page size to be used in {@link Operation#CHORD} */
    public int itemCount;
}
