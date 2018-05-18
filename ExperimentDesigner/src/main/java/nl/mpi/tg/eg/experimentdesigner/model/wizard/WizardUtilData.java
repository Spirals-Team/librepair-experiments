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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @since Mar 1, 2018 10:43:43 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@XmlRootElement
public class WizardUtilData {

    protected WizardUtilEnum templateType;

    protected String experimentTitle;

    protected boolean showMenuBar;

    protected boolean showProgress;

    protected WizardUtilScreen[] screenData;

    protected String feedbackScreenText;

    public WizardUtilEnum getTemplateType() {
        return templateType;
    }

    public void setTemplateType(WizardUtilEnum templateType) {
        this.templateType = templateType;
    }

    public String getExperimentTitle() {
        return experimentTitle;
    }

    public void setExperimentTitle(String experimentTitle) {
        this.experimentTitle = experimentTitle;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isShowMenuBar() {
        return showMenuBar;
    }

    public void setShowMenuBar(boolean showMenuBar) {
        this.showMenuBar = showMenuBar;
    }

    public WizardUtilScreen[] getScreenData() {
        return screenData;
    }

    public void setScreenData(WizardUtilScreen[] screenData) {
        this.screenData = screenData;
    }

    public String getFeedbackScreenText() {
        return feedbackScreenText;
    }

    public void setFeedbackScreenText(String feedbackScreenText) {
        this.feedbackScreenText = feedbackScreenText;
    }
}
