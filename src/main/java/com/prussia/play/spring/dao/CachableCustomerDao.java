package com.prussia.play.spring.dao;

import com.prussia.play.spring.domain.po.Customer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = "Customers")
public class CachableCustomerDao {

	@Cacheable
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	public Customer findByLastName(String lastName) {
		System.out.println("---> Loading Customer with last name '" + lastName + "'"); //when the value can be found in cache, the method won't be invoked
		return new Customer(1L, "firstName", lastName);
	}
	
	/**
	 * http://tianmaying.com/tutorial/spring-web-ehcache#15
	 * 	@Cacheable 触发添加缓存的方法
		@CacheEvict 触发删除缓存的方法
		@CachePut 在不干涉方法执行的情况下更新缓存
		@Caching 组织多个缓存标注的标注
		@CacheConfig 在class的层次共享缓存的设置
	 * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html
	 * 
	 * 
	 */
}
