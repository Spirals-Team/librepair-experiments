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

import java.util.List;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;

/**
 * @since Nov 3, 2016 2:45:01 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardSynQuizStimulusScreen extends AbstractWizardScreen {

    public WizardSynQuizStimulusScreen() {
        super(WizardScreenEnum.WizardSynQuizStimulusScreen, "Select User", "Select User", "SelectUser");
    }

    public WizardSynQuizStimulusScreen(String screenName, String menuLabel, final WizardScreenData backPresenter, final WizardScreenData nextPresenter, final String ok_go_to_test, final String helpText) {
        super(WizardScreenEnum.WizardSynQuizStimulusScreen, screenName, screenName, screenName);
        wizardScreenData.setMenuLabel(menuLabel);
        wizardScreenData.setBackWizardScreenData(backPresenter);
        wizardScreenData.setNextWizardScreenData(nextPresenter);
        wizardScreenData.setHelpText(helpText);
        wizardScreenData.setNextButton(new String[]{ok_go_to_test});
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
        return new String[]{"Go To Test Button Label"}[index];
    }

    @Override
    public String getScreenIntegerInfo(int index) {
        return new String[]{}[index];
    }

    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        final PresenterScreen presenterScreen = storedWizardScreenData.getPresenterScreen();
        presenterScreen.setPresenterType(PresenterType.colourPicker);
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
        final PresenterFeature helpDialogue = new PresenterFeature(FeatureType.helpDialogue, storedWizardScreenData.getHelpText());
        helpDialogue.addFeatureAttributes(FeatureAttribute.closeButtonLabel, storedWizardScreenData.getNextButton()[0]);
        presenterScreen.getPresenterFeatureList().add(helpDialogue);
        List<PresenterFeature> presenterFeatureList = presenterScreen.getPresenterFeatureList();
        final PresenterFeature loadStimuliFeature = new PresenterFeature(FeatureType.loadStimulus, null);
        loadStimuliFeature.addStimulusTag(storedWizardScreenData.getScreenTitle());
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.eventTag, storedWizardScreenData.getScreenTitle());
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.randomise, "true");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatCount, "3");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatRandomWindow, "0"); // todo: does Amanda want a random window to be used
        presenterFeatureList.add(loadStimuliFeature);
        final PresenterFeature hasMoreStimulusFeature = new PresenterFeature(FeatureType.hasMoreStimulus, null);
//        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.stimulusLabel, null));
//        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.showStimulusProgress, null));
        loadStimuliFeature.getPresenterFeatureList().add(hasMoreStimulusFeature);
        final PresenterFeature endOfStimulusFeature = new PresenterFeature(FeatureType.endOfStimulus, null);
//        endOfStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.text, "end of stimuli"));
//        final PresenterFeature menuButtonFeature = new PresenterFeature(FeatureType.targetButton, "Menu");
//        menuButtonFeature.addFeatureAttributes(FeatureAttribute.target, "AutoMenu");
//        endOfStimulusFeature.getPresenterFeatureList().add(menuButtonFeature);
//        endOfStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.autoNextPresenter, null));
        loadStimuliFeature.getPresenterFeatureList().add(endOfStimulusFeature);
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }
}
