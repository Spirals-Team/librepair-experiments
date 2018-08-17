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
 * @since May 13, 2016 2:20:22 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardMenuScreen extends AbstractWizardScreen {

    public WizardMenuScreen() {
        super(WizardScreenEnum.WizardMenuScreen, "Menu", "Menu", "Menu");
        setBranchOnGetParam(false, null);
        setJumpToRandomScreen(false);
    }

    public WizardMenuScreen(String screenTitle, String menuLabel, String screenTag) {
        super(WizardScreenEnum.WizardMenuScreen, screenTitle, menuLabel, screenTag);
        setBranchOnGetParam(false, null);
        setJumpToRandomScreen(false);
    }

    @Override
    public String getScreenBooleanInfo(int index) {
        return new String[]{"Requre URL GET parameter of a screen name to branch to rather than showing the menu", "Jump to random screen in menu, if that screen is not already completed"}[index];
    }

    @Override
    public String getScreenTextInfo(int index) {
        return new String[]{"Text before the menu", "Text after the menu"}[index];
    }

    @Override
    public String getNextButtonInfo(int index) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public String getScreenIntegerInfo(int index) {
        return new String[]{}[index];
    }

    final public void setBranchOnGetParam(boolean branchOnGetParam, String errorMessage) {
        this.wizardScreenData.setScreenText(0, errorMessage);
        this.wizardScreenData.setScreenBoolean(0, branchOnGetParam);
    }

    private boolean isBranchOnGetParam(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenBoolean(0);
    }

    final public void setJumpToRandomScreen(boolean jumpToRandomScreen) {
        this.wizardScreenData.setScreenBoolean(1, jumpToRandomScreen);
    }

    private boolean isJumpToRandomScreen(WizardScreenData storedWizardScreenData) {
        return storedWizardScreenData.getScreenBoolean(1);
    }

    public void addTargetScreen(final AbstractWizardScreen targetScreen) {
        wizardScreenData.getMenuWizardScreenData().add(targetScreen.getWizardScreenData());
    }

    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.menu);
        if (storedWizardScreenData.getScreenText(0) != null) {
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(0)));
        }
        if (isBranchOnGetParam(storedWizardScreenData)) {
            String aChoiceMustBeProvidedMessage = "";
            for (WizardScreenData targetScreen : storedWizardScreenData.getMenuWizardScreenData()) {
                final PresenterFeature hasGetParameter = new PresenterFeature(FeatureType.hasGetParameter, null);
                hasGetParameter.addFeatureAttributes(FeatureAttribute.parameterName, targetScreen.getScreenTag());
                final PresenterFeature conditionTrue = new PresenterFeature(FeatureType.conditionTrue, null);
                final PresenterFeature autoNextPresenter = new PresenterFeature(FeatureType.gotoPresenter, null);
                autoNextPresenter.addFeatureAttributes(FeatureAttribute.target, targetScreen.getScreenTag());
                conditionTrue.getPresenterFeatureList().add(autoNextPresenter);
                hasGetParameter.getPresenterFeatureList().add(conditionTrue);
                hasGetParameter.getPresenterFeatureList().add(new PresenterFeature(FeatureType.conditionFalse, null));
                storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(hasGetParameter);
                aChoiceMustBeProvidedMessage += targetScreen.getScreenTag() + "<br/>";
            }
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, aChoiceMustBeProvidedMessage));
        } else if (storedWizardScreenData.getMenuWizardScreenData().isEmpty()) {
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.allMenuItems, null));
        } else {
            for (WizardScreenData targetScreen : storedWizardScreenData.getMenuWizardScreenData()) {
                final PresenterFeature presenterFeature1 = new PresenterFeature(FeatureType.menuItem, targetScreen.getMenuLabel());
                presenterFeature1.addFeatureAttributes(FeatureAttribute.target, targetScreen.getScreenTag());
                storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(presenterFeature1);
            }
        }
        if (isJumpToRandomScreen(storedWizardScreenData)) {
            final PresenterFeature jumpToRandomScreen = new PresenterFeature(FeatureType.activateRandomItem, null);
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(jumpToRandomScreen);
        }
        if (storedWizardScreenData.getScreenText(1) != null) {
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(1)));
        }
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }
}
