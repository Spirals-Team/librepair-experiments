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
 * @since Oct 25, 2016 1:36:35 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardVideoTimelineScreen extends AbstractWizardScreen {

    public WizardVideoTimelineScreen() {
        super(WizardScreenEnum.WizardVideoTimelineScreen);
    }

    public WizardVideoTimelineScreen(final String screenName) {
        super(WizardScreenEnum.WizardVideoTimelineScreen, screenName, screenName, screenName);
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
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.timeline);
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
        storedWizardScreenData.getPresenterScreen().setNextPresenter(null);
        final PresenterFeature loadStimulus = new PresenterFeature(FeatureType.loadStimulus, null);
        loadStimulus.addFeatureAttributes(FeatureAttribute.eventTag, "AnnotationTimelinePanel");
        loadStimulus.addFeatureAttributes(FeatureAttribute.randomise, "true");
        loadStimulus.addFeatureAttributes(FeatureAttribute.repeatRandomWindow, "0");
        loadStimulus.addFeatureAttributes(FeatureAttribute.maxStimuli, "20");
        loadStimulus.addFeatureAttributes(FeatureAttribute.repeatCount, "1");
        final PresenterFeature presenterFeature1 = new PresenterFeature(FeatureType.AnnotationTimelinePanel, null);
        for (String tagString : storedWizardScreenData.getStimuliRandomTags()) {
            loadStimulus.addStimulusTag(tagString);
        }
        presenterFeature1.addFeatureAttributes(FeatureAttribute.poster, "poster.jpg");
        presenterFeature1.addFeatureAttributes(FeatureAttribute.columnCount, "2");
        presenterFeature1.addFeatureAttributes(FeatureAttribute.eventTag, storedWizardScreenData.getScreenTag());
        presenterFeature1.addFeatureAttributes(FeatureAttribute.maxStimuli, String.valueOf(storedWizardScreenData.getStimuliCount()));
        presenterFeature1.addFeatureAttributes(FeatureAttribute.src, storedWizardScreenData.getScreenMediaPath());
        storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(loadStimulus);
        loadStimulus.addFeature(FeatureType.hasMoreStimulus, null).getPresenterFeatureList().add(presenterFeature1);
        loadStimulus.addFeature(FeatureType.endOfStimulus, null);
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        experiment.appendUniqueStimuli(storedWizardScreenData.getStimuli());
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }
}
