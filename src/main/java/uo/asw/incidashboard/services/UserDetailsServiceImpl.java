package uo.asw.incidashboard.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.model.Usuario;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String inden) {
		Usuario usuario = usuarioService.getUsuarioByIdentificador(inden);
		
			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

			return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getContrasena(),
					grantedAuthorities);
		
		//throw new UsernameNotFoundException(inden);
	}

}
