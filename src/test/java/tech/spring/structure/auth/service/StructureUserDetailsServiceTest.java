package tech.spring.structure.auth.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StructureUserDetailsServiceTest {

    @Autowired
    private StructureUserDetailsService structureUserDetailsService;

    @Test
    public void testLoadUserByUsername() {
        UserDetails userDetails = structureUserDetailsService.loadUserByUsername("user");
        assertNotNull("Unable to find user details!", userDetails);
        assertEquals("User details had incorrect username!", "user", userDetails.getUsername());
        assertEquals("User details had incorrect number of granted authorities!", 1, userDetails.getAuthorities().size());
        assertEquals("User details had incorrect authority!", "ROLE_USER", userDetails.getAuthorities().toArray(new GrantedAuthority[userDetails.getAuthorities().size()])[0].getAuthority());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsernameNotFound() {
        structureUserDetailsService.loadUserByUsername("notfound");
    }

}
