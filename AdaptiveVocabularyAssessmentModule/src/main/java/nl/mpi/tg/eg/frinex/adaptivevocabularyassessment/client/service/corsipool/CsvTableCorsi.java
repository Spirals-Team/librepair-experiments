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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.corsipool;

/**
 *
 * @author olhshk
 */
public class CsvTableCorsi {

    public static String CSV_STRING = "Trial_id;Blank_stimulis;Letter_stimulus;Stimulus_1;Stimulus_2;Stimulus_3;Stimulus_4;Stimulus_5;Stimulus_6;Stimulus_7;Stimulus_8;Correct_sequence;Voice_announcement;Capture\n"
            + "Practice_1;practice1a;practice1aw;practice1a1;practice1a2;;;;;;;;p01;practice 01\n"
            + "Practice_2;practice2a;practice2aw;practice2a1;practice2a2;practice2a3;;;;;;;p02;practice 02\n"
            + "Block_3_1;SF;SFW1;SFa1;SFa2;SFa3;;;;;;MDB;trial_01;trial 01\n"
            + "Block_3_2;SF;SFW2;SFb1;SFb2;SFb3;;;;;;NCY;trial_02;trial 02\n"
            + "Block_3_3;SF;SFW3;SFc1;SFc2;SFc3;;;;;;FQK;trial_03;trial 03\n"
            + "Block_3_4;SF;SFW4;SFd1;SFd2;SFd3;;;;;;CQJ;trial_04;trial 04\n"
            + "Block_3_5;SF;SFW5;SFe1;SFe2;SFe3;;;;;;TLS;trial_05;trial 05\n"
            + "Block_4_1;SE1;SEW1;SEa1;SEa2;SEa3;SEa4;;;;;YTNK;trial_06;trial 06\n"
            + "Block_4_2;SE1;SEW2;SEb1;SEb2;SEb3;SEb4;;;;;YRTB;trial_07;trial 07\n"
            + "Block_4_3;SE1;SEW3;SEc1;SEc2;SEc3;SEc4;;;;;RNTK;trial_08;trial 08\n"
            + "Block_4_4;SE2;SEW4;SEd1;SEd2;SEd3;SEd4;;;;;YKXR;trial_09;trial 09\n"
            + "Block_4_5;SE2;SEW5;SEe1;SEe2;SEe3;SEe4;;;;;YFQB;trial_10;trial 10\n"
            + "Block_5_1;SD1;SDW1;SDa1;SDa2;SDa3;SDa4;SDa5;;;;FJYQR;trial_11;trial 11\n"
            + "Block_5_2;SD1;SDW2;SDb1;SDb2;SDb3;SDb4;SDb5;;;;BRJZL;trial_12;trial 12\n"
            + "Block_5_3;SD1;SDW3;SDc1;SDc2;SDc3;SDc4;SDc5;;;;FYLBH;trial_13;trial 13\n"
            + "Block_5_4;SD2;SDW4;SDd1;SDd2;SDd3;SDd4;SDd5;;;;FYQZH;trial_14;trial 14\n"
            + "Block_5_5;SD2;SDW5;SDe1;SDe2;SDe3;SDe4;SDe5;;;;ZRQYH;trial_15;trial 15\n"
            + "Block_6_1;SC1;SCW1;SCa1;SCa2;SCa3;SCa4;SCa5;SCa6;;;DWNKYC;trial_16;trial 16\n"
            + "Block_6_2;SC1;SCW2;SCb1;SCb2;SCb3;SCb4;SCb5;SCb6;;;NHYWKD;trial_17;trial 17\n"
            + "Block_6_3;SC1;SCW3;SCc1;SCc2;SCc3;SCc4;SCc5;SCc6;;;WJQDHY;trial_18;trial 18\n"
            + "Block_6_4;SC2;SCW4;SCd1;SCd2;SCd3;SCd4;SCd5;SCd6;;;NKWCQJ;trial_19;trial 19\n"
            + "Block_6_5;SC2;SCW5;SCe1;SCe2;SCe3;SCe4;SCe5;SCe6;;;JWNHDK;trial_20;trial 20\n"
            + "Block_7_1;SB1;SBW1;SBa1;SBa2;SBa3;SBa4;SBa5;SBa6;SBa7;;XJYKRZV;trial_21;trial 21\n"
            + "Block_7_2;SB1;SBW2;SBb1;SBb2;SBb3;SBb4;SBb5;SBb6;SBb7;;KDXZYVJ;trial_22;trial 22\n"
            + "Block_7_3;SB1;SBW3;SBc1;SBc2;SBc3;SBc4;SBc5;SBc6;SBc7;;VDKRZJY;trial_23;trial 23\n"
            + "Block_7_4;SB2;SBW4;SBd1;SBd2;SBd3;SBd4;SBd5;SBd6;SBd7;;RXKHYJV;trial_24;trial 24\n"
            + "Block_7_5;SB2;SBW5;SBe1;SBe2;SBe3;SBe4;SBe5;SBe6;SBe7;;KJVHRXY;trial_25;trial 25\n"
            + "Block_8_1;SA1;SAW1;SAa1;SAa2;SAa3;SAa4;SAa5;SAa6;SAa7;SAa8;ZWRHNQBX;trial_26;trial 26\n"
            + "Block_8_2;SA1;SAW2;SAb1;SAb2;SAb3;SAb4;SAb5;SAb6;SAb7;SAb8;WNYBQRZX;trial_27;trial 27\n"
            + "Block_8_3;SA1;SAW3;SAc1;SAc2;SAc3;SAc4;SAc5;SAc6;SAc7;SAc8;ZHQXNBRW;trial_28;trial 28\n"
            + "Block_8_4;SA2;SAW4;SAd1;SAd2;SAd3;SAd4;SAd5;SAd6;SAd7;SAd8;NXZBQYHR;trial_29;trial 29\n"
            + "Block_8_5;SA2;SAW5;SAe1;SAe2;SAe3;SAe4;SAe5;SAe6;SAe7;SAe8;BWYZXNHQ;trial_30;trial 30";

}
