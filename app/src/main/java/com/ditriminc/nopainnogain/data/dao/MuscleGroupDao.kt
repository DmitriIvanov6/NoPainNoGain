package com.ditriminc.nopainnogain.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ditriminc.nopainnogain.data.entities.MuscleGroup
import com.ditriminc.nopainnogain.data.entities.TrainingDay
import com.ditriminc.nopainnogain.data.relations.DayWithMuscleGroups


@Dao
interface MuscleGroupDao {

    @Insert
    suspend fun insertAll(vararg muscleGroup: MuscleGroup)

    @Query("SELECT * FROM muscle_groups")
    suspend fun getAll(): List<MuscleGroup>

    @Delete
    fun delete(muscleGroup: MuscleGroup)

    @Query("SELECT * FROM muscle_groups WHERE name LIKE :name")
    fun findByName(name: String): MuscleGroup?

    @Transaction
    @Query("SELECT * FROM training_days WHERE id = :id")
    suspend fun getDaysWithGroups(id: Long ): DayWithMuscleGroups

}