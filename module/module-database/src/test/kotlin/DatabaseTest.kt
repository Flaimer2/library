package ru.snapix.library.database

import org.junit.jupiter.api.Test
import kotlin.time.measureTime

class DatabaseTest {
    @Test
    fun `Connection Test`() {
        System.setProperty("kotlinx.coroutines.debug", "on" )
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

    @Test
    fun `Database Stress Test`() {
        System.setProperty("kotlinx.coroutines.debug", "on" )
        val database = initializeDatabase {
            host = "localhost"
            port = 3306
            database = "server_global"
            username = "root"
            password = "root"
        }

        val timeTaken = measureTime {
            for (i in 0..200) {
                database.database {
                    execute("CREATE TABLE IF NOT EXISTS table_name(name VARCHAR(255), position VARCHAR(30))")
                    execute("INSERT INTO table_name VALUES (?, ?)", "Flaimer", "first")
                    println(select("SELECT * FROM table_name"))
                    execute("INSERT INTO table_name VALUES (?, ?)", "FGS", "second")
                    println(firstRow("SELECT * FROM table_name"))
                    execute("DROP TABLE table_name")
                }
            }
        }
        println(timeTaken)
    }

    @Test
    fun `Test transaction`() {
        System.setProperty("kotlinx.coroutines.debug", "on" )
        val database = initializeDatabase {
            host = "localhost"
            port = 3306
            database = "server_global"
            username = "root"
            password = "root"
        }
        database.transaction {
            println(firstRow("SELECT * FROM account_coins"))
            println(execute("UPDATE account_coins SET coins = 100000 WHERE player_name = ?", "Danuk16"))
            println(firstRow("SELECT * FROM account_coins"))
            println(firstRow("SELECT * FROM account_sff"))
        }
    }
}