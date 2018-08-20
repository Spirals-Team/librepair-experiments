/*
 *
 * Copyright 2018 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package uk.ac.ebi.eva.accession.core.configuration;

public class ApplicationProperties {

    private String instanceId;

    private VariantAccessioningProperties variant;

    public String getInstanceId() {
        return instanceId;
    }

    public VariantAccessioningProperties getVariant() {
        return variant;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public void setVariant(VariantAccessioningProperties variant) {
        this.variant = variant;
    }

    @Override
    public String toString() {
        return "ApplicationProperties{" +
                "instanceId='" + instanceId + '\'' +
                ", variant=" + variant +
                '}';
    }
}
