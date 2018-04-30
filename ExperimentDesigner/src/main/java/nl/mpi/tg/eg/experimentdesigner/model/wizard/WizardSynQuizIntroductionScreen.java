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

import java.util.ArrayList;
import java.util.List;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.link;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.maxHeight;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.maxWidth;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.percentOfPage;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.src;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.target;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;
import static nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardScreenEnum.WizardSinQuizIntroductionScreen;

/**
 * @since Nov 3, 2016 1:54:22 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardSynQuizIntroductionScreen extends AbstractWizardScreen {

    private final String imageSize = "80";

    public WizardSynQuizIntroductionScreen() {
        super(WizardSinQuizIntroductionScreen);
    }

    public WizardSynQuizIntroductionScreen(
            final String screenName,
            final String decoding_the_Genetics_of_Synaesthesia,
            final String we_are_studying_the_genetic_basis_of_syna,
            final String how_our_study_works,
            final String staticstudy_diagramsvg,
            final String the_synaesthesia_tests_take_about_20_minu,
            final String for_more_information_about_synaesthesia_p,
            final String this_project_is_organised_and_funded_by_t, final String participateButton,
            final WizardScreenData participatePresenter
    ) {
        super(WizardSinQuizIntroductionScreen, screenName, screenName, screenName);
        wizardScreenData.setScreenTag(screenName);
        wizardScreenData.setScreenTitle(decoding_the_Genetics_of_Synaesthesia);
        wizardScreenData.setScreenText(0, we_are_studying_the_genetic_basis_of_syna);
        wizardScreenData.setScreenText(1, how_our_study_works);
        wizardScreenData.setScreenText(2, staticstudy_diagramsvg);
        wizardScreenData.setScreenText(3, the_synaesthesia_tests_take_about_20_minu);
        wizardScreenData.setScreenText(4, for_more_information_about_synaesthesia_p);
        wizardScreenData.setScreenText(5, this_project_is_organised_and_funded_by_t);
        wizardScreenData.setNextButton(new String[]{participateButton});
        wizardScreenData.getMenuWizardScreenData().add(participatePresenter);
    }

    @Override
    public String getScreenBooleanInfo(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getScreenTextInfo(int index) {
        return new String[]{"we_are_studying_the_genetic_basis_of_syna",
            "how_our_study_works",
            "staticstudy_diagramsvg",
            "the_synaesthesia_tests_take_about_20_minu",
            "for_more_information_about_synaesthesia_p",
            "this_project_is_organised_and_funded_by_t"}[index];
    }

    @Override
    public String getNextButtonInfo(int index) {
        return new String[]{"Participate Button Label"}[index];
    }

    @Override
    public String getScreenIntegerInfo(int index) {
        return new String[]{}[index];
    }

    @Override
    public PresenterScreen[] populatePresenterScreen(WizardScreenData storedWizardScreenData, Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        storedWizardScreenData.getPresenterScreen().setPresenterType(PresenterType.text);
        super.populatePresenterScreen(storedWizardScreenData, experiment, obfuscateScreenNames, displayOrder);
        final PresenterScreen presenterScreen = storedWizardScreenData.getPresenterScreen();
        List<PresenterFeature> presenterFeatureList = presenterScreen.getPresenterFeatureList();

//        presenterFeatureList.add(new PresenterFeature(FeatureType.centrePage, null));
        presenterFeatureList.add(new PresenterFeature(FeatureType.plainText, storedWizardScreenData.getScreenText(0)));
        presenterFeatureList.add(new PresenterFeature(FeatureType.addPadding, null));

        presenterFeatureList.add(new PresenterFeature(FeatureType.plainText, storedWizardScreenData.getScreenText(1)));
        presenterFeatureList.add(new PresenterFeature(FeatureType.addPadding, null));
        final PresenterFeature studyDiagramFeature = new PresenterFeature(FeatureType.image, null);
        studyDiagramFeature.addFeatureAttributes(percentOfPage, imageSize);
        studyDiagramFeature.addFeatureAttributes(maxHeight, imageSize);
        studyDiagramFeature.addFeatureAttributes(maxWidth, imageSize);
        studyDiagramFeature.addFeatureAttributes(src, storedWizardScreenData.getScreenText(2));
        studyDiagramFeature.addFeatureAttributes(link, "");
        presenterFeatureList.add(studyDiagramFeature);
        presenterFeatureList.add(new PresenterFeature(FeatureType.addPadding, null));
        presenterFeatureList.add(new PresenterFeature(FeatureType.plainText, storedWizardScreenData.getScreenText(3)));
        presenterFeatureList.add(new PresenterFeature(FeatureType.addPadding, null));

        final PresenterFeature targetButtonFeature = new PresenterFeature(FeatureType.targetButton, storedWizardScreenData.getNextButton()[0]);
        targetButtonFeature.addFeatureAttributes(target, storedWizardScreenData.getMenuWizardScreenData().get(0).getScreenTag());
        presenterFeatureList.add(targetButtonFeature);
        presenterFeatureList.add(new PresenterFeature(FeatureType.addPadding, null));
        presenterFeatureList.add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(4)));
        presenterFeatureList.add(new PresenterFeature(FeatureType.htmlText, storedWizardScreenData.getScreenText(5)));
        experiment.getPresenterScreen().add(storedWizardScreenData.getPresenterScreen());
        return new PresenterScreen[]{storedWizardScreenData.getPresenterScreen()};
    }
}
