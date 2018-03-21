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
 * @since May 13, 2016 2:50:22 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardKinDiagramScreen extends AbstractWizardScreen {

    public enum ExampleType {
        PredefinedKinDiagram,
        SavedKinDiagram,
        EditableEntitesDiagram
    }
    private final ExampleType exampleType;

    static WizardScreenEnum WizardScreenEnumFromExampleType(ExampleType exampleType) {
        switch (exampleType) {
            case PredefinedKinDiagram:
                return WizardScreenEnum.WizardPredefinedKinDiagram;
            case EditableEntitesDiagram:
                return WizardScreenEnum.WizardEditableEntitesDiagram;
            case SavedKinDiagram:
                return WizardScreenEnum.WizardSavedKinDiagram;
        }
        throw new UnsupportedOperationException();
    }

    public WizardKinDiagramScreen(ExampleType exampleType) {
        super(WizardScreenEnumFromExampleType(exampleType), exampleType.name(), exampleType.name(), exampleType.name());
        this.exampleType = exampleType;
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
        switch (exampleType) {
            case PredefinedKinDiagram:
                final PresenterFeature kinTypeStringDiagram = new PresenterFeature(FeatureType.kinTypeStringDiagram, null);
                kinTypeStringDiagram.addFeatureAttributes(FeatureAttribute.msToNext, "0");
                kinTypeStringDiagram.addFeatureAttributes(FeatureAttribute.kintypestring, "EmMD:1:|EmFD:1:|EmS:2:|EmWD:3:|EmD:3:|EmWS:2:|EmZ:1:");
                kinTypeStringDiagram.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, "This screen shows a simple predefined kin type string diagram."));
                storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(kinTypeStringDiagram);
                break;
            case SavedKinDiagram:
                storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, "This screen shows a freeform kin type string diagram that you create. Use this form to add individuals to this kin type string diagram below."));
                storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
                final PresenterFeature loadKinTypeStringDiagram = new PresenterFeature(FeatureType.loadKinTypeStringDiagram, null);
                loadKinTypeStringDiagram.addFeatureAttributes(FeatureAttribute.msToNext, "0");
                loadKinTypeStringDiagram.addFeatureAttributes(FeatureAttribute.diagramName, "kinTypeDiagram");
                storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(loadKinTypeStringDiagram);
                final PresenterFeature addKinTypeGui = new PresenterFeature(FeatureType.addKinTypeGui, null);
                addKinTypeGui.addFeatureAttributes(FeatureAttribute.diagramName, "kinTypeDiagram");
                storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(addKinTypeGui);
                experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
                break;
            case EditableEntitesDiagram:
                storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, "This screen shows diagram on which you can editi the entities. Use the mouse to add relations and entites."));
                storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
                final PresenterFeature editableKinEntitesDiagram = new PresenterFeature(FeatureType.editableKinEntitesDiagram, null);
                editableKinEntitesDiagram.addFeatureAttributes(FeatureAttribute.msToNext, "0");
                editableKinEntitesDiagram.addFeatureAttributes(FeatureAttribute.diagramName, "kinTypeDiagram");
                storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(editableKinEntitesDiagram);
//        final PresenterFeature addKinTypeGui = new PresenterFeature(FeatureType.addKinTypeGui, null);
//        addKinTypeGui.addFeatureAttributes(FeatureAttribute.diagramName, "kinTypeDiagram");
//        presenterScreen.getPresenterFeatureList().add(addKinTypeGui);
                experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
                break;
        }
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }
}
