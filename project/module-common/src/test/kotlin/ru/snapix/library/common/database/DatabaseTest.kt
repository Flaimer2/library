package ru.snapix.library.common.database

import java.util.concurrent.TimeUnit

class DatabaseTest {
    @org.junit.jupiter.api.Test
    fun connection() {
        initializeDatabase {
            host = "localhost"
            port = 3306
            database = "server_global"
            username = "root"
            password = "root"
        }
        database {
            execute("CREATE TABLE table_name(name VARCHAR(255), position VARCHAR(30))")
            execute("INSERT INTO table_name VALUES (?, ?)", "Flaimer", "first")
            val result = query("SELECT * FROM table_name LIMIT 1")
            while (result.next()) {
                println(result.getString(2))
            }
            execute("DROP TABLE table_name")
        }
    }
}