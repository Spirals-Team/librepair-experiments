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

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import nl.mpi.tg.eg.experimentdesigner.dao.ExperimentRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.MetadataRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.PresenterFeatureRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.PresenterScreenRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.PublishEventRepository;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.Metadata;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;
import nl.mpi.tg.eg.experimentdesigner.util.DefaultExperiments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @since Aug 18, 2015 1:40:11 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Controller
public class DesignController {

    @Autowired
    PresenterScreenRepository presenterScreenRepository;
    @Autowired
    PresenterFeatureRepository presenterFeatureRepository;
    @Autowired
    MetadataRepository metadataRepository;
    @Autowired
    ExperimentRepository experimentRepository;
    @Autowired
    PublishEventRepository eventRepository;

    private void populateModel(Model model, final Experiment experiment) {
        model.addAttribute("screencount", presenterScreenRepository.count());
        model.addAttribute("experimentcount", experimentRepository.count());
        model.addAttribute("experiment", experiment);
        model.addAttribute("featurecount", presenterFeatureRepository.count());
        model.addAttribute("metadatacount", metadataRepository.count());
        model.addAttribute("featureattributes", FeatureAttribute.values());
        model.addAttribute("featuretypes", FeatureType.values());
        model.addAttribute("presentertypes", PresenterType.values());

    }

    @RequestMapping(value = "/experiment/{experiment}/screen/delete", method = RequestMethod.POST)
    public String deleteScreen(@ModelAttribute PresenterScreen prersenterScreen, Model model, HttpServletRequest request, @PathVariable Experiment experiment) {
        final PresenterScreen presenterToDelete = presenterScreenRepository.findOne(prersenterScreen.getId());
        if (presenterToDelete.getUsageCount() > 0) {
            throw new IllegalArgumentException("Cannot delete because this screen is in use by " + presenterToDelete.getUsageCount() + " screens.");
        }
        experiment.getPresenterScreen().remove(presenterToDelete);
        experimentRepository.save(experiment);
//        model.addAttribute("contextPath", request.getContextPath());
//        model.addAttribute("updatedPresenterScreen", null);
//        model.addAttribute("detailType", "screens");
//        populateModel(model, experiment);
//        return "screens :: screenRow";
        return "redirect:design";
    }

    @RequestMapping(value = "/experiment/{experiment}/screen/add", method = RequestMethod.POST)
    public String addScreen(@ModelAttribute PresenterScreen prersenterScreen, Model model, HttpServletRequest request, @PathVariable Experiment experiment) {
        if (prersenterScreen.getSelfPresenterTag() == null || prersenterScreen.getSelfPresenterTag().length() < 3) {
            throw new IllegalArgumentException("Self (Action) must be longer than three characters.");
        }
        if (!prersenterScreen.getSelfPresenterTag().matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("Self (Action) must only be letters or numbers.");
        }
        if (presenterScreenRepository.findBySelfPresenterTag(prersenterScreen.getSelfPresenterTag()) != null) {
            throw new IllegalArgumentException("Self (Action) must be unique.");
        }
        experiment.getPresenterScreen().add(prersenterScreen);
        final PresenterScreen savedScreen = presenterScreenRepository.save(prersenterScreen);
        experimentRepository.save(experiment);
        model.addAttribute("contextPath", request.getContextPath());
        model.addAttribute("updatedPresenterScreen", savedScreen);
        model.addAttribute("detailType", "screens");
        populateModel(model, experiment);
        return "screens :: screenRow";
    }

    @RequestMapping(value = "/experiment/{experiment}/screen/update", method = RequestMethod.POST)
    public String updateScreen(@ModelAttribute PresenterScreen prersenterScreen, Model model, HttpServletRequest request, @PathVariable Experiment experiment) {
//        experiment.getPresenterScreen().add(prersenterScreen);
        final PresenterScreen updatedScreen = presenterScreenRepository.findOne(prersenterScreen.getId());
        updatedScreen.setTitle(prersenterScreen.getTitle());
        updatedScreen.setMenuLabel(prersenterScreen.getMenuLabel());
//        updatedScreen.setSelfPresenterTag(prersenterScreen.getSelfPresenterTag());
        updatedScreen.setBackPresenter(prersenterScreen.getBackPresenter());
        updatedScreen.setNextPresenter(prersenterScreen.getNextPresenter());
        presenterScreenRepository.save(updatedScreen);
        model.addAttribute("contextPath", request.getContextPath());
        model.addAttribute("updatedPresenterScreen", updatedScreen);
        model.addAttribute("detailType", "screens");
        populateModel(model, experiment);
        return "screens :: screenRow";
    }

    @RequestMapping("/design")
    public String designView(Model model, HttpServletRequest request) {
        return "redirect:experiments";
    }

    @RequestMapping("/")
    public String designView1(Model model, HttpServletRequest request) {
        return "redirect:experiments";
    }

    @RequestMapping("/experiment/{experiment}/screen/{presenterScreen}")
    public String editPresenterScreen(Model model, HttpServletRequest request, @PathVariable Experiment experiment, @PathVariable PresenterScreen presenterScreen) {
        model.addAttribute("contextPath", request.getContextPath());
        model.addAttribute("detailType", "screen");
        model.addAttribute("presenterScreen", presenterScreen);
        populateModel(model, experiment);
        return "design";
    }

    @RequestMapping("/experiment/{experiment}/{detailType}")
    public String designView(Model model, HttpServletRequest request, @PathVariable Experiment experiment, @PathVariable String detailType) {
        model.addAttribute("contextPath", request.getContextPath());
        model.addAttribute("detailType", detailType);
        populateModel(model, experiment);
        return "design";
    }

    @RequestMapping(value = "/experiment/{experiment}/metadata/add", method = RequestMethod.POST)
    public String addMetadata(final HttpServletRequest req, Model model, @ModelAttribute Metadata metadata, @PathVariable Experiment experiment) {
        final Metadata savedMetadata = metadataRepository.save(metadata);
        experiment.getMetadata().add(metadata);
        experimentRepository.save(experiment);
        model.addAttribute("updatedMetadata", savedMetadata);
        populateModel(model, experiment);
        return "metadata :: metadataRow";
    }

    @RequestMapping(value = "/experiment/{experiment}/metadata/update", method = RequestMethod.POST)
    public String updateMetadata(final HttpServletRequest req, Model model, @ModelAttribute Metadata metadata, @PathVariable Experiment experiment) {
        final Metadata savedMetadata = metadataRepository.save(metadata);
        model.addAttribute("updatedMetadata", savedMetadata);
        populateModel(model, experiment);
        return "metadata :: metadataRow";
    }

    @RequestMapping(value = "/experiment/{experiment}/metadata/delete", method = RequestMethod.POST)
    public String deleteMetadata(final HttpServletRequest req, Model model, @ModelAttribute Metadata metadata, @PathVariable Experiment experiment) {
        final Metadata storedMetadata = metadataRepository.findOne(metadata.getId());
        experiment.getMetadata().remove(storedMetadata);
        experimentRepository.save(experiment);
        metadataRepository.delete(storedMetadata);
        model.addAttribute("updatedMetadata", null);
        populateModel(model, experiment);
        return "metadata :: metadataRow";
    }

    @RequestMapping(value = "/experiment/{experiment}/feature/add", method = RequestMethod.POST)
    public String addFeature(final HttpServletRequest req, Model model, @ModelAttribute PresenterFeature presenterFeature, @PathVariable Experiment experiment) {
        final Long rowId = Long.valueOf(req.getParameter("screenId"));
        final PresenterScreen presenterScreen = presenterScreenRepository.findOne(rowId);
        presenterFeatureRepository.save(presenterFeature);
        presenterScreen.getPresenterFeatureList().add(presenterFeature);
        presenterScreenRepository.save(presenterScreen);
        populateModel(model, experiment);
        model.addAttribute("features", presenterFeature);
        model.addAttribute("presenterScreen", presenterScreen);
        return "screens :: featuresrow";
    }

    @RequestMapping(value = "/experiment/{experiment}/feature/add", params = {"featureId"}, method = RequestMethod.POST)
    public String addSubFeature(final HttpServletRequest req, Model model, @ModelAttribute PresenterFeature childFeature, @PathVariable Experiment experiment) {
        final Long rowId = Long.valueOf(req.getParameter("featureId"));
        final PresenterFeature parentFeature = presenterFeatureRepository.findOne(rowId);
        presenterFeatureRepository.save(childFeature);
        parentFeature.getPresenterFeatureList().add(childFeature);
        presenterFeatureRepository.save(parentFeature);
        populateModel(model, experiment);
        model.addAttribute("features", childFeature);
        model.addAttribute("presenterScreen", parentFeature);
        return "screens :: featuresrow";
    }

    @RequestMapping(value = "/experiment/{experiment}/feature/delete", params = {"featureId"}, method = RequestMethod.POST)
    public String deleteSubFeature(final HttpServletRequest req, Model model, @ModelAttribute PresenterFeature deletableFeature, @PathVariable Experiment experiment) {
        final Long rowId = Long.valueOf(req.getParameter("featureId"));
        final PresenterFeature parentFeature = presenterFeatureRepository.findOne(rowId);
        parentFeature.getPresenterFeatureList().remove(deletableFeature);
        presenterFeatureRepository.save(parentFeature);
        presenterFeatureRepository.delete(deletableFeature.getId());
        model.addAttribute("features", null);
        return "screens :: featuresrow";
    }

    @RequestMapping(value = "/experiment/{experiment}/feature/delete", params = {"screenId"}, method = RequestMethod.POST)
    public String deleteFeature(final HttpServletRequest req, Model model, @ModelAttribute PresenterFeature deletableFeature, @PathVariable Experiment experiment) {
        final Long rowId = Long.valueOf(req.getParameter("screenId"));
        final PresenterScreen presenterScreen = presenterScreenRepository.findOne(rowId);
        presenterScreen.getPresenterFeatureList().remove(presenterFeatureRepository.findOne(deletableFeature.getId()));
        presenterScreenRepository.save(presenterScreen);
//        presenterFeatureRepository.delete(deletableFeature.getId());
        model.addAttribute("features", null);
        return "screens :: featuresrow";
    }

    @RequestMapping(value = "/experiment/{experiment}/feature/update", method = RequestMethod.POST)
    public String saveFeature(final HttpServletRequest req, Model model, @ModelAttribute PresenterFeature changedFeature, @PathVariable Experiment experiment) {
        final PresenterFeature presenterFeature = presenterFeatureRepository.findOne(changedFeature.getId());
        presenterFeature.setDisplayOrder(changedFeature.getDisplayOrder());
        presenterFeature.setFeatureAttributes(changedFeature.getFeatureAttributesMap());
        presenterFeature.setFeatureText(changedFeature.getFeatureText());
        presenterFeature.setStimulusTags(changedFeature.getStimulusTags());
        final HashMap<FeatureAttribute, String> featureAttributesMap = presenterFeature.getFeatureAttributesMap();
        for (FeatureAttribute featureAttribute : presenterFeature.getFeatureType().getFeatureAttributes()) {
            final String attributeValue = req.getParameter(featureAttribute.name());
            featureAttributesMap.put(featureAttribute, attributeValue);
        }
        presenterFeatureRepository.save(presenterFeature);
//        model.addAttribute("features", presenterFeature);
        return "screens :: featuresrow";
    }

    @RequestMapping(value = "/experiment/{experiment}", params = {"removeScreen"})
    public String removeScreen(final HttpServletRequest req, Model model, @PathVariable Experiment experiment) {
        final Long rowId = Long.valueOf(req.getParameter("removeScreen"));
        presenterScreenRepository.delete(rowId);
        populateModel(model, experiment);
        return "design";
    }
}
