package com.rgross.parser.common;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 1/6/2018.
 */
public abstract class CSVParser implements Parser {

    List<String> readCSVFile(String file) {
        InputStream fileAsStream = getClass().getClassLoader().getResourceAsStream(file);
        InputStreamReader reader = new InputStreamReader(fileAsStream);
        BufferedReader bufferedReader = null;
        ArrayList<String> parsedLines = new ArrayList<>();
        String line;
        try {
            bufferedReader = new BufferedReader(reader);
            while ((line = bufferedReader.readLine()) != null)  {


                // Try/Catch Varies on Implementation
                parsedLines.add(line);
            }

            // Need to a these try/catch blocks to see if they're necessary.
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        return parsedLines;
    }

    abstract public void processLine(String line) throws Exception;

    // It processes the output from the file..
    public void parse(String file) throws Exception {

        List<String> readLines = readCSVFile(file);

        for (String line : readLines) {

            processLine(line);

        }

    }

}
