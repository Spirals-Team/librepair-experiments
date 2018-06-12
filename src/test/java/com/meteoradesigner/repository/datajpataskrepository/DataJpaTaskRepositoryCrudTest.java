package com.meteoradesigner.repository.datajpataskrepository;

import com.meteoradesigner.model.Task;
import com.meteoradesigner.repository.DataJpaTaskRepository;
import com.meteoradesigner.repository.GenericDataJpaRepositoryCrudTest;
import data.DataJpaTaskRepositoryTestData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

//TODO documentation.
public class DataJpaTaskRepositoryCrudTest extends
        GenericDataJpaRepositoryCrudTest<Task, Integer> {

    @Autowired
    DataJpaTaskRepository taskRepository;

    @Before
    public void initialize() {
        super.repository = taskRepository;
        this.testData = new DataJpaTaskRepositoryTestData();
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