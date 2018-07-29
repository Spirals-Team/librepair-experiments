package com.longmaple.ttmall.address.controller;

import org.springframework.data.repository.CrudRepository;

import com.longmaple.ttmall.address.data.Address;

public interface AddressRepo extends CrudRepository<Address, Long> {

	Address findByUserId(Long userId);

}
