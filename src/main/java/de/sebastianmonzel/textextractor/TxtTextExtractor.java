package de.sebastianmonzel.textextractor;

import java.io.*;

public class TxtTextExtractor extends AbstractTextExtractor {

    public static TxtTextExtractor of(File file) {
        return new TxtTextExtractor(file);
    }

    public static TxtTextExtractor of(InputStream inputStream) {
        return new TxtTextExtractor(inputStream);
    }

    public static TxtTextExtractor of(String text) {
        return new TxtTextExtractor(text);
    }

    public TxtTextExtractor(File file) {
        super(file);
    }

    public TxtTextExtractor(InputStream inputStream) {
        super(inputStream);
    }

    public TxtTextExtractor(String text) {
        super(text);
    }

    public AbstractTextExtractor extractText() {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
                if ( br.ready() ) {
                    stringBuilder.append(System.getProperty(LINE_SEPARATOR));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        extractedText = stringBuilder.toString();
        resetResultedText();

        return this;
    }

}
