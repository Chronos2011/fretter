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
		// Showing the complete board effectively means applying a chromatic scale
		Pitch boardRoot = applicationOptions.boardOptions.tuning.pitches.get(0);
		visitBoard(fret -> applyIntervalList(Scale.fromName("chromatic").intervalList, boardRoot, fret));
		return new Solution(board, null, warnings, hints);
	}
}
