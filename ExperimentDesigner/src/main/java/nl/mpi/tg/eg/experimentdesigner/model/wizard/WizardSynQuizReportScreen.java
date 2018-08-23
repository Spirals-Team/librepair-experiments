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
 * @since Nov 3, 2016 2:49:46 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardSynQuizReportScreen extends AbstractWizardScreen {

    public WizardSynQuizReportScreen() {
        super(WizardScreenEnum.WizardSynQuizReportScreen, "Select User", "Select User", "SelectUser");
    }

    public WizardSynQuizReportScreen(String screenName, final WizardScreenData backPresenter, final WizardScreenData nextPresenter) {
        super(WizardScreenEnum.WizardSynQuizReportScreen, screenName, screenName, screenName);
        wizardScreenData.setBackWizardScreenData(backPresenter);
        wizardScreenData.setNextWizardScreenData(nextPresenter);
    }

    @Override
    public String getScreenBooleanInfo(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getScreenTextInfo(int index) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public String getNextButtonInfo(int index) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public String getScreenIntegerInfo(int index) {
        return new String[]{}[index];
    }

    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        final PresenterScreen presenterScreen = storedWizardScreenData.getPresenterScreen();
        presenterScreen.setPresenterType(PresenterType.colourReport);
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);

        final PresenterFeature showColourReport = new PresenterFeature(FeatureType.showColourReport, null);
//        showColourReport.addStimulusTag(screenName);
        showColourReport.addFeatureAttributes(FeatureAttribute.scoreThreshold, "1");
//        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.randomise, "true");
//        presenterFeatureList.add(loadStimuliFeature);
        final PresenterFeature aboveThreshold = new PresenterFeature(FeatureType.aboveThreshold, null);
//        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.plainText, "above threshold"));
//        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.stimulusLabel, null));
//        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.showStimulusProgress, null));
        showColourReport.getPresenterFeatureList().add(aboveThreshold);
        final PresenterFeature withinThreshold = new PresenterFeature(FeatureType.withinThreshold, null);
//        withinThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.plainText, "below threshold"));
//        final PresenterFeature menuButtonFeature = new PresenterFeature(FeatureType.targetButton, "Menu");
//        menuButtonFeature.addFeatureAttributes(FeatureAttribute.target, "AutoMenu");
//        endOfStimulusFeature.getPresenterFeatureList().add(menuButtonFeature);
//        endOfStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.autoNextPresenter, null));
        showColourReport.getPresenterFeatureList().add(withinThreshold);
        presenterScreen.getPresenterFeatureList().add(showColourReport);
        final PresenterFeature submitTestResults = new PresenterFeature(FeatureType.submitTestResults, null);
        submitTestResults.getPresenterFeatureList().add(new PresenterFeature(FeatureType.onSuccess, null));
        submitTestResults.getPresenterFeatureList().add(new PresenterFeature(FeatureType.onError, null));
        presenterScreen.getPresenterFeatureList().add(submitTestResults);

        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }
}
