package edu.unq.desapp.grupo_a.backend.webservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Service;

import edu.unq.desapp.grupo_a.backend.api.PublicationResource;
import edu.unq.desapp.grupo_a.backend.dto.PublicationDto;
import edu.unq.desapp.grupo_a.backend.dto.PublicationFilterDto;

@Service
@Produces("application/json")
@Consumes("application/json")
@Path("/publications")
public class PublicationResourceImpl implements PublicationResource {

	@Override
	public List<PublicationDto> getPublicationVehicles(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PublicationDto> searchPublication(PublicationFilterDto publicationFilterDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
