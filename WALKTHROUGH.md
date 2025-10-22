# A simple walkthrough

This walkthrough presents the main features of *fretter*. We're not claiming to produce art here - this is the user's duty. Walking through this example should give you a descent understanding of the tool.

## Objective

As an easy example, assume we  wanted to fret an <a href="https://en.wikipedia.org/wiki/Andalusian_cadence">Andalusian cadence</a>; for the chord progression, we choose the degrees *i*, *VII*, *VI*, and *V7*; later, we plan to apply the *Aeolian mode* for improvised soloing. Selecting *A* as tonic, we obtain *Am* / *G* / *F* / *E7* and *A Aeolian*.

We want to create simple fingerings without big position changes. Also, we aim for high pitches to create a corresponding lighter feeling.

## A fingering for A minor

In order to produce a fingering for A minor, we must run *fretter* in `chord` mode of operation with `--operation Chord`. There are some options in `chord` mode which are mandatory, obviously the root pitch class and the type of chord. So we must add `--root A` to indicate a chord on *A*. Additionally, we must add `--chord minor` for a *minor chord*.

Running *fretter* with these options will still not be sufficient, because it would find more than a hundred potential fingerings, even though multiple sanity checks and filters are enabled by default. We must hint the algorithm to where we want to find a chord with the `--position 12` parameter setting, asking for fingerings around 12th fret:

```
$ ./fretter.sh --operation Chord --root A --chord minor --position 12

Fretter v0.1.3 - Fretboard Diagram Generator


Chord A minor (standard guitar tuning)

Showing fingerings 1 through 4 (of 38)

   12      13      14

┼───────┼───────┼───────┼
┼───────┼──♭3 ──┼───────┼
┼───────┼───────┼── 1 ──┼
┼───────┼───────┼── 5 ──┼
┼── 1 ──┼───────┼───────┼
┼───────┼───────┼───────┼

   12      13      14

   12      13      14

┼── 5 ──┼───────┼───────┼
┼───────┼──♭3 ──┼───────┼
┼───────┼───────┼── 1 ──┼
┼───────┼───────┼── 5 ──┼
┼── 1 ──┼───────┼───────┼
┼── 5 ──┼───────┼───────┼

   12      13      14

    9      10      11      12

┼───────┼───────┼───────┼───────┼
┼───────┼── 1 ──┼───────┼───────┼
┼── 5 ──┼───────┼───────┼───────┼
┼───────┼──♭3 ──┼───────┼───────┼
┼───────┼───────┼───────┼── 1 ──┼
┼───────┼───────┼───────┼───────┼

    9      10      11      12

   12      13      14

┼── 5 ──┼───────┼───────┼
┼───────┼──♭3 ──┼───────┼
┼───────┼───────┼── 1 ──┼
┼───────┼───────┼───────┼
┼───────┼───────┼───────┼
┼───────┼───────┼───────┼

   12      13      14

```

## Dealing with big result sets

The algorithm finds a total of 38 fingerings, which is quite a range to choose from. The result is separated into pages and we can select individual pages by adding `--page <number>` (page numbers starting with 0) and item count on a page with `--item-count <count>`:

```
$ ./fretter.sh --operation Chord --root A --chord minor --position 12 --page 2 --item-count 2

Fretter v0.1.3 - Fretboard Diagram Generator


Chord A minor (standard guitar tuning)

Showing fingerings 5 through 6 (of 38)

   10      11      12

┼───────┼───────┼───────┼
┼───────┼───────┼───────┼
┼───────┼───────┼───────┼
┼──♭3 ──┼───────┼───────┼
┼───────┼───────┼── 1 ──┼
┼───────┼───────┼── 5 ──┼

   10      11      12

   12      13      14

┼── 5 ──┼───────┼───────┼
┼───────┼──♭3 ──┼───────┼
┼───────┼───────┼── 1 ──┼
┼───────┼───────┼── 5 ──┼
┼── 1 ──┼───────┼───────┼
┼───────┼───────┼───────┼

   12      13      14

```

## Nailing down the chords with additional options

Back to our result from `--operation Chord --root A --chord minor --position 12`: we received a total of 38 fingerings, so we can be generous and add more requirements to our fingerings search. First, we want to restrict the *maximum width* of a fingering to 3 frets with `--width 3`; second, we insist on *position* 12 by specifying `--deviation 0`, indicating that we want to deviate at most 0 frets from the position:

```
$ ./fretter.sh --operation Chord --root A --chord minor --position 12 --width 3 --deviation 0

Fretter v0.1.3 - Fretboard Diagram Generator


Chord A minor (standard guitar tuning)

Showing fingerings 1 through 4 (of 7)

   12      13      14

┼───────┼───────┼───────┼
┼───────┼──♭3 ──┼───────┼
┼───────┼───────┼── 1 ──┼
┼───────┼───────┼── 5 ──┼
┼── 1 ──┼───────┼───────┼
┼───────┼───────┼───────┼

   12      13      14

   12      13      14

┼── 5 ──┼───────┼───────┼
┼───────┼──♭3 ──┼───────┼
┼───────┼───────┼── 1 ──┼
┼───────┼───────┼── 5 ──┼
┼── 1 ──┼───────┼───────┼
┼── 5 ──┼───────┼───────┼

   12      13      14

   12      13      14

┼── 5 ──┼───────┼───────┼
┼───────┼──♭3 ──┼───────┼
┼───────┼───────┼── 1 ──┼
┼───────┼───────┼───────┼
┼───────┼───────┼───────┼
┼───────┼───────┼───────┼

   12      13      14

   10      11      12

┼───────┼───────┼───────┼
┼───────┼───────┼───────┼
┼───────┼───────┼───────┼
┼──♭3 ──┼───────┼───────┼
┼───────┼───────┼── 1 ──┼
┼───────┼───────┼── 5 ──┼

   10      11      12

```

For our example, we choose the (high-pitched) third fingering presented:

```
   12      13      14

┼── 5 ──┼───────┼───────┼
┼───────┼──♭3 ──┼───────┼
┼───────┼───────┼── 1 ──┼
┼───────┼───────┼───────┼
┼───────┼───────┼───────┼
┼───────┼───────┼───────┼

   12      13      14
```

## What about other tunings? And instruments?

As you may have noticed, the fingerings produced were for a guitar in standard tuning, which is the default of *fretter*. You can select a different *tuning* (and thereby implicitly another instrument, see also `--fret-count`), e.g. for a ukulele with `--tuning "standard ukulele"`:

```
$ ./fretter.sh --operation Chord --root A --chord minor --position 12 --width 3 --deviation 0 --tuning "standard ukulele"

Fretter v0.1.3 - Fretboard Diagram Generator


Chord A minor (standard ukulele tuning)

Showing fingerings 1 through 2 (of 2)

   12

┼── 1 ──┼
┼── 5 ──┼
┼──♭3 ──┼
┼───────┼

   12

   12      13      14

┼───────┼───────┼───────┼
┼── 5 ──┼───────┼───────┼
┼──♭3 ──┼───────┼───────┼
┼───────┼───────┼── 1 ──┼

   12      13      14

```

Please take note that whenever a named option such as `standard ukulele` contains whitespace, we must put it into double quotes. (Also note this is an example - position 12 probably wouldn't make much sense on ukulele.)

## A fingering for G major

Back to our original example and building on what we have learned so far, we ask for a G major fingering now:

```
$ ./fretter.sh --operation Chord --root G --chord major --position 12 --width 3 --deviation 0
```

Remember to watch out for result pages, because we found the most interesting fingering on `--page 1`:

```
$ ./fretter.sh --operation Chord --root G --chord major --position 12 --width 3 --deviation 0 --page 1

Fretter v0.1.3 - Fretboard Diagram Generator


Chord G major (standard guitar tuning)

Showing fingerings 5 through 7 (of 7)

   12

┼───────┼
┼── 3 ──┼
┼── 1 ──┼
┼── 5 ──┼
┼───────┼
┼───────┼

   12

[...]
```

## Using short option names

Entering all those option names becomes tedious; fortunately, short option names exist for most options. We can shorten the last command to:

```
$ ./fretter.sh -oChord -rG -cMaj -p12 -w3 -d0 -g1

Fretter v0.1.3 - Fretboard Diagram Generator


Chord G Maj (standard guitar tuning)

Showing fingerings 5 through 7 (of 7)

   12

┼───────┼
┼── 3 ──┼
┼── 1 ──┼
┼── 5 ──┼
┼───────┼
┼───────┼

   12

[...]
```

We tried to make the short option names catchy so that they can be remembered easily. Check the [Option reference](REFERENCE.md) for more details. 

## Listing chord types (and other option names) available

How did we get to the chord type names `minor`, `major`, and `Maj` in the first place? You can list all named options (*tunings*, *scales*, *chords*, *pitches*, and (fret) *labels*) with the `--list <option>` option, e.g. as of version *0.1.3* the following chord types are available:

```
$ ./fretter.sh --list Chord

Fretter v0.1.3 - Fretboard Diagram Generator


Chord: add fourth, add second, add sixth, add2, add4, add6, alpha, aug, aug maj7, aug7, augmented, augmented major seventh, augmented seventh, beta, dim, dim7, diminished, diminished seventh, dom7, dom7 dim5, dominant seventh, dominant seventh diminished fifth, dream, gamma, maj, maj7, maj7/6, major, major seventh, major seventh add 6, min, min add2, min add6, min dim5 maj7, min maj7, min min6, min7, min7 dim5, minor, minor add second, minor add sixth, minor diminished fifth major seventh, minor major seventh, minor minor sixth, minor seventh, minor seventh diminished fifth, pow4, pow5, power fifth, power fourth, sus2, sus4, suspended fourth, suspended second, viennese inverse, viennese prime

 * Named options that contain multiple words must be enclosed in "double quotes" *
```

Note that upper or lower case letters in named options are both accepted; the original option name will be added to the diagram, though, giving you some limited influence on title generation.

## A fingering for F major

Continuing on what we learned, we tried `-oChord -cMaj -rF -p12 -w3 -d0`, but unfortunately we did not find a fingering matching our demands. So we decided to release requirements a little:

```
$ ./fretter.sh -oChord -cMaj -rF -p12 -w3 -d2 -g1

Fretter v0.1.3 - Fretboard Diagram Generator


Chord F Maj (standard guitar tuning)

Showing fingerings 5 through 8 (of 16)

[...]

   13      14      15

┼───────┼───────┼───────┼
┼── 5 ──┼───────┼───────┼
┼───────┼── 3 ──┼───────┼
┼───────┼───────┼── 1 ──┼
┼───────┼───────┼───────┼
┼───────┼───────┼───────┼

   13      14      15

```

## Showing diagnostics

Finding a specialized chord for a specific use can be a tough business. Sometimes it is worth checking what filtering the fingering generation algorithm applied with `--verbose 1`:

```
$ ./fretter.sh -oChord -cMaj -rF -p12 -w3 -d2 -v1

Fretter v0.1.3 - Fretboard Diagram Generator


Creating combinations
 * Created 1386 combinations
Removing broken
 * Removed 0 nonviable
 * Removed 0 unfinished
   => 1386 remaining
Removing duplicates
 * Removed 1308 duplicates
   => 78 remaining
Checking locations
 * Removed 28 with open strings
 * Removed 32 with unused (inner) strings
 * Removed 0 violating maximum width or deviation
   => 18 remaining
Checking sanity
 * Removed 0 with more than four different frets
 * Removed 0 with duplicate pitches
 * Removed 0 with frets left of bar
 * Removed 2 with frets top left of bar
   => 16 remaining

Chord F Maj (standard guitar tuning)

Showing fingerings 1 through 4 (of 16)

[...]
```

## Modifying results filtering

These diagnostics tell us that from the 1386 brute-forced combinations built, 1308 were duplicates, resulting in a total 78 unique fingerings. After that various filters are being applied that can be modified by the user.

We won't cover sanity checks here, see the [Option reference](REFERENCE.md) for details.

Interestingly, we see that 28 and 32 fingerings were removed due to open and unused (inner) strings respectively. Maybe, we find some interesting chords by undoing these filters with `--allow-open-strings` and `--allow-unused-strings`:

```
$ ./fretter.sh -oChord -cMaj -rF -p12 -w3 -d2 --allow-open-strings --allow-unused-strings

Fretter v0.1.3 - Fretboard Diagram Generator


Chord F Maj (standard guitar tuning)

Showing fingerings 1 through 4 (of 72)

 0          13      14

   ├ ··· ┼── 1 ──┼───────┼
   ├ ··· ┼── 5 ──┼───────┼
   ├ ··· ┼───────┼── 3 ──┼
   ├ ··· ┼───────┼───────┼
 3 ├ ··· ┼───────┼───────┼
   ├ ··· ┼── 1 ──┼───────┼

 0          13      14

[...]
```

In the first fingering already, we find an open string (A string) and an unused string (D string). Unfortunately, we don't find a nicer fingering in the results than the one we have; this is due to F major being composed of F, A, and C - of which only A is found on an open but bass string.

## A fingering for E dominant seventh

Finding open string fingerings for the *E7* yields much more fingerings so that we can even reduce to `--width 2` and `--deviation 0`:

```
$ ./fretter.sh -oChord -cDom7 -rE -p2,12 -w2 d0 --allow-open-strings --allow-unused-strings -g1

Fretter v0.1.3 - Fretboard Diagram Generator


Chord E Dom7 (standard guitar tuning)

Showing fingerings 5 through 8 (of 9)

[...]

 0          12      13

 1 ├ ··· ┼───────┼───────┼
   ├ ··· ┼── 5 ──┼───────┼
   ├ ··· ┼───────┼── 3 ──┼
   ├ ··· ┼──♭7 ──┼───────┼
   ├ ··· ┼───────┼───────┼
   ├ ··· ┼───────┼───────┼

 0          12      13

[...]
```

We have now found the four fingerings we needed for our example progression *Am* / *G* / *F* / *E7*; personal preferences may differ of course. (I would definitely not use the open string in the *E7*.)

## Charting the fretboard with A aeolian

Now that we have the fingerings for our *chord* progression, we will look at some diagrams for the purpose of soloing. Displaying *scale* notes on the fretboard requires *operation* `scale`. Again, mandatory options must be filled, in case of `scale` these are `--scale <scale>` and `--root <pitch-class>`:

```
$ ./fretter.sh -oScale -sAEOLIAN -rA

Fretter v0.1.3 - Fretboard Diagram Generator


Scale A AEOLIAN (standard guitar tuning)

 0     1       2       3       4       5       6       7       8       9      10      11      12      13      14      15      16      17      18      19      20      21      22      23      24

 5 ├──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼
 2 ├──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼
♭7 ├───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼
 4 ├───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼
 1 ├───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼
 5 ├──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼

 0     1       2       3       4       5       6       7       8       9      10      11      12      13      14      15      16      17      18      19      20      21      22      23      24

```

## Limiting board size

As we can see, this produces a huge, 24 fretsboard which is quite unwieldy in this case. We have four options to limit the size: (1) explicitly setting *fretboard size* with `--fret-count <count>`, (2) limiting the *render window* with `--render-window <window>`, (3) specify a *working window* with `--working-window <window>`, or (4) using a *distribution pattern* with `--notes-per-string <count>` and `--position <position>`.

(1) Explicitly setting the *fretboard size* with `--fret-count <count>` might help in situations where you're just interested in the lower part of the fretboard. (It would most probably be wrong in `--operation Chord` because it would limit the options available for the fingerings generation algorithm):

```
$ ./fretter.sh -oScale -sAEOLIAN -rA -f12

Fretter v0.1.3 - Fretboard Diagram Generator


Scale A AEOLIAN (standard guitar tuning)

 0     1       2       3       4       5       6       7       8       9      10      11      12

 5 ├──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼
 2 ├──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼
♭7 ├───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼
 4 ├───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼
 1 ├───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼
 5 ├──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼

 0     1       2       3       4       5       6       7       8       9      10      11      12

```

(2) To solve the issue from last section, we could employ a *render window* with `--render-window <window>`. But since *notes* would be outside the *render window*, the tool will warn us that there are additional notes which are now hidden:

```
$ ./fretter.sh -oScale -sAEOLIAN -rA -e10,17

Fretter v0.1.3 - Fretboard Diagram Generator


Scale A AEOLIAN (standard guitar tuning)

   10      11      12      13      14      15      16      17

┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼
┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼
┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼
┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼
┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼
┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼

   10      11      12      13      14      15      16      17

 ** WARNING: Notes exist outside the defined render window! **
```

(3) Alternatively, we could set a *working window* with `--working-window <window>` for the `scale` operation, limiting *scale* generation to this very range (but now we're ourselves responsible to span the correct window containing all notes):

```
$ ./fretter.sh -oScale -sAEOLIAN -rA -m12,15

Fretter v0.1.3 - Fretboard Diagram Generator


Scale A AEOLIAN (standard guitar tuning)

   12      13      14      15

┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼
┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼
┼──♭7 ──┼───────┼── 1 ──┼───────┼
┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼
┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼
┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼

   12      13      14      15

```

This functionality is used to produce specific diagrams in situations where more control is required (and mostly in combination with a *render window*):

```
$ ./fretter.sh -oScale -sAEOLIAN -rA -e10,17 -m12,15

Fretter v0.1.3 - Fretboard Diagram Generator


Scale A AEOLIAN (standard guitar tuning)

   10      11      12      13      14      15      16      17

┼───────┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼───────┼
┼───────┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼───────┼
┼───────┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼───────┼───────┼
┼───────┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼───────┼
┼───────┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼───────┼
┼───────┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼───────┼

   10      11      12      13      14      15      16      17

```

(4) Finally, using a *distribution pattern* with `--notes-per-string <count>` and `--position <position>` creates the skewed *scale* patterns we're used to from guitar courses in traditional media and online:

```
$ ./fretter.sh -oScale -sAEOLIAN -rA -n3 -p12

Fretter v0.1.3 - Fretboard Diagram Generator


Scale A AEOLIAN (standard guitar tuning)

   12      13      14      15      16      17

┼───────┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼
┼───────┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼
┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼───────┼
┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼───────┼
┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼───────┼
┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼───────┼

   12      13      14      15      16      17

```

## Modifying starting point of a scale diagram

Note that the diagram starts at the *position* given and distributes notes upward; it does not care which *interval* of the scale we hit, giving us maximum freedom for our scale layout.

In this specific situation, if we wanted to start it on a root note, we could additionally specify a string index (starting at 0), to exactly hit the root note with `--position 1,12` (no whitespace allowed):

```
$ ./fretter.sh -oScale -sAEOLIAN -rA -n3 -p1,12

Fretter v0.1.3 - Fretboard Diagram Generator


Scale A AEOLIAN (standard guitar tuning)

   12      13      14      15      16      17

┼───────┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼
┼───────┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼
┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼───────┼
┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼───────┼
┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼───────┼
┼───────┼───────┼───────┼───────┼───────┼───────┼

   12      13      14      15      16      17

```

## Changing fret labeling

Finally, we can change the *fret labeling* with `--label <label>`, for example to render *scale membership* or *note names*:

```
$ ./fretter.sh -oScale -sAEOLIAN -rA -n3 -p1,12 -aSCALE_MEMBERSHIP
Fretter v0.1.3 - Fretboard Diagram Generator


Scale A AEOLIAN (standard guitar tuning)

   12      13      14      15      16      17

┼───────┼── ○ ──┼───────┼── ○ ──┼───────┼── ◉ ──┼
┼───────┼── ○ ──┼───────┼── ○ ──┼───────┼── ○ ──┼
┼── ○ ──┼───────┼── ◉ ──┼───────┼── ○ ──┼───────┼
┼── ○ ──┼───────┼── ○ ──┼── ○ ──┼───────┼───────┼
┼── ◉ ──┼───────┼── ○ ──┼── ○ ──┼───────┼───────┼
┼───────┼───────┼───────┼───────┼───────┼───────┼

   12      13      14      15      16      17

```

```
$ ./fretter.sh -oScale -sAEOLIAN -rA -n3 -p1,12 -aNOTE_NAME

Fretter v0.1.3 - Fretboard Diagram Generator


Scale A AEOLIAN (standard guitar tuning)

   12      13      14      15      16      17

┼───────┼──F ₆──┼───────┼──G ₆──┼───────┼──A ₆──┼
┼───────┼──C ₆──┼───────┼──D ₆──┼───────┼──E ₆──┼
┼──G ₅──┼───────┼──A ₅──┼───────┼──B ₅──┼───────┼
┼──D ₅──┼───────┼──E ₅──┼──F ₅──┼───────┼───────┼
┼──A ₄──┼───────┼──B ₄──┼──C ₅──┼───────┼───────┼
┼───────┼───────┼───────┼───────┼───────┼───────┼

   12      13      14      15      16      17

 * Hint: Be aware that octave indices displayed conform to MIDI pitch naming (where middle C corresponds to C₄). *
 ```

This finishes our walkthrough. You can find additional details and uses of the application in the [Option reference](REFERENCE.md).

