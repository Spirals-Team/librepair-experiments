/*
 * OrbisWPS contains a set of libraries to build a Web Processing Service (WPS)
 * compliant with the 2.0 specification.
 *
 * OrbisWPS is part of the OrbisGIS platform
 *
 * OrbisGIS is a java GIS application dedicated to research in GIScience.
 * OrbisGIS is developed by the GIS group of the DECIDE team of the
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 *
 * OrbisWPS is distributed under GPL 3 license.
 *
 * Copyright (C) 2015-2017 CNRS (Lab-STICC UMR CNRS 6285)
 *
 *
 * OrbisWPS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OrbisWPS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OrbisWPS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.orbisgis.orbiswps.client.api.utils;

import java.util.UUID;

/**
 * This interface defines an object listening for the state changes of an executed job.
 * The methods onJobAccepted and onJobRunning might not be called because the rate of the refresh state of the job. A state
 * might no be seen by the client if the it happens between two refresh date.
 *
 * @author Sylvain PALOMINOS
 */
public interface WpsJobStateListener {

    /**
     * Returns the id of the listened job.
     *
     * @return The id of the listened job.
     */
    UUID getJobID();

    /**
     * Method called when the job has been accepted.
     */
    void onJobAccepted();

    /**
     * Method called when the job starts running.
     */
    void onJobRunning();

    /**
     * Method called when the job has end with success.
     */
    void onJobSuccess();

    /**
     * Method called when the job has end with failure.
     */
    void onJobFailed();
}