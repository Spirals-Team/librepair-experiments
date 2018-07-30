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
package nl.mpi.tg.eg.frinex;

import android.Manifest;
import android.os.Environment;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * @since Dec 9, 2015 4:41:18 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class FieldKitRecorder extends CordovaPlugin {

    private AudioRecorder audioRecorder = new WavRecorder();
    String externalStoragePath = Environment.getExternalStorageDirectory().getPath();
    private static final String AUDIO_RECORDER_FOLDER = "MPI_Recorder";
//  private   String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    private CsvWriter csvWriter = null;
    private String currentRecoringDirectory = null;
    final static int PAUSE_TIER = 0;
    private String startPauseSystemTime = null;
    private CallbackContext callbackContextTemp;

    @Override
    public boolean execute(final String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals("requestRecorderPermissions")) {
            System.out.println("action: requestRecorderPermissions");
            if (!cordova.hasPermission(Manifest.permission.RECORD_AUDIO)
                    || !cordova.hasPermission(Manifest.permission.CAMERA)
                    //                    || !cordova.hasPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS)
                    //                    || !cordova.hasPermission(Manifest.permission.MEDIA_CONTENT_CONTROL) // MODIFY_AUDIO_SETTINGS MEDIA_CONTENT_CONTROL?
                    || !cordova.hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) // MODIFY_AUDIO_SETTINGS MEDIA_CONTENT_CONTROL?
                    || !cordova.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                String[] permissions = {
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    //                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    //                Manifest.permission.ACCESS_NETWORK_STATE,
                    //                Manifest.permission.MEDIA_CONTENT_CONTROL,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                callbackContextTemp = callbackContext;
                cordova.requestPermissions(this, 0, permissions);
                return true;
            } else {
                callbackContext.success();
                return true;
            }
        }
        if (action.equals("requestFilePermissions")) {
            System.out.println("action: requestFilePermissions");
            if (!cordova.hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) // MODIFY_AUDIO_SETTINGS MEDIA_CONTENT_CONTROL?
                    || !cordova.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                String[] permissions = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                callbackContextTemp = callbackContext;
                cordova.requestPermissions(this, 0, permissions);
                return true;
            } else {
                callbackContext.success();
                return true;
            }
        }
        if (action.equals("record")) {
            System.out.println("action: record");
            if (//!cordova.hasPermission(Manifest.permission.RECORD_AUDIO)||
                    !cordova.hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    || !cordova.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // only if permissions are available should we continue at this point
                callbackContext.error("Permissions not available");
                return true;
            }
            final String userId = args.getString(0);
            final String stimulusSet = args.getString(1);
            final String stimulusId = args.getString(2);

            System.out.println("record: " + userId);
            System.out.println("record: " + stimulusSet);
            System.out.println("record: " + stimulusId);
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
//                        Date date = new Date();
                        //SimpleDateFormat dateFormat = new SimpleDateFormat("yy_MM_dd");
//                        String dirName = "MPI_Recorder_" + dateFormat.format(date);
                        final File outputDirectory = new File(externalStoragePath, AUDIO_RECORDER_FOLDER
                                //                                + File.separator + dirName
                                + File.separator + userId
                                + File.separator + stimulusSet
                                + ((stimulusId != null && !stimulusId.isEmpty()) ? File.separator + stimulusId + File.separator : ""));
                        if (!audioRecorder.isRecording() || currentRecoringDirectory == null || !currentRecoringDirectory.equals(outputDirectory.getAbsolutePath())) {
                            if (csvWriter != null) {
                                csvWriter.writeCsvFile(FieldKitRecorder.this.cordova.getActivity().getApplicationContext());
                                csvWriter = null;
                            }
                            currentRecoringDirectory = outputDirectory.getAbsolutePath();
                            final String baseName = audioRecorder.startRecording(outputDirectory, FieldKitRecorder.this.cordova.getActivity().getApplicationContext());
                            csvWriter = new CsvWriter(outputDirectory, baseName);
                            callbackContext.success();
                        }
                    } catch (final IOException e) {
                        System.out.println("IOException: " + e.getMessage());
                        callbackContext.error(e.getMessage());
                    }
                }
            });
            return true;
        }
        if (action.equals("stop")) {
            System.out.println("action: stop");
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (csvWriter != null) {
                            csvWriter.writeCsvFile(FieldKitRecorder.this.cordova.getActivity().getApplicationContext());
                            csvWriter = null;
                        }
                        audioRecorder.stopRecording();
                        callbackContext.success();
                    } catch (final IOException e) {
                        System.out.println("IOException: " + e.getMessage());
                        callbackContext.error(e.getMessage());
                    }
                }
            });
            return true;
        }
        if (action.equals("startTag")) {
            System.out.println("action: startTag");
            final String tier = args.getString(0);
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    if (csvWriter != null) {
                        csvWriter.startTag(Integer.parseInt(tier), audioRecorder.getTime());
                    } else {
                        callbackContext.error("not recording");
                    }
                }
            });
            return true;
        }
        if (action.equals("endTag")) {
            System.out.println("action: endTag");
            final String tier = args.getString(0);
            final String stimulusId = args.getString(1);
            final String stimulusCode = args.getString(2);
            final String tagString = args.getString(3);
            System.out.println("endTag: " + tier);
            System.out.println("endTag: " + stimulusId);
            System.out.println("endTag: " + stimulusCode);
            System.out.println("endTag: " + tagString);
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    if (csvWriter != null) {
                        csvWriter.endTag(Integer.parseInt(tier), audioRecorder.getTime(), stimulusId, stimulusCode, tagString);
                    } else {
                        callbackContext.error("not recording");
                    }
                }
            });
            return true;
        }
        if (action.equals("getTime")) {
//            System.out.println("action: getTime");
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    if (audioRecorder != null && audioRecorder.isRecording()) {
                        callbackContext.success(CsvWriter.makeShortTimeString(audioRecorder.getTime()));
                    } else {
                        callbackContext.error("not recording");
                    }
                }
            });
            return true;
        }
        if (action.equals("isRecording")) {
//            System.out.println("action: isRecording");
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    if (audioRecorder != null && audioRecorder.isRecording()) {
                        callbackContext.success();
                    } else {
                        callbackContext.error("not recording");
                    }
                }
            });
            return true;
        }
        if (action.equals("writeStimuliData")) {
            final String userId = args.getString(0);
            final String stimulusId = args.getString(1);
            final String stimuliData = args.getString(2);
            System.out.println("userId: " + userId);
            System.out.println("stimulusId: " + stimulusId);
            System.out.println("stimuliData: " + stimuliData);
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        final File outputDirectory = new File(externalStoragePath, AUDIO_RECORDER_FOLDER
                                + File.separator + userId + File.separator);
                        final StimuliJsonWriter stimuliJsonWriter = new StimuliJsonWriter(outputDirectory);
                        if (stimuliJsonWriter.writeJsonFile(FieldKitRecorder.this.cordova.getActivity().getApplicationContext(), stimulusId, stimuliData)) {
                            callbackContext.success();
                        } else {
                            callbackContext.error("stimulid data not written");
                        }
                    } catch (final IOException e) {
                        System.out.println("IOException, stimulid data not written: " + e.getMessage());
                    }
                }
            });
            return true;
        }
        if (action.equals("writeCsvLine")) {
            final String userId = args.getString(0);
            final String screenName = args.getString(1);
            final int dataChannel = args.getInt(2);
            final String eventTag = args.getString(3);
            final String tagValue1 = args.getString(4);
            final String tagValue2 = args.getString(5);
            final int eventMs = args.getInt(6);
            System.out.println("userId: " + userId);
            System.out.println("screenName: " + screenName);
            System.out.println("dataChannel: " + dataChannel);
            System.out.println("eventTag: " + eventTag);
            System.out.println("tagValue1: " + tagValue1);
            System.out.println("tagValue2: " + tagValue2);
            System.out.println("eventMs: " + eventMs);
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        final File outputDirectory = new File(externalStoragePath, AUDIO_RECORDER_FOLDER
                                + File.separator + userId + File.separator);
                        final StimuliCsvWriter stimuliCsvWriter = new StimuliScvWriter(outputDirectory);
                        if (stimuliCsvWriter.writeJsonFile(FieldKitRecorder.this.cordova.getActivity().getApplicationContext(), userId, screenName, dataChannel, eventTag, tagValue1, tagValue2, eventMs)) {
                            callbackContext.success();
                        } else {
                            callbackContext.error("stimulid data not written");
                        }
                    } catch (final IOException e) {
                        System.out.println("IOException, stimulid data not written: " + e.getMessage());
                    }
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        if (csvWriter != null) {
            try {
                csvWriter.writeCsvFile(this.cordova.getActivity().getApplicationContext());
                csvWriter = null;
            } catch (final IOException e) {
                System.out.println("IOException closing csvWriter: " + e.getMessage());
            }
        }
        audioRecorder.terminateRecorder();
    }

    private String getFormattedSystemTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onPause(boolean multitasking) {
        if (csvWriter != null) {
            try {
                // the recording is paused when focus is lost so we dont explicitly pause here
                csvWriter.startTag(PAUSE_TIER, audioRecorder.getTime());
                startPauseSystemTime = getFormattedSystemTime();
                csvWriter.writeCsvFile(this.cordova.getActivity().getApplicationContext());
            } catch (final IOException e) {
                System.out.println("IOException from csvWriter: " + e.getMessage());
            }
        }
        audioRecorder.pauseRecorder();
    }

    @Override
    public void onResume(boolean multitasking) {
        audioRecorder.resumeRecorder();
        if (csvWriter != null) {
            csvWriter.endTag(PAUSE_TIER, audioRecorder.getTime(), "ResumedAfterPause", startPauseSystemTime, getFormattedSystemTime());
        }
        startPauseSystemTime = null;
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException {
        if (!cordova.hasPermission(Manifest.permission.RECORD_AUDIO)
                || !cordova.hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                || !cordova.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            callbackContextTemp.error("permissions not granted");
        } else {
            audioRecorder.terminateRecorder();
            audioRecorder = new WavRecorder();
            callbackContextTemp.success();
        }
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        View decorView = getWindow().getDecorView();
//        if (hasFocus) {
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }
}
