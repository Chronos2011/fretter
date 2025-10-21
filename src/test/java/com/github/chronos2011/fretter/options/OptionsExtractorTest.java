package com.github.chronos2011.fretter.options;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;

import com.github.chronos2011.fretter.domain.Position;
import com.github.chronos2011.fretter.domain.library.Chord;
import com.github.chronos2011.fretter.domain.library.Pitch;
import com.github.chronos2011.fretter.domain.library.Scale;
import com.github.chronos2011.fretter.domain.library.Tuning;
import com.github.chronos2011.fretter.options.ApplicationOptions.Operation;

public class OptionsExtractorTest {
	OptionsExtractor optionsExtractor;
	ApplicationOptions applicationOptions;

	@Before
	public void setup() {
		this.optionsExtractor = new OptionsExtractor();
	}

	@Test
	public void testExtract_NoArguments() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] {});
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_ParseException() throws ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenThrow(new ParseException(""));
		mock.extract(new String[] {});
		verify(mock, times(1)).printHelpAndExit(null);
	}

	@Test
	public void testExtract_Help() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		doCallRealMethod().when(mock).checkForHelp();
		mock.extract(new String[] { "--help" });
		verify(mock, times(1)).printHelpAndExit(null);
	}

	@Test
	public void testExtract_List_Scales() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "--list", "SCALE" });
		verify(mock, times(1)).printListAndExit(Scale.class);
	}

	@Test
	public void testExtract_List_NoItem() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "--list" });
		verify(mock, times(1)).printHelpAndExit(null);
	}

	@Test
	public void testExtract_NoOperation() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-o", "-t", "EADGBE" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_Board() throws IOException {
		applicationOptions = optionsExtractor.extract(new String[] { "-o", "board", "-t", "DADGAD" });
		assertEquals(Operation.BOARD, applicationOptions.operation);
		assertEquals(Tuning.fromName("DADGAD"), applicationOptions.boardOptions.tuning);
	}

	@Test
	public void testExtract_Board_TuningInvalid() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-o", "board", "-t", "GUITAR" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_Board_FretCount() throws IOException {
		applicationOptions = optionsExtractor.extract(new String[] { "-o", "board", "-t", "DADGAD", "-f", "30" });
		assertEquals(30, applicationOptions.boardOptions.fretCount);
	}

	@Test
	public void testExtract_Board_FretCountInvalid() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-o", "board", "-t", "DADGAD", "-f", "0" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_Pagination() throws IOException {
		applicationOptions = optionsExtractor
				.extract(new String[] { "-o", "board", "-t", "DADGAD", "-g", "3", "-i", "5" });
		assertEquals(3, applicationOptions.renderOptions.page);
		assertEquals(5, applicationOptions.renderOptions.itemCount);
	}

	@Test
	public void testExtract_Pagination_PageInvalid() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-o", "board", "-t", "DADGAD", "-g", "-1", "-i", "5" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_Pagination_ItemCountInvalid() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-o", "board", "-t", "DADGAD", "-g", "3", "-i", "0" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_RenderWindow() throws IOException {
		applicationOptions = optionsExtractor.extract(new String[] { "-o", "board", "-t", "DADGAD", "-e1,4,open" });
		assertEquals(1, applicationOptions.renderOptions.renderWindow.start);
		assertEquals(4, applicationOptions.renderOptions.renderWindow.end);
		assertEquals(true, applicationOptions.renderOptions.renderWindow.includeOpen);
	}

	@Test
	public void testExtract_RenderWindow_FormatInvalid_NegativeStart() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-o", "board", "-t", "DADGAD", "-e-1,2" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_RenderWindow_FormatInvalid_NegativeEnd() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-o", "board", "-t", "DADGAD", "-e1,-2" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_RenderWindow_FormatInvalid_NotAnInteger() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-o", "board", "-t", "DADGAD", "-e1,2.5" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_RenderWindow_FormatInvalid_EndBeforeStart() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-o", "board", "-t", "DADGAD", "-e2,1" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_RenderWindow_FormatInvalid_TooManyParts() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-o", "board", "-t", "DADGAD", "-e1,2,3,4" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_RenderWindow_FormatInvalid_OpenPartInvalid() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-o", "board", "-t", "DADGAD", "-e1,2,closed" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_Scale() throws IOException {
		applicationOptions = optionsExtractor
				.extract(new String[] { "-o", "scale", "-t", "DADGAD", "-s", "DORIAN", "-r", "E_4" });
		assertEquals(Operation.SCALE, applicationOptions.operation);
		assertEquals(Tuning.fromName("DADGAD"), applicationOptions.boardOptions.tuning);
		assertEquals(Scale.fromName("dorian"), applicationOptions.scaleOptions.scale);
		assertEquals(Pitch.E_4, applicationOptions.scaleOptions.pitch);
	}

	@Test
	public void testExtract_Scale_DistributionPattern() throws IOException {
		applicationOptions = optionsExtractor
				.extract(new String[] { "-oSCALE", "-tDADGAD", "-sDORIAN", "-rE_4", "-p0,12", "-n3" });
		assertEquals(new Position(0, 12), applicationOptions.scaleOptions.pattern.position);
		assertEquals(3, applicationOptions.scaleOptions.pattern.notesPerString);
		applicationOptions = optionsExtractor
				.extract(new String[] { "-oSCALE", "-tDADGAD", "-sDORIAN", "-rE_4", "-p12", "-n3" });
		assertEquals(new Position(12), applicationOptions.scaleOptions.pattern.position);
		assertEquals(3, applicationOptions.scaleOptions.pattern.notesPerString);
	}

	@Test
	public void testExtract_Scale_DistributionPattern_PositionFormatInvalid() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-oSCALE", "-tDADGAD", "-sDORIAN", "-rE_4", "-p1,2,3", "-n3" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_Scale_DistributionPattern_PositionInvalid_StringIndexInvalid()
			throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-oSCALE", "-tDADGAD", "-sDORIAN", "-rE_4", "-p7,0", "-n3" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_Scale_DistributionPattern_PositionInvalid_FretIndexInvalid()
			throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-oSCALE", "-tDADGAD", "-sDORIAN", "-rE_4", "-p0,30", "-n3" });
		verify(mock, times(1)).printHelpAndExit(any());
		clearInvocations(mock);
		mock.extract(new String[] { "-oSCALE", "-tDADGAD", "-sDORIAN", "-rE_4", "-p30", "-n3" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_Scale_DistributionPattern_PositionInvalid_NotAnInteger()
			throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-oSCALE", "-tDADGAD", "-sDORIAN", "-rE_4", "-p0,6o", "-n3" });
		verify(mock, times(1)).printHelpAndExit(any());
		clearInvocations(mock);
		mock.extract(new String[] { "-oSCALE", "-tDADGAD", "-sDORIAN", "-rE_4", "-p6o", "-n3" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_Scale_DistributionPattern_NotesPerStringInvalid() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-oSCALE", "-tDADGAD", "-sDORIAN", "-rE_4", "-p0,12", "-n6" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_Scale_WorkingWindow() throws IOException {
		applicationOptions = optionsExtractor
				.extract(new String[] { "-oSCALE", "-tDADGAD", "-sDORIAN", "-rE_4", "-m8,12" });
		assertEquals(8, applicationOptions.scaleOptions.window.start);
		assertEquals(12, applicationOptions.scaleOptions.window.end);
		assertEquals(false, applicationOptions.scaleOptions.window.includeOpen);
	}

	@Test
	public void testExtract_Scale_SubmodeInvalid() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-oSCALE", "-tDADGAD", "-sDORIAN", "-rE_4", "-p0,12", "-n3", "-m8,12" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_Arpeggio() throws IOException {
		applicationOptions = optionsExtractor
				.extract(new String[] { "-o", "arpeggio", "-t", "DADGAD", "-c", "MAJ7", "-r", "E_4" });
		assertEquals(Operation.ARPEGGIO, applicationOptions.operation);
		assertEquals(Tuning.fromName("DADGAD"), applicationOptions.boardOptions.tuning);
		assertEquals(Chord.fromName("maj7"), applicationOptions.arpeggioOptions.chord);
		assertEquals(Pitch.E_4, applicationOptions.arpeggioOptions.pitch);
	}

	@Test
	public void testExtract_Chord() throws IOException {
		applicationOptions = optionsExtractor
				.extract(new String[] { "-oCHORD", "-tDADGAD", "-cMAJ7", "-rE_4", "-p1,5", "-w4", "-d0" });
		assertEquals(Operation.CHORD, applicationOptions.operation);
		assertEquals(Tuning.fromName("DADGAD"), applicationOptions.boardOptions.tuning);
		assertEquals(Chord.fromName("maj7"), applicationOptions.chordOptions.chord);
		assertEquals(Pitch.E_4, applicationOptions.chordOptions.pitch);
		assertEquals(1, applicationOptions.chordOptions.position.stringIndex);
		assertEquals(5, applicationOptions.chordOptions.position.fretIndex);
		assertEquals(4, applicationOptions.chordOptions.maxWidth);
		assertEquals(0, applicationOptions.chordOptions.maxDeviation);
	}

	@Test
	public void testExtract_Chord_WidthInvalid() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-oCHORD", "-tDADGAD", "-cMAJ7", "-rE_4", "-p1,5", "-w0" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_Chord_DeviationInvalid() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-oCHORD", "-tDADGAD", "-cMAJ7", "-rE_4", "-p1,5", "-d-1" });
		verify(mock, times(1)).printHelpAndExit(any());
	}

	@Test
	public void testExtract_Chord_NoPosition() throws IOException, ParseException {
		OptionsExtractor mock = mock(OptionsExtractor.class);
		when(mock.extract(any())).thenCallRealMethod();
		when(mock.parseArguments(any())).thenCallRealMethod();
		mock.extract(new String[] { "-o", "chord", "-t", "DADGAD", "-c", "MAJ7", "-r", "E_4" });
		verify(mock, times(1)).printHelpAndExit(any());
	}
}
