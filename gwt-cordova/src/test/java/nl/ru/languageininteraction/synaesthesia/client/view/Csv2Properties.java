/*
 * Copyright (C) 2015 Language In Interaction
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.ru.languageininteraction.synaesthesia.client.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Properties;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * @since Jan 29, 2015 4:23:38 PM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public class Csv2Properties {

    private static final String FILE_SUFFIX = ".csv";
    private static final String PROPERTIES_SUFFIX = ".properties";
    private static final String COLUMN_SEPARATOR = ",";
    private static final String PROPERTY_SEPARATOR = "=";
    private static final int DE_COLUMN = 4;
    private static final int RU_COLUMN = 3;
    private static final int NL_COLUMN = 2;
    private static final int EN_COLUMN = 1;
    private static final int KEY_COLUMN = 0;
    private final HashMap<String, String> translationsEN = new HashMap<>();
    private final HashMap<String, String> translationsDE = new HashMap<>();
    private final HashMap<String, String> translationsRU = new HashMap<>();
    private final HashMap<String, String> translationsNL = new HashMap<>();
    final File inputFile = new File("src/main/static/synquiz2/translations" + FILE_SUFFIX);

    public void readTranslations() {

    }

    public void parseInputCSV() throws IOException {
        final Reader reader = new InputStreamReader(inputFile.toURL().openStream(), "UTF-8"); // todo: this might need to change to "ISO-8859-1" depending on the usage
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(reader);
        for (CSVRecord record : records) {
            String key_name = record.get(KEY_COLUMN);
            System.out.println(key_name);
            String en_value = record.get(EN_COLUMN);
            String nl_value = record.get(NL_COLUMN);
            String ru_value = record.get(RU_COLUMN);
//            String de_value = record.get(DE_COLUMN);
            System.out.println(key_name);
            System.out.println(en_value);
            System.out.println(nl_value);
            System.out.println(ru_value);
            //   System.out.println(de_value);
            translationsEN.put(key_name, en_value);
            translationsNL.put(key_name, nl_value);
            translationsRU.put(key_name, ru_value);
            //    translationsDE.put(key_name, de_value);
        }
    }

    public void writePropertyValues(String propertiesFileName) throws FileNotFoundException, IOException {
        System.out.println("propertiesFileName: " + propertiesFileName);
        Properties properties = new Properties();
//        Properties properties_nl = new Properties();
//        Properties properties_ru = new Properties();
//        Properties properties_de = new Properties();
        final InputStream resourceAsStream = SimpleViewTest.class.getResourceAsStream("/nl/ru/languageininteraction/language/client/" + propertiesFileName + ".properties");
        properties.load(resourceAsStream);
//        final InputStream resourceAsStream_nl = SimpleViewTest.class.getResourceAsStream("/nl/ru/languageininteraction/language/client/" + propertiesFileName + "_nl.properties");
//        properties_nl.load(resourceAsStream_nl);
//        final InputStream resourceAsStream_de = SimpleViewTest.class.getResourceAsStream("/nl/ru/languageininteraction/language/client/" + propertiesFileName + "_de.properties");
//        properties_de.load(resourceAsStream_de);
//        final InputStream resourceAsStream_ru = SimpleViewTest.class.getResourceAsStream("/nl/ru/languageininteraction/language/client/" + propertiesFileName + "_ru.properties");
//        properties_ru.load(resourceAsStream_ru);
        final String targetDirectory = "src/main/resources/nl/ru/languageininteraction/language/client/";
        final File outputFileJava = new File(targetDirectory + propertiesFileName + ".txt");
        final File outputFile = new File(targetDirectory + propertiesFileName + PROPERTIES_SUFFIX);
        final File outputFileDE = new File(targetDirectory + propertiesFileName + "_de" + PROPERTIES_SUFFIX);
        final File outputFileRU = new File(targetDirectory + propertiesFileName + "_ru" + PROPERTIES_SUFFIX);
        final File outputFileNL = new File(targetDirectory + propertiesFileName + "_nl" + PROPERTIES_SUFFIX);
        OutputStream outputStreamJava = new FileOutputStream(outputFileJava, false);
        OutputStream outputStream = new FileOutputStream(outputFile, false);
        OutputStream outputStreamDE = new FileOutputStream(outputFileDE, false);
        OutputStream outputStreamNL = new FileOutputStream(outputFileNL, false);
        OutputStream outputStreamRU = new FileOutputStream(outputFileRU, false);
        try (OutputStreamWriter writerJava = new OutputStreamWriter(outputStreamJava, "UTF-8");
                OutputStreamWriter writer = new OutputStreamWriter(outputStream, "ISO-8859-1");
                OutputStreamWriter writerDE = new OutputStreamWriter(outputStreamDE, "ISO-8859-1");
                OutputStreamWriter writerNL = new OutputStreamWriter(outputStreamNL, "ISO-8859-1");
                OutputStreamWriter writerRU = new OutputStreamWriter(outputStreamRU, "ISO-8859-1")) {

            final InputStream propertiesFileStream = SimpleViewTest.class.getResourceAsStream("/nl/ru/languageininteraction/language/client/" + propertiesFileName + ".properties");
            InputStreamReader inputStreamReader = new InputStreamReader(propertiesFileStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineString;
            while ((lineString = bufferedReader.readLine()) != null) {
                if (lineString.isEmpty()) {
                    writer.write("\n");
                    writerDE.write("\n");
                    writerNL.write("\n");
                    writerRU.write("\n");
                } else if (lineString.startsWith("#")) {
                    writer.write(lineString);
                    writerDE.write(lineString);
                    writerNL.write(lineString);
                    writerRU.write(lineString);
                    writer.write("\n");
                    writerDE.write("\n");
                    writerNL.write("\n");
                    writerRU.write("\n");
                } else {
                    String key = lineString.split("=")[0];
                    final String escapedStringEN = (translationsEN.containsKey(key)) ? escapeString(translationsEN.get(key)) : escapePropertiesString(properties.getProperty(key, ""));
//                    if (!escapedStringEN.isEmpty()) {
                    writer.write(key);
                    writer.write(PROPERTY_SEPARATOR);
                    writer.write(escapedStringEN);
                    writer.write("\n");
//                    }
                    final String escapedStringDE = (translationsDE.containsKey(key)) ? escapeString(translationsDE.get(key)) : ""; //escapePropertiesString(properties_de.getProperty(key, ""));
                    if (!escapedStringDE.isEmpty()) {
                        writerDE.write(key);
                        writerDE.write(PROPERTY_SEPARATOR);
                        writerDE.write(escapedStringDE);
                        writerDE.write("\n");
                    }
                    final String escapedStringNL = (translationsNL.containsKey(key)) ? escapeString(translationsNL.get(key)) : ""; //escapePropertiesString(properties_nl.getProperty(key, ""));
                    if (!escapedStringNL.isEmpty()) {
                        writerNL.write(key);
                        writerNL.write(PROPERTY_SEPARATOR);
                        writerNL.write(escapedStringNL);
                        writerNL.write("\n");
                    }
                    final String escapedStringRU = (translationsRU.containsKey(key)) ? escapeString(translationsRU.get(key)) : ""; //escapePropertiesString(properties_nl.getProperty(key, ""));
                    if (!escapedStringRU.isEmpty()) {
                        writerRU.write(key);
                        writerRU.write(PROPERTY_SEPARATOR);
                        writerRU.write(escapedStringRU);
                        writerRU.write("\n");
                    }
                    writerJava.write("insertEnNlDeRu(\"");
                    writerJava.write(escapedStringEN.replace("\"", "\\\""));
                    writerJava.write("\", \"");
                    writerJava.write(escapedStringNL.replace("\"", "\\\""));
                    writerJava.write("\", \"");
                    writerJava.write(escapedStringDE.replace("\"", "\\\""));
                    writerJava.write("\", \"");
                    writerJava.write(escapedStringRU.replace("\"", "\\\""));
                    writerJava.write("\");\n");
                }
            }
            writerJava.close();
            writer.close();
            writerDE.close();
            writerNL.close();
            writerRU.close();
        }
    }

    private String escapeString(String inputString) {
        return inputString.replaceAll("\n", "\\\\n")
                .replace("onclick=\"window.open(this.href,'_system'); return false;\"", "")
                .replace("<a href=\"", "<a href=\"#\" onclick=\"window.open(''")
                .replaceAll("\"[ ]*>", "'',''_system''); return false;\">");
    }

    private String escapePropertiesString(String inputString) {
        return inputString.replaceAll("\n", "\\\\n");
    }

    public static void main(String[] args) throws IOException {
        final Csv2Properties properties2Csv = new Csv2Properties();
        properties2Csv.parseInputCSV();
        properties2Csv.writePropertyValues("Messages");
//        properties2Csv.writePropertyValues("Stimuli");
        properties2Csv.writePropertyValues("MetadataFields");
    }
}
