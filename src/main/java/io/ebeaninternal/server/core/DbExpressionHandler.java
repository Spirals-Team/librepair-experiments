package io.ebeaninternal.server.core;

import io.ebeaninternal.api.SpiExpressionRequest;
import io.ebeaninternal.server.expression.Op;

/**
 * Adds the db platform specific json expression.
 */
public interface DbExpressionHandler {

  /**
   * Return the DB concat operator (Usually SQL standard "||").
   */
  String getConcatOperator();

  /**
   * Write the db platform specific json expression.
   */
  void json(SpiExpressionRequest request, String propName, String path, Op operator, Object value);

  /**
   * Add SQL for ARRAY CONTAINS expression.
   */
  void arrayContains(SpiExpressionRequest request, String propName, boolean contains, Object... values);

  /**
   * Add SQL for ARRAY IS EMPTY expression.
   */
  void arrayIsEmpty(SpiExpressionRequest request, String propName, boolean empty);

}
