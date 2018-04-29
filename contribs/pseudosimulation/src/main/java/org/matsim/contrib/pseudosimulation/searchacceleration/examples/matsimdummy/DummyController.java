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
package org.matsim.contrib.pseudosimulation.searchacceleration.examples.matsimdummy;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.VehicleEntersTrafficEventHandler;
import org.matsim.core.controler.events.IterationStartsEvent;
import org.matsim.core.controler.listener.IterationStartsListener;
import org.matsim.core.events.handler.EventHandler;
import org.matsim.core.replanning.PlanStrategy;

/**
 * Code that pretends to be the MATSim controller. The sole purpose of this is
 * to illustrate a logic that eventually is wired into the real MATSim
 * controller.
 * 
 * @author Gunnar Flötteröd
 *
 */
public class DummyController {

	private IterationStartsListener iterationStartsListener;

	private LinkEnterEventHandler linkEnterEventHandler;

	private VehicleEntersTrafficEventHandler vehicleEntersTrafficEventHandler;

	private PlanStrategy overridingPlanStragety;

	public DummyController(Scenario scenario) {
	}
	
	public void addIterationStartsListener(IterationStartsListener iterationStartsListener) {
		this.iterationStartsListener = iterationStartsListener;
	}

	public void addEventHandler(final EventHandler eventHandler) {
		this.linkEnterEventHandler = (LinkEnterEventHandler) eventHandler;
		this.vehicleEntersTrafficEventHandler = (VehicleEntersTrafficEventHandler) eventHandler;
	}

	public void setPlanStrategyThatOverridesAllOthers(PlanStrategy overridingPlanStrategy) {
		this.overridingPlanStragety = overridingPlanStrategy;
	}

	public void run() {

		callIterationStartsListener(0);
		execution(0);
		scoring();

		for (int iteration = 1; iteration <= 10; iteration++) {

			callIterationStartsListener(iteration);

			/*
			 * TODO The re-planning must do nothing but take over the plan
			 * choice decisions computed by the during the last call to the
			 * iterationStartsListener that is implemented by an instance of
			 * LinkUsageAnalyzer. Hence the "overridinPlanStrategy".
			 */
			replanning();

			execution(iteration);
			scoring();
		}
	}

	/*
	 * Placeholder functions for actual MATSim functionality. Again, the sole
	 * purpose of this is to specify a program logic that eventually is wired
	 * into the real MATSim controller.
	 */

	private void callIterationStartsListener(final int iteration) {
		this.iterationStartsListener.notifyIterationStarts(new IterationStartsEvent(null, iteration));
	}

	private void execution(int iteration) {
		linkEnterEventHandler.reset(iteration);
		vehicleEntersTrafficEventHandler.reset(iteration);
		// run the physical mobsim
	}

	private void scoring() {
		// run the scoring
	}

	private void replanning() {
		// TODO use overridingPlanStrategy
	}
}
