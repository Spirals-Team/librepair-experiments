package tech.spring.structure.auth.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class RoleTest {

    @Test
    public void testGetValue() {
        assertEquals("User role has incorrect value!", "User", Role.ROLE_USER.getValue());
        assertEquals("Admin role has incorrect value!", "Administrator", Role.ROLE_ADMIN.getValue());
        assertEquals("Super admin role has incorrect value!", "Super Administrator", Role.ROLE_SUPER_ADMIN.getValue());
    }

    @Test
    public void testWithValue() {
        assertEquals("Could not create user role with value!", Role.ROLE_USER, Role.withValue("User"));
        assertEquals("Could not create admin role with value!", Role.ROLE_ADMIN, Role.withValue("Administrator"));
        assertEquals("Could not create super admin role with value!", Role.ROLE_SUPER_ADMIN, Role.withValue("Super Administrator"));
    }

}
