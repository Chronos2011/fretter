package com.github.chronos2011.fretter.presentation;

import com.github.chronos2011.fretter.domain.Board;
import com.github.chronos2011.fretter.domain.Fret;
import com.github.chronos2011.fretter.domain.library.Interval;
import com.github.chronos2011.fretter.options.ApplicationOptions;

/**
 * Class ConsoleBoardRenderer renders a {@link Board} to be printed onto the console.
 */
public class ConsoleBoardRenderer implements BoardRenderer {
    public static final String NEWLINE = System.lineSeparator();

    private static class FretRenderHints {
        public boolean open = false;
        public int from = Integer.MAX_VALUE;
        public int to = Integer.MIN_VALUE;
    }

    @Override
    public void render(StringBuilder builder, ApplicationOptions applicationOptions, Board board) {
        final FretRenderer fretRenderer = new ConsoleFretRenderer(applicationOptions);
        final FretRenderHints hints = calculateFretRenderHints(applicationOptions, board);
        // Render header
        builder.append(NEWLINE);
        if (hints.open) {
            builder.append(String.format("%2d ", 0));
            if (hints.from != 1)
                builder.append("      ");
        }
        for (int fretIndex = hints.from; fretIndex <= hints.to; fretIndex++)
            builder.append(String.format("   %2d   ", fretIndex));
        builder.append(NEWLINE);
        builder.append(NEWLINE);
        // Render strings
        for (int stringIndex = board.stringCount - 1; stringIndex >= 0; stringIndex--) {
            if (hints.open) {
                String fretLabel = fretRenderer.render(board.frets[stringIndex][0]);
                builder.append(fretLabel == null ? "   " : fretLabel);
                if (hints.from != 1)
                    builder.append("\u251C \u00B7\u00B7\u00B7 ");
            }
            for (int fretIndex = hints.from; fretIndex <= hints.to; fretIndex++) {
                Fret fret = board.frets[stringIndex][fretIndex];
                String fretLabel = fretRenderer.render(fret);
                builder.append(fretIndex == 1 ? "\u251C\u2500\u2500" : "\u253C\u2500\u2500");
                builder.append(fretLabel == null ? "\u2500\u2500\u2500" : fretLabel);
                builder.append("\u2500\u2500");
            }
            builder.append("\u253C");
            builder.append(NEWLINE);
        }
        // Render footer
        builder.append(NEWLINE);
        if (hints.open) {
            builder.append(String.format("%2d ", 0));
            if (hints.from != 1)
                builder.append("      ");
        }
        for (int fretIndex = hints.from; fretIndex <= hints.to; fretIndex++)
            builder.append(String.format("   %2d   ", fretIndex));
        builder.append(NEWLINE);
        builder.append(NEWLINE);
    }

    private FretRenderHints calculateFretRenderHints(ApplicationOptions applicationOptions, Board board) {
        FretRenderHints hints = new FretRenderHints();
        if (applicationOptions.renderOptions.renderWindow != null) {
            hints.open = applicationOptions.renderOptions.renderWindow.includeOpen;
            hints.from = Math.min(applicationOptions.renderOptions.renderWindow.start, board.fretCount);
            hints.from = Math.max(hints.from, 1);
            hints.to = Math.min(applicationOptions.renderOptions.renderWindow.end, board.fretCount);
        } else {
            for (int stringIndex = 0; stringIndex < board.stringCount; stringIndex++) {
                for (int fretIndex = 0; fretIndex <= board.fretCount; fretIndex++) {
                    if (board.frets[stringIndex][fretIndex].interval != Interval.OUT) {
                        if (fretIndex == 0)
                            hints.open = true;
                        else {
                            if (fretIndex < hints.from)
                                hints.from = fretIndex;
                            if (fretIndex > hints.to)
                                hints.to = fretIndex;
                        }
                    }
                }
            }
        }
        return hints;
    }
}
