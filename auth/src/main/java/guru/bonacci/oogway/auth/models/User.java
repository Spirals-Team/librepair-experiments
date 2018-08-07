package guru.bonacci.oogway.auth.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import guru.bonacci.oogway.shareddomain.UserInfo;

@Entity
public class User implements UserDetails, UserInfo {

	private static final long serialVersionUID = 1270982218058894480L;

	@Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    
	@JsonIgnore
    @Column(nullable = false)
    private String password;

    @Transient
    private String encryptedPassword;

    @Column(unique = true, nullable =false)
    private String apiKey;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public String getPassword() {
        return password;
    }

    @JsonProperty
	public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    @Override
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @JsonIgnore
	@Override
	public List<GrantedAuthority> getAuthorities() {
		return null;
	}

    @JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

    @JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

    @JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

    @JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
}


