/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics
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
package nl.mpi.tg.eg.experimentdesigner.util;

import nl.mpi.tg.eg.experimentdesigner.controller.WizardController;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAboutScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAgreementScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAudioTestScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardCompletionScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardEditUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardRandomStimulusScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardTextScreen;

/**
 * @since Mar 15, 2016 4:50:41 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class Sara01 {

    private final WizardController wizardController = new WizardController();

    String[] stimuliExperimentArray = new String[]{
        "experiment/groupA/E_001_N_A.JPG",
        "experiment/groupA/E_002_P_A.JPG",
        "experiment/groupA/E_003_N_A.JPG",
        "experiment/groupA/E_004_P_A.JPG",
        "experiment/groupA/E_005_N_A.JPG",
        "experiment/groupA/E_006_P_A.JPG",
        "experiment/groupA/E_007_N_A.JPG",
        "experiment/groupA/E_008_P_A.JPG",
        "experiment/groupA/E_009_N_A.JPG",
        "experiment/groupA/E_010_P_A.JPG",
        "experiment/groupA/E_011_N_A.JPG",
        "experiment/groupA/E_012_P_A.JPG",
        "experiment/groupA/E_013_N_A.JPG",
        "experiment/groupA/E_014_P_A.JPG",
        "experiment/groupA/E_015_N_A.JPG",
        "experiment/groupA/E_016_P_A.JPG",
        "experiment/groupA/E_017_N_A.JPG",
        "experiment/groupA/E_018_P_A.JPG",
        "experiment/groupA/E_019_N_A.JPG",
        "experiment/groupA/E_020_P_A.JPG",
        "experiment/groupA/E_021_N_A.JPG",
        "experiment/groupA/E_022_P_A.JPG",
        "experiment/groupA/E_023_N_A.JPG",
        "experiment/groupA/E_024_P_A.JPG",
        "experiment/groupA/E_025_N_A.JPG",
        "experiment/groupA/E_026_P_A.JPG",
        "experiment/groupA/E_027_N_A.JPG",
        "experiment/groupA/E_028_P_A.JPG",
        "experiment/groupA/E_029_N_A.JPG",
        "experiment/groupA/E_030_P_A.JPG",
        "experiment/groupA/E_031_N_A.JPG",
        "experiment/groupA/E_032_P_A.JPG",
        "experiment/groupA/E_033_N_A.JPG",
        "experiment/groupA/E_034_P_A.JPG",
        "experiment/groupA/E_035_N_A.JPG",
        "experiment/groupA/E_036_P_A.JPG",
        "experiment/groupA/E_037_N_A.JPG",
        "experiment/groupA/E_038_P_A.JPG",
        "experiment/groupA/E_039_N_A.JPG",
        "experiment/groupA/E_040_P_A.JPG",
        "experiment/groupA/E_041_N_A.JPG",
        "experiment/groupA/E_042_P_A.JPG",
        "experiment/groupA/E_043_N_A.JPG",
        "experiment/groupA/E_044_P_A.JPG",
        "experiment/groupA/E_045_N_A.JPG",
        "experiment/groupA/E_046_P_A.JPG",
        "experiment/groupA/E_047_N_A.JPG",
        "experiment/groupA/E_048_P_A.JPG",
        "experiment/groupA/E_049_N_A.JPG",
        "experiment/groupA/E_050_P_A.JPG",
        "experiment/groupA/E_051_N_A.JPG",
        "experiment/groupA/E_052_P_A.JPG",
        "experiment/groupA/E_053_N_A.JPG",
        "experiment/groupA/E_054_P_A.JPG",
        "experiment/groupA/E_055_N_A.JPG",
        "experiment/groupA/E_056_P_A.JPG",
        "experiment/groupA/E_057_N_A.JPG",
        "experiment/groupA/E_058_P_A.JPG",
        "experiment/groupA/E_059_N_A.JPG",
        "experiment/groupA/E_060_P_A.JPG",
        "experiment/groupA/E_061_N_A.JPG",
        "experiment/groupA/E_062_P_A.JPG",
        "experiment/groupA/E_063_N_A.JPG",
        "experiment/groupA/E_064_P_A.JPG",
        "experiment/groupA/E_065_N_A.JPG",
        "experiment/groupA/E_066_P_A.JPG",
        "experiment/groupA/E_067_N_A.JPG",
        "experiment/groupA/E_068_P_A.JPG",
        "experiment/groupA/E_069_N_A.JPG",
        "experiment/groupA/E_070_P_A.JPG",
        "experiment/groupA/E_071_N_A.JPG",
        "experiment/groupA/E_072_P_A.JPG",
        "experiment/groupA/E_073_N_A.JPG",
        "experiment/groupA/E_074_P_A.JPG",
        "experiment/groupA/E_075_N_A.JPG",
        "experiment/groupA/E_076_P_A.JPG",
        "experiment/groupA/E_077_N_A.JPG",
        "experiment/groupA/E_078_P_A.JPG",
        "experiment/groupA/E_079_N_A.JPG",
        "experiment/groupA/E_080_P_A.JPG",
        "experiment/groupA/E_081_N_A.JPG",
        "experiment/groupA/E_082_P_A.JPG",
        "experiment/groupA/E_083_N_A.JPG",
        "experiment/groupA/E_084_P_A.JPG",
        "experiment/groupA/E_085_N_A.JPG",
        "experiment/groupA/E_086_P_A.JPG",
        "experiment/groupA/E_087_N_A.JPG",
        "experiment/groupA/E_088_P_A.JPG",
        "experiment/groupA/E_089_N_A.JPG",
        "experiment/groupA/E_090_P_A.JPG",
        "experiment/groupA/E_091_N_A.JPG",
        "experiment/groupA/E_092_P_A.JPG",
        "experiment/groupA/E_093_N_A.JPG",
        "experiment/groupA/E_094_P_A.JPG",
        "experiment/groupA/E_095_N_A.JPG",
        "experiment/groupA/E_096_P_A.JPG",
        "experiment/groupA/E_097_N_A.JPG",
        "experiment/groupA/E_098_P_A.JPG",
        "experiment/groupA/E_099_N_A.JPG",
        "experiment/groupA/E_100_P_A.JPG",
        "experiment/groupA/E_101_N_A.JPG",
        "experiment/groupA/E_102_P_A.JPG",
        "experiment/groupA/E_103_N_A.JPG",
        "experiment/groupA/E_104_P_A.JPG",
        "experiment/groupA/E_105_N_A.JPG",
        "experiment/groupA/E_106_P_A.JPG",
        "experiment/groupA/E_107_N_A.JPG",
        "experiment/groupA/E_108_P_A.JPG",
        "experiment/groupA/E_109_N_A.JPG",
        "experiment/groupA/E_110_P_A.JPG",
        "experiment/groupA/E_111_N_A.JPG",
        "experiment/groupA/E_112_P_A.JPG",
        "experiment/groupA/E_113_N_A.JPG",
        "experiment/groupA/E_114_P_A.JPG",
        "experiment/groupA/E_115_N_A.JPG",
        "experiment/groupA/E_116_P_A.JPG",
        "experiment/groupA/E_117_N_A.JPG",
        "experiment/groupA/E_118_P_A.JPG",
        "experiment/groupA/E_119_N_A.JPG",
        "experiment/groupA/E_120_P_A.JPG",
        "experiment/groupB/E_001_P_B.JPG",
        "experiment/groupB/E_002_N_B.JPG",
        "experiment/groupB/E_003_P_B.JPG",
        "experiment/groupB/E_004_N_B.JPG",
        "experiment/groupB/E_005_P_B.JPG",
        "experiment/groupB/E_006_N_B.JPG",
        "experiment/groupB/E_007_P_B.JPG",
        "experiment/groupB/E_008_N_B.JPG",
        "experiment/groupB/E_009_P_B.JPG",
        "experiment/groupB/E_010_N_B.JPG",
        "experiment/groupB/E_011_P_B.JPG",
        "experiment/groupB/E_012_N_B.JPG",
        "experiment/groupB/E_013_P_B.JPG",
        "experiment/groupB/E_014_N_B.JPG",
        "experiment/groupB/E_015_P_B.JPG",
        "experiment/groupB/E_016_N_B.JPG",
        "experiment/groupB/E_017_P_B.JPG",
        "experiment/groupB/E_018_N_B.JPG",
        "experiment/groupB/E_019_P_B.JPG",
        "experiment/groupB/E_020_N_B.JPG",
        "experiment/groupB/E_021_P_B.JPG",
        "experiment/groupB/E_022_N_B.JPG",
        "experiment/groupB/E_023_P_B.JPG",
        "experiment/groupB/E_024_N_B.JPG",
        "experiment/groupB/E_025_P_B.JPG",
        "experiment/groupB/E_026_N_B.JPG",
        "experiment/groupB/E_027_P_B.JPG",
        "experiment/groupB/E_028_N_B.JPG",
        "experiment/groupB/E_029_P_B.JPG",
        "experiment/groupB/E_030_N_B.JPG",
        "experiment/groupB/E_031_P_B.JPG",
        "experiment/groupB/E_032_N_B.JPG",
        "experiment/groupB/E_033_P_B.JPG",
        "experiment/groupB/E_034_N_B.JPG",
        "experiment/groupB/E_035_P_B.JPG",
        "experiment/groupB/E_036_N_B.JPG",
        "experiment/groupB/E_037_P_B.JPG",
        "experiment/groupB/E_038_N_B.JPG",
        "experiment/groupB/E_039_P_B.JPG",
        "experiment/groupB/E_040_N_B.JPG",
        "experiment/groupB/E_041_P_B.JPG",
        "experiment/groupB/E_042_N_B.JPG",
        "experiment/groupB/E_043_P_B.JPG",
        "experiment/groupB/E_044_N_B.JPG",
        "experiment/groupB/E_045_P_B.JPG",
        "experiment/groupB/E_046_N_B.JPG",
        "experiment/groupB/E_047_P_B.JPG",
        "experiment/groupB/E_048_N_B.JPG",
        "experiment/groupB/E_049_P_B.JPG",
        "experiment/groupB/E_050_N_B.JPG",
        "experiment/groupB/E_051_P_B.JPG",
        "experiment/groupB/E_052_N_B.JPG",
        "experiment/groupB/E_053_P_B.JPG",
        "experiment/groupB/E_054_N_B.JPG",
        "experiment/groupB/E_055_P_B.JPG",
        "experiment/groupB/E_056_N_B.JPG",
        "experiment/groupB/E_057_P_B.JPG",
        "experiment/groupB/E_058_N_B.JPG",
        "experiment/groupB/E_059_P_B.JPG",
        "experiment/groupB/E_060_N_B.JPG",
        "experiment/groupB/E_061_P_B.JPG",
        "experiment/groupB/E_062_N_B.JPG",
        "experiment/groupB/E_063_P_B.JPG",
        "experiment/groupB/E_064_N_B.JPG",
        "experiment/groupB/E_065_P_B.JPG",
        "experiment/groupB/E_066_N_B.JPG",
        "experiment/groupB/E_067_P_B.JPG",
        "experiment/groupB/E_068_N_B.JPG",
        "experiment/groupB/E_069_P_B.JPG",
        "experiment/groupB/E_070_N_B.JPG",
        "experiment/groupB/E_071_P_B.JPG",
        "experiment/groupB/E_072_N_B.JPG",
        "experiment/groupB/E_073_P_B.JPG",
        "experiment/groupB/E_074_N_B.JPG",
        "experiment/groupB/E_075_P_B.JPG",
        "experiment/groupB/E_076_N_B.JPG",
        "experiment/groupB/E_077_P_B.JPG",
        "experiment/groupB/E_078_N_B.JPG",
        "experiment/groupB/E_079_P_B.JPG",
        "experiment/groupB/E_080_N_B.JPG",
        "experiment/groupB/E_081_P_B.JPG",
        "experiment/groupB/E_082_N_B.JPG",
        "experiment/groupB/E_083_P_B.JPG",
        "experiment/groupB/E_084_N_B.JPG",
        "experiment/groupB/E_085_P_B.JPG",
        "experiment/groupB/E_086_N_B.JPG",
        "experiment/groupB/E_087_P_B.JPG",
        "experiment/groupB/E_088_N_B.JPG",
        "experiment/groupB/E_089_P_B.JPG",
        "experiment/groupB/E_090_N_B.JPG",
        "experiment/groupB/E_091_P_B.JPG",
        "experiment/groupB/E_092_N_B.JPG",
        "experiment/groupB/E_093_P_B.JPG",
        "experiment/groupB/E_094_N_B.JPG",
        "experiment/groupB/E_095_P_B.JPG",
        "experiment/groupB/E_096_N_B.JPG",
        "experiment/groupB/E_097_P_B.JPG",
        "experiment/groupB/E_098_N_B.JPG",
        "experiment/groupB/E_099_P_B.JPG",
        "experiment/groupB/E_100_N_B.JPG",
        "experiment/groupB/E_101_P_B.JPG",
        "experiment/groupB/E_102_N_B.JPG",
        "experiment/groupB/E_103_P_B.JPG",
        "experiment/groupB/E_104_N_B.JPG",
        "experiment/groupB/E_105_P_B.JPG",
        "experiment/groupB/E_106_N_B.JPG",
        "experiment/groupB/E_107_P_B.JPG",
        "experiment/groupB/E_108_N_B.JPG",
        "experiment/groupB/E_109_P_B.JPG",
        "experiment/groupB/E_110_N_B.JPG",
        "experiment/groupB/E_111_P_B.JPG",
        "experiment/groupB/E_112_N_B.JPG",
        "experiment/groupB/E_113_P_B.JPG",
        "experiment/groupB/E_114_N_B.JPG",
        "experiment/groupB/E_115_P_B.JPG",
        "experiment/groupB/E_116_N_B.JPG",
        "experiment/groupB/E_117_P_B.JPG",
        "experiment/groupB/E_118_N_B.JPG",
        "experiment/groupB/E_119_P_B.JPG",
        "experiment/groupB/E_120_N_B.JPG"
    };
//        "experiment/wav/E_001.mp3",
//        "experiment/wav/E_001.ogg",
//        "experiment/wav/E_001.wav",
//        "experiment/wav/E_002.mp3",
//        "experiment/wav/E_002.ogg",
//        "experiment/wav/E_002.wav",
//        "experiment/wav/E_003.mp3",
//        "experiment/wav/E_003.ogg",
//        "experiment/wav/E_003.wav",
//        "experiment/wav/E_004.mp3",
//        "experiment/wav/E_004.ogg",
//        "experiment/wav/E_004.wav",
//        "experiment/wav/E_005.mp3",
//        "experiment/wav/E_005.ogg",
//        "experiment/wav/E_005.wav",
//        "experiment/wav/E_006.mp3",
//        "experiment/wav/E_006.ogg",
//        "experiment/wav/E_006.wav",
//        "experiment/wav/E_007.mp3",
//        "experiment/wav/E_007.ogg",
//        "experiment/wav/E_007.wav",
//        "experiment/wav/E_008.mp3",
//        "experiment/wav/E_008.ogg",
//        "experiment/wav/E_008.wav",
//        "experiment/wav/E_009.mp3",
//        "experiment/wav/E_009.ogg",
//        "experiment/wav/E_009.wav",
//        "experiment/wav/E_010.mp3",
//        "experiment/wav/E_010.ogg",
//        "experiment/wav/E_010.wav",
//        "experiment/wav/E_011.mp3",
//        "experiment/wav/E_011.ogg",
//        "experiment/wav/E_011.wav",
//        "experiment/wav/E_012.mp3",
//        "experiment/wav/E_012.ogg",
//        "experiment/wav/E_012.wav",
//        "experiment/wav/E_013.mp3",
//        "experiment/wav/E_013.ogg",
//        "experiment/wav/E_013.wav",
//        "experiment/wav/E_014.mp3",
//        "experiment/wav/E_014.ogg",
//        "experiment/wav/E_014.wav",
//        "experiment/wav/E_015.mp3",
//        "experiment/wav/E_015.ogg",
//        "experiment/wav/E_015.wav",
//        "experiment/wav/E_016.mp3",
//        "experiment/wav/E_016.ogg",
//        "experiment/wav/E_016.wav",
//        "experiment/wav/E_017.mp3",
//        "experiment/wav/E_017.ogg",
//        "experiment/wav/E_017.wav",
//        "experiment/wav/E_018.mp3",
//        "experiment/wav/E_018.ogg",
//        "experiment/wav/E_018.wav",
//        "experiment/wav/E_019.mp3",
//        "experiment/wav/E_019.ogg",
//        "experiment/wav/E_019.wav",
//        "experiment/wav/E_020.mp3",
//        "experiment/wav/E_020.ogg",
//        "experiment/wav/E_020.wav",
//        "experiment/wav/E_021.mp3",
//        "experiment/wav/E_021.ogg",
//        "experiment/wav/E_021.wav",
//        "experiment/wav/E_022.mp3",
//        "experiment/wav/E_022.ogg",
//        "experiment/wav/E_022.wav",
//        "experiment/wav/E_023.mp3",
//        "experiment/wav/E_023.ogg",
//        "experiment/wav/E_023.wav",
//        "experiment/wav/E_024.mp3",
//        "experiment/wav/E_024.ogg",
//        "experiment/wav/E_024.wav",
//        "experiment/wav/E_025.mp3",
//        "experiment/wav/E_025.ogg",
//        "experiment/wav/E_025.wav",
//        "experiment/wav/E_026.mp3",
//        "experiment/wav/E_026.ogg",
//        "experiment/wav/E_026.wav",
//        "experiment/wav/E_027.mp3",
//        "experiment/wav/E_027.ogg",
//        "experiment/wav/E_027.wav",
//        "experiment/wav/E_028.mp3",
//        "experiment/wav/E_028.ogg",
//        "experiment/wav/E_028.wav",
//        "experiment/wav/E_029.mp3",
//        "experiment/wav/E_029.ogg",
//        "experiment/wav/E_029.wav",
//        "experiment/wav/E_030.mp3",
//        "experiment/wav/E_030.ogg",
//        "experiment/wav/E_030.wav",
//        "experiment/wav/E_031.mp3",
//        "experiment/wav/E_031.ogg",
//        "experiment/wav/E_031.wav",
//        "experiment/wav/E_032.mp3",
//        "experiment/wav/E_032.ogg",
//        "experiment/wav/E_032.wav",
//        "experiment/wav/E_033.mp3",
//        "experiment/wav/E_033.ogg",
//        "experiment/wav/E_033.wav",
//        "experiment/wav/E_034.mp3",
//        "experiment/wav/E_034.ogg",
//        "experiment/wav/E_034.wav",
//        "experiment/wav/E_035.mp3",
//        "experiment/wav/E_035.ogg",
//        "experiment/wav/E_035.wav",
//        "experiment/wav/E_036.mp3",
//        "experiment/wav/E_036.ogg",
//        "experiment/wav/E_036.wav",
//        "experiment/wav/E_037.mp3",
//        "experiment/wav/E_037.ogg",
//        "experiment/wav/E_037.wav",
//        "experiment/wav/E_038.mp3",
//        "experiment/wav/E_038.ogg",
//        "experiment/wav/E_038.wav",
//        "experiment/wav/E_039.mp3",
//        "experiment/wav/E_039.ogg",
//        "experiment/wav/E_039.wav",
//        "experiment/wav/E_040.mp3",
//        "experiment/wav/E_040.ogg",
//        "experiment/wav/E_040.wav",
//        "experiment/wav/E_041.mp3",
//        "experiment/wav/E_041.ogg",
//        "experiment/wav/E_041.wav",
//        "experiment/wav/E_042.mp3",
//        "experiment/wav/E_042.ogg",
//        "experiment/wav/E_042.wav",
//        "experiment/wav/E_043.mp3",
//        "experiment/wav/E_043.ogg",
//        "experiment/wav/E_043.wav",
//        "experiment/wav/E_044.mp3",
//        "experiment/wav/E_044.ogg",
//        "experiment/wav/E_044.wav",
//        "experiment/wav/E_045.mp3",
//        "experiment/wav/E_045.ogg",
//        "experiment/wav/E_045.wav",
//        "experiment/wav/E_046.mp3",
//        "experiment/wav/E_046.ogg",
//        "experiment/wav/E_046.wav",
//        "experiment/wav/E_047.mp3",
//        "experiment/wav/E_047.ogg",
//        "experiment/wav/E_047.wav",
//        "experiment/wav/E_048.mp3",
//        "experiment/wav/E_048.ogg",
//        "experiment/wav/E_048.wav",
//        "experiment/wav/E_049.mp3",
//        "experiment/wav/E_049.ogg",
//        "experiment/wav/E_049.wav",
//        "experiment/wav/E_050.mp3",
//        "experiment/wav/E_050.ogg",
//        "experiment/wav/E_050.wav",
//        "experiment/wav/E_051.mp3",
//        "experiment/wav/E_051.ogg",
//        "experiment/wav/E_051.wav",
//        "experiment/wav/E_052.mp3",
//        "experiment/wav/E_052.ogg",
//        "experiment/wav/E_052.wav",
//        "experiment/wav/E_053.mp3",
//        "experiment/wav/E_053.ogg",
//        "experiment/wav/E_053.wav",
//        "experiment/wav/E_054.mp3",
//        "experiment/wav/E_054.ogg",
//        "experiment/wav/E_054.wav",
//        "experiment/wav/E_055.mp3",
//        "experiment/wav/E_055.ogg",
//        "experiment/wav/E_055.wav",
//        "experiment/wav/E_056.mp3",
//        "experiment/wav/E_056.ogg",
//        "experiment/wav/E_056.wav",
//        "experiment/wav/E_057.mp3",
//        "experiment/wav/E_057.ogg",
//        "experiment/wav/E_057.wav",
//        "experiment/wav/E_058.mp3",
//        "experiment/wav/E_058.ogg",
//        "experiment/wav/E_058.wav",
//        "experiment/wav/E_059.mp3",
//        "experiment/wav/E_059.ogg",
//        "experiment/wav/E_059.wav",
//        "experiment/wav/E_060.mp3",
//        "experiment/wav/E_060.ogg",
//        "experiment/wav/E_060.wav",
//        "experiment/wav/E_061.mp3",
//        "experiment/wav/E_061.ogg",
//        "experiment/wav/E_061.wav",
//        "experiment/wav/E_062.mp3",
//        "experiment/wav/E_062.ogg",
//        "experiment/wav/E_062.wav",
//        "experiment/wav/E_063.mp3",
//        "experiment/wav/E_063.ogg",
//        "experiment/wav/E_063.wav",
//        "experiment/wav/E_064.mp3",
//        "experiment/wav/E_064.ogg",
//        "experiment/wav/E_064.wav",
//        "experiment/wav/E_065.mp3",
//        "experiment/wav/E_065.ogg",
//        "experiment/wav/E_065.wav",
//        "experiment/wav/E_066.mp3",
//        "experiment/wav/E_066.ogg",
//        "experiment/wav/E_066.wav",
//        "experiment/wav/E_067.mp3",
//        "experiment/wav/E_067.ogg",
//        "experiment/wav/E_067.wav",
//        "experiment/wav/E_068.mp3",
//        "experiment/wav/E_068.ogg",
//        "experiment/wav/E_068.wav",
//        "experiment/wav/E_069.mp3",
//        "experiment/wav/E_069.ogg",
//        "experiment/wav/E_069.wav",
//        "experiment/wav/E_070.mp3",
//        "experiment/wav/E_070.ogg",
//        "experiment/wav/E_070.wav",
//        "experiment/wav/E_071.mp3",
//        "experiment/wav/E_071.ogg",
//        "experiment/wav/E_071.wav",
//        "experiment/wav/E_072.mp3",
//        "experiment/wav/E_072.ogg",
//        "experiment/wav/E_072.wav",
//        "experiment/wav/E_073.mp3",
//        "experiment/wav/E_073.ogg",
//        "experiment/wav/E_073.wav",
//        "experiment/wav/E_074.mp3",
//        "experiment/wav/E_074.ogg",
//        "experiment/wav/E_074.wav",
//        "experiment/wav/E_075.mp3",
//        "experiment/wav/E_075.ogg",
//        "experiment/wav/E_075.wav",
//        "experiment/wav/E_076.mp3",
//        "experiment/wav/E_076.ogg",
//        "experiment/wav/E_076.wav",
//        "experiment/wav/E_077.mp3",
//        "experiment/wav/E_077.ogg",
//        "experiment/wav/E_077.wav",
//        "experiment/wav/E_078.mp3",
//        "experiment/wav/E_078.ogg",
//        "experiment/wav/E_078.wav",
//        "experiment/wav/E_079.mp3",
//        "experiment/wav/E_079.ogg",
//        "experiment/wav/E_079.wav",
//        "experiment/wav/E_080.mp3",
//        "experiment/wav/E_080.ogg",
//        "experiment/wav/E_080.wav",
//        "experiment/wav/E_081.mp3",
//        "experiment/wav/E_081.ogg",
//        "experiment/wav/E_081.wav",
//        "experiment/wav/E_082.mp3",
//        "experiment/wav/E_082.ogg",
//        "experiment/wav/E_082.wav",
//        "experiment/wav/E_083.mp3",
//        "experiment/wav/E_083.ogg",
//        "experiment/wav/E_083.wav",
//        "experiment/wav/E_084.mp3",
//        "experiment/wav/E_084.ogg",
//        "experiment/wav/E_084.wav",
//        "experiment/wav/E_085.mp3",
//        "experiment/wav/E_085.ogg",
//        "experiment/wav/E_085.wav",
//        "experiment/wav/E_086.mp3",
//        "experiment/wav/E_086.ogg",
//        "experiment/wav/E_086.wav",
//        "experiment/wav/E_087.mp3",
//        "experiment/wav/E_087.ogg",
//        "experiment/wav/E_087.wav",
//        "experiment/wav/E_088.mp3",
//        "experiment/wav/E_088.ogg",
//        "experiment/wav/E_088.wav",
//        "experiment/wav/E_089.mp3",
//        "experiment/wav/E_089.ogg",
//        "experiment/wav/E_089.wav",
//        "experiment/wav/E_090.mp3",
//        "experiment/wav/E_090.ogg",
//        "experiment/wav/E_090.wav",
//        "experiment/wav/E_091.mp3",
//        "experiment/wav/E_091.ogg",
//        "experiment/wav/E_091.wav",
//        "experiment/wav/E_092.mp3",
//        "experiment/wav/E_092.ogg",
//        "experiment/wav/E_092.wav",
//        "experiment/wav/E_093.mp3",
//        "experiment/wav/E_093.ogg",
//        "experiment/wav/E_093.wav",
//        "experiment/wav/E_094.mp3",
//        "experiment/wav/E_094.ogg",
//        "experiment/wav/E_094.wav",
//        "experiment/wav/E_095.mp3",
//        "experiment/wav/E_095.ogg",
//        "experiment/wav/E_095.wav",
//        "experiment/wav/E_096.mp3",
//        "experiment/wav/E_096.ogg",
//        "experiment/wav/E_096.wav",
//        "experiment/wav/E_097.mp3",
//        "experiment/wav/E_097.ogg",
//        "experiment/wav/E_097.wav",
//        "experiment/wav/E_098.mp3",
//        "experiment/wav/E_098.ogg",
//        "experiment/wav/E_098.wav",
//        "experiment/wav/E_099.mp3",
//        "experiment/wav/E_099.ogg",
//        "experiment/wav/E_099.wav",
//        "experiment/wav/E_100.mp3",
//        "experiment/wav/E_100.ogg",
//        "experiment/wav/E_100.wav",
//        "experiment/wav/E_101.mp3",
//        "experiment/wav/E_101.ogg",
//        "experiment/wav/E_101.wav",
//        "experiment/wav/E_102.mp3",
//        "experiment/wav/E_102.ogg",
//        "experiment/wav/E_102.wav",
//        "experiment/wav/E_103.mp3",
//        "experiment/wav/E_103.ogg",
//        "experiment/wav/E_103.wav",
//        "experiment/wav/E_104.mp3",
//        "experiment/wav/E_104.ogg",
//        "experiment/wav/E_104.wav",
//        "experiment/wav/E_105.mp3",
//        "experiment/wav/E_105.ogg",
//        "experiment/wav/E_105.wav",
//        "experiment/wav/E_106.mp3",
//        "experiment/wav/E_106.ogg",
//        "experiment/wav/E_106.wav",
//        "experiment/wav/E_107.mp3",
//        "experiment/wav/E_107.ogg",
//        "experiment/wav/E_107.wav",
//        "experiment/wav/E_108.mp3",
//        "experiment/wav/E_108.ogg",
//        "experiment/wav/E_108.wav",
//        "experiment/wav/E_109.mp3",
//        "experiment/wav/E_109.ogg",
//        "experiment/wav/E_109.wav",
//        "experiment/wav/E_110.mp3",
//        "experiment/wav/E_110.ogg",
//        "experiment/wav/E_110.wav",
//        "experiment/wav/E_111.mp3",
//        "experiment/wav/E_111.ogg",
//        "experiment/wav/E_111.wav",
//        "experiment/wav/E_112.mp3",
//        "experiment/wav/E_112.ogg",
//        "experiment/wav/E_112.wav",
//        "experiment/wav/E_113.mp3",
//        "experiment/wav/E_113.ogg",
//        "experiment/wav/E_113.wav",
//        "experiment/wav/E_114.mp3",
//        "experiment/wav/E_114.ogg",
//        "experiment/wav/E_114.wav",
//        "experiment/wav/E_115.mp3",
//        "experiment/wav/E_115.ogg",
//        "experiment/wav/E_115.wav",
//        "experiment/wav/E_116.mp3",
//        "experiment/wav/E_116.ogg",
//        "experiment/wav/E_116.wav",
//        "experiment/wav/E_117.mp3",
//        "experiment/wav/E_117.ogg",
//        "experiment/wav/E_117.wav",
//        "experiment/wav/E_118.mp3",
//        "experiment/wav/E_118.ogg",
//        "experiment/wav/E_118.wav",
//        "experiment/wav/E_119.mp3",
//        "experiment/wav/E_119.ogg",
//        "experiment/wav/E_119.wav",
//        "experiment/wav/E_120.mp3",
//        "experiment/wav/E_120.ogg",
//        "experiment/wav/E_120.wav",
    String[] stimuliPracticeArray = new String[]{
        "practice/groupA/T_001_N_A.JPG",
        "practice/groupA/T_002_P_A.JPG",
        "practice/groupA/T_003_N_A.JPG",
        "practice/groupA/T_004_P_A.JPG",
        "practice/groupA/T_005_N_A.JPG",
        "practice/groupA/T_006_P_A.JPG",
        "practice/groupA/T_007_N_A.JPG",
        "practice/groupA/T_008_P_A.JPG",
        "practice/groupA/T_009_N_A.JPG",
        "practice/groupA/T_010_P_A.JPG",
        "practice/groupB/T_001_P_B.JPG",
        "practice/groupB/T_002_N_B.JPG",
        "practice/groupB/T_003_P_B.JPG",
        "practice/groupB/T_004_N_B.JPG",
        "practice/groupB/T_005_P_B.JPG",
        "practice/groupB/T_006_N_B.JPG",
        "practice/groupB/T_007_P_B.JPG",
        "practice/groupB/T_008_N_B.JPG",
        "practice/groupB/T_009_P_B.JPG",
        "practice/groupB/T_010_N_B.JPG", //        "practice/wav/T_001.mp3",
    //        "practice/wav/T_001.ogg",
    //        "practice/wav/T_001.wav",
    //        "practice/wav/T_002.mp3",
    //        "practice/wav/T_002.ogg",
    //        "practice/wav/T_002.wav",
    //        "practice/wav/T_003.mp3",
    //        "practice/wav/T_003.ogg",
    //        "practice/wav/T_003.wav",
    //        "practice/wav/T_004.mp3",
    //        "practice/wav/T_004.ogg",
    //        "practice/wav/T_004.wav",
    //        "practice/wav/T_005.mp3",
    //        "practice/wav/T_005.ogg",
    //        "practice/wav/T_005.wav",
    //        "practice/wav/T_006.mp3",
    //        "practice/wav/T_006.ogg",
    //        "practice/wav/T_006.wav",
    //        "practice/wav/T_007.mp3",
    //        "practice/wav/T_007.ogg",
    //        "practice/wav/T_007.wav",
    //        "practice/wav/T_008.mp3",
    //        "practice/wav/T_008.ogg",
    //        "practice/wav/T_008.wav",
    //        "practice/wav/T_009.mp3",
    //        "practice/wav/T_009.ogg",
    //        "practice/wav/T_009.wav",
    //        "practice/wav/T_010.mp3",
    //        "practice/wav/T_010.ogg",
    //        "practice/wav/T_010.wav"
    };
    String[] testAudio = new String[]{
        "testwav/test_wav.mp3",
        "testwav/test_wav.ogg",
        "testwav/test_wav.wav"
    };
    String introImage = "introimage/introimage.jpg";

    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName("AntwoordRaden");
        wizardData.setShowMenuBar(false);
        wizardData.setTextFontSize(17);
        wizardData.setObfuscateScreenNames(false);
        //Here is the updated experiment flow and the suggested directory structure:
        //Directory/introimage
        //Directory/testwav
        //Directory/practice/wav
        //Directory/practice/groupA
        //Directory/practice/groupB
        //Directory/experiment/wav
        //Directory/experiment/groupA
        //Directory/experiment/groupB
        //E_001_P_A
        //T_001_N_B
        //I have added the 1/2 second pause in my list below:
        //User follows link with group id and invite id
        WizardTextScreen wizardTextScreen = new WizardTextScreen("InformationScreen", "Beste deelnemer,<br/>"
                + "<br/>"
                + "Welkom. In dit experiment vragen we je om naar geluidsfragmenten te luisteren en te proberen te voorspellen hoe de volgende spreker zal reageren. Alle geluidsfragmenten komen uit een echt telefoongesprek tussen twee bekenden.<br/>"
                + "<br/>"
                + "Het gaat als volgt. Je krijgt eerst de context van het gesprek te zien. Dat gebeurt met plaatjes van twee mensen die aan het bellen zijn en tekstballonnen met wat ze tegen elkaar zeggen. Hieronder staat een voorbeeld. Soms zijn er ook denkballonnen, dat betekent dat die persoon het alleen denkt en niet hardop zegt. Onderaan staat altijd een plaatje van een luidspreker bij de persoon die daarna gaat praten. Die volgende uitspraak krijg je dan als geluidsfragment te horen, zodra je op de spatiebalk hebt gedrukt.<br/>"
                + "<br/>"
                + "Deze laatste uitspraak bestaat altijd uit een vraag of voorstel aan de andere persoon. Bijvoorbeeld: 'Heb je zin om vanavond langs te komen?'. Het is jouw taak om te gokken hoe positief of negatief het antwoord van de ander op die vraag was. Met andere woorden, gaat de antwoorder (graag) op dit voorstel in of niet? Dit geef je aan op een schaal van 1 tot 7, waar 1 zeer waarschijnlijk negatief betekent en 7 zeer waarschijnlijk positief. Baseer je antwoord zowel op de context die je gezien hebt als op de vraag zelf.<br/>"
                + "<br/>"
                + "Dus: je krijgt eerst de context van het gesprek uitgeschreven op het scherm te zien. Probeer je een goed beeld te vormen van de situatie, daar mag je best even de tijd voor nemen. Als je alles goed gelezen en begrepen hebt, druk je op de spatiebalk. Op dat moment VERDWIJNT HET BEELD en hoor je de volgende uitspraak door de koptelefoon. Zodra de uitspraak afgelopen is, kan je klikken op een cijfer van 1 tot 7. 1 betekent een zeer waarschijnlijk negatief antwoord, 7 een zeer waarschijnlijk positief antwoord.<br/>"
                + "Zorg ervoor dat je deze instructies goed begrepen hebt. Druk dan op de spatiebalk."
                + "<br/>"
                + "<img src=\"static/introimage/introimage.jpg\"/><br/>",
                "volgende [ spatiebalk ]"
        );
        //Information screen 

        //Agreement
        WizardAgreementScreen agreementScreen = new WizardAgreementScreen("Agreement",
                "Toestemmingsverklaring voor deelname aan het onderzoek:<br/>"
                + "Antwoord raden<br/>"
                + "Voordat je begint met dit experiment, dien je eerst te bevestigen dat je toestemt met deelname aan dit experiment. We zullen je antwoorden opslaan voor latere analyse. We gebruiken de resultaten alleen voor onderzoeksdoeleinden, en zullen ze beschrijven in gespecialiseerde tijschriften of wellicht in kranten of op onze website. Echter, we zullen de resultaten NOOIT rapporteren op zo'n manier dat je zou kunnen worden geïdentificeerd.<br/>"
                + "<br/>"
                + "Door akkoord te gaan, verklaar je dat je voldoende bent geïnformeerd over het onderzoek en dat je goed over deelname aan het onderzoek hebt na kunnen denken. Je bent tijdens dit experiment op elk moment vrij om je terug te trekken zonder uitleg te geven. Ook kun je je gegevens laten verwijderen tot het moment van publicatie, zonder uit te leggen waarom je dat doet.<br/>"
                + "<br/>"
                + "Er zijn geen risico's bekend met het meedoen aan dit experiment.<br/>"
                + "<br/>"
                + "Als je ermee instemt om door te gaan met dit experiment, klik dan op 'Akkoord' om verder te gaan.<br/>"
                + "Als je besluit niet deel te nemen aan het experiment, kun je de pagina sluiten of naar een andere website gaan.<br/>"
                + "<br/>"
                + "", "Akkoord");
//        wizardData.setAgreementText("agreementText");
//        wizardData.setDisagreementScreenText("disagreementScreenText");
        //metadata
        final WizardEditUserScreen wizardEditUserScreen = new WizardEditUserScreen();
        wizardEditUserScreen.setOn_Error_Text("Could not contact the server, please check your internet connection and try again.");
        wizardEditUserScreen.setScreenTitle("Edit User");
        wizardEditUserScreen.setMenuLabel("Edit User");
        wizardEditUserScreen.setScreenTag("Edit_User");
        wizardEditUserScreen.setNextButton("Save Details");
        wizardEditUserScreen.setSendData(true);
//        wizardEditUserScreen.setMetadataScreen(true);
//        wizardData.setAgeField(true);
        wizardEditUserScreen.setCustomFields(new String[]{
            "workerId:Arbeider id:.*:.",
            "firstName:Voornaam:.'{'3,'}':Voer minimaal drie letters.",
            "lastName:Achternaam:.'{'3,'}':Voer minimaal drie letters.",
            "age:Leeftijd:[0-9]+:Voer een getal.",
            "gender:Geslacht:|man|vrouw|anders:."
        });
//        wizardData.setFirstNameField(true);
//        wizardData.setLastNameField(true);
//        wizardData.setGenderField(true);
        //audio test page
        final WizardAudioTestScreen wizardAudioTestScreen = new WizardAudioTestScreen();
        wizardAudioTestScreen.setNextButton("Het geluid is OK");
        wizardAudioTestScreen.setScreenText("Voordat we met het echte experiment beginnen, willen we je vragen even te testen of je geluidsinstellingen goed zijn voor het experiment. Druk op de onderstaande knop om een kort voorbeeldfragment te horen. De echte fragmenten variëren in sterkte, maar het voorbeeld is ongeveer net zo zacht als het zachtste fragment. Het is belangrijk dat je dit goed kunt verstaan. Als het te zacht klinkt (of te hard), probeer dan de het geluidsniveau op je computer aan te passen. Je kunt net zo vaak op de knop drukken tot het goed te verstaan is.<br/>"
                + " <br/>"
                + "Daarnaast willen we je ook vragen te testen of dit fragment (vooral) in je linkeroor te horen is. Zo niet, kun je dan je koptelefoon (of luidsprekers) omdraaien?<br/>"
                + " <br/>"
                + "Als de geluidssterkte goed is en je het geluid links hoort, kun je op de knop \"Het geluid is OK\" drukken om te beginnen met het experiment. Denk eraan om de context steeds goed op je in te laten werken voordat je verder gaat naar het geluidsfragment.");
        wizardAudioTestScreen.setAudioPath("static/testwav/test_wav");

        wizardEditUserScreen.setNextWizardScreen(wizardAudioTestScreen);
        wizardTextScreen.setNextWizardScreen(wizardEditUserScreen);
        agreementScreen.setNextWizardScreen(wizardTextScreen);
        wizardData.addScreen(agreementScreen);
        wizardData.addScreen(wizardTextScreen);
        wizardData.addScreen(wizardEditUserScreen);
        wizardData.addScreen(wizardAudioTestScreen);
        //practice (5 items):
        //                image
        //                next button
        //                Clear screen
        //                pause 1/2 second
        //                audio
        //                end of audio 1-7 rating buttons
        //                next stimuli
        WizardRandomStimulusScreen randomStimulusScreenP = new WizardRandomStimulusScreen();
        randomStimulusScreenP.getWizardScreenData().setScreenTitle("StimulusScreenP");
        randomStimulusScreenP.getWizardScreenData().setMenuLabel("StimulusScreenP");
        randomStimulusScreenP.getWizardScreenData().setScreenTag("StimulusScreenP");
//        randomStimulusScreenP.setStimuliPath("stimuliPath");
        randomStimulusScreenP.setButtonLabel("volgende [ spatiebalk ]");
        randomStimulusScreenP.getWizardScreenData().setStimuliRandomTags(new String[]{"groupA", "groupB"});
        randomStimulusScreenP.getWizardScreenData().setStimulusCodeMatch("/([ET]_[0-9]+)_");
        randomStimulusScreenP.getWizardScreenData().setStimulusCodeFormat("static/practice/wav/<code>");
        randomStimulusScreenP.setStimuliSet(stimuliPracticeArray);
        randomStimulusScreenP.getWizardScreenData().setStimuliCount(1000);
        randomStimulusScreenP.setRandomiseStimuli(true);
        randomStimulusScreenP.setStimulusMsDelay(1000);
        randomStimulusScreenP.getWizardScreenData().setStimulusCodeMsDelay(500);
        randomStimulusScreenP.getWizardScreenData().setStimulusResponseOptions("1,2,3,4,5,6,7");
        randomStimulusScreenP.getWizardScreenData().setStimulusResponseLabelLeft("zeer waarschijnlijk negatief");
        randomStimulusScreenP.getWizardScreenData().setStimulusResponseLabelRight("zeer waarschijnlijk positief");
        wizardData.addScreen(randomStimulusScreenP);
        //experiment round (120 items):
        //     

        WizardRandomStimulusScreen randomStimulusScreenE = new WizardRandomStimulusScreen();
        randomStimulusScreenE.setScreenTitle("StimulusScreenE");
        randomStimulusScreenE.setMenuLabel("StimulusScreenE");
        randomStimulusScreenE.setScreenTag("StimulusScreenE");
//        randomStimulusScreenE.setStimuliPath("stimuliPath");
        randomStimulusScreenE.setButtonLabel("volgende [ spatiebalk ]");
        randomStimulusScreenE.getWizardScreenData().setStimuliRandomTags(new String[]{"groupA", "groupB"});
        randomStimulusScreenE.getWizardScreenData().setStimulusCodeMatch("/([ET]_[0-9]+)_");
        randomStimulusScreenE.getWizardScreenData().setStimuliCount(1000);
        randomStimulusScreenE.setRandomiseStimuli(true);
        randomStimulusScreenE.setStimulusMsDelay(1000);
        randomStimulusScreenE.getWizardScreenData().setStimulusCodeMsDelay(500);
        randomStimulusScreenE.getWizardScreenData().setStimulusCodeFormat("static/experiment/wav/<code>");
        randomStimulusScreenE.setStimuliSet(stimuliExperimentArray);
        randomStimulusScreenE.getWizardScreenData().setStimulusResponseOptions("1,2,3,4,5,6,7");
        randomStimulusScreenE.getWizardScreenData().setStimulusResponseLabelLeft("zeer waarschijnlijk negatief");
        randomStimulusScreenE.getWizardScreenData().setStimulusResponseLabelRight("zeer waarschijnlijk positief");
        wizardData.addScreen(randomStimulusScreenE);

        WizardCompletionScreen completionScreen = new WizardCompletionScreen("Dit is het einde van het experiment.<br/>"
                + "<br/>"
                + "Ter bevestiging van je deelname willen we je vragen om de volgende code te sturen naar dorine.vanbelzen@mpi.nl. Pas na ontvangst van deze code, kun je worden uitbetaald.<br/>"
                + "<br/>"
                + "Bedankt voor je deelname!", true, true,
                "Wil nog iemand op dit apparaat deelnemen aan dit onderzoek, klik dan op de onderstaande knop.",
                "Opnieuw beginnen",
                "Finished",
                "Could not contact the server, please check your internet connection and try again.", "Retry");
        wizardData.addScreen(completionScreen);

        agreementScreen.setNextWizardScreen(wizardTextScreen);
        wizardTextScreen.setNextWizardScreen(wizardEditUserScreen);
        wizardEditUserScreen.setNextWizardScreen(wizardAudioTestScreen);
        wizardAudioTestScreen.setNextWizardScreen(randomStimulusScreenP);
        randomStimulusScreenP.setNextWizardScreen(randomStimulusScreenE);
        randomStimulusScreenE.setNextWizardScreen(completionScreen);
        completionScreen.setNextWizardScreen(agreementScreen);
//                        completionScreen
        final WizardAboutScreen wizardAboutScreen = new WizardAboutScreen("Over", false);
        wizardAboutScreen.setBackWizardScreen(agreementScreen);
        wizardData.addScreen(wizardAboutScreen);
        return wizardData;
    }

    public Experiment getExperiment() {
        return wizardController.getExperiment(getWizardData());
    }
}
