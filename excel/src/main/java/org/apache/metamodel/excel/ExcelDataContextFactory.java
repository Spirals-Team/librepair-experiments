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
package org.apache.metamodel.excel;

import org.apache.metamodel.ConnectionException;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.factory.AbstractDataContextFactory;
import org.apache.metamodel.factory.DataContextProperties;
import org.apache.metamodel.factory.ResourceFactoryRegistry;
import org.apache.metamodel.factory.UnsupportedDataContextPropertiesException;
import org.apache.metamodel.schema.naming.ColumnNamingStrategy;
import org.apache.metamodel.schema.naming.CustomColumnNamingStrategy;
import org.apache.metamodel.util.Resource;
import org.apache.metamodel.util.SimpleTableDef;

public class ExcelDataContextFactory extends AbstractDataContextFactory {

    @Override
    protected String getType() {
        return "excel";
    }

    @Override
    public DataContext create(DataContextProperties properties, ResourceFactoryRegistry resourceFactoryRegistry)
            throws UnsupportedDataContextPropertiesException, ConnectionException {

        final Resource resource = resourceFactoryRegistry.createResource(properties.getResourceProperties());

        final int columnNameLineNumber =
                getInt(properties.getColumnNameLineNumber(), ExcelConfiguration.DEFAULT_COLUMN_NAME_LINE);
        final Boolean skipEmptyLines = getBoolean(properties.isSkipEmptyLines(), true);
        final Boolean skipEmptyColumns = getBoolean(properties.isSkipEmptyColumns(), false);

        final ColumnNamingStrategy columnNamingStrategy;
        if (properties.getTableDefs() == null) {
            columnNamingStrategy = null;
        } else {
            final SimpleTableDef firstTable = properties.getTableDefs()[0];
            final String[] columnNames = firstTable.getColumnNames();
            columnNamingStrategy = new CustomColumnNamingStrategy(columnNames);
        }

        final ExcelConfiguration configuration =
                new ExcelConfiguration(columnNameLineNumber, columnNamingStrategy, skipEmptyLines, skipEmptyColumns);
        return new ExcelDataContext(resource, configuration);
    }
}
