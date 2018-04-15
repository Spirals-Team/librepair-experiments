package com.isa.instaticketapi.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.instaticketapi.domain.Authority;

import com.isa.instaticketapi.domain.EmploymentPlace;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.AuthorityRepository;

import com.isa.instaticketapi.repository.EmploymentPlaceRepository;

import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.AuthoritiesConstants;
import com.isa.instaticketapi.service.dto.account.AdminRoleDTO;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private PlaceRepository placeRepository;
	
	@Autowired
	private EmploymentPlaceRepository employmentPlaceRepository;
	

	
	
	public void setSystemAdminRole(Long id) {
		
		User user = userRepository.findOneById(id).get();
		
		Authority authoritySuperAdmin = authorityRepository.findOne(AuthoritiesConstants.SUPER_ADMIN);
	
		if(!user.getAuthorities().contains(authoritySuperAdmin)) {		
			user.getAuthorities().add(authoritySuperAdmin);
		}
		
	}
	
	
	
	public void removeRole(Long id) {
		
		User user = userRepository.findOneById(id).get();
		
		Authority authorityBasicUser = authorityRepository.findOne(AuthoritiesConstants.USER);
		
		user.getAuthorities().clear();
		user.getAuthorities().add(authorityBasicUser);
		
	}
	
	
	
	public void setPlaceAdminRole(AdminRoleDTO adminRoleDTO, Long id) throws SQLException {
		
		
		
		Place place = placeRepository.findOneById(adminRoleDTO.getId());
		
		List<User> placeAdmins = employmentPlaceRepository.findAllByPlace(place);
		
		User user = userRepository.findOneById(id).get();
		
		if(!placeAdmins.contains(user)) {
			
			EmploymentPlace ep = new EmploymentPlace(place,user);		
			ep.setCreatedBy("Dejan");			
			employmentPlaceRepository.save(ep);
			
		}
		
		
		
	}
	
	
	
	public void setFanZoneAdminRole(Long id) throws SQLException {
		
		
		
		User user = userRepository.findOneById(id).get();
		
		Authority authorityFanZone = authorityRepository.findOne(AuthoritiesConstants.FANZONE_ADMIN);
	
		if(!user.getAuthorities().contains(authorityFanZone)) {		
			user.getAuthorities().add(authorityFanZone);
		}
		
		
		
	}
	
	
	
	
	
}
