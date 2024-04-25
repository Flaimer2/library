package ru.snapix.library.database

class DatabaseOptions {
    var host = "localhost"
    var port = 3306
    var database = "server_global"
    var username = "root"
    var password = "root"
    var maximumPoolSize = 10
    var connectionTimeout = 1000L
    var autoReconnect = true
    var cachePrepStmts = true
    var prepStmtCacheSize = 250
    var prepStmtCacheSqlLimit = 2048
    var useServerPrepStmts = true
    var cacheResultSetMetadata = true
}