package com.github.chronos2011.fretter.domain;

import java.util.*;

import com.github.chronos2011.fretter.domain.library.Degree;
import com.github.chronos2011.fretter.options.BoardOptions;
import com.github.chronos2011.fretter.options.ChordOptions;

/**
 * Class Fingering provides various functionality for building different fretboard fingerings.
 */
public final class Fingering implements Cloneable {
	// TODO this class has become too big - split it
	public final Board board;
	public final Position position;
	public final ChordOptions chordOptions;
	public final int stringCount;
	public final int degreeCount;
	public final List<Fret>[] frets;
	public final boolean[] stringFilled;
	public final List<Fret>[] degrees;
	public final boolean[] degreeFilled;
	public boolean isFinished;
	public boolean isViable;
	public int lowerFretLimit;
	public int upperFretLimit;
	// Caches for various checks - not part of state
	private Set<Integer> differentFrets;
	private List<Integer> totalFrets;
	private List<BarPosition> bars;
	private Integer stringsUsed;
	private Integer width;
	private Integer deviation;
	private Integer openStringsPresent;
	private Integer unusedStringsPresent;
	private int lowestDegreePitches[];

	/**
	 * Create a new Fingering from an existing Fingering (copy constructor).
	 *
	 * @param other the Fingering to be copied from
	 */
	@SuppressWarnings("unchecked")
	public Fingering(Fingering other) {
		this.board = other.board;
		this.position = other.position;
		this.chordOptions = other.chordOptions;
		this.stringCount = other.stringCount;
		this.degreeCount = other.degreeCount;
		this.frets = new ArrayList[board.stringCount];
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++)
			this.frets[stringIndex] = new ArrayList<>(other.frets[stringIndex]);
		this.stringFilled = Arrays.copyOf(other.stringFilled, other.stringFilled.length);
		this.degrees = new ArrayList[chordOptions.chord.intervalList.size()];
		for (int degreeIndex = 0; degreeIndex < degreeCount; degreeIndex++)
			this.degrees[degreeIndex] = new ArrayList<>(other.degrees[degreeIndex]);
		this.degreeFilled = Arrays.copyOf(other.degreeFilled, other.degreeFilled.length);
		this.isFinished = other.isFinished;
		this.isViable = other.isViable;
		this.lowerFretLimit = other.lowerFretLimit;
		this.upperFretLimit = other.upperFretLimit;
	}

	/**
	 * Create a new Fingering.
	 *
	 * @param board        the configured {@link Board to be used}
	 * @param position     the {@link Position} to create a Fingering at
	 * @param chordOptions the {@link ChordOptions} to be used
	 */
	@SuppressWarnings("unchecked")
	public Fingering(Board board, Position position, ChordOptions chordOptions) {
		this.board = board;
		this.position = position;
		this.chordOptions = chordOptions;
		this.stringCount = board.stringCount;
		this.degreeCount = chordOptions.chord.intervalList.size();
		this.frets = new ArrayList[stringCount];
		this.stringFilled = new boolean[stringCount];
		this.degrees = new ArrayList[degreeCount];
		this.degreeFilled = new boolean[degreeCount];
		this.isFinished = false;
		this.isViable = true;
		setup();
	}

	@Override
	public boolean equals(Object object) {
		if (object.getClass() != Fingering.class)
			return false;
		Fingering other = (Fingering) object;
		if (other.board != this.board)
			return false;
		if (!other.position.equals(this.position))
			return false;
		if (other.chordOptions != this.chordOptions)
			return false;
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++) {
			if (!other.frets[stringIndex].equals(this.frets[stringIndex]))
				return false;
			if (other.stringFilled[stringIndex] != this.stringFilled[stringIndex])
				return false;
		}
		for (int degreeIndex = 0; degreeIndex < degreeCount; degreeIndex++) {
			if (!other.degrees[degreeIndex].equals(this.degrees[degreeIndex]))
				return false;
			if (other.degreeFilled[degreeIndex] != this.degreeFilled[degreeIndex])
				return false;
		}
		if (other.isFinished != this.isFinished)
			return false;
		if (other.isViable != this.isViable)
			return false;
		if (other.lowerFretLimit != this.lowerFretLimit)
			return false;
		if (other.upperFretLimit != this.upperFretLimit)
			return false;
		return true;
	}

	private void setup() {
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++) {
			this.frets[stringIndex] = new ArrayList<>();
			this.stringFilled[stringIndex] = false;
		}
		for (int degreeIndex = 0; degreeIndex < degreeCount; degreeIndex++) {
			this.degrees[degreeIndex] = new ArrayList<>();
			this.degreeFilled[degreeIndex] = false;
		}
		lowerFretLimit = position.fretIndex - chordOptions.maxWidth - chordOptions.maxDeviation + 1;
		lowerFretLimit = Math.max(1, lowerFretLimit);
		upperFretLimit = position.fretIndex + chordOptions.maxWidth + chordOptions.maxDeviation - 1;
		upperFretLimit = Math.min(board.fretCount, upperFretLimit);
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++) {
			if (stringIndex < position.stringIndex) {
				stringFilled[stringIndex] = true;
				continue;
			}
			Fret fret = board.frets[stringIndex][0];
			if (fret.degree != Degree.OUT) {
				this.frets[stringIndex].add(fret);
				this.degrees[fret.degree.index].add(fret);
			}
			for (int fretIndex = lowerFretLimit; fretIndex <= upperFretLimit; fretIndex++) {
				fret = board.frets[stringIndex][fretIndex];
				if (fret.degree != Degree.OUT) {
					this.frets[stringIndex].add(fret);
					this.degrees[fret.degree.index].add(fret);
				}
			}
		}
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++)
			if (this.frets[stringIndex].size() == 0)
				this.stringFilled[stringIndex] = true;
		for (int degreeIndex = 0; degreeIndex < degreeCount; degreeIndex++)
			if (this.degrees[degreeIndex].size() == 0)
				isViable = false;
	}

	/**
	 * Convert this Fingering to a new {@link Board}.
	 *
	 * @param boardOptions the {@link BoardOptions} to be used
	 * @return the {@link Board} created from this Fingering
	 */
	public Board asBoard(BoardOptions boardOptions) {
		// TODO make Fingering receive ApplicationOptions in constructor and re-use here - currently this is asking for
		// trouble
		if (!isFinished || !isViable)
			return null;
		Board targetBoard = new Board(board.boardOptions);
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++) {
			if (stringFilled[stringIndex] && frets[stringIndex].size() > 0) {
				Fret fretSource = frets[stringIndex].get(0);
				Fret fretTarget = targetBoard.frets[stringIndex][fretSource.position.fretIndex];
				fretTarget.position = fretSource.position;
				fretTarget.pitch = fretSource.pitch;
				fretTarget.interval = fretSource.interval;
				fretTarget.degree = fretSource.degree;
			}
		}
		return targetBoard;
	}

	/**
	 * Return a List of next {@link Fret}s that would semantically correct (i.e. "in tune") add to this Fingering.
	 *
	 * @return a List of next {@link Fret} options
	 */
	public List<Fret> getNextOptions() {
		if (!isViable || isFinished)
			return Collections.emptyList();
		List<Fret> options = new ArrayList<>();
		// First check for most restrictive degree options
		int leastOptionCount = Integer.MAX_VALUE;
		for (int degreeIndex = 0; degreeIndex < degreeCount; degreeIndex++) {
			if (degreeFilled[degreeIndex])
				continue;
			int currentOptionCount = degrees[degreeIndex].size();
			if (currentOptionCount < leastOptionCount) {
				leastOptionCount = currentOptionCount;
			}
		}
		for (int degreeIndex = 0; degreeIndex < degreeCount; degreeIndex++) {
			if (degreeFilled[degreeIndex])
				continue;
			if (degrees[degreeIndex].size() == leastOptionCount)
				options.addAll(degrees[degreeIndex]);
		}
		if (options.size() != 0)
			return options;
		// Then check for remaining string options, including unused strings
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++) {
			if (stringFilled[stringIndex])
				continue;
			options.add(new Fret(new Position(stringIndex, Position.INDEX_UNDEFINED)));
			options.addAll(frets[stringIndex]);
		}
		return options;
	}

	/**
	 * Select a next {@link Fret} to add to this Fingering. It must come from the List generated in
	 * {@link Fingering#getNextOptions()} otherwise the behavior is undefined.
	 *
	 * @param fret the next {@link Fret} option to add
	 */
	public void select(Fret fret) {
		if (!isViable || isFinished)
			return;
		// If unused string...
		if (fret.position.fretIndex == Position.INDEX_UNDEFINED) {
			List<Fret> stringOptions = frets[fret.position.stringIndex];
			while (stringOptions.size() > 0)
				removeOption(stringOptions.get(0));
			// ...else if fret option
		} else {
			// Adapt fret limits if not open string
			if (fret.position.fretIndex != 0) {
				lowerFretLimit = Math.max(lowerFretLimit, fret.position.fretIndex - chordOptions.maxWidth + 1);
				upperFretLimit = Math.min(upperFretLimit, fret.position.fretIndex + chordOptions.maxWidth - 1);
			}
			// Remove all frets outside new fret limits or on same string except selected
			for (int stringIndex = 0; stringIndex < stringCount; stringIndex++) {
				List<Fret> stringOptions = frets[stringIndex];
				int optionIndex = 0;
				while (optionIndex < stringOptions.size()) {
					Fret option = stringOptions.get(optionIndex);
					Position p = option.position;
					boolean outsideLimits = p.fretIndex < lowerFretLimit || p.fretIndex > upperFretLimit;
					boolean isOpen = p.fretIndex == 0;
					boolean onSameString = p.stringIndex == fret.position.stringIndex;
					boolean selected = option == fret;
					if ((outsideLimits && !isOpen) || (onSameString && !selected))
						removeOption(option);
					else
						optionIndex++;
				}
			}
			// Mark degree as filled
			degreeFilled[fret.degree.index] = true;
		}
		// Mark string as used
		stringFilled[fret.position.stringIndex] = true;
		// Mark as not viable if any degree cannot be filled
		for (int degreeIndex = 0; degreeIndex < degreeCount; degreeIndex++)
			if (degrees[degreeIndex].size() == 0)
				isViable = false;
		// Mark as finished if all strings are used
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++)
			if (!stringFilled[stringIndex])
				return;
		isFinished = true;
	}

	private static final int[] STRINGS_USED_REWARD = { 0, 0, 1, 4, 9, 16, 25, 36, 49 };
	private static final int[] WIDTH_PENALTY = { 0, 0, 0, 1, 4, 9, 16, 25, 36, 49 };
	private static final int[] DEVIATION_PENALTY = { 0, 2, 4, 7, 10, 13, 16, 19, 22, 25 };
	private static final int[] TOTAL_FRETS_PENALTY = { 0, 1, 2, 3, 5, 7, 10, 13, 17, 21 };
	private static final int[] DIFFERENT_FRETS_PENALTY = { 0, 0, 1, 4, 9, 16, 25, 36, 49, 64 };
	private static final int[] BARS_PENALTY = { 3, 12, 27, 48, 75, 108, 147, 192 };

	/**
	 * Return the "usefulness" of this Fingering. This is of course mostly arbitrary, but should hopefully be close to
	 * common reasoning.
	 *
	 * @return the usefulness of this Fingering
	 */
	public int getUsefulness() {
		int usefulness = 0;
		// We do not penalize unused or open strings, because they must have been enabled by the user explicitly,
		// and then, we do not dare challenge the user's decision (by ranking these fingerings lower)
		calculateLowestDegreePitches();
		calculateStringsUsed();
		calculateWidthAndDeviation();
		calculateTotalFrets();
		calculateDifferentFrets();
		calculateBarsRequired();
		if (isFirstDegreeOfLowestPitch())
			usefulness += 4;
		usefulness += calculateScore(STRINGS_USED_REWARD, stringsUsed);
		usefulness -= calculateScore(WIDTH_PENALTY, width);
		usefulness -= calculateScore(DEVIATION_PENALTY, deviation);
		usefulness -= calculateScore(TOTAL_FRETS_PENALTY, totalFrets.size());
		usefulness -= calculateScore(DIFFERENT_FRETS_PENALTY, differentFrets.size());
		usefulness -= calculateScore(BARS_PENALTY, bars.size());
		return usefulness;
	}

	private void calculateStringsUsed() {
		if (stringsUsed != null)
			return;
		stringsUsed = Integer.valueOf(0);
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++)
			if (stringFilled[stringIndex] && frets[stringIndex].size() > 0)
				stringsUsed++;
	}

	private int calculateScore(int[] penalties, int value) {
		if (value < 0)
			return penalties[0];
		if (value >= penalties.length)
			return penalties[penalties.length - 1];
		return penalties[value];
	}

	/**
	 * Returns a flag indicating whether the first degree (= root) is also of lowest pitch (i.e. the chord is not an
	 * inversion).
	 *
	 * @return a flag indicating whether the first degree is of lowest pitch
	 */
	public boolean isFirstDegreeOfLowestPitch() {
		calculateLowestDegreePitches();
		int lowestFirstDegreePitch = lowestDegreePitches[0];
		for (int degreeIndex = 1; degreeIndex < degreeCount; degreeIndex++)
			if (lowestDegreePitches[degreeIndex] < lowestFirstDegreePitch)
				return false;
		return true;
	}

	private void calculateLowestDegreePitches() {
		if (lowestDegreePitches != null)
			return;
		lowestDegreePitches = new int[degreeCount];
		for (int degreeIndex = 0; degreeIndex < degreeCount; degreeIndex++) {
			List<Fret> degree = degrees[degreeIndex];
			lowestDegreePitches[degreeIndex] = Integer.MAX_VALUE;
			for (Fret fret : degree)
				if (fret.pitch.midiIndex < lowestDegreePitches[degreeIndex])
					lowestDegreePitches[degreeIndex] = fret.pitch.midiIndex;
		}
	}

	/**
	 * Returns a flag indicating whether this Fingering fulfills maximum width and deviation options.
	 *
	 * @return a flag indicating whether this Fingering fulfills maximum width and deviation options
	 */
	public boolean checkMaxWidthAndDeviationFulfilled() {
		calculateWidthAndDeviation();
		boolean maxWidthFulfilled = width <= chordOptions.maxWidth;
		boolean maxDeviationFulfilled = deviation <= chordOptions.maxDeviation;
		return maxWidthFulfilled && maxDeviationFulfilled;
	}

	private void calculateWidthAndDeviation() {
		if (width != null && deviation != null)
			return;
		int lowestFret = Integer.MAX_VALUE;
		int highestFret = Integer.MIN_VALUE;
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++) {
			List<Fret> stringOptions = frets[stringIndex];
			if (stringFilled[stringIndex] && stringOptions.size() > 0) {
				int fretIndex = stringOptions.get(0).position.fretIndex;
				// Ignore open strings
				if (fretIndex == 0)
					continue;
				if (fretIndex < lowestFret)
					lowestFret = fretIndex;
				if (fretIndex > highestFret)
					highestFret = fretIndex;
			}
		}
		width = highestFret - lowestFret + 1;
		deviation = Math.max(0, lowestFret - position.fretIndex);
		deviation = Math.max(Math.max(0, position.fretIndex - highestFret), deviation);
	}

	/**
	 * Return a flag indicating whether this Fingering contains open strings.
	 *
	 * @return a flag indicating whether this Fingering contains open strings
	 */
	public boolean checkOpenStringsPresent() {
		return calculateOpenStringsPresent() > 0;
	}

	private int calculateOpenStringsPresent() {
		if (openStringsPresent == null)
			openStringsPresent = Integer.valueOf(0);
		else
			return openStringsPresent;
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++) {
			List<Fret> stringOptions = frets[stringIndex];
			if (stringFilled[stringIndex] && stringOptions.size() > 0) {
				int fretIndex = stringOptions.get(0).position.fretIndex;
				if (fretIndex == 0)
					openStringsPresent++;
			}
		}
		return openStringsPresent;
	}

	/**
	 * Return a flag indicating whether this Fingering contains unused strings.
	 *
	 * @return a flag indicating whether this Fingering contains unused strings
	 */
	public boolean checkUnusedStringsPresent() {
		return calculateUnusedStringsPresent() > 0;
	}

	private int calculateUnusedStringsPresent() {
		if (unusedStringsPresent == null)
			unusedStringsPresent = Integer.valueOf(0);
		else
			return unusedStringsPresent;
		int lowestUsed = Integer.MAX_VALUE;
		int highestUsed = Integer.MIN_VALUE;
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++) {
			if (stringFilled[stringIndex] && frets[stringIndex].size() > 0) {
				if (stringIndex < lowestUsed)
					lowestUsed = stringIndex;
				if (stringIndex > highestUsed)
					highestUsed = stringIndex;
			}
		}
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++) {
			boolean stringIsUnused = stringFilled[stringIndex] && frets[stringIndex].size() == 0;
			boolean stringIsInner = stringIndex > lowestUsed && stringIndex < highestUsed;
			if (stringIsUnused && stringIsInner)
				unusedStringsPresent++;
		}
		return unusedStringsPresent;
	}

	/**
	 * Return a flag indicating whether this Fingering contains at most four different fret indices; unless one is using
	 * the thumb, is an octopus or extraterrestrial entity, this is an important check.
	 *
	 * @return a flag indicating whether this Fingering contains unused strings
	 */
	public boolean sanityCheckAtMostFourDifferentFrets() {
		calculateDifferentFrets();
		return differentFrets.size() <= 4;
	}

	/**
	 * Return a flag indicating whether this Fingering contains no duplicate pitches. One might want to turn off this
	 * check if one's instrument does not easily fall out of tune.
	 *
	 * @return a flag indicating whether this Fingering contains no duplicate pitches
	 */
	public boolean sanityCheckNoDuplicatePitches() {
		for (int outerIndex = 0; outerIndex < stringCount - 1; outerIndex++)
			for (int innerIndex = outerIndex + 1; innerIndex < stringCount; innerIndex++)
				if ((frets[outerIndex].size() > 0) && (frets[innerIndex].size() > 0)
						&& (frets[outerIndex].get(0).pitch == frets[innerIndex].get(0).pitch))
					return false;
		return true;
	}

	/**
	 * Return a flag indicating whether this Fingering contains frets LEFT of a bar.
	 *
	 * @return a flag indicating whether this Fingering contains frets LEFT of a bar
	 */
	public boolean sanityCheckNoFretsLeftOfBar() {
		calculateBarsRequired();
		for (BarPosition bar : bars)
			for (int stringIndex = bar.lowerStringIndex; stringIndex <= bar.upperStringIndex; stringIndex++)
				if ((frets[stringIndex].size() > 0) && (frets[stringIndex].get(0).position.fretIndex < bar.fretIndex))
					return false;
		return true;
	}

	/**
	 * Return a flag indicating whether this Fingering contains frets TOP LEFT of a bar. Some players may be able to
	 * reliably fret partial bars and might want to turn this check off.
	 *
	 * @return a flag indicating whether this Fingering contains frets TOP LEFT of a bar
	 */
	public boolean sanityCheckNoFretsTopLeftOfBar() {
		calculateBarsRequired();
		for (BarPosition bar : bars)
			for (int stringIndex = stringCount - 1; stringIndex > bar.upperStringIndex; stringIndex--)
				if ((frets[stringIndex].size() > 0) && (frets[stringIndex].get(0).position.fretIndex < bar.fretIndex))
					return false;
		return true;
	}

	private void calculateDifferentFrets() {
		if (differentFrets != null)
			return;
		differentFrets = new HashSet<>();
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++)
			differentFrets.addAll(frets[stringIndex].stream().map(fret -> fret.position.fretIndex).toList());
		// Open strings are not considered
		differentFrets.remove(0);
	}

	private void calculateTotalFrets() {
		if (totalFrets != null)
			return;
		totalFrets = new ArrayList<>();
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++)
			totalFrets.addAll(frets[stringIndex].stream().map(fret -> fret.position.fretIndex).toList());
		// Open strings are not considered
		while (totalFrets.remove(Integer.valueOf(0)))
			// Do nothing since work is done in ArrayList.remove()
			;
	}

	private void calculateBarsRequired() {
		if (bars != null)
			return;
		bars = new ArrayList<>();
		calculateTotalFrets();
		calculateDifferentFrets();
		if (totalFrets.size() <= 4)
			return;
		int barFrets = totalFrets.size() - differentFrets.size();
		int lowestFret = totalFrets.stream().min(Integer::compare).get();
		int highestFret = totalFrets.stream().max(Integer::compare).get();
		for (int fretIndex = lowestFret; fretIndex <= highestFret; fretIndex++) {
			List<Position> positions = new ArrayList<>();
			for (int stringIndex = 0; stringIndex < stringCount; stringIndex++) {
				List<Fret> stringOptions = frets[stringIndex];
				if (stringOptions.size() == 0)
					continue;
				Position position = stringOptions.get(0).position;
				if (position.fretIndex == fretIndex)
					positions.add(position);
			}
			if (positions.size() > 1) {
				barFrets -= positions.size();
				int firstStringIndex = positions.get(0).stringIndex;
				int lastStringIndex = positions.get(positions.size() - 1).stringIndex;
				bars.add(new BarPosition(firstStringIndex, lastStringIndex, fretIndex));
				if (barFrets == 0)
					return;
			}
		}
	}

	private void removeOption(Fret option) {
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++)
			this.frets[stringIndex].remove(option);
		for (int degreeIndex = 0; degreeIndex < degreeCount; degreeIndex++)
			this.degrees[degreeIndex].remove(option);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("** Fingering **\nFret limit: ");
		builder.append(lowerFretLimit);
		builder.append(" - ");
		builder.append(upperFretLimit);
		if (bars != null) {
			builder.append("\n * Bars : ");
			for (BarPosition bar : bars) {
				builder.append("\n");
				builder.append(bar);
			}
		}
		builder.append("\n * String options: ");
		for (int stringIndex = 0; stringIndex < stringCount; stringIndex++) {
			builder.append("\nString ");
			builder.append(stringIndex);
			builder.append(" (");
			builder.append(stringFilled[stringIndex] ? "U" : " ");
			builder.append("): ");
			List<Fret> stringOptions = frets[stringIndex];
			for (int optionIndex = 0; optionIndex < stringOptions.size(); optionIndex++) {
				builder.append(stringOptions.get(optionIndex).position);
				builder.append(", ");
			}
		}
		builder.append("\n * Degree options: ");
		for (int degreeIndex = 0; degreeIndex < degreeCount; degreeIndex++) {
			builder.append("\nDegree ");
			builder.append(Degree.from(degreeIndex).symbol);
			builder.append(" (");
			builder.append(degreeFilled[degreeIndex] ? "F" : " ");
			builder.append("): ");
			List<Fret> scaleOptions = degrees[degreeIndex];
			for (int optionIndex = 0; optionIndex < scaleOptions.size(); optionIndex++) {
				builder.append(scaleOptions.get(optionIndex).position);
				builder.append(", ");
			}
		}
		return builder.toString();
	}
}
