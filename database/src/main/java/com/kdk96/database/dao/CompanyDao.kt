package com.kdk96.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.kdk96.database.entity.Company

@Dao
interface CompanyDao {
    @Insert
    fun insertAll(companies: Set<Company>)

    @Query("DELETE FROM COMPANIES")
    fun deleteAll()
}