package net.mirwaldt.jcomparison.object;

import java.util.Map;
import java.util.Set;

/**
 * This file is part of the open-source-framework jComparison.
 * Copyright (C) 2015-2017 Michael Mirwaldt.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Person {
	enum Sex {
		MALE, FEMALE
	}

	enum Feature {
		HAIR_COLOR, SKIN_COLOR, TATTOO_TEXT, WRISTWATCH_BRAND
	}

	private final String name;
	private final Sex sex;
	private final int age;
	private final boolean isMarried;
	private final double moneyInPocket;
	private final Address address;
	
	private final Map<Feature, String> features;
	private final Set<String> leisureActivities;

	// TODO: maybe more fields?
	// private final String[] children;
	// private final List<String> companiesWhoHeOrSheWorkedFor;

	public Person(String name, Sex sex, int age, boolean isMarried, double moneyInPocket, Address address, Map<Feature, String> features, Set<String> leisureActivities) {
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.isMarried = isMarried;
		this.moneyInPocket = moneyInPocket;
		this.address = address;
		this.features = features;
		this.leisureActivities = leisureActivities;
	}

	public String getName() {
		return name;
	}

	public Sex getSex() {
		return sex;
	}

	public int getAge() {
		return age;
	}

	public boolean isMarried() {
		return isMarried;
	}

	public double getMoneyInPocket() {
		return moneyInPocket;
	}

	public Address getAddress() {
		return address;
	}

	public Map<Feature, String> getFeatures() {
		return features;
	}

	public Set<String> getLeisureActivities() {
		return leisureActivities;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Person person = (Person) o;

		if (age != person.age) return false;
		if (isMarried != person.isMarried) return false;
		if (Double.compare(person.moneyInPocket, moneyInPocket) != 0) return false;
		if (name != null ? !name.equals(person.name) : person.name != null) return false;
		if (sex != person.sex) return false;
		if (address != null ? !address.equals(person.address) : person.address != null) return false;
		if (features != null ? !features.equals(person.features) : person.features != null) return false;
		return leisureActivities != null ? leisureActivities.equals(person.leisureActivities) : person.leisureActivities == null;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = name != null ? name.hashCode() : 0;
		result = 31 * result + (sex != null ? sex.hashCode() : 0);
		result = 31 * result + age;
		result = 31 * result + (isMarried ? 1 : 0);
		temp = Double.doubleToLongBits(moneyInPocket);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (features != null ? features.hashCode() : 0);
		result = 31 * result + (leisureActivities != null ? leisureActivities.hashCode() : 0);
		return result;
	}
}
