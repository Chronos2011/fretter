package com.github.chronos2011.fretter.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.github.chronos2011.fretter.domain.library.Tuning;
import com.github.chronos2011.fretter.options.BoardOptions;

public class BoardTest {
	@Test
	public void testBoard() {
		BoardOptions options = new BoardOptions();
		options.tuning = Tuning.fromName("DADGAD");
		options.fretCount = 15;
		Board board = new Board(options);
		assertEquals(6, board.stringCount);
		assertEquals(15, board.fretCount);
		assertNotNull(board.frets[3][4]);
	}
}
