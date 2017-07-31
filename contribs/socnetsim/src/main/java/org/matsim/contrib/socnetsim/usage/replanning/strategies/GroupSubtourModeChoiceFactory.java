/* *********************************************************************** *
 * project: org.matsim.*
 * GroupSubtourModeChoiceFactory.java
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
package org.matsim.contrib.socnetsim.usage.replanning.strategies;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.replanning.modules.SubtourModeChoice;
import org.matsim.core.router.TripRouter;

import org.matsim.contrib.socnetsim.framework.PlanRoutingAlgorithmFactory;
import org.matsim.contrib.socnetsim.framework.population.JointPlans;
import org.matsim.contrib.socnetsim.framework.replanning.GroupPlanStrategy;
import org.matsim.contrib.socnetsim.usage.replanning.GroupPlanStrategyFactoryUtils;
import org.matsim.contrib.socnetsim.framework.replanning.IndividualBasedGroupStrategyModule;
import org.matsim.contrib.socnetsim.framework.replanning.modules.PlanLinkIdentifier;
import org.matsim.contrib.socnetsim.framework.replanning.modules.PlanLinkIdentifier.Strong;
import org.matsim.contrib.socnetsim.sharedvehicles.VehicleRessources;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author thibautd
 */
public class GroupSubtourModeChoiceFactory extends AbstractConfigurableSelectionStrategy {

	private final Scenario sc;
	private final PlanRoutingAlgorithmFactory planRoutingAlgorithmFactory;
	private final Provider<TripRouter> tripRouterFactory;
	private final PlanLinkIdentifier planLinkIdentifier;
	private javax.inject.Provider<TripRouter> tripRouterProvider;

	@Inject
	public GroupSubtourModeChoiceFactory(Scenario sc, PlanRoutingAlgorithmFactory planRoutingAlgorithmFactory, Provider<TripRouter> tripRouterFactory,
										 @Strong PlanLinkIdentifier planLinkIdentifier, javax.inject.Provider<TripRouter> tripRouterProvider) {
		this.sc = sc;
		this.planRoutingAlgorithmFactory = planRoutingAlgorithmFactory;
		this.tripRouterFactory = tripRouterFactory;
		this.planLinkIdentifier = planLinkIdentifier;
		this.tripRouterProvider = tripRouterProvider;
	}


	@Override
	public GroupPlanStrategy get() {
		final GroupPlanStrategy strategy = instantiateStrategy( sc.getConfig() );

		// Why the hell did I put this here???
		//strategy.addStrategyModule(
		//		createReRouteModule(
		//			sc.getConfig(),
		//			planRoutingAlgorithmFactory,
		//			tripRouterFactory ) );

		strategy.addStrategyModule(
				new IndividualBasedGroupStrategyModule(
					new SubtourModeChoice(
							tripRouterProvider, sc.getConfig().global(), sc.getConfig().subtourModeChoice()) ) );

		// TODO: add an option to enable or disable this part?
		final VehicleRessources vehicles =
				(VehicleRessources) sc.getScenarioElement(
					VehicleRessources.ELEMENT_NAME );
		if ( vehicles != null ) {
			strategy.addStrategyModule(
					GroupPlanStrategyFactoryUtils.createVehicleAllocationModule(
							sc.getConfig(),
							vehicles) );
		}

		strategy.addStrategyModule(
				GroupPlanStrategyFactoryUtils.createReRouteModule(
					sc.getConfig(),
					planRoutingAlgorithmFactory,
					tripRouterFactory ) );

		strategy.addStrategyModule(
				GroupPlanStrategyFactoryUtils.createSynchronizerModule(
					sc.getConfig(),
					tripRouterFactory) );
		
		strategy.addStrategyModule(
				GroupPlanStrategyFactoryUtils.createRecomposeJointPlansModule(
					sc.getConfig(),
					((JointPlans) sc.getScenarioElement( JointPlans.ELEMENT_NAME  )).getFactory(),
					planLinkIdentifier));

		return strategy;

	}
}

