package com.github.chronos2011.fretter.domain.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ScaleTest {
	@Test
	public void testToString() {
		assertEquals("minor pentatonic", Scale.MINOR_PENTATONIC.toString());
	}
}
