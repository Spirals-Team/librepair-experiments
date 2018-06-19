/*
 * Copyright 2017 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.eva.bd2k;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.ddi.xml.validator.parser.marshaller.OmicsDataMarshaller;

import uk.ac.ebi.eva.bd2k.client.ProjectClient;
import uk.ac.ebi.eva.bd2k.client.ProjectEnaWSClient;
import uk.ac.ebi.eva.bd2k.client.StudyEvaWSClient;
import uk.ac.ebi.eva.bd2k.conf.ExporterConfigurationProperties;
import uk.ac.ebi.eva.bd2k.export.EvaStudyExporter;
import uk.ac.ebi.eva.bd2k.export.EvaStudyTransformer;
import uk.ac.ebi.eva.bd2k.export.StudyExporter;
import uk.ac.ebi.eva.bd2k.model.VariantStudy;

import java.io.File;
import java.nio.file.Paths;

@Component
public class StudyExporterCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StudyExporterCommandLineRunner.class);

    @Autowired
    private ExporterConfigurationProperties exporterConfiguration;

    @Override
    public void run(String... strings) throws Exception {
        try {
            cleanupOutputDirectory(exporterConfiguration.getOutputDirectory());
            ProjectClient projectClient = new ProjectEnaWSClient(exporterConfiguration.getEnaProjectApiUrl(),
                                                                 new RestTemplate());
            StudyExporter<VariantStudy> exporter = new EvaStudyExporter(
                    new EvaStudyTransformer(projectClient, exporterConfiguration.getEvaStudyWebUrl()),
                    new OmicsDataMarshaller());
            StudyEvaWSClient studyEvaWSClient = new StudyEvaWSClient(exporterConfiguration.getEvaStudiesApiUrl(),
                                                                     new RestTemplate());
            exporter.export(studyEvaWSClient.getAllStudies(), Paths.get(exporterConfiguration.getOutputDirectory()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.debug("", e);
            System.exit(1);
        }
    }

    /**
     * In order to avoid keeping files from deprecated studies, the XML files in the output directory are deleted. Any
     * other files or directories are not affected by this operation.
     *
     * @param outputDirectory Directory where the output files are stored
     */
    private void cleanupOutputDirectory(String outputDirectory) {
        for (File f : Paths.get(outputDirectory).toFile().listFiles((f, name) -> name.matches("^PRJ.*.xml$"))) {
            f.delete();
            logger.info("File {} deleted", f.getName());
        }
    }
}
