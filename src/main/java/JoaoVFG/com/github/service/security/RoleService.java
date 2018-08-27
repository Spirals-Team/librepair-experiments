package JoaoVFG.com.github.service.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.entity.security.Role;
import JoaoVFG.com.github.repositories.security.RoleRepository;
import JoaoVFG.com.github.services.exception.ObjectNotFoundException;

@Service
public class RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	public List<Role> findAll(){
		return roleRepository.findAll();
	}
	
	public Role findById(Integer id){
		Optional<Role> role = Optional.ofNullable(roleRepository.buscaPorId(id));
		return role.orElseThrow(() -> new ObjectNotFoundException(
				"Autorização não encontrado! Id: " + id + ". Tipo: " + Role.class.getName()));
	}
	
	
}
