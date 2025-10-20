package com.github.chronos2011.fretter.presentation;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.github.chronos2011.fretter.domain.*;
import com.github.chronos2011.fretter.domain.library.*;
import com.github.chronos2011.fretter.domain.solver.Solution;
import com.github.chronos2011.fretter.options.ApplicationOptions;
import com.github.chronos2011.fretter.options.BoardOptions;
import com.github.chronos2011.fretter.options.ChordOptions;
import com.github.chronos2011.fretter.options.RenderOptions;
import com.github.chronos2011.fretter.options.RenderOptions.FretLabeling;

public class ConsoleSolutionRendererTest {
	ApplicationOptions applicationOptions;
	Board board;
	Solution solution;
	ConsoleSolutionRenderer renderer;

	@Before
	public void setup() {
		applicationOptions = new ApplicationOptions();
		applicationOptions.renderOptions = new RenderOptions();
		applicationOptions.renderOptions.renderWindow = new FretWindow();
		applicationOptions.renderOptions.renderWindow.start = 0;
		applicationOptions.renderOptions.renderWindow.end = 4;
		applicationOptions.renderOptions.renderWindow.includeOpen = true;
		applicationOptions.boardOptions = new BoardOptions();
		applicationOptions.boardOptions.tuning = Tuning.STANDARD_UKULELE;
		applicationOptions.boardOptions.fretCount = 4;
		applicationOptions.chordOptions = new ChordOptions();
		applicationOptions.chordOptions.chord = Chord.MAJ;
		applicationOptions.chordOptions.pitch = Pitch.C_4;
		applicationOptions.chordOptions.position = new Position(0, 8);
		board = new Board(applicationOptions.boardOptions);
		solution = new Solution(board, null, new ArrayList<String>(), new ArrayList<String>());
		renderer = new ConsoleSolutionRenderer();
	}

	@Test
	public void testRenderBoard() {
		StringBuilder builder = renderer.render(applicationOptions, solution);
		assertEquals("\n" + //
				" 0     1       2       3       4   \n" + //
				"\n" + //
				"   ├───────┼───────┼───────┼───────┼\n" + //
				"   ├───────┼───────┼───────┼───────┼\n" + //
				"   ├───────┼───────┼───────┼───────┼\n" + //
				"   ├───────┼───────┼───────┼───────┼\n" + //
				"\n" + //
				" 0     1       2       3       4   \n" + //
				"\n" + //
				"", builder.toString());
	}

	@Test
	public void testRenderFingerings() {
		applicationOptions.renderOptions.renderWindow = null;
		applicationOptions.renderOptions.fretLabeling = FretLabeling.INTERVAL_SYMBOL;
		applicationOptions.renderOptions.page = 0;
		applicationOptions.renderOptions.itemCount = 1;
		for (int stringIndex = 0; stringIndex < board.stringCount; stringIndex++) {
			for (int fretIndex = 0; fretIndex < board.fretCount; fretIndex++) {
				Fret fret = board.frets[stringIndex][fretIndex];
				fret.pitch = applicationOptions.boardOptions.tuning.pitches.get(stringIndex).raise(fretIndex);
			}
		}
		board.frets[0][0].interval = Interval.PER1;
		board.frets[0][0].degree = Degree.D01;
		board.frets[3][4].interval = Interval.MIN7;
		board.frets[3][4].degree = Degree.D07;
		Fingering fingering0 = mock(Fingering.class);
		Fingering fingering1 = mock(Fingering.class);
		when(fingering0.asBoard(any())).thenReturn(board);
		when(fingering1.asBoard(any())).thenReturn(board);
		solution = new Solution(board, new ArrayList<>(), new ArrayList<String>(), new ArrayList<String>());
		solution.fingerings.add(fingering0);
		solution.fingerings.add(fingering1);
		StringBuilder builder = renderer.render(applicationOptions, solution);
		assertEquals("Showing fingerings 1 through 1 (of 2)\n" + //
				"\n" + //
				" 0           4   \n" + //
				"\n" + //
				"   ├ ··· ┼──♭7 ──┼\n" + //
				"   ├ ··· ┼───────┼\n" + //
				"   ├ ··· ┼───────┼\n" + //
				" 1 ├ ··· ┼───────┼\n" + //
				"\n" + //
				" 0           4   \n" + //
				"\n", builder.toString());
	}

	@Test
	public void testRenderFingeringOutsideRenderWindow() {
		applicationOptions.renderOptions.renderWindow.start = 2;
		applicationOptions.renderOptions.renderWindow.end = 4;
		applicationOptions.renderOptions.page = 0;
		applicationOptions.renderOptions.itemCount = 1;
		applicationOptions.renderOptions.fretLabeling = FretLabeling.INTERVAL_SYMBOL;
		for (int stringIndex = 0; stringIndex < board.stringCount; stringIndex++) {
			for (int fretIndex = 0; fretIndex < board.fretCount; fretIndex++) {
				Fret fret = board.frets[stringIndex][fretIndex];
				fret.pitch = applicationOptions.boardOptions.tuning.pitches.get(stringIndex).raise(fretIndex);
			}
		}
		board.frets[0][0].interval = Interval.PER1;
		board.frets[0][0].degree = Degree.D01;
		board.frets[3][1].interval = Interval.MIN7;
		board.frets[3][1].degree = Degree.D07;
		Fingering fingering = mock(Fingering.class);
		when(fingering.asBoard(any())).thenReturn(board);
		solution = new Solution(board, new ArrayList<>(), new ArrayList<String>(), new ArrayList<String>());
		solution.fingerings.add(fingering);
		StringBuilder builder = renderer.render(applicationOptions, solution);
		assertEquals("Showing fingerings 1 through 1 (of 1)\n" + //
				"\n" + //
				" 0           2       3       4   \n" + //
				"\n" + //
				"   ├ ··· ┼───────┼───────┼───────┼\n" + //
				"   ├ ··· ┼───────┼───────┼───────┼\n" + //
				"   ├ ··· ┼───────┼───────┼───────┼\n" + //
				" 1 ├ ··· ┼───────┼───────┼───────┼\n" + //
				"\n" + //
				" 0           2       3       4   \n" + //
				"\n" + //
				" ** WARNING: Notes exist outside the defined render window! **\n" + //
				"", builder.toString());
	}

	@Test
	public void testRenderWarnings() {
		solution.warnings.add("W0");
		solution.warnings.add("W1");
		StringBuilder builder = renderer.render(applicationOptions, solution);
		assertEquals("\n" + //
				" 0     1       2       3       4   \n" + //
				"\n" + //
				"   ├───────┼───────┼───────┼───────┼\n" + //
				"   ├───────┼───────┼───────┼───────┼\n" + //
				"   ├───────┼───────┼───────┼───────┼\n" + //
				"   ├───────┼───────┼───────┼───────┼\n" + //
				"\n" + //
				" 0     1       2       3       4   \n" + //
				"\n" + //
				" ** WARNING: W0 **\n" + //
				" ** WARNING: W1 **\n" + //
				"", builder.toString());
	}

	@Test
	public void testRenderHints() {
		applicationOptions.renderOptions.fretLabeling = FretLabeling.NOTE_NAME;
		solution.hints.add("H0");
		solution.hints.add("H1");
		StringBuilder builder = renderer.render(applicationOptions, solution);
		assertEquals("\n" + //
				" 0     1       2       3       4   \n" + //
				"\n" + //
				"   ├───────┼───────┼───────┼───────┼\n" + //
				"   ├───────┼───────┼───────┼───────┼\n" + //
				"   ├───────┼───────┼───────┼───────┼\n" + //
				"   ├───────┼───────┼───────┼───────┼\n" + //
				"\n" + //
				" 0     1       2       3       4   \n" + //
				"\n" + //
				" * Hint: H0 *\n" + //
				" * Hint: H1 *\n" + //
				" * Hint: Be aware that octave indices displayed conform to MIDI pitch naming (where middle C corresponds to C₄). *\n"
				+ "", builder.toString());
	}
}
