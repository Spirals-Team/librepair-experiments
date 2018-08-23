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
import nl.mpi.tg.eg.experimentdesigner.model.Stimulus;

/**
 * @since Jun 16, 2017 10:30:28 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardStimuliJsonMetadataScreen extends AbstractWizardScreen {

    public WizardStimuliJsonMetadataScreen(String screenTitle, String menuLabel, String screenTag) {
        super(WizardScreenEnum.WizardStimuliJsonMetadataScreen, screenTitle, menuLabel, screenTag);
    }

    public WizardStimuliJsonMetadataScreen() {
        super(WizardScreenEnum.WizardStimuliJsonMetadataScreen, "JsonMetadata", "JsonMetadata", "JsonMetadata");
    }

//    public WizardStimuliJsonMetadataScreen(final String screenTitle, final String screenTag, String dispalyText, final String saveButtonLabel, final String postText, final AbstractWizardScreen alternateNextScreen, final String alternateButtonLabel, final boolean sendData, final String on_Error_Text) {
//        super(WizardScreenEnum.WizardStimuliJsonMetadataScreen, screenTitle, screenTitle, screenTag);
//        this.wizardScreenData.setScreenText(0, dispalyText);
//        this.wizardScreenData.setNextButton(new String[]{saveButtonLabel, alternateButtonLabel});
//        this.wizardScreenData.setScreenText(1, postText);
//        this.wizardScreenData.setScreenBoolean(0, sendData);
//        this.wizardScreenData.setOn_Error_Text(on_Error_Text);
//        if (alternateNextScreen != null) {
//            this.wizardScreenData.getMenuWizardScreenData().add(0, alternateNextScreen.getWizardScreenData());
//        }
//    }
    public WizardStimuliJsonMetadataScreen(String[] metadataStrings) {
        super(WizardScreenEnum.WizardStimuliJsonMetadataScreen, "JsonMetadata", "JsonMetadata", "JsonMetadata");
        final List<Stimulus> stimuliList = new ArrayList<>();
        final HashSet<String> tagSet = new HashSet<>(Arrays.asList(new String[]{"metadata"}));
        for (String metadataString : metadataStrings) {
            final Stimulus stimulus = new Stimulus(metadataString, null, null, metadataString, metadataString, null, 0, tagSet, null, null);
            stimuliList.add(stimulus);
        }
        this.wizardScreenData.setStimuli(stimuliList);
        setUseSdCard(true);
    }

    final public void setUseSdCard(boolean useSdCard) {
        this.wizardScreenData.setScreenBoolean(0, useSdCard);
    }

    private boolean useSdCard(WizardScreenData wizardScreenData) {
        return wizardScreenData.getScreenBoolean(0);
    }

    @Override
    public String getScreenBooleanInfo(int index) {
        return new String[]{"SD Card Stimuli"}[index];
    }

    @Override
    public String getScreenTextInfo(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getScreenIntegerInfo(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNextButtonInfo(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addStimuliMetadataField(String postName, String displayName) {
        wizardScreenData.getMetadataFields().add(new Metadata(postName, displayName, ".*", "", false, null));
    }

    public void addStimuliBooleanMetadataField(String postName, String displayName) {
        wizardScreenData.getMetadataFields().add(new Metadata(postName, displayName, "true|false", "Please enter true or false.", false, null));
    }

    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.stimulus);
        experiment.appendUniqueStimuli(storedWizardScreenData.getStimuli());
        if (storedWizardScreenData.getScreenText(0) != null) {
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(0)));
        }
        final PresenterFeature loadStimuliFeature = (useSdCard(storedWizardScreenData)) ? new PresenterFeature(FeatureType.loadSdCardStimulus, null) : new PresenterFeature(FeatureType.loadStimulus, null);
        loadStimuliFeature.addStimulusTag("metadata");
//        if (useSdCard(storedWizardScreenData)) {
//            loadStimuliFeature.addFeatureAttributes(FeatureAttribute.excludeRegex, ".*_question\\....$");
//        }
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.eventTag, "Metadata");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.randomise, "false");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatCount, "1");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatRandomWindow, "0");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.maxStimuli, "1000");
        storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(loadStimuliFeature);
        final PresenterFeature hasMoreStimulusFeature = new PresenterFeature(FeatureType.hasMoreStimulus, null);
        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.centrePage, null));
        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.showStimulusProgress, null));
        PresenterFeature presenterFeatureTable = new PresenterFeature(FeatureType.table, null);
        hasMoreStimulusFeature.getPresenterFeatureList().add(presenterFeatureTable);
        PresenterFeature presenterFeatureRow = new PresenterFeature(FeatureType.row, null);
        presenterFeatureTable.getPresenterFeatureList().add(presenterFeatureRow);
        int columnCounter = 0;
        for (Metadata metadata : storedWizardScreenData.getMetadataFields()) {
            if (columnCounter > 3) {
                presenterFeatureRow = new PresenterFeature(FeatureType.row, null);
                presenterFeatureTable.getPresenterFeatureList().add(presenterFeatureRow);
                columnCounter = 0;
            }
            if (!experiment.getMetadata().contains(metadata)) {
                experiment.getMetadata().add(metadata);
            }
            // todo: this metadataFieldConnection use needs to be replaced with wizard parameters
            final PresenterFeature metadataField = new PresenterFeature((metadata.getPostName().startsWith("connection")) ? FeatureType.metadataFieldConnection : FeatureType.stimulusMetadataField, null);
            if (metadata.getPostName().startsWith("connection")) {
                metadataField.addFeatureAttributes(FeatureAttribute.linkedFieldName, "workerId");
            }
            metadataField.addFeatureAttributes(FeatureAttribute.fieldName, metadata.getPostName());
            final PresenterFeature presenterFeatureColumn = new PresenterFeature(FeatureType.column, null);
            presenterFeatureRow.getPresenterFeatureList().add(presenterFeatureColumn);
            presenterFeatureColumn.getPresenterFeatureList().add(metadataField);
            columnCounter++;
        }
        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.stimulusLabel, null));
        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.showStimulusProgress, null));
        PresenterFeature presenterFeatureTableS = new PresenterFeature(FeatureType.table, null);
        hasMoreStimulusFeature.getPresenterFeatureList().add(presenterFeatureTableS);
        PresenterFeature stimulusFeatureRow = new PresenterFeature(FeatureType.row, null);
        presenterFeatureTableS.getPresenterFeatureList().add(stimulusFeatureRow);
        final PresenterFeature contextFeature = new PresenterFeature(FeatureType.stimulusPresent, null);
        contextFeature.addFeatureAttributes(FeatureAttribute.maxHeight, "80");
        contextFeature.addFeatureAttributes(FeatureAttribute.maxWidth, "80");
        contextFeature.addFeatureAttributes(FeatureAttribute.percentOfPage, "80");
        contextFeature.addFeatureAttributes(FeatureAttribute.animate, "none");
        contextFeature.addFeatureAttributes(FeatureAttribute.replacementRegex, "(\\\\.[^\\\\.]*)$");
        contextFeature.addFeatureAttributes(FeatureAttribute.replacement, "_context$1");
        contextFeature.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        contextFeature.addFeatureAttributes(FeatureAttribute.showControls, "false");
        contextFeature.addFeatures(FeatureType.mediaLoaded, FeatureType.mediaLoadFailed, FeatureType.mediaPlaybackComplete);
        final PresenterFeature contextFeatureColumn = new PresenterFeature(FeatureType.column, null);
        stimulusFeatureRow.getPresenterFeatureList().add(contextFeatureColumn);
        contextFeatureColumn.getPresenterFeatureList().add(contextFeature);
        final PresenterFeature imageFeature = new PresenterFeature(FeatureType.stimulusPresent, null);
        imageFeature.addFeatureAttributes(FeatureAttribute.maxHeight, "80");
        imageFeature.addFeatureAttributes(FeatureAttribute.maxWidth, "80");
        imageFeature.addFeatureAttributes(FeatureAttribute.percentOfPage, "80");
        imageFeature.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        imageFeature.addFeatureAttributes(FeatureAttribute.animate, "none");
        imageFeature.addFeatureAttributes(FeatureAttribute.showControls, "false");
        imageFeature.addFeatures(FeatureType.mediaLoaded, FeatureType.mediaLoadFailed, FeatureType.mediaPlaybackComplete);
        final PresenterFeature imageFeatureColumn = new PresenterFeature(FeatureType.column, null);
        stimulusFeatureRow.getPresenterFeatureList().add(imageFeatureColumn);
        imageFeatureColumn.getPresenterFeatureList().add(imageFeature);

        PresenterFeature presenterFeatureTableB = new PresenterFeature(FeatureType.table, null);
        hasMoreStimulusFeature.getPresenterFeatureList().add(presenterFeatureTableB);
        PresenterFeature buttonsFeatureRow = new PresenterFeature(FeatureType.row, null);
        final PresenterFeature prevFeatureColumn = new PresenterFeature(FeatureType.column, null);
        buttonsFeatureRow.getPresenterFeatureList().add(prevFeatureColumn);
        final PresenterFeature nextFeatureColumn = new PresenterFeature(FeatureType.column, null);
        buttonsFeatureRow.getPresenterFeatureList().add(nextFeatureColumn);
        presenterFeatureTableB.getPresenterFeatureList().add(buttonsFeatureRow);
        final PresenterFeature stimulusExistsPrev = prevFeatureColumn.addFeature(FeatureType.stimulusExists, null, "-1");
        stimulusExistsPrev.addFeature(FeatureType.conditionTrue, null).addFeature(FeatureType.prevStimulusButton, "Previous", "prevStimulusButton", "false", "-1", "");
        stimulusExistsPrev.addFeature(FeatureType.conditionFalse, null);
        final PresenterFeature stimulusExistsNext = nextFeatureColumn.addFeature(FeatureType.stimulusExists, null, "+1");
        stimulusExistsNext.addFeature(FeatureType.conditionTrue, null).addFeature(FeatureType.nextStimulusButton, "Next", "nextStimulusButton", "false", "-1", "");
        stimulusExistsNext.addFeature(FeatureType.conditionFalse, null);
        loadStimuliFeature.getPresenterFeatureList().add(hasMoreStimulusFeature);
        final PresenterFeature endOfStimulusFeature = new PresenterFeature(FeatureType.endOfStimulus, null);
        final PresenterFeature htmlText = new PresenterFeature(FeatureType.htmlText, "End of stimuli");
        endOfStimulusFeature.getPresenterFeatureList().add(htmlText);
        loadStimuliFeature.getPresenterFeatureList().add(endOfStimulusFeature);
        if (storedWizardScreenData.getScreenText(1) != null && storedWizardScreenData.getMenuWizardScreenData().size() > 0) {
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
            if (storedWizardScreenData.getScreenText(1) != null) {
                storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(1)));
            }
            if (storedWizardScreenData.getMenuWizardScreenData().size() > 0) {
                final PresenterFeature alternateNextFeature = new PresenterFeature(FeatureType.targetButton, storedWizardScreenData.getNextButton()[1]);
                alternateNextFeature.addFeatureAttributes(FeatureAttribute.target, storedWizardScreenData.getMenuWizardScreenData().get(0).getScreenTag());
                storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(alternateNextFeature);
            }
        }
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }
}
