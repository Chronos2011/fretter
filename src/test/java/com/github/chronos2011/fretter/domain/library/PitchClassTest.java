package com.github.chronos2011.fretter.domain.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PitchClassTest {
	@Test
	public void testGetTargetPitch() {
		assertEquals(Pitch.AS0, PitchClass.AS.getTargetPitch());
	}

	@Test
	public void testGetNoteName() {
		assertEquals("??", PitchClass.UNKNOWN.getNoteName());
		assertEquals("D\u266F", PitchClass.DS.getNoteName());
		assertEquals("D\u266D", PitchClass.DF.getNoteName());
	}

	@Test
	public void testGetName() {
		assertEquals("D#", PitchClass.DS.getName());
	}

	@Test
	public void testGetAlternativeNames() {
		assertEquals(0, PitchClass.DS.getAlternativeNames().size());
	}
}
