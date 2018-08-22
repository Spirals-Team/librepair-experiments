/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.bigtable.hbase;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;

import java.io.IOException;

/**
 * Tests creation and deletion of column families.
 */
public class TestColumnFamilyAdmin extends AbstractTestColumnFamilyAdmin {

  @Override
  protected HTableDescriptor getTableDescriptor(TableName tableName) throws IOException {
    return admin.getTableDescriptor(tableName);
  }

  @Override
  protected void addColumn(byte[] columnName, int versions) throws Exception {
    admin.addColumn(tableName, new HColumnDescriptor(columnName).setMaxVersions(versions));
  }

  @Override
  protected void modifyColumn(byte[] columnName, int versions) throws Exception {
    admin.modifyColumn(tableName, new HColumnDescriptor(columnName).setMaxVersions(versions));
  }

  @Override
  protected void deleteColumn(byte[] columnName) throws Exception {
    admin.deleteColumn(tableName, columnName);
  }
}
