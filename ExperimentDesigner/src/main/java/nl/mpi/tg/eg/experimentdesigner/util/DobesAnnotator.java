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
package nl.mpi.tg.eg.experimentdesigner.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.mpi.tg.eg.experimentdesigner.controller.WizardController;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.Stimulus;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAboutScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardVideoTimelineScreen;

/**
 * @since Oct 25, 2016 1:08:41 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class DobesAnnotator {

    private static final Logger LOG = Logger.getLogger(DefaultExperiments.class.getName());
    private final WizardController wizardController = new WizardController();

    private ArrayList<Stimulus> addDobesStimuli() {
        final ArrayList<Stimulus> stimuliList = new ArrayList<>();
        final HashSet<String> tagSet = new HashSet<>();

        tagSet.add("videotag");
        for (int i = 0; i < 10; i++) {
            final Stimulus stimulus = new Stimulus("videotag" + i, null, null, "videotag" + i + ".png", "videotag" + i, null, 0, tagSet, null, null);
            final URL resourceUrl = DefaultExperiments.class.getResource("/stimuli/videotag" + (i + 1) + ".png");
            File file = new File(resourceUrl.getFile());
            byte[] fileBytes = new byte[(int) file.length()];

            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(fileBytes);
                fileInputStream.close();
                stimulus.setImageData(fileBytes);
            } catch (Exception e) {
                LOG.log(Level.INFO, "failed to load stimuli resource file", e);
            }
            stimuliList.add(stimulus);
        }
        for (String tag : new String[]{"sim", "mid", "diff", "noise"}) {
            for (String label : new String[]{"rabbit", "cat", "muffin", "you"}) {
                tagSet.clear();
                tagSet.add(tag);
                stimuliList.add(new Stimulus(tag + "_" + label, tag + "_" + label, tag + "_" + label, tag + "_" + label + ".jpg", tag + "_" + label, tag + "_" + label, 0, tagSet, null, null));
            }
        }
        tagSet.clear();
        for (String word : "termites scorpions centipedes".split(" ")) {
            for (String speaker : "Rocket Festival Thai ประเพณีบุญบั้งไฟ Lao ບຸນບັ້ງໄຟ".split(" ")) {
                for (int i = 0; i < 6; i++) {
                    stimuliList.add(new Stimulus(word + "_" + speaker + "_" + i, word + "_" + speaker + "_" + i + ".mp3", word + "_" + speaker + "_" + i + ".mp4", word + "_" + speaker + "_" + i + ".jpg", word + "_" + speaker + "_" + i, word + "_" + speaker + "_" + i, 0, new HashSet<>(Arrays.asList(new String[]{word, speaker})), null, null));
                }
            }
        }
        return stimuliList;
    }

    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName("Dobes Annotator");
        wizardData.setShowMenuBar(true);
        wizardData.setTextFontSize(17);
        wizardData.setObfuscateScreenNames(false);
        final WizardVideoTimelineScreen timelineScreen = new WizardVideoTimelineScreen("Annotation Timeline Panel");
        timelineScreen.getWizardScreenData().setStimuliCount(10);
        timelineScreen.getWizardScreenData().setScreenMediaPath("http://corpus1.mpi.nl/media-archive/Info/enctest/aspen");
        timelineScreen.getWizardScreenData().setStimuli(addDobesStimuli());
        timelineScreen.getWizardScreenData().setStimuliRandomTags(new String[]{"videotag"});
        final WizardAboutScreen aboutScreen = new WizardAboutScreen(true);
        timelineScreen.setBackWizardScreen(aboutScreen);
        aboutScreen.setBackWizardScreen(timelineScreen);
        wizardData.addScreen(timelineScreen);
        wizardData.addScreen(aboutScreen);
        return wizardData;
    }

    public Experiment getExperiment() {
        return wizardController.getExperiment(getWizardData());
    }
}
