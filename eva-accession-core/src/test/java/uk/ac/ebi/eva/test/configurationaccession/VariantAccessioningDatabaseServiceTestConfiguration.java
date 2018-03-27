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
package uk.ac.ebi.eva.test.configurationaccession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import uk.ac.ebi.eva.accession.core.SubmittedVariantAccessioningService;
import uk.ac.ebi.eva.accession.core.SubmittedVariantModel;
import uk.ac.ebi.eva.accession.core.persistence.SubmittedVariantAccessioningDatabaseService;
import uk.ac.ebi.eva.accession.core.persistence.SubmittedVariantAccessioningRepository;
import uk.ac.ebi.ampt2d.commons.accession.generators.monotonic.MonotonicAccessionGenerator;
import uk.ac.ebi.ampt2d.commons.accession.persistence.monotonic.service.ContiguousIdBlockService;

@TestConfiguration
public class VariantAccessioningDatabaseServiceTestConfiguration {

    @Autowired
    private SubmittedVariantAccessioningRepository repository;

    @Autowired
    private ContiguousIdBlockService service;

    @Bean
    public SubmittedVariantAccessioningService variantAccessionService() {
        return new SubmittedVariantAccessioningService(variantAccessionGenerator(), variantAccessioningDatabaseService());
    }

    @Bean
    public SubmittedVariantAccessioningDatabaseService variantAccessioningDatabaseService() {
        return new SubmittedVariantAccessioningDatabaseService(repository);
    }

    @Bean
    public MonotonicAccessionGenerator<SubmittedVariantModel> variantAccessionGenerator() {
        return new MonotonicAccessionGenerator<>(1000L, "var-test", "test-inst", service);
    }

}
