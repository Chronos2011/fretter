package com.github.chronos2011.fretter.domain.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntervalTest {
	@Test
	public void testIntervalFromSteps() {
		assertEquals(Interval.TRIT, Interval.from(6));
	}

	@Test
	public void testIntervalFromPitches() {
		assertEquals(Interval.PER5, Interval.from(Pitch.A_4, Pitch.E_5));
	}

	@Test
	public void testGetSymbol() {
		assertEquals("♭2 ", Interval.MIN2.getSymbol());
	}
}
