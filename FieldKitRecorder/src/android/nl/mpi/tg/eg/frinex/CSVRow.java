/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.frinex;

/**
 * @since Jan 21, 2016 3:45:50 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class CSVRow implements Comparable {

    private final long beginTime;
    private final long endTime;
    private final int tier;
    private final String tagString;
    private final String stimulusId;
    private final String stimulusCode;

    public CSVRow(long beginTime, long endTime, int tier, String stimulusId, String stimulusCode, String tagString) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.tier = tier;
        this.tagString = tagString;
        this.stimulusId = stimulusId;
        this.stimulusCode = stimulusCode;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public int getTier() {
        return tier;
    }

    public String getTagString() {
        return tagString;
    }

    public String getStimulusId() {
        return stimulusId;
    }

    public String getStimulusCode() {
        return stimulusCode;
    }

    @Override
    public int compareTo(Object obj) {
        final int before = -1;
        final int equals = 0;
        final int after = 1;
        if (this == obj) {
            return equals;
        }
        if (obj == null) {
            return before;
        }
        if (getClass() != obj.getClass()) {
            return before;
        }
        final CSVRow other = (CSVRow) obj;
        if (this.beginTime == other.beginTime) {
            return equals;
        }
        if (this.beginTime < other.beginTime) {
            return before;
        }
        return after;
    }
}
