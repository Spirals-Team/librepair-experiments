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
package org.apache.calcite.rex;

import org.apache.calcite.DataContext;
import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.linq4j.QueryProvider;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptSchema;
import org.apache.calcite.rel.logical.LogicalTableScan;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.test.JdbcTest;
import org.apache.calcite.tools.Frameworks;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Unit test for {@link org.apache.calcite.rex.RexSimplifier}.
 */
public class RexSimplifierTest {
  protected void check(final Action action) throws Exception {
    Frameworks.withPlanner(
      new Frameworks.PlannerAction<Void>() {
        public Void apply(RelOptCluster cluster,
                  RelOptSchema relOptSchema, SchemaPlus rootSchema) {

          rootSchema.add("hr",
            new ReflectiveSchema(new JdbcTest.HrSchema()));

          LogicalTableScan scan =
              LogicalTableScan.create(cluster,
                  relOptSchema.getTableForMember(
                      Arrays.asList("hr", "emps")));

          final RexBuilder rexBuilder = cluster.getRexBuilder();

          DataContext dataContext = new RexSimplifierTestDataContext();
          final RexExecutorImpl executor = new RexExecutorImpl(dataContext);

          final RexNode intRef = rexBuilder.makeFieldAccess(
            rexBuilder.makeRangeReference(scan),
            "deptno", false);
          final RexNode strRef = rexBuilder.makeFieldAccess(
            rexBuilder.makeRangeReference(scan),
            "name", false);
          final RexSimplify simplifier = new RexSimplify(rexBuilder, false, executor);

          action.check(rexBuilder, simplifier, intRef, strRef);
          return null;
        }
      });
  }

  @Test public void testCaseWhenAlwaysTrueElseFalse() throws Exception {
    check(new Action() {
      public void check(RexBuilder rexBuilder, RexSimplify simplifier,
        RexNode intRef, RexNode strRef) {

        final RelDataTypeFactory typeFactory = rexBuilder.getTypeFactory();
        final RelDataType integer =
                typeFactory.createSqlType(SqlTypeName.INTEGER);

        // CASE
        // WHEN $1 = 3 THEN TRUE
        // WHEN $3 = 5 THEN TRUE
        // ELSE FALSE
        // END
        final RexNode caseNode = rexBuilder.makeCall(
          SqlStdOperatorTable.CASE,
          rexBuilder.makeCall(
            SqlStdOperatorTable.EQUALS,
              intRef,
              rexBuilder.makeLiteral(3, integer, true)),
          rexBuilder.makeLiteral(true),
          rexBuilder.makeCall(
            SqlStdOperatorTable.EQUALS,
              intRef,
              rexBuilder.makeLiteral(5, integer, true)),
          rexBuilder.makeLiteral(true),
          rexBuilder.makeLiteral(false));

        RexNode expr = simplifier.simplify(caseNode);

        // should be reduced to $1=3 OR $1=5
        assertThat(expr.toString(), equalTo("OR(=($1, 3), =($1, 5))"));
      }
    });
  }

  @Test public void testCaseWhenAlwaysTrue() throws Exception {
    check(new Action() {
      public void check(RexBuilder rexBuilder, RexSimplify simplifier,
        RexNode intRef, RexNode strRef) {

        final RelDataTypeFactory typeFactory = rexBuilder.getTypeFactory();
        final RelDataType integer =
                typeFactory.createSqlType(SqlTypeName.INTEGER);

        // CASE
        // WHEN $1 = 3 THEN TRUE
        // WHEN $1 = 5 THEN TRUE
        // ELSE TRUE
        // END
        final RexNode caseNode = rexBuilder.makeCall(
          SqlStdOperatorTable.CASE,
          rexBuilder.makeCall(
            SqlStdOperatorTable.EQUALS,
              intRef,
              rexBuilder.makeLiteral(3, integer, true)),
          rexBuilder.makeLiteral(true),
          rexBuilder.makeCall(
            SqlStdOperatorTable.EQUALS,
              intRef,
              rexBuilder.makeLiteral(5, integer, true)),
          rexBuilder.makeLiteral(true),
          rexBuilder.makeLiteral(true));

        RexNode expr = simplifier.simplify(caseNode);

        // Should be reduced to true
        assertThat(expr.toString(), equalTo("true"));
      }
    });
  }

  @Test public void testCaseWhenAnyExpressionElseTrue() throws Exception {
    check(new Action() {
      public void check(RexBuilder rexBuilder, RexSimplify simplifier,
        RexNode intRef, RexNode strRef) {

        final RelDataTypeFactory typeFactory = rexBuilder.getTypeFactory();
        final RelDataType integer =
                typeFactory.createSqlType(SqlTypeName.INTEGER);

        // CASE
        // WHEN $1 = 3 THEN $2 = foo
        // WHEN $1 = 5 THEN $2= bar
        // ELSE TRUE
        // END
        final RexNode caseNode = rexBuilder.makeCall(
          SqlStdOperatorTable.CASE,
          rexBuilder.makeCall(
            SqlStdOperatorTable.EQUALS,
              intRef,
              rexBuilder.makeLiteral(3, integer, true)),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("foo")),
          rexBuilder.makeCall(
            SqlStdOperatorTable.EQUALS,
              intRef,
              rexBuilder.makeLiteral(5, integer, true)),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("bar")),
          rexBuilder.makeLiteral(true));

        RexNode expr = simplifier.simplify(caseNode);

        // ($1=3 AND $2=foo) OR (!$1=3 AND $1=5 AND $2=bar) OR (!$=3 AND !$1=5)
        assertThat(expr.toString(),
          equalTo("OR("
            + "AND(=($1, 3), =($2, 'foo')), "
            + "AND(<>($1, 3), =($1, 5), =($2, 'bar')), "
            + "AND(<>($1, 3), <>($1, 5)))"));
      }
    });
  }

  @Test public void testCaseWhenAnyExpressionElseFalse() throws Exception {
    check(new Action() {
      public void check(RexBuilder rexBuilder, RexSimplify simplifier,
        RexNode intRef, RexNode strRef) {

        final RelDataTypeFactory typeFactory = rexBuilder.getTypeFactory();
        final RelDataType integer =
                typeFactory.createSqlType(SqlTypeName.INTEGER);

        // CASE
        // WHEN $1 = 3 THEN $2 = foo
        // WHEN $1 = 5 THEN $2= bar
        // ELSE TRUE
        // END
        final RexNode caseNode = rexBuilder.makeCall(
          SqlStdOperatorTable.CASE,
          rexBuilder.makeCall(
            SqlStdOperatorTable.EQUALS,
              intRef,
              rexBuilder.makeLiteral(3, integer, true)),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("foo")),
          rexBuilder.makeCall(
            SqlStdOperatorTable.EQUALS,
              intRef,
              rexBuilder.makeLiteral(5, integer, true)),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("bar")),
          rexBuilder.makeLiteral(false));

        RexNode expr = simplifier.simplify(caseNode);

        // ($1=3 AND $2=foo) OR (!$1=3 AND $1=5 AND $2=bar)
        assertThat(expr.toString(),
          equalTo("OR("
            + "AND(=($1, 3), =($2, 'foo')), "
            + "AND(<>($1, 3), =($1, 5), =($2, 'bar')))"));
      }
    });
  }

  @Test public void testCaseWhenStopAfterTrue() throws Exception {
    check(new Action() {
      public void check(RexBuilder rexBuilder, RexSimplify simplifier,
        RexNode intRef, RexNode strRef) {

        final RelDataTypeFactory typeFactory = rexBuilder.getTypeFactory();
        final RelDataType integer =
                typeFactory.createSqlType(SqlTypeName.INTEGER);

        // CASE
        // WHEN $1 = 3 THEN $2 = foo
        // WHEN TRUE THEN $2 = stop
        // WHEN $1 = 5 THEN $2= bar
        // WHEN $1 = 7 THEN $2= barbar
        // ELSE ...
        // END
        final RexNode caseNode = rexBuilder.makeCall(
          SqlStdOperatorTable.CASE,
          rexBuilder.makeCall(
            SqlStdOperatorTable.EQUALS,
              intRef,
              rexBuilder.makeLiteral(3, integer, true)),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("foo")),
          rexBuilder.makeLiteral(true),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("stop")),
          rexBuilder.makeCall(
            SqlStdOperatorTable.EQUALS,
              intRef,
              rexBuilder.makeLiteral(5, integer, true)),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("bar")),
          rexBuilder.makeCall(
            SqlStdOperatorTable.EQUALS,
              intRef,
              rexBuilder.makeLiteral(7, integer, true)),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("barbar")),
          rexBuilder.makeLiteral(false));

        RexNode expr = simplifier.simplify(caseNode);

        // ($1=3 AND $2=foo) OR (!$1=3 AND $2=stop)
        assertThat(expr.toString(),
          equalTo("OR("
            + "AND(=($1, 3), =($2, 'foo')), "
            + "AND(<>($1, 3), =($2, 'stop')))"));
      }
    });
  }

  @Test public void testCaseWhenSkipFalse() throws Exception {
    check(new Action() {
      public void check(RexBuilder rexBuilder, RexSimplify simplifier,
        RexNode intRef, RexNode strRef) {

        final RelDataTypeFactory typeFactory = rexBuilder.getTypeFactory();
        final RelDataType integer =
                typeFactory.createSqlType(SqlTypeName.INTEGER);

        // CASE
        // WHEN $1 = 3 THEN $2 = foo
        // WHEN FALSE THEN $2 = skip
        // WHEN $1 = 5 THEN $2= bar
        // WHEN $1<>$1 = 7 THEN $2= skip
        // ELSE ...
        // END
        final RexNode caseNode = rexBuilder.makeCall(
          SqlStdOperatorTable.CASE,
          rexBuilder.makeCall(
            SqlStdOperatorTable.EQUALS,
              intRef,
              rexBuilder.makeLiteral(3, integer, true)),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("foo")),
          rexBuilder.makeLiteral(false),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("skip")),
          rexBuilder.makeCall(
            SqlStdOperatorTable.EQUALS,
              intRef,
              rexBuilder.makeLiteral(5, integer, true)),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("bar")),
          rexBuilder.makeCall(
            SqlStdOperatorTable.NOT_EQUALS,
              intRef,
              intRef),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("skip")),
          rexBuilder.makeLiteral(false));

        RexNode expr = simplifier.simplify(caseNode);

        // ($1=3 AND $2=foo) OR (!$1=3 AND $1=5 AND $2=bar)
        assertThat(expr.toString(),
          equalTo("OR("
            + "AND(=($1, 3), =($2, 'foo')), "
            + "AND(<>($1, 3), =($1, 5), =($2, 'bar')))"));
      }
    });
  }

  @Test public void testCaseNestedCaseExpression() throws Exception {
    check(new Action() {
      public void check(RexBuilder rexBuilder, RexSimplify simplifier,
        RexNode intRef, RexNode strRef) {

        final RelDataTypeFactory typeFactory = rexBuilder.getTypeFactory();
        final RelDataType integer =
                typeFactory.createSqlType(SqlTypeName.INTEGER);

        // CASE
        // WHEN $1 = 3 THEN $2 = foo
        // WHEN $1 > 10 THEN
        //  CASE
        //    WHEN $i = 11 THEN $2 = bar
        //    WHEN $i = 13 THEN $2 = far
        //    ELSE ...
        // WHEN $1 < 3 THEN $2 = car
        // ELSE ...
        // END
        final RexNode caseNode = rexBuilder.makeCall(
          SqlStdOperatorTable.CASE,
          rexBuilder.makeCall(
            SqlStdOperatorTable.EQUALS,
              intRef,
              rexBuilder.makeLiteral(3, integer, true)),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("foo")),
          rexBuilder.makeCall(
            SqlStdOperatorTable.GREATER_THAN,
              intRef,
              rexBuilder.makeLiteral(10, integer, true)),
          rexBuilder.makeCall(SqlStdOperatorTable.CASE,
            rexBuilder.makeCall(
              SqlStdOperatorTable.EQUALS,
                intRef,
                rexBuilder.makeLiteral(11, integer, true)),
            rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
              strRef,
              rexBuilder.makeLiteral("bar")),
            rexBuilder.makeCall(
              SqlStdOperatorTable.EQUALS,
                intRef,
                rexBuilder.makeLiteral(13, integer, true)),
            rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
              strRef,
              rexBuilder.makeLiteral("far")),
            rexBuilder.makeLiteral(false)),
          rexBuilder.makeCall(
            SqlStdOperatorTable.LESS_THAN,
              intRef,
              rexBuilder.makeLiteral(3, integer, true)),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("car")),
          rexBuilder.makeLiteral(true));

        RexNode expr = simplifier.simplify(caseNode);

        assertThat(expr.toString(),
          equalTo("OR("
            + "AND(=($1, 3), =($2, 'foo')), "
            + "AND(<>($1, 3), >($1, 10), OR("
              + "AND(=($1, 11), =($2, 'bar')), "
              + "AND(<>($1, 11), =($1, 13), =($2, 'far')))), "
            + "AND(<>($1, 3), <=($1, 10), <($1, 3), =($2, 'car')), "
            + "AND(<>($1, 3), <=($1, 10), >=($1, 3)))"));
      }
    });
  }

  @Test public void testCaseNestedCasePredicate() throws Exception {
    check(new Action() {
      public void check(RexBuilder rexBuilder, RexSimplify simplifier,
        RexNode intRef, RexNode strRef) {

        final RelDataTypeFactory typeFactory = rexBuilder.getTypeFactory();
        final RelDataType integer =
                typeFactory.createSqlType(SqlTypeName.INTEGER);

        // CASE
        // WHEN $1 = 3 THEN $2 = foo
        // WHEN CASE
        //    WHEN $i >100 THEN $1 < 200
        //    WHEN $i < 50 THEN $1 > 25
        //    ELSE ...
        //  END THEN $2=bar
        // WHEN $1 < 3 THEN $2 = car
        // ELSE ...
        // END
        final RexNode caseNode = rexBuilder.makeCall(
          SqlStdOperatorTable.CASE,
          rexBuilder.makeCall(
            SqlStdOperatorTable.EQUALS,
              intRef,
              rexBuilder.makeLiteral(3, integer, true)),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("foo")),
          rexBuilder.makeCall(SqlStdOperatorTable.CASE,
            rexBuilder.makeCall(
              SqlStdOperatorTable.GREATER_THAN,
                intRef,
                rexBuilder.makeLiteral(100, integer, true)),
            rexBuilder.makeCall(SqlStdOperatorTable.LESS_THAN,
              intRef,
              rexBuilder.makeLiteral(200, integer, true)),
            rexBuilder.makeCall(
              SqlStdOperatorTable.LESS_THAN,
                intRef,
                rexBuilder.makeLiteral(50, integer, true)),
            rexBuilder.makeCall(SqlStdOperatorTable.GREATER_THAN,
              intRef,
              rexBuilder.makeLiteral(25, integer, true)),
            rexBuilder.makeLiteral(false)),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("bar")),
          rexBuilder.makeCall(
            SqlStdOperatorTable.LESS_THAN,
              intRef,
              rexBuilder.makeLiteral(3, integer, true)),
          rexBuilder.makeCall(SqlStdOperatorTable.EQUALS,
            strRef,
            rexBuilder.makeLiteral("car")),
          rexBuilder.makeLiteral(false));

        RexNode expr = simplifier.simplify(caseNode);

        assertThat(expr.toString(),
          equalTo("OR("
            + "AND(=($1, 3), =($2, 'foo')), "
            + "AND(<>($1, 3), OR("
              + "AND(>($1, 100), <($1, 200)), "
              + "AND(<=($1, 100), <($1, 50), >($1, 25))),"
            + " =($2, 'bar')), "
            + "AND(<>($1, 3), "
              + "OR(<=($1, 100), >=($1, 200)), "
              + "OR(>($1, 100), >=($1, 50), <=($1, 25)), "
              + "<($1, 3), =($2, 'car')))"));
      }
    });
  }

  /** Callback for {@link #check}. Test code will typically use {@code builder}
   * to create some expressions, call
   * {@link org.apache.calcite.rex.RexExecutorImpl#reduce} to evaluate them into
   * a list, then check that the results are as expected. */
  interface Action {
    void check(RexBuilder rexBuilder, RexSimplify simplifier,
        RexNode intRef, RexNode strRef);
  }

  /**
   * RexSimplifierTest DataContext
   */
  protected static class RexSimplifierTestDataContext implements DataContext {

    @Override public SchemaPlus getRootSchema() {
      throw new UnsupportedOperationException("getRootSchema: Not supported yet.");
    }

    @Override public JavaTypeFactory getTypeFactory() {
      throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override public QueryProvider getQueryProvider() {
      throw new UnsupportedOperationException("getTypeFactory: Not supported yet.");
    }

    @Override public Object get(String name) {
      throw new UnsupportedOperationException("get: Not supported yet.");
    }
  }
}

// End RexSimplifierTest.java
