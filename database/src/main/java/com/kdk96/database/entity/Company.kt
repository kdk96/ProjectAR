package com.kdk96.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "COMPANIES")
data class Company(
        @PrimaryKey val id: String,
        val name: String,
        @ColumnInfo(name = "logo_url") val logoUrl: String
)