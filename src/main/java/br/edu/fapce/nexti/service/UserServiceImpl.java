//package br.edu.fapce.nexti.service;
//
//import java.util.HashSet;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import br.edu.fapce.nexti.model.Usuario;
//import br.edu.fapce.nexti.repository.BibliotecaRepository;
//import br.edu.fapce.nexti.repository.UsuarioRepository;
//
//@Service
//public class UserServiceImpl implements UserService{
//
//	@Autowired
//	private UsuarioRepository usuarioRepository;
//	
//	@Autowired
//	private BibliotecaRepository bibliotecaRepository;
//	
//	@Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//	
//	@Override
//	public void save(Usuario usuario) {
//		usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
//		usuario.setBibliotecas(new HashSet<>(bibliotecaRepository.findAll()));
//		usuarioRepository.save(usuario);
//	}
//	
//	@Override
//	public Usuario findByEmail(String email) {
//		return usuarioRepository.findByEmail(email);
//	}
//	
//	@Override
//	public Iterable<Usuario> findAll(){
//		return this.findAll();
//	}
//}
