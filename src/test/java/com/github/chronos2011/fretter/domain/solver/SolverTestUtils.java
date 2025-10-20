package com.github.chronos2011.fretter.domain.solver;

import static org.junit.Assert.assertEquals;

import com.github.chronos2011.fretter.domain.Board;
import com.github.chronos2011.fretter.domain.library.Degree;
import com.github.chronos2011.fretter.domain.library.Interval;
import com.github.chronos2011.fretter.domain.library.Pitch;

public class SolverTestUtils {
	public static void assertTone(Board board, int stringIndex, int fretIndex, Pitch pitch, Interval interval,
			Degree degree) {
		String place = " @ (" + stringIndex + ", " + fretIndex + ")";
		assertEquals("Invalid pitch" + place, pitch, board.frets[stringIndex][fretIndex].pitch);
		assertEquals("Invalid interval" + place, interval, board.frets[stringIndex][fretIndex].interval);
		assertEquals("Invalid degree" + place, degree, board.frets[stringIndex][fretIndex].degree);
	}

	private SolverTestUtils() {
		// Make class purely static
	}
}
