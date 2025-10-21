package com.github.chronos2011.fretter.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

import com.github.chronos2011.fretter.domain.library.Chord;
import com.github.chronos2011.fretter.domain.library.PitchClass;
import com.github.chronos2011.fretter.domain.library.Tuning;
import com.github.chronos2011.fretter.options.BoardOptions;
import com.github.chronos2011.fretter.options.ChordOptions;

public class FingeringTest {
	BoardOptions boardOptions;
	Board board;
	ChordOptions chordOptions;

	@Before
	public void setup() {
		boardOptions = new BoardOptions();
		boardOptions.tuning = Tuning.fromName("EADGBE");
		boardOptions.fretCount = 15;
		board = new Board(boardOptions);
		chordOptions = new ChordOptions();
		chordOptions.chord = Chord.fromName("maj");
		chordOptions.pitchClass = PitchClass.C_;
		chordOptions.position = new Position(0, 8);
		chordOptions.maxWidth = 4;
		chordOptions.maxDeviation = 0;
		chordOptions.allowOpenStrings = false;
		chordOptions.allowUnusedStrings = false;
		chordOptions.sanityCheck.atMostFourDifferentFrets = true;
		chordOptions.sanityCheck.noDuplicatePitches = true;
		chordOptions.sanityCheck.noFretsLeftOfBar = true;
		chordOptions.sanityCheck.noFretsTopLeftOfBar = true;
	}

	// @Test
	public void testFingering() {
		Fingering fingering = new Fingering(board, chordOptions.position, chordOptions);
		assertEquals(board, fingering.board);
		assertEquals(chordOptions.position, fingering.position);
		assertEquals(chordOptions, fingering.chordOptions);
		assertEquals(6, fingering.stringCount);
		assertEquals(3, fingering.degreeCount);
		assertEquals(6, fingering.frets.length);
		for (int stringIndex = 0; stringIndex <= 6; stringIndex++) {
			assertEquals(16, fingering.frets[stringIndex].size());
			assertEquals(false, fingering.stringFilled[stringIndex]);
		}
		assertEquals(3, fingering.degrees.length);
		for (int degreeIndex = 0; degreeIndex <= 3; degreeIndex++) {
			assertEquals(0, fingering.degrees[degreeIndex].size());
			assertEquals(false, fingering.degreeFilled[degreeIndex]);
		}
		assertEquals(false, fingering.isFinished);
		assertEquals(true, fingering.isViable);
		assertEquals(4, fingering.lowerFretLimit);
		assertEquals(12, fingering.upperFretLimit);
	}
}
