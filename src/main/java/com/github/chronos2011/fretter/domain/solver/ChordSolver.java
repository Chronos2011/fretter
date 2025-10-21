package com.github.chronos2011.fretter.domain.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.chronos2011.fretter.domain.DomainException;
import com.github.chronos2011.fretter.domain.Fingering;
import com.github.chronos2011.fretter.domain.Fret;
import com.github.chronos2011.fretter.options.ApplicationOptions;
import com.github.chronos2011.fretter.options.ChordOptions;
import com.github.chronos2011.fretter.options.ChordOptions.SanityCheck;

/**
 * Class ChordSolver implements solving functionality for {@link Operation#CHORD}.
 */
public class ChordSolver extends BaseSolver {
	/**
	 * Creates a new ChordSolver with {@link ApplicationOptions}.
	 *
	 * @param applicationOptions the {@link ApplicationOptions} to be used
	 */
	public ChordSolver(ApplicationOptions applicationOptions) {
		super(applicationOptions);
	}

	@Override
	public Solution solve() {
		createBoard();
		tuneBoard();
		ChordOptions options = applicationOptions.chordOptions;
		visitBoard(fret -> applyIntervalList(options.chord.intervalList, options.pitchClass, fret));
		try {
			List<Fingering> fingerings = createCombinations();
			fingerings = removeBroken(fingerings);
			fingerings = removeDuplicates(fingerings);
			fingerings = removeMislocated(fingerings);
			fingerings = removeInsane(fingerings);
			Collections.sort(fingerings, (f0, f1) -> f1.getUsefulness() - f0.getUsefulness());
			return new Solution(board, fingerings, warnings, hints);
		} catch (DomainException exception) {
			warnings.add(exception.getMessage());
		}
		return new Solution(board, Collections.emptyList(), warnings, hints);
	}

	/**
	 * Creates all potential combinations of {@link Fingering}s.
	 *
	 * @return a List of all potential {@link Fingering}s
	 * @throws DomainException if a start {@link Fret} could not be found
	 */
	private List<Fingering> createCombinations() throws DomainException {
		ChordOptions options = applicationOptions.chordOptions;
		List<Fingering> fingerings = new ArrayList<>();
		fingerings.add(new Fingering(board, options.position, options));
		int fingeringIndex = 0;
		while (fingeringIndex < fingerings.size()) {
			Fingering current = fingerings.get(fingeringIndex);
			if (current.isFinished)
				fingeringIndex++;
			else if (!current.isViable)
				fingeringIndex++;
			else {
				List<Fret> nextOptions = current.getNextOptions();
				for (Fret nextOption : nextOptions) {
					Fingering cloned = new Fingering(current);
					cloned.select(nextOption);
					fingerings.add(cloned);
				}
				fingerings.remove(fingeringIndex);
			}
		}
		if (applicationOptions.verbosity >= 1) {
			System.out.println("Creating combinations");
			System.out.println(" * Created " + fingerings.size() + " combinations");
		}
		return fingerings;
	}

	/**
	 * Remove unfinished or non-viable {@link Fingering}s from a List.
	 *
	 * @param fingerings the List of {@link Fingering}s to be modified
	 * @return the cleaned List
	 */
	private List<Fingering> removeBroken(List<Fingering> fingerings) {
		int nonviable = 0;
		int unfinished = 0;
		int fingeringIndex = 0;
		while (fingeringIndex < fingerings.size()) {
			Fingering fingering = fingerings.get(fingeringIndex);
			if (!fingering.isViable) {
				nonviable++;
				fingerings.remove(fingeringIndex);
			} else if (fingering.isFinished) {
				fingeringIndex++;
			} else {
				unfinished++;
				fingerings.remove(fingeringIndex);
			}
		}
		if (applicationOptions.verbosity >= 1) {
			System.out.println("Removing broken");
			System.out.println(" * Removed " + nonviable + " nonviable");
			System.out.println(" * Removed " + unfinished + " unfinished");
			System.out.println("   => " + fingerings.size() + " remaining");
		}
		return fingerings;
	}

	/**
	 * Remove duplicate {@link Fingering}s from a List.
	 *
	 * @param fingerings the List of {@link Fingering}s to be modified
	 * @return the cleaned List
	 */
	private List<Fingering> removeDuplicates(List<Fingering> fingerings) {
		int total = fingerings.size();
		int outerIndex = 0;
		int innerIndex = 1;
		while (outerIndex < fingerings.size() - 1) {
			Fingering outer = fingerings.get(outerIndex);
			innerIndex = outerIndex + 1;
			while (innerIndex < fingerings.size()) {
				if (outer.equals(fingerings.get(innerIndex))) {
					fingerings.remove(innerIndex);
					continue;
				}
				innerIndex++;
			}
			outerIndex++;
		}
		if (applicationOptions.verbosity >= 1) {
			System.out.println("Removing duplicates");
			System.out.println(" * Removed " + (total - fingerings.size()) + " duplicates");
			System.out.println("   => " + fingerings.size() + " remaining");
		}
		return fingerings;
	}

	/**
	 * Remove mislocated {@link Fingering}s from a List.
	 *
	 * @param fingerings the List of {@link Fingering}s to be modified
	 * @return the cleaned List
	 */
	private List<Fingering> removeMislocated(List<Fingering> fingerings) {
		ChordOptions chordOptions = applicationOptions.chordOptions;
		int withOpenStrings = 0;
		int withUnusedStrings = 0;
		int maxWidthOrDeviationViolated = 0;
		int fingeringIndex = 0;
		while (fingeringIndex < fingerings.size()) {
			Fingering current = fingerings.get(fingeringIndex);
			if (!chordOptions.allowOpenStrings && current.checkOpenStringsPresent()) {
				fingerings.remove(fingeringIndex);
				withOpenStrings++;
				continue;
			}
			if (!chordOptions.allowUnusedStrings && current.checkUnusedStringsPresent()) {
				fingerings.remove(fingeringIndex);
				withUnusedStrings++;
				continue;
			}
			if (!current.checkMaxWidthAndDeviationFulfilled()) {
				fingerings.remove(fingeringIndex);
				maxWidthOrDeviationViolated++;
				continue;
			}
			fingeringIndex++;
		}
		if (applicationOptions.verbosity >= 1) {
			System.out.println("Checking locations");
			System.out.println(" * Removed " + withOpenStrings + " with open strings");
			System.out.println(" * Removed " + withUnusedStrings + " with unused (inner) strings");
			System.out.println(" * Removed " + maxWidthOrDeviationViolated + " violating maximum width or deviation");
			System.out.println("   => " + fingerings.size() + " remaining");
		}
		return fingerings;
	}

	/**
	 * Remove {@link Fingering}s failing sanity checks from a List.
	 *
	 * @param fingerings the List of {@link Fingering}s to be modified
	 * @return the cleaned List
	 */
	private List<Fingering> removeInsane(List<Fingering> fingerings) {
		SanityCheck check = applicationOptions.chordOptions.sanityCheck;
		int moreThanFourDifferentFrets = 0;
		int duplicatePitches = 0;
		int fretsLeftOfBar = 0;
		int fretsTopLeftOfBar = 0;
		int fingeringIndex = 0;
		while (fingeringIndex < fingerings.size()) {
			Fingering current = fingerings.get(fingeringIndex);
			if (check.atMostFourDifferentFrets && !current.sanityCheckAtMostFourDifferentFrets()) {
				fingerings.remove(fingeringIndex);
				moreThanFourDifferentFrets++;
				continue;
			}
			if (check.noDuplicatePitches && !current.sanityCheckNoDuplicatePitches()) {
				fingerings.remove(fingeringIndex);
				duplicatePitches++;
				continue;
			}
			if (check.noFretsLeftOfBar && !current.sanityCheckNoFretsLeftOfBar()) {
				fingerings.remove(fingeringIndex);
				fretsLeftOfBar++;
				continue;
			}
			if (check.noFretsTopLeftOfBar && !current.sanityCheckNoFretsTopLeftOfBar()) {
				fingerings.remove(fingeringIndex);
				fretsTopLeftOfBar++;
				continue;
			}
			fingeringIndex++;
		}
		if (applicationOptions.verbosity >= 1) {
			System.out.println("Checking sanity");
			System.out.println(" * Removed " + moreThanFourDifferentFrets + " with more than four different frets");
			System.out.println(" * Removed " + duplicatePitches + " with duplicate pitches");
			System.out.println(" * Removed " + fretsLeftOfBar + " with frets left of bar");
			System.out.println(" * Removed " + fretsTopLeftOfBar + " with frets top left of bar");
			System.out.println("   => " + fingerings.size() + " remaining");
		}
		return fingerings;
	}
}
