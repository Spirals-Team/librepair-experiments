package edu.unq.desapp.grupo_a.backend.webservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unq.desapp.grupo_a.backend.api.UserResource;
import edu.unq.desapp.grupo_a.backend.dao.UserDao;
import edu.unq.desapp.grupo_a.backend.dto.UserDto;
import edu.unq.desapp.grupo_a.backend.model.User;
import edu.unq.desapp.grupo_a.backend.service.GenericService;
import edu.unq.desapp.grupo_a.backend.service.UserServiceImpl;

@Service
@Produces("application/json")
@Consumes("application/json")
@Path("/users")
public class UserResourceImpl extends GenericRest<User>  implements UserResource{
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		try {
			
			//Mapper

		}catch(Exception e) {
			
		}
		
		
		return null;
	}

	@Override
	public UserDto update(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto offerVehicle(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDto> searchUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericService<User> getService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto getUser(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto deleteUser(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserServiceImpl getUserService() {
		return userService;
	}

	public UserDao getUserDao() {
		return userDao;
	}
	
}
