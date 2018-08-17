/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author olhshk
 */
public class CsvRecords {

    private ArrayList<LinkedHashMap<String, String>> records;
    private final String delimiter;
    private final String eol;
    private String[] rawRecords;
    private String[] header;

    public CsvRecords(String[] header, String delimiter, String eol) {
        this.header = header;
        this.delimiter = delimiter;
        this.eol = eol;
    }

    public void readRecords(String rawString) throws Exception {

        this.rawRecords = rawString.split(this.eol);

        int startRecord;
        
        if (this.header == null) {
            startRecord = 1;
            this.header = this.rawRecords[0].split(this.delimiter);
            this.records = new ArrayList<LinkedHashMap<String, String>>(rawRecords.length - 1);
        } else {
            startRecord = 0;
            this.records = new ArrayList<LinkedHashMap<String, String>>(rawRecords.length);
        }

        for (int i = startRecord; i < this.rawRecords.length; i++) {

            String[] row = this.rawRecords[i].split(this.delimiter);
            if (row.length != this.header.length) {
                throw new Exception("The length of the row differs from the length of the header row. "
                        + "\n The header row length is " + this.header.length + ",\n"
                        + "and the current row number " + i + " is " + this.rawRecords[i]+" of length "+row.length);
            }

            LinkedHashMap<String, String> freshRecord = new LinkedHashMap<String, String>();
            this.records.add(freshRecord);

            for (int j = 0; j < this.header.length; j++) {
                freshRecord.put(this.header[j].trim(), row[j].trim());
            }
        }
    }

    public ArrayList<LinkedHashMap<String, String>> getRecords(){
        return this.records;
    }
  
}
