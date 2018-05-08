package edu.unq.desapp.grupo_a.backend.service;

import java.util.List;

import edu.unq.desapp.grupo_a.backend.model.Publication;
import edu.unq.desapp.grupo_a.backend.model.Rent;
import edu.unq.desapp.grupo_a.backend.model.RentFilter;
import edu.unq.desapp.grupo_a.backend.model.User;

public interface RentService {
	
	public abstract void rentVehicle(Publication publication, User user);
	
	public List<Rent> searchRents(RentFilter rentFilter, User user);

}
