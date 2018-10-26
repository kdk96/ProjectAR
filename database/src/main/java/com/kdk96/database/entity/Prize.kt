package com.kdk96.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "PRIZES",
        foreignKeys = [
            ForeignKey(
                    entity = Quest::class,
                    parentColumns = ["id"],
                    childColumns = ["quest_id"],
                    onDelete = CASCADE,
                    onUpdate = CASCADE
            )
        ])
data class Prize(
        @PrimaryKey val id: String,
        val description: String,
        val place: Int,
        @ColumnInfo(name = "quest_id") val questId: String
)