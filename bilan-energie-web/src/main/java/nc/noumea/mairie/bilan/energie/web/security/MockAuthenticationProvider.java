package nc.noumea.mairie.bilan.energie.web.security;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Implémentation de AuthenticationProvider
 * 
 * @author David Alexis
 *
 */
public class MockAuthenticationProvider implements AuthenticationProvider {

	private String username = null;

	private String password = null;

	private String roles = null;

	/**
	 * Override la méthode authenticate
	 * 
	 * @param authentication Authentication
	 * @throws AuthenticationException Exception d'authentification
	 */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		if (this.username != null && !username.equals(this.username))
			throw new BadCredentialsException("Login incorrecte");

		if (this.password != null && !password.equals(this.password))
			throw new BadCredentialsException("Mot de passe incorrecte");

		ArrayList<Role> authorities = new ArrayList<Role>();
		if (this.roles != null)
			for (String item : this.roles.split(","))
				authorities.add(new Role(item));

		return new UsernamePasswordAuthenticationToken(username, password,
				authorities);
	}

	/**
	 * Override de la méthode supports
	 * 
	 * @param arg0 arg0
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	/**
	 * get Username
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * set Username
	 * 
	 * @param username Username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * get Password
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * set Password
	 * 
	 * @param password Password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * get Roles
	 * 
	 * @return roles
	 */
	public String getRoles() {
		return roles;
	}

	/**
	 * set Roles
	 * 
	 * @param roles Roles to set
	 */
	public void setRoles(String roles) {
		this.roles = roles;
	}

}
