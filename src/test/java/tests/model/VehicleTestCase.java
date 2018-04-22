package tests.model;

import static org.junit.Assert.*;
import org.junit.Test;
import builders.VehicleBuilder;
import model.Vehicle;
import model.exceptions.DescriptionTooLongException;
import model.exceptions.DescriptionTooShortException;
import model.exceptions.InvalidCapacityException;
import model.exceptions.NoVehicleTypeException;

public class VehicleTestCase {

	@Test
	public void shouldBuildAStandardVehicle() {
		Vehicle vehicle= VehicleBuilder.aVehicle().build();
		assertNotNull(vehicle);
	}
	
	@Test(expected=DescriptionTooShortException.class)
	public void shouldThrowAExceptionWhenTheDescriptionIsShort(){
		VehicleBuilder
				.aVehicle()
				.withDescription("Es un auto lindo")
				.build();
	}

	@Test(expected=DescriptionTooLongException.class)
	public void shouldThrowAExceptionWhenTheDescriptionIsLong(){
		VehicleBuilder
				.aVehicle()
				.withDescription("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
					+ "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890")
				.build();
	}
	
	@Test(expected=InvalidCapacityException.class)
	public void shouldThrowAExceptionWhenTheCapacityIsZero(){
		VehicleBuilder
				.aVehicle()
				.withCapacity(0)
				.build();
	}

	@Test(expected=NoVehicleTypeException.class)
	public void shouldThrowAExceptionWhenNoType(){
		VehicleBuilder
			.aVehicle()
			.withType(null)
			.build();
	}
}
