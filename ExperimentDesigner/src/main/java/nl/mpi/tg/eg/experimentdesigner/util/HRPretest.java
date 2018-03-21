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
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAboutScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAgreementScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAudioTestScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardCompletionScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardEditUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardTextScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardVideoAudioOptionStimulusScreen;

/**
 * @since Jun 6, 2016 11:41:41 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class HRPretest {
    // @done: add a worker id entry befor the agree screen
    // @done: shift the audio test to InformationScreen1  
    // @done: make the next button appear only after the audio has played
    // @done: remove the Edit_User screen 
    // @done: add the stimuli counter 1/10 at the top of the screen 
    // @done: run the stimuli twice and a fresh randomisation for each run
    // @todo: participant.csv is missing the UUID
    // @todo: add a csv that has a row for each stimuli and the metadata of the user on each row
    // @done: remove the restart button

    private final WizardController wizardController = new WizardController();
    protected boolean showRatingAfterStimuliResponse = false;

    protected String getExperimentName() {
        return "HRPretest";
    }

    final String agreementScreenText = "Alvast bedankt voor uw interesse in dit online-experiment! Gedetailleerde instructies over de taak worden op de volgende pagina gegeven. <br/>"
            + "<br/>"
            + "Voordat u begint, dient u eerst te bevestigen dat u toestemt met deelname aan dit experiment. Let erop dat we uw antwoorden opslaan voor latere analyse. We gebruiken de resultaten alleen voor onderzoeksdoeleinden, en zullen ze beschrijven in gespecialiseerde tijdschriften of wellicht in kranten of op onze website. Echter, we zullen de resultaten NOOIT rapporteren op zo'n manier dat u zou kunnen worden geïdentificeerd. <br/>"
            + "<br/>"
            + "U bent tijdens dit experiment op elk moment vrij om de taak af te breken zonder uitleg te geven. Ook kunt u uw gegevens laten verwijderen tot het moment van publicatie, zonder uit te leggen waarom u dat doet. <br/>"
            + "<br/>"
            + "Er zijn geen risico's bekend van het meedoen aan dit experiment. <br/>"
            + "<br/>"
            + "Als u ermee instemt om door te gaan met dit experiment, klikt u op 'Ik ga akkoord'. Als u besluit niet deel te nemen aan het experiment, klikt u op 'Ik ga niet akkoord'. Verlaat het experiment door naar een andere website te gaan of de pagina af te sluiten."
            + "<br/>";
    final String informationScreenText1 = "Dit online experiment is een luisterexperiment. Daarom vragen we je nu de geluidsinstellingen van je computersysteem te testen door op de grote ronde knop hieronder te klikken.<br/>"
            + "<br/>"
            + "<b>Hoor je geen geluid?</b> Dan is er iets mis met je huidige geluidsinstellingen. Pas deze zelf aan en probeer het nogmaals.<br/>"
            + "<br/>"
            + "<b>Hoor je een geluid?</b> Dan staan je geluidsinstellingen goed ingesteld. Stel zelf het volume van je computersysteem in op een comfortabel niveau.<br/>"
            + "<br/>"
            + "----------------------------------------------------------------<br/>"
            + "LET OP: Doe dit experiment ALLEEN als je in een rustige omgeving zit zonder achtergrondgeluid. Dit is heel belangrijk!<br/>"
            + "----------------------------------------------------------------<br/>"
            + "<br/>"
            + "<br/>"
            + "<br/>"
            + "[Druk pas op VOLGENDE als de geluidsinstellingen goed zijn...]";

    protected String informationScreenText2() {
        return "Dit online experiment is een luisterexperiment. Je krijgt telkens een woord te horen dat ofwel een <b>a-klinker</b> bevat (bijv. dan) ofwel een <b>aa-klinker</b> bevat (bijv. Daan). Jouw taak is om aan te geven welk woord je hoort.<br/>"
                + "<br/>"
                + "Bijvoorbeeld:<br/>"
                + "Je hoort het woord [bas] en daarna verschijnen er twee namen op het scherm:<br/>"
                + "links staat “bas” en rechts staat “baas”.<br/>"
                + "Jouw taak is dan om links op “bas” te klikken.<br/>"
                + "<br/>"
                + "Er zijn ongeveer 800 woorden in dit experiment. Een normale sessie duurt daarom ongeveer 30 minuten. Bovenaan elk scherm staat aangegeven hoe ver je in het experiment bent.<br/>"
                + "<br/>"
                + "Let op: je kunt het experiment NIET pauzeren, onderbreken, of later weer hervatten. Doe dit experiment daarom ALLEEN als je ook echt de tijd hebt ervoor. Voer het experiment volledig en serieus uit.<br/>"
                + "<br/>"
                + "Als het experiment helder is en je klaar bent om te beginnen, druk dan op VOLGENDE.<br/>"
                + "Het experiment start dan METEEN!";
    }
    final String completionScreenText1 = "Dit is het einde van het experiment.<br/>"
            + "<br/>"
            + "<br/>"
            + "Bedankt voor je deelname!";

    protected int repeatCount() {
        return 4;
    }

    protected String getStimulusResponseOptions() {
        return null;
    }

    protected String[] getStimuliString() {
        return new String[]{
            //        "list_1/list_2:AV_happy.mpg:prevoicing9_e_440Hz_coda_k.wav:bik,bek",
            //        "list_2/list_3:AV_sad.mpg:prevoicing9_e_440Hz_coda_t.wav:bid,bed",
            "999:tgt_5_1100Hz_100ms.wav:bas,baas",
            "999:tgt_5_1100Hz_120ms.wav:bas,baas",
            "999:tgt_5_1100Hz_130ms.wav:bas,baas",
            "999:tgt_5_1100Hz_140ms.wav:bas,baas",
            "999:tgt_5_1100Hz_150ms.wav:bas,baas",
            "999:tgt_5_1100Hz_160ms.wav:bas,baas",
            "999:tgt_5_1100Hz_180ms.wav:bas,baas",
            "999:tgt_5_1150Hz_100ms.wav:bas,baas",
            "999:tgt_5_1150Hz_120ms.wav:bas,baas",
            "999:tgt_5_1150Hz_130ms.wav:bas,baas",
            "999:tgt_5_1150Hz_140ms.wav:bas,baas",
            "999:tgt_5_1150Hz_150ms.wav:bas,baas",
            "999:tgt_5_1150Hz_160ms.wav:bas,baas",
            "999:tgt_5_1150Hz_180ms.wav:bas,baas",
            "999:tgt_5_1200Hz_100ms.wav:bas,baas",
            "999:tgt_5_1200Hz_120ms.wav:bas,baas",
            "999:tgt_5_1200Hz_130ms.wav:bas,baas",
            "999:tgt_5_1200Hz_140ms.wav:bas,baas",
            "999:tgt_5_1200Hz_150ms.wav:bas,baas",
            "999:tgt_5_1200Hz_160ms.wav:bas,baas",
            "999:tgt_5_1200Hz_180ms.wav:bas,baas",
            "999:tgt_5_1250Hz_100ms.wav:bas,baas",
            "999:tgt_5_1250Hz_120ms.wav:bas,baas",
            "999:tgt_5_1250Hz_130ms.wav:bas,baas",
            "999:tgt_5_1250Hz_140ms.wav:bas,baas",
            "999:tgt_5_1250Hz_150ms.wav:bas,baas",
            "999:tgt_5_1250Hz_160ms.wav:bas,baas",
            "999:tgt_5_1250Hz_180ms.wav:bas,baas",
            "999:tgt_5_1300Hz_100ms.wav:bas,baas",
            "999:tgt_5_1300Hz_120ms.wav:bas,baas",
            "999:tgt_5_1300Hz_130ms.wav:bas,baas",
            "999:tgt_5_1300Hz_140ms.wav:bas,baas",
            "999:tgt_5_1300Hz_150ms.wav:bas,baas",
            "999:tgt_5_1300Hz_160ms.wav:bas,baas",
            "999:tgt_5_1300Hz_180ms.wav:bas,baas",
            "999:tgt_5_1350Hz_100ms.wav:bas,baas",
            "999:tgt_5_1350Hz_120ms.wav:bas,baas",
            "999:tgt_5_1350Hz_130ms.wav:bas,baas",
            "999:tgt_5_1350Hz_140ms.wav:bas,baas",
            "999:tgt_5_1350Hz_150ms.wav:bas,baas",
            "999:tgt_5_1350Hz_160ms.wav:bas,baas",
            "999:tgt_5_1350Hz_180ms.wav:bas,baas",
            "999:tgt_5_1400Hz_100ms.wav:bas,baas",
            "999:tgt_5_1400Hz_120ms.wav:bas,baas",
            "999:tgt_5_1400Hz_130ms.wav:bas,baas",
            "999:tgt_5_1400Hz_140ms.wav:bas,baas",
            "999:tgt_5_1400Hz_150ms.wav:bas,baas",
            "999:tgt_5_1400Hz_160ms.wav:bas,baas",
            "999:tgt_5_1400Hz_180ms.wav:bas,baas",
            "999:tgt_6_1100Hz_100ms.wav:ad,aad",
            "999:tgt_6_1100Hz_120ms.wav:ad,aad",
            "999:tgt_6_1100Hz_130ms.wav:ad,aad",
            "999:tgt_6_1100Hz_140ms.wav:ad,aad",
            "999:tgt_6_1100Hz_150ms.wav:ad,aad",
            "999:tgt_6_1100Hz_160ms.wav:ad,aad",
            "999:tgt_6_1100Hz_180ms.wav:ad,aad",
            "999:tgt_6_1150Hz_100ms.wav:ad,aad",
            "999:tgt_6_1150Hz_120ms.wav:ad,aad",
            "999:tgt_6_1150Hz_130ms.wav:ad,aad",
            "999:tgt_6_1150Hz_140ms.wav:ad,aad",
            "999:tgt_6_1150Hz_150ms.wav:ad,aad",
            "999:tgt_6_1150Hz_160ms.wav:ad,aad",
            "999:tgt_6_1150Hz_180ms.wav:ad,aad",
            "999:tgt_6_1200Hz_100ms.wav:ad,aad",
            "999:tgt_6_1200Hz_120ms.wav:ad,aad",
            "999:tgt_6_1200Hz_130ms.wav:ad,aad",
            "999:tgt_6_1200Hz_140ms.wav:ad,aad",
            "999:tgt_6_1200Hz_150ms.wav:ad,aad",
            "999:tgt_6_1200Hz_160ms.wav:ad,aad",
            "999:tgt_6_1200Hz_180ms.wav:ad,aad",
            "999:tgt_6_1250Hz_100ms.wav:ad,aad",
            "999:tgt_6_1250Hz_120ms.wav:ad,aad",
            "999:tgt_6_1250Hz_130ms.wav:ad,aad",
            "999:tgt_6_1250Hz_140ms.wav:ad,aad",
            "999:tgt_6_1250Hz_150ms.wav:ad,aad",
            "999:tgt_6_1250Hz_160ms.wav:ad,aad",
            "999:tgt_6_1250Hz_180ms.wav:ad,aad",
            "999:tgt_6_1300Hz_100ms.wav:ad,aad",
            "999:tgt_6_1300Hz_120ms.wav:ad,aad",
            "999:tgt_6_1300Hz_130ms.wav:ad,aad",
            "999:tgt_6_1300Hz_140ms.wav:ad,aad",
            "999:tgt_6_1300Hz_150ms.wav:ad,aad",
            "999:tgt_6_1300Hz_160ms.wav:ad,aad",
            "999:tgt_6_1300Hz_180ms.wav:ad,aad",
            "999:tgt_6_1350Hz_100ms.wav:ad,aad",
            "999:tgt_6_1350Hz_120ms.wav:ad,aad",
            "999:tgt_6_1350Hz_130ms.wav:ad,aad",
            "999:tgt_6_1350Hz_140ms.wav:ad,aad",
            "999:tgt_6_1350Hz_150ms.wav:ad,aad",
            "999:tgt_6_1350Hz_160ms.wav:ad,aad",
            "999:tgt_6_1350Hz_180ms.wav:ad,aad",
            "999:tgt_6_1400Hz_100ms.wav:ad,aad",
            "999:tgt_6_1400Hz_120ms.wav:ad,aad",
            "999:tgt_6_1400Hz_130ms.wav:ad,aad",
            "999:tgt_6_1400Hz_140ms.wav:ad,aad",
            "999:tgt_6_1400Hz_150ms.wav:ad,aad",
            "999:tgt_6_1400Hz_160ms.wav:ad,aad",
            "999:tgt_6_1400Hz_180ms.wav:ad,aad",
            "999:tgt_7_1100Hz_100ms.wav:mart,maart",
            "999:tgt_7_1100Hz_120ms.wav:mart,maart",
            "999:tgt_7_1100Hz_130ms.wav:mart,maart",
            "999:tgt_7_1100Hz_140ms.wav:mart,maart",
            "999:tgt_7_1100Hz_150ms.wav:mart,maart",
            "999:tgt_7_1100Hz_160ms.wav:mart,maart",
            "999:tgt_7_1100Hz_180ms.wav:mart,maart",
            "999:tgt_7_1150Hz_100ms.wav:mart,maart",
            "999:tgt_7_1150Hz_120ms.wav:mart,maart",
            "999:tgt_7_1150Hz_130ms.wav:mart,maart",
            "999:tgt_7_1150Hz_140ms.wav:mart,maart",
            "999:tgt_7_1150Hz_150ms.wav:mart,maart",
            "999:tgt_7_1150Hz_160ms.wav:mart,maart",
            "999:tgt_7_1150Hz_180ms.wav:mart,maart",
            "999:tgt_7_1200Hz_100ms.wav:mart,maart",
            "999:tgt_7_1200Hz_120ms.wav:mart,maart",
            "999:tgt_7_1200Hz_130ms.wav:mart,maart",
            "999:tgt_7_1200Hz_140ms.wav:mart,maart",
            "999:tgt_7_1200Hz_150ms.wav:mart,maart",
            "999:tgt_7_1200Hz_160ms.wav:mart,maart",
            "999:tgt_7_1200Hz_180ms.wav:mart,maart",
            "999:tgt_7_1250Hz_100ms.wav:mart,maart",
            "999:tgt_7_1250Hz_120ms.wav:mart,maart",
            "999:tgt_7_1250Hz_130ms.wav:mart,maart",
            "999:tgt_7_1250Hz_140ms.wav:mart,maart",
            "999:tgt_7_1250Hz_150ms.wav:mart,maart",
            "999:tgt_7_1250Hz_160ms.wav:mart,maart",
            "999:tgt_7_1250Hz_180ms.wav:mart,maart",
            "999:tgt_7_1300Hz_100ms.wav:mart,maart",
            "999:tgt_7_1300Hz_120ms.wav:mart,maart",
            "999:tgt_7_1300Hz_130ms.wav:mart,maart",
            "999:tgt_7_1300Hz_140ms.wav:mart,maart",
            "999:tgt_7_1300Hz_150ms.wav:mart,maart",
            "999:tgt_7_1300Hz_160ms.wav:mart,maart",
            "999:tgt_7_1300Hz_180ms.wav:mart,maart",
            "999:tgt_7_1350Hz_100ms.wav:mart,maart",
            "999:tgt_7_1350Hz_120ms.wav:mart,maart",
            "999:tgt_7_1350Hz_130ms.wav:mart,maart",
            "999:tgt_7_1350Hz_140ms.wav:mart,maart",
            "999:tgt_7_1350Hz_150ms.wav:mart,maart",
            "999:tgt_7_1350Hz_160ms.wav:mart,maart",
            "999:tgt_7_1350Hz_180ms.wav:mart,maart",
            "999:tgt_7_1400Hz_100ms.wav:mart,maart",
            "999:tgt_7_1400Hz_120ms.wav:mart,maart",
            "999:tgt_7_1400Hz_130ms.wav:mart,maart",
            "999:tgt_7_1400Hz_140ms.wav:mart,maart",
            "999:tgt_7_1400Hz_150ms.wav:mart,maart",
            "999:tgt_7_1400Hz_160ms.wav:mart,maart",
            "999:tgt_7_1400Hz_180ms.wav:mart,maart",
            "999:tgt_8_1100Hz_100ms.wav:dan,daan",
            "999:tgt_8_1100Hz_120ms.wav:dan,daan",
            "999:tgt_8_1100Hz_130ms.wav:dan,daan",
            "999:tgt_8_1100Hz_140ms.wav:dan,daan",
            "999:tgt_8_1100Hz_150ms.wav:dan,daan",
            "999:tgt_8_1100Hz_160ms.wav:dan,daan",
            "999:tgt_8_1100Hz_180ms.wav:dan,daan",
            "999:tgt_8_1150Hz_100ms.wav:dan,daan",
            "999:tgt_8_1150Hz_120ms.wav:dan,daan",
            "999:tgt_8_1150Hz_130ms.wav:dan,daan",
            "999:tgt_8_1150Hz_140ms.wav:dan,daan",
            "999:tgt_8_1150Hz_150ms.wav:dan,daan",
            "999:tgt_8_1150Hz_160ms.wav:dan,daan",
            "999:tgt_8_1150Hz_180ms.wav:dan,daan",
            "999:tgt_8_1200Hz_100ms.wav:dan,daan",
            "999:tgt_8_1200Hz_120ms.wav:dan,daan",
            "999:tgt_8_1200Hz_130ms.wav:dan,daan",
            "999:tgt_8_1200Hz_140ms.wav:dan,daan",
            "999:tgt_8_1200Hz_150ms.wav:dan,daan",
            "999:tgt_8_1200Hz_160ms.wav:dan,daan",
            "999:tgt_8_1200Hz_180ms.wav:dan,daan",
            "999:tgt_8_1250Hz_100ms.wav:dan,daan",
            "999:tgt_8_1250Hz_120ms.wav:dan,daan",
            "999:tgt_8_1250Hz_130ms.wav:dan,daan",
            "999:tgt_8_1250Hz_140ms.wav:dan,daan",
            "999:tgt_8_1250Hz_150ms.wav:dan,daan",
            "999:tgt_8_1250Hz_160ms.wav:dan,daan",
            "999:tgt_8_1250Hz_180ms.wav:dan,daan",
            "999:tgt_8_1300Hz_100ms.wav:dan,daan",
            "999:tgt_8_1300Hz_120ms.wav:dan,daan",
            "999:tgt_8_1300Hz_130ms.wav:dan,daan",
            "999:tgt_8_1300Hz_140ms.wav:dan,daan",
            "999:tgt_8_1300Hz_150ms.wav:dan,daan",
            "999:tgt_8_1300Hz_160ms.wav:dan,daan",
            "999:tgt_8_1300Hz_180ms.wav:dan,daan",
            "999:tgt_8_1350Hz_100ms.wav:dan,daan",
            "999:tgt_8_1350Hz_120ms.wav:dan,daan",
            "999:tgt_8_1350Hz_130ms.wav:dan,daan",
            "999:tgt_8_1350Hz_140ms.wav:dan,daan",
            "999:tgt_8_1350Hz_150ms.wav:dan,daan",
            "999:tgt_8_1350Hz_160ms.wav:dan,daan",
            "999:tgt_8_1350Hz_180ms.wav:dan,daan",
            "999:tgt_8_1400Hz_100ms.wav:dan,daan",
            "999:tgt_8_1400Hz_120ms.wav:dan,daan",
            "999:tgt_8_1400Hz_130ms.wav:dan,daan",
            "999:tgt_8_1400Hz_140ms.wav:dan,daan",
            "999:tgt_8_1400Hz_150ms.wav:dan,daan",
            "999:tgt_8_1400Hz_160ms.wav:dan,daan",
            "999:tgt_8_1400Hz_180ms.wav:dan,daan"
//        "AV_happy.mpg",
//        "AV_happy.mpg",
//        "prevoicing9_e_440Hz_coda_k.wav",
//        "prevoicing9_e_440Hz_coda_t.wav"
        };
    }

    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName(getExperimentName());
        wizardData.setShowMenuBar(false);
        wizardData.setTextFontSize(17);
        wizardData.setObfuscateScreenNames(false);
        WizardTextScreen wizardTextScreen2 = new WizardTextScreen("InformationScreen1", informationScreenText2(),
                "volgende [ spatiebalk ]"
        );
        WizardAudioTestScreen wizardTextScreen1 = new WizardAudioTestScreen("AudioTest", informationScreenText1, "volgende [ spatiebalk ]", "welkom");
        //Information screen 
        //Agreement
        WizardAgreementScreen agreementScreen = new WizardAgreementScreen("Agreement", agreementScreenText, "Ik ga akkoord");
//        wizardData.setAgreementText("agreementText");
//        wizardData.setDisagreementScreenText("disagreementScreenText");
        //metadata
        final WizardEditUserScreen wizardEditUserScreen = new WizardEditUserScreen();
        wizardEditUserScreen.setScreenTitle("Edit User");
        wizardEditUserScreen.setMenuLabel("Edit User");
        wizardEditUserScreen.setScreenTag("Edit_User");
        wizardEditUserScreen.setNextButton("Volgende");
        wizardEditUserScreen.setScreenText("Vul hier je login code in:");
        wizardEditUserScreen.setSendData(true);
        wizardEditUserScreen.setOn_Error_Text("Could not contact the server, please check your internet connection and try again.");
        wizardEditUserScreen.setCustomFields(new String[]{
            "workerId:login code:.'{'3,'}':Voer minimaal drie letters."
//            "firstName:Voornaam:.'{'3,'}':Voer minimaal drie letters.",
//            "lastName:Achternaam:.'{'3,'}':Voer minimaal drie letters.",
//            "age:Leeftijd:[0-9]+:Voer een getal.",
//            "gender:Geslacht:|man|vrouw|anders:."
        });

        wizardData.addScreen(wizardEditUserScreen);
        wizardData.addScreen(agreementScreen);
        wizardData.addScreen(wizardTextScreen1);
        wizardData.addScreen(wizardTextScreen2);

        final WizardVideoAudioOptionStimulusScreen list1234Screen = new WizardVideoAudioOptionStimulusScreen("Stimuli", false, getStimuliString(), false, false,
                null, 1000, repeatCount(), 20, true, 100, "", "", false);
        list1234Screen.setShowRatingAfterStimuliResponse(showRatingAfterStimuliResponse);
        list1234Screen.getWizardScreenData().setStimulusResponseOptions(getStimulusResponseOptions());

//        list1234Screen.setStimulusResponseOptions("1,2,3,4,5");
//        list1234Screen.setStimulusResponseLabelLeft("zeer waarschijnlijk negatief");
//        list1234Screen.setStimulusResponseLabelRight("zeer waarschijnlijk positief");
        wizardData.addScreen(list1234Screen);

        WizardCompletionScreen completionScreen = new WizardCompletionScreen(completionScreenText1, false, true,
                null, //Wil nog iemand op dit apparaat deelnemen aan dit onderzoek, klik dan op de onderstaande knop.",
                "Opnieuw beginnen",
                "Finished",
                "Could not contact the server, please check your internet connection and try again.", "Retry");
        wizardData.addScreen(completionScreen);
        final WizardAboutScreen wizardAboutScreen = new WizardAboutScreen("Over", false);
        wizardAboutScreen.setBackWizardScreen(wizardEditUserScreen);
        wizardData.addScreen(wizardAboutScreen);

        wizardEditUserScreen.setNextWizardScreen(agreementScreen);
        agreementScreen.setNextWizardScreen(wizardTextScreen1);
        wizardTextScreen1.setNextWizardScreen(wizardTextScreen2);
        wizardTextScreen2.setNextWizardScreen(list1234Screen);
        list1234Screen.setNextWizardScreen(completionScreen);
        return wizardData;
    }

    public Experiment getExperiment() {
        return wizardController.getExperiment(getWizardData());
    }
}
