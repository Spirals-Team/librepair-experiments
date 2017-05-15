package ca.uhn.fhir.rest.gclient;

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

import java.util.Date;

import org.hl7.fhir.instance.model.api.IPrimitiveType;

public interface IHistoryTyped<T> extends IClientExecutable<IHistoryTyped<T>, T>  {

	/**
	 * Request that the server return only resource versions that were created at or after the given time (inclusive)
	 */
	IHistoryTyped<T> since(Date theCutoff);

	/**
	 * Request that the server return only resource versions that were created at or after the given time (inclusive)
	 * <p>
	 * Parameter theCutoff can be any priitive type which accepts a date, such as
	 * a <code>DateTimeDt</code>, <code>InstantType</code>, etc.
	 * </p>
	 */
	IHistoryTyped<T> since(IPrimitiveType<Date> theCutoff);

	/**
	 * Request that the server return only up to <code>theCount</code> number of resources
	 */
	IHistoryTyped<T> count(Integer theCount);
	
	
}
