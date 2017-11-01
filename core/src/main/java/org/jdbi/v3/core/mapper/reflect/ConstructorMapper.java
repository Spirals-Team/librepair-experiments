/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdbi.v3.core.mapper.reflect;

import static org.jdbi.v3.core.mapper.reflect.JdbiConstructors.findConstructorFor;

import java.beans.ConstructorProperties;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jdbi.v3.core.mapper.ColumnMapper;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.mapper.RowMapperFactory;
import org.jdbi.v3.core.statement.StatementContext;

/**
 * A row mapper which maps the fields in a result set into a constructor. The default implementation will perform a
 * case insensitive mapping between the constructor parameter names and the column labels,
 * also considering camel-case to underscores conversion.
 * <p>
 * Currently the constructor must have exactly the same number of columns as the result set, and
 * the mapping must be one-to-one.  These restrictions may be reconsidered at a later time.
 */
public class ConstructorMapper<T> implements RowMapper<T>
{
    /**
     * Use the only declared constructor to map a class.
     *
     * @param clazz the class to find a constructor of
     * @return the factory
     */
    public static RowMapperFactory factory(Class<?> clazz) {
        return RowMapperFactory.of(clazz, ConstructorMapper.of(clazz));
    }

    /**
     * Use the only declared constructor to map a class.
     *
     * @param clazz the class to find a constructor of
     * @param prefix a prefix for the parameter names
     * @return the factory
     */
    public static RowMapperFactory factory(Class<?> clazz, String prefix) {
        return RowMapperFactory.of(clazz, ConstructorMapper.of(clazz, prefix));
    }

    /**
     * Use a {@code Constructor<T>} to map its declaring type.
     *
     * @param constructor the constructor to invoke
     * @return the factory
     */
    public static RowMapperFactory factory(Constructor<?> constructor) {
        return RowMapperFactory.of(constructor.getDeclaringClass(), ConstructorMapper.of(constructor));
    }

    /**
     * Use a {@code Constructor<T>} to map its declaring type.
     *
     * @param constructor the constructor to invoke
     * @param prefix a prefix to the constructor parameter names
     * @return the factory
     */
    public static RowMapperFactory factory(Constructor<?> constructor, String prefix) {
        return RowMapperFactory.of(constructor.getDeclaringClass(), ConstructorMapper.of(constructor, prefix));
    }

    /**
     * Return a ConstructorMapper for the given type.
     *
     * @param <T>  the type to map
     * @param type the mapped type
     * @return the mapper
     */
    public static <T> RowMapper<T> of(Class<T> type) {
        return ConstructorMapper.of(findConstructorFor(type));
    }

    /**
     * Return a ConstructorMapper for the given type and prefix.
     *
     * @param <T>    the type to map
     * @param type   the mapped type
     * @param prefix the column name prefix
     * @return the mapper
     */
    public static <T> RowMapper<T> of(Class<T> type, String prefix) {
        return ConstructorMapper.of(findConstructorFor(type), prefix);
    }

    /**
     * Return a ConstructorMapper using the given constructor
     *
     * @param <T> the type to map
     * @param constructor the constructor to be used in mapping
     * @return the mapper
     */
    public static <T> RowMapper<T> of(Constructor<T> constructor) {
        return ConstructorMapper.of(constructor, DEFAULT_PREFIX);
    }

    /**
     * Instantiate a ConstructorMapper using the given constructor and prefix
     *
     * @param <T> the type to map
     * @param constructor the constructor to be used in mapping
     * @param prefix      the column name prefix
     * @return the mapper
     */
    public static <T> RowMapper<T> of(Constructor<T> constructor, String prefix) {
        return new ConstructorMapper<>(constructor, prefix);
    }

    static final String DEFAULT_PREFIX = "";

    private final Constructor<T> constructor;
    private final String prefix;
    private final ConstructorProperties constructorProperties;

    private ConstructorMapper(Constructor<T> constructor, String prefix) {
        this.constructor = constructor;
        this.prefix = prefix;
        this.constructorProperties = constructor.getAnnotation(ConstructorProperties.class);
    }

    @Override
    public T map(ResultSet rs, StatementContext ctx) throws SQLException {
        return specialize(rs, ctx).map(rs, ctx);
    }

    @Override
    public RowMapper<T> specialize(ResultSet rs, StatementContext ctx) throws SQLException {
        final ResultSetMetaData metadata = rs.getMetaData();
        final List<String> columnNames = new ArrayList<>(metadata.getColumnCount());

        for (int i = 1; i <= metadata.getColumnCount(); ++i) {
            columnNames.add(metadata.getColumnLabel(i));
        }

        final int columns = constructor.getParameterCount();

        if (columns > columnNames.size()) {
            throw new IllegalStateException(columnNames.size() +
                    " columns in result set, but constructor takes " +
                    constructor.getParameterCount());
        }

        List<ColumnNameMatcher> columnNameMatchers = ctx.getConfig(ReflectionMappers.class).getColumnNameMatchers();

        final int[] columnMap = new int[columns];
        final ColumnMapper<?>[] mappers = new ColumnMapper<?>[columns];

        for (int i = 0; i < columns; i++) {
            final Type type = constructor.getGenericParameterTypes()[i];
            final String paramName = paramName(constructor.getParameters(), i, constructorProperties);
            final int columnIndex = columnIndexForParameter(columnNames, paramName, columnNameMatchers);

            mappers[i] = ctx.findColumnMapperFor(type)
                    .orElseThrow(() -> new IllegalArgumentException(String.format(
                            "Could not find column mapper for type '%s' of parameter '%s' for constructor '%s'",
                            type, paramName, constructor)));
            columnMap[i] = columnIndex;
        }

        return (r, c) -> {
            final Object[] params = new Object[columns];
            for (int i = 0; i < columns; i++) {
                params[i] = mappers[i].map(r, columnMap[i] + 1, c);
            }
            try {
                return constructor.newInstance(params);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                if (e.getCause() instanceof RuntimeException) {
                    throw (RuntimeException) e.getCause();
                }
                if (e.getCause() instanceof Error) {
                    throw (Error) e.getCause();
                }
                throw new RuntimeException(e);
            }
        };
    }

    private int columnIndexForParameter(List<String> columnNames,
                                        String parameterName,
                                        List<ColumnNameMatcher> columnNameMatchers)
    {
        int result = -1;
        for (int i = 0; i < columnNames.size(); i++) {
            String columnName = columnNames.get(i);
            if (prefix.length() > 0) {
                if (columnName.length() > prefix.length() &&
                        columnName.regionMatches(true, 0, prefix, 0, prefix.length())) {
                    columnName = columnName.substring(prefix.length());
                }
                else {
                    continue;
                }
            }

            for (ColumnNameMatcher strategy : columnNameMatchers) {
                if (strategy.columnNameMatches(columnName, parameterName)) {
                    if (result >= 0) {
                        throw new IllegalArgumentException(String.format(
                                "Constructor '%s' parameter '%s' matches multiple " +
                                "columns: '%s' (%d) and '%s' (%d)", constructor,
                                parameterName, columnNames.get(result), result,
                                columnNames.get(i), i));
                    }
                    result = i;
                    break;
                }
            }
        }
        if (result >= 0) {
            return result;
        }
        throw new IllegalArgumentException("Constructor '" + constructor + "' parameter '" +
                parameterName +
                "' has no column in the result set.  Verify that the Java " +
                "compiler is configured to emit parameter names, " +
                "that your result set has the columns expected, " +
                "or annotate the parameter names explicitly with @ColumnName");
    }

    private static String paramName(Parameter[] parameters, int position, ConstructorProperties parameterNames) {
        final Parameter parameter = parameters[position];
        ColumnName dbName = parameter.getAnnotation(ColumnName.class);
        if (dbName != null) {
            return dbName.value();
        }
        if (parameterNames != null) {
            return parameterNames.value()[position];
        }
        return parameter.getName();
    }
}
