package org.example.project.database

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): MusicDatabaseQueries {
    val driver = driverFactory.createDriver()
    val d = MusicDatabaseQueries(driver)
    return d
}