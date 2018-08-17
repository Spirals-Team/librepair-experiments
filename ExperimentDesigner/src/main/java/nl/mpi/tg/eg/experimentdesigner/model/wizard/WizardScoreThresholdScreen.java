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

import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;

/**
 * @since Mar 28, 2017 4:32:03 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardScoreThresholdScreen extends AbstractWizardScreen {

    public WizardScoreThresholdScreen() {
        super(WizardScreenEnum.WizardScoreThresholdScreen, "ScoreThreshold", "ScoreThreshold", "ScoreThreshold");
    }

    public WizardScoreThresholdScreen(String screenText1, final Integer scoreThreshold, final String screenTitle, final AbstractWizardScreen alternateNextScreen, final String retryButton) {
        super(WizardScreenEnum.WizardScoreThresholdScreen, screenTitle, screenTitle, screenTitle);
        wizardScreenData.setScreenText(0, screenText1);
        this.wizardScreenData.setNextButton(new String[]{retryButton});
        this.wizardScreenData.getMenuWizardScreenData().add(0, alternateNextScreen.getWizardScreenData());
        setScoreThreshold(scoreThreshold);
    }

    @Override
    public String getScreenBooleanInfo(int index) {
        return new String[]{}[index];
    }

    @Override
    public String getScreenIntegerInfo(int index) {
        return new String[]{"Score Threshold"}[index];
    }

    @Override
    public String getScreenTextInfo(int index) {
        return new String[]{"Screen Text"}[index];
    }

    @Override
    public String getNextButtonInfo(int index) {
        return new String[]{"Retry Button Text"}[index];
    }

    final public void setScoreThreshold(Integer scoreThreshold) {
        this.wizardScreenData.setScreenIntegers(0, scoreThreshold);
    }

    final public Integer getScoreThreshold(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenInteger(0);
    }

    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.stimulus);

        final PresenterFeature bestScoreAboveThreshold = new PresenterFeature(FeatureType.bestScoreAboveThreshold, null);
        bestScoreAboveThreshold.addFeatureAttributes(FeatureAttribute.scoreThreshold, Integer.toString(getScoreThreshold(storedWizardScreenData)));
//        bestScoreAboveThreshold.addFeatureAttributes(FeatureAttribute.potentialThreshold, Integer.toString(getScoreThreshold(storedWizardScreenData)));

        final PresenterFeature aboveThreshold = new PresenterFeature(FeatureType.aboveThreshold, null);
        final PresenterFeature withinThreshold = new PresenterFeature(FeatureType.withinThreshold, null);

        final PresenterFeature autoNextPresenter = new PresenterFeature(FeatureType.gotoNextPresenter, null);
//        autoNextPresenter.addFeatureAttributes(FeatureAttribute.target, storedWizardScreenData.getNextWizardScreenData().getScreenTag());
        aboveThreshold.getPresenterFeatureList().add(autoNextPresenter);

        final PresenterFeature htmlText = new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(0));
        withinThreshold.getPresenterFeatureList().add(htmlText);

        final PresenterFeature resetStimulus = new PresenterFeature(FeatureType.resetStimulus, null);
        resetStimulus.addFeatureAttributes(FeatureAttribute.target, storedWizardScreenData.getMenuWizardScreenData().get(0).getScreenTag());
        withinThreshold.getPresenterFeatureList().add(resetStimulus);

        final PresenterFeature targetButton = new PresenterFeature(FeatureType.targetButton, storedWizardScreenData.getNextButton()[0]);
        targetButton.addFeatureAttributes(FeatureAttribute.target, storedWizardScreenData.getMenuWizardScreenData().get(0).getScreenTag());

        withinThreshold.getPresenterFeatureList().add(targetButton);

        bestScoreAboveThreshold.getPresenterFeatureList().add(aboveThreshold);
        bestScoreAboveThreshold.getPresenterFeatureList().add(withinThreshold);

        storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(bestScoreAboveThreshold);
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }
}
