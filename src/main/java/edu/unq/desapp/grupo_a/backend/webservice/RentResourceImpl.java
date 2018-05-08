package edu.unq.desapp.grupo_a.backend.webservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import edu.unq.desapp.grupo_a.backend.api.RentResource;
import edu.unq.desapp.grupo_a.backend.dto.PublicationDto;
import edu.unq.desapp.grupo_a.backend.dto.RentDto;
import edu.unq.desapp.grupo_a.backend.dto.RentFilterDto;

@Service
@Produces("application/json")
@Consumes("application/json")
@Path("/rents")
public class RentResourceImpl implements RentResource{

	@Override
	public Response rentVehicle(PublicationDto publicationDto, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RentDto> searchRents(RentFilterDto rentFilterDto) {
		// TODO Auto-generated method stub
		return null;
	}
 
}
