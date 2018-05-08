package edu.unq.desapp.grupo_a.backend.model;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import edu.unq.desapp.grupo_a.backend.model.builders.VehicleBuilder;
import edu.unq.desapp.grupo_a.backend.model.builders.VehicleDataBuilder;
import edu.unq.desapp.grupo_a.backend.model.exceptions.VehicleDataException;

public class VehicleTest {
	
	@Test (expected = VehicleDataException.class)
	public void testVehicleWithNullVehicleData() {
		VehicleBuilder.aVehicle()
			.withVehicleData(null)
			.build();
	}

	@Test
	public void testVehicleWithValidVehicleData() {
		Vehicle aVehicle = VehicleBuilder.aVehicle()
			.withVehicleData(VehicleDataBuilder.aVehicleData()
					.withVehicleType(VehicleType.Moto)
					.withPassengerCapability(2)
					.withCity(new City())
					.withVehicleDescription("Moto de prueba para testVehicleWithValidVehicleData")
					.withPhone(new Phone())
					.withPhotos(new ArrayList<>())
					.build())
			.build();
		assertEquals(aVehicle.getVehicleData().getVehicleType(), VehicleType.Moto);
	}
	
	@Test (expected = VehicleDataException.class)
	public void testVehicleWithNullVehicleType() {
		VehicleBuilder.aVehicle()
			.withVehicleData(VehicleDataBuilder.aVehicleData()
					.withVehicleType(null)
					.build())
			.build();
	}
	
	@Test (expected = VehicleDataException.class)
	public void testVehicleWithZeroPassengerCapability() {
		VehicleBuilder.aVehicle()
			.withVehicleData(VehicleDataBuilder.aVehicleData()
					.withPassengerCapability(0)
					.build())
			.build();
	}
	
	@Test (expected = VehicleDataException.class)
	public void testVehicleWithNullCity() {
		VehicleBuilder.aVehicle()
			.withVehicleData(VehicleDataBuilder.aVehicleData()
					.withCity(null)
					.build())
			.build();
	}
	
	@Test (expected = VehicleDataException.class)
	public void testVehicleWithNullVehicleDescription() {
		VehicleBuilder.aVehicle()
			.withVehicleData(VehicleDataBuilder.aVehicleData()
					.withVehicleDescription(null)
					.build())
			.build();
	}
	
	@Test (expected = VehicleDataException.class)
	public void testVehicleWithEmptyVehicleDescription() {
		VehicleBuilder.aVehicle()
			.withVehicleData(VehicleDataBuilder.aVehicleData()
					.withVehicleDescription("")
					.build())
			.build();
	}
	
	@Test (expected = VehicleDataException.class)
	public void testVehicleWithBlankVehicleDescription() {
		VehicleBuilder.aVehicle()
			.withVehicleData(VehicleDataBuilder.aVehicleData()
					.withVehicleDescription("   ")
					.build())
			.build();
	}
	
	@Test (expected = VehicleDataException.class)
	public void testVehicleWithNullPhone() {
		VehicleBuilder.aVehicle()
			.withVehicleData(VehicleDataBuilder.aVehicleData()
					.withPhone(null)
					.build())
			.build();
	}
	
	@Test (expected = VehicleDataException.class)
	public void testVehicleWithNullPhotos() {
		VehicleBuilder.aVehicle()
			.withVehicleData(VehicleDataBuilder.aVehicleData()
					.withPhotos(null)
					.build())
			.build();
	}
	
	@Test (expected = VehicleDataException.class)
	public void testVehicleWithInvalidPhotos() {
		List<Photo> photos = new ArrayList<>();
		photos.add(null);
		VehicleBuilder.aVehicle()
			.withVehicleData(VehicleDataBuilder.aVehicleData()
					.withPhotos(photos)
					.build())
			.build();
	}
}
