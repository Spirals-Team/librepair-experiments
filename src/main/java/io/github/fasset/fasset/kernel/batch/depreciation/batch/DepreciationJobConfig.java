/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.fasset.fasset.kernel.batch.depreciation.batch;

import io.github.fasset.fasset.kernel.batch.depreciation.DepreciationJobListener;
import io.github.fasset.fasset.kernel.batch.depreciation.DepreciationProceeds;
import io.github.fasset.fasset.kernel.util.ProcessingList;
import io.github.fasset.fasset.kernel.util.ProcessingListImpl;
import io.github.fasset.fasset.model.FixedAsset;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Depreciation batch process
 */
@Configuration
public class DepreciationJobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final ItemReader<FixedAsset> fixedAssetItemReader;

    @Autowired
    public DepreciationJobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ItemReader<FixedAsset> fixedAssetItemReader) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.fixedAssetItemReader = fixedAssetItemReader;
    }

    @Bean("depreciationJob")
    public Job depreciationJob(DepreciationJobListener depreciationJobListener) {
        return jobBuilderFactory.get("depreciationJob").preventRestart().incrementer(new RunIdIncrementer()).listener(depreciationJobListener).flow(depreciationStep1()).end().build();
    }

    @Bean
    public DepreciationProcessor depreciationProcessor() {

        return new DepreciationProcessor(new ProcessingListImpl<>());
    }

    @Bean
    public DepreciationWriter depreciationWriter() {

        return new DepreciationWriter();
    }

    @Bean
    public Step depreciationStep1() {
        return stepBuilderFactory.get("depreciationStep1").<FixedAsset, ProcessingList<DepreciationProceeds>>chunk(100).reader(fixedAssetItemReader).processor(depreciationProcessor())
            .writer(depreciationWriter()).build();
    }


}
