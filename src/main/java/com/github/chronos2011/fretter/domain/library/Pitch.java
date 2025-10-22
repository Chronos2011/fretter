package com.github.chronos2011.fretter.domain.library;

import java.util.Collections;
import java.util.List;

import com.github.chronos2011.fretter.application.Configuration;

/**
 * Enumeration Pitch enumerates the various pitches available in the application. Please note that scientific pitch
 * notation is being used, where middle C corresponds to C4. Additionally, octave number increases from B to C.<br/>
 * Internal calculations are based on MIDI pitch indices.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Scientific_pitch_notation">Scientific pitch notation</a>
 * @see <a href="https://en.wikipedia.org/wiki/MIDI_tuning_standard">MIDI tuning standard</a>
 */
public enum Pitch implements Nameable {
	UNKNOWN(-1, 0),
	C_M(0, 8.18),
	CSM(1, 8.66),
	DFM(1, 8.66),
	D_M(2, 9.18),
	DSM(3, 9.72),
	EFM(3, 9.72),
	E_M(4, 10.3),
	FFM(4, 10.3),
	F_M(5, 10.91),
	ESM(5, 10.91),
	FSM(6, 11.56),
	GFM(6, 11.56),
	G_M(7, 12.25),
	GSM(8, 12.98),
	AFM(8, 12.98),
	A_M(9, 13.75),
	ASM(10, 14.57),
	BFM(10, 14.57),
	B_M(11, 15.43),
	CF0(11, 15.43),
	C_0(12, 16.35),
	BSM(12, 16.35),
	CS0(13, 17.32),
	DF0(13, 17.32),
	D_0(14, 18.35),
	DS0(15, 19.45),
	EF0(15, 19.45),
	E_0(16, 20.6),
	FF0(16, 20.6),
	F_0(17, 21.83),
	ES0(17, 21.83),
	FS0(18, 23.12),
	GF0(18, 23.12),
	G_0(19, 24.5),
	GS0(20, 25.96),
	AF0(20, 25.96),
	A_0(21, 27.5),
	AS0(22, 29.14),
	BF0(22, 29.14),
	B_0(23, 30.87),
	CF1(23, 30.87),
	C_1(24, 32.7),
	BS0(24, 32.7),
	CS1(25, 34.65),
	DF1(25, 34.65),
	D_1(26, 36.71),
	DS1(27, 38.89),
	EF1(27, 38.89),
	E_1(28, 41.2),
	FF1(28, 41.2),
	F_1(29, 43.65),
	ES1(29, 43.65),
	FS1(30, 46.25),
	GF1(30, 46.25),
	G_1(31, 49),
	GS1(32, 51.91),
	AF1(32, 51.91),
	A_1(33, 55),
	AS1(34, 58.27),
	BF1(34, 58.27),
	B_1(35, 61.74),
	CF2(35, 61.74),
	C_2(36, 65.41),
	BS1(36, 65.41),
	CS2(37, 69.3),
	DF2(37, 69.3),
	D_2(38, 73.42),
	DS2(39, 77.78),
	EF2(39, 77.78),
	E_2(40, 82.41),
	FF2(40, 82.41),
	F_2(41, 87.31),
	ES2(41, 87.31),
	FS2(42, 92.5),
	GF2(42, 92.5),
	G_2(43, 98),
	GS2(44, 103.83),
	AF2(44, 103.83),
	A_2(45, 110),
	AS2(46, 116.54),
	BF2(46, 116.54),
	B_2(47, 123.47),
	CF3(47, 123.47),
	C_3(48, 130.81),
	BS2(48, 130.81),
	CS3(49, 138.59),
	DF3(49, 138.59),
	D_3(50, 146.83),
	DS3(51, 155.56),
	EF3(51, 155.56),
	E_3(52, 164.81),
	FF3(52, 164.81),
	F_3(53, 174.61),
	ES3(53, 174.61),
	FS3(54, 185),
	GF3(54, 185),
	G_3(55, 196),
	GS3(56, 207.65),
	AF3(56, 207.65),
	/** Guitar A */
	A_3(57, 220),
	AS3(58, 233.08),
	BF3(58, 233.08),
	B_3(59, 246.94),
	CF4(59, 246.94),
	/** Middle C */
	C_4(60, 261.63),
	BS3(60, 261.63),
	CS4(61, 277.18),
	DF4(61, 277.18),
	D_4(62, 293.66),
	DS4(63, 311.13),
	EF4(63, 311.13),
	E_4(64, 329.63),
	FF4(64, 329.63),
	F_4(65, 349.23),
	ES4(65, 349.23),
	FS4(66, 369.99),
	GF4(66, 369.99),
	G_4(67, 392),
	GS4(68, 415.3),
	AF4(68, 415.3),
	/** Concert pitch */
	A_4(69, 440),
	AS4(70, 466.16),
	BF4(70, 466.16),
	B_4(71, 493.88),
	CF5(71, 493.88),
	C_5(72, 523.25),
	BS4(72, 523.25),
	CS5(73, 554.37),
	DF5(73, 554.37),
	D_5(74, 587.33),
	DS5(75, 622.25),
	EF5(75, 622.25),
	E_5(76, 659.26),
	FF5(76, 659.26),
	F_5(77, 698.46),
	ES5(77, 698.46),
	FS5(78, 739.99),
	GF5(78, 739.99),
	G_5(79, 783.99),
	GS5(80, 830.61),
	AF5(80, 830.61),
	A_5(81, 880),
	AS5(82, 932.33),
	BF5(82, 932.33),
	B_5(83, 987.77),
	CF6(83, 987.77),
	C_6(84, 1046.5),
	BS5(84, 1046.5),
	CS6(85, 1108.73),
	DF6(85, 1108.73),
	D_6(86, 1174.66),
	DS6(87, 1244.51),
	EF6(87, 1244.51),
	E_6(88, 1318.51),
	FF6(88, 1318.51),
	F_6(89, 1396.91),
	ES6(89, 1396.91),
	FS6(90, 1479.98),
	GF6(90, 1479.98),
	G_6(91, 1567.98),
	GS6(92, 1661.22),
	AF6(92, 1661.22),
	A_6(93, 1760),
	AS6(94, 1864.66),
	BF6(94, 1864.66),
	B_6(95, 1975.53),
	CF7(95, 1975.53),
	C_7(96, 2093),
	BS6(96, 2093),
	CS7(97, 2217.46),
	DF7(97, 2217.46),
	D_7(98, 2349.32),
	DS7(99, 2489.02),
	EF7(99, 2489.02),
	E_7(100, 2637.02),
	FF7(100, 2637.02),
	F_7(101, 2793.83),
	ES7(101, 2793.83),
	FS7(102, 2959.96),
	GF7(102, 2959.96),
	G_7(103, 3135.96),
	GS7(104, 3322.44),
	AF7(104, 3322.44),
	A_7(105, 3520),
	AS7(106, 3729.31),
	BF7(106, 3729.31),
	B_7(107, 3951.07),
	CF8(107, 3951.07),
	C_8(108, 4186.01),
	BS7(108, 4186.01),
	CS8(109, 4434.92),
	DF8(109, 4434.92),
	D_8(110, 4698.64),
	DS8(111, 4978.03),
	EF8(111, 4978.03),
	E_8(112, 5274.04),
	FF8(112, 5274.04),
	F_8(113, 5587.65),
	ES8(113, 5587.65),
	FS8(114, 5919.91),
	GF8(114, 5919.91),
	G_8(115, 6271.93),
	GS8(116, 6644.88),
	AF8(116, 6644.88),
	A_8(117, 7040),
	AS8(118, 7458.62),
	BF8(118, 7458.62),
	B_8(119, 7902.13),
	CF9(119, 7902.13),
	C_9(120, 8372.02),
	BS8(120, 8372.02),
	CS9(121, 8869.84),
	DF9(121, 8869.84),
	D_9(122, 9397.27),
	DS9(123, 9956.06),
	EF9(123, 9956.06),
	E_9(124, 10548.08),
	FF9(124, 10548.08),
	F_9(125, 11175.3),
	ES9(125, 11175.3),
	FS9(126, 11839.82),
	GF9(126, 11839.82),
	G_9(127, 12543.85);

	private static final String SUBSCRIPTS = "\u2080\u2081\u2082\u2083\u2084\u2085\u2086\u2087\u2088\u2089";

	/**
	 * Returns the Pitch corresponding to a given MIDI pitch index.
	 *
	 * @param midiIndex the MIDI pitch index to find the Pitch to
	 * @return the corresponding Pitch
	 */
	public static Pitch from(int midiIndex) {
		for (Pitch pitch : Pitch.values())
			if (pitch.midiIndex == midiIndex)
				return (pitch);
		return null;
	}

	/**
	 * Returns the Pitch corresponding to its constant name.
	 *
	 * @param constantName the constant name of the Pitch
	 * @return the corresponding Pitch
	 */
	public static Pitch from(String constantName) {
		for (Pitch pitch : Pitch.values())
			if (pitch.name().equals(constantName))
				return (pitch);
		return null;
	}

	/** MIDI pitch index of the Pitch */
	public final int midiIndex;
	/** Frequency in [Hz] of the Pitch */
	public final double frequency;

	private Pitch(int midiIndex, double frequency) {
		this.midiIndex = midiIndex;
		this.frequency = frequency;
	}

	/**
	 * Raises the pitch by an amount of half-steps.
	 *
	 * @param steps the amount of half-steps to raise
	 * @return the raised Pitch
	 */
	public Pitch raise(int steps) {
		return from(midiIndex + steps);
	}

	/**
	 * Returns the flat equivalent to this Pitch if available.
	 *
	 * @return the flat equivalent to this Pitch
	 */
	public Pitch getFlatEquivalent() {
		for (Pitch pitch : Pitch.values())
			if (pitch.midiIndex == this.midiIndex && pitch.name().charAt(1) == 'F')
				return pitch;
		return this;
	}

	/**
	 * Returns the {@link PitchClass} of this Pitch.
	 *
	 * @return the {@link PitchClass} of this Pitch
	 */
	public PitchClass getPitchClass() {
		for (PitchClass pitchClass : PitchClass.values())
			if (pitchClass.name().equals(this.name().substring(0, 2)))
				return pitchClass;
		return null;
	}

	/**
	 * Returns the human-readable not name of the Pitch.
	 *
	 * @return the human-readable not name of the Pitch
	 */
	public String getNoteName(boolean preferFlat) {
		if (this == UNKNOWN)
			return "???";
		Pitch pitch = this;
		if (preferFlat)
			pitch = pitch.getFlatEquivalent();
		String base = pitch.name().substring(0, 1);
		String modifier = " ";
		switch (pitch.name().substring(1, 2)) {
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
		String octave = pitch.name().substring(2, 3);
		switch (octave) {
			case "M":
				if (!Configuration.hostIsWindows)
					octave = "\u208B";
				else
					octave = "-";
				break;
			default:
				int octaveIndex = Integer.parseInt(octave);
				if (!Configuration.hostIsWindows)
					octave = SUBSCRIPTS.substring(octaveIndex, octaveIndex + 1);
				break;
		}
		return base + modifier + octave;
	}

	@Override
	public String getName() {
		return name();
	}

	@Override
	public List<String> getAlternativeNames() {
		return Collections.emptyList();
	}
}
