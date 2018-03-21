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
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;

/**
 * @since May 3, 2016 1:34:51 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public abstract class AbstractWizardScreen implements WizardScreen {

    protected final WizardScreenData wizardScreenData;

    public AbstractWizardScreen(WizardScreenEnum wizardScreenEnum) {
        this.wizardScreenData = new WizardScreenData(wizardScreenEnum);
        this.wizardScreenData.setCentreScreen(false);
    }

    public AbstractWizardScreen(WizardScreenEnum wizardScreenEnum, String screenTitle, String menuLabel, String screenTag) {
        this.wizardScreenData = new WizardScreenData(wizardScreenEnum);
        this.wizardScreenData.setScreenTitle(screenTitle);
        this.wizardScreenData.setMenuLabel(menuLabel);
        this.wizardScreenData.setScreenTag(screenTag.replaceAll("[^A-Za-z0-9]", "_"));
        this.wizardScreenData.setCentreScreen(false);
    }

    public final void setScreenTitle(String screenTitle) {
        this.wizardScreenData.setScreenTitle(screenTitle);
    }

    public final void setMenuLabel(String menuLabel) {
        this.wizardScreenData.setMenuLabel(menuLabel);
    }

    @Override
    public final void setScreenTag(String screenTag) {
        this.wizardScreenData.setScreenTag(screenTag.replaceAll("[^A-Za-z0-9]", "_"));
    }

    @Override
    public WizardScreenData getWizardScreenData() {
        return wizardScreenData;
    }

    @Override
    public WizardScreenData getBackWizardScreenData() {
        return this.wizardScreenData.getBackWizardScreenData();
    }

    @Override
    public void setBackWizardScreenData(WizardScreenData backWizardScreenData) {
        this.wizardScreenData.setBackWizardScreenData(backWizardScreenData);
    }

    @Override
    public WizardScreenData getNextWizardScreenData() {
        return this.wizardScreenData.getNextWizardScreenData();
    }

    @Override
    public void setNextWizardScreenData(WizardScreenData nextWizardScreenData) {
        this.wizardScreenData.setNextWizardScreenData(nextWizardScreenData);
    }

    @Override
    public void setBackWizardScreen(WizardScreen backWizardScreen) {
        this.wizardScreenData.setBackWizardScreenData(backWizardScreen.getWizardScreenData());
    }

    @Override
    public void setNextWizardScreen(WizardScreen nextWizardScreen) {
        this.wizardScreenData.setNextWizardScreenData(nextWizardScreen.getWizardScreenData());
    }

    @Override
    public final void setScreenText(String screenText) {
        this.wizardScreenData.setScreenText(0, screenText);
    }

    public final void setCentreScreen(boolean centreScreen) {
        this.wizardScreenData.setCentreScreen(centreScreen);
    }

    @Override
    public final void setNextButton(String nextButton) {
        this.wizardScreenData.setNextButton(new String[]{nextButton});
    }

    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, final Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        storedWizardScreenData.getPresenterScreen().setTitle((obfuscateScreenNames) ? experiment.getAppNameDisplay() + " " + displayOrder : storedWizardScreenData.getScreenTitle());
        storedWizardScreenData.getPresenterScreen().setMenuLabel((storedWizardScreenData.getMenuLabel() != null) ? storedWizardScreenData.getMenuLabel() : storedWizardScreenData.getScreenTitle());
        final String currentTagString = (storedWizardScreenData.getScreenTag() != null) ? storedWizardScreenData.getScreenTag() : storedWizardScreenData.getScreenTitle();
        storedWizardScreenData.getPresenterScreen().setSelfPresenterTag(currentTagString.replaceAll("[^A-Za-z0-9]", "_"));
        if (storedWizardScreenData.getBackWizardScreenData() != null) {
            storedWizardScreenData.getPresenterScreen().setBackPresenter(storedWizardScreenData.getBackWizardScreenData().getPresenterScreen());
        }
        if (storedWizardScreenData.getNextWizardScreenData() != null) {
            storedWizardScreenData.getPresenterScreen().setNextPresenter(storedWizardScreenData.getNextWizardScreenData().getPresenterScreen());
        }
        storedWizardScreenData.getPresenterScreen().setDisplayOrder(displayOrder);
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }
}
