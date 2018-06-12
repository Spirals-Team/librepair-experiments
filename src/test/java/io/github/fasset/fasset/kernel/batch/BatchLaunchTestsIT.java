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
package io.github.fasset.fasset.kernel.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchLaunchTestsIT {

    @Qualifier("importExcelJob")
    @Autowired
    private Job importExcelJob;

    @Qualifier("depreciationJob")
    @Autowired
    private Job depreciationJob;

    @Qualifier("monthlyAssetDepreciationJob")
    @Autowired
    private Job monthlyAssetDepreciationJob;

    @Qualifier("monthlySolDepreciationJob")
    @Autowired
    private Job monthlySolDepreciationJob;

    @Qualifier("monthlyCategoryDepreciationJob")
    @Autowired
    private Job monthlyCategoryDepreciationJob;


    @Test
    public void importExcelJobRuns() throws Exception {

        JobInstance instance = new JobInstance(LocalDate.now().toEpochDay(), importExcelJob.getName());

        JobExecution jobExecution = new JobExecution(instance, new JobParameters());

        assertEquals(BatchStatus.STARTING, jobExecution.getStatus());

    }

    @Test
    public void depreciationJobRuns() throws Exception {

        JobInstance instance = new JobInstance(LocalDate.now().toEpochDay(), depreciationJob.getName());

        JobExecution jobExecution = new JobExecution(instance, new JobParameters());

        assertEquals(BatchStatus.STARTING, jobExecution.getStatus());

    }

    @Test
    public void monthlyAssetDepreciationJobRuns() throws Exception {

        JobInstance instance = new JobInstance(LocalDate.now().toEpochDay(), monthlyAssetDepreciationJob.getName());

        JobExecution jobExecution = new JobExecution(instance, new JobParameters());

        assertEquals(BatchStatus.STARTING, jobExecution.getStatus());

    }

    @Test
    public void monthlySolDepreciationJobRuns() throws Exception {

        JobInstance instance = new JobInstance(LocalDate.now().toEpochDay(), monthlySolDepreciationJob.getName());

        JobExecution jobExecution = new JobExecution(instance, new JobParameters());

        assertEquals(BatchStatus.STARTING, jobExecution.getStatus());

    }

    @Test
    public void monthlyCategoryDepreciationJobRuns() throws Exception {

        JobInstance instance = new JobInstance(LocalDate.now().toEpochDay(), monthlyCategoryDepreciationJob.getName());

        JobExecution jobExecution = new JobExecution(instance, new JobParameters());

        assertEquals(BatchStatus.STARTING, jobExecution.getStatus());

    }

}
