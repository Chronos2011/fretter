# fretter - a generic, automatic / algorithmic, command-line fretboard diagram generator

*fretter* is a sophisticated command-line tool, generating various diagrams for fretted instruments; it is extremely powerful and can - due to combinatorial explosion - generate millions of different diagrams. The tool is not intended for beginners but advanced musicians.

## Features

This section lists currently implemented, planned and rejected features, providing a quick overview when evaluating the use of this tool.

### Currently implemented

* Fully automatic / algorithmic: no manual entry of notes required
* Command line: easily accessible via command-line and batchable
* Operation modes:
    * Board
    * Scale
    * Arpeggio
    * Chord
* Selection of instrument tuning, currently 12 options
* Configurable fretboard size
* Render options:
    * Configurable render window
    * Configurable showing of open strings
    * Fret labeling options:
        * MIDI pitch
        * Note name
        * Interval symbol
        * Interval half steps
        * Scale membership
* Scale mode:
    * Selection of scale, currently 52 options
    * Selection of pitch, currently 181 options (only 12 relevant, though)
    * Configurable position in "Patterned" sub-mode
    * Sub-modes:
        * Fretboard: shows all occurences of in-scale frets
        * Windowed: shows all occurences of in-scale frets in a fixed window
        * Patterned: finds and shows common scale patterns (i.e. 2 or 3 notes per string)
* Arpeggio mode:
    * Selection of chord, currently 31 options
    * Selection of pitch, currently 181 options (only 12 relevant, though)
* Chord mode:
    * Selection of chord, currently 31 options
    * Selection of pitch, currently 181 options
    * Configurable position
    * Configurable maximum position deviation
    * Configurable maximum chord width
    * Configurable use of open strings
    * Configurable use of unused (inner) strings
    * Generation of all potential chord combinations
    * Configurable sanity checking of fingerings
    * Automatic bar detection
    * Ordering of fingerings by usefulness

### Planned features

* Presentation modes:
    * UTF
    * ASCII
    * HTML
* Additional and customizable string tunings
* Scale mode:
    * Additional and customizable scales
* Arpeggio mode:
    * Additional and customizable arpeggios
* Chord mode:
    * Additional and customizable chords
    * Thumb mode

### Not implemented, currently not planned

* add-n-chords with n >= 9 (strongly discussed / challenged)
* 24 fret scales (definitely negligible)

## Requirements

 * Java 8+ (tested)
 * Apache Maven 3.8+ (tested), Apache Maven 2+ (assumed)

## Usage

### Warning

**Do not blindly follow** the diagrams generated: although the diagrams generated are harmonically sound, most will not be on the easy and / or standard track to learning an instrument. You should already have decent understanding of the corresponding diagram types in order to identify whether a result is a reasonable step on your path of learning.

It seems that **Wikipedia is unreliable** regarding scale construction and / or naming; please double-check that the scale you use contains the correct intervals (e.g. with `-oScale -s<scale name> -rC_4 -f12`).

### Installing and running

The release zip file is self-contained, no installation is required. Simply extract the zip with your favorite tool, enter the `fretter` folder, then:

* on a Linux OS, run the tool with `./fretter.sh <arguments>`.
* on a Windows OS, run the tool with `fretter.bat <arguments>`.

### A simple walkthrough

This section presents the main features of *fretter*. We're not claiming to produce art here - this is the user's duty. Walking through this example (up to section *Option reference*) should give you a descent understanding of the tool.

#### Objective

As an easy example, assume we  wanted to fret an <a href="https://en.wikipedia.org/wiki/Andalusian_cadence">Andalusian cadence</a>; for the chord progression, we choose the degrees *i*, *VII*, *VI*, and *V7*; later, we plan to apply the *Aeolian mode* for improvised soloing. Selecting *A* as tonic, we obtain *Am* / *G* / *F* / *E7* and *A Aeolian*.

We want to create simple fingerings without big position changes. Also, we aim for high pitches to create a corresponding lighter feeling.

#### A fingering for A minor

In order to produce a fingering for A minor, we must run *fretter* in `chord` mode of operation with `--operation Chord`. There are some options in `chord` mode which are mandatory, obviously the root pitch and the type of chord. So we must add `--root A_3` to indicate a chord on *A*; the octave index `3` does not have an effect and we will shortly see, how to affect the position. Additionally, we must add `--chord Min` for a *minor chord*. Running *fretter* with these options will still not be sufficient, because it would find more than 100 potential fingerings, even though multiple sanity checks and filters are enabled by default. We must hint the algorithm to where we want to find a chord with the `--position 12` parameter setting, asking for fingerings around 12th fret:

```
$ ./fretter.sh --operation Chord --root A_3 --chord Min --position 12

Fretter v0.1.2 - Fretboard Diagram Generator

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

#### Dealing with big result sets

The algorithm finds a total of 38 fingerings, which is quite a range to choose from. The result is separated into pages and we can select individual pages by adding `--page <number>` (page numbers starting with 0) and item count on a page with `--item-count <count>`:

```
$ ./fretter.sh --operation Chord --root A_3 --chord Min --position 12 --page 2 --item-count 2

Fretter v0.1.2 - Fretboard Diagram Generator

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

#### Nailing down the chords with additional options

Back to our result from `--operation Chord --root A_3 --chord Min --position 12`: we received a total of 38 fingerings, so we can be generous and add more requirements to our fingerings search. First, we want to restrict the *maximum width* of a fingering to 3 frets with `--width 3`; second, we insist on *position* 12 by specifying `--deviation 0`, indicating that we want to deviate at most 0 frets from the position:

```
$ ./fretter.sh --operation Chord --root A_3 --chord Min --position 12 --width 3 --deviation 0

Fretter v0.1.2 - Fretboard Diagram Generator

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

#### What about other tunings? And instruments?

As you may have noticed, the fingerings produced were for a guitar in standard tuning, which is the default of *fretter*. You can select a different *tuning* (and thereby implicitly another instrument, see also `--fret-count`), e.g. for a ukulele with `--tuning Standard_ukulele`:

```
$ ./fretter.sh --operation Chord --root A_3 --chord Min --position 12 --width 3 --deviation 0 --tuning Standard_ukulele

Fretter v0.1.2 - Fretboard Diagram Generator

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

(This is an example - position 12 probably wouldn't make much sense on ukulele).

#### A fingering for G major

Back to our original example and building on what we have learned so far, we ask for a G major fingering now:

```
$ ./fretter.sh --operation Chord --root G_3 --chord Maj --position 12 --width 3 --deviation 0
```

Remember to watch out for result pages, because we found the most interesting fingering on `--page 1`:

```
$ ./fretter.sh --operation Chord --root G_3 --chord Maj --position 12 --width 3 --deviation 0 --page 1

Fretter v0.1.2 - Fretboard Diagram Generator

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

#### Using short option names

Entering all those option names becomes tedious; fortunately, short option names exist for most options. We can shorten the last command to:

```
$ ./fretter.sh -oChord -rG_3 -cMaj -p12 -w3 -d0 -g1

Fretter v0.1.2 - Fretboard Diagram Generator

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

We tried to make the short option names catchy so that they can be remembered easily. Check the *option reference* for more details. 

#### Listing chord types (and other option names) available

How did we get to the chord type names `Min` and `Maj` in the first place? You can list all named options (*tunings*, *scales*, *chords*, *pitches*, and (fret) *labels*) with the `--list <option>` option, e.g. as of version *0.1.2* the following chord types are available:

```
$ ./fretter.sh --list Chord

Fretter v0.1.2 - Fretboard Diagram Generator

Chord: ADD2, ADD4, ADD6, ALPHA, AUG, AUG7, AUG_MAJ7, BETA, DIM, DIM7, DOM7, DOM7_DIM5, DREAM, GAMMA, MAJ, MAJ7, MAJ76, MIN, MIN7, MIN7_DIM5, MIN_ADD2, MIN_ADD6, MIN_DIM5_MAJ7, MIN_MAJ7, MIN_MIN6, POW4, POW5, SUS2, SUS4, VIENNESE_INVERSE, VIENNESE_PRIME
```

#### A fingering for F major

We did not find a fingering matching our demands with `-oChord -cMaj -rF_3 -p12 -w3 -d0` and decided to release requirements a little:

```
$ ./fretter.sh -o Chord -c Maj -r F_3 -p12 -w3 -d2 -g1

Fretter v0.1.2 - Fretboard Diagram Generator

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

#### Showing diagnostics

Finding a specialized chord for a specific use can be a tough business. Sometimes it is worth checking what filtering the fingering generation algorithm applied with `--verbose 1`:

```
$ ./fretter.sh -o Chord -c Maj -r F_3 -p12 -w3 -d2 -v1

Fretter v0.1.2 - Fretboard Diagram Generator

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
Showing fingerings 1 through 4 (of 16)
```

#### Modifying results filtering

These diagnostics tell us that from the 1386 brute-forced combinations built, 1308 were duplicates, resulting in a total 78 unique fingerings. After that various filters are being applied that can be modified by the user.

We won't cover sanity checks here, see the *Option reference* for details.

Interestingly, we see that 28 and 32 fingerings were removed due to open and unused (inner) strings respectively. Maybe, we find some interesting chords by undoing these filters with `--allow-open-strings` and `--allow-unused-strings`:

```
$ ./fretter.sh -oChord -cMaj -rF_3 -p12 -w3 -d2 --allow-open-strings --allow-unused-strings

Fretter v0.1.2 - Fretboard Diagram Generator

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

#### A fingering for E dominant seventh

Finding open string fingerings for the *E7* yields much more fingerings so that we can even reduce to `--width 2` and `--deviation 0`:

```
$ ./fretter.sh -oChord -cDom7 -rE_3 -p2,12 -w2 d0 --allow-open-strings --allow-unused-strings -g1

Fretter v0.1.2 - Fretboard Diagram Generator

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

#### Charting the fretboard with A aeolian

Now that we have the fingerings for our *chord* progression, we will look at some diagrams for the purpose of soloing. Displaying *scale* notes on the fretboard requires *operation* `scale`. Again, mandatory options must be filled, in case of `scale` these are `--scale <scale>` and `--root <pitch>`:

```
$ ./fretter.sh -oScale -sAEOLIAN -rA_3

Fretter v0.1.2 - Fretboard Diagram Generator


 0     1       2       3       4       5       6       7       8       9      10      11      12      13      14      15      16      17      18      19      20      21      22      23      24

 5 ├──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼
 2 ├──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼
♭7 ├───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼
 4 ├───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼
 1 ├───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼
 5 ├──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼

 0     1       2       3       4       5       6       7       8       9      10      11      12      13      14      15      16      17      18      19      20      21      22      23      24
```

#### Limiting board size

As we can see, this produces a huge, 24 fretsboard which is quite unwieldy in this case. We have four options to limit the size: (1) explicitly setting *fretboard size* with `--fret-count <count>`, (2) limiting the *render window* with `--render-window <window>`, (3) specify a *working window* with `--working-window <window>`, or (4) using a *distribution pattern* with `--notes-per-string <count>` and `--position <position>`.

(1) Explicitly setting the *fretboard size* with `--fret-count <count>` might help in situations where you're just interested in the lower part of the fretboard. (It would most probably be wrong in `--operation Chord` because it would limit the options available for the fingerings generation algorithm):

```
$ ./fretter.sh -oScale -sAEOLIAN -rA_3 -f12

Fretter v0.1.2 - Fretboard Diagram Generator


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
$ ./fretter.sh -oScale -sAEOLIAN -rA_3 -e10,17

Fretter v0.1.2 - Fretboard Diagram Generator


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
$ ./fretter.sh -oScale -sAEOLIAN -rA_3 -m12,15

Fretter v0.1.2 - Fretboard Diagram Generator


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
$ ./fretter.sh -oScale -sAEOLIAN -rA_3 -e10,17 -m12,15

Fretter v0.1.2 - Fretboard Diagram Generator


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
$ ./fretter.sh -oScale -sAEOLIAN -rA_3 -n3 -p12

Fretter v0.1.2 - Fretboard Diagram Generator


   12      13      14      15      16      17

┼───────┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼
┼───────┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼
┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼───────┼
┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼───────┼
┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼───────┼
┼── 5 ──┼──♭6 ──┼───────┼──♭7 ──┼───────┼───────┼

   12      13      14      15      16      17

```

#### Modifying starting point of a scale diagram

Note that the diagram starts at the *position* given and distributes notes upward; it does not care which *interval* of the scale we hit, giving us maximum freedom for our scale layout.

In this specific situation, if we wanted to start it on a root note, we could additionally specify a string index (starting at 0), to exactly hit the root note with `--position 1,12` (no whitespace allowed):

```
$ ./fretter.sh -oScale -sAEOLIAN -rA_3 -n3 -p1,12

Fretter v0.1.2 - Fretboard Diagram Generator


   12      13      14      15      16      17

┼───────┼──♭6 ──┼───────┼──♭7 ──┼───────┼── 1 ──┼
┼───────┼──♭3 ──┼───────┼── 4 ──┼───────┼── 5 ──┼
┼──♭7 ──┼───────┼── 1 ──┼───────┼── 2 ──┼───────┼
┼── 4 ──┼───────┼── 5 ──┼──♭6 ──┼───────┼───────┼
┼── 1 ──┼───────┼── 2 ──┼──♭3 ──┼───────┼───────┼
┼───────┼───────┼───────┼───────┼───────┼───────┼

   12      13      14      15      16      17

```

#### Changing fret labeling

Finally, we can change the *fret labeling* with `--label <label>`, for example to render *scale membership* or *note names*:

```
$ ./fretter.sh -oScale -sAEOLIAN -rA_3 -n3 -p1,12 -aSCALE_MEMBERSHIP

Fretter v0.1.2 - Fretboard Diagram Generator


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
$ ./fretter.sh -oScale -sAEOLIAN -rA_3 -n3 -p1,12 -aNOTE_NAME

Fretter v0.1.2 - Fretboard Diagram Generator


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

This finishes our walkthrough. You can find additional details and uses of the application in the *Option reference* below.

## Option reference

### Overview

*fretter* is a command-line tool using user-provided arguments to control its behavior. The following list shows the currently implemented options:

```
$ ./fretter.sh -h

Fretter v0.1.2 - Fretboard Diagram Generator

usage: Fretter [options]
 -a,--label <label>                 the fret labeling to be used, INTERVAL_NAME (NOTE_NAME for operation 'board') if not
                                    set
    --allow-duplicate-pitches       allow duplicate pitches in operation 'chord'
    --allow-frets-left-of-bar       allow frets left of a bar in operation 'chord'
    --allow-frets-top-left-of-bar   allow frets top left of a bar in operation 'chord'
    --allow-more-than-four-frets    allow more than 4 different frets in operation 'chord'
    --allow-open-strings            allow open strings during in operation 'chord'
    --allow-unused-strings          allow unused (inner) strings in operation 'chord'
 -c,--chord <chord>                 the chord to be used, mandatory for operations 'arpeggio' and 'chord'
 -d,--deviation <deviation>         the maximum deviation (from requested position) of a chord, 2 if not set
 -e,--render-window <window>        the fret window to be rendered from start x to end y (format: 'x,y'), optionally
                                    followed by ',open' to render open strings
 -f,--fret-count <count>            the number of frets of the board, 24 if not set
 -g,--page <number>                 the page number to be shown for operation 'chord', 0 if not set
 -h,--help                          prints usage details
 -i,--item-count <count>            the number of items per page to be shown for operation 'chord', 4 if not set
 -l,--list <item>                   lists items to be used with other parameters, either 'TUNING', 'SCALE', 'CHORD',
                                    'PITCH', or 'LABEL'
 -m,--working-window <window>       the fret window to be used from start x to end y (format: 'x,y'), optional for
                                    operation 'scale'
 -n,--notes-per-string <count>      the number of notes per string to be used, optional for operation 'scale'
 -o,--operation <operation>         the operation to be executed, either 'BOARD', 'SCALE', 'ARPEGGIO', or 'CHORD'
 -p,--position <position>           either the fret x position to be used (format: 'x') or string position x and fret
                                    position y to be used (format: 'x,y' - no whitespace), mandatory for operations
                                    'chord' and 'scale' with distribution pattern
 -r,--root <pitch>                  the root pitch to be used, mandatory for operations 'scale', 'arpeggio', and 'chord'
 -s,--scale <scale>                 the scale to be used, mandatory for operation 'scale'
 -t,--tuning <tuning>               the tuning to be used, EADGBE if not set
 -v,--verbosity <level>             the level of verbosity to be used
 -w,--width <width>                 the maximum width of a chord (excluding potential open strings), 5 if not set
```

*The short and long options can be used interchangeably.*

### Print help (-h, --help)

Prints the list of options given above.

### List named option values (-l, --list)

The options `-a`, `-c`, `-o`, `-r`, and `-s` (resp. `--label`, `--chord`, `--operation`, `--root`, and `--scale`) require a named option value. These named options can be listed:

```
$ ./fretter.sh -l LABEL

Fretter v0.1.2 - Fretboard Diagram Generator

FretLabeling: MIDI_PITCH, NOTE_NAME, INTERVAL_SYMBOL, INTERVAL_STEPS, SCALE_MEMBERSHIP
```

*Named option values are case insensitve.*

### Mode of operation (-o, --operation)

The most important option is the selection of the mode of operation. The following modes of operation are defined; also listed are mandatory options that are required for a specific mode of operation:

|Mode|Diagram|Mandatory option(s)|
|---|---|---|
|`Board`|Shows all notes on the board regardless of scale (effectively a chromatic scale rooted on 1st string)|*none*|
|`Scale`|(1) Shows all notes on the board that are 'in scale', or (2) shows all notes in a working window that are 'in scale', or (3) shows a scale distribution pattern|`-s`, `--scale`; `-r`, `--root`|
|`Arpeggio`|Shows all notes on the board that are 'in chord'|`-c`, `--chord`; `-r`, `--root`|
|`Chord`|Shows all fingerings on the board that produce the chord (filtered by various options)|`-c`, `--chord`; `-r`, `--root`; `-p`, `--position`|

```
$ ./fretter.sh -oBOARD -f12

Fretter v0.1.2 - Fretboard Diagram Generator


 0     1       2       3       4       5       6       7       8       9      10      11      12   

E ₅├──F ₅──┼──F♯₅──┼──G ₅──┼──G♯₅──┼──A ₅──┼──A♯₅──┼──B ₅──┼──C ₆──┼──C♯₆──┼──D ₆──┼──D♯₆──┼──E ₆──┼
B ₄├──C ₅──┼──C♯₅──┼──D ₅──┼──D♯₅──┼──E ₅──┼──F ₅──┼──F♯₅──┼──G ₅──┼──G♯₅──┼──A ₅──┼──A♯₅──┼──B ₅──┼
G ₄├──G♯₄──┼──A ₄──┼──A♯₄──┼──B ₄──┼──C ₅──┼──C♯₅──┼──D ₅──┼──D♯₅──┼──E ₅──┼──F ₅──┼──F♯₅──┼──G ₅──┼
D ₄├──D♯₄──┼──E ₄──┼──F ₄──┼──F♯₄──┼──G ₄──┼──G♯₄──┼──A ₄──┼──A♯₄──┼──B ₄──┼──C ₅──┼──C♯₅──┼──D ₅──┼
A ₃├──A♯₃──┼──B ₃──┼──C ₄──┼──C♯₄──┼──D ₄──┼──D♯₄──┼──E ₄──┼──F ₄──┼──F♯₄──┼──G ₄──┼──G♯₄──┼──A ₄──┼
E ₃├──F ₃──┼──F♯₃──┼──G ₃──┼──G♯₃──┼──A ₃──┼──A♯₃──┼──B ₃──┼──C ₄──┼──C♯₄──┼──D ₄──┼──D♯₄──┼──E ₄──┼

 0     1       2       3       4       5       6       7       8       9      10      11      12   

 * Hint: Be aware that octave indices displayed conform to MIDI pitch naming (where middle C corresponds to C₄). *
```

### Fret count (-f, --fret-count)

Specifies the number of frets of the instrument. Implicitly, another fret will be added for the instrument nut:

```
$ ./fretter.sh -oboard -f4

Fretter v0.1.2 - Fretboard Diagram Generator


 0     1       2       3       4   

E ₅├──F ₅──┼──F♯₅──┼──G ₅──┼──G♯₅──┼
B ₄├──C ₅──┼──C♯₅──┼──D ₅──┼──D♯₅──┼
G ₄├──G♯₄──┼──A ₄──┼──A♯₄──┼──B ₄──┼
D ₄├──D♯₄──┼──E ₄──┼──F ₄──┼──F♯₄──┼
A ₃├──A♯₃──┼──B ₃──┼──C ₄──┼──C♯₄──┼
E ₃├──F ₃──┼──F♯₃──┼──G ₃──┼──G♯₃──┼

 0     1       2       3       4   

 * Hint: Be aware that octave indices displayed conform to MIDI pitch naming (where middle C corresponds to C₄). *
```

### Tuning (-t, --tuning)

This option is used to change the tuning of an instrument (and implicitly define the number of strings):

```
$ ./fretter.sh -oboard -f12 -tSTANDARD_BASS

Fretter v0.1.2 - Fretboard Diagram Generator


 0     1       2       3       4       5       6       7       8       9      10      11      12   

G ₃├──G♯₃──┼──A ₃──┼──A♯₃──┼──B ₃──┼──C ₄──┼──C♯₄──┼──D ₄──┼──D♯₄──┼──E ₄──┼──F ₄──┼──F♯₄──┼──G ₄──┼
D ₃├──D♯₃──┼──E ₃──┼──F ₃──┼──F♯₃──┼──G ₃──┼──G♯₃──┼──A ₃──┼──A♯₃──┼──B ₃──┼──C ₄──┼──C♯₄──┼──D ₄──┼
A ₂├──A♯₂──┼──B ₂──┼──C ₃──┼──C♯₃──┼──D ₃──┼──D♯₃──┼──E ₃──┼──F ₃──┼──F♯₃──┼──G ₃──┼──G♯₃──┼──A ₃──┼
E ₂├──F ₂──┼──F♯₂──┼──G ₂──┼──G♯₂──┼──A ₂──┼──A♯₂──┼──B ₂──┼──C ₃──┼──C♯₃──┼──D ₃──┼──D♯₃──┼──E ₃──┼

 0     1       2       3       4       5       6       7       8       9      10      11      12   

 * Hint: Be aware that octave indices displayed conform to MIDI pitch naming (where middle C corresponds to C₄). *
```

### Fret labeling (-a, --label)

Depending of user preference, different variations of fret labeling may be used:

```
$ ./fretter.sh -oScale -sMajor -rE_3 -f12 -aScale_membership

Fretter v0.1.2 - Fretboard Diagram Generator


 0     1       2       3       4       5       6       7       8       9      10      11      12   

 ◉ ├───────┼── ○ ──┼───────┼── ○ ──┼── ○ ──┼───────┼── ○ ──┼───────┼── ○ ──┼───────┼── ○ ──┼── ◉ ──┼
 ○ ├───────┼── ○ ──┼───────┼── ○ ──┼── ◉ ──┼───────┼── ○ ──┼───────┼── ○ ──┼── ○ ──┼───────┼── ○ ──┼
   ├── ○ ──┼── ○ ──┼───────┼── ○ ──┼───────┼── ○ ──┼───────┼── ○ ──┼── ◉ ──┼───────┼── ○ ──┼───────┼
   ├── ○ ──┼── ◉ ──┼───────┼── ○ ──┼───────┼── ○ ──┼── ○ ──┼───────┼── ○ ──┼───────┼── ○ ──┼───────┼
 ○ ├───────┼── ○ ──┼───────┼── ○ ──┼───────┼── ○ ──┼── ◉ ──┼───────┼── ○ ──┼───────┼── ○ ──┼── ○ ──┼
 ◉ ├───────┼── ○ ──┼───────┼── ○ ──┼── ○ ──┼───────┼── ○ ──┼───────┼── ○ ──┼───────┼── ○ ──┼── ◉ ──┼

 0     1       2       3       4       5       6       7       8       9      10      11      12   

```

### Root (-r, --root)

All modes of operation except `board` require that you specify a root pitch:

```
$ ./fretter.sh -oscale -smajor -rFS2 -f12

Fretter v0.1.2 - Fretboard Diagram Generator


 0     1       2       3       4       5       6       7       8       9      10      11      12   

   ├── 7 ──┼── 1 ──┼───────┼── 2 ──┼───────┼── 3 ──┼── 4 ──┼───────┼── 5 ──┼───────┼── 6 ──┼───────┼
 4 ├───────┼── 5 ──┼───────┼── 6 ──┼───────┼── 7 ──┼── 1 ──┼───────┼── 2 ──┼───────┼── 3 ──┼── 4 ──┼
   ├── 2 ──┼───────┼── 3 ──┼── 4 ──┼───────┼── 5 ──┼───────┼── 6 ──┼───────┼── 7 ──┼── 1 ──┼───────┼
   ├── 6 ──┼───────┼── 7 ──┼── 1 ──┼───────┼── 2 ──┼───────┼── 3 ──┼── 4 ──┼───────┼── 5 ──┼───────┼
   ├── 3 ──┼── 4 ──┼───────┼── 5 ──┼───────┼── 6 ──┼───────┼── 7 ──┼── 1 ──┼───────┼── 2 ──┼───────┼
   ├── 7 ──┼── 1 ──┼───────┼── 2 ──┼───────┼── 3 ──┼── 4 ──┼───────┼── 5 ──┼───────┼── 6 ──┼───────┼

 0     1       2       3       4       5       6       7       8       9      10      11      12   

```

Pitches can be remembered easily if their format is understood. These are composed of three consecutive characters with the following meanings:

|Character|Meaning|Possible values|
|---|---|---|
|1st|Note name| `A`, `B`, `C`, `D`, `E`, `F`, `G` |
|2nd|Modifier| `S` (sharp), `_` (natural), `F` (flat)|
|3rd|Octave| `M` (-1), `0`, `1`, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9`|

Please take note: *according to <a href="https://en.wikipedia.org/wiki/Scientific_pitch_notation">scientific pitch notation</a>, octave indices increase from note `B` to `C`.*

### Scale (-s, --scale)

The `scale` mode of operation requires that you specify a scale to be used:

```
$ ./fretter.sh -oScale -sMAJOR -rC_4 -f12

Fretter v0.1.2 - Fretboard Diagram Generator


 0     1       2       3       4       5       6       7       8       9      10      11      12   

 3 ├── 4 ──┼───────┼── 5 ──┼───────┼── 6 ──┼───────┼── 7 ──┼── 1 ──┼───────┼── 2 ──┼───────┼── 3 ──┼
 7 ├── 1 ──┼───────┼── 2 ──┼───────┼── 3 ──┼── 4 ──┼───────┼── 5 ──┼───────┼── 6 ──┼───────┼── 7 ──┼
 5 ├───────┼── 6 ──┼───────┼── 7 ──┼── 1 ──┼───────┼── 2 ──┼───────┼── 3 ──┼── 4 ──┼───────┼── 5 ──┼
 2 ├───────┼── 3 ──┼── 4 ──┼───────┼── 5 ──┼───────┼── 6 ──┼───────┼── 7 ──┼── 1 ──┼───────┼── 2 ──┼
 6 ├───────┼── 7 ──┼── 1 ──┼───────┼── 2 ──┼───────┼── 3 ──┼── 4 ──┼───────┼── 5 ──┼───────┼── 6 ──┼
 3 ├── 4 ──┼───────┼── 5 ──┼───────┼── 6 ──┼───────┼── 7 ──┼── 1 ──┼───────┼── 2 ──┼───────┼── 3 ──┼

 0     1       2       3       4       5       6       7       8       9      10      11      12   

```

### Chord (-c, --chord)

Specifying a chord is required in modes `chord` and `arpeggio`:

```
$ ./fretter.sh -oARPEGGIO -cDOM7 -rE_4 -f12

Fretter v0.1.2 - Fretboard Diagram Generator


 0     1       2       3       4       5       6       7       8       9      10      11      12   

 1 ├───────┼───────┼───────┼── 3 ──┼───────┼───────┼── 5 ──┼───────┼───────┼──♭7 ──┼───────┼── 1 ──┼
 5 ├───────┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼───────┼───────┼── 3 ──┼───────┼───────┼── 5 ──┼
   ├── 3 ──┼───────┼───────┼── 5 ──┼───────┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼───────┼───────┼
♭7 ├───────┼── 1 ──┼───────┼───────┼───────┼── 3 ──┼───────┼───────┼── 5 ──┼───────┼───────┼──♭7 ──┼
   ├───────┼── 5 ──┼───────┼───────┼──♭7 ──┼───────┼── 1 ──┼───────┼───────┼───────┼── 3 ──┼───────┼
 1 ├───────┼───────┼───────┼── 3 ──┼───────┼───────┼── 5 ──┼───────┼───────┼──♭7 ──┼───────┼── 1 ──┼

 0     1       2       3       4       5       6       7       8       9      10      11      12   

```

### Position (-p, --position)

Operation mode `chord` requires a *position*, indicating the preferred position for the fingerings. The generation algorithm may deviate (see *maximum deviation*) from this position.

Positions may be specified in two formats: *fret-only* or *string-and-fret*; in fret-only positioning you specify just a fret index (0 corresponding to the open string), while in string-and-fret positioning
you specify a string index (starting with 0) and a fret index, separated by a comma.

Specifying a fret only (e.g. `--position 12`) gives the algorithm all freedom to start at a string of its preference. Specifying string and fret (e.g. `--position 2,4`) limits the generation of fingerings to strings equal or greater than the starting string (2 in the example).

```
$ ./fretter.sh -oCHORD -cDOM7 -rE_4 -p2,2

Fretter v0.1.2 - Fretboard Diagram Generator

Showing fingerings 1 through 1 (of 1)

    2       3       4   

┼───────┼───────┼── 3 ──┼
┼───────┼──♭7 ──┼───────┼
┼───────┼───────┼── 5 ──┼
┼── 1 ──┼───────┼───────┼
┼───────┼───────┼───────┼
┼───────┼───────┼───────┼

    2       3       4   

```

```
$ ./fretter.sh -oCHORD -cDOM7 -rE_4 -p12

Fretter v0.1.2 - Fretboard Diagram Generator

Showing fingerings 1 through 4 (of 49)

   12      13      14   

┼── 1 ──┼───────┼───────┼
┼── 5 ──┼───────┼───────┼
┼───────┼── 3 ──┼───────┼
┼──♭7 ──┼───────┼───────┼
┼───────┼───────┼── 5 ──┼
┼── 1 ──┼───────┼───────┼

   12      13      14   


   12      13      14   

┼───────┼───────┼───────┼
┼───────┼───────┼───────┼
┼───────┼── 3 ──┼───────┼
┼──♭7 ──┼───────┼───────┼
┼───────┼───────┼── 5 ──┼
┼── 1 ──┼───────┼───────┼

   12      13      14   


   12      13   

┼── 1 ──┼───────┼
┼── 5 ──┼───────┼
┼───────┼── 3 ──┼
┼──♭7 ──┼───────┼
┼───────┼───────┼
┼───────┼───────┼

   12      13   


    9      10      11   

┼───────┼──♭7 ──┼───────┼
┼── 3 ──┼───────┼───────┼
┼── 1 ──┼───────┼───────┼
┼── 5 ──┼───────┼───────┼
┼───────┼───────┼── 3 ──┼
┼───────┼──♭7 ──┼───────┼

    9      10      11   

```

### Page selection (-g, --page)

In *chord* mode, very long lists of fingerings may be generated. In order to prevent flooding of the console, pagination was implemented. This option sets the result page to be shown (starting with 0):

```
$ ./fretter.sh -oCHORD -cMAJ -rC_4 -p2,10 -g1

Fretter v0.1.2 - Fretboard Diagram Generator

Showing fingerings 5 through 8 (of 11)

   12      13   

┼── 3 ──┼───────┼
┼───────┼── 1 ──┼
┼── 5 ──┼───────┼
┼───────┼───────┼
┼───────┼───────┼
┼───────┼───────┼

   12      13   


    5       6       7       8   

┼───────┼───────┼───────┼── 1 ──┼
┼── 3 ──┼───────┼───────┼───────┼
┼── 1 ──┼───────┼───────┼───────┼
┼── 5 ──┼───────┼───────┼───────┼
┼───────┼───────┼───────┼───────┼
┼───────┼───────┼───────┼───────┼

    5       6       7       8   


   12      13      14   

┼── 3 ──┼───────┼───────┼
┼───────┼── 1 ──┼───────┼
┼── 5 ──┼───────┼───────┼
┼───────┼───────┼── 3 ──┼
┼───────┼───────┼───────┼
┼───────┼───────┼───────┼

   12      13      14   


   12      13      14   

┼───────┼───────┼───────┼
┼───────┼── 1 ──┼───────┼
┼── 5 ──┼───────┼───────┼
┼───────┼───────┼── 3 ──┼
┼───────┼───────┼───────┼
┼───────┼───────┼───────┼

   12      13      14   

```

### Page size (-i, --item-count)

In *chord* mode, very long lists of fingerings may be generated. In order to prevent flooding of the console, pagination was implemented. This option sets the size of result pages:

```
$ ./fretter.sh -oCHORD -cMAJ -rC_4 -p2,10 -i2

Fretter v0.1.2 - Fretboard Diagram Generator

Showing fingerings 1 through 2 (of 11)

    8       9      10   

┼── 1 ──┼───────┼───────┼
┼── 5 ──┼───────┼───────┼
┼───────┼── 3 ──┼───────┼
┼───────┼───────┼── 1 ──┼
┼───────┼───────┼───────┼
┼───────┼───────┼───────┼

    8       9      10   


    8       9      10   

┼───────┼───────┼───────┼
┼── 5 ──┼───────┼───────┼
┼───────┼── 3 ──┼───────┼
┼───────┼───────┼── 1 ──┼
┼───────┼───────┼───────┼
┼───────┼───────┼───────┼

    8       9      10   

```

### Maximum chord width (-w, --width)

Specifies the maximum width of a chord in `chord` mode of operation. Of course, the narrower a chord fingering you request, the less fingerings you will get.

```
$ ./fretter.sh -oCHORD -cMAJ -rC_4 -p0,8 -w2

Fretter v0.1.2 - Fretboard Diagram Generator

Showing fingerings 1 through 2 (of 2)

    8       9   

┼── 1 ──┼───────┼
┼── 5 ──┼───────┼
┼───────┼── 3 ──┼
┼───────┼───────┼
┼───────┼───────┼
┼───────┼───────┼

    8       9   


    9      10   

┼───────┼───────┼
┼───────┼───────┼
┼── 3 ──┼───────┼
┼───────┼── 1 ──┼
┼───────┼── 5 ──┼
┼───────┼───────┼

    9      10   

```

### Maximum chord deviation (-d, --deviation)

Specifies the maximum number of frets a fingering may deviate from a requested *position* in operation `chord`.

Deviation is *in addition* to the *maximum width*. So requesting chords at position 8 with width 2, deviation 0 will allow fret ranges [7; 8] and [8; 9]. Changing deviation to 2 will allow fret ranges [5; 6] through [10; 11].

Again, lower deviation means less fingerings.

```
$ ./fretter.sh -oCHORD -cMAJ -rC_4 -p0,8 -w1 -d3

Fretter v0.1.2 - Fretboard Diagram Generator

Showing fingerings 1 through 1 (of 1)

    5   

┼───────┼
┼── 3 ──┼
┼── 1 ──┼
┼── 5 ──┼
┼───────┼
┼───────┼

    5   

```

### Additional chord options and chord sanity checks (--allow-open-strings, --allow-unused-strings, --allow-duplicate-pitches, --allow-frets-left-of-bar, --allow-frets-top-left-of-bar, --allow-more-than-four-frets)

This group of options modifies the filtering applied to the generation of fingerings in mode `chord`. These options do not have short option names; all behave in the same way, removing a filter which is active by default, therefore potentially increasing (but never reducing) the number of fingerings returned.
|Option|Frequency|Meaning|Usage hints|
|---|---|---|---|
|`--allow-open-strings`|Common|Allows fingerings to contain open strings|Depending on context, you may allow open strings (for a richer sound) or deny open strings (to get a moveable shape).|
|`--allow-unused-strings`|Uncommon|Allows fingerings to contain unused strings in between used strings|Depending on context, you may allow unused inner strings (if picking) or deny unused inner strings (if strumming).|
|`--allow-duplicate-pitches`|Rare|Allows fingerings to contain multiple tones with the exact same pitch|You may want to allow these fingerings if you have an instrument / style of play that keeps in tune for a long time. Otherwise these chords start to sound discordant.|
|`--allow-frets-left-of-bar`|Rare|Allows frets to be placed left of a required bar|Unless employing an exotic way of playing your instrument (with feet, tongue, on the knees, etc.), there should be no use for these type of fingerings|
|`--allow-frets-top-left-of-bar`|Uncommon|Allows frets to be placed top left of a required bar|Advanced players may be able to play multiple bars or bars with various fingers, enabling them to play this kind of fingering.|
|`--allow-more-than-four-frets`|Rare|Allows fingerings to contain more than 4 different frets|Advanced players may be able to use the thumb, thus allowing to fret these fingerings.|

```
$ ./fretter.sh -oChord -cMaj -rD_4 -p0 -w2 --allow-open-strings 
Fretter v0.1.2 - Fretboard Diagram Generator

Showing fingerings 1 through 2 (of 2)

 0           2       3   

   ├ ··· ┼── 3 ──┼───────┼
   ├ ··· ┼───────┼── 1 ──┼
   ├ ··· ┼── 5 ──┼───────┼
 1 ├ ··· ┼───────┼───────┼
   ├ ··· ┼───────┼───────┼
   ├ ··· ┼───────┼───────┼

 0           2       3   


    2       3   

┼── 3 ──┼───────┼
┼───────┼── 1 ──┼
┼── 5 ──┼───────┼
┼───────┼───────┼
┼───────┼───────┼
┼───────┼───────┼

    2       3   

```

### Scale working window (-m, --working-window)

Specifying a start and end fret index (separated by a comma) as working window limits diagram generation to this very range of frets (window) in operation mode `scale`. A *scale working window* is mutually exclusive with a *scale distribution pattern*.

This may be helpful in situations where reaching specific frets is difficult or impossible (e.g. close to the instrument body).

```
$ ./fretter.sh -oScale -sMajor -rC_4 -m19,22

Fretter v0.1.2 - Fretboard Diagram Generator


   19      20      21      22   

┼── 7 ──┼── 1 ──┼───────┼── 2 ──┼
┼───────┼── 5 ──┼───────┼── 6 ──┼
┼── 2 ──┼───────┼── 3 ──┼── 4 ──┼
┼── 6 ──┼───────┼── 7 ──┼── 1 ──┼
┼── 3 ──┼── 4 ──┼───────┼── 5 ──┼
┼── 7 ──┼── 1 ──┼───────┼── 2 ──┼

   19      20      21      22   

```

### Scale distribution pattern (-n, --notes-per-string)

Specifying a *scale distribution pattern* by setting *notes-per-string* generates a skewed type of working window in operation mode `scale`. Specifying this option makes the `-p`, `--position` option mandatory to indicate the start position of the *scale*.  A *scale distribution pattern* is mutually exclusive with a *scale working window*.

This option generates the common notes-per-string diagrams found in various guitar courses.

```
$ ./fretter.sh -oScale -sMajor -rC_4 -n3 -p8

Fretter v0.1.2 - Fretboard Diagram Generator


    8       9      10      11      12      13   

┼───────┼───────┼── 2 ──┼───────┼── 3 ──┼── 4 ──┼
┼───────┼───────┼── 6 ──┼───────┼── 7 ──┼── 1 ──┼
┼───────┼── 3 ──┼── 4 ──┼───────┼── 5 ──┼───────┼
┼───────┼── 7 ──┼── 1 ──┼───────┼── 2 ──┼───────┼
┼── 4 ──┼───────┼── 5 ──┼───────┼── 6 ──┼───────┼
┼── 1 ──┼───────┼── 2 ──┼───────┼── 3 ──┼───────┼

    8       9      10      11      12      13   

```

### Render window (-e, --render-window)

This option allows you to explicitly specify which frets are to be rendered in a diagram. Provide a start and end fret index (separated by comma) of the range to be rendered; optionally add `,open` to also render open strings:

```
$ ./fretter.sh -oScale -sMajor -rD_4 -f12 -e1,4

Fretter v0.1.2 - Fretboard Diagram Generator


    1       2       3       4   

├───────┼── 3 ──┼── 4 ──┼───────┼
├───────┼── 7 ──┼── 1 ──┼───────┼
├───────┼── 5 ──┼───────┼── 6 ──┼
├───────┼── 2 ──┼───────┼── 3 ──┼
├───────┼── 6 ──┼───────┼── 7 ──┼
├───────┼── 3 ──┼── 4 ──┼───────┼

    1       2       3       4   

 ** WARNING: Notes exist outside the defined render window! **
```

```
$ ./fretter.sh -oScale -sMajor -rD_4 -f12 -e1,4,open

Fretter v0.1.2 - Fretboard Diagram Generator


 0     1       2       3       4   

 2 ├───────┼── 3 ──┼── 4 ──┼───────┼
 6 ├───────┼── 7 ──┼── 1 ──┼───────┼
 4 ├───────┼── 5 ──┼───────┼── 6 ──┼
 1 ├───────┼── 2 ──┼───────┼── 3 ──┼
 5 ├───────┼── 6 ──┼───────┼── 7 ──┼
 2 ├───────┼── 3 ──┼── 4 ──┼───────┼

 0     1       2       3       4   

 ** WARNING: Notes exist outside the defined render window! **
```

Whenever this option is used, the program will add a warning if valid frets exist outside the rendered fret range.

### Verbosity (-v, --verbosity)

This option will product additional information about diagram generation; currently the only useful output is generated with `-v 1` in operation mode `chord`, where the filtering details are printed:

```
$ ./fretter.sh -oChord -cMin -rA_3 -p12 -w6 -d6 -v1

Fretter v0.1.2 - Fretboard Diagram Generator

Creating combinations
 * Created 10266 combinations
Removing broken
 * Removed 0 nonviable
 * Removed 0 unfinished
   => 10266 remaining
Removing duplicates
 * Removed 9780 duplicates
   => 486 remaining
Checking locations
 * Removed 245 with open strings
 * Removed 115 with unused (inner) strings
 * Removed 19 violating maximum width or deviation
   => 107 remaining
Checking sanity
 * Removed 3 with more than four different frets
 * Removed 44 with duplicate pitches
 * Removed 6 with frets left of bar
 * Removed 5 with frets top left of bar
   => 49 remaining
Showing fingerings 1 through 4 (of 49)

   12      13      14   

┼───────┼───────┼───────┼
┼───────┼──♭3 ──┼───────┼
┼───────┼───────┼── 1 ──┼
┼───────┼───────┼── 5 ──┼
┼── 1 ──┼───────┼───────┼
┼───────┼───────┼───────┼

   12      13      14   


    9      10      11      12   

┼───────┼───────┼───────┼───────┼
┼───────┼── 1 ──┼───────┼───────┼
┼── 5 ──┼───────┼───────┼───────┼
┼───────┼──♭3 ──┼───────┼───────┼
┼───────┼───────┼───────┼── 1 ──┼
┼───────┼───────┼───────┼───────┼

    9      10      11      12   

[...]
```

## Feature requests / bugs / contribution

If you you want to request a feature, report a bug, or want to contribute, please use the standard GitHub mechanisms provided for the project. Please make sure to add enough information to your request, especially the musical or technical challenge your trying to solve.
