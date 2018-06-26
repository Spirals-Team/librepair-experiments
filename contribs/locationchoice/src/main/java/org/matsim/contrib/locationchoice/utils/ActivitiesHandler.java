/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2012 by the members listed in the COPYING,        *
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

package org.matsim.contrib.locationchoice.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.contrib.locationchoice.DestinationChoiceConfigGroup;
import org.matsim.contrib.locationchoice.bestresponse.scoring.ScaleEpsilon;

public class ActivitiesHandler {
	private HashSet<String> flexibleTypes = new HashSet<String>();
	private DestinationChoiceConfigGroup dcconfig;
	private ActTypeConverter converter = null;

	public ActivitiesHandler(final DestinationChoiceConfigGroup dcconfig) {
		this.dcconfig = dcconfig;
		this.createActivityTypeConverter();
		this.initFlexibleTypes(dcconfig);
	}
	
	public ScaleEpsilon createScaleEpsilon() {
		ScaleEpsilon scaleEpsilon = new ScaleEpsilon();
		String factors = this.dcconfig.getEpsilonScaleFactors();
		String types = this.dcconfig.getFlexibleTypes();
		
		if (!types.equals("null")) {			
			String[] fentries = factors.split(",", -1);
			String[] tentries = types.split(",", -1);
			
			// check if demand is v1 with types = s0.5, ...
			if (tentries[0].length() == 1) {
				scaleEpsilon.setUseSimpleTypes(true);
			}
			
			int fentriesLength = fentries.length;
			if (fentries[0].equals("null")) fentriesLength = 0;
			
			int tentriesLength = tentries.length;
			if (tentries[0].equals("null")) tentriesLength = 0;
			
			if (fentriesLength != tentriesLength || factors.equals("null")) {
				throw new RuntimeException("Specify an epsilon (now: " + fentriesLength + " specified) " +
						"for every flexible activity type (now: " + tentriesLength + " specified)!");
			}			
			for (int i = 0; i < fentries.length; i++) {
					scaleEpsilon.setEpsilonFactor(tentries[i].trim(), Double.parseDouble(fentries[i].trim()));
			}
		}	
		return scaleEpsilon;
	}
	
	private void createActivityTypeConverter() {
		String types = this.dcconfig.getFlexibleTypes();
		String[] tentries = types.split(",", -1);
		
		// check if demand = v1
		if (tentries[0].length() == 1) {
			this.converter = new ActTypeConverter(true);
			Logger.getLogger(this.getClass()).warn("LocationChoice uses an activity type converter that only uses the first letter of every activity type. " +
					"For example, `shopping' and `school' may be treated as the same activity.  Make sure this is what you want.") ; // kai, jan'13
		}
		else {
			this.converter = new ActTypeConverter(false);
		}
		// yyyy If I understand this correctly, this means: If the first activity in the list of flexible activities is of length
		// one, then "true" is used (i.e. all activities are reduced to length one).  Otherwise "false".  
		// I find this "automagic" a bit dangerous.  kai, jan'13
	}

	// only used by TGSimple
	public List<Activity> getFlexibleActivities(final Plan plan) {
		List<Activity> flexibleActivities = new Vector<Activity>();
		this.getFlexibleActs(plan, flexibleActivities);
		return flexibleActivities;
	}

	private void getFlexibleActs(Plan plan, List<Activity> flexibleActivities) {
		for (int i = 0; i < plan.getPlanElements().size(); i = i + 2) {
			Activity act = (Activity)plan.getPlanElements().get(i);
			if (this.flexibleTypes.contains(this.converter.convertType(act.getType()))) {
				flexibleActivities.add(act);
			}
		}
	}

	private void initFlexibleTypes(DestinationChoiceConfigGroup config) {
		String types = config.getFlexibleTypes();
		if (!types.equals("null")) {
			String[] entries = types.split(",", -1);
			for (int i = 0; i < entries.length; i++) {
				this.flexibleTypes.add(this.converter.convertType(entries[i].trim()));
			}
		}
	}

	public HashSet<String> getFlexibleTypes() {
		return flexibleTypes;
	}

	public void setFlexibleTypes(HashSet<String> flexibleTypes) {
		this.flexibleTypes = flexibleTypes;
	}

	public ActTypeConverter getConverter() {
		return converter;
	}

	public void setConverter(ActTypeConverter converter) {
		this.converter = converter;
	}

}
