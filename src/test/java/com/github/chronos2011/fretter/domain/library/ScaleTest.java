package com.github.chronos2011.fretter.domain.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ScaleTest {
	@Test
	public void testFromName() {
		assertEquals(Scale.N24579E, Scale.fromName("major"));
		assertEquals(Scale.N24579E, Scale.fromName("ionian"));
		assertEquals(null, Scale.fromName("ma"));
	}

	@Test
	public void testGetName() {
		assertEquals("minor pentatonic", Scale.fromName("minor pentatonic").getName());
	}

	@Test
	public void testGetAlternativeNames() {
		assertEquals(2, Scale.fromName("minor natural").getAlternativeNames().size());
		assertEquals("minor natural", Scale.fromName("minor natural").getAlternativeNames().get(0));
		assertEquals("aeolian", Scale.fromName("minor natural").getAlternativeNames().get(1));
	}
}
