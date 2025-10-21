package com.github.chronos2011.fretter.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class ListUtil implement convenience methods for writing compact code. Use sparingly!
 */
public class ListUtil {
	private ListUtil() {
		// Make class purely static
	};

	/**
	 * Creates a modifiable List of a variable number of items.
	 *
	 * @param <T>   the type of the items to be added to the List
	 * @param items the items to be added to the List
	 * @return the resulting List
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> ml(T... items) {
		return Arrays.asList(items);
	}

	/**
	 * Creates an unmodifiable List of a variable number of items.
	 *
	 * @param <T>   the type of the items to be added to the List
	 * @param items the items to be added to the List
	 * @return the resulting List
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> ul(T... items) {
		return Collections.unmodifiableList(Arrays.asList(items));
	}
}
