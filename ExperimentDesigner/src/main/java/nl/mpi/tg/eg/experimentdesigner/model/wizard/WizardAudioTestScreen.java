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
package nl.mpi.tg.eg.experimentdesigner.model.wizard;

import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;

/**
 * @since May 2, 2016 5:05:16 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardAudioTestScreen extends AbstractWizardScreen {

    public WizardAudioTestScreen() {
        super(WizardScreenEnum.WizardAudioTestScreen, "AudioTest", "AudioTest", "AudioTest");
        this.wizardScreenData.setScreenText(1, "");
        this.wizardScreenData.setScreenText(2, "");
        this.wizardScreenData.setScreenText(3, null);
        this.wizardScreenData.setScreenText(4, null);
        this.wizardScreenData.setScreenText(5, null);
        this.wizardScreenData.setScreenText(6, "");
        this.wizardScreenData.setScreenText(7, null);
        this.wizardScreenData.setScreenBoolean(0, false);
        this.wizardScreenData.setScreenBoolean(1, false);
        this.wizardScreenData.setScreenIntegers(0, 0);
    }

    public WizardAudioTestScreen(String screenName, String pageText, String buttonLabel, String audioPath) {
        super(WizardScreenEnum.WizardAudioTestScreen, screenName, screenName, screenName);
        this.setScreenText(pageText);
        this.wizardScreenData.setScreenText(1, "");
        this.wizardScreenData.setScreenText(2, "");
        this.wizardScreenData.setScreenText(3, null);
        this.wizardScreenData.setScreenText(4, null);
        this.wizardScreenData.setScreenText(5, null);
        this.wizardScreenData.setScreenText(6, "");
        this.wizardScreenData.setScreenText(7, null);
        this.wizardScreenData.setScreenBoolean(0, false);
        this.wizardScreenData.setScreenBoolean(1, false);
        this.wizardScreenData.setScreenIntegers(0, 0);
        this.setNextButton(buttonLabel);
        this.wizardScreenData.setScreenMediaPath(audioPath);
    }

    public WizardAudioTestScreen(WizardUtilAudioTest utilAudioTest) {
        super(WizardScreenEnum.WizardAudioTestScreen, utilAudioTest.getTitle(), utilAudioTest.getMenuLabel(), utilAudioTest.getTitle());
        this.setScreenText(null);
        this.wizardScreenData.setScreenText(0, utilAudioTest.getText());
        this.wizardScreenData.setScreenText(1, utilAudioTest.getBackgroundImage());
        this.wizardScreenData.setScreenText(2, utilAudioTest.getAudioHotkey());
        this.wizardScreenData.setScreenText(3, utilAudioTest.getHotkey());
        this.wizardScreenData.setScreenText(4, utilAudioTest.getImageStyle());
        this.wizardScreenData.setScreenText(5, utilAudioTest.getButonStyle());
        this.wizardScreenData.setScreenText(6, utilAudioTest.getBackgroundStyle());
        this.wizardScreenData.setScreenText(7, utilAudioTest.getImageName());
        this.wizardScreenData.setScreenBoolean(0, utilAudioTest.isAutoPlay());
        this.wizardScreenData.setScreenBoolean(1, utilAudioTest.isAutoNext());
        this.wizardScreenData.setScreenIntegers(0, utilAudioTest.getBackgroundMs());
        this.setNextButton(utilAudioTest.getButonLabel());
        this.wizardScreenData.setScreenMediaPath(utilAudioTest.getAudioFile());
    }

    @Override
    public String getScreenBooleanInfo(int index) {
        return new String[]{"Auto Play", "Auto Next"}[index];
    }

    @Override
    public String getScreenTextInfo(int index) {
        return new String[]{"Audio Test Instructions", "Background Image", "AudioHotKey", "NextHotKey", "ImageStyle", "Button Style", "Background Style", "Image"}[index];
    }

    @Override
    public String getNextButtonInfo(int index) {
        return new String[]{"Audio Confirmation Button Label"}[index];
    }

    @Override
    public String getScreenIntegerInfo(int index) {
        return new String[]{"AutoNextDelay"}[index];
    }
//    public String getAudioPath() {
//        return this.wizardScreenData
//    }

    public void setAudioPath(String audioPath) {
        this.wizardScreenData.setScreenMediaPath(audioPath);
    }

    private String getBackgroundImage(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(1);
    }

    public void setBackgroundImage(String backgroundImage) {
        this.wizardScreenData.setScreenText(1, backgroundImage);
    }

    private String getImageName(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(7);
    }

    public void setImageName(String backgroundImage) {
        this.wizardScreenData.setScreenText(7, backgroundImage);
    }

    private String getImageStyle(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(4);
    }

    public void setImageStyle(String backgroundImage) {
        this.wizardScreenData.setScreenText(4, backgroundImage);
    }

    private String getButtonStyle(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(5);
    }

    public void setButtonStyle(String backgroundImage) {
        this.wizardScreenData.setScreenText(5, backgroundImage);
    }

    private String getBackgroundStyle(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(6);
    }

    public void setBackgroundStyle(String backgroundStyle) {
        this.wizardScreenData.setScreenText(6, backgroundStyle);
    }

    private String getAudioHotKey(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(2);
    }

    public void setAudioHotKey(String hotKey) {
        this.wizardScreenData.setScreenText(2, hotKey);
    }

    private String getNextHotKey(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(3);
    }

    public void setNextHotKey(String hotKey) {
        this.wizardScreenData.setScreenText(3, hotKey);
    }

    private boolean getAutoPlay(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenBoolean(0);
    }

    public void setAutoNextDelay(int autoNextDelay) {
        this.wizardScreenData.setScreenIntegers(0, autoNextDelay);
    }

    private int getAutoNextDelay(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenInteger(0);
    }

    private boolean getAutoNext(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenBoolean(1);
    }

    public void setAutoPlay(boolean autoPlay) {
        this.wizardScreenData.setScreenBoolean(0, autoPlay);
    }

    public void setAutoNext(boolean autoNext) {
        this.wizardScreenData.setScreenBoolean(1, autoNext);
    }

//    String[] fieldNames = new String[]{"audioTestScreenText", "audioWorksButtonText", "testAudioPath"};
//    @Override
//    public String[] getFieldNames() {
//        return fieldNames;
//    }
//
//    @Override
//    public void setFieldValue(String fieldName, String fieldValue) {
//        namedFields.put(fieldName, fieldValue);
//    }
//
//    @Override
//    public String getFieldValue(String fieldName) {
//        return namedFields.get(fieldName);
//    }
    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, final Experiment experiment, final boolean obfuscateScreenNames, final long displayOrder) {
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
//        populatePresenterScreen(experiment, obfuscateScreenNames, displayOrder);
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.stimulus);
        final String backgroundImage = getBackgroundImage(storedWizardScreenData);
        final PresenterFeature backgoundFeature;
        if (backgroundImage != null && !backgroundImage.isEmpty()) {
            backgoundFeature = new PresenterFeature(FeatureType.backgroundImage, null);
            backgoundFeature.addFeatureAttributes(FeatureAttribute.msToNext, "1000");
            backgoundFeature.addFeatureAttributes(FeatureAttribute.styleName, getBackgroundStyle(storedWizardScreenData));
            backgoundFeature.addFeatureAttributes(FeatureAttribute.src, backgroundImage);
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(backgoundFeature);
        } else {
            backgoundFeature = null;
        }
        final PresenterFeature presenterFeatureOuter;
        final PresenterFeature presenterFeatureInner;
        if (storedWizardScreenData.getScreenMediaPath() != null) {
            PresenterFeature audioButton = new PresenterFeature(FeatureType.audioButton, null);
            audioButton.addFeatureAttributes(FeatureAttribute.eventTag, storedWizardScreenData.getScreenMediaPath());
            audioButton.addFeatureAttributes(FeatureAttribute.src, storedWizardScreenData.getScreenMediaPath());
            audioButton.addFeatureAttributes(FeatureAttribute.poster, "audiobutton.jpg");
            audioButton.addFeatureAttributes(FeatureAttribute.autoPlay, Boolean.toString(getAutoPlay(storedWizardScreenData)));
            audioButton.addFeatureAttributes(FeatureAttribute.hotKey, getAudioHotKey(storedWizardScreenData));
            PresenterFeature mediaLoaded = audioButton.addFeatures(FeatureType.mediaLoaded, FeatureType.mediaLoadFailed, FeatureType.mediaPlaybackComplete)[2];
            if (getButtonStyle(storedWizardScreenData) != null) {
                audioButton.addFeatureAttributes(FeatureAttribute.styleName, getButtonStyle(storedWizardScreenData));
            }
            if (getImageName(storedWizardScreenData) != null) {
                mediaLoaded.addFeature(FeatureType.backgroundImage, null, "0", getImageName(storedWizardScreenData), getImageStyle(storedWizardScreenData));
            }
            presenterFeatureInner = mediaLoaded;
            presenterFeatureOuter = audioButton;
        } else {
            presenterFeatureOuter = new PresenterFeature(FeatureType.backgroundImage, null);
            presenterFeatureOuter.addFeatureAttributes(FeatureAttribute.src, getImageName(storedWizardScreenData));
            presenterFeatureOuter.addFeatureAttributes(FeatureAttribute.styleName, getImageStyle(storedWizardScreenData));
            presenterFeatureOuter.addFeatureAttributes(FeatureAttribute.msToNext, "0");
            presenterFeatureInner = presenterFeatureOuter;
        }
        if (storedWizardScreenData.getScreenMediaPath() == null && backgoundFeature != null) {
//            final PresenterFeature clearBackgroundImage = backgoundFeature.addFeature(FeatureType.backgroundImage, null, "0", "", "");
            backgoundFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(0)));
            backgoundFeature.getPresenterFeatureList().add(presenterFeatureOuter);
        } else {
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(0)));
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(presenterFeatureOuter);
        }
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        if (getAutoNext(storedWizardScreenData)) {
            final PresenterFeature pauseFeature = new PresenterFeature(FeatureType.pause, null);
            pauseFeature.addFeatureAttributes(FeatureAttribute.msToNext, Integer.toString(getAutoNextDelay(storedWizardScreenData)));
            presenterFeatureInner.getPresenterFeatureList().add(pauseFeature);
            pauseFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.gotoNextPresenter, null));
        } else {
            final PresenterFeature actionButtonFeature = new PresenterFeature(FeatureType.actionButton, storedWizardScreenData.getNextButton()[0]);
            if (getNextHotKey(storedWizardScreenData) != null) {
                actionButtonFeature.addFeatureAttributes(FeatureAttribute.hotKey, getNextHotKey(storedWizardScreenData));
            }
            if (getButtonStyle(storedWizardScreenData) != null) {
                final PresenterFeature tableFeature = new PresenterFeature(FeatureType.table, null);
                tableFeature.addFeatureAttributes(FeatureAttribute.styleName, getButtonStyle(storedWizardScreenData));
                tableFeature.addFeature(FeatureType.row, null).addFeature(FeatureType.column, null, "").getPresenterFeatureList().add(actionButtonFeature);
                presenterFeatureInner.getPresenterFeatureList().add(tableFeature);
            } else {
                presenterFeatureInner.getPresenterFeatureList().add(actionButtonFeature);
            }
            actionButtonFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.gotoNextPresenter, null));
        }
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }
}
