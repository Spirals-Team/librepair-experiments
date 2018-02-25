package io.ebeaninternal.server.core;

import io.ebean.QueryIterator;
import io.ebean.Version;
import io.ebean.bean.BeanCollection;

import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.util.List;

/**
 * The Object Relational query execution API.
 */
public interface OrmQueryEngine {

  /**
   * Execute the 'find by id' query returning a single bean.
   */
  <T> T findId(OrmQueryRequest<T> request);

  /**
   * Execute the findList, findSet, findMap query returning an appropriate BeanCollection.
   */
  <T> BeanCollection<T> findMany(OrmQueryRequest<T> request);

  /**
   * Execute the findSingleAttributeList query.
   */
  <A> List<A> findSingleAttributeList(OrmQueryRequest<?> request);

  /**
   * Execute the findVersions query.
   */
  <T> List<Version<T>> findVersions(OrmQueryRequest<T> request);

  /**
   * Execute the query using a QueryIterator.
   */
  <T> QueryIterator<T> findIterate(OrmQueryRequest<T> request);

  /**
   * Execute the row count query.
   */
  <T> int findCount(OrmQueryRequest<T> request);

  /**
   * Execute the find id's query.
   */
  <A> List<A> findIds(OrmQueryRequest<?> request);

  /**
   * Execute the query as a delete statement.
   */
  <T> int delete(OrmQueryRequest<T> request);

  /**
   * Execute the query as a update statement.
   */
  <T> int update(OrmQueryRequest<T> request);

  /**
   * Translate the SQLException to a specific persistence exception type if possible.
   */
  <T> PersistenceException translate(OrmQueryRequest<T> request, String bindLog, String sql, SQLException e);

  /**
   * Return true if multi-value bind is supported for this type (and current platform).
   */
  boolean isMultiValueSupported(Class<?> valueType);
}
