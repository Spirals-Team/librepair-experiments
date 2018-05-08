package edu.unq.desapp.grupo_a.backend.model.builders;

import java.util.ArrayList;
import java.util.List;

import edu.unq.desapp.grupo_a.backend.model.Address;
import edu.unq.desapp.grupo_a.backend.model.Photo;
import edu.unq.desapp.grupo_a.backend.model.Vehicle;
import edu.unq.desapp.grupo_a.backend.model.VehicleData;

public class VehicleBuilder {

	private VehicleData vehicleData = VehicleDataBuilder.aVehicleData().build();
	private Address withdrawAddress = null;
	private List<Address> returnAddresses = new ArrayList<>();
	private Boolean availability = null;
	private Double rentPrice = null;
	private List<Photo> photos = new ArrayList<>();
	
	public static VehicleBuilder aVehicle() {
		return new VehicleBuilder();
	}
	
	public Vehicle build() {

		Vehicle vehicle = new Vehicle(vehicleData, withdrawAddress, returnAddresses,
				availability, rentPrice, photos);
		return vehicle;
	}

	public VehicleBuilder withVehicleData(final VehicleData aVehicleData) {
		vehicleData = aVehicleData;
		return this;
	}
}

