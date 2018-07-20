package de.sebastianmonzel.textextractor;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TxtTextExtractorTest extends AbstractTextExtractorTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void extractText() throws Exception {

        InputStream resourceAsStream = getInputStreamFromResource("test1234.txt");

        String extractedText = TxtTextExtractor.of(resourceAsStream)
                .extractText()
                .getResultedText();

        assertThat(extractedText,is("test1234"));
    }

    @Test
    public void extractTextWithoutStopwords() throws Exception {

        InputStream resourceAsStream = getInputStreamFromResource("sometextwithstopwords.txt");

        String extractedText = TxtTextExtractor.of(resourceAsStream)
                .extractText()
                .removeStopwords(Locale.GERMAN)
                .removePunctuationMarks()
                .getResultedText();

        assertThat(extractedText,is("test"));
    }

    @Test
    public void extractTextByRegex() throws Exception {

        InputStream resourceAsStream = getInputStreamFromResource("sometextwithstopwords.txt");

        String extractedText = TxtTextExtractor.of(resourceAsStream)
                .extractText()
                .removePunctuationMarks()
                .filterByRegularExpression(".*(ist).*")
                .getResultedText();

        assertThat(extractedText,is("ist"));
    }

    @Test
    public void removeXml() throws Exception {

        InputStream resourceAsStream = getInputStreamFromResource("sometextwithxmltags.txt");

        String extractedText = TxtTextExtractor.of(resourceAsStream)
                .extractText()
                .removeXmlTags()
                .getResultedText();

        assertThat(extractedText,is("dies ist ein test"));
    }



}