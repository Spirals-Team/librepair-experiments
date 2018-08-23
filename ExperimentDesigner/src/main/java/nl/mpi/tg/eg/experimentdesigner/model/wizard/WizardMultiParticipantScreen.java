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
package nl.mpi.tg.eg.experimentdesigner.model.wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.Metadata;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;
import nl.mpi.tg.eg.experimentdesigner.model.RandomGrouping;
import nl.mpi.tg.eg.experimentdesigner.model.Stimulus;

/**
 * @since Nov 7, 2016 1:59:52 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardMultiParticipantScreen extends AbstractWizardScreen {

    public WizardMultiParticipantScreen() {
        super(WizardScreenEnum.WizardMultiParticipantScreen, "MultiParticipant", "MultiParticipant", "MultiParticipant");
    }

    public WizardMultiParticipantScreen(final String screenName,
            final String groupMembers,
            int phasesPerStimulus,
            final String communicationChannels,
            final String textEntryPhaseRoles,
            final String textEntryPhaseText,
            final String textWaitPhaseRoles,
            final boolean textWaitPhaseStimuluIncrement,
            final String textWaitPhaseText,
            final String gridWaitPhaseRoles,
            final String gridWaitPhaseText,
            final String responseGridPhaseRoles,
            final String responseGridPhaseText,
            final String mutualFeedbackPhaseRoles,
            final String mutualFeedbackPhaseText,
            final String mutualFeedbackPhaseCorrectText,
            final String mutualFeedbackPhaseIncorrectText,
            final String trainingDisplayPhaseRoles,
            final String trainingDisplayPhaseText,
            final String groupRecordSubmissionPhaseRoles,
            final String groupRecordSubmissionNextPhaseRoles,
            final String preStimuliText,
            final String postStimuliText,
            final int stimuliCount,
            final int repeatCount,
            final int repeatRandomWindow,
            final int msToShow,
            final int exposureMsDelay,
            final int timerCountDownProducerMs,
            final int timerCountDownGuesserMs,
            final String timerCountDownLabel
    ) {
        super(WizardScreenEnum.WizardMultiParticipantScreen, screenName, screenName, screenName);
        setRandomiseStimuli(true);
        this.wizardScreenData.setStimuliCount(stimuliCount);
        this.wizardScreenData.setRepeatCount(repeatCount);
        this.wizardScreenData.setRepeatRandomWindow(repeatRandomWindow);
        setStimulusMsDelay(msToShow);
        setExposureMsDelay(exposureMsDelay);
        setStimulusFreeText(false);
        setAllowHotkeyButtons(false);
//        this.wizardScreenData.setButtonLabelEventTag("");
        this.wizardScreenData.setCentreScreen(true);
        this.wizardScreenData.setGroupMembers(groupMembers);
        this.setPhasesPerStimulus(phasesPerStimulus);
        this.wizardScreenData.setGroupCommunicationChannels(communicationChannels);
        this.wizardScreenData.setGroupPhasesRoles(new String[]{textEntryPhaseRoles, textWaitPhaseRoles, gridWaitPhaseRoles, responseGridPhaseRoles, mutualFeedbackPhaseRoles, trainingDisplayPhaseRoles, groupRecordSubmissionPhaseRoles, groupRecordSubmissionNextPhaseRoles});

        this.wizardScreenData.setTaskIndex((textWaitPhaseStimuluIncrement) ? 1 : 0);
        this.wizardScreenData.setScreenText(0, textEntryPhaseText);
        this.wizardScreenData.setScreenText(1, gridWaitPhaseText);
        this.wizardScreenData.setScreenText(2, textWaitPhaseText);
        this.wizardScreenData.setScreenText(3, responseGridPhaseText);
        this.wizardScreenData.setScreenText(4, mutualFeedbackPhaseText);
        this.wizardScreenData.setScreenText(5, trainingDisplayPhaseText);
        this.wizardScreenData.setScreenText(6, "");
        this.wizardScreenData.setScreenText(7, preStimuliText);
        this.wizardScreenData.setScreenText(8, postStimuliText);
        this.wizardScreenData.setScreenText(13, mutualFeedbackPhaseCorrectText);
        this.wizardScreenData.setScreenText(14, mutualFeedbackPhaseIncorrectText);
        this.wizardScreenData.setScreenText(15, "");
        setTimerCountDownGuesserMs(timerCountDownGuesserMs);
        setTimerCountDownProducerMs(timerCountDownProducerMs);
        setTimerCountDownLabel(timerCountDownLabel);
        setAllowedCharCodes("");
        setUseDictionary(false);
    }

    final public void setRandomiseStimuli(Boolean randomiseStimuli) {
        this.wizardScreenData.setScreenBoolean(0, randomiseStimuli);
    }

    final public void setStimulusFreeText(Boolean stimulusFreeText) {
        this.wizardScreenData.setScreenBoolean(1, stimulusFreeText);
    }

    final public void setAllowHotkeyButtons(Boolean allowHotkeyButtons) {
        this.wizardScreenData.setScreenBoolean(2, allowHotkeyButtons);
    }

    private boolean isUseDictionary(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenBoolean(3);
    }

    final public void setUseDictionary(Boolean useDictionary) {
        this.wizardScreenData.setScreenBoolean(3, useDictionary);
    }

    private boolean isRandomiseStimuli(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenBoolean(0);
    }

    private boolean isStimulusFreeText(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenBoolean(1);
    }

    private String getPreStimuliText(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(7);
    }

    private String getPostStimuliText(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(8);
    }

    private String getTimerCountDownLabel(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(9);
    }

    final public void setTimerCountDownLabel(String timerCountDownLabel) {
        this.wizardScreenData.setScreenText(9, timerCountDownLabel);
    }

    private String getAllowedCharCodes(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(6);
    }

    final public void setAllowedCharCodes(String allowedCharCodes) {
        this.wizardScreenData.setScreenText(6, allowedCharCodes);
    }

    private String getGroupTitle(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(15);
    }

    final public void setGroupTitle(String groupTitle) {
        this.wizardScreenData.setScreenText(15, groupTitle);
    }

    private String getInputErrorMessage(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(12);
    }

    final public void setInputErrorMessage(String inputErrorMessage) {
        this.wizardScreenData.setScreenText(12, inputErrorMessage);
    }

    private String getFreeTextValidationMessage(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(11);
    }

    final public void setFreeTextValidationMessage(String freeTextValidationMessage) {
        this.wizardScreenData.setScreenText(11, freeTextValidationMessage);
    }

    private String getFreeTextValidationRegex(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(10);
    }

    final public void setFreeTextValidationRegex(String freeTextValidationRegex) {
        this.wizardScreenData.setScreenText(10, freeTextValidationRegex);
    }

    private int getPhasesPerStimulus(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenInteger(3);
    }

    final public void setPhasesPerStimulus(int phasesPerStimulus) {
        this.wizardScreenData.setScreenIntegers(3, phasesPerStimulus);
    }

    private int getTimerCountDownProducerMs(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenInteger(2);
    }

    final public void setTimerCountDownProducerMs(int timerCountDownProducerMs) {
        this.wizardScreenData.setScreenIntegers(2, timerCountDownProducerMs);
    }

    private int getTimerCountDownGuesserMs(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenInteger(1);
    }

    final public void setTimerCountDownGuesserMs(int timerCountDownGuesserMs) {
        this.wizardScreenData.setScreenIntegers(1, timerCountDownGuesserMs);
    }

    private int getStimulusMsDelay(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenInteger(0);
    }

    final public void setStimulusMsDelay(int stimulusMsDelay) {
        this.wizardScreenData.setScreenIntegers(0, stimulusMsDelay);
    }

    private int getExposureMsDelay(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenInteger(4);
    }

    final public void setExposureMsDelay(int exposureMsDelay) {
        this.wizardScreenData.setScreenIntegers(4, exposureMsDelay);
    }

    @Override
    public String getScreenBooleanInfo(int index) {
        return new String[]{"Randomise Stimuli", "Stimulus Free Text", "Allow Hotkey Buttons", "Allowed Char Codes", "Text shown before the stimuli are presented", "Text shown after all the stimuli have been presented", "Use stored dictionary words instead of user input for known stimuli"}[index];
    }

    @Override
    public String getScreenTextInfo(int index) {
        return new String[]{"Text Entry Phase Text", "Grid Wait Phase Text", "Text Wait Phase Text", "Response Grid Phase Text", "Mutual Feedback Phase Text", "Training Display Phase Text",
            "Allowed Char Codes", "Pre Stimuli Text", "Post Stimuli Text",
            "Timer Count Down Ended Label", "FreeTextValidationRegex", "FreeTextValidationMessage", "KeyInputErrorMessage", "MutualFeedbackPhaseCorrectText", "MutualFeedbackPhaseIncorrectText", "Group Page Title"}[index];
    }

    @Override
    public String getScreenIntegerInfo(int index) {
        return new String[]{"Seconds to show the screen, rather than showing a next button", "timerCountDownProducerMs", "timerCountDownGuesserMs", "PhasesPerStimulus", "ExposureMsDelay"}[index];
    }

    @Override
    public String getNextButtonInfo(int index) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public final void setStimuliSet(String[] stimuliSet) {
        // note: new implementations should use the method setStimuliSet in AbstractWizardScreen
        if (this.wizardScreenData.getStimuli() == null) {
            this.wizardScreenData.setStimuli(new ArrayList<>());
        }
        final List<Stimulus> stimuliList = this.wizardScreenData.getStimuli();
        if (stimuliSet != null) {
            for (String stimulusLine : stimuliSet) {
                final HashSet<String> tagSet = new HashSet<>(/*Arrays.asList(new String[]{this.wizardScreenData.getScreenTitle()})*/);
                tagSet.addAll(Arrays.asList(stimulusLine.split("(\\.png)?:")));
                final Stimulus stimulus;
                if (stimulusLine.contains(".png")) {
                    final String[] splitLine = stimulusLine.split(":");
//                    tagSet.addAll(Arrays.asList(stimulusLine.split("/")));
                    stimulus = new Stimulus(stimulusLine, null, null, splitLine[0], null, splitLine[splitLine.length - 1], 0, tagSet, null, null);
                    stimuliList.add(stimulus);
                }
            }
        }
    }

    public void setStimulusFreeText(boolean stimulusFreeText, String freeTextValidationRegex, String freeTextValidationMessage, String inputErrorMessage) {
        setStimulusFreeText(stimulusFreeText);
        setFreeTextValidationRegex(freeTextValidationRegex);
        setFreeTextValidationMessage(freeTextValidationMessage);
        setInputErrorMessage(inputErrorMessage);
    }

    final public void setButtonLabel(String buttonLabelEventTag) {
        this.wizardScreenData.setNextButton(new String[]{buttonLabelEventTag});
        this.wizardScreenData.setButtonLabelEventTag(buttonLabelEventTag);
    }

    private List<PresenterFeature> getScoreFeatures(boolean correctResponse) {
//        final PresenterFeature scoreButtonFeature = new PresenterFeature(FeatureType.actionButton, "correct");
//        scoreButtonFeature.addFeatureAttributes(FeatureAttribute.eventTag, "correct button");
//        scoreButtonFeature.addFeatureAttributes(FeatureAttribute.hotKey, "Z");
//        scoreButtonFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
        final PresenterFeature scoreIncrement = new PresenterFeature(FeatureType.scoreIncrement, null);
        scoreIncrement.addFeatureAttributes(FeatureAttribute.scoreValue, (correctResponse) ? "true" : "false");
        final PresenterFeature scoreAboveThreshold = new PresenterFeature(FeatureType.scoreAboveThreshold, null);
        scoreAboveThreshold.addFeatureAttributes(FeatureAttribute.scoreThreshold, "5");
        final PresenterFeature aboveThreshold = new PresenterFeature(FeatureType.aboveThreshold, null);
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.plainText, "above threshold"));
        scoreAboveThreshold.getPresenterFeatureList().add(aboveThreshold);
        final PresenterFeature withinThreshold = new PresenterFeature(FeatureType.withinThreshold, null);
//        withinThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.plainText, "below threshold"));
        scoreAboveThreshold.getPresenterFeatureList().add(withinThreshold);
//        scoreButtonFeature.getPresenterFeatureList().add(scoreIncrement);
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.scoreLabel, null));
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupChannelScoreLabel, null));
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupScoreLabel, null));
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));

//        withinThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        withinThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.scoreLabel, null));
//        withinThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        withinThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupChannelScoreLabel, null));
//        withinThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        withinThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupScoreLabel, null));
//        withinThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        final PresenterFeature aboveFeature = addGroupMessageButton("correctNetworkActivityAboveThreshold", "Z");
//        final PresenterFeature belowFeature = addGroupMessageButton("correctNetworkActivityBelowThreshold", "Z");
        final PresenterFeature aboveFeature = new PresenterFeature(FeatureType.sendGroupMessage, null);
        aboveFeature.addFeatureAttributes(FeatureAttribute.eventTag, "aboveFeature");
        aboveFeature.addFeatureAttributes(FeatureAttribute.incrementPhase, "1");
        final PresenterFeature belowFeature = new PresenterFeature(FeatureType.sendGroupMessage, null);
        belowFeature.addFeatureAttributes(FeatureAttribute.eventTag, "belowFeature");
        belowFeature.addFeatureAttributes(FeatureAttribute.incrementPhase, "1");

        aboveThreshold.getPresenterFeatureList().add(aboveFeature);
        withinThreshold.getPresenterFeatureList().add(belowFeature);
        return Arrays.asList(new PresenterFeature[]{scoreIncrement, scoreAboveThreshold, new PresenterFeature(FeatureType.submitGroupEvent, null)});
    }

    private void addClearPageAndTitle(final WizardScreenData storedWizardScreenData, final PresenterFeature presenterFeature) {
        presenterFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
        if (!getGroupTitle(storedWizardScreenData).isEmpty()) {
            presenterFeature.addFeature(FeatureType.logTokenText, null, "3", "groupNetworkActivity", "logTokenText", "Group:'<groupId>';Members:'<groupAllMemberCodes>';Score:<groupScore>;Channels:'<groupCommunicationChannels>';Scores:'<channelLoop><channelLabel>-<channelScore> </channelLoop>';<groupMemberCode>-best:<playerBestScore>;<groupMemberCode>-current:<playerScore>;GroupOther:'<groupOtherMemberCodes>';ChannelOther:'<channelOtherMemberCodes>';<groupActiveChannel>:<channelScore>;Message:'<groupMessageString>';");
            final PresenterFeature htmlTokenText = new PresenterFeature(FeatureType.htmlTokenText, getGroupTitle(storedWizardScreenData));
            presenterFeature.getPresenterFeatureList().add(htmlTokenText);
            htmlTokenText.addFeatureAttributes(FeatureAttribute.styleName, "headerLabelCentered");
        }
    }

    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        experiment.appendUniqueStimuli(storedWizardScreenData.getStimuli());
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.stimulus);
        List<PresenterFeature> presenterFeatureList = storedWizardScreenData.getPresenterScreen().getPresenterFeatureList();
        presenterFeatureList.add(new PresenterFeature(FeatureType.clearCurrentScore, null));
        if (storedWizardScreenData.isCentreScreen()) {
            presenterFeatureList.add(new PresenterFeature(FeatureType.centrePage, null));
        }
        final PresenterFeature groupNetwork = new PresenterFeature(FeatureType.groupNetwork, null);
        groupNetwork.addFeatureAttributes(FeatureAttribute.groupMembers, storedWizardScreenData.getGroupMembers());
        groupNetwork.addFeatureAttributes(FeatureAttribute.phasesPerStimulus, Integer.toString(getPhasesPerStimulus(storedWizardScreenData)));
        //@todo: add groupRoleSequence
//        groupNetwork.addFeatureAttributes(FeatureAttribute.groupCommunicationChannels, 
        groupNetwork.addFeatureAttributes(FeatureAttribute.groupCommunicationChannels, storedWizardScreenData.getGroupCommunicationChannels());
//        groupNetwork.addFeatureAttributes(FeatureAttribute.groupCommunicationChannels, 

        final PresenterFeature producerNetworkActivity0 = new PresenterFeature(FeatureType.groupNetworkActivity, null);
//        selfNetworkActivity1.addFeatureAttributes(FeatureAttribute.requestedPhase, "0");
        final PresenterFeature producerNetworkActivity1 = new PresenterFeature(FeatureType.groupNetworkActivity, null);
//        selfNetworkActivity2.addFeatureAttributes(FeatureAttribute.requestedPhase, "1");
        final PresenterFeature guesserNetworkActivity0 = new PresenterFeature(FeatureType.groupNetworkActivity, null);
        final PresenterFeature guesserNetworkActivity1 = new PresenterFeature(FeatureType.groupNetworkActivity, null);
        final PresenterFeature allNetworkActivity2 = new PresenterFeature(FeatureType.groupNetworkActivity, null);
//        groupNetworkActivity2.addFeatureAttributes(FeatureAttribute.requestedPhase, "1");
        final PresenterFeature trainingDisplayNetworkActivity3 = new PresenterFeature(FeatureType.groupNetworkActivity, null);
        final PresenterFeature groupRecordSubmission = new PresenterFeature(FeatureType.groupNetworkActivity, null);
        final PresenterFeature groupRecordSubmissionNext = new PresenterFeature(FeatureType.groupNetworkActivity, null);

//        final PresenterFeature nextStimulusP = new PresenterFeature(FeatureType.nextStimulus, null);
//        producerNetworkActivity0.getPresenterFeatureList().add(nextStimulusP);
//        nextStimulusP.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
//        nextStimulusP.addFeatureAttributes(FeatureAttribute.eventTag, "nextStimulusProducerNetworkActivity1" + storedWizardScreenData.getScreenTitle());
        addClearPageAndTitle(storedWizardScreenData, producerNetworkActivity0);
        addClearPageAndTitle(storedWizardScreenData, producerNetworkActivity1);
        addClearPageAndTitle(storedWizardScreenData, guesserNetworkActivity0);
        addClearPageAndTitle(storedWizardScreenData, guesserNetworkActivity1);
        addClearPageAndTitle(storedWizardScreenData, allNetworkActivity2);
        addClearPageAndTitle(storedWizardScreenData, trainingDisplayNetworkActivity3);

        final PresenterFeature conditionFalseProduer;
        final PresenterFeature conditionFalseGuesser;
        if ((isUseDictionary(storedWizardScreenData))) {
            final PresenterFeature stimulusHasResponseGuesser = new PresenterFeature(FeatureType.stimulusHasResponse, null);
            final PresenterFeature conditionTrueGuesser = new PresenterFeature(FeatureType.conditionTrue, null);
            conditionFalseGuesser = new PresenterFeature(FeatureType.conditionFalse, null);
            stimulusHasResponseGuesser.getPresenterFeatureList().add(conditionTrueGuesser);
            stimulusHasResponseGuesser.getPresenterFeatureList().add(conditionFalseGuesser);
            guesserNetworkActivity0.getPresenterFeatureList().add(stimulusHasResponseGuesser);

            final PresenterFeature stimulusHasResponseProduer = new PresenterFeature(FeatureType.stimulusHasResponse, null);
            final PresenterFeature conditionTrueProduer = new PresenterFeature(FeatureType.conditionTrue, null);
            final PresenterFeature sendGroupStoredMessageProduer = new PresenterFeature(FeatureType.sendGroupStoredMessage, null);
            sendGroupStoredMessageProduer.addFeatureAttributes(FeatureAttribute.eventTag, "sendGroupStoredMessage");
            sendGroupStoredMessageProduer.addFeatureAttributes(FeatureAttribute.incrementPhase, "2");
            final PresenterFeature pause = new PresenterFeature(FeatureType.pause, null);
            pause.addFeatureAttributes(FeatureAttribute.msToNext, Integer.toString(getExposureMsDelay(storedWizardScreenData)));
            pause.getPresenterFeatureList().add(sendGroupStoredMessageProduer);
            conditionTrueProduer.getPresenterFeatureList().add(pause);
            conditionFalseProduer = new PresenterFeature(FeatureType.conditionFalse, null);
            stimulusHasResponseProduer.getPresenterFeatureList().add(conditionTrueProduer);
            stimulusHasResponseProduer.getPresenterFeatureList().add(conditionFalseProduer);
            producerNetworkActivity0.getPresenterFeatureList().add(stimulusHasResponseProduer);
        } else {
            conditionFalseGuesser = guesserNetworkActivity0;
            conditionFalseProduer = producerNetworkActivity0;
        }

//        producerNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.scoreLabel, null));
//        producerNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.scoreLabel, null));
//        guesserNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.scoreLabel, null));
//        guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.scoreLabel, null));
//        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.scoreLabel, null));
//        producerNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupChannelScoreLabel, null));
//        producerNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupChannelScoreLabel, null));
//        guesserNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupChannelScoreLabel, null));
//        guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupChannelScoreLabel, null));
//        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupChannelScoreLabel, null));
//        producerNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupScoreLabel, null));
//        producerNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupScoreLabel, null));
//        guesserNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupScoreLabel, null));
//        guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupScoreLabel, null));
//        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupScoreLabel, null));
//        producerNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberLabel, null));
//        producerNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberLabel, null));
//        guesserNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberLabel, null));
//        guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberLabel, null));
//        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberLabel, null));
//        trainingDisplayNetworkActivity3.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberLabel, null));
//        producerNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberCodeLabel, null));
//        producerNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberCodeLabel, null));
//        guesserNetworkActivity0.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberCodeLabel, null));
//        guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberCodeLabel, null));
//        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberCodeLabel, null));
//        trainingDisplayNetworkActivity3.getPresenterFeatureList().add(new PresenterFeature(FeatureType.groupMemberCodeLabel, null));
        final PresenterFeature stimulusLabel = new PresenterFeature(FeatureType.stimulusLabel, null);
//        stimulusLabel.addFeatureAttributes(FeatureAttribute.styleName, "");
        conditionFalseProduer.getPresenterFeatureList().add(stimulusLabel);

//        final PresenterFeature nextStimulusG = new PresenterFeature(FeatureType.nextStimulus, null);
//        guesserNetworkActivity1.getPresenterFeatureList().add(nextStimulusG);
//        nextStimulusG.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
//        nextStimulusG.addFeatureAttributes(FeatureAttribute.eventTag, "nextStimulusGuesserNetworkActivity1" + storedWizardScreenData.getScreenTitle());
        if (storedWizardScreenData.getTaskIndex() == 1) {
//            final PresenterFeature nextStimulusX = new PresenterFeature(FeatureType.nextStimulus, null);
//            guesserNetworkActivity0.getPresenterFeatureList().add(nextStimulusX);
//            nextStimulusX.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
//            nextStimulusX.addFeatureAttributes(FeatureAttribute.eventTag, "nextStimulusFeatureTrainingDisplay" + storedWizardScreenData.getScreenTitle());
        }
        if (storedWizardScreenData.getScreenText(0) != null) {
            conditionFalseProduer.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(0)));
        }
        if (storedWizardScreenData.getScreenText(1) != null) {
            producerNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(1)));
        }
        if (storedWizardScreenData.getScreenText(2) != null) {
            conditionFalseGuesser.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(2)));
        }
        if (storedWizardScreenData.getScreenText(3) != null) {
            guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(3)));
        }
        if (storedWizardScreenData.getScreenText(4) != null) {
            allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(4)));
        }
        if (storedWizardScreenData.getScreenText(5) != null) {
            trainingDisplayNetworkActivity3.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(5)));
        }
        String mutualFeedbackPhaseCorrectText = (storedWizardScreenData.getScreenText(13) != null) ? storedWizardScreenData.getScreenText(13) : "";
        String mutualFeedbackPhaseIncorrectText = (storedWizardScreenData.getScreenText(14) != null) ? storedWizardScreenData.getScreenText(14) : "";

        final PresenterFeature groupResponseFeedback2 = new PresenterFeature(FeatureType.groupResponseFeedback, null);
        final PresenterFeature responseCorrect2 = new PresenterFeature(FeatureType.responseCorrect, null);
        responseCorrect2.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        responseCorrect2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, mutualFeedbackPhaseCorrectText));
        groupResponseFeedback2.getPresenterFeatureList().add(responseCorrect2);
        final PresenterFeature responseIncorrect2 = new PresenterFeature(FeatureType.responseIncorrect, null);
        responseIncorrect2.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        responseIncorrect2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, mutualFeedbackPhaseIncorrectText));
        groupResponseFeedback2.getPresenterFeatureList().add(responseIncorrect2);
        allNetworkActivity2.getPresenterFeatureList().add(groupResponseFeedback2);
        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        if (storedWizardScreenData.getStimulusFreeText()) {
//            final PresenterFeature stimulusFreeTextFeature = new PresenterFeature(FeatureType.stimulusFreeText, storedWizardScreenData.getFreeTextValidationMessage());
//            stimulusFreeTextFeature.addFeatureAttributes(FeatureAttribute.validationRegex, storedWizardScreenData.getFreeTextValidationRegex());
//            groupNetworkActivity1.getPresenterFeatureList().add(stimulusFreeTextFeature);
//        }
        final PresenterFeature loadStimuliFeature = new PresenterFeature(FeatureType.loadStimulus, null);
        if (storedWizardScreenData.getStimuliRandomTags() != null) {
            final RandomGrouping randomGrouping = new RandomGrouping();
            for (String randomTag : storedWizardScreenData.getStimuliRandomTags()) {
                // todo: move the "v1" into the congiguration data with randomTag
                randomGrouping.addRandomTag("v1", randomTag);
            }
//            final String metadataFieldname = "stimuliAllocation" + storedWizardScreenData.getScreenTag();
            final String metadataFieldname = "stimuli";
            randomGrouping.setStorageField(metadataFieldname);
            loadStimuliFeature.setRandomGrouping(randomGrouping);
            final Metadata metadataField = new Metadata(metadataFieldname, metadataFieldname, ".*", ".", false, null);
            if (!experiment.getMetadata().contains(metadataField)) {
                experiment.getMetadata().add(metadataField);
            }
        } else {
            loadStimuliFeature.addStimulusTag(storedWizardScreenData.getScreenTitle());
        }
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.eventTag, storedWizardScreenData.getScreenTitle());
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.minStimuliPerTag, "1");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.maxStimuliPerTag, "100");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.randomise, Boolean.toString(isRandomiseStimuli(storedWizardScreenData)));
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatCount, Integer.toString(storedWizardScreenData.getRepeatCount()));
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatRandomWindow, Integer.toString(storedWizardScreenData.getRepeatRandomWindow()));
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.adjacencyThreshold, "3");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.maxStimuli, Integer.toString(storedWizardScreenData.getStimuliCount()));
        if (getTimerCountDownProducerMs(storedWizardScreenData) > 0) {
            final PresenterFeature countDownFeature = new PresenterFeature(FeatureType.countdownLabel, getTimerCountDownLabel(storedWizardScreenData));
            countDownFeature.addFeatureAttributes(FeatureAttribute.msToNext, Integer.toString(getTimerCountDownProducerMs(storedWizardScreenData)));
            countDownFeature.addFeatureAttributes(FeatureAttribute.msLabelFormat, "ss"); // HH:mm:ss
            countDownFeature.addFeatureAttributes(FeatureAttribute.styleName, "countDownLabel");
            conditionFalseProduer.getPresenterFeatureList().add(countDownFeature);
        }
        final PresenterFeature hasMoreStimulusFeature = new PresenterFeature(FeatureType.hasMoreStimulus, null);
        final PresenterFeature imageFeature = new PresenterFeature(FeatureType.stimulusPresent, null);
        imageFeature.addFeatureAttributes(FeatureAttribute.animate, "stimuliCode");
        imageFeature.addFeatureAttributes(FeatureAttribute.showControls, "false");
        conditionFalseProduer.getPresenterFeatureList().add(imageFeature);
        final PresenterFeature groupMessageLabel = new PresenterFeature(FeatureType.groupMessageLabel, null);
        groupMessageLabel.addFeatureAttributes(FeatureAttribute.styleName, "groupMessageLabel");
        guesserNetworkActivity1.getPresenterFeatureList().add(groupMessageLabel);
        if (getTimerCountDownGuesserMs(storedWizardScreenData) > 0) {
            final PresenterFeature countDownFeature = new PresenterFeature(FeatureType.countdownLabel, getTimerCountDownLabel(storedWizardScreenData));
            countDownFeature.addFeatureAttributes(FeatureAttribute.msToNext, Integer.toString(getTimerCountDownGuesserMs(storedWizardScreenData)));
            countDownFeature.addFeatureAttributes(FeatureAttribute.msLabelFormat, "ss"); // HH:mm:ss
            countDownFeature.addFeatureAttributes(FeatureAttribute.styleName, "countDownLabel");
            guesserNetworkActivity1.getPresenterFeatureList().add(countDownFeature);
        }
        final PresenterFeature stimulusGrid = addStimuliGrid("guesserNetworkActivity1Grid", getScoreFeatures(true), getScoreFeatures(false));
        guesserNetworkActivity1.getPresenterFeatureList().add(stimulusGrid);
//        final PresenterFeature resultsGrid = addStimuliGrid("resultsGrid", new PresenterFeature(FeatureType.htmlText, "response not relevant"), new PresenterFeature(FeatureType.htmlText, "response not relevant"));
//        allNetworkActivity2.getPresenterFeatureList().add(resultsGrid);
        final PresenterFeature groupMessageLabel2 = new PresenterFeature(FeatureType.groupMessageLabel, null);
        groupMessageLabel2.addFeatureAttributes(FeatureAttribute.styleName, "groupMessageLabel");
        allNetworkActivity2.getPresenterFeatureList().add(groupMessageLabel2);
        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
        final PresenterFeature allNetworkActivityRow = new PresenterFeature(FeatureType.row, null);
        final PresenterFeature allNetworkActivityColumn1 = new PresenterFeature(FeatureType.column, null);
        final PresenterFeature allNetworkActivityColumn2 = new PresenterFeature(FeatureType.column, null);
        final PresenterFeature allNetworkActivityColumn3 = new PresenterFeature(FeatureType.column, null);
        allNetworkActivityRow.getPresenterFeatureList().add(allNetworkActivityColumn1);
        allNetworkActivityRow.getPresenterFeatureList().add(allNetworkActivityColumn2);
        allNetworkActivityRow.getPresenterFeatureList().add(allNetworkActivityColumn3);
        final PresenterFeature allNetworkActivity2Image = new PresenterFeature(FeatureType.stimulusPresent, null);
        allNetworkActivity2Image.addFeatureAttributes(FeatureAttribute.maxHeight, "0");
        allNetworkActivity2Image.addFeatureAttributes(FeatureAttribute.maxWidth, "0");
        allNetworkActivity2Image.addFeatureAttributes(FeatureAttribute.percentOfPage, "0");
        allNetworkActivity2Image.addFeatureAttributes(FeatureAttribute.animate, "stimuliCode");
        allNetworkActivity2Image.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        allNetworkActivity2Image.addFeatureAttributes(FeatureAttribute.showControls, "false");
        allNetworkActivity2Image.addFeatures(FeatureType.mediaLoaded, FeatureType.mediaLoadFailed, FeatureType.mediaPlaybackComplete);
        allNetworkActivityColumn3.getPresenterFeatureList().add(allNetworkActivity2Image);
        allNetworkActivityColumn3.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, "Target"));
        allNetworkActivity2.getPresenterFeatureList().add(allNetworkActivityRow);

        final PresenterFeature groupResponseFeedback = new PresenterFeature(FeatureType.groupResponseFeedback, null);
        final PresenterFeature responseCorrect = new PresenterFeature(FeatureType.responseCorrect, null);
        responseCorrect.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        groupResponseFeedback.getPresenterFeatureList().add(responseCorrect);
        final PresenterFeature responseIncorrect = new PresenterFeature(FeatureType.responseIncorrect, null);
        responseIncorrect.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        groupResponseFeedback.getPresenterFeatureList().add(responseIncorrect);

        responseCorrect.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, "<h1 style=\"color:green;\">&#10004;</h1>"));
        responseIncorrect.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, "<h1 style=\"color:red;\">&#10008;</h1>"));
//        responseIncorrect.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, "your guesser chose"));

        final PresenterFeature groupResponseStimulusImage = new PresenterFeature(FeatureType.groupResponseStimulusImage, null);
        groupResponseStimulusImage.addFeatureAttributes(FeatureAttribute.maxHeight, "0");
        groupResponseStimulusImage.addFeatureAttributes(FeatureAttribute.maxWidth, "0");
        groupResponseStimulusImage.addFeatureAttributes(FeatureAttribute.percentOfPage, "0");
        groupResponseStimulusImage.addFeatureAttributes(FeatureAttribute.animate, "stimuliCode");
        groupResponseStimulusImage.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        groupResponseStimulusImage.addFeature(FeatureType.mediaLoaded, null);
        groupResponseStimulusImage.addFeature(FeatureType.mediaLoadFailed, null);
        groupResponseStimulusImage.addFeature(FeatureType.mediaPlaybackComplete, null);
        allNetworkActivityColumn1.getPresenterFeatureList().add(groupResponseStimulusImage);
        allNetworkActivityColumn1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, "Gekozen"));
        allNetworkActivityColumn2.getPresenterFeatureList().add(groupResponseFeedback);

//        (true, false, new FeatureAttribute[]{, , }, true, false, FeatureType.Contitionals.hasCorrectIncorrect),
//        final PresenterFeature correctButton = getScoreFeatures();
//        final PresenterFeature incorrectButton = addGroupMessageButton("incorrect", "NUM_PERIOD");
//        final PresenterFeature nextStimulusFeature2 = addGroupMessageButton("groupNetworkActivity2", "Q");
//        guesserNetworkActivity1.getPresenterFeatureList().add(correctButton);
//        guesserNetworkActivity1.getPresenterFeatureList().add(incorrectButton);
        // @todo: is this inserted into the correct location
        guesserNetworkActivity1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.enableStimulusButtons, null));
//        incorrectButton.getPresenterFeatureList().add(new PresenterFeature(FeatureType.submitGroupEvent, null));

//        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        final PresenterFeature groupScoreLabel = new PresenterFeature(FeatureType.groupScoreLabel, null);
//        groupScoreLabel.addFeatureAttributes(FeatureAttribute.styleName, "");
//        allNetworkActivity2.getPresenterFeatureList().add(groupScoreLabel);
//        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        final PresenterFeature groupChannelScoreLabel = new PresenterFeature(FeatureType.groupChannelScoreLabel, null);
//        groupChannelScoreLabel.addFeatureAttributes(FeatureAttribute.styleName, "");
//        allNetworkActivity2.getPresenterFeatureList().add(groupChannelScoreLabel);
//        allNetworkActivity2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        final PresenterFeature scoreLabel = new PresenterFeature(FeatureType.scoreLabel, null);
//        scoreLabel.addFeatureAttributes(FeatureAttribute.styleName, "");
//        allNetworkActivity2.getPresenterFeatureList().add(scoreLabel);
        // allNetworkActivity2 phase shows the stimulus and the selected stimulus and the message and the group score
        allNetworkActivity2.getPresenterFeatureList().add(addGroupMessageButton("Volgende [enter]", "guesser and producer see the response", "ENTER"));

        final PresenterFeature groupMessageLabel3 = new PresenterFeature(FeatureType.groupMessageLabel, null);
        groupMessageLabel3.addFeatureAttributes(FeatureAttribute.styleName, "groupMessageLabel");
        trainingDisplayNetworkActivity3.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
        trainingDisplayNetworkActivity3.getPresenterFeatureList().add(groupMessageLabel3);
        trainingDisplayNetworkActivity3.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));

        final PresenterFeature stimulusImage = new PresenterFeature(FeatureType.stimulusPresent, null);
        stimulusImage.addFeatureAttributes(FeatureAttribute.maxHeight, "0");
        stimulusImage.addFeatureAttributes(FeatureAttribute.maxWidth, "0");
        stimulusImage.addFeatureAttributes(FeatureAttribute.percentOfPage, "0");
        stimulusImage.addFeatureAttributes(FeatureAttribute.animate, "stimuliCode");
        stimulusImage.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        stimulusImage.addFeatureAttributes(FeatureAttribute.showControls, "false");
        trainingDisplayNetworkActivity3.getPresenterFeatureList().add(stimulusImage);
        final PresenterFeature mediaLoaded = stimulusImage.addFeatures(FeatureType.mediaLoaded, FeatureType.mediaLoadFailed, FeatureType.mediaPlaybackComplete)[2];
        if (getStimulusMsDelay(storedWizardScreenData) > 0) {
            final PresenterFeature pause = new PresenterFeature(FeatureType.pause, null);
            final PresenterFeature sendGroupMessage = new PresenterFeature(FeatureType.sendGroupMessage, null);
            pause.addFeatureAttributes(FeatureAttribute.msToNext, Integer.toString(getStimulusMsDelay(storedWizardScreenData)));
            sendGroupMessage.addFeatureAttributes(FeatureAttribute.eventTag, "autoNext");
            sendGroupMessage.addFeatureAttributes(FeatureAttribute.incrementPhase, "1");
            pause.getPresenterFeatureList().add(sendGroupMessage);
            mediaLoaded.getPresenterFeatureList().add(pause);
        } else {
            final PresenterFeature nextStimulusFeature4 = addGroupMessageButton("Volgende [enter]", "nextStimulusFeature4", "ENTER");
            mediaLoaded.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
            mediaLoaded.getPresenterFeatureList().add(nextStimulusFeature4);
        }
        hasMoreStimulusFeature.getPresenterFeatureList().add(groupNetwork);
//        presenterFeatureList.add(groupNetwork);
//        presenterFeatureList.add(loadStimuliFeature);
//
//        final PresenterFeature groupMessageShowingStimuli = new PresenterFeature(FeatureType.sendGroupMessage, null);
//        groupMessageShowingStimuli.addFeatureAttributes(FeatureAttribute.eventTag, "groupMessageShowingStimuli");
//        groupMessageShowingStimuli.addFeatureAttributes(FeatureAttribute.incrementPhase, "0");

        final String preStimuliText = getPreStimuliText(storedWizardScreenData);
        if (preStimuliText != null && !preStimuliText.isEmpty()) {
            presenterFeatureList.add(new PresenterFeature(FeatureType.htmlText, preStimuliText));
            final PresenterFeature actionButton = new PresenterFeature(FeatureType.actionButton, "Volgende [enter]");
            actionButton.addFeatureAttributes(FeatureAttribute.hotKey, "ENTER");
            actionButton.getPresenterFeatureList().add(loadStimuliFeature);
            presenterFeatureList.add(actionButton);
        } else {
            presenterFeatureList.add(loadStimuliFeature);
        }

        if (!storedWizardScreenData.getGroupPhasesRoles()[0].isEmpty()) {
            producerNetworkActivity0.addFeatureAttributes(FeatureAttribute.groupRole, storedWizardScreenData.getGroupPhasesRoles()[0]);
            groupNetwork.getPresenterFeatureList().add(producerNetworkActivity0);
        }
        if (!storedWizardScreenData.getGroupPhasesRoles()[1].isEmpty()) {
            guesserNetworkActivity0.addFeatureAttributes(FeatureAttribute.groupRole, storedWizardScreenData.getGroupPhasesRoles()[1]);
            groupNetwork.getPresenterFeatureList().add(guesserNetworkActivity0);
        }
        if (!storedWizardScreenData.getGroupPhasesRoles()[2].isEmpty()) {
            producerNetworkActivity1.addFeatureAttributes(FeatureAttribute.groupRole, storedWizardScreenData.getGroupPhasesRoles()[2]);
            groupNetwork.getPresenterFeatureList().add(producerNetworkActivity1);
        }
        if (!storedWizardScreenData.getGroupPhasesRoles()[3].isEmpty()) {
            guesserNetworkActivity1.addFeatureAttributes(FeatureAttribute.groupRole, storedWizardScreenData.getGroupPhasesRoles()[3]);
            groupNetwork.getPresenterFeatureList().add(guesserNetworkActivity1);
        }
        if (!storedWizardScreenData.getGroupPhasesRoles()[4].isEmpty()) {
            allNetworkActivity2.addFeatureAttributes(FeatureAttribute.groupRole, storedWizardScreenData.getGroupPhasesRoles()[4]);
            groupNetwork.getPresenterFeatureList().add(allNetworkActivity2);
        }
        if (!storedWizardScreenData.getGroupPhasesRoles()[5].isEmpty()) {
            trainingDisplayNetworkActivity3.addFeatureAttributes(FeatureAttribute.groupRole, storedWizardScreenData.getGroupPhasesRoles()[5]);
            groupNetwork.getPresenterFeatureList().add(trainingDisplayNetworkActivity3);
        }
        if (!storedWizardScreenData.getGroupPhasesRoles()[6].isEmpty()) {
            groupRecordSubmission.addFeatureAttributes(FeatureAttribute.groupRole, storedWizardScreenData.getGroupPhasesRoles()[6]);
            groupNetwork.getPresenterFeatureList().add(groupRecordSubmission);
        }
        if (!storedWizardScreenData.getGroupPhasesRoles()[7].isEmpty()) {
            // this is used as a next phase phase for the testing screen that needs to only submit a group record after a message has been sent and then return to the previous phase
            groupRecordSubmissionNext.addFeatureAttributes(FeatureAttribute.groupRole, storedWizardScreenData.getGroupPhasesRoles()[7]);
            groupNetwork.getPresenterFeatureList().add(groupRecordSubmissionNext);
        }

        imageFeature.addFeatureAttributes(FeatureAttribute.maxHeight, "0");
        imageFeature.addFeatureAttributes(FeatureAttribute.maxWidth, "0");
        imageFeature.addFeatureAttributes(FeatureAttribute.percentOfPage, "0");
        imageFeature.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        final PresenterFeature presenterFeature;
//        final String hotKeyString = "SPACE";
        presenterFeature = imageFeature.addFeatures(FeatureType.mediaLoaded, FeatureType.mediaLoadFailed, FeatureType.mediaPlaybackComplete)[2];
        presenterFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
        if (isStimulusFreeText(storedWizardScreenData)) {
            final PresenterFeature stimulusFreeTextFeature = new PresenterFeature(FeatureType.stimulusFreeText, getFreeTextValidationMessage(storedWizardScreenData));
            stimulusFreeTextFeature.addFeatureAttributes(FeatureAttribute.validationRegex, getFreeTextValidationRegex(storedWizardScreenData));
            stimulusFreeTextFeature.addFeatureAttributes(FeatureAttribute.inputErrorMessage, getInputErrorMessage(storedWizardScreenData));
            stimulusFreeTextFeature.addFeatureAttributes(FeatureAttribute.allowedCharCodes, getAllowedCharCodes(storedWizardScreenData));
            stimulusFreeTextFeature.addFeatureAttributes(FeatureAttribute.hotKey, "ENTER");
            stimulusFreeTextFeature.addFeatureAttributes(FeatureAttribute.styleName, "minimalWidth");
            presenterFeature.getPresenterFeatureList().add(stimulusFreeTextFeature);
        }
        if (storedWizardScreenData.getStimulusResponseOptions() != null) {
            final PresenterFeature ratingFooterButtonFeature = new PresenterFeature(FeatureType.ratingButton, null);
            ratingFooterButtonFeature.addFeatureAttributes(FeatureAttribute.ratingLabels, storedWizardScreenData.getStimulusResponseOptions());
            ratingFooterButtonFeature.addFeatureAttributes(FeatureAttribute.ratingLabelLeft, storedWizardScreenData.getStimulusResponseLabelLeft());
            ratingFooterButtonFeature.addFeatureAttributes(FeatureAttribute.ratingLabelRight, storedWizardScreenData.getStimulusResponseLabelRight());
            ratingFooterButtonFeature.addFeatureAttributes(FeatureAttribute.eventTier, "1");
//            final PresenterFeature nextStimulusFeature = new PresenterFeature(FeatureType.nextStimulus, null);
//            nextStimulusFeature.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
//            nextStimulusFeature.addFeatureAttributes(FeatureAttribute.eventTag, "NextStimulus" + storedWizardScreenData.getScreenTitle());
//            ratingFooterButtonFeature.getPresenterFeatureList().add(nextStimulusFeature);
            presenterFeature.getPresenterFeatureList().add(ratingFooterButtonFeature);
//        } else {
//            final PresenterFeature nextButtonFeature = new PresenterFeature(FeatureType.actionButton, storedWizardScreenData.getNextButton()[0]);
//            nextButtonFeature.addFeatureAttributes(FeatureAttribute.eventTag, storedWizardScreenData.getButtonLabelEventTag());
//            if (storedWizardScreenData.getAllowHotkeyButtons()) {
//                nextButtonFeature.addFeatureAttributes(FeatureAttribute.hotKey, hotKeyString);
//            }
//            final PresenterFeature nextStimulusFeature = new PresenterFeature(FeatureType.nextStimulus, null);
//            nextStimulusFeature.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
//            nextStimulusFeature.addFeatureAttributes(FeatureAttribute.eventTag, "nextStimulus" + storedWizardScreenData.getScreenTitle());
//            nextButtonFeature.getPresenterFeatureList().add(nextStimulusFeature);
//            presenterFeature.getPresenterFeatureList().add(nextButtonFeature);
        }
        final PresenterFeature groupNetworkActivitySelf1 = addGroupMessageButton("Volgende [enter]", "TextEntered", "ENTER");
        groupRecordSubmission.getPresenterFeatureList().add(new PresenterFeature(FeatureType.submitGroupEvent, null));
        groupRecordSubmissionNext.getPresenterFeatureList().add(new PresenterFeature(FeatureType.submitGroupEvent, null));
        final PresenterFeature groupRecordSubmissionNextFeature = new PresenterFeature(FeatureType.sendGroupMessage, null);
        groupRecordSubmissionNext.getPresenterFeatureList().add(groupRecordSubmissionNextFeature);
        groupRecordSubmissionNextFeature.addFeatureAttributes(FeatureAttribute.eventTag, "group record submitted");
        groupRecordSubmissionNextFeature.addFeatureAttributes(FeatureAttribute.incrementPhase, "1");
        presenterFeature.getPresenterFeatureList().add(groupNetworkActivitySelf1);
        loadStimuliFeature.getPresenterFeatureList().add(hasMoreStimulusFeature);

        final PresenterFeature endOfStimulusFeature = new PresenterFeature(FeatureType.endOfStimulus, null);
        final PresenterFeature autoNextPresenter = new PresenterFeature(FeatureType.gotoNextPresenter, null);

        final String postStimuliText = getPostStimuliText(storedWizardScreenData);
        if (postStimuliText != null && !postStimuliText.isEmpty()) {
            final PresenterFeature endOfStimulusGroupNetwork = new PresenterFeature(FeatureType.groupNetwork, null);
            endOfStimulusGroupNetwork.addFeatureAttributes(FeatureAttribute.groupMembers, storedWizardScreenData.getGroupMembers());
            endOfStimulusGroupNetwork.addFeatureAttributes(FeatureAttribute.phasesPerStimulus, Integer.toString(getPhasesPerStimulus(storedWizardScreenData)));
            endOfStimulusGroupNetwork.addFeatureAttributes(FeatureAttribute.groupCommunicationChannels, storedWizardScreenData.getGroupCommunicationChannels());
            endOfStimulusFeature.getPresenterFeatureList().add(endOfStimulusGroupNetwork);
            final PresenterFeature endOfStimulusGroupMessage = new PresenterFeature(FeatureType.sendGroupEndOfStimuli, null);
            endOfStimulusGroupNetwork.getPresenterFeatureList().add(endOfStimulusGroupMessage);
            // todo: endOfStimulusGroupMessage might not be needed in the new paradgm
            endOfStimulusGroupMessage.addFeatureAttributes(FeatureAttribute.eventTag, "endOfStimulusGroupMessage");
//            endOfStimulusGroupMessage.addFeatureAttributes(FeatureAttribute.incrementPhase, "1");
            endOfStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
            endOfStimulusFeature.addFeature(FeatureType.logTokenText, null, "3", "groupNetworkActivity", "logTokenText", "Group:'<groupId>';Members:'<groupAllMemberCodes>';Score:<groupScore>;Channels:'<groupCommunicationChannels>';Scores:'<channelLoop><channelLabel>-<channelScore> </channelLoop>';<groupMemberCode>-best:<playerBestScore>;<groupMemberCode>-current:<playerScore>;GroupOther:'<groupOtherMemberCodes>';ChannelOther:'<channelOtherMemberCodes>';<groupActiveChannel>:<channelScore>;Message:'<groupMessageString>';");
            endOfStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlTokenText, postStimuliText));
//            final PresenterFeature groupChannelScoreLabel1 = new PresenterFeature(FeatureType.groupChannelScoreLabel, null);
//            groupChannelScoreLabel1.addFeatureAttributes(FeatureAttribute.styleName, "");
//            endOfStimulusFeature.getPresenterFeatureList().add(groupChannelScoreLabel1);
//            final PresenterFeature groupScoreLabel1 = new PresenterFeature(FeatureType.groupScoreLabel, null);
//            groupScoreLabel1.addFeatureAttributes(FeatureAttribute.styleName, "");
//            endOfStimulusFeature.getPresenterFeatureList().add(groupScoreLabel1);
            final PresenterFeature actionButton = new PresenterFeature(FeatureType.actionButton, "Volgende [enter]");
            actionButton.addFeatureAttributes(FeatureAttribute.hotKey, "ENTER");
            actionButton.getPresenterFeatureList().add(autoNextPresenter);
//            final PresenterFeature groupMessageEndOfStimuli = new PresenterFeature(FeatureType.sendGroupMessage, null);
//            groupMessageEndOfStimuli.addFeatureAttributes(FeatureAttribute.eventTag, "groupMessageEndOfStimuli");
//            groupMessageEndOfStimuli.addFeatureAttributes(FeatureAttribute.incrementPhase, "0");
//            endOfStimulusFeature.getPresenterFeatureList().add(groupMessageEndOfStimuli);
            endOfStimulusFeature.getPresenterFeatureList().add(actionButton);
        } else {
            endOfStimulusFeature.getPresenterFeatureList().add(autoNextPresenter);
        }
        loadStimuliFeature.getPresenterFeatureList().add(endOfStimulusFeature);
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }

    protected PresenterFeature addStimuliGrid(String eventTagString, final List<PresenterFeature> responseCorrectFeatures, final List<PresenterFeature> responseIncorrectFeatures) {
        //        final PresenterFeature guesserRatingButtons = new PresenterFeature(FeatureType.ratingButton, null);
//        guesserRatingButtons.addFeatureAttributes(FeatureAttribute.ratingLabels, "one,two,three");
//        guesserRatingButtons.addFeatureAttributes(FeatureAttribute.ratingLabelLeft, "");
//        guesserRatingButtons.addFeatureAttributes(FeatureAttribute.ratingLabelRight, "");
//        guesserRatingButtons.addFeatureAttributes(FeatureAttribute.eventTier, "1");
//        groupNetworkActivity2.getPresenterFeatureList().add(guesserRatingButtons);
// temporary testing features
//        final PresenterFeature nextStimulusFeature1 = new PresenterFeature(FeatureType.sendGroupMessageButton, "groupNetworkActivity1");
//        nextStimulusFeature1.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
//        nextStimulusFeature1.addFeatureAttributes(FeatureAttribute.eventTag, "groupNetworkActivity1");
//        nextStimulusFeature1.addFeatureAttributes(FeatureAttribute.hotKey, "Q");
//        nextStimulusFeature1.addFeatureAttributes(FeatureAttribute.requestedPhase, "2");
//        guesserNetworkActivity0.getPresenterFeatureList().add(nextStimulusFeature1);
        final PresenterFeature resultsGrid = new PresenterFeature(FeatureType.showStimulusGrid, null);
        resultsGrid.addFeatureAttributes(FeatureAttribute.columnCount, "4");
        resultsGrid.addFeatureAttributes(FeatureAttribute.maxStimuli, "8");
        resultsGrid.addFeatureAttributes(FeatureAttribute.imageWidth, "100");
        resultsGrid.addFeatureAttributes(FeatureAttribute.animate, "stimuliCode");
        resultsGrid.addFeatureAttributes(FeatureAttribute.eventTag, eventTagString);
        final PresenterFeature responseCorrect = new PresenterFeature(FeatureType.responseCorrect, null);
        responseCorrect.getPresenterFeatureList().addAll(responseCorrectFeatures);
        responseCorrect.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        resultsGrid.getPresenterFeatureList().add(responseCorrect);
        final PresenterFeature resultsIncorrect = new PresenterFeature(FeatureType.responseIncorrect, null);
        resultsIncorrect.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        resultsIncorrect.getPresenterFeatureList().addAll(responseIncorrectFeatures);
        resultsGrid.getPresenterFeatureList().add(resultsIncorrect);
        return resultsGrid;
    }

//    showStimulusGrid( true, false, new FeatureAttribute[]{columnCount, imageWidth, eventTag
//    }
//     , true, false, FeatureType.Contitionals.hasCorrectIncorrect
    protected PresenterFeature addGroupMessageButton(final String labelString, final String eventTag, final String hotKey) {
        final PresenterFeature nextStimulusFeature2 = new PresenterFeature(FeatureType.sendGroupMessageButton, labelString);
        nextStimulusFeature2.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
        nextStimulusFeature2.addFeatureAttributes(FeatureAttribute.eventTag, eventTag);
        nextStimulusFeature2.addFeatureAttributes(FeatureAttribute.hotKey, hotKey);
        nextStimulusFeature2.addFeatureAttributes(FeatureAttribute.incrementPhase, "1");
        return nextStimulusFeature2;
    }
}
