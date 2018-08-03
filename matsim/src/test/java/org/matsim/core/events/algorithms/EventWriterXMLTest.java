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

package org.matsim.core.events.algorithms;

import java.io.File;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.GenericEvent;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;
import org.matsim.testcases.MatsimTestUtils;
import org.matsim.testcases.utils.EventsCollector;
import org.matsim.vehicles.Vehicle;

/**
 * @author mrieser / Senozon AG
 */
public class EventWriterXMLTest {

	@Rule public MatsimTestUtils utils = new MatsimTestUtils();
	
	/**
	 * Some people use the ids as names, including special characters in there... so make sure attribute
	 * values are correctly encoded when written to a file.
	 */
	@Test
	public void testSpecialCharacters() {
		String filename = this.utils.getOutputDirectory() + "testEvents.xml";
		EventWriterXML writer = new EventWriterXML(filename);
		
		writer.handleEvent(new LinkLeaveEvent(3600.0, Id.create("vehicle>3", Vehicle.class), Id.create("link<2", Link.class)));
		writer.handleEvent(new LinkLeaveEvent(3601.0, Id.create("vehicle\"4", Vehicle.class), Id.create("link'3", Link.class)));
		writer.closeFile();
		Assert.assertTrue(new File(filename).exists());

		EventsManager events = EventsUtils.createEventsManager();
		EventsCollector collector = new EventsCollector();
		events.addHandler(collector);
		// this is already a test: is the XML valid so it can be parsed again?
		new MatsimEventsReader(events).readFile(filename);

		Assert.assertEquals("there must be 2 events.", 2, collector.getEvents().size());
		LinkLeaveEvent event1 = (LinkLeaveEvent) collector.getEvents().get(0);
		LinkLeaveEvent event2 = (LinkLeaveEvent) collector.getEvents().get(1);

		Assert.assertEquals("link<2", event1.getLinkId().toString());
		Assert.assertEquals("vehicle>3", event1.getVehicleId().toString());

		Assert.assertEquals("link'3", event2.getLinkId().toString());
		Assert.assertEquals("vehicle\"4", event2.getVehicleId().toString());
	}

	@Test
	public void testNullAttribute() {
		String filename = this.utils.getOutputDirectory() + "testEvents.xml";
		EventWriterXML writer = new EventWriterXML(filename);
		
		GenericEvent event = new GenericEvent("TEST", 3600.0);
		event.getAttributes().put("dummy", null);
		writer.handleEvent(event);
		writer.closeFile();
		Assert.assertTrue(new File(filename).exists());
		
		EventsManager events = EventsUtils.createEventsManager();
		EventsCollector collector = new EventsCollector();
		events.addHandler(collector);
		// this is already a test: is the XML valid so it can be parsed again?
		new MatsimEventsReader(events).readFile(filename);
		
		Assert.assertEquals("there must be 1 event.", 1, collector.getEvents().size());
	}
}
