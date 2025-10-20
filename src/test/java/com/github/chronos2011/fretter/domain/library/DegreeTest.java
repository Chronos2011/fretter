package com.github.chronos2011.fretter.domain.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DegreeTest {
	@Test public void testFrom() {
		assertEquals(Degree.D03, Degree.from(2));
	}

	@Test public void raise() {
		assertEquals(Degree.D04, Degree.D01.raise(3, 12));
		assertEquals(Degree.D02, Degree.D01.raise(13, 12));
	}
}
