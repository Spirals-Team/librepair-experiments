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
import java.util.HashMap;
import java.util.List;
import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;
import nl.mpi.tg.eg.experiment.client.model.SdCardStimulus;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;

/**
 * @since Jan 8, 2016 11:31:32 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class SdCardStimuli {

    static public final String MPI_STIMULI = "MPI_STIMULI";
    private final List<Stimulus> stimulusArray;
    private final HashMap<String, SdCardStimulus> stimulusHashMap = new HashMap<>();
    private final List<String[]> directoryList;
    private final TimedStimulusListener simulusLoadedListener;
    private final TimedStimulusListener simulusErrorListener;
    private final String excludeRegex;

    public SdCardStimuli(List<Stimulus> stimulusArray, final List<String[]> directoryList, final String excludeRegex, TimedStimulusListener simulusLoadedListener, TimedStimulusListener simulusErrorListener) {
        this.stimulusArray = stimulusArray;
        this.simulusLoadedListener = simulusLoadedListener;
        this.simulusErrorListener = simulusErrorListener;
        this.directoryList = directoryList;
        this.excludeRegex = excludeRegex;
    }

    public final void fillStimulusList(final String directoryTag) {
        final String directoryTag1 = (directoryTag.contains(MPI_STIMULI)) ? directoryTag.split(MPI_STIMULI, 2)[1] : directoryTag;
        scanSdCard(MPI_STIMULI, directoryTag1);
//        testInsertStimulus();
//        nonScan(directoryTag);
//        for (GeneratedStimulus.Tag currentTag : tagArray) {
//            scanSdCard(MPI_STIMULI, currentTag.name().replaceFirst("^tag_", ""));
//        }
        // todo: remove these entries
//        scanSdCard(MPI_STIMULI, "bodies");
//        scanSdCard(MPI_STIMULI, "grammar");
//        scanSdCard(MPI_STIMULI, "reciprocal");
//        scanSdCard(MPI_STIMULI, "cutbreak");
    }

    public void insertDirectory(String directoryPath, String directoryName) {
        directoryList.add(new String[]{directoryPath, directoryName});
    }

    private static final String BASE_FILE_REGEX = "\\.[a-zA-Z34]+$";

    public void insertStimulus(String stimulusPath, String fileName) {
        if (excludeRegex == null || !fileName.matches(excludeRegex)) {
            // GWT.log("stimulusPath: " + stimulusPath);
            // GWT.log("fileName: " + fileName);
//        final String stimulusId = stimulusPath.substring(stimulusPath.indexOf(MPI_STIMULI) + MPI_STIMULI.length() + 1);
//        final String stimulusId = stimulusPath.replaceAll("^.*" + MPI_STIMULI + "/", "").replaceAll("\\....$", "");
            final String stimulusId = stimulusPath.substring(stimulusPath.lastIndexOf("/") + 1).replaceAll("\\....$", "");
            final String suffix = stimulusPath.toLowerCase().substring(stimulusPath.length() - 4, stimulusPath.length());
            final String filePart = stimulusPath.substring(0, stimulusPath.length() - 4);
            // GWT.log("suffix: " + suffix);
            final String stimuliLabel = null;
            final String stimuliCode = filePart;
            final int pause = 0;
//        final boolean isLabel = ".txt".equals(suffix);
            final boolean isMp3 = ".mp3".equals(suffix);
            final boolean isMp4 = ".mp4".equals(suffix);
            final boolean isOgg = ".ogg".equals(suffix);
            final boolean isImage = ".jpg".equals(suffix) || ".png".equals(suffix);
            // todo: insert a relevant tag and address enum limitiation
            final SdCardStimulus existingSdCardStimulus = stimulusHashMap.get(stimulusId);
            if (existingSdCardStimulus != null) {
                if (isMp3) {
                    existingSdCardStimulus.addAudio();
                }
                if (isMp4) {
                    existingSdCardStimulus.addVideo();
                }
                if (isOgg) {
                    existingSdCardStimulus.addVideo();
                }
                if (isImage) {
                    existingSdCardStimulus.addImage(stimulusPath);
                }
            } else {
                final SdCardStimulus sdCardStimulus = new SdCardStimulus(stimulusId,
                        stimulusPath.replaceFirst(BASE_FILE_REGEX, ""),
                        //                /* tagArray */ new Stimulus.Tag[0]/* we dont set this with the tag array because each stimulus would only have one out of many applicable from the array */,
                        stimuliLabel, stimuliCode, pause, isMp3, (isMp4 || isOgg), (isImage) ? stimulusPath : null);
                stimulusHashMap.put(stimulusId, sdCardStimulus);
                stimulusArray.add(sdCardStimulus);
            }
        }
    }

    public void errorAction(String errorCode, String errorMessage) {
        // GWT.log("errorAction: " + errorCode + " " + errorMessage);
        simulusErrorListener.postLoadTimerFired();
    }

    public void loadingCompleteAction() {
        simulusLoadedListener.postLoadTimerFired();
    }

    private void nonScan(final String directoryTag) {
        if (directoryTag == null || !directoryTag.equals("/storage/emulated/")) {
            insertDirectory("/storage/emulated/", "/storage/emulated/");
            insertDirectory("/storage/emulated/jena/", "/static/jena/");
        } else {
//            String[] testFiles = new String[]{
//                "Canoe.jpg",
//                "Cocoa.jpg",
//                "Flying%20fox.jpg"
//            };
//            for (String item : testFiles) {
//                insertStimulus("file:///Users/petwit/Documents/ExperimentTemplate/gwt-cordova/src/main/static/jena/Pictures/", item);
//            }
//        insertDirectory("./path/path", "a directory name");
//        insertDirectory("./path2/path2", "a directory 2 name");
            String[][] testStiuli = new String[][]{
                new String[]{"file:///storage/emulated/0/MPI_STIMULI/Task_2/1_pig_question.mp3", "1_pig_question.mp3"
                }, new String[]{"file:///storage/emulated/0/MPI_STIMULI/Task_2/1_pig.mp3", "1_pig.mp3"
                }, new String[]{"file:///storage/emulated/0/MPI_STIMULI/Task_2/1_pig.png", "1_pig.png"
                }, new String[]{"file:///storage/emulated/0/MPI_STIMULI/Task_2/1_rat_question.mp3", "1_rat_question.mp3"
                }, new String[]{"file:///storage/emulated/0/MPI_STIMULI/Task_2/1_rat.mp3", "1_rat.mp3"
                }, new String[]{"file:///storage/emulated/0/MPI_STIMULI/Task_2/1_rat.png", "1_rat.png"
                }, new String[]{"file:///storage/emulated/0/MPI_STIMULI/Task_2/2_bat_question.mp3", "2_bat_question.mp3"
                }, new String[]{"file:///storage/emulated/0/MPI_STIMULI/Task_2/2_bat.mp3", "2_bat.mp3"
                }, new String[]{"file:///storage/emulated/0/MPI_STIMULI/Task_2/2_bat.png", "2_bat.png"
                }, new String[]{"file:///storage/emulated/0/MPI_STIMULI/Task_2/2_fish_question.mp3", "2_fish_question.mp3"
                }, new String[]{"file:///storage/emulated/0/MPI_STIMULI/Task_2/2_fish.mp3", "2_fish.mp3"
                }, new String[]{"file:///storage/emulated/0/MPI_STIMULI/Task_2/2_fish.png", "2_fish.png"}

            //            "d1e263.aiff", "d1e378.mp3", "d1e521.jpg", "d1e674.ogg", "d1e831.aiff", "inge_grijp2.ogg", "sharon_flees3.ogg",
            //            "d1e263.jpg", "d1e378.mp4", "d1e521.mp3", "d1e679.aiff", "d1e831.jpg", "inge_grijp3.mp3", "sharon_flees4.mp3",
            //            "d1e263.mp3", "d1e378.ogg", "d1e521.mp4", "d1e679.jpg", "d1e831.mp3", "inge_grijp3.ogg", "sharon_flees4.ogg",
            //            "d1e263.mp4", "d1e383.aiff", "d1e521.ogg", "d1e679.mp3", "d1e831.mp4", "inge_grijp4.mp3", "sharon_flees5.mp3",
            //            "d1e263.ogg", "d1e383.jpg", "d1e526.aiff", "d1e679.mp4", "d1e831.ogg", "inge_grijp4.ogg"
            };
            for (String[] item : testStiuli) {
                insertStimulus(item[0].replace("file:///storage/emulated/0/", "/static/"), item[1]);
            }
        }
//        insertStimulus("file:///storage/emulated/0/MPI_STIMULI/bowped/10.jpg", "10.jpg");
        Timer timer = new Timer() {
            public void run() {
                loadingCompleteAction();
            }
        };
        timer.schedule(100);
    }

    protected native void scanSdCard(String stimuliDirectory, String cleanedTag) /*-{
        var sdCardStimuli = this;
        if($wnd.cordova === undefined) {
            sdCardStimuli.@nl.mpi.tg.eg.experiment.client.service.SdCardStimuli::errorAction(Ljava/lang/String;Ljava/lang/String;)(-1, "cordova not defined");
        } else {
            var potentialDirectories = [
                $wnd.cordova.file.dataDirectory,
                $wnd.cordova.file.externalRootDirectory,
                $wnd.cordova.file.externalDataDirectory
            ];
            var directoryIndex;
            for (directoryIndex = 0; directoryIndex < potentialDirectories.length; directoryIndex++) {
                if (potentialDirectories[directoryIndex] === null || potentialDirectories[directoryIndex].length === 0) {
                    continue;
                }
                console.log(potentialDirectories[directoryIndex] + stimuliDirectory + "/" + cleanedTag);
                console.log(typeof $wnd.resolveLocalFileSystemURL);
                $wnd.resolveLocalFileSystemURL(potentialDirectories[directoryIndex] + stimuliDirectory + "/" + cleanedTag, function (entry) {
                var dirReader = entry.createReader();
                dirReader.readEntries(
                    function (entries) {
                        console.log("entries.length: " + entries.length);
                        if(entries === undefined || entries.length == 0) {
    //                        console.log("readEntries returned nothing");
    //                        sdCardStimuli.@nl.mpi.tg.eg.experiment.client.service.SdCardStimuli::loadingCompleteAction()();
                        } else {
                            var currentIndex;
                            for (currentIndex = 0; currentIndex < entries.length; currentIndex++) {
                                console.log("currentEntry: " + entries[currentIndex].toURL());
                                console.log("currentEntry: " + entries[currentIndex].name);
                                if (entries[currentIndex].isDirectory === true) {
                                    console.log("isDirectory");                
                                    sdCardStimuli.@nl.mpi.tg.eg.experiment.client.service.SdCardStimuli::insertDirectory(Ljava/lang/String;Ljava/lang/String;)(entries[currentIndex].toURL(), entries[currentIndex].name);
        //                            readFileEntry(entries[currentIndex]);
                                } else {
                                    console.log(); 
                                    sdCardStimuli.@nl.mpi.tg.eg.experiment.client.service.SdCardStimuli::insertStimulus(Ljava/lang/String;Ljava/lang/String;)(entries[currentIndex].toURL(), entries[currentIndex].name);
                                }
                            }
                            console.log("readEntries complete");
                            sdCardStimuli.@nl.mpi.tg.eg.experiment.client.service.SdCardStimuli::loadingCompleteAction()();
                        }
                    },
                    function (error) {
                        console.log("readEntries error: " + error.code);
                        console.log("readEntries error: " + error.message);
                        sdCardStimuli.@nl.mpi.tg.eg.experiment.client.service.SdCardStimuli::errorAction(Ljava/lang/String;Ljava/lang/String;)(error.code, error.message);
                    }
                );
            }, function (error) {
                    console.log("resolveLocalFileSystemURL error: " + error.code);
                    console.log("resolveLocalFileSystemURL error: " + error.message);
//                    sdCardStimuli.@nl.mpi.tg.eg.experiment.client.service.SdCardStimuli::errorAction(Ljava/lang/String;Ljava/lang/String;)(error.code, error.message + "Plase make sure that the directory " + stimuliDirectory + "/" + cleanedTag + " exists and has stimuli files.");
                });
            }
        }
     }-*/;
}
