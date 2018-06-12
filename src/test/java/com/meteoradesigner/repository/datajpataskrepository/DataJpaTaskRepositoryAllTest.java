package com.meteoradesigner.repository.datajpataskrepository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This class @code{DataJpaTaskRepositoryAllTest} runs all tests for @code{DataJpaTaskRepository}.
 */
//TODO documentation.
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataJpaTaskRepositoryCrudTest.class,
        DataJpaTaskRepositoryCustomTest.class
})
public class DataJpaTaskRepositoryAllTest {
}
