package com.github.chronos2011.fretter.presentation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.chronos2011.fretter.domain.Board;
import com.github.chronos2011.fretter.domain.Fret;
import com.github.chronos2011.fretter.domain.FretWindow;
import com.github.chronos2011.fretter.domain.library.Interval;
import com.github.chronos2011.fretter.domain.library.Tuning;
import com.github.chronos2011.fretter.options.ApplicationOptions;
import com.github.chronos2011.fretter.options.BoardOptions;
import com.github.chronos2011.fretter.options.RenderOptions;
import com.github.chronos2011.fretter.options.RenderOptions.FretLabeling;

public class ConsoleBoardRendererTest {
	ApplicationOptions applicationOptions;
	Board board;
	StringBuilder builder;
	ConsoleBoardRenderer renderer;

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
		board = new Board(applicationOptions.boardOptions);
		builder = new StringBuilder();
		renderer = new ConsoleBoardRenderer();
	}

	@Test
	public void testRender() {
		renderer.render(builder, applicationOptions, board);
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
	public void testRender_NonContiguous() {
		applicationOptions.renderOptions.renderWindow.start = 2;
		renderer.render(builder, applicationOptions, board);
		assertEquals("\n" + //
				" 0           2       3       4   \n" + //
				"\n" + //
				"   ├ ··· ┼───────┼───────┼───────┼\n" + //
				"   ├ ··· ┼───────┼───────┼───────┼\n" + //
				"   ├ ··· ┼───────┼───────┼───────┼\n" + //
				"   ├ ··· ┼───────┼───────┼───────┼\n" + //
				"\n" + //
				" 0           2       3       4   \n" + //
				"\n" + //
				"", builder.toString());
	}

	@Test
	public void testRender_NoWindow() {
		applicationOptions.renderOptions.renderWindow = null;
		applicationOptions.renderOptions.fretLabeling = FretLabeling.INTERVAL_SYMBOL;
		for (int stringIndex = 0; stringIndex < board.stringCount; stringIndex++) {
			for (int fretIndex = 0; fretIndex < board.fretCount; fretIndex++) {
				Fret fret = board.frets[stringIndex][fretIndex];
				fret.pitch = applicationOptions.boardOptions.tuning.pitches.get(stringIndex).raise(fretIndex);
			}
		}
		board.frets[0][0].interval = Interval.PER1;
		board.frets[1][1].interval = Interval.PER4;
		board.frets[2][3].interval = Interval.PER5;
		board.frets[3][4].interval = Interval.MIN7;
		renderer.render(builder, applicationOptions, board);
		assertEquals("\n" + //
				" 0     1       2       3       4   \n" + //
				"\n" + //
				"   ├───────┼───────┼───────┼──♭7 ──┼\n" + //
				"   ├───────┼───────┼── 5 ──┼───────┼\n" + //
				"   ├── 4 ──┼───────┼───────┼───────┼\n" + //
				" 1 ├───────┼───────┼───────┼───────┼\n" + //
				"\n" + //
				" 0     1       2       3       4   \n" + //
				"\n" + //
				"", builder.toString());
	}
}
