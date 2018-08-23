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

    protected WizardUtilMenu menuScreen;

    protected WizardUtilText agreementScreen;

    protected WizardUtilSelectParticipant selectParticipantMenu;

    protected WizardUtilStimuliData stimuliData;

    protected WizardUtilMetadata metadataScreen;

    protected WizardUtilSendData sendDataScreen;

    protected WizardUtilAudioTest audioScreen;

    public WizardUtilText getTextScreen() {
        return textScreen;
    }

    public void setTextScreen(WizardUtilText textScreen) {
        this.textScreen = textScreen;
    }

    public WizardUtilMenu getMenuScreen() {
        return menuScreen;
    }

    public void setMenuScreen(WizardUtilMenu menuScreen) {
        this.menuScreen = menuScreen;
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

    public WizardUtilMetadata getMetadataScreen() {
        return metadataScreen;
    }

    public void setMetadataScreen(WizardUtilMetadata metadataScreen) {
        this.metadataScreen = metadataScreen;
    }

    public WizardUtilSendData getSendDataScreen() {
        return sendDataScreen;
    }

    public void setSendDataScreen(WizardUtilSendData sendDataScreen) {
        this.sendDataScreen = sendDataScreen;
    }

    public WizardUtilAudioTest getAudioScreen() {
        return audioScreen;
    }

    public void setAudioScreen(WizardUtilAudioTest audioScreen) {
        this.audioScreen = audioScreen;
    }
}
