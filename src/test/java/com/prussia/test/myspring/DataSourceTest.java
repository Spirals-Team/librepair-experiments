package com.prussia.test.myspring;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.prussia.play.spring.Application;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Slf4j
public class DataSourceTest {
	
	@Autowired
	DataSource datasource;

	@Test
	public void testTemplate(){
		org.apache.tomcat.jdbc.pool.DataSource bds = (org.apache.tomcat.jdbc.pool.DataSource)datasource;
		log.warn("Username=", bds.getUsername());
		log.warn("URL=", bds.getUrl());
		log.warn("DriverClassName=", bds.getDriverClassName());
	}
}
