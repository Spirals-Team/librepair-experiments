/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;
import nl.mpi.tg.eg.experimentdesigner.model.Stimulus;

/**
 * @since Dec 2, 2015 1:21:07 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class Sentveri_exp3 {

    final String[] stimulusTagList = new String[]{"list1", "list2", "list3", "list4"};

    public void create3c(final List<PresenterScreen> presenterScreenList) {
        long displayOrder = 1;
        PresenterScreen practiceScreen = createStimulusScreen("practice", displayOrder);
        presenterScreenList.add(practiceScreen);
        for (char setChar : new char[]{'a', 'b', 'c'}) {
            for (String tagString : stimulusTagList) {
                String screenName = tagString + "_" + setChar;
                displayOrder++;
                PresenterScreen stimuliSetScreen = createStimulusScreen(screenName, displayOrder);
                presenterScreenList.add(stimuliSetScreen);
            }
        }
        final PresenterScreen aboutScreen = new PresenterScreen(null, "about", practiceScreen, "about", null, PresenterType.debug, displayOrder);
        List<PresenterFeature> presenterFeatureList = aboutScreen.getPresenterFeatureList();
        final PresenterFeature versionData = new PresenterFeature(FeatureType.versionData, null);
        presenterFeatureList.add(versionData);
        presenterScreenList.add(aboutScreen);
    }

    private PresenterScreen createStimulusScreen(String screenName, long displayOrder) {
        final PresenterScreen presenterScreen = new PresenterScreen(null, screenName, null, screenName + "Screen", null, PresenterType.stimulus, displayOrder);
        List<PresenterFeature> presenterFeatureList = presenterScreen.getPresenterFeatureList();
        final PresenterFeature loadStimuliFeature = new PresenterFeature(FeatureType.loadStimulus, null);
        loadStimuliFeature.addStimulusTag(screenName);
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.eventTag, screenName);
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.randomise, "false");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatCount, "1");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatRandomWindow, "0");
        presenterFeatureList.add(loadStimuliFeature);
        final PresenterFeature hasMoreStimulusFeature = new PresenterFeature(FeatureType.hasMoreStimulus, null);
        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.centrePage, null));
        //There are 12 sets/lists in total and each participant only responds to one of them.
        //There are 8 practice trials at the beginning of each set, which are fixed.
        //each trial starts with:
        //1. a "cross" for fixation in the center (1000ms or 1ms);
        hasMoreStimulusFeature.getPresenterFeatureList().add(addSentenceFeature(screenName));
        //6. a blank screen (1000ms) before starting the next trial (loop).
//        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.showStimulusProgress, null));
        loadStimuliFeature.getPresenterFeatureList().add(hasMoreStimulusFeature);
        final PresenterFeature endOfStimulusFeature = new PresenterFeature(FeatureType.endOfStimulus, null);
        endOfStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.plainText, "end of stimuli"));
        final PresenterFeature menuButtonFeature = new PresenterFeature(FeatureType.targetButton, "Menu");
        menuButtonFeature.addFeatureAttributes(FeatureAttribute.target, "AutoMenu");
        endOfStimulusFeature.getPresenterFeatureList().add(menuButtonFeature);
        loadStimuliFeature.getPresenterFeatureList().add(endOfStimulusFeature);
        return presenterScreen;
    }

    private PresenterFeature addStimulusImage(String screenName, String imageSet, final String msToNext) {
        final PresenterFeature imageFeature = new PresenterFeature(FeatureType.stimulusCodeImage, null);
//        imageFeature.addFeatureAttributes(FeatureAttribute.percentOfPage, "100");
//        imageFeature.addFeatureAttributes(FeatureAttribute.maxHeight, "100");
//        imageFeature.addFeatureAttributes(FeatureAttribute.maxWidth, "100");
        imageFeature.addFeatureAttributes(FeatureAttribute.codeFormat, screenName + "/" + imageSet + "/<code>.jpg");
        imageFeature.addFeatureAttributes(FeatureAttribute.msToNext, msToNext);
        return imageFeature;
    }

    private PresenterFeature addSentenceFeature(final String screenName) {
        //2. the image of the "sentence" in the center (self-paced - wait till a "spacebar" response, lock out all the other button responses)
        final PresenterFeature sentenceFeature = addStimulusImage(screenName, screenName + "_sent", "0");
        final PresenterFeature nextStimulusFeature = new PresenterFeature(FeatureType.actionFooterButton, "spacebar");
        nextStimulusFeature.addFeatureAttributes(FeatureAttribute.eventTag, "spacebar");
        nextStimulusFeature.addFeatureAttributes(FeatureAttribute.hotKey, "SPACE");
        nextStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
        nextStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.centrePage, null));
        nextStimulusFeature.getPresenterFeatureList().add(addImageFeature(screenName));
        sentenceFeature.addFeatures(FeatureType.mediaLoaded, FeatureType.mediaLoadFailed)[0].getPresenterFeatureList().add(nextStimulusFeature);
        return sentenceFeature;
    }

    private PresenterFeature addImageFeature(final String screenName) {
        //3. an arbitrary fast (0ms) or slow (500ms) delay with a blank screen between sentence and picture -defined by the variable "delay"
        //4. the image of the "picture" in the center (self-paced - wait till a "." for yes or a "z" for no response, lock out all the other button responses)
        final PresenterFeature delayFeature = new PresenterFeature(FeatureType.stimulusPause, null);
        final PresenterFeature imageFeature = addStimulusImage(screenName, "image_" + screenName, "0");
        delayFeature.getPresenterFeatureList().add(imageFeature);
        final PresenterFeature mediaLoaded = imageFeature.addFeatures(FeatureType.mediaLoaded, FeatureType.mediaLoadFailed)[0];
        final PresenterFeature responseZFeature = new PresenterFeature(FeatureType.actionFooterButton, "z");
//        responseZFeature.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
        responseZFeature.addFeatureAttributes(FeatureAttribute.eventTag, "responseZ");
        responseZFeature.addFeatureAttributes(FeatureAttribute.hotKey, "Z");
        responseZFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
        responseZFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.centrePage, null));
        mediaLoaded.getPresenterFeatureList().add(responseZFeature);
        final PresenterFeature responseDotFeature = new PresenterFeature(FeatureType.actionFooterButton, ".");
//        responseDotFeature.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
        responseDotFeature.addFeatureAttributes(FeatureAttribute.eventTag, "responseDot");
        responseDotFeature.addFeatureAttributes(FeatureAttribute.hotKey, "NUM_PERIOD");
        responseDotFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
        responseDotFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.centrePage, null));
        mediaLoaded.getPresenterFeatureList().add(responseDotFeature);
        responseDotFeature.getPresenterFeatureList().add(addNextStimulusButtons(screenName));
        responseZFeature.getPresenterFeatureList().add(addNextStimulusButtons(screenName));
        return delayFeature;
    }

    private PresenterFeature addNextStimulusButtons(final String screenName) {
        // todo: vertical centre all screens
        final PresenterFeature checkTagFeature = new PresenterFeature(FeatureType.currentStimulusHasTag, null);
        checkTagFeature.addFeatureAttributes(FeatureAttribute.msToNext, "3000");
        checkTagFeature.addStimulusTag("question");
        final PresenterFeature withoutTagFeature = new PresenterFeature(FeatureType.conditionFalse, null);
        final PresenterFeature autoNextFeature = new PresenterFeature(FeatureType.nextStimulus, null);
//        autoNextFeature.addFeatureAttributes(FeatureAttribute.eventTag, "nonquestion");
        autoNextFeature.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
        withoutTagFeature.getPresenterFeatureList().add(autoNextFeature);
        checkTagFeature.getPresenterFeatureList().add(withoutTagFeature);
        final PresenterFeature hasTagFeature = new PresenterFeature(FeatureType.conditionTrue, null);
        checkTagFeature.getPresenterFeatureList().add(hasTagFeature);
        //5. on half of the trials (36/72), the image of the "question" in the center (self-paced - wait till a "." for yes or a "z" for no response, lock out all the other button responses) - arbitrarily defined by the variable "QorNOT"
        // todo: this should have a red border, but should it be a tag or on the image and if a tag should it take style?
        final PresenterFeature questionFeature = addStimulusImage(screenName, screenName + "_Q", "0");
        final PresenterFeature responseZFeature = new PresenterFeature(FeatureType.actionFooterButton, "z");
        responseZFeature.addFeatureAttributes(FeatureAttribute.eventTag, "responseZ");
        responseZFeature.addFeatureAttributes(FeatureAttribute.hotKey, "Z");
        final PresenterFeature nextStimulusFeature1 = new PresenterFeature(FeatureType.nextStimulus, null);
        nextStimulusFeature1.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
//        nextStimulusFeature1.addFeatureAttributes(FeatureAttribute.eventTag, "nextStimulus");
        responseZFeature.getPresenterFeatureList().add(nextStimulusFeature1);
        hasTagFeature.getPresenterFeatureList().add(responseZFeature);
        questionFeature.addFeatures(FeatureType.mediaLoaded, FeatureType.mediaLoadFailed)[0].getPresenterFeatureList().add(checkTagFeature);
        final PresenterFeature responseDotFeature = new PresenterFeature(FeatureType.actionFooterButton, ".");
        responseDotFeature.addFeatureAttributes(FeatureAttribute.eventTag, "responseDot");
        responseDotFeature.addFeatureAttributes(FeatureAttribute.hotKey, "NUM_PERIOD");
        final PresenterFeature nextStimulusFeature2 = new PresenterFeature(FeatureType.nextStimulus, null);
        nextStimulusFeature2.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
//        nextStimulusFeature2.addFeatureAttributes(FeatureAttribute.eventTag, "nextStimulus");
        responseDotFeature.getPresenterFeatureList().add(nextStimulusFeature2);
        hasTagFeature.getPresenterFeatureList().add(responseDotFeature);
        return questionFeature;
    }

    public ArrayList<Stimulus> createStimuli() {
        final ArrayList<Stimulus> stimuliList = new ArrayList<>();

        for (int index = 0; index < Sentveri_exp3Data.practPictureIndex.length; index++) {
            final HashSet<String> tagSet = new HashSet<>(Arrays.asList(new String[]{"practice"}));
            if (Sentveri_exp3Data.practQorNOT[index]) {
                tagSet.add("question");
            }
            final String labelString = "prac_" + Sentveri_exp3Data.practPictureIndex[index] + ((Sentveri_exp3Data.practQorNOT[index]) ? "_q" : "");
            final Stimulus stimulus = new Stimulus(labelString, null, null, null, labelString, "" + Sentveri_exp3Data.practPictureIndex[index], (Sentveri_exp3Data.practslow[index]) ? 1000 : 0, tagSet, null, null);
            stimuliList.add(stimulus);
        }

        for (char setChar : new char[]{'a', 'b', 'c'}) {
            for (String tagString : stimulusTagList) {
                final boolean[] currendSlow;
                switch (setChar) {
                    case 'a':
                        currendSlow = Sentveri_exp3Data.slowA;
                        break;
                    case 'b':
                        currendSlow = Sentveri_exp3Data.slowB;
                        break;
                    default:
                        currendSlow = Sentveri_exp3Data.slowC;
                }
                for (int index = 0; index < Sentveri_exp3Data.pictureIndex.length; index++) {
                    final HashSet<String> tagSet = new HashSet<>(Arrays.asList(new String[]{tagString + "_" + setChar}));
                    if (Sentveri_exp3Data.QorNOT[index]) {
                        tagSet.add("question");
                    }
                    final String identifierString = tagString + "_" + setChar + "_" + Sentveri_exp3Data.pictureIndex[index] + ((Sentveri_exp3Data.QorNOT[index]) ? "_q" : "");
                    final Stimulus stimulus = new Stimulus(identifierString, null, null, null, identifierString, "" + Sentveri_exp3Data.pictureIndex[index], (currendSlow[index]) ? 1000 : 0, tagSet, null, null);
                    stimuliList.add(stimulus);
                }
            }
        }
        return stimuliList;
    }
}
