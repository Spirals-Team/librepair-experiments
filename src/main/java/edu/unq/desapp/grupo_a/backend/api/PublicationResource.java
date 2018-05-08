package edu.unq.desapp.grupo_a.backend.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import edu.unq.desapp.grupo_a.backend.dto.PublicationDto;
import edu.unq.desapp.grupo_a.backend.dto.PublicationFilterDto;

public interface PublicationResource {

	@GET
	@Path("/publications-vehicle/{id}")
	public List<PublicationDto> getPublicationVehicles(@PathParam("id")  Long id);
	
	@GET
	@Path("/search-publications")
	public List<PublicationDto> searchPublication(PublicationFilterDto publicationFilterDto);
	
}
