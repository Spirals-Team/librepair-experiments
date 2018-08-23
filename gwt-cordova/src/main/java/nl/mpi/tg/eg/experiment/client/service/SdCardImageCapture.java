/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.experiment.client.service;

import com.google.gwt.user.client.Timer;
import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;
import nl.mpi.tg.eg.experiment.client.model.SdCardStimulus;
import nl.mpi.tg.eg.experiment.client.model.UserId;

/**
 * @since Jun 29, 2016 2:49:37 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class SdCardImageCapture {

    final private TimedStimulusListener timedStimulusListener;
    final private SdCardStimulus sdCardStimulus;
    final private UserId userId;
    private final LocalStorage localStorage;
    protected static final String CAPTURED_IMAGES = "capturedImages";

    public SdCardImageCapture(TimedStimulusListener timedStimulusListener, SdCardStimulus sdCardStimulus, UserId userId, LocalStorage localStorage) {
        this.timedStimulusListener = timedStimulusListener;
        this.sdCardStimulus = sdCardStimulus;
        this.userId = userId;
        this.localStorage = localStorage;
    }

    public boolean hasBeenCaptured() {
        final String storedDataValue = localStorage.getStoredDataValue(userId, CAPTURED_IMAGES + sdCardStimulus.getUniqueId());
        return storedDataValue != null && storedDataValue.length() > 0;
    }

    public void captureImage() {
        captureImageUI(userId.toString(), sdCardStimulus.getUniqueId());
    }

    public String getCapturedPath() {
        final String storedDataValue = localStorage.getStoredDataValue(userId, CAPTURED_IMAGES + sdCardStimulus.getUniqueId());
        if (storedDataValue != null && storedDataValue.length() > 0) {
            return storedDataValue;
        } else {
            return sdCardStimulus.getImage(); // todo change this
        }
    }

//    protected void captureStimulusImage() {
//        // todo: add capture / replace button
//        captureImageUI(userId.toString(), sdCardStimulus.getUniqueId());
////        throw new UnsupportedOperationException();
//    }
    //    protected void stimulusImageCapture(int percentOfPage, int maxHeight, int maxWidth, int postLoadMs, final TimedStimulusListener timedStimulusListener) {
//        final String workerId = userResults.getUserData().getMetadataValue(new MetadataFieldProvider().workerIdMetadataField);
////        String directoryPath= ((SdCardStimulus)stimulusProvider.getCurrentStimulus()).
//        super.captureStimulusImage(workerId, directoryName, stimulusProvider.getCurrentStimulus().getUniqueId());
//    }
    protected void imageCaptured(final String stimulusIdString, final String fullPath) {
        Timer timer = new Timer() {
            @Override
            public void run() {
                localStorage.setStoredDataValue(userId, CAPTURED_IMAGES + sdCardStimulus.getUniqueId(), fullPath);
                timedStimulusListener.postLoadTimerFired();
            }
        };
        timer.schedule(1000);
    }

    protected void imageCapturedFailed(String stimulusIdString, String message) {
//        throw new UnsupportedOperationException(); // todo: add error display
    }

    private native void captureImageUI(String userIdString, String stimulusIdString) /*-{
        var sdCardImageCapture = this;
        console.log("captureImageUI: " + userIdString + " : " + stimulusIdString);
        if($wnd.navigator.device.capture){
            $wnd.AndroidFullScreen.showSystemUI();
            $wnd.navigator.device.capture.captureImage(function (mediaFiles) {
                console.log("captureImageOk: " + mediaFiles.length);
                sdCardImageCapture.@nl.mpi.tg.eg.experiment.client.service.SdCardImageCapture::imageCaptured(Ljava/lang/String;Ljava/lang/String;)(stimulusIdString, mediaFiles[0].fullPath);
//                $wnd.navigator.camera.cleanup(function () {
//                    console.log("Camera cleanup success.")
//                }, function (message) {
//                    console.log('Failed cleanup because: ' + message);
//                });
            }, function (error) {
                console.log("captureImageError: " + error.code);
                sdCardImageCapture.@nl.mpi.tg.eg.experiment.client.service.SdCardImageCapture::imageCapturedFailed(Ljava/lang/String;Ljava/lang/String;)(stimulusIdString, "Error:" + error.code);
//                $wnd.navigator.camera.cleanup(function () {
//                    console.log("Camera cleanup success.")
//                }, function (message) {
//                    console.log('Failed cleanup because: ' + message);
//                });
            }, { limit: 1 });
        } else {
          //  sdCardImageCapture.@nl.mpi.tg.eg.experiment.client.service.SdCardImageCapture::imageCapturedFailed(Ljava/lang/String;Ljava/lang/String;)(stimulusIdString, null);
            console.log("camera not defined");
        }
     }-*/;
}
