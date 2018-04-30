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
 * @since May 13, 2016 2:50:22 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardAboutScreen extends AbstractWizardScreen {

    private final boolean showDebug;

    public WizardAboutScreen(boolean showDebug) {
        super(WizardScreenEnumFromDebugType(showDebug), "About Screen", "About Screen", "about");
        this.showDebug = showDebug;
        setScreenText("");
    }

    @Override
    public String getScreenBooleanInfo(int index) {
        return new String[]{}[index];
    }

    @Override
    public String getScreenTextInfo(int index) {
        return new String[]{"Screen text"}[index];
    }

    @Override
    public String getNextButtonInfo(int index) {
        return new String[]{}[index];
    }

    @Override
    public String getScreenIntegerInfo(int index) {
        return new String[]{}[index];
    }

    static WizardScreenEnum WizardScreenEnumFromDebugType(boolean showDebug) {
        if (showDebug) {
            return WizardScreenEnum.WizardDebugAboutScreen;
        } else {
            return WizardScreenEnum.WizardAboutScreen;
        }
    }

    public WizardAboutScreen(String screenTitle, boolean showDebug) {
        super(WizardScreenEnumFromDebugType(showDebug), screenTitle, screenTitle, "about");
        this.showDebug = showDebug;
    }

    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.debug);
        if (storedWizardScreenData.getScreenText(0) != null && !storedWizardScreenData.getScreenText(0).isEmpty()) {
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(0)));
        }
        storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.versionData, null));
        if (showDebug) {
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.eraseLocalStorageButton, null));
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.localStorageData, null));
        }
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }
}
