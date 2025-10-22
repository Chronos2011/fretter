# Option reference

## Overview

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

## Print help (-h, --help)

Prints the list of options given above.

## List named option values (-l, --list)

The options `-a`, `-c`, `-o`, `-r`, and `-s` (resp. `--label`, `--chord`, `--operation`, `--root`, and `--scale`) require a named option value. These named options can be listed:

```
$ ./fretter.sh -l LABEL

Fretter v0.1.2 - Fretboard Diagram Generator

FretLabeling: MIDI_PITCH, NOTE_NAME, INTERVAL_SYMBOL, INTERVAL_STEPS, SCALE_MEMBERSHIP
```

*Named option values are case insensitve.*

## Mode of operation (-o, --operation)

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

## Fret count (-f, --fret-count)

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

## Tuning (-t, --tuning)

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

## Fret labeling (-a, --label)

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

## Root (-r, --root)

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

## Scale (-s, --scale)

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

## Chord (-c, --chord)

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

## Position (-p, --position)

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

## Page selection (-g, --page)

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

## Page size (-i, --item-count)

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

## Maximum chord width (-w, --width)

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

## Maximum chord deviation (-d, --deviation)

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

## Additional chord options and chord sanity checks (--allow-open-strings, --allow-unused-strings, --allow-duplicate-pitches, --allow-frets-left-of-bar, --allow-frets-top-left-of-bar, --allow-more-than-four-frets)

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

## Scale working window (-m, --working-window)

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

## Scale distribution pattern (-n, --notes-per-string)

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

## Render window (-e, --render-window)

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

## Verbosity (-v, --verbosity)

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
