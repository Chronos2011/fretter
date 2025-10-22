# fretter - a generic, automatic / algorithmic, command-line fretboard diagram generator

*fretter* is a sophisticated command-line tool, generating various diagrams for fretted instruments; it is extremely powerful and can - due to combinatorial explosion - generate millions of different diagrams. The tool is not intended for beginners but advanced musicians.

 * [Features](#features)
   * [Currently implemented](#currently-implemented)
   * [Planned](#planned)
   * [Not implemented, not planned](#not-implemented-not-planned)
 * [Requirements](#requirements)
 * [Usage](#usage)
   * [Warning](#warning)
   * [Installing and running](#installing-and-running)
 * [A simple walkthrough](WALKTHROUGH.md)
 * [Option reference](REFERENCE.md)

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
* Selection of instrument tuning, currently 8 (16 with alternative names) options
* Configurable fretboard size
* Render options:
    * Configurable render window
    * Configurable showing of open strings
    * Fret labeling options:
        * MIDI pitch
        * Note name
        * Interval symbol
        * Interval steps (half tones)
        * Scale membership
    * Alternative naming option
* Scale mode:
    * Selection of scale, currently 45 (54 with alternative names) options
    * Selection of pitch class, currently 21 options
    * Configurable position in "Patterned" sub-mode
    * Sub-modes:
        * Fretboard: shows all occurences of in-scale frets
        * Windowed: shows all occurences of in-scale frets in a fixed window
        * Patterned: finds and shows common scale patterns (e.g. 2 or 3 notes per string)
* Arpeggio mode:
    * Selection of chord, currently 31 options
    * Selection of pitch class, currently 21 options
* Chord mode:
    * Selection of chord, currently 31 options
    * Selection of pitch class, currently 21 options
    * Configurable position
    * Configurable maximum position deviation
    * Configurable maximum chord width
    * Configurable use of open strings
    * Configurable use of unused (inner) strings
    * Generation of all potential chord combinations
    * Configurable sanity checking of fingerings
    * Automatic bar detection
    * Ordering of fingerings by usefulness

### Planned

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

### Not implemented, not planned

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

Alternatively, simply clone from GitHub and run wither `fretter.sh` or `fretter.bat` from the root folder.

## A simple walkthrough

For a hands-on introduction to *fretter*, check out our [walkthrough](WALKTHROUGH.md). It presents the main features of *fretter*; walking through this big example should give you a descent understanding of the tool.

## Option reference

You can get detailed information in the [Option reference](REFERENCE.md), which should answer all your questions.

## Change log

The change log can be found here: [Change log](CHANGELOG.md)
