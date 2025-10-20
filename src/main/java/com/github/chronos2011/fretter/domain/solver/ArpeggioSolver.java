package com.github.chronos2011.fretter.domain.solver;

import com.github.chronos2011.fretter.options.ApplicationOptions;
import com.github.chronos2011.fretter.options.ApplicationOptions.Operation;
import com.github.chronos2011.fretter.options.ArpeggioOptions;

/**
 * Class ArpeggioSolver implements solving functionality for {@link Operation#ARPEGGIO}.
 */
public class ArpeggioSolver extends BaseSolver {
	/**
	 * Creates a new ArpeggioSolver with {@link ApplicationOptions}.
	 *
	 * @param applicationOptions the {@link ApplicationOptions} to be used
	 */
	public ArpeggioSolver(ApplicationOptions applicationOptions) {
		super(applicationOptions);
	}

	@Override
	public Solution solve() {
		createBoard();
		tuneBoard();
		ArpeggioOptions options = applicationOptions.arpeggioOptions;
		visitBoard(fret -> applyIntervalList(options.chord.intervalList, options.pitch, fret));
		return new Solution(board, null, warnings, hints);
	}
}
