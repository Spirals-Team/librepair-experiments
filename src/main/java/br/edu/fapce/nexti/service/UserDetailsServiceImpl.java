//package br.edu.fapce.nexti.service;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import br.edu.fapce.nexti.model.Biblioteca;
//import br.edu.fapce.nexti.model.Usuario;
//import br.edu.fapce.nexti.repository.UsuarioRepository;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//	
//	@Autowired
//	private UsuarioRepository usuarioRepository;
//	
//	@Override
//	@Transactional(readOnly = true)
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
//		
//		Usuario usuario = usuarioRepository.findByEmail(email);
//		
//		Set<GrantedAuthority> acessoAutorizado = new HashSet<>();
//		
//		for(Biblioteca biblioteca: usuario.getBibliotecas()){
//			acessoAutorizado.add(new SimpleGrantedAuthority(biblioteca.getNome()));
//			}
//		
//		 return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getSenha(), acessoAutorizado);
//		}
//
//	
//}
