package com.github.chronos2011.fretter.domain.solver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.chronos2011.fretter.domain.library.Interval;
import com.github.chronos2011.fretter.domain.library.Tuning;
import com.github.chronos2011.fretter.options.ApplicationOptions;
import com.github.chronos2011.fretter.options.BoardOptions;

public class BoardSolverTest {
	@Test
	public void testApplyAll() {
		ApplicationOptions applicationOptions = new ApplicationOptions();
		applicationOptions.boardOptions = new BoardOptions();
		applicationOptions.boardOptions.fretCount = 20;
		applicationOptions.boardOptions.tuning = Tuning.DADGAD;
		BoardSolver solver = new BoardSolver(applicationOptions);
		Solution solution = solver.solve();
		assertEquals(6, solution.board.stringCount);
		for (int stringIndex = 0; stringIndex < solution.board.stringCount; stringIndex++)
			assertEquals(Tuning.DADGAD.pitches.get(stringIndex), solution.board.frets[stringIndex][0].pitch);
		assertEquals(20, solution.board.fretCount);
		for (int stringIndex = 0; stringIndex < solution.board.stringCount; stringIndex++)
			for (int fretIndex = 0; fretIndex < solution.board.fretCount; fretIndex++)
				assertTrue(solution.board.frets[stringIndex][fretIndex].interval != Interval.OUT);
	}
}
