package com.github.chronos2011.fretter.domain;

import com.github.chronos2011.fretter.domain.library.Degree;
import com.github.chronos2011.fretter.domain.library.Interval;
import com.github.chronos2011.fretter.domain.library.Pitch;
import com.github.chronos2011.fretter.domain.solver.Solver;

/**
 * Class Fret defines a fret on a fretboard, which is composed of a {@link Position} and its harmonic semantics. A Fret
 * is either part of a harmonic (i.e. having an {@link Interval} and {@link Degree} in relation to a root note) or not
 * (i.e. being "out of scale"). It is a {@link Solver}'s responsibility to apply harmonic semantics to a Fret.
 */
public class Fret {
	/** Position of the Fret */
	public Position position;
	/** {@link Pitch} of the Fret */
	public Pitch pitch;
	/** {@link Interval} of the Fret */
	public Interval interval;
	/** {@link Degree} of the Fret */
	public Degree degree;

	/**
	 * Creates a new Fret with a {@link Position}.
	 *
	 * @param position the {@link Position} to be used
	 */
	public Fret(Position position) {
		this.position = position;
		this.pitch = Pitch.UNKNOWN;
		setOutOfScale();
	}

	/**
	 * Sets this Fret to be "out of scale", effectively resetting {@link Interval} and {@link Degree}.
	 */
	public void setOutOfScale() {
		this.interval = Interval.OUT;
		this.degree = Degree.OUT;
	}
}
