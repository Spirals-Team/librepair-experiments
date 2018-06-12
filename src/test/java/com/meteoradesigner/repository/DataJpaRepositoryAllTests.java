package com.meteoradesigner.repository;

import com.meteoradesigner.repository.datajpataskcontextrepository.DataJpaTaskContextRepositoryAllTest;
import com.meteoradesigner.repository.datajpataskportfoliorepository.DataJpaTaskPortfolioRepositoryAllTest;
import com.meteoradesigner.repository.datajpataskrepository.DataJpaTaskRepositoryAllTest;
import com.meteoradesigner.repository.datajpauserrepository.DataJpaUserRepositoryAllTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//TODO documentation.
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataJpaUserRepositoryAllTest.class,
        DataJpaTaskContextRepositoryAllTest.class,
        DataJpaTaskPortfolioRepositoryAllTest.class,
        DataJpaTaskRepositoryAllTest.class
})
public class DataJpaRepositoryAllTests {
}