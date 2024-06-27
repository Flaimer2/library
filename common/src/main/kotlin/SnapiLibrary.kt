package ru.snapix.library

import org.jetbrains.exposed.sql.Database

object SnapiLibrary {
    val globalDatabase = Database.connect(
        url = "jdbc:mariadb://127.0.0.1:3306/server_global",
        driver = "org.mariadb.jdbc.Driver",
        user = "root",
        password = "root"
    )
    var platform: Platform? = null
}