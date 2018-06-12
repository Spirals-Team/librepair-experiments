package com.meteoradesigner.repository.datajpataskrepository;

import com.meteoradesigner.repository.DataJpaTaskRepository;
import com.meteoradesigner.repository.config.DataJpaRepositoryTestConfiguration;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class @code{DataJpaTaskRepositoryCustomTest} runs custom tests for @code{DataJpaTaskRepository}'s custom
 * methods.
 */
@Ignore
//TODO documentation.
public class DataJpaTaskRepositoryCustomTest extends DataJpaRepositoryTestConfiguration {

    @Autowired
    private DataJpaTaskRepository dataJpaTaskRepository;

}