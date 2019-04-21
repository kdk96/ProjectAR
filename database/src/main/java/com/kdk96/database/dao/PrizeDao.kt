package com.kdk96.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.kdk96.database.entity.Prize

@Dao
interface PrizeDao {
    @Insert
    fun insertAll(prizes: List<Prize>)
}