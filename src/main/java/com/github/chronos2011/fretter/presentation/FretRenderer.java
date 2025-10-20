package com.github.chronos2011.fretter.presentation;

import com.github.chronos2011.fretter.domain.Fret;

/**
 * Interface FretRenderer defines functionality to render a {@link Fret}.
 */
public interface FretRenderer {
	/**
	 * Returns a {@link String} representing a {@link Fret}.
	 *
	 * @param fret the {@link Fret} to be rendered
	 * @return the rendered {@link Fret}
	 */
	public String render(Fret fret);
}
