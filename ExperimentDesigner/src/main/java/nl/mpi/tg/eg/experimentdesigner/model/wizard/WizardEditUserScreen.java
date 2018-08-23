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

import java.util.Arrays;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.Metadata;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;

/**
 * @since May 3, 2016 2:11:15 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardEditUserScreen extends AbstractWizardScreen {

    public WizardEditUserScreen() {
        super(WizardScreenEnum.WizardEditUserScreen, "EditUser", "EditUser", "EditUser");
        setSendData(false);
        setIgnoreNetworkError(false);
    }

    public WizardEditUserScreen(final String screenTitle, final String screenTag, String dispalyText, final String saveButtonLabel, final String postText, final AbstractWizardScreen alternateNextScreen, final String alternateButtonLabel, final boolean sendData, final boolean ignoreNetworkError, final String on_Error_Text) {
        super(WizardScreenEnum.WizardEditUserScreen, screenTitle, screenTitle, screenTag);
        this.wizardScreenData.setScreenText(0, dispalyText);
        this.wizardScreenData.setNextButton(new String[]{saveButtonLabel, alternateButtonLabel});
        this.wizardScreenData.setScreenText(1, postText);
        setSendData(sendData);
        setIgnoreNetworkError(ignoreNetworkError);
        this.wizardScreenData.setOn_Error_Text(on_Error_Text);
        if (alternateNextScreen != null) {
            this.wizardScreenData.getMenuWizardScreenData().add(0, alternateNextScreen.getWizardScreenData());
        }
    }

    @Override
    public String getScreenBooleanInfo(int index) {
        return new String[]{"Send Data", "Ignore Network Error"}[index];
    }

    @Override
    public String getScreenTextInfo(int index) {
        return new String[]{"dispalyText", "postText"}[index];
    }

    @Override
    public String getNextButtonInfo(int index) {
        return new String[]{"Save Button Label", "Alternate Button Label"}[index];
    }

    @Override
    public String getScreenIntegerInfo(int index) {
        return new String[]{}[index];
    }

    final public void setSendData(boolean sendData) {
        this.wizardScreenData.setScreenBoolean(0, sendData);
    }

    final public void setIgnoreNetworkError(boolean ignoreNetworkError) {
        this.wizardScreenData.setScreenBoolean(1, ignoreNetworkError);
    }

    public void setOn_Error_Text(String on_Error_Text) {
        this.wizardScreenData.setOn_Error_Text(on_Error_Text);
    }

    public void setWorkerIdField() {
        wizardScreenData.getMetadataFields().add(new Metadata("workerId", "Login code", ".'{'3,'}'", "Please enter your login code.", false, null));
    }

    public void setFirstNameField() {
        wizardScreenData.getMetadataFields().add(new Metadata("firstName", "First name", ".'{'3,'}'", "Please enter at least three letters.", false, null));
    }

    public void setLastNameField() {
        wizardScreenData.getMetadataFields().add(new Metadata("lastName", "Last name", ".'{'3,'}'", "Please enter at least three letters.", false, null));

    }

    public void setEmailAddressField() {
        wizardScreenData.getMetadataFields().add(new Metadata("emailAddress", "Email address", "^[^@]+@[^@]+$", "Please enter a valid email address.", false, null));
    }

    public void setOptionCheckBox1(String optionCheckBox1) {
        wizardScreenData.getMetadataFields().add(new Metadata("optionCheckBox1", optionCheckBox1, "true|false", "Please enter true or false.", false, null));
    }

    public void setOptionCheckBox2(String optionCheckBox2) {
        wizardScreenData.getMetadataFields().add(new Metadata("optionCheckBox2", optionCheckBox2, "true|false", "Please enter true or false.", false, null));
    }

    public void setMandatoryCheckBox(String mandatoryCheckBox) {
        wizardScreenData.getMetadataFields().add(new Metadata("mandatoryCheckBox", mandatoryCheckBox, "true", "Please agree to continue.", false, null));
    }

    public void setAgeField() {
        wizardScreenData.getMetadataFields().add(new Metadata("age", "Age", "[0-9]+", "Please enter a valid age.", false, null));
    }

    public void setGenderField() {
        wizardScreenData.getMetadataFields().add(new Metadata("gender", "Gender", "|male|female|other", null, false, null));
    }

    public void setMandatoryGenderField() {
        wizardScreenData.getMetadataFields().add(new Metadata("gender", "Gender", "female|male|other", "Please choose a gender.", false, null));
    }

    public void setCustomTextField(String customTextField) {
        wizardScreenData.getMetadataFields().add(new Metadata("customTextField1", customTextField, ".'{'3,'}'", "Please enter at least three letters.", false, null));
    }

    public void setCustomFields(String[] customFields) {
        if (customFields != null) {
            for (String fieldString : customFields) {
                insertMetadataByString(fieldString);
            }
        }
    }

    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.metadata);
        if (storedWizardScreenData.getScreenText(0) != null) {
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(0)));
        }
        for (Metadata metadata : storedWizardScreenData.getMetadataFields()) {
            experiment.getMetadata().add(metadata);
            // todo: this metadataFieldConnection use needs to be replaced with wizard parameters
            final PresenterFeature metadataField = new PresenterFeature((metadata.getPostName().startsWith("connection")) ? FeatureType.metadataFieldConnection : FeatureType.metadataField, null);
            if (metadata.getPostName().startsWith("connection")) {
                metadataField.addFeatureAttributes(FeatureAttribute.linkedFieldName, "workerId");
            }
            metadataField.addFeatureAttributes(FeatureAttribute.fieldName, metadata.getPostName());
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(metadataField);
        }
//        if (storedWizardScreenData.getMetadataFields().isEmpty()) {
//            presenterScreen.getPresenterFeatureList().add(new PresenterFeature(FeatureType.allMetadataFields, null));
//        }
        final PresenterFeature saveMetadataButton = new PresenterFeature(FeatureType.saveMetadataButton, storedWizardScreenData.getNextButton()[0]);
        saveMetadataButton.addFeatureAttributes(FeatureAttribute.sendData, Boolean.toString(storedWizardScreenData.getScreenBoolean(0)));
        saveMetadataButton.addFeatureAttributes(FeatureAttribute.networkErrorMessage, storedWizardScreenData.getOn_Error_Text());
        final PresenterFeature onErrorFeature = new PresenterFeature(FeatureType.onError, null);
        if (storedWizardScreenData.getScreenBoolean(1)) {
            onErrorFeature.addFeature(FeatureType.gotoPresenter, null, storedWizardScreenData.getNextWizardScreenData().getScreenTag());
        }
        saveMetadataButton.getPresenterFeatureList().add(onErrorFeature);
        final PresenterFeature onSuccessFeature = new PresenterFeature(FeatureType.onSuccess, null);
        final PresenterFeature menuButtonFeature = new PresenterFeature(FeatureType.gotoNextPresenter, null);
        onSuccessFeature.getPresenterFeatureList().add(menuButtonFeature);
        saveMetadataButton.getPresenterFeatureList().add(onSuccessFeature);
        storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(saveMetadataButton);
        if (storedWizardScreenData.getScreenText(1) != null && storedWizardScreenData.getMenuWizardScreenData().size() > 0) {
            storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
            if (storedWizardScreenData.getScreenText(1) != null) {
                storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(1)));
            }
            if (storedWizardScreenData.getMenuWizardScreenData().size() > 0) {
                final PresenterFeature alternateNextFeature = new PresenterFeature(FeatureType.targetButton, storedWizardScreenData.getNextButton()[1]);
                alternateNextFeature.addFeatureAttributes(FeatureAttribute.target, storedWizardScreenData.getMenuWizardScreenData().get(0).getScreenTag());
                storedWizardScreenData.getPresenterScreen().getPresenterFeatureList().add(alternateNextFeature);
            }
        }
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }

    final public void insertMetadataByString(String fieldString) {
// note: new implementations should use the method setStimuliSet in AbstractWizardScreen
        final String[] splitFieldString = fieldString.split(":");
        wizardScreenData.getMetadataFields().add(new Metadata(splitFieldString[0], splitFieldString[1], splitFieldString[2], splitFieldString[3], false, null));
    }

    public void insertMetadataField(final String label) {
        wizardScreenData.getMetadataFields().add(new Metadata(label.replaceAll("[^A-Za-z0-9]", "_"), label, "true|false", "Please enter true or false.", false, null));
    }

    public void insertMetadataField(final Metadata metadata) {
        wizardScreenData.getMetadataFields().add(metadata);
    }
}
