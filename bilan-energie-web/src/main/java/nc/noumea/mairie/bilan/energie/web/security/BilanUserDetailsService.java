package nc.noumea.mairie.bilan.energie.web.security;

import nc.noumea.mairie.bilan.energie.contract.dto.Utilisateur;
import nc.noumea.mairie.bilan.energie.contract.service.UtilisateurService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Service BilanUserDetails 
 * 
 * @author David Alexis
 *
 */
public class BilanUserDetailsService implements UserDetailsService {
	
	/** Service des utilisateurs */
	@Autowired
	private UtilisateurService utilisateurService;

	/**
	 * Chargement de l'utilisateur par userName
	 * 
	 * @param username Utilisateur à charger
	 * @throws UsernameNotFoundException Exception Utilisateur non trouvé
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		Utilisateur user = null;
		try {
			user = utilisateurService.getUtilisateurByLogin(username);
		} catch (TechnicalException | BusinessException e) {
			
		}
		if (user == null)
			throw new UsernameNotFoundException(username);
		
		
		DefaultUserDetail detail = new DefaultUserDetail();
		boolean nonExpire = false;
		
		if (user.getDerniereConnexion() != null)
			nonExpire = true;
		
		detail.setAccountNonExpired(nonExpire);
		detail.setAccountNonLocked(nonExpire);
		detail.setCredentialsNonExpired(nonExpire);
		detail.setEnabled(false);
		
		detail.setUsername(user.getLogin());

		return detail;
	}

}
