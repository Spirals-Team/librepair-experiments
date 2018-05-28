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
import nl.mpi.tg.eg.experimentdesigner.controller.WizardController;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.StimuliSubAction;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAboutScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAudioRecorderMetadataScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardEditUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardExistingUserCheckScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardKinshipScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardMenuScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardSelectUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardStimulusScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardTextScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardWelcomeScreen;
import org.apache.tomcat.util.digester.ArrayStack;

/**
 * @since Feb 22, 2016 4:39:04 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class ShawiFieldKit {

    private final WizardController wizardController = new WizardController();

    public WizardData getWizardData() {
        final WizardData wizardData = new WizardData();
        return wizardData;
    }
    // all filler stimuli must be included for each participant
    // non fillers come in pairs and one of each pair must be shown

    // can we use file name parts to select as tags? eg filler_1.xxx partA_12.xxx partB_12.xxx
    public Experiment getShawiExperiment() {
//       return wizardController.getExperiment(getWizardData());
        Experiment experiment = wizardController.getExperiment("shawifieldkit", "Shawi FieldKit", true);
        boolean obfuscateScreenNames = false;
        experiment.setBackgroundColour("#ffeda0");
        experiment.setPrimaryColour4("#feb24c");
        experiment.setPrimaryColour2("#f03b20");
//        wizardController.addMetadata(experiment);
        final WizardMenuScreen autoMenuPresenter = new WizardMenuScreen("Auto Menu", "Menu", "AutoMenu");
        final WizardMenuScreen experimentMenuPresenter = new WizardMenuScreen("Menu", "Menu", "ExperimentMenu");
        final WizardExistingUserCheckScreen welcomeMenuPresenter = new WizardExistingUserCheckScreen("Start", "New Interview.", "Resume Interview", "Is this a new recording?", "Have you already started a recording and do you want to go back to it?");
        final WizardTextScreen instructionsPresenter = new WizardTextScreen("Instructions", "With this app you can make recordings of your language. "
                + "Describe pictures in this app by speaking and the app records what you say.", "Go directly to program");
        final WizardWelcomeScreen welcomePresenter = new WizardWelcomeScreen("Welcome", "Welcome", "Instructions", "Go directly to program", welcomeMenuPresenter, instructionsPresenter);
        StimuliSubAction[] picturesValuesArray = new StimuliSubAction[]{new StimuliSubAction("80", "the informant talks/says whatever s/he wants", "next")};
        StimuliSubAction[] grammaticalityValuesArray = new StimuliSubAction[]{new StimuliSubAction("80", new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})};
// this should not be random but use alphanum sorting 
        final int maxStimuliPerTag = 1000;
        final int grammaticalityMaxStimuliPerTag = 1;
        final WizardStimulusScreen cutbreakScreen = createStimulusScreen(experiment, experimentMenuPresenter, "cutbreak", experimentMenuPresenter, new String[]{"cutbreak"}, maxStimuliPerTag, grammaticalityValuesArray, true, 1000, false, "end_of_stimuli", 15, obfuscateScreenNames);
        experimentMenuPresenter.addTargetScreen(cutbreakScreen);
        final WizardStimulusScreen grammarScreen = createStimulusScreen(experiment, experimentMenuPresenter, "grammar", experimentMenuPresenter, new String[]{"grammar"}, maxStimuliPerTag, grammaticalityValuesArray, true, 1000, false, "end_of_stimuli", 14, obfuscateScreenNames);
        experimentMenuPresenter.addTargetScreen(grammarScreen);
        final WizardStimulusScreen vanuatuScreen = createStimulusScreen(experiment, experimentMenuPresenter, "vanuatu", experimentMenuPresenter, new String[]{"vanuatu"}, maxStimuliPerTag, grammaticalityValuesArray, true, 1000, false, "end_of_stimuli", 13, obfuscateScreenNames);
        experimentMenuPresenter.addTargetScreen(vanuatuScreen);
        final WizardStimulusScreen bodiesScreen = createStimulusScreen(experiment, experimentMenuPresenter, "bodies", experimentMenuPresenter, new String[]{"bodies"}, maxStimuliPerTag, grammaticalityValuesArray, true, 1000, false, "end_of_stimuli", 12, obfuscateScreenNames);
        experimentMenuPresenter.addTargetScreen(bodiesScreen);
        final WizardStimulusScreen bowpedScreen = createStimulusScreen(experiment, experimentMenuPresenter, "bowped", experimentMenuPresenter, new String[]{"bowped"}, maxStimuliPerTag, grammaticalityValuesArray, true, 1000, false, "end_of_stimuli", 11, obfuscateScreenNames);
        experimentMenuPresenter.addTargetScreen(bowpedScreen);
        final WizardStimulusScreen grammaticalityScreen = createStimulusScreen(experiment, experimentMenuPresenter, "Grammaticality", experimentMenuPresenter, new String[]{"Grammaticality"}, maxStimuliPerTag, grammaticalityValuesArray, true, 1000, false, "end_of_stimuli", 7, obfuscateScreenNames);
        experimentMenuPresenter.addTargetScreen(grammaticalityScreen);
        ArrayList<String> tagList = new ArrayStack<>();
        tagList.add("AntiNominalHierrarchy");
        for (int tagCounter = 1; tagCounter < 6; tagCounter++) {
            tagList.add("Filler" + tagCounter);
        }
        for (int tagCounter = 1; tagCounter < 50; tagCounter++) {
            tagList.add("Critical" + tagCounter);
        }
        final WizardStimulusScreen antiNominalHierrarchy = createStimulusScreen(experiment, experimentMenuPresenter, "Anti Nominal Hierrarchy", experimentMenuPresenter,
                tagList.toArray(new String[]{}),
                // requires all: filler1 filler2 filler3 filler4 filler5 random1_part1 random1_part2 random2_part1 random2_part2 random3_part1 random3_part2 etc
                // for the sake of speed, create a list of stimuli names to generate tags
                grammaticalityMaxStimuliPerTag, grammaticalityValuesArray, true, 1000, false, "end of stimuli", 7, obfuscateScreenNames);
        final WizardStimulusScreen antiNominalHierrarchyFillers = createStimulusScreen(experiment, experimentMenuPresenter, "Anti Nominal Hierrarchy F", antiNominalHierrarchy, new String[]{"AntiNominalHierrarchyFillers"}, maxStimuliPerTag, grammaticalityValuesArray, true, 1000, false, "end_of_stimuli", 7, obfuscateScreenNames);
        experimentMenuPresenter.addTargetScreen(antiNominalHierrarchyFillers);
        final WizardStimulusScreen picturesScreen = createStimulusScreen(experiment, experimentMenuPresenter, "Pictures", experimentMenuPresenter, new String[]{"Pictures"}, maxStimuliPerTag, picturesValuesArray, true, 1000, false, "end_of_stimuli", 8, obfuscateScreenNames);
        experimentMenuPresenter.addTargetScreen(picturesScreen);
        final WizardStimulusScreen animalsScreen = createStimulusScreen(experiment, experimentMenuPresenter, "Animals", experimentMenuPresenter, new String[]{"Animals"}, maxStimuliPerTag, picturesValuesArray, true, 1000, false, "end_of_stimuli", 9, obfuscateScreenNames);
        experimentMenuPresenter.addTargetScreen(animalsScreen);
        final WizardStimulusScreen frogsScreen = createStimulusScreen(experiment, experimentMenuPresenter, "Frogs", experimentMenuPresenter, new String[]{"Frogs"}, maxStimuliPerTag, picturesValuesArray, false, 1000, false, "end_of_stimuli", 10, obfuscateScreenNames);
        experimentMenuPresenter.addTargetScreen(frogsScreen);
        final WizardAudioRecorderMetadataScreen metadataScreen = new WizardAudioRecorderMetadataScreen(new String[]{"Nombre", "Sexo", "Edad", "Estado civil", "Origen", "Lugar de residencia", "Nombre de la comunidad a la que pertenece", "Actividad laboral", "Nivel de estudios", "Número de hijos", "Religión", "Idiomas"}, "next"
        //                , "end of stimuli"
        );
        final WizardSelectUserScreen wizardSelectUserScreen = new WizardSelectUserScreen();
        final WizardEditUserScreen editUserPresenter = new WizardEditUserScreen("Edit User", "Edit User", null, "Save and continue", null, null, null, false, false, "Could not contact the server, please check your internet connection and try again.");
        editUserPresenter.setCustomFields(new String[]{"workerId:Speaker name *:.'{'3,'}':Please enter at least three letters."});
        final WizardAboutScreen wizardAboutScreen = new WizardAboutScreen(true);
        final WizardKinshipScreen kinshipPresenter = addKinshipScreen(experiment, autoMenuPresenter, null, 16, obfuscateScreenNames);
        instructionsPresenter.setBackWizardScreen(welcomePresenter);
        instructionsPresenter.setNextWizardScreen(welcomeMenuPresenter);
        metadataScreen.setBackWizardScreen(autoMenuPresenter);
        metadataScreen.setNextWizardScreen(experimentMenuPresenter);
        experimentMenuPresenter.addTargetScreen(picturesScreen);
        welcomeMenuPresenter.setBackWizardScreen(welcomePresenter);
        welcomeMenuPresenter.setNextWizardScreen(editUserPresenter);
        wizardSelectUserScreen.setBackWizardScreen(welcomePresenter);
        wizardSelectUserScreen.setNextWizardScreen(metadataScreen);
//        welcomeMenuPresenter.setNextWizardScreen(metadataScreen);
        editUserPresenter.setBackWizardScreen(welcomePresenter);
        editUserPresenter.setNextWizardScreen(metadataScreen);
        wizardAboutScreen.setBackWizardScreen(autoMenuPresenter);
        welcomePresenter.setBackWizardScreen(autoMenuPresenter);
        wizardAboutScreen.populatePresenterScreen(wizardAboutScreen.getWizardScreenData(), experiment, obfuscateScreenNames, 17);
        autoMenuPresenter.populatePresenterScreen(autoMenuPresenter.getWizardScreenData(), experiment, obfuscateScreenNames, 18);
        welcomePresenter.populatePresenterScreen(welcomePresenter.getWizardScreenData(), experiment, obfuscateScreenNames, 1);
        welcomeMenuPresenter.populatePresenterScreen(welcomeMenuPresenter.getWizardScreenData(), experiment, obfuscateScreenNames, 2);
        instructionsPresenter.populatePresenterScreen(instructionsPresenter.getWizardScreenData(), experiment, obfuscateScreenNames, 3);
        metadataScreen.populatePresenterScreen(metadataScreen.getWizardScreenData(), experiment, obfuscateScreenNames, 4);
        wizardSelectUserScreen.populatePresenterScreen(wizardSelectUserScreen.getWizardScreenData(), experiment, false, 5);
        editUserPresenter.populatePresenterScreen(editUserPresenter.getWizardScreenData(), experiment, obfuscateScreenNames, 6);
        experimentMenuPresenter.populatePresenterScreen(experimentMenuPresenter.getWizardScreenData(), experiment, obfuscateScreenNames, 7);
        return experiment;
    }

    public WizardStimulusScreen createStimulusScreen(final Experiment experiment, final WizardScreen backPresenter, final String screenLabel, final WizardScreen nextPresenter, final String stimulusTagArray[], final int maxStimuliPerTag, final StimuliSubAction[] featureValuesArray, final boolean randomiseStimuli, final int maxStimuli, boolean filePerStimulus, final String end_of_stimuli, long displayOrder, boolean obfuscateScreenNames) {
        final WizardStimulusScreen wizardStimulusScreen = new WizardStimulusScreen();
        wizardStimulusScreen.setScreenTitle(screenLabel);
        wizardStimulusScreen.setMenuLabel(screenLabel);
//        wizardStimulusScreen.setScreenLabel(screenLabel);
        wizardStimulusScreen.setScreenTag(screenLabel);
        wizardStimulusScreen.setStimulusTagArray(stimulusTagArray);
        wizardStimulusScreen.setFeatureValuesArray(featureValuesArray);
        wizardStimulusScreen.setMaxStimuli(maxStimuli);
        wizardStimulusScreen.setMaxStimuliPerTag(maxStimuliPerTag);
        wizardStimulusScreen.setRandomiseStimuli(randomiseStimuli);
        wizardStimulusScreen.setFilePerStimulus(filePerStimulus);
        wizardStimulusScreen.setEnd_of_stimuli(end_of_stimuli);
        wizardStimulusScreen.setBackWizardScreen(backPresenter);
        wizardStimulusScreen.setEndOfStimulisWizardScreen(nextPresenter);
        wizardStimulusScreen.populatePresenterScreen(wizardStimulusScreen.getWizardScreenData(), experiment, obfuscateScreenNames, displayOrder);
        return wizardStimulusScreen;
    }

    public WizardKinshipScreen addKinshipScreen(final Experiment experiment, final WizardScreen backPresenter, final WizardScreen nextPresenter, long displayOrder, boolean obfuscateScreenNames) {
        final String diagramName = "kinDiagram";
        final String title = "Kinship";
        WizardKinshipScreen kinshipScreen = new WizardKinshipScreen(title, diagramName);
        kinshipScreen.setBackWizardScreen(backPresenter);
        kinshipScreen.populatePresenterScreen(kinshipScreen.getWizardScreenData(), experiment, obfuscateScreenNames, displayOrder);
        return kinshipScreen;
    }
}
