/* (c) 2016 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.backuprestore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.CoverageInfo;
import org.geoserver.catalog.FeatureTypeInfo;
import org.geoserver.platform.resource.Files;
import org.geotools.factory.Hints;
import org.geotools.filter.text.ecql.ECQL;
import org.junit.Test;
import org.opengis.filter.Filter;
import org.springframework.batch.core.BatchStatus;

/**
 * 
 * @author Alessio Fabiani, GeoSolutions
 *
 */
public class BackupTest extends BackupRestoreTestSupport {
    
    @Test
    public void testRunSpringBatchBackupJob() throws Exception {
        Hints hints = new Hints(new HashMap(2));
        hints.add(new Hints(new Hints.OptionKey(Backup.PARAM_BEST_EFFORT_MODE), Backup.PARAM_BEST_EFFORT_MODE));

        BackupExecutionAdapter backupExecution = backupFacade.runBackupAsync(
                Files.asResource(File.createTempFile("testRunSpringBatchBackupJob", ".zip")), true, null, hints);

        // Wait a bit
        Thread.sleep(100);

        assertNotNull(backupFacade.getBackupExecutions());
        assertTrue(!backupFacade.getBackupExecutions().isEmpty());
        assertNotNull(backupExecution);

        while (backupExecution.getStatus() != BatchStatus.COMPLETED) {
            Thread.sleep(100);

            if (backupExecution.getStatus() == BatchStatus.ABANDONED
                    || backupExecution.getStatus() == BatchStatus.FAILED
                    || backupExecution.getStatus() == BatchStatus.UNKNOWN) {

                for (Throwable exception : backupExecution.getAllFailureExceptions()) {
                    LOGGER.log(Level.INFO, "ERROR: " + exception.getLocalizedMessage(), exception);
                    exception.printStackTrace();
                }
                break;
            }
        }

        assertTrue(backupExecution.getStatus() == BatchStatus.COMPLETED);
    }

    @Test
    public void testTryToRunMultipleSpringBatchBackupJobs() throws Exception {
        Hints hints = new Hints(new HashMap(2));
        hints.add(new Hints(new Hints.OptionKey(Backup.PARAM_BEST_EFFORT_MODE), Backup.PARAM_BEST_EFFORT_MODE));

        backupFacade.runBackupAsync(
                Files.asResource(File.createTempFile("testRunSpringBatchBackupJob", ".zip")), true, null, hints);
        try {
            backupFacade.runBackupAsync(
                    Files.asResource(File.createTempFile("testRunSpringBatchBackupJob", ".zip")),
                    true, null, hints);
        } catch (IOException e) {
            assertEquals(e.getMessage(),
                    "Could not start a new Backup Job Execution since there are currently Running jobs.");
        }

        // Wait a bit
        Thread.sleep(100);

        assertNotNull(backupFacade.getBackupExecutions());
        assertTrue(!backupFacade.getBackupExecutions().isEmpty());
        assertTrue(backupFacade.getBackupRunningExecutions().size() == 1);

        BackupExecutionAdapter backupExecution = null;
        final Iterator<BackupExecutionAdapter> iterator = backupFacade.getBackupExecutions()
                .values().iterator();
        while (iterator.hasNext()) {
            backupExecution = iterator.next();
        }

        assertNotNull(backupExecution);

        while (backupExecution.getStatus() != BatchStatus.COMPLETED) {
            Thread.sleep(100);

            if (backupExecution.getStatus() == BatchStatus.ABANDONED
                    || backupExecution.getStatus() == BatchStatus.FAILED
                    || backupExecution.getStatus() == BatchStatus.UNKNOWN) {
                LOGGER.severe("backupExecution.getStatus() == " + (backupExecution.getStatus()));

                for (Throwable exception : backupExecution.getAllFailureExceptions()) {
                    LOGGER.log(Level.INFO, "ERROR: " + exception.getLocalizedMessage(), exception);
                    exception.printStackTrace();
                }
                break;
            }
        }

        assertTrue(backupExecution.getStatus() == BatchStatus.COMPLETED);
    }

    @Test
    public void testRunSpringBatchRestoreJob() throws Exception {
        Hints hints = new Hints(new HashMap(2));
        hints.add(new Hints(new Hints.OptionKey(Backup.PARAM_BEST_EFFORT_MODE), Backup.PARAM_BEST_EFFORT_MODE));

        RestoreExecutionAdapter restoreExecution = backupFacade
                .runRestoreAsync(file("geoserver-full-backup.zip"), null, hints);

        // Wait a bit
        Thread.sleep(100);

        assertNotNull(backupFacade.getRestoreExecutions());
        assertTrue(!backupFacade.getRestoreExecutions().isEmpty());

        assertNotNull(restoreExecution);

        Thread.sleep(100);

        final Catalog restoreCatalog = restoreExecution.getRestoreCatalog();
        assertNotNull(restoreCatalog);

        while (restoreExecution.getStatus() != BatchStatus.COMPLETED) {
            Thread.sleep(100);

            if (restoreExecution.getStatus() == BatchStatus.ABANDONED
                    || restoreExecution.getStatus() == BatchStatus.FAILED
                    || restoreExecution.getStatus() == BatchStatus.UNKNOWN) {

                for (Throwable exception : restoreExecution.getAllFailureExceptions()) {
                    LOGGER.log(Level.INFO, "ERROR: " + exception.getLocalizedMessage(), exception);
                    exception.printStackTrace();
                }
                break;
            }
        }

        assertTrue(restoreExecution.getStatus() == BatchStatus.COMPLETED);

        if (restoreCatalog.getWorkspaces().size() > 0) {
            assertTrue(restoreCatalog.getWorkspaces().size() == restoreCatalog.getNamespaces().size());
    
            assertTrue(restoreCatalog.getDataStores().size() == 4);
            assertTrue(restoreCatalog.getResources(FeatureTypeInfo.class).size() == 14);
            assertTrue(restoreCatalog.getResources(CoverageInfo.class).size() == 4);
            assertTrue(restoreCatalog.getStyles().size() == 21);
            assertTrue(restoreCatalog.getLayers().size() == 4);
            assertTrue(restoreCatalog.getLayerGroups().size() == 1);
        }
    }

    @Test
    public void testRunSpringBatchFilteredRestoreJob() throws Exception {
        Hints hints = new Hints(new HashMap(2));
        hints.add(new Hints(new Hints.OptionKey(Backup.PARAM_BEST_EFFORT_MODE), Backup.PARAM_BEST_EFFORT_MODE));

        Filter filter = ECQL.toFilter("name = 'topp'");
        RestoreExecutionAdapter restoreExecution = backupFacade
                .runRestoreAsync(file("geoserver-full-backup.zip"), filter, hints);

        // Wait a bit
        Thread.sleep(100);

        assertNotNull(backupFacade.getRestoreExecutions());
        assertTrue(!backupFacade.getRestoreExecutions().isEmpty());

        assertNotNull(restoreExecution);

        Thread.sleep(100);

        final Catalog restoreCatalog = restoreExecution.getRestoreCatalog();
        assertNotNull(restoreCatalog);

        while (restoreExecution.getStatus() != BatchStatus.COMPLETED) {
            Thread.sleep(100);

            if (restoreExecution.getStatus() == BatchStatus.ABANDONED
                    || restoreExecution.getStatus() == BatchStatus.FAILED
                    || restoreExecution.getStatus() == BatchStatus.UNKNOWN) {

                for (Throwable exception : restoreExecution.getAllFailureExceptions()) {
                    LOGGER.log(Level.INFO, "ERROR: " + exception.getLocalizedMessage(), exception);
                    exception.printStackTrace();
                }
                break;
            }
        }

        assertTrue(restoreExecution.getStatus() == BatchStatus.COMPLETED);
        if (restoreCatalog.getWorkspaces().size() > 0) {
            assertTrue(restoreCatalog.getWorkspaces().size() == 2);
    
            assertTrue(restoreCatalog.getDataStores().size() == 2);
            assertTrue(restoreCatalog.getStyles().size() == 21);
        }
    }

    @Test
    public void testStopSpringBatchBackupJob() throws Exception {
        Hints hints = new Hints(new HashMap(2));
        hints.add(new Hints(new Hints.OptionKey(Backup.PARAM_BEST_EFFORT_MODE), Backup.PARAM_BEST_EFFORT_MODE));
        
        BackupExecutionAdapter backupExecution = backupFacade.runBackupAsync(
                Files.asResource(File.createTempFile("testRunSpringBatchBackupJob", ".zip")), true, null, hints);

        while(backupExecution.getStatus() != BatchStatus.STARTED) {
            // Wait a bit
            Thread.sleep(10);
            
            if (backupExecution.getStatus() == BatchStatus.ABANDONED
                    || backupExecution.getStatus() == BatchStatus.FAILED
                    || backupExecution.getStatus() == BatchStatus.UNKNOWN) {

                for (Throwable exception : backupExecution.getAllFailureExceptions()) {
                    LOGGER.log(Level.INFO, "ERROR: " + exception.getLocalizedMessage(), exception);
                    exception.printStackTrace();
                }
                break;
            }
        }
        
        if (backupExecution.getStatus() != BatchStatus.COMPLETED) {
            backupFacade.stopExecution(backupExecution.getId());
            
            // Wait a bit
            Thread.sleep(100);
    
            assertNotNull(backupExecution);
    
            while (backupExecution.getStatus() != BatchStatus.STOPPED) {
                Thread.sleep(100);
    
                if (backupExecution.getStatus() == BatchStatus.ABANDONED
                        || backupExecution.getStatus() == BatchStatus.FAILED
                        || backupExecution.getStatus() == BatchStatus.UNKNOWN) {
    
                    for (Throwable exception : backupExecution.getAllFailureExceptions()) {
                        LOGGER.log(Level.INFO, "ERROR: " + exception.getLocalizedMessage(), exception);
                        exception.printStackTrace();
                    }
                    break;
                }
            }
    
            assertTrue(backupExecution.getStatus() == BatchStatus.STOPPED);
        }
    }

}
