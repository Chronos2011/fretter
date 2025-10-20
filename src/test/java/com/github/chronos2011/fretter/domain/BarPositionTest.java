package com.github.chronos2011.fretter.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BarPositionTest {
	@Test
	public void testNewBarPosition() {
		BarPosition barPosition = new BarPosition(2, 5, 3);
		assertEquals(2, barPosition.lowerStringIndex);
		assertEquals(5, barPosition.upperStringIndex);
		assertEquals(3, barPosition.fretIndex);
	}

	@Test
	public void testToString() {
		assertEquals("(2-5, 3)", new BarPosition(2, 5, 3).toString());
	}
}
