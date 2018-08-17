/*
 * Copyright (C) 2014 Language In Interaction
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
package nl.mpi.tg.eg.experiment.client.service;

import com.google.gwt.core.client.GWT;
import java.util.logging.Logger;
import nl.mpi.tg.eg.experiment.client.Messages;
import nl.mpi.tg.eg.experiment.client.ServiceLocations;
import nl.mpi.tg.eg.experiment.client.Version;

/**
 * @since Oct 29, 2014 11:18:31 AM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public abstract class AbstractSubmissionService {

    final protected static Logger logger = Logger.getLogger(AbstractSubmissionService.class.getName());
    final protected ServiceLocations serviceLocations = GWT.create(ServiceLocations.class);
    final protected MetadataFieldProvider metadataFieldProvider = new MetadataFieldProvider();
    final protected Version version = GWT.create(Version.class);
    protected final Messages messages = GWT.create(Messages.class);

    abstract boolean isProductionVersion();

    // todo: move common parts to this class
}
