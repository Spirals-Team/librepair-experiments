/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
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
 * @since Feb 19, 2018 18:20:00 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardSubmitOfflineDataScreen extends AbstractWizardScreen {

    public WizardSubmitOfflineDataScreen() {
        super(WizardScreenEnum.WizardSubmitOfflineDataScreen, "Data Management", "Data Management", "DataManagement");
        wizardScreenData.setScreenText(0, "Participants with data in the application memory:");
        wizardScreenData.setScreenText(1, "The participant's data has been uploaded to the server");
        wizardScreenData.setScreenText(2, "Remove the participants's data from this application");
        wizardScreenData.setScreenText(3, "Keep local copy of the participants's data");
        wizardScreenData.setScreenText(4, "Cannot connect to the server, please check your internet connection and/or upload data later. Do not clean the browser's cache.");
        wizardScreenData.setScreenText(5, "Retry");
        wizardScreenData.setScreenText(6, "Participant's Data Listing");
    }

    public void setSendData(boolean sendData) {
        wizardScreenData.setScreenBoolean(2, sendData);
    }

    @Override
    public String getScreenBooleanInfo(int index) {
        return new String[]{}[index];
    }

    @Override
    public String getScreenTextInfo(int index) {
        return new String[]{"User Data Listing Text", "Upload Done Text", "Remove Data Text", "Keep Data Text", "Network Error Message", "Retry Button Text", "User Listing Button Text"}[index];
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
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.metadata);
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        PresenterScreen uploadPresenterScreen = new PresenterScreen("Data Upload", "Data Upload", storedWizardScreenData.getPresenterScreen(), "DataUpload", storedWizardScreenData.getPresenterScreen(), PresenterType.transmission, displayOrder + 1);
        experiment.getPresenterScreen().add(uploadPresenterScreen);
        storedWizardScreenData.getPresenterScreen().setNextPresenter(uploadPresenterScreen);

        final PresenterFeature featureText = new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(0));
        storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(featureText);
        final PresenterFeature selectUserMenu = new PresenterFeature(FeatureType.selectUserMenu, null);
        storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(selectUserMenu);
        uploadPresenterScreen.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, "Uploading matadata"));
        final PresenterFeature sendPause = new PresenterFeature(FeatureType.pause, null);
        sendPause.addFeatureAttributes(FeatureAttribute.msToNext, "100");
        uploadPresenterScreen.getPresenterFeatureList().add(sendPause);
        final PresenterFeature sendMetadata = sendPause.addFeature(FeatureType.sendMetadata, null);
        final PresenterFeature onSuccess = sendMetadata.addFeature(FeatureType.onSuccess, null);
        onSuccess.addFeature(FeatureType.htmlText, "Uploading data", "");
        final PresenterFeature sendAllPause = onSuccess.addFeature(FeatureType.pause, null, "100");
        final PresenterFeature sendAllData = sendAllPause.addFeature(FeatureType.sendAllData, null);
        final PresenterFeature onSuccess2 = sendAllData.addFeature(FeatureType.onSuccess, null);
        final PresenterFeature htmlText1 = onSuccess2.addFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(1), "");
        onSuccess2.addFeature(FeatureType.addPadding, null);
        onSuccess2.addFeature(FeatureType.addPadding, null);
        onSuccess2.addFeature(FeatureType.eraseUsersDataButton, storedWizardScreenData.getScreenText(2), storedWizardScreenData.getScreenTag());
        onSuccess2.addFeature(FeatureType.addPadding, null);
        onSuccess2.addFeature(FeatureType.addPadding, null);
        onSuccess2.addFeature(FeatureType.targetButton, storedWizardScreenData.getScreenText(3), "", storedWizardScreenData.getScreenTag(), "");
        onSuccess2.addFeature(FeatureType.addPadding, null);
        onSuccess2.addFeature(FeatureType.addPadding, null);
        final PresenterFeature onError2 = sendAllData.addFeature(FeatureType.onError, null);
        onError2.addFeature(FeatureType.plainText, storedWizardScreenData.getScreenText(4));
        onError2.addFeature(FeatureType.addPadding, null);
        onError2.addFeature(FeatureType.addPadding, null);
        onError2.addFeature(FeatureType.targetButton, storedWizardScreenData.getScreenText(5), "", uploadPresenterScreen.getSelfPresenterTag(), "");
        onError2.addFeature(FeatureType.addPadding, null);
        onError2.addFeature(FeatureType.addPadding, null);
        onError2.addFeature(FeatureType.targetButton, storedWizardScreenData.getScreenText(6), "", storedWizardScreenData.getScreenTag(), "");
        onError2.addFeature(FeatureType.addPadding, null);
        onError2.addFeature(FeatureType.addPadding, null);

        final PresenterFeature onError = sendMetadata.addFeature(FeatureType.onError, null);
        onError.addFeature(FeatureType.plainText, storedWizardScreenData.getScreenText(4));
        onError.addFeature(FeatureType.addPadding, null);
        onError.addFeature(FeatureType.addPadding, null);
        onError.addFeature(FeatureType.targetButton, storedWizardScreenData.getScreenText(5), "", uploadPresenterScreen.getSelfPresenterTag(), "");
        onError.addFeature(FeatureType.addPadding, null);
        onError.addFeature(FeatureType.addPadding, null);
        onError.addFeature(FeatureType.targetButton, storedWizardScreenData.getScreenText(6), "", storedWizardScreenData.getScreenTag(), "");
        onError.addFeature(FeatureType.addPadding, null);
        onError.addFeature(FeatureType.addPadding, null);
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen(), uploadPresenterScreen};
    }
}
