package com.meteoradesigner.util.matcher;

import com.meteoradesigner.model.Role;
import com.meteoradesigner.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This @code{GenericMatcherTest} class declares tests for @code{GenericMatcher} class.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GenericMatcherTest {

    /**
     * Tests the case when two objects to check are equal.
     */
    @Test
    public void whenMatches_thenIsMatches() {
        boolean equalsWithoutId = GenericMatcher.isEqualsWithoutId(
                new User(3,
                         "userName",
                         "userEmail",
                         "userPassword",
                         Role.ROLE_USER),
                new User(3,
                         "userName",
                         "userEmail",
                         "userPassword",
                         Role.ROLE_USER)
        );

        assertTrue(equalsWithoutId);
    }

    /**
     * Tests the case when two objects to check are equal (except @code{id} field).
     */
    @Test
    public void whenMatchesWithoutId_thenIsMatches() {
        boolean equalsWithoutId = GenericMatcher.isEqualsWithoutId(
                new User(1,
                         "userName",
                         "userEmail",
                         "userPassword",
                         Role.ROLE_USER),
                new User(3,
                         "userName",
                         "userEmail",
                         "userPassword",
                         Role.ROLE_USER)
        );

        assertTrue(equalsWithoutId);
    }

    /**
     * Tests the case when two objects to check are not equal.
     */
    @Test
    public void whenNotMatches_thenIsNotMatches() {
        boolean equalsWithoutId = GenericMatcher.isEqualsWithoutId(
                new User(1,
                         "userName",
                         "userEmail",
                         "userPassword",
                         Role.ROLE_USER),
                new User(3,
                         "userNameNotMatches",
                         "userEmail",
                         "userPassword",
                         Role.ROLE_USER)
        );

        assertFalse(equalsWithoutId);
    }
}