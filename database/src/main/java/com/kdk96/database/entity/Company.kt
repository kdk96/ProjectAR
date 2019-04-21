package com.kdk96.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "COMPANIES")
data class Company(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo(name = "logo_url") val logoUrl: String
)