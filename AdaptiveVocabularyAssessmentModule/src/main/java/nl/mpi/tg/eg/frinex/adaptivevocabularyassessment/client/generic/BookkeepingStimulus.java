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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author olhshk
 */
/**
 * Generic BookkeepingStimulus class.
 *
 * @param <T> userRecation, can be string, boolean, double, etc.
 */
public class BookkeepingStimulus<A extends BandStimulus> {

    private final static String[] FLDS = {"stimulus", "userReaction", "correctness", "timeStamp"};
    private final A stimulus;
    private String userReaction; // can be string, boolean, double, etc.
    private Boolean correctness;
    private long timeStamp;

    public BookkeepingStimulus(A stimulus) {
        this.stimulus = stimulus;
        this.userReaction = null;
        this.correctness = null;
    }

    public A getStimulus() {
        return this.stimulus;
    }

    public String getReaction() {
        return this.userReaction;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public Boolean getCorrectness() {
        return this.correctness;
    }

    public void setReaction(String reaction) {
        this.userReaction = reaction;
    }

    public void setCorrectness(boolean eval) {
        this.correctness = eval;
    }

    public void setTimeStamp(long timeStr) {
        this.timeStamp = timeStr;
    }

    @Override
    public String toString() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("fields", Arrays.asList(BookkeepingStimulus.FLDS).toString());
        map.put("stimulus", this.stimulus.getUniqueId());
        map.put("userReaction", this.userReaction);
        map.put("correctness", this.correctness);
        map.put("timeStamp", this.timeStamp);
        return map.toString();
    }

    public BookkeepingStimulus<A> toObject(String serialisation, LinkedHashMap<String, A> hashedStimuli) throws Exception {
        Map<String, Object> map = UtilsJSONdialect.stringToObjectMap(serialisation, BookkeepingStimulus.FLDS);
        BookkeepingStimulus<A> retVal = this.toBookkeepingStimulusObject(map, hashedStimuli);
        return retVal;
    }

    public BookkeepingStimulus<A> toBookkeepingStimulusObject(Map<String, Object> map, LinkedHashMap<String, A> hashedStimuli) throws Exception {
        try {
            if (map==null) {
                return null;
            }
            Set<String> keys = map.keySet();
            String corr = null;
            String reaction = null;
            String time = null;
            A localStimulus = null;
            for (String key : keys) {
                switch (key) {
                    case "stimulus":
                        String stimulusId = map.get(key).toString();
                        localStimulus = hashedStimuli.get(stimulusId);
                        if (localStimulus == null) {
                            throw new Exception("Cannot fine a stimulus with id "+stimulusId+ " in the hash map "+hashedStimuli);
                        }
                        break;
                    case "correctness":
                        Object hlp = map.get(key);
                        corr = (hlp != null) ? hlp.toString() : null;
                        break;
                    case "userReaction":
                        Object hlp2 = map.get(key);
                        reaction = (hlp2 != null) ? hlp2.toString() : null;
                        break;
                    case "timeStamp":
                        time = map.get(key).toString();
                        break;
                    default:
                        break;

                }
            }
            BookkeepingStimulus<A> retVal = new BookkeepingStimulus<A>(localStimulus);
            if (corr != null) {
                if (corr.equals("true")) {
                    retVal.setCorrectness(true);
                } else {
                    if (corr.equals("false")) {
                        retVal.setCorrectness(false);
                    } else {
                        return null;
                    }
                }
            }
            retVal.setReaction(reaction);
            retVal.setTimeStamp(Long.parseLong(time));
            return retVal;
        } catch (Exception ex) {
            System.out.println("Exception with map" + map);
            throw ex;
        }
    }
}
