package edu.unq.desapp.grupo_a.backend.service;

import java.util.List;

import edu.unq.desapp.grupo_a.backend.model.Publication;
import edu.unq.desapp.grupo_a.backend.model.User;

public interface PublicationService {
	
	public abstract List<Publication> getPublicationVehicles(User user);
	
	public abstract List<Publication> searchPublication(User user);

}
