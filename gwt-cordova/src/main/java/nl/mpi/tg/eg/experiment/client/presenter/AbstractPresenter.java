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

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.List;
import nl.mpi.tg.eg.experiment.client.ApplicationController.ApplicationState;
import nl.mpi.tg.eg.experiment.client.Messages;
import nl.mpi.tg.eg.experiment.client.ServiceLocations;
import nl.mpi.tg.eg.experiment.client.listener.AppEventListner;
import nl.mpi.tg.eg.experiment.client.listener.CancelableStimulusListener;
import nl.mpi.tg.eg.experiment.client.listener.MediaSubmissionListener;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.mpi.tg.eg.experiment.client.listener.SingleShotEventListner;
import nl.mpi.tg.eg.experiment.client.service.DataSubmissionService;
import nl.mpi.tg.eg.experiment.client.view.ComplexView;
import nl.mpi.tg.eg.experiment.client.view.SimpleView;
import nl.mpi.tg.eg.experiment.client.view.TimedStimulusView;
import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;

/**
 * @since Oct 28, 2014 3:32:10 PM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public abstract class AbstractPresenter implements Presenter {

    protected final Messages messages = GWT.create(Messages.class);
    protected final ServiceLocations serviceLocations = GWT.create(ServiceLocations.class);
    protected final RootLayoutPanel widgetTag;
    final protected SimpleView simpleView;
    private PresenterEventListner backEventListner = null;
    protected final List<TimedStimulusListener> backEventListners = new ArrayList<>();
    private PresenterEventListner nextEventListner = null;
    private PresenterEventListner windowClosingEventListner = null;
    private final Timer audioTickerTimer;
    protected ApplicationState nextState;

    public AbstractPresenter(RootLayoutPanel widgetTag, SimpleView simpleView) {
        this.widgetTag = widgetTag;
        this.simpleView = simpleView;
        audioTickerTimer = new Timer() {
            public void run() {
//                isAudioRecording();
                getAudioRecorderTime();
            }
        };
    }

    @Override
    public void setState(final AppEventListner appEventListner, final ApplicationState prevState, final ApplicationState nextState) {
        this.nextState = nextState;
        widgetTag.clear();
        if (prevState != null) {
            backEventListner = new PresenterEventListner() {

                @Override
                public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                    if (allowBackAction(appEventListner)) {
                        savePresenterState();
                        appEventListner.requestApplicationState(prevState);
                    }
                }

                @Override
                public int getHotKey() {
                    return -1;
                }

                @Override
                public String getLabel() {
                    return prevState.label;
                }
            };
        } else {
            // todo: on android there needs to be a back action available
//            backEventListner = new PresenterEventListner() {
//
//                @Override
//                public void eventFired(ButtonBase button) {
//                    appEventListner.requestApplicationState(ApplicationState.end);
//                }
//
//                @Override
//                public String getLabel() {
//                    return ApplicationState.menu.label;
//                }
//            };
        }
        simpleView.addTitle(getTitle(), backEventListner);

        if (nextState != null) {
            nextEventListner = new PresenterEventListner() {

                @Override
                public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                    savePresenterState();
                    appEventListner.requestApplicationState(nextState);
                }

                @Override
                public int getHotKey() {
                    return -1;
                }

                @Override
                public String getLabel() {
                    return nextState.label;
                }
            };
            simpleView.setButton(SimpleView.ButtonType.next, nextEventListner);
        }
        setContent(appEventListner);
        simpleView.resizeView();
        widgetTag.add(simpleView);
    }

    protected void addText(String textString) {
        ((ComplexView) simpleView).addText(textString);
    }

    protected void addHtmlText(String textString) {
        ((ComplexView) simpleView).addHtmlText(textString, null);
    }

    protected void addHtmlText(String textString, String styleName) {
        ((ComplexView) simpleView).addHtmlText(textString, styleName);
    }

    protected void image(final String imageString, int postLoadMs, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener) {
        image(imageString, null, postLoadMs, loadedStimulusListener, failedStimulusListener);
    }

    protected void image(final String imageString, final String styleName, int postLoadMs, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener) {
        // consider fromTrustedString if there are issues with fromString when sdcard stimuli are used
        ((TimedStimulusView) simpleView).addTimedImage(UriUtils.fromString((imageString.startsWith("file")) ? imageString : serviceLocations.staticFilesUrl() + imageString), styleName, postLoadMs, null, loadedStimulusListener, failedStimulusListener, null);
    }

    protected void addPadding() {
        ((ComplexView) simpleView).addPadding();
    }

    protected void centrePage() {
        ((ComplexView) simpleView).centrePage();
    }

    public void actionFooterButton(final PresenterEventListner presenterListerner) {
        ((ComplexView) simpleView).addFooterButton(presenterListerner);
    }

    public void targetFooterButton(final PresenterEventListner presenterListerner) {
        ((ComplexView) simpleView).addFooterButton(presenterListerner);
    }

    public void actionButton(final PresenterEventListner presenterListerner, String styleName) {
        ((ComplexView) simpleView).addOptionButton(presenterListerner, styleName);
    }

    public void targetButton(final PresenterEventListner presenterListerner, String styleName) {
        ((ComplexView) simpleView).addOptionButton(presenterListerner, styleName);
    }

    public void optionButton(final PresenterEventListner presenterListerner, String styleName) {
        ((ComplexView) simpleView).addOptionButton(presenterListerner, styleName);
    }

    protected void table(final TimedStimulusListener timedStimulusListener) {
        table(null, timedStimulusListener);
    }

    protected void table(final String styleName, final TimedStimulusListener timedStimulusListener) {
        table(styleName, false, timedStimulusListener);
    }

    protected void table(final String styleName, boolean showOnBackButton, final TimedStimulusListener timedStimulusListener) {
        final Widget tableWidget = ((ComplexView) simpleView).startTable(styleName);
        timedStimulusListener.postLoadTimerFired();
        ((ComplexView) simpleView).endTable();
        if (showOnBackButton) {
            tableWidget.setVisible(false);
            // todo: backEventListners list should be emptied on screen clear etc
            backEventListners.add(new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    tableWidget.setVisible(!tableWidget.isVisible());
                }
            });
        }
    }

    protected void row(final TimedStimulusListener timedStimulusListener) {
        ((ComplexView) simpleView).startRow();
        timedStimulusListener.postLoadTimerFired();
        ((ComplexView) simpleView).endRow();
    }

    protected void column(final TimedStimulusListener timedStimulusListener) {
        column(null, timedStimulusListener);
    }

    protected void column(final String styleName, final TimedStimulusListener timedStimulusListener) {
        ((ComplexView) simpleView).startCell(styleName);
        timedStimulusListener.postLoadTimerFired();
        ((ComplexView) simpleView).endCell();
    }

    protected void region(final String regionId, final String styleName, final TimedStimulusListener timedStimulusListener) {
        ((ComplexView) simpleView).startRegion(regionId, styleName);
        timedStimulusListener.postLoadTimerFired();
        ((ComplexView) simpleView).endRegion();
    }

    protected void regionStyle(final String regionId, final String styleName) {
        ((ComplexView) simpleView).setRegionStyle(regionId, styleName);
    }

    protected void regionReplace(final String regionId, final String styleName, final TimedStimulusListener timedStimulusListener) {
        ((ComplexView) simpleView).clearRegion(regionId);
        ((ComplexView) simpleView).startRegion(regionId, styleName);
        timedStimulusListener.postLoadTimerFired();
        ((ComplexView) simpleView).endRegion();
    }

    protected void regionClear(final String regionId) {
        ((ComplexView) simpleView).clearRegion(regionId);
    }

    @Override
    public void fireBackEvent() {
        if (backEventListner != null) {
            backEventListner.eventFired(null, null);
        } else {
            for (TimedStimulusListener listner : backEventListners) {
                listner.postLoadTimerFired();
            }
        }
    }

    @Override
    public void fireResizeEvent() {
        simpleView.resizeView();
    }

    @Override
    public void fireWindowClosing() {
        if (windowClosingEventListner != null) {
            windowClosingEventListner.eventFired(null, null);
        }
    }

    public void setWindowClosingListener(PresenterEventListner windowClosingEventListner) {
        this.windowClosingEventListner = windowClosingEventListner;
    }

    /**
     * called before the back event listener is triggered
     *
     * @param appEventListner
     * @return {@code true} if the back event is to continue
     */
    protected boolean allowBackAction(final AppEventListner appEventListner) {
        return true;
    }

    protected abstract String getTitle();

    protected abstract String getSelfTag();

    protected abstract void setContent(final AppEventListner appEventListner);

    protected void gotoNextPresenter(final AppEventListner appEventListner) {
        savePresenterState();
        Timer timer = new Timer() {
            public void run() {
                appEventListner.requestApplicationState(nextState);
            }
        };
        timer.schedule(100);
    }

    protected void gotoPresenter(final AppEventListner appEventListner, final ApplicationState targetState) {
        savePresenterState();
        Timer timer = new Timer() {
            public void run() {
                appEventListner.requestApplicationState(targetState);
            }
        };
        timer.schedule(100);
    }

    public void hasGetParameter(final AppEventListner appEventListner, final TimedStimulusListener conditionTrue, final TimedStimulusListener conditionFalse, final String getParamName) {
        Timer timer = new Timer() {
            public void run() {
                String paramValue = Window.Location.getParameter(getParamName);
                if (paramValue != null) {
                    conditionTrue.postLoadTimerFired();
                } else {
                    conditionFalse.postLoadTimerFired();
                }
            }
        };
        timer.schedule(100);
    }

    protected void bumpAudioTicker() {
        audioTickerTimer.schedule(100);
    }

    protected void audioOk(Boolean isRecording, String message) {
        if (simpleView instanceof ComplexView) {
            ((ComplexView) simpleView).setRecorderState(message, isRecording);
//            if (isRecording) {
            bumpAudioTicker();
//            }
        }
    }

    protected void audioError(String message) {
        if (simpleView instanceof ComplexView) {
            ((ComplexView) simpleView).setRecorderState(message, false);
//            ((ComplexView) simpleView).clearPage();
//            ((ComplexView) simpleView).addText("Could not start the audio recorder");
            bumpAudioTicker();
        }
    }

//    protected void startAudioRecorderFailed(String message) {
//        backEventListner.eventFired(null, null);
//        ((ComplexView) simpleView).clearPage();
//        ((ComplexView) simpleView).addText("Could not start the audio recorder");
//        ((ComplexView) simpleView).addText(message);
//    }
    protected native void startAudioRecorder(final DataSubmissionService dataSubmissionService, final boolean wavFormat, final String deviceRegex, final String subDirectoryName, final String directoryName, final String stimulusIdString, final String userIdString, final String screenName, final MediaSubmissionListener mediaSubmissionListener) /*-{
        var abstractPresenter = this;
        console.log("startAudioRecorder: " + wavFormat + " : " + subDirectoryName + " : " + directoryName + " : " + stimulusIdString + " : " + userIdString);
        if($wnd.plugins){
            $wnd.plugins.fieldKitRecorder.record(function (tagvalue) {
                console.log("startAudioRecorderOk: " + tagvalue);
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioOk(Ljava/lang/Boolean;Ljava/lang/String;)(@java.lang.Boolean::TRUE, tagvalue);
            }, function (tagvalue) {
                console.log("startAudioRecorderError: " + tagvalue);
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioError(Ljava/lang/String;)(tagvalue);
            },  subDirectoryName, directoryName,  stimulusIdString);
        } else if($wnd.Recorder && $wnd.Recorder.isRecordingSupported()) {
            console.log("isRecordingSupported");
            abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::addText(Ljava/lang/String;)("(debug) enumerateDevices");
            navigator.mediaDevices.enumerateDevices().then(function (deviceInfos) {
                for (var index = 0; index < deviceInfos.length; index++) {
                    var deviceInfo = deviceInfos[index];
                    abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::addText(Ljava/lang/String;)("(debug) " + deviceInfo.label.search(deviceRegex));    
                    if(deviceInfo.kind === 'audioinput' && deviceInfo.label.search(deviceRegex) >= 0){
                        abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::addText(Ljava/lang/String;)("(debug) " + deviceInfo.kind);            
                        abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::addText(Ljava/lang/String;)("(debug) " + deviceInfo.label);                    
                        abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::addText(Ljava/lang/String;)("(debug) " + deviceInfo.deviceId);                    
                    }
                }
            });
            //.catch(function (error) {
             //   abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::addText(Ljava/lang/String;)("(debug) " + error.message);
            //});
            //deviceId: "Logitech USB Headset (046d:0a45)"groupId: "a7214c3e9d2d1568020e35a6adea7e75e8246bc9db67abc72fdab7a0036617a1"
            
            abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioOk(Ljava/lang/Boolean;Ljava/lang/String;)(@java.lang.Boolean::TRUE, "isRecordingSupported");
            $wnd.recorder = new $wnd.Recorder({numberOfChannels: 1, encoderPath: "opus-recorder/encoderWorker.min.js", monitorGain: 0, recordingGain: 1, encoderSampleRate: 48000, mediaTrackConstraints: {deviceId: '855f1790680be68988e9147de97e07ef89ad753b69907100b5198f752d134ffb'}});
            $wnd.recorder.ondataavailable = function( typedArray ){
                dataSubmissionService.@nl.mpi.tg.eg.experiment.client.service.DataSubmissionService::submitAudioData(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/google/gwt/typedarrays/shared/Uint8Array;Lnl/mpi/tg/eg/experiment/client/listener/MediaSubmissionListener;)(userIdString, screenName, stimulusIdString, typedArray, mediaSubmissionListener);
            };
            try {
                $wnd.recorder.start(); 
            } catch(e) {
                console.log(e.message);
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioError(Ljava/lang/String;)(e.message);
            };
        } else {
            abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioError(Ljava/lang/String;)(null);
        }
     }-*/;

    protected native void requestRecorderPermissions() /*-{
        var abstractPresenter = this;
        console.log("requestRecorderPermissions");
        if($wnd.plugins){
            $wnd.plugins.fieldKitRecorder.requestRecorderPermissions(function () {
                console.log("requestRecorderPermissionsOk");
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioOk(Ljava/lang/Boolean;Ljava/lang/String;)(@java.lang.Boolean::TRUE, null);
            }, function (tagvalue) {
                console.log("requestRecorderPermissionsError: " + tagvalue);
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioError(Ljava/lang/String;)(tagvalue);
            });
        } else {
            abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioError(Ljava/lang/String;)(null);
        }
     }-*/;

    protected native void requestFilePermissions() /*-{
        var abstractPresenter = this;
        console.log("requestFilePermissions");
        if($wnd.plugins){
            $wnd.plugins.fieldKitRecorder.requestFilePermissions(function () {
                console.log("requestFilePermissionsOk");
//                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioOk(Ljava/lang/Boolean;Ljava/lang/String;)(@java.lang.Boolean::TRUE, null);
            }, function (tagvalue) {
                console.log("requestFilePermissionsError: " + tagvalue);
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioError(Ljava/lang/String;)(tagvalue);
            });
        } else {
            abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioError(Ljava/lang/String;)(null);
        }
     }-*/;

    protected native void isAudioRecording() /*-{
        var abstractPresenter = this;
        if($wnd.plugins){
            $wnd.plugins.fieldKitRecorder.isRecording(function () {
//                console.log("isAudioRecording");
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioOk(Ljava/lang/Boolean;Ljava/lang/String;)(@java.lang.Boolean::TRUE, null);
            }, function (tagvalue) {
//                console.log("isAudioRecording: " + tagvalue);
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioOk(Ljava/lang/Boolean;Ljava/lang/String;)(@java.lang.Boolean::FALSE, null);
            });
        } else {
            abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioError(Ljava/lang/String;)(null);
        }
     }-*/;

    protected native void getAudioRecorderTime() /*-{
        var abstractPresenter = this;
        if($wnd.plugins){
            $wnd.plugins.fieldKitRecorder.getTime(function (currentTime) {
//                console.log("isAudioRecording: " + " : " + currentTime);
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioOk(Ljava/lang/Boolean;Ljava/lang/String;)(@java.lang.Boolean::TRUE, currentTime);
            }, function (tagvalue) {
//                console.log("isAudioRecording: " + tagvalue);
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioOk(Ljava/lang/Boolean;Ljava/lang/String;)(@java.lang.Boolean::FALSE, null);
            });
        } else if($wnd.Recorder && $wnd.Recorder.isRecordingSupported() && $wnd.recorder) {
            if ($wnd.recorder) {
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioOk(Ljava/lang/Boolean;Ljava/lang/String;)(@java.lang.Boolean::TRUE, "recording");
            }
        } else {
            abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioError(Ljava/lang/String;)(null);
        }
     }-*/;

    protected native void stopAudioRecorder() /*-{
        var abstractPresenter = this;
        console.log("stopAudioRecorder");
        if($wnd.plugins){
            $wnd.plugins.fieldKitRecorder.stop(function (tagvalue) {
                console.log("stopAudioRecorderOk: " + tagvalue);
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioOk(Ljava/lang/Boolean;Ljava/lang/String;)(@java.lang.Boolean::FALSE, tagvalue);
            }, function (tagvalue) {
                console.log("stopAudioRecorderError: " + tagvalue);
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioError(Ljava/lang/String;)(tagvalue);
            });
        } else if($wnd.Recorder && $wnd.Recorder.isRecordingSupported()) {
            if ($wnd.recorder) {
                $wnd.recorder.stop();
                $wnd.recorder = null;
            }
        } else {
            abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioError(Ljava/lang/String;)(null);
        }
     }-*/;

    protected native void startAudioRecorderTag(int tier) /*-{
        var abstractPresenter = this;
        console.log("startAudioRecorderTag: " + tier);
        if($wnd.plugins){
            $wnd.plugins.fieldKitRecorder.startTag(function (tagvalue) {
                console.log("startAudioRecorderTagOk: " + tagvalue);
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioOk(Ljava/lang/Boolean;Ljava/lang/String;)(@java.lang.Boolean::TRUE, tagvalue);
            }, function (tagvalue) {
                console.log("startAudioRecorderTagError: " + tagvalue);
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioError(Ljava/lang/String;)(tagvalue);
            }, tier);
        } else {
            abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioError(Ljava/lang/String;)(null);
        }
     }-*/;

    protected native void endAudioRecorderTag(int tier, String stimulusId, String stimulusCode, String eventTag) /*-{
        var abstractPresenter = this;
        console.log("endAudioRecorderTag: " + tier + " : " + stimulusId + " : " + stimulusCode + " : " + eventTag);
        if($wnd.plugins){
            $wnd.plugins.fieldKitRecorder.endTag(function (tagvalue) {
                console.log("endAudioRecorderTagOk: " + tagvalue);
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioOk(Ljava/lang/Boolean;Ljava/lang/String;)(@java.lang.Boolean::TRUE, tagvalue);
            }, function (tagvalue) {
                console.log("endAudioRecorderTagError: " + tagvalue);
                abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioError(Ljava/lang/String;)(tagvalue);
            }, tier, stimulusId, stimulusCode, eventTag);
        } else {
            abstractPresenter.@nl.mpi.tg.eg.experiment.client.presenter.AbstractPresenter::audioError(Ljava/lang/String;)(null);
        }
     }-*/;

    @Override
    public void savePresenterState() {
        if (simpleView instanceof ComplexView) {
            ((ComplexView) simpleView).clearDomHandlers();
        }
    }

}
