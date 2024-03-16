package ru.snapix.library.common.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.intellij.lang.annotations.Language
import ru.snapix.library.common.utils.addDataSourceProperty
import ru.snapix.library.common.utils.forEachIndexed
import java.sql.Connection

class Database(databaseOptions: DatabaseOptions) {
    private var dataSource: HikariDataSource
    private var connection: Connection

    init {
        val hikariConfig = HikariConfig()
        hikariConfig.apply {
            databaseOptions.also {
                maximumPoolSize = it.maximumPoolSize
                driverClassName = "org.mariadb.jdbc.Driver"
                jdbcUrl = "jdbc:mariadb://${it.host}:${it.port}/${it.database}"
                username = it.username
                password = it.password
                connectionTimeout = it.connectionTimeout
                addDataSourceProperty(
                    "autoReconnect" to it.autoReconnect,
                    "cachePrepStmts" to it.cachePrepStmts,
                    "prepStmtCacheSize" to it.prepStmtCacheSize,
                    "prepStmtCacheSqlLimit" to it.prepStmtCacheSqlLimit,
                    "useServerPrepStmts" to it.useServerPrepStmts,
                    "cacheResultSetMetadata" to it.cacheResultSetMetadata,
                )
            }
        }
        dataSource = HikariDataSource(hikariConfig)
        connection = dataSource.connection
    }

    fun close() {
        dataSource.close()
    }

    @Suppress("DeferredResultUnused", "SqlSourceToSinkFlow")
    suspend fun execute(@Language("sql") query: String, vararg parameters: Any) {
        coroutineScope {
            async {
                val ps = connection.prepareStatement(query)
                parameters.forEachIndexed(1) { index, value -> ps.setObject(index, value) }
                ps.execute()
            }
        }
    }

    @Suppress("SqlSourceToSinkFlow")
    suspend fun query(@Language("sql") query: String, vararg parameters: Any) = flow {
        val ps = connection.prepareStatement(query)
        parameters.forEachIndexed(1) { index, value -> ps.setObject(index, value) }

        val resultSet = ps.executeQuery()
        while (resultSet.next()) {
            val resultMetadata = resultSet.metaData
            val dbRow = DbRow()
            for (i in 1 until resultMetadata.columnCount) {
                dbRow[resultMetadata.getColumnLabel(i)] = resultSet.getObject(i)
            }
            emit(dbRow)
        }
    }

    suspend fun firstRowQuery(@Language("sql") query: String, vararg parameters: Any): DbRow? {
        if (parameters.isEmpty()) {
            return query(query).firstOrNull()
        }
        return query(query).firstOrNull()
    }

    suspend fun firstColumnQuery(@Language("sql") query: String, vararg parameters: Any): Any? {
        if (parameters.isEmpty()) {
            return firstRowQuery(query)?.values?.firstOrNull()
        }
        return firstRowQuery(query, parameters)?.values?.firstOrNull()
    }
}

fun initializeDatabase(block: DatabaseOptions.() -> Unit): Database {
    val options = DatabaseOptions()
    options.block()
    return Database(options)
}

fun Database.database(block: suspend Database.() -> Unit) {
    runBlocking {
        this@database.block()
    }
}