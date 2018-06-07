/*
 * Copyright 2017 JBoss Inc
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

public class NoContractFoundException extends InvalidContractException implements IStatusCode {
    private static final long serialVersionUID = -4912477715792327167L;
    private int statusCode = 404;

    public NoContractFoundException(String message) {
        super(message);
    }

    public NoContractFoundException(String message, Exception e) {
        super(message, e);
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
