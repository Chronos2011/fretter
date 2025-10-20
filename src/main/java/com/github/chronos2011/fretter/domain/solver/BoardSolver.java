package com.github.chronos2011.fretter.domain.solver;

import com.github.chronos2011.fretter.domain.library.Pitch;
import com.github.chronos2011.fretter.domain.library.Scale;
import com.github.chronos2011.fretter.options.ApplicationOptions;

/**
 * Class BoardSolver implements solving functionality for {@link Operation#BOARD}.
 */
public class BoardSolver extends BaseSolver {
	/**
	 * Creates a new BoardSolver with {@link ApplicationOptions}.
	 *
	 * @param applicationOptions the {@link ApplicationOptions} to be used
	 */
	public BoardSolver(ApplicationOptions applicationOptions) {
		super(applicationOptions);
	}

	@Override
	public Solution solve() {
		createBoard();
		tuneBoard();
		// Showing the complete board effectively means applying a {@link Scale.CHROMATIC}
		Pitch boardRoot = applicationOptions.boardOptions.tuning.pitches.get(0);
		visitBoard(fret -> applyIntervalList(Scale.CHROMATIC.intervalList, boardRoot, fret));
		return new Solution(board, null, warnings, hints);
	}
}
