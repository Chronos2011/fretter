package com.github.chronos2011.fretter.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PositionTest {
	@Test
	public void testNewPositionWithoutStringIndex() {
		Position position = new Position(3);
		assertEquals(false, position.stringFixed);
		assertEquals(Position.INDEX_UNDEFINED, position.stringIndex);
		assertEquals(3, position.fretIndex);
	}

	@Test
	public void testNewPositionWithStringIndex() {
		Position position = new Position(2, 3);
		assertEquals(true, position.stringFixed);
		assertEquals(2, position.stringIndex);
		assertEquals(3, position.fretIndex);
	}

	@Test
	public void testEquals() {
		Position position0 = new Position(2, 3);
		Position position1 = new Position(2, 3);
		Position position2 = new Position(3);
		assertTrue(position0.equals(position1));
		assertFalse(position0.equals(position2));
		assertFalse(position0.equals(Integer.MAX_VALUE));
	}

	@Test
	public void testToString() {
		assertEquals("(2, 3)", new Position(2, 3).toString());
	}
}
