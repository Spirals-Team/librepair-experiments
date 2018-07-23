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
import java.util.HashSet;
import java.util.List;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.Stimulus;
import static nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardUtilStimuliData.StimuliFields.audio;
import static nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardUtilStimuliData.StimuliFields.correct;
import static nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardUtilStimuliData.StimuliFields.image;
import static nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardUtilStimuliData.StimuliFields.pause;
import static nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardUtilStimuliData.StimuliFields.rating;
import static nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardUtilStimuliData.StimuliFields.video;

/**
 * @since May 3, 2016 1:34:51 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public abstract class AbstractWizardScreen implements WizardScreen {

    protected final WizardScreenData wizardScreenData;

    public AbstractWizardScreen(WizardScreenEnum wizardScreenEnum) {
        this.wizardScreenData = new WizardScreenData(wizardScreenEnum);
        this.wizardScreenData.setCentreScreen(false);
    }

    public AbstractWizardScreen(WizardScreenEnum wizardScreenEnum, String screenTitle, String menuLabel, String screenTag) {
        this.wizardScreenData = new WizardScreenData(wizardScreenEnum);
        this.wizardScreenData.setScreenTitle(screenTitle);
        this.wizardScreenData.setMenuLabel(menuLabel);
        this.wizardScreenData.setScreenTag(screenTag.replaceAll("[^A-Za-z0-9]", "_"));
        this.wizardScreenData.setCentreScreen(false);
    }

    protected final void setStimuliSet(final WizardUtilStimuliData stimuliData, String... tagNames) {
        if (this.wizardScreenData.getStimuli() == null) {
            this.wizardScreenData.setStimuli(new ArrayList<>());
        }
        final List<Stimulus> stimuliList = this.wizardScreenData.getStimuli();
        for (String stimulusEntry : stimuliData.getStimuliArray()) {
            final HashSet<String> tagSet = new HashSet<>();
            tagSet.add(this.wizardScreenData.getScreenTitle());

            String[] stimuliParts = stimulusEntry.split(":", (stimuliData.getStimuliFields() != null) ? stimuliData.getStimuliFields().length + 1 : WizardUtilStimuliData.StimuliFields.values().length + 1);
            for (String tagName : stimuliParts[0].split("/")) {
                tagSet.add(tagName);
            }
            for (String tagName : tagNames) {
                tagSet.add(tagName);
            }
            String identifier = (stimulusEntry.length() < 55) ? stimulusEntry : stimulusEntry.substring(0, 54);;
            String audioPath = null;
            String videoPath = null;
            String imagePath = null;
            String label = null;
            String code = null;
            int pauseMs = 0;
            String ratingLabels = null;
            String correctResponses = null;
            if (stimuliData.getStimuliFields() != null) {
                int index = 1;
                for (WizardUtilStimuliData.StimuliFields stimuliField : stimuliData.getStimuliFields()) {
                    switch (stimuliField) {
                        case identifier:
                            identifier = (stimuliParts.length > index && !stimuliParts[index].isEmpty()) ? stimuliParts[index] : identifier;
                            break;
                        case audio:
                            audioPath = (stimuliParts.length > index && !stimuliParts[index].isEmpty()) ? stimuliParts[index] : null;
                            break;
                        case video:
                            videoPath = (stimuliParts.length > index && !stimuliParts[index].isEmpty()) ? stimuliParts[index] : null;
                            break;
                        case image:
                            imagePath = (stimuliParts.length > index && !stimuliParts[index].isEmpty()) ? stimuliParts[index] : null;
                            break;
                        case label:
                            label = (stimuliParts.length > index && !stimuliParts[index].isEmpty()) ? stimuliParts[index] : null;
                            break;
                        case code:
                            code = (stimuliParts.length > index && !stimuliParts[index].isEmpty()) ? stimuliParts[index] : null;
                            break;
                        case pause:
                            pauseMs = (stimuliParts.length > index && !stimuliParts[index].isEmpty()) ? Integer.parseInt(stimuliParts[index]) : 0;
                            break;
                        case rating:
                            ratingLabels = (stimuliParts.length > index && !stimuliParts[index].isEmpty()) ? stimuliParts[index] : null;
                            break;
                        case correct:
                            correctResponses = (stimuliParts.length > index && !stimuliParts[index].isEmpty()) ? stimuliParts[index] : null;
                            break;
                    }
                    index++;
                }
            } else {
                identifier = (stimuliParts.length > 1 && !stimuliParts[1].isEmpty()) ? stimuliParts[1] : stimulusEntry;
                audioPath = (stimuliParts.length > 2 && !stimuliParts[2].isEmpty()) ? stimuliParts[2] : null;
                videoPath = (stimuliParts.length > 3 && !stimuliParts[3].isEmpty()) ? stimuliParts[3] : null;
                imagePath = (stimuliParts.length > 4 && !stimuliParts[4].isEmpty()) ? stimuliParts[4] : null;
                label = (stimuliParts.length > 5 && !stimuliParts[5].isEmpty()) ? stimuliParts[5] : null;
                code = (stimuliParts.length > 6 && !stimuliParts[6].isEmpty()) ? stimuliParts[6] : null;
                pauseMs = (stimuliParts.length > 7 && !stimuliParts[7].isEmpty()) ? Integer.parseInt(stimuliParts[7]) : 0;
                ratingLabels = (stimuliParts.length > 8 && !stimuliParts[8].isEmpty()) ? stimuliParts[8] : null;
                correctResponses = (stimuliParts.length > 9 && !stimuliParts[9].isEmpty()) ? stimuliParts[9] : null;
            }
            stimuliList.add(new Stimulus(identifier, audioPath, videoPath, imagePath, label, code, pauseMs, tagSet, ratingLabels, correctResponses));
        }
    }

    public final void setScreenTitle(String screenTitle) {
        this.wizardScreenData.setScreenTitle(screenTitle);
    }

    public final void setMenuLabel(String menuLabel) {
        this.wizardScreenData.setMenuLabel(menuLabel);
    }

    @Override
    public final void setScreenTag(String screenTag) {
        this.wizardScreenData.setScreenTag(screenTag.replaceAll("[^A-Za-z0-9]", "_"));
    }

    @Override
    public WizardScreenData getWizardScreenData() {
        return wizardScreenData;
    }

    @Override
    public WizardScreenData getBackWizardScreenData() {
        return this.wizardScreenData.getBackWizardScreenData();
    }

    @Override
    public void setBackWizardScreenData(WizardScreenData backWizardScreenData) {
        this.wizardScreenData.setBackWizardScreenData(backWizardScreenData);
    }

    @Override
    public WizardScreenData getNextWizardScreenData() {
        return this.wizardScreenData.getNextWizardScreenData();
    }

    @Override
    public void setNextWizardScreenData(WizardScreenData nextWizardScreenData) {
        this.wizardScreenData.setNextWizardScreenData(nextWizardScreenData);
    }

    @Override
    public void setBackWizardScreen(WizardScreen backWizardScreen) {
        this.wizardScreenData.setBackWizardScreenData(backWizardScreen.getWizardScreenData());
    }

    @Override
    public void setNextWizardScreen(WizardScreen nextWizardScreen) {
        this.wizardScreenData.setNextWizardScreenData(nextWizardScreen.getWizardScreenData());
    }

    @Override
    public final void setScreenText(String screenText) {
        this.wizardScreenData.setScreenText(0, screenText);
    }

    public final void setCentreScreen(boolean centreScreen) {
        this.wizardScreenData.setCentreScreen(centreScreen);
    }

    @Override
    public final void setNextButton(String nextButton) {
        this.wizardScreenData.setNextButton(new String[]{nextButton});
    }

    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, final Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        storedWizardScreenData.getPresenterScreen().setTitle((obfuscateScreenNames) ? experiment.getAppNameDisplay() + " " + displayOrder : storedWizardScreenData.getScreenTitle());
        storedWizardScreenData.getPresenterScreen().setMenuLabel((storedWizardScreenData.getMenuLabel() != null) ? storedWizardScreenData.getMenuLabel() : storedWizardScreenData.getScreenTitle());
        final String currentTagString = (storedWizardScreenData.getScreenTag() != null) ? storedWizardScreenData.getScreenTag() : storedWizardScreenData.getScreenTitle();
        storedWizardScreenData.getPresenterScreen().setSelfPresenterTag(currentTagString.replaceAll("[^A-Za-z0-9]", "_"));
        if (storedWizardScreenData.getBackWizardScreenData() != null) {
            storedWizardScreenData.getPresenterScreen().setBackPresenter(storedWizardScreenData.getBackWizardScreenData().getPresenterScreen());
        }
        if (storedWizardScreenData.getNextWizardScreenData() != null) {
            storedWizardScreenData.getPresenterScreen().setNextPresenter(storedWizardScreenData.getNextWizardScreenData().getPresenterScreen());
        }
        storedWizardScreenData.getPresenterScreen().setDisplayOrder(displayOrder);
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }
}
