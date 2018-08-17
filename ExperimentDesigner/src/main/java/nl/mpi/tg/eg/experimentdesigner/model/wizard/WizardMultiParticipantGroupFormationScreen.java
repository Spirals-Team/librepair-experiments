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
package nl.mpi.tg.eg.experimentdesigner.model.wizard;

import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;

/**
 * @since Aug 8, 2017 11:41:23 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardMultiParticipantGroupFormationScreen extends AbstractWizardScreen {

    public WizardMultiParticipantGroupFormationScreen() {
        super(WizardScreenEnum.WizardMultiParticipantGroupFormationScreen);
    }

    public WizardMultiParticipantGroupFormationScreen(String screenTitle, String screenText, final String agreementButtonLabel) {
        super(WizardScreenEnum.WizardMultiParticipantGroupFormationScreen, screenTitle, screenTitle, screenTitle);
        this.setNextButton(agreementButtonLabel);
        this.setScreenText(screenText);
    }

    @Override
    public String getScreenBooleanInfo(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getScreenTextInfo(int index) {
        return new String[]{"Agreement Text"}[index];
    }

    @Override
    public String getNextButtonInfo(int index) {
        return new String[]{"Agreement Button Label"}[index];
    }

    @Override
    public String getScreenIntegerInfo(int index) {
        return new String[]{}[index];
    }

    private PresenterFeature getGroupFeatures(WizardScreenData storedWizardScreenData, String members) {
        String communicationChannels = members;
        String[] groupPhasesRoles = new String[]{members + ":-", "-:" + members};
        final PresenterFeature groupNetwork = new PresenterFeature(FeatureType.groupNetwork, null);
        groupNetwork.addFeatureAttributes(FeatureAttribute.groupMembers, members);
        groupNetwork.addFeatureAttributes(FeatureAttribute.phasesPerStimulus, Integer.toString(0));
        groupNetwork.addFeatureAttributes(FeatureAttribute.groupCommunicationChannels, communicationChannels);
        final PresenterFeature joinGroupActivity = new PresenterFeature(FeatureType.groupNetworkActivity, null);
        final PresenterFeature agreementActivity = new PresenterFeature(FeatureType.groupNetworkActivity, null);
        joinGroupActivity.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
        joinGroupActivity.getPresenterFeatureList().add(new PresenterFeature(FeatureType.centrePage, null));
        joinGroupActivity.addFeature(FeatureType.logTokenText, null, "3", "groupNetworkActivity", "logTokenText", "Group:'<groupId>';Members:'<groupAllMemberCodes>';Score:<groupScore>;Channels:'<groupCommunicationChannels>';Scores:'<channelLoop><channelLabel>-<channelScore> </channelLoop>';<groupMemberCode>-best:<playerBestScore>;<groupMemberCode>-current:<playerScore>;GroupOther:'<groupOtherMemberCodes>';ChannelOther:'<channelOtherMemberCodes>';<groupActiveChannel>:<channelScore>;Message:'<groupMessageString>';");
        joinGroupActivity.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlTokenText, "Connected to: <groupId><br/><br/>Group members: <groupAllMemberCodes><br/><br/>As member: <groupMemberCode><br/><br/>"));
        final PresenterFeature joinGroupMessageButton = new PresenterFeature(FeatureType.sendGroupMessageButton, "Continue [enter]");
        joinGroupMessageButton.addFeatureAttributes(FeatureAttribute.eventTag, "joinGroupMessageButton");
        joinGroupMessageButton.addFeatureAttributes(FeatureAttribute.incrementPhase, "1");
        joinGroupMessageButton.addFeatureAttributes(FeatureAttribute.hotKey, "ENTER");
        joinGroupMessageButton.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
        joinGroupActivity.getPresenterFeatureList().add(joinGroupMessageButton);
        joinGroupActivity.addFeatureAttributes(FeatureAttribute.groupRole, groupPhasesRoles[0]);
        groupNetwork.getPresenterFeatureList().add(joinGroupActivity);
        agreementActivity.addFeatureAttributes(FeatureAttribute.groupRole, groupPhasesRoles[1]);
        groupNetwork.getPresenterFeatureList().add(agreementActivity);
        agreementActivity.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
//        agreementActivity.getPresenterFeatureList().add(new PresenterFeature(FeatureType.centrePage, null));
        agreementActivity.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(0)));
        final PresenterFeature presenterFeature = new PresenterFeature(FeatureType.targetButton, storedWizardScreenData.getNextButton()[0]);
        presenterFeature.addFeatureAttributes(FeatureAttribute.target, storedWizardScreenData.getNextWizardScreenData().getScreenTag());
        agreementActivity.getPresenterFeatureList().add(presenterFeature);
//        return groupNetwork;
        final PresenterFeature loadStimuliFeature = new PresenterFeature(FeatureType.loadStimulus, null);
        final PresenterFeature hasMoreStimulusFeature = new PresenterFeature(FeatureType.hasMoreStimulus, null);
        final PresenterFeature endOfStimulusFeature = new PresenterFeature(FeatureType.endOfStimulus, null);
        loadStimuliFeature.getPresenterFeatureList().add(hasMoreStimulusFeature);
        loadStimuliFeature.getPresenterFeatureList().add(endOfStimulusFeature);
        loadStimuliFeature.addStimulusTag("version1");

        endOfStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
        endOfStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.centrePage, null));
        endOfStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, "No valid stimuli"));

        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.eventTag, storedWizardScreenData.getScreenTitle());
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.randomise, Boolean.toString(false));
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatCount, "1");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatRandomWindow, "0");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.adjacencyThreshold, "0");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.maxStimuli, "100");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.minStimuliPerTag, "100");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.maxStimuliPerTag, "100");
        hasMoreStimulusFeature.getPresenterFeatureList().add(groupNetwork);
        return loadStimuliFeature;
    }

    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.stimulus);
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);

        String[][] getParameters1 = {{"R0_4", "A,B,C,D"}, {"R0_8", "A,B,C,D,E,F,G,H"}};
        String[] getParameters2 = {"group", "member"};
        PresenterFeature conditionNested1 = null;
        for (String[] param1 : getParameters1) {
            final PresenterFeature hasGetParameter1 = new PresenterFeature(FeatureType.hasGetParameter, null);
            hasGetParameter1.addFeatureAttributes(FeatureAttribute.parameterName, param1[0]);
            final PresenterFeature conditionTrue1 = new PresenterFeature(FeatureType.conditionTrue, null);
            PresenterFeature conditionNested2 = conditionTrue1;
            for (String param2 : getParameters2) {
                final PresenterFeature hasGetParameter2 = new PresenterFeature(FeatureType.hasGetParameter, null);
                hasGetParameter2.addFeatureAttributes(FeatureAttribute.parameterName, param2);
                final PresenterFeature conditionTrue2 = new PresenterFeature(FeatureType.conditionTrue, null);
                hasGetParameter2.getPresenterFeatureList().add(conditionTrue2);
                final PresenterFeature conditionFalse2 = new PresenterFeature(FeatureType.conditionFalse, null);
                conditionFalse2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, param2 + " must be provided"));
                hasGetParameter2.getPresenterFeatureList().add(conditionFalse2);
                conditionNested2.getPresenterFeatureList().add(hasGetParameter2);
                conditionNested2 = conditionTrue2;
            }
            conditionNested2.getPresenterFeatureList().add(getGroupFeatures(storedWizardScreenData, param1[1]));
            hasGetParameter1.getPresenterFeatureList().add(conditionTrue1);
            final PresenterFeature conditionFalse1 = new PresenterFeature(FeatureType.conditionFalse, null);
            hasGetParameter1.getPresenterFeatureList().add(conditionFalse1);
            if (conditionNested1 == null) {
                storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(hasGetParameter1);
            } else {
                conditionNested1.getPresenterFeatureList().add(hasGetParameter1);
                conditionFalse1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, "R0_4 or R0_8 must be provided"));
            }
            conditionNested1 = conditionFalse1;
        }
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }
}
