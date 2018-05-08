package edu.unq.desapp.grupo_a.backend.service;

import java.util.List;

import edu.unq.desapp.grupo_a.backend.dto.VehicleDto;
import edu.unq.desapp.grupo_a.backend.model.User;
import edu.unq.desapp.grupo_a.backend.model.Vehicle;
import edu.unq.desapp.grupo_a.backend.model.VehicleData;
import edu.unq.desapp.grupo_a.backend.model.VehicleFilter;

public interface VehicleService {
	
	public abstract void addVehicleData(VehicleData vehicleData);
	
	public abstract void addVehicle(VehicleDto vehicleDto, User user );
	
	public abstract VehicleDto getVehicle(User user);
	
	public abstract void updateVehicle(Vehicle vehicle);
	
	public abstract void deleteVehicle(User user);

	public abstract void publishVehicle(Vehicle vehicle, User user);

	public abstract void rentVehicle(Vehicle vehicle, User user);
	
	public abstract List<Vehicle> searchVehicle(VehicleFilter vehicleFilter);

}
