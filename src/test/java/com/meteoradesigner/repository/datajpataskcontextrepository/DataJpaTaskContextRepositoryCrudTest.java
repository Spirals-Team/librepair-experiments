package com.meteoradesigner.repository.datajpataskcontextrepository;

import com.meteoradesigner.model.TaskContext;
import com.meteoradesigner.repository.DataJpaTaskContextRepository;
import com.meteoradesigner.repository.GenericDataJpaRepositoryCrudTest;
import data.DataJpaTaskContextRepositoryTestData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

//TODO documentation.
public class DataJpaTaskContextRepositoryCrudTest extends
        GenericDataJpaRepositoryCrudTest<TaskContext, Integer> {

    @Autowired
    DataJpaTaskContextRepository taskContextRepository;

    @Before
    public void initialize() {
        super.repository = taskContextRepository;
        this.testData = new DataJpaTaskContextRepositoryTestData();
        setTestCommon();
    }

    /**
     * {@inheritDoc}
     */
    @Test
    @Override
    public void saveOneTest() {
        super.saveOneTest();
    }

    /**
     * {@inheritDoc}
     */
    @Test
    @Override
    public void findOneTest() {
        super.findOneTest();
    }

    /**
     * {@inheritDoc}
     */
    @Test
    @Override
    public void deleteOneTest() {
        super.deleteOneTest();
    }

    /**
     * {@inheritDoc}
     */
    @Test
    @Override
    public void findAllTest() {
        super.findAllTest();
    }
}