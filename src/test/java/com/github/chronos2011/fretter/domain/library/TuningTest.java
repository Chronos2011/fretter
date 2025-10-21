package com.github.chronos2011.fretter.domain.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TuningTest {
	@Test
	public void testFromName() {
		assertEquals(Tuning.E3A3D4G4B4E5, Tuning.fromName("standard guitar"));
		assertEquals(Tuning.E3A3D4G4B4E5, Tuning.fromName("eadgbe"));
		assertEquals(null, Tuning.fromName("standard"));
	}

	@Test
	public void testGetName() {
		assertEquals("standard ukulele", Tuning.fromName("standard ukulele").getName());
	}

	@Test
	public void testGetAlternativeNames() {
		assertEquals(1, Tuning.fromName("standard ukulele").getAlternativeNames().size());
		assertEquals("GCEA", Tuning.fromName("standard ukulele").getAlternativeNames().get(0));
	}
}
