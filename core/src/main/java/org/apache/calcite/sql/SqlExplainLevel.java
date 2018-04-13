/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.sql;

import org.apache.calcite.sql.parser.SqlParserPos;

/**
 * SqlExplainLevel defines detail levels for EXPLAIN PLAN.
 */
public enum SqlExplainLevel {
  /**
   * Suppress all attributes.
   */
  NO_ATTRIBUTES,

  /**
   * Display only attributes which contribute to the plan output.
   */
  EXPPLAN_ATTRIBUTES,

  /**
   * Display only attributes which contribute to an expression's digest.
   */
  DIGEST_ATTRIBUTES,

  /**
   * Display all attributes, including id, except cost.
   */
  NON_COST_ATTRIBUTES,

  /**
   * Display all attributes, including cost.
   */
  ALL_ATTRIBUTES;

  /**
   * Creates a parse-tree node representing an occurrence of this symbol at
   * a particular position in the parsed text.
   */
  public SqlLiteral symbol(SqlParserPos pos) {
    return SqlLiteral.createSymbol(this, pos);
  }
}

// End SqlExplainLevel.java
