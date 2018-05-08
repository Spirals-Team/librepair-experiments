package edu.unq.desapp.grupo_a.backend.model.builders;

import java.util.ArrayList;
import java.util.List;

import edu.unq.desapp.grupo_a.backend.model.City;
import edu.unq.desapp.grupo_a.backend.model.Phone;
import edu.unq.desapp.grupo_a.backend.model.Photo;
import edu.unq.desapp.grupo_a.backend.model.VehicleData;
import edu.unq.desapp.grupo_a.backend.model.VehicleType;

public class VehicleDataBuilder {
	
	private VehicleType vehicleType = VehicleType.Car;
	private int passengerCapability = 5;
	private City city = new City();
	private String vehicleDescription = "no description";
	private Phone contactPhone = new Phone();
	private List<Photo> photos = new ArrayList<>();

	public static VehicleDataBuilder aVehicleData() {
		return new VehicleDataBuilder();
	}
	
	public VehicleData build() {
		
		VehicleData vehicleData = new VehicleData(vehicleType, passengerCapability,
				city, vehicleDescription, contactPhone, photos);
		return vehicleData;
	}
	
	public VehicleDataBuilder withVehicleType(VehicleType aVehicleType) {
		vehicleType = aVehicleType;
		return this;
	}

	public VehicleDataBuilder withPassengerCapability(int aPassengerCapability) {
		passengerCapability = aPassengerCapability;
		return this;
	}
	
	public VehicleDataBuilder withCity(City aCity) {
		city = aCity;
		return this;
	}

	public VehicleDataBuilder withVehicleDescription(String aDescription) {
		vehicleDescription = aDescription;
		return this;
	}
	
	public VehicleDataBuilder withPhone(Phone aPhone) {
		contactPhone = aPhone;
		return this;
	}
	
	public VehicleDataBuilder withPhotos(List<Photo> aPhotosCollection) {
		photos = aPhotosCollection;
		return this;
	}
}
