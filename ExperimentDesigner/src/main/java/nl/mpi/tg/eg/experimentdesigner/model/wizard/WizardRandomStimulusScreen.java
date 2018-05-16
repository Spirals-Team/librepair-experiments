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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
 * @since May 11, 2016 5:08:41 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardRandomStimulusScreen extends AbstractWizardScreen {

    public WizardRandomStimulusScreen() {
        super(WizardScreenEnum.WizardRandomStimulusScreen, "RandomStimulus", "RandomStimulus", "RandomStimulus");
        setRandomiseStimuli(false);
        this.wizardScreenData.setStimuliCount(1);
        setStimulusMsDelay(0);
        setStimulusFreeText(false);
        setHotkeyButton("SPACE");
        setTableLayout(false);
        setShowProgress(false);
        setStimuliLabelStyle(null);
//        this.wizardScreenData.setButtonLabelEventTag("");
        this.wizardScreenData.setCentreScreen(true);
    }

    public WizardRandomStimulusScreen(final WizardUtilStimuliData stimuliData) {
        super(WizardScreenEnum.WizardRandomStimulusScreen, stimuliData.getStimuliName(), stimuliData.getStimuliName(), stimuliData.getStimuliName());
        this.setScreenTitle(stimuliData.getStimuliName());
        this.wizardScreenData.setStimuliRandomTags(stimuliData.getRandomStimuliTags());
        setRandomStimuliTagsField("");
        this.wizardScreenData.setStimulusCodeMatch(null);
        this.wizardScreenData.setStimulusCodeMsDelay(0);
        setStimulusMsDelay(0);
        this.wizardScreenData.setStimulusCodeFormat(null);
        this.wizardScreenData.setStimuliCount(1000);
        this.wizardScreenData.setStimulusResponseLabelLeft(null);
        this.wizardScreenData.setStimulusResponseLabelRight(null);
        this.wizardScreenData.setStimulusResponseOptions(null);
        setRandomiseStimuli(true);
        this.wizardScreenData.setCentreScreen(false);
        setHotkeyButton("SPACE");
        setShowProgress(false);
        setStimulusFreeText(false);
        setTableLayout(false);
        String spacebar = "Volgende [tab + enter]";
        if (spacebar == null) {
            throw new UnsupportedOperationException("button text cannot be null");
        }
        setButtonLabel(spacebar);
        setStimuliLabelStyle(null);
//        this.wizardScreenData.setButtonLabelEventTag(spacebar);
        setStimuliSet(stimuliData.getStimuliArray());
        if ("horizontal".equals(stimuliData.getStimuliLayout())) {
            setTableLayout(true);
        }
    }

    public WizardRandomStimulusScreen(String screenName, boolean centreScreen, String[] stimuliStringArray, String[] randomStimuliTags, int maxStimuli, final boolean randomiseStimuli, String stimulusCodeMatch, int stimulusDelay, int codeStimulusDelay, String codeFormat, String responseOptions, String responseOptionsLabelLeft, String responseOptionsLabelRight, final String spacebar) {
        super(WizardScreenEnum.WizardRandomStimulusScreen, screenName, screenName, screenName);
        this.setScreenTitle(screenName);
        this.wizardScreenData.setStimuliRandomTags(randomStimuliTags);
        setRandomStimuliTagsField("");
        this.wizardScreenData.setStimulusCodeMatch(stimulusCodeMatch);
        this.wizardScreenData.setStimulusCodeMsDelay(0);
        setStimulusMsDelay(0);
        this.wizardScreenData.setStimulusCodeFormat(codeFormat);
        this.wizardScreenData.setStimuliCount(maxStimuli);
        this.wizardScreenData.setStimulusResponseLabelLeft(responseOptionsLabelLeft);
        this.wizardScreenData.setStimulusResponseLabelRight(responseOptionsLabelRight);
        this.wizardScreenData.setStimulusResponseOptions(responseOptions);
        setRandomiseStimuli(randomiseStimuli);
        this.wizardScreenData.setCentreScreen(centreScreen);
        setHotkeyButton("SPACE");
        setShowProgress(false);
        setStimulusFreeText(false);
        setTableLayout(false);
        if (spacebar == null) {
            throw new UnsupportedOperationException("button text cannot be null");
        }
        setButtonLabel(spacebar);
        setStimuliLabelStyle(null);
//        this.wizardScreenData.setButtonLabelEventTag(spacebar);
        setStimuliSet(stimuliStringArray);
    }

    public WizardRandomStimulusScreen(String screenName, String[] screenTextArray, int maxStimuli, final boolean randomiseStimuli, String responseOptions, String responseOptionsLabelLeft, String responseOptionsLabelRight) {
        super(WizardScreenEnum.WizardRandomStimulusScreen, screenName, screenName, screenName);
        this.setScreenTitle(screenName);
        this.wizardScreenData.setStimulusCodeMsDelay(0);
        setStimulusMsDelay(0);
        this.wizardScreenData.setStimuliCount(maxStimuli);
        this.wizardScreenData.setStimulusResponseLabelLeft(responseOptionsLabelLeft);
        this.wizardScreenData.setStimulusResponseLabelRight(responseOptionsLabelRight);
        this.wizardScreenData.setStimulusResponseOptions(responseOptions);
        setRandomiseStimuli(randomiseStimuli);
        this.wizardScreenData.setCentreScreen(true);
        setStimulusFreeText(false);
        setHotkeyButton("SPACE");
        setShowProgress(false);
        setTableLayout(false);
        setStimuliSet(screenTextArray);
    }

    final public void setRandomiseStimuli(boolean randomiseStimuli) {
        this.wizardScreenData.setScreenBoolean(0, randomiseStimuli);
    }

    final public void setStimulusFreeText(boolean stimulusFreeText) {
        this.wizardScreenData.setScreenBoolean(1, stimulusFreeText);
    }

    final public void setHotkeyButton(String hotkeyButton) {
        this.wizardScreenData.setScreenText(6, hotkeyButton);
    }

    final public void setShowProgress(boolean showProgress) {
        this.wizardScreenData.setScreenBoolean(2, showProgress);
    }

    final public void setTableLayout(boolean isTableLayout) {
        this.wizardScreenData.setScreenBoolean(3, isTableLayout);
    }

    private boolean isRandomiseStimuli(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenBoolean(0);
    }

    private boolean isStimulusFreeText(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenBoolean(1);
    }

    private String getHotkeyButton(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(6);
    }

    private boolean isShowProgress(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenBoolean(2);
    }

    private boolean isTableLayout(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenBoolean(3);
    }
//    private boolean getFreeTextValidationRegex(WizardScreenData wizardScreenData) {
//
//    }
//
//    private boolean getExcludedCharCodes(WizardScreenData wizardScreenData) {
//
//    }

    @Override
    public String getScreenBooleanInfo(int index) {
        return new String[]{"RandomiseStimuli", "StimulusFreeText", "ShowProgress", "TableLayout"}[index];
    }

    @Override
    public String getScreenTextInfo(int index) {
        return new String[]{"FreeTextValidationMessage", "FreeTextValidationRegex", "RandomStimuliTagsField", "InputKeyErrorMessage", "AllowedCharCodes (case sensitive, input will be switched to an allowed case if it exists)", "Stimuli Label Style", "HotkeyButton"}[index];
    }

    @Override
    public String getNextButtonInfo(int index) {
        return new String[]{"Next Button Label"}[index];
    }

    private String getInputKeyErrorMessage(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(3);
    }

    final public void setInputKeyErrorMessage(String inputErrorMessage) {
        this.wizardScreenData.setScreenText(3, inputErrorMessage);
    }

    private String getAllowedCharCodes(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(4);
    }

    final public void setAllowedCharCodes(String allowedCharCodes) {
        this.wizardScreenData.setScreenText(4, allowedCharCodes);
    }

    final public void setStimuliLabelStyle(String stimuliLabelStyle) {
        this.wizardScreenData.setScreenText(5, stimuliLabelStyle);
    }

    final public String getStimuliLabelStyle(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(5);
    }

    private int getStimulusMsDelay(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenInteger(0);
    }

    final public void setStimulusMsDelay(int stimulusMsDelay) {
        this.wizardScreenData.setScreenIntegers(0, stimulusMsDelay);
    }

    @Override
    public String getScreenIntegerInfo(int index) {
        return new String[]{"Stimulus Ms Delay"}[index];
    }
//    public void setStimuliPath(String stimuliPath) {
//        this.stimuliPath = stimuliPath;
//    }

    public final void setStimuliSet(String[] stimuliSet) {
        if (this.wizardScreenData.getStimuli() == null) {
            this.wizardScreenData.setStimuli(new ArrayList<>());
        }
        final List<Stimulus> stimuliList = this.wizardScreenData.getStimuli();
        final Pattern stimulusCodePattern = (wizardScreenData.getStimulusCodeMatch() != null) ? Pattern.compile(wizardScreenData.getStimulusCodeMatch()) : null;
        if (stimuliSet != null) {
            for (String stimulusLine : stimuliSet) {
                final HashSet<String> tagSet = new HashSet<>(Arrays.asList(new String[]{this.wizardScreenData.getScreenTitle()}));
                final Stimulus stimulus;
                if (stimulusCodePattern != null) {
//                    System.out.println("stimulusCodeMatch:" + wizardScreenData.getStimulusCodeMatch());
                    Matcher matcher = stimulusCodePattern.matcher(stimulusLine);
                    final String codeString = (matcher.find()) ? matcher.group(1) : null;
//                    System.out.println("codeString: " + codeString);
                    final String baseFileName = stimulusLine.replaceAll(BASE_FILE_REGEX, "");
                    tagSet.addAll(Arrays.asList(baseFileName.split("/")));
                    stimulus = new Stimulus(baseFileName, null, null, stimulusLine, null, codeString, 0, tagSet, null, null);
                } else if (stimulusLine.endsWith(".png")) {
                    tagSet.addAll(Arrays.asList(stimulusLine.split("/")));
                    stimulus = new Stimulus(stimulusLine.replace(".png", ""), null, null, stimulusLine, null, null, 0, tagSet, null, null);
                } else {
                    final String[] splitScreenText = stimulusLine.split(":", 2);
                    tagSet.addAll(Arrays.asList(splitScreenText[0].split("/")));
                    final String substring = (stimulusLine.length() < 55) ? stimulusLine : stimulusLine.substring(0, 54);
                    stimulus = new Stimulus(substring, null, null, null, splitScreenText[1].replace("\n", "<br/>"), null/*splitScreenText[0].replace(" ", "_").replace("/", "_")*/, 0, tagSet, null, null);
                }
                stimuliList.add(stimulus);
            }
        }
    }

//    private String getInputErrorMessage(WizardScreenData storedWizardScreenData) {
//        return storedWizardScreenData.getScreenText(0);
//    }
//
//    final public void setInputErrorMessage(String inputErrorMessage) {
//        this.wizardScreenData.setScreenText(0, inputErrorMessage);
//    }
    private String getFreeTextValidationMessage(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(0);
    }

    final public void setFreeTextValidationMessage(String freeTextValidationMessage) {
        this.wizardScreenData.setScreenText(0, freeTextValidationMessage);
    }

    private String getFreeTextValidationRegex(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(1);
    }

    final public void setFreeTextValidationRegex(String freeTextValidationRegex) {
        this.wizardScreenData.setScreenText(1, freeTextValidationRegex);
    }

    final public void setRandomStimuliTagsField(String fieldName) {
        this.wizardScreenData.setScreenText(2, fieldName);
    }

    private String getRandomStimuliTagsField(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenText(2);
    }

    public void setStimulusFreeText(boolean stimulusFreeText, String freeTextValidationRegex, String freeTextValidationMessage) {
        setStimulusFreeText(stimulusFreeText);
        setFreeTextValidationRegex(freeTextValidationRegex);
        setFreeTextValidationMessage(freeTextValidationMessage);
    }

    final public void setButtonLabel(String buttonLabelEventTag) {
        this.wizardScreenData.setNextButton(new String[]{buttonLabelEventTag});
        this.wizardScreenData.setButtonLabelEventTag(buttonLabelEventTag);
    }

    private static final String BASE_FILE_REGEX = "\\.[a-zA-Z34]+$";

    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        experiment.appendUniqueStimuli(storedWizardScreenData.getStimuli());
//        final PresenterScreen presenterScreen = new PresenterScreen((obfuscateScreenNames) ? experiment.getAppNameDisplay() + " " + displayOrder : getScreenTitle(), getScreenTitle(), backPresenter, screenName, null, PresenterType.stimulus, displayOrder);
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.stimulus);
        List<PresenterFeature> presenterFeatureList = storedWizardScreenData.getPresenterScreen().getPresenterFeatureList();
        if (storedWizardScreenData.isCentreScreen()) {
            presenterFeatureList.add(new PresenterFeature(FeatureType.centrePage, null));
        }
        final PresenterFeature loadStimuliFeature = new PresenterFeature(FeatureType.loadStimulus, null);
        loadStimuliFeature.addStimulusTag(storedWizardScreenData.getScreenTitle());
        final RandomGrouping randomGrouping = new RandomGrouping();
        if (storedWizardScreenData.getStimuliRandomTags() != null) {
            for (String randomTag : storedWizardScreenData.getStimuliRandomTags()) {
                randomGrouping.addRandomTag(randomTag);
            }
            String metadataFieldname = getRandomStimuliTagsField(storedWizardScreenData);
            metadataFieldname = (metadataFieldname == null || metadataFieldname.isEmpty()) ? "groupAllocation_" + storedWizardScreenData.getScreenTag() : metadataFieldname;
            randomGrouping.setStorageField(metadataFieldname);
            loadStimuliFeature.setRandomGrouping(randomGrouping);
            final Metadata metadataField = new Metadata(metadataFieldname, metadataFieldname, ".*", ".", false, null);
            if (!experiment.getMetadata().contains(metadataField)) {
                experiment.getMetadata().add(metadataField);
            }
        }
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.eventTag, storedWizardScreenData.getScreenTitle());
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.randomise, Boolean.toString(isRandomiseStimuli(storedWizardScreenData)));
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatCount, "1");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatRandomWindow, "0");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.maxStimuli, Integer.toString(storedWizardScreenData.getStimuliCount()));
        presenterFeatureList.add(loadStimuliFeature);
        final PresenterFeature hasMoreStimulusFeature = new PresenterFeature(FeatureType.hasMoreStimulus, null);
        if (isShowProgress(storedWizardScreenData)) {
            hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.showStimulusProgress, null));
            hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
        }
        final String stimuliLabelStyle = getStimuliLabelStyle(storedWizardScreenData);
        final PresenterFeature stimuliFeature;
        final PresenterFeature tableFeature = (isTableLayout(storedWizardScreenData)) ? hasMoreStimulusFeature.addFeature(FeatureType.table, null, "", "false").addFeature(FeatureType.row, null) : null;
        if (stimuliLabelStyle != null) {
            final PresenterFeature labelFeature = new PresenterFeature(FeatureType.stimulusLabel, null);
            labelFeature.addFeatureAttributes(FeatureAttribute.styleName, stimuliLabelStyle);
            if (tableFeature != null) {
                tableFeature.addFeature(FeatureType.column, null, "").getPresenterFeatureList().add(labelFeature);
            } else {
                hasMoreStimulusFeature.getPresenterFeatureList().add(labelFeature);
            }
            stimuliFeature = hasMoreStimulusFeature;// image features cannot have child nodes
        } else {
            final PresenterFeature imageFeature = new PresenterFeature(FeatureType.stimulusImage, null);
            imageFeature.addFeatureAttributes(FeatureAttribute.maxHeight, "80");
            imageFeature.addFeatureAttributes(FeatureAttribute.maxWidth, "80");
            imageFeature.addFeatureAttributes(FeatureAttribute.percentOfPage, "0");
            imageFeature.addFeatureAttributes(FeatureAttribute.msToNext, Integer.toString(getStimulusMsDelay(storedWizardScreenData)));
            if (tableFeature != null) {
                tableFeature.addFeature(FeatureType.column, null, "").getPresenterFeatureList().add(imageFeature);
            } else {
                hasMoreStimulusFeature.getPresenterFeatureList().add(imageFeature);
            }
            stimuliFeature = imageFeature;
        }

        final PresenterFeature presenterFeature;
        if (storedWizardScreenData.getStimulusCodeFormat() != null) {
            final PresenterFeature nextButtonFeature = new PresenterFeature(FeatureType.actionButton, storedWizardScreenData.getNextButton()[0]);
            nextButtonFeature.addFeatureAttributes(FeatureAttribute.eventTag, storedWizardScreenData.getButtonLabelEventTag());
            if (getHotkeyButton(storedWizardScreenData) != null) {
                nextButtonFeature.addFeatureAttributes(FeatureAttribute.hotKey, getHotkeyButton(storedWizardScreenData));
            }
            nextButtonFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
            final PresenterFeature pauseFeature = new PresenterFeature(FeatureType.pause, null);
            pauseFeature.addFeatureAttributes(FeatureAttribute.msToNext, Integer.toString(storedWizardScreenData.getStimulusCodeMsDelay()));
            nextButtonFeature.getPresenterFeatureList().add(pauseFeature);
            final PresenterFeature stimulusCodeAudio = new PresenterFeature(FeatureType.stimulusCodeAudio, null);
            stimulusCodeAudio.addFeatureAttributes(FeatureAttribute.showPlaybackIndicator, "false");
            stimulusCodeAudio.addFeatureAttributes(FeatureAttribute.codeFormat, storedWizardScreenData.getStimulusCodeFormat());
            stimulusCodeAudio.addFeatureAttributes(FeatureAttribute.msToNext, "0");
            pauseFeature.getPresenterFeatureList().add(stimulusCodeAudio);
            stimuliFeature.getPresenterFeatureList().add(nextButtonFeature);
            stimulusCodeAudio.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
            stimulusCodeAudio.getPresenterFeatureList().add(new PresenterFeature(FeatureType.plainText, "Het antwoord is:"));
            final PresenterFeature logTImeStamp = new PresenterFeature(FeatureType.logTimeStamp, null);
            logTImeStamp.addFeatureAttributes(FeatureAttribute.eventTag, "hetAntwoordIs");
            stimulusCodeAudio.getPresenterFeatureList().add(logTImeStamp);
//            stimulusCodeAudio.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//            stimulusCodeAudio.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//            stimulusCodeAudio.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//            stimulusCodeAudio.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, "<style=\"text-align: left;\">zeer waarschijnlijk negatief</style><style=\"text-align: right;\">zeer waarschijnlijk positief</style>"));
            presenterFeature = stimulusCodeAudio;
        } else {
            presenterFeature = stimuliFeature;
        }
        presenterFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
        if (isStimulusFreeText(storedWizardScreenData)) {
            final PresenterFeature stimulusFreeTextFeature = new PresenterFeature(FeatureType.stimulusFreeText, getFreeTextValidationMessage(storedWizardScreenData));
            if (stimuliLabelStyle != null) {
                stimulusFreeTextFeature.addFeatureAttributes(FeatureAttribute.styleName, stimuliLabelStyle);
            }
            stimulusFreeTextFeature.addFeatureAttributes(FeatureAttribute.validationRegex, getFreeTextValidationRegex(storedWizardScreenData));
            if (getAllowedCharCodes(storedWizardScreenData) != null) {
                stimulusFreeTextFeature.addFeatureAttributes(FeatureAttribute.allowedCharCodes, getAllowedCharCodes(storedWizardScreenData));
            }
            if (getInputKeyErrorMessage(storedWizardScreenData) != null) {
                stimulusFreeTextFeature.addFeatureAttributes(FeatureAttribute.inputErrorMessage, getInputKeyErrorMessage(storedWizardScreenData));
            }
            if (getHotkeyButton(storedWizardScreenData) != null) {
                stimulusFreeTextFeature.addFeatureAttributes(FeatureAttribute.hotKey, getHotkeyButton(storedWizardScreenData));
            }
            if (tableFeature != null) {
                tableFeature.addFeature(FeatureType.column, "", "").getPresenterFeatureList().add(stimulusFreeTextFeature);
            } else {
                presenterFeature.getPresenterFeatureList().add(stimulusFreeTextFeature);
            }
        }
        if (storedWizardScreenData.getStimulusResponseOptions() != null) {
            final PresenterFeature ratingFooterButtonFeature = new PresenterFeature(FeatureType.ratingButton, null);
            ratingFooterButtonFeature.addFeatureAttributes(FeatureAttribute.ratingLabels, storedWizardScreenData.getStimulusResponseOptions());
            ratingFooterButtonFeature.addFeatureAttributes(FeatureAttribute.ratingLabelLeft, storedWizardScreenData.getStimulusResponseLabelLeft());
            ratingFooterButtonFeature.addFeatureAttributes(FeatureAttribute.ratingLabelRight, storedWizardScreenData.getStimulusResponseLabelRight());
            ratingFooterButtonFeature.addFeatureAttributes(FeatureAttribute.eventTier, "1");
            final PresenterFeature nextStimulusFeature = new PresenterFeature(FeatureType.nextStimulus, null);
            nextStimulusFeature.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
            ratingFooterButtonFeature.getPresenterFeatureList().add(nextStimulusFeature);
            presenterFeature.getPresenterFeatureList().add(ratingFooterButtonFeature);
        } else {
            final PresenterFeature nextButtonFeature = new PresenterFeature(FeatureType.actionButton, storedWizardScreenData.getNextButton()[0]);
            nextButtonFeature.addFeatureAttributes(FeatureAttribute.eventTag, storedWizardScreenData.getButtonLabelEventTag());
            if (getHotkeyButton(storedWizardScreenData) != null) {
                nextButtonFeature.addFeatureAttributes(FeatureAttribute.hotKey, getHotkeyButton(storedWizardScreenData));
            }
            final PresenterFeature nextStimulusFeature = new PresenterFeature(FeatureType.nextStimulus, null);
            nextStimulusFeature.addFeatureAttributes(FeatureAttribute.repeatIncorrect, "false");
            nextButtonFeature.getPresenterFeatureList().add(nextStimulusFeature);
            presenterFeature.getPresenterFeatureList().add(nextButtonFeature);
        }
        loadStimuliFeature.getPresenterFeatureList().add(hasMoreStimulusFeature);

        final PresenterFeature endOfStimulusFeature = new PresenterFeature(FeatureType.endOfStimulus, null);
        final PresenterFeature autoNextPresenter = new PresenterFeature(FeatureType.autoNextPresenter, null);
        endOfStimulusFeature.getPresenterFeatureList().add(autoNextPresenter);
        loadStimuliFeature.getPresenterFeatureList().add(endOfStimulusFeature);
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }
}
