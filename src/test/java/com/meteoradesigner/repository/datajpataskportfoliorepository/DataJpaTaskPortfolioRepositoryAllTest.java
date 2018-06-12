package com.meteoradesigner.repository.datajpataskportfoliorepository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//TODO documentation
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataJpaTaskPortfolioRepositoryCrudTest.class,
        DataJpaTaskPortfolioRepositoryCustomTest.class
})
public class DataJpaTaskPortfolioRepositoryAllTest {
}
