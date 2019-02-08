package com.kdk96.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.DatabaseConfiguration
import android.arch.persistence.room.RoomDatabase
import android.database.sqlite.SQLiteDatabase
import com.kdk96.database.dao.CompanyDao
import com.kdk96.database.dao.PrizeDao
import com.kdk96.database.dao.QuestDao
import com.kdk96.database.entity.Company
import com.kdk96.database.entity.Prize
import com.kdk96.database.entity.Quest
import java.io.File

@Database(
        entities = [
            Company::class,
            Quest::class,
            Prize::class
        ],
        version = 1,
        exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun companyDao(): CompanyDao
    abstract fun questDao(): QuestDao
    abstract fun prizeDao(): PrizeDao

    override fun init(configuration: DatabaseConfiguration) {
        val dbPath = configuration.context.getDatabasePath(configuration.name).path
        if (dbExists(dbPath)) {
            val db = SQLiteDatabase.openDatabase(dbPath, null, 0)
            db.execSQL("VACUUM")
            db.close()
        }
        super.init(configuration)
    }

    private fun dbExists(dbPath: String) = File(dbPath).exists()
}