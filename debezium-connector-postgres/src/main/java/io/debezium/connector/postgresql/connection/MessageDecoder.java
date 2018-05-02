/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

package io.debezium.connector.postgresql.connection;

import java.nio.ByteBuffer;
import java.sql.SQLException;

import org.postgresql.replication.fluent.logical.ChainedLogicalStreamBuilder;

import io.debezium.connector.postgresql.TypeRegistry;
import io.debezium.connector.postgresql.connection.ReplicationStream.ReplicationMessageProcessor;

/**
 * A class that is able to deserialize/decode binary representation of a batch of replication messages generated by
 * logical decoding plugin. Clients provide a callback code for processing.
 *
 * @author Jiri Pechanec
 *
 */
public interface MessageDecoder {

    /**
     * Process a message upon arrival from logical decoder
     *
     * @param buffer - binary representation of replication message
     * @param processor - message processing on arrival
     * @param typeRegistry - registry with known types
     */
    void processMessage(final ByteBuffer buffer, ReplicationMessageProcessor processor, TypeRegistry typeRegistry) throws SQLException, InterruptedException;

    /**
     * Allows MessageDecoder to configure options with which the replication stream is started.
     * The messages CAN contain type metadata.
     * See PostgreSQL command START_REPLICATION SLOT for more details.
     *
     * @param builder
     * @return the builder instance
     */
    ChainedLogicalStreamBuilder optionsWithMetadata(final ChainedLogicalStreamBuilder builder);

    /**
     * Allows MessageDecoder to configure options with which the replication stream is started.
     * The messages MUST NOT contain type metadata.
     * See PostgreSQL command START_REPLICATION SLOT for more details.
     *
     * @param builder
     * @return the builder instance
     */
    ChainedLogicalStreamBuilder optionsWithoutMetadata(final ChainedLogicalStreamBuilder builder);

    /**
     * Signals to this decoder whether messages contain type metadata or not.
     */
    // TODO DBZ-508 Remove once we only support LD plug-ins always sending the metadata
    default void setContainsMetadata(boolean flag) {
    }
}
