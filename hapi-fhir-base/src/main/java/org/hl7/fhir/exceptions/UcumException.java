package org.hl7.fhir.exceptions;

/*
 * #%L
 * HAPI FHIR - Core Library
 * %%
 * Copyright (C) 2014 - 2017 University Health Network
 * %%
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
 * #L%
 */


public class UcumException extends FHIRException {

	private static final long serialVersionUID = -8535757334881835619L;

	public UcumException() {
		super();
	}

	public UcumException(String message, Throwable cause) {
		super(message, cause);
	}

	public UcumException(String message) {
		super(message);
	}

	public UcumException(Throwable cause) {
		super(cause);
	}

}
