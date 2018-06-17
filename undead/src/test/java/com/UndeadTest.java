package com;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import com.undeadcrud.domain.Undead;
import com.undeadcrud.repository.UndeadRepository;
import com.undeadcrud.repository.UndeadRepositoryFactory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

public class UndeadTest {

    private Undead undead;

    @Before
    public void buildUndead() {
        undead = new Undead();
    }

    @Test
    public void checkIfUndeadExists() {
        assertNotNull(undead);
    }


}