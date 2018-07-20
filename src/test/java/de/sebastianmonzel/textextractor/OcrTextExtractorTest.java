package de.sebastianmonzel.textextractor;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

@Ignore
public class OcrTextExtractorTest {
    @Test
    public void extractText() throws Exception {
        OcrTextExtractor ocrTextExtractor = new OcrTextExtractor(new File("src/test/resources/textinimagetoscanviaocr.png"));
        ocrTextExtractor.extractText();
    }

}