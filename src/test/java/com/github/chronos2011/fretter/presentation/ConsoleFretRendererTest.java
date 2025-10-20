package com.github.chronos2011.fretter.presentation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.chronos2011.fretter.domain.Fret;
import com.github.chronos2011.fretter.domain.Position;
import com.github.chronos2011.fretter.domain.library.Degree;
import com.github.chronos2011.fretter.domain.library.Interval;
import com.github.chronos2011.fretter.domain.library.Pitch;
import com.github.chronos2011.fretter.options.ApplicationOptions;
import com.github.chronos2011.fretter.options.RenderOptions;
import com.github.chronos2011.fretter.options.RenderOptions.FretLabeling;

public class ConsoleFretRendererTest {
	ApplicationOptions applicationOptions;
	ConsoleFretRenderer renderer;
	Fret fret;

	@Before public void setup() {
		applicationOptions = new ApplicationOptions();
		applicationOptions.renderOptions = new RenderOptions();
		fret = new Fret(new Position(0,12));
		fret.pitch = Pitch.GS3;
		fret.interval = Interval.PER5;
		fret.degree = Degree.D05;
	}

	@Test public void testRender_Labeling_MidiPitch() {
		applicationOptions.renderOptions.fretLabeling = FretLabeling.MIDI_PITCH;
		renderer = new ConsoleFretRenderer(applicationOptions);
		String label = renderer.render(fret);
		assertEquals("56 ", label);
	}

	@Test public void testRender_Labeling_NoteName() {
		applicationOptions.renderOptions.fretLabeling = FretLabeling.NOTE_NAME;
		renderer = new ConsoleFretRenderer(applicationOptions);
		String label = renderer.render(fret);
		assertEquals("G♯₃", label);
	}

	@Test public void testRender_Labeling_IntervalName() {
		applicationOptions.renderOptions.fretLabeling = FretLabeling.INTERVAL_SYMBOL;
		renderer = new ConsoleFretRenderer(applicationOptions);
		String label = renderer.render(fret);
		assertEquals(" 5 ", label);
	}

	@Test public void testRender_Labeling_IntervalSteps() {
		applicationOptions.renderOptions.fretLabeling = FretLabeling.INTERVAL_STEPS;
		renderer = new ConsoleFretRenderer(applicationOptions);
		String label = renderer.render(fret);
		assertEquals(" 7 ", label);
	}

	@Test public void testRender_Labeling_ScaleMembership() {
		applicationOptions.renderOptions.fretLabeling = FretLabeling.SCALE_MEMBERSHIP;
		renderer = new ConsoleFretRenderer(applicationOptions);
		String label = renderer.render(fret);
		assertEquals(" ○ ", label);
		fret.interval = Interval.PER1;
		label = renderer.render(fret);
		assertEquals(" ◉ ", label);
		fret.interval = Interval.OUT;
		label = renderer.render(fret);
		assertEquals(null, label);
	}
}
