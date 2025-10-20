package com.github.chronos2011.fretter.presentation;

import com.github.chronos2011.fretter.application.Configuration;
import com.github.chronos2011.fretter.domain.Board;
import com.github.chronos2011.fretter.domain.Fingering;
import com.github.chronos2011.fretter.domain.FretWindow;
import com.github.chronos2011.fretter.domain.library.Degree;
import com.github.chronos2011.fretter.domain.solver.Solution;
import com.github.chronos2011.fretter.options.ApplicationOptions;
import com.github.chronos2011.fretter.options.RenderOptions;
import com.github.chronos2011.fretter.options.RenderOptions.FretLabeling;

/**
 * Class ConsoleSolutionRenderer renders a {@link Solution} to be printed onto the console.
 */
public class ConsoleSolutionRenderer implements SolutionRenderer {
	public static final String NEWLINE = System.lineSeparator();

	@Override
	public StringBuilder render(ApplicationOptions applicationOptions, Solution solution) {
		StringBuilder builder = new StringBuilder();
		RenderOptions renderOptions = applicationOptions.renderOptions;
		BoardRenderer renderer = new ConsoleBoardRenderer();
		if (solution.fingerings == null)
			renderer.render(builder, applicationOptions, solution.board);
		else {
			int count = solution.fingerings.size();
			if (count == 0)
				builder.append("No fingerings found with current options\n");
			else {
				int indexFrom = Math.min(count, renderOptions.page * renderOptions.itemCount);
				int indexTo = Math.min(count, (renderOptions.page + 1) * renderOptions.itemCount);
				builder.append(String.format("Showing fingerings %d through %d", indexFrom + 1, indexTo));
				builder.append(String.format(" (of %d)\n", count));
				for (int fingeringIndex = indexFrom; fingeringIndex < indexTo; fingeringIndex++) {
					Fingering fingering = solution.fingerings.get(fingeringIndex);
					Board fingeringBoard = fingering.asBoard(applicationOptions.boardOptions);
					renderer.render(builder, applicationOptions, fingeringBoard);
				}
			}
		}
		addWarnings(applicationOptions, solution);
		renderWarnings(solution, builder);
		addHints(applicationOptions, solution);
		renderHints(solution, builder);
		return builder;
	}

	private void addHints(ApplicationOptions applicationOptions, Solution solution) {
		if (applicationOptions.renderOptions.fretLabeling == FretLabeling.NOTE_NAME) {
			solution.hints.add("Be aware that octave indices displayed conform to"
					+ " MIDI pitch naming (where middle C corresponds to C"
					+ (!Configuration.hostIsWindows ? "\u2084" : "4") + ").");
		}
	}

	private void addWarnings(ApplicationOptions applicationOptions, Solution solution) {
		FretWindow renderWindow = applicationOptions.renderOptions.renderWindow;
		if (renderWindow != null) {
			FretWindow usedFrets = new FretWindow();
			usedFrets.includeOpen = false;
			usedFrets.start = Integer.MAX_VALUE;
			usedFrets.end = Integer.MIN_VALUE;
			if (solution.fingerings == null)
				usedFrets.extendBy(findUsedFrets(solution.board));
			else
				for (Fingering fingering : solution.fingerings)
					usedFrets.extendBy(findUsedFrets(fingering.asBoard(applicationOptions.boardOptions)));
			boolean fretsBeforeRenderWindow = usedFrets.start < renderWindow.start;
			boolean fretsAfterRenderWindow = usedFrets.end > renderWindow.end;
			boolean openStringsHidden = usedFrets.includeOpen && !renderWindow.includeOpen;
			if (fretsBeforeRenderWindow || fretsAfterRenderWindow || openStringsHidden)
				solution.warnings.add("Notes exist outside the defined render window!");
		}
	}

	private void renderHints(Solution solution, StringBuilder builder) {
		for (String hint : solution.hints) {
			builder.append(" * Hint: ");
			builder.append(hint);
			builder.append(" *");
			builder.append(NEWLINE);
		}
	}

	private void renderWarnings(Solution solution, StringBuilder builder) {
		for (String warning : solution.warnings) {
			builder.append(" ** WARNING: ");
			builder.append(warning);
			builder.append(" **");
			builder.append(NEWLINE);
		}
	}

	private FretWindow findUsedFrets(Board board) {
		FretWindow usedFrets = new FretWindow();
		usedFrets.includeOpen = false;
		usedFrets.start = Integer.MAX_VALUE;
		usedFrets.end = Integer.MIN_VALUE;
		for (int stringIndex = 0; stringIndex < board.stringCount; stringIndex++) {
			if (board.frets[stringIndex][0].degree != Degree.OUT)
				usedFrets.includeOpen = true;
			for (int fretIndex = 1; fretIndex < board.fretCount; fretIndex++) {
				if (usedFrets.start > fretIndex && board.frets[stringIndex][fretIndex].degree != Degree.OUT)
					usedFrets.start = fretIndex;
				if (usedFrets.end < fretIndex && board.frets[stringIndex][fretIndex].degree != Degree.OUT)
					usedFrets.end = fretIndex;
			}
		}
		return usedFrets;
	}
}
