/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.webservices.rest.web.v1_0.converter.openmrs2_2;

import org.openmrs.CodedOrFreeText;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.Converter;
import org.openmrs.module.webservices.rest.web.response.ConversionException;

@Handler(supports = CodedOrFreeText.class, order = 0)
public class CodedOrFreeTextConverter implements Converter<CodedOrFreeText> {
	
	@Override
	public SimpleObject asRepresentation(CodedOrFreeText instance, Representation rep) throws ConversionException {
		return null;
	}
	
	@Override
	public CodedOrFreeText getByUniqueId(String uuid) {
		return null;
	}
	
	@Override
	public Object getProperty(CodedOrFreeText instance, String propertyName) throws ConversionException {
		if (propertyName.equals("specificName")) {
			((CodedOrFreeText) instance).getSpecificName();
		}
		else if (propertyName.equals("coded")) {
			((CodedOrFreeText) instance).getCoded();
		}
		else if (propertyName.equals("nodeCoded")) {
			((CodedOrFreeText) instance).getNonCoded();
		}
		return null;
	}
	
	@Override
	public CodedOrFreeText newInstance(String type) {
		return new CodedOrFreeText();
	}
	
	@Override
	public void setProperty(Object instance, String propertyName, Object value) throws ConversionException {
		if (propertyName.equals("specificName")) {
			((CodedOrFreeText) instance).setSpecificName(Context.getConceptService().getConceptNameByUuid((String) value));
		}
		else if (propertyName.equals("coded")) {
			((CodedOrFreeText) instance).setCoded(Context.getConceptService().getConceptByUuid((String) value));
		}
		else if (propertyName.equals("nodeCoded")) {
			((CodedOrFreeText) instance).setNonCoded((String) value);
		}
	}
}
