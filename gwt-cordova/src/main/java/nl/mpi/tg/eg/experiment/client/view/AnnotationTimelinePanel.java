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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.autobean.shared.AutoBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import nl.mpi.tg.eg.experiment.client.ServiceLocations;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.mpi.tg.eg.experiment.client.listener.SingleShotEventListner;
import nl.mpi.tg.eg.experiment.client.listener.StimulusButton;
import nl.mpi.tg.eg.experiment.client.model.AnnotationData;
import nl.mpi.tg.eg.experiment.client.model.AnnotationSet;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;
import nl.mpi.tg.eg.experiment.client.service.DataFactory;

/**
 * @since Sep 21, 2015 11:56:46 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class AnnotationTimelinePanel extends FocusPanel {

    protected final ServiceLocations serviceLocations = GWT.create(ServiceLocations.class);
    private final HashMap<AnnotationData, Label> annotationLebels = new HashMap<>();
    private final HashMap<Stimulus, StimulusButton> stimulusButtons = new HashMap<>();
    private final HashMap<Stimulus, Integer> tierTopPositions = new HashMap<>();
    private final ArrayList<Stimulus> stimulusArray = new ArrayList<>();
    AbsolutePanel absolutePanel = new AbsolutePanel();
    final Label timelineCursor = new Label();
    private int currentOffsetWidth = -1;
    private double currentVideoLength = -1;
    final int tierHeight;
    private boolean isScrubbing = false;
//    private Image addAnnotationButton = null;

    public AnnotationTimelinePanel() {
        absolutePanel.setStylePrimaryName("annotationTimelineTier");
        tierHeight = 30;
        timelineCursor.setStylePrimaryName("annotationTimelineCursor");
        absolutePanel.add(timelineCursor);
        this.add(absolutePanel);
    }

    public void startUpdating(final VideoPanel videoPanel, final AnnotationTimelineView annotationTimelineView, final DataFactory dataFactory) {
        AnnotationTimelinePanel.this.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                isScrubbing = false;
                final int relativeX = event.getRelativeX(absolutePanel.getElement());
                final int relativeY = event.getRelativeY(absolutePanel.getElement());
                videoPanel.setCurrentTime(currentVideoLength * ((float) relativeX / (float) currentOffsetWidth));
                int stimulusIndex = relativeY / tierHeight;
                final int topPosition = tierHeight * stimulusIndex;
                final Stimulus stimulus = stimulusArray.get(stimulusIndex);
//                if (addAnnotationButton != null) {
//                    absolutePanel.remove(addAnnotationButton);
//                }
//                addAnnotationButton = new Image(UriUtils.fromString(serviceLocations.staticFilesUrl() + stimulus.getImage()));
//                addAnnotationButton.setHeight(tierHeight + "px");
////                absolutePanel.add(addAnnotationButton, 0, topPosition);
//                absolutePanel.add(addAnnotationButton, getLeftPosition(videoPanel.getCurrentTime(), videoPanel.getDurationTime()), topPosition);
//                addAnnotationButton.addClickHandler(new ClickHandler() {
//
//                    @Override
//                    public void onClick(ClickEvent event) {
//                        insertAnnotation(stimulus, videoPanel, annotationTimelineView, dataFactory);
//                    }
//                });
            }
        });
        AnnotationTimelinePanel.this.addMouseDownHandler(new MouseDownHandler() {

            @Override
            public void onMouseDown(MouseDownEvent event) {
                isScrubbing = true;
            }
        });
        AnnotationTimelinePanel.this.addMouseUpHandler(new MouseUpHandler() {

            @Override
            public void onMouseUp(MouseUpEvent event) {
                isScrubbing = false;
            }
        });
        isScrubbing = false;
        AnnotationTimelinePanel.this.addMouseOverHandler(new MouseOverHandler() {

            @Override
            public void onMouseOver(MouseOverEvent event) {
                isScrubbing = false;
            }
        });
        AnnotationTimelinePanel.this.addMouseMoveHandler(new MouseMoveHandler() {

            @Override
            public void onMouseMove(MouseMoveEvent event) {
                if (isScrubbing) {
                    final int relativeX = event.getRelativeX(absolutePanel.getElement());
                    videoPanel.setCurrentTime(currentVideoLength * ((float) relativeX / (float) currentOffsetWidth));
                }
            }
        });
        Timer timer = new Timer() {
            double lastTime = -1;

            @Override
            public void run() {
                final double currentTime = videoPanel.getCurrentTime();
                if (lastTime != currentTime) {
                    lastTime = currentTime;
                    resizeTimeline(currentTime, videoPanel.getDurationTime());
//                labelticker.setText("" + currentTime);
                    absolutePanel.setWidgetPosition(timelineCursor, getLeftPosition(videoPanel.getCurrentTime(), videoPanel.getDurationTime()), absolutePanel.getOffsetHeight() - timelineCursor.getOffsetHeight());
                    // to folling section is going to be a bit time critical, so might need some attention in the future
                    ArrayList<Stimulus> intersectedStimuli = new ArrayList<>();
                    for (AnnotationData annotationData : annotationLebels.keySet()) {
                        if (intersectsTime(annotationData, currentTime)) {
                            intersectedStimuli.add(annotationData.getStimulus());
                        }
                    }
                    // since we dont have an included and excluded list, we instead clear all highlights then set the known highlights after that
                    for (StimulusButton button : stimulusButtons.values()) {
                        button.removeStyleName("stimulusButtonHighlight");
                    }
                    for (Stimulus intersectedStimulus : intersectedStimuli) {
                        final StimulusButton stimulusButton = stimulusButtons.get(intersectedStimulus);
                        if (stimulusButton != null) {
                            stimulusButton.addStyleName("stimulusButtonHighlight");
                        }
                    }
                }
                this.schedule(10);
            }
        };
        timer.schedule(10);
    }

    public void setTierCount(int tierCount) {
        absolutePanel.setHeight(tierHeight * tierCount + "px");
    }

    public void addStimulusButton(final Stimulus stimulus, final StimulusGrid stimulusGrid, final VideoPanel videoPanel, final AnnotationTimelineView annotationTimelineView, final DataFactory dataFactory, final int stimulusCounter, final int columnCount, final String imageWidth) {
        final int topPosition = getTierTopPosition(stimulus);
        tierTopPositions.put(stimulus, topPosition);
//        final Image image = new Image(UriUtils.fromString(serviceLocations.staticFilesUrl() + stimulus.getImage()));
//        image.setHeight(tierHeight + "px");
//        absolutePanel.add(image, 0, topPosition);
//        final Label tierLabel = new Label(stimulus.getLabel());
//        tierLabel.setHeight(tierHeight + "px");
//        absolutePanel.add(tierLabel, 0, topPosition);
        stimulusButtons.put(stimulus, stimulusGrid.addImageItem(new PresenterEventListner() {
            @Override
            public String getLabel() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                insertAnnotation(stimulus, videoPanel, annotationTimelineView, dataFactory);
                singleShotEventListner.resetSingleShot();
            }
        }, UriUtils.fromString(stimulus.getImage()), stimulusCounter / columnCount, 1 + stimulusCounter % columnCount, imageWidth, null, -1));
    }

    public void insertAnnotation(final Stimulus stimulus, final VideoPanel videoPanel, final AnnotationTimelineView annotationTimelineView, final DataFactory dataFactory) {
        final double clickedTime = videoPanel.getCurrentTime();
        AnnotationData foundAnnotationData = null;
        for (AnnotationData currentAnnotationData : annotationLebels.keySet()) {
            if (currentAnnotationData.getStimulus().equals(stimulus)) {
                if (intersectsTime(currentAnnotationData, clickedTime)) {
                    foundAnnotationData = currentAnnotationData;
                    break;
                }
            }
        }
        if (foundAnnotationData != null) {
            foundAnnotationData.setOutTime(clickedTime);
            annotationLebels.get(foundAnnotationData).setWidth(getWidth(foundAnnotationData, videoPanel.getDurationTime()) + "px");
        } else {
            final AutoBean<AnnotationData> annotationDataBean = dataFactory.annotation();
            final AnnotationData annotationData = annotationDataBean.as();
            annotationData.setInTime(clickedTime);
            annotationData.setOutTime(videoPanel.getDurationTime());
            annotationData.setAnnotationHtml("" + videoPanel.getCurrentTime());
            annotationData.setStimulus(stimulus);
            insertTierAnnotation(annotationData, videoPanel, annotationTimelineView);
        }
    }

    private int getLeftPosition(final AnnotationData annotationData, final double durationTime) {
        return getLeftPosition(annotationData.getInTime(), durationTime);
    }

    private int getWidth(final AnnotationData annotationData, final double durationTime) {
        return getLeftPosition(annotationData.getOutTime() - annotationData.getInTime(), durationTime);
    }

    private int getLeftPosition(final double currentTime, final double durationTime) {
        return (int) ((currentOffsetWidth - 1) * (currentTime / durationTime));
    }

    public boolean intersectsTime(final AnnotationData annotationData, final double currentTime) {
        return (currentTime >= annotationData.getInTime() && currentTime <= annotationData.getOutTime());
    }

    public Set<AnnotationData> getAnnotations() {
        return annotationLebels.keySet();
    }

    public Set<Stimulus> setAnnotations(AnnotationSet annotationSet, final VideoPanel videoPanel, final AnnotationTimelineView annotationTimelineView) {
        Set<Stimulus> foundStimulus = new HashSet<>();
        final Set<AnnotationData> annotations = annotationSet.getAnnotations();
        if (annotations != null) {
            for (AnnotationData annotationData : annotations) {
                insertTierAnnotation(annotationData, videoPanel, annotationTimelineView);
                foundStimulus.add(annotationData.getStimulus());
            }
        }
        return foundStimulus;
    }

    private void insertTierAnnotation(final AnnotationData annotationData, final VideoPanel videoPanel, final AnnotationTimelineView annotationTimelineView) {
        final Label label1 = new Label(annotationData.getAnnotationHtml());
        label1.setStylePrimaryName("annotationTimelineTierSegment");
        final SingleShotEventListner tierSegmentListner = new SingleShotEventListner() {

            @Override
            protected void singleShotFired() {
//                if (addAnnotationButton != null) {
//                    // todo: this would be better if the button was not added when a tier is clicked
//                    absolutePanel.remove(addAnnotationButton);
//                }
                clearHighlights();
                videoPanel.playSegment(annotationData);
                annotationTimelineView.setEditingAnnotation(annotationData);
                label1.setStyleDependentName("Highlight", true);
                resetSingleShot();
            }
        };
        label1.addClickHandler(tierSegmentListner);
        label1.addTouchStartHandler(tierSegmentListner);
        label1.addTouchMoveHandler(tierSegmentListner);
        label1.addTouchEndHandler(tierSegmentListner);
        label1.setWidth(getWidth(annotationData, videoPanel.getDurationTime()) + "px");
        final int topPosition = getTierTopPosition(annotationData.getStimulus());
        absolutePanel.add(label1, getLeftPosition(annotationData, videoPanel.getDurationTime()), topPosition);
        annotationLebels.put(annotationData, label1);
    }

    private int getTierTopPosition(Stimulus stimulus) {
        final int topPosition;
        if (tierTopPositions.containsKey(stimulus)) {
            topPosition = tierTopPositions.get(stimulus);
        } else {
            int stimulusCounter = tierTopPositions.size();
            topPosition = tierHeight * stimulusCounter;
            tierTopPositions.put(stimulus, topPosition);
            stimulusArray.add(stimulus);
        }
        return topPosition;
    }

    public void deleteAnnotation(final AnnotationData annotationData) {
        absolutePanel.remove(annotationLebels.remove(annotationData));
    }

    public void updateAnnotationText(final AnnotationData annotationData) {
        final Label label = annotationLebels.get(annotationData);
        label.setText(annotationData.getAnnotationHtml());
    }

    private void clearHighlights() {
        for (Label label : annotationLebels.values()) {
            label.setStyleDependentName("Highlight", false);
        }
    }

    public void updateAnnotation(final AnnotationData annotationData) {
        final Label label = annotationLebels.get(annotationData);
        label.setWidth(getWidth(annotationData, currentVideoLength) + "px");
        label.setText(annotationData.getAnnotationHtml());
        final int topPosition = absolutePanel.getWidgetTop(label);
        absolutePanel.setWidgetPosition(label, getLeftPosition(annotationData, currentVideoLength), topPosition);
    }

    public void resizeTimeline(final double currentTime, final double durationTime) {
        int width = absolutePanel.getOffsetWidth();
        if (currentOffsetWidth < 1 || currentOffsetWidth != width || currentVideoLength != durationTime) {
//            AnnotationTimelinePanel.this.setWidth(width + units);
            currentOffsetWidth = width;
            currentVideoLength = durationTime;
//            currentOffsetUnits = units;
            for (AnnotationData annotationData : annotationLebels.keySet()) {
                final Label label = annotationLebels.get(annotationData);
                label.setWidth(getWidth(annotationData, durationTime) + "px");
                final int topPosition = absolutePanel.getWidgetTop(label);
                absolutePanel.setWidgetPosition(label, getLeftPosition(annotationData, durationTime), topPosition);
            }
        }
    }
}
