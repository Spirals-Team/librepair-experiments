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
package io.github.fasset.fasset.kernel.batch.depreciation.report.asset;

import io.github.fasset.fasset.kernel.batch.depreciation.report.sol.MonthlySolDepreciationWriter;
import io.github.fasset.fasset.model.FixedAsset;
import io.github.fasset.fasset.model.depreciation.MonthlyAssetDepreciation;
import io.github.fasset.fasset.service.MonthlyAssetDepreciationService;
import io.github.fasset.fasset.service.MonthlySolDepreciationService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * Configuration for the MonthlyAssetDepreciationJob
 */
@Configuration
public class MonthlyAssetDepreciationJobConfiguration {

    /*@Value("#{jobParameters['year']}")
    private static final String year = null;*/

    // Trust me I know. I find the existence of this thing very creepy
    @Value("#{jobParameters['year']}")
    private static String year;

    private final JobBuilderFactory jobBuilderFactory;

    private final MonthlyAssetDepreciationService monthlyAssetDepreciationService;

    private final StepBuilderFactory stepBuilderFactory;

    private final ItemReader<FixedAsset> fixedAssetItemReader;

    private final MonthlyAssetDepreciationExecutor monthlyAssetDepreciationExecutor;

    private final MonthlySolDepreciationService monthlySolDepreciationService;

    //private final MonthlySolDepreciationExecutor monthlySolDepreciationExecutor;

    @Autowired
    public MonthlyAssetDepreciationJobConfiguration(JobBuilderFactory jobBuilderFactory, @Qualifier("monthlyAssetDepreciationService") MonthlyAssetDepreciationService monthlyAssetDepreciationService,
                                                    StepBuilderFactory stepBuilderFactory, ItemReader<FixedAsset> fixedAssetItemReader,
                                                    @Qualifier("monthlyAssetDepreciationExecutor") MonthlyAssetDepreciationExecutor monthlyAssetDepreciationExecutor,
                                                    MonthlySolDepreciationService monthlySolDepreciationService /*,
                                                    MonthlySolDepreciationExecutor monthlySolDepreciationExecutor*/) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.monthlyAssetDepreciationService = monthlyAssetDepreciationService;
        this.stepBuilderFactory = stepBuilderFactory;
        this.fixedAssetItemReader = fixedAssetItemReader;
        this.monthlyAssetDepreciationExecutor = monthlyAssetDepreciationExecutor;
        this.monthlySolDepreciationService = monthlySolDepreciationService;
        //this.monthlySolDepreciationExecutor = monthlySolDepreciationExecutor;
    }


    @Bean("monthlyAssetDepreciationJobListener")
    public MonthlyAssetDepreciationJobListener monthlyAssetDepreciationJobListener() {

        return new MonthlyAssetDepreciationJobListener();
    }

    /*@Bean("monthlyAssetDepreciationJob")
    @DependsOn("monthlyAssetDepreciationJobListener")
    public Job monthlyAssetDepreciationJob() {
        return jobBuilderFactory.get("monthlyAssetDepreciationJob")
                .incrementer(new RunIdIncrementer())
                .listener(monthlyAssetDepreciationJobListener())
                .start(updateMonthlyAssetDepreciation())
                .next(createMonthlySolDepreciationItems())
                .next(createMonthlyCategoryDepreciationItems())
                .build();
    }*/

    @Bean("monthlyAssetDepreciationJob")
    @DependsOn("monthlyAssetDepreciationJobListener")
    public Job monthlyAssetDepreciationJob() {
        return jobBuilderFactory.get("monthlyAssetDepreciationJob").preventRestart().incrementer(new RunIdIncrementer()).listener(monthlyAssetDepreciationJobListener()).preventRestart()
            .flow(updateMonthlyAssetDepreciation()).end().build();
    }

    @Bean
    @JobScope
    public MonthlyAssetDepreciationProcessor monthlyAssetDepreciationProcessor(@Value("#{jobParameters['year']}") String year) {

        return new MonthlyAssetDepreciationProcessor(monthlyAssetDepreciationExecutor, year);
    }

    @Bean
    public MonthlyAssetDepreciationWriter monthlyAssetDepreciationWriter() {

        return new MonthlyAssetDepreciationWriter(monthlyAssetDepreciationService);
    }

    @Bean
    public Step updateMonthlyAssetDepreciation() {
        Step updateMonthlyAssetDepreciation = null;
        try {
            updateMonthlyAssetDepreciation = stepBuilderFactory.get("updateMonthlyAssetDepreciation").<FixedAsset, MonthlyAssetDepreciation>chunk(100).reader(fixedAssetItemReader)
                .processor(monthlyAssetDepreciationProcessor(year)).writer(monthlyAssetDepreciationWriter()).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return updateMonthlyAssetDepreciation;
    }

    @Bean
    public MonthlySolDepreciationWriter monthlySolDepreciationWriter() {

        return new MonthlySolDepreciationWriter(monthlySolDepreciationService);
    }

    /*@Bean
    @JobScope
    public MonthlySolDepreciationProcessor monthlySolDepreciationProcessor(@Value("#{jobParameters['year']}") String year) {

        return new MonthlySolDepreciationProcessor(monthlySolDepreciationExecutor, year);
    }*/


    /*@Bean("createMonthlySolDepreciationItems")
    public Step createMonthlySolDepreciationItems() {

        Step createMonthlySolDepreciationItems = null;

        try {
            createMonthlySolDepreciationItems = stepBuilderFactory
                    .get("createMonthlySolDepreciationItems")
                    .<String,MonthlySolDepreciation>chunk(5)
                    .reader(monthlySolDepreciationReader)
                    .writer(monthlySolDepreciationWriter())
                    .processor(monthlySolDepreciationProcessor(year))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return createMonthlySolDepreciationItems;
    }*/

    /*@Bean
    public Step updateMonthlyAssetDepreciation() {
        Step updateMonthlyAssetDepreciation = null;
        try {
            updateMonthlyAssetDepreciation =  stepBuilderFactory.getCategoryConfiguration("updateMonthlyAssetDepreciation")
                    .<Depreciation, MonthlyAssetDepreciation> chunk(100)
                    .reader(depreciationItemReader())
                    .processor(monthlyAssetDepreciationProcessor())
                    .writer(monthlyAssetDepreciationWriter())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return updateMonthlyAssetDepreciation;
    }*/
}
