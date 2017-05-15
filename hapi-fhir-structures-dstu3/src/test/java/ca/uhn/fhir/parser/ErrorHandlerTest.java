package ca.uhn.fhir.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.uhn.fhir.parser.json.JsonLikeValue.ValueType;

/*
 * #%L
 * HAPI FHIR - Core Library
 * %%
 * Copyright (C) 2014 - 2016 University Health Network
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

public class ErrorHandlerTest {

	@Test
	public void testAdapterMethods() {
		new ErrorHandlerAdapter().unexpectedRepeatingElement(null, null);
		new ErrorHandlerAdapter().unknownAttribute(null, null);
		new ErrorHandlerAdapter().unknownElement(null, null);
		new ErrorHandlerAdapter().containedResourceWithNoId(null);
		new ErrorHandlerAdapter().unknownReference(null, null);
		new ErrorHandlerAdapter().missingRequiredElement(null, null);
		new ErrorHandlerAdapter().incorrectJsonType(null, null, null, null, null, null);
		new ErrorHandlerAdapter().invalidValue(null, null, null);
	}

	@Test
	public void testLenientMethods() {
		new LenientErrorHandler().unexpectedRepeatingElement(null, null);
		new LenientErrorHandler().unknownAttribute(null, null);
		new LenientErrorHandler().unknownElement(null, null);
		new LenientErrorHandler().containedResourceWithNoId(null);
		new LenientErrorHandler().unknownReference(null, null);
		new LenientErrorHandler().incorrectJsonType(null, null, ValueType.ARRAY, null, ValueType.SCALAR, null);
		new LenientErrorHandler().setErrorOnInvalidValue(false).invalidValue(null, "FOO", "");
		new LenientErrorHandler().invalidValue(null, null, "");
		try {
			new LenientErrorHandler().invalidValue(null, "FOO", "");
			fail();
		} catch (DataFormatException e) {
			// good, this one method defaults to causing an error
		}
	}

	@Test(expected = DataFormatException.class)
	public void testStrictMethods1() {
		new StrictErrorHandler().unexpectedRepeatingElement(null, null);
	}

	@Test(expected = DataFormatException.class)
	public void testStrictMethods2() {
		new StrictErrorHandler().unknownAttribute(null, null);
	}

	@Test(expected = DataFormatException.class)
	public void testStrictMethods3() {
		new StrictErrorHandler().unknownElement(null, null);
	}

	@Test(expected = DataFormatException.class)
	public void testStrictMethods4() {
		new StrictErrorHandler().containedResourceWithNoId(null);
	}

	@Test(expected = DataFormatException.class)
	public void testStrictMethods5() {
		new StrictErrorHandler().unknownReference(null, null);
	}

	@Test(expected = DataFormatException.class)
	public void testStrictMethods6() {
		new StrictErrorHandler().incorrectJsonType(null, null, ValueType.ARRAY, null, ValueType.SCALAR, null);
	}

	@Test(expected = DataFormatException.class)
	public void testStrictMethods7() {
		new StrictErrorHandler().invalidValue(null, null, null);
	}

}
