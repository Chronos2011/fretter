package com.github.chronos2011.fretter.domain.solver;

import static com.github.chronos2011.fretter.domain.solver.SolverTestUtils.assertTone;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.github.chronos2011.fretter.domain.Board;
import com.github.chronos2011.fretter.domain.DomainException;
import com.github.chronos2011.fretter.domain.FretWindow;
import com.github.chronos2011.fretter.domain.Position;
import com.github.chronos2011.fretter.domain.library.*;
import com.github.chronos2011.fretter.options.ApplicationOptions;
import com.github.chronos2011.fretter.options.BoardOptions;
import com.github.chronos2011.fretter.options.ScaleOptions;

public class ScaleSolverTest {
	ApplicationOptions applicationOptions;

	@Before
	public void setup() {
		applicationOptions = new ApplicationOptions();
		applicationOptions.boardOptions = new BoardOptions();
		applicationOptions.boardOptions.fretCount = 24;
		applicationOptions.boardOptions.tuning = Tuning.EADGBE;

	}

	@Test
	public void testSolve() {
		applicationOptions.scaleOptions = new ScaleOptions();
		applicationOptions.scaleOptions.scale = Scale.MAJOR;
		applicationOptions.scaleOptions.pitch = Pitch.C_4;
		applicationOptions.scaleOptions.pattern = null;
		applicationOptions.scaleOptions.window = null;
		ScaleSolver solver = new ScaleSolver(applicationOptions);
		Solution solution = solver.solve();
		Board board = solution.board;
		// lower E string
		assertTone(board, 0, 6, Pitch.AS3, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 7, Pitch.B_3, Interval.MAJ7, Degree.D07);
		assertTone(board, 0, 8, Pitch.C_4, Interval.PER1, Degree.D01);
		assertTone(board, 0, 9, Pitch.CS4, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 10, Pitch.D_4, Interval.MAJ2, Degree.D02);
		assertTone(board, 0, 11, Pitch.DS4, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 12, Pitch.E_4, Interval.MAJ3, Degree.D03);
		assertTone(board, 0, 13, Pitch.F_4, Interval.PER4, Degree.D04);
		assertTone(board, 0, 14, Pitch.FS4, Interval.OUT, Degree.OUT);
		// A string
		assertTone(board, 1, 6, Pitch.DS4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 7, Pitch.E_4, Interval.MAJ3, Degree.D03);
		assertTone(board, 1, 8, Pitch.F_4, Interval.PER4, Degree.D04);
		assertTone(board, 1, 9, Pitch.FS4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 10, Pitch.G_4, Interval.PER5, Degree.D05);
		assertTone(board, 1, 11, Pitch.GS4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 12, Pitch.A_4, Interval.MAJ6, Degree.D06);
		assertTone(board, 1, 13, Pitch.AS4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 14, Pitch.B_4, Interval.MAJ7, Degree.D07);
		// D string
		assertTone(board, 2, 6, Pitch.GS4, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 7, Pitch.A_4, Interval.MAJ6, Degree.D06);
		assertTone(board, 2, 8, Pitch.AS4, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 9, Pitch.B_4, Interval.MAJ7, Degree.D07);
		assertTone(board, 2, 10, Pitch.C_5, Interval.PER1, Degree.D01);
		assertTone(board, 2, 11, Pitch.CS5, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 12, Pitch.D_5, Interval.MAJ2, Degree.D02);
		assertTone(board, 2, 13, Pitch.DS5, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 14, Pitch.E_5, Interval.MAJ3, Degree.D03);
	}

	@Test
	public void testSolve_WorkingWindow() {
		applicationOptions.scaleOptions = new ScaleOptions();
		applicationOptions.scaleOptions.scale = Scale.MAJOR;
		applicationOptions.scaleOptions.pitch = Pitch.C_4;
		applicationOptions.scaleOptions.pattern = null;
		applicationOptions.scaleOptions.window = new FretWindow();
		applicationOptions.scaleOptions.window.start = 8;
		applicationOptions.scaleOptions.window.end = 12;
		ScaleSolver solver = new ScaleSolver(applicationOptions);
		Solution solution = solver.solve();
		Board board = solution.board;
		// lower E string
		assertTone(board, 0, 6, Pitch.AS3, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 7, Pitch.B_3, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 8, Pitch.C_4, Interval.PER1, Degree.D01);
		assertTone(board, 0, 9, Pitch.CS4, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 10, Pitch.D_4, Interval.MAJ2, Degree.D02);
		assertTone(board, 0, 11, Pitch.DS4, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 12, Pitch.E_4, Interval.MAJ3, Degree.D03);
		assertTone(board, 0, 13, Pitch.F_4, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 14, Pitch.FS4, Interval.OUT, Degree.OUT);
		// A string
		assertTone(board, 1, 6, Pitch.DS4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 7, Pitch.E_4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 8, Pitch.F_4, Interval.PER4, Degree.D04);
		assertTone(board, 1, 9, Pitch.FS4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 10, Pitch.G_4, Interval.PER5, Degree.D05);
		assertTone(board, 1, 11, Pitch.GS4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 12, Pitch.A_4, Interval.MAJ6, Degree.D06);
		assertTone(board, 1, 13, Pitch.AS4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 14, Pitch.B_4, Interval.OUT, Degree.OUT);
		// D string
		assertTone(board, 2, 6, Pitch.GS4, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 7, Pitch.A_4, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 8, Pitch.AS4, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 9, Pitch.B_4, Interval.MAJ7, Degree.D07);
		assertTone(board, 2, 10, Pitch.C_5, Interval.PER1, Degree.D01);
		assertTone(board, 2, 11, Pitch.CS5, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 12, Pitch.D_5, Interval.MAJ2, Degree.D02);
		assertTone(board, 2, 13, Pitch.DS5, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 14, Pitch.E_5, Interval.OUT, Degree.OUT);
		// upper E string
		assertTone(board, 5, 6, Pitch.AS5, Interval.OUT, Degree.OUT);
		assertTone(board, 5, 7, Pitch.B_5, Interval.OUT, Degree.OUT);
		assertTone(board, 5, 8, Pitch.C_6, Interval.PER1, Degree.D01);
		assertTone(board, 5, 9, Pitch.CS6, Interval.OUT, Degree.OUT);
		assertTone(board, 5, 10, Pitch.D_6, Interval.MAJ2, Degree.D02);
		assertTone(board, 5, 11, Pitch.DS6, Interval.OUT, Degree.OUT);
		assertTone(board, 5, 12, Pitch.E_6, Interval.MAJ3, Degree.D03);
		assertTone(board, 5, 13, Pitch.F_6, Interval.OUT, Degree.OUT);
		assertTone(board, 5, 14, Pitch.FS6, Interval.OUT, Degree.OUT);
	}

	@Test
	public void testSolve_DistributionPattern() throws IOException {
		applicationOptions.scaleOptions = new ScaleOptions();
		applicationOptions.scaleOptions.scale = Scale.MAJOR;
		applicationOptions.scaleOptions.pitch = Pitch.C_4;
		applicationOptions.scaleOptions.pattern = new ScaleOptions.DistributionPattern();
		applicationOptions.scaleOptions.pattern.position = new Position(0, 8);
		applicationOptions.scaleOptions.pattern.notesPerString = 3;
		applicationOptions.scaleOptions.window = null;
		ScaleSolver solver = new ScaleSolver(applicationOptions);
		Solution solution = solver.solve();
		Board board = solution.board;
		// lower E string
		assertTone(board, 0, 6, Pitch.AS3, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 7, Pitch.B_3, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 8, Pitch.C_4, Interval.PER1, Degree.D01);
		assertTone(board, 0, 9, Pitch.CS4, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 10, Pitch.D_4, Interval.MAJ2, Degree.D02);
		assertTone(board, 0, 11, Pitch.DS4, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 12, Pitch.E_4, Interval.MAJ3, Degree.D03);
		assertTone(board, 0, 13, Pitch.F_4, Interval.OUT, Degree.OUT);
		assertTone(board, 0, 14, Pitch.FS4, Interval.OUT, Degree.OUT);
		// A string
		assertTone(board, 1, 6, Pitch.DS4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 7, Pitch.E_4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 8, Pitch.F_4, Interval.PER4, Degree.D04);
		assertTone(board, 1, 9, Pitch.FS4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 10, Pitch.G_4, Interval.PER5, Degree.D05);
		assertTone(board, 1, 11, Pitch.GS4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 12, Pitch.A_4, Interval.MAJ6, Degree.D06);
		assertTone(board, 1, 13, Pitch.AS4, Interval.OUT, Degree.OUT);
		assertTone(board, 1, 14, Pitch.B_4, Interval.OUT, Degree.OUT);
		// D string
		assertTone(board, 2, 6, Pitch.GS4, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 7, Pitch.A_4, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 8, Pitch.AS4, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 9, Pitch.B_4, Interval.MAJ7, Degree.D07);
		assertTone(board, 2, 10, Pitch.C_5, Interval.PER1, Degree.D01);
		assertTone(board, 2, 11, Pitch.CS5, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 12, Pitch.D_5, Interval.MAJ2, Degree.D02);
		assertTone(board, 2, 13, Pitch.DS5, Interval.OUT, Degree.OUT);
		assertTone(board, 2, 14, Pitch.E_5, Interval.OUT, Degree.OUT);
		// upper E string
		assertTone(board, 5, 6, Pitch.AS5, Interval.OUT, Degree.OUT);
		assertTone(board, 5, 7, Pitch.B_5, Interval.OUT, Degree.OUT);
		assertTone(board, 5, 8, Pitch.C_6, Interval.OUT, Degree.OUT);
		assertTone(board, 5, 9, Pitch.CS6, Interval.OUT, Degree.OUT);
		assertTone(board, 5, 10, Pitch.D_6, Interval.MAJ2, Degree.D02);
		assertTone(board, 5, 11, Pitch.DS6, Interval.OUT, Degree.OUT);
		assertTone(board, 5, 12, Pitch.E_6, Interval.MAJ3, Degree.D03);
		assertTone(board, 5, 13, Pitch.F_6, Interval.PER4, Degree.D04);
		assertTone(board, 5, 14, Pitch.FS6, Interval.OUT, Degree.OUT);
	}

	@Test
	public void testSolve_Throws() throws DomainException {
		applicationOptions.scaleOptions = new ScaleOptions();
		applicationOptions.scaleOptions.scale = Scale.MAJOR;
		applicationOptions.scaleOptions.pitch = Pitch.C_4;
		applicationOptions.scaleOptions.pattern = new ScaleOptions.DistributionPattern();
		applicationOptions.scaleOptions.pattern.position = new Position(0, 8);
		applicationOptions.scaleOptions.pattern.notesPerString = 3;
		applicationOptions.scaleOptions.window = null;
		ScaleSolver solver = spy(new ScaleSolver(applicationOptions));
		doThrow(new DomainException()).when(solver).findUpDown(any(), any());
		Solution solution = solver.solve();
		assertEquals(1, solution.warnings.size());
		assertEquals("Distribution pattern could not be applied (fully)", solution.warnings.get(0));
	}
}
