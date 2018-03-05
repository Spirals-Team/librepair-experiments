package com.worldoftestcraft.repository;

import java.util.List;

import com.worldoftestcraft.app.Account;

public interface AccountRepository {
	public void initDatabase();

	public Account getById(int id);

	public List<Account> getAll();

	public void add(Account account);

	public void delete(Account account);

	public void update(int accountId, Account account);
}
