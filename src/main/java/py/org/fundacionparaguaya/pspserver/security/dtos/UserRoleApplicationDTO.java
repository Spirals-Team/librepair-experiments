package py.org.fundacionparaguaya.pspserver.security.dtos;

import com.google.common.base.MoreObjects;
import py.org.fundacionparaguaya.pspserver.security.constants.Role;

public class UserRoleApplicationDTO {

    private String username;

    private String email;

    private String pass;

    private Role role;

    private Long application;

    private Long organization;

    public UserRoleApplicationDTO() {
    }

    private UserRoleApplicationDTO(String username, String email, String pass, Role role, Long application, Long organization) {
        this.username = username;
        this.email = email;
        this.pass = pass;
        this.role = role;
        this.application = application;
        this.organization = organization;
    }

    public static class Builder {
        private String username;
        private String email;
        private String pass;
        private Role role;
        private Long application;
        private Long organization;

        public UserRoleApplicationDTO.Builder username(String username) {
            this.username = username;
            return this;
        }

        public UserRoleApplicationDTO.Builder email(String email) {
            this.email = email;
            return this;
        }

        public UserRoleApplicationDTO.Builder pass(String pass) {
            this.pass = pass;
            return this;
        }

        public UserRoleApplicationDTO.Builder role(Role role) {
            this.role = role;
            return this;
        }

        public UserRoleApplicationDTO.Builder application(Long application) {
            this.application = application;
            return this;
        }

        public UserRoleApplicationDTO.Builder organization(Long organization) {
            this.organization = organization;
            return this;
        }

        public UserRoleApplicationDTO build() {
            return new UserRoleApplicationDTO(username, email, pass, role, application, organization);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getApplication() {
        return application;
    }

    public void setApplication(Long application) {
        this.application = application;
    }

    public Long getOrganization() {
        return organization;
    }

    public void setOrganization(Long organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("email", email)
                .add("pass", pass)
                .add("role", role)
                .add("application", application)
                .add("organization", organization)
                .toString();
    }

}
