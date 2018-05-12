/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.table.runtime.stream.sql

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.TableEnvironment
import org.apache.flink.table.api.scala._
import org.apache.flink.api.scala._
import org.apache.flink.table.runtime.utils.{StreamITCase, StreamTestData, StreamingWithStateTestBase}
import org.apache.flink.types.Row
import org.junit.Assert.assertEquals
import org.junit.Test

import scala.collection.mutable
import scala.util.Random

class SetOperatorsITCase extends StreamingWithStateTestBase {

  @Test
  def testIntersect(): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val tEnv = TableEnvironment.getTableEnvironment(env)

    StreamITCase.clear
    val sqlQuery = "SELECT c FROM t1 INTERSECT SELECT c FROM t2"

    val ds1 = StreamTestData.getSmall3TupleDataStream(env)
    val data = new mutable.MutableList[(Int, Long, String)]
    data.+=((1, 1L, "Hi"))
    data.+=((2, 2L, "Hello"))
    data.+=((2, 2L, "Hello"))
    data.+=((3, 2L, "Hello world!"))
    val ds2 = env.fromCollection(Random.shuffle(data))

    tEnv.registerTable("t1", ds1.toTable(tEnv, 'a, 'b, 'c))
    tEnv.registerTable("t2", ds2.toTable(tEnv, 'a, 'b, 'c))

    val result = tEnv.sqlQuery(sqlQuery).toAppendStream[Row]
    result.addSink(new StreamITCase.StringSink[Row])
    env.execute()

    val expected = List("Hi", "Hello")
    assertEquals(expected.sorted, StreamITCase.testResults.sorted)
  }

  @Test
  def testIntersectAll(): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val tEnv = TableEnvironment.getTableEnvironment(env)

    StreamITCase.clear
    val sqlQuery = "SELECT c FROM t1 INTERSECT ALL SELECT c FROM t2"

    val data1 = new mutable.MutableList[Int]
    data1 += (1, 1, 1, 2, 2, 3, 3, 3, 4)
    val data2 = new mutable.MutableList[Int]
    data2 += (1, 2, 2, 3, 3, 3, 4, 4, 4, 4)
    val ds1 = env.fromCollection(data1)
    val ds2 = env.fromCollection(data2)

    tEnv.registerTable("t1", ds1.toTable(tEnv, 'c))
    tEnv.registerTable("t2", ds2.toTable(tEnv, 'c))

    val result = tEnv.sqlQuery(sqlQuery).toAppendStream[Row]
    result.addSink(new StreamITCase.StringSink[Row])
    env.execute()

    val expected = List("1", "2", "2", "3", "3", "3", "4")

    assertEquals(expected.sorted, StreamITCase.testResults.sorted)
  }

  @Test
  def testIntersectWithFilter(): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val tEnv = TableEnvironment.getTableEnvironment(env)

    StreamITCase.clear

    val sqlQuery = "SELECT c FROM ((SELECT * FROM t1) INTERSECT (SELECT * FROM t2)) WHERE a > 1"

    val ds1 = StreamTestData.getSmall3TupleDataStream(env)
    val ds2 = StreamTestData.get3TupleDataStream(env)

    tEnv.registerTable("t1", ds1.toTable(tEnv, 'a, 'b, 'c))
    tEnv.registerTable("t2", ds2.toTable(tEnv, 'a, 'b, 'c))

    val result = tEnv.sqlQuery(sqlQuery).toAppendStream[Row]
    result.addSink(new StreamITCase.StringSink[Row])
    env.execute()

    val expected = List("Hello", "Hello world")

    assertEquals(expected.sorted, StreamITCase.testResults.sorted)
  }

  @Test
  def testIntersectWithRetraction(): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val tEnv = TableEnvironment.getTableEnvironment(env)

    StreamITCase.clear
    val sqlQuery = "SELECT SUM(b) FROM t1 GROUP BY a INTERSECT SELECT SUM(b) FROM t2 GROUP BY a"

    val data1 = List(
      ("a", 1),
      ("a", 2),
      ("a", 3),
      ("a", 4),
      ("b", 10),
      ("b", -6),
      ("b", -1)
    )
    val data2 = List(
      ("a", 3),
      ("a", 3),
      ("a", 3),
      ("a", 1),
      ("b", 1),
      ("b", 1),
      ("b", 1)
    )

    val ds1 = env.fromCollection(data1)
    val ds2 = env.fromCollection(data2)

    tEnv.registerTable("t1", ds1.toTable(tEnv, 'a, 'b))
    tEnv.registerTable("t2", ds2.toTable(tEnv, 'a, 'b))

    val result = tEnv.sqlQuery(sqlQuery).toRetractStream[Row]
    result.addSink(new StreamITCase.RetractingSink)
    env.execute()

    val expected = List("10", "3")

    assertEquals(expected.sorted, StreamITCase.retractedResults.sorted)
  }

}
