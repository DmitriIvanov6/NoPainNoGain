package com.ditriminc.nopainnogain.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "training_sets")
data class TrainingSet(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val reps: Int?,
    val weight: Int?,
    @ColumnInfo(name = "exercise_id")
    val exerciseId: Long?,
    @ColumnInfo(name = "time")
    val date: Date?
)
