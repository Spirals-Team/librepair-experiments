/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.apiman.gateway.engine.beans.exceptions;

/**
 * Exception thrown when attempting (but failing) to connect to a back end system.
 *
 * @author eric.wittmann@redhat.com
 */
public class ConnectorException extends AbstractEngineException implements IStatusCode {

    private static final long serialVersionUID = -3509254747425991797L;
    private int statusCode = 500;

    /**
     * Constructor.
     * @param message an error message
     */
    public ConnectorException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message an error message
     * @param cause the exception cause the root cause
     */
    public ConnectorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     * @param cause the exception cause the root cause
     */
    public ConnectorException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public void setStatusCode(int code) {
        this.statusCode = code;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
