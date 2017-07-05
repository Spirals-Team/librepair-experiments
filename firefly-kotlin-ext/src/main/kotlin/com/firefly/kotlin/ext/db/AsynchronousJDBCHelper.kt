package com.firefly.kotlin.ext.db

import com.firefly.db.DBException
import com.firefly.db.JDBCHelper
import com.firefly.db.MetricReporterFactory
import com.firefly.kotlin.ext.log.Log
import com.firefly.utils.Assert
import kotlinx.coroutines.experimental.future.await
import org.apache.commons.dbutils.BeanProcessor
import org.apache.commons.dbutils.ResultSetHandler
import java.sql.Connection
import javax.sql.DataSource

/**
 * Asynchronous JDBC helper executes SQL using coroutine
 *
 * @author Pengtao Qiu
 */
private val log = Log.getLogger("firefly-system")

open class AsynchronousJDBCHelper(val jdbcHelper: JDBCHelper) {

    constructor(dataSource: DataSource) : this(dataSource, null)

    constructor(dataSource: DataSource,
                metricReporterFactory: MetricReporterFactory?) : this(JDBCHelper(dataSource, metricReporterFactory))

    suspend fun <T> queryForSingleColumn(sql: String, vararg params: Any) = executeSQL { connection, helper ->
        helper.queryForSingleColumn<T>(connection, sql, *params)
    }

    suspend inline fun <reified T> queryForObject(sql: String, vararg params: Any) = executeSQL { connection, helper ->
        helper.queryForObject<T>(connection, sql, T::class.java, *params)
    }

    suspend inline fun <reified T> queryForObject(sql: String, beanProcessor: BeanProcessor, vararg params: Any) = executeSQL { connection, helper ->
        helper.queryForObject<T>(connection, sql, T::class.java, beanProcessor, *params)
    }

    suspend inline fun <reified T> queryById(id: Any) = executeSQL { connection, helper ->
        helper.queryById<T>(connection, T::class.java, id)
    }

    suspend inline fun <K, reified V> queryForBeanMap(sql: String, vararg params: Any): Map<K, V> = executeSQL { connection, helper ->
        helper.queryForBeanMap<K, V>(connection, sql, V::class.java, *params)
    } ?: mapOf()

    suspend inline fun <K, reified V> queryForBeanMap(sql: String, beanProcessor: BeanProcessor, vararg params: Any): Map<K, V> = executeSQL { connection, helper ->
        val t = V::class.java
        val columnName = helper.defaultBeanProcessor.getIdColumnName(t)
        Assert.notNull(columnName)
        helper.queryForBeanMap<K, V>(connection, sql, t, columnName, beanProcessor, *params)
    } ?: mapOf()

    suspend inline fun <reified T> queryForList(sql: String, vararg params: Any): List<T> = executeSQL { connection, helper ->
        helper.queryForList<T>(connection, sql, T::class.java, *params)
    } ?: listOf()

    suspend inline fun <reified T> queryForList(sql: String, beanProcessor: BeanProcessor, vararg params: Any): List<T> = executeSQL { connection, helper ->
        helper.queryForList<T>(connection, sql, T::class.java, beanProcessor, *params)
    } ?: listOf()

    suspend fun update(sql: String, vararg params: Any) = executeSQL { connection, helper ->
        helper.update(connection, sql, *params)
    } ?: -1

    suspend fun <T> updateObject(obj: T) = executeSQL { connection, helper ->
        helper.updateObject(connection, obj)
    } ?: -1

    suspend fun <R> insert(sql: String, vararg params: Any) = executeSQL { connection, helper ->
        helper.insert<R>(connection, sql, *params)
    }

    suspend fun <T, R> insertObject(obj: T) = executeSQL { connection, helper ->
        helper.insertObject<R>(connection, obj)
    }

    suspend inline fun <reified T, R> insertObjectBatch(list: List<T>, rsh: ResultSetHandler<R>) = executeSQL { connection, helper ->
        helper.insertObjectBatch(connection, rsh, T::class.java, list)
    }

    suspend inline fun <reified T, reified R> insertObjectBatch(list: List<T>) = insertObjectBatch(list, ResultSetHandler<List<R>> { rs ->
        val ret = mutableListOf<R>()
        while (rs.next()) {
            val element = rs.getObject(1)
            if (element is R) {
                ret.add(element)
            }
        }
        ret
    })

    suspend inline fun <reified T> deleteById(id: Any) = executeSQL { connection, helper ->
        helper.deleteById(connection, T::class.java, id)
    } ?: -1

    suspend fun batch(sql: String, params: Array<Array<Any>>) = executeSQL { connection, helper ->
        try {
            helper.runner.batch(connection, sql, params)
        } catch (e: Exception) {
            log.error("batch exception", e)
            throw DBException(e)
        }
    }

    suspend fun <T> executeSQL(func: (Connection, JDBCHelper) -> T): T? {
        val r: T? = jdbcHelper.connection.use {
            jdbcHelper.async(it, func).await()
        }
        return r
    }

}