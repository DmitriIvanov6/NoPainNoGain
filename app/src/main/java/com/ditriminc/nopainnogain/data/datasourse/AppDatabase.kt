package com.ditriminc.nopainnogain.data.datasourse

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ditriminc.nopainnogain.data.dao.ExerciseDao
import com.ditriminc.nopainnogain.data.dao.MuscleGroupDao
import com.ditriminc.nopainnogain.data.dao.TrainingDayDao
import com.ditriminc.nopainnogain.data.dao.TrainingSetDao
import com.ditriminc.nopainnogain.data.entities.Exercise
import com.ditriminc.nopainnogain.data.entities.MuscleGroup
import com.ditriminc.nopainnogain.data.entities.TrainingDay
import com.ditriminc.nopainnogain.data.entities.TrainingSet
import com.ditriminc.nopainnogain.data.type_converters.Converters

@Database(version = 1, entities = [Exercise::class, MuscleGroup::class, TrainingDay::class, TrainingSet::class])
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao

    abstract fun muscleGroupDao(): MuscleGroupDao

    abstract fun trainingDayDao(): TrainingDayDao

    abstract fun trainingSetDao(): TrainingSetDao

}