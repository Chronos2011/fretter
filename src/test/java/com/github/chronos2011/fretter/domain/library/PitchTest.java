package com.github.chronos2011.fretter.domain.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PitchTest {
	@Test
	public void testPitchFromMidiIndex() {
		assertEquals(Pitch.DS8, Pitch.from(111));
		assertEquals(null, Pitch.from(1111));
	}

	@Test
	public void testPitchFromConstantName() {
		assertEquals(Pitch.DS8, Pitch.from("DS8"));
		assertEquals(null, Pitch.from("TEST"));
	}

	@Test
	public void testRaise() {
		assertEquals(Pitch.G_4, Pitch.C_4.raise(7));
		assertEquals(Pitch.C_5, Pitch.C_4.raise(12));
	}

	@Test public void getPitchClass() {
		assertEquals(PitchClass.CS, Pitch.CS7.getPitchClass());
	}

	@Test
	public void testGetNoteName() {
		assertEquals("???", Pitch.UNKNOWN.getNoteName());
		assertEquals("D\u266F\u2088", Pitch.DS8.getNoteName());
		assertEquals("D\u266D\u208B", Pitch.DFM.getNoteName());
	}

	@Test
	public void testGetName() {
		assertEquals("DS8", Pitch.DS8.getName());
	}

	@Test
	public void testGetAlternativeNames() {
		assertEquals(0, Pitch.DS8.getAlternativeNames().size());
	}
}
