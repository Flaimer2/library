package ru.snapix.library.common.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.intellij.lang.annotations.Language
import ru.snapix.library.common.utils.addDataSourceProperty
import ru.snapix.library.common.utils.forEachIndexed
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException


// TODO: 1. Add kotlin dsl
// TODO: 2. Add kotlin coroutines
// TODO: 3. Add logger
object Database {
    private lateinit var dataSource: HikariDataSource
    private lateinit var connection: Connection

    operator fun invoke(databaseOptions: DatabaseOptions) {
        val hikariConfig = HikariConfig()
        hikariConfig.apply {
            databaseOptions.also {
                maximumPoolSize = it.maximumPoolSize
                driverClassName = "org.mariadb.jdbc.Driver"
                jdbcUrl = "jdbc:mariadb://localhost:3306/${it.database}"
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

    private fun execute(@Language("sql") query: String, vararg parameters: Any) {
        try {
            val ps = connection.prepareStatement(query)

            parameters.forEachIndexed(1) { index, value -> ps.setObject(index, value) }
            ps.execute()
        } catch (e: SQLException) {
            // TODO: log exception
        }
    }

    private fun query(@Language("sql") query: String, vararg parameters: Any): ResultSet {
        val ps = connection.prepareStatement(query)

        parameters.forEachIndexed(1) { index, value -> ps.setObject(index, value) }
        return ps.executeQuery()
    }


}

fun initializeDatabase(block: DatabaseOptions.() -> Unit) {
    val options = DatabaseOptions()
    options.block()
    Database(options)
}

fun database(block: Database.() -> Unit) {
    Database.block()
}