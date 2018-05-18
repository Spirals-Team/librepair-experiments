/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import nl.mpi.tg.eg.experimentdesigner.controller.WizardController;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardUtilData;

/**
 * @since April 5, 2018 16:10 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class JsonToXml {

    /**
     * @param args, input directory path, output directory path
     */
    public static void main(String[] args) {
        System.out.println("JsonToXml");
        if (args.length < 2) {
            System.out.println("both inputDirectory and outputDirectory must be specified");
        } else {
            final String inputDirectory = args[0];
            final String outputDirectory = args[1];
            System.out.println("inputDirectory: " + inputDirectory);
            System.out.println("outputDirectory: " + outputDirectory);
            if (!new File(inputDirectory).exists()) {
                System.out.println("inputDirectory does not exist");
            } else if (!new File(outputDirectory).exists()) {
                System.out.println("outputDirectory does not exist");
            } else {
                try {
                    final File schemaOutputFile = new File(outputDirectory, "frinex.xsd");
                    new SchemaGenerator().createSchemaFile(schemaOutputFile);
                } catch (IOException exception) {
                    System.out.println("Failed to create schema file: " + exception.getMessage());
                }
                final WizardController wizardController = new WizardController();
                for (File jsonFile : new File(inputDirectory).listFiles((File dir, String name) -> name.endsWith(".json"))) {
                    System.out.println(jsonFile);
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
                        WizardUtilData wizardData = mapper.readValue(jsonFile, WizardUtilData.class);
                        final Experiment experiment = wizardController.getExperiment(new SentenceCompletion(wizardData).getWizardData());
                        System.out.println("experiment: " + experiment.getAppNameInternal());
                        final File outputFile = new File(outputDirectory, experiment.getAppNameInternal() + ".xml");
                        System.out.println(outputFile);
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
                        FileWriter fileWriter = new FileWriter(outputFile);
                        jaxbMarshaller.marshal(experiment, fileWriter);
                    } catch (IOException | JAXBException exception) {
                        System.out.println(exception.getMessage());
                    }
                }
            }
        }
    }
}
