package com.worldoftestcraft.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.worldoftestcraft.app.Account;

public interface AccountRepository {
	public Connection getConnection();

	public void setConnection(Connection connection) throws SQLException;

	public void initDatabase() throws SQLException;

	public void dropRepository();

	public Account getById(int id);

	public List<Account> getAll();

	public int add(Account account);

	public int delete(Account account);

	public int update(int oldAccountId, Account newAccount);
}
