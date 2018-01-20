package ru.curriculum.domain.admin.application;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import ru.curriculum.application.auth.principal.AuthorityFactory;
import ru.curriculum.domain.admin.user.entity.Role;

import static org.junit.Assert.*;

public class AuthorityFactoryTest {

    @Test
    public void createAuthorityFactory_mustBeCreateCorrectly() {
        new AuthorityFactory();
    }

    @Test
    public void createAuthorityFromSomeRole_mustBeCreateCorrectly() {
        Role test = new Role("test", "Тестовая роль");
        GrantedAuthority authority = new AuthorityFactory().create(test);

        assertEquals("ROLE_TEST", authority.getAuthority().toString());
    }
}
