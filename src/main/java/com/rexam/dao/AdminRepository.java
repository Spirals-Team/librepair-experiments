package com.rexam.dao;

import org.springframework.data.repository.CrudRepository;

import com.rexam.model.Admin;

public interface AdminRepository extends CrudRepository<Admin, String>{
	Admin findByEmail(String email);
}
