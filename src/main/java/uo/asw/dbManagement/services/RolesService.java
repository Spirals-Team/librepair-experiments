package uo.asw.dbManagement.services;

import org.springframework.stereotype.Service;

@Service
public class RolesService {
	
	String[] roles = {"ROLE_OPERATOR"};
		
	public String[] getRoles() {
		return roles;
	}
}
