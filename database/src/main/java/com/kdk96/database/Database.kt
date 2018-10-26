package com.kdk96.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.kdk96.database.dao.CompanyDao
import com.kdk96.database.dao.PrizeDao
import com.kdk96.database.dao.QuestDao
import com.kdk96.database.entity.Company
import com.kdk96.database.entity.Prize
import com.kdk96.database.entity.Quest

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
}