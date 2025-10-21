package com.github.chronos2011.fretter.domain.solver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.chronos2011.fretter.domain.Board;
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
		applicationOptions.boardOptions.tuning = Tuning.fromName("DADGAD");
		BoardSolver solver = new BoardSolver(applicationOptions);
		Solution solution = solver.solve();
		Board board = solution.board;
		assertEquals(6, board.stringCount);
		for (int stringIndex = 0; stringIndex < board.stringCount; stringIndex++)
			assertEquals(Tuning.fromName("DADGAD").pitches.get(stringIndex), board.frets[stringIndex][0].pitch);
		assertEquals(20, board.fretCount);
		for (int stringIndex = 0; stringIndex < board.stringCount; stringIndex++)
			for (int fretIndex = 0; fretIndex < board.fretCount; fretIndex++)
				assertTrue(board.frets[stringIndex][fretIndex].interval != Interval.OUT);
	}
}
