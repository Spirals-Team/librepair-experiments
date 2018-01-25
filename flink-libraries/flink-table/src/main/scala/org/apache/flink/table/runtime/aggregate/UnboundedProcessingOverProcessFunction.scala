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
package org.apache.flink.table.runtime.aggregate

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.ProcessFunction.{Context, OnTimerContext}
import org.apache.flink.types.Row
import org.apache.flink.streaming.api.functions.RichProcessFunction
import org.apache.flink.util.{Collector, Preconditions}
import org.apache.flink.api.common.typeutils.TypeSerializer
import org.apache.flink.api.common.state.ValueStateDescriptor
import org.apache.flink.api.java.typeutils.RowTypeInfo
import org.apache.flink.api.common.state.ValueState
import org.apache.flink.api.java.typeutils.runtime.RowSerializer

class UnboundedProcessingOverProcessFunction(
    private val aggregates: Array[Aggregate[_]],
    private val projectionsMapping: Array[(Int, Int)],
    private val aggregateMapping: Array[(Int, Int)],
    private val  intermediateRowType: RowTypeInfo,
  @transient private val returnType: TypeInformation[Row])
  extends RichProcessFunction[Row, Row]{

  protected var stateSerializer: TypeSerializer[Row] = _
  protected var stateDescriptor: ValueStateDescriptor[Row] = _

  private var output: Row = _
  private var state: ValueState[Row] = _

  override def open(config: Configuration) {
    Preconditions.checkNotNull(aggregates)
    Preconditions.checkNotNull(projectionsMapping)
    Preconditions.checkNotNull(aggregateMapping)
    Preconditions.checkArgument(aggregates.length == aggregateMapping.length)

    val finalRowLength: Int = projectionsMapping.length + aggregateMapping.length
    output = new Row(finalRowLength)
    stateSerializer = intermediateRowType.createSerializer(getRuntimeContext.getExecutionConfig)
    stateDescriptor = new ValueStateDescriptor[Row]("overState", stateSerializer)
    state = getRuntimeContext.getState(stateDescriptor)
  }

  override def processElement(
    value2: Row,
    ctx: Context,
    out: Collector[Row]): Unit = {
    val value1 = state.value()
    val accumulatorRow = new Row(intermediateRowType.getArity)

    if (null != value1) {
      // copy all fields of value1 into accumulatorRow
      (0 until intermediateRowType.getArity)
      .foreach(i => accumulatorRow.setField(i, value1.getField(i)))
      // merge value2 to accumulatorRow
      aggregates.foreach(_.merge(value2, accumulatorRow))
      // Set projections value to final output.
      projectionsMapping.foreach {
        case (after, previous) =>
          accumulatorRow.setField(after, value2.getField(previous))
      }
    } else {
      // copy all fields of value1 into accumulatorRow
      (0 until intermediateRowType.getArity)
      .foreach(i => accumulatorRow.setField(i, value2.getField(i)))

    }
    state.update(accumulatorRow)

    // Set input value to final output.
    projectionsMapping.foreach {
      case (after, previous) =>
        output.setField(after, accumulatorRow.getField(previous))
    }

    // Evaluate final aggregate value and set to output.
    aggregateMapping.foreach {
      case (after, previous) =>
        output.setField(after, aggregates(previous).evaluate(accumulatorRow))
    }
    //TODO add window information When we implement FLINK-4680.
    out.collect(output)
  }

  override def onTimer(
    timestamp: Long,
    ctx: OnTimerContext,
    out: Collector[Row]): Unit = ??? // Implement this method if following is needed to be supported
}
