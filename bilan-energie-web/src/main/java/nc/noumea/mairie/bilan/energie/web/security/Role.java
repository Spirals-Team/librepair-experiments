package nc.noumea.mairie.bilan.energie.web.security;

import org.springframework.security.core.GrantedAuthority;

/**
 *  Implémentation de GrantedAuthority
 * 
 * @author David Alexis
 *
 */
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	private String authority;

	/**
	 * Constructeur Role
	 * 
	 * @param authority Authority to set
	 */
	public Role(String authority){
		this.authority = authority;
	}

	/**
	 * Constructeur Role
	 * 
	 */
	public Role() {
	}

	/**
	 * get Authority
	 * 
	 * @return authority
	 */
	@Override
	public String getAuthority() {
		return authority;
	}

	/**
	 * set Authority
	 * 
	 * @param authority Authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
