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
package io.github.fasset.fasset.kernel.batch.depreciation.report.category;

import io.github.fasset.fasset.model.depreciation.MonthlyCategoryDepreciation;
import io.github.fasset.fasset.service.MonthlyCategoryDepreciationService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

/**
 * Configuration for MonthlyCategoryDepreciation job
 */
@Configuration
public class MonthlyCategoryDepreciationJobConfiguration {

    // Still find the existence of this thing quite creepy.
    @Value("#{jobParameters['year']}")
    private static String year;

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final MonthlyCategoryDepreciationService monthlyCategoryDepreciationService;

    private final MonthyCategoryDepreciationJobListener monthyCategoryDepreciationJobListener;

    private final EntityManagerFactory entityManagerFactory;

    private final MonthlyCategoryDepreciationExecutor monthlyCategoryDepreciationExecutor;


    @Autowired
    public MonthlyCategoryDepreciationJobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                                                       MonthlyCategoryDepreciationService monthlyCategoryDepreciationService,
                                                       MonthyCategoryDepreciationJobListener monthyCategoryDepreciationJobListener, EntityManagerFactory entityManagerFactory,
                                                       MonthlyCategoryDepreciationExecutor monthlyCategoryDepreciationExecutor) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.monthlyCategoryDepreciationService = monthlyCategoryDepreciationService;
        this.monthyCategoryDepreciationJobListener = monthyCategoryDepreciationJobListener;
        this.entityManagerFactory = entityManagerFactory;
        this.monthlyCategoryDepreciationExecutor = monthlyCategoryDepreciationExecutor;
    }

    @Bean("monthlyCategoryDepreciationJob")
    public Job monthlyCategoryDepreciationJob() {
        return jobBuilderFactory.get("monthlyCategoryDepreciationJob").preventRestart().incrementer(new RunIdIncrementer()).listener(monthyCategoryDepreciationJobListener).preventRestart()
            .flow(createMonthlyCategoryDepreciationItems()).end().build();
    }

    @Bean
    public Step createMonthlyCategoryDepreciationItems() {

        Step createMonthlyCategoryDepreciationItems = null;

        try {
            createMonthlyCategoryDepreciationItems =
                stepBuilderFactory.get("createMonthlyCategoryDepreciationItems").<String, MonthlyCategoryDepreciation>chunk(5).reader(monthlyCategoryDepreciationReader())
                    .writer(monthlyCategoryDepreciationWriter()).processor(monthlyCategoryDepreciationProcessor(year)).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return createMonthlyCategoryDepreciationItems;
    }

    @Bean
    @JobScope
    public ItemProcessor<String, MonthlyCategoryDepreciation> monthlyCategoryDepreciationProcessor(@Value("#{jobParameters['year']}") String year) {

        return new MonthlyCategoryDepreciationProcessor(monthlyCategoryDepreciationExecutor, year);
    }

    @Bean
    public ItemWriter<? super MonthlyCategoryDepreciation> monthlyCategoryDepreciationWriter() {
        return new MonthlyCategoryDepreciationWriter(monthlyCategoryDepreciationService);
    }

    @Bean
    public ItemReader<? extends String> monthlyCategoryDepreciationReader() {

        JpaPagingItemReader<String> categoryNameReader = new JpaPagingItemReader<>();

        categoryNameReader.setEntityManagerFactory(entityManagerFactory);

        categoryNameReader.setQueryString("SELECT DISTINCT e.category From Depreciation e");

        categoryNameReader.setTransacted(true);
        categoryNameReader.setPageSize(5);
        categoryNameReader.setSaveState(true);
        try {
            categoryNameReader.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoryNameReader;
    }

}
