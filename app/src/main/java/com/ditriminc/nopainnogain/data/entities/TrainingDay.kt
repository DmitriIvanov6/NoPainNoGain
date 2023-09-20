package com.ditriminc.nopainnogain.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "training_days")
data class TrainingDay(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String?
)
