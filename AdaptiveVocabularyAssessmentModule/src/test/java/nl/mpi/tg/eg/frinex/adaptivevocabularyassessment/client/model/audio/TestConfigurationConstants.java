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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio;

/**
 *
 * @author olhshk
 */
public class TestConfigurationConstants {
    
    public static final String AUDIO_STIMULI_DIR = "static/stimuli/" ; // must be the same as in the configuration file
    public static final int N_AUDIO_TRIALS = 3136;
    public static final int AUDIO_START_BAND = 5;
    public static final String AUDIO_UPPER_BOUND_FOR_CYCLES = "2";
    public static final int AUDIO_NUMBER_OF_BANDS = 16;
    public static final int AUDIO_TUPLE_SIZE = 3;
    public static final int AUDIO_MAX_LENGTH = 6;
    public static final String AUDIO_REQUIRED_LENGTHS = "3,4,5,6";
    public static final String AUDIO_REQUIRED_TYPES = "TARGET_ONLY,NO_TARGET,TARGET_AND_FOIL";
    public static final String AUDIO_MAX_DURATION_MINUTES = "10";
    public static final int AUDIO_TEST_DELAY_MS = 500;
    public static final int AUDIO_TEST_DELAY_CUE_MS = 1000;
    public static final String AUDIO_FIRST_STIMULUS_DURATION = "1500";
    public static final String AUDIO_LEARNING_TRIALS = "1000,1737,2045";
    public static final int AUDIO_N_LEARNING_TRIALS = 3;
}
