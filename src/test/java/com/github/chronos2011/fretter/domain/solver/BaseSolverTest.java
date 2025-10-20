package com.github.chronos2011.fretter.domain.solver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import com.github.chronos2011.fretter.domain.DomainException;
import com.github.chronos2011.fretter.domain.Fret;
import com.github.chronos2011.fretter.domain.Position;
import com.github.chronos2011.fretter.domain.library.*;
import com.github.chronos2011.fretter.options.ApplicationOptions;
import com.github.chronos2011.fretter.options.BoardOptions;

public class BaseSolverTest {
	ApplicationOptions applicationOptions;
	BaseSolver solver;

	@Before
	public void setup() {
		applicationOptions = new ApplicationOptions();
		applicationOptions.boardOptions = new BoardOptions();
		applicationOptions.boardOptions.fretCount = 15;
		applicationOptions.boardOptions.tuning = Tuning.EADGBE;
		solver = new BaseSolver(applicationOptions) {
			@Override
			public Solution solve() {
				throw new UnsupportedOperationException("Unimplemented method 'solve'");
			}
		};
	}

	@Test
	public void testBaseSolver() {
		assertEquals(applicationOptions, solver.applicationOptions);
		assertNotNull(solver.warnings);
		assertEquals(0, solver.warnings.size());
		assertNotNull(solver.hints);
		assertEquals(0, solver.hints.size());
	}

	@Test
	public void testCreateBoard() {
		solver.createBoard();
		assertNotNull(solver.board);
		assertEquals(15, solver.board.fretCount);
		assertEquals(6, solver.board.stringCount);
	}

	@Test
	public void testTuneBoard() {
		solver.createBoard();
		solver.tuneBoard();
		Fret fret0 = solver.board.frets[0][8];
		Fret fret1 = solver.board.frets[1][7];
		assertEquals(Pitch.C_4, fret0.pitch);
		assertEquals(Interval.OUT, fret0.interval);
		assertEquals(Degree.OUT, fret0.degree);
		assertEquals(8, fret0.position.fretIndex);
		assertEquals(0, fret0.position.stringIndex);
		assertEquals(Pitch.E_4, fret1.pitch);
		assertEquals(Interval.OUT, fret1.interval);
		assertEquals(Degree.OUT, fret1.degree);
		assertEquals(7, fret1.position.fretIndex);
		assertEquals(1, fret1.position.stringIndex);
	}

	@Test
	public void testApplyIntervalList() {
		solver.createBoard();
		solver.tuneBoard();
		solver.applyIntervalList(Scale.MAJOR.intervalList, Pitch.C_4, solver.board.frets[0][8]);
		solver.applyIntervalList(Scale.MAJOR.intervalList, Pitch.C_4, solver.board.frets[1][6]);
		Fret fret0 = solver.board.frets[0][8];
		Fret fret1 = solver.board.frets[1][6];
		assertEquals(Pitch.C_4, fret0.pitch);
		assertEquals(Interval.PER1, fret0.interval);
		assertEquals(Degree.D01, fret0.degree);
		assertEquals(8, fret0.position.fretIndex);
		assertEquals(0, fret0.position.stringIndex);
		assertEquals(Pitch.DS4, fret1.pitch);
		assertEquals(Interval.OUT, fret1.interval);
		assertEquals(Degree.OUT, fret1.degree);
		assertEquals(6, fret1.position.fretIndex);
		assertEquals(1, fret1.position.stringIndex);
	}

	@Test
	public void testFindUp() throws DomainException {
		solver.createBoard();
		solver.tuneBoard();
		Fret fret = solver.findUp(new Position(0, 6), f -> f.pitch.getName().charAt(0) == 'E');
		assertEquals(0, fret.position.stringIndex);
		assertEquals(12, fret.position.fretIndex);
		fret = solver.findUp(new Position(6), f -> f.pitch.getName().charAt(0) == 'E');
		assertEquals(1, fret.position.stringIndex);
		assertEquals(7, fret.position.fretIndex);
	}

	@Test
	public void testFindUpDown() throws DomainException {
		solver.createBoard();
		solver.tuneBoard();
		Fret fret = solver.findUpDown(new Position(0, 6), f -> f.pitch.getName().charAt(0) == 'E');
		assertEquals(0, fret.position.stringIndex);
		assertEquals(12, fret.position.fretIndex);
		fret = solver.findUpDown(new Position(4), f -> f.pitch.getName().charAt(0) == 'E');
		assertEquals(4, fret.position.stringIndex);
		assertEquals(5, fret.position.fretIndex);
	}

	@Test
	public void testFind_Up() throws DomainException {
		solver.createBoard();
		solver.tuneBoard();
		Fret fret = solver.find(new Position(0, 6), true, false, f -> f.pitch.getName().charAt(0) == 'E');
		assertEquals(0, fret.position.stringIndex);
		assertEquals(12, fret.position.fretIndex);
		fret = solver.find(new Position(6), true, false, f -> f.pitch.getName().charAt(0) == 'E');
		assertEquals(1, fret.position.stringIndex);
		assertEquals(7, fret.position.fretIndex);
	}

	@Test
	public void testFind_Down() throws DomainException {
		solver.createBoard();
		solver.tuneBoard();
		Fret fret = solver.find(new Position(0, 6), false, true, f -> f.pitch.getName().charAt(0) == 'E');
		assertEquals(0, fret.position.stringIndex);
		assertEquals(0, fret.position.fretIndex);
		fret = solver.find(new Position(6), false, true, f -> f.pitch.getName().charAt(0) == 'E');
		assertEquals(4, fret.position.stringIndex);
		assertEquals(5, fret.position.fretIndex);
	}

	@Test
	public void testFind_throw() {
		solver.createBoard();
		solver.tuneBoard();
		assertThrows(DomainException.class, () -> solver.find(new Position(12), true, true, f -> false));
	}
}
