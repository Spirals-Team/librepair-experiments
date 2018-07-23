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
 * @since May 12, 2016 5:05:03 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardExistingUserCheckScreen extends AbstractWizardScreen {

    public WizardExistingUserCheckScreen() {
        super(WizardScreenEnum.WizardExistingUserCheckScreen, "ExistingUserCheck", "ExistingUserCheck", "ExistingUserCheck");
        setGenerateSelectUserMenuScreen(false);
    }

    public WizardExistingUserCheckScreen(final String screenTitle, final String new_Interview, final String resume_Interview, final String startNewText, final String resumeoldText) {
        super(WizardScreenEnum.WizardExistingUserCheckScreen, screenTitle, screenTitle, screenTitle);
        this.wizardScreenData.setNextButton(new String[]{new_Interview, resume_Interview});
        this.wizardScreenData.setScreenText(0, startNewText);
        this.wizardScreenData.setScreenText(1, resumeoldText);
        setGenerateSelectUserMenuScreen(false);
    }

    @Override
    public String getScreenBooleanInfo(int index) {
        return new String[]{"Generate Select User Menu Screen"}[index];
    }

    @Override
    public String getScreenTextInfo(int index) {
        return new String[]{"startNewText", "resumeoldText"}[index];
    }

    @Override
    public String getNextButtonInfo(int index) {
        return new String[]{"New Interview Button Label", "Resume Interview Button Label"}[index];
    }

    @Override
    public String getScreenIntegerInfo(int index) {
        return new String[]{}[index];
    }

    final public void setGenerateSelectUserMenuScreen(boolean generateMenu) {
        wizardScreenData.setScreenBoolean(0, generateMenu);
    }

    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.metadata);
        storedWizardScreenData.getPresenterScreen().setNextPresenter(null);
        final PresenterFeature userCheckFeature = new PresenterFeature(FeatureType.existingUserCheck, null);
        final PresenterFeature multipleUsersFeature = new PresenterFeature(FeatureType.multipleUsers, null);
        userCheckFeature.getPresenterFeatureList().add(multipleUsersFeature);
        final PresenterFeature singleUserFeature = new PresenterFeature(FeatureType.singleUser, null);
        final PresenterFeature autoNextPresenter = new PresenterFeature(FeatureType.gotoPresenter, null);
        autoNextPresenter.addFeatureAttributes(FeatureAttribute.target, storedWizardScreenData.getNextWizardScreenData().getScreenTag());
        singleUserFeature.getPresenterFeatureList().add(autoNextPresenter);
        userCheckFeature.getPresenterFeatureList().add(singleUserFeature);
        multipleUsersFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.plainText, storedWizardScreenData.getScreenText(0)));
        final PresenterFeature createUserFeature = new PresenterFeature(FeatureType.createUserButton, storedWizardScreenData.getNextButton()[0]);
        createUserFeature.addFeatureAttributes(FeatureAttribute.target, storedWizardScreenData.getNextWizardScreenData().getScreenTag());
        multipleUsersFeature.getPresenterFeatureList().add(createUserFeature);
        multipleUsersFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
        multipleUsersFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.plainText, storedWizardScreenData.getScreenText(1)));

        final PresenterFeature selectUserButton = new PresenterFeature(FeatureType.targetButton, storedWizardScreenData.getNextButton()[1]);
        selectUserButton.addFeatureAttributes(FeatureAttribute.target, "SelectUser");
        multipleUsersFeature.getPresenterFeatureList().add(selectUserButton);
        storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(userCheckFeature);
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());

        if (storedWizardScreenData.getScreenBoolean(0)) {
            PresenterScreen selectUserScreen = new PresenterScreen(storedWizardScreenData.getScreenTitle(), storedWizardScreenData.getScreenTitle(), storedWizardScreenData.getPresenterScreen(), "SelectUser", storedWizardScreenData.getNextWizardScreenData().getPresenterScreen(), PresenterType.metadata, displayOrder + 1);
            final PresenterFeature selectUserFeature = new PresenterFeature(FeatureType.selectUserMenu, null);
            selectUserScreen.getPresenterFeatureList().add(selectUserFeature);
            experiment.getPresenterScreen().add(selectUserScreen);
            return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen(), selectUserScreen};
        } else {
            return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
        }
    }
}
