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
package nl.mpi.tg.eg.experiment.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.Set;
import nl.mpi.tg.eg.experiment.client.ServiceLocations;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.mpi.tg.eg.experiment.client.listener.SingleShotEventListner;
import nl.mpi.tg.eg.experiment.client.model.AnnotationData;
import nl.mpi.tg.eg.experiment.client.model.AnnotationSet;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;
import nl.mpi.tg.eg.experiment.client.service.AudioPlayer;
import nl.mpi.tg.eg.experiment.client.service.DataFactory;
import nl.mpi.tg.eg.experiment.client.service.StimulusProvider;

/**
 * @since Oct 21, 2015 1:25:34 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class AnnotationTimelineView extends TimedStimulusView {

    protected final ServiceLocations serviceLocations = GWT.create(ServiceLocations.class);
    private AnnotationTimelinePanel annotationTimelinePanel;
    private VideoPanel videoPanel;
    final FlexTable flexTable = new FlexTable();

    public AnnotationTimelineView(AudioPlayer audioPlayer) {
        super(audioPlayer);
        flexTable.setStylePrimaryName("annotationTimelinePanel");
        outerPanel.add(flexTable);
    }

    public void setAnnotationTimelinePanel(AnnotationTimelinePanel annotationTimelinePanel) {
        this.annotationTimelinePanel = annotationTimelinePanel;
        flexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
        flexTable.getFlexCellFormatter().setColSpan(2, 0, 2);
        flexTable.setWidget(2, 0, annotationTimelinePanel);
    }

    public void setEditingAnnotation(final AnnotationData annotationData) {
        final TextBox textBox = new TextBox();
        // Set up the IN and OUT buttons and a delete annotation button
        final VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.setStylePrimaryName("annotationUiGroup");
        final HorizontalPanel editingPanel = new HorizontalPanel();
        verticalPanel.setWidth(annotationTimelinePanel.getOffsetWidth() + "px");
        textBox.setWidth("100%");
        textBox.setText(annotationData.getAnnotationHtml());
        textBox.addKeyUpHandler(new KeyUpHandler() {

            @Override
            public void onKeyUp(KeyUpEvent event) {
                annotationData.setAnnotationHtml(textBox.getText());
                annotationTimelinePanel.updateAnnotationText(annotationData);
            }
        });
        verticalPanel.add(textBox);
        editingPanel.add(getOptionButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return "in";
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                annotationData.setInTime(videoPanel.getCurrentTime());
                annotationTimelinePanel.updateAnnotation(annotationData);
            }
        }).getWidget());
        editingPanel.add(getOptionButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return "Delete";
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                annotationTimelinePanel.deleteAnnotation(annotationData);
                flexTable.remove(verticalPanel);
            }
        }).getWidget());
        editingPanel.add(getOptionButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return "Out";
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                annotationData.setOutTime(videoPanel.getCurrentTime());
                annotationTimelinePanel.updateAnnotation(annotationData);
            }
        }).getWidget());
        final Image image = new Image(UriUtils.fromString(annotationData.getStimulus().getImage()));
        image.setHeight("65px");
        editingPanel.add(image);
        verticalPanel.add(editingPanel);
        flexTable.setWidget(1, 0, verticalPanel);
    }

    public void setVideoPanel(VideoPanel videoPanel) {
        this.videoPanel = videoPanel;
        flexTable.setWidget(0, 0, videoPanel);
    }

    public Set<AnnotationData> getAnnotations() {
        return annotationTimelinePanel.getAnnotations();
    }

    public void setStimuli(final DataFactory dataFactory, final AnnotationSet savedAnnotations, StimulusProvider stimulusProvider, int columnCount, String imageWidth, int maxButtons) {
        final Set<Stimulus> stimuliSet = annotationTimelinePanel.setAnnotations(savedAnnotations, videoPanel, this);
        while (stimuliSet.size() < maxButtons && stimulusProvider.hasNextStimulus(1)) {
            stimulusProvider.nextStimulus(1);
            final Stimulus stimulus = stimulusProvider.getCurrentStimulus();
            stimuliSet.add(stimulus);
        }

        final StimulusGrid stimulusGrid = new StimulusGrid(domHandlerArray);
        int stimulusCounter = 0;
        annotationTimelinePanel.setTierCount(stimuliSet.size());
        for (Stimulus stimulus : stimuliSet) {
            annotationTimelinePanel.addStimulusButton(stimulus, stimulusGrid, videoPanel, this, dataFactory, stimulusCounter, columnCount, imageWidth);
            stimulusCounter++;
        }
        flexTable.setWidget(0, 1, stimulusGrid);
    }

    @Override
    protected void parentResized(int height, int width, String units) {
        videoPanel.setWidth((int) (width * 0.4) + units);
        annotationTimelinePanel.setWidth((int) (width * 0.8) + units); //.resizeTimeline(videoPanel.getCurrentTime(), videoPanel.getDurationTime(), (int) (width * 0.8), units);
        super.parentResized(height, width, units);
    }
}
