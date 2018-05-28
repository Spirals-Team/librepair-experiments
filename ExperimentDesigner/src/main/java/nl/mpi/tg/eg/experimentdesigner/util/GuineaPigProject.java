/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics
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
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAudioTestScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardCompletionScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardEditUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardExistingUserCheckScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardGridStimulusScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardMenuScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardSubmitOfflineDataScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardSelectUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardTextScreen;

/**
 * @since Mar 23, 2017 2:50:16 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class GuineaPigProject {

    private final WizardController wizardController = new WizardController();
//    final String agreementScreenText = "Toestemmingsverklaring voor deelname aan het onderzoek:<br/>"
//            + "<br/>"
//            + "Ik stem geheel vrijwillig in met deelname aan dit onderzoek.<br/>"
//            + "<br/>"
//            + "Als u ermee instemt om door te gaan met dit experiment, klik dan op 'Akkoord' om verder te gaan.<br/>"
//            + "<br/>"
//            + "Als u besluit niet deel te nemen aan het experiment, kunt u de pagina sluiten of naar een andere website gaan.";
//    final String informationScreenText = "Alvast ontzettend bedankt voor uw deelname aan dit onderzoek.<br/>";
    final String completionScreenText1 = "Dit is het einde van het experiment.<br/>"
            + "Hartelijk dank voor uw deelname! <br/>"
            + "<br/>";
    final String completionScreenText2 = "<br/>"
            + "Het bovenstaande nummer is het bewijs dat u het experiment heeft voltooid, en is vereist voor het in orde maken van uw vergoeding.";

    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName("ld-screensize");
        wizardData.setShowMenuBar(false);
        wizardData.setTextFontSize(17);
        wizardData.setObfuscateScreenNames(false);

        final WizardTextScreen bluetoothInstructionsScreen = new WizardTextScreen("Bluetooth Instructions", "When the bluetooth controller is connected the virtual keyboard will not show, to enter participant metadata please turn off the bluetooth controller so that the virtual keyboard can be shown. To start the bluetooth controller turn it on and press the button combination M+A.", "Volgende");
        bluetoothInstructionsScreen.setNextHotKey("ENTER");
        wizardData.addScreen(bluetoothInstructionsScreen);

        // @TODO: add use of the sound files Correct and Incorrect
        final WizardExistingUserCheckScreen existingUserCheckScreen = new WizardExistingUserCheckScreen("Start", "New interview", "Resume interview", "Begin a new interview with a new participant", "Resume an interview with an existing participant");
        final WizardSelectUserScreen selectUserScreen = new WizardSelectUserScreen("Select Participant");
        wizardData.addScreen(existingUserCheckScreen);
        wizardData.addScreen(selectUserScreen);
//        WizardTextScreen wizardTextScreen = new WizardTextScreen("Informatie", informationScreenText,
//                "volgende [ spatiebalk ]"
//        );
//        wizardTextScreen.setMenuLabel("Terug");
//        WizardAgreementScreen agreementScreen = new WizardAgreementScreen("Toestemming", agreementScreenText, "Akkoord");
//        agreementScreen.setMenuLabel("Terug");
        final WizardEditUserScreen wizardEditUserScreen = new WizardEditUserScreen();
        wizardEditUserScreen.setScreenTitle("Gegevens");
        wizardEditUserScreen.setMenuLabel("Terug");
        wizardEditUserScreen.setScreenTag("Edit User");
        wizardEditUserScreen.setNextButton("Volgende");
        wizardEditUserScreen.setSendData(true);
        wizardEditUserScreen.setIgnoreNetworkError(true);
        wizardEditUserScreen.setOn_Error_Text("Geen verbinding met de server. Controleer alstublieft uw internetverbinding en probeer het opnieuw.");
        wizardEditUserScreen.setCustomFields(new String[]{
            "workerId:Proefpersoon ID:.'{'3,'}':Voer minimaal drie letters.", // @todo: update the regex to date format and in the future add a calandar popup
            "datOfBirth:Geboortedatum:[0-3][0-9]/[0-1][0-9]/[1-2][0-9][0-9][0-9]:Voer een getal.",
            "gender:Geslacht:|man|vrouw|anders:."
        });
        existingUserCheckScreen.setNextWizardScreen(wizardEditUserScreen);
        final WizardMenuScreen menuScreen = new WizardMenuScreen("Menu", "Menu", "Menu");
//        wizardData.addScreen(agreementScreen);
//        wizardData.addScreen(wizardTextScreen);
        wizardData.addScreen(menuScreen);
        wizardData.addScreen(wizardEditUserScreen);

        String backgroundImage = "huisje_02.jpg";
        WizardAudioTestScreen introductionAudio1 = new WizardAudioTestScreen("Introduction 1", "&nbsp;", "continue button", "intro_1");
        wizardData.addScreen(introductionAudio1);
        WizardAudioTestScreen introductionAudio2 = new WizardAudioTestScreen("Introduction 2", "&nbsp;", "continue button", "intro_2");
        wizardData.addScreen(introductionAudio2);
        WizardAudioTestScreen introductionAudio3 = new WizardAudioTestScreen("Introduction 3", "&nbsp;", "continue button", "intro_3");
        wizardData.addScreen(introductionAudio3);
        introductionAudio1.setBackgroundImage(backgroundImage);
        introductionAudio2.setBackgroundImage(backgroundImage);
        introductionAudio3.setBackgroundImage(backgroundImage);
//        introductionAudio1.setAutoPlay(true); //@todo: do not auto play, in future version the play button may be hidden
//        introductionAudio2.setAutoPlay(true);
//        introductionAudio3.setAutoPlay(true);
        introductionAudio1.setAutoNext(true);
        introductionAudio2.setAutoNext(true);
        introductionAudio3.setAutoNext(true);
        introductionAudio1.setAudioHotKey("ENTER");
        introductionAudio2.setAudioHotKey("ENTER");
        introductionAudio3.setAudioHotKey("ENTER");
//        introductionAudio1.setNextHotKey("ENTER");
//        introductionAudio2.setNextHotKey("ENTER");
//        introductionAudio3.setNextHotKey("ENTER");
        introductionAudio1.setButtonStyle("titleBarButton");
        introductionAudio2.setButtonStyle("titleBarButton");
        introductionAudio3.setButtonStyle("titleBarButton");

//        String[] fillerList = new String[]{};
        //            "intro_1",
        //            "intro_2",
        //            "intro_3",
        //            "room_1",
        //            "room_2",
        //            "room_3",
        //            "room_4",
        //            "room_5",
        String[][][][] testList = new String[][][][]{
            // @todo: all of test, filler and training need to be grouped by test_1 and order by test_1_1, test_1_2, test_1_3 *** change the stimuli to test_1 and code to add the _1 _2 _3 on the screen
            // @todo: test_1 and test_5 for example must never be ajacent, perhaps this can be done with the exisiting adjacency code, by adding a test_x_1 and moving the _1 to code
            //@todo: blank screen with audio 1
            //@todo: videos and audio 2
            //@todo: still of video and audio 3 with touch input can be collected during audio 1 2 and 3, touch input does not cause any action, only the remote can move to the next stimulus
            {{{"Room 1", "zoomToBlock1", "room_1", "Set_1"}, {}}, {{"test_1", "adjacency_a"}, {"test_8", "adjacency_b"}, {"filler_1", "filler_1"}}},
            {{{"Room 2", "zoomToBlock2", "room_2", "Set_2"}, {}}, {{"test_3", "adjacency_c"}, {"test_2", "adjacency_d"}, {"filler_3", "filler_3"}}},
            {{{"Room 3", "zoomToBlock3", "room_3", "Set_3"}, {}}, {{"test_4", "adjacency_a"}, {"test_5", "adjacency_b"}, {"filler_2", "filler_2"}}},
            {{{"Room 4", "zoomToBlock4", "room_4", "Set_4"}, {}}, {{"test_6", "adjacency_c"}, {"test_7", "adjacency_d"}, {"filler_4", "filler_4"}}},};
        String[][] trainingList = new String[][]{
            {"training_1", "training_1"}, {"training_2", "training_2"}, {"training_3", "training_3"}, {"training_4", "training_4"}
        };
        String[] groupTagArray = {"Set_1", "Set_2", "Set_3", "Set_4"};
//        final WizardGridStimulusScreen fillerStimulusScreen = new WizardGridStimulusScreen("fillerScreen", false, fillerList,
//                new String[]{
        //                    "list_b",
//                    "fillerScreen"}, 1000, true, null, 0, 0, null);
//        wizardData.addScreen(fillerStimulusScreen);
        final WizardGridStimulusScreen trainingStimulusScreen = new WizardGridStimulusScreen("Training", false, trainingList,
                null, 1000, false, null, 0, 0, null); // @todo: this screen is in the garden
        trainingStimulusScreen.setCodeAudio(true);
        trainingStimulusScreen.setBackgroundImage(backgroundImage);
        trainingStimulusScreen.setBackgroundStyle("zoomToGarden");
        trainingStimulusScreen.getWizardScreenData().setBackWizardScreenData(menuScreen.getWizardScreenData());
        wizardData.addScreen(trainingStimulusScreen);
        final WizardMenuScreen textMenuScreen = new WizardMenuScreen("TestMenu", "TestMenu", "TestMenu");
//        textMenuScreen.setJumpToRandomScreen(true);
        wizardData.addScreen(textMenuScreen);
        WizardScreen backScreen = trainingStimulusScreen;
        for (String[][][] testSubList : testList) {
//            WizardAudioTestScreen testIntroAudio = new WizardAudioTestScreen(testSubList[0][0] + "a", "&nbsp;", "continue button", testSubList[0][2]);
//            wizardData.addScreen(testIntroAudio);
//            testIntroAudio.setBackgroundImage(backgroundImage);
//            testIntroAudio.setBackgroundStyle(testSubList[0][1]);
//            testIntroAudio.setAutoPlay(true);
//            testIntroAudio.setAutoNext(true);
//            testIntroAudio.setAutoNextDelay(2000);
//            testIntroAudio.setAudioHotKey("F6");
//            testIntroAudio.setImageName("intro_1.jpg");
//            testIntroAudio.setNextHotKey("ENTER");
//            testIntroAudio.setStyleName("titleBarButton");

            final WizardGridStimulusScreen testStimulusScreen = new WizardGridStimulusScreen(testSubList[0][0][0], false, testSubList[1],
                    null, 1000, false, null, 0, 0, null, "allRooms", testSubList[0][0][3]);
            testStimulusScreen.setCodeAudio(true);
            testStimulusScreen.setBackgroundImage(backgroundImage);
            testStimulusScreen.setBackgroundStyle(testSubList[0][0][1]);
            testStimulusScreen.setIntroAudio(testSubList[0][0][2]);
            testStimulusScreen.setIntroAudioDelay(2000);
            textMenuScreen.addTargetScreen(testStimulusScreen);
            backScreen.setNextWizardScreenData(testStimulusScreen.getWizardScreenData());
            wizardData.addScreen(testStimulusScreen);
            testStimulusScreen.getWizardScreenData().setBackWizardScreenData(menuScreen.getWizardScreenData());
            testStimulusScreen.setNextWizardScreen(textMenuScreen);
            testStimulusScreen.getWizardScreenData().setStimuliRandomTags(groupTagArray);
            testStimulusScreen.setConsumedTagGroup("allRooms");
            backScreen = testStimulusScreen;
        }
        WizardCompletionScreen completionScreen = new WizardCompletionScreen(completionScreenText1, false, true, true, completionScreenText2,
                "Opnieuw beginnen",
                "Einde van het experiment",
                "Geen verbinding met de server. Controleer alstublieft uw internetverbinding en probeer het opnieuw.",
                "Probeer opnieuw");
        completionScreen.setSendData(false);
        wizardData.addScreen(completionScreen);
        completionScreen.setScreenTag("completion");

        WizardAudioTestScreen atticScreen = new WizardAudioTestScreen("Attic", "&nbsp;", "completion", "room_5");
        wizardData.addScreen(atticScreen);
        atticScreen.setBackgroundImage(backgroundImage);
        atticScreen.setBackgroundStyle("zoomToAttic");
        atticScreen.setAutoPlay(true);
        atticScreen.setAutoNext(false);
        atticScreen.setAutoNextDelay(2000);
        atticScreen.setAudioHotKey("R1_MA_A");
        atticScreen.setImageName("Playroom.jpg");
        atticScreen.setNextHotKey("ENTER");
        atticScreen.setImageStyle("zoomToPlayroom");
        atticScreen.setButtonStyle("titleBarButton");
//        atticScreen.setBackWizardScreen(menuScreen);
        atticScreen.setNextWizardScreen(completionScreen);
        bluetoothInstructionsScreen.setBackWizardScreen(menuScreen);
        bluetoothInstructionsScreen.setNextWizardScreen(existingUserCheckScreen);

//        existingUserCheckScreen.setNextWizardScreen(selectUserScreen);
        selectUserScreen.setBackWizardScreen(existingUserCheckScreen);
        selectUserScreen.setNextWizardScreen(wizardEditUserScreen);

//        wizardTextScreen.setNextWizardScreen(wizardEditUserScreen);
//        agreementScreen.setNextWizardScreen(wizardTextScreen);
//        wizardTextScreen.setBackWizardScreen(agreementScreen);
        wizardEditUserScreen.setNextWizardScreen(introductionAudio1);
        introductionAudio1.setNextWizardScreen(introductionAudio2);
        introductionAudio2.setNextWizardScreen(introductionAudio3);
        introductionAudio3.setNextWizardScreen(trainingStimulusScreen);
//        fillerStimulusScreen.setNextWizardScreen(trainingStimulusScreen);
//        trainingStimulusScreen.setNextWizardScreen(textMenuScreen);
        introductionAudio1.setBackWizardScreen(menuScreen);
        introductionAudio2.setBackWizardScreen(menuScreen);
        introductionAudio3.setBackWizardScreen(menuScreen);
//        fillerStimulusScreen.setBackWizardScreen(introductionAudio3);
//        trainingStimulusScreen.setBackWizardScreen(menuScreen);
        textMenuScreen.setBackWizardScreen(menuScreen);
        backScreen.setNextWizardScreenData(atticScreen.getWizardScreenData());

        WizardSubmitOfflineDataScreen offlineCompletionScreen = new WizardSubmitOfflineDataScreen();
        wizardData.addScreen(offlineCompletionScreen);
        offlineCompletionScreen.setBackWizardScreen(menuScreen);

        final WizardAboutScreen wizardAboutScreen = new WizardAboutScreen("Over", false);
        wizardAboutScreen.setBackWizardScreen(menuScreen);
        completionScreen.setBackWizardScreen(menuScreen);
        completionScreen.setNextWizardScreen(wizardEditUserScreen);
        wizardData.addScreen(wizardAboutScreen);
        wizardEditUserScreen.setBackWizardScreen(selectUserScreen);

        return wizardData;
    }

    public Experiment getExperiment() {
        // @todo: prevent portrate mode
        return wizardController.getExperiment(getWizardData());
    }
}
