/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics
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
package nl.mpi.tg.eg.experimentdesigner.model.wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.Metadata;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;
import nl.mpi.tg.eg.experimentdesigner.model.RandomGrouping;
import nl.mpi.tg.eg.experimentdesigner.model.Stimulus;

/**
 * @since Mar 23, 2017 3:12:33 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardGridStimulusScreen extends AbstractWizardScreen {

    public WizardGridStimulusScreen() {
        super(WizardScreenEnum.WizardGridStimulusScreen, "GridStimulusScreen", "GridStimulusScreen", "GridStimulusScreen");
        setRandomiseStimuli(false);
        this.wizardScreenData.setStimuliCount(1);
//        setStimulusMsDelay(0);
        setFullScreenGrid(false);
        setCodeAudio(false);
        setRememberLastStimuli(true);
        setAudioAB("");
        setConsumedTagGroup(null);
        setSdCardStimuli(false);
        setShowCurtains(false);
        setIntroAudio(null);
        setCorrectAudio(null);
        setRewardImage(null);
        setIntroAudioDelay(0);
        setSelectedPause(0);
        this.wizardScreenData.setButtonLabelEventTag("");
        this.wizardScreenData.setCentreScreen(true);
    }

    public WizardGridStimulusScreen(final WizardUtilStimuliData stimuliData) {
        super(WizardScreenEnum.WizardGridStimulusScreen, stimuliData.getStimuliName(), stimuliData.getStimuliName(), stimuliData.getStimuliName());
        //        setStimulusMsDelay(0);
        setFullScreenGrid(false);
        setSdCardStimuli(false);
        setCodeAudio(false);
        setRememberLastStimuli(true);
        setShowCurtains(false);
        setConsumedTagGroup(null);
//        setIntroAudioDelay(0);
        final String[] options = stimuliData.getOptions();
        setIntroAudioDelay(2000);
        if (options != null) {
            final String[] copyOfOptions = Arrays.copyOf(options, 9);
            setBackgroundImage(copyOfOptions[0]);
            setBackgroundStyle(copyOfOptions[1]);
            setRewardImage(copyOfOptions[3]);
            setAudioAB((copyOfOptions[4] != null) ? copyOfOptions[4] : "");
            try {
                setSelectedPause(Integer.parseInt(copyOfOptions[5]));
            } catch (NumberFormatException exception) {
                setSelectedPause(0);
            }
            setCorrectAudio(copyOfOptions[6]);
            setIntroAudio(copyOfOptions[7]);
            setStimuliButtonArray(copyOfOptions[8]);
        } else {
            setBackgroundImage(null);
            setBackgroundStyle(null);
            setRewardImage(null);
            setAudioAB("");
            setSelectedPause(0);
            setCorrectAudio(null);
            setIntroAudio(null);
        }
        this.wizardScreenData.setButtonLabelEventTag("");
        this.wizardScreenData.setStimulusCodeFormat("");
        this.wizardScreenData.setStimuliCount(1000);
        setRandomiseStimuli(false);
        this.wizardScreenData.setCentreScreen(false);
        setStimuliSet(new String[][]{stimuliData.getStimuliArray()});
    }

    public WizardGridStimulusScreen(String screenName, boolean centreScreen, String[][] stimuliStringArray, String[] randomStimuliTags, int maxStimuli, final boolean randomiseStimuli, String stimulusCodeMatch, int stimulusDelay, int codeStimulusDelay, String codeFormat, String... tagNames) {
        super(WizardScreenEnum.WizardGridStimulusScreen, screenName, screenName, screenName);
        this.setScreenTitle(screenName);
        this.wizardScreenData.setStimuliRandomTags(randomStimuliTags);
        this.wizardScreenData.setStimulusCodeMatch(stimulusCodeMatch);
        this.wizardScreenData.setStimulusCodeMsDelay(0);
//        setStimulusMsDelay(0);
        setFullScreenGrid(false);
        setSdCardStimuli(false);
        setCodeAudio(false);
        setRememberLastStimuli(true);
        setShowCurtains(false);
        setAudioAB("");
        setConsumedTagGroup(null);
        setIntroAudio(null);
        setCorrectAudio(null);
        setRewardImage(null);
        setIntroAudioDelay(0);
        setSelectedPause(0);
        this.wizardScreenData.setStimulusCodeFormat(codeFormat);
        this.wizardScreenData.setStimuliCount(maxStimuli);
        setRandomiseStimuli(randomiseStimuli);
        this.wizardScreenData.setCentreScreen(centreScreen);
        setStimuliSet(stimuliStringArray, tagNames);
    }

    final public void setRandomiseStimuli(boolean randomiseStimuli) {
        this.wizardScreenData.setScreenBoolean(0, randomiseStimuli);
    }

    final public void setFullScreenGrid(boolean fullScreenGrid) {
        this.wizardScreenData.setScreenBoolean(2, fullScreenGrid);
    }

    final public void setCodeAudio(boolean stimulusCodeAudio) {
        this.wizardScreenData.setScreenBoolean(3, stimulusCodeAudio);
    }

    private boolean isRandomiseStimuli(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenBoolean(0);
    }

    private boolean isFullScreenGrid(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenBoolean(2);
    }

    private boolean isCodeAudio(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenBoolean(3);
    }

    private String getBackgroundImage(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(0);
    }

    final public void setBackgroundImage(String backgroundImage) {
        this.wizardScreenData.setScreenText(0, backgroundImage);
    }

    final public void setIntroAudio(String initialAudio) {
        this.wizardScreenData.setScreenText(2, initialAudio);
    }

    private String getIntroAudio(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(2);
    }

    final public void setCorrectAudio(String correctAudio) {
        this.wizardScreenData.setScreenText(3, correctAudio);
    }

    private String getCorrectAudio(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(3);
    }

    private String getRewardImage(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(4);
    }

    final public void setRewardImage(String rewardImage) {
        this.wizardScreenData.setScreenText(4, rewardImage);
    }

    private String getAudioAB(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(5);
    }

    final public void setAudioAB(String audioAB) {
        this.wizardScreenData.setScreenText(5, audioAB);
    }

    private String getConsumedTagGroup(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(6);
    }

    final public void setConsumedTagGroup(String consumedGroupName) {
        this.wizardScreenData.setScreenText(6, consumedGroupName);
    }

    private String getStimuliButtonArray(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(7);
    }

    final public void setStimuliButtonArray(String stimuliButtonArray) {
        this.wizardScreenData.setScreenText(7, stimuliButtonArray);
    }

    private String getBackgroundStyle(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(1);
    }

    final public void setIntroAudioDelay(int initialAudioDelay) {
        this.wizardScreenData.setScreenIntegers(0, initialAudioDelay);
    }

    private int getSelectedPause(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenInteger(1);
    }

    final public void setSelectedPause(int selectedPause) {
        this.wizardScreenData.setScreenIntegers(1, selectedPause);
    }

    private int getIntroAudioDelay(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenInteger(0);
    }

    final public void setBackgroundStyle(String backgroundStyle) {
        this.wizardScreenData.setScreenText(1, backgroundStyle);
    }

    final public void setSdCardStimuli(boolean sdCardStimuli) {
        this.wizardScreenData.setScreenBoolean(1, sdCardStimuli);
    }

    private boolean isSdCardStimuli(WizardScreenData wizardScreenData) {
        return wizardScreenData.getScreenBoolean(1);
    }

    final public void setRememberLastStimuli(boolean rememberLastStimuli) {
        this.wizardScreenData.setScreenBoolean(4, rememberLastStimuli);
    }

    private boolean isRememberLastStimuli(WizardScreenData wizardScreenData) {
        return wizardScreenData.getScreenBoolean(4);
    }

    final public void setShowCurtains(boolean showCurtains) {
        this.wizardScreenData.setScreenBoolean(5, showCurtains);
    }

    private boolean isShowCurtains(WizardScreenData wizardScreenData) {
        return wizardScreenData.getScreenBoolean(5);
    }

    @Override
    public String getScreenBooleanInfo(int index) {
        return new String[]{
            "Randomise Stimuli",
            "Full Screen Grid",
            "SDcard Stimuli",
            "StimulusCodeAudio",
            "RememberLastStimuli",
            "ShowCurtains"
        }[index];
    }

    @Override
    public String getScreenTextInfo(int index) {
        return new String[]{
            "BackgroundImage",
            "BackgroundStyle",
            "IntroAudio",
            "CorrectAudio",
            "RewardImage",
            "AudioAB",
            "ConsumedTagGroup",
            "StimuliButtonArray"
        }[index];
    }

    @Override
    public String getNextButtonInfo(int index) {
        return new String[]{"Next Button Label"}[index];
    }

    @Override
    public String getScreenIntegerInfo(int index) {
        return new String[]{"IntroAudioDelay", "SelectedPause"}[index];
    }

    public final void setStimuliSet(final WizardUtilStimuliData stimuliData) {
        if (this.wizardScreenData.getStimuli() == null) {
            this.wizardScreenData.setStimuli(new ArrayList<>());
        }
        final List<Stimulus> stimuliList = this.wizardScreenData.getStimuli();
        final HashSet<String> tagSet = new HashSet<>(Arrays.asList(new String[]{this.wizardScreenData.getScreenTitle()}));
        tagSet.add(stimuliData.getStimuliName());
        for (String stimulusEntry : stimuliData.getStimuliArray()) {
            // @todo: perhaps this is clearer if there is an explicit adjacency regex used on the identifyer in the GUI app or an explicit adjacency / sort field in the stimuli
//            final String adjacencyString = stimulusEntry.replaceAll("test_[15]", "adjacency_a").replaceAll("test_[26]", "adjacency_b").replaceAll("test_[37]", "adjacency_c").replaceAll("test_[48]", "adjacency_d");
            stimuliList.add(new Stimulus(stimulusEntry, stimulusEntry, null, stimulusEntry + ".jpg", null, stimulusEntry /*.substring(0, stimulusEntry.length() - 1)*/, 0, tagSet, null, null));
        }
    }

    public final void setStimuliSet(String[][] stimuliSet, String... tagNames) {
//        String[] tempStimuli = {
//            "filler_1_1", "intro_1", "test_2_2", "test_6_2", "training_2_2",
//            "filler_1_2", "intro_2", "test_2_3", "test_6_3", "training_2_3",
//            "filler_1_3", "intro_3", "test_3_1", "test_7_1", "training_3_1",
//            "filler_2_1", "room_1", "test_3_2", "test_7_2", "training_3_2",
//            "filler_2_2", "room_2", "test_3_3", "test_7_3", "training_3_3",
//            "filler_2_3", "room_3", "test_4_1", "test_8_1", "training_4_1",
//            "filler_3_1", "room_4", "test_4_2", "test_8_2", "training_4_2",
//            "filler_3_2", "room_5", "test_4_3", "test_8_3", "training_4_3",
//            "filler_3_3", "test_1_1", "test_5_1", "training_1_1",
//            "filler_4_1", "test_1_2", "test_5_2", "training_1_2",
//            "filler_4_2", "test_1_3", "test_5_3", "training_1_3",
//            "filler_4_3", "test_2_1", "test_6_1", "training_2_1",};
        if (this.wizardScreenData.getStimuli() == null) {
            this.wizardScreenData.setStimuli(new ArrayList<>());
        }
        final List<Stimulus> stimuliList = this.wizardScreenData.getStimuli();
        final HashSet<String> tagSet = new HashSet<>(Arrays.asList(new String[]{this.wizardScreenData.getScreenTitle()}));
        for (String tagName : tagNames) {
            tagSet.add(tagName);
        }
        for (String[] stimulusEntry : stimuliSet) {
            // @todo: perhaps this is clearer if there is an explicit adjacency regex used on the identifyer in the GUI app or an explicit adjacency / sort field in the stimuli
//            final String adjacencyString = stimulusEntry.replaceAll("test_[15]", "adjacency_a").replaceAll("test_[26]", "adjacency_b").replaceAll("test_[37]", "adjacency_c").replaceAll("test_[48]", "adjacency_d");
            stimuliList.add(new Stimulus(stimulusEntry[0], stimulusEntry[0], null, stimulusEntry[1], null, stimulusEntry[0] /*.substring(0, stimulusEntry.length() - 1)*/, 0, tagSet, null, null));
        }
        // add screen text
        // add grid with full screen option
        // add stimulus to the grid and add distractors by using the rating options as the stimuli id selector
        // add code audio
        // next stimulis based on grid click
        //        if (this.wizardScreenData.getStimuli() == null) {
        //            this.wizardScreenData.setStimuli(new ArrayList<>());
        //        }
        //        final List<Stimulus> stimuliList = this.wizardScreenData.getStimuli();
        //        final Pattern stimulusCodePattern = (wizardScreenData.getStimulusCodeMatch() != null) ? Pattern.compile(wizardScreenData.getStimulusCodeMatch()) : null;
        //        if (stimuliSet != null) {
        //            for (String stimulusLine : stimuliSet) {
        //                final HashSet<String> tagSet = new HashSet<>(Arrays.asList(new String[]{this.wizardScreenData.getScreenTitle()}));
        //                final Stimulus stimulus;
        //                if (stimulusCodePattern != null) {
        //                    System.out.println("stimulusCodeMatch:" + wizardScreenData.getStimulusCodeMatch());
        //                    Matcher matcher = stimulusCodePattern.matcher(stimulusLine);
        //                    final String codeString = (matcher.find()) ? matcher.group(1) : null;
        //                    System.out.println("codeString: " + codeString);
        //                    final String baseFileName = stimulusLine.replaceAll(BASE_FILE_REGEX, "");
        //                    tagSet.addAll(Arrays.asList(baseFileName.split("/")));
        //                    stimulus = new Stimulus(baseFileName, null, null, stimulusLine, null, codeString, 0, tagSet, null, null);
        //                } else if (stimulusLine.endsWith(".png")) {
        //                    tagSet.addAll(Arrays.asList(stimulusLine.split("/")));
        //                    stimulus = new Stimulus(stimulusLine.replace(".png", ""), null, null, stimulusLine, null, null, 0, tagSet, null, null);
        //                } else {
        //                    final String[] splitScreenText = stimulusLine.split(":", 2);
        //                    tagSet.addAll(Arrays.asList(splitScreenText[0].split("/")));
        //                    final String substring = (stimulusLine.length() < 55) ? stimulusLine : stimulusLine.substring(0, 54);
        //                    stimulus = new Stimulus(substring, null, null, null, splitScreenText[1].replace("\n", "<br/>"), null/*splitScreenText[0].replace(" ", "_").replace("/", "_")*/, 0, tagSet, null, null);
        //                }
        //                stimuliList.add(stimulus);
        //            }
        //        }
    }

    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, Experiment experiment,
            boolean obfuscateScreenNames, long displayOrder
    ) {
        experiment.appendUniqueStimuli(storedWizardScreenData.getStimuli());
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.stimulus);
        List<PresenterFeature> presenterFeatureList = storedWizardScreenData.getPresenterScreen().getPresenterFeatureList();
        if (storedWizardScreenData.isCentreScreen()) {
            presenterFeatureList.add(new PresenterFeature(FeatureType.centrePage, null));
        }
        if (getBackgroundImage(storedWizardScreenData) != null) {
            final PresenterFeature backgoundFeature = new PresenterFeature(FeatureType.backgroundImage, null);
            backgoundFeature.addFeatureAttributes(FeatureAttribute.msToNext, "3000");
//        backgoundFeature.addFeatureAttributes(FeatureAttribute.styleName, "zoomTo3of6 zoom to house");
            backgoundFeature.addFeatureAttributes(FeatureAttribute.styleName, getBackgroundStyle(storedWizardScreenData));
//        backgoundFeature.addFeatureAttributes(FeatureAttribute.styleName, "zoomOut");
            backgoundFeature.addFeatureAttributes(FeatureAttribute.src, getBackgroundImage(storedWizardScreenData));
            presenterFeatureList.add(backgoundFeature);
//        set the image as the parent to subsequent features
            presenterFeatureList = backgoundFeature.getPresenterFeatureList();
        }
        if (getIntroAudio(storedWizardScreenData) != null) {
            final PresenterFeature introAudioFeature = new PresenterFeature(FeatureType.audioButton, null);
            introAudioFeature.addFeatureAttributes(FeatureAttribute.eventTag, getIntroAudio(storedWizardScreenData));
            introAudioFeature.addFeatureAttributes(FeatureAttribute.src, getIntroAudio(storedWizardScreenData));
            introAudioFeature.addFeatureAttributes(FeatureAttribute.poster, "intro_1.jpg");
            introAudioFeature.addFeatureAttributes(FeatureAttribute.autoPlay, Boolean.toString(true));
            introAudioFeature.addFeatureAttributes(FeatureAttribute.hotKey, "R1_MA_A");
            introAudioFeature.addFeatureAttributes(FeatureAttribute.styleName, "titleBarButton");
            presenterFeatureList.add(introAudioFeature);
            presenterFeatureList = introAudioFeature.getPresenterFeatureList();
        }
        if (getIntroAudioDelay(storedWizardScreenData) > 0) {
            final PresenterFeature pauseFeature = new PresenterFeature(FeatureType.pause, null);
            pauseFeature.addFeatureAttributes(FeatureAttribute.msToNext, Integer.toString(getIntroAudioDelay(storedWizardScreenData)));
            presenterFeatureList.add(pauseFeature);
            presenterFeatureList = pauseFeature.getPresenterFeatureList();
        }
        final PresenterFeature loadStimuliFeature = new PresenterFeature((!isSdCardStimuli(storedWizardScreenData)) ? FeatureType.loadStimulus : FeatureType.loadSdCardStimulus, null);
        final RandomGrouping randomGrouping = new RandomGrouping();
        if (getConsumedTagGroup(storedWizardScreenData) != null) {
            randomGrouping.setConsumedTagGroup(getConsumedTagGroup(storedWizardScreenData));
            loadStimuliFeature.addStimulusTag(getConsumedTagGroup(storedWizardScreenData));
        } else {
            loadStimuliFeature.addStimulusTag(storedWizardScreenData.getScreenTitle());
        }
        if (storedWizardScreenData.getStimuliRandomTags() != null) {
            for (String randomTag : storedWizardScreenData.getStimuliRandomTags()) {
                randomGrouping.addRandomTag(randomTag);
            }
            final String metadataFieldname = "groupAllocation_" + storedWizardScreenData.getScreenTag();
            randomGrouping.setStorageField(metadataFieldname);
            loadStimuliFeature.setRandomGrouping(randomGrouping);
            experiment.getMetadata().add(new Metadata(metadataFieldname, metadataFieldname, ".*", ".", false, null));
        }
//        if (isSdCardStimuli(storedWizardScreenData)) {
//            loadStimuliFeature.addFeatureAttributes(FeatureAttribute.excludeRegex, ".*_question\\....$");
//        }
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.eventTag, storedWizardScreenData.getScreenTitle());
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.randomise, Boolean.toString(isRandomiseStimuli(storedWizardScreenData)));
        if (!isRememberLastStimuli(storedWizardScreenData)) {
            loadStimuliFeature.addUndefinedAttribute("rememberLastStimuli", "false");
        } else {
            loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatCount, "1");
            loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatRandomWindow, "0");
            loadStimuliFeature.addFeatureAttributes(FeatureAttribute.maxStimuli, Integer.toString(storedWizardScreenData.getStimuliCount()));
        }
        presenterFeatureList.add(loadStimuliFeature);
        final PresenterFeature hasMoreStimulusFeature = new PresenterFeature(FeatureType.hasMoreStimulus, null);

        final PresenterFeature backgoundFeatureRemove = new PresenterFeature(FeatureType.backgroundImage, null);
        backgoundFeatureRemove.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        backgoundFeatureRemove.addFeatureAttributes(FeatureAttribute.styleName, "");
        backgoundFeatureRemove.addFeatureAttributes(FeatureAttribute.src, "");
        hasMoreStimulusFeature.getPresenterFeatureList().add(backgoundFeatureRemove);
        final PresenterFeature clearScreenFeature = new PresenterFeature(FeatureType.clearPage, null);
        clearScreenFeature.addFeatureAttributes(FeatureAttribute.styleName, "fullScreenWidth");
        hasMoreStimulusFeature.getPresenterFeatureList().add(clearScreenFeature);

        final PresenterFeature stimulusRelatedTags;
        if (isShowCurtains(storedWizardScreenData)) {
            stimulusRelatedTags = hasMoreStimulusFeature;
            hasMoreStimulusFeature.addFeature(FeatureType.stimulusCodeVideo, null, "0", "100", "Monkey_mp4/<code>", "0", "true", "borderedVideoFull", "false", "false", "100");
            final PresenterFeature touchInputCaptureStart = hasMoreStimulusFeature.addFeature(FeatureType.touchInputCaptureStart, null, "false", "3000");
            touchInputCaptureStart.addFeature(FeatureType.pauseVideo, null);
            touchInputCaptureStart.addFeature(FeatureType.enableStimulusButtons, null);
            final PresenterFeature touchInputStimulusButton1 = hasMoreStimulusFeature.addFeature(FeatureType.touchInputStimulusButton, "Left Overlay Button", "Curtain", "curtain_left.png", "leftOverlayCurtain", "allCurtainGroup");
            touchInputStimulusButton1.addFeature(FeatureType.disableStimulusButtons, null);
            touchInputStimulusButton1.addFeature(FeatureType.rewindVideo, null);
            touchInputStimulusButton1.addFeature(FeatureType.pause, null, "1000").addFeature(FeatureType.playVideo, null);
            touchInputStimulusButton1.addFeature(FeatureType.stimulusCodeAudio, null, "500", "Correct", "false").addFeature(FeatureType.enableStimulusButtons, null);
            final PresenterFeature touchInputStimulusButton2 = hasMoreStimulusFeature.addFeature(FeatureType.touchInputStimulusButton, "Right Overlay Button", "Curtain", "curtain_right.png", "rightOverlayCurtain", "allCurtainGroup");
            touchInputStimulusButton2.addFeature(FeatureType.disableStimulusButtons, null);
            touchInputStimulusButton2.addFeature(FeatureType.rewindVideo, null);
            touchInputStimulusButton2.addFeature(FeatureType.pause, null, "1000").addFeature(FeatureType.playVideo, null);
            touchInputStimulusButton2.addFeature(FeatureType.stimulusCodeAudio, null, "500", "Correct", "false").addFeature(FeatureType.enableStimulusButtons, null);
        } else if (isCodeAudio(storedWizardScreenData)) {
            final PresenterFeature stimulusCodeAudio1 = new PresenterFeature(FeatureType.stimulusCodeAudio, null);
            stimulusCodeAudio1.addFeatureAttributes(FeatureAttribute.showPlaybackIndicator, Boolean.toString(false));
            stimulusCodeAudio1.addFeatureAttributes(FeatureAttribute.codeFormat, "<code>_1");
            stimulusCodeAudio1.addFeatureAttributes(FeatureAttribute.msToNext, "1000");
            hasMoreStimulusFeature.getPresenterFeatureList().add(stimulusCodeAudio1);
            final PresenterFeature stimulusCodeAudio2 = new PresenterFeature(FeatureType.stimulusCodeAudio, null);
            stimulusCodeAudio2.addFeatureAttributes(FeatureAttribute.showPlaybackIndicator, Boolean.toString(false));
            stimulusCodeAudio2.addFeatureAttributes(FeatureAttribute.codeFormat, "<code>_2");
            stimulusCodeAudio2.addFeatureAttributes(FeatureAttribute.msToNext, "0");
            stimulusCodeAudio1.getPresenterFeatureList().add(stimulusCodeAudio2);
//        stimulusCodeAudio.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
//        final PresenterFeature tableFeature = new PresenterFeature(FeatureType.table, null);
//        stimulusCodeAudio1.getPresenterFeatureList().add(tableFeature);
//        final PresenterFeature rowFeature = new PresenterFeature(FeatureType.row, null);
//        tableFeature.getPresenterFeatureList().add(rowFeature);
//        final PresenterFeature leftColumnFeature = new PresenterFeature(FeatureType.column, null);
//        rowFeature.getPresenterFeatureList().add(leftColumnFeature);
//        final PresenterFeature middleColumnFeature = new PresenterFeature(FeatureType.column, null);
//        final PresenterFeature middlePadding = new PresenterFeature(FeatureType.htmlText, "&nbsp;");
//        middleColumnFeature.addFeatureAttributes(FeatureAttribute.styleName, "middlePadding");
//        middleColumnFeature.getPresenterFeatureList().add(middlePadding);
//        rowFeature.getPresenterFeatureList().add(middleColumnFeature);
//        final PresenterFeature rightColumnFeature = new PresenterFeature(FeatureType.column, null);
//        rowFeature.getPresenterFeatureList().add(rightColumnFeature);
            final PresenterFeature stimulusCodeVideoL = stimulusCodeAudio1.addFeature(FeatureType.stimulusCodeVideo, null, "0", "100", "<code>_L", "0", "false", "borderedVideoLeft", "true", "false", "100");
            final PresenterFeature stimulusCodeVideoR = stimulusCodeAudio1.addFeature(FeatureType.stimulusCodeVideo, null, "0", "100", "<code>_R", "0", "false", "borderedVideoRight", "true", "false", "100");
            final PresenterFeature stimulusCodeAudio3 = stimulusCodeVideoR.addFeature(FeatureType.stimulusCodeAudio, null, "0", "<code>_3", Boolean.toString(false));
            stimulusRelatedTags = stimulusCodeAudio3;
            final PresenterFeature touchInputCaptureStart = hasMoreStimulusFeature.addFeature(FeatureType.touchInputCaptureStart, null, "false", "-1");
//        final PresenterFeature nextStimulusL = new PresenterFeature(FeatureType.nextStimulus, null);
//        nextStimulusL.addFeatureAttributes(FeatureAttribute.eventTag, "nextStimulusL");
//        nextStimulusL.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
//        touchInputZoneL.getPresenterFeatureList().add(nextStimulusL);

            final PresenterFeature leftOverlayButton = stimulusRelatedTags.addFeature(FeatureType.touchInputStimulusButton, "Left Overlay Button", "Left", "", "leftOverlayButton", "leftButtonGroup");
            leftOverlayButton.getPresenterFeatureList().add(new PresenterFeature(FeatureType.disableStimulusButtons, null));
            final PresenterFeature responseAudio1 = new PresenterFeature(FeatureType.stimulusCodeAudio, null);
            responseAudio1.addFeatureAttributes(FeatureAttribute.showPlaybackIndicator, Boolean.toString(false));
            responseAudio1.addFeatureAttributes(FeatureAttribute.codeFormat, "Correct");
            responseAudio1.addFeatureAttributes(FeatureAttribute.msToNext, "500");
            responseAudio1.getPresenterFeatureList().add(new PresenterFeature(FeatureType.enableStimulusButtons, null));
            leftOverlayButton.getPresenterFeatureList().add(responseAudio1);
            final PresenterFeature rightOverlayButton = stimulusRelatedTags.addFeature(FeatureType.touchInputStimulusButton, "Right Overlay Button", "Right", "", "rightOverlayButton", "rightButtonGroup");
            rightOverlayButton.getPresenterFeatureList().add(new PresenterFeature(FeatureType.disableStimulusButtons, null));
            final PresenterFeature responseAudio2 = new PresenterFeature(FeatureType.stimulusCodeAudio, null);
            responseAudio2.addFeatureAttributes(FeatureAttribute.showPlaybackIndicator, Boolean.toString(false));
            responseAudio2.addFeatureAttributes(FeatureAttribute.codeFormat, "Correct");
            responseAudio2.addFeatureAttributes(FeatureAttribute.msToNext, "500");
            responseAudio2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.enableStimulusButtons, null));
            rightOverlayButton.getPresenterFeatureList().add(responseAudio2);
        } else {
            final PresenterFeature imageLoadedAction = hasMoreStimulusFeature.addFeature(FeatureType.triggerListener, null, "imageLoadedAction", ("AudioRepeat2".equals(getAudioAB(storedWizardScreenData))) ? "2" : "1", "1");
            final PresenterFeature currentAction;
            if ("LoopAction".equals(getAudioAB(storedWizardScreenData))) {
                imageLoadedAction.addFeature(FeatureType.trigger, null, "loopAction");
                currentAction = hasMoreStimulusFeature.addFeature(FeatureType.triggerListener, null, "loopAction", "1", "-1");
            } else {
                currentAction = imageLoadedAction;
            }
            currentAction.addFeature(FeatureType.backgroundImage, null, "0", "", "");
            currentAction.addFeature(FeatureType.disableStimulusButtons, null);
            final PresenterFeature stimulusAudio = ("AudioAB".equals(getAudioAB(storedWizardScreenData))) ? currentAction.addFeature(FeatureType.stimulusCodeAudio, null, "500", "<code>_a", Boolean.toString(false)).addFeature(FeatureType.stimulusCodeAudio, null, "0", "<code>_b", Boolean.toString(false)) : currentAction.addFeature(FeatureType.stimulusAudio, null, "0", Boolean.toString(false));
            stimulusAudio.addFeature(FeatureType.enableStimulusButtons, null);
            stimulusAudio.addFeature(FeatureType.backgroundImage, null, "0", "", "backgroundHighlight");
            final PresenterFeature pause2 = stimulusAudio.addFeature(FeatureType.pause, null, "3000");
            switch (getAudioAB(storedWizardScreenData)) {
                case "LoopAction":
                    pause2.addFeature(FeatureType.trigger, null, "loopAction");
                    break;
                case "AudioAB":
                    final PresenterFeature repeatAudioB = pause2.addFeature(FeatureType.stimulusCodeAudio, null, "3000", "<code>_b", Boolean.toString(false));
                    final PresenterFeature drukophetplaatje = repeatAudioB.addFeature(FeatureType.stimulusCodeAudio, null, "3000", "DrukOpHetPlaatje", Boolean.toString(false));
                    drukophetplaatje.addFeature(FeatureType.disableStimulusButtons, null);
                    drukophetplaatje.addFeature(FeatureType.cancelPauseTimers, null);
                    drukophetplaatje.addFeature(FeatureType.clearPage, null, "");
                    if (getRewardImage(storedWizardScreenData) != null) {
                        drukophetplaatje.addFeature(FeatureType.backgroundImage, null, "0", getRewardImage(storedWizardScreenData), "");
                    }
                    final PresenterFeature pause3a = drukophetplaatje.addFeature(FeatureType.pause, null, "1000");
                    pause3a.addFeature(FeatureType.touchInputReportSubmit, null);
                    pause3a.addFeature(FeatureType.nextStimulus, null, "false");
                    break;
                case "AudioRepeat1":
                case "AudioRepeat2":
                    final PresenterFeature repeatAudio = pause2.addFeature(FeatureType.stimulusAudio, null, "3000", Boolean.toString(false));
                    repeatAudio.addFeature(FeatureType.disableStimulusButtons, null);
                    repeatAudio.addFeature(FeatureType.cancelPauseTimers, null);
                    repeatAudio.addFeature(FeatureType.clearPage, null, "");
                    if (getRewardImage(storedWizardScreenData) != null) {
                        repeatAudio.addFeature(FeatureType.backgroundImage, null, "0", getRewardImage(storedWizardScreenData), "");
                    }
                    final PresenterFeature pause3b = repeatAudio.addFeature(FeatureType.pause, null, "1000");
                    pause3b.addFeature(FeatureType.touchInputReportSubmit, null);
                    pause3b.addFeature(FeatureType.nextStimulus, null, "false");
                    break;
            }
            hasMoreStimulusFeature.addFeature(FeatureType.disableStimulusButtons, null);
            final String[][] stimuliButtonArray;
            if (getStimuliButtonArray(storedWizardScreenData) != null) {
                final ArrayList<String[]> tempList = new ArrayList<>();
                final String[] splitButtonArray = getStimuliButtonArray(storedWizardScreenData).split(",");
                final int buttonArrayLength = splitButtonArray.length;
                for (String buttonElement : splitButtonArray) {
                    tempList.add(new String[]{"<code>_" + buttonElement + ".jpg", buttonElement + buttonArrayLength + "TouchButton", buttonElement, buttonElement + buttonArrayLength + "OverlayButton", buttonElement + buttonArrayLength + "OverlayButton", buttonElement + "ButtonGroup"});
                }
                stimuliButtonArray = tempList.toArray(new String[0][6]);
            } else {
                stimuliButtonArray = new String[][]{{"<code>_left.jpg", "borderedVideoLeft", "Left Overlay Button", "Left", "leftOverlayButton", "leftButtonGroup"}, {"<code>_right.jpg", "borderedVideoRight", "Right Overlay Button", "Right", "rightOverlayButton", "rightButtonGroup"}};
            }
            for (String[] additionString : stimuliButtonArray) {
                final PresenterFeature stimulusImage = hasMoreStimulusFeature.addFeature(FeatureType.stimulusCodeImage, null, "250", additionString[0], additionString[1]);
                final PresenterFeature leftOverlayButton = stimulusImage.addFeature(FeatureType.touchInputStimulusButton, additionString[2], additionString[3], "", additionString[4], additionString[5]);
                leftOverlayButton.addFeature(FeatureType.disableStimulusButtons, null);
                leftOverlayButton.addFeature(FeatureType.cancelPauseTimers, null);
//                leftOverlayButton.addFeature(FeatureType.trigger, null, "buttonAction");
                final PresenterFeature pause = leftOverlayButton.addFeature(FeatureType.pause, null, Integer.toString(getSelectedPause(storedWizardScreenData)));
                pause.addFeature(FeatureType.clearPage, null, "");
                if (getRewardImage(storedWizardScreenData) != null) {
                    pause.addFeature(FeatureType.backgroundImage, null, "0", getRewardImage(storedWizardScreenData), "");
                }
                final PresenterFeature stimulusCodeAudio = (getCorrectAudio(storedWizardScreenData) == null) ? pause.addFeature(FeatureType.pause, null, "1000") : pause.addFeature(FeatureType.stimulusCodeAudio, null, "500", getCorrectAudio(storedWizardScreenData), "false");
                stimulusCodeAudio.addFeature(FeatureType.touchInputReportSubmit, null);
                stimulusCodeAudio.addFeature(FeatureType.nextStimulus, null, "false");
                stimulusImage.addFeature(FeatureType.disableStimulusButtons, null);
                stimulusImage.addFeature(FeatureType.trigger, null, "imageLoadedAction");
            }
            stimulusRelatedTags = hasMoreStimulusFeature;

//                                        <touchInputStimulusButton featureText="Left Overlay Button" eventTag="Left" styleName="leftOverlayButton">
//                                <disableStimulusButtons/>
//                                <cancelPauseTimers/>
//                                <pause msToNext="1000">
//                                    <clearPage/>
//                                    <backgroundImage msToNext="0" src="P0.png" styleName=""/>
//                                    <stimulusCodeAudio msToNext="500" codeFormat="Correct" showPlaybackIndicator="false">
//                                        <touchInputReportSubmit/>
//                                        <nextStimulus repeatIncorrect="false"/>
//                                    </stimulusCodeAudio>
//                                </pause>
//                            </touchInputStimulusButton>
//                            <pause msToNext="3000">
//                                <showStimulus/>
//                            </pause>
//            hasMoreStimulusFeature.addFeature(FeatureType.htmlText, "&nbsp;");
            hasMoreStimulusFeature.addFeature(FeatureType.touchInputCaptureStart, null, "false", "-1");
//            final PresenterFeature rightOverlayButton = new PresenterFeature(FeatureType.touchInputStimulusButton, "Right Overlay Button");
//            rightOverlayButton.addFeatureAttributes(FeatureAttribute.eventTag, "Right");
//            rightOverlayButton.addFeatureAttributes(FeatureAttribute.styleName, "rightOverlayButton");
//            rightOverlayButton.getPresenterFeatureList().add(new PresenterFeature(FeatureType.disableStimulusButtons, null));
//            final PresenterFeature responseAudio2 = new PresenterFeature(FeatureType.stimulusCodeAudio, null);
//            responseAudio2.addFeatureAttributes(FeatureAttribute.showPlaybackIndicator, Boolean.toString(false));
//            responseAudio2.addFeatureAttributes(FeatureAttribute.codeFormat, "Correct");
//            responseAudio2.addFeatureAttributes(FeatureAttribute.msToNext, "500");
//            responseAudio2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.enableStimulusButtons, null));
//            rightOverlayButton.getPresenterFeatureList().add(responseAudio2);
//            stimulusRelatedTags.getPresenterFeatureList().add(rightOverlayButton);

        }

        final PresenterFeature tableFeature = new PresenterFeature(FeatureType.table, null);
        tableFeature.addFeatureAttributes(FeatureAttribute.styleName, "titleBarButton");
        tableFeature.addFeatureAttributes(FeatureAttribute.showOnBackButton, "true");
        hasMoreStimulusFeature.getPresenterFeatureList().add(tableFeature);
        final PresenterFeature rowFeature = new PresenterFeature(FeatureType.row, null);
        tableFeature.getPresenterFeatureList().add(rowFeature);
        WizardScreenData menuScreenData = storedWizardScreenData.getMenuWizardScreenData().get(0);
        final PresenterFeature menuStimulusButton = rowFeature.addFeature(FeatureType.column, null, "").addFeature(FeatureType.actionButton, menuScreenData.getMenuLabel() + " (O)", "R1_MA_ENTER", "R1_MA_ENTER", "");
        menuStimulusButton.addFeature(FeatureType.touchInputReportSubmit, null);
        menuStimulusButton.addFeature(FeatureType.autoNextPresenter, null, menuScreenData.getScreenTag());
        final PresenterFeature previousStimulusButton = rowFeature.addFeature(FeatureType.column, null, "").addFeature(FeatureType.actionButton, "Prev (left)", "R1_MA_LEFT", "R1_MA_LEFT", "");
        previousStimulusButton.addFeature(FeatureType.touchInputReportSubmit, null);
        previousStimulusButton.addFeature(FeatureType.prevStimulus, null, "false");
        final PresenterFeature repeatStimulusButton = rowFeature.addFeature(FeatureType.column, null, "").addFeature(FeatureType.actionButton, "Repeat (A)", "R1_MA_A", "R1_MA_A", "");
        repeatStimulusButton.addFeature(FeatureType.touchInputReportSubmit, null);
        repeatStimulusButton.addFeature(FeatureType.showStimulus, null);
        final PresenterFeature nextStimulusButton = rowFeature.addFeature(FeatureType.column, null, "").addFeature(FeatureType.actionButton, "Next (right)", "R1_MA_RIGHT", "R1_MA_RIGHT", "");
        nextStimulusButton.addFeature(FeatureType.touchInputReportSubmit, null);
        nextStimulusButton.addFeature(FeatureType.nextStimulus, null, "false");
        final PresenterFeature endOfStimulusFeature = new PresenterFeature(FeatureType.endOfStimulus, null);
        final PresenterFeature autoNextPresenter = new PresenterFeature(FeatureType.autoNextPresenter, null);
        endOfStimulusFeature.getPresenterFeatureList().add(autoNextPresenter);
        loadStimuliFeature.getPresenterFeatureList().add(hasMoreStimulusFeature);
        loadStimuliFeature.getPresenterFeatureList().add(endOfStimulusFeature);
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }
}
