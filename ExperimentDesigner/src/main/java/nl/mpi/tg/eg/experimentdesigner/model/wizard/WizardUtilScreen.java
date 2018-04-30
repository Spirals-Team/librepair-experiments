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

/**
 * @since Apr 23, 2018 11:22:34 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardUtilScreen {

    protected WizardUtilText textScreen;

    protected WizardUtilText agreementScreen;

    protected WizardUtilSelectParticipant selectParticipantMenu;

    protected WizardUtilStimuliData stimuliData;

    protected String metadataText;
    protected String[] metadataFields;

    public WizardUtilText getTextScreen() {
        return textScreen;
    }

    public void setTextScreen(WizardUtilText textScreen) {
        this.textScreen = textScreen;
    }

    public WizardUtilText getAgreementScreen() {
        return agreementScreen;
    }

    public void setAgreementScreen(WizardUtilText agreementScreen) {
        this.agreementScreen = agreementScreen;
    }

    public WizardUtilSelectParticipant getSelectParticipantMenu() {
        return selectParticipantMenu;
    }

    public void setSelectParticipantMenu(WizardUtilSelectParticipant selectParticipantMenu) {
        this.selectParticipantMenu = selectParticipantMenu;
    }

    public WizardUtilStimuliData getStimuliData() {
        return stimuliData;
    }

    public void setStimuliData(WizardUtilStimuliData stimuliData) {
        this.stimuliData = stimuliData;
    }

    public String getMetadataText() {
        return metadataText;
    }

    public void setMetadataText(String metadataText) {
        this.metadataText = metadataText;
    }

    public String[] getMetadataFields() {
        return metadataFields;
    }

    public void setMetadataFields(String[] metadataFields) {
        this.metadataFields = metadataFields;
    }
}
