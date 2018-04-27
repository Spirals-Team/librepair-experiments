package nc.noumea.mairie.bilan.energie.web.security;

import java.util.ArrayList;

import nc.noumea.mairie.bilan.energie.contract.dto.Utilisateur;
import nc.noumea.mairie.bilan.energie.contract.service.UtilisateurService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Implémentation de AuthenticationProvider pour l'authentification à l'application
 * 
 * @author David Alexis
 *
 */
public class BilanAuthenticationProvider implements AuthenticationProvider {
	
	/** Message par défaut */
	private static final String DEFAULT_MESSAGE = "Connection à l'application Bilan Energie impossible";
	
	/** Authentication provider */
	private AuthenticationProvider provider; 
	
	/** Message d'erreur si échec d'authentification par le provider */
	private String messageProvider = DEFAULT_MESSAGE;
	
	/** Message d'erreur si échec d'authentification à l'application */
	private String messageBilan = DEFAULT_MESSAGE;
	
	/** Service utilisateur */
	@Autowired
	private UtilisateurService utilisateurService;

	/**
	 * Override la méthode authenticate
	 * 
	 * @param authentication Authentication
	 * @throws AuthenticationException Exception d'authentification
	 */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		Authentication authenticationResult = null;
		
		if (provider != null)
			try{
				authenticationResult = provider.authenticate(authentication);
			}
			catch (BadCredentialsException e) {
				throw new BadCredentialsException(messageProvider);
			}
		
		String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        ArrayList<Role> authorities = new ArrayList<Role>();
        
        Utilisateur util = null;
        try {
        	util = utilisateurService.getUtilisateurByLogin(username);
		} catch (TechnicalException | BusinessException e) {
		}
        if (util == null)
        	throw new BadCredentialsException(messageBilan);
   
		
		return (provider == null) ? new UsernamePasswordAuthenticationToken(username, password, authorities) : authenticationResult;
	}

	/**
	 * Override de la méthode supports
	 * 
	 * @param authentication Authentication
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return (provider == null) ? true : provider.supports(authentication);
	}

	/**
	 * get Provider
	 * 
	 * @return provider
	 */
	public AuthenticationProvider getProvider() {
		return provider;
	}

	/**
	 * set Provider
	 * 
	 * @param provider Provider to set
	 */
	public void setProvider(AuthenticationProvider provider) {
		this.provider = provider;
	}

	/**
	 * get MessageProvider
	 * 
	 * @return message
	 */
	public String getMessageProvider() {
		return messageProvider;
	}

	/**
	 * set MessageProvider
	 * 
	 * @param messageProvider message to set
	 */
	public void setMessageProvider(String messageProvider) {
		this.messageProvider = messageProvider;
	}

	/**
	 * get MessageBilan
	 * 
	 * @return message
	 */
	public String getMessageBilan() {
		return messageBilan;
	}

	/**
	 * set MessageBilan
	 * 
	 * @param messageBilan message to set
	 */
	public void setMessageBilan(String messageBilan) {
		this.messageBilan = messageBilan;
	}

}
