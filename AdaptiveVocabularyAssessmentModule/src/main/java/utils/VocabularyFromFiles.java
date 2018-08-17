/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics, Nijmegen
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
package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author olhshk
 */
public class VocabularyFromFiles {
    
    private final ArrayList<String> localWORDS  = new ArrayList<String>();
    private final ArrayList<String> localNONWORDS = new ArrayList<String>(); 
    
   
    public void parseWordInputCSV(String wordFileLocation, String bandColumnName, String wordColumnName, String ratingLabels, String correctResponse) throws IOException {
        File inputFileWords = new File(wordFileLocation);
        final Reader reader = new InputStreamReader(inputFileWords.toURL().openStream(), "UTF-8"); // todo: this might need to change to "ISO-8859-1" depending on the usage
        Iterable<CSVRecord> records = CSVFormat.newFormat(';').withHeader().parse(reader);
        
        for (CSVRecord record : records) {
            int bandNumber = Integer.parseInt(record.get(bandColumnName));
            String spelling = record.get(wordColumnName);
            String stimulusString = bandNumber+";"+spelling;
            localWORDS.add(stimulusString);
        }
      }

    public void parseNonwordInputCSV(String nonwordFileLocation, String nonwordColumnName, String ratingLabels, String correctResponse) throws IOException {
        final File inputFileNonWords = new File(nonwordFileLocation);
        final Reader reader = new InputStreamReader(inputFileNonWords.toURL().openStream(), "UTF-8"); // todo: this might need to change to "ISO-8859-1" depending on the usage
        Iterable<CSVRecord> records = CSVFormat.newFormat(';').withHeader().parse(reader);
        for (CSVRecord record : records) {
            String spelling = record.get(nonwordColumnName);
            localNONWORDS.add(spelling);
        }
        
         
    }

    public ArrayList<String> getWords() {
        return localWORDS;
    }

    public ArrayList<String> getNonwords() {
        return localNONWORDS;
    }

  

}
