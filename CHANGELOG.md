# Change log

This file contains a change log for the released versions of *fretter*.

## Version 0.1.3

 * Added title generation for diagrams
 * Added multi-word arguments for named options
 * Added alternative names for domain objects
 * Replaced Pitch with PitchClass in options to get rid of irrelevant octave specifications
 * Added rendering of flat note names whenever the user asks for a flat root pitch class
 * Keep original user name for Tuning, Scale, or Chord in diagrams
 * Split README for readability

## Version 0.1.2 - initial public release

* Fully automatic / algorithmic: no manual entry of notes required
* Command line: easily accessible via command-line and batchable
* Operation modes:
    * Board
    * Scale
    * Arpeggio
    * Chord
* Selection of instrument tuning
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
    * Selection of scale
    * Selection of pitch
    * Configurable position in "Patterned" sub-mode
    * Sub-modes:
        * Fretboard: shows all occurences of in-scale frets
        * Windowed: shows all occurences of in-scale frets in a fixed window
        * Patterned: finds and shows common scale patterns (i.e. 2 or 3 notes per string)
* Arpeggio mode:
    * Selection of chord
    * Selection of pitch
* Chord mode:
    * Selection of chord
    * Selection of pitch
    * Configurable position
    * Configurable maximum position deviation
    * Configurable maximum chord width
    * Configurable use of open strings
    * Configurable use of unused (inner) strings
    * Generation of all potential chord combinations
    * Configurable sanity checking of fingerings
    * Automatic bar detection
    * Ordering of fingerings by usefulness
