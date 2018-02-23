/*
 * JBoss, Home of Professional Open Source
 * Copyright 2017 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aesh.terminal.http.server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.aesh.terminal.http.Status;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:matejonnet@gmail.com">Matej Lazar</a>
 */
@JsonDeserialize(using = TaskStatusUpdateEventDeserializer.class)
public class TaskStatusUpdateEvent implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(TaskStatusUpdateEvent.class.getName());

    private final String taskId;
    private final Status oldStatus;
    private final Status newStatus;
    private final String context;

    public TaskStatusUpdateEvent(String taskId, Status oldStatus, Status newStatus, String context) {
        this.taskId = taskId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.context = context;
    }

    public String getTaskId() {
        return taskId;
    }

    public Status getOldStatus() {
        return oldStatus;
    }

    public Status getNewStatus() {
        return newStatus;
    }

    public String getContext() {
        return context;
    }

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        }
        catch (JsonProcessingException e) {
            LOGGER.log(Level.SEVERE, "Cannot serialize object.", e);
        }
        return null;
    }

    public static TaskStatusUpdateEvent fromJson(String serialized) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(serialized, TaskStatusUpdateEvent.class);
        }
        catch (JsonParseException | JsonMappingException e) {
            LOGGER.log(Level.SEVERE, "Cannot deserialize object from json", e);
            throw e;
        }
    }

}
