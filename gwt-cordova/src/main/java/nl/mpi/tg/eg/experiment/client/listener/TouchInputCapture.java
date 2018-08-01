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
package nl.mpi.tg.eg.experiment.client.listener;

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchCancelHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;

/**
 * @since Oct 23, 2017 2:10:09 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public abstract class TouchInputCapture implements Event.NativePreviewHandler, MouseDownHandler, MouseMoveHandler, MouseOutHandler, MouseOverHandler, MouseUpHandler, TouchCancelHandler, TouchEndHandler, TouchMoveHandler, TouchStartHandler {

    private final StringBuilder recordedTouches = new StringBuilder();
    private final List<TouchInputZone> touchZones = new ArrayList<>();
    private final List<TouchRecord> touchRecordList = new LinkedList<>();
    private final Set<String> currentTriggeredZoneGroups = new HashSet<>();
    private final TimedStimulusListener endOfTouchEventListner;
    private final int msAfterEndOfTouchToNext;
    private final Timer endOfTouchTimer;
    private final Timer touchRecordTimer;
    private final Duration duration = new Duration();
    boolean disableMouseEvents = false;

    public TouchInputCapture(final TimedStimulusListener endOfTouchEventListner, final int msAfterEndOfTouchToNext) {
        this.endOfTouchEventListner = endOfTouchEventListner;
        this.msAfterEndOfTouchToNext = msAfterEndOfTouchToNext;
        endOfTouchTimer = new Timer() {
            @Override
            public void run() {
                setDebugLabel("Triggered:msAfterLastTouchEvent");
                triggerZones(new ArrayList<TouchInputZone>());
            }
        };
        touchRecordTimer = new Timer() {
            @Override
            public void run() {
                processTouchRecords();
            }
        };
    }

    class TouchRecord {

        final boolean activeEvent;
        final int elapsedMillis;
        final int xPos;
        final int yPos;

        public TouchRecord(boolean activeEvent, int elapsedMillis, int xPos, int yPos) {
            this.activeEvent = activeEvent;
            this.elapsedMillis = elapsedMillis;
            this.xPos = xPos;
            this.yPos = yPos;
        }
    }

    public abstract void setDebugLabel(String debugLabel);

    public abstract void endOfTouchEvent(String debugLabel);

    private void appendTouch(int xPos, int yPos, final List<TouchInputZone> triggeredZones) {
        recordedTouches.append(duration.elapsedMillis());
        recordedTouches.append(",");
        recordedTouches.append(xPos);
        recordedTouches.append(",");
        recordedTouches.append(yPos);
        recordedTouches.append(",");
        for (TouchInputZone zone : touchZones) {
            if (zone.intersects(xPos, yPos)) {
                recordedTouches.append(zone.getEventTag());
                triggeredZones.add(zone);
            }
            recordedTouches.append(",");
        }
        recordedTouches.append(";");
    }

    public void addTouchZone(TouchInputZone touchZone) {
        touchZones.add(touchZone);
    }

    private void triggerZones(final List<TouchInputZone> triggeredZones) {
        final Set<String> triggeredZoneGroups = new HashSet<String>();
        for (TouchInputZone zone : triggeredZones) {
            triggeredZoneGroups.add(zone.getGroupName());
        }
        if (triggeredZones.isEmpty()) {
            for (TouchInputZone zone : touchZones) {
                zone.clearEvent();
            }
            endOfTouchTimer.cancel();
            endOfTouchEventListner.postLoadTimerFired();
        } else {
            for (TouchInputZone zone : touchZones) {
                if (triggeredZoneGroups.contains(zone.getGroupName())) {
                    zone.triggerEvent();
                } else {
                    zone.clearEvent();
                }
            }
            if (msAfterEndOfTouchToNext > 0) {
                endOfTouchTimer.schedule(msAfterEndOfTouchToNext);
            }
        }
        for (String currentTriggeredZone : currentTriggeredZoneGroups) {
            if (!triggeredZoneGroups.contains(currentTriggeredZone)) {
                endOfTouchEvent(currentTriggeredZone);
            }
        }
        currentTriggeredZoneGroups.retainAll(triggeredZoneGroups);
        currentTriggeredZoneGroups.addAll(triggeredZoneGroups);
    }

    private void recordTouches(final JsArray<Touch> targetTouches) {
        if (targetTouches.length() == 0) {
            touchRecordList.add(new TouchRecord(false, duration.elapsedMillis(), -1, -1));
        }
        for (int index = 0; index < targetTouches.length(); index++) {
            touchRecordList.add(new TouchRecord(true, duration.elapsedMillis(), targetTouches.get(index).getScreenX(), targetTouches.get(index).getScreenY()));
        }
    }

    private void touchesToDebugLabel(final JsArray<Touch> targetTouches) {
        StringBuilder builder = new StringBuilder();
//        final JsArray<Touch> targetTouches = event.getTouches();
        for (int index = 0; index < targetTouches.length(); index++) {
            builder.append(targetTouches.get(index).getScreenX());
            builder.append(", ");
            builder.append(targetTouches.get(index).getScreenY());
            builder.append(" ");
        }
        setDebugLabel(builder.toString());
    }

    private void bumpTouchRecords() {
        if (!touchRecordTimer.isRunning()) {
            touchRecordTimer.schedule(100);
        }
    }

    private void processTouchRecords() {
        final List<TouchInputZone> triggeredZones = new ArrayList<>();
        while (!touchRecordList.isEmpty()) {
            final TouchRecord currentRecord = touchRecordList.remove(0);
            if (currentRecord.activeEvent) {
                appendTouch(currentRecord.xPos, currentRecord.yPos, triggeredZones);
            } else {
                triggeredZones.clear();
            }
            setDebugLabel(touchRecordList.size() + " " + currentRecord.xPos + "," + currentRecord.yPos + " " + currentRecord.activeEvent);
        }
        triggerZones(triggeredZones);
    }
    private boolean isMouseDown = false;

    private void onAnyTouch(TouchEvent event) {
        recordTouches(event.getTouches());
        bumpTouchRecords();
//        touchesToDebugLabel(event.getTouches());
    }

    private void onAnyMouse(MouseEvent event) {
        touchRecordList.add(new TouchRecord(isMouseDown, duration.elapsedMillis(), event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY()));
        bumpTouchRecords();
//        setDebugLabel(event.toDebugString() + " " + event.getNativeEvent().getClientX() + "," + event.getNativeEvent().getClientY());
    }

    @Override
    public void onTouchStart(TouchStartEvent event) {
        onAnyTouch(event);
    }

    @Override
    public void onTouchMove(TouchMoveEvent event) {
        onAnyTouch(event);
    }

    @Override
    public void onTouchEnd(TouchEndEvent event) {
        onAnyTouch(event);
    }

    @Override
    public void onTouchCancel(TouchCancelEvent event) {
        onAnyTouch(event);
    }

    @Override
    public void onMouseDown(MouseDownEvent event) {
        isMouseDown = true;
        onAnyMouse(event);
    }

    @Override
    public void onMouseMove(MouseMoveEvent event) {
        onAnyMouse(event);
    }

    @Override
    public void onMouseOut(MouseOutEvent event) {
        onAnyMouse(event);
    }

    @Override
    public void onMouseOver(MouseOverEvent event) {
        onAnyMouse(event);
    }

    @Override
    public void onMouseUp(MouseUpEvent event) {
        isMouseDown = false;
        onAnyMouse(event);
    }

    @Override
    public void onPreviewNativeEvent(Event.NativePreviewEvent event) {
        final int eventType = event.getTypeInt();
        int button = event.getNativeEvent().getButton();
        // in chrome (gwt debug) event.getNativeEvent().getButton() is always returning 1
        isMouseDown = (button & (NativeEvent.BUTTON_LEFT | NativeEvent.BUTTON_RIGHT)) != 0;
        final List<TouchInputZone> triggeredZones = new ArrayList<>();
        switch (eventType) {
            case Event.ONMOUSEDOWN:
            case Event.ONMOUSEMOVE:
            case Event.ONMOUSEOVER:
                touchRecordList.add(new TouchRecord(true, duration.elapsedMillis(), event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY()));
//                setDebugLabel(event.toDebugString() + " " + event.getNativeEvent().getClientX() + "," + event.getNativeEvent().getClientY());
                break;
            case Event.ONCLICK:
            case Event.ONDBLCLICK:
            case Event.ONMOUSEUP:
            case Event.ONMOUSEOUT:
                touchRecordList.add(new TouchRecord(false, duration.elapsedMillis(), event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY()));
//                setDebugLabel(event.toDebugString() + " " + event.getNativeEvent().getClientX() + "," + event.getNativeEvent().getClientY());
                break;
            case Event.ONTOUCHCANCEL:
            case Event.ONTOUCHEND:
            case Event.ONTOUCHMOVE:
            case Event.ONTOUCHSTART:
//                touchesToDebugLabel(event.getNativeEvent().getTouches());
                recordTouches(event.getNativeEvent().getTouches());
                break;
            default:
//                setDebugLabel(event.toDebugString());
        }
        bumpTouchRecords();
    }

    public String getTouchReport(int screenWidth, int screenHeight) {
        recordedTouches.insert(0, ";");
        recordedTouches.insert(0, screenHeight);
        recordedTouches.insert(0, ",");
        recordedTouches.insert(0, screenWidth);
        recordedTouches.insert(0, ",");
        recordedTouches.insert(0, duration.elapsedMillis());
        return recordedTouches.toString();
    }
}
