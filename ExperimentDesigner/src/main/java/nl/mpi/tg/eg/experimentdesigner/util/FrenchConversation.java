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
public class FrenchConversation {
// two groups of participants: butons left to right and buttons right to left
    // time taken popup or other pester action to tell the participant to speed up
    // two sub groups: one gets part a (context) and part b(final, items), the other gets only part b
    // @todo: progress bar like indicator of the sound playback position 
    // buttons appear as soon as the sound ends

    private final WizardController wizardController = new WizardController();

    protected String getExperimentName() {
        return "french_conversation";
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
        return "Dans cette expérience en ligne, nous vous présenterons à plusieurs reprises un extrait de conversation naturelle suivi d'un silence. Votre tâche est d'indiquer si le locuteur qui parle va continuer ou arrêter de parler après le silence.<br/>"
                + "<br/>"
                + "Par example:<br/>"
                + "Après l’extrait deux boutons apparaissent sur l'écran:<br/>"
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
            "out1ver1conarr:03-12-07_1_p1_M19L_i_727746.wav:continue,arrête",
            "out1ver1conarr:05-12-07_1_p1_M23R_i_104729.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01R_i_219580.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02L_r_279309.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_i_280359.wav:continue,arrête",
            "out1ver1conarr:22-11-07_1_p1_F06R_f_1430740.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19R_r_414259.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_i_254252.wav:continue,arrête",
            "out1ver1conarr:23-11-07_1_p1_F08R_im_321628.wav:continue,arrête",
            "out1ver1conarr:04-12-07_1_p1_M20R_im_333326.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19L_im_131836.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_f_208696.wav:continue,arrête",
            "out1ver1conarr:20-11-07_1_p1_F05R_i_581293.wav:continue,arrête",
            "out1ver1conarr:05-12-07_1_p1_M23L_r_275666.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07R_i_1625043.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02R_f_831537.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07R_f_1086927.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_i_255747.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21R_im_368102.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01R_r_369646.wav:continue,arrête",
            "out1ver1conarr:20-11-07_1_p1_F05R_i_315485.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_i_803331.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02L_l_454858.wav:continue,arrête",
            "out1ver1conarr:04-12-07_1_p1_M20R_im_364784.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_f_1377242.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_f_608165.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02R_i_530691.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01R_r_397825.wav:continue,arrête",
            "out1ver1conarr:04-12-07_1_p1_M20R_r_226308.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_i_1450339.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_n_869460.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02L_r_343273.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19R_r_518943.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07R_i_1300490.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_i_1100294.wav:continue,arrête",
            "out1ver1conarr:20-11-07_1_p1_F05L_i_106723.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07R_i_1198900.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02L_r_782925.wav:continue,arrête",
            "out1ver1conarr:20-11-07_1_p1_F05R_f_624120.wav:continue,arrête",
            "out1ver1conarr:23-11-07_1_p1_F08L_n_333073.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_f_431951.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19R_l_360744.wav:continue,arrête",
            "out1ver1conarr:22-11-07_1_p1_F06L_i_97728.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07R_i_728550.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02L_i_22560.wav:continue,arrête",
            "out1ver1conarr:04-12-07_1_p1_M20L_im_279950.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01R_r_160211.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19R_i_353600.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21L_r_380821.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02R_f_660313.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19L_f_226523.wav:continue,arrête",
            "out1ver1conarr:22-11-07_1_p1_F06R_i_385665.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19R_r_529986.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01L_r_325139.wav:continue,arrête",
            "out1ver1conarr:20-11-07_1_p1_F05R_i_375598.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02R_i_542926.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_i_332023.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19L_i_288514.wav:continue,arrête",
            "out1ver1conarr:22-11-07_1_p1_F06R_r_1249394.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02L_r_119874.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19L_i_178668.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_f_888933.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02R_i_662936.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_f_1397300.wav:continue,arrête",
            "out1ver1conarr:20-11-07_1_p1_F05R_i_589983.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21R_f_164284.wav:continue,arrête",
            "out1ver1conarr:04-12-07_1_p1_M20R_r_233843.wav:continue,arrête",
            "out1ver1conarr:04-12-07_2_p1_M21R_i_770591.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02R_i_203840.wav:continue,arrête",
            "out1ver1conarr:14-11-07_1_p1_M01L_r_23773.wav:continue,arrête",
            "out1ver1conarr:14-11-07_2_p1_M02R_f_681124.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_i_924770.wav:continue,arrête",
            "out1ver1conarr:05-12-07_1_p1_M23R_i_359359.wav:continue,arrête",
            "out1ver1conarr:04-12-07_1_p1_M20R_i_146255.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19R_r_416402.wav:continue,arrête",
            "out1ver1conarr:03-12-07_1_p1_M19R_r_374050.wav:continue,arrête",
            "out1ver1conarr:22-11-07_2_p1_F07L_r_896141.wav:continue,arrête"
        };
    }

    protected String[] getStimuliOut1Ver2ConArr() {
        return new String[]{
            "out1ver2conarr:03-12-07_1_p1_M19L_i_727746_NA.mpeg:continue,arrête",
            "out1ver2conarr:05-12-07_1_p1_M23R_i_104729_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01R_i_219580_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02L_r_279309_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_i_280359_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_1_p1_F06R_f_1430740_NA.mpeg:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19R_r_414259_NA.mpeg:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_i_254252_NA.mpeg:continue,arrête",
            "out1ver2conarr:23-11-07_1_p1_F08R_im_321628_NA.mpeg:continue,arrête",
            "out1ver2conarr:04-12-07_1_p1_M20R_im_333326_NA.mpeg:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19L_im_131836_NA.mpeg:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_f_208696_NA.mpeg:continue,arrête",
            "out1ver2conarr:20-11-07_1_p1_F05R_i_581293_NA.mpeg:continue,arrête",
            "out1ver2conarr:05-12-07_1_p1_M23L_r_275666_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07R_i_1625043_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02R_f_831537_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07R_f_1086927_NA.mpeg:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_i_255747_NA.mpeg:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21R_im_368102_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01R_r_369646_NA.mpeg:continue,arrête",
            "out1ver2conarr:20-11-07_1_p1_F05R_i_315485_NA.mpeg:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_i_803331_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02L_l_454858_NA.mpeg:continue,arrête",
            "out1ver2conarr:04-12-07_1_p1_M20R_im_364784_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_f_1377242_NA.mpeg:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_f_608165_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02R_i_530691_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01R_r_397825_NA.mpeg:continue,arrête",
            "out1ver2conarr:04-12-07_1_p1_M20R_r_226308_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_i_1450339_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_n_869460_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02L_r_343273_NA.mpeg:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19R_r_518943_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07R_i_1300490_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_i_1100294_NA.mpeg:continue,arrête",
            "out1ver2conarr:20-11-07_1_p1_F05L_i_106723_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07R_i_1198900_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02L_r_782925_NA.mpeg:continue,arrête",
            "out1ver2conarr:20-11-07_1_p1_F05R_f_624120_NA.mpeg:continue,arrête",
            "out1ver2conarr:23-11-07_1_p1_F08L_n_333073_NA.mpeg:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_f_431951_NA.mpeg:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19R_l_360744_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_1_p1_F06L_i_97728_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07R_i_728550_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02L_i_22560_NA.mpeg:continue,arrête",
            "out1ver2conarr:04-12-07_1_p1_M20L_im_279950_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01R_r_160211_NA.mpeg:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19R_i_353600_NA.mpeg:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21L_r_380821_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02R_f_660313_NA.mpeg:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19L_f_226523_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_1_p1_F06R_i_385665_NA.mpeg:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19R_r_529986_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01L_r_325139_NA.mpeg:continue,arrête",
            "out1ver2conarr:20-11-07_1_p1_F05R_i_375598_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02R_i_542926_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_i_332023_NA.mpeg:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19L_i_288514_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_1_p1_F06R_r_1249394_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02L_r_119874_NA.mpeg:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19L_i_178668_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_f_888933_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02R_i_662936_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_f_1397300_NA.mpeg:continue,arrête",
            "out1ver2conarr:20-11-07_1_p1_F05R_i_589983_NA.mpeg:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21R_f_164284_NA.mpeg:continue,arrête",
            "out1ver2conarr:04-12-07_1_p1_M20R_r_233843_NA.mpeg:continue,arrête",
            "out1ver2conarr:04-12-07_2_p1_M21R_i_770591_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02R_i_203840_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_1_p1_M01L_r_23773_NA.mpeg:continue,arrête",
            "out1ver2conarr:14-11-07_2_p1_M02R_f_681124_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_i_924770_NA.mpeg:continue,arrête",
            "out1ver2conarr:05-12-07_1_p1_M23R_i_359359_NA.mpeg:continue,arrête",
            "out1ver2conarr:04-12-07_1_p1_M20R_i_146255_NA.mpeg:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19R_r_416402_NA.mpeg:continue,arrête",
            "out1ver2conarr:03-12-07_1_p1_M19R_r_374050_NA.mpeg:continue,arrête",
            "out1ver2conarr:22-11-07_2_p1_F07L_r_896141_NA.mpeg:continue,arrête"};
    }

    protected String[] getStimuliOut1Ver3ConArr() {
        return new String[]{
            "out1ver3conarr:03-12-07_1_p1_M19L_i_727746_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:05-12-07_1_p1_M23R_i_104729_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01R_i_219580_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02L_r_279309_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_i_280359_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_1_p1_F06R_f_1430740_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19R_r_414259_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_i_254252_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:23-11-07_1_p1_F08R_im_321628_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:04-12-07_1_p1_M20R_im_333326_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19L_im_131836_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_f_208696_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:20-11-07_1_p1_F05R_i_581293_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:05-12-07_1_p1_M23L_r_275666_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07R_i_1625043_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02R_f_831537_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07R_f_1086927_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_i_255747_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21R_im_368102_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01R_r_369646_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:20-11-07_1_p1_F05R_i_315485_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_i_803331_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02L_l_454858_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:04-12-07_1_p1_M20R_im_364784_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_f_1377242_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_f_608165_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02R_i_530691_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01R_r_397825_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:04-12-07_1_p1_M20R_r_226308_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_i_1450339_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_n_869460_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02L_r_343273_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19R_r_518943_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07R_i_1300490_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_i_1100294_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:20-11-07_1_p1_F05L_i_106723_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07R_i_1198900_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02L_r_782925_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:20-11-07_1_p1_F05R_f_624120_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:23-11-07_1_p1_F08L_n_333073_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_f_431951_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19R_l_360744_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_1_p1_F06L_i_97728_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07R_i_728550_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02L_i_22560_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:04-12-07_1_p1_M20L_im_279950_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01R_r_160211_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19R_i_353600_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21L_r_380821_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02R_f_660313_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19L_f_226523_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_1_p1_F06R_i_385665_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19R_r_529986_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01L_r_325139_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:20-11-07_1_p1_F05R_i_375598_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02R_i_542926_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_i_332023_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19L_i_288514_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_1_p1_F06R_r_1249394_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02L_r_119874_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19L_i_178668_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_f_888933_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02R_i_662936_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_f_1397300_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:20-11-07_1_p1_F05R_i_589983_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21R_f_164284_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:04-12-07_1_p1_M20R_r_233843_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:04-12-07_2_p1_M21R_i_770591_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02R_i_203840_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_1_p1_M01L_r_23773_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:14-11-07_2_p1_M02R_f_681124_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_i_924770_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:05-12-07_1_p1_M23R_i_359359_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:04-12-07_1_p1_M20R_i_146255_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19R_r_416402_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:03-12-07_1_p1_M19R_r_374050_NA__F.mpeg:continue,arrête",
            "out1ver3conarr:22-11-07_2_p1_F07L_r_896141_NA__F.mpeg:continue,arrête"};
    }

    protected String[] getStimuliOut2Ver1ConArr() {
        return new String[]{
            "out2ver1conarr:03-12-07_1_p1_M19L_i_727746.wav:continue,arrête",
            "out2ver1conarr:05-12-07_1_p1_M23R_i_104729.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01R_i_219580.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02L_r_279309.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_i_280359.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07R_i_728550.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21R_im_368102.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02R_f_831537.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_f_1377242.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19R_r_414259.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01L_r_325139.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_f_608165.wav:continue,arrête",
            "out2ver1conarr:20-11-07_1_p1_F05L_i_106723.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19R_l_360744.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_f_208696.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21R_i_770591.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07R_i_1625043.wav:continue,arrête",
            "out2ver1conarr:04-12-07_1_p1_M20R_r_226308.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01L_r_23773.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_n_869460.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07R_i_1198900.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_i_254252.wav:continue,arrête",
            "out2ver1conarr:04-12-07_1_p1_M20L_im_279950.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02R_i_203840.wav:continue,arrête",
            "out2ver1conarr:04-12-07_1_p1_M20R_i_146255.wav:continue,arrête",
            "out2ver1conarr:05-12-07_1_p1_M23R_i_359359.wav:continue,arrête",
            "out2ver1conarr:05-12-07_1_p1_M23L_r_275666.wav:continue,arrête",
            "out2ver1conarr:20-11-07_1_p1_F05R_i_315485.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02R_f_660313.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19L_i_288514.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19L_i_178668.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21R_f_164284.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19R_r_416402.wav:continue,arrête",
            "out2ver1conarr:04-12-07_1_p1_M20R_im_364784.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02L_r_119874.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_i_803331.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_i_255747.wav:continue,arrête",
            "out2ver1conarr:22-11-07_1_p1_F06R_i_385665.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07R_i_1300490.wav:continue,arrête",
            "out2ver1conarr:20-11-07_1_p1_F05R_i_581293.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07R_f_1086927.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19L_f_226523.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02R_i_662936.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_r_380821.wav:continue,arrête",
            "out2ver1conarr:22-11-07_1_p1_F06L_i_97728.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19R_i_353600.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02L_r_782925.wav:continue,arrête",
            "out2ver1conarr:23-11-07_1_p1_F08R_im_321628.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01R_r_160211.wav:continue,arrête",
            "out2ver1conarr:22-11-07_1_p1_F06R_r_1249394.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02R_i_530691.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_f_888933.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_i_1100294.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02L_r_343273.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_i_924770.wav:continue,arrête",
            "out2ver1conarr:22-11-07_1_p1_F06R_f_1430740.wav:continue,arrête",
            "out2ver1conarr:04-12-07_2_p1_M21L_f_431951.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02L_i_22560.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01R_r_397825.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02R_f_681124.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19R_r_374050.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19L_im_131836.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_f_1397300.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02R_i_542926.wav:continue,arrête",
            "out2ver1conarr:14-11-07_2_p1_M02L_l_454858.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19R_r_518943.wav:continue,arrête",
            "out2ver1conarr:14-11-07_1_p1_M01R_r_369646.wav:continue,arrête",
            "out2ver1conarr:20-11-07_1_p1_F05R_i_589983.wav:continue,arrête",
            "out2ver1conarr:20-11-07_1_p1_F05R_i_375598.wav:continue,arrête",
            "out2ver1conarr:20-11-07_1_p1_F05R_f_624120.wav:continue,arrête",
            "out2ver1conarr:03-12-07_1_p1_M19R_r_529986.wav:continue,arrête",
            "out2ver1conarr:23-11-07_1_p1_F08L_n_333073.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_r_896141.wav:continue,arrête",
            "out2ver1conarr:04-12-07_1_p1_M20R_r_233843.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_i_1450339.wav:continue,arrête",
            "out2ver1conarr:22-11-07_2_p1_F07L_i_332023.wav:continue,arrête",
            "out2ver1conarr:04-12-07_1_p1_M20R_im_333326.wav:continue,arrête"};
    }

    protected String[] getStimuliOut2Ver2ConArr() {
        return new String[]{
            "out2ver2conarr:03-12-07_1_p1_M19L_i_727746_NA.mpeg:continue,arrête",
            "out2ver2conarr:05-12-07_1_p1_M23R_i_104729_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01R_i_219580_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02L_r_279309_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_i_280359_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07R_i_728550_NA.mpeg:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21R_im_368102_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02R_f_831537_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_f_1377242_NA.mpeg:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19R_r_414259_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01L_r_325139_NA.mpeg:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_f_608165_NA.mpeg:continue,arrête",
            "out2ver2conarr:20-11-07_1_p1_F05L_i_106723_NA.mpeg:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19R_l_360744_NA.mpeg:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_f_208696_NA.mpeg:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21R_i_770591_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07R_i_1625043_NA.mpeg:continue,arrête",
            "out2ver2conarr:04-12-07_1_p1_M20R_r_226308_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01L_r_23773_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_n_869460_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07R_i_1198900_NA.mpeg:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_i_254252_NA.mpeg:continue,arrête",
            "out2ver2conarr:04-12-07_1_p1_M20L_im_279950_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02R_i_203840_NA.mpeg:continue,arrête",
            "out2ver2conarr:04-12-07_1_p1_M20R_i_146255_NA.mpeg:continue,arrête",
            "out2ver2conarr:05-12-07_1_p1_M23R_i_359359_NA.mpeg:continue,arrête",
            "out2ver2conarr:05-12-07_1_p1_M23L_r_275666_NA.mpeg:continue,arrête",
            "out2ver2conarr:20-11-07_1_p1_F05R_i_315485_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02R_f_660313_NA.mpeg:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19L_i_288514_NA.mpeg:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19L_i_178668_NA.mpeg:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21R_f_164284_NA.mpeg:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19R_r_416402_NA.mpeg:continue,arrête",
            "out2ver2conarr:04-12-07_1_p1_M20R_im_364784_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02L_r_119874_NA.mpeg:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_i_803331_NA.mpeg:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_i_255747_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_1_p1_F06R_i_385665_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07R_i_1300490_NA.mpeg:continue,arrête",
            "out2ver2conarr:20-11-07_1_p1_F05R_i_581293_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07R_f_1086927_NA.mpeg:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19L_f_226523_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02R_i_662936_NA.mpeg:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_r_380821_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_1_p1_F06L_i_97728_NA.mpeg:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19R_i_353600_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02L_r_782925_NA.mpeg:continue,arrête",
            "out2ver2conarr:23-11-07_1_p1_F08R_im_321628_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01R_r_160211_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_1_p1_F06R_r_1249394_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02R_i_530691_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_f_888933_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_i_1100294_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02L_r_343273_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_i_924770_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_1_p1_F06R_f_1430740_NA.mpeg:continue,arrête",
            "out2ver2conarr:04-12-07_2_p1_M21L_f_431951_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02L_i_22560_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01R_r_397825_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02R_f_681124_NA.mpeg:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19R_r_374050_NA.mpeg:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19L_im_131836_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_f_1397300_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02R_i_542926_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_2_p1_M02L_l_454858_NA.mpeg:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19R_r_518943_NA.mpeg:continue,arrête",
            "out2ver2conarr:14-11-07_1_p1_M01R_r_369646_NA.mpeg:continue,arrête",
            "out2ver2conarr:20-11-07_1_p1_F05R_i_589983_NA.mpeg:continue,arrête",
            "out2ver2conarr:20-11-07_1_p1_F05R_i_375598_NA.mpeg:continue,arrête",
            "out2ver2conarr:20-11-07_1_p1_F05R_f_624120_NA.mpeg:continue,arrête",
            "out2ver2conarr:03-12-07_1_p1_M19R_r_529986_NA.mpeg:continue,arrête",
            "out2ver2conarr:23-11-07_1_p1_F08L_n_333073_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_r_896141_NA.mpeg:continue,arrête",
            "out2ver2conarr:04-12-07_1_p1_M20R_r_233843_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_i_1450339_NA.mpeg:continue,arrête",
            "out2ver2conarr:22-11-07_2_p1_F07L_i_332023_NA.mpeg:continue,arrête",
            "out2ver2conarr:04-12-07_1_p1_M20R_im_333326_NA.mpeg:continue,arrête"};
    }

    protected String[] getStimuliOut2Ver3ConArr() {
        return new String[]{
            "out2ver3conarr:03-12-07_1_p1_M19L_i_727746_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:05-12-07_1_p1_M23R_i_104729_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01R_i_219580_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02L_r_279309_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_i_280359_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07R_i_728550_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21R_im_368102_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02R_f_831537_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_f_1377242_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19R_r_414259_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01L_r_325139_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_f_608165_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:20-11-07_1_p1_F05L_i_106723_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19R_l_360744_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_f_208696_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21R_i_770591_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07R_i_1625043_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:04-12-07_1_p1_M20R_r_226308_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01L_r_23773_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_n_869460_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07R_i_1198900_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_i_254252_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:04-12-07_1_p1_M20L_im_279950_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02R_i_203840_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:04-12-07_1_p1_M20R_i_146255_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:05-12-07_1_p1_M23R_i_359359_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:05-12-07_1_p1_M23L_r_275666_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:20-11-07_1_p1_F05R_i_315485_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02R_f_660313_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19L_i_288514_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19L_i_178668_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21R_f_164284_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19R_r_416402_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:04-12-07_1_p1_M20R_im_364784_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02L_r_119874_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_i_803331_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_i_255747_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_1_p1_F06R_i_385665_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07R_i_1300490_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:20-11-07_1_p1_F05R_i_581293_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07R_f_1086927_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19L_f_226523_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02R_i_662936_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_r_380821_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_1_p1_F06L_i_97728_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19R_i_353600_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02L_r_782925_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:23-11-07_1_p1_F08R_im_321628_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01R_r_160211_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_1_p1_F06R_r_1249394_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02R_i_530691_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_f_888933_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_i_1100294_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02L_r_343273_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_i_924770_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_1_p1_F06R_f_1430740_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:04-12-07_2_p1_M21L_f_431951_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02L_i_22560_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01R_r_397825_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02R_f_681124_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19R_r_374050_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19L_im_131836_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_f_1397300_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02R_i_542926_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_2_p1_M02L_l_454858_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19R_r_518943_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:14-11-07_1_p1_M01R_r_369646_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:20-11-07_1_p1_F05R_i_589983_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:20-11-07_1_p1_F05R_i_375598_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:20-11-07_1_p1_F05R_f_624120_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:03-12-07_1_p1_M19R_r_529986_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:23-11-07_1_p1_F08L_n_333073_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_r_896141_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:04-12-07_1_p1_M20R_r_233843_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_i_1450339_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:22-11-07_2_p1_F07L_i_332023_NA__F.mpeg:continue,arrête",
            "out2ver3conarr:04-12-07_1_p1_M20R_im_333326_NA__F.mpeg:continue,arrête"};
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
        WizardAgreementScreen agreementScreen1 = new WizardAgreementScreen("Accord", agreementScreenText, "Je suis d'accord");
        WizardAgreementScreen agreementScreen2 = new WizardAgreementScreen("AccordConfiddentialite", "Avant de pouvoir continuer,<br/>"
                + "vous devez vous engager à ne pas discuter le contenu des extraits avec d'autres personnes.", "J'accepte");
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
        wizardData.addScreen(agreementScreen1);
        wizardData.addScreen(agreementScreen2);
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

//        WizardMenuScreen contextMenu = new WizardMenuScreen("ContextMenu", "ContextMenu", "ContextMenu");
//        contextMenu.setBranchOnGetParam(true, "A choice must be provided out of the following:<br/>");
//        wizardData.addScreen(contextMenu);
//        final WizardVideoAudioOptionStimulusScreen randomStimuliTagsScreenC = new WizardVideoAudioOptionStimulusScreen("stimuliC", false, stimuliList.toArray(new String[]{}), false, true, randomStimuliTags, 1000, repeatCount(), 20, false, 100, "", "", true);
        final WizardVideoAudioOptionStimulusScreen randomStimuliTagsScreenN = new WizardVideoAudioOptionStimulusScreen("stimuliN", false, stimuliList.toArray(new String[]{}), false, true, randomStimuliTags, 1000, repeatCount(), 20, false, 100, "", "", true);
//        randomStimuliTagsScreenC.setRandomTagField("stimuli");
        randomStimuliTagsScreenN.setRandomTagField("stimuli");
        randomStimuliTagsScreenN.setStimuliTypeAny(true);
//        randomStimuliTagsScreenC.setShowProgress(true);
//        randomStimuliTagsScreenC.setShowHurryIndicator(false);
        randomStimuliTagsScreenN.setShowProgress(true);
        randomStimuliTagsScreenN.setShowHurryIndicator(false);

        randomStimuliTagsScreenN.setUseCodeAudio(false); // there is an error in the way stimuli are processed in the constructor that affects this experiment so we construct with code audio and change this back to false here
//        list1234Screen.setStimulusResponseOptions("1,2,3,4,5");
//        list1234Screen.setStimulusResponseLabelLeft("très probable négatif");
//        list1234Screen.setStimulusResponseLabelRight("très probable positif");
        wizardData.addScreen(pretestScreen);
        wizardData.addScreen(scoreThresholdScreen);
//        wizardData.addScreen(randomStimuliTagsScreenC);
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

        wizardEditUserScreen.setNextWizardScreen(agreementScreen1);
        agreementScreen1.setNextWizardScreen(wizardTextScreen1);

        agreementScreen2.setNextWizardScreen(randomStimuliTagsScreenN);
//        WizardMenuScreen menuScreen = new WizardMenuScreen("temporary menu", "temporary menu", "temporary menu");
//        wizardData.addScreen(menuScreen);
        wizardTextScreen1.setNextWizardScreen(pretestScreen);
        pretestScreen.setNextWizardScreen(scoreThresholdScreen);
        scoreThresholdScreen.setNextWizardScreen(wizardTextScreen2);
        wizardTextScreen2.setNextWizardScreen(agreementScreen2);

//        contextMenu.addTargetScreen(randomStimuliTagsScreenC);
//        contextMenu.addTargetScreen(randomStimuliTagsScreenN);
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
//        randomStimuliTagsScreenC.setNextWizardScreen(wizardFeedbackScreen);
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
