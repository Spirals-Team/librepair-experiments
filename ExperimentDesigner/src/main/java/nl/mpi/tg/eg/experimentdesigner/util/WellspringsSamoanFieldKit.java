/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics
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
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.StimuliSubAction;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAboutScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAgreementScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAudioRecorderMetadataScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardEditUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardExistingUserCheckScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardSelectUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardStimulusScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardTextScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardWelcomeScreen;

/**
 * @since Dec 15, 2016 17:05:54 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WellspringsSamoanFieldKit {

    private final WizardController wizardController = new WizardController();

    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName("WellspringsSamoan");
        wizardData.setShowMenuBar(true);
        wizardData.setObfuscateScreenNames(false);

        final PresenterScreen autoMenuPresenter = null;//wizardController.addAutoMenu(experiment, 12, false);//(Blong programa)
        //wizardController.addWelcomeScreen(experiment, autoMenuPresenter, "Welcome", null, 1, "Instructions", "Stat - Go long program nao", false);
        final WizardExistingUserCheckScreen welcomeMenuPresenter = new WizardExistingUserCheckScreen("Start", "Pu’eina fou (new recording)", "Toe fo'i ma fa'auma le pu'eina (go back and finish recording", "Faia pu'eina fou (make new recording)", "Toe fo'i ma fa'auma le pu'eina (go back and finish recording)");
        final WizardTextScreen instructionsPresenter = new WizardTextScreen("Fa’atonuga (instructions)", "This application will make a recording of you. The recording will be archived at the ANU and it will be possible for other people in the world to hear what you say.", "Go long program nao");
        final WizardWelcomeScreen welcomePresenter = new WizardWelcomeScreen("Afio mai (welcome)", "Afio mai (welcome)", "Fa'atonuga (instructions)", "Āmata i le taimi lava (start right away)", welcomeMenuPresenter, instructionsPresenter);
        StimuliSubAction[] featureValuesArray = new StimuliSubAction[]{
            new StimuliSubAction("80", "Askem long man o woman wea i toktok se i talem nem blong wanem i stap lo foto long lanwis blong hem.", "’Uma (finished)")
        };

        final WizardStimulusScreen wizardStimulusScreen = new WizardStimulusScreen();
        wizardStimulusScreen.setScreenTitle("Va’ai i le ata (look at the picture)");
        wizardStimulusScreen.setMenuLabel("Va’ai i le ata (look at the picture)");
//        wizardStimulusScreen.setScreenLabel("Lukluk ol foto");
        wizardStimulusScreen.setEnd_of_stimuli("’Uma (finished)");
        wizardStimulusScreen.setStimulusTagArray(new String[]{"Ata (pictures)"});
        wizardStimulusScreen.setFeatureValuesArray(featureValuesArray);
        wizardStimulusScreen.setMaxStimuli(1000);
//        wizardStimulusScreen.setMaxStimuliPerTag(1000);
        wizardStimulusScreen.setRandomiseStimuli(false);
//        wizardStimulusScreen.setStimulusImageCapture(true);
        wizardStimulusScreen.setFilePerStimulus(true);
        wizardStimulusScreen.setBackWizardScreen(welcomePresenter);
        wizardStimulusScreen.setEndOfStimulisWizardScreen(welcomePresenter);
//        final PresenterScreen vanuatuScreen = wizardController.createStimulusScreen(experiment, welcomePresenter, stimulusScreen, new String[]{"vanuatu"}, featureValuesArray, true, 1000, true, 7, false);
//        final PresenterScreen bowpedStimulusScreen = wizardController.createStimulusScreen(experiment, welcomePresenter, vanuatuScreen, new String[]{"bowped"}, featureValuesArray, true, 1000, true, 9, false);
//        final PresenterScreen bodiesStimulusScreen = wizardController.createStimulusScreen(experiment, welcomePresenter, bowpedStimulusScreen, new String[]{"bodies"}, featureValuesArray, true, 1000, true, 10, false);
        final WizardAudioRecorderMetadataScreen metadataScreen = new WizardAudioRecorderMetadataScreen(new String[]{
            "Igoa/suafa atoa ’o lē ’olo’o faia le fa’atalanoaga, nofaga ma aso o le talanoaga (full name of interviewee and location and date)",
            "Igoa/suafa atoa ’o lē ’olo’o fa’atalanoa (full name of interviewee)",
            "tausaga ma le itūpā tama’ita’i po ’o le ali’i (po ’o le fa’afafine) (age and gender of interviewee)"
        }, "Fa’asolo (continue)"
        //                , "Finis olgeta"
        );
        metadataScreen.setBackWizardScreen(welcomePresenter);
        metadataScreen.setNextWizardScreen(wizardStimulusScreen);
        WizardAgreementScreen wizardTextScreen = new WizardAgreementScreen("Fa’atagaga (consent)", " ’E te malie ’e pu’e le fa’atalanoaga lenei? (Do you agree to this interview being audio taped?)<br/>"
                + "<br/>"
                + "’E te malie ’e fa’aaogā mea ia ’ua pu’e ’e isi tagata ’i nei ma so’o se isi vāega o le lalolagi e fa’alogologo ma matamata, ’ina ’ia mafai ’ona o lātou fa’alogologo ’iā ’oe ’o tautala ’i lau gagana ma fa’amatala au tala? (Are you happy for these recordings to be made available for other people, here and in other parts of the world, to listen to and watch, so they can hear you speaking your language and telling your stories?)<br/>"
                + "<br/>"
                + "’E te mālamalama ’a fa’apea ’e toe sui lou māfaufau ’e tapē le pu’ega ’o au fa’amatalaga, ’e mafai ’ona ’e ta’ua ’iā Heti ’ae le’i toe fo’i ’i Kenipera ’ina ’ia mafai ’ona tapē ’ese ma lē teua? (Do you understand that if you change you mind and want to close off the recordings, you can tell Heti before she goes back to Canberra so that she can delete them and not put them in the archive?)", "Ioe (yes)");

        final WizardSelectUserScreen wizardSelectUserScreen = new WizardSelectUserScreen();
        wizardSelectUserScreen.setBackWizardScreen(welcomePresenter);
        wizardSelectUserScreen.setNextWizardScreen(wizardTextScreen);
        final WizardEditUserScreen editUserPresenter = new WizardEditUserScreen("Infomesen blong man/woman we i toktok", "Edit User", null, "Save information", null, null, null, false, false, "Could not contact the server, please check your internet connection and try again.");
        editUserPresenter.setCustomFields(new String[]{
            "workerId:Igoa/suafa:.'{'3,'}':Please enter at least three letters.",
            "connectionString:connection:.'{'3,'}':Please enter at least three letters.",
            "connectionType:Type:M|F|P|C|S|D:Please select a type."
        });
        final WizardAboutScreen debugScreenPresenter = new WizardAboutScreen(true);
        editUserPresenter.setBackWizardScreen(welcomePresenter);
        wizardTextScreen.setBackWizardScreen(welcomePresenter);
        wizardTextScreen.setNextWizardScreen(metadataScreen);
        editUserPresenter.setNextWizardScreen(wizardTextScreen);
        welcomeMenuPresenter.setNextWizardScreen(editUserPresenter);
        welcomeMenuPresenter.setBackWizardScreen(welcomePresenter);
        instructionsPresenter.setBackWizardScreen(welcomePresenter);
        instructionsPresenter.setNextWizardScreen(welcomeMenuPresenter);
        wizardData.addScreen(welcomePresenter);
        wizardData.addScreen(welcomeMenuPresenter);
        wizardData.addScreen(instructionsPresenter);
        wizardData.addScreen(editUserPresenter);
        wizardData.addScreen(wizardSelectUserScreen);
        wizardData.addScreen(wizardTextScreen);
        wizardData.addScreen(metadataScreen);
        wizardData.addScreen(wizardStimulusScreen);
        wizardData.addScreen(debugScreenPresenter);
        return wizardData;
    }

    public Experiment getExperiment() {
        final Experiment experiment = wizardController.getExperiment(getWizardData());
        experiment.setPrimaryColour4("#DCF4F4");
        return experiment;
    }
}
