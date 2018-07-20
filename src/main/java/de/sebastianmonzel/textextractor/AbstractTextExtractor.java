package de.sebastianmonzel.textextractor;

import java.io.*;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractTextExtractor {

    public static final String LINE_SEPARATOR = "line.separator";
    protected String extractedText;
    protected String resultedText;
    protected InputStream inputStream;

    public AbstractTextExtractor(File file) {
        try {
            this.inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public AbstractTextExtractor(InputStream inputStream) {
        this.inputStream = inputStream;
        this.extractedText = "";
        this.resultedText = "";
    }

    public AbstractTextExtractor(String text) {
        this.extractedText = text;
        this.resultedText = text;
    }

    public abstract AbstractTextExtractor extractText();

    public AbstractTextExtractor removeStopwords(Locale locale) {

        String[] stopwords = readStopwords(locale);

        for (String stopword : stopwords) {
            resultedText = resultedText.replaceAll( " " + stopword.trim() + " "," ");
            resultedText = resultedText.replaceAll( stopword.trim() + " ","");
            resultedText = resultedText.replaceAll( " " + stopword.trim(),"");
        }
        return this;
    }

    private String[] readStopwords(Locale locale) {
        File stopwordFile = resolveStopwordFileByLocale(locale);

        String stopwordText = TxtTextExtractor
                .of(stopwordFile)
                .extractText()
                .getResultedText();

        return stopwordText.split(System.getProperty(LINE_SEPARATOR));
    }



    private File resolveStopwordFileByLocale(Locale locale) {
        ClassLoader classLoader = AbstractTextExtractor.class.getClassLoader();
        File file = new File(classLoader.getResource("stopwords_" + locale.getLanguage() + ".txt").getFile());
        return file;
    }

    public AbstractTextExtractor removePunctuationMarks() {

        String[] punctuationMarks = {"\\,","\\;","\\!","\\.","\\?"};

        for (String punctuationMark : punctuationMarks) {
            resultedText = resultedText.replaceAll(punctuationMark.trim(),"");
        }
        return this;
    }

    public AbstractTextExtractor removeXmlTags() {

        resultedText = resultedText.replaceAll("<[^>]+>", "");
        return this;
    }

    public AbstractTextExtractor filterByRegularExpression(String regularExpression) {

        final Pattern pattern = Pattern.compile(regularExpression);
        final Matcher matcher = pattern.matcher(extractedText);

        if ( matcher.find() ) {
            resultedText = matcher.group(1);
        } else {
            System.err.println("Pattern '" + regularExpression + "' not found. Will ignore it.");
            resultedText = null;
        }

        return this;
    }

    public AbstractTextExtractor normalizeWhitespace() {



        return this;
    }

    public AbstractTextExtractor limitToFirstRows(int limit) {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(resultedText));

        int actualLine = 1;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                stringBuilder.append(line).append(System.getProperty("line.separator"));
                if (actualLine >= limit) {
                    break;
                }
                actualLine++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultedText = stringBuilder.toString();
        return this;
    }


    public String getResultedText() {
        String resultedTextToReturn = resultedText;
        resetResultedText();
        return resultedTextToReturn;
    }

    protected void resetResultedText() {
        resultedText = extractedText;
    }
}
