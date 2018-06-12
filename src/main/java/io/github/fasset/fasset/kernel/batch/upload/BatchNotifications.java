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
package io.github.fasset.fasset.kernel.batch.upload;

import io.github.fasset.fasset.kernel.batch.depreciation.DepreciationJobProxy;
import io.github.fasset.fasset.kernel.util.BatchJobExecutionException;
import io.github.fasset.fasset.service.BriefingService;
import io.github.fasset.fasset.service.FixedAssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Raises notifications during start and end of a batch or to interface between two batches
 */
@Component
public class BatchNotifications implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(BatchNotifications.class);

    private final ExcelItemReader excelItemReader;

    private final FixedAssetService fixedAssetService;

    private DepreciationJobProxy depreciationJobProxy;


    private BriefingService briefingService;

    @Autowired
    public BatchNotifications(@Qualifier("excelItemReader") ExcelItemReader excelItemReader, @Qualifier("fixedAssetService") FixedAssetService fixedAssetService,
                              @Qualifier("briefingService") BriefingService briefingService, @Qualifier("depreciationJobProxy") DepreciationJobProxy depreciationJobProxy) {
        this.excelItemReader = excelItemReader;
        this.fixedAssetService = fixedAssetService;
        this.briefingService = briefingService;
        this.depreciationJobProxy = depreciationJobProxy;
    }


    /**
     * Callback before a job executes.
     *
     * @param jobExecution the current {@link JobExecution}
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {

        // Trying to log the filePath here
        log.info("reading file from filePath : {} ", jobExecution.getJobParameters().getString("fileName"));

        // this will reset the nextItem to Zero
        excelItemReader.resetNextItem();
        //excelItemReader.init();

    }

    /**
     * Callback after completion of a job. Called after both both successful and
     * failed executions. To perform logic on a particular status, use
     * "if (jobExecution.getStatus() == BatchStatus.X)".
     *
     * @param jobExecution the current {@link JobExecution}
     */
    @Override
    public void afterJob(JobExecution jobExecution) {

        log.debug("Step 1 completed : following items have been persisted...");

        //fixedAssetService.fetchAllExistingAssets().forEach(System.out::println);

        briefingService.updateCategoryBriefs();

        briefingService.updateServiceOutletBriefs();

        //TODO trigger depreciation with message
        try {
            depreciationJobProxy.initializeDepreciationRun();
        } catch (BatchJobExecutionException e) {
            e.printStackTrace();
        }

    }

}
