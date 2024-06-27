package ru.snapix.library

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.ResultSet

fun <T:Any> Database.executeQuery(query: String, transform : (ResultSet) -> T): List<T> {
    val result = arrayListOf<T>()
    transaction(this) {
        exec(query) { rs ->
            while (rs.next()) {
                result += transform(rs)
            }
        }
    }
    return result
}
