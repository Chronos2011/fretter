package com.github.chronos2011.fretter.presentation;

import com.github.chronos2011.fretter.domain.Board;
import com.github.chronos2011.fretter.options.ApplicationOptions;

/**
 * Interface BoardRenderer defines functionality for rendering a {@link Board}.
 */
public interface BoardRenderer {
	/**
	 * Renders a {@link Board} into a {@link StringBuilder} respecting {@link ApplicationOptions}.
	 *
	 * @param builder            the {@link StringBuilder} to render into
	 * @param applicationOptions the {@link ApplicationOptions} to be respected
	 * @param board              the {@link Board} to be rendered
	 */
	public void render(StringBuilder builder, ApplicationOptions applicationOptions, Board board);
}
