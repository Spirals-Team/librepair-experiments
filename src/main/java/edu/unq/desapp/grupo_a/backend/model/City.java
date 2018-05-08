package edu.unq.desapp.grupo_a.backend.model;

import edu.unq.desapp.grupo_a.backend.model.exceptions.VehicleDataException;

public class City {

	public static void check(City city) throws VehicleDataException {
		if (city == null) {
			throw new VehicleDataException();
		}
	}
}
