package edu.unq.desapp.grupo_a.backend.service;

import java.util.List;

import edu.unq.desapp.grupo_a.backend.model.User;

/**
 * 
 * Servicios para los usuarios.
 * 
 * @author Charlie Cardozo
 *
 */
public interface UserService {
	
	public abstract void createUser(User user);
	
	public abstract User getUser(User user);
	
	public abstract void deleteUser(User user);

	public abstract void update(User user);
	
	public abstract void offerVehicle (User user);
	
	public abstract List<User> searchUsers();


}
