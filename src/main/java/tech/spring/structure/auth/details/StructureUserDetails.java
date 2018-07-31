package tech.spring.structure.auth.details;

import static tech.spring.structure.StructureConstants.PASSWORD_DURATION_IN_DAYS;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tech.spring.structure.auth.model.User;

public class StructureUserDetails extends User implements UserDetails {

    private static final long serialVersionUID = 6674712962625174202L;

    public StructureUserDetails(User user) {
        super(user);
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(super.getRole().toString());
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return isActive();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        Calendar now = Calendar.getInstance();
        return ChronoUnit.DAYS.between(getTimestamp().toInstant(), now.toInstant()) < PASSWORD_DURATION_IN_DAYS;
    }

}
