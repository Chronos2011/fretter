package com.github.chronos2011.fretter.domain.solver;

import static com.github.chronos2011.fretter.domain.solver.SolverTestUtils.assertTone;

import org.junit.Test;

import com.github.chronos2011.fretter.domain.Board;
import com.github.chronos2011.fretter.domain.library.*;
import com.github.chronos2011.fretter.options.ApplicationOptions;
import com.github.chronos2011.fretter.options.ArpeggioOptions;
import com.github.chronos2011.fretter.options.BoardOptions;

public class ArpeggioSolverTest {
	@Test
	public void testApplyArpeggio() {
		ApplicationOptions applicationOptions = new ApplicationOptions();
		applicationOptions.boardOptions = new BoardOptions();
		applicationOptions.boardOptions.fretCount = 20;
		applicationOptions.boardOptions.tuning = Tuning.EADGBE;
		applicationOptions.arpeggioOptions = new ArpeggioOptions();
		applicationOptions.arpeggioOptions.chord = Chord.MAJ7;
		applicationOptions.arpeggioOptions.pitch = Pitch.C_4;
		ArpeggioSolver solver = new ArpeggioSolver(applicationOptions);
		Solution solution = solver.solve();
		Board board = solution.board;
		// lower E string
		assertTone(board, 0, 6, Pitch.AS3, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 7, Pitch.B_3, Interval.MAJ7, Degree.D04);
		assertTone(board, 0, 8, Pitch.C_4, Interval.PER1, Degree.D01);
		assertTone(board, 0, 9, Pitch.CS4, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 10, Pitch.D_4, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 11, Pitch.DS4, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 12, Pitch.E_4, Interval.MAJ3, Degree.D02);
		assertTone(board, 0, 13, Pitch.F_4, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 14, Pitch.FS4, Interval.OUT, Degree.OUT);
		// A string
		assertTone(board, 1, 6, Pitch.DS4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 7, Pitch.E_4, Interval.MAJ3, Degree.D02);
		assertTone(board, 1, 8, Pitch.F_4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 9, Pitch.FS4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 10, Pitch.G_4, Interval.PER5, Degree.D03);
		assertTone(board, 1, 11, Pitch.GS4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 12, Pitch.A_4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 13, Pitch.AS4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 14, Pitch.B_4, Interval.MAJ7, Degree.D04);
		// D string
		assertTone(board, 2, 6, Pitch.GS4, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 7, Pitch.A_4, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 8, Pitch.AS4, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 9, Pitch.B_4, Interval.MAJ7, Degree.D04);
		assertTone(board, 2, 10, Pitch.C_5, Interval.PER1, Degree.D01);
		assertTone(board, 2, 11, Pitch.CS5, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 12, Pitch.D_5, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 13, Pitch.DS5, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 14, Pitch.E_5, Interval.MAJ3, Degree.D02);
	}
}
