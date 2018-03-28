package com.rexam.security.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rexam.dao.AdminRepository;
import com.rexam.dao.StudentRepository;
import com.rexam.model.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User admin = adminRepository.findByEmail(email);
        User student = studentRepository.findByEmail(email);
        User user = null;

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (admin != null) {
            grantedAuthorities.add(new SimpleGrantedAuthority("admin"));
            user = admin;
        }
        else {
            grantedAuthorities.add(new SimpleGrantedAuthority("student"));
            user = student;
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
        
    }
}
