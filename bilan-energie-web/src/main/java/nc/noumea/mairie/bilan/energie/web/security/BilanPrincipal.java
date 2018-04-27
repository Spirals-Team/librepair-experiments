package nc.noumea.mairie.bilan.energie.web.security;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Principal contenant
 * 
 * @author David ALEXIS
 * 
 */
public class BilanPrincipal implements Principal {

	/*
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String login;
		
		if (requestAttributes == null)
			login = "JOB_QUARTZ";
		else{
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    login = auth.getName();
		}


		return login;

	}
}