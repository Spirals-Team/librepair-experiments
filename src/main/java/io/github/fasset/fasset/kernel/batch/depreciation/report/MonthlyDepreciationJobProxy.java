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
package io.github.fasset.fasset.kernel.batch.depreciation.report;

import com.google.common.collect.ImmutableSet;
import io.github.fasset.fasset.kernel.batch.FixedAssetsJobsActivator;
import io.github.fasset.fasset.kernel.batch.depreciation.batch.DepreciationRelay;
import io.github.fasset.fasset.service.FixedAssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Used to launch the monthly depreciation job
 */
@Component("monthlyDepreciationJobProxy")
public class MonthlyDepreciationJobProxy {

    private static final Logger log = LoggerFactory.getLogger(MonthlyDepreciationJobProxy.class);

    private final JobLauncher jobLauncher;

    private final Job monthlyAssetDepreciationJob;
    private final Job monthlySolDepreciationJob;
    private final Job monthlyCategoryDepreciationJob;

    private final FixedAssetService fixedAssetService;
    private final FixedAssetsJobsActivator fixedAssetsJobsActivator;
    private final DepreciationRelay depreciationRelay;


    @Autowired
    public MonthlyDepreciationJobProxy(JobLauncher jobLauncher, @Qualifier("monthlyAssetDepreciationJob") Job monthlyAssetDepreciationJob,
                                       @Qualifier("monthlyCategoryDepreciationJob") Job monthlyCategoryDepreciationJob, @Qualifier("monthlySolDepreciationJob") Job monthlySolDepreciationJob,
                                       @Qualifier("fixedAssetService") FixedAssetService fixedAssetService, FixedAssetsJobsActivator fixedAssetsJobsActivator, DepreciationRelay depreciationRelay) {
        this.jobLauncher = jobLauncher;
        this.monthlyAssetDepreciationJob = monthlyAssetDepreciationJob;
        this.fixedAssetService = fixedAssetService;
        this.fixedAssetsJobsActivator = fixedAssetsJobsActivator;
        this.depreciationRelay = depreciationRelay;
        this.monthlyCategoryDepreciationJob = monthlyCategoryDepreciationJob;
        this.monthlySolDepreciationJob = monthlySolDepreciationJob;
    }

    private List<Integer> annualRelay() {

        List<Integer> annualList = depreciationRelay.getMonthlyDepreciationSequence().parallelStream().map(YearMonth::getYear).distinct().sorted().collect(Collectors.toList());

        //Just to ensure only unique items are returned
        return ImmutableSet.copyOf(annualList).asList();
    }

    public void initializeMonthlyDepreciationReporting() {

        int numberOfAssets = fixedAssetService.getPoll();
        LocalDateTime startingTime = LocalDateTime.now();

        log.info("Depreciation has begun with {} items at time: {}", numberOfAssets, startingTime);

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder().addString("no_of_assets", String.valueOf(numberOfAssets)).addString("starting_time", startingTime.toString());

        log.info("executing MonthlyAssetDepreciation job : {}", monthlyAssetDepreciationJob);
        executeMonthlyJob(jobParametersBuilder, monthlyAssetDepreciationJob);

        log.info("executing MonthlySolDepreciation job : {}", monthlySolDepreciationJob);
        executeMonthlyJob(jobParametersBuilder, monthlySolDepreciationJob);

        log.info("executing MonthlyCategoryDepreciation job : {}", monthlyCategoryDepreciationJob);
        executeMonthlyJob(jobParametersBuilder, monthlyCategoryDepreciationJob);

    }

    private void executeMonthlyJob(JobParametersBuilder jobParametersBuilder, Job job) {
        annualRelay().forEach(year -> {

            log.info("Running {} job for the year : {}", job, year);

            JobParametersBuilder parametersBuilder = jobParametersBuilder.addString("year", year.toString());

            fixedAssetsJobsActivator.bootstrap(parametersBuilder.addLong("year", Long.valueOf(year)).toJobParameters(), jobLauncher, job, fixedAssetService);
        });
    }


}
