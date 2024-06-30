package ru.snapix.library

import org.junit.jupiter.api.Test

class NativeSqlTest {
    @Test
    fun `Test value`() {
        val np =
            SnapiLibrary.globalDatabase.executeQuery("SELECT * FROM `account_levels` WHERE `playername` = '${"Dgksf"}'") {
                it.getInt("lastlevel")
            }.firstOrNull() ?: 0
        println(np)
    }
}