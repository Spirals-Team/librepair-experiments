/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics
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
package nl.mpi.tg.eg.experimentdesigner.util;

import nl.mpi.tg.eg.experimentdesigner.controller.WizardController;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.StimuliSubAction;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAboutScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAnimatedStimuliScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardEditUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardExistingUserCheckScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardMenuScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardSelectUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardStimulusScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardTextScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardWelcomeScreen;

/**
 * @since July 12, 2016 13:33:54 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class RosselFieldKit {

    private final WizardController wizardController = new WizardController();

    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName("RosselFieldKit");
        wizardData.setShowMenuBar(true);
        wizardData.setObfuscateScreenNames(false);

        final WizardExistingUserCheckScreen welcomeMenuPresenter = new WizardExistingUserCheckScreen("Start", "New interview", "Resume interview", "Begin a new interview with a new participant", "Resume an interview with an existing participant");
        final WizardTextScreen instructionsPresenter = new WizardTextScreen("Instructions",
                "The stimuli used by this app and the recordings created by this app are stored in MPI_STIMULI and MPI_Recorder respectively. "
                + "Depending on the version of android and the file browser that you are using these will show up as:<br/>"
                + "\"/MPI_Recorder/\"<br/>"
                + "\"/storage/emulated/0/MPI_Recorder/\"<br/>"
                + "\"/data/media/0/MPI_Recorder/\"<br/>"
                + "<br/>"
                + "When you connect the mobile device to a computer via USB, you may need to enable USB file browsing. This can often be done by draging down from the top of the screen to access the option directly or via the settings menu.<br/>"
                + "<br/>"
                + "Task 1 and Task 2 each have different background images to be located as follows:<br/>"
                + "/MPI_STIMULI/background1.png<br/>"
                + "/MPI_STIMULI/background2.png<br/>"
                + "<br/>"
                + "The stimuli for Task 1 and Task 2 are to be placed in the following directories:<br/>"
                + "/MPI_STIMULI/Task_1<br/>"
                + "/MPI_STIMULI/Task_2<br/>"
                + "<br/>"
                + "Each image for task 1 and task 2 should have an mp3 file with the same name. "
                + "Each image group for task 1 and task 2 should be prefixed by a string common to that group followed by an underscore:<br/>"
                + "\"1_pig.png\" \"1_pig.mp3\"<br/>"
                + "\"2_bat.png\" \"2_bat.mp3\"<br/>"
                + "\"2_fish.png\" \"2_fish.mp3\"<br/>"
                + "\"1_rat.png\" \"1_rat.mp3\"<br/>"
                + "<br/>"
                + "In more recent versions of this app OGG audio file format can also be used. If you provide both mp3 and ogg the system will pick its prefered type.<br/>"
                + "\"2_bat.png\" \"2_bat.ogg\" \"2_bat.mp3\"<br/>"
                + "<br/>"
                + "Task 2 has an additional audio file for each stimilus. This file has the postfix _question as follows:<br/>"
                + "\"1_pig.png\" \"1_pig.mp3\" \"1_pig_question.mp3\"<br/>"
                + "<br/>"
                + "If you want to have multiple stimuli sets for Task 1 and Task 2, you can create subdirectories containing stimuli sets for each task. These subdirectories will show up in the task screen as a menu listing of the directory names:<br/>"
                + "/MPI_STIMULI/Task_1/set_1<br/>"
                + "/MPI_STIMULI/Task_1/set_2<br/>"
                + "/MPI_STIMULI/Task_2/bats<br/>"
                + "/MPI_STIMULI/Task_2/possums<br/>"
                + "<br/>"
                + "<br/>"
                + "The MPI Stimuli tasks can also be used within this application. This can be done on the FieldKit screen and by placing the stimuli into directories within: /MPI_STIMULI/FieldKit/<br/>"
                + "eg:<br/>"
                + "/MPI_STIMULI/FieldKit/bodies<br/>"
                + "/MPI_STIMULI/FieldKit/bowped<br/>"
                + "<br/>"
                + "Do not put sub directories within directories that also contain stimuli. Because if a directory is found the stimuli next to it will not be used.<br/>"
                + "eg:<br/>"
                + "/MPI_STIMULI/FieldKit/bowped/autoplay (will break the stimuli display)<br/>"
                + "<br/>"
                + "You can keep the stimuli for the original FieldKit in their respective directories. These will not be seen by this application:<br/>"
                + "/MPI_STIMULI/bodies<br/>"
                + "/MPI_STIMULI/bowped/<br/>"
                + "/MPI_STIMULI/bowped/autoplay<br/>"
                + "<br/>"
                + "Recorded audio will be stored on the sdcard in the MPI_Recorder directory:<br/>"
                + "/data/media/0/MPI_Recorder/155feab481d-87fb-9eb9-46c9-3e40/SDCardPictureTask/2016-07-18-175348.wav'.<br/>"
                + "From version 0.1.877 onwards the participant ID will be used as a directory name:<br/>"
                + "/data/media/0/MPI_Recorder/-participant-id-/Task_1/2016-07-18-175348.wav'.<br/>"
                + "<br/>"
                + "Importing recordings into ELAN can be done via the CSV import functionality of ELAN. "
                + "The CSV file generated by this app provides tha start time and end time of each annotation. "
                + "There are two pairs of columns for the start and end times, each with a different date time format and you can select which you prefer. "
                + "The remaining columns the suggested tier number for ELAN, the stimulus ID / code and the tag indicating the event or participant response:<br/>"
                + "<table>"
                + "<tr><td>BeginTime,</td><td>EndTime,</td><td>BeginTime2,</td><td>EndTime2,</td><td>Tier,</td><td>StimulusID,</td><td>StimulusCode,</td><td>Tag</tr></td>"
                + "<tr><td>00:00:00.000,</td><td>00:00:01.202,</td><td>0.000,</td><td>1.202,</td><td>4,</td><td>'1_rat',</td><td>'1_rat',</td><td>'incorrect image clicked'</tr></td>"
                + "<tr><td>00:00:04.570,</td><td>00:00:04.570,</td><td>4.570,</td><td>4.570,</td><td>1,</td><td>'1_rat',</td><td>'1_rat',</td><td>'task 2 animated'</tr></td>"
                + "<tr><td>00:00:04.570,</td><td>00:00:06.735,</td><td>4.570,</td><td>6.735,</td><td>4,</td><td>'2_bat',</td><td>'2_bat',</td><td>'incorrect image clicked'</tr></td>"
                + "<tr><td>00:00:08.659,</td><td>00:00:08.659,</td><td>8.659,</td><td>8.659,</td><td>1,</td><td>'2_bat',</td><td>'2_bat',</td><td>'task 2 animated'</tr></td>"
                + "<tr><td>00:00:08.659,</td><td>00:00:09.862,</td><td>8.659,</td><td>9.862,</td><td>4,</td><td>'1_pig',</td><td>'1_pig',</td><td>'correct image clicked'</tr></td>"
                + "<tr><td>00:00:11.305,</td><td>00:00:11.305,</td><td>11.305,</td><td>11.305,</td><td>1,</td><td>'1_pig',</td><td>'1_pig',</td><td>'task 2 animated'</tr></td>"
                + "<tr><td>00:00:11.305,</td><td>00:00:12.267,</td><td>11.305,</td><td>12.267,</td><td>4,</td><td>'2_fish',</td><td>'2_fish',</td><td>'correct image clicked'</tr></td>"
                + "<tr><td>00:00:13.470,</td><td>00:00:13.470,</td><td>13.470,</td><td>13.470,</td><td>1,</td><td>'2_fish',</td><td>'2_fish',</td><td>'task 2 animated'</tr></td>"
                + "</table>"
                + "<br/>"
                + "Hidden buttons are used on the Task 1 and Task 2 screens and are as follows:<br/>"
                + "Top right: replay audio (when available).<br/>"
                + "Bottom right: next stimulus.<br/>"
                + "In the multiple image sections each image can be clicked.<br/>"
                + "<br/>"
                + "The full directory listing that the application has been tested with is as follows:<br/>"
                + "/MPI_STIMULI/Task_1/1_pig.mp3<br/>"
                + "/MPI_STIMULI/Task_1/1_pig.png<br/>"
                + "/MPI_STIMULI/Task_1/1_rat.mp3<br/>"
                + "/MPI_STIMULI/Task_1/1_rat.png<br/>"
                + "/MPI_STIMULI/Task_1/2_bat.mp3<br/>"
                + "/MPI_STIMULI/Task_1/2_bat.png<br/>"
                + "/MPI_STIMULI/Task_1/2_fish.mp3<br/>"
                + "/MPI_STIMULI/Task_1/2_fish.png<br/>"
                + "/MPI_STIMULI/Task_2/1_pig.mp3<br/>"
                + "/MPI_STIMULI/Task_2/1_pig.png<br/>"
                + "/MPI_STIMULI/Task_2/1_pig_question.mp3<br/>"
                + "/MPI_STIMULI/Task_2/1_rat.mp3<br/>"
                + "/MPI_STIMULI/Task_2/1_rat.png<br/>"
                + "/MPI_STIMULI/Task_2/1_rat_question.mp3<br/>"
                + "/MPI_STIMULI/Task_2/2_bat.mp3<br/>"
                + "/MPI_STIMULI/Task_2/2_bat.png<br/>"
                + "/MPI_STIMULI/Task_2/2_bat_question.mp3<br/>"
                + "/MPI_STIMULI/Task_2/2_fish.mp3<br/>"
                + "/MPI_STIMULI/Task_2/2_fish.png<br/>"
                + "/MPI_STIMULI/Task_2/2_fish_question.mp3",
                "Begin");
        final WizardWelcomeScreen welcomePresenter = new WizardWelcomeScreen("Rossel Island FieldKit", "Welcome", "Instructions", "Begin", welcomeMenuPresenter, instructionsPresenter);
        final WizardMenuScreen menuScreen = new WizardMenuScreen("Menu", "Menu", "Menu");
        menuScreen.setBackWizardScreen(welcomePresenter);
//        String[] images = new String[]{
//            "1_pig.png",
//            "2_bat.png",
//            "2_fish.png",
//            "1_rat.png", //            "ffmpeg -i 1_pig.wav 1_pig.mp3",
//        //            "ffmpeg -i 2_bat.wav 2_bat.mp3",
//        //            "ffmpeg -i 2_fish.wav 2_fish.mp3",
//        //            "ffmpeg -i 1_rat.wav 1_rat.mp3"
//        };
//        final WizardAnimatedStimuliScreen task1Screen = new WizardAnimatedStimuliScreen("Task 1 Demo", images, false, 1000, true, "Next", "background1.png", false);
//        final WizardAnimatedStimuliScreen task2Screen = new WizardAnimatedStimuliScreen("Task 2 Demo", images, false, 1000, true, "Next", "background2.png", true);
//        final WizardAnimatedStimuliScreen pictureTaskScreen = new WizardAnimatedStimuliScreen("PictureTask", images, false, 1000, true, "Next", "background.png", false);
        final WizardAnimatedStimuliScreen task1ScreenSD = new WizardAnimatedStimuliScreen("Task 1", new String[]{"Task1"}, true, 1000, true, "Next", "file:///storage/emulated/0/MPI_STIMULI/background1.png", false);
        final WizardAnimatedStimuliScreen task2ScreenSD = new WizardAnimatedStimuliScreen("Task 2", new String[]{"Task2"}, true, 1000, true, "Next", "file:///storage/emulated/0/MPI_STIMULI/background2.png", true);
        final WizardSelectUserScreen wizardSelectUserScreen = new WizardSelectUserScreen("Select Participant");
        wizardSelectUserScreen.setBackWizardScreen(welcomePresenter);
        wizardSelectUserScreen.setNextWizardScreen(menuScreen);
        final WizardEditUserScreen editUserPresenter = new WizardEditUserScreen("Information about the participant", "Edit User", null, "Save and continue", null, null, null, false, false, "Could not contact the server, please check your internet connection and try again.");
        editUserPresenter.setCustomFields(new String[]{"workerId:Participant ID:.'{'3,'}':Please enter at least three letters.",
            "connectionString:connection:.'{'3,'}':Please enter at least three letters."});
        final WizardAboutScreen debugScreenPresenter = new WizardAboutScreen("About this app", true);

        final WizardStimulusScreen wizardStimulusScreenSequential = new WizardStimulusScreen();
        wizardStimulusScreenSequential.setScreenTitle("FieldKit Sequential");
        wizardStimulusScreenSequential.setMenuLabel("FieldKit Sequential");
//        wizardStimulusScreenSequential.setScreenLabel("FieldKit Sequential");
        wizardStimulusScreenSequential.setScreenTag("FieldKitSequential");
//        experiment, welcomePresenter, "cutbreak", welcomePresenter, new String[]{"cutbreak"}, grammaticalityValuesArray, true, 1000, false, "end_of_stimuli", 15, obfuscateScreenNames
        wizardStimulusScreenSequential.setStimulusTagArray(new String[]{"FieldKit"});
//        wizardStimulusScreenSequential.setStimulusArray(new String[]{"FieldKit"});
        wizardStimulusScreenSequential.setFeatureValuesArray(new StimuliSubAction[]{new StimuliSubAction("80", "the informant talks about the image", "next")});
        wizardStimulusScreenSequential.setMaxStimuli(1000);
        wizardStimulusScreenSequential.setMaxStimuliPerTag(1000);
        wizardStimulusScreenSequential.setRandomiseStimuli(false);
        wizardStimulusScreenSequential.setFilePerStimulus(false);
        wizardStimulusScreenSequential.setEnd_of_stimuli("Complete");

        final WizardStimulusScreen wizardStimulusScreenRandom = new WizardStimulusScreen();
        wizardStimulusScreenRandom.setScreenTitle("FieldKit Random");
        wizardStimulusScreenRandom.setMenuLabel("FieldKit Random");
//        wizardStimulusScreenRandom.setScreenLabel("FieldKit Random");
        wizardStimulusScreenRandom.setScreenTag("FieldKitRandom");
//        experiment, welcomePresenter, "cutbreak", welcomePresenter, new String[]{"cutbreak"}, grammaticalityValuesArray, true, 1000, false, "end_of_stimuli", 15, obfuscateScreenNames
        wizardStimulusScreenRandom.setStimulusTagArray(new String[]{"FieldKit"});
//        wizardStimulusScreenRandom.setStimulusArray(new String[]{"FieldKit"});
        wizardStimulusScreenRandom.setFeatureValuesArray(new StimuliSubAction[]{new StimuliSubAction("80", "the informant talks about the image", "next")});
        wizardStimulusScreenRandom.setMaxStimuli(1000);
        wizardStimulusScreenRandom.setMaxStimuliPerTag(1000);
        wizardStimulusScreenRandom.setRandomiseStimuli(true);
        wizardStimulusScreenRandom.setFilePerStimulus(false);
        wizardStimulusScreenRandom.setEnd_of_stimuli("Complete");

        editUserPresenter.setBackWizardScreen(welcomePresenter);
//        task1Screen.setBackWizardScreen(menuScreen);
//        task2Screen.setBackWizardScreen(menuScreen);
//        task1Screen.setNextWizardScreen(menuScreen);
//        task2Screen.setNextWizardScreen(menuScreen);
        debugScreenPresenter.setBackWizardScreen(menuScreen);
        task1ScreenSD.setBackWizardScreen(menuScreen);
        task1ScreenSD.setNextWizardScreen(menuScreen);
        task2ScreenSD.setBackWizardScreen(menuScreen);
        task2ScreenSD.setNextWizardScreen(menuScreen);
        editUserPresenter.setNextWizardScreen(menuScreen);
        welcomeMenuPresenter.setNextWizardScreen(editUserPresenter);
        welcomeMenuPresenter.setBackWizardScreen(instructionsPresenter);
        instructionsPresenter.setBackWizardScreen(welcomePresenter);
        instructionsPresenter.setNextWizardScreen(welcomeMenuPresenter);
        wizardStimulusScreenSequential.setBackWizardScreen(menuScreen);
        wizardStimulusScreenSequential.setEndOfStimulisWizardScreen(menuScreen);
        wizardStimulusScreenRandom.setBackWizardScreen(menuScreen);
        wizardStimulusScreenRandom.setEndOfStimulisWizardScreen(menuScreen);
        menuScreen.addTargetScreen(task1ScreenSD);
        menuScreen.addTargetScreen(task2ScreenSD);
//        menuScreen.addTargetScreen(task1Screen);
//        menuScreen.addTargetScreen(task2Screen);
        menuScreen.addTargetScreen(wizardStimulusScreenSequential);
        menuScreen.addTargetScreen(wizardStimulusScreenRandom);
        menuScreen.addTargetScreen(debugScreenPresenter);
        menuScreen.addTargetScreen(instructionsPresenter);
        wizardData.addScreen(menuScreen);
        wizardData.addScreen(welcomePresenter);
        wizardData.addScreen(welcomeMenuPresenter);
        wizardData.addScreen(instructionsPresenter);
        wizardData.addScreen(editUserPresenter);
        wizardData.addScreen(wizardSelectUserScreen);
        wizardData.addScreen(wizardStimulusScreenSequential);
        wizardData.addScreen(wizardStimulusScreenRandom);
//        wizardData.addScreen(task1Screen);
//        wizardData.addScreen(task2Screen);
        wizardData.addScreen(task1ScreenSD);
        wizardData.addScreen(task2ScreenSD);
        wizardData.addScreen(debugScreenPresenter);
        return wizardData;
    }

    public Experiment getExperiment() {
        return wizardController.getExperiment(getWizardData());
    }
    // @todo:
    // training phase (can record)
    // issue with doubletap skipping the next stimulus (look into tapping solution but also consider swipe)
    // flashing photo (web view maybe) (perhaps images too large)
    // skip and next buttons should not be in the same location
    // blootooth presentation remote button
    // already has a polar heart reate monitor (maybe add to field kit plugin)
    // video stimuli (does this already work or not?)
    // would be nice to have the tablet mic into one channel of the wav and a bluetooth headset into a second channel in the wav
    // stimuli images could be bigger (maximum possible)

}
