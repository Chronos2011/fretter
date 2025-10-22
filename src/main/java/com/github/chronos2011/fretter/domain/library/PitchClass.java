package com.github.chronos2011.fretter.domain.library;

import java.util.Collections;
import java.util.List;

import com.github.chronos2011.fretter.application.Configuration;

/**
 * Class PitchClass defines functionality for pitch classes.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Pitch_class">Pitch class</a>
 */
public enum PitchClass implements Nameable {
	UNKNOWN(Pitch.UNKNOWN, ""),
	AF(Pitch.AF0, "Ab"),
	A_(Pitch.A_0, "A"),
	AS(Pitch.AS0, "A#"),
	BF(Pitch.BF0, "Bb"),
	B_(Pitch.B_0, "B"),
	BS(Pitch.BS0, "B#"),
	CF(Pitch.CF0, "Cb"),
	C_(Pitch.C_0, "C"),
	CS(Pitch.CS0, "C#"),
	DF(Pitch.DF0, "Db"),
	D_(Pitch.D_0, "D"),
	DS(Pitch.DS0, "D#"),
	EF(Pitch.EF0, "Eb"),
	E_(Pitch.E_0, "E"),
	ES(Pitch.ES0, "E#"),
	FF(Pitch.FF0, "Fb"),
	F_(Pitch.F_0, "F"),
	FS(Pitch.FS0, "F#"),
	GF(Pitch.GF0, "Gb"),
	G_(Pitch.G_0, "G"),
	GS(Pitch.GS0, "G#");

	/** Target {@link Pitch} of the PitchClass */
	public final Pitch targetPitch;
	/** Name of the PitchClass */
	public final String name;

	private PitchClass(Pitch targetPitch, String name) {
		this.targetPitch = targetPitch;
		this.name = name;
	}

	/**
	 * Returns the target {@link Pitch} of this PitchClass.
	 *
	 * @return the target {@link Pitch} of this PitchClass
	 */
	public Pitch getTargetPitch() {
		return targetPitch;
	}

	/**
	 * Returns a flag indicating whether the PitchClass is flat.
	 *
	 * @return a flag indicating whether the PitchClass is flat
	 */
	public boolean isFlat() {
		return name().charAt(1) == 'F';
	}

	/**
	 * Returns the human-readable not name of the PitchClass.
	 *
	 * @return the human-readable not name of the PitchClass
	 */
	public String getNoteName() {
		if (this == UNKNOWN)
			return "??";
		String base = name().substring(0, 1);
		String modifier = null;
		switch (name().substring(1, 2)) {
			case "S":
				if (!Configuration.hostIsWindows)
					modifier = "\u266F";
				else
					modifier = "#";
				break;
			case "F":
				if (!Configuration.hostIsWindows)
					modifier = "\u266D";
				else
					modifier = "b";
				break;
		}
		if (modifier == null)
			return base;
		else
			return base + modifier;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<String> getAlternativeNames() {
		return Collections.emptyList();
	}
}
