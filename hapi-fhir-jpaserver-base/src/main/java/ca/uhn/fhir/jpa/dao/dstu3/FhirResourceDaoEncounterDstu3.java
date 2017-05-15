package ca.uhn.fhir.jpa.dao.dstu3;

/*
 * #%L
 * HAPI FHIR JPA Server
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

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.instance.model.api.IPrimitiveType;

import ca.uhn.fhir.jpa.dao.IFhirResourceDaoEncounter;
import ca.uhn.fhir.jpa.dao.SearchParameterMap;
import ca.uhn.fhir.jpa.dao.SearchParameterMap.EverythingModeEnum;
import ca.uhn.fhir.model.api.IResource;
import ca.uhn.fhir.rest.api.SortSpec;
import ca.uhn.fhir.rest.param.DateRangeParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IBundleProvider;

public class FhirResourceDaoEncounterDstu3 extends FhirResourceDaoDstu3<Encounter>implements IFhirResourceDaoEncounter<Encounter> {

	@Override
	public IBundleProvider encounterInstanceEverything(HttpServletRequest theServletRequest, IIdType theId, IPrimitiveType<Integer> theCount, DateRangeParam theLastUpdated, SortSpec theSort) {
		SearchParameterMap paramMap = new SearchParameterMap();
		if (theCount != null) {
			paramMap.setCount(theCount.getValue());
		}

//		paramMap.setRevIncludes(Collections.singleton(IResource.INCLUDE_ALL.asRecursive()));
		paramMap.setIncludes(Collections.singleton(IResource.INCLUDE_ALL.asRecursive()));
		paramMap.setEverythingMode(theId != null ? EverythingModeEnum.ENCOUNTER_INSTANCE : EverythingModeEnum.ENCOUNTER_TYPE);
		paramMap.setSort(theSort);
		paramMap.setLastUpdated(theLastUpdated);
		if (theId != null) {
			paramMap.add("_id", new StringParam(theId.getIdPart()));
		}
		ca.uhn.fhir.rest.server.IBundleProvider retVal = search(paramMap);
		return retVal;
	}

	@Override
	public IBundleProvider encounterTypeEverything(HttpServletRequest theServletRequest, IPrimitiveType<Integer> theCount, DateRangeParam theLastUpdated, SortSpec theSort) {
		return encounterInstanceEverything(theServletRequest, null, theCount, theLastUpdated, theSort);
	}

}
