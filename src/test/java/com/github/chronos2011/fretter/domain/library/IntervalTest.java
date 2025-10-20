package com.github.chronos2011.fretter.domain.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntervalTest {
	@Test
	public void testIntervalFromSteps() {
		assertEquals(Interval.TRIT, Interval.from(6));
	}

	@Test public void testIntervalFromPitches() {
		assertEquals(Interval.PER5, Interval.from(Pitch.A_4, Pitch.E_5));
	}
}
