package com.baselet.diagram.io;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

public class DiagramFileHandlerTest {

	@InjectMocks
	private DiagramFileHandler diagramFileHandlerMock;

	private static String EXTENSION_PDF = "pdf";
	private static String EXTENSION_BMP = "bmp";
	private static String EXTENSION_EPS = "eps";
	private static String EXTENSION_GIF = "gif";
	private static String EXTENSION_PNG = "png";
	private static String EXTENSION_SVG = "svg";

	@Before
	public void setUp() {
		diagramFileHandlerMock = mock(DiagramFileHandler.class);
	}

	/**
	 * Save diagram and expect IOException
	 *
	 * @throws IOException
	 */
	@Test(expected = IOException.class)
	public void testSave_returnIOException() throws IOException {

		when(diagramFileHandlerMock.doSaveAs(EXTENSION_PDF))
				.thenThrow(IOException.class);

		diagramFileHandlerMock.doSaveAs(EXTENSION_PDF);
	}

	/**
	 * Save diagram as pdf
	 *
	 * @throws IOException
	 */
	@Test
	public void testSaveAsPdf_returnSuccessWhenExtensionEqualsPdf() throws IOException {

		when(diagramFileHandlerMock.doSaveAs(EXTENSION_PDF)).thenReturn("pdf");
		assertEquals("pdf", diagramFileHandlerMock.doSaveAs(EXTENSION_PDF));
	}

	/**
	 * Save diagram as bmp
	 *
	 * @throws IOException
	 */
	@Test
	public void testSaveAsBmp_returnSuccessWhenExtensionEqualsPmp() throws IOException {

        when(diagramFileHandlerMock.doSaveAs(EXTENSION_BMP)).thenReturn("bmp");
        assertEquals("bmp", diagramFileHandlerMock.doSaveAs(EXTENSION_BMP));
	}

	/**
	 * Save diagram as eps
	 *
	 * @throws IOException
	 */
	@Test
	public void testSaveAsEps_returnSuccessWhenExtensionEqualsEps() throws IOException {

        when(diagramFileHandlerMock.doSaveAs(EXTENSION_EPS)).thenReturn("eps");
        assertEquals("eps", diagramFileHandlerMock.doSaveAs(EXTENSION_EPS));
	}

	/**
	 * Save diagram as gif
	 *
	 * @throws IOException
	 */
	@Test
	public void testSaveAsGif_returnSuccessWhenExtensionEqualsGif() throws IOException {

        when(diagramFileHandlerMock.doSaveAs(EXTENSION_GIF)).thenReturn("gif");
        assertEquals("gif", diagramFileHandlerMock.doSaveAs(EXTENSION_GIF));

	}

	/**
	 * Save diagram as png
	 *
	 * @throws IOException
	 */
	@Test
	public void testSaveAsPng_returnSuccessWhenExtensionEqualsPng() throws IOException {

        when(diagramFileHandlerMock.doSaveAs(EXTENSION_PNG)).thenReturn("png");
        assertEquals("png", diagramFileHandlerMock.doSaveAs(EXTENSION_PNG));
	}

	/**
	 * Save diagram as svg
	 *
	 * @throws IOException
	 */
	@Test
	public void testSaveAsSvg_returnSuccessWhenExtensionEqualsSvg() throws IOException {

        when(diagramFileHandlerMock.doSaveAs(EXTENSION_SVG)).thenReturn("svg");
        assertEquals("svg", diagramFileHandlerMock.doSaveAs(EXTENSION_SVG));
	}
}