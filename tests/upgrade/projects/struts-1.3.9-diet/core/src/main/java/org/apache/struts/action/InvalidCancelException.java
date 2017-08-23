/*
 * $Id: InvalidCancelException.java 471754 2006-11-06 14:55:09Z husted $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.struts.action;


/**
 * <p> Thrown when a token generated by the Cancel tag is found in the
 * request, but the cancellable property for the Action Mapping is not set.
 * </p>
 */
public class InvalidCancelException extends Exception {
    /**
     * <p>Default constructor.</p>
     */
    public InvalidCancelException() {
        super();
    }

    /**
     * <p>Construct the exception with the specified message.</p>
     *
     * @param message the message
     */
    public InvalidCancelException(String message) {
        super(message);
    }
}
