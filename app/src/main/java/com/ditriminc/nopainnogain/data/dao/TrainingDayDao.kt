package com.ditriminc.nopainnogain.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ditriminc.nopainnogain.data.entities.Exercise
import com.ditriminc.nopainnogain.data.entities.TrainingDay


@Dao
interface TrainingDayDao {

    @Insert
    suspend fun insertAll(vararg trainingDay: TrainingDay)

    @Query("SELECT * FROM training_days")
    suspend fun getAll(): List<TrainingDay>

    @Delete
    fun delete(trainingDay: TrainingDay)

    @Query("SELECT * FROM training_days WHERE name LIKE :name")
    fun findByName(name: String): TrainingDay?

}