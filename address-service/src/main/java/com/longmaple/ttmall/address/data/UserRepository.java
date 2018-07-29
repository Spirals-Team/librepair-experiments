package com.longmaple.ttmall.address.data;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<EMallUser, Long> {

	EMallUser findByUsername(String username);
}
