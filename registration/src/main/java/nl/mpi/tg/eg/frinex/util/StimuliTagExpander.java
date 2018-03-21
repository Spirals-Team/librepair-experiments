/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics
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
package nl.mpi.tg.eg.frinex.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @since Apr 25, 2017 10:13:46 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class StimuliTagExpander {

    final String[] tagColumns = new String[]{"png", "shape", "version", "quadrant", "move"};
    final int[] distractorColumns = new int[]{0, 1, 2, 3, 4, 5, 6, 7};

    public String[] getTagColumns() {
        return tagColumns;
    }

    public List<String> getDistractorTagColumns(final String distractorString, final String spacerString) {
        List<String> returnList = new ArrayList<>();
        String[] distractorArray = (distractorString != null) ? distractorString.split(",") : new String[0];
        for (int distractorIndex : getDistractorColumns()) {
            if (distractorArray.length > distractorIndex) {
                returnList.addAll(Arrays.asList(getTagColumns(distractorArray[distractorIndex], spacerString)));
                returnList.add(distractorArray[distractorIndex]);
            } else {
                for (String unused : tagColumns) {
                    returnList.add("");
                }
                returnList.add("");
            }
        }
        return returnList;
    }

    public String[] getTagColumns(final String stimulusString, final String spacerString) {
        String[] returnColumns = new String[tagColumns.length];
        String[] stimulusTags = (stimulusString != null) ? stimulusString.split(":") : null;
        for (int columnIndex = 0; columnIndex < tagColumns.length; columnIndex++) {
            String columnContents = "";
            String tag = tagColumns[columnIndex];
            if (stimulusTags != null) {
                for (String stimulusTag : stimulusTags) {
                    if (stimulusTag.contains(tag)) {
                        columnContents += ((columnContents.isEmpty()) ? "" : spacerString) + stimulusTag;
                    }
                    returnColumns[columnIndex] = columnContents;
                }
            } else {
                returnColumns[columnIndex] = "";
            }
        }
        return returnColumns;
    }

    public int[] getDistractorColumns() {
        return distractorColumns;
    }

}
