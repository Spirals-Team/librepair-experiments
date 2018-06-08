/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.metamodel.data;

/**
 * Wraps another DataSet and enforces a maximum number of rows constraint
 */
public final class MaxRowsDataSet extends AbstractDataSet implements WrappingDataSet {

    private final DataSet _dataSet;
    private volatile int _rowsLeft;

    public MaxRowsDataSet(DataSet dataSet, int maxRows) {
        super(dataSet);
        _dataSet = dataSet;
        _rowsLeft = maxRows;
    }
    
    @Override
    public DataSet getWrappedDataSet() {
        return _dataSet;
    }

    @Override
    public void close() {
        _dataSet.close();
    }

    @Override
    public Row getRow() {
        return _dataSet.getRow();
    }

    @Override
    public boolean next() {
        if (_rowsLeft > 0) {
            boolean next = _dataSet.next();
            if (next) {
                _rowsLeft--;
            }
            return next;
        }
        return false;
    }
}