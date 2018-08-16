/*******************************************************************************
 * Copyright (c) 2010 Robert "Unlogic" Olofsson (unlogic@unlogic.se).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0-standalone.html
 ******************************************************************************/
package se.unlogic.standardutils.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Deprecated
public interface GenericDAO<KeyType,BeanType> {

	public void add(BeanType bean) throws SQLException;

	public void add(BeanType bean, TransactionHandler transactionHandler) throws SQLException;

	public void add(BeanType bean, Connection connection) throws SQLException;

	public void update(BeanType bean) throws SQLException;

	public void update(BeanType bean, TransactionHandler transactionHandler) throws SQLException;

	public void update(BeanType bean, Connection connection) throws SQLException;

	public BeanType get(KeyType id, Field... relations) throws SQLException;

	public BeanType get(KeyType id, TransactionHandler transactionHandler, Field... relations) throws SQLException;

	public BeanType get(KeyType id, Connection connection, Field... relations) throws SQLException;

	public List<BeanType> getAll(Field... relations) throws SQLException;

	public List<BeanType> getAll(TransactionHandler transactionHandler, Field... relations) throws SQLException;

	public List<BeanType> getAll(Connection connection, Field... relations) throws SQLException;

	public void delete(BeanType bean) throws SQLException;

	public void delete(BeanType bean, TransactionHandler transactionHandler) throws SQLException;

	public void delete(BeanType bean, Connection connection) throws SQLException;
}
