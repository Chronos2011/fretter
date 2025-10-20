package com.github.chronos2011.fretter.domain.library;

import static com.github.chronos2011.fretter.domain.library.Pitch.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Enumeration Tuning enumerates the various tunings available in the application. The Tuning of an instrument
 * effectively dictates the number of strings available; the instrument itself does not matter, as long as it is
 * fretted, since one might want to tune a ukelele like a bass guitar.
 */
public enum Tuning {
	CGCGCE(new Pitch[] { C_3, G_3, C_4, G_4, C_5, E_5 }),
	// Same as CGCGCE
	OPEN_C(new Pitch[] { C_3, G_3, C_4, G_4, C_5, E_5 }),
	DADFAD(new Pitch[] { D_3, A_3, D_4, F_4, A_4, D_5 }),
	// Same as DADFAD
	OPEN_D_MINOR(new Pitch[] { D_3, A_3, D_4, F_4, A_4, D_5 }),
	DADGAD(new Pitch[] { D_3, A_3, D_4, G_4, A_4, D_5 }),
	// Same as DADGAD
	DSUS4(new Pitch[] { D_3, A_3, D_4, G_4, A_4, D_5 }),
	DADGBE(new Pitch[] { D_3, A_3, D_4, G_4, B_4, E_5 }),
	// Same as DADGBE
	DROP_D(new Pitch[] { D_3, A_3, D_4, G_4, B_4, E_5 }),
	DGDGBD(new Pitch[] { D_3, G_3, D_4, G_4, B_4, D_5 }),
	// Same as DGDGBD
	OPEN_G(new Pitch[] { D_3, G_3, D_4, G_4, B_4, D_5 }),
	EADGBE(new Pitch[] { E_3, A_3, D_4, G_4, B_4, E_5 }),
	// Same as EADGBE
	STANDARD_GUITAR(new Pitch[] { E_3, A_3, D_4, G_4, B_4, E_5 }),
	STANDARD_BASS(new Pitch[] { E_2, A_2, D_3, G_3 }),
	STANDARD_UKULELE(new Pitch[] { G_4, C_4, E_4, A_4 });

	/** List of {@link Pitch}es the Tuning is built from, in common order (lowest to highest except for the ukulele) */
	public final List<Pitch> pitches;

	private Tuning(Pitch[] pitches) {
		this.pitches = Collections.unmodifiableList(Arrays.asList(pitches));
	}
}