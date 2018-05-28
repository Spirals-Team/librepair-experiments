package com.softmill.account.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softmill.account.domain.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

	Account findByName(String name);

}