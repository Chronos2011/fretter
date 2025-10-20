package com.github.chronos2011.fretter.options;

import com.github.chronos2011.fretter.domain.FretWindow;
import com.github.chronos2011.fretter.domain.Position;
import com.github.chronos2011.fretter.domain.library.Degree;
import com.github.chronos2011.fretter.domain.library.Pitch;
import com.github.chronos2011.fretter.domain.library.Scale;
import com.github.chronos2011.fretter.options.ApplicationOptions.Operation;

/**
 * Class ScaleOptions groups options that apply to a scale (in {@link Operation.SCALE}).
 */
public class ScaleOptions {
	/**
	 * Class DistributionPattern specifies the scale distribution pattern to be used in {@link Operation.SCALE}; it is
	 * mutually exclusive with a {@link ScaleOptions.window}.
	 */
	public static class DistributionPattern {
		/**
		 * {@link Position} to start the {@link Scale} generation at; will start generation at the closest
		 * {@link Degree} "in scale"
		 */
		public Position position;
		/** Number of notes per string to generate */
		public int notesPerString;
	}

	/** {@link Scale} to be generated */
	public Scale scale;
	/** {@link Pitch} of the {@link Scale}; the octave is irrelevant */
	public Pitch pitch;
	/** Defines a "working window" to be used; mutually exclusive with a {@link DistributionPattern} */
	public FretWindow window;
	/** Defines a {@link DistributionPattern} to be used; mutually exclusive with a "working window" */
	public DistributionPattern pattern;
}
