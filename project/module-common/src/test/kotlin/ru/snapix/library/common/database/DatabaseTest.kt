package ru.snapix.library.common.database

import kotlin.time.measureTime

class DatabaseTest {
    @org.junit.jupiter.api.Test
    fun `Connection Test`() {
        val database = initializeDatabase {
            host = "localhost"
            port = 3306
            database = "server_global"
            username = "root"
            password = "root"
        }

        val timeTaken = measureTime {
            database.database {
                execute("CREATE TABLE IF NOT EXISTS table_name(name VARCHAR(255), position VARCHAR(30))")
                execute("INSERT INTO table_name VALUES (?, ?)", "Flaimer", "first")
                println(firstColumn("SELECT * FROM table_name"))
                execute("DROP TABLE table_name")
            }
        }

        println(timeTaken)
    }
}