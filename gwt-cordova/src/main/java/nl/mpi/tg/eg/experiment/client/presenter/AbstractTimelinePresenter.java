/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics, Nijmegen
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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import java.util.Set;
import nl.mpi.tg.eg.experiment.client.model.AnnotationData;
import nl.mpi.tg.eg.experiment.client.model.AnnotationSet;
import nl.mpi.tg.eg.experiment.client.model.UserResults;
import nl.mpi.tg.eg.experiment.client.service.AudioPlayer;
import nl.mpi.tg.eg.experiment.client.service.DataFactory;
import nl.mpi.tg.eg.experiment.client.service.DataSubmissionService;
import nl.mpi.tg.eg.experiment.client.service.LocalStorage;
import nl.mpi.tg.eg.experiment.client.service.StimulusProvider;
import nl.mpi.tg.eg.experiment.client.util.GeneratedStimulusProvider;
import nl.mpi.tg.eg.experiment.client.view.AnnotationTimelinePanel;
import nl.mpi.tg.eg.experiment.client.view.AnnotationTimelineView;
import nl.mpi.tg.eg.experiment.client.view.VideoPanel;

/**
 * @since Oct 2, 2015 4:22:12 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public abstract class AbstractTimelinePresenter extends AbstractPresenter implements Presenter {

    DataFactory dataFactory = GWT.create(DataFactory.class);
    private final StimulusProvider stimulusProvider;
    private final LocalStorage localStorage;
    private final UserResults userResults;
    private String storageTag = "temp_tag";

    public AbstractTimelinePresenter(RootLayoutPanel widgetTag, AudioPlayer audioPlayer, DataSubmissionService submissionService, UserResults userResults, LocalStorage localStorage) {
        super(widgetTag, new AnnotationTimelineView(audioPlayer));
        this.localStorage = localStorage;
        this.userResults = userResults;
        this.stimulusProvider = new nl.mpi.tg.eg.experiment.client.service.StimulusProvider(GeneratedStimulusProvider.values);
    }

    protected void setVideoPanel(String src, int percentOfPage, int maxHeight, int maxWidth, String poster) {
        // todo: utilise the maxHeight and maxWidth correctly without affecting the AnnotationTimelinePanel
        final VideoPanel videoPanel = new VideoPanel(maxWidth + "%", poster, src);
        ((AnnotationTimelineView) simpleView).setVideoPanel(videoPanel);
    }

    public void setAnnotationTimelinePanel(String eventTag, String poster, String src, /*StimulusSelector[] stimulusSelectorArray, int maxStimuli,*/ int columnCount) {
        /*List<Stimulus.Tag> tags = new ArrayList<>();
        for (StimulusSelector stimulusSelector : stimulusSelectorArray) {
            tags.add(stimulusSelector.getTag());
        }
         */
        // todo: this has to be updated so that an enclosing loadStimulus does the stimuli selection    
        this.storageTag = eventTag;
        final VideoPanel videoPanel = new VideoPanel("50%", poster, src);
        ((AnnotationTimelineView) simpleView).setVideoPanel(videoPanel);
        //stimulusProvider.getSubset(tags, maxStimuli, false, 1, 0, 0, "", -1);
        final AnnotationSet savedAnnotations = loadAnnotations(storageTag);
        final AnnotationTimelinePanel annotationTimelinePanel = new AnnotationTimelinePanel();
        ((AnnotationTimelineView) simpleView).setAnnotationTimelinePanel(annotationTimelinePanel);
        int maxStimuli = 10; // todo: this has to be updated so that the enclosing loadStimulus does the stimuli selection       
        ((AnnotationTimelineView) simpleView).setStimuli(dataFactory, savedAnnotations, stimulusProvider, columnCount, "30px", maxStimuli);
//        ((ComplexView) simpleView).addOptionButton(new PresenterEventListner() {
//
//            @Override
//            public String getLabel() {
//                return "save annotations";
//            }
//
//            @Override
//            public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
//                saveAnnotations();
//                shotEventListner.resetSingleShot();
//            }
//        });
        annotationTimelinePanel.startUpdating(videoPanel, ((AnnotationTimelineView) simpleView), dataFactory);
    }

    private void saveAnnotations() {
        final Set<AnnotationData> annotations = ((AnnotationTimelineView) simpleView).getAnnotations();
        final AutoBean<AnnotationSet> annotationSetBean = dataFactory.annotationSet();
        final AnnotationSet annotationSet = annotationSetBean.as();
        annotationSet.setAnnotations(annotations);
        AutoBean<AnnotationSet> bean = AutoBeanUtils.getAutoBean(annotationSet);
        final String payload = AutoBeanCodex.encode(bean).getPayload();

        localStorage.setStoredDataValue(userResults.getUserData().getUserId(), storageTag, payload);
    }

    protected AnnotationSet loadAnnotations(String storageTag) {
        final String storedDataValue = localStorage.getStoredDataValue(userResults.getUserData().getUserId(), storageTag);
        if (storedDataValue != null && !storedDataValue.isEmpty()) {
            AutoBean<AnnotationSet> annotationSetBean = AutoBeanCodex.decode(dataFactory, AnnotationSet.class, storedDataValue);
            return annotationSetBean.as();
        } else {
            return dataFactory.annotationSet().as();
        }
    }

    @Override
    public void savePresenterState() {
        saveAnnotations();
        super.savePresenterState();
    }
}
