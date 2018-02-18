/*
 * Licensed to Metamarkets Group Inc. (Metamarkets) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Metamarkets licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.sql.calcite.expression.builtin;

import io.druid.java.util.common.StringUtils;
import io.druid.query.extraction.SubstringDimExtractionFn;
import io.druid.sql.calcite.expression.DruidExpression;
import io.druid.sql.calcite.expression.Expressions;
import io.druid.sql.calcite.expression.OperatorConversions;
import io.druid.sql.calcite.expression.SqlOperatorConversion;
import io.druid.sql.calcite.planner.PlannerContext;
import io.druid.sql.calcite.table.RowSignature;
import org.apache.calcite.rex.RexCall;
import org.apache.calcite.rex.RexLiteral;
import org.apache.calcite.rex.RexNode;
import org.apache.calcite.sql.SqlFunction;
import org.apache.calcite.sql.SqlFunctionCategory;
import org.apache.calcite.sql.SqlOperator;
import org.apache.calcite.sql.type.ReturnTypes;
import org.apache.calcite.sql.type.SqlTypeFamily;

public class SubstringOperatorConversion implements SqlOperatorConversion
{
  private static final SqlFunction SQL_FUNCTION = OperatorConversions
      .operatorBuilder("SUBSTRING")
      .operandTypes(SqlTypeFamily.CHARACTER, SqlTypeFamily.INTEGER, SqlTypeFamily.INTEGER)
      .functionCategory(SqlFunctionCategory.STRING)
      .returnTypeInference(ReturnTypes.ARG0)
      .requiredOperands(2)
      .build();

  @Override
  public SqlOperator calciteOperator()
  {
    return SQL_FUNCTION;
  }

  @Override
  public DruidExpression toDruidExpression(
      final PlannerContext plannerContext,
      final RowSignature rowSignature,
      final RexNode rexNode
  )
  {
    // Can't simply pass-through operands, since SQL standard args don't match what Druid's expression language wants.
    // SQL is 1-indexed, Druid is 0-indexed.

    final RexCall call = (RexCall) rexNode;
    final DruidExpression input = Expressions.toDruidExpression(
        plannerContext,
        rowSignature,
        call.getOperands().get(0)
    );
    if (input == null) {
      return null;
    }
    final int index = RexLiteral.intValue(call.getOperands().get(1)) - 1;
    final int length;
    if (call.getOperands().size() > 2) {
      length = RexLiteral.intValue(call.getOperands().get(2));
    } else {
      length = -1;
    }

    return input.map(
        simpleExtraction -> simpleExtraction.cascade(new SubstringDimExtractionFn(index, length < 0 ? null : length)),
        expression -> StringUtils.format(
            "substring(%s, %s, %s)",
            expression,
            DruidExpression.numberLiteral(index),
            DruidExpression.numberLiteral(length)
        )
    );
  }
}
