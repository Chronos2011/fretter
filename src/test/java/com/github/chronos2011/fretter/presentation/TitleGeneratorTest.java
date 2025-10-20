package com.github.chronos2011.fretter.presentation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.chronos2011.fretter.domain.library.Chord;
import com.github.chronos2011.fretter.domain.library.Pitch;
import com.github.chronos2011.fretter.domain.library.Scale;
import com.github.chronos2011.fretter.domain.library.Tuning;
import com.github.chronos2011.fretter.options.*;
import com.github.chronos2011.fretter.options.ApplicationOptions.Operation;

public class TitleGeneratorTest {
	ApplicationOptions applicationOptions;
	TitleGenerator generator;

	@Before
	public void setup() {
		applicationOptions = new ApplicationOptions();
		applicationOptions.operation = Operation.BOARD;
		applicationOptions.boardOptions = new BoardOptions();
		applicationOptions.boardOptions.tuning = Tuning.STANDARD_BASS;
		applicationOptions.boardOptions.fretCount = 12;
	}

	@Test
	public void testGenerateTitleBoard() {
		generator = new TitleGenerator(applicationOptions);
		assertEquals("Board (standard bass tuning, 12 frets)", generator.generateTitle());
	}

	@Test
	public void testGenerateTitleScale() {
		applicationOptions.operation = Operation.SCALE;
		applicationOptions.scaleOptions = new ScaleOptions();
		applicationOptions.scaleOptions.scale = Scale.AEOLIAN;
		applicationOptions.scaleOptions.pitch = Pitch.E_4;
		generator = new TitleGenerator(applicationOptions);
		assertEquals("Scale E ₄ aeolian (standard bass tuning)", generator.generateTitle());
	}

	@Test
	public void testGenerateTitleArpeggio() {
		applicationOptions.operation = Operation.ARPEGGIO;
		applicationOptions.arpeggioOptions = new ArpeggioOptions();
		applicationOptions.arpeggioOptions.chord = Chord.MAJ7;
		applicationOptions.arpeggioOptions.pitch = Pitch.D_3;
		generator = new TitleGenerator(applicationOptions);
		assertEquals("Arpeggio D ₃ maj7 (standard bass tuning)", generator.generateTitle());
	}

	@Test
	public void testGenerateTitleChord() {
		applicationOptions.operation = Operation.CHORD;
		applicationOptions.chordOptions = new ChordOptions();
		applicationOptions.chordOptions.chord = Chord.AUG;
		applicationOptions.chordOptions.pitch = Pitch.C_4;
		generator = new TitleGenerator(applicationOptions);
		assertEquals("Chord C ₄ aug (standard bass tuning)", generator.generateTitle());
	}
}
