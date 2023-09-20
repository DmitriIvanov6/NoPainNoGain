package com.ditriminc.nopainnogain.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ditriminc.nopainnogain.data.entities.Exercise
import com.ditriminc.nopainnogain.data.entities.MuscleGroup
import com.ditriminc.nopainnogain.data.relations.GroupWithExercises

@Dao
interface ExerciseDao {

    //todo переделать под упражнение

    @Insert
    suspend fun insertAll(vararg exercise: Exercise)

    @Query("SELECT * FROM exercises")
    suspend fun getAll(): List<Exercise>

    @Update
    fun update(exercise: Exercise)

    @Delete
    fun delete(muscleGroup: MuscleGroup)

    @Query("SELECT * FROM exercises WHERE name LIKE :name")
    fun findByName(name: String): Exercise?

    @Query("SELECT * FROM exercises WHERE id LIKE :id")
    suspend fun findById(id: Long): Exercise?

    @Transaction
    @Query("SELECT * FROM muscle_groups WHERE id = :id")
    suspend fun getGroupWithExercises(id: Long ): GroupWithExercises


}