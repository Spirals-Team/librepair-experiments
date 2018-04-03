package com.d4dl.hellofib.persistence;

import com.d4dl.hellofib.greeting.Greeting;
import com.d4dl.hellofib.greeting.GreetingRepository;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the hello world jpa tier
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class GreetingJPATests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GreetingRepository helloWorldRepository;


    @Test
    public void whenAGreetingIsAddedItCanBeFoundByName() {
        Greeting helloMiddleEarth = new Greeting("Joshua", "Earth");
        helloMiddleEarth = entityManager.persist(helloMiddleEarth);
        assertThat(helloMiddleEarth.getId()).isGreaterThan(0);
        entityManager.flush();

        List<Greeting> greetings = helloWorldRepository.findByGreeterName("Joshua");
        assertThat(greetings.size()).isEqualTo(1);
        assertThat(greetings.get(0).getGreeterName()).isEqualTo("Joshua");
        assertThat(greetings.get(0).getWorldName()).isEqualTo("Earth");

    }

    @Test
    public void whenAGreetingIsAddedItsAttributesAreCorrect() {
        Greeting helloMiddleEarth = new Greeting("Joshua", "Earth");
        helloMiddleEarth = entityManager.persist(helloMiddleEarth);
        assertThat(helloMiddleEarth.getId()).isGreaterThan(0);
        entityManager.flush();

        Iterator<Greeting> iterator = helloWorldRepository.findAll().iterator();
        assertThat(iterator.hasNext()).isTrue();
        Greeting greeting = iterator.next();
        assertThat(greeting.getGreeterName()).isEqualTo("Joshua");
        assertThat(greeting.getWorldName()).isEqualTo("Earth");
    }

    @Test
    public void whenGreetingsAreAddedTheyAreAllFindable() {
        Greeting helloMiddleEarth = new Greeting("Joshua", "Earth");
        helloMiddleEarth = entityManager.persist(helloMiddleEarth);
        assertThat(helloMiddleEarth.getId()).isGreaterThan(0);
        entityManager.flush();

        Greeting helloMars = new Greeting("Frodo", "Middle Earth");
        entityManager.persist(helloMars);
        entityManager.flush();

        Iterator iterator = helloWorldRepository.findAll().iterator();
        ArrayList greetings= Lists.newArrayList(iterator);

        assertThat(greetings.size()).isEqualTo(2);
    }

}
