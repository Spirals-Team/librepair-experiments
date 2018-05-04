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
package nl.mpi.tg.eg.experimentdesigner.model.wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @since Apr 19, 2017 3:26:53 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardScreenTest {

    protected static List<WizardScreen> getInstance() {
        List<WizardScreen> wizardScreens = new ArrayList<>();
        for (WizardScreenEnum wizardScreenEnum : WizardScreenEnum.values()) {
            wizardScreens.add(wizardScreenEnum.wizardScreen);
        }

        wizardScreens.addAll(Arrays.asList(new AbstractWizardScreen[]{
            new WizardVideoAudioOptionStimulusScreen("screenName", true, new String[]{"list_1/list_2:AV_happy.mpg:prevoicing9_e_440Hz_coda_k.wav:bik,bek"}, true, true, new String[]{"randomStimuliTags"}, 0, 0, 0, true, 0, "responseOptionsLabelLeft", "responseOptionsLabelRight", true),
            new WizardAboutScreen("screenTitle", true),
            new WizardAgreementScreen("screenTitle", "screenText", "agreementButtonLabel"),
            new WizardAudioTestScreen("screenName", "pageText", "buttonLabel", "audioPath"),
            new WizardCompletionScreen("completedText1", true, true, "completedText2", "eraseUsersDataButtonlabel", "screenTitle", "could_not_contact_the_server_please_check", "retryButtonLabel"),
            new WizardEditUserScreen("screenTitle", "screenTag", "dispalyText", "saveButtonLabel", "postText", new WizardAboutScreen("screenTitle", true), "alternateButtonLabel", true, false, "on_Error_Text"),
            new WizardScoreThresholdScreen("screenText1", 8, "screenTitle", new WizardAboutScreen("screenTitle", true), "retryButton"),
            new WizardTextScreen("screenName", "screenText", "nextButtonLabel"),
            new WizardMenuScreen("screenTitle", "menuLabel", "screenTag"),
            new WizardMultiParticipantScreen("screenName", "groupMembers", 2, "communicationChannels", "textEntryPhaseRoles", "textEntryPhaseText", "textWaitPhaseRoles", true, "textWaitPhaseText", "gridWaitPhaseRoles", "gridWaitPhaseText", "responseGridPhaseRoles", "responseGridPhaseText", "responseGridPhaseText", "responseGridPhaseText", "mutualFeedbackPhaseRoles", "mutualFeedbackPhaseText", "trainingDisplayPhaseRoles", "trainingDisplayPhaseText", "groupRecordSubmitionPhaseRoles", "groupRecordSubmitionNextPhaseRoles", "preStimuliText", "postStimuliText", 0, 0, 0, 0, 0, 0, 0, "timerCountDownLabel")
        }));
        return wizardScreens;
    }

    /**
     * Test of getScreenBooleanInfo method, of class AbstractWizardScreen.
     */
    @Test
    public void testGetScreenBooleanInfo() {
        System.out.println("getScreenBooleanInfo");
        for (WizardScreen instance : getInstance()) {
            if ((instance.getWizardScreenData().getScreenBooleans() != null)) {
                assertNotNull(instance.getScreenBooleanInfo(instance.getWizardScreenData().getScreenBooleans().size() - 1));
            }
        }
    }

    /**
     * Test of getScreenTextInfo method, of class AbstractWizardScreen.
     */
    @Test
    public void testGetScreenTextInfo() {
        System.out.println("getScreenTextInfo");
        for (WizardScreen instance : getInstance()) {
            if ((instance.getWizardScreenData().getScreenText() != null)) {
                assertNotNull(instance.getScreenTextInfo(instance.getWizardScreenData().getScreenText().size() - 1));
            }
        }
    }

    /**
     * Test of getScreenIntegerInfo method, of class AbstractWizardScreen.
     */
    @Test
    public void testGetScreenIntegerInfo() {
        System.out.println("getScreenIntegerInfo");
        for (WizardScreen instance : getInstance()) {
            if ((instance.getWizardScreenData().getScreenIntegers() != null)) {
                assertNotNull(instance.getScreenIntegerInfo(instance.getWizardScreenData().getScreenIntegers().size() - 1));
            }
        }
    }

    /**
     * Test of getNextButtonInfo method, of class AbstractWizardScreen.
     */
    @Test
    public void testGetNextButtonInfo() {
        System.out.println("getNextButtonInfo");
        for (WizardScreen instance : getInstance()) {
            if ((instance.getWizardScreenData().getNextButton() != null)) {
                assertNotNull(instance.getNextButtonInfo(instance.getWizardScreenData().getNextButton().length - 1));
            }
        }
    }
}
