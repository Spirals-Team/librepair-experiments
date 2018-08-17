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
package nl.mpi.tg.eg.experimentdesigner.controller;

import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.mpi.tg.eg.experimentdesigner.dao.ExperimentRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.MetadataRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.PresenterFeatureRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.PresenterScreenRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.PublishEventRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.ScreenTextRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.WizardRepository;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.Metadata;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.ScreenText;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardScreenData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static nl.mpi.tg.eg.experimentdesigner.util.DefaultExperiments.getDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @since Feb 22, 2016 4:23:36 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Controller
public class WizardController {

    private static final Logger LOG = Logger.getLogger(StimulusController.class.getName());
    @Autowired
    PresenterScreenRepository presenterScreenRepository;
    @Autowired
    PublishEventRepository eventRepository;
    @Autowired
    PresenterFeatureRepository presenterFeatureRepository;
    @Autowired
    MetadataRepository metadataRepository;
    @Autowired
    ExperimentRepository experimentRepository;
    @Autowired
    WizardRepository wizardRepository;
    @Autowired
    ScreenTextRepository screenTextRepository;

//    @RequestMapping(value = "/wizard/json/{wizardData}", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
//    @ResponseStatus(HttpStatus.OK)
//    public @ResponseBody
//    WizardData getJson(@PathVariable WizardData wizardData) {
//        return wizardData;
//    }
    
//    @RequestMapping(value = "/experiments/wizard/create/{wizardData}")
//    public String create(final HttpServletRequest req, @PathVariable WizardData wizardData) {
//        final Experiment experiment = getExperiment(wizardData);
////        final Experiment experiment = wizardData.getExperiment();
////        final Experiment experiment = new Sara01().getExperiment();
//        experimentRepository.save(experiment);
//        return "redirect:/experiment/" + experiment.getId();
//    }
    
    @RequestMapping(value = "/wizard/update/screenText", method = RequestMethod.POST)
    public String screenText(final HttpServletRequest req,
            final Model model,
            final @ModelAttribute("screenTextId") ScreenText screenTextId,
            final @ModelAttribute("wizardData") WizardData wizardData,
            final @ModelAttribute("wizardScreenId") String wizardScreenId,
            final @ModelAttribute("wizardFragmentLabel") String wizardFragmentLabel,
            final @ModelAttribute("wizardFragmentName") String wizardFragmentName,
            final @ModelAttribute("screenText") String screenText
    ) {
        screenTextId.setScreenText(screenText);
        screenTextRepository.save(screenTextId);
        // todo: consider adding an owner to the screentext for permissions
        model.addAttribute("wizardFragmentValue", screenTextRepository.findOne(screenTextId.getId()).getScreenText());
        return "wizard :: wizardTextArea";
    }

    public Experiment getExperiment(WizardData wizardData) {
        final Experiment experiment = getExperiment(wizardData.getAppName().replaceAll("[^A-Za-z0-9]", "_"), wizardData.getAppName(), wizardData.isShowMenuBar());
        experiment.setTextFontSize(wizardData.getTextFontSize());
        experiment.setDataChannels(wizardData.getDataChannels());
        int currentDisplaySequence = 1;
        for (WizardScreenData wizardScreen : wizardData.getWizardScreens()) {
            final WizardScreen wizardScreenType = wizardScreen.getWizardScreenType().wizardScreen;
//            wizardScreenType.set
            // get the wizard screen defined in the enumeration which is storable in the DB
            PresenterScreen[] addedScreens = wizardScreenType.populatePresenterScreen(wizardScreen, experiment, wizardData.isObfuscateScreenNames(), currentDisplaySequence);
            currentDisplaySequence += addedScreens.length;
        }
        boolean hasWorkerId = false;
        for (Metadata metadataItem : experiment.getMetadata()) {
            if (metadataItem.getPostName().equals("workerId")) {
                hasWorkerId = true;
                break;
            }
        }
        if (!hasWorkerId) {
            addMetadata(experiment);
        }
        return experiment;
    }

    public Experiment getExperiment(String appNameInternal, String appName, boolean showMenuBar) {
        Experiment experiment = getDefault();
        experiment.setAppNameDisplay(appName);
        experiment.setAppNameInternal(appNameInternal);
        experiment.setShowMenuBar(showMenuBar);
        return experiment;
    }

    public void addMetadata(Experiment experiment) {
        final Metadata metadata = new Metadata("workerId", "Worker ID", ".'{'3,'}'", "Please enter at least three letters.", false, null);
        experiment.getMetadata().add(metadata);
    }
}
