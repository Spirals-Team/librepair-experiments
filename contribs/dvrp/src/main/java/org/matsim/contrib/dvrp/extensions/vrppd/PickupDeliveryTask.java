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

package org.matsim.contrib.dvrp.extensions.vrppd;

import java.util.List;

import org.matsim.contrib.dvrp.schedule.StayTask;

public interface PickupDeliveryTask extends StayTask {
	boolean isPickup();

	List<PickupDeliveryRequest> getRequests();

	void addRequest(PickupDeliveryRequest request);

	void removeRequest(PickupDeliveryRequest request);

	void removeAllRequests();
}
