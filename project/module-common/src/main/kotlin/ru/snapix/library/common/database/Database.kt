package ru.snapix.library.common.database

import org.intellij.lang.annotations.Language
import org.mariadb.jdbc.Configuration
import org.mariadb.jdbc.pool.Pool
import org.mariadb.jdbc.pool.Pools
import ru.snapix.library.common.utils.forEachIndexed
import java.sql.ResultSet
import java.sql.SQLException

// TODO: 1. Add kotlin dsl
// TODO: 2. Add kotlin coroutines
// TODO: 3. Add logger
object Database {
    private lateinit var pool: Pool

    operator fun invoke(databaseOptions: DatabaseOptions) {
        databaseOptions.apply {
            val configuration = Configuration.parse("jdbc:mariadb://$host:$port/$database?$options")
            pool = Pools.retrievePool(configuration.clone(username, password))
        }
    }

    fun execute(@Language("sql") query: String, vararg parameters: Any) {
        try {
            val connection = pool.poolConnection.connection
            val ps = connection.prepareStatement(query)

            parameters.forEachIndexed(1) { index, value -> ps.setObject(index, value) }
            ps.execute()
        } catch (e: SQLException) {
            // TODO: log exception
        }
    }

    fun query(@Language("sql") query: String, vararg parameters: Any): ResultSet? {
        val result: ResultSet? = try {
            val connection = pool.poolConnection.connection
            val ps = connection.prepareStatement(query)

            parameters.forEachIndexed(1) { index, value -> ps.setObject(index, value) }

            ps.executeQuery()
        } catch (e: SQLException) {
            // TODO: log exception
            null
        }
        return result
    }
}