/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics
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

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import nl.mpi.tg.eg.experimentdesigner.dao.ExperimentRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.MetadataRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.PresenterFeatureRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.PresenterScreenRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.PublishEventRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.TranslationRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.WizardRepository;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.util.DefaultExperiments;
import nl.mpi.tg.eg.experimentdesigner.util.DobesAnnotator;
import nl.mpi.tg.eg.experimentdesigner.util.FactOrFiction;
import nl.mpi.tg.eg.experimentdesigner.util.GuineaPigProject;
import nl.mpi.tg.eg.experimentdesigner.util.HRExperiment01;
import nl.mpi.tg.eg.experimentdesigner.util.HRPretest;
import nl.mpi.tg.eg.experimentdesigner.util.HRPretest02;
import nl.mpi.tg.eg.experimentdesigner.util.JenaFieldKit;
import nl.mpi.tg.eg.experimentdesigner.util.KinOathExample;
import nl.mpi.tg.eg.experimentdesigner.util.ManipulatedContours;
import nl.mpi.tg.eg.experimentdesigner.util.MultiParticipant;
import nl.mpi.tg.eg.experimentdesigner.util.NblExperiment01;
import nl.mpi.tg.eg.experimentdesigner.util.NonWacq;
import nl.mpi.tg.eg.experimentdesigner.util.Parcours;
import nl.mpi.tg.eg.experimentdesigner.util.RdExperiment02;
import nl.mpi.tg.eg.experimentdesigner.util.RosselFieldKit;
import nl.mpi.tg.eg.experimentdesigner.util.Sara01;
import nl.mpi.tg.eg.experimentdesigner.util.SentenceCompletion;
import nl.mpi.tg.eg.experimentdesigner.util.SentencesRatingTask;
import nl.mpi.tg.eg.experimentdesigner.util.ShawiFieldKit;
import nl.mpi.tg.eg.experimentdesigner.util.ShortMultiparticipant01;
import nl.mpi.tg.eg.experimentdesigner.util.SynQuiz2;
import nl.mpi.tg.eg.experimentdesigner.util.TransmissionChain;
import nl.mpi.tg.eg.experimentdesigner.util.WellspringsSamoanFieldKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @since Nov 4, 2015 1:59:50 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Controller
public class ExperimentController {

    @Autowired
    ExperimentRepository experimentRepository;
    @Autowired
    PublishEventRepository eventRepository;
    @Autowired
    PresenterScreenRepository presenterScreenRepository;
    @Autowired
    PresenterFeatureRepository presenterFeatureRepository;
    @Autowired
    MetadataRepository metadataRepository;
    @Autowired
    WizardRepository wizardRepository;
    @Autowired
    TranslationRepository translationRepository;

    @RequestMapping("/experiments")
    public String designView(Model model, HttpServletRequest request) {
        model.addAttribute("contextPath", request.getContextPath());
        model.addAttribute("detailType", "experiments");
        model.addAttribute("allExperiments", experimentRepository.findAll());
        return "design";
    }

    @RequestMapping("/experiment/{experiment}")
    public String designView(Model model, HttpServletRequest request, @PathVariable Experiment experiment) {
        model.addAttribute("contextPath", request.getContextPath());
        model.addAttribute("detailType", "configuration");
        model.addAttribute("experiment", experiment);
        return "design";
    }

    @RequestMapping("/experiments/add")
    public String addExperiment(Model model, HttpServletRequest request) {
        Experiment createdExperiment = DefaultExperiments.getDefault();
        experimentRepository.save(createdExperiment);
        model.addAttribute("contextPath", request.getContextPath());
        model.addAttribute("detailType", "configuration");
        model.addAttribute("experiment", createdExperiment);
        return "design";
    }

    @RequestMapping("/wizard")
    public String listWizard(Model model, HttpServletRequest request) {
        model.addAttribute("contextPath", request.getContextPath());
        model.addAttribute("detailType", "wizard");
        model.addAttribute("wizardList", wizardRepository.findAll());
        return "design";
    }

    @RequestMapping("/wizard/start")
    public String startWizard(Model model, HttpServletRequest request) {
        model.addAttribute("contextPath", request.getContextPath());
        model.addAttribute("detailType", "wizard");
        final String[] wizardTemplateList = new String[]{
            "MultiParticipant",
            "Sentveri_exp3",
            "Dobes Annotator",
            "All Options",
            "Vanuatu FieldKit",
            "Shawi FieldKit",
            "AntwoordRaden",
            "Leeservaring",
            "SynQuiz2",
            "Online Emotions",
            "Parcours01",
            "HRPretest02",
            "HRPretest",
            "TransmissionChain",
            "Zinnen afmaken",
            "Kinship Example",
            "RosselFieldKit",
            "WellspringsSamoan",
            "Zinnen Beoordelen",
            "ManipulatedContours",
            "ShortMultiparticipant01",
            "NonWacq",
            "SentencesRatingTask",
            "GuineaPigProject"};
        Arrays.sort(wizardTemplateList);
        model.addAttribute("templateList", wizardTemplateList);
        model.addAttribute("existingWizardList", wizardRepository.findDistinctAppName());
        return "design";
    }

    @RequestMapping("/wizard/create")
    public String showWizard(Model model, @ModelAttribute WizardData wizardData, HttpServletRequest request, @RequestParam String templateName, @RequestParam String experimentName) {
//        Experiment createdExperiment = DefaultExperiments.getDefault();
//        experimentRepository.save(createdExperiment);
        if (templateName != null) {
            switch (templateName) {
                case "Sentveri_exp3":
//                    wizardData = new Sentveri_exp3().getWizardData();
                    break;
                case "Dobes Annotator":
                    wizardData = new DobesAnnotator().getWizardData();
                    break;
                case "All Options":
                    break;
                case "Vanuatu FieldKit":
                    wizardData = new JenaFieldKit().getWizardData();
                    break;
                case "Shawi FieldKit":
                    wizardData = new ShawiFieldKit().getWizardData();
                    break;
                case "AntwoordRaden":
                    wizardData = new Sara01().getWizardData();
                    break;
                case "Leeservaring":
                    wizardData = new FactOrFiction().getWizardData();
                    break;
                case "SynQuiz2":
                    wizardData = new SynQuiz2().getWizardData();
                    break;
                case "Zinnen Beoordelen":
                    wizardData = new NblExperiment01().getWizardData();
                    break;
                case "Online Emotions":
                    wizardData = new HRExperiment01().getWizardData();
                    break;
                case "HRPretest":
                    wizardData = new HRPretest().getWizardData();
                    break;
                case "HRPretest02":
                    wizardData = new HRPretest02().getWizardData();
                    break;
                case "TransmissionChain":
                    wizardData = new TransmissionChain().getWizardData();
                    break;
                case "Zinnen afmaken":
                    wizardData = new RdExperiment02().getWizardData();
                    break;
                case "Kinship Example":
                    wizardData = new KinOathExample().getWizardData();
                    break;
                case "RosselFieldKit":
                    wizardData = new RosselFieldKit().getWizardData();
                    break;
                case "WellspringsSamoan":
                    wizardData = new WellspringsSamoanFieldKit().getWizardData();
                    break;
                case "MultiParticipant":
                    wizardData = new MultiParticipant().getWizardData();
                    break;
                case "ManipulatedContours":
                    wizardData = new ManipulatedContours().getWizardData();
                    break;
                case "Parcours01":
                    wizardData = new SentenceCompletion(new Parcours()).getWizardData();
                    break;
                case "ShortMultiparticipant01":
                    wizardData = new ShortMultiparticipant01().getWizardData();
                    break;
                case "NonWacq":
                    wizardData = new NonWacq().getWizardData();
                    break;
                case "SentencesRatingTask":
                    wizardData = new SentencesRatingTask().getWizardData();
                    break;
                case "GuineaPigProject":
                    wizardData = new GuineaPigProject().getWizardData();
                    break;
            }
        }
        wizardData.setAppName(experimentName);
        wizardRepository.save(wizardData);
//        model.addAttribute("contextPath", request.getContextPath());
//        model.addAttribute("detailType", "wizard");
//        model.addAttribute("wizardData", wizardData);
        return "redirect:/wizard/edit/" + wizardData.getId();
    }

    @RequestMapping("/wizard/edit/{wizardData}")
    public String editWizard(Model model, HttpServletRequest request, @PathVariable WizardData wizardData) {
        model.addAttribute("contextPath", request.getContextPath());
        model.addAttribute("detailType", "wizard");
        model.addAttribute("wizardData", wizardData);
        return "design";
    }

    @RequestMapping("/experiments/translations")
    public String showTranslations(Model model, HttpServletRequest request) {
//        Experiment createdExperiment = DefaultExperiments.getDefault();
//        experimentRepository.save(createdExperiment);
        model.addAttribute("contextPath", request.getContextPath());
        model.addAttribute("detailType", "translations");
//        model.addAttribute("localeList", translationRepository.getAllLocales());
        model.addAttribute("localeList", new String[]{"en", "nl", "de", "ru"}); // todo: add a qeury for the local list
        model.addAttribute("translationTexts", translationRepository.findAll());
        return "design";
    }

    @RequestMapping("/experiments/createDefaults")
    public String createDefaults(Model model, HttpServletRequest request) {
        // todo: this is currently here to simplify the development process and should be removed in production
//        experimentRepository.deleteAll();
//        if (experimentRepository.count() == 0) {
        new DefaultExperiments().insertDefaultExperiment(presenterScreenRepository, presenterFeatureRepository, metadataRepository, experimentRepository, eventRepository, translationRepository);
//        }
        return "redirect:/";
    }

    @RequestMapping(value = "/experiment/{experiment}/update", method = RequestMethod.POST)
    public String updateScreen(@ModelAttribute Experiment updatedExperiment, Model model, HttpServletRequest request, @PathVariable Experiment experiment) {
        experiment.setAppNameDisplay(updatedExperiment.getAppNameDisplay());
        experiment.setAppNameInternal(updatedExperiment.getAppNameInternal());
        experiment.setBackgroundColour(updatedExperiment.getBackgroundColour());
        experiment.setComplementColour0(updatedExperiment.getComplementColour0());
        experiment.setComplementColour1(updatedExperiment.getComplementColour1());
        experiment.setComplementColour2(updatedExperiment.getComplementColour2());
        experiment.setComplementColour3(updatedExperiment.getComplementColour3());
        experiment.setComplementColour4(updatedExperiment.getComplementColour4());
        experiment.setPrimaryColour0(updatedExperiment.getPrimaryColour0());
        experiment.setPrimaryColour1(updatedExperiment.getPrimaryColour1());
        experiment.setPrimaryColour2(updatedExperiment.getPrimaryColour2());
        experiment.setPrimaryColour3(updatedExperiment.getPrimaryColour3());
        experiment.setPrimaryColour4(updatedExperiment.getPrimaryColour4());
        experimentRepository.save(experiment);
        model.addAttribute("contextPath", request.getContextPath());
        model.addAttribute("detailType", "configuration");
        model.addAttribute("experiment", experiment);
        return "design";
    }
}
