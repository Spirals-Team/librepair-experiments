/*
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
package com.facebook.presto.execution;

import com.facebook.presto.Session;
import com.facebook.presto.connector.ConnectorId;
import com.facebook.presto.metadata.Metadata;
import com.facebook.presto.metadata.QualifiedObjectName;
import com.facebook.presto.metadata.TableHandle;
import com.facebook.presto.metadata.TableMetadata;
import com.facebook.presto.security.AccessControl;
import com.facebook.presto.spi.ColumnMetadata;
import com.facebook.presto.spi.ConnectorTableMetadata;
import com.facebook.presto.spi.PrestoException;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.sql.analyzer.SemanticException;
import com.facebook.presto.sql.tree.ColumnDefinition;
import com.facebook.presto.sql.tree.CreateTable;
import com.facebook.presto.sql.tree.Expression;
import com.facebook.presto.sql.tree.LikeClause;
import com.facebook.presto.sql.tree.TableElement;
import com.facebook.presto.transaction.TransactionManager;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.facebook.presto.metadata.MetadataUtil.createQualifiedObjectName;
import static com.facebook.presto.spi.StandardErrorCode.GENERIC_INTERNAL_ERROR;
import static com.facebook.presto.spi.StandardErrorCode.NOT_FOUND;
import static com.facebook.presto.spi.type.TypeSignature.parseTypeSignature;
import static com.facebook.presto.sql.analyzer.SemanticErrorCode.DUPLICATE_COLUMN_NAME;
import static com.facebook.presto.sql.analyzer.SemanticErrorCode.MISSING_CATALOG;
import static com.facebook.presto.sql.analyzer.SemanticErrorCode.MISSING_TABLE;
import static com.facebook.presto.sql.analyzer.SemanticErrorCode.NOT_SUPPORTED;
import static com.facebook.presto.sql.analyzer.SemanticErrorCode.TABLE_ALREADY_EXISTS;
import static com.facebook.presto.sql.analyzer.SemanticErrorCode.TYPE_MISMATCH;
import static com.facebook.presto.type.UnknownType.UNKNOWN;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.util.concurrent.Futures.immediateFuture;

public class CreateTableTask
        implements DataDefinitionTask<CreateTable>
{
    @Override
    public String getName()
    {
        return "CREATE TABLE";
    }

    @Override
    public String explain(CreateTable statement, List<Expression> parameters)
    {
        return "CREATE TABLE " + statement.getName();
    }

    @Override
    public ListenableFuture<?> execute(CreateTable statement, TransactionManager transactionManager, Metadata metadata, AccessControl accessControl, QueryStateMachine stateMachine, List<Expression> parameters)
    {
        return internalExecute(statement, metadata, accessControl, stateMachine.getSession(), parameters);
    }

    @VisibleForTesting
    public ListenableFuture<?> internalExecute(CreateTable statement, Metadata metadata, AccessControl accessControl, Session session, List<Expression> parameters)
    {
        checkArgument(!statement.getElements().isEmpty(), "no columns for table");

        QualifiedObjectName tableName = createQualifiedObjectName(session, statement, statement.getName());

        LinkedHashMap<String, ColumnMetadata> columns = new LinkedHashMap<>();
        Map<String, Object> inheritedProperties = new HashMap<>();
        prepareColumnMetadata(statement, metadata, session, tableName, columns, inheritedProperties);

        Optional<TableHandle> tableHandle = metadata.getTableHandle(session, tableName);
        if (tableHandle.isPresent()) {
            TableMetadata tableMetadata = metadata.getTableMetadata(session, tableHandle.get());
            if (!statement.isNotExists()) {
                if (columnsEquals(columns.values(), tableMetadata.getColumns())) {
                    throw new SemanticException(TABLE_ALREADY_EXISTS, statement, "Table '%s' already exists", tableName);
                }
                throw new SemanticException(
                        TABLE_ALREADY_EXISTS,
                        statement,
                        "Table '%s' already exists with a different schema",
                        tableName);
            }
            return immediateFuture(null);
        }

        accessControl.checkCanCreateTable(session.getRequiredTransactionId(), session.getIdentity(), tableName);

        ConnectorId connectorId = metadata.getCatalogHandle(session, tableName.getCatalogName())
                .orElseThrow(() -> new PrestoException(NOT_FOUND, "Catalog does not exist: " + tableName.getCatalogName()));

        Map<String, Object> properties = metadata.getTablePropertyManager().getProperties(
                connectorId,
                tableName.getCatalogName(),
                statement.getProperties(),
                session,
                metadata,
                parameters);

        Map<String, Object> finalProperties = combineProperties(statement.getProperties().keySet(), properties, inheritedProperties);

        ConnectorTableMetadata tableMetadata = new ConnectorTableMetadata(tableName.asSchemaTableName(), ImmutableList.copyOf(columns.values()), finalProperties, statement.getComment());

        if (statement.isNotExists()) {
            metadata.createTable(session, tableName.getCatalogName(), tableMetadata, false);
        }
        else {
            metadata.createTable(session, tableName.getCatalogName(), tableMetadata);
        }

        return immediateFuture(null);
    }

    private boolean columnsEquals(Collection<ColumnMetadata> createTableColumns, Collection<ColumnMetadata> metadataColumns)
    {
        if (createTableColumns == null) {
            return metadataColumns == null;
        }

        if (metadataColumns == null) {
            return createTableColumns == null;
        }

        for (ColumnMetadata columnMetadata : metadataColumns) {
            if (!columnMetadata.isHidden() && !createTableColumns.contains(columnMetadata)) {
                return false;
            }
        }

        return true;
    }

    private void prepareColumnMetadata(
            CreateTable statement,
            Metadata metadata,
            Session session,
            QualifiedObjectName tableName,
            LinkedHashMap<String, ColumnMetadata> columns,
            Map<String, Object> inheritedProperties)
    {
        boolean includingProperties = false;
        for (TableElement element : statement.getElements()) {
            if (element instanceof ColumnDefinition) {
                ColumnDefinition column = (ColumnDefinition) element;
                String name = column.getName().getValue().toLowerCase(Locale.ENGLISH);
                Type type = metadata.getType(parseTypeSignature(column.getType()));
                if ((type == null) || type.equals(UNKNOWN)) {
                    throw new SemanticException(TYPE_MISMATCH, column, "Unknown type for column '%s' ", column.getName());
                }
                if (columns.containsKey(name)) {
                    throw new SemanticException(DUPLICATE_COLUMN_NAME, column, "Column name '%s' specified more than once", column.getName());
                }
                columns.put(name, new ColumnMetadata(name, type, column.getComment().orElse(null), false));
            }
            else if (element instanceof LikeClause) {
                LikeClause likeClause = (LikeClause) element;
                QualifiedObjectName likeTableName = createQualifiedObjectName(session, statement, likeClause.getTableName());
                if (!metadata.getCatalogHandle(session, likeTableName.getCatalogName()).isPresent()) {
                    throw new SemanticException(MISSING_CATALOG, statement, "LIKE table catalog '%s' does not exist", likeTableName.getCatalogName());
                }
                if (!tableName.getCatalogName().equals(likeTableName.getCatalogName())) {
                    throw new SemanticException(NOT_SUPPORTED, statement, "LIKE table across catalogs is not supported");
                }
                TableHandle likeTable = metadata.getTableHandle(session, likeTableName)
                        .orElseThrow(() -> new SemanticException(MISSING_TABLE, statement, "LIKE table '%s' does not exist", likeTableName));

                TableMetadata likeTableMetadata = metadata.getTableMetadata(session, likeTable);

                Optional<LikeClause.PropertiesOption> propertiesOption = likeClause.getPropertiesOption();
                if (propertiesOption.isPresent() && propertiesOption.get().equals(LikeClause.PropertiesOption.INCLUDING)) {
                    if (includingProperties) {
                        throw new SemanticException(NOT_SUPPORTED, statement, "Only one LIKE clause can specify INCLUDING PROPERTIES");
                    }
                    includingProperties = true;
                    inheritedProperties.putAll(likeTableMetadata.getMetadata().getProperties());
                }

                likeTableMetadata.getColumns().stream()
                        .filter(column -> !column.isHidden())
                        .forEach(column -> {
                            if (columns.containsKey(column.getName().toLowerCase())) {
                                throw new SemanticException(DUPLICATE_COLUMN_NAME, element, "Column name '%s' specified more than once", column.getName());
                            }
                            columns.put(column.getName().toLowerCase(), column);
                        });
            }
            else {
                throw new PrestoException(GENERIC_INTERNAL_ERROR, "Invalid TableElement: " + element.getClass().getName());
            }
        }
    }

    private static Map<String, Object> combineProperties(Set<String> specifiedPropertyKeys, Map<String, Object> defaultProperties, Map<String, Object> inheritedProperties)
    {
        Map<String, Object> finalProperties = new HashMap<>();
        finalProperties.putAll(inheritedProperties);
        for (Map.Entry<String, Object> entry : defaultProperties.entrySet()) {
            if (specifiedPropertyKeys.contains(entry.getKey()) || !finalProperties.containsKey(entry.getKey())) {
                finalProperties.put(entry.getKey(), entry.getValue());
            }
        }
        return finalProperties;
    }
}
