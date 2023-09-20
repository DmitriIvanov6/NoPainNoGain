package com.ditriminc.nopainnogain.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscle_groups")
data class MuscleGroup(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String?,
    @ColumnInfo(name = "day_id")
    val dayId: Long?
)


