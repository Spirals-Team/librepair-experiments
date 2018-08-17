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
 * @since May 16, 2018 2:44:47 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardUtilSendData extends WizardUtilText {

    protected boolean allowUserRestart;
    protected boolean generateCompletionCode;
    protected String postCompletionCodeText;

    public boolean isAllowUserRestart() {
        return allowUserRestart;
    }

    public void setAllowUserRestart(boolean allowUserRestart) {
        this.allowUserRestart = allowUserRestart;
    }

    public String getPostCompletionCodeText() {
        return postCompletionCodeText;
    }

    public void setPostCompletionCodeText(String postCompletionCodeText) {
        this.postCompletionCodeText = postCompletionCodeText;
    }

    public boolean isGenerateCompletionCode() {
        return generateCompletionCode;
    }

    public void setGenerateCompletionCode(boolean generateCompletionCode) {
        this.generateCompletionCode = generateCompletionCode;
    }
}
