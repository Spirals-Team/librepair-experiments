/* (c) 2017 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.taskmanager.schedule;

import org.geoserver.taskmanager.data.Batch;
import org.geoserver.taskmanager.data.Configuration;

/**
 * The batch job is responsible for scheduling the batches.
 * 
 * @author Niels Charlier
 *
 */
public interface BatchJobService {

    /**
     * Saves this batch and update the schedule according to its new settings.
     * This may also mean that the batch is actually unscheduled, or that it scheduling is changed,
     * if it was previously scheduled.
     * 
     * Batches are scheduled if they are ACTIVE, ENABLED and have a FREQUENCY set.
     * Batches which are ACTIVE, but not ENABLED _or_ have FREQUENCY set to NULL, are known by the 
     * quartz scheduler but are never triggered unless explicitly done.
     * Batches which are NOT ACTIVE, are entirely removed from the quartz scheduler.
     * 
     * @param batch the batch.
     * @return the saved batch.
     */
    Batch saveAndSchedule(Batch batch);

    /**
     * Refreshes the scheduler completely based on all of the batches in the database.
     */
    void reloadFromData();

    /**
     * Saves this configuration and update the schedule according to its new settings of each batch.
     * 
     * @param config the Configuration.
     * @return the saved config.
     */
    Configuration saveAndSchedule(Configuration config);

}
