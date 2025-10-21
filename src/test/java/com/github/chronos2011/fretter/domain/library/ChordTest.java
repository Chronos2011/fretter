package com.github.chronos2011.fretter.domain.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ChordTest {
	@Test public void testFromName()
	{
		assertEquals(Chord.N47, Chord.fromName("major"));
		assertEquals(Chord.N47, Chord.fromName("maj"));
		assertEquals(null, Chord.fromName("m"));
	}

	@Test
	public void testGetName() {
		assertEquals("augmented seventh", Chord.fromName("aug7").getName());
	}

	@Test
	public void testGetAlternativeName() {
		assertEquals(1, Chord.fromName("augmented seventh").getAlternativeNames().size());
		assertEquals("aug7", Chord.fromName("augmented seventh").getAlternativeNames().get(0));
	}
}
