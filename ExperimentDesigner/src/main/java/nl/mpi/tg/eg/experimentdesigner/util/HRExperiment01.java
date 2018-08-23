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

import nl.mpi.tg.eg.experimentdesigner.controller.WizardController;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAgreementScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardCompletionScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardEditUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardTextScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardVideoAudioOptionStimulusScreen;

/**
 * @since Jun 6, 2016 11:41:41 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class HRExperiment01 {

    private final WizardController wizardController = new WizardController();
    final String agreementScreenText = "Agreement Screen Text";
    final String informationScreenText = "Information Screen Text";
    final String completionScreenText1 = "Dit is het einde van het experiment.<br/>"
            + "<br/>"
            + "<br/>"
            + "Bedankt voor je deelname!";
    private final String[] stimuliString = {
        "list_1/list_2:AV_happy.mpg:prevoicing9_e_440Hz_coda_k.wav:bik,bek",
        "list_2/list_3:AV_sad.mpg:prevoicing9_e_440Hz_coda_t.wav:bid,bed"
//        "AV_happy.mpg",
//        "AV_happy.mpg",
//        "prevoicing9_e_440Hz_coda_k.wav",
//        "prevoicing9_e_440Hz_coda_t.wav"
    };

    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName("Online Emotions");
        wizardData.setShowMenuBar(false);
        wizardData.setTextFontSize(17);
        wizardData.setObfuscateScreenNames(false);
        WizardTextScreen wizardTextScreen = new WizardTextScreen("InformationScreen", informationScreenText,
                "volgende [ spatiebalk ]"
        );
        //Information screen 
        //Agreement
        WizardAgreementScreen agreementScreen = new WizardAgreementScreen("Agreement", agreementScreenText, "Akkoord");
//        wizardData.setAgreementText("agreementText");
//        wizardData.setDisagreementScreenText("disagreementScreenText");
        //metadata
        final WizardEditUserScreen wizardEditUserScreen = new WizardEditUserScreen();
        wizardEditUserScreen.setScreenTitle("Edit User");
        wizardEditUserScreen.setMenuLabel("Edit User");
        wizardEditUserScreen.setScreenTag("Edit_User");
        wizardEditUserScreen.setNextButton("Save Details");
        wizardEditUserScreen.setSendData(true);
        wizardEditUserScreen.setOn_Error_Text("Could not contact the server, please check your internet connection and try again.");
//        wizardData.setAgeField(true);
        wizardEditUserScreen.setCustomFields(new String[]{
            "workerId:Arbeider id:.*:.",
            "firstName:Voornaam:.'{'3,'}':Voer minimaal drie letters.",
            "lastName:Achternaam:.'{'3,'}':Voer minimaal drie letters.",
            "age:Leeftijd:[0-9]+:Voer een getal.",
            "gender:Geslacht:|man|vrouw|anders:."
        });

        wizardData.addScreen(agreementScreen);
        wizardData.addScreen(wizardTextScreen);
        wizardData.addScreen(wizardEditUserScreen);

        final WizardVideoAudioOptionStimulusScreen list1234Screen = new WizardVideoAudioOptionStimulusScreen("VideoAudioOption", false, stimuliString, true, false,
                new String[]{"list_2"}, 1000, 1, 0, true, 1000, "", "", false);
//        list1234Screen.setStimulusResponseOptions("1,2,3,4,5");
//        list1234Screen.setStimulusResponseLabelLeft("zeer waarschijnlijk negatief");
//        list1234Screen.setStimulusResponseLabelRight("zeer waarschijnlijk positief");
        wizardData.addScreen(list1234Screen);

        WizardCompletionScreen completionScreen = new WizardCompletionScreen(completionScreenText1, true, true,
                "Wil nog iemand op dit apparaat deelnemen aan dit onderzoek, klik dan op de onderstaande knop.",
                "Opnieuw beginnen",
                "Finished",
                "Could not contact the server, please check your internet connection and try again.", "Retry");
        wizardData.addScreen(completionScreen);

        wizardTextScreen.setNextWizardScreen(wizardEditUserScreen);
        agreementScreen.setNextWizardScreen(wizardTextScreen);
        wizardEditUserScreen.setNextWizardScreen(list1234Screen);
        list1234Screen.setNextWizardScreen(completionScreen);
        completionScreen.setNextWizardScreen(wizardTextScreen);
        return wizardData;
    }

    public Experiment getExperiment() {
        return wizardController.getExperiment(getWizardData());
    }
}
