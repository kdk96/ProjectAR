package com.kdk96.database.entity

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE

@Entity(tableName = "PRIZES",
        foreignKeys = [
            ForeignKey(
                    entity = Quest::class,
                    parentColumns = ["id"],
                    childColumns = ["quest_id"],
                    onDelete = CASCADE,
                    onUpdate = CASCADE
            )
        ],
        indices = [Index("quest_id")])
data class Prize(
        @PrimaryKey val id: String,
        val description: String,
        val place: Int,
        @ColumnInfo(name = "quest_id") val questId: String
)