package de.sebastianmonzel.textextractor;

import de.sebastianmonzel.textextractor.util.PDFLayoutTextStripper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;

public class PdfTextExtractor extends AbstractTextExtractor {


    public static PdfTextExtractor of(File file) {
        return new PdfTextExtractor(file);
    }

    public static PdfTextExtractor of(InputStream file) {
        return new PdfTextExtractor(file);
    }

    public static PdfTextExtractor of(String text) {
        return new PdfTextExtractor(text);
    }

    public PdfTextExtractor(File file) {
        super(file);
    }

    public PdfTextExtractor(InputStream inputStream) {
       super(inputStream);
    }

    public PdfTextExtractor(String text) {
        super(text);
    }

    @Override
    public AbstractTextExtractor extractText() {

        try {
            PDDocument document = null;
            document = PDDocument.load(inputStream);
            PDFLayoutTextStripper stripper = new PDFLayoutTextStripper();

            String content = stripper.getText(document);
            document.close();

            extractedText = content;
            resetResultedText();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

}