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
 * @since Dec 22, 2015 11:07:54 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class JenaFieldKit {

    private final WizardController wizardController = new WizardController();

    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName("Vanuatu FieldKit");
        wizardData.setShowMenuBar(true);
        wizardData.setObfuscateScreenNames(false);
//        wizardController.addMetadata(experiment);
        final WizardExistingUserCheckScreen welcomeMenuPresenter = new WizardExistingUserCheckScreen("Start", "Niu rikording", "Gobak long wan olfala rikoding", "Makem wan niufala rikoding", "Gobak long wan rikoding we yu stat hem finis");
        final WizardTextScreen instructionsPresenter = new WizardTextScreen("Instruksen", "Wetem aplikasen ia yu save makem rikoding blong lanwis blong yu,"
                + " bambai ol pipol blong Vanuatu mo ol pipol blong evri ples long world save harem lanwis blong yu. I gat fulap foto blong difren ples long Malakula wea i stap insaed long aplikasen ia. "
                + "Bai yu showem ol foto ia long wan olfala woman o wan olfala man blong vilej blong yu mo askem long hem se i tokabaot ol foto ia long lanwis blong hem. Yu save transletem wanem i talem, tu.", "Go long program nao");

//        final PresenterScreen autoMenuPresenter = null;//wizardController.addAutoMenu(experiment, 12, false);//(Blong programa)
        final WizardWelcomeScreen welcomePresenter = new WizardWelcomeScreen("Welkam", "Welkam", "Instruksen", "Stat - Go long program nao", welcomeMenuPresenter, instructionsPresenter);
//wizardController.addWelcomeScreen(experiment, autoMenuPresenter, "Welkam", null, 1, "Instruksen", "Stat - Go long program nao", false);
        StimuliSubAction[] featureValuesArray = new StimuliSubAction[]{new StimuliSubAction("80", "Askem long man o woman wea i toktok se i talem nem blong wanem i stap lo foto long lanwis blong hem.", "Finis"),
            new StimuliSubAction("60", "Noaia yu toktok. I talem wanem? Givim wan translesen long Bislama o Inglis o Franis.", "Finis"),
            new StimuliSubAction("80", "Askem long man o woman we i toktok se i gat sam stori blong laef blong hem long saed blong ting ia we i stap long foto ia.", "Finis"),
            new StimuliSubAction("60", "Noaia yu toktok. I talem wanem? Givim wan translesen long Bislama o Inglis o Franis.", "Finis"),
            new StimuliSubAction("80", "Askem long man o woman we i toktok se i save wan kastom stori abaot ting ia we i stap long foto ia.", "Finis"),
            new StimuliSubAction("60", "Noaia yu toktok. I talem wanem? Givim wan translesen long Bislama o Inglis o Franis.", "Finis")
        };

        final WizardStimulusScreen captureStimulusScreen = new WizardStimulusScreen("Capture");
        captureStimulusScreen.setScreenTitle("Mekem ol foto");
        captureStimulusScreen.setMenuLabel("Mekem ol foto");
//        captureStimulusScreen.setScreenLabel("Mekem ol foto");
        captureStimulusScreen.setEnd_of_stimuli("Finis olgeta");
        captureStimulusScreen.setStimulusTagArray(new String[]{"Pictures"});
//        captureStimulusScreen.setStimulusArray(new String[]{"Pictures"});
        captureStimulusScreen.setFeatureValuesArray(featureValuesArray);
        captureStimulusScreen.setMaxStimuli(1000);
        captureStimulusScreen.setRandomiseStimuli(true);
        captureStimulusScreen.setStimulusImageCapture(true);
        captureStimulusScreen.setFilePerStimulus(true);
        captureStimulusScreen.setEndOfStimulisWizardScreen(welcomePresenter);

        final WizardStimulusScreen wizardStimulusScreen = new WizardStimulusScreen();
        wizardStimulusScreen.setScreenTitle("Lukluk ol foto");
        wizardStimulusScreen.setMenuLabel("Lukluk ol foto");
//        wizardStimulusScreen.setScreenLabel("Lukluk ol foto");
        wizardStimulusScreen.setEnd_of_stimuli("Finis olgeta");
        wizardStimulusScreen.setStimulusTagArray(new String[]{"Pictures"});
//        wizardStimulusScreen.setStimulusArray(new String[]{"Pictures"});
        wizardStimulusScreen.setFeatureValuesArray(featureValuesArray);
        wizardStimulusScreen.setMaxStimuli(1000);
        wizardStimulusScreen.setRandomiseStimuli(true);
//        wizardStimulusScreen.setStimulusImageCapture(true);
        wizardStimulusScreen.setFilePerStimulus(true);
        wizardStimulusScreen.setBackWizardScreen(welcomePresenter);
        wizardStimulusScreen.setEndOfStimulisWizardScreen(welcomePresenter);

//        final PresenterScreen vanuatuScreen = wizardController.createStimulusScreen(experiment, welcomePresenter, stimulusScreen, new String[]{"vanuatu"}, featureValuesArray, true, 1000, true, 7, false);
//        final PresenterScreen bowpedStimulusScreen = wizardController.createStimulusScreen(experiment, welcomePresenter, vanuatuScreen, new String[]{"bowped"}, featureValuesArray, true, 1000, true, 9, false);
//        final PresenterScreen bodiesStimulusScreen = wizardController.createStimulusScreen(experiment, welcomePresenter, bowpedStimulusScreen, new String[]{"bodies"}, featureValuesArray, true, 1000, true, 10, false);
        final WizardAudioRecorderMetadataScreen metadataScreen = new WizardAudioRecorderMetadataScreen(new String[]{
            "I stap rikod nao. Man o woman we i toktok bai i talem nem blong hem.",
            "Bai i talem nem blong lanwis wea it toktok long hem.",
            "Bai i talem nem blong ples wea i stap nao.",
            "Bai i talem nem blong ples wea i bon long hem.",
            "Bai i talem wanem yea i bon."
        }, "Neks"
        //                , "Finis olgeta"
        );
        metadataScreen.setBackWizardScreen(welcomePresenter);
        metadataScreen.setNextWizardScreen(captureStimulusScreen);
        captureStimulusScreen.setNextWizardScreen(wizardStimulusScreen);
        WizardAgreementScreen wizardTextScreen = new WizardAgreementScreen("Konsen", "(Blong man/woman we i makem rikoding)<br><br>"
                + "Mi undastan se wetem aplikasen ia mi makem wan rikoding; mo mi undastan se rikoding ia bai i stap long intanet bambai ol man mo ol woman long evri kantri i save harem rikoding ia wea mi stap makem nao.<br><br>",
                "Prestem ples hea sapos yu agri.");

        captureStimulusScreen.setBackWizardScreen(wizardTextScreen);
//        final PresenterScreen consentPresenter = wizardTextScreen.populatePresenterScreen(experiment, false, 6);
        final WizardSelectUserScreen wizardSelectUserScreen = new WizardSelectUserScreen();
        wizardSelectUserScreen.setBackWizardScreen(welcomePresenter);
        wizardSelectUserScreen.setNextWizardScreen(wizardTextScreen);
//        final PresenterScreen selectUserPresenter = wizardSelectUserScreen.populatePresenterScreen(experiment, false, 5);
        final WizardEditUserScreen editUserPresenter = new WizardEditUserScreen("Infomesen blong man/woman we i toktok", "Edit User", null, "Savem infomesen", null, null, null, false, false, "Could not contact the server, please check your internet connection and try again.");
        editUserPresenter.setCustomFields(new String[]{"workerId:Nem blong man/woman we i toktok:.'{'3,'}':Please enter at least three letters."});
        final WizardAboutScreen debugScreenPresenter = new WizardAboutScreen(true);
        editUserPresenter.setBackWizardScreen(welcomePresenter);
        wizardTextScreen.setBackWizardScreen(welcomePresenter);
        wizardTextScreen.setNextWizardScreen(metadataScreen);
        editUserPresenter.setNextWizardScreen(wizardTextScreen);
        welcomeMenuPresenter.setNextWizardScreen(editUserPresenter);
        welcomeMenuPresenter.setBackWizardScreen(welcomePresenter);
        instructionsPresenter.setBackWizardScreen(welcomePresenter);
//        instructionsPresenter.setNextWizardScreen(metadataScreen);
        instructionsPresenter.setNextWizardScreen(welcomeMenuPresenter);
//        wizardData.addScreen(instructionsPresenter);
//        welcomePresenter.populatePresenterScreen(experiment, false, 1);
        wizardData.addScreen(welcomePresenter);
//        welcomeMenuPresenter.populatePresenterScreen(experiment, false, 2);
        wizardData.addScreen(welcomeMenuPresenter);
//        instructionsPresenter.populatePresenterScreen(experiment, false, 3);
        wizardData.addScreen(instructionsPresenter);
//        editUserPresenter.populatePresenterScreen(experiment, false, 4);
        wizardData.addScreen(editUserPresenter);
//        wizardSelectUserScreen.populatePresenterScreen(experiment, false, 5);
        wizardData.addScreen(wizardSelectUserScreen);
//        wizardTextScreen.populatePresenterScreen(experiment, false, 6);
        wizardData.addScreen(wizardTextScreen);
//        metadataScreen.populatePresenterScreen(experiment, false, 7);
        wizardData.addScreen(metadataScreen);
//        wizardStimulusScreen.populatePresenterScreen(experiment, false, 8);
        wizardData.addScreen(wizardStimulusScreen);
        wizardData.addScreen(captureStimulusScreen);
//        debugScreenPresenter.populatePresenterScreen(experiment, false, 11);
        wizardData.addScreen(debugScreenPresenter);
        return wizardData;
    }

    public Experiment getExperiment() {
        final Experiment experiment = wizardController.getExperiment(getWizardData());
        experiment.setAppNameInternal("VanuatuFieldKit");
        return experiment;
    }
}
