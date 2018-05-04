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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.audiopool;

import java.util.HashMap;

/**
 *
 * @author olhshk
 */
public class Indices {

    public static final HashMap<String, String> BAND_LABEL_TO_INTEGER;
    public static final HashMap<String, String> BAND_LABEL_TO_DIRNAME;
    public static final HashMap<String, Integer> BAND_LABEL_TO_INDEX;

    static {
        BAND_LABEL_TO_INTEGER = new HashMap<String, String>();
        BAND_LABEL_TO_INTEGER.put("min12db", "-12");
        BAND_LABEL_TO_INTEGER.put("min10db", "-10");
        BAND_LABEL_TO_INTEGER.put("min8db", "-8");
        BAND_LABEL_TO_INTEGER.put("min6db", "-6");
        BAND_LABEL_TO_INTEGER.put("min4db", "-4");
        BAND_LABEL_TO_INTEGER.put("min2db", "-2");
        BAND_LABEL_TO_INTEGER.put("zerodb", "0");
        BAND_LABEL_TO_INTEGER.put("plus2db", "2");
        BAND_LABEL_TO_INTEGER.put("plus4db", "4");
        BAND_LABEL_TO_INTEGER.put("plus6db", "6");
        BAND_LABEL_TO_INTEGER.put("plus8db", "8");
        BAND_LABEL_TO_INTEGER.put("plus10db", "10");
    }
    
    static {
        BAND_LABEL_TO_DIRNAME = new HashMap<String, String>();
        BAND_LABEL_TO_DIRNAME.put("min12db", "min12");
        BAND_LABEL_TO_DIRNAME.put("min10db", "min10");
        BAND_LABEL_TO_DIRNAME.put("min8db", "min8");
        BAND_LABEL_TO_DIRNAME.put("min6db", "min6");
        BAND_LABEL_TO_DIRNAME.put("min4db", "min4");
        BAND_LABEL_TO_DIRNAME.put("min2db", "min2");
        BAND_LABEL_TO_DIRNAME.put("zerodb", "zero");
        BAND_LABEL_TO_DIRNAME.put("plus2db", "plus2");
        BAND_LABEL_TO_DIRNAME.put("plus4db", "plus4");
        BAND_LABEL_TO_DIRNAME.put("plus6db", "plus6");
        BAND_LABEL_TO_DIRNAME.put("plus8db", "plus8");
        BAND_LABEL_TO_DIRNAME.put("plus10db", "plus10");
    }
    
     static {
        BAND_LABEL_TO_INDEX = new HashMap<String, Integer>();
        BAND_LABEL_TO_INDEX.put("min12db", 11);
        BAND_LABEL_TO_INDEX.put("min10db", 10);
        BAND_LABEL_TO_INDEX.put("min8db", 9);
        BAND_LABEL_TO_INDEX.put("min6db", 8);
        BAND_LABEL_TO_INDEX.put("min4db", 7);
        BAND_LABEL_TO_INDEX.put("min2db", 6);
        BAND_LABEL_TO_INDEX.put("zerodb", 5);
        BAND_LABEL_TO_INDEX.put("plus2db", 4);
        BAND_LABEL_TO_INDEX.put("plus4db", 3);
        BAND_LABEL_TO_INDEX.put("plus6db", 2);
        BAND_LABEL_TO_INDEX.put("plus8db", 1);
        BAND_LABEL_TO_INDEX.put("plus10db", 0);
    }

     
}
