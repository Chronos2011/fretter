package com.github.chronos2011.fretter.domain.library;

import java.util.List;

/**
 * Interface Nameable defines functionality for objects that have names and alternative names.
 */
public interface Nameable {
	/**
	 * Return the default name.
	 *
	 * @return the default name
	 */
	public String getName();

	/**
	 * Return alternative names, if available.
	 *
	 * @return a List of alternative names
	 */
	public List<String> getAlternativeNames();
}
