package edu.unq.desapp.grupo_a.backend.model;

import edu.unq.desapp.grupo_a.backend.model.exceptions.VehicleDataException;

public class Phone {

	public static void check(Phone phone) throws VehicleDataException {
		if (phone == null) {
			throw new VehicleDataException();
		}
	}
}
