package ru.snapix.library

import io.github.crackthecodeabhi.kreds.connection.AbstractKredsSubscriber
import org.junit.jupiter.api.Test
import ru.snapix.library.redis.async
import ru.snapix.library.redis.redisClient
import ru.snapix.library.redis.subscribe

class NativeSqlTest {
    @Test
    fun `Test value`() {
       val np = SnapiLibrary.globalDatabase.executeQuery("SELECT * FROM `account_levels` WHERE `playername` = '${"Dgksf"}'") {
           it.getInt("lastlevel")
       }.firstOrNull() ?: 0
        println(np)
    }
}