/*
 * Copyright 2018 Gunnar Flötteröd
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * contact: gunnar.flotterod@gmail.com
 *
 */
package org.matsim.contrib.pseudosimulation.searchacceleration;

import java.util.LinkedHashMap;
import java.util.Map;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.VehicleEntersTrafficEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.VehicleEntersTrafficEventHandler;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.population.Person;
import org.matsim.contrib.pseudosimulation.searchacceleration.datastructures.SpaceTimeIndicators;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.vehicles.Vehicle;

import floetteroed.utilities.TimeDiscretization;

/**
 * Keeps track of when every single vehicle enters which link.
 * 
 * @author Gunnar Flötteröd
 *
 */
public class LinkUsageListener implements LinkEnterEventHandler, VehicleEntersTrafficEventHandler {

	// -------------------- MEMBERS --------------------

	private final TimeDiscretization timeDiscretization;

	private final Map<Id<Vehicle>, Id<Person>> vehicleId2driverId = new LinkedHashMap<>();

	private final Map<Id<Person>, SpaceTimeIndicators<Id<Link>>> driverId2indicators = new LinkedHashMap<>();

	// -------------------- CONSTRUCTION --------------------

	public LinkUsageListener(final TimeDiscretization timeDiscretization) {
		this.timeDiscretization = timeDiscretization;
	}

	// -------------------- INTERNALS --------------------

	private void registerLinkEntry(final Id<Link> linkId, final Id<Vehicle> vehicleId, final double time_s,
			Id<Person> driverId) {
		if (driverId != null) {
			// Added even if currently outside of the time window because later
			// link entries may be within the time window.
			this.vehicleId2driverId.put(vehicleId, driverId);
		} else {
			driverId = this.vehicleId2driverId.get(vehicleId);
			if (driverId == null) {
				throw new RuntimeException("Driver of vehicle " + vehicleId + " is unknown.");
			}
		}
		if ((time_s >= this.timeDiscretization.getStartTime_s()) && (time_s < this.timeDiscretization.getEndTime_s())) {
			SpaceTimeIndicators<Id<Link>> indicators = this.driverId2indicators.get(driverId);
			if (indicators == null) {
				indicators = new SpaceTimeIndicators<Id<Link>>(this.timeDiscretization.getBinCnt());
				this.driverId2indicators.put(driverId, indicators);
			}
			indicators.visit(linkId, this.timeDiscretization.getBin(time_s));
		}
	}

	// -------------------- IMPLEMENTATION --------------------

	public TimeDiscretization getTimeDiscretization() {
		return this.timeDiscretization;
	}

	Map<Id<Person>, SpaceTimeIndicators<Id<Link>>> getAndClearIndicators() {
		final Map<Id<Person>, SpaceTimeIndicators<Id<Link>>> result = new LinkedHashMap<>(this.driverId2indicators);
		this.vehicleId2driverId.clear();
		this.driverId2indicators.clear();
		return result;
	}

	// --------------- IMPLEMENTATION OF EventHandler INTERFACES ---------------

	@Override
	public void reset(int iteration) {
		// TODO Probably better to (i) remove replace "getAndClearIndicators" by a plain
		// getter and to clear here.
		if (this.driverId2indicators.size() > 0) {
			throw new RuntimeException("veh2indicators should be empty");
		}
		if (this.vehicleId2driverId.size() > 0) {
			throw new RuntimeException("vehicleId2driverId should be empty");
		}
	}

	@Override
	public void handleEvent(final VehicleEntersTrafficEvent event) {
		this.registerLinkEntry(event.getLinkId(), event.getVehicleId(), event.getTime(), event.getPersonId());
	}

	@Override
	public void handleEvent(final LinkEnterEvent event) {
		this.registerLinkEntry(event.getLinkId(), event.getVehicleId(), event.getTime(), null);
	}

	// -------------------- MAIN-FUNCTION, ONLY FOR TESTING --------------------

	public static void main(String[] args) {

		System.out.println("Started ...");

		final Config config = ConfigUtils.loadConfig("./testdata/berlin_2014-08-01_car_1pct/config.xml");
		config.controler().setOverwriteFileSetting(OverwriteFileSetting.deleteDirectoryIfExists);

		final Scenario scenario = ScenarioUtils.loadScenario(config);

		final Controler controler = new Controler(scenario);
		final TimeDiscretization timeDiscr = new TimeDiscretization(0, 3600, 24);
		final LinkUsageListener loa = new LinkUsageListener(timeDiscr);
		controler.getEvents().addHandler(loa);

		controler.run();

		System.out.println("... done.");
	}

}
