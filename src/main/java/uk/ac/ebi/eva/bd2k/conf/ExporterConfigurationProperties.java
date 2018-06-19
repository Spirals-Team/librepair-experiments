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
package uk.ac.ebi.eva.bd2k.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "omicsdi")
public class ExporterConfigurationProperties {

    private String evaStudiesApiUrl;

    private String evaStudyWebUrl;

    private String enaProjectApiUrl;

    private String outputDirectory;

    public String getEvaStudiesApiUrl() {
        return evaStudiesApiUrl;
    }

    public void setEvaStudiesApiUrl(String evaStudiesApiUrl) {
        this.evaStudiesApiUrl = evaStudiesApiUrl;
    }

    public String getEvaStudyWebUrl() {
        return evaStudyWebUrl;
    }

    public void setEvaStudyWebUrl(String evaStudyWebUrl) {
        this.evaStudyWebUrl = evaStudyWebUrl;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String getEnaProjectApiUrl() {
        return enaProjectApiUrl;
    }

    public void setEnaProjectApiUrl(String enaProjectApiUrl) {
        this.enaProjectApiUrl = enaProjectApiUrl;
    }
}
