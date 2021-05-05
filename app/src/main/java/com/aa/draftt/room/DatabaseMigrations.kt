package com.aa.draftt.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class DatabaseMigrations {

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE users")
            }
        }
    }
}