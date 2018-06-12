package com.meteoradesigner.repository.datajpauserrepository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This class @code{DataJpaUserRepositoryAllTest} runs all tests for @code{DataJpaUserRepository}.
 */
//TODO documentation.
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataJpaUserRepositoryCrudTest.class,
        DataJpaUserRepositoryCustomTest.class
})
public class DataJpaUserRepositoryAllTest {
}
