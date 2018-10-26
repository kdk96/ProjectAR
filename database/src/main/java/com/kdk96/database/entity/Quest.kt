package com.kdk96.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "QUESTS",
        foreignKeys = [
            ForeignKey(
                    entity = Company::class,
                    parentColumns = ["id"],
                    childColumns = ["company_id"],
                    onDelete = CASCADE,
                    onUpdate = CASCADE
            )
        ])
data class Quest(
        @PrimaryKey val id: String,
        val title: String,
        @ColumnInfo(name = "start_time") val startTime: Long,
        @ColumnInfo(name = "company_id") val companyId: String
)