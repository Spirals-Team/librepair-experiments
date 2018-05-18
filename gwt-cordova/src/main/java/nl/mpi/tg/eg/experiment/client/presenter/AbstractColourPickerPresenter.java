/*
 * Copyright (C) 2014 Language In Interaction
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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.mpi.tg.eg.experiment.client.ApplicationController;
import nl.mpi.tg.eg.experiment.client.Messages;
import nl.mpi.tg.eg.experiment.client.listener.AppEventListner;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.mpi.tg.eg.experiment.client.listener.SingleShotEventListner;
import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;
import nl.mpi.tg.eg.experiment.client.model.UserResults;
import nl.mpi.tg.eg.experiment.client.service.DataSubmissionService;
import nl.mpi.tg.eg.experiment.client.service.LocalStorage;
import nl.mpi.tg.eg.experiment.client.service.StimulusProvider;
import nl.mpi.tg.eg.experiment.client.view.ColourPickerCanvasView;
import nl.mpi.tg.eg.experiment.client.exception.CanvasError;
import nl.mpi.tg.eg.experiment.client.service.AudioPlayer;
import nl.mpi.tg.eg.experiment.client.model.colour.StimulusResponse;
import nl.mpi.tg.eg.experiment.client.model.colour.StimulusResponseGroup;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;
import nl.mpi.tg.eg.frinex.common.model.StimulusSelector;

/**
 * @since Oct 10, 2014 9:52:25 AM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public abstract class AbstractColourPickerPresenter implements Presenter {

    protected final Messages messages = GWT.create(Messages.class);
    private final RootLayoutPanel widgetTag;
    private final DataSubmissionService submissionService;
//    private final ArrayList<Stimulus> stimuli;
//    private final int maxStimuli;
    private final UserResults userResults;
    private final ColourPickerCanvasView colourPickerCanvasView;
    protected final StimulusProvider stimulusProvider;
//    private Stimulus currentStimulus = null;
//    final List<GeneratedStimulus.Tag> selectionTags;
    private final Duration duration;
    private long startMs;
//    private final int repeatCount;
    private int shownSetCount;
    private int shownCount = 0;
    private TimedStimulusListener hasMoreStimulusListener;
    private TimedStimulusListener endOfStimulusListener;
    private final LocalStorage localStorage;
    private AppEventListner appEventListner;
    private ApplicationController.ApplicationState nextState;
    private StimulusResponseGroup stimulusResponseGroup = null;

    public AbstractColourPickerPresenter(RootLayoutPanel widgetTag, AudioPlayer audioPlayer, DataSubmissionService submissionService, UserResults userResults, final LocalStorage localStorage, final StimulusProvider provider) throws CanvasError {
        this.widgetTag = widgetTag;
        stimulusProvider = provider;
//        this.stimuliGroup = userResults.getPendingStimuliGroup();
//        userResults.setPendingStimuliGroup(null);
//        this.stimuli = new ArrayList<>(stimuliGroup.getStimuli());
        this.localStorage = localStorage;
        this.submissionService = submissionService;
        this.userResults = userResults;
//        this.repeatCount = repeatCount;
//        maxStimuli = this.stimuli.size();
//        this.selectionTags = selectionTags;
        colourPickerCanvasView = new ColourPickerCanvasView();
        colourPickerCanvasView.setRandomColour();
        duration = new Duration();
    }

//    private void submitFrinexResults() {
//        submissionService.submitTagValue(userResults.getUserData().getUserId(), "ScoreLog", new ResultsSerialiser() {
//            final DateTimeFormat format = DateTimeFormat.getFormat(messages.reportDateFormat());
//
//            @Override
//            protected String formatDate(Date date) {
//                return format.format(date);
//            }
//
//            @Override
//            protected String getSeparator() {
//                return ",";
//            }
//
//            @Override
//            protected String getRowSeparator() {
//                return "\\n";
//            }
//
//        }.serialise(stimulusResponseGroup, userResults.getUserData().getUserId().toString()), 0);
//    }
    protected void requestFilePermissions() {
        // not used but required by default
    }

    private void triggerEvent() {
        if (!stimulusProvider.hasNextStimulus(1)) {
            shownSetCount++;
//            if (repeatCount > shownSetCount) {
//                stimulusProvider.getSubset(selectionTags, false, "");
//            }
//            submitFrinexResults();
            appEventListner.requestApplicationState(nextState);
        }
        if (!stimulusProvider.hasNextStimulus(1)) {
            endOfStimulusListener.postLoadTimerFired();
        } else {
            colourPickerCanvasView.setRandomColour();
            stimulusProvider.nextStimulus(1);
            startMs = System.currentTimeMillis();
            shownCount++;
            int repeatCount = 1;
            colourPickerCanvasView.setStimulus(stimulusProvider.getCurrentStimulus(),
                    //                    messages.stimulusscreenprogresstext(Integer.toString(shownCount), Integer.toString(stimulusProvider.getTotalStimuli() * repeatCount))
                    Integer.toString((int) (((double) (shownCount - 1) / (stimulusProvider.getTotalStimuli() * repeatCount)) * 100)) + "%"
            );
        }
    }

    protected abstract String getTitle();

    protected abstract String getSelfTag();

    abstract void setContent(final AppEventListner appEventListner);

    @Override
    public void setState(final AppEventListner appEventListner, final ApplicationController.ApplicationState prevState, final ApplicationController.ApplicationState nextState) {
        this.appEventListner = appEventListner;
        this.nextState = nextState;
        widgetTag.clear();
        colourPickerCanvasView.setAcceptButton(new PresenterEventListner() {

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                final long durationMs = System.currentTimeMillis() - startMs;
                stimulusResponseGroup.addResponse(stimulusProvider.getCurrentStimulus(), new StimulusResponse(colourPickerCanvasView.getColour(), new Date(), durationMs));
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), stimulusResponseGroup.getPostName(), stimulusProvider.getCurrentStimulus().getUniqueId(), colourPickerCanvasView.getColour().getHexValue(), (int) (durationMs));
                triggerEvent();
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public String getLabel() {
                return messages.stimulusscreenselectbutton();
            }
        });
        colourPickerCanvasView.setRejectButton(new PresenterEventListner() {

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                final long durationMs = System.currentTimeMillis() - startMs;
                stimulusResponseGroup.addResponse(stimulusProvider.getCurrentStimulus(), new StimulusResponse(null, new Date(), durationMs));
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), stimulusResponseGroup.getPostName(), stimulusProvider.getCurrentStimulus().getUniqueId(), "", (int) (durationMs));
                triggerEvent();
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public String getLabel() {
                return messages.stimulusscreenrejectbutton();
            }
        });
        colourPickerCanvasView.setQuitButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return messages.stimulusscreenQuitButton();
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                // delete the incomplete test results
//                userResults.deleteStimuliGroupResults(stimuliGroup);
                appEventListner.requestApplicationState(prevState);
            }
        });
        colourPickerCanvasView.resizeView();
        widgetTag.add(colourPickerCanvasView);

        setContent(appEventListner);
    }

    public void helpDialogue(String helpText, String closeButtonLabel) {
        colourPickerCanvasView.setInstructions(helpText, messages.helpButtonChar(), closeButtonLabel);
    }

    protected void loadStimulus(String eventTag, final StimulusSelector[] stimulusSelectors, final TimedStimulusListener hasMoreStimulusListener, final TimedStimulusListener endOfStimulusListener) {
        submissionService.submitTimeStamp(userResults.getUserData().getUserId(), eventTag, duration.elapsedMillis());
        final List<Stimulus.Tag> selectionTags = new ArrayList<>();
        for (StimulusSelector selector : stimulusSelectors) {
            selectionTags.add(selector.getTag());
        }
        stimulusProvider.getSubset(selectionTags, "", -1);
        this.hasMoreStimulusListener = hasMoreStimulusListener;
        this.endOfStimulusListener = endOfStimulusListener;
        stimulusResponseGroup = new StimulusResponseGroup(eventTag, eventTag.replaceAll("[^A-Za-z0-9]", "_"));
        userResults.addStimulusResponseGroup(stimulusResponseGroup);
//        showStimulus();
        triggerEvent();
    }

    @Override
    public void fireBackEvent() {
    }

    @Override
    public void fireResizeEvent() {
    }

    @Override
    public void fireWindowClosing() {
    }

    @Override
    public void savePresenterState() {
    }
}
