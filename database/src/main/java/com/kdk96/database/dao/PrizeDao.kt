package com.kdk96.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import com.kdk96.database.entity.Prize

@Dao
interface PrizeDao {
    @Insert
    fun insertAll(prizes: List<Prize>)
}