package com.github.chronos2011.fretter.options;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.cli.*;

import com.github.chronos2011.fretter.application.Configuration;
import com.github.chronos2011.fretter.domain.DomainException;
import com.github.chronos2011.fretter.domain.FretWindow;
import com.github.chronos2011.fretter.domain.Position;
import com.github.chronos2011.fretter.domain.library.*;
import com.github.chronos2011.fretter.options.ApplicationOptions.Operation;
import com.github.chronos2011.fretter.options.RenderOptions.FretLabeling;

/**
 * Class OptionsExtractor transforms command-line arguments into {@link ApplicationOptions}.
 */
public class OptionsExtractor {
    private static final Options APPLICATION_OPTIONS;

    static {
        Options options = new Options();
        // Help
        options.addOption(new Option("h", "help", false, "prints usage details"));
        // List
        options.addOption(Option.builder("l").longOpt("list").hasArg().argName("item").type(String.class)
                .desc("lists items to be used with other parameters"
                        + ", either 'TUNING', 'SCALE', 'CHORD', 'PITCH', or 'LABEL'")
                .get());
        // ApplicationOptions
        options.addOption(Option.builder("o").longOpt("operation").hasArg().argName("operation").type(String.class)
                .desc("the operation to be executed, either 'BOARD', 'SCALE', 'ARPEGGIO', or 'CHORD'").get());
        options.addOption(Option.builder("v").longOpt("verbosity").hasArg().argName("level").type(Integer.class)
                .desc("the level of verbosity to be used").get());
        // RenderOptions
        options.addOption(Option.builder("e").longOpt("render-window").hasArg().argName("window").type(String.class)
                .desc("the fret window to be rendered from start x to end y (format: 'x,y')"
                        + ", optionally followed by ',open' to render open strings")
                .get());
        options.addOption(Option.builder("a").longOpt("label").hasArg().argName("label").type(String.class)
                .desc("the fret labeling to be used, INTERVAL_NAME (NOTE_NAME for operation 'board') if not set")
                .get());
        options.addOption(Option.builder("g").longOpt("page").hasArg().argName("number").type(Integer.class)
                .desc("the page number to be shown for operation 'chord', 0 if not set").get());
        options.addOption(Option.builder("i").longOpt("item-count").hasArg().argName("count").type(Integer.class)
                .desc("the number of items per page to be shown for operation 'chord', 4 if not set").get());
        // BoardOptions
        options.addOption(Option.builder("f").longOpt("fret-count").hasArg().argName("count").type(Integer.class)
                .desc("the number of frets of the board, 24 if not set").get());
        options.addOption(Option.builder("t").longOpt("tuning").hasArg().argName("tuning").type(String.class)
                .desc("the tuning to be used, EADGBE if not set").get());
        // ScaleOptions
        options.addOption(Option.builder("s").longOpt("scale").hasArg().argName("scale").type(String.class)
                .desc("the scale to be used, mandatory for operation 'scale'").get());
        options.addOption(Option.builder("m").longOpt("working-window").hasArg().argName("window").type(String.class)
                .desc("the fret window to be used from start x to end y (format: 'x,y')"
                        + ", optional for operation 'scale'")
                .get());
        options.addOption(Option.builder("n").longOpt("notes-per-string").hasArg().argName("count").type(Integer.class)
                .desc("the number of notes per string to be used, optional for operation 'scale'").get());
        // ChordOptions
        options.addOption(Option.builder("c").longOpt("chord").hasArg().argName("chord").type(String.class)
                .desc("the chord to be used, mandatory for operations 'arpeggio' and 'chord'").get());
        options.addOption(Option.builder("w").longOpt("width").hasArg().argName("width").type(Integer.class)
                .desc("the maximum width of a chord (excluding potential open strings), 5 if not set").get());
        options.addOption(Option.builder("d").longOpt("deviation").hasArg().argName("deviation").type(Integer.class)
                .desc("the maximum deviation (from requested position) of a chord, 2 if not set").get());
        options.addOption(Option.builder().longOpt("allow-open-strings")
                .desc("allow open strings during in operation 'chord'").get());
        options.addOption(Option.builder().longOpt("allow-unused-strings")
                .desc("allow unused (inner) strings in operation 'chord'").get());
        options.addOption(Option.builder().longOpt("allow-more-than-four-frets")
                .desc("allow more than 4 different frets in operation 'chord'").get());
        options.addOption(Option.builder().longOpt("allow-duplicate-pitches")
                .desc("allow duplicate pitches in operation 'chord'").get());
        options.addOption(Option.builder().longOpt("allow-frets-left-of-bar")
                .desc("allow frets left of a bar in operation 'chord'").get());
        options.addOption(Option.builder().longOpt("allow-frets-top-left-of-bar")
                .desc("allow frets top left of a bar in operation 'chord'").get());
        // Common options
        options.addOption(Option.builder("r").longOpt("root").hasArg().argName("pitch").type(String.class)
                .desc("the root pitch to be used, mandatory for operations 'scale', 'arpeggio', and 'chord'").get());
        options.addOption(Option.builder("p").longOpt("position").hasArg().argName("position").type(String.class)
                .desc("either the fret x position to be used (format: 'x') "
                        + "or string position x and fret position y to be used (format: 'x,y' - no whitespace)"
                        + ", mandatory for operations 'chord' and 'scale' with distribution pattern")
                .get());
        APPLICATION_OPTIONS = options;
    }

    private CommandLine commandLine;
    private ApplicationOptions applicationOptions;

    /**
     * Transforms command-line arguments into {@link ApplicationOptions}. If the arguments require user output (e.g.
     * usage details), the method will print and exit.
     *
     * @param arguments the command-line arguments given to the application
     * @return the {@link ApplicationOptions} extracted
     */
    public ApplicationOptions extract(String[] arguments) {
        commandLine = null;
        applicationOptions = null;
        try {
            commandLine = parseArguments(arguments);
            checkForHelp();
            checkForList();
            applicationOptions = parseApplicationOptions();
            applicationOptions.renderOptions = parseRenderOptions(applicationOptions.operation);
            applicationOptions.boardOptions = parseBoardOptions();
            applicationOptions.scaleOptions = parseScaleOptions();
            applicationOptions.arpeggioOptions = parseArpeggioOptions();
            applicationOptions.chordOptions = parseChordOptions();
            return applicationOptions;
        } catch (DomainException exception) {
            printHelpAndExit(exception.getMessage());
            return null;
        } catch (ParseException exception) {
            printHelpAndExit(null);
            return null;
        }
    }

    protected CommandLine parseArguments(String[] arguments) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        return parser.parse(APPLICATION_OPTIONS, arguments);
    }

    protected void checkForHelp() {
        if (commandLine.hasOption("help"))
            printHelpAndExit(null);
    }

    private void checkForList() throws DomainException, ParseException {
        if (commandLine.hasOption("list")) {
            String requestedItem = commandLine.getParsedOptionValue("list");
            if (requestedItem == null)
                throw new DomainException("No item to be listed");
            switch (requestedItem.toLowerCase()) {
                case "tuning":
                    printListAndExit(Tuning.class);
                case "scale":
                    printListAndExit(Scale.class);
                case "chord":
                    printListAndExit(Chord.class);
                case "pitch":
                    printListAndExit(Pitch.class);
                case "label":
                    printListAndExit(FretLabeling.class);
                default:
                    throw new DomainException("Invalid item to be listed");
            }
        }
    }

    private ApplicationOptions parseApplicationOptions() throws DomainException, ParseException {
        applicationOptions = new ApplicationOptions();
        applicationOptions.operation = (Operation) getParsedEnum("operation", Operation.class);
        if (applicationOptions.operation == null)
            printHelpAndExit(null);
        applicationOptions.verbosity = commandLine.getParsedOptionValue("verbosity", 0);
        return applicationOptions;
    }

    private RenderOptions parseRenderOptions(Operation operation) throws DomainException, ParseException {
        RenderOptions renderOptions = new RenderOptions();
        renderOptions = new RenderOptions();
        FretLabeling defaultLabeling;
        switch (operation) {
            case BOARD:
                defaultLabeling = FretLabeling.NOTE_NAME;
                break;
            default:
                defaultLabeling = FretLabeling.INTERVAL_SYMBOL;
                break;
        }
        renderOptions.fretLabeling = (FretLabeling) getParsedEnum("label", FretLabeling.class, defaultLabeling);
        renderOptions.renderWindow = (FretWindow) getFretWindow("render-window");
        renderOptions.page = commandLine.getParsedOptionValue("page", 0);
        if (renderOptions.page < 0)
            throw new DomainException("Page number cannot be negative");
        renderOptions.itemCount = commandLine.getParsedOptionValue("item-count", 4);
        if (renderOptions.itemCount < 1)
            throw new DomainException("Item count must be >= 1");
        return renderOptions;
    }

    private BoardOptions parseBoardOptions() throws DomainException, ParseException {
        BoardOptions boardOptions = new BoardOptions();
        boardOptions.fretCount = commandLine.getParsedOptionValue("fret-count", 24);
        if (boardOptions.fretCount < 1)
            throw new DomainException("Fret count must be >= 1");
        boardOptions.tuning = (Tuning) getParsedEnum("tuning", Tuning.class, Tuning.fromName("standard guitar"));
        return boardOptions;
    }

    private ScaleOptions parseScaleOptions() throws DomainException, ParseException {
        if (applicationOptions.operation != Operation.SCALE)
            return null;
        ScaleOptions scaleOptions = new ScaleOptions();
        scaleOptions.scale = (Scale) getParsedEnum("scale", Scale.class);
        scaleOptions.pitch = (Pitch) getParsedEnum("root", Pitch.class);
        boolean hasPosition = commandLine.hasOption("position");
        boolean hasNotesPerString = commandLine.hasOption("notes-per-string");
        boolean hasWorkingWindow = commandLine.hasOption("working-window");
        if (hasPosition && hasNotesPerString && !hasWorkingWindow) {
            scaleOptions.pattern = new ScaleOptions.DistributionPattern();
            scaleOptions.pattern.position = getPosition("position");
            scaleOptions.pattern.notesPerString = commandLine.getParsedOptionValue("notes-per-string", 3);
            if ((scaleOptions.pattern.notesPerString < 1) || (scaleOptions.pattern.notesPerString > 5))
                throw new DomainException("Notes per string must be >= 1 and <= 5");
        } else if (!hasPosition && !hasNotesPerString && hasWorkingWindow) {
            scaleOptions.window = (FretWindow) getFretWindow("working-window");
        } else if (hasPosition || hasNotesPerString || hasWorkingWindow) {
            throw new DomainException("Provide either 'position' and 'notes-per-string' or 'working-window' or none");
        }
        return scaleOptions;
    }

    private ArpeggioOptions parseArpeggioOptions() throws DomainException, ParseException {
        if (applicationOptions.operation != Operation.ARPEGGIO)
            return null;
        ArpeggioOptions arpeggioOptions = new ArpeggioOptions();
        arpeggioOptions.chord = (Chord) getParsedEnum("chord", Chord.class);
        arpeggioOptions.pitch = (Pitch) getParsedEnum("root", Pitch.class);
        return arpeggioOptions;
    }

    private ChordOptions parseChordOptions() throws DomainException, ParseException {
        if (applicationOptions.operation != Operation.CHORD)
            return null;
        ChordOptions chordOptions = new ChordOptions();
        chordOptions.chord = (Chord) getParsedEnum("chord", Chord.class);
        chordOptions.pitch = (Pitch) getParsedEnum("root", Pitch.class);
        chordOptions.position = getPosition("position");
        chordOptions.maxWidth = commandLine.getParsedOptionValue("width", 5);
        if (chordOptions.maxWidth < 1)
            throw new DomainException("Maximum width must be >= 1");
        chordOptions.maxDeviation = commandLine.getParsedOptionValue("deviation", 2);
        if (chordOptions.maxDeviation < 0)
            throw new DomainException("Maximum deviation cannot be negative");
        chordOptions.allowOpenStrings = commandLine.hasOption("allow-open-strings");
        chordOptions.allowUnusedStrings = commandLine.hasOption("allow-unused-strings");
        chordOptions.sanityCheck.atMostFourDifferentFrets = !commandLine.hasOption("allow-more-than-four-frets");
        chordOptions.sanityCheck.noDuplicatePitches = !commandLine.hasOption("allow-duplicate-pitches");
        chordOptions.sanityCheck.noFretsLeftOfBar = !commandLine.hasOption("allow-frets-left-of-bar");
        chordOptions.sanityCheck.noFretsTopLeftOfBar = !commandLine.hasOption("allow-frets-top-left-of-bar");
        return chordOptions;
    }

    private FretWindow getFretWindow(String optionName) throws DomainException {
        String value = commandLine.getOptionValue(optionName);
        if (value == null)
            return null;
        FretWindow window = new FretWindow();
        String parts[] = value.split(",");
        try {
            if (parts.length != 2 && parts.length != 3)
                throw new DomainException("Invalid window format");
            if (parts.length == 3 && !parts[2].equalsIgnoreCase("open"))
                throw new DomainException("Invalid window format");
            window.start = Integer.parseInt(parts[0]);
            window.end = Integer.parseInt(parts[1]);
            if (window.start < 0 || window.end < 0)
                throw new DomainException("Window start and end cannot be negative");
            if (window.start > window.end)
                throw new DomainException("Window start cannot be smaller than window end");
            if (parts.length == 2)
                window.includeOpen = false;
            else
                window.includeOpen = true;
            return window;
        } catch (NumberFormatException exception) {
            throw new DomainException("Invalid window format");
        }
    }

    private Position getPosition(String optionName) throws DomainException {
        String value = commandLine.getOptionValue(optionName);
        if (value == null)
            throw new DomainException("No position given");
        if (value.contains(",")) {
            // String-fret-format
            String parts[] = value.split(",");
            if (parts.length != 2)
                throw new DomainException("Invalid position format");
            try {
                int stringIndex = Integer.parseInt(parts[0]);
                int fretIndex = Integer.parseInt(parts[1]);
                if (stringIndex < 0 || stringIndex >= applicationOptions.boardOptions.tuning.pitches.size())
                    throw new DomainException("String index out of bounds");
                if (fretIndex < 0 || fretIndex > applicationOptions.boardOptions.fretCount)
                    throw new DomainException("Fret index out of bounds");
                return new Position(stringIndex, fretIndex);
            } catch (NumberFormatException exception) {
                throw new DomainException("Invalid number format in position");
            }
        } else {
            // Fret-only-format
            try {
                int fretIndex = Integer.parseInt(value);
                if (fretIndex < 0 || fretIndex > applicationOptions.boardOptions.fretCount)
                    throw new DomainException("Fret index out of bounds");
                return new Position(fretIndex);
            } catch (NumberFormatException exception) {
                throw new DomainException("Invalid number format in position");
            }
        }
    }

    private <T extends Enum<?> & Nameable> T getParsedEnum(String optionName, Class<T> clazz)
            throws DomainException, ParseException {
        return getParsedEnum(optionName, clazz, null);
    }

    private <T extends Enum<?> & Nameable> T getParsedEnum(String optionName, Class<T> clazz, T fallback)
            throws DomainException, ParseException {
        try {
            String requestedEnum = commandLine.getParsedOptionValue(optionName);
            if (requestedEnum == null) {
                if (fallback == null)
                    throw new DomainException("No " + clazz.getSimpleName().toLowerCase() + " specified");
                else
                    return fallback;
            }
            for (T constant : clazz.getEnumConstants()) {
                String name = constant.getName();
                if (name.equalsIgnoreCase(requestedEnum))
                    return constant;
                List<String> alternativeNames = constant.getAlternativeNames();
                for (String alternativeName : alternativeNames)
                    if (alternativeName.equalsIgnoreCase(requestedEnum))
                        return constant;
            }
            throw new DomainException("Invalid " + clazz.getSimpleName().toLowerCase() + " specified");
        } catch (DomainException exception) {
            String options = getNamedOptions(clazz);
            throw new DomainException(exception.getMessage() + " (options available: " + options + ")");
        }
    }

    @SuppressWarnings("deprecation")
    protected void printHelpAndExit(String message) {
        // Unfortunately, the replacement formatter is far inferior, so we use the deprecated one
        HelpFormatter formatter = HelpFormatter.builder().setShowSince(false).get();
        formatter.setWidth(120);
        String format = Configuration.applicationName + " [options]";
        formatter.printHelp(format, null, APPLICATION_OPTIONS, null, false);
        if (message != null) {
            System.out.println("\n *** Error: " + message + " *** \n");
            System.exit(1);
        } else
            System.exit(0);
    }

    protected <T extends Enum<?> & Nameable> void printListAndExit(Class<T> clazz) {
        String namedOptions = getNamedOptions(clazz);
        System.out.println(clazz.getSimpleName() + ": " + namedOptions);
        System.out.println("\n * Named options that contain multiple words must be enclosed in \"double quotes\" *");
        System.exit(0);
    }

    protected <T extends Enum<?> & Nameable> String getNamedOptions(Class<T> clazz) {
        List<String> names = new ArrayList<>();
        for (T constant : clazz.getEnumConstants()) {
            names.add(constant.getName());
            names.addAll(constant.getAlternativeNames());
        }
        Collections.sort(names);
        String options = String.join(", ", names);
        return options;
    }
}