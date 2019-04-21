package com.kdk96.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kdk96.database.entity.Company

@Dao
interface CompanyDao {
    @Insert
    fun insertAll(companies: Set<Company>)

    @Query("DELETE FROM COMPANIES")
    fun deleteAll()
}