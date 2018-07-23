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

import java.util.ArrayList;
import java.util.Arrays;
import nl.mpi.tg.eg.experimentdesigner.controller.WizardController;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAboutScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAgreementScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAudioTestScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardCompletionScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardEditUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardMenuScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardScoreThresholdScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardTextScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardVideoAudioOptionStimulusScreen;

/**
 * @since Oct 21, 2016 2:08:51 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class ManipulatedContours {
// two groups of participants: butons left to right and buttons right to left
    // time taken popup or other pester action to tell the participant to speed up
    // two sub groups: one gets part a (context) and part b(final, items), the other gets only part b
    // @todo: progress bar like indicator of the sound playback position 
    // buttons appear as soon as the sound ends

    private final WizardController wizardController = new WizardController();

    protected String getExperimentName() {
        return "french_audio";
    }

    final String agreementScreenText = "Merci beaucoup pour votre intérêt pour cette expérience scientifique en ligne! Les instructions détaillées pour la tâche seront données à la page suivante.<br/>"
            + "<br/>"
            + "Avant de commencer, vous devez d'abord confirmer que vous êtes d'accord pour participer à cette expérience. Veuillez noter que nous enregistrerons vos réponses pour des analyses scientifiques ultérieures. Nous utiliserons les résultats uniquement pour des buts scientifiques et nous allons les décrire dans des journaux scientifiques ou peut-être dans des quotidiens ou sur notre site web. Cependant, nous NE RAPORTERONS JAMAIS les résultats d'une manière qui permettrait de vous identifier. <br/>"
            + "<br/>"
            + "Vous êtes libre d'interrompre l'expérience à tout moment sans aucune explication. En outre, vous pourrez supprimer vos données ultérieurement sans avoir à fournir aucune explication également.<br/>"
            + "<br/>"
            + "Il n'y a aucun risque connu à participer à cette expérience. <br/>"
            + "<br/>"
            + "Si vous êtes d'accord pour participer à cette expérience, cliquez sur 'Je suis d'accord'. Si vous décidez de ne pas participer à cette expérience, abandonnez l'expérience en allant sur un autre site web ou en quittant le site web.<br/>"
            + "<br/>";
    final String informationScreenText1 = "Cette expérience en ligne est une expérience audio. Pour cette raison, nous vous demandons de tester les réglages sonores de votre ordinateur en cliquant sur le gros bouton ci-dessous.<br/>"
            + "<br/>"
            + "<b>Vous n'entendez aucun son?</b> Dans ce cas, il y a quelque chose qui ne va pas avec les réglages sonores de votre ordinateur. Personnalisez les réglages et essayez de nouveau.<br/>"
            + "<br/>"
            + "<b>Entendez-vous un son?</b> Dans ce cas, vos réglages sonores sont bons. Ajustez le volume de l'ordinateur à un niveau confortable.<br/>"
            + "<br/>"
            + "----------------------------------------------------------------<br/>"
            + "ATTENTION: Faites UNIQUEMENT cette expérience si vous vous trouvez dans un environment calme sans aucun bruit de fond. C'est très important!<br/>"
            + "----------------------------------------------------------------<br/>"
            + "<br/>"
            + "<br/>"
            + "<br/>"
            + "[Cliquez sur SUIVANT si vos réglages sonores sont bons...]";

    protected String informationScreenText2() {
        return "Cette expérience en ligne est une expérience audio. Vous entendrez à plusieurs reprises un extrait de conversation naturelle suivi d'un silence. Votre tâche est d'indiquer si le locuteur qui parle avant le silence va continuer ou arrêter de parler après le silence.<br/>"
                + "<br/>"
                + "Par example:<br/>"
                + "Vous entendez l'extrait puis deux boutons apparaissent sur l'écran:<br/>"
                + "ils sont marqués “continue” et “arrête”.<br/>"
                + "Votre tâche est alors de cliquer sur le bouton à droite ou à gauche, suivant votre décision.<br/>"
                + "<br/>"
                + "Il y a environ 80 extraits dans cette expérience. Une session dure environ 20 minutes. Votre progrès est indiqué en haut de chaque écran.<br/>"
                + "<br/>"
                + "Attention: vous NE POUVEZ PAS mettre en pause, interrompre, ou reprendre l'expérience. Faites UNIQUEMENT cette expérience si vous avez vraiment le temps de la faire en entier. Faites l'expérience entièrement et sérieusement.<br/>"
                + "<br/>"
                + "Si l'expérience est claire et que vous êtes prêt(e) à commencer, cliquez sur SUIVANT.<br/>"
                + "L'expérience commencera IMMEDIATEMENT";
    }

    final String completionScreenText1 = "L'expérience est terminée.<br/>"
            + "<br/>"
            + "Voici votre code d'expérience. Vos devez le mettre sur Crowdflower afin d'être payé.<br/>"
            + "<br/>"
            + "Merci beaucoup pour votre participation!";

    protected int repeatCount() {
        return 1;
    }

    protected String[] getStimuliOut1Ver1ConArr() {
        return new String[]{
            "out1ver1conarr:03-12-07_1_p1_M19L_i_178668.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_i_378137.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01R_r_374964.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_i_1458920.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_r_943288.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19R_i_54470_original.wav:continue,arrête",
            "out1ver1conarr:05-12-07_1_p1_M23R_i_359359_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_f_208696_original.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_f_608165_original.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01R_r_483828_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21R_f_164284_original.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_i_254252_short_rise_manip.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19L_f_929888_original.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_f_1377242_original.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_f_358114_original.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02R_i_530691_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_i_1450339_short_rise_manip.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_f_1397300_original.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19L_f_226523_original.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02R_f_660313_original.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01R_r_369646_original.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02L_r_782925_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01L_r_325139_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:20-11-07_1_p1_F05R_i_315485_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01L_r_50507_long_original.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_f_1398960_original.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07R_i_1198900_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02R_f_831537_original.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_i_11468_long_rise_manip.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19L_i_146083_original.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01R_i_219580_short_rise_manip.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19R_r_416402_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_i_255747_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02L_i_22560_original.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02R_i_542926_long_rise_manip.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02L_r_343273_short_rise_manip.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19R_r_518943_long_original.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07R_f_1086927_original.wav:continue,arrête",
            "out1ver1conarr:20-11-07_1_p1_F05R_f_619771_original.wav:continue,arrête",
            "out1ver1conarr:20-11-07_1_p1_F05L_i_106723_short_rise_manip.wav:continue,arrête",
            "out1ver1conarr:04-12-07_1_p1_M20R_i_129603_short_rise_manip.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19L_i_779215_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01R_f_222400_original.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02L_i_75209_short_rise_manip.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19R_f_340735_original.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01R_r_242530_original.wav:continue,arrête",
            "out1ver1conarr:04-12-07_1_p1_M20R_i_146255_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:22-11-07_1_p1_F06R_f_1696027_original.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02R_i_662936_original.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_i_280359_short_rise_manip.wav:continue,arrête",
            "out1ver1conarr:20-11-07_1_p1_F05R_f_624120_original.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01R_r_160211_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19R_r_529986_long_imp_manip.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19L_r_838332_short_rise_manip.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02R_f_669651_original.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01L_r_23773_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01R_r_394807_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_f_888933_original.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_f_427727_original.wav:continue,arrête",
            "out1ver1conarr:05-12-07_1_p1_M23L_i_259230_short_rise_manip.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19R_i_353600_short_rise_manip.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21R_i_770591_short_rise_manip.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01R_r_397825_original.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_i_803331_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19R_i_60085_short_rise_manip.wav:continue,arrête",
            "out1ver1conarr:04-12-07_1_p1_M20R_r_226308_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19R_r_374050_original.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_i_139907_short_imp_manip.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02R_f_681124_original.wav:continue,arrête",
            "out1ver1conarr:22-11-07_1_p1_F06R_f_1430740_original.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_f_431951_original.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_r_380821_original.wav:continue,arrête"
        };
    }

    protected String[] getStimuliOut1Ver2ConArr() {
        return new String[]{"out1ver2conarr:03-12-07_1_p1_M19L_i_178668.wav:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_i_378137.wav:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01R_r_374964.wav:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_i_1458920.wav:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_r_943288.wav:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19R_i_54470_short_rise_manip.wav:continue,arrête",
            "out1ver2conarr:05-12-07_1_p1_M23R_i_359359_original.wav:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_f_208696_original.wav:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_f_608165_original.wav:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01R_r_483828_short_rise_manip.wav:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21R_f_164284_original.wav:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_i_254252_short_imp_manip.wav:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19L_f_929888_original.wav:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_f_1377242_original.wav:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_f_358114_original.wav:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02R_i_530691_original.wav:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_i_1450339_short_imp_manip.wav:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_f_1397300_original.wav:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19L_f_226523_original.wav:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02R_f_660313_original.wav:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01R_r_369646_short_imp_manip.wav:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02L_r_782925_short_rise_manip.wav:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01L_r_325139_short_rise_manip.wav:continue,arrête",
            "out1ver2conarr:20-11-07_1_p1_F05R_i_315485_original.wav:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01L_r_50507_long_imp_manip.wav:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_f_1398960_original.wav:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07R_i_1198900_original.wav:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02R_f_831537_original.wav:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_i_11468_long_imp_manip.wav:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19L_i_146083_short_rise_manip.wav:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01R_i_219580_short_imp_manip.wav:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19R_r_416402_short_rise_manip.wav:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_i_255747_original.wav:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02L_i_22560_short_rise_manip.wav:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02R_i_542926_long_imp_manip.wav:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02L_r_343273_original.wav:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19R_r_518943_long_imp_manip.wav:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07R_f_1086927_original.wav:continue,arrête",
            "out1ver2conarr:20-11-07_1_p1_F05R_f_619771_original.wav:continue,arrête",
            "out1ver2conarr:20-11-07_1_p1_F05L_i_106723_short_imp_manip.wav:continue,arrête",
            "out1ver2conarr:04-12-07_1_p1_M20R_i_129603_short_imp_manip.wav:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19L_i_779215_original.wav:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01R_f_222400_original.wav:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02L_i_75209_short_imp_manip.wav:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19R_f_340735_original.wav:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01R_r_242530_short_imp_manip.wav:continue,arrête",
            "out1ver2conarr:04-12-07_1_p1_M20R_i_146255_original.wav:continue,arrête",
            "out1ver2conarr:22-11-07_1_p1_F06R_f_1696027_original.wav:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02R_i_662936_short_rise_manip.wav:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_i_280359_short_imp_manip.wav:continue,arrête",
            "out1ver2conarr:20-11-07_1_p1_F05R_f_624120_original.wav:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01R_r_160211_short_rise_manip.wav:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19R_r_529986_long_rise_manip.wav:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19L_r_838332_original.wav:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02R_f_669651_original.wav:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01L_r_23773_short_rise_manip.wav:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01R_r_394807_short_rise_manip.wav:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_f_888933_original.wav:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_f_427727_original.wav:continue,arrête",
            "out1ver2conarr:05-12-07_1_p1_M23L_i_259230_short_imp_manip.wav:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19R_i_353600_short_imp_manip.wav:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21R_i_770591_short_imp_manip.wav:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01R_r_397825_short_imp_manip.wav:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_i_803331_original.wav:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19R_i_60085_short_imp_manip.wav:continue,arrête",
            "out1ver2conarr:04-12-07_1_p1_M20R_r_226308_short_rise_manip.wav:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19R_r_374050_short_imp_manip.wav:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_i_139907_original.wav:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02R_f_681124_original.wav:continue,arrête",
            "out1ver2conarr:22-11-07_1_p1_F06R_f_1430740_original.wav:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_f_431951_original.wav:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_r_380821_short_imp_manip.wav:continue,arrête"};
    }

    protected String[] getStimuliOut1Ver3ConArr() {
        return new String[]{
            "out1ver3conarr:03-12-07_1_p1_M19L_i_178668.wav:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_i_378137.wav:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01R_r_374964.wav:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_i_1458920.wav:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_r_943288.wav:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19R_i_54470_short_imp_manip.wav:continue,arrête",
            "out1ver3conarr:05-12-07_1_p1_M23R_i_359359_short_rise_manip.wav:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_f_208696_original.wav:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_f_608165_original.wav:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01R_r_483828_original.wav:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21R_f_164284_original.wav:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_i_254252_original.wav:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19L_f_929888_original.wav:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_f_1377242_original.wav:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_f_358114_original.wav:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02R_i_530691_short_rise_manip.wav:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_i_1450339_original.wav:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_f_1397300_original.wav:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19L_f_226523_original.wav:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02R_f_660313_original.wav:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01R_r_369646_short_rise_manip.wav:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02L_r_782925_original.wav:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01L_r_325139_original.wav:continue,arrête",
            "out1ver3conarr:20-11-07_1_p1_F05R_i_315485_short_rise_manip.wav:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01L_r_50507_long_rise_manip.wav:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_f_1398960_original.wav:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07R_i_1198900_short_rise_manip.wav:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02R_f_831537_original.wav:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_i_11468_long_original.wav:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19L_i_146083_short_imp_manip.wav:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01R_i_219580_original.wav:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19R_r_416402_original.wav:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_i_255747_short_rise_manip.wav:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02L_i_22560_short_imp_manip.wav:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02R_i_542926_long_original.wav:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02L_r_343273_short_imp_manip.wav:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19R_r_518943_long_rise_manip.wav:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07R_f_1086927_original.wav:continue,arrête",
            "out1ver3conarr:20-11-07_1_p1_F05R_f_619771_original.wav:continue,arrête",
            "out1ver3conarr:20-11-07_1_p1_F05L_i_106723_original.wav:continue,arrête",
            "out1ver3conarr:04-12-07_1_p1_M20R_i_129603_original.wav:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19L_i_779215_short_rise_manip.wav:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01R_f_222400_original.wav:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02L_i_75209_original.wav:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19R_f_340735_original.wav:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01R_r_242530_short_rise_manip.wav:continue,arrête",
            "out1ver3conarr:04-12-07_1_p1_M20R_i_146255_short_rise_manip.wav:continue,arrête",
            "out1ver3conarr:22-11-07_1_p1_F06R_f_1696027_original.wav:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02R_i_662936_short_imp_manip.wav:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_i_280359_original.wav:continue,arrête",
            "out1ver3conarr:20-11-07_1_p1_F05R_f_624120_original.wav:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01R_r_160211_original.wav:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19R_r_529986_long_original.wav:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19L_r_838332_short_imp_manip.wav:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02R_f_669651_original.wav:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01L_r_23773_original.wav:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01R_r_394807_original.wav:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_f_888933_original.wav:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_f_427727_original.wav:continue,arrête",
            "out1ver3conarr:05-12-07_1_p1_M23L_i_259230_original.wav:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19R_i_353600_original.wav:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21R_i_770591_original.wav:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01R_r_397825_short_rise_manip.wav:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_i_803331_short_rise_manip.wav:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19R_i_60085_original.wav:continue,arrête",
            "out1ver3conarr:04-12-07_1_p1_M20R_r_226308_original.wav:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19R_r_374050_short_rise_manip.wav:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_i_139907_short_rise_manip.wav:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02R_f_681124_original.wav:continue,arrête",
            "out1ver3conarr:22-11-07_1_p1_F06R_f_1430740_original.wav:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_f_431951_original.wav:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_r_380821_short_rise_manip.wav:continue,arrête"};
    }

    protected String[] getStimuliOut2Ver1ConArr() {
        return new String[]{"out2ver1conarr:03-12-07_1_p1_M19L_i_178668.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_i_378137.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01R_r_374964.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_i_1458920.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_r_943288.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_f_427727_original.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01L_r_50507_long_original.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01R_i_219580_short_rise_manip.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_i_11468_long_rise_manip.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02R_i_530691_short_imp_manip.wav:continue,arrête",
            "out2ver1conarr:20-11-07_1_p1_F05R_f_619771_original.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_r_380821_original.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19L_f_226523_original.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_f_431951_original.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02L_i_75209_short_rise_manip.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02L_i_22560_short_imp_manip.wav:continue,arrête",
            "out2ver1conarr:04-12-07_1_p1_M20R_r_226308_short_imp_manip.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_i_254252_short_rise_manip.wav:continue,arrête",
            "out2ver1conarr:20-11-07_1_p1_F05R_i_315485_original.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_f_888933_original.wav:continue,arrête",
            "out2ver1conarr:05-12-07_1_p1_M23L_i_259230_short_rise_manip.wav:continue,arrête",
            "out2ver1conarr:04-12-07_1_p1_M20R_i_129603_short_rise_manip.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01R_r_483828_short_imp_manip.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01L_r_23773_short_imp_manip.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19R_r_416402_short_imp_manip.wav:continue,arrête",
            "out2ver1conarr:04-12-07_1_p1_M20R_i_146255_short_imp_manip.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01R_r_160211_short_imp_manip.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19R_i_54470_original.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_i_139907_short_imp_manip.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02R_f_660313_original.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_f_208696_original.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21R_i_770591_short_rise_manip.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19R_f_340735_original.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19L_r_838332_original.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19R_i_60085_short_rise_manip.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_i_803331_short_imp_manip.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01R_r_369646_original.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19L_i_779215_short_imp_manip.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01R_f_222400_original.wav:continue,arrête",
            "out2ver1conarr:20-11-07_1_p1_F05L_i_106723_short_rise_manip.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01R_r_394807_short_imp_manip.wav:continue,arrête",
            "out2ver1conarr:22-11-07_1_p1_F06R_f_1430740_original.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_f_1377242_original.wav:continue,arrête",
            "out2ver1conarr:05-12-07_1_p1_M23R_i_359359_original.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19L_f_929888_original.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01R_r_242530_short_rise_manip.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02R_i_662936_original.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07R_f_1086927_original.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19R_i_353600_short_rise_manip.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_f_608165_original.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_i_255747_short_imp_manip.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07R_i_1198900_original.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02R_i_542926_long_rise_manip.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02R_f_831537_original.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_i_280359_short_rise_manip.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19R_r_518943_long_rise_manip.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_i_1450339_short_rise_manip.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_f_1397300_original.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02L_r_782925_short_imp_manip.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19R_r_529986_long_imp_manip.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01L_r_325139_short_imp_manip.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21R_f_164284_original.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_f_1398960_original.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19R_r_374050_original.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19L_i_146083_short_imp_manip.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02R_f_669651_original.wav:continue,arrête",
            "out2ver1conarr:22-11-07_1_p1_F06R_f_1696027_original.wav:continue,arrête",
            "out2ver1conarr:20-11-07_1_p1_F05R_f_624120_original.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02L_r_343273_original.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_f_358114_original.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01R_r_397825_short_rise_manip.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02R_f_681124_original.wav:continue,arrête"};
    }

    protected String[] getStimuliOut2Ver2ConArr() {
        return new String[]{"out2ver2conarr:03-12-07_1_p1_M19L_i_178668.wav:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_i_378137.wav:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01R_r_374964.wav:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_i_1458920.wav:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_r_943288.wav:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_f_427727_original.wav:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01L_r_50507_long_imp_manip.wav:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01R_i_219580_short_imp_manip.wav:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_i_11468_long_imp_manip.wav:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02R_i_530691_original.wav:continue,arrête",
            "out2ver2conarr:20-11-07_1_p1_F05R_f_619771_original.wav:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_r_380821_short_imp_manip.wav:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19L_f_226523_original.wav:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_f_431951_original.wav:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02L_i_75209_short_imp_manip.wav:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02L_i_22560_original.wav:continue,arrête",
            "out2ver2conarr:04-12-07_1_p1_M20R_r_226308_short_rise_manip.wav:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_i_254252_short_imp_manip.wav:continue,arrête",
            "out2ver2conarr:20-11-07_1_p1_F05R_i_315485_short_rise_manip.wav:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_f_888933_original.wav:continue,arrête",
            "out2ver2conarr:05-12-07_1_p1_M23L_i_259230_short_imp_manip.wav:continue,arrête",
            "out2ver2conarr:04-12-07_1_p1_M20R_i_129603_short_imp_manip.wav:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01R_r_483828_short_rise_manip.wav:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01L_r_23773_short_rise_manip.wav:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19R_r_416402_short_rise_manip.wav:continue,arrête",
            "out2ver2conarr:04-12-07_1_p1_M20R_i_146255_original.wav:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01R_r_160211_short_rise_manip.wav:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19R_i_54470_short_rise_manip.wav:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_i_139907_original.wav:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02R_f_660313_original.wav:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_f_208696_original.wav:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21R_i_770591_short_imp_manip.wav:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19R_f_340735_original.wav:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19L_r_838332_short_imp_manip.wav:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19R_i_60085_short_imp_manip.wav:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_i_803331_original.wav:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01R_r_369646_short_imp_manip.wav:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19L_i_779215_original.wav:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01R_f_222400_original.wav:continue,arrête",
            "out2ver2conarr:20-11-07_1_p1_F05L_i_106723_short_imp_manip.wav:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01R_r_394807_short_rise_manip.wav:continue,arrête",
            "out2ver2conarr:22-11-07_1_p1_F06R_f_1430740_original.wav:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_f_1377242_original.wav:continue,arrête",
            "out2ver2conarr:05-12-07_1_p1_M23R_i_359359_short_rise_manip.wav:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19L_f_929888_original.wav:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01R_r_242530_original.wav:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02R_i_662936_short_rise_manip.wav:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07R_f_1086927_original.wav:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19R_i_353600_short_imp_manip.wav:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_f_608165_original.wav:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_i_255747_original.wav:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07R_i_1198900_short_rise_manip.wav:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02R_i_542926_long_imp_manip.wav:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02R_f_831537_original.wav:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_i_280359_short_imp_manip.wav:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19R_r_518943_long_original.wav:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_i_1450339_short_imp_manip.wav:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_f_1397300_original.wav:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02L_r_782925_short_rise_manip.wav:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19R_r_529986_long_rise_manip.wav:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01L_r_325139_short_rise_manip.wav:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21R_f_164284_original.wav:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_f_1398960_original.wav:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19R_r_374050_short_imp_manip.wav:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19L_i_146083_original.wav:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02R_f_669651_original.wav:continue,arrête",
            "out2ver2conarr:22-11-07_1_p1_F06R_f_1696027_original.wav:continue,arrête",
            "out2ver2conarr:20-11-07_1_p1_F05R_f_624120_original.wav:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02L_r_343273_short_imp_manip.wav:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_f_358114_original.wav:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01R_r_397825_original.wav:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02R_f_681124_original.wav:continue,arrête"};
    }

    protected String[] getStimuliOut2Ver3ConArr() {
        return new String[]{"out2ver3conarr:03-12-07_1_p1_M19L_i_178668.wav:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_i_378137.wav:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01R_r_374964.wav:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_i_1458920.wav:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_r_943288.wav:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_f_427727_original.wav:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01L_r_50507_long_rise_manip.wav:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01R_i_219580_original.wav:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_i_11468_long_original.wav:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02R_i_530691_short_rise_manip.wav:continue,arrête",
            "out2ver3conarr:20-11-07_1_p1_F05R_f_619771_original.wav:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_r_380821_short_rise_manip.wav:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19L_f_226523_original.wav:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_f_431951_original.wav:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02L_i_75209_original.wav:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02L_i_22560_short_rise_manip.wav:continue,arrête",
            "out2ver3conarr:04-12-07_1_p1_M20R_r_226308_original.wav:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_i_254252_original.wav:continue,arrête",
            "out2ver3conarr:20-11-07_1_p1_F05R_i_315485_short_imp_manip.wav:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_f_888933_original.wav:continue,arrête",
            "out2ver3conarr:05-12-07_1_p1_M23L_i_259230_original.wav:continue,arrête",
            "out2ver3conarr:04-12-07_1_p1_M20R_i_129603_original.wav:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01R_r_483828_original.wav:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01L_r_23773_original.wav:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19R_r_416402_original.wav:continue,arrête",
            "out2ver3conarr:04-12-07_1_p1_M20R_i_146255_short_rise_manip.wav:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01R_r_160211_original.wav:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19R_i_54470_short_imp_manip.wav:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_i_139907_short_rise_manip.wav:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02R_f_660313_original.wav:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_f_208696_original.wav:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21R_i_770591_original.wav:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19R_f_340735_original.wav:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19L_r_838332_short_rise_manip.wav:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19R_i_60085_original.wav:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_i_803331_short_rise_manip.wav:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01R_r_369646_short_rise_manip.wav:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19L_i_779215_short_rise_manip.wav:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01R_f_222400_original.wav:continue,arrête",
            "out2ver3conarr:20-11-07_1_p1_F05L_i_106723_original.wav:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01R_r_394807_original.wav:continue,arrête",
            "out2ver3conarr:22-11-07_1_p1_F06R_f_1430740_original.wav:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_f_1377242_original.wav:continue,arrête",
            "out2ver3conarr:05-12-07_1_p1_M23R_i_359359_short_imp_manip.wav:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19L_f_929888_original.wav:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01R_r_242530_short_imp_manip.wav:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02R_i_662936_short_imp_manip.wav:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07R_f_1086927_original.wav:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19R_i_353600_original.wav:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_f_608165_original.wav:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_i_255747_short_rise_manip.wav:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07R_i_1198900_short_imp_manip.wav:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02R_i_542926_long_original.wav:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02R_f_831537_original.wav:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_i_280359_original.wav:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19R_r_518943_long_imp_manip.wav:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_i_1450339_original.wav:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_f_1397300_original.wav:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02L_r_782925_original.wav:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19R_r_529986_long_original.wav:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01L_r_325139_original.wav:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21R_f_164284_original.wav:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_f_1398960_original.wav:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19R_r_374050_short_rise_manip.wav:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19L_i_146083_short_rise_manip.wav:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02R_f_669651_original.wav:continue,arrête",
            "out2ver3conarr:22-11-07_1_p1_F06R_f_1696027_original.wav:continue,arrête",
            "out2ver3conarr:20-11-07_1_p1_F05R_f_624120_original.wav:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02L_r_343273_short_rise_manip.wav:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_f_358114_original.wav:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01R_r_397825_short_imp_manip.wav:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02R_f_681124_original.wav:continue,arrête"};
    }

    private String[] invertButtons(String[] stimuliArray) {
        for (int currentIndex = 0; currentIndex < stimuliArray.length; currentIndex++) {
            stimuliArray[currentIndex] = stimuliArray[currentIndex].replace("conarr", "arrcon");
            stimuliArray[currentIndex] = stimuliArray[currentIndex].replace(":continue,arrête", ":arrête,continue");
        }
        return stimuliArray;
    }

    private String[] addCodePart(String groupTag, String[] stimuliArray) {
        for (int currentIndex = 0; currentIndex < stimuliArray.length; currentIndex++) {
            final String[] lineParts = stimuliArray[currentIndex].split(":");
            String codePart = lineParts[1].replaceAll("[^0-9]*$", "");
            stimuliArray[currentIndex] = lineParts[0] + "/" + groupTag + ":" + codePart + "_context" + ":" + lineParts[1] + ":" + lineParts[2];
        }
        return stimuliArray;
    }

    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName(getExperimentName());
        wizardData.setShowMenuBar(false);
        wizardData.setTextFontSize(17);
        wizardData.setObfuscateScreenNames(false);
        WizardTextScreen wizardTextScreen2 = new WizardTextScreen("InformationScreen2", informationScreenText2(),
                "volgende [ spatiebalk ]"
        );
        WizardAudioTestScreen wizardTextScreen1 = new WizardAudioTestScreen("AudioTest", informationScreenText1, "suivant [ barre d'espacement ]", "bienvenue");
        //Information screen 
        //Agreement
        WizardAgreementScreen agreementScreen = new WizardAgreementScreen("Accord", agreementScreenText, "Je suis d'accord");
//        wizardData.setAgreementText("agreementText");
//        wizardData.setDisagreementScreenText("disagreementScreenText");
        //metadata
        final WizardEditUserScreen wizardEditUserScreen = new WizardEditUserScreen();
        wizardEditUserScreen.setScreenTitle("Edit User");
        wizardEditUserScreen.setMenuLabel("Edit User");
        wizardEditUserScreen.setScreenTag("Edit_User");
        wizardEditUserScreen.setNextButton("Suivant");
        wizardEditUserScreen.setScreenText("Entrez votre code identifiant ici:");
        wizardEditUserScreen.setSendData(true);
        wizardEditUserScreen.setOn_Error_Text("Impossible de contacter le serveur, vérifiez votre connexion Internet s'il vous plaît.");
        wizardEditUserScreen.setCustomFields(new String[]{
            "workerId:code identifiant:.'{'3,'}':Entrez au moins trois lettres."
//            "firstName:Prénom:.'{'3,'}':Entrez au moins trois lettres.",
//            "lastName:Nom de famille:.'{'3,'}':Entrez au moins trois lettres.",
//            "age:Âge:[0-9]+:Entrez un nombre.",
//            "gender:Sexe:|homme|femme|autre:."
        });

        wizardData.addScreen(wizardEditUserScreen);
        wizardData.addScreen(agreementScreen);
        wizardData.addScreen(wizardTextScreen1);
        wizardData.addScreen(wizardTextScreen2);
        final String[] pretestScreenStimuli = new String[]{
            // (Q1-Q5), whereas Q6 can only be answered with "h" or "H"
            "Q1:Q1.wav:",
            "Q2:Q2.wav:",
            "Q3:Q3.wav:",
            "Q4:Q4.wav:",
            "Q5:Q5.wav:",
            "Q6:Q6.wav::[hH]", //            "Q6:Q6.wav:test correct response,test incorrect response:test correct response"
            "Q7:Q7.wav::.*[Cc][Rr][Aa][Yy][Oo][Nn]"
        };
        final WizardVideoAudioOptionStimulusScreen pretestScreen = new WizardVideoAudioOptionStimulusScreen("Pretest Screen", false, pretestScreenStimuli, false, false, null, 1000, 1, 20, false, 100, "", "", true);
        pretestScreen.setAllowFreeText(true, "Next [TAB + ENTER]", ".{1,}", "Entrez au moins trois lettres.", null, "ENTER"
                + "");
        pretestScreen.setShowProgress(true);
        pretestScreen.setShowHurryIndicator(false);
        pretestScreen.setRepeatIncorrect(false);
        WizardScoreThresholdScreen scoreThresholdScreen = new WizardScoreThresholdScreen("Si votre réponse n'est pas correcte, vous ne pourrez pas poursuivre l'expérience", 2, "ScoreThreshold", pretestScreen, "Retry");
        final ArrayList<String> stimuliList = new ArrayList();
        stimuliList.addAll(Arrays.asList(addCodePart("out1ver1Screen", getStimuliOut1Ver1ConArr())));
        stimuliList.addAll(Arrays.asList(addCodePart("out1ver2Screen", getStimuliOut1Ver2ConArr())));
        stimuliList.addAll(Arrays.asList(addCodePart("out1ver3Screen", getStimuliOut1Ver3ConArr())));
        stimuliList.addAll(Arrays.asList(addCodePart("out2ver1Screen", getStimuliOut2Ver1ConArr())));
        stimuliList.addAll(Arrays.asList(addCodePart("out2ver2Screen", getStimuliOut2Ver2ConArr())));
        stimuliList.addAll(Arrays.asList(addCodePart("out2ver3Screen", getStimuliOut2Ver3ConArr())));
        stimuliList.addAll(Arrays.asList(invertButtons(addCodePart("out1ver1ScreenI", getStimuliOut1Ver1ConArr()))));
        stimuliList.addAll(Arrays.asList(invertButtons(addCodePart("out1ver2ScreenI", getStimuliOut1Ver2ConArr()))));
        stimuliList.addAll(Arrays.asList(invertButtons(addCodePart("out1ver3ScreenI", getStimuliOut1Ver3ConArr()))));
        stimuliList.addAll(Arrays.asList(invertButtons(addCodePart("out2ver1ScreenI", getStimuliOut2Ver1ConArr()))));
        stimuliList.addAll(Arrays.asList(invertButtons(addCodePart("out2ver2ScreenI", getStimuliOut2Ver2ConArr()))));
        stimuliList.addAll(Arrays.asList(invertButtons(addCodePart("out2ver3ScreenI", getStimuliOut2Ver3ConArr()))));
        String[] randomStimuliTags = new String[]{"out1ver1Screen", "out1ver2Screen", "out1ver3Screen", "out2ver1Screen", "out2ver2Screen", "out2ver3Screen", "out1ver1ScreenI", "out1ver2ScreenI", "out1ver3ScreenI", "out2ver1ScreenI", "out2ver2ScreenI", "out2ver3ScreenI"};

        WizardMenuScreen contextMenu = new WizardMenuScreen("ContextMenu", "ContextMenu", "ContextMenu");
        contextMenu.setBranchOnGetParam(true, "A choice must be provided out of the following:<br/>");
        wizardData.addScreen(contextMenu);

        final WizardVideoAudioOptionStimulusScreen randomStimuliTagsScreenC = new WizardVideoAudioOptionStimulusScreen("stimuliC", false, stimuliList.toArray(new String[]{}), false, true, randomStimuliTags, 1000, repeatCount(), 20, false, 100, "", "", true);
        final WizardVideoAudioOptionStimulusScreen randomStimuliTagsScreenN = new WizardVideoAudioOptionStimulusScreen("stimuliN", false, stimuliList.toArray(new String[]{}), false, false, randomStimuliTags, 1000, repeatCount(), 20, false, 100, "", "", true);
        randomStimuliTagsScreenC.setRandomTagField("stimuli");
        randomStimuliTagsScreenN.setRandomTagField("stimuli");
        randomStimuliTagsScreenC.setShowProgress(true);
        randomStimuliTagsScreenC.setShowHurryIndicator(false);
        randomStimuliTagsScreenN.setShowProgress(true);
        randomStimuliTagsScreenN.setShowHurryIndicator(false);
//        list1234Screen.setStimulusResponseOptions("1,2,3,4,5");
//        list1234Screen.setStimulusResponseLabelLeft("très probable négatif");
//        list1234Screen.setStimulusResponseLabelRight("très probable positif");
        wizardData.addScreen(pretestScreen);
        wizardData.addScreen(scoreThresholdScreen);
        wizardData.addScreen(randomStimuliTagsScreenC);
        wizardData.addScreen(randomStimuliTagsScreenN);
//        wizardData.addScreen(out1ver1Screen);
//        wizardData.addScreen(out1ver2Screen);
//        wizardData.addScreen(out1ver3Screen);
//        wizardData.addScreen(out2ver1Screen);
//        wizardData.addScreen(out2ver2Screen);
//        wizardData.addScreen(out2ver3Screen);
//        wizardData.addScreen(out1ver1ScreenI);
//        wizardData.addScreen(out1ver2ScreenI);
//        wizardData.addScreen(out1ver3ScreenI);
//        wizardData.addScreen(out2ver1ScreenI);
//        wizardData.addScreen(out2ver2ScreenI);
//        wizardData.addScreen(out2ver3ScreenI);

        final WizardEditUserScreen wizardFeedbackScreen = new WizardEditUserScreen();
        wizardFeedbackScreen.setScreenText("D'après vous, quel est le thème/but de cette expérience?");
        wizardFeedbackScreen.setSendData(true);
        wizardFeedbackScreen.setNextButton("Suivant");
        wizardFeedbackScreen.setOn_Error_Text("Impossible de contacter le serveur, vérifiez votre connexion Internet s'il vous plaît.");
        wizardFeedbackScreen.setCustomFields(new String[]{
            "feedBack::['\\\\'S'\\\\'s]'{'3,'}':Entrez au moins trois lettres."
        });
        wizardData.addScreen(wizardFeedbackScreen);

        WizardCompletionScreen completionScreen = new WizardCompletionScreen(completionScreenText1, false, true,
                null, //Si quelqu'un d'autre veut participer à l'expérience sur cet ordinateur, veuillez cliquer sur le bouton ci-dessous.",
                "Redémarrer",
                "Fini",
                "Impossible de contacter le serveur, vérifiez votre connexion Internet s'il vous plaît.", "Réessayer");
        wizardData.addScreen(completionScreen);
        final WizardAboutScreen wizardAboutScreen = new WizardAboutScreen("about", true);
        wizardAboutScreen.setBackWizardScreen(wizardEditUserScreen);
        wizardData.addScreen(wizardAboutScreen);

        wizardEditUserScreen.setNextWizardScreen(agreementScreen);
        agreementScreen.setNextWizardScreen(wizardTextScreen1);
//        WizardMenuScreen menuScreen = new WizardMenuScreen("temporary menu", "temporary menu", "temporary menu");
//        wizardData.addScreen(menuScreen);
        wizardTextScreen1.setNextWizardScreen(pretestScreen);
        pretestScreen.setNextWizardScreen(scoreThresholdScreen);
        scoreThresholdScreen.setNextWizardScreen(wizardTextScreen2);
        wizardTextScreen2.setNextWizardScreen(contextMenu);

        contextMenu.addTargetScreen(randomStimuliTagsScreenC);
        contextMenu.addTargetScreen(randomStimuliTagsScreenN);

//        menuScreen.addTargetScreen(randomStimuliTagsScreen);
//        menuScreen.addTargetScreen(out1ver1Screen);
//        menuScreen.addTargetScreen(out1ver2Screen);
//        menuScreen.addTargetScreen(out1ver3Screen);
//        menuScreen.addTargetScreen(out2ver1Screen);
//        menuScreen.addTargetScreen(out2ver2Screen);
//        menuScreen.addTargetScreen(out2ver3Screen);
//        menuScreen.addTargetScreen(out1ver1ScreenI);
//        menuScreen.addTargetScreen(out1ver2ScreenI);
//        menuScreen.addTargetScreen(out1ver3ScreenI);
//        menuScreen.addTargetScreen(out2ver1ScreenI);
//        menuScreen.addTargetScreen(out2ver2ScreenI);
//        menuScreen.addTargetScreen(out2ver3ScreenI);
        randomStimuliTagsScreenC.setNextWizardScreen(wizardFeedbackScreen);
        randomStimuliTagsScreenN.setNextWizardScreen(wizardFeedbackScreen);
//        out1ver1Screen.setNextWizardScreen(completionScreen);
//        out1ver2Screen.setNextWizardScreen(completionScreen);
//        out1ver3Screen.setNextWizardScreen(completionScreen);
//        out2ver1Screen.setNextWizardScreen(completionScreen);
//        out2ver2Screen.setNextWizardScreen(completionScreen);
//        out2ver3Screen.setNextWizardScreen(completionScreen);
//        out1ver1ScreenI.setNextWizardScreen(completionScreen);
//        out1ver2ScreenI.setNextWizardScreen(completionScreen);
//        out1ver3ScreenI.setNextWizardScreen(completionScreen);
//        out2ver1ScreenI.setNextWizardScreen(completionScreen);
//        out2ver2ScreenI.setNextWizardScreen(completionScreen);
//        out2ver3ScreenI.setNextWizardScreen(completionScreen);
        wizardFeedbackScreen.setNextWizardScreen(completionScreen);
        return wizardData;
    }

    public Experiment getExperiment() {
        return wizardController.getExperiment(getWizardData());
    }

//final RandomGrouping randomGrouping = new RandomGrouping();
//        if (storedWizardScreenData.getStimuliRandomTags() != null) {
//            for (String randomTag : storedWizardScreenData.getStimuliRandomTags()) {
//                randomGrouping.addRandomTag(randomTag);
//            }
//            final String metadataFieldname = "groupAllocation_" + storedWizardScreenData.getScreenTag();
//            randomGrouping.setStorageField(metadataFieldname);
//            loadStimuliFeature.setRandomGrouping(randomGrouping);
//            experiment.getMetadata().add(new Metadata(metadataFieldname, metadataFieldname, ".*", ".", false, null));
//        }
}
