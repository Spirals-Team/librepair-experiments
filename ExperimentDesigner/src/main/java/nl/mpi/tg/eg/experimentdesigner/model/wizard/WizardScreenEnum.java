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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import static nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardKinDiagramScreen.ExampleType.EditableEntitesDiagram;
import static nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardKinDiagramScreen.ExampleType.PredefinedKinDiagram;
import static nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardKinDiagramScreen.ExampleType.SavedKinDiagram;

/**
 * @since Oct 25(new ()), 2016 2:21:49 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@XmlType
@XmlEnum(String.class)
public enum WizardScreenEnum {
    WizardDebugAboutScreen(new WizardAboutScreen(true)),
    WizardAboutScreen(new WizardAboutScreen(false)),
    WizardAgreementScreen(new WizardAgreementScreen()),
    WizardAnimatedStimuliScreen(new WizardAnimatedStimuliScreen()),
    WizardAudioRecorderMetadataScreen(new WizardAudioRecorderMetadataScreen()),
    WizardAudioTestScreen(new WizardAudioTestScreen()),
    WizardCompletionScreen(new WizardCompletionScreen()),
    WizardSubmitOfflineDataScreen(new WizardSubmitOfflineDataScreen()),
    WizardEditUserScreen(new WizardEditUserScreen()),
    WizardStimuliJsonMetadataScreen(new WizardStimuliJsonMetadataScreen()),
    WizardExistingUserCheckScreen(new WizardExistingUserCheckScreen()),
    WizardPredefinedKinDiagram(new WizardKinDiagramScreen(PredefinedKinDiagram)),
    WizardSavedKinDiagram(new WizardKinDiagramScreen(SavedKinDiagram)),
    WizardEditableEntitesDiagram(new WizardKinDiagramScreen(EditableEntitesDiagram)),
    WizardKinshipScreen(new WizardKinshipScreen()),
    WizardMenuScreen(new WizardMenuScreen()),
    WizardRandomStimulusScreen(new WizardRandomStimulusScreen()),
    WizardSelectUserScreen(new WizardSelectUserScreen()),
    WizardStimulusScreen(new WizardStimulusScreen()),
    WizardGridStimulusScreen(new WizardGridStimulusScreen()),
    WizardScoreThresholdScreen(new WizardScoreThresholdScreen()),
    WizardSubmitDataScreen(new WizardSubmitDataScreen()),
    WizardTextScreen(new WizardTextScreen()),
    WizardVideoAudioOptionStimulusScreen(new WizardVideoAudioOptionStimulusScreen()),
    WizardVideoTimelineScreen(new WizardVideoTimelineScreen()),
    WizardWelcomeScreen(new WizardWelcomeScreen()),
    WizardSinQuizIntroductionScreen(new WizardSynQuizIntroductionScreen()),
    WizardSynQuizStimulusScreen(new WizardSynQuizStimulusScreen()),
    WizardSynQuizReportScreen(new WizardSynQuizReportScreen()),
    WizardSynQuizSumbitScreen(new WizardSynQuizSumbitScreen()),
    WizardMultiParticipantScreen(new WizardMultiParticipantScreen()),
    WizardMultiParticipantGroupFormationScreen(new WizardMultiParticipantGroupFormationScreen());
    public final WizardScreen wizardScreen;

    private WizardScreenEnum(WizardScreen wizardScreen) {
        this.wizardScreen = wizardScreen;
    }
}
