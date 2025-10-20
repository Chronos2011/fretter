package com.github.chronos2011.fretter.domain.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ChordTest {
	@Test
	public void testToString() {
		assertEquals("aug7", Chord.AUG7.toString());
	}
}
