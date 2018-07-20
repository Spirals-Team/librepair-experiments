package de.sebastianmonzel.textextractor;

import com.asprise.ocr.Ocr;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.*;

public class OcrTextExtractor extends AbstractTextExtractor {

    public static OcrTextExtractor of(File file) {
        return new OcrTextExtractor(file);
    }

    public static OcrTextExtractor of(InputStream file) {
        return new OcrTextExtractor(file);
    }

    public static OcrTextExtractor of(String text) {
        return new OcrTextExtractor(text);
    }

    public OcrTextExtractor(File file) {
        super(file);
    }

    public OcrTextExtractor(InputStream inputStream) {
        super(inputStream);
    }

    public OcrTextExtractor(String inputStream) {
        super(inputStream);
    }


    @Override
    public AbstractTextExtractor extractText() {

        // TODO alternativen evaluieren

        //-->http://tess4j.sourceforge.net/ - http://tess4j.sourceforge.net/codesample.html
        //http://www.ocrserver.at/
        //https://cloud.google.com/vision/docs/
        //http://stackoverflow.com/questions/1813881/java-ocr-implementation



        /*Ocr.setUp();
        for (String s:
        Ocr.listSupportedLanguages()) {
            System.out.println(s);
        };*/

        //Ocr ocr = new Ocr();
        //ocr.startEngine("deu",Ocr.SPEED_FAST);
        File textFile = new File("src/test/resources/textinimagetoscanviaocr.png");

        File[] textfiles = {textFile};
        //String recognizedtext = ocr.recognize(textfiles, Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
        //System.out.println(recognizedtext);

        File barcodeFile = new File("src/test/resources/getCode.png");
        File[] barcodefiles = {barcodeFile};
        //String recognizedbarcode = ocr.recognize(barcodefiles, Ocr.RECOGNIZE_TYPE_BARCODE, Ocr.OUTPUT_FORMAT_PLAINTEXT);
        //System.out.println(recognizedbarcode);


        //ocr.stopEngine();


        ITesseract instance = new Tesseract();
        try {
            String result = instance.doOCR(textFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

}
