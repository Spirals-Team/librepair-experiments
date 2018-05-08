package edu.unq.desapp.grupo_a.backend.webservice;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unq.desapp.grupo_a.backend.dto.UserDto;
import edu.unq.desapp.grupo_a.backend.model.User;
import edu.unq.desapp.grupo_a.backend.service.GenericService;
import edu.unq.desapp.grupo_a.backend.service.UserServiceImpl;

@Service
@Produces("application/json")
@Consumes("application/json")
@Path("/users")
public class UserRest extends GenericRest<User> {

	@Autowired
	private UserServiceImpl userService;

	@Override
	public GenericService<User> getService() {
		return userService;
	}

	@POST
	@Path("/saveuser")
	public Response saveuser(@Context HttpServletRequest request, UserDto dto) {
		try {
			//ToDo... implement
			//userService.saveuser(dto);
			return this.getResponseGenerator().buildSuccessResponse("Success");
		} catch (Exception e) {
			return this.getResponseGenerator().buildErrorResponse(new RuntimeException(e.getMessage()));
		}
	}
	
	@GET
	@Path("/saludo")
	public Response saludo(@Context HttpServletRequest request) {
		try {
			UserDto dto = new UserDto();
			dto.name ="Saludo";
			return this.getResponseGenerator().buildResponse(dto, Status.ACCEPTED);
			
		} catch (Exception e) {
			return this.getResponseGenerator().buildErrorResponse(new RuntimeException(e.getMessage()));
		}
	}

}