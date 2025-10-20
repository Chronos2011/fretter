package com.github.chronos2011.fretter.domain.solver;

import java.util.ArrayList;
import java.util.List;

import com.github.chronos2011.fretter.domain.*;
import com.github.chronos2011.fretter.domain.library.Degree;
import com.github.chronos2011.fretter.domain.library.Interval;
import com.github.chronos2011.fretter.domain.library.Pitch;
import com.github.chronos2011.fretter.domain.library.Scale;
import com.github.chronos2011.fretter.options.ApplicationOptions;
import com.github.chronos2011.fretter.options.ScaleOptions;

/**
 * Class ScaleSolver implements solving functionality for {@link Operation#SCALE}.
 */
public class ScaleSolver extends BaseSolver {
	private final List<Fret> markedFrets = new ArrayList<>();

	/**
	 * Creates a new ScaleSolver with {@link ApplicationOptions}.
	 *
	 * @param applicationOptions the {@link ApplicationOptions} to be used
	 */
	public ScaleSolver(ApplicationOptions applicationOptions) {
		super(applicationOptions);
	}

	@Override
	public Solution solve() {
		createBoard();
		tuneBoard();
		ScaleOptions options = applicationOptions.scaleOptions;
		visitBoard(fret -> applyIntervalList(options.scale.intervalList, options.pitch, fret));
		if (options.pattern != null)
			solveWithDistributionPattern();
		else if (options.window != null)
			solveWithWorkingWindow();
		return new Solution(board, null, warnings, hints);
	}

	/**
	 * Applies a distribution pattern to the {@link Board}.
	 */
	private void solveWithDistributionPattern() {
		try {
			ScaleOptions.DistributionPattern pattern = applicationOptions.scaleOptions.pattern;
			Scale scale = applicationOptions.scaleOptions.scale;
			Position position = findUpDown(pattern.position, (fret) -> fret.degree != Degree.OUT).position;
			Degree degreeToFind = board.frets[position.stringIndex][position.fretIndex].degree;
			Pitch pitchToFind = board.frets[position.stringIndex][position.fretIndex].pitch;
			while (position.stringIndex < board.stringCount) {
				int foundOnString = 0;
				Fret next = null;
				while (foundOnString < pattern.notesPerString) {
					final Degree degree = degreeToFind;
					final Pitch pitch = pitchToFind;
					if (foundOnString == 0)
						next = findUpDown(position, (fret) -> fret.degree == degree && fret.pitch == pitch);
					else
						next = findUp(next.position, (fret) -> fret.degree == degree && fret.pitch == pitch);
					mark(next);
					foundOnString++;
					int stepsToRaise = -scale.intervalList.get(degreeToFind.index).steps;
					degreeToFind = degreeToFind.raise(1, scale.intervalList.size());
					stepsToRaise += scale.intervalList.get(degreeToFind.index).steps + Interval.PER8.steps;
					stepsToRaise %= Interval.PER8.steps;
					pitchToFind = pitchToFind.raise(stepsToRaise);
				}
				if (foundOnString != pattern.notesPerString)
					throw new DomainException();
				position = new Position(position.stringIndex + 1, position.fretIndex);
			}
		} catch (DomainException exception) {
			warnings.add("Distribution pattern could not be applied (fully)");
		}
		clearUnmarked();
	}

	/**
	 * Applies a working window to the {@link Board}.
	 */
	private void solveWithWorkingWindow() {
		FretWindow window = applicationOptions.scaleOptions.window;
		visitBoard(fret -> {
			int fretIndex = fret.position.fretIndex;
			if (fretIndex < window.start || fretIndex > window.end)
				fret.setOutOfScale();
		});
	}

	private void mark(Fret fret) {
		markedFrets.add(fret);
	}

	private void clearUnmarked() {
		visitBoard(fret -> {
			if (!markedFrets.contains(fret))
				fret.setOutOfScale();
		});
	}
}
