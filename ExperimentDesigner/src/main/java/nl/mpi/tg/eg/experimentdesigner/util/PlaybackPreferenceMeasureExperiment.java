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
import nl.mpi.tg.eg.experimentdesigner.model.DataChannel;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAboutScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardCompletionScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardSubmitOfflineDataScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardEditUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardExistingUserCheckScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardGridStimulusScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardMenuScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardSelectUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardTextScreen;

/**
 * @since Dec 05, 2017 18:01 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class PlaybackPreferenceMeasureExperiment {

    private final WizardController wizardController = new WizardController();
    final String completionScreenText1 = "Dit is het einde van het experiment.<br/>"
            + "Hartelijk dank voor uw deelname! <br/>"
            + "<br/>";
    final String completionScreenText2 = "<br/>"
            + "Het bovenstaande nummer is het bewijs dat u het experiment heeft voltooid, en is vereist voor het in orde maken van uw vergoeding.";

    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName("Playback Preference");
        wizardData.setShowMenuBar(false);
        wizardData.setTextFontSize(17);
        wizardData.setObfuscateScreenNames(false);
        wizardData.addDataChannel(new DataChannel(3, "Touch Input", true));
        final WizardTextScreen bluetoothInstructionsScreen = new WizardTextScreen("Bluetooth Instructions", "When the bluetooth controller is connected the virtual keyboard will not show, to enter participant metadata please turn off the bluetooth controller so that the virtual keyboard can be shown. To start the bluetooth controller turn it on and press the button combination M+A.", "Volgende");
        bluetoothInstructionsScreen.setNextHotKey("ENTER");
        wizardData.addScreen(bluetoothInstructionsScreen);

        final WizardExistingUserCheckScreen existingUserCheckScreen = new WizardExistingUserCheckScreen("Start", "New interview", "Resume interview", "Begin a new interview with a new participant", "Resume an interview with an existing participant");
        final WizardSelectUserScreen selectUserScreen = new WizardSelectUserScreen("Select Participant");
        wizardData.addScreen(existingUserCheckScreen);
        wizardData.addScreen(selectUserScreen);
        final WizardEditUserScreen wizardEditUserScreen = new WizardEditUserScreen();
        wizardEditUserScreen.setScreenTitle("Gegevens");
        wizardEditUserScreen.setMenuLabel("Terug");
        wizardEditUserScreen.setScreenTag("Edit User");
        wizardEditUserScreen.setNextButton("Volgende");
        wizardEditUserScreen.setSendData(false);
        wizardEditUserScreen.setOn_Error_Text("Geen verbinding met de server. Controleer alstublieft uw internetverbinding en probeer het opnieuw.");
        wizardEditUserScreen.setCustomFields(new String[]{
            "workerId:Proefpersoon ID:.'{'3,'}':Voer minimaal drie letters.", // @todo: update the regex to date format and in the future add a calandar popup
            "datOfBirth:Geboortedatum:[0-3][0-9]/[0-1][0-9]/[1-2][0-9][0-9][0-9]:Voer een getal.",
            "gender:Geslacht:|man|vrouw|anders:."
        });
        existingUserCheckScreen.setNextWizardScreen(wizardEditUserScreen);
        final WizardMenuScreen menuScreen = new WizardMenuScreen("Menu", "Menu", "Menu");
        wizardData.addScreen(menuScreen);

        final WizardMenuScreen textMenuScreen = new WizardMenuScreen("StimuliMenu", "StimuliMenu", "StimuliMenu");
        textMenuScreen.setJumpToRandomScreen(false);
        wizardData.addScreen(textMenuScreen);

        wizardData.addScreen(wizardEditUserScreen);
//        String backgroundImage = "huisje_02.jpg";
        String[][][][] testList = new String[][][][]{
            {{{"COW", "zoomToBlock1", "room_1"}, {}},
            {{"COW", "COW"}, {"COWmis", "COWmis"}, {"COWmis-5", "COWmis-5"}}},
            {{{"FATHER", "zoomToBlock2", "room_2"}, {}},
            {{"FATHER", "FATHER"}, {"FATHERmis", "FATHERmis"}}},
            {{{"GHOST_a", "zoomToBlock3", "room_3"}, {}},
            {{"GHOST-a", "GHOST-a"}, {"GHOST-amis", "GHOST-amis"}}},
            {{{"GHOST_b", "zoomToBlock4", "room_4"}, {}},
            {{"GHOST-b", "GHOST-b"}, {"GHOST-bmis", "GHOST-bmis"}}},
            {{{"PRAY", "zoomToBlock4", "room_4"}, {}},
            {{"PRAY", "PRAY"}, {"PRAYmis", "PRAYmis"}}},
            {{{"SHY", "zoomToBlock4", "room_4"}, {}},
            {{"SHY", "SHY"}, {"SHYmis", "SHYmis"}}}};
        for (String[][][] testSubList : testList) {
            final WizardGridStimulusScreen testStimulusScreen = new WizardGridStimulusScreen(testSubList[0][0][0], false, testSubList[1],
                    null, 1000, false, null, 0, 0, null);
//            testStimulusScreen.setBackgroundImage(backgroundImage);
            testStimulusScreen.setBackgroundStyle(testSubList[0][0][1]);
//            testStimulusScreen.setIntroAudio(testSubList[0][0][2]);
//            testStimulusScreen.setIntroAudioDelay(2000);
            testStimulusScreen.setRememberLastStimuli(false);
            testStimulusScreen.setShowCurtains(true);
            textMenuScreen.addTargetScreen(testStimulusScreen);
            wizardData.addScreen(testStimulusScreen);
            testStimulusScreen.setBackWizardScreenData(menuScreen.getWizardScreenData());
            testStimulusScreen.setNextWizardScreen(textMenuScreen);
        }
        WizardCompletionScreen completionScreen = new WizardCompletionScreen(completionScreenText1, true, true, false, completionScreenText2,
                "Opnieuw beginnen",
                "Einde van het experiment",
                "Geen verbinding met de server. Controleer alstublieft uw internetverbinding en probeer het opnieuw.",
                "Probeer opnieuw");
        completionScreen.setSendData(false);
        wizardData.addScreen(completionScreen);
        completionScreen.setScreenTag("completion");
        completionScreen.setNextWizardScreen(existingUserCheckScreen);

        WizardSubmitOfflineDataScreen offlineCompletionScreen = new WizardSubmitOfflineDataScreen();
        wizardData.addScreen(offlineCompletionScreen);
        offlineCompletionScreen.setBackWizardScreen(menuScreen);

        bluetoothInstructionsScreen.setBackWizardScreen(menuScreen);
        bluetoothInstructionsScreen.setNextWizardScreen(wizardEditUserScreen);

        selectUserScreen.setBackWizardScreen(existingUserCheckScreen);
        selectUserScreen.setNextWizardScreen(wizardEditUserScreen);

        wizardEditUserScreen.setNextWizardScreen(textMenuScreen);
        textMenuScreen.setBackWizardScreen(menuScreen);

        final WizardAboutScreen wizardAboutScreen = new WizardAboutScreen("Over", false);
        wizardAboutScreen.setBackWizardScreen(menuScreen);
        completionScreen.setBackWizardScreen(menuScreen);
        wizardData.addScreen(wizardAboutScreen);
        wizardEditUserScreen.setBackWizardScreen(selectUserScreen);

        return wizardData;
    }

    public Experiment getExperiment() {
        // @todo: prevent portrate mode
        return wizardController.getExperiment(getWizardData());
    }
}
