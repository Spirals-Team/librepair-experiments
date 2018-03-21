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

import java.util.HashMap;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;

/**
 * @since Apr 5, 2016 11:50:04 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public interface WizardScreen {

//    public enum WizardScreenType {
//        MenuScreen(null, false, false),
//        TextScreen(null, false, false),
//        CompletionScreen(null, false, false),
//        UserSelectScreen(null, false, false),
//        AgreementScreen(null, true, false),
//        MetadataScreen(null, true, false),
//        AudioTestScreen(, false, true),
//        StimuliScreen(null, false, true);
//        final boolean hasMetadata;
//        final boolean hasStimuli;
//        final String[] fieldNames;
//
//        WizardScreenType(final String[] fieldNames, boolean hasMetadata, boolean hasStimuli) {
//            this.hasMetadata = hasMetadata;
//            this.hasStimuli = hasStimuli;
//            
//        }
//    }
//    final WizardScreenType wizardScreenType;
//    final WizardScreen prevWizardScreen;
//    final WizardScreen nextWizardScreen;
    final HashMap<String, String> namedFields = new HashMap<>();
//
//    public WizardScreen(WizardScreenType wizardScreenType, WizardScreen prevWizardScreen, WizardScreen nextWizardScreen) {
//        this.wizardScreenType = wizardScreenType;
//        this.prevWizardScreen = prevWizardScreen;
//        this.nextWizardScreen = nextWizardScreen;
//    }
//

//    String[] getFieldNames();
//    public void setFieldValue(String fieldName, String fieldValue);
//    public String getFieldValue(String fieldName);
//    @Deprecated
//    PresenterScreen getPresenterScreen();
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, final Experiment experiment, final boolean obfuscateScreenNames, final long displayOrder);

//    public WizardScreen getBackWizardScreen();
//    public void setBackWizardScreen(WizardScreen backWizardScreen);
//    public WizardScreen getNextWizardScreen();
//    public void setNextWizardScreen(WizardScreen nextWizardScreen);
    @Deprecated
    public void setBackWizardScreen(WizardScreen backWizardScreen);

    @Deprecated
    public void setNextWizardScreen(WizardScreen nextWizardScreen);

    public WizardScreenData getWizardScreenData();

//    public void setWizardScreenData(WizardScreenData wizardScreenData);
    public WizardScreenData getBackWizardScreenData();

    public WizardScreenData getNextWizardScreenData();

    public void setBackWizardScreenData(WizardScreenData wizardScreenData);

    public void setNextWizardScreenData(WizardScreenData wizardScreenData);

    public void setScreenText(String screenText);

    public void setScreenTag(String selfTag);

    public void setNextButton(String nextButton);

    public String getScreenTextInfo(int index);

    public String getScreenBooleanInfo(int index);

    public String getScreenIntegerInfo(int index);

    public String getNextButtonInfo(int index);
}
