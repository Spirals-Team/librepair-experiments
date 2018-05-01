package uo.asw.configuration;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.model.Operator;
import uo.asw.dbManagement.repositories.OperatorsRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private OperatorsRepository operatorsRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
    	    		Operator operator = operatorsRepository.findByIdentifier(identifier);
    	    		
      	if(operator == null)
    	    	throw new UsernameNotFoundException("Operator con identifier: " + identifier + " no encontrado.");
    	    	
    	Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    	grantedAuthorities.add(new SimpleGrantedAuthority(operator.getRole()));
 		
		return new org.springframework.security.core.userdetails.User(operator.getIdentifier(), operator.getPassword(),
				grantedAuthorities);
		
	}
    
//    @Override
//	public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException{
//		Operator operator = operatorsRepository.findByIdentifier(identifier);
//		
//		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//		//grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ESTUDIANTE"));
//		
//		if(operator!=null) {
//			
//			grantedAuthorities.add(new SimpleGrantedAuthority(operator.getRole()));
//			
//			return new org.springframework.security.core.userdetails.User(
//					operator.getIdentifier(), operator.getPassword(), grantedAuthorities);
//		}
//		
//		return new org.springframework.security.core.userdetails.User(
//				"noUsername", "noPassword", grantedAuthorities);
//		
//	}
}
