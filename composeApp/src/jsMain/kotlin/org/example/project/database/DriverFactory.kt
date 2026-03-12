package org.example.project.database

import app.cash.sqldelight.db.SqlDriver

actual class DriverFactory {

    actual fun createDriver(): SqlDriver {
        throw NotImplementedError("SQLDelight driver not implemented for JS")
    }
}