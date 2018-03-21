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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.vocabulary;

import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.advocaspool.AdVocAsStimulusImp;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.advocaspool.Vocabulary;
import nl.mpi.tg.eg.frinex.common.model.Stimulus.Tag;

/**
 *
 * @author olhshk
 */
public class AdVocAsStimuliTest {

    //AdVocAsStimulusImp(String uniqueId, Tag[] tags, String label, String code, int pauseMs, String audioPath, String videoPath, String imagePath, String ratingLabels, String correctResponses, String bandIndex)
    public static final AdVocAsStimulusImp[] POOL = {
        new AdVocAsStimulusImp("reugen_1510324082373", new Tag[1], "reugen", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("pondoes_1510324082373", new Tag[1], "pondoes", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("lagadilistisch_1510324082373", new Tag[1], "lagadilistisch", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("maberet_1510324082373", new Tag[1], "maberet", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("uitgempen_1510324082373", new Tag[1], "uitgempen", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("criturg_1510324082373", new Tag[1], "criturg", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("inbricetie_1510324082373", new Tag[1], "inbricetie", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("stumolen_1510324082373", new Tag[1], "stumolen", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("godderijn_1510324082373", new Tag[1], "godderijn", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("iriel_1510324082373", new Tag[1], "iriel", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("flosperen_1510324082373", new Tag[1], "flosperen", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("pretebentie_1510324082373", new Tag[1], "pretebentie", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("afflist_1510324082373", new Tag[1], "afflist", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("betijring_1510324082373", new Tag[1], "betijring", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("invensiptie_1510324082373", new Tag[1], "invensiptie", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("teramikratie_1510324082373", new Tag[1], "teramikratie", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("kwarzend_1510324082373", new Tag[1], "kwarzend", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("demaans_1510324082373", new Tag[1], "demaans", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("pulstueel_1510324082373", new Tag[1], "pulstueel", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("verroppend_1510324082373", new Tag[1], "verroppend", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("ralm_1510324082373", new Tag[1], "ralm", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("enturmen_1510324082373", new Tag[1], "enturmen", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("teleniaan_1510324082373", new Tag[1], "teleniaan", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("megelijk_1510324082373", new Tag[1], "megelijk", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("intallinaal_1510324082373", new Tag[1], "intallinaal", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("temifrase_1510324082373", new Tag[1], "temifrase", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("puvide_1510324082373", new Tag[1], "puvide", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("relabijn_1510324082374", new Tag[1], "relabijn", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("glippant_1510324082374", new Tag[1], "glippant", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("pijtelen_1510324082374", new Tag[1], "pijtelen", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("vaatstonzel_1510324082374", new Tag[1], "vaatstonzel", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("afwilsen_1510324082374", new Tag[1], "afwilsen", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("berkaaien_1510324082374", new Tag[1], "berkaaien", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("asteraards_1510324082374", new Tag[1], "asteraards", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("karpes_1510324082374", new Tag[1], "karpes", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("bermuiden_1510324082374", new Tag[1], "bermuiden", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("schieluin_1510324082374", new Tag[1], "schieluin", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("ronnotutie_1510324082374", new Tag[1], "ronnotutie", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("pating_1510324082374", new Tag[1], "pating", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("immagaal_1510324082374", new Tag[1], "immagaal", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("oordiet_1510324082374", new Tag[1], "oordiet", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("ralding_1510324082374", new Tag[1], "ralding", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("storf_1510324082374", new Tag[1], "storf", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("ankentement_1510324082374", new Tag[1], "ankentement", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("liprema_1510324082374", new Tag[1], "liprema", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("gavimaal_1510324082374", new Tag[1], "gavimaal", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("mandon_1510324082374", new Tag[1], "mandon", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("mienerlijk_1510324082374", new Tag[1], "mienerlijk", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("tendetorgie_1510324082374", new Tag[1], "tendetorgie", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("mardij_1510324082374", new Tag[1], "mardij", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1"),
        new AdVocAsStimulusImp("lokkel_1510324082374", new Tag[1], "lokkel", "", 0, null, null, null, Vocabulary.NONWORD + ',' + Vocabulary.WORD, Vocabulary.NONWORD, "-1")
    };
}
