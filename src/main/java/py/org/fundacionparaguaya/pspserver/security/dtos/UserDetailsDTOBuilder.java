package py.org.fundacionparaguaya.pspserver.security.dtos;

import org.springframework.security.core.GrantedAuthority;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;

import java.util.List;

public class UserDetailsDTOBuilder {
    private String username;
    private String password;
    private boolean enabled;
    private List<GrantedAuthority> grantedAuthorities;
    private OrganizationDTO organization;
    private ApplicationDTO application;

    public UserDetailsDTOBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserDetailsDTOBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserDetailsDTOBuilder enabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public UserDetailsDTOBuilder grantedAuthorities(List<GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
        return this;
    }

    public UserDetailsDTOBuilder organization(OrganizationDTO organization) {
        this.organization = organization;
        return this;
    }

    public UserDetailsDTOBuilder application(ApplicationDTO application) {
        this.application = application;
        return this;
    }

    public UserDetailsDTO build() {
        return new UserDetailsDTO(username, password, enabled, grantedAuthorities, organization, application);
    }
}