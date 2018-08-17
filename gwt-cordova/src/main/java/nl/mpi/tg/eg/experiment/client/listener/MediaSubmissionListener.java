/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.experiment.client.listener;

import com.google.gwt.typedarrays.shared.Uint8Array;

/**
 * @since Aug 13, 2018 18:28:24 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public interface MediaSubmissionListener {

    void submissionFailed(final String message, final String userIdString, final String screenName, final String stimulusIdString, final Uint8Array dataArray);

    void submissionComplete(String message);
}
