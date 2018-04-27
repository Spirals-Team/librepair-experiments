package nc.noumea.mairie.bilan.energie.web.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Implémentation de UserDetails
 * 
 * @author David Alexis
 *
 */
public class DefaultUserDetail implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Collection<? extends GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	private String password = null;
	private String username = null;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

	/**
	 * get Authorities 
	 * 
	 * @return Collection de GrantedAuthority
	 */
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * set Authorities
	 * 
	 * @param authorities Collection de GrantedAuthority
	 */
	public void setAuthorities(
			Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
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
	 * Account non expired ?
	 * 
	 * @return booléen
	 */
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	/**
	 * set AccountNonExpired
	 * 
	 * @param accountNonExpired booléen
	 */
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * Account non locked ?
	 * 
	 * @return booléen
	 */
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	/**
	 * setAccountNonLocked
	 * 
	 * @param accountNonLocked booléen
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	/**
	 * Credentials non expired
	 * 
	 * @return booléen
	 */
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * setCredentialsNonExpired
	 *  
	 * @param credentialsNonExpired booléen
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * Enabled ?
	 * 
	 * @return booléen
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * set Enabled
	 * 
	 * @param enabled booléen
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
