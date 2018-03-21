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

import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import java.util.List;
import nl.mpi.tg.eg.experiment.client.listener.AppEventListner;
import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;
import nl.mpi.tg.eg.experiment.client.model.UserResults;
import nl.mpi.tg.eg.experiment.client.service.AudioPlayer;
import nl.mpi.tg.eg.experiment.client.service.DataSubmissionService;
import nl.mpi.tg.eg.experiment.client.service.StimulusProvider;
import nl.mpi.tg.eg.experiment.client.view.TimedStimulusView;

/**
 * @since Jun 29, 2015 11:51:54 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public abstract class AbstractPreloadStimulusPresenter extends AbstractStimulusPresenter implements Presenter {

    public AbstractPreloadStimulusPresenter(RootLayoutPanel widgetTag, AudioPlayer audioPlayer, DataSubmissionService submissionService, UserResults userResults) {
        super(widgetTag, audioPlayer, submissionService, userResults, null, null);
    }

    private void preloadAllStimuli(final AppEventListner appEventListner, final HorizontalPanel progressBar, final TimedStimulusListener timedStimulusListener, final List<Stimulus> pictureList, final int totalImages) {
        ((TimedStimulusView) simpleView).updateProgressBar(progressBar, 0, totalImages - pictureList.size(), totalImages);
        if (!pictureList.isEmpty()) {
            ((TimedStimulusView) simpleView).preloadImage(UriUtils.fromString(pictureList.remove(0).getImage()), new TimedStimulusListener() {

                @Override
                public void postLoadTimerFired() {
                    preloadAllStimuli(appEventListner, progressBar, timedStimulusListener, pictureList, totalImages);
                }
            });
        } else {
            timedStimulusListener.postLoadTimerFired();
        }
    }

    protected void preloadAllStimuli(final AppEventListner appEventListner, final TimedStimulusListener timedStimulusListener, final List<Stimulus.Tag> selectionTags) {
        final StimulusProvider stimulusProvider = new StimulusProvider();
        stimulusProvider.getSubset(selectionTags, false, 1, 0, 0, "", -1);
        // todo: this should be modified to get all relevant stimuli and load as required by type
        final List<Stimulus> pictureList = stimulusProvider.getDistractorList(stimulusProvider.getTotalStimuli());
        final HorizontalPanel progressBar = ((TimedStimulusView) simpleView).addProgressBar(0, 0, pictureList.size());
        preloadAllStimuli(appEventListner, progressBar, timedStimulusListener, pictureList, pictureList.size());
    }
}
