package com.example.demo.security;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entities.Funcionario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

//import com.example.demo.security.entities.Usuario;
//import com.example.demo.security.enums.PerfilEnum;

import com.example.demo.enums.PerfilEnum;

public class JwtUserFactory {

    private JwtUserFactory() {
    }

    /**
     * Converte e gera um JwtUser com base nos dados de um funcionário.
     *
     * @param funcionario
     * @return JwtUser
     */
    public static JwtUser create(Funcionario funcionario) {
        return new JwtUser(funcionario.getId(), funcionario.getEmail(), funcionario.getSenha(),
                mapToGrantedAuthorities(funcionario.getPerfil()));
    }
//    public static JwtUser create(Usuario usuario) {
//        return new JwtUser(usuario.getId(), usuario.getEmail(), usuario.getSenha(),
//                mapToGrantedAuthorities(usuario.getPerfil()));
//    }

    /**
     * Converte o perfil do usuário para o formato utilizado pelo Spring Security.
     *
     * @param perfilEnum
     * @return List<GrantedAuthority>
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(PerfilEnum perfilEnum) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(perfilEnum.toString()));
        return authorities;
    }

}
