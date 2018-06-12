package com.meteoradesigner.repository.config;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This class @code{DataJpaRepositoryTestConfiguration} runs parametrized tests to
 * saveOneTest @code{DataJpaTaskContextRepository}'s save one method.
 */
//TODO documentation.
//@ContextConfiguration(classes = AppConfig.class)
//@WebAppConfiguration
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@RunWith(SpringRunner.class)

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class DataJpaRepositoryTestConfiguration {
}