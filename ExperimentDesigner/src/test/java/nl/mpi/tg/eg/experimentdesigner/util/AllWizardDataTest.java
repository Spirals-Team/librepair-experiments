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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import nl.mpi.tg.eg.experimentdesigner.controller.WizardController;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardUtilData;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 * @since Aug 2, 2016 2:24:59 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class AllWizardDataTest {

    private final WizardController wizardController = new WizardController();

    public AllWizardDataTest() {
    }

    public void testGetWizardData(Experiment experiment) throws IOException, JAXBException, URISyntaxException {
        System.out.println("getWizardData: " + experiment.getAppNameInternal());
        final String name = "/frinex-rest-output/" + experiment.getAppNameInternal() + ".xml";
        System.out.println(name);
        URI testXmlUri = this.getClass().getResource(name).toURI();
        String expResult = new String(Files.readAllBytes(Paths.get(testXmlUri)), StandardCharsets.UTF_8);
        experiment.getPresenterScreen().sort(new Comparator<PresenterScreen>() {
            // because the experiment has not been stored and retrieved from the DB we need to sort this manually
            @Override
            public int compare(PresenterScreen o1, PresenterScreen o2) {
                return Long.compare(o1.getDisplayOrder(), o2.getDisplayOrder());
            }
        });
        JAXBContext jaxbContext = JAXBContext.newInstance(Experiment.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        jaxbMarshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "http://frinexbuild.mpi.nl/frinex.xsd");
        StringWriter stringWriter = new StringWriter();
        final String testOutputName = experiment.getAppNameInternal() + "-testoutput.xml";
//        jaxbMarshaller.marshal(result, System.out);
        FileWriter fileWriter = new FileWriter(new File(new File(testXmlUri).getParentFile(), testOutputName));
        jaxbMarshaller.marshal(experiment, fileWriter);
        jaxbMarshaller.marshal(experiment, stringWriter);
        assertEquals(testOutputName, expResult, stringWriter.toString());
    }

    private void testDeserialiseWizardData(final File serialisedFile) throws IOException, JAXBException, URISyntaxException {
        JAXBContext jaxbContext = JAXBContext.newInstance(WizardData.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        WizardData wizardData = (WizardData) jaxbUnmarshaller.unmarshal(serialisedFile);
        testGetWizardData(wizardController.getExperiment(wizardData));
    }

    public void testSerialiseWizardData(WizardData wizardData) throws IOException, JAXBException, URISyntaxException {
        System.out.println("testSerialiseWizardData: " + wizardData.getAppName());
        final String outputDirectory = "/frinex-rest-output/";
        URI outputDirectoryUri = this.getClass().getResource(outputDirectory).toURI();
        System.out.println(outputDirectory);
        JAXBContext jaxbContext = JAXBContext.newInstance(WizardData.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        jaxbMarshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "http://frinexbuild.mpi.nl/frinex.xsd");
//        StringWriter stringWriter = new StringWriter();
//        jaxbMarshaller.marshal(wizardData, stringWriter);
        FileWriter fileWriter = new FileWriter(new File(new File(outputDirectoryUri), wizardData.getAppName().replaceAll("[^A-Za-z0-9]", "_").toLowerCase() + "-wizarddata.xml"));
        jaxbMarshaller.marshal(wizardData, fileWriter);
//        System.out.println(stringWriter);

        // todo: when wizard serialisation is ready, include the following test
        testDeserialiseWizardData(new File(new File(outputDirectoryUri), wizardData.getAppName().replaceAll("[^A-Za-z0-9]", "_").toLowerCase() + "-wizarddata.xml"));
    }

    private void testDeserialiseWizardUtil(final File serialisedFile) throws IOException, JAXBException, URISyntaxException {
        System.out.println(serialisedFile);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        WizardUtilData wizardData = mapper.readValue(serialisedFile, WizardUtilData.class);
        testGetWizardData(wizardController.getExperiment(new SentenceCompletion(wizardData).getWizardData()));
    }

    public void testSerialiseWizardUtil(WizardUtilData wizardUtil) throws IOException, JAXBException, URISyntaxException {
        System.out.println("testSerialiseWizardUtil: " + wizardUtil.getExperimentTitle());
        final String outputDirectory = "/frinex-rest-output/";
        URI outputDirectoryUri = this.getClass().getResource(outputDirectory).toURI();
        System.out.println(outputDirectory);

        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(new File(outputDirectoryUri), wizardUtil.getExperimentTitle().replaceAll("[^A-Za-z0-9]", "_").toLowerCase() + "-wizardutildata.json"), wizardUtil);
        testDeserialiseWizardUtil(new File(new File(outputDirectoryUri), wizardUtil.getExperimentTitle().replaceAll("[^A-Za-z0-9]", "_").toLowerCase() + "-wizardutildata.json"));
    }

    /**
     * Test of getWizardData method, of multiple wizard classes.
     *
     * @throws java.io.IOException
     * @throws javax.xml.bind.JAXBException
     * @throws java.net.URISyntaxException
     */
    @Test
    public void testAllGetWizardData() throws IOException, JAXBException, URISyntaxException {
        System.out.println("testAllGetWizardData");
        final DefaultTranslations defaultTranslations = new DefaultTranslations();
//        defaultTranslations.insertTranslations();
//        testGetWizardData(new DefaultExperiments().getAllOptionsExperiment(null, null, null));
//        testGetWizardData(new DobesAnnotator().getExperiment());
        testGetWizardData(new JenaFieldKit().getExperiment());
//        testGetWizardData(new TransmissionChain().getExperiment());
        testGetWizardData(new ShawiFieldKit().getShawiExperiment());
        testGetWizardData(new Sara01().getExperiment());
        testGetWizardData(new FactOrFiction().getExperiment());
//        testGetWizardData(defaultTranslations.applyTranslations(new SynQuiz2().getExperiment()));
        testGetWizardData(new RdExperiment02().getExperiment());
        testGetWizardData(new NblExperiment01().getExperiment());
        testGetWizardData(new HRExperiment01().getExperiment());
        testGetWizardData(new HRPretest().getExperiment());
        testGetWizardData(new HRPretest02().getExperiment());
        testGetWizardData(new HROnlinePretest().getExperiment());
        testGetWizardData(new KinOathExample().getExperiment());
//        testGetWizardData(new RosselFieldKit().getExperiment());
        testGetWizardData(new SentenceCompletion(new Parcours()).getExperiment());
        testGetWizardData(new MultiParticipant().getExperiment());
        testGetWizardData(new ShortMultiparticipant01().getExperiment());
        testGetWizardData(new ManipulatedContours().getExperiment());
        testGetWizardData(new FrenchConversation().getExperiment());
        testGetWizardData(new NonWacq().getExperiment());
        testGetWizardData(new SentencesRatingTask().getExperiment());
        testGetWizardData(new WellspringsSamoanFieldKit().getExperiment());
        testGetWizardData(new GuineaPigProject().getExperiment());
//        testGetWizardData(new PlayhouseStudy().getExperiment());
//        testGetWizardData(new SentenceCompletion(new Joost01()).getExperiment());
//        testGetWizardData(new SentenceCompletion(new Joost02()).getExperiment());
//        testGetWizardData(new PlaybackPreferenceMeasureExperiment().getExperiment());
    }

    /**
     * Test of testAllSerialiseWizardData method, of multiple wizard classes.
     *
     * @throws java.io.IOException
     * @throws javax.xml.bind.JAXBException
     * @throws java.net.URISyntaxException
     */
    @Test
    public void testAllSerialiseWizardData() throws IOException, JAXBException, URISyntaxException {
        System.out.println("testAllSerialiseWizardData");
//        final DefaultTranslations defaultTranslations = new DefaultTranslations();
//        defaultTranslations.insertTranslations();
//        testSerialiseWizardData(new DefaultExperiments().getAllOptionsExperiment(null, null, null));
//        testSerialiseWizardData(new DobesAnnotator().getWizardData());
//        testSerialiseWizardData(new JenaFieldKit().getWizardData());
//        testSerialiseWizardData(new TransmissionChain().getWizardData());
//        testSerialiseWizardData(new ShawiFieldKit().getWizardData());
//        testSerialiseWizardData(new Sara01().getWizardData());
//        testSerialiseWizardData(new FactOrFiction().getWizardData());
////        testSerialiseWizardData(defaultTranslations.applyTranslations(new SynQuiz2().getWizardData()));
//        testSerialiseWizardData(new RdExperiment02().getWizardData());
//        testSerialiseWizardData(new NblExperiment01().getWizardData());
//        testSerialiseWizardData(new HRExperiment01().getWizardData());
//        testSerialiseWizardData(new HRPretest().getWizardData());
//        testSerialiseWizardData(new HRPretest02().getWizardData());
//        testSerialiseWizardData(new HROnlinePretest().getWizardData());
//        testSerialiseWizardData(new KinOathExample().getWizardData());
//        testSerialiseWizardData(new RosselFieldKit().getWizardData());
//        testSerialiseWizardData(new Parcours().getWizardData());
//        testSerialiseWizardData(new MultiParticipant().getWizardData());
//        testSerialiseWizardData(new ShortMultiparticipant01().getWizardData());
//        testSerialiseWizardData(new ManipulatedContours().getWizardData());
//        testSerialiseWizardData(new FrenchConversation().getWizardData());
//        testSerialiseWizardData(new NonWacq().getWizardData());
//        testSerialiseWizardData(new SentencesRatingTask().getWizardData());
//        testSerialiseWizardData(new WellspringsSamoanFieldKit().getWizardData());
//        testSerialiseWizardData(new GuineaPigProject().getWizardData());
//        testSerialiseWizardData(new PlayhouseStudy().getWizardData());
//        testSerialiseWizardData(new Joost01().getWizardData());
//        testSerialiseWizardData(new Joost02().getWizardData());
//        testSerialiseWizardData(new PlaybackPreferenceMeasureExperiment().getWizardData());
    }

    /**
     * Test of testAllSerialiseWizardUtil method, of multiple wizard classes.
     *
     * @throws java.io.IOException
     * @throws javax.xml.bind.JAXBException
     * @throws java.net.URISyntaxException
     */
    @Test
    public void testAllSerialiseWizardUtil() throws IOException, JAXBException, URISyntaxException {
        System.out.println("testAllSerialiseWizardUtil");
//        testSerialiseWizardUtil(new Joost01());
//        testSerialiseWizardUtil(new Joost02());
        testSerialiseWizardUtil(new Parcours());
    }

    /**
     * Test of JsonOnly wizard experiments
     *
     * @throws java.io.IOException
     * @throws javax.xml.bind.JAXBException
     * @throws java.net.URISyntaxException
     */
    @Ignore
    @Test
    public void testJsonOnlyWizardUtil() throws IOException, JAXBException, URISyntaxException {
        System.out.println("testJsonOnlyWizardUtil");
        final String inputDirectory = "/frinex-rest-output/";
        URI outputDirectoryUri = this.getClass().getResource(inputDirectory).toURI();
        System.out.println(inputDirectory);
//        testDeserialiseWizardUtil(new File(new File(outputDirectoryUri), "heoexp01.json"));
//        testDeserialiseWizardUtil(new File(new File(outputDirectoryUri), "parcours01.json"));
//        testDeserialiseWizardUtil(new File(new File(outputDirectoryUri), "joseco01.json"));
//        testDeserialiseWizardUtil(new File(new File(outputDirectoryUri), "joseco02.json"));
//        testDeserialiseWizardUtil(new File(new File(outputDirectoryUri), "generic_example.json"));
//        testDeserialiseWizardUtil(new File(new File(outputDirectoryUri), "playhouse_study.json"));
//        testDeserialiseWizardUtil(new File(new File(outputDirectoryUri), "ppvt.json"));
    }
}
