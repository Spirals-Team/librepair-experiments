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
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardMenuScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardStimuliJsonMetadataScreen;

/**
 * @since Mar 7, 2017 11:44:39 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class NonWacq {

    // JSON file per stimulus filled with the form like input (probably metadatata fields)
    /*    
    {
        DateCreated: blaDate,
        DateSaved: blaDate,
        Coder: Cder,
        ChildId: blaC,
        Informant: bla2,
        StimulusId: blaId,
        ChildSpeaks: true,
        WhoSpeaks: Grandma,
        Understandable: true,
        IsChildAddressed: true,
        WhichLanguage: blaL,
        LanguageLat: 180,
        LanguageLon: 10,
        TimesPlayed: 2,
        TimesContextPlayed: 1,
    }
     */
    // the JSON should be structured 
    // the JSON really should be loaded into the fields when the user navigates stimuli
    // probablly should be a date saved stored in the JSON and that would be also shown / loaded
    // the next and previous stimuli should have rewind to start could also have skip 10 or 100 etc (nice to have)
    // some input fields that go into the json file are stored across sessions to be repopulated but can be changed at any time
    // label for the current stimulus file name minus suffix
    // two audio files are playable from each screen, base and context where context has an additional file name part eg  _context 
    // add a geolocated image with click and pointer fir lat and lon
//    <metadata> perhaps JSON data tag <jsondata>
    // file name = stimuli base name + .json
    // some input fields that go into the json file are stored across sessions to be repopulated but can be changed at any time
//        <field controlledMessage="Please enter at least three letters." controlledRegex=".'{'3,'}'" postName="workerId" preventServerDuplicates="false" registrationField="Participant ID"/>
//        an attribute should indicate if the value of a field is included in the JSON file name
//        <field controlledMessage="Please enter at least three letters." controlledRegex=".'{'3,'}'" postName="connectionString" preventServerDuplicates="false" registrationField="connection"/>
//    </metadata>
    // two videos, one of just the utterance, the other with the context
    // perhaps record the number of times each recording is played
    // fields to collect data like, speaker, language, location
    // perhaps a popup up map to click on to show the location (record x, y and convert to lat,log later)(or simply use an svg with a window over the lat, long area and coast lines of the actual lat,lon)
    // perhaps a popup of household individuals to click on, if it can be done cross culturally
    // for now just collect the string name of individuals, but in the long term have a separate screen to define relations and use kin type strings to select an individual or add an individual
    private final WizardController wizardController = new WizardController();
    private final String[] stimuliString = {
        //        "UttAnnotApp-Logo.png",
        //        "UttAnnotApp-Logo512.png", 
        "d1e128.jpg",
        "d1e140.jpg",
        "d1e152.jpg",
        "videotag2.png",
        "videotag6.png",
        "d1e131.jpg",
        "d1e143.jpg",
        "d1e155.jpg",
        "videotag3.png",
        "videotag7.png",
        "d1e134.jpg",
        "d1e146.jpg",
        "videotag0.png",
        "videotag4.png",
        "videotag8.png",
        "d1e137.jpg",
        "d1e149.jpg",
        "videotag1.png",
        "videotag5.png",
        "videotag9.png"
    };

    private WizardStimuliJsonMetadataScreen getJsonMetadataScreenrScreen(final String screenName) {
        final WizardStimuliJsonMetadataScreen jsonMetadataScreenrScreen = new WizardStimuliJsonMetadataScreen(stimuliString);
        jsonMetadataScreenrScreen.setScreenTitle(screenName);
        jsonMetadataScreenrScreen.setMenuLabel(screenName);
        jsonMetadataScreenrScreen.setScreenTag(screenName);
        jsonMetadataScreenrScreen.setNextButton("Volgende");
        jsonMetadataScreenrScreen.getWizardScreenData().getMetadataFields();

        jsonMetadataScreenrScreen.addStimuliMetadataField("DateCreated", "DateCreated");
        jsonMetadataScreenrScreen.addStimuliMetadataField("DateSaved", "DateSaved");
        jsonMetadataScreenrScreen.addStimuliMetadataField("Coder", "Coder");
        jsonMetadataScreenrScreen.addStimuliMetadataField("ChildId", "ChildId");
        jsonMetadataScreenrScreen.addStimuliMetadataField("Informant", "Informant");
        jsonMetadataScreenrScreen.addStimuliMetadataField("StimulusId", "StimulusId");
        jsonMetadataScreenrScreen.addStimuliBooleanMetadataField("ChildSpeaks", "ChildSpeaks");
        jsonMetadataScreenrScreen.addStimuliMetadataField("WhoSpeaks", "WhoSpeaks");
        jsonMetadataScreenrScreen.addStimuliBooleanMetadataField("Understandable", "Understandable");
        jsonMetadataScreenrScreen.addStimuliBooleanMetadataField("IsChildAddressed", "IsChildAddressed");
        jsonMetadataScreenrScreen.addStimuliMetadataField("WhichLanguage", "WhichLanguage");
        jsonMetadataScreenrScreen.addStimuliMetadataField("LanguageLat", "LanguageLat");
        jsonMetadataScreenrScreen.addStimuliMetadataField("LanguageLon", "LanguageLon");
        jsonMetadataScreenrScreen.addStimuliMetadataField("TimesPlayed", "TimesPlayed");
        jsonMetadataScreenrScreen.addStimuliMetadataField("TimesContextPlayed", "TimesContextPlayed");
        return jsonMetadataScreenrScreen;
    }

    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName("NonWacq");
        wizardData.setShowMenuBar(true);
        wizardData.setTextFontSize(17);
        wizardData.setObfuscateScreenNames(false);
        final WizardStimuliJsonMetadataScreen jsonMetadataScreenrScreenSdCard = getJsonMetadataScreenrScreen("SdCard Stimuli");
        final WizardStimuliJsonMetadataScreen jsonMetadataScreenrScreenSample = getJsonMetadataScreenrScreen("Sample Stimuli");

//        wizardEditUserScreen.setSendData(true);
//        wizardEditUserScreen.setOn_Error_Text("Geen verbinding met de server. Controleer alstublieft uw internetverbinding en probeer het opnieuw.");
//        wizardEditUserScreen.setCustomFields(new String[]{
//            "Coder:Coder:.'{'3,'}':Voer minimaal drie letters.",
//            "ChildId:ChildId:.'{'3,'}':Voer minimaal drie letters.",
//            "Informant:Informant:.'{'3,'}':Voer minimaal drie letters.",
//            "WhoSpeaks:WhoSpeaks:.'{'3,'}':Voer minimaal drie letters.",
//            "ChildSpeaks:ChildSpeaks:|true|false:.",
//            "Understandable:Understandable:|true|false:.",
//            "IsChildAddressed:IsChildAddressed:|true|false:.",
//            "WhichLanguage:WhichLanguage:.'{'3,'}':Voer minimaal drie letters.",
//            "LanguageLat:LanguageLat:[0-9]+:Voer een getal.",
//            "LanguageLon:LanguageLon:[0-9]+:Voer een getal."
//        });
//        final WizardAboutScreen wizardAboutScreen = new WizardAboutScreen("Over", false);
//        wizardAboutScreen.setBackWizardScreen(wizardEditUserScreen);
//        wizardEditUserScreen.setNextWizardScreen(wizardAboutScreen);
//        wizardData.addScreen(wizardAboutScreen);
        jsonMetadataScreenrScreenSample.setUseSdCard(false);
        wizardData.addScreen(jsonMetadataScreenrScreenSample);
        wizardData.addScreen(jsonMetadataScreenrScreenSdCard);
        final WizardAboutScreen wizardAboutScreen = new WizardAboutScreen("About", true);
        wizardData.addScreen(wizardAboutScreen);
//        jsonMetadataScreenrScreenSdCard.setNextWizardScreen(jsonMetadataScreenrScreenSample);
//        jsonMetadataScreenrScreenSample.setNextWizardScreen(jsonMetadataScreenrScreenSdCard);
        final WizardMenuScreen menuScreen = new WizardMenuScreen();
        wizardData.addScreen(menuScreen);
        menuScreen.addTargetScreen(jsonMetadataScreenrScreenSdCard);
        menuScreen.addTargetScreen(jsonMetadataScreenrScreenSample);
        menuScreen.addTargetScreen(wizardAboutScreen);
        jsonMetadataScreenrScreenSdCard.setBackWizardScreen(menuScreen);
        jsonMetadataScreenrScreenSample.setBackWizardScreen(menuScreen);
        wizardAboutScreen.setBackWizardScreen(menuScreen);
        return wizardData;
    }

    public Experiment getExperiment() {
        final Experiment experiment = wizardController.getExperiment(getWizardData());
        return experiment;
    }
}
