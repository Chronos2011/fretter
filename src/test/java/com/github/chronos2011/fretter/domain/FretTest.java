package com.github.chronos2011.fretter.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.chronos2011.fretter.domain.library.Degree;
import com.github.chronos2011.fretter.domain.library.Interval;
import com.github.chronos2011.fretter.domain.library.Pitch;

public class FretTest {
	@Test
	public void testSetOutOfScale() {
		Fret fret = new Fret(new Position(1, 2));
		fret.pitch = Pitch.A_5;
		fret.interval = Interval.PER5;
		fret.degree = Degree.D05;
		fret.setOutOfScale();
		assertEquals(1, fret.position.stringIndex);
		assertEquals(2, fret.position.fretIndex);
		assertEquals(Pitch.A_5, fret.pitch);
		assertEquals(Interval.OUT, fret.interval);
		assertEquals(Degree.OUT, fret.degree);
	}
}
