/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2013 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.facilities;

import org.junit.Assert;
import org.junit.Test;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;

/**
 * @author mrieser / Senozon AG
 */
public class ActivityFacilitiesFactoryImplTest {

	@Test
	public void testCreateActivityFacility() {
		ActivityFacilitiesFactoryImpl factory = new ActivityFacilitiesFactoryImpl();
		ActivityFacility facility = factory.createActivityFacility(Id.create(1980, ActivityFacility.class), new Coord((double) 5, (double) 11));

		Assert.assertEquals("1980", facility.getId().toString());
		Assert.assertEquals(5.0, facility.getCoord().getX(), 1e-9);
		Assert.assertEquals(11.0, facility.getCoord().getY(), 1e-9);
	}

	@Test
	public void testCreateActivityOption() {
		ActivityFacilitiesFactoryImpl factory = new ActivityFacilitiesFactoryImpl();
		ActivityOption option = factory.createActivityOption("leisure");

		Assert.assertEquals("leisure", option.getType());
		Assert.assertEquals(Integer.MAX_VALUE, option.getCapacity(), 1e-9);
	}

}
