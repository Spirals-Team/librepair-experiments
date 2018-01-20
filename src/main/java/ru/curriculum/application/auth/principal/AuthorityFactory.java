package ru.curriculum.application.auth.principal;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.curriculum.domain.admin.user.entity.Role;

@Component
public class AuthorityFactory {
    private final String AUTHORITY_ROLE_PREFIX = "ROLE_";

    public GrantedAuthority create(Role role) {
        String roleAsAuthorityFormat =
                AUTHORITY_ROLE_PREFIX.concat(role.code().toString().toUpperCase());

        return new SimpleGrantedAuthority(roleAsAuthorityFormat);
    }
}
