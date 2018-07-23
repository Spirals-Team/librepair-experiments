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
import java.util.Arrays;
import java.util.HashSet;
import nl.mpi.tg.eg.experimentdesigner.controller.WizardController;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.Metadata;
import nl.mpi.tg.eg.experimentdesigner.model.Stimulus;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardCompletionScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardEditUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardSynQuizIntroductionScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardMenuScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardSynQuizReportScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardSynQuizStimulusScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardSynQuizSumbitScreen;

/**
 * @since Jan 18, 2016 11:20:47 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Deprecated
public class SynQuiz2ru {

    private final WizardController wizardController = new WizardController();

    public Experiment getExperiment() {
        final Experiment experiment = wizardController.getExperiment(getWizardData());
        experiment.appendUniqueStimuli(new SynQuiz2ru().createStimuli());
        return experiment;
    }

    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName("SynQuiz2ru");
        wizardData.setShowMenuBar(true);
        wizardData.setObfuscateScreenNames(false);
        final String we_are_studying_the_genetic_basis_of_syna = "We are studying the genetic basis of synaesthesia, a neurological phenomenon described as a \"mixing of the senses\". To find out how our genes shape how we see the world, "
                + "we are looking for people who connect letters, numbers, days of the week, or months with specific colours. This is called \"grapheme-colour\" synaesthesia. ";
        final String how_our_study_works = "How our study works:";
        final String staticstudy_diagramsvg = "static/study_diagram.svg";
        final String the_synaesthesia_tests_take_about_20_minu = "The synaesthesia tests take about 20 minutes to complete, and you can choose the ones that apply to you. "
                + "Depending on your scores, we may send you an email inviting you to participate in the genetics part of the study. There is no cost to participate, and you can do everything from home.";
        final String for_more_information_about_synaesthesia_p = "For more information about synaesthesia, please see our 'About synaesthesia' page. "
                + "If you are not sure if you have synaesthesia, and want to find out, try our SynQuiz app or take a quick test at synesthete.org.";
        final String this_project_is_organised_and_funded_by_t = "This project is organised and funded by the Language & Genetics Department at the Max Planck Institute for Psycholinguistics in Nijmegen in the Netherlands, directed by Prof. Dr. Simon E. Fisher. " + "The synaesthesia studies are coordinated by Dr. Amanda Tilot and Dr. Sarah Graham. "
                + "If you have any questions about our research, please contact us at " + formatLink("synaesthesia@mpi.nl", "mailto:synaesthesia@mpi.nl") + ".";
        final String participateButton = "Participate!";
        final WizardEditUserScreen wizardEditUserScreen = new WizardEditUserScreen();
        final WizardSynQuizIntroductionScreen introductionScreen = new WizardSynQuizIntroductionScreen("Introduction",
                "Decoding the Genetics of Synaesthesia",
                we_are_studying_the_genetic_basis_of_syna,
                how_our_study_works,
                staticstudy_diagramsvg,
                the_synaesthesia_tests_take_about_20_minu,
                for_more_information_about_synaesthesia_p,
                this_project_is_organised_and_funded_by_t, participateButton, wizardEditUserScreen.getWizardScreenData());
        final WizardCompletionScreen completionScreen = new WizardCompletionScreen(
                "Thank you for participating! You may hear from us in the next few weeks to ask if you would like to participate in the genetics part of the study. Your data has been saved, and you can now close your browser. <br><br>"
                + "If you have any questions about the study, you can email them to us at "
                + formatLink("synaesthesia@mpi.nl", "mailto:synaesthesia@mpi.nl")
                + ". It will be a year or more before there are results, but when we publish our study it will be posted on our "
                + formatLink("website", "http://www.mpi.nl/departments/language-and-genetics/projects/decoding-the-genetics-of-synaesthesia/publications")
                + ".<br/><br/>",
                true, false, null,
                "Finish this expriment and start from the begining",
                "Completion",
                "Could not contact the server, please check your internet connection and try again.", "Retry");

        wizardEditUserScreen.setScreenText("Please read the " + formatLink("Participant Information Sheet", "static/synaesthesia_info_sheet_ENGLISH_webversion.pdf") + " carefully!");
        wizardEditUserScreen.setBackWizardScreen(introductionScreen);
        wizardEditUserScreen.setFirstNameField();
        wizardEditUserScreen.setLastNameField();
        wizardEditUserScreen.setEmailAddressField();
        wizardEditUserScreen.setOptionCheckBox1("I would like to be contacted about participating in other synaesthesia research studies (optional)");
        wizardEditUserScreen.setMandatoryCheckBox("By checking this box I confirm that I have read and understood the Volunteer's Information Sheet and I agree to take part in this study");

        wizardEditUserScreen.setScreenTitle("Participant");
        wizardEditUserScreen.setScreenTag("Participant");
        wizardEditUserScreen.setMenuLabel("Participant");
        wizardEditUserScreen.setSendData(true);
        wizardEditUserScreen.setNextButton("Continue");
        wizardEditUserScreen.setSendData(true);
        wizardEditUserScreen.setOn_Error_Text(COULD_NOT_CONTACT_THE_SERVER_PLEASE_CHECK);

        final WizardMenuScreen menuScreen = new WizardMenuScreen("Menu", "Menu", "Menu");

        final String submit_my_results = "Submit my results";
        final String error_submitting_data = "Error submitting data.";
        final WizardSynQuizSumbitScreen sumbitScreen = new WizardSynQuizSumbitScreen("Register", menuScreen.getWizardScreenData(), completionScreen.getWizardScreenData(), submit_my_results, error_submitting_data);
        sumbitScreen.setNextWizardScreen(completionScreen);
        completionScreen.setBackWizardScreen(menuScreen);
        completionScreen.setNextWizardScreen(introductionScreen);

        wizardData.addScreen(introductionScreen);
        wizardData.addScreen(wizardEditUserScreen);
        WizardEditUserScreen previousDemographicsScreen = wizardEditUserScreen;//demographicsScreen1.getPresenterScreen();
        for (DemographicScreenType demographicScreenType : DemographicScreenType.values()) {
            final WizardEditUserScreen demographicsScreen = createDemographicsScreen(previousDemographicsScreen, demographicScreenType);
            wizardData.addScreen(demographicsScreen);
            previousDemographicsScreen.setNextWizardScreen(demographicsScreen);
            previousDemographicsScreen = demographicsScreen;
        }

        previousDemographicsScreen.setNextWizardScreen(menuScreen);
        final WizardEditUserScreen menuBackPresenter = previousDemographicsScreen;
        menuScreen.getWizardScreenData().getPresenterScreen().setBackPresenter(menuBackPresenter.getWizardScreenData().getPresenterScreen());
        menuScreen.getWizardScreenData().getPresenterScreen().setNextPresenter(sumbitScreen.getWizardScreenData().getPresenterScreen());
        final WizardSynQuizReportScreen reportScreen = new WizardSynQuizReportScreen("Report", menuScreen.getWizardScreenData(), menuScreen.getWizardScreenData());
        wizardData.addScreen(menuScreen);
        final String ok_go_to_test = "OK, go to test!";
        final String helpText = "<b>Instructions</b>\\n<p>Select the colour that you associate with the presented character or word \\n<ol>\\n<li>Select the hue by tapping on the colour bar on the right </li><li>Select the shade by tapping on the square on the left </li>\\n<li>When the colour of the preview rectangle matches your association, tap \"Submit\"</li>\\n<li>If you have no colour association tap \"No colour\"</li>\\n</ol>\\n</p>";
        final WizardSynQuizStimulusScreen weekdaysScreen = new WizardSynQuizStimulusScreen("Weekdays", "Weekdays", menuScreen.getWizardScreenData(), reportScreen.getWizardScreenData(), ok_go_to_test, helpText);
        wizardData.addScreen(weekdaysScreen);
        final WizardSynQuizStimulusScreen lettersScreen = new WizardSynQuizStimulusScreen("LettersNumbers", "Letters and Numbers", menuScreen.getWizardScreenData(), reportScreen.getWizardScreenData(), ok_go_to_test, helpText);
        wizardData.addScreen(lettersScreen);
        final WizardSynQuizStimulusScreen monthsScreen = new WizardSynQuizStimulusScreen("Months", "Months", menuScreen.getWizardScreenData(), reportScreen.getWizardScreenData(), ok_go_to_test, helpText);
        menuScreen.addTargetScreen(weekdaysScreen);
        menuScreen.addTargetScreen(lettersScreen);
        menuScreen.addTargetScreen(monthsScreen);
        menuScreen.getWizardScreenData().setScreenText(1, "The tests above will ask about the colours that you associate with Weekdays, Letters and Numbers, or Months. If you do not have colour associations with one of the options, you can skip that test. After each test you can view your results.<br/><br/>"
                + "When you are finished taking the tests that apply to you, please click <b>Submit my results</b> below to finish the experiment.");

        wizardData.addScreen(completionScreen);
        wizardData.addScreen(monthsScreen);
        wizardData.addScreen(sumbitScreen);
        wizardData.addScreen(reportScreen);

        return wizardData;
    }
    protected static final String COULD_NOT_CONTACT_THE_SERVER_PLEASE_CHECK = "Could not contact the server, please check your internet connection and try again.";

    private String formatLink(String linkUrl) {
        return formatLink(linkUrl, linkUrl);
    }

    private String formatLink(String linkText, String linkUrl) {
        return "<a href=\"#\" onclick=\"window.open('" + linkUrl + "','_system'); return false;\">" + linkText + "</a>";
    }

    enum DemographicScreenType {
        Details, Study, Colour, Smell, Sound, Spatial, Taste, Touch, Other
    }

    private WizardEditUserScreen createDemographicsScreen(final WizardEditUserScreen backPresenter, DemographicScreenType screenName) {
        final WizardEditUserScreen wizardEditUserScreen = new WizardEditUserScreen();
        wizardEditUserScreen.setScreenTitle("Tell us about your synaesthesia: " + screenName.name() + "(" + (screenName.ordinal()) + "/" + (DemographicScreenType.values().length - 1) + ")");
        wizardEditUserScreen.setBackWizardScreen(backPresenter);
        wizardEditUserScreen.setScreenTag(screenName.name());
        wizardEditUserScreen.setMenuLabel(screenName.name());
        wizardEditUserScreen.setSendData(true);
        wizardEditUserScreen.setNextButton("Continue");
        wizardEditUserScreen.setOn_Error_Text(COULD_NOT_CONTACT_THE_SERVER_PLEASE_CHECK);
        switch (screenName) {
            case Details:
                wizardEditUserScreen.setScreenTitle(screenName.name());
                wizardEditUserScreen.insertMetadataByString("DateOfBirth:Date of Birth:[0-3][0-9]/[0-1][0-9]/[1-2][0-9][0-9][0-9]:Please enter in the standard format DD/MM/YYYY.");
                //        "Age:Age:[0-9]+:Please enter in number format.",
                wizardEditUserScreen.insertMetadataByString("Gender:Gender:|male|female|other:.");
                wizardEditUserScreen.insertMetadataByString("AbsolutePitch:Absolute pitch:true|false:Please enter true or false.");
                wizardEditUserScreen.insertMetadataByString("TraumaticBlowToTheHead:Traumatic blow to the head:true|false:Please enter true or false.");
                wizardEditUserScreen.insertMetadataByString("Migraines:Migraines:true|false:Please enter true or false.");
                wizardEditUserScreen.insertMetadataByString("Headaches:Headaches:true|false:Please enter true or false.");
                wizardEditUserScreen.insertMetadataByString("Seizures:Seizures:true|false:Please enter true or false.");
                wizardEditUserScreen.insertMetadataByString("Dyslexia:Dyslexia:true|false:Please enter true or false.");
                wizardEditUserScreen.insertMetadataByString("BrainSurgery:Brain surgery:true|false:Please enter true or false.");
                wizardEditUserScreen.insertMetadataByString("AnyOtherConditions:Are there any other conditions that you would like us to know about?:['\\\\'S'\\\\'s]*:.");
                break;
            case Study:
                wizardEditUserScreen.setScreenText("Our study at the Max Planck Institute focuses on synaesthesia where numbers, letters, weekdays, or months cause people to have a colour experience. To someone with synaesthesia, the letter A might \"mean\" red to them, or the number \"5\" might make them experience the colour green. Please let us know if you experience any other types of synaesthesia by checking the boxes in the following screens. We may contact you in the future about studies related to these other types.");
                break;
            case Colour:
                wizardEditUserScreen.setScreenText(//"Colour<br/>" +
                        "Do any of the items below cause you to have a color experience?<br/><br/>"
                        + "Examples: Does the letter M \"mean\" orange to you? Or does hearing a piano being played make you perceive red?<br/><br/>");
                wizardEditUserScreen.insertMetadataField("Numbers->Colour");
                wizardEditUserScreen.insertMetadataField("Letters->Colour");
                wizardEditUserScreen.insertMetadataField("Weekdays->Colour");
                wizardEditUserScreen.insertMetadataField("Months->Colour");
                wizardEditUserScreen.insertMetadataField("Sequences->Colour");
                wizardEditUserScreen.insertMetadataField("Musical Pitch->Colour");
                wizardEditUserScreen.insertMetadataField("Musical Chord->Colour");
                wizardEditUserScreen.insertMetadataField("Musical Instruments->Colour");
                wizardEditUserScreen.insertMetadataField("Taste->Colour");
                wizardEditUserScreen.insertMetadataField("Smell->Colour");
                wizardEditUserScreen.insertMetadataField("Pain->Colour");
                wizardEditUserScreen.insertMetadataField("Personalities->Colour");
                wizardEditUserScreen.insertMetadataField("Touch->Colour");
                wizardEditUserScreen.insertMetadataField("Temperature->Colour");
                wizardEditUserScreen.insertMetadataField("Vision->Colour");
                wizardEditUserScreen.insertMetadataField("Sound->Colour");
                wizardEditUserScreen.insertMetadataField("American Sign -> Colour");
                wizardEditUserScreen.insertMetadataField("British Sign -> Colour");
                break;
            case Smell:
                wizardEditUserScreen.setScreenText(//"Smell<br/>"
                        "Do any of the items below cause you to experience smells?<br/><br/>"
                        + "Example: Does Tuesday smell like bananas?<br/><br/>");
                wizardEditUserScreen.insertMetadataField("Numbers->Smell");
                wizardEditUserScreen.insertMetadataField("Letters->Smell");
                wizardEditUserScreen.insertMetadataField("Weekdays->Smell");
                wizardEditUserScreen.insertMetadataField("Months->Smell");
                wizardEditUserScreen.insertMetadataField("Sequences->Smell");
                wizardEditUserScreen.insertMetadataField("Musical Pitch->Smell");
                wizardEditUserScreen.insertMetadataField("Musical Chord->Smell");
                wizardEditUserScreen.insertMetadataField("Musical Instruments->Smell");
                wizardEditUserScreen.insertMetadataField("Taste->Smell");
                wizardEditUserScreen.insertMetadataField("Smell->Smell");
                wizardEditUserScreen.insertMetadataField("Pain->Smell");
                wizardEditUserScreen.insertMetadataField("Personalities->Smell");
                wizardEditUserScreen.insertMetadataField("Touch->Smell");
                wizardEditUserScreen.insertMetadataField("Temperature->Smell");
                wizardEditUserScreen.insertMetadataField("Vision->Smell");
                wizardEditUserScreen.insertMetadataField("Sound->Smell");
                wizardEditUserScreen.insertMetadataField("American Sign->Smell");
                wizardEditUserScreen.insertMetadataField("British Sign->Smell");
                break;
            case Sound:
                wizardEditUserScreen.setScreenText(//"Sound<br/>"
                        "Do any of the items below cause you to experience sound?<br/><br/>"
                        + "Example: Do you hear a particular sound when you experience cold temperatures?<br/><br/>");
                wizardEditUserScreen.insertMetadataField("Numbers->Sound");
                wizardEditUserScreen.insertMetadataField("Letters->Sound");
                wizardEditUserScreen.insertMetadataField("Weekdays->Sound");
                wizardEditUserScreen.insertMetadataField("Months->Sound");
                wizardEditUserScreen.insertMetadataField("Sequences->Sound");
                wizardEditUserScreen.insertMetadataField("Musical Pitch->Sound");
                wizardEditUserScreen.insertMetadataField("Musical Chord->Sound");
                wizardEditUserScreen.insertMetadataField("Musical Instruments->Sound");
                wizardEditUserScreen.insertMetadataField("Taste->Sound");
                wizardEditUserScreen.insertMetadataField("Smell->Sound");
                wizardEditUserScreen.insertMetadataField("Pain->Sound");
                wizardEditUserScreen.insertMetadataField("Personalities->Sound");
                wizardEditUserScreen.insertMetadataField("Touch->Sound");
                wizardEditUserScreen.insertMetadataField("Temperature->Sound");
                wizardEditUserScreen.insertMetadataField("Vision->Sound");
                wizardEditUserScreen.insertMetadataField("Sound->Sound");
                wizardEditUserScreen.insertMetadataField("American Sign->Sound");
                wizardEditUserScreen.insertMetadataField("British Sign->Sound");
                break;
            case Spatial:
                wizardEditUserScreen.setScreenText( //"Spatial<br/>"
                        "Do you experience any of the items below in a particular spatial location?<br/><br/>"
                        + "Example: Do you \"see\" sequences like the days of the month or numbers in physical space?<br/><br/>");
                wizardEditUserScreen.insertMetadataField("Numbers->Spatial");
                wizardEditUserScreen.insertMetadataField("Letters->Spatial");
                wizardEditUserScreen.insertMetadataField("Weekdays->Spatial");
                wizardEditUserScreen.insertMetadataField("Months->Spatial");
                wizardEditUserScreen.insertMetadataField("Sequences->Spatial");
                wizardEditUserScreen.insertMetadataField("Musical Pitch->Spatial");
                wizardEditUserScreen.insertMetadataField("Musical Chord->Spatial");
                wizardEditUserScreen.insertMetadataField("Musical Instruments>Spatial");
                wizardEditUserScreen.insertMetadataField("Taste->Spatial");
                wizardEditUserScreen.insertMetadataField("Smell->Spatial");
                wizardEditUserScreen.insertMetadataField("Pain->Spatial");
                wizardEditUserScreen.insertMetadataField("Personalities->Spatial");
                wizardEditUserScreen.insertMetadataField("Touch->Spatial");
                wizardEditUserScreen.insertMetadataField("Temperature->Spatial");
                wizardEditUserScreen.insertMetadataField("Vision->Spatial");
                wizardEditUserScreen.insertMetadataField("Sound->Spatial");
                wizardEditUserScreen.insertMetadataField("American Sign->Spatial");
                wizardEditUserScreen.insertMetadataField("British Sign->Spatial");
                break;
            case Taste:
                wizardEditUserScreen.setScreenText( //"Taste<br/>"
                        "Do any of the items below cause you to experience tastes?<br/><br/>"
                        + "Examples: \"Philip tastes of bitter oranges, while April tastes of apricots.\" \"The word 'safety' tastes of lightly buttered toast\"<br/><br/>");
                wizardEditUserScreen.insertMetadataField("Numbers->Taste");
                wizardEditUserScreen.insertMetadataField("Letters->Taste");
                wizardEditUserScreen.insertMetadataField("Weekdays->Taste");
                wizardEditUserScreen.insertMetadataField("Months->Taste");
                wizardEditUserScreen.insertMetadataField("Sequences->Taste");
                wizardEditUserScreen.insertMetadataField("Musical Pitch->Taste");
                wizardEditUserScreen.insertMetadataField("Musical Chord->Taste");
                wizardEditUserScreen.insertMetadataField("Musical Instruments>Taste");
                wizardEditUserScreen.insertMetadataField("Taste->Taste");
                wizardEditUserScreen.insertMetadataField("Smell->Taste");
                wizardEditUserScreen.insertMetadataField("Pain->Taste");
                wizardEditUserScreen.insertMetadataField("Personalities->Taste");
                wizardEditUserScreen.insertMetadataField("Touch->Taste");
                wizardEditUserScreen.insertMetadataField("Temperature->Taste");
                wizardEditUserScreen.insertMetadataField("Vision->Taste");
                wizardEditUserScreen.insertMetadataField("Sound->Taste");
                wizardEditUserScreen.insertMetadataField("American Sign->Taste");
                wizardEditUserScreen.insertMetadataField("British Sign->Taste");
                break;
            case Touch:
                wizardEditUserScreen.setScreenText( //"Touch<br/>"
                        "Do any of the items below cause you to experience a sense of touch?<br/><br/>"
                        + "Example: You feel a touch on your arm when you see someone else being touched on their arm.<br/><br/>");
                wizardEditUserScreen.insertMetadataField("Numbers->Touch");
                wizardEditUserScreen.insertMetadataField("Letters->Touch");
                wizardEditUserScreen.insertMetadataField("Weekdays->Touch");
                wizardEditUserScreen.insertMetadataField("Months->Touch");
                wizardEditUserScreen.insertMetadataField("Sequences->Touch");
                wizardEditUserScreen.insertMetadataField("Musical Pitch->Touch");
                wizardEditUserScreen.insertMetadataField("Musical Chord->Touch");
                wizardEditUserScreen.insertMetadataField("Musical Instruments->Touch");
                wizardEditUserScreen.insertMetadataField("Taste->Touch");
                wizardEditUserScreen.insertMetadataField("Smell->Touch");
                wizardEditUserScreen.insertMetadataField("Pain->Touch");
                wizardEditUserScreen.insertMetadataField("Personalities->Touch");
                wizardEditUserScreen.insertMetadataField("Touch->Touch");
                wizardEditUserScreen.insertMetadataField("Temperature->Touch");
                wizardEditUserScreen.insertMetadataField("Vision->Touch");
                wizardEditUserScreen.insertMetadataField("Sound->Touch");
                wizardEditUserScreen.insertMetadataField("American Sign->Touch");
                wizardEditUserScreen.insertMetadataField("British Sign->Touch");
                break;
            case Other:
                wizardEditUserScreen.insertMetadataField(new Metadata("AnyOtherTypes", "If you experience any other types, please explain below.", "['\\\\'S'\\\\'s]'{'0,'}'", "", false, null));
        }
        return wizardEditUserScreen;
    }

    private void insertStimulusGroup(final ArrayList<Stimulus> stimuliList, String groupName, String groupItems) {
        final HashSet<String> tagSet = new HashSet<>(Arrays.asList(new String[]{groupName}));
        final String[] itemArray = groupItems.split(",");
        for (String itemString : itemArray) {
            final Stimulus stimulus = new Stimulus(itemString, null, null, null, itemString, null, 0, tagSet, null, null);
            stimuliList.add(stimulus);
        }
    }

    public ArrayList<Stimulus> createStimuli() {
        final ArrayList<Stimulus> stimuliList = new ArrayList<>();
        insertStimulusGroup(stimuliList, "Weekdays", "Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday");
//        insertStimulusGroup(stimuliList, "Numbers", "0,1,2,3,4,5,6,7,8,9");
//        insertStimulusGroup(stimuliList, "Letters", "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z");
        insertStimulusGroup(stimuliList, "LettersNumbers", "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,0,1,2,3,4,5,6,7,8,9");
        insertStimulusGroup(stimuliList, "Months", "January,February,March,April,May,June,July,August,September,October,November,December");
        return stimuliList;
    }
}
