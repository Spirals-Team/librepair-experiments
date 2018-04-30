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
package nl.mpi.tg.eg.experimentdesigner.util;

import nl.mpi.tg.eg.experimentdesigner.controller.WizardController;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAboutScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAgreementScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardCompletionScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardEditUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardExistingUserCheckScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardMenuScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardRandomStimulusScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardSelectUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardTextScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardUtilData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardUtilScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardUtilSelectParticipant;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardUtilStimuliData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardUtilText;

/**
 * @since Nov 16, 2017 3:25:23 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class SentenceCompletion {

    private final WizardController wizardController = new WizardController();
    private final WizardUtilData wizardUtilData;

    public SentenceCompletion(WizardUtilData wizardUtilData) {
        this.wizardUtilData = wizardUtilData;
    }

    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName(wizardUtilData.getExperimentTitle());
        wizardData.setShowMenuBar(wizardUtilData.isShowMenuBar());
        wizardData.setTextFontSize(17);
        wizardData.setObfuscateScreenNames(false);
        WizardMenuScreen menuScreen = null;
        if (wizardUtilData.getMainMenuTitle() != null) {
            menuScreen = new WizardMenuScreen(wizardUtilData.getMainMenuTitle(), wizardUtilData.getMainMenuTitle(), "Menu");
        }
        WizardScreen firstScreen = null;
        WizardScreen lastScreen = null;

//        WizardTextScreen wizardTextScreen = new WizardTextScreen("Informatie", wizardUtilData.getInstructionsText(),
//                "volgende [ spatiebalk ]"
//        );
//        wizardTextScreen.setMenuLabel("Terug");
        //Information screen 
        //Agreement
//        WizardAgreementScreen agreementScreen = new WizardAgreementScreen("Toestemming", wizardUtilData.getAgreementText(), "Akkoord");
//        agreementScreen.setMenuLabel("Terug");
//        wizardData.addScreen(agreementScreen);
//        wizardData.addScreen(wizardTextScreen);
//        if (lastScreen != null) {
//            lastScreen.setNextWizardScreen(wizardTextScreen);
//        }
//        lastScreen = wizardTextScreen;
//        wizardData.setAgreementText("agreementText");
//        wizardData.setDisagreementScreenText("disagreementScreenText");
        for (WizardUtilScreen screenData : wizardUtilData.getScreenData()) {
            final WizardUtilText introductionText = screenData.getTextScreen();
            if (introductionText != null) {
                WizardTextScreen introductionScreen = new WizardTextScreen(introductionText.getTitle(), introductionText.getText(),
                        introductionText.getButonLabel()
                );
                introductionScreen.setMenuLabel(introductionText.getMenuLabel());
                if (introductionText.getHotkey() != null) {
                    introductionScreen.setNextHotKey(introductionText.getHotkey());
                }
                wizardData.addScreen(introductionScreen);
                if (menuScreen != null) {
                    introductionScreen.setBackWizardScreen(menuScreen);
                } else if (lastScreen != null) {
                    introductionScreen.setBackWizardScreen(lastScreen);
                }
                if (lastScreen != null) {
                    lastScreen.setNextWizardScreen(introductionScreen);
                }
                lastScreen = introductionScreen;
                if (firstScreen == null) {
                    firstScreen = lastScreen;
                }
            }
            final WizardUtilText agreementScreenText = screenData.getAgreementScreen();
            if (agreementScreenText != null) {
                WizardAgreementScreen agreementScreen = new WizardAgreementScreen(agreementScreenText.getTitle(), agreementScreenText.getText(),
                        agreementScreenText.getButonLabel()
                );
                agreementScreen.setMenuLabel(agreementScreenText.getMenuLabel());
                wizardData.addScreen(agreementScreen);
                if (lastScreen != null) {
                    lastScreen.setNextWizardScreen(agreementScreen);
                }
                lastScreen = agreementScreen;
                if (firstScreen == null) {
                    firstScreen = lastScreen;
                }
            }
            final WizardUtilSelectParticipant selectParticipantMenu = screenData.getSelectParticipantMenu();
            if (selectParticipantMenu != null) {
                final WizardExistingUserCheckScreen existingUserCheckScreen = new WizardExistingUserCheckScreen(
                        selectParticipantMenu.getSelectParticipantTitle(),
                        selectParticipantMenu.getNewParticipantButton(),
                        selectParticipantMenu.getResumeParticipantButton(),
                        selectParticipantMenu.getNewParticipantText(),
                        selectParticipantMenu.getResumeParticipantText());
                final WizardSelectUserScreen selectUserScreen = new WizardSelectUserScreen(selectParticipantMenu.getSelectParticipantTitle());
                wizardData.addScreen(existingUserCheckScreen);
                wizardData.addScreen(selectUserScreen);
                if (lastScreen != null) {
                    lastScreen.setNextWizardScreen(existingUserCheckScreen);
                }
                if (firstScreen == null) {
                    firstScreen = existingUserCheckScreen;
                }
            }
            final String[] metadataFields = screenData.getMetadataFields();
            if (metadataFields != null) {
                //metadata
                final WizardEditUserScreen wizardEditUserScreen = new WizardEditUserScreen();
                wizardEditUserScreen.setScreenText(screenData.getMetadataText());
                wizardEditUserScreen.setScreenTitle("Gegevens");
                wizardEditUserScreen.setMenuLabel("Terug");
                wizardEditUserScreen.setScreenTag("Gegevens");
                wizardEditUserScreen.setNextButton("Volgende");
                wizardEditUserScreen.setSendData(true);
                wizardEditUserScreen.setOn_Error_Text("Geen verbinding met de server. Controleer alstublieft uw internetverbinding en probeer het opnieuw.");
//        wizardData.setAgeField(true);
                wizardEditUserScreen.setCustomFields(metadataFields);

                if (lastScreen != null) {
                    lastScreen.setNextWizardScreen(wizardEditUserScreen);
                    wizardEditUserScreen.setBackWizardScreen(lastScreen);
                }
                wizardData.addScreen(wizardEditUserScreen);
                lastScreen = wizardEditUserScreen;
                if (firstScreen == null) {
                    firstScreen = lastScreen;
                }
            }
//            WizardScreenData firstStimuliScreen = wizardEditUserScreen.getWizardScreenData();
            final WizardUtilStimuliData stimuliData = screenData.getStimuliData();
            if (stimuliData != null) {
                if (stimuliData.getInstructions() != null) {
                    WizardTextScreen stimulusInstructionsScreen = new WizardTextScreen(stimuliData.getStimuliName() + " Informatie", stimuliData.getInstructions(),
                            "volgende [ spatiebalk ]"
                    );
                    stimulusInstructionsScreen.setMenuLabel("Terug");
                    wizardData.addScreen(stimulusInstructionsScreen);
//                    stimulusInstructionsScreen.setNextWizardScreen(wizardEditUserScreen);
//                    if (lastScreen != null) {
//                        agreementScreen.setNextWizardScreen(stimulusInstructionsScreen);
//                    }
                    if (lastScreen != null) {
                        lastScreen.setNextWizardScreenData(stimulusInstructionsScreen.getWizardScreenData());
                    }
                    lastScreen = stimulusInstructionsScreen;
                    if (firstScreen == null) {
                        firstScreen = lastScreen;
                    }
                }
                final WizardRandomStimulusScreen list1234Screen = new WizardRandomStimulusScreen(stimuliData.getStimuliName(), false, stimuliData.getStimuliArray(),
                        stimuliData.getRandomStimuliTags(), 1000, true, null, 0, 0, null, null, null, null, "Volgende [tab + enter]");
                if ("horizontal".equals(stimuliData.getStimuliLayout())) {
                    list1234Screen.setTableLayout(true);
                }
                if (stimuliData.getRatingLabels() != null) {
                    list1234Screen.getWizardScreenData().setStimulusResponseOptions(stimuliData.getRatingLabels());
                } else {
                    list1234Screen.setStimulusFreeText(true,
                            (stimuliData.getFreeTextValidationRegex() == null) ? ".{2,}" : stimuliData.getFreeTextValidationRegex(),
                            stimuliData.getFreeTextValidationMessage()
                    );
                    list1234Screen.setAllowedCharCodes(stimuliData.getFreeTextAllowedCharCodes());
                    list1234Screen.setInputKeyErrorMessage("Sorry, dit teken is niet toegestaan.");
                }
                list1234Screen.getWizardScreenData().setStimulusResponseLabelLeft("");
                list1234Screen.getWizardScreenData().setStimulusResponseLabelRight("");
                list1234Screen.setRandomStimuliTagsField("item");
                list1234Screen.setStimuliLabelStyle(stimuliData.getStimuliLabelStyle());
                list1234Screen.setHotkeyButton(stimuliData.getStimuliHotKey());
                if (wizardUtilData.isShowProgress()) {
                    list1234Screen.setShowProgress(true);
                }
                wizardData.addScreen(list1234Screen);
                if (lastScreen != null) {
                    lastScreen.setNextWizardScreenData(list1234Screen.getWizardScreenData());
                }
                lastScreen = list1234Screen;
                if (firstScreen == null) {
                    firstScreen = lastScreen;
                }
            }
        }
        // @todo: remove the restart button
        // 
        WizardCompletionScreen completionScreen = new WizardCompletionScreen(wizardUtilData.getDebriefingText1(), wizardUtilData.isAllowUserRestart(), true,
                //                "Wil nog iemand op dit apparaat deelnemen aan dit onderzoek, klik dan op de onderstaande knop.",
                wizardUtilData.getDebriefingText2(),
                "Opnieuw beginnen",
                "Einde van het experiment",
                "Geen verbinding met de server. Controleer alstublieft uw internetverbinding en probeer het opnieuw.",
                "Probeer opnieuw");
        wizardData.addScreen(completionScreen);
        completionScreen.setScreenTag("completion");

        if (wizardUtilData.getFeedbackScreenText() != null) {
            final WizardEditUserScreen wizardFeedbackScreen = new WizardEditUserScreen();
            wizardFeedbackScreen.setScreenTitle("Opmerkingen");
            wizardFeedbackScreen.setScreenTag("opmerkingen");
            wizardFeedbackScreen.setMenuLabel("Opmerkingen");
            wizardFeedbackScreen.setScreenText(wizardUtilData.getFeedbackScreenText());
            wizardFeedbackScreen.setSendData(true);
            wizardFeedbackScreen.setNextButton("Volgende");
            wizardFeedbackScreen.setOn_Error_Text("Geen verbinding met de server. Controleer alstublieft uw internetverbinding en probeer het opnieuw.");
            wizardFeedbackScreen.setCustomFields(new String[]{
                "feedBack::.*:."
            });
            wizardData.addScreen(wizardFeedbackScreen);
            if (lastScreen != null) {
                lastScreen.setNextWizardScreen(wizardFeedbackScreen);
            }
            wizardFeedbackScreen.setNextWizardScreen(completionScreen);
        } else {
            if (lastScreen != null) {
                lastScreen.setNextWizardScreen(completionScreen);
            }
        }
//        agreementScreen.setNextWizardScreen(wizardTextScreen);
        completionScreen.setNextWizardScreen(firstScreen);

//        wizardTextScreen.setBackWizardScreen(agreementScreen);
//        list1234Screen.setBackWizardScreen(wizardEditUserScreen);
        //completionScreen.setBackWizardScreen(list1234Screen);
        final WizardAboutScreen wizardAboutScreen = new WizardAboutScreen("Over", false);
        wizardAboutScreen.setBackWizardScreen(firstScreen);
        if (menuScreen != null) {
            // the menu screen should not be the first screen
            wizardData.getWizardScreens().add(1, menuScreen.getWizardScreenData());
        }
        wizardData.addScreen(wizardAboutScreen);

        return wizardData;
    }

    public Experiment getExperiment() {
        return wizardController.getExperiment(getWizardData());
    }
}
