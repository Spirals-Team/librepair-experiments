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
package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author olhshk
 */
public class AudioStimuliFromFiles {

    private final int max_string_size = 500;

    public ArrayList<String> parseInputCSVIntoStringArray(String fileLocation) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(fileLocation));

        try {
            ArrayList<String> retVal = new ArrayList<String>();
            int counter = 0;
            StringBuilder freshPortion = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                //System.out.println(line);
                freshPortion.append("\n+\"").append(line).append("\\n\"");
                counter++;
                if ((counter % this.max_string_size) == 0) {
                    retVal.add(freshPortion.toString());
                    freshPortion = new StringBuilder();
                }
                line = br.readLine();
            }
            retVal.add(freshPortion.toString());
            return retVal;
        } finally {
            br.close();
        }
    }
}
