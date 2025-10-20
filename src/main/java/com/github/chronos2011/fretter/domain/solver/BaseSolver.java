package com.github.chronos2011.fretter.domain.solver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.github.chronos2011.fretter.domain.Board;
import com.github.chronos2011.fretter.domain.DomainException;
import com.github.chronos2011.fretter.domain.Fret;
import com.github.chronos2011.fretter.domain.Position;
import com.github.chronos2011.fretter.domain.library.*;
import com.github.chronos2011.fretter.options.ApplicationOptions;

/**
 * Class BaseSolver provides base functionality for the various {@link Solver}s.
 */
public abstract class BaseSolver implements Solver {
	@FunctionalInterface
	protected interface BoardVisitor {
		void visit(Fret fret);
	}

	protected final ApplicationOptions applicationOptions;
	protected Board board;
	protected final List<String> warnings;
	protected final List<String> hints;

	protected BaseSolver(ApplicationOptions applicationOptions) {
		this.applicationOptions = applicationOptions;
		this.warnings = new ArrayList<>();
		this.hints = new ArrayList<>();
	}

	/**
	 * Creates a new (untuned) {@link Board}.
	 */
	protected void createBoard() {
		this.board = new Board(applicationOptions.boardOptions);
	}

	/**
	 * Sets the {@link Pitch} of all {@link Fret}s on the {@link Board}. Does NOT set {@link Interval}s or
	 * {@link Degree}s of a {@link Fret}, since scale relationships are not yet defined during tuning.
	 */
	protected void tuneBoard() {
		Tuning tuning = applicationOptions.boardOptions.tuning;
		visitBoard((fret) -> {
			Position position = fret.position;
			fret.pitch = tuning.pitches.get(position.stringIndex).raise(position.fretIndex);
		});
	}

	/**
	 * Visits all {@link Fret}s of the {@link Board}.
	 *
	 * @param visitor the {@link BoardVisitor} to be used
	 */
	protected void visitBoard(BoardVisitor visitor) {
		for (int stringIndex = 0; stringIndex < board.stringCount; stringIndex++)
			for (int fretIndex = 0; fretIndex <= board.fretCount; fretIndex++)
				visitor.visit(board.frets[stringIndex][fretIndex]);
	}

	/**
	 * Applies a List of {@link Interval}s to the a specific {@link Fret}, setting its semantic meaning (i.e. scale
	 * relationship). Lists of {@link Interval}s are defined by {@link Scale}s and {@link Chord}s.
	 *
	 * @param intervalList the List of {@link Interval}s to be used
	 * @param root         the root {@link Pitch} of the List of {@link Intervals}; the octave does not have an impact
	 * @param fret         the {@link Fret} to set
	 */
	protected void applyIntervalList(List<Interval> intervalList, Pitch root, Fret fret) {
		Interval interval = Interval.from(root, fret.pitch);
		for (int order = 0; order < intervalList.size(); order++) {
			if (intervalList.get(order) == interval) {
				fret.interval = interval;
				fret.degree = Degree.from(order);
			}
		}
	}

	/**
	 * Finds the first matching {@link Fret} in up or down direction. If the starting {@link Position} does not have a
	 * fixed string, the search checks higher strings for matches, so that effectively higher strings are preferred to
	 * farther {@link Fret}s.
	 *
	 * @param startPosition the {@link Position} to start from
	 * @param findUp        a flag indicating whether higher {@link Fret}s should be considered
	 * @param findDown      a flag indicating whether lower {@link Fret}s should be considered
	 * @param matcher       the matcher to be used
	 * @return the first {@link Fret} matching
	 * @throws DomainException if no matching {@Fret} could be found
	 */
	protected Fret find(Position startPosition, boolean findUp, boolean findDown, Function<Fret, Boolean> matcher)
			throws DomainException {
		int offset = 0;
		int currentString;
		while (offset <= board.fretCount) {
			// We run these parts separately for higher positions and...
			if (findUp) {
				int upperPosition = startPosition.fretIndex + offset;
				currentString = startPosition.stringFixed ? startPosition.stringIndex : 0;
				do {
					if (upperPosition <= board.fretCount) {
						Fret fret = board.frets[currentString][upperPosition];
						if (matcher.apply(fret))
							return fret;
					}
					currentString++;
				} while (!startPosition.stringFixed && currentString < board.stringCount);
			}
			// ...lower positions because we prefer a different string to a farther fret
			if (findDown) {
				int lowerPosition = startPosition.fretIndex - offset;
				currentString = startPosition.stringFixed ? startPosition.stringIndex : 0;
				do {
					if (lowerPosition >= 0) {
						Fret fret = board.frets[currentString][lowerPosition];
						if (matcher.apply(fret))
							return fret;
					}
					currentString++;
				} while (!startPosition.stringFixed && currentString < board.stringCount);
			}
			offset++;
		}
		throw new DomainException("Could not find position on board");
	}

	/**
	 * Convenience method for {@link BaseSolver#find(Position, boolean, boolean, Function)}
	 *
	 * @see {@link BaseSolver#find(Position, boolean, boolean, Function)}
	 */
	protected Fret findUpDown(Position startPosition, Function<Fret, Boolean> matcher) throws DomainException {
		return find(startPosition, true, true, matcher);
	}

	/**
	 * Convenience method for {@link BaseSolver#find(Position, boolean, boolean, Function)}
	 *
	 * @see {@link BaseSolver#find(Position, boolean, boolean, Function)}
	 */
	protected Fret findUp(Position startPosition, Function<Fret, Boolean> matcher) throws DomainException {
		return find(startPosition, true, false, matcher);
	}
}
