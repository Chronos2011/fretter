package com.github.chronos2011.fretter.presentation;

import com.github.chronos2011.fretter.application.Configuration;
import com.github.chronos2011.fretter.domain.Fret;
import com.github.chronos2011.fretter.domain.library.Interval;
import com.github.chronos2011.fretter.options.ApplicationOptions;
import com.github.chronos2011.fretter.options.RenderOptions;

/**
 * Class ConsoleFretRenderer renders a {@link Fret} to be printed onto the console.
 */
public class ConsoleFretRenderer implements FretRenderer {
	private final RenderOptions renderOptions;

	public ConsoleFretRenderer(ApplicationOptions applicationOptions) {
		this.renderOptions = applicationOptions.renderOptions;
	}

	@Override
	public String render(Fret fret) {
		if (fret.interval == Interval.OUT)
			return null;
		switch (renderOptions.fretLabeling) {
			case MIDI_PITCH:
				return String.format("%2d ", fret.pitch.midiIndex);
			case NOTE_NAME:
				return fret.pitch.getName();
			case INTERVAL_SYMBOL:
				return fret.interval.getSymbol();
			case INTERVAL_STEPS:
				return String.format("%2d ", fret.interval.steps);
			case SCALE_MEMBERSHIP:
				if (!Configuration.hostIsWindows) {
					if (fret.interval == Interval.PER1)
						return " \u25C9 ";
					else
						return " \u25CB ";
				} else {
					if (fret.interval == Interval.PER1)
						return " X ";
					else
						return " + ";
				}
			default:
				return null;
		}
	}
}
