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
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;

/**
 * @since Nov 3, 2016 2:26:58 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardSynQuizSumbitScreen extends AbstractWizardScreen {

    public WizardSynQuizSumbitScreen() {
        super(WizardScreenEnum.WizardSynQuizSumbitScreen);
    }

    public WizardSynQuizSumbitScreen(String screenName, final WizardScreenData backPresenter, final WizardScreenData nextPresenter, final String submit_my_results, final String error_submitting_data) {
        super(WizardScreenEnum.WizardSynQuizSumbitScreen);
        wizardScreenData.setScreenTitle(screenName);
        wizardScreenData.setScreenTag(screenName);
        wizardScreenData.setMenuLabel(submit_my_results);
        wizardScreenData.setScreenText(0, error_submitting_data);
        wizardScreenData.setBackWizardScreenData(backPresenter);
        wizardScreenData.setNextWizardScreenData(nextPresenter);
    }

    @Override
    public String getScreenBooleanInfo(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getScreenTextInfo(int index) {
        return new String[]{"Network Error Message"}[index];
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
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
        presenterScreen.setPresenterType(PresenterType.colourReport);
        //        List<PresenterFeature> presenterFeatureList = presenterScreen.getPresenterFeatureList();
        final PresenterFeature showColourReport = new PresenterFeature(FeatureType.submitTestResults, null);
        final PresenterFeature aboveThreshold = new PresenterFeature(FeatureType.onError, null);
        aboveThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.plainText, storedWizardScreenData.getScreenText(0)));
//        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.stimulusLabel, null));
//        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.showStimulusProgress, null));
        showColourReport.getPresenterFeatureList().add(aboveThreshold);
        final PresenterFeature belowThreshold = new PresenterFeature(FeatureType.onSuccess, null);
//        belowThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.plainText, "data sumbitted"));
//        final PresenterFeature menuButtonFeature = new PresenterFeature(FeatureType.targetButton, "Menu");
//        menuButtonFeature.addFeatureAttributes(FeatureAttribute.target, "AutoMenu");
//        endOfStimulusFeature.getPresenterFeatureList().add(menuButtonFeature);
        belowThreshold.getPresenterFeatureList().add(new PresenterFeature(FeatureType.gotoNextPresenter, null));
        showColourReport.getPresenterFeatureList().add(belowThreshold);
        presenterScreen.getPresenterFeatureList().add(showColourReport);
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }

}
