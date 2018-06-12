package com.meteoradesigner.repository;

import com.meteoradesigner.HasId;
import com.meteoradesigner.repository.config.DataJpaRepositoryTestConfiguration;
import data.GenericDataJpaRepositoryTestData;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import util.GenericTestHelper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static data.DataJpaTaskRepositoryTestData.CONSTRUCTED_PROGRAMMATICALLY_TASK;

@Ignore
//TODO documentation.
public class GenericDataJpaRepositoryCrudTest<E extends HasId, ID extends Serializable> extends
        DataJpaRepositoryTestConfiguration {

    @Autowired
    private  DataJpaTaskPortfolioRepository portfolioRepository;

    @Autowired
    private DataJpaTaskRepository taskRepository;

    @Autowired
    private DataJpaTaskContextRepository taskContextRepository;

    @Autowired
    private DataJpaUserRepository userRepository;

    protected GenericAbstractCrudRepository<E, ID> repository;
    protected GenericDataJpaRepositoryTestData<E> testData;
    protected Map<String, GenericAbstractCrudRepository<?,Integer>> repositoryMap;

    protected void setTestCommon() {
        repositoryMap = new HashMap<String, GenericAbstractCrudRepository<?,Integer>>() {{
            put("DataJpaUserRepository", userRepository);
            put("DataJpaTaskContextRepository", taskContextRepository);
            put("DataJpaTaskRepository", taskRepository);
            put("DataJpaTaskPortfolioRepository", portfolioRepository);
        }};

        //constructed programmatically test data sets here
        taskRepository.saveAll(CONSTRUCTED_PROGRAMMATICALLY_TASK);
    }

    public void saveOneTest() {
        GenericTestHelper.cruTest(
                testData.getSaveOneTestData(),
                repository,
                GenericDataJpaRepositoryTestData.getSaveOneTestConsumer()
        );
    }

    public void findOneTest() {
        GenericTestHelper.cruTest(
                testData.getFindOneTestData(),
                repository,
                GenericDataJpaRepositoryTestData.getFindOneTestConsumer()
        );
    }

    public void deleteOneTest() {
        GenericTestHelper.deleteTest(
                testData.getDeleteOneTestData(),
                repositoryMap,
                GenericDataJpaRepositoryTestData.getDeleteOneTestConsumer()
        );
    }

    public void findAllTest() {
        GenericTestHelper.findAll(
                testData.getFindAllTestData(),
                repository
        );
    }
}