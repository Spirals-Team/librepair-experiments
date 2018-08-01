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
package nl.mpi.tg.eg.experimentdesigner.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Logger;
import nl.mpi.tg.eg.experimentdesigner.dao.ExperimentRepository;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.dao.MetadataRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.PresenterFeatureRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.PresenterScreenRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.PublishEventRepository;
import nl.mpi.tg.eg.experimentdesigner.dao.TranslationRepository;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.maxHeight;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.maxWidth;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.percentOfPage;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.Metadata;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;
import nl.mpi.tg.eg.experimentdesigner.model.PublishEvents;
import nl.mpi.tg.eg.experimentdesigner.model.RandomGrouping;
import nl.mpi.tg.eg.experimentdesigner.model.Stimulus;

/**
 * @since Sep 8, 2015 3:19:56 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class DefaultExperiments {

    private static final Logger LOG = Logger.getLogger(DefaultExperiments.class.getName());

    public void insertDefaultExperiment(
            PresenterScreenRepository presenterScreenRepository,
            PresenterFeatureRepository presenterFeatureRepository,
            MetadataRepository metadataRepository,
            ExperimentRepository experimentRepository,
            PublishEventRepository eventRepository,
            TranslationRepository translationRepository) {
        final DefaultTranslations defaultTranslations = new DefaultTranslations();
//        defaultTranslations.insertTranslations();
        experimentRepository.save(getSentveri_exp3Experiment());
        experimentRepository.save(new DobesAnnotator().getExperiment());
        experimentRepository.save(getAllOptionsExperiment(metadataRepository, presenterFeatureRepository, presenterScreenRepository));
        experimentRepository.save(new JenaFieldKit().getExperiment());
        experimentRepository.save(new TransmissionChain().getExperiment());
        experimentRepository.save(new ShawiFieldKit().getShawiExperiment());
        experimentRepository.save(new Sara01().getExperiment());
        experimentRepository.save(new FactOrFiction().getExperiment());
        experimentRepository.save(defaultTranslations.applyTranslations(new SynQuiz2().getExperiment()));
        experimentRepository.save(new RdExperiment02().getExperiment());
        experimentRepository.save(new NblExperiment01().getExperiment());
        experimentRepository.save(new HRExperiment01().getExperiment());
        experimentRepository.save(new HRPretest().getExperiment());
        experimentRepository.save(new HRPretest02().getExperiment());
        experimentRepository.save(new HROnlinePretest().getExperiment());
        experimentRepository.save(new KinOathExample().getExperiment());
        experimentRepository.save(new RosselFieldKit().getExperiment());
        experimentRepository.save(new WellspringsSamoanFieldKit().getExperiment());
        experimentRepository.save(new SentenceCompletion(new Parcours()).getExperiment());
        experimentRepository.save(new MultiParticipant().getExperiment());
        experimentRepository.save(new ShortMultiparticipant01().getExperiment());
        experimentRepository.save(new ManipulatedContours().getExperiment());
        experimentRepository.save(new FrenchConversation().getExperiment());
        experimentRepository.save(new NonWacq().getExperiment());
        experimentRepository.save(new SentencesRatingTask().getExperiment());
        experimentRepository.save(new GuineaPigProject().getExperiment());
//        experimentRepository.save(new AdVoCas().getExperiment());
//        experimentRepository.save(new SentenceCompletion(new Joost01()).getExperiment());
//        experimentRepository.save(new SentenceCompletion(new Joost02()).getExperiment());
        experimentRepository.save(new PlaybackPreferenceMeasureExperiment().getExperiment());

        for (Experiment experiment : experimentRepository.findAll()) {
            eventRepository.save(new PublishEvents(experiment, new Date(), new Date(), PublishEvents.PublishState.editing, true, false, false, false));
        }
    }

    public Experiment getSentveri_exp3Experiment() {
        Experiment experiment = getDefault("Sentveri_exp3");
        experiment.appendUniqueStimuli(new Sentveri_exp3().createStimuli());
        new Sentveri_exp3().create3c(experiment.getPresenterScreen());
        return experiment;
    }

    public final Experiment getDefault(final String appName) {
        final Experiment experiment = getDefault();
        experiment.setAppNameDisplay(appName);
        experiment.setAppNameInternal(appName);
        final PresenterScreen autoMenuPresenter = addAutoMenu(10);
        experiment.getPresenterScreen().add(autoMenuPresenter);
        final Metadata metadata = new Metadata("workerId", "Reporter name *", ".'{'3,'}'", "Please enter at least three letters.", true, "This test can only be done once per worker.");
        final Metadata metadata1 = new Metadata("errordevice", "Device model", ".'{'2,'}'", "Please enter the device model", false, null);
        final Metadata metadata2 = new Metadata("errordescription", "Please describe the error", ".'{'2,'}'", "Please enter a short description of the issue", false, null);
        experiment.addMetadataOnce(metadata);
        experiment.addMetadataOnce(metadata1);
        experiment.addMetadataOnce(metadata2);
        return experiment;
    }

    public final static Experiment getDefault() {
        final Experiment experiment = new Experiment();
        experiment.setAppNameDisplay("");
        experiment.setAppNameInternal("");
        experiment.setPrimaryColour0("#628D8D");
        experiment.setPrimaryColour1("#385E5E");
        experiment.setPrimaryColour2("#4A7777");
        experiment.setPrimaryColour3("#96ADAD");
        experiment.setPrimaryColour4("#D5D8D8");
        experiment.setComplementColour0("#EAC3A3");
        experiment.setComplementColour1("#9D7B5E");
        experiment.setComplementColour2("#C69E7C");
        experiment.setComplementColour3("#FFEDDE");
        experiment.setComplementColour4("#FFFDFB");
        experiment.setBackgroundColour("#FFFFFF");
        return experiment;
    }

    public Experiment getAllOptionsExperiment(MetadataRepository metadataRepository, PresenterFeatureRepository presenterFeatureRepository, PresenterScreenRepository presenterScreenRepository) {
        final Experiment experiment = new Experiment();
        experiment.setAppNameDisplay("All Options");
        experiment.setAppNameInternal("AllOptions");
        experiment.setPrimaryColour0("#413B52");
        experiment.setPrimaryColour1("#656469");
        experiment.setPrimaryColour2("#514E5C");
        experiment.setPrimaryColour3("#342954");
        experiment.setPrimaryColour4("#271460");
        experiment.setComplementColour0("#777151");
        experiment.setComplementColour1("#999891");
        experiment.setComplementColour2("#85816F");
        experiment.setComplementColour3("#7B6F34");
        experiment.setComplementColour4("#8B770E");
        experiment.setBackgroundColour("#FFFFFF");
        final Metadata metadata = new Metadata("workerId", "Reporter name *", ".'{'3,'}'", "Please enter at least three letters.", true, "This test can only be done once per worker.");
        final Metadata metadata1 = new Metadata("errordevice", "Device model", ".'{'2,'}'", "Please enter the device model", false, null);
        final Metadata metadata2 = new Metadata("errordescription", "Please describe the error", ".'{'2,'}'", "Please enter a short description of the issue", false, null);
        final Metadata metadata3 = new Metadata("emailAddress", "Please enter an email address", ".'{'2,'}'", "Please enter a short description of the issue", false, null);
        experiment.addMetadataOnce(metadata);
        experiment.addMetadataOnce(metadata1);
        experiment.addMetadataOnce(metadata2);
        experiment.addMetadataOnce(metadata3);
        if (metadataRepository != null) {
            metadataRepository.save(experiment.getMetadata());
        }
        addStimuli(experiment);
//        experiment.getPresenterScreen().add(addAnnotationTimelinePanel(presenterFeatureRepository));
//        experiment.getPresenterScreen().add(addVideosMenu(presenterFeatureRepository));
//        experiment.getPresenterScreen().add(addAutoMenu(presenterFeatureRepository));
//        experiment.getPresenterScreen().add(addVideoAspen(presenterFeatureRepository));
//        experiment.getPresenterScreen().add(addVideoWorksPage(presenterFeatureRepository));
//        experiment.getPresenterScreen().add(addVideoFailedPage(presenterFeatureRepository));
        final PresenterScreen autoMenu = addAutoMenu(0);
        final PresenterScreen aboutScreen = addAbout(1);
        if (presenterFeatureRepository != null) {
            presenterFeatureRepository.save(aboutScreen.getPresenterFeatureList());
        }
        if (presenterFeatureRepository != null) {
            presenterFeatureRepository.save(autoMenu.getPresenterFeatureList());
        }
        experiment.getPresenterScreen().add(addTargetScreen(autoMenu, 0));
        experiment.getPresenterScreen().add(autoMenu);
        experiment.getPresenterScreen().add(aboutScreen);
        addAllFeaturesAsPages(presenterFeatureRepository, experiment, autoMenu, 0, true);
        addAllFeaturesAsPages(presenterFeatureRepository, experiment, autoMenu, 0, false);
        if (presenterFeatureRepository != null) {
            presenterScreenRepository.save(experiment.getPresenterScreen());
        }
        return experiment;
    }

    private void addStimuli(final Experiment experiment) {
        final ArrayList<Stimulus> stimuliList = new ArrayList<>();
        final HashSet<String> tagSet = new HashSet<>();
        tagSet.add("number");
        tagSet.add("interesting");
        stimuliList.add(new Stimulus("one", "one.mp3", "one.mp4", "one.jpg", "One", "One", 0, tagSet, null, null));
        tagSet.add("multiple words");
        stimuliList.add(new Stimulus("two", "two.mp3", "two.mp4", "two.jpg", "Two", "Two", 0, tagSet, null, null));
        tagSet.clear();
        tagSet.add("FILLER_AUDIO");
        stimuliList.add(new Stimulus("three", "three.mp3", "three.mp4", "three.jpg", "Three", "Three", 0, tagSet, null, null));
        stimuliList.add(new Stimulus("four", "four.mp3", "four.mp4", "four.jpg", "Four", "Four", 0, tagSet, null, null));
        tagSet.clear();
        tagSet.add("NOISE_AUDIO");
        stimuliList.add(new Stimulus("five", "five.mp3", "five.mp4", "five.jpg", "Five", "Five", 0, tagSet, null, null));
        stimuliList.add(new Stimulus("six", "six.mp3", "six.mp4", "six.jpg", "Six", "Six", 0, tagSet, null, null));
        for (String tag : new String[]{"sim", "mid", "diff", "noise"}) {
            for (String label : new String[]{"rabbit", "cat", "muffin", "you"}) {
                tagSet.clear();
                tagSet.add(tag);
                stimuliList.add(new Stimulus(tag + "_" + label, tag + "_" + label, tag + "_" + label, tag + "_" + label, tag + "_" + label + ".jpg", tag + "_" + label, 0, tagSet, null, null));
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
        stimuliList.add(new Stimulus("bad chars", "bad chars", "bad chars", "bad chars", "bad chars", "bad chars", 0, new HashSet<>(Arrays.asList("bad chars bad_chars bad_chars  ( ) {\n    ( ) {\n         = .(\"[ \\\\t\\\\n\\\\x0B\\\\f\\\\r\\\\(\\\\)\\\\{\\\\};\\\\?\\\\/\\\\\\\\]\", \"_\");\n        this..add();\n    }         = .(\"[ \\\\t\\\\n\\\\x0B\\\\f\\\\r\\\\(\\\\)\\\\{\\\\};\\\\?\\\\/\\\\\\\\]\", \"_\");\n        this..add();\n    }".split(" "))), null, null));
        experiment.appendUniqueStimuli(stimuliList);
    }

    private void addAllFeaturesAsPages(PresenterFeatureRepository presenterFeatureRepository, final Experiment experiment, PresenterScreen backPresenter, long displayOrder, boolean addOptionalAttributes) {
//        int maxScreenAddCount = 5;
        for (PresenterType presenterType : PresenterType.values()) {
            final String presenterName = presenterType.name() + ((addOptionalAttributes) ? "_all_attributes" : "_minimal_attributes");
//            maxScreenAddCount--;
            final PresenterScreen presenterScreen = new PresenterScreen(presenterName, presenterName, backPresenter, presenterName + "Screen", null, presenterType, displayOrder);
            for (FeatureType featureType : presenterType.getFeatureTypes()) {
                if (featureType.getIsChildType() == FeatureType.Contitionals.none) {
                    if (featureType == FeatureType.clearPage) {
                        final PresenterFeature clearScreenButton = new PresenterFeature(FeatureType.actionButton, "Clear Screen");
                        clearScreenButton.getPresenterFeatureList().add(addFeature(experiment, presenterType, featureType, presenterFeatureRepository, addOptionalAttributes));
                        presenterScreen.getPresenterFeatureList().add(clearScreenButton);
                    } else {
                        presenterScreen.getPresenterFeatureList().add(addFeature(experiment, presenterType, featureType, presenterFeatureRepository, addOptionalAttributes));
                    }
                }
            }
            if (presenterFeatureRepository != null) {
                presenterFeatureRepository.save(presenterScreen.getPresenterFeatureList());
            }
            experiment.getPresenterScreen().add(presenterScreen);
//            if (maxScreenAddCount <= 0) {
//                break;
//            }
        }
    }

    private PresenterFeature addFeature(Experiment experiment, PresenterType presenterType, FeatureType featureType, PresenterFeatureRepository presenterFeatureRepository, boolean addOptionalAttributes) {
        final PresenterFeature presenterFeature = new PresenterFeature(featureType, (featureType.canHaveText()) ? featureType.name() : null);
        if (featureType.getFeatureAttributes() != null) {
            for (FeatureAttribute attribute : featureType.getFeatureAttributes()) {
                if (addOptionalAttributes || !attribute.isOptional()) {
                    switch (attribute) {
                        case columnCount:
                        case maxStimuli:
                        case repeatCount:
                        case repeatRandomWindow:
                        case scoreThreshold:
                        case errorThreshold:
                        case correctStreak:
                        case errorStreak:
                        case potentialThreshold:
                        case incrementPhase:
                        case minStimuliPerTag:
                        case maxStimuliPerTag:
                        case minimum:
                            presenterFeature.addFeatureAttributes(attribute, "3");
                            break;
                        case dataChannel:
                        case adjacencyThreshold:
                            presenterFeature.addFeatureAttributes(attribute, "2");
                            break;
                        case maxHeight:
                        case maxWidth:
                        case msToNext:
                        case maximum:
                            presenterFeature.addFeatureAttributes(attribute, "60");
                            break;
                        case hotKey:
                            presenterFeature.addFeatureAttributes(attribute, "A");
                            break;
                        case percentOfPage:
                            presenterFeature.addFeatureAttributes(attribute, "56");
                            break;
                        case eventTier:
                        case threshold:
                        case phasesPerStimulus:
                            presenterFeature.addFeatureAttributes(attribute, "8");
                            break;
                        case fieldName:
                        case linkedFieldName:
                            presenterFeature.addFeatureAttributes(attribute, "workerId");
                            break;
                        case animate:
                            presenterFeature.addFeatureAttributes(attribute, "bounce");
                            break;
                        case randomise:
                        case autoPlay:
                        case loop:
                        case showControls:
                        case scoreValue:
                            presenterFeature.addFeatureAttributes(attribute, "true");
                            break;
                        default:
                            presenterFeature.addFeatureAttributes(attribute, attribute.name());
                    }
                }
            }
        }
        if (featureType.canHaveStimulusTags()) {
            for (String stimulusTag : new String[]{"ประเพณีบุญบั้งไฟ", "Rocket", "Festival", "Lao", "Thai", "ບຸນບັ້ງໄຟ"}) {
                presenterFeature.addStimulusTag(stimulusTag);
            }
        }
        if (featureType.isCanHaveRandomGrouping()) {
            final RandomGrouping randomGrouping = new RandomGrouping();
            for (String randomTag : new String[]{"ประเพณีบุญบั้งไฟ", "Rocket", "Festival", "Lao", "Thai", "ບຸນບັ້ງໄຟ"}) {
                randomGrouping.addRandomTag(randomTag);
            }
            final String metadataFieldname = "groupAllocation_" + featureType.name();
            randomGrouping.setStorageField(metadataFieldname);
            presenterFeature.setRandomGrouping(randomGrouping);
            experiment.addMetadataOnce(new Metadata(metadataFieldname, metadataFieldname, ".*", ".", false, null));
        }
        switch (featureType.getRequiresChildType()) {
            case hasTrueFalseCondition:
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.conditionTrue, presenterFeatureRepository, addOptionalAttributes));
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.conditionFalse, presenterFeatureRepository, addOptionalAttributes));
                if (presenterFeatureRepository != null) {
                    presenterFeatureRepository.save(presenterFeature.getPresenterFeatureList());
                }
                break;
            case hasCorrectIncorrect:
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.responseCorrect, presenterFeatureRepository, addOptionalAttributes));
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.responseIncorrect, presenterFeatureRepository, addOptionalAttributes));
                if (presenterFeatureRepository != null) {
                    presenterFeatureRepository.save(presenterFeature.getPresenterFeatureList());
                }
                break;
//            case hasStimulusTag:
//                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, FeatureType.hasTag, presenterFeatureRepository));
//                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, FeatureType.withoutTag, presenterFeatureRepository));
//                if (presenterFeatureRepository != null) {
//                    presenterFeatureRepository.save(presenterFeature.getPresenterFeatureList());
//                }
//                break;
            case hasMoreStimulus:
                if (featureType == FeatureType.withMatchingStimulus) {
                    presenterFeature.addFeature(FeatureType.hasMoreStimulus, null).addFeature(FeatureType.plainText, "hasMoreStimulus in " + featureType.name());;
                    presenterFeature.addFeature(FeatureType.endOfStimulus, null).addFeature(FeatureType.plainText, "endOfStimulus in " + featureType.name());;
                } else {
                    presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.hasMoreStimulus, presenterFeatureRepository, addOptionalAttributes));
                    presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.endOfStimulus, presenterFeatureRepository, addOptionalAttributes));
                }
                if (presenterFeatureRepository != null) {
                    presenterFeatureRepository.save(presenterFeature.getPresenterFeatureList());
                }
                break;
            case hasErrorSuccess:
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.onError, presenterFeatureRepository, addOptionalAttributes));
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.onSuccess, presenterFeatureRepository, addOptionalAttributes));
                if (presenterFeatureRepository != null) {
                    presenterFeatureRepository.save(presenterFeature.getPresenterFeatureList());
                }
                break;
            case hasUserCount:
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.multipleUsers, presenterFeatureRepository, addOptionalAttributes));
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.singleUser, presenterFeatureRepository, addOptionalAttributes));
                if (presenterFeatureRepository != null) {
                    presenterFeatureRepository.save(presenterFeature.getPresenterFeatureList());
                }
                break;
            case hasThreshold:
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.aboveThreshold, presenterFeatureRepository, addOptionalAttributes));
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.withinThreshold, presenterFeatureRepository, addOptionalAttributes));
                if (presenterFeatureRepository != null) {
                    presenterFeatureRepository.save(presenterFeature.getPresenterFeatureList());
                }
                break;
            case hasMediaLoading:
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.mediaLoaded, presenterFeatureRepository, addOptionalAttributes));
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.mediaLoadFailed, presenterFeatureRepository, addOptionalAttributes));
                if (presenterFeatureRepository != null) {
                    presenterFeatureRepository.save(presenterFeature.getPresenterFeatureList());
                }
                break;
            case hasMediaPlayback:
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.mediaLoaded, presenterFeatureRepository, addOptionalAttributes));
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.mediaLoadFailed, presenterFeatureRepository, addOptionalAttributes));
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.mediaPlaybackComplete, presenterFeatureRepository, addOptionalAttributes));
                if (presenterFeatureRepository != null) {
                    presenterFeatureRepository.save(presenterFeature.getPresenterFeatureList());
                }
                break;
            case none:
                break;
            case groupNetworkActivity:
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.groupNetworkActivity, presenterFeatureRepository, addOptionalAttributes));
                presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, FeatureType.groupNetworkActivity, presenterFeatureRepository, addOptionalAttributes));
                if (presenterFeatureRepository != null) {
                    presenterFeatureRepository.save(presenterFeature.getPresenterFeatureList());
                }
            default:
                System.out.println(featureType.name());
                for (FeatureType featureType1 : presenterType.getFeatureTypes()) {
//                    if (featureType1.getIsChildType() == FeatureType.Contitionals.eachStimulus
//                            || featureType1.getIsChildType() == FeatureType.Contitionals.hasMoreStimulus) {
//                        presenterFeature.addFeature(FeatureType.plainText, featureType1.name() + " in " + featureType.name());
//                    } else 
                    if (featureType1.getIsChildType() == featureType.getRequiresChildType()) {
//                        if (featureType == FeatureType.withMatchingStimulus) {
//                            presenterFeature.addFeature(FeatureType.plainText, featureType1.name() + " in " + featureType.name());
//                        } else {
                        presenterFeature.getPresenterFeatureList().add(addFeature(experiment, presenterType, featureType1, presenterFeatureRepository, addOptionalAttributes));
//                        }
                    }
                }
                if (presenterFeatureRepository != null) {
                    presenterFeatureRepository.save(presenterFeature.getPresenterFeatureList());
                }
                break;
        }
        if (featureType.canHaveFeatures()) {
            presenterFeature.addFeature(FeatureType.plainText, "plainText in " + featureType.name());
//            presenterFeature.getPresenterFeatureList().add(addFeature(experiment, FeatureType.plainText, presenterFeatureRepository, ));
            if (presenterFeatureRepository != null) {
                presenterFeatureRepository.save(presenterFeature.getPresenterFeatureList());
            }
        }
        return presenterFeature;
    }

    private PresenterScreen addVideosMenu(PresenterScreen backPresenter, long displayOrder) {
        final PresenterScreen presenterScreen = new PresenterScreen("Video List", "Videos", backPresenter, "VideosPage", null, PresenterType.text, displayOrder);
        presenterScreen.getPresenterFeatureList().add(new PresenterFeature(FeatureType.htmlText, "This is a simple video codec testing application."));
        presenterScreen.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
//        final PresenterFeature presenterFeature = new PresenterFeature(FeatureType.AudioRecorderPanel, null);
//        presenterFeature.addFeatureAttributes(FeatureType.AudioRecorderPanel.getFeatureAttributes()[0], "some path");
//        presenterScreen.getPresenterFeatureList().add(presenterFeature);

        final PresenterFeature optionButton1 = new PresenterFeature(FeatureType.targetButton, "Video Test Page (aspen)");
        optionButton1.addFeatureAttributes(FeatureAttribute.target, "VideoTestPageAspen");
        presenterScreen.getPresenterFeatureList().add(optionButton1);

        final PresenterFeature optionButton2 = new PresenterFeature(FeatureType.targetButton, "Annotation Timeline Screen");
        optionButton2.addFeatureAttributes(FeatureAttribute.target, "AnnotationTimelinePanel");
        presenterScreen.getPresenterFeatureList().add(optionButton2);

        presenterScreen.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
        presenterScreen.getPresenterFeatureList().add(new PresenterFeature(FeatureType.versionData, null));

//        presenterFeatureRepository.save(presenterScreen.getPresenterFeatureList());
        return presenterScreen;
    }

    private PresenterScreen addAutoMenu(long displayOrder) {
        final PresenterScreen presenterScreen = new PresenterScreen("Auto Menu", "Menu", null, "AutoMenu", null, PresenterType.menu, displayOrder);
        presenterScreen.getPresenterFeatureList().add(new PresenterFeature(FeatureType.allMenuItems, null));
        return presenterScreen;
    }

    private PresenterScreen addAbout(long displayOrder) {
        final PresenterScreen presenterScreen = new PresenterScreen("about", "about", null, "about", null, PresenterType.debug, displayOrder);
        return presenterScreen;
    }

    private PresenterScreen addTargetScreen(PresenterScreen backPresenter, long displayOrder) {
        final PresenterScreen presenterScreen = new PresenterScreen("Target Screen", "Target", backPresenter, "target", null, PresenterType.text, displayOrder);
        presenterScreen.getPresenterFeatureList().add(new PresenterFeature(FeatureType.plainText, "A simple page so that there is a screen with the target value of 'target' for testing purposes."));

//        presenterFeatureRepository.save(presenterScreen.getPresenterFeatureList());
        return presenterScreen;
    }

}
