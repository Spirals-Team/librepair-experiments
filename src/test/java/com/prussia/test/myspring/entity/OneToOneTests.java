package com.prussia.test.myspring.entity;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.prussia.play.spring.Application;
import com.prussia.play.spring.domain.po.Customer;
import com.prussia.play.spring.repository.CustomerRepostory;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Slf4j
//@Transactional
public class OneToOneTests {
	@Autowired
	private CustomerRepostory customerRepo;
	private Customer expectCustomer;

	@Before
	public void init() {
		expectCustomer = new Customer();
		expectCustomer.setId(1L);
		expectCustomer.setFirstName("Jimmy");
		expectCustomer.setLastName("Jin");
		expectCustomer.setCreatedDate(new Date());
		expectCustomer.setUpdatedDate(new Date());
		Customer customer = customerRepo.saveAndFlush(expectCustomer);
		
//		Customer expectCustomer1 = new Customer();
//		expectCustomer1.setId(2L);
//		expectCustomer1.setFirstName("Prussia");
//		expectCustomer1.setLastName("Jin");
//		expectCustomer1.setCreatedDate(new Date());
//		expectCustomer1.setUpdatedDate(new Date());
//		
//		customerRepo.saveAndFlush(expectCustomer1);
	}

	@Test
	public void testFindOne() {
		
		boolean iexist = customerRepo.exists(expectCustomer.getId());
		Assert.assertTrue(iexist);
	    Customer customer2 = customerRepo.findOne(expectCustomer.getId());
		Assert.assertNotNull(customer2.getId());
		Assert.assertEquals(Long.valueOf(1), customer2.getId());
		Assert.assertTrue("Jimmy".equals(customer2.getFirstName()));
		Assert.assertTrue("Jin".equals(customer2.getLastName()));
	}
	
	@Test
	public void testFindCustomer() {
		List<Customer> customers = customerRepo.findAll();
		long count = customers.size();
		Assert.assertEquals(1L, count);
		Assert.assertEquals(expectCustomer.getFirstName(), customers.get(0).getFirstName());
		Assert.assertEquals(expectCustomer.getLastName(), customers.get(0).getLastName());
		Assert.assertEquals(expectCustomer.getId(), customers.get(0).getId());
		Assert.assertNotNull(customers.get(0).getCreatedDate());
		Assert.assertNotNull(customers.get(0).getUpdatedDate());
	}
}
