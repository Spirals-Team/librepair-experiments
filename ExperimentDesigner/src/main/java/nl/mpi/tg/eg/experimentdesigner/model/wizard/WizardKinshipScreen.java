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
 * @since May 13, 2016 2:38:18 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardKinshipScreen extends AbstractWizardScreen {

    private String diagramName = "kinDiagram";

    public WizardKinshipScreen() {
        super(WizardScreenEnum.WizardKinshipScreen, "Kinship", "Kinship", "Kinship");
    }

    public WizardKinshipScreen(String screenTitle, String diagramName) {
        super(WizardScreenEnum.WizardKinshipScreen, screenTitle, screenTitle, screenTitle);
        this.diagramName = diagramName;
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
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.kindiagram);
        final PresenterFeature presenterFeature1 = new PresenterFeature(FeatureType.addKinTypeGui, null);
        presenterFeature1.addFeatureAttributes(FeatureAttribute.diagramName, diagramName);
        storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(presenterFeature1);
        final PresenterFeature presenterFeature2 = new PresenterFeature(FeatureType.loadKinTypeStringDiagram, null);
        presenterFeature2.addFeatureAttributes(FeatureAttribute.diagramName, diagramName);
        presenterFeature2.addFeatureAttributes(FeatureAttribute.msToNext, "0");
        storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(presenterFeature2);
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};

    }

}
