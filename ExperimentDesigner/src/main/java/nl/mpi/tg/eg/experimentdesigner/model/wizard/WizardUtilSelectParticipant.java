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
 * @since Apr 23, 2018 3:24:17 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardUtilSelectParticipant {

    protected String selectParticipantTitle;
    protected String newParticipantButton;
    protected String resumeParticipantButton;
    protected String newParticipantText;
    protected String resumeParticipantText;

    public String getSelectParticipantTitle() {
        return selectParticipantTitle;
    }

    public void setSelectParticipantTitle(String selectParticipantTitle) {
        this.selectParticipantTitle = selectParticipantTitle;
    }

    public String getNewParticipantButton() {
        return newParticipantButton;
    }

    public void setNewParticipantButton(String newParticipantButton) {
        this.newParticipantButton = newParticipantButton;
    }

    public String getResumeParticipantButton() {
        return resumeParticipantButton;
    }

    public void setResumeParticipantButton(String resumeParticipantButton) {
        this.resumeParticipantButton = resumeParticipantButton;
    }

    public String getNewParticipantText() {
        return newParticipantText;
    }

    public void setNewParticipantText(String newParticipantText) {
        this.newParticipantText = newParticipantText;
    }

    public String getResumeParticipantText() {
        return resumeParticipantText;
    }

    public void setResumeParticipantText(String resumeParticipantText) {
        this.resumeParticipantText = resumeParticipantText;
    }
}
