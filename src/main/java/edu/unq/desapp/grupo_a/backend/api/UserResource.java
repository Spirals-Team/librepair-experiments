package edu.unq.desapp.grupo_a.backend.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import edu.unq.desapp.grupo_a.backend.dto.UserDto;


@Path("/users")
@Consumes({"application/xml", "application/json"})
@Produces({"application/xml", "application/json"})
public interface UserResource {
	
	@POST
	@Path("/add-vehicle")
	public UserDto createUser(UserDto userDto);
	
	@GET
	@Path("/get-user/{id}")
	public UserDto getUser(@PathParam("id")  Long id);
	
	@DELETE
	@Path("/delete-user/{id}")
	public UserDto deleteUser(@PathParam("id")  Long id);

	@PUT
	@Path("/update/{id}")
	public UserDto update(@PathParam("id") final Long id);
	
	@POST
	@Path("/offer-vehicle/{id}")
	public UserDto offerVehicle (@PathParam("id") final Long id);
	
	@GET
	@Path("all-users")
	public List<UserDto> searchUsers();
	

}
