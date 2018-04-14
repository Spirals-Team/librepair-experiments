package com.prussia.play.spring.dao;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.prussia.play.spring.domain.po.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyDao {
	private static final Logger log = LoggerFactory.getLogger(MyDao.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Transactional
	public void accessRelationDBbyTemplate4mysql() {
		log.info("Creating tables");

		createTables();
//		throwMyException();

		insertData();

		findRecords();
	}

	private void throwMyException() {
		int i =0;
		if(i==0)
		throw new RuntimeException();
	}

	@Transactional
	private void findRecords() {
		log.info("Querying for customer records where first_name = 'Josh':");
		jdbcTemplate
				.query("SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
						new Object[] { "Josh" }, (rs, rowNum) -> new Customer(rs.getLong("id"),
								rs.getString("first_name"), rs.getString("last_name")))
				.forEach(customer -> log.info(customer.toString()));
	}

	@Transactional
	private void insertData() {
		// Split up the array of whole names into an array of first/last names
		List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
				.map(name -> name.split(" ")).collect(Collectors.toList());

		// Use a Java 8 stream to print out each tuple of the list
		splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

		// Uses JdbcTemplate's batchUpdate operation to bulk load data
		jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);
	}

	@Transactional
	private void createTables() {
		jdbcTemplate.execute("DROP TABLE  IF EXISTS customers");
		jdbcTemplate.execute("CREATE TABLE customers(" + "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");
	}
}
