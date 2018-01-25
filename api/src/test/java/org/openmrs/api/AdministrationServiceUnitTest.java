/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.api;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNull;

/**
 * This class unit tests methods in the AdministrationServiceImpl class.
 * Unlike AdministrationServiceTest, this class does not extend
 * BaseContextSensitiveTest so as not to auto-wire the dependencies
 */
public class AdministrationServiceUnitTest {
	
	private AdministrationService adminService = null;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test
	public void executeSQL_shouldFailIfSqlIsNull() {
		
		assertNull(adminService.executeSQL(null, true));
	}
	
	@Test
	public void getGlobalPropertyValue_shouldFailIfDefaultValueIsNull() {
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("The defaultValue argument cannot be null");
		
		adminService.getGlobalPropertyValue("valid.double", null);
		
	}
	
}
