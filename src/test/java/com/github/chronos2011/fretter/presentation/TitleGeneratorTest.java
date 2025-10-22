package com.github.chronos2011.fretter.presentation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.chronos2011.fretter.domain.library.Chord;
import com.github.chronos2011.fretter.domain.library.PitchClass;
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
		applicationOptions.boardOptions.tuning = Tuning.fromName("standard bass");
		applicationOptions.boardOptions.fretCount = 12;
	}

	@Test
	public void testGenerateBoardTitle() {
		generator = new TitleGenerator(applicationOptions);
		assertEquals("Board (standard bass tuning, 12 frets)", generator.generateTitle());
	}

	@Test
	public void testGenerateBoardTitle_AlternativeName() {
		applicationOptions.boardOptions.tuningName = "TEST";
		generator = new TitleGenerator(applicationOptions);
		assertEquals("Board (TEST tuning, 12 frets)", generator.generateTitle());
	}

	@Test
	public void testGenerateScaleTitle() {
		applicationOptions.operation = Operation.SCALE;
		applicationOptions.scaleOptions = new ScaleOptions();
		applicationOptions.scaleOptions.scale = Scale.fromName("minor");
		applicationOptions.scaleOptions.pitchClass = PitchClass.E_;
		generator = new TitleGenerator(applicationOptions);
		assertEquals("Scale E minor (standard bass tuning)", generator.generateTitle());
	}

	@Test
	public void testGenerateScaleTitle_AlternativeName() {
		applicationOptions.operation = Operation.SCALE;
		applicationOptions.scaleOptions = new ScaleOptions();
		applicationOptions.scaleOptions.scale = Scale.fromName("minor");
		applicationOptions.scaleOptions.scaleName = "TEST";
		applicationOptions.scaleOptions.pitchClass = PitchClass.E_;
		generator = new TitleGenerator(applicationOptions);
		assertEquals("Scale E TEST (standard bass tuning)", generator.generateTitle());
	}

	@Test
	public void testGenerateArpeggioTitle() {
		applicationOptions.operation = Operation.ARPEGGIO;
		applicationOptions.arpeggioOptions = new ArpeggioOptions();
		applicationOptions.arpeggioOptions.chord = Chord.fromName("maj7");
		applicationOptions.arpeggioOptions.pitchClass = PitchClass.D_;
		generator = new TitleGenerator(applicationOptions);
		assertEquals("Arpeggio D major seventh (standard bass tuning)", generator.generateTitle());
	}

	@Test
	public void testGenerateArpeggioTitle_AlternativeName() {
		applicationOptions.operation = Operation.ARPEGGIO;
		applicationOptions.arpeggioOptions = new ArpeggioOptions();
		applicationOptions.arpeggioOptions.chord = Chord.fromName("maj7");
		applicationOptions.arpeggioOptions.chordName = "TEST";
		applicationOptions.arpeggioOptions.pitchClass = PitchClass.D_;
		generator = new TitleGenerator(applicationOptions);
		assertEquals("Arpeggio D TEST (standard bass tuning)", generator.generateTitle());
	}

	@Test
	public void testGenerateChordTitle() {
		applicationOptions.operation = Operation.CHORD;
		applicationOptions.chordOptions = new ChordOptions();
		applicationOptions.chordOptions.chord = Chord.fromName("aug");
		applicationOptions.chordOptions.pitchClass = PitchClass.C_;
		generator = new TitleGenerator(applicationOptions);
		assertEquals("Chord C augmented (standard bass tuning)", generator.generateTitle());
	}

	@Test
	public void testGenerateChordTitle_AlternativeName() {
		applicationOptions.operation = Operation.CHORD;
		applicationOptions.chordOptions = new ChordOptions();
		applicationOptions.chordOptions.chord = Chord.fromName("aug");
		applicationOptions.chordOptions.chordName = "TEST";
		applicationOptions.chordOptions.pitchClass = PitchClass.C_;
		generator = new TitleGenerator(applicationOptions);
		assertEquals("Chord C TEST (standard bass tuning)", generator.generateTitle());
	}
}
