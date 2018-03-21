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
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardEditUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardKinDiagramScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardMenuScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardTextScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardVideoAudioOptionStimulusScreen;

/**
 * @since Jun 6, 2016 11:41:41 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class KinOathExample {

    private final WizardController wizardController = new WizardController();
    final String informationScreenText = "This is an example experiment that demostrates some kinship diagram interactivity.";

    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName("Kinship Example");
        wizardData.setShowMenuBar(true);
        wizardData.setTextFontSize(17);
        wizardData.setObfuscateScreenNames(false);
        WizardTextScreen introTextScreen = new WizardTextScreen("InformationScreen", informationScreenText,
                "volgende [ spatiebalk ]"
        );

        final WizardMenuScreen autoMenuPresenter = new WizardMenuScreen("Auto Menu", "Menu", "AutoMenu");

        wizardData.addScreen(introTextScreen);
        wizardData.addScreen(autoMenuPresenter);
        final WizardKinDiagramScreen predefinedKinDiagram = new WizardKinDiagramScreen(WizardKinDiagramScreen.ExampleType.PredefinedKinDiagram);
        wizardData.addScreen(predefinedKinDiagram);
        final WizardKinDiagramScreen savedKinDiagram = new WizardKinDiagramScreen(WizardKinDiagramScreen.ExampleType.SavedKinDiagram);
        wizardData.addScreen(savedKinDiagram);
        final WizardKinDiagramScreen editableEntitesDiagram = new WizardKinDiagramScreen(WizardKinDiagramScreen.ExampleType.EditableEntitesDiagram);
        wizardData.addScreen(editableEntitesDiagram);

        predefinedKinDiagram.setBackWizardScreen(autoMenuPresenter);
        savedKinDiagram.setBackWizardScreen(autoMenuPresenter);
        editableEntitesDiagram.setBackWizardScreen(autoMenuPresenter);
        introTextScreen.setNextWizardScreen(autoMenuPresenter);
        autoMenuPresenter.setBackWizardScreen(introTextScreen);

        // some metadata fields are required even if the following screen is not used
        final WizardEditUserScreen wizardEditUserScreen = new WizardEditUserScreen();
        wizardEditUserScreen.setScreenTitle("Edit User");
        wizardEditUserScreen.setMenuLabel("Edit User");
        wizardEditUserScreen.setScreenTag("Edit_User");
        wizardEditUserScreen.setNextButton("Save Details");
        wizardEditUserScreen.setSendData(true);
        wizardEditUserScreen.setOn_Error_Text("Could not contact the server, please check your internet connection and try again.");
//        wizardEditUserScreen.setMetadataScreen(true);
//        wizardData.setAgeField(true);
        wizardEditUserScreen.setCustomFields(new String[]{
            "workerId:Arbeider id:.*:.",
            "firstName:Voornaam:.'{'3,'}':Voer minimaal drie letters.",
            "lastName:Achternaam:.'{'3,'}':Voer minimaal drie letters.",
            "age:Leeftijd:[0-9]+:Voer een getal.",
            "gender:Geslacht:|man|vrouw|anders:."
        });
        wizardData.addScreen(wizardEditUserScreen);
        wizardEditUserScreen.setBackWizardScreen(autoMenuPresenter);

        String[] stimuliString = {
            "list_1/list_2:AV_happy.mpg:prevoicing9_e_440Hz_coda_k.wav:bik,bek",
            "list_2/list_3:AV_sad.mpg:prevoicing9_e_440Hz_coda_t.wav:bid,bed"
        };
        final WizardVideoAudioOptionStimulusScreen list1234Screen = new WizardVideoAudioOptionStimulusScreen("VideoAudioOption", false, stimuliString, true, false, new String[]{"list_2"}, 1000, 1, 0, true, 100, "", "", false);
        wizardData.addScreen(list1234Screen);
        list1234Screen.setBackWizardScreen(autoMenuPresenter);
        return wizardData;
    }

    public Experiment getExperiment() {
        return wizardController.getExperiment(getWizardData());
    }
}
