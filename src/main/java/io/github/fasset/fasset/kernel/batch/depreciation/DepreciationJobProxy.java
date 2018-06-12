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
package io.github.fasset.fasset.kernel.batch.depreciation;

import io.github.fasset.fasset.kernel.batch.FixedAssetsJobsActivator;
import io.github.fasset.fasset.kernel.util.BatchJobExecutionException;
import io.github.fasset.fasset.service.FixedAssetService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Interface for launching the depreciation job. This abstraction enables the launch to be carried out from any
 * object
 */
@Component("depreciationJobProxy")
public class DepreciationJobProxy {

    private final JobLauncher jobLauncher;

    private final FixedAssetService fixedAssetService;

    private final Job depreciationRun;

    private final FixedAssetsJobsActivator fixedAssetsJobsActivator;

    @Autowired
    public DepreciationJobProxy(JobLauncher jobLauncher, @Qualifier("fixedAssetService") FixedAssetService fixedAssetService, @Qualifier("depreciationJob") Job depreciationRun,
                                FixedAssetsJobsActivator fixedAssetsJobsActivator) {
        this.jobLauncher = jobLauncher;
        this.fixedAssetService = fixedAssetService;
        this.depreciationRun = depreciationRun;
        this.fixedAssetsJobsActivator = fixedAssetsJobsActivator;
    }


    public void initializeDepreciationRun() throws BatchJobExecutionException {

        fixedAssetsJobsActivator.bootstrap(jobLauncher, depreciationRun, fixedAssetService);
    }

}

