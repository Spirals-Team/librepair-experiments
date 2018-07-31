/*
 * Copyright (c) 2017 Bosch Software Innovations GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/epl-2.0/index.php
 * Contributors:
 *    Bosch Software Innovations GmbH - initial contribution
 *
 */
package org.eclipse.ditto.services.things.persistence.actors.strategies.commands;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.eclipse.ditto.model.base.headers.DittoHeaders;
import org.eclipse.ditto.model.things.Thing;
import org.eclipse.ditto.services.utils.akka.LogUtil;
import org.eclipse.ditto.signals.commands.things.modify.DeleteThing;
import org.eclipse.ditto.signals.commands.things.modify.DeleteThingResponse;
import org.eclipse.ditto.signals.events.things.ThingDeleted;

import akka.event.DiagnosticLoggingAdapter;

/**
 * This strategy handles the {@link DeleteThing} command.
 */
@Immutable
final class DeleteThingStrategy extends AbstractCommandStrategy<DeleteThing> {

    /**
     * Constructs a new {@code DeleteThingStrategy} object.
     */
    DeleteThingStrategy() {
        super(DeleteThing.class);
    }

    @Override
    protected Result doApply(final Context context, @Nullable final Thing thing,
            final long nextRevision, final DeleteThing command) {
        final String thingId = context.getThingId();
        final DittoHeaders dittoHeaders = command.getDittoHeaders();
        final DiagnosticLoggingAdapter log = context.getLog();
        LogUtil.enhanceLogWithCorrelationId(log, command);
        log.info("Deleted Thing with ID <{}>.", thingId);

        return ResultFactory.newResult(
                ThingDeleted.of(thingId, nextRevision, getEventTimestamp(), dittoHeaders),
                DeleteThingResponse.of(thingId, dittoHeaders), true);
    }

}
