package de.sebastianmonzel.textextractor;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class PdfTextExtractorTest extends AbstractTextExtractorTest {

    @Before
    public void setUp() {
    }

    @Test
    public void extractText() {


        String extractedText = PdfTextExtractor
                .of(getInputStreamFromResource("sometextwithstopwords.pdf"))
                .extractText()
                .getResultedText();

        assertThat(extractedText,containsString("dies    ist   ein   test,"));
    }

    @Test
    public void limitText() {

        String extractedText = PdfTextExtractor
                .of(getInputStreamFromResource("five_lines.pdf"))
                .extractText()
                .limitToFirstRows(2)
                .getResultedText();
        String[] split = StringUtils.split(extractedText, System.getProperty("line.separator"));
        assertThat(split.length,is(2));
    }

}