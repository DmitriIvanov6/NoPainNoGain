package com.ditriminc.nopainnogain.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ditriminc.nopainnogain.data.entities.TrainingDay
import com.ditriminc.nopainnogain.data.entities.TrainingSet
import com.ditriminc.nopainnogain.data.relations.ExerciseWithTrainingSets


@Dao
interface TrainingSetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(trainingSets: ArrayList<TrainingSet>) : List<Long>

    @Query("SELECT * FROM training_sets")
    suspend fun getAll(): List<TrainingSet>

    @Delete
    fun delete(trainingSet: TrainingSet)

    @Query("SELECT * FROM training_sets WHERE id LIKE :id")
    suspend fun findById(id: Long): TrainingSet?

    @Transaction
    @Query("SELECT * FROM exercises WHERE id = :exerciseId ")
    fun getTrainingSetsForExercise(exerciseId: Long): ExerciseWithTrainingSets

}