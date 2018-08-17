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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.peabodypool;

import java.util.HashMap;

/**
 *
 * @author olhshk
 */
public class Indices {
    public static final HashMap<String, Integer> SET_TO_BAND_INDEX;
    public static final String[] SET_INDEX = {"set1", "set2","set3","set4","set5","set6","set7","set8","set9","set10","set11","set12","set13","set14","set15","set16","set17"};
    
    static {
        SET_TO_BAND_INDEX = new HashMap<String, Integer>();
        SET_TO_BAND_INDEX.put("set1", 0);
        SET_TO_BAND_INDEX.put("set2", 1);
        SET_TO_BAND_INDEX.put("set3", 2);
        SET_TO_BAND_INDEX.put("set4", 3);
        SET_TO_BAND_INDEX.put("set5", 4);
        SET_TO_BAND_INDEX.put("set6", 5);
        SET_TO_BAND_INDEX.put("set7", 6);
        SET_TO_BAND_INDEX.put("set8", 7);
        SET_TO_BAND_INDEX.put("set9", 8);
        SET_TO_BAND_INDEX.put("set10", 9);
        SET_TO_BAND_INDEX.put("set11", 10);
        SET_TO_BAND_INDEX.put("set12", 11);
        SET_TO_BAND_INDEX.put("set13", 12);
        SET_TO_BAND_INDEX.put("set14", 13);
        SET_TO_BAND_INDEX.put("set15", 14);
        SET_TO_BAND_INDEX.put("set16", 15);
        SET_TO_BAND_INDEX.put("set17", 16);
    }
    
    public static final String[][] AGE_RANGES ={
        {"2;3","2;5"},
        {"2;6","2;11"}, 
        {"3;0","3;11"}, 
        {"4;0","4;5"},
        {"4;6","5;5"}, 
        {"5;6","6;5"}, 
        {"6;6","7;11"},
        {"8;0","9;11"},
        {"10;0","10;11"}, 
        {"11;0","13;11"},
        {"14;0","15;11"},
        {"16;0","35;11"},
        {"36;0","90;0"}};
    
}
