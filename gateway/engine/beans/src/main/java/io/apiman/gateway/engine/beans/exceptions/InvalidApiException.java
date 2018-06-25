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
 * Exception thrown when attempting to use a API in some invalid way.  For example
 * when trying to use a API for one API when accessing a different API.
 *
 * @author eric.wittmann@redhat.com
 */
public class InvalidApiException extends AbstractEngineException implements IStatusCode {

    private static final long serialVersionUID = 2430774910840954299L;
    private int statusCode;

    /**
     * Constructor.
     * @param message an error message
     */
    public InvalidApiException(String message) {
        super(message);
    }

    @Override
    public void setStatusCode(int code) {
        this.statusCode = code;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

}
