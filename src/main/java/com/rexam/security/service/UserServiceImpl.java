package com.rexam.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rexam.dao.AdminRepository;
import com.rexam.dao.StudentRepository;
import com.rexam.model.Admin;
import com.rexam.model.User;
import com.rexam.model.Student;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    //

    @Override
    public User findByEmail(String email) {
        User admin = adminRepository.findByEmail(email);
        if (admin != null)
        	return admin;
        
        User student = studentRepository.findByEmail(email);
        return student;
    }

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		
	}
}
