package ru.snapix.library.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import org.intellij.lang.annotations.Language
import java.sql.Connection
import java.sql.SQLException

class Database(databaseOptions: DatabaseOptions) {
    internal var scope = CoroutineScope(CoroutineName("Database"))
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

    fun execute(@Language("sql") query: String, vararg parameters: Any) {
        val ps = connection.prepareStatement(query)
        parameters.forEachIndexed(1) { index, value -> ps.setObject(index, value) }
        ps.execute()
    }

    suspend fun select(@Language("sql") query: String, vararg parameters: Any) = flow {
        val ps = connection.prepareStatement(query)
        parameters.forEachIndexed(1) { index, value -> ps.setObject(index, value) }

        val resultSet = ps.executeQuery()
        while (resultSet.next()) {
            val resultMetadata = resultSet.metaData
            val dbRow = DbRow()
            for (i in 1.. resultMetadata.columnCount) {
                dbRow[resultMetadata.getColumnLabel(i)] = resultSet.getObject(i)
            }
            emit(dbRow)
        }
    }

    suspend fun firstRow(@Language("sql") query: String, vararg parameters: Any): DbRow? {
        return select(query, *parameters).firstOrNull()
    }

    suspend fun firstColumn(@Language("sql") query: String, vararg parameters: Any): Any? {
        return firstRow(query, *parameters)?.values?.firstOrNull()
    }

    fun transaction(block: suspend Database.() -> Unit) {
        runBlocking {
            try {
                val save = connection.setSavepoint()
                try {
                    connection.autoCommit = false
                    block()
                    connection.commit()
                    connection.autoCommit = true
                } catch (e: Exception) {
                    e.printStackTrace()
                    connection.rollback(save)
                    connection.autoCommit = true
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }
}

fun initializeDatabase(block: DatabaseOptions.() -> Unit): Database {
    val options = DatabaseOptions()
    options.block()
    return Database(options)
}

fun Database.useAsync(dispatcher: CoroutineDispatcher = Dispatchers.Main, block: suspend Database.() -> Unit) {
    CoroutineScope(dispatcher).async { block() }
}

fun <V> Database.async(block: suspend Database.() -> V): V = runBlocking {
    async { block() }.await()
}

@Deprecated("Not used anymore", level = DeprecationLevel.ERROR, replaceWith = ReplaceWith("useAsync { block() }"))
fun Database.database(block: suspend Database.() -> Unit): Database {
    return apply {
        runBlocking {
            block()
        }
    }
}
