/**
 * Copyright (C) 2014-2016 LinkedIn Corp. (pinot-core@linkedin.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linkedin.pinot.tools.scan.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.linkedin.pinot.core.query.utils.Pair;


public class DistinctCountFunction extends AggregationFunc {
  private static final String _name = "distinctcount";

  public DistinctCountFunction(ResultTable rows, String column) {
    super(rows, column);
  }

  @Override
  public ResultTable run() {
    Set<String> distinctSet = new HashSet<>();

    for (ResultTable.Row row : _rows) {
      Object object = row.get(_column, _name);

      if (object instanceof HashSet) {
        Set<String> existingSet = (HashSet<String>) object;
        distinctSet.addAll(existingSet);
      } else {
        String value = row.get(_column, _name).toString();
        if (!distinctSet.contains(value)) {
          distinctSet.add(value);
        }
      }
    }

    ResultTable resultTable = new ResultTable(new ArrayList<Pair>(), 1);
    resultTable.add(0, distinctSet);

    return resultTable;
  }
}
