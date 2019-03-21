package com.kdk96.database.entity

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE

@Entity(tableName = "QUESTS",
        foreignKeys = [
            ForeignKey(
                    entity = Company::class,
                    parentColumns = ["id"],
                    childColumns = ["company_id"],
                    onDelete = CASCADE,
                    onUpdate = CASCADE
            )
        ],
        indices = [Index("company_id")])
data class Quest(
        @PrimaryKey val id: String,
        val title: String,
        @ColumnInfo(name = "start_time") val startTime: Long,
        @ColumnInfo(name = "company_id") val companyId: String
)