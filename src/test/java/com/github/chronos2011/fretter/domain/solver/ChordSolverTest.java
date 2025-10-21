package com.github.chronos2011.fretter.domain.solver;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.chronos2011.fretter.domain.Fingering;
import com.github.chronos2011.fretter.domain.Fret;
import com.github.chronos2011.fretter.domain.Position;
import com.github.chronos2011.fretter.domain.library.Chord;
import com.github.chronos2011.fretter.domain.library.Pitch;
import com.github.chronos2011.fretter.domain.library.Tuning;
import com.github.chronos2011.fretter.options.ApplicationOptions;
import com.github.chronos2011.fretter.options.BoardOptions;
import com.github.chronos2011.fretter.options.ChordOptions;

public class ChordSolverTest {
	ApplicationOptions applicationOptions;

	@Before
	public void setup() {
		applicationOptions = new ApplicationOptions();
		applicationOptions.boardOptions = new BoardOptions();
		applicationOptions.boardOptions.fretCount = 24;
		applicationOptions.boardOptions.tuning = Tuning.fromName("EADGBE");
		applicationOptions.chordOptions = new ChordOptions();
		applicationOptions.chordOptions.chord = Chord.fromName("dom7");
		applicationOptions.chordOptions.pitch = Pitch.C_4;
		applicationOptions.chordOptions.position = new Position(2, 10);
		applicationOptions.chordOptions.maxWidth = 5;
		applicationOptions.chordOptions.maxDeviation = 1;

	}

	@Test
	public void testSolveAllowOpenAndUnusedStrings() {
		applicationOptions.chordOptions.allowOpenStrings = true;
		applicationOptions.chordOptions.allowUnusedStrings = true;
		ChordSolver solver = new ChordSolver(applicationOptions);
		Solution solution = solver.solve();
		assertEquals(6, solution.fingerings.size());
		Position[][] positions = new Position[6][];
		positions[0] = new Position[] { null, null, p(2, 10), p(3, 0), p(4, 11), p(5, 0) };
		positions[1] = new Position[] { null, null, p(2, 10), p(3, 12), p(4, 11), p(5, 0) };
		positions[2] = new Position[] { null, null, p(2, 10), p(3, 12), p(4, 11), p(5, 12) };
		positions[3] = new Position[] { null, null, p(2, 10), p(3, 0), p(4, 11), p(5, 12) };
		positions[4] = new Position[] { null, null, p(2, 8), p(3, 9), p(4, 8), p(5, 8) };
		positions[5] = new Position[] { null, null, p(2, 10), p(3, 9), p(4, 8), p(5, 6) };
		for (int exampleIndex = 0; exampleIndex < positions.length; exampleIndex++)
			assertFingering(solution.fingerings.get(exampleIndex), positions[exampleIndex]);
	}

	@Test
	public void testSolveTightMaximumWidth() {
		applicationOptions.chordOptions.chord = Chord.fromName("maj7");
		applicationOptions.chordOptions.pitch = Pitch.E_4;
		applicationOptions.chordOptions.position = new Position(8);
		applicationOptions.chordOptions.allowOpenStrings = true;
		applicationOptions.chordOptions.maxWidth = 2;
		applicationOptions.chordOptions.maxDeviation = 2;
		ChordSolver solver = new ChordSolver(applicationOptions);
		Solution solution = solver.solve();
		assertEquals(1, solution.fingerings.size());
		assertFingering(solution.fingerings.get(0), new Position[] { null, null, p(2, 9), p(3, 8), p(4, 9), p(5, 0) });
	}

	private Position p(int stringIndex, int fretIndex) {
		return new Position(stringIndex, fretIndex);
	}

	private void assertFingering(Fingering fingering, Position[] positions) {
		assertEquals(positions.length, fingering.stringCount);
		int index = 0;
		while (index < positions.length) {
			List<Fret> stringOptions = fingering.frets[index];
			if (positions[index] == null)
				assertEquals(0, stringOptions.size());
			else {
				assertEquals(1, stringOptions.size());
				assertEquals(positions[index], stringOptions.get(0).position);
			}
			index++;
		}
	}
}
