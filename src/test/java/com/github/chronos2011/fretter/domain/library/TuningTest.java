package com.github.chronos2011.fretter.domain.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TuningTest {
	@Test
	public void testToString() {
		assertEquals("standard ukulele", Tuning.STANDARD_UKULELE.toString());
	}
}
