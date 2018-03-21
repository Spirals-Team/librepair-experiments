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
 * @since May 12, 2016 3:38:09 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardCompletionScreen extends AbstractWizardScreen {

    public WizardCompletionScreen() {
        super(WizardScreenEnum.WizardCompletionScreen, "Completion", "Completion", "Completion");
    }

    public WizardCompletionScreen(String completedText1, final boolean allowDeleteUserRestart, boolean generateCompletionCode, String completedText2, String eraseUsersDataButtonlabel, final String screenTitle, final String could_not_contact_the_server_please_check, final String retryButtonLabel) {
        super(WizardScreenEnum.WizardCompletionScreen, screenTitle, screenTitle, screenTitle);
        wizardScreenData.setScreenText(0, completedText1);
        wizardScreenData.setScreenText(1, completedText2);
        wizardScreenData.setScreenBoolean(0, allowDeleteUserRestart);
        wizardScreenData.setScreenBoolean(3, false);
        wizardScreenData.setScreenBoolean(1, generateCompletionCode);
        wizardScreenData.setScreenBoolean(2, true);
        wizardScreenData.setScreenText(2, could_not_contact_the_server_please_check);
        wizardScreenData.setNextButton(new String[]{retryButtonLabel, eraseUsersDataButtonlabel});

    }

    public WizardCompletionScreen(String completedText1, final boolean allowDeleteUserRestart, final boolean allowCreateUserRestart, boolean generateCompletionCode, String completedText2, String eraseUsersDataButtonlabel, final String screenTitle, final String could_not_contact_the_server_please_check, final String retryButtonLabel) {
        super(WizardScreenEnum.WizardCompletionScreen, screenTitle, screenTitle, screenTitle);
        wizardScreenData.setScreenText(0, completedText1);
        wizardScreenData.setScreenText(1, completedText2);
        wizardScreenData.setScreenBoolean(0, allowDeleteUserRestart);
        wizardScreenData.setScreenBoolean(3, allowCreateUserRestart);
        wizardScreenData.setScreenBoolean(1, generateCompletionCode);
        wizardScreenData.setScreenBoolean(2, true);
        wizardScreenData.setScreenText(2, could_not_contact_the_server_please_check);
        wizardScreenData.setNextButton(new String[]{retryButtonLabel, eraseUsersDataButtonlabel});
    }

    public void setSendData(boolean sendData) {
        wizardScreenData.setScreenBoolean(2, sendData);
    }

    @Override
    public String getScreenBooleanInfo(int index) {
        return new String[]{"Allow Erase User Restart", "Generate Completion Code", "Send Data", "Allow Create User Restart"}[index];
    }

    @Override
    public String getScreenTextInfo(int index) {
        return new String[]{"completedText1", "completedText2", "Network Error Message"}[index];
    }

    @Override
    public String getNextButtonInfo(int index) {
        return new String[]{"Retry Button Label", "Erase Users Data Button Label"}[index];
    }

    @Override
    public String getScreenIntegerInfo(int index) {
        return new String[]{}[index];
    }

    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.transmission);
        final List<PresenterFeature> onSuccessFeatureList;
        if (storedWizardScreenData.getScreenBoolean(2)) {
            final PresenterFeature sendAllDataFeature = (storedWizardScreenData.getScreenBoolean(1)) ? new PresenterFeature(FeatureType.generateCompletionCode, null) : new PresenterFeature(FeatureType.sendAllData, null);
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(sendAllDataFeature);

            final PresenterFeature onSuccessFeature = new PresenterFeature(FeatureType.onSuccess, null);
            sendAllDataFeature.getPresenterFeatureList().add(onSuccessFeature);
            onSuccessFeatureList = onSuccessFeature.getPresenterFeatureList();
            final PresenterFeature onErrorFeature = new PresenterFeature(FeatureType.onError, null);
            sendAllDataFeature.getPresenterFeatureList().add(onErrorFeature);
//        final String could_not_contact_the_server_please_check = "Could not contact the server, please check your internet connection and try again.";
            onErrorFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.plainText, storedWizardScreenData.getScreenText(2)));
//        final String retryButtonLabel = "Retry";
            final PresenterFeature retryFeature = new PresenterFeature(FeatureType.targetButton, storedWizardScreenData.getNextButton()[0]);
            onErrorFeature.getPresenterFeatureList().add(retryFeature);
            retryFeature.addFeatureAttributes(FeatureAttribute.target, storedWizardScreenData.getScreenTag());
        } else {
            onSuccessFeatureList = storedWizardScreenData.getPresenterScreen().getPresenterFeatureList();
//            onSuccessFeatureList.add(new PresenterFeature(FeatureType.htmlText, "Data will not be collected in this version."));
        }
        onSuccessFeatureList.add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(0)));
        onSuccessFeatureList.add(new PresenterFeature(FeatureType.addPadding, null));
        if (storedWizardScreenData.getScreenBoolean(2) && storedWizardScreenData.getScreenBoolean(1)) {
            onSuccessFeatureList.add(new PresenterFeature(FeatureType.displayCompletionCode, null));
        }
        if (storedWizardScreenData.getScreenText(1) != null) {
            onSuccessFeatureList.add(new PresenterFeature(FeatureType.addPadding, null));
            onSuccessFeatureList.add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(1)));
        }
        if (storedWizardScreenData.getScreenBoolean(2) && storedWizardScreenData.getScreenBoolean(0)) {
            onSuccessFeatureList.add(new PresenterFeature(FeatureType.addPadding, null));
            // this erase data should not be shown unless the data has been sent, therefore we  check ScreenBoolean(2) here
            final PresenterFeature eraseUserButton = new PresenterFeature(FeatureType.eraseUsersDataButton, storedWizardScreenData.getNextButton()[1]);
            onSuccessFeatureList.add(eraseUserButton);
            eraseUserButton.addFeatureAttributes(FeatureAttribute.target, storedWizardScreenData.getNextWizardScreenData().getScreenTag());
        }
        if (storedWizardScreenData.getScreenBoolean(3)) {
            onSuccessFeatureList.add(new PresenterFeature(FeatureType.addPadding, null));
            final PresenterFeature createUserButton = new PresenterFeature(FeatureType.createUserButton, storedWizardScreenData.getNextButton()[1]);
            createUserButton.addFeatureAttributes(FeatureAttribute.target, storedWizardScreenData.getNextWizardScreenData().getScreenTag());
            onSuccessFeatureList.add(createUserButton);
        }
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }
}
