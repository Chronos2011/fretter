package com.github.chronos2011.fretter.domain.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PitchTest {
	@Test
	public void testPitchFromMidiIndex() {
		assertEquals(Pitch.DS8, Pitch.from(111));
	}

	@Test
	public void testGetNoteName() {
		assertEquals("D\u266F\u2088", Pitch.DS8.getName());
	}

	@Test
	public void testRaise() {
		assertEquals(Pitch.G_4, Pitch.C_4.raise(7));
		assertEquals(Pitch.C_5, Pitch.C_4.raise(12));
	}
}
