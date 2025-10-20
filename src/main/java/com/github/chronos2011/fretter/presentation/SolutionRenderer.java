package com.github.chronos2011.fretter.presentation;

import com.github.chronos2011.fretter.domain.solver.Solution;
import com.github.chronos2011.fretter.options.ApplicationOptions;

/**
 * Interface SolutionRenderer defines functionality for rendering a {@link Solution}.
 */
public interface SolutionRenderer {
	/**
	 * Renders a {@link Solution} into a {@link StringBuilder} respecting {@link ApplicationOptions}.
	 *
	 * @param applicationOptions the {@link ApplicationOptions} to be respected
	 * @param solution           the {@link Solution} to be rendered
	 * @return the {@link StringBuilder} that was rendered into
	 */
	public StringBuilder render(ApplicationOptions applicationOptions, Solution solution);
}
