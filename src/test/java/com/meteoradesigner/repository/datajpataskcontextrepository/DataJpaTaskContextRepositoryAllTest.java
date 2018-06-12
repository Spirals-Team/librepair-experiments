package com.meteoradesigner.repository.datajpataskcontextrepository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This class @code{DataJpaTaskRepositoryAllTest} runs all tests
 * for @code{DataJpaTaskContextRepository}.
 */
//TODO documentation.
@RunWith(Suite.class)
@Suite.SuiteClasses({
                            DataJpaTaskContextRepositoryCrudTest.class,
                            DataJpaTaskContextRepositoryCustomTest.class
                    })
public class DataJpaTaskContextRepositoryAllTest {
}
