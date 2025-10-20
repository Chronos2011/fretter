package com.github.chronos2011.fretter.domain.solver;

import com.github.chronos2011.fretter.options.ApplicationOptions.Operation;

/**
 * Interface Solver defines a class that can create {@link Solution}s for specific modes of {@link Operation}.
 */
public interface Solver {
	/**
	 * Create a {@link Solution} for a specific mode of {@link Operation}.
	 *
	 * @return the {@link Solution} created
	 */
	public Solution solve();
}
