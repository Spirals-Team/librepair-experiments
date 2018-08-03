/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2014 by the members listed in the COPYING,        *
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

package org.matsim.contrib.dynagent;

import java.util.List;

import org.matsim.api.core.v01.Id;
import org.matsim.core.mobsim.qsim.pt.TransitVehicle;
import org.matsim.pt.transitSchedule.api.*;

public interface PTPassengerDynLeg extends PassengerDynLeg {
	public boolean getEnterTransitRoute(final TransitLine line, final TransitRoute transitRoute,
			final List<TransitRouteStop> stopsToCome, TransitVehicle transitVehicle);

	public boolean getExitAtStop(final TransitStopFacility stop);

	public Id<TransitStopFacility> getDesiredAccessStopId();

	public Id<TransitStopFacility> getDesiredDestinationStopId();
}
