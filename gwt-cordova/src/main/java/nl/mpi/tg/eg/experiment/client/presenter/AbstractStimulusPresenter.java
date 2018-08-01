/*
 * Copyright (C) 2015 
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
package nl.mpi.tg.eg.experiment.client.presenter;

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import nl.mpi.tg.eg.experiment.client.ApplicationController;
import nl.mpi.tg.eg.experiment.client.ApplicationController.ApplicationState;
import nl.mpi.tg.eg.experiment.client.exception.DataSubmissionException;
import nl.mpi.tg.eg.experiment.client.listener.AppEventListner;
import nl.mpi.tg.eg.experiment.client.listener.CancelableStimulusListener;
import nl.mpi.tg.eg.experiment.client.listener.CurrentStimulusListener;
import nl.mpi.tg.eg.experiment.client.listener.DataSubmissionListener;
import nl.mpi.tg.eg.experiment.client.listener.GroupActivityListener;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.mpi.tg.eg.experiment.client.listener.SingleShotEventListner;
import nl.mpi.tg.eg.experiment.client.listener.StimulusButton;
import nl.mpi.tg.eg.experiment.client.listener.TouchInputCapture;
import nl.mpi.tg.eg.experiment.client.listener.TouchInputZone;
import nl.mpi.tg.eg.experiment.client.listener.TriggerListener;
import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;
import nl.mpi.tg.eg.experiment.client.model.DataSubmissionResult;
import nl.mpi.tg.eg.experiment.client.model.MetadataField;
import nl.mpi.tg.eg.experiment.client.model.SdCardStimulus;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;
import nl.mpi.tg.eg.experiment.client.model.StimulusFreeText;
import nl.mpi.tg.eg.experiment.client.model.UserResults;
import nl.mpi.tg.eg.experiment.client.service.AudioPlayer;
import nl.mpi.tg.eg.experiment.client.service.DataSubmissionService;
import nl.mpi.tg.eg.experiment.client.service.GroupParticipantService;
import nl.mpi.tg.eg.experiment.client.service.LocalStorage;
import nl.mpi.tg.eg.experiment.client.service.MatchingStimuliGroup;
import nl.mpi.tg.eg.experiment.client.service.MetadataFieldProvider;
import nl.mpi.tg.eg.experiment.client.service.SdCardImageCapture;
import nl.mpi.tg.eg.experiment.client.service.TimerService;
import nl.mpi.tg.eg.frinex.common.StimuliProvider;
import nl.mpi.tg.eg.experiment.client.util.HtmlTokenFormatter;
import nl.mpi.tg.eg.experiment.client.view.ComplexView;
import nl.mpi.tg.eg.experiment.client.view.MetadataFieldWidget;
import nl.mpi.tg.eg.experiment.client.view.TimedStimulusView;
import nl.mpi.tg.eg.frinex.common.model.StimulusSelector;

/**
 * @since Jun 23, 2015 11:36:37 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public abstract class AbstractStimulusPresenter extends AbstractPresenter implements Presenter {

    final MetadataFieldProvider metadataFieldProvider = new MetadataFieldProvider();
    private static final String LOADED_STIMULUS_LIST = "loadedStimulusList";
    private static final String CONSUMED_TAGS_LIST = "consumedTagsList";
    private static final String SEEN_STIMULUS_INDEX = "seenStimulusIndex";
    private final LocalStorage localStorage;
    private final DataSubmissionService submissionService;
    private GroupParticipantService groupParticipantService = null;
    final UserResults userResults;
    private final Duration duration;
    final ArrayList<StimulusButton> buttonList = new ArrayList<>();
    private final ArrayList<Timer> pauseTimers = new ArrayList<>();
    private CurrentStimulusListener hasMoreStimulusListener;
    private TimedStimulusListener endOfStimulusListener;
    final private ArrayList<PresenterEventListner> nextButtonEventListnerList = new ArrayList<>();
    private final ArrayList<StimulusFreeText> stimulusFreeTextList = new ArrayList<>();
    private final HashMap<String, TriggerListener> triggerListeners = new HashMap<>();
    private final TimerService timerService;
    MatchingStimuliGroup matchingStimuliGroup = null;
    private boolean hasSubdirectories = false;
    private TouchInputCapture touchInputCapture = null;

    protected enum AnimateTypes {
        bounce, none, stimuliCode
    }

    public AbstractStimulusPresenter(RootLayoutPanel widgetTag, AudioPlayer audioPlayer, DataSubmissionService submissionService, UserResults userResults, final LocalStorage localStorage, final TimerService timerService) {
        super(widgetTag, new TimedStimulusView(audioPlayer));
        duration = new Duration();
        this.submissionService = submissionService;
        this.timerService = timerService;
        this.userResults = userResults;
        this.localStorage = localStorage;

//        final Label debugLabel = new Label();
//        debugLabel.setStyleName("debugLabel");
//        new Timer() {
//            public void run() {
//                todo: verify that these are cleared correctly: domHandlerArray scaledImagesList
//                final String debugString = "BEL:" + backEventListners.size() + "PT:" + pauseTimers.size() + "NB:" + nextButtonEventListnerList.size() + "FT:" + stimulusFreeTextList.size() + "TL:" + triggerListeners.size() + "BL:" + buttonList.size();
//                debugLabel.setText(debugString);
//                ((TimedStimulusView) simpleView).addWidget(debugLabel);
//                schedule(1000);
//            }
//        }.schedule(1000);
    }

    @Override
    public void setState(AppEventListner appEventListner, ApplicationController.ApplicationState prevState, ApplicationController.ApplicationState nextState) {
        super.setState(appEventListner, prevState, null);
        this.nextState = nextState;
    }

    protected void loadSdCardStimulus(final String eventTag,
            final StimulusSelector[] selectionTags, // only stimuli with tags in this list can be included
            final StimulusSelector[] randomTags,
            final MetadataField stimulusAllocationField,
            final String consumedTagsGroupName,
            final StimuliProvider stimulusProvider,
            final CurrentStimulusListener hasMoreStimulusListener,
            final TimedStimulusListener endOfStimulusListener) {
        final String subDirectory = localStorage.getStoredDataValue(userResults.getUserData().getUserId(), "sdcard-directory-" + getSelfTag());
        submissionService.submitTimeStamp(userResults.getUserData().getUserId(), eventTag, duration.elapsedMillis());
        final String storedStimulusList = localStorage.getStoredDataValue(userResults.getUserData().getUserId(), LOADED_STIMULUS_LIST + getSelfTag() + subDirectory);
        int seenStimulusIndextemp;
        try {
            seenStimulusIndextemp = Integer.parseInt(localStorage.getStoredDataValue(userResults.getUserData().getUserId(), SEEN_STIMULUS_INDEX + getSelfTag()));
        } catch (NumberFormatException exception) {
            seenStimulusIndextemp = 0;
        }
        final int seenStimulusIndex = seenStimulusIndextemp;
        this.hasMoreStimulusListener = hasMoreStimulusListener;
        this.endOfStimulusListener = new TimedStimulusListener() {
            @Override
            public void postLoadTimerFired() {
                if (subDirectory == null || subDirectory.isEmpty()) {
                    endOfStimulusListener.postLoadTimerFired();
                } else {
                    localStorage.setStoredDataValue(userResults.getUserData().getUserId(), "sdcard-directory-" + getSelfTag(), "");
                    loadSdCardStimulus(eventTag, selectionTags, randomTags, stimulusAllocationField, consumedTagsGroupName, stimulusProvider, hasMoreStimulusListener, endOfStimulusListener);
                }
            }
        };
        ArrayList<String> directoryTagArray = new ArrayList<>();
        if (subDirectory == null || subDirectory.isEmpty()) {
            for (StimulusSelector directoryTag : selectionTags) {
                directoryTagArray.add(directoryTag.getTag().name().substring("tag_".length()));
            }
        } else {
            // if a sub directory is passed then only load stimuli from that directory
            directoryTagArray.add(subDirectory);
        }
        final List<String[]> directoryList = new ArrayList<>();
        // @todo: add the limits for maxStimulusCount and maxStimulusPerTag -
        stimulusProvider.getSdCardSubset(directoryTagArray, directoryList, new TimedStimulusListener() {
            @Override
            public void postLoadTimerFired() {
                clearPage();
                if (directoryList.isEmpty()) {
                    showStimulus(stimulusProvider, null, 0);
                } else {
                    hasSubdirectories = true;
                    for (final String[] directory : directoryList) {
                        final boolean directoryCompleted = Boolean.parseBoolean(localStorage.getStoredDataValue(userResults.getUserData().getUserId(), "completed-directory-" + directory[0] + "-" + getSelfTag()));
                        ((TimedStimulusView) simpleView).addOptionButton(new PresenterEventListner() {
                            @Override
                            public String getLabel() {
                                return directory[1] + ((directoryCompleted) ? " (complete)" : "");
                            }

                            @Override
                            public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                                // show the subdirectorydirectory[0], 
                                localStorage.setStoredDataValue(userResults.getUserData().getUserId(), "sdcard-directory-" + getSelfTag(), directory[0]);
                                loadSdCardStimulus(directory[1], selectionTags, randomTags, stimulusAllocationField, consumedTagsGroupName, stimulusProvider, hasMoreStimulusListener, new TimedStimulusListener() {
                                    @Override
                                    public void postLoadTimerFired() {
                                        localStorage.setStoredDataValue(userResults.getUserData().getUserId(), "completed-directory-" + directory[0] + "-" + getSelfTag(), Boolean.toString(true));
                                        // go back to the initial directory 
                                        localStorage.setStoredDataValue(userResults.getUserData().getUserId(), "sdcard-directory-" + getSelfTag(), "");
                                        loadSdCardStimulus(eventTag, selectionTags, randomTags, stimulusAllocationField, consumedTagsGroupName, stimulusProvider, hasMoreStimulusListener, endOfStimulusListener);
                                    }
                                });
                            }

                            @Override
                            public int getHotKey() {
                                return -1;
                            }
                        });
                    }
                }
            }
        }, new TimedStimulusListener() {
            @Override
            public void postLoadTimerFired() {
                ((TimedStimulusView) simpleView).addText("Stimulus loading error");
                // @todo: when sdcard stimuli sub directories are used:  + "Plase make sure that the directory " + stimuliDirectory + "/" + cleanedTag + " exists and has stimuli files."
            }
        }, storedStimulusList, seenStimulusIndex);
    }

    @Override
    protected boolean allowBackAction(final AppEventListner appEventListner) {
        final String subDirectory = localStorage.getStoredDataValue(userResults.getUserData().getUserId(), "sdcard-directory-" + getSelfTag());
        if (subDirectory != null) {
            if (!subDirectory.isEmpty()) {
                localStorage.setStoredDataValue(userResults.getUserData().getUserId(), "sdcard-directory-" + getSelfTag(), "");
                setContent(appEventListner);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    protected void withStimuli(String eventTag,
            final StimulusSelector[] selectionTags, // only stimuli with tags in this list can be included
            final StimulusSelector[] randomTags,
            final MetadataField stimulusAllocationField,
            final String consumedTagsGroupName,
            final StimuliProvider stimulusProvider,
            final TimedStimulusListener beforeStimuliListener,
            final CurrentStimulusListener eachStimulusListener,
            final TimedStimulusListener afterStimuliListener
    ) {
        loadStimulus(stimulusProvider, eventTag, selectionTags, randomTags, stimulusAllocationField, consumedTagsGroupName);
        this.hasMoreStimulusListener = null;
        this.endOfStimulusListener = null;
        beforeStimuliListener.postLoadTimerFired();
        while (stimulusProvider.hasNextStimulus(0)) {
            eachStimulusListener.postLoadTimerFired(stimulusProvider, stimulusProvider.getCurrentStimulus());
            stimulusProvider.nextStimulus(1);
        }
        afterStimuliListener.postLoadTimerFired();
    }

    protected void loadStimulus(String eventTag,
            final StimulusSelector[] selectionTags, // only stimuli with tags in this list can be included
            final StimulusSelector[] randomTags,
            final MetadataField stimulusAllocationField,
            final String consumedTagsGroupName,
            final StimuliProvider stimulusProvider,
            final CurrentStimulusListener hasMoreStimulusListener,
            final TimedStimulusListener endOfStimulusListener
    ) {
        loadStimulus(stimulusProvider, eventTag, selectionTags, randomTags, stimulusAllocationField, consumedTagsGroupName);
        this.hasMoreStimulusListener = hasMoreStimulusListener;
        this.endOfStimulusListener = endOfStimulusListener;
        showStimulus(stimulusProvider, null, 0);
    }

    private void loadStimulus(
            final StimuliProvider stimulusProvider,
            String eventTag,
            final StimulusSelector[] selectionTags, // only stimuli with tags in this list can be included
            final StimulusSelector[] randomTags,
            final MetadataField stimulusAllocationField,
            final String consumedTagsGroupName
    ) {
        submissionService.submitTimeStamp(userResults.getUserData().getUserId(), eventTag, duration.elapsedMillis());
        final String storedStimulusList = localStorage.getStoredDataValue(userResults.getUserData().getUserId(), LOADED_STIMULUS_LIST + getSelfTag());
        int seenStimulusIndex;
        try {
            seenStimulusIndex = Integer.parseInt(localStorage.getStoredDataValue(userResults.getUserData().getUserId(), SEEN_STIMULUS_INDEX + getSelfTag()));
        } catch (NumberFormatException exception) {
            seenStimulusIndex = 0;
        }
        final List<Stimulus.Tag> allocatedTags = new ArrayList<>();
//        final List<StimulusSelector> allocatedTags = new ArrayList<>(Arrays.asList(selectionTags));
        for (StimulusSelector selector : selectionTags) {
            allocatedTags.add(selector.getTag());
        }
        if (randomTags.length > 0) {
            final String storedStimulusAllocation = userResults.getUserData().getMetadataValue(stimulusAllocationField);
            if (storedStimulusAllocation != null && !storedStimulusAllocation.isEmpty()) {
                for (StimulusSelector selector : randomTags) {
                    if (storedStimulusAllocation.equals(selector.getAlias())) {
                        allocatedTags.add(selector.getTag());
                    }
                }
            } else {
                final List<StimulusSelector> randomTagsList = new ArrayList();
                String consumedTagsGroupString = (consumedTagsGroupName != null) ? localStorage.getStoredDataValue(userResults.getUserData().getUserId(), CONSUMED_TAGS_LIST + consumedTagsGroupName) : "";
                for (StimulusSelector currentSelector : randomTags) {
                    if (!consumedTagsGroupString.contains("-" + currentSelector.getAlias() + "-")) {
                        randomTagsList.add(currentSelector);
                    }
                }
                StimulusSelector stimulusAllocation = randomTagsList.get(new Random().nextInt(randomTagsList.size()));
                if (consumedTagsGroupName != null) {
                    localStorage.appendStoredDataValue(userResults.getUserData().getUserId(), CONSUMED_TAGS_LIST + consumedTagsGroupName, "-" + stimulusAllocation.getAlias() + "-");
                }
                userResults.getUserData().setMetadataValue(stimulusAllocationField, stimulusAllocation.getAlias());
                localStorage.storeData(userResults, metadataFieldProvider);
                allocatedTags.add(stimulusAllocation.getTag());
                // submit the user metadata so that the selected stimuli group is stored
                submissionService.submitMetadata(userResults, new DataSubmissionListener() {
                    @Override
                    public void scoreSubmissionFailed(DataSubmissionException exception) {
                    }

                    @Override
                    public void scoreSubmissionComplete(JsArray<DataSubmissionResult> highScoreData) {
                    }
                });
            }
        }
        // @todo: add the limits for maxStimulusCount and maxStimulusPerTag -
        stimulusProvider.getSubset(allocatedTags, storedStimulusList, seenStimulusIndex);
    }

    protected void sendStimuliReport(final StimuliProvider stimulusProvider, String reportType, final int dataChannel) {
        final Map<String, String> stimuliReport = stimulusProvider.getStimuliReport(reportType);
        for (String keyString : stimuliReport.keySet()) {
            submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, reportType, keyString, stimuliReport.get(keyString), duration.elapsedMillis());
        }
    }

    protected void showStimuliReport(final StimuliProvider stimulusProvider) {
        ((TimedStimulusView) simpleView).addHtmlText(stimulusProvider.getHtmlStimuliReport(), null);
    }

    protected void withMatchingStimulus(String eventTag, final String matchingRegex, final StimuliProvider stimulusProvider, final CurrentStimulusListener hasMoreStimulusListener, final TimedStimulusListener endOfStimulusListener) {
        matchingStimuliGroup = new MatchingStimuliGroup(stimulusProvider.getCurrentStimulus(), stimulusProvider.getMatchingStimuli(matchingRegex), true, hasMoreStimulusListener, endOfStimulusListener);
        matchingStimuliGroup.getNextStimulus(stimulusProvider);
        matchingStimuliGroup.showNextStimulus(stimulusProvider);
    }

    public void logTokenText(final String reportType, final String headerKey, final int dataChannel, final String dataLogFormat) {
        submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, reportType, headerKey, new HtmlTokenFormatter(groupParticipantService, userResults.getUserData(), timerService).formatString(dataLogFormat), duration.elapsedMillis());
    }

    public void htmlTokenText(final String textString, final String styleName) {
        ((TimedStimulusView) simpleView).addHtmlText(new HtmlTokenFormatter(groupParticipantService, userResults.getUserData(), timerService).formatString(textString), styleName);
        // the submitTagValue previously used here by the multiparticipant configuration has been migrated to logTokenText which should function the sames for the multiparticipant experiment except that it now uses submitTagPairValue
    }

    public void htmlTokenText(String textString) {
        htmlTokenText(textString, null);
    }

    public void htmlText(String textString, final String styleName) {
        ((TimedStimulusView) simpleView).addHtmlText(textString, styleName);
    }

    protected void countdownLabel(final String timesUpLabel, final int postLoadMs, final String msLabelFormat, final TimedStimulusListener timedStimulusListener) {
        countdownLabel(timesUpLabel, null, postLoadMs, msLabelFormat, timedStimulusListener);
    }

    protected void countdownLabel(final String timesUpLabel, String styleName, final int postLoadMs, final String msLabelFormat, final TimedStimulusListener timedStimulusListener) {
        final Duration labelDuration = new Duration();
        final DateTimeFormat formatter = DateTimeFormat.getFormat(msLabelFormat);
        final HTML html = ((TimedStimulusView) simpleView).addHtmlText(formatter.format(new Date((long) postLoadMs)), styleName);
        Timer labelTimer = new Timer() {
            @Override
            public void run() {
                final long remainingMs = (long) postLoadMs - labelDuration.elapsedMillis();
                if (remainingMs > 0) {
                    Date msBasedDate = new Date(remainingMs);
                    String labelText = formatter.format(msBasedDate);
                    html.setHTML(labelText);
                    schedule(500);
                } else {
                    html.setHTML(timesUpLabel);
                    timedStimulusListener.postLoadTimerFired();
                }
            }
        };
        labelTimer.schedule(500);
    }

    protected void pause(int postLoadMs, final TimedStimulusListener timedStimulusListener) {
        final Timer timer = new Timer() {
            @Override
            public void run() {
                timedStimulusListener.postLoadTimerFired();
                pauseTimers.remove(this);
            }
        };
        pauseTimers.add(timer);
        timer.schedule(postLoadMs);
    }

    protected void randomMsPause(int minimumMs, int maximumMs, final TimedStimulusListener timedStimulusListener) {
        final Timer timer = new Timer() {
            @Override
            public void run() {
                timedStimulusListener.postLoadTimerFired();
                pauseTimers.remove(this);
            }
        };
        pauseTimers.add(timer);
        timer.schedule((int) (Math.random() * (maximumMs - minimumMs) + minimumMs));
    }

    protected void stimulusPause(final Stimulus currentStimulus, final TimedStimulusListener timedStimulusListener) {
        pause(currentStimulus.getPauseMs(), timedStimulusListener);
    }

    protected void currentStimulusHasTag(int postLoadMs, final Stimulus.Tag[] tagList, final Stimulus currentStimulus, final TimedStimulusListener hasTagListener, final TimedStimulusListener hasntTagListener) {
// todo: implement randomTags
//        List<Stimulus.Tag> editableList = new LinkedList<Stimulus.Tag>(tagList);
//        editableList.retainAll();
//        if (editableList.isEmpty()) {
        if (currentStimulus.getTags().containsAll(Arrays.asList(tagList))) {
            pause(postLoadMs, hasTagListener);
        } else {
            pause(postLoadMs, hasntTagListener);
        }
    }

    public void stimulusLabel(final Stimulus currentStimulus) {
        stimulusLabel(currentStimulus, null);
    }

    public void stimulusLabel(final Stimulus currentStimulus, String styleName) {
        final String label = currentStimulus.getLabel();
        if (label != null) {
            ((TimedStimulusView) simpleView).addHtmlText(label, styleName);
        }
    }

    protected void showStimulus(final StimuliProvider stimulusProvider) {
        showStimulus(stimulusProvider, null, 0);
    }

    protected void showStimulus(final StimuliProvider stimulusProvider, GroupActivityListener groupActivityListener, final int increment) {
        final int currentStimulusIndex = stimulusProvider.getCurrentStimulusIndex();
        final String subDirectory = localStorage.getStoredDataValue(userResults.getUserData().getUserId(), "sdcard-directory-" + getSelfTag());
        localStorage.setStoredDataValue(userResults.getUserData().getUserId(), SEEN_STIMULUS_INDEX + getSelfTag() + ((subDirectory != null) ? subDirectory : ""), Integer.toString(currentStimulusIndex));
        localStorage.setStoredDataValue(userResults.getUserData().getUserId(), LOADED_STIMULUS_LIST + getSelfTag() + ((subDirectory != null) ? subDirectory : ""), stimulusProvider.generateStimuliStateSnapshot());
        localStorage.storeUserScore(userResults);
        if (stimulusProvider.hasNextStimulus(increment)) {
            stimulusProvider.nextStimulus(increment);
//            submissionService.submitTagValue(userResults.getUserData().getUserId(), "NextStimulus", stimulusProvider.getCurrentStimulus().getUniqueId(), duration.elapsedMillis());
//            super.startAudioRecorderTag(STIMULUS_TIER);
            hasMoreStimulusListener.postLoadTimerFired(stimulusProvider, stimulusProvider.getCurrentStimulus());
//        } else if (!hasSubdirectories) {
        } else {
            localStorage.setStoredDataValue(userResults.getUserData().getUserId(), "completed-screen-" + getSelfTag(), Boolean.toString(true));
            submissionService.submitTagValue(userResults.getUserData().getUserId(), getSelfTag(), "showStimulus.endOfStimulusListener", (currentStimulusIndex + increment) + "/" + stimulusProvider.getTotalStimuli(), duration.elapsedMillis()); // todo: this is sent
            localStorage.setStoredDataValue(userResults.getUserData().getUserId(), SEEN_STIMULUS_INDEX + getSelfTag() + ((subDirectory != null) ? subDirectory : ""), Integer.toString(currentStimulusIndex + increment));
            localStorage.setStoredDataValue(userResults.getUserData().getUserId(), LOADED_STIMULUS_LIST + getSelfTag() + ((subDirectory != null) ? subDirectory : ""), stimulusProvider.generateStimuliStateSnapshot());
            endOfStimulusListener.postLoadTimerFired();
        }
    }
//    private static final int STIMULUS_TIER = 2;

    @Deprecated // @todo: is this really used anymore? -
    protected void removeStimulus(final StimuliProvider stimulusProvider, final Stimulus currentStimulus) {
        stimulusProvider.nextStimulus(1);
        //localStorage.setStoredDataValue(userResults.getUserData().getUserId(), SEEN_STIMULUS_INDEX + getSelfTag(), Integer.toString(stimulusProvider.getCurrentStimulusIndex()));
    }

    protected void nextMatchingStimulus(final StimuliProvider stimulusProvider) {
        matchingStimuliGroup.getNextStimulus(stimulusProvider);
        matchingStimuliGroup.showNextStimulus(stimulusProvider);
    }

    protected void removeMatchingStimulus(final String matchingRegex) {
        throw new UnsupportedOperationException("todo: removeMatchingStimulus");
    }

    protected void keepStimulus(final StimuliProvider stimulusProvider, final Stimulus currentStimulus) {
        stimulusProvider.pushCurrentStimulusToEnd();
    }

    protected void groupNetwork(final AppEventListner appEventListner, final ApplicationState selfApplicationState, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String groupMembers, final String groupCommunicationChannels, final int phasesPerStimulus, final TimedStimulusListener timedStimulusListener) {
        if (groupParticipantService == null) {
            final Timer groupKickTimer = new Timer() {
                @Override
                public void run() {
                    groupParticipantService.messageGroup(0, 0, stimulusProvider.getCurrentStimulusUniqueId(), Integer.toString(stimulusProvider.getCurrentStimulusIndex()), null, null, null, (int) userResults.getUserData().getCurrentScore(), groupMembers);
                }
            };
            groupParticipantService = new GroupParticipantService(
                    userResults.getUserData().getUserId().toString(),
                    getSelfTag(), groupMembers, groupCommunicationChannels,
                    phasesPerStimulus,
                    stimulusProvider.generateStimuliStateSnapshot(),
                    new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    // do not clear the screen at this point because reconnects when the stimuli list is at the end will need to keep its UI items
                    clearPage();
                    ((ComplexView) simpleView).addPadding();
                    if (groupParticipantService.isConnected()) {
                        ((ComplexView) simpleView).addText("connected, waiting for other members");
                    } else {
                        ((ComplexView) simpleView).addText("not connected");
                    }
                    timedStimulusListener.postLoadTimerFired();
                    groupKickTimer.schedule(1000);
                }
            }, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    clearPage();
                    ((ComplexView) simpleView).addPadding();
//                    ((ComplexView) simpleView).addText("connected: " + groupParticipantService.isConnected());
                    ((ComplexView) simpleView).addHtmlText("Group not ready", "highlightedText");
                    ((ComplexView) simpleView).addPadding();
                    groupKickTimer.schedule(1000);
                }
            }, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    ((ComplexView) simpleView).addPadding();
                    ((ComplexView) simpleView).addText("synchronising the stimuli");
                    final String stimuliListGroup = groupParticipantService.getStimuliListGroup();
                    // when the stimuli list for this screen does not match that of the group, this listener is fired to: save the group stimuli list and then load the group stimuli list
                    stimulusProvider.initialiseStimuliState(stimuliListGroup);
                    final String loadedStimulusString = stimulusProvider.generateStimuliStateSnapshot();
                    localStorage.setStoredDataValue(userResults.getUserData().getUserId(), LOADED_STIMULUS_LIST + getSelfTag(), loadedStimulusString);
                    groupParticipantService.setStimuliListLoaded(loadedStimulusString);
                    groupKickTimer.schedule(1000);
                }
            }, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    if (groupParticipantService.getStimulusIndex() < stimulusProvider.getTotalStimuli()) {
                        if (groupParticipantService.getStimulusIndex() != stimulusProvider.getCurrentStimulusIndex()) {
                            groupParticipantService.setResponseStimulusId(null);
                            groupParticipantService.setResponseStimulusOptions(null);
                        } else if (groupParticipantService.getMessageString() != null && !groupParticipantService.getMessageString().isEmpty()) {
                            JSONObject storedStimulusJSONObject = localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus);
                            storedStimulusJSONObject = (storedStimulusJSONObject == null) ? new JSONObject() : storedStimulusJSONObject;
                            storedStimulusJSONObject.put("groupMessage", new JSONString(groupParticipantService.getMessageString()));
                            localStorage.setStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus, storedStimulusJSONObject);
                            // submissionService.writeJsonData would be called on next stimulus anyway: submissionService.writeJsonData(userResults.getUserData().getUserId().toString(), currentStimulus.getUniqueId(), storedStimulusJSONObject.toString());
                        }
                        // when a valid message has been received the current stimuli needs to be synchronised with the group
                        stimulusProvider.setCurrentStimuliIndex(groupParticipantService.getStimulusIndex());
                    } else {
                        // if the group message puts the stimuli list at the end then fire the end of stimulus listner
                        submissionService.submitTagValue(userResults.getUserData().getUserId(), getSelfTag(), "group message puts the stimuli list at the end", stimulusProvider.getCurrentStimulusUniqueId() + ":" + stimulusProvider.getCurrentStimulusIndex() + "/" + stimulusProvider.getTotalStimuli(), duration.elapsedMillis());
                        groupParticipantService.setEndOfStimuli(true); // block further messages
                        if (endOfStimulusListener != null) {
                            endOfStimulusListener.postLoadTimerFired();
                        }
                    }
                }
            }, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    ((ComplexView) simpleView).addInfoButton(new PresenterEventListner() {
                        @Override
                        public String getLabel() {
                            final Integer stimulusIndex = groupParticipantService.getStimulusIndex();
//                            final String activeChannel = groupParticipantService.getActiveChannel();
                            return groupParticipantService.getMemberCode()
                                    //                                    + ((stimulusIndex != null) ? "("
                                    //                                            + activeChannel
                                    //                                            + ")" : "")
                                    + ((stimulusIndex != null) ? "-T"
                                            + (stimulusIndex + 1) : "");
                        }

                        @Override
                        public void eventFired(ButtonBase button, final SingleShotEventListner shotEventListner) {
                            groupParticipantService.messageGroup(0, 0, stimulusProvider.getCurrentStimulusUniqueId(), Integer.toString(stimulusProvider.getCurrentStimulusIndex()), null, null, null, (int) userResults.getUserData().getCurrentScore(), groupMembers);
//                            ((ComplexView) simpleView).showHtmlPopup(null,
//                                    "Group Members\n" + groupParticipantService.getAllMemberCodes()
//                                    + "\n\nGroup Communication Channels\n" + groupParticipantService.getGroupCommunicationChannels()
//                                    + "\n\nGroupId\n" + groupParticipantService.getGroupId()
//                                    + "\n\nGroupUUID\n" + groupParticipantService.getGroupUUID()
//                                    + "\n\nMemberCode\n" + groupParticipantService.getMemberCode()
//                                    + "\n\nMessageSender\n" + groupParticipantService.getMessageSenderId()
//                                    + "\n\nMessageString\n" + groupParticipantService.getMessageString()
//                                    + "\n\nStimulusId\n" + groupParticipantService.getStimulusId()
//                                    + "\n\nStimuliListLoaded\n" + groupParticipantService.getStimuliListLoaded()
//                                    + "\n\nStimuliListGroup\n" + groupParticipantService.getStimuliListGroup()
//                                    + "\n\nResponseStimulusOptions\n" + groupParticipantService.getResponseStimulusOptions()
//                                    + "\n\nResponseStimulusId\n" + groupParticipantService.getResponseStimulusId()
//                                    + "\n\nStimulusIndex\n" + groupParticipantService.getStimulusIndex()
//                                    + "\n\nRequestedPhase\n" + groupParticipantService.getRequestedPhase()
//                                    + "\n\nUserLabel\n" + groupParticipantService.getUserLabel()
//                                    + "\n\nGroupReady\n" + groupParticipantService.isGroupReady()
//                            );
                            shotEventListner.resetSingleShot();
                        }

                        @Override
                        public int getHotKey() {
                            return -1;
                        }
                    });
                }
            }
            //                    , endOfStimulusListener
            );
            groupParticipantService.joinGroupNetwork(serviceLocations.groupServerUrl());
        } else {
            timedStimulusListener.postLoadTimerFired();
//            groupParticipantService.messageGroup(0, currentStimulus.getUniqueId(), Integer.toString(stimulusProvider.getCurrentStimulusIndex()), null, null, null);
//              groupParticipantService.messageGroup(0, currentStimulus.getUniqueId(), Integer.toString(stimulusProvider.getCurrentStimulusIndex()), messageString, groupParticipantService.getResponseStimulusOptions(), groupParticipantService.getResponseStimulusId());
        }
    }

    protected void groupNetworkActivity(final GroupActivityListener timedStimulusListener) {
        groupParticipantService.addGroupActivity(timedStimulusListener);
    }

    public void submitGroupEvent() {
        submissionService.submitGroupEvent(userResults.getUserData().getUserId(),
                groupParticipantService.getGroupUUID(),
                groupParticipantService.getGroupId(),
                groupParticipantService.getAllMemberCodes(),
                groupParticipantService.getGroupCommunicationChannels(),
                getSelfTag(),
                groupParticipantService.getMemberCode(),
                groupParticipantService.getUserLabel(),
                groupParticipantService.getStimulusId(),
                groupParticipantService.getStimulusIndex(),
                groupParticipantService.getMessageSenderId(),
                groupParticipantService.getMessageString(),
                groupParticipantService.getResponseStimulusId(),
                groupParticipantService.getResponseStimulusOptions(),
                groupParticipantService.getMessageSenderId(),
                groupParticipantService.getMessageSenderMemberCode(),
                duration.elapsedMillis());
    }

    protected void scoreAboveThreshold(final Integer scoreThreshold, final Integer errorThreshold, final Integer potentialThreshold, final Integer correctStreak, final Integer errorStreak, final TimedStimulusListener aboveThreshold, final TimedStimulusListener withinThreshold) {
        boolean isWithin = true;
        if (scoreThreshold != null) {
            if (userResults.getUserData().getCurrentScore() > scoreThreshold) {
                isWithin = false;
            }
        }
        if (errorThreshold != null) {
            if (userResults.getUserData().getPotentialScore() - userResults.getUserData().getCurrentScore() > errorThreshold) {
                isWithin = false;
            }
        }
        if (potentialThreshold != null) {
            if (userResults.getUserData().getPotentialScore() > potentialThreshold) {
                isWithin = false;
            }
        }
        if (correctStreak != null) {
            if (userResults.getUserData().getCorrectStreak() > correctStreak) {
                isWithin = false;
            }
        }
        if (errorStreak != null) {
            if (userResults.getUserData().getErrorStreak() > errorStreak) {
                isWithin = false;
            }
        }
        if (isWithin) {
            withinThreshold.postLoadTimerFired();
        } else {
            aboveThreshold.postLoadTimerFired();
        }
    }

    protected void bestScoreAboveThreshold(final Integer scoreThreshold, final Integer errorThreshold, final Integer potentialThreshold, final Integer correctStreak, final Integer errorStreak, final TimedStimulusListener aboveThreshold, final TimedStimulusListener withinThreshold) {
        boolean isWithin = true;
        if (scoreThreshold != null) {
            if (userResults.getUserData().getMaxScore() > scoreThreshold) {
                isWithin = false;
            }
        }
        if (errorThreshold != null) {
            if (userResults.getUserData().getMaxErrors() > errorThreshold) {
                isWithin = false;
            }
        }
        if (potentialThreshold != null) {
            if (userResults.getUserData().getMaxPotentialScore() > potentialThreshold) {
                isWithin = false;
            }
        }
        if (correctStreak != null) {
            if (userResults.getUserData().getMaxCorrectStreak() > correctStreak) {
                isWithin = false;
            }
        }
        if (errorStreak != null) {
            if (userResults.getUserData().getMaxErrorStreak() > errorStreak) {
                isWithin = false;
            }
        }
        if (isWithin) {
            withinThreshold.postLoadTimerFired();
        } else {
            aboveThreshold.postLoadTimerFired();
        }
    }

    protected void totalScoreAboveThreshold(final Integer scoreThreshold, final Integer errorThreshold, final Integer potentialThreshold, final TimedStimulusListener aboveThreshold, final TimedStimulusListener withinThreshold) {
        boolean isWithin = true;
        if (scoreThreshold != null) {
            if (userResults.getUserData().getTotalScore() > scoreThreshold) {
                isWithin = false;
            }
        }
        if (errorThreshold != null) {
            if (userResults.getUserData().getTotalPotentialScore() - userResults.getUserData().getTotalScore() > errorThreshold) {
                isWithin = false;
            }
        }
        if (potentialThreshold != null) {
            if (userResults.getUserData().getTotalPotentialScore() > potentialThreshold) {
                isWithin = false;
            }
        }
        if (isWithin) {
            withinThreshold.postLoadTimerFired();
        } else {
            aboveThreshold.postLoadTimerFired();
        }
    }

    protected void resetStimulus(final String stimuliScreenToReset) {
        if (stimuliScreenToReset != null) {
            localStorage.deleteStoredDataValue(userResults.getUserData().getUserId(), LOADED_STIMULUS_LIST + stimuliScreenToReset);
            localStorage.deleteStoredDataValue(userResults.getUserData().getUserId(), SEEN_STIMULUS_INDEX + stimuliScreenToReset);
        }
    }

    protected void clearCurrentScore() {
        userResults.getUserData().addGamePlayed();
        userResults.getUserData().clearCurrentScore();
        localStorage.storeUserScore(userResults);
    }

    protected void scoreIncrement(final boolean isCorrect) {
        userResults.getUserData().addPotentialScore(isCorrect);
        submissionService.submitTagValue(userResults.getUserData().getUserId(), getSelfTag(), "scoreIncrement", userResults.getUserData().getCurrentScore() + "/" + userResults.getUserData().getPotentialScore(), duration.elapsedMillis());
    }

    protected void scoreLabel() {
        scoreLabel(null);
    }

    protected void scoreLabel(String styleName) {
        ((TimedStimulusView) simpleView).addHtmlText("Current Score: " + userResults.getUserData().getCurrentScore() + "/" + userResults.getUserData().getPotentialScore(), styleName);
        ((TimedStimulusView) simpleView).addHtmlText("Best Score: " + userResults.getUserData().getMaxScore(), styleName);
    }

    protected void groupChannelScoreLabel() {
        groupChannelScoreLabel(null);
    }

    protected void groupChannelScoreLabel(String styleName) {
        if (groupParticipantService != null) {
            ((TimedStimulusView) simpleView).addHtmlText("Channel Score: " + groupParticipantService.getChannelScore(), styleName);
        }
    }

    protected void groupMessageLabel() {
        groupMessageLabel(null);
    }

    protected void groupMessageLabel(String styleName) {
        ((TimedStimulusView) simpleView).addHtmlText(groupParticipantService.getMessageString(), styleName);
    }

    protected void groupScoreLabel() {
        groupScoreLabel(null);

    }

    protected void groupScoreLabel(String styleName) {
        if (groupParticipantService != null) {
            ((TimedStimulusView) simpleView).addHtmlText("Group Score: " + groupParticipantService.getGroupScore(), styleName);
        }
    }

    protected void groupMemberLabel() {
        groupMemberLabel(null);
    }

    protected void groupMemberLabel(String styleName) {
        ((TimedStimulusView) simpleView).addHtmlText(groupParticipantService.getUserLabel(), styleName);
    }

    protected void groupMemberCodeLabel() {
        groupMemberCodeLabel(null);
    }

    protected void groupMemberCodeLabel(String styleName) {
        ((TimedStimulusView) simpleView).addHtmlText(groupParticipantService.getMemberCode(), styleName);
    }

    protected void groupResponseFeedback(final AppEventListner appEventListner, int postLoadMs1, final TimedStimulusListener correctListener, int postLoadMs2, final TimedStimulusListener incorrectListener) {
        // todo: make use of the postLoadMs 
        groupResponseFeedback(appEventListner, correctListener, incorrectListener);
    }

    protected void groupResponseFeedback(final AppEventListner appEventListner, final TimedStimulusListener correctListener, final TimedStimulusListener incorrectListener) {
        if (groupParticipantService.getStimulusId().equals(groupParticipantService.getResponseStimulusId())) {
            correctListener.postLoadTimerFired();
        } else {
            incorrectListener.postLoadTimerFired();
        }

    }

    public void stimulusHasResponse(final AppEventListner appEventListner, final Stimulus currentStimulus, final TimedStimulusListener correctListener, final TimedStimulusListener incorrectListener) {
        if (localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus) != null) {
            correctListener.postLoadTimerFired();
        } else {
            incorrectListener.postLoadTimerFired();
        }
    }

    protected void stimulusMetadataField(final Stimulus currentStimulus, MetadataField metadataField, final int dataChannel) {
        final JSONObject storedStimulusJSONObject = localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus);
        final String fieldValue;
        if (storedStimulusJSONObject != null) {
            fieldValue = storedStimulusJSONObject.containsKey(metadataField.getPostName()) ? storedStimulusJSONObject.get(metadataField.getPostName()).isString().stringValue() : "";
        } else {
            fieldValue = "";
        }
        final MetadataFieldWidget metadataFieldWidget = new MetadataFieldWidget(metadataField, currentStimulus, fieldValue, dataChannel);
        ((TimedStimulusView) simpleView).addWidget(metadataFieldWidget.getLabel());
        ((TimedStimulusView) simpleView).addWidget(metadataFieldWidget.getWidget());
        stimulusFreeTextList.add(metadataFieldWidget);
    }

    protected void stimulusFreeText(final Stimulus currentStimulus, String validationRegex, String keyCodeChallenge, String validationChallenge, final String allowedCharCodes, final int hotKey, String styleName, final int dataChannel) {
        final JSONObject storedStimulusJSONObject = localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus);
        final String postName = "freeText";
        final JSONValue freeTextValue = (storedStimulusJSONObject == null) ? null : storedStimulusJSONObject.get(postName);
        StimulusFreeText stimulusFreeText = ((TimedStimulusView) simpleView).addStimulusFreeText(currentStimulus, postName, validationRegex, keyCodeChallenge, validationChallenge, allowedCharCodes, new SingleShotEventListner() {
            @Override
            protected void singleShotFired() {
                for (PresenterEventListner nextButtonEventListner : nextButtonEventListnerList) {
                    // this process is to make sure that group events are submitted and not just call nextStimulus
                    if (nextButtonEventListner.getHotKey() == hotKey) {
                        nextButtonEventListner.eventFired(null, this);
                    } else {
//                    nextStimulus("stimulusFreeTextEnter", false);
                    }
                }
                this.resetSingleShot();
            }
        }, hotKey, styleName, dataChannel, ((freeTextValue != null) ? freeTextValue.isString().stringValue() : null));
        stimulusFreeTextList.add(stimulusFreeText);
    }

    protected void stimulusImageCapture(final StimuliProvider stimulusProvider, final Stimulus currentStimulusO, final String captureLabel, int percentOfPage, int maxHeight, int maxWidth, int postLoadMs, final TimedStimulusListener timedStimulusListener) {
        final SdCardStimulus currentStimulus = (SdCardStimulus) currentStimulusO;
        final SdCardImageCapture sdCardImageCapture = new SdCardImageCapture(new TimedStimulusListener() {
            @Override
            public void postLoadTimerFired() {
                clearPage();
                // cause the taken image to be shown
                hasMoreStimulusListener.postLoadTimerFired(stimulusProvider, currentStimulusO);
            }
        }, currentStimulus, userResults.getUserData().getUserId(), localStorage);
        if (sdCardImageCapture.hasBeenCaptured()) {
            final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
                @Override
                protected void trigggerCancelableEvent() {
                    timedStimulusListener.postLoadTimerFired();
                }
            };
            ((TimedStimulusView) simpleView).addTimedImage(UriUtils.fromTrustedString(sdCardImageCapture.getCapturedPath()), percentOfPage, maxHeight, maxWidth, null, null, postLoadMs, shownStimulusListener, shownStimulusListener, null, null);
        }
        ((TimedStimulusView) simpleView).addOptionButton(new PresenterEventListner() {
            @Override
            public String getLabel() {
                return captureLabel;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                sdCardImageCapture.captureImage();
            }

            @Override
            public int getHotKey() {
                return -1;
            }
        });
    }

    protected void backgroundImage(final String imageSrc, String styleName, int postLoadMs, final TimedStimulusListener timedStimulusListener) {
        ((TimedStimulusView) simpleView).addBackgroundImage((imageSrc == null || imageSrc.isEmpty()) ? null : UriUtils.fromTrustedString((imageSrc.startsWith("file")) ? imageSrc : serviceLocations.staticFilesUrl() + imageSrc), styleName, postLoadMs, timedStimulusListener);
    }

    protected void stimulusImage(final Stimulus currentStimulus, int postLoadMs, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener) {
        stimulusImage(currentStimulus, null, postLoadMs, dataChannel, loadedStimulusListener, failedStimulusListener);
    }

    protected void stimulusImage(final Stimulus currentStimulus, final String styleName, int postLoadMs, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener) {
        final String imageString = currentStimulus.getImage();
        final String uniqueId = currentStimulus.getUniqueId();
        final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
            @Override
            protected void trigggerCancelableEvent() {
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusImageShown", uniqueId, imageString, duration.elapsedMillis());
            }
        };
        ((TimedStimulusView) simpleView).addTimedImage(UriUtils.fromString(imageString), styleName, postLoadMs, shownStimulusListener, loadedStimulusListener, failedStimulusListener, null);
    }

    @Deprecated
    protected void stimulusPresent(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, int percentOfPage, int maxHeight, int maxWidth, final boolean showControls, int postLoadMs, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        stimulusPresent(stimulusProvider, currentStimulus, percentOfPage, maxHeight, maxWidth, AnimateTypes.none, showControls, postLoadMs, dataChannel, loadedStimulusListener, failedStimulusListener, playedStimulusListener);
    }

    @Deprecated
    protected void stimulusPresent(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, int percentOfPage, int maxHeight, int maxWidth, final AnimateTypes animateType, final boolean showControls, int postLoadMs, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        stimulusPresent(stimulusProvider, currentStimulus, percentOfPage, maxHeight, maxWidth, animateType, showControls, null, postLoadMs, null, null, dataChannel, loadedStimulusListener, failedStimulusListener, playedStimulusListener, null);
    }

    @Deprecated
    protected void stimulusPresent(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, int percentOfPage, int maxHeight, int maxWidth, final AnimateTypes animateType, final boolean showControls, int postLoadMs, String regex, String replacement, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        stimulusPresent(stimulusProvider, currentStimulus, percentOfPage, maxHeight, maxWidth, animateType, showControls, null, postLoadMs, regex, replacement, dataChannel, loadedStimulusListener, failedStimulusListener, playedStimulusListener, null);
    }

    @Deprecated
    protected void stimulusPresent(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, int percentOfPage, int maxHeight, int maxWidth, final AnimateTypes animateType, final Integer fixedPositionY, int postLoadMs, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        stimulusPresent(stimulusProvider, currentStimulus, percentOfPage, maxHeight, maxWidth, animateType, true, fixedPositionY, postLoadMs, null, null, dataChannel, loadedStimulusListener, failedStimulusListener, playedStimulusListener, null);
    }

//    @Deprecated
//    protected void stimulusPresent(final Stimulus currentStimulus, int percentOfPage, int maxHeight, int maxWidth, final AnimateTypes animateType, final boolean showControls, final Integer fixedPositionY, int postLoadMs, String regex, String replacement, final TimedStimulusListener timedStimulusListener, final TimedStimulusListener clickedStimulusListener) {
//        stimulusPresent(currentStimulus, percentOfPage, maxHeight, maxWidth, animateType, showControls, fixedPositionY, postLoadMs, regex, replacement, timedStimulusListener, clickedStimulusListener);
//    }
    protected void stimulusPresent(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, int percentOfPage, int maxHeight, int maxWidth, final AnimateTypes animateType, final boolean showControls, final Integer fixedPositionY, int postLoadMs, String regex, String replacement, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playedStimulusListener, final CancelableStimulusListener clickedStimulusListener) {
        if (currentStimulus.hasImage()) {
            final String image;
            if (regex != null && replacement != null) {
                image = currentStimulus.getImage().replaceAll(regex, replacement);
            } else {
                image = currentStimulus.getImage();
            }
            final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
                @Override
                protected void trigggerCancelableEvent() {
                    // send image shown tag
                    submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusImageShown", currentStimulus.getUniqueId(), image, duration.elapsedMillis());
                }
            };
//            submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusImage", image, duration.elapsedMillis());
            final String animateStyle;
            if (animateType == AnimateTypes.stimuliCode) {
                animateStyle = currentStimulus.getCode() + "Animation";
            } else if (animateType != AnimateTypes.none) {
                animateStyle = animateType.name() + "Animation";
            } else {
                animateStyle = null;
            }
            ((TimedStimulusView) simpleView).addTimedImage(UriUtils.fromTrustedString(image), percentOfPage, maxHeight, maxWidth, animateStyle, fixedPositionY, postLoadMs, shownStimulusListener, new CancelableStimulusListener() {
                @Override
                protected void trigggerCancelableEvent() {
                    loadedStimulusListener.postLoadTimerFired();
                    playedStimulusListener.postLoadTimerFired();
                }
            }, failedStimulusListener, clickedStimulusListener);
//        ((TimedStimulusView) simpleView).addText("addStimulusImage: " + duration.elapsedMillis() + "ms");
        } else if (currentStimulus.hasAudio()) {
            String mp3 = currentStimulus.getAudio() + ".mp3";
            String ogg = currentStimulus.getAudio() + ".ogg";
            if (regex != null && replacement != null) {
                mp3 = mp3.replaceAll(regex, replacement);
                ogg = ogg.replaceAll(regex, replacement);
            }
            final SafeUri oggTrustedString = (ogg == null) ? null : UriUtils.fromTrustedString(ogg);
            final SafeUri mp3TrustedString = (mp3 == null) ? null : UriUtils.fromTrustedString(mp3);
            final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
                @Override
                protected void trigggerCancelableEvent() {
                    // send audio shown tag
                    submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusAudioShown", currentStimulus.getUniqueId(), currentStimulus.getAudio(), duration.elapsedMillis());
                    loadedStimulusListener.postLoadTimerFired();
                }
            };
//            submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusAudio", currentStimulus.getAudio(), duration.elapsedMillis());
            ((TimedStimulusView) simpleView).addTimedAudio(oggTrustedString, mp3TrustedString, postLoadMs, false, shownStimulusListener, failedStimulusListener, playedStimulusListener);
        } else if (currentStimulus.hasVideo()) {
            String ogg = currentStimulus.getVideo() + ".ogg";
            String mp4 = currentStimulus.getVideo() + ".mp4";
            if (regex != null && replacement != null) {
                mp4 = mp4.replaceAll(regex, replacement);
                ogg = ogg.replaceAll(regex, replacement);
            }
//            submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusVideo", currentStimulus.getVideo(), duration.elapsedMillis());
            final SafeUri oggTrustedString = (ogg == null) ? null : UriUtils.fromTrustedString(ogg);
            final SafeUri mp4TrustedString = (mp4 == null) ? null : UriUtils.fromTrustedString(mp4);
            final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
                @Override
                protected void trigggerCancelableEvent() {
                    // send video shown tag
                    submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusVideoShown", currentStimulus.getUniqueId(), currentStimulus.getVideo(), duration.elapsedMillis());
                    loadedStimulusListener.postLoadTimerFired();
                }
            };
            ((TimedStimulusView) simpleView).addTimedVideo(oggTrustedString, mp4TrustedString, percentOfPage, maxHeight, maxWidth, null, false, false, showControls, postLoadMs, shownStimulusListener, failedStimulusListener, playedStimulusListener);
        } else if (currentStimulus.getLabel() != null) {
            ((TimedStimulusView) simpleView).addHtmlText(currentStimulus.getLabel(), null);
            // send label shown tag
            submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusLabelShown", currentStimulus.getUniqueId(), currentStimulus.getLabel(), duration.elapsedMillis());
            loadedStimulusListener.postLoadTimerFired();
            playedStimulusListener.postLoadTimerFired();
        } else {
            final String incorrect_stimulus_format = "incorrect stimulus format";
            nextStimulusButton(stimulusProvider, currentStimulus, incorrect_stimulus_format, incorrect_stimulus_format + " " + currentStimulus.getLabel(), null, true, -1);
        }
    }

//    protected void stimulusCodeImage(int percentOfPage, int maxHeight, int maxWidth, final AnimateTypes animateType, int postLoadMs, String codeFormat, TimedStimulusListener timedStimulusListener) {
    protected void stimulusCodeImage(final Stimulus currentStimulus, final String styleName, int postLoadMs, String codeFormat, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener) {
        String formattedCodeTemp = codeFormat.replace("<code>", currentStimulus.getCode());
        if (currentStimulus.hasRatingLabels()) {
            int index = 0;
            for (final String ratingLabel : currentStimulus.getRatingLabels().split(",")) {
                formattedCodeTemp = formattedCodeTemp.replace("<rating_" + index + ">", ratingLabel);
                index++;
            }
        }
        final String formattedCode = formattedCodeTemp;
        final String uniqueId = currentStimulus.getUniqueId();
//        submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusCodeImage", formattedCode, duration.elapsedMillis());
        final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
            @Override
            protected void trigggerCancelableEvent() {
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusCodeImageShown", uniqueId, formattedCode, duration.elapsedMillis());
            }
        };
        ((TimedStimulusView) simpleView).addTimedImage(UriUtils.fromString(serviceLocations.staticFilesUrl() + formattedCode), styleName, postLoadMs, shownStimulusListener, loadedStimulusListener, failedStimulusListener, null);
//        ((TimedStimulusView) simpleView).addText("addStimulusImage: " + duration.elapsedMillis() + "ms");
    }

    protected void stimulusCodeAudio(final Stimulus currentStimulus, int postLoadMs, String codeFormat, boolean showPlaybackIndicator, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        final String formattedCode = codeFormat.replace("<code>", currentStimulus.getCode());
        final String uniqueId = currentStimulus.getUniqueId();

        String mp3 = formattedCode + ".mp3";
        String ogg = formattedCode + ".ogg";
        final SafeUri oggTrustedString = (ogg == null) ? null : UriUtils.fromTrustedString((ogg.startsWith("file") ? "" : serviceLocations.staticFilesUrl()) + ogg);
        final SafeUri mp3TrustedString = (mp3 == null) ? null : UriUtils.fromTrustedString((mp3.startsWith("file") ? "" : serviceLocations.staticFilesUrl()) + mp3);
//        submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusCodeAudio", formattedCode, duration.elapsedMillis());
//        submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusAudio", formattedCode, duration.elapsedMillis());
        final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
            @Override
            protected void trigggerCancelableEvent() {
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusCodeAudioShown", uniqueId, formattedCode, duration.elapsedMillis());
                loadedStimulusListener.postLoadTimerFired();
            }
        };
        ((TimedStimulusView) simpleView).addTimedAudio(oggTrustedString, mp3TrustedString, postLoadMs, showPlaybackIndicator, shownStimulusListener, failedStimulusListener, playedStimulusListener);
    }

    protected void stimulusCodeVideo(final Stimulus currentStimulus, int percentOfPage, int maxHeight, int maxWidth, final String styleName, final boolean autoPlay, final boolean loop, final boolean showControls, int postLoadMs, String codeFormat, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        final String formattedCode = codeFormat.replace("<code>", currentStimulus.getCode());
        final String uniqueId = currentStimulus.getUniqueId();
        String mp4 = formattedCode + ".mp4";
        String ogg = formattedCode + ".ogg";
        final SafeUri oggTrustedString = (ogg == null) ? null : UriUtils.fromTrustedString(serviceLocations.staticFilesUrl() + ogg);
        final SafeUri mp4TrustedString = (mp4 == null) ? null : UriUtils.fromTrustedString(serviceLocations.staticFilesUrl() + mp4);
//        submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusCodeVideo", formattedCode, duration.elapsedMillis());
        final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
            @Override
            protected void trigggerCancelableEvent() {
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusCodeVideoShown", uniqueId, formattedCode, duration.elapsedMillis());
                loadedStimulusListener.postLoadTimerFired();
            }
        };
//        submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusAudio", formattedCode, duration.elapsedMillis());
        ((TimedStimulusView) simpleView).addTimedVideo(oggTrustedString, mp4TrustedString, percentOfPage, maxHeight, maxWidth, styleName, autoPlay, loop, showControls, postLoadMs, shownStimulusListener, failedStimulusListener, playedStimulusListener);
    }

    protected void stimulusAudio(final Stimulus currentStimulus, int postLoadMs, boolean showPlaybackIndicator, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        final String audio = currentStimulus.getAudio();
        final String uniqueId = currentStimulus.getUniqueId();
        String ogg = audio + ".ogg";
        String mp3 = audio + ".mp3";
//        submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusAudio", ogg, duration.elapsedMillis());
        final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
            @Override
            protected void trigggerCancelableEvent() {
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusAudioShown", uniqueId, audio, duration.elapsedMillis());
                loadedStimulusListener.postLoadTimerFired();
            }
        };
        ((TimedStimulusView) simpleView).addTimedAudio(UriUtils.fromTrustedString(ogg), UriUtils.fromTrustedString(mp3), postLoadMs, showPlaybackIndicator, shownStimulusListener, failedStimulusListener, playedStimulusListener);
//        ((TimedStimulusView) simpleView).addText("playStimulusAudio: " + duration.elapsedMillis() + "ms");
    }

    public void stimulusHasRatingOptions(final AppEventListner appEventListner, final Stimulus currentStimulus, final TimedStimulusListener correctListener, final TimedStimulusListener incorrectListener) {
        if (currentStimulus.hasRatingLabels()) {
            correctListener.postLoadTimerFired();
        } else {
            incorrectListener.postLoadTimerFired();
        }
    }

    public void touchInputStimulusButton(final PresenterEventListner presenterListerner, final String eventTag, final String styleName, final String imagePath, final String buttonGroup) {
        final StimulusButton buttonItem;
        if (imagePath == null || imagePath.isEmpty()) {
            buttonItem = ((ComplexView) simpleView).addOptionButton(presenterListerner, styleName);
        } else {
            buttonItem = ((ComplexView) simpleView).addImageButton(presenterListerner, UriUtils.fromString(serviceLocations.staticFilesUrl() + imagePath), styleName, true);
        }
        buttonList.add(buttonItem);
        touchInputCapture.addTouchZone(new TouchInputZone() {
            boolean isTriggered = false;

            @Override
            public String getEventTag() {
                return eventTag;
            }

            @Override
            public String getGroupName() {
                return buttonGroup;
            }

            @Override
            public boolean intersects(int xPos, int yPos) {
                boolean returnValue = (yPos >= buttonItem.getWidget().getAbsoluteTop()
                        && yPos <= buttonItem.getWidget().getAbsoluteTop() + buttonItem.getWidget().getOffsetHeight()
                        && xPos >= buttonItem.getWidget().getAbsoluteLeft()
                        && xPos <= buttonItem.getWidget().getAbsoluteLeft() + buttonItem.getWidget().getOffsetWidth());
                return returnValue;
            }

            @Override
            public void triggerEvent() {
                if (!isTriggered) {
                    isTriggered = true;
                    buttonItem.addStyleName(styleName + "Intersection");
                    buttonItem.triggerSingleShotEventListner();
                }
            }

            @Override
            public void clearEvent() {
                buttonItem.removeStyleName(styleName + "Intersection");
                isTriggered = false;
            }
        });
    }

    public void stimulusButton(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final PresenterEventListner presenterListerner, String styleName, final int dataChannel) {
        final StimulusButton buttonItem = ((ComplexView) simpleView).addOptionButton(new PresenterEventListner() {
            @Override
            public String getLabel() {
                return presenterListerner.getLabel();
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusButton", currentStimulus.getUniqueId(), presenterListerner.getLabel(), duration.elapsedMillis());
                if (currentStimulus.hasCorrectResponses()) {
                    // if there are correct responses to this stimulus then increment the score
                    userResults.getUserData().addPotentialScore(currentStimulus.isCorrect(presenterListerner.getLabel()));
                }
                presenterListerner.eventFired(button, shotEventListner);
            }

            @Override
            public int getHotKey() {
                return presenterListerner.getHotKey();
            }
        }, styleName);
        buttonList.add(buttonItem);
    }

    public void stimulusRatingButton(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final TimedStimulusListener timedStimulusListener, final String ratingLabelLeft, final String ratingLabelRight, final String styleName, final int dataChannel) {
        ((ComplexView) simpleView).addRatingButtons(getRatingEventListners(appEventListner, stimulusProvider, currentStimulus, timedStimulusListener, currentStimulus.getUniqueId(), currentStimulus.getRatingLabels(), dataChannel), ratingLabelLeft, ratingLabelRight, false, styleName);
    }

    public void ratingButton(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final TimedStimulusListener timedStimulusListener, final String ratingLabels, final String ratingLabelLeft, final String ratingLabelRight, final String styleName, final int dataChannel) {
        ((ComplexView) simpleView).addRatingButtons(getRatingEventListners(appEventListner, stimulusProvider, currentStimulus, timedStimulusListener, currentStimulus.getUniqueId(), ratingLabels, dataChannel), ratingLabelLeft, ratingLabelRight, false, styleName);
    }

    public void ratingFooterButton(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final TimedStimulusListener timedStimulusListener, final String ratingLabels, final String ratingLabelLeft, final String ratingLabelRight, final String styleName, final int dataChannel) {
        ((ComplexView) simpleView).addRatingButtons(getRatingEventListners(appEventListner, stimulusProvider, currentStimulus, timedStimulusListener, currentStimulus.getUniqueId(), ratingLabels, dataChannel), ratingLabelLeft, ratingLabelRight, true, styleName);
    }

    public List<PresenterEventListner> getRatingEventListners(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final TimedStimulusListener timedStimulusListener, final String stimulusString, final String ratingLabels, final int dataChannel) {
        ArrayList<PresenterEventListner> eventListners = new ArrayList<>();
        final String[] splitRatingLabels = ratingLabels.split(",");
        for (final String ratingItem : splitRatingLabels) {
            int derivedHotKey = -1;
            if (ratingItem.equals("0")) {
                derivedHotKey = KeyCodes.KEY_ZERO;
            } else if (ratingItem.equals("1")) {
                derivedHotKey = KeyCodes.KEY_ONE;
            } else if (ratingItem.equals("2")) {
                derivedHotKey = KeyCodes.KEY_TWO;
            } else if (ratingItem.equals("3")) {
                derivedHotKey = KeyCodes.KEY_THREE;
            } else if (ratingItem.equals("4")) {
                derivedHotKey = KeyCodes.KEY_FOUR;
            } else if (ratingItem.equals("5")) {
                derivedHotKey = KeyCodes.KEY_FIVE;
            } else if (ratingItem.equals("6")) {
                derivedHotKey = KeyCodes.KEY_SIX;
            } else if (ratingItem.equals("7")) {
                derivedHotKey = KeyCodes.KEY_SEVEN;
            } else if (ratingItem.equals("8")) {
                derivedHotKey = KeyCodes.KEY_EIGHT;
            } else if (ratingItem.equals("9")) {
                derivedHotKey = KeyCodes.KEY_NINE;
            } else if (splitRatingLabels.length == 2) {
                // if there are only two options then use z and . as the hot keys
                if (splitRatingLabels[0].equals(ratingItem)) {
                    derivedHotKey = KeyCodes.KEY_Z;
                } else {
                    derivedHotKey = KeyCodes.KEY_NUM_PERIOD;
                }
            }

            final int hotKey = derivedHotKey;
            eventListners.add(new PresenterEventListner() {
                @Override
                public String getLabel() {
                    return ratingItem;
                }

                @Override
                public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                    endAudioRecorderTag(dataChannel, ratingItem, currentStimulus);
                    submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "RatingButton", stimulusString, ratingItem, duration.elapsedMillis());
                    if (currentStimulus.hasCorrectResponses()) {
                        // if there are correct responses to this stimulus then increment the score
                        userResults.getUserData().addPotentialScore(currentStimulus.isCorrect(ratingItem));
                    }
                    timedStimulusListener.postLoadTimerFired();
                }

                @Override
                public int getHotKey() {
                    return hotKey;
                }
            });
        }
        return eventListners;
    }

    protected void showCurrentMs() {
//        ((TimedStimulusView) simpleView).addText(duration.elapsedMillis() + "ms");
    }

    protected void logTimeStamp(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, String eventTag, final int dataChannel) {
        logTimeStamp(stimulusProvider, currentStimulus, "logTimeStamp", eventTag, dataChannel);
    }

    protected void logTimeStamp(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, String eventName, String eventTag, final int dataChannel) {
        submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, eventTag, currentStimulus.getUniqueId(), eventName, duration.elapsedMillis());
    }

    protected void endAudioRecorderTag(int tier, String tagString, final Stimulus currentStimulus) {
        super.endAudioRecorderTag(tier, currentStimulus.getUniqueId(), currentStimulus.getUniqueId(), tagString);
    }

    @Override
    protected void startAudioRecorderTag(int tier) {
        super.startAudioRecorderTag(tier); //((tier < 1) ? 1 : tier) + 2); //  tier 1 and 2 are reserved for stimulus set loading and stimulus display events
    }

    protected void startAudioRecorder(boolean wavFormat, boolean filePerStimulus, String directoryName, final Stimulus currentStimulus) {
//        final String subdirectoryName = userResults.getUserData().getUserId().toString();
        final String subdirectoryName = userResults.getUserData().getMetadataValue(new MetadataFieldProvider().workerIdMetadataField);
        super.startAudioRecorder(true, subdirectoryName, directoryName, (filePerStimulus) ? currentStimulus.getUniqueId() : "");
    }

    protected void showStimulusGrid(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final int postLoadCorrectMs, final TimedStimulusListener correctListener, final int postLoadIncorrectMs, final TimedStimulusListener incorrectListener, final int columnCount, final String imageWidth, final String eventTag, final int dataChannel) {
        final int maxStimuli = -1;
        final AnimateTypes animateType = AnimateTypes.none;
        showStimulusGrid(appEventListner, stimulusProvider, currentStimulus, postLoadCorrectMs, correctListener, postLoadIncorrectMs, incorrectListener, maxStimuli, columnCount, imageWidth, animateType, eventTag, dataChannel);
    }

    protected void showStimulusGrid(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final int postLoadCorrectMs, final TimedStimulusListener correctListener, final int postLoadIncorrectMs, final TimedStimulusListener incorrectListener, final int maxStimuli, final int columnCount, final String imageWidth, final AnimateTypes animateType, final String eventTag, final int dataChannel) {
        ((TimedStimulusView) simpleView).stopAudio();
        TimedStimulusListener correctTimedListener = new TimedStimulusListener() {

            @Override
            public void postLoadTimerFired() {
                Timer timer = new Timer() {
                    @Override
                    public void run() {
                        correctListener.postLoadTimerFired();
                    }
                };
                timer.schedule(postLoadCorrectMs);
            }
        };
        TimedStimulusListener incorrectTimedListener = new TimedStimulusListener() {

            @Override
            public void postLoadTimerFired() {
                Timer timer = new Timer() {
                    @Override
                    public void run() {
                        incorrectListener.postLoadTimerFired();
                    }
                };
                timer.schedule(postLoadIncorrectMs);
            }
        };
        final String gridStyle = "stimulusGrid";
        // todo: the appendStoredDataValue should occur in the correct or incorrect response within stimulusListener
        //localStorage.appendStoredDataValue(userResults.getUserData().getUserId(), SEEN_STIMULUS_LIST, currentStimulus.getAudioTag());
        ((TimedStimulusView) simpleView).startGrid(gridStyle);
        int imageCounter = 0;
//        if (alternativeChoice != null) {
//            buttonList.add(((TimedStimulusView) simpleView).addStringItem(getEventListener(buttonList, eventTag, currentStimulus, alternativeChoice, correctTimedListener, incorrectTimedListener), alternativeChoice, 0, 0, imageWidth));
//        }
        String groupResponseOptions = null;
        for (final Stimulus nextJpgStimulus : stimulusProvider.getDistractorList(maxStimuli)) {
            final String styleName;
            if (animateType == AnimateTypes.stimuliCode) {
                styleName = nextJpgStimulus.getCode() + "Animation";
            } else if (animateType != AnimateTypes.none) {
                styleName = animateType.name() + "Animation";
            } else {
                styleName = null;
            }
            // collect the distractor list for later reporting
            if (groupResponseOptions == null) {
                groupResponseOptions = "";
            } else {
                groupResponseOptions += ",";
            }
            groupResponseOptions += nextJpgStimulus.getUniqueId();
            final StimulusButton imageItem = ((TimedStimulusView) simpleView).addImageItem(getEventListener(currentStimulus, buttonList, eventTag, dataChannel, currentStimulus, nextJpgStimulus, correctTimedListener, incorrectTimedListener), UriUtils.fromString(nextJpgStimulus.getImage()), imageCounter / columnCount, 1 + imageCounter++ % columnCount, imageWidth, styleName, imageCounter);
            buttonList.add(imageItem);
        }
        if (groupParticipantService != null) {
            groupParticipantService.setResponseStimulusOptions(groupResponseOptions);
        }
        disableStimulusButtons();
        ((TimedStimulusView) simpleView).endGrid();
        //((TimedStimulusView) simpleView).addAudioPlayerGui();
    }

    protected void matchingStimulusGrid(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final int postLoadCorrectMs, final TimedStimulusListener correctListener, final int postLoadIncorrectMs, final TimedStimulusListener incorrectListener, final String matchingRegex, final boolean randomise, final int columnCount, int maxWidth, final int dataChannel) {
        final int maxStimulusCount = -1;
        final AnimateTypes animateType = AnimateTypes.none;
        matchingStimulusGrid(appEventListner, stimulusProvider, currentStimulus, postLoadCorrectMs, correctListener, postLoadIncorrectMs, incorrectListener, matchingRegex, maxStimulusCount, randomise, columnCount, maxWidth, animateType, dataChannel);
    }

    protected void matchingStimulusGrid(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final int postLoadCorrectMs, final TimedStimulusListener correctListener, final int postLoadIncorrectMs, final TimedStimulusListener incorrectListener, final String matchingRegex, final int maxStimulusCount, final boolean randomise, final int columnCount, int maxWidth, final AnimateTypes animateType, final int dataChannel) {
        matchingStimuliGroup = new MatchingStimuliGroup(currentStimulus, stimulusProvider.getMatchingStimuli(matchingRegex), true, hasMoreStimulusListener, endOfStimulusListener);
        ((TimedStimulusView) simpleView).startHorizontalPanel();
        int ySpacing = (int) (100.0 / (matchingStimuliGroup.getStimulusCount() + 1));
        int yPos = 0;
        while (matchingStimuliGroup.getNextStimulus(stimulusProvider)) {
            yPos += ySpacing;
            if (matchingStimuliGroup.isCorrect(currentStimulus)) {
                stimulusPresent(stimulusProvider, currentStimulus, 0, maxWidth, maxWidth, animateType, false, yPos - (maxWidth / 2), postLoadCorrectMs, null, null, dataChannel,
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {

                    }
                },
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {

                    }
                },
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {

                    }
                },
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {
                        correctListener.postLoadTimerFired();
                    }
                });
            } else {
                stimulusPresent(stimulusProvider, currentStimulus, 0, maxWidth, maxWidth, animateType, false, yPos - (maxWidth / 2), postLoadIncorrectMs, null, null, dataChannel,
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {

                    }
                },
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {

                    }
                },
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {

                    }
                },
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {
                        incorrectListener.postLoadTimerFired();
                    }
                });
            }
        }
        ((TimedStimulusView) simpleView).endHorizontalPanel();
    }

    private PresenterEventListner getEventListener(final Stimulus currentStimulus, final ArrayList<StimulusButton> buttonList, final String eventTag, final int dataChannel, final Stimulus correctStimulusItem, final Stimulus currentStimulusItem, final TimedStimulusListener correctTimedListener, final TimedStimulusListener incorrectTimedListener) {
        final String tagValue1 = correctStimulusItem.getImage();
        final String tagValue2 = currentStimulusItem.getImage();
        return new PresenterEventListner() {

            @Override
            public String getLabel() {
                return "";
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                for (StimulusButton currentButton : buttonList) {
                    currentButton.setEnabled(false);
                }
                if (groupParticipantService != null) {
                    groupParticipantService.setResponseStimulusId(currentStimulusItem.getUniqueId());
//                    groupParticipantService.messageGroup(0, currentStimulus.getUniqueId(), Integer.toString(stimulusProvider.getCurrentStimulusIndex()), null, null, currentStimulusItem.getUniqueId());
                }
                button.addStyleName("stimulusButtonHighlight");
                // eventTag is set by the user and is different for each state (correct/incorrect).
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, eventTag, tagValue1, tagValue2, duration.elapsedMillis());
                if (currentStimulus.getImage().equals(tagValue2)) {
                    correctTimedListener.postLoadTimerFired();
                } else {
                    incorrectTimedListener.postLoadTimerFired();
                }
            }
        };
    }

    public void triggerListener(final String listenerId, final int threshold, final int maximum, final TimedStimulusListener triggerListener) {
        triggerListeners.put(listenerId, new TriggerListener(listenerId, threshold, maximum, triggerListener));
    }

    public void trigger(final String listenerId) {
        triggerListeners.get(listenerId).trigger();
    }

    protected void startTimer(final int msToNext, final String listenerId, final TimedStimulusListener timeoutListener) {
        final String storedDataValue = localStorage.getStoredDataValue(userResults.getUserData().getUserId(), "timer_" + listenerId);
        final long initialTimerStartMs;
        if (storedDataValue == null || storedDataValue.isEmpty()) {
            initialTimerStartMs = new Date().getTime();
            localStorage.setStoredDataValue(userResults.getUserData().getUserId(), "timer_" + listenerId, Long.toString(initialTimerStartMs));
        } else {
            initialTimerStartMs = Long.parseLong(storedDataValue);
        }
        timerService.startTimer(initialTimerStartMs, msToNext, listenerId, timeoutListener);
    }

    protected void compareTimer(final int msToNext, final String listenerId, final TimedStimulusListener aboveThreshold, final TimedStimulusListener withinThreshold) {
        if (timerService.getTimerValue(listenerId) > msToNext) {
            aboveThreshold.postLoadTimerFired();
        } else {
            withinThreshold.postLoadTimerFired();
        }
    }

    protected void clearTimer(final String listenerId) {
        localStorage.deleteStoredDataValue(userResults.getUserData().getUserId(), "timer_" + listenerId);
        timerService.clearTimer(listenerId);
    }

    protected void logTimerValue(final String listenerId, final String eventTag, final int dataChannel) {
        submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, eventTag, listenerId, Integer.toString(timerService.getTimerValue(listenerId)), duration.elapsedMillis());
    }

    public void cancelPauseTimers() {
//        ((TimedStimulusView) simpleView).stopTimers();
        for (Timer currentTimer : pauseTimers) {
            if (currentTimer != null) {
                currentTimer.cancel();
            }
        }
        pauseTimers.clear();
    }

    public void disableStimulusButtons() {
        for (StimulusButton currentButton : buttonList) {
            currentButton.setEnabled(false);
        }
//        ((TimedStimulusView) simpleView).addText("disableStimulusButtons: " + duration.elapsedMillis() + "ms");
    }

    public void hideStimulusButtons() {
        for (StimulusButton currentButton : buttonList) {
            currentButton.setVisible(false);
        }
//        ((TimedStimulusView) simpleView).addText("hideStimulusButtons: " + duration.elapsedMillis() + "ms");
    }

    public void showStimulusButtons() {
        for (StimulusButton currentButton : buttonList) {
            currentButton.setVisible(true);
        }
//        ((TimedStimulusView) simpleView).addText("showStimulusButtons: " + duration.elapsedMillis() + "ms");
    }

    public void enableStimulusButtons() {
        for (StimulusButton currentButton : buttonList) {
            currentButton.setEnabled(true);
            currentButton.removeStyleName("optionButtonActivated");
        }
//        ((TimedStimulusView) simpleView).addText("enableStimulusButtons: " + duration.elapsedMillis() + "ms");
    }

    public void showStimulusProgress(final StimuliProvider stimulusProvider) {
        showStimulusProgress(stimulusProvider, null);
    }

    public void showStimulusProgress(final StimuliProvider stimulusProvider, String styleName) {
        ((TimedStimulusView) simpleView).addHtmlText((stimulusProvider.getCurrentStimulusIndex() + 1) + " / " + stimulusProvider.getTotalStimuli(), styleName);
//        ((TimedStimulusView) simpleView).addText("showStimulusProgress: " + duration.elapsedMillis() + "ms");
    }

//    public void popupMessage(final PresenterEventListner presenterListerner, String message) {
//        ((TimedStimulusView) simpleView).showHtmlPopup(presenterListerner, message);
//    }

    /*protected boolean stimulusIndexIn(int[] validIndexes) {
        int currentIndex = stimulusProvider.getTotalStimuli() - stimulusProvider.getRemainingStimuli();
        for (int currentValid : validIndexes) {
            if (currentIndex == currentValid) {
                return true;
            }
        }
        return false;
    }*/
    protected void clearStimulus() {
        // when is this used?
        clearPage();
        buttonList.clear();
    }

    public void validateStimuliResponses(final boolean unusedValue, final TimedStimulusListener conditionTrue, final TimedStimulusListener conditionFalse) {
        if (validateStimuliResponses()) {
            conditionTrue.postLoadTimerFired();
        } else {
            conditionFalse.postLoadTimerFired();
        }
    }

    private boolean validateStimuliResponses(/* this must use the stimuli for each StimulusFreeText and not from the stimulusProvider */) {
        HashMap<Stimulus, JSONObject> jsonStimulusMap = new HashMap<>();
        for (StimulusFreeText stimulusFreeText : stimulusFreeTextList) {
            if (!jsonStimulusMap.containsKey(stimulusFreeText.getStimulus())) {
                JSONObject storedStimulusJSONObject = localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), stimulusFreeText.getStimulus());
                storedStimulusJSONObject = (storedStimulusJSONObject == null) ? new JSONObject() : storedStimulusJSONObject;
                jsonStimulusMap.put(stimulusFreeText.getStimulus(), storedStimulusJSONObject);
            }

            jsonStimulusMap.get(stimulusFreeText.getStimulus()).put(stimulusFreeText.getPostName(), new JSONString(stimulusFreeText.getValue()));
        }
        for (Stimulus stimulus : jsonStimulusMap.keySet()) {
            localStorage.setStoredJSONObject(userResults.getUserData().getUserId(), stimulus, jsonStimulusMap.get(stimulus));
        }
        for (StimulusFreeText stimulusFreeText : stimulusFreeTextList) {
            if (!stimulusFreeText.isValid()) {
                stimulusFreeText.getFocusWidget().setFocus(true);
                return false;
            }
        }
        // @todo: probably good to check if the data has changed before writing to disk
        for (Stimulus stimulus : jsonStimulusMap.keySet()) {
            submissionService.writeJsonData(userResults.getUserData().getUserId().toString(), stimulus.getUniqueId(), jsonStimulusMap.get(stimulus).toString());
        }
        for (StimulusFreeText stimulusFreeText : stimulusFreeTextList) {
            // @todo: checking the free text boxes is also done in the group stimulus sync code, therefore this should be shared in a single function
            submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), stimulusFreeText.getDataChannel(), stimulusFreeText.getPostName(), stimulusFreeText.getStimulus().getUniqueId(), stimulusFreeText.getValue(), duration.elapsedMillis());
            final String responseTimes = stimulusFreeText.getResponseTimes();
            if (responseTimes != null) {
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), stimulusFreeText.getDataChannel(), stimulusFreeText.getPostName() + "_ms", stimulusFreeText.getStimulus().getUniqueId(), responseTimes, duration.elapsedMillis());
            }
            if (stimulusFreeText.getStimulus().hasCorrectResponses()) {
                // if there are correct responses to this stimulus then increment the score
                userResults.getUserData().addPotentialScore(stimulusFreeText.getStimulus().isCorrect(stimulusFreeText.getValue()));
            }
        }
        return true;
    }

    protected void prevStimulus(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final boolean repeatIncorrect) {
        nextStimulus(stimulusProvider, currentStimulus, repeatIncorrect, -1);
    }

    protected void nextStimulus(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final boolean repeatIncorrect) {
        nextStimulus(stimulusProvider, currentStimulus, repeatIncorrect, 1);
    }

    private void nextStimulus(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final boolean repeatIncorrect, final int increment) {
        if (groupParticipantService != null) {
            ((ComplexView) simpleView).addText("showStimulus should not be used with the groupParticipantService");
            throw new UnsupportedOperationException("showStimulus should not be used with the groupParticipantService");
        }
        if (!validateStimuliResponses()) {
            return;
        }
        if (repeatIncorrect && userResults.getUserData().isCurrentIncorrect()) {
            stimulusProvider.pushCurrentStimulusToEnd();
        }
        userResults.getUserData().clearCurrentResponse();
        clearPage();
        showStimulus(stimulusProvider, null, increment);
    }

    protected void clearPage() {
        clearPage(null);
    }

    protected void clearPage(String styleName) {
        ((TimedStimulusView) simpleView).stopTimers();
        ((TimedStimulusView) simpleView).stopAudio();
        ((TimedStimulusView) simpleView).stopVideo();
        ((TimedStimulusView) simpleView).clearPageAndTimers(styleName);
        nextButtonEventListnerList.clear(); // clear this now to prevent refires of the event
        stimulusFreeTextList.clear();
        buttonList.clear();
        backEventListners.clear();
    }

    protected void playVideo() {
        ((TimedStimulusView) simpleView).startVideo();
    }

    protected void rewindVideo() {
        ((TimedStimulusView) simpleView).rewindVideo();
    }

    protected void pauseVideo() {
        ((TimedStimulusView) simpleView).stopVideo();
    }

    protected void groupResponseStimulusImage(final StimuliProvider stimulusProvider, int percentOfPage, int maxHeight, int maxWidth, int postLoadMs, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        final AnimateTypes animateType = AnimateTypes.none;
        groupResponseStimulusImage(stimulusProvider, percentOfPage, maxHeight, maxWidth, animateType, postLoadMs, dataChannel, loadedStimulusListener, failedStimulusListener, playedStimulusListener);
    }

    protected void groupResponseStimulusImage(final StimuliProvider stimulusProvider, int percentOfPage, int maxHeight, int maxWidth, final AnimateTypes animateType, int postLoadMs, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        stimulusPresent(stimulusProvider, stimulusProvider.getStimuliFromString(groupParticipantService.getResponseStimulusId()), percentOfPage, maxHeight, maxWidth, animateType, false, null, postLoadMs, null, null, dataChannel, loadedStimulusListener, failedStimulusListener, playedStimulusListener, null);
    }

    protected void sendGroupEndOfStimuli(final StimuliProvider stimulusProvider, final String eventTag) {
        groupParticipantService.messageGroup(groupParticipantService.getRequestedPhase(), 1, null, Integer.toString(stimulusProvider.getCurrentStimulusIndex() + 1), groupParticipantService.getMessageString(), groupParticipantService.getResponseStimulusOptions(), groupParticipantService.getResponseStimulusId(), (int) userResults.getUserData().getCurrentScore(), "");
//        showStimulusProgress();
    }

    protected void sendGroupStoredMessage(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String eventTag, final int originPhase, final int incrementPhase, String expectedRespondents) {
        final JSONObject storedStimulusJSONObject = localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus);
        final JSONValue freeTextValue = (storedStimulusJSONObject == null) ? null : storedStimulusJSONObject.get("groupMessage");
        String messageString = ((freeTextValue != null) ? freeTextValue.isString().stringValue() : null);
        groupParticipantService.messageGroup(originPhase, incrementPhase, currentStimulus.getUniqueId(), Integer.toString(stimulusProvider.getCurrentStimulusIndex()), messageString, groupParticipantService.getResponseStimulusOptions(), groupParticipantService.getResponseStimulusId(), (int) userResults.getUserData().getCurrentScore(), expectedRespondents);
    }

    protected void sendGroupMessage(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String eventTag, final int originPhase, final int incrementPhase, String expectedRespondents) {
        submissionService.submitTagValue(userResults.getUserData().getUserId(), getSelfTag(), eventTag, (groupParticipantService != null) ? groupParticipantService.getMessageString() : null, duration.elapsedMillis());
        final String uniqueId = (stimulusProvider.getCurrentStimulusIndex() < stimulusProvider.getTotalStimuli()) ? currentStimulus.getUniqueId() : null;
        if (groupParticipantService != null) {
            groupParticipantService.messageGroup(originPhase, incrementPhase, uniqueId, Integer.toString(stimulusProvider.getCurrentStimulusIndex()), groupParticipantService.getMessageString(), groupParticipantService.getResponseStimulusOptions(), groupParticipantService.getResponseStimulusId(), (int) userResults.getUserData().getCurrentScore(), expectedRespondents);
        }
        clearPage();
        ((TimedStimulusView) simpleView).addText("Waiting for a group response."); // + eventTag + ":" + originPhase + ":" + incrementPhase + ":" + groupParticipantService.getRequestedPhase());
    }

    // @todo: tag pair data and tag data tables could show the number of stimuli show events and the unique stimuli (grouped by tag strings) show events per screen
    protected void sendGroupMessageButton(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String eventTag, final int dataChannel, final String buttonLabel, final String styleName, final boolean norepeat, final int hotKey, final int originPhase, final int incrementPhase, final String expectedRespondents) {
        PresenterEventListner eventListner = new PresenterEventListner() {

            @Override
            public String getLabel() {
                return buttonLabel;
            }

            @Override
            public int getHotKey() {
                return hotKey;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                for (StimulusFreeText stimulusFreeText : stimulusFreeTextList) {
                    if (!stimulusFreeText.isValid()) {
                        return;
                    }
                }
                String messageString = "";
                for (StimulusFreeText stimulusFreeText : stimulusFreeTextList) {
                    messageString += stimulusFreeText.getValue();
                }
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, eventTag, (stimulusProvider.getCurrentStimulusIndex() < stimulusProvider.getTotalStimuli()) ? currentStimulus.getUniqueId() : null, messageString, duration.elapsedMillis());
                groupParticipantService.messageGroup(originPhase, incrementPhase, currentStimulus.getUniqueId(), Integer.toString(stimulusProvider.getCurrentStimulusIndex()), messageString, groupParticipantService.getResponseStimulusOptions(), groupParticipantService.getResponseStimulusId(), (int) userResults.getUserData().getCurrentScore(), expectedRespondents);
                clearPage();
            }
        };
        nextButtonEventListnerList.add(eventListner);
        ((TimedStimulusView) simpleView).addOptionButton(eventListner, styleName);
    }

    protected void touchInputReportSubmit(final Stimulus currentStimulus, final int dataChannel) {
        if (touchInputCapture != null) {
            final String touchReport = touchInputCapture.getTouchReport(Window.getClientWidth(), Window.getClientHeight());
            submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "touchInputReport", currentStimulus.getUniqueId(), touchReport, duration.elapsedMillis());
//            // todo: perhaps this is a bit heavy on local storage but at least only one touch event would be stored
//            JSONObject storedStimulusJSONObject = localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus);
//            storedStimulusJSONObject = (storedStimulusJSONObject == null) ? new JSONObject() : storedStimulusJSONObject;
//            storedStimulusJSONObject.put("touchInputReport", new JSONString(touchReport));
//            localStorage.setStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus, storedStimulusJSONObject);
        }
        touchInputCapture = null;
    }

    protected void touchInputCaptureStart(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final int dataChannel, final boolean showDebug, final int msAfterEndOfTouchToNext, final TimedStimulusListener endOfTouchEventListner) {
        if (touchInputCapture == null) {
            final HTML debugHtmlLabel;
            if (showDebug) {
                debugHtmlLabel = ((TimedStimulusView) simpleView).addHtmlText("&nbsp;", "footerLabel");
            } else {
                debugHtmlLabel = null;
            }
            touchInputCapture = new TouchInputCapture(endOfTouchEventListner, msAfterEndOfTouchToNext) {
                @Override
                public void setDebugLabel(String debugLabel) {
                    if (debugHtmlLabel != null) {
                        ((TimedStimulusView) simpleView).addWidget(debugHtmlLabel);
                        debugHtmlLabel.setHTML(debugLabel);
                    }
                }

                @Override
                public void endOfTouchEvent(String groupName) {
                    logTimeStamp(stimulusProvider, currentStimulus, "endOfTouchEvent", groupName, dataChannel);
                }

            };
            ((TimedStimulusView) simpleView).addTouchInputCapture(touchInputCapture);
        }
    }

    protected void prevStimulusButton(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String eventTag, final String buttonLabel, final String styleName, final boolean repeatIncorrect) {
        final int hotKey = -1;
        prevStimulusButton(stimulusProvider, currentStimulus, eventTag, buttonLabel, styleName, repeatIncorrect, hotKey);
    }

    protected void prevStimulusButton(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String eventTag, final String buttonLabel, final String styleName, final boolean repeatIncorrect, final int hotKey) {
        PresenterEventListner eventListner = new PresenterEventListner() {

            @Override
            public String getLabel() {
                return buttonLabel;
            }

            @Override
            public int getHotKey() {
                return hotKey;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                nextStimulus(stimulusProvider, currentStimulus, repeatIncorrect, -1);
            }
        };
        nextButtonEventListnerList.add(eventListner);
        final StimulusButton prevButton = ((TimedStimulusView) simpleView).addOptionButton(eventListner, styleName);
        prevButton.setEnabled(stimulusProvider.hasNextStimulus(-1));
    }

    protected void nextStimulusButton(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String eventTag, final String buttonLabel, final String styleName, final boolean repeatIncorrect) {
        final int hotKey = -1;
        nextStimulusButton(stimulusProvider, currentStimulus, eventTag, buttonLabel, styleName, repeatIncorrect, hotKey);
    }

    protected void nextStimulusButton(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String eventTag, final String buttonLabel, final String styleName, final boolean repeatIncorrect, final int hotKey) {
//        if (stimulusProvider.hasNextStimulus()) {
        PresenterEventListner eventListner = new PresenterEventListner() {

            @Override
            public String getLabel() {
                return buttonLabel;
            }

            @Override
            public int getHotKey() {
                return hotKey;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                nextStimulus(stimulusProvider, currentStimulus, repeatIncorrect, 1);
            }
        };
        nextButtonEventListnerList.add(eventListner);
        final StimulusButton nextButton = ((TimedStimulusView) simpleView).addOptionButton(eventListner, styleName);
//        final boolean disableAtEnd = false;
//        nextButton.setEnabled(stimulusProvider.hasNextStimulus(1));
    }

    protected void endOfStimulusButton(final StimuliProvider stimulusProvider, final PresenterEventListner appEventListner, final String eventTag) {
        //logTimeStamp(eventTag);
        if (!stimulusProvider.hasNextStimulus(1)) {
            ((TimedStimulusView) simpleView).addOptionButton(appEventListner);
        }
    }

    protected void audioButton(final String eventTag, final int dataChannel, final String srcString, final String imagePath, final boolean autoPlay, final int hotKey, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        audioButton(eventTag, dataChannel, srcString, null, imagePath, autoPlay, hotKey, loadedStimulusListener, failedStimulusListener, playedStimulusListener);
    }

    protected void audioButton(final String eventTag, final int dataChannel, final String srcString, final String styleName, final String imagePath, final boolean autoPlay, final int hotKey, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        final String mp3Path = srcString + ".mp3";
        final String oggPath = srcString + ".ogg";
        final PresenterEventListner presenterEventListner = new PresenterEventListner() {
            private boolean hasPlayed = false;

            @Override
            public String getLabel() {
                return imagePath;
            }

            @Override
            public int getHotKey() {
                return hotKey;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {
                        submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, eventTag, "PlayAudio", mp3Path, duration.elapsedMillis());
                        loadedStimulusListener.postLoadTimerFired();
                    }
                };
                ((TimedStimulusView) simpleView).addTimedAudio(UriUtils.fromString(serviceLocations.staticFilesUrl() + oggPath), UriUtils.fromString(serviceLocations.staticFilesUrl() + mp3Path), 0, false, shownStimulusListener, failedStimulusListener, new CancelableStimulusListener() {

                    @Override
                    protected void trigggerCancelableEvent() {
                        if (!hasPlayed) {
                            playedStimulusListener.postLoadTimerFired();
                        }
                        hasPlayed = true;
                    }
                });
            }
        };
        ((TimedStimulusView) simpleView).addImageButton(presenterEventListner, UriUtils.fromString(serviceLocations.staticFilesUrl() + imagePath), styleName, false);
        if (autoPlay) {
            presenterEventListner.eventFired(null, null);
        }
    }

    @Override
    public void savePresenterState() {        
        ((TimedStimulusView) simpleView).stopListeners();
        ((TimedStimulusView) simpleView).stopTimers();
        ((TimedStimulusView) simpleView).stopAudio();
        ((TimedStimulusView) simpleView).stopVideo();
        ((TimedStimulusView) simpleView).clearDomHandlers();
        super.savePresenterState();
        stopAudioRecorder();
        timerService.clearAllTimers(); // clear all callbacks in timerService before exiting the presenter
    }
}
