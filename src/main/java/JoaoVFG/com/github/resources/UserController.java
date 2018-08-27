package JoaoVFG.com.github.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import JoaoVFG.com.github.entity.security.Role;
import JoaoVFG.com.github.entity.security.User;
import JoaoVFG.com.github.service.security.RoleService;
import JoaoVFG.com.github.service.security.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;

	@RequestMapping(value = "/buscauser/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> findById(@PathVariable("id") Integer idUser) {
		User user = userService.findById(idUser);
		return ResponseEntity.ok(user);
	}

	@PreAuthorize("hasRole('ROLE_BUSCA_USERS')  or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscauser/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> findAll() {
		List<User> users = userService.findAll();
		return ResponseEntity.ok(users);
	}

	@RequestMapping(value = "/buscauser/email/{email}", method = RequestMethod.GET)
	public ResponseEntity<User> findByEmail(@PathVariable("email") String email) {
		User user = userService.findByEmail(email);
		return ResponseEntity.ok(user);
	}

	@PreAuthorize("hasRole('ROLE_BUSCA_USERS')  or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscauser/empresa/{idEmpresa}", method = RequestMethod.GET)
	public ResponseEntity<List<User>> findByEmpresa(@PathVariable("idEmpresa") Integer idEmpresa) {
		List<User> users = userService.findByEmpresa(idEmpresa);
		return ResponseEntity.ok(users);
	}
	
	@PreAuthorize("hasRole('ROLE_BUSCA_ROLES')  or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscarole", method = RequestMethod.GET)
	public ResponseEntity<List<Role>> findAllRoles(){
		List<Role> roles =  roleService.findAll();
		return ResponseEntity.ok(roles);
	}

	@RequestMapping(value = "/deleta/{idUser}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletaUser(@PathVariable("idUser") Integer idUser) {
		userService.deletaUser(idUser);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/updateuser", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@RequestBody User updateUser) {
		User user = userService.updateUser(updateUser);
		return ResponseEntity.ok(user);
	}

}
