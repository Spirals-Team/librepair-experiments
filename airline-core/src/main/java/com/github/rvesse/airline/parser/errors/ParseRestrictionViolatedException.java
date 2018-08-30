/**
 * Copyright (C) 2010-16 the original author or authors.
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
package com.github.rvesse.airline.parser.errors;

/**
 * Exception class that indicates that some restriction was violated
 */
public class ParseRestrictionViolatedException extends ParseException {
    private static final long serialVersionUID = -3269082489946417712L;

    public ParseRestrictionViolatedException(String message, Object... args) {
        super(message, args);
    }

    public ParseRestrictionViolatedException(Exception cause, String message, Object... args) {
        super(cause, message, args);
    }

}
