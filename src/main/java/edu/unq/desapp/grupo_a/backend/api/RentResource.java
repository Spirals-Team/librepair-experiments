package edu.unq.desapp.grupo_a.backend.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import edu.unq.desapp.grupo_a.backend.dto.PublicationDto;
import edu.unq.desapp.grupo_a.backend.dto.RentDto;
import edu.unq.desapp.grupo_a.backend.dto.RentFilterDto;

public interface RentResource {
	
	@POST
	@Path("/rent-vehicle/{id}")
	public Response rentVehicle(PublicationDto publicationDto, @PathParam("id")  Long id);
	
	@GET
	@Path("/search-rents")
	public List<RentDto> searchRents(RentFilterDto rentFilterDto); 

}
