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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.mpi.tg.eg.experimentdesigner.dao.ExperimentRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.StimuliRepository;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.Stimulus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @since Nov 12, 2015 12:02:29 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Controller
public class StimulusController {

    private static final Logger LOG = Logger.getLogger(StimulusController.class.getName());

    @Autowired
    StimuliRepository stimuliRepository;
    @Autowired
    ExperimentRepository experimentRepository;

    @RequestMapping(value = "/experiment/{appName}/stimulus/add", method = RequestMethod.POST)
    public String addStimulus(final HttpServletRequest req, Model model, @ModelAttribute Stimulus stimulus, @PathVariable String appName) {
        final Stimulus savedStimulus = stimuliRepository.save(stimulus);
        final Experiment experiment = experimentRepository.findByAppNameInternal(appName);
        experiment.getStimuli().add(stimulus);
        experimentRepository.save(experiment);
        model.addAttribute("updatedStimulus", savedStimulus);
//        populateModel(model, appName);
        return "stimuli :: stimulusRow";
    }

    @RequestMapping(value = "/experiment/{appName}/stimulus/update", method = RequestMethod.POST)
    public String updateStimulus(final HttpServletRequest req, Model model, @ModelAttribute Stimulus stimulus, @PathVariable String appName) {
        final Stimulus savedStimulus = stimuliRepository.save(stimulus);
        model.addAttribute("updatedStimulus", savedStimulus);
//        populateModel(model, appName);
        return "stimuli :: stimulusRow";
    }

    @RequestMapping(value = "/experiment/{appName}/stimulus/delete", method = RequestMethod.POST)
    public String deleteStimulus(final HttpServletRequest req, Model model, @ModelAttribute Stimulus stimulus, @PathVariable String appName) {
        final Stimulus storedStimulus = stimuliRepository.findOne(stimulus.getId());
        final Experiment experiment = experimentRepository.findByAppNameInternal(appName);
        experiment.getStimuli().remove(storedStimulus);
        experimentRepository.save(experiment);
        stimuliRepository.delete(storedStimulus);
        model.addAttribute("updatedStimulus", storedStimulus);
//        populateModel(model, appName);
        return "stimuli :: stimulusRow";
    }

    public enum UploadType {

        audio, video, image
    }

    @RequestMapping(value = "/experiment/{appName}/stimulus/{stimulus}/{fileType}/upload", method = RequestMethod.POST)
    public ResponseEntity<Object> uploadFile(final HttpServletRequest req, Model model, @PathVariable Stimulus stimulus, @PathVariable String appName,
            @PathVariable UploadType fileType,
            @RequestParam("file") MultipartFile uploadFile) {
        if (!uploadFile.isEmpty()) {
            try {
                byte[] uploadBytes = uploadFile.getBytes();
                switch (fileType) {
                    case audio:
                        stimulus.setAudioData(uploadBytes);
                        break;
                    case image:
                        stimulus.setImageData(uploadBytes);
                        break;
                    case video:
                        stimulus.setVideoData(uploadBytes);
                        break;
                }
                stimuliRepository.save(stimulus);
            } catch (IOException exception) {
                LOG.log(Level.INFO, "upload of stimulus failed", exception);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/wizard/{parentId}/stimulus/{fileName}/{fileType}/{subType}/upload", method = RequestMethod.POST)
    public String uploadFile(final HttpServletRequest req, Model model, HttpServletRequest request,
            //            @PathVariable Stimulus stimulus, 
            @PathVariable int parentId,
            @PathVariable String fileName,
            @PathVariable UploadType fileType,
            @PathVariable String subType,
            @RequestParam("stimulusFile") MultipartFile uploadFile) throws IOException {
        if (!uploadFile.isEmpty()) {
            Stimulus stimulus = new Stimulus();
            stimulus.setIdentifier(fileName);
            byte[] uploadBytes = uploadFile.getBytes();
            switch (fileType) {
                case audio:
                    stimulus.setAudioData(uploadBytes);
                    break;
                case image:
                    stimulus.setImageData(uploadBytes);
                    stimulus.setImagePath(fileName);
                    break;
                case video:
                    stimulus.setVideoData(uploadBytes);
                    break;
            }
            stimuliRepository.save(stimulus);
            model.addAttribute("updatedStimulus", stimulus);
            model.addAttribute("contextPath", request.getContextPath());
            return "stimuli :: stimulusRow";
        } else {
            return "stimuli :: stimulusError";
        }
    }

    @RequestMapping(value = "/stimuli/{stimulus}/{fileType}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> getFile(@PathVariable Stimulus stimulus,
            @PathVariable UploadType fileType
    ) {
        if (stimulus.getImageData() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok()
                    .contentLength(stimulus.getImageData().length)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new InputStreamResource(new ByteArrayInputStream(stimulus.getImageData())));
        }
    }
}
