package ru.snapix.library.common.database

class DatabaseOptions {
    var host = "localhost"
    var port = 3306
    var database = "server_global"
    var username = "root"
    var password = "root"
    var options = ""
}
fun databaseOptions(init: DatabaseOptions.() -> Unit): DatabaseOptions {
    val options = DatabaseOptions()
    options.init()
    return options
}