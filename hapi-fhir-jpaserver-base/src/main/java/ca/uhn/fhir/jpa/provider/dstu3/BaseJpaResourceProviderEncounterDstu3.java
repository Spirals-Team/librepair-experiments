package ca.uhn.fhir.jpa.provider.dstu3;

import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.UnsignedIntType;

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

import ca.uhn.fhir.jpa.dao.IFhirResourceDaoEncounter;
import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.valueset.BundleTypeEnum;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Operation;
import ca.uhn.fhir.rest.annotation.OperationParam;
import ca.uhn.fhir.rest.annotation.Sort;
import ca.uhn.fhir.rest.api.SortSpec;
import ca.uhn.fhir.rest.param.DateRangeParam;
import ca.uhn.fhir.rest.server.Constants;

public class BaseJpaResourceProviderEncounterDstu3 extends JpaResourceProviderDstu3<Encounter> {

	/**
	 * Encounter/123/$everything
	 */
	//@formatter:off
	@Operation(name = "everything", idempotent = true, bundleType=BundleTypeEnum.SEARCHSET)
	public ca.uhn.fhir.rest.server.IBundleProvider EncounterInstanceEverything(

			javax.servlet.http.HttpServletRequest theServletRequest,

			@IdParam 
			IdType theId,
			
			@Description(formalDefinition="Results from this method are returned across multiple pages. This parameter controls the size of those pages.") 
			@OperationParam(name = Constants.PARAM_COUNT) 
			UnsignedIntType theCount,
			
			@Description(shortDefinition="Only return resources which were last updated as specified by the given range")
			@OperationParam(name = Constants.PARAM_LASTUPDATED, min=0, max=1) 
			DateRangeParam theLastUpdated,
			
			@Sort
			SortSpec theSortSpec
			) {
		//@formatter:on

		startRequest(theServletRequest);
		try {
			return ((IFhirResourceDaoEncounter<Encounter>)getDao()).encounterInstanceEverything(theServletRequest, theId, theCount, theLastUpdated, theSortSpec);
		} finally {
			endRequest(theServletRequest);
		}}

		/**
		 * /Encounter/$everything
		 */
		//@formatter:off
		@Operation(name = "everything", idempotent = true, bundleType=BundleTypeEnum.SEARCHSET)
		public ca.uhn.fhir.rest.server.IBundleProvider EncounterTypeEverything(

				javax.servlet.http.HttpServletRequest theServletRequest,

				@Description(formalDefinition="Results from this method are returned across multiple pages. This parameter controls the size of those pages.") 
				@OperationParam(name = Constants.PARAM_COUNT) 
				UnsignedIntType theCount,
				
				@Description(shortDefinition="Only return resources which were last updated as specified by the given range")
				@OperationParam(name = Constants.PARAM_LASTUPDATED, min=0, max=1) 
				DateRangeParam theLastUpdated,
				
				@Sort
				SortSpec theSortSpec
				) {
			//@formatter:on

			startRequest(theServletRequest);
			try {
				return ((IFhirResourceDaoEncounter<Encounter>)getDao()).encounterTypeEverything(theServletRequest, theCount, theLastUpdated, theSortSpec);
			} finally {
				endRequest(theServletRequest);
			}

	}

}
