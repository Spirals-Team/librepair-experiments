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
package org.jooby.internal.quartz;

import static java.util.Objects.requireNonNull;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Provider;
import javax.sql.DataSource;

import org.quartz.utils.ConnectionProvider;

public class QuartzConnectionProvider implements ConnectionProvider {

  private Provider<DataSource> ds;

  public QuartzConnectionProvider(final Provider<DataSource> ds) {
    this.ds = requireNonNull(ds, "Data source is required.");
  }

  @Override
  public Connection getConnection() throws SQLException {
    return ds.get().getConnection();
  }

  @Override
  public void shutdown() throws SQLException {
    // NOOP
  }

  @Override
  public void initialize() throws SQLException {
    ds.get();
  }

}
