package com.ditriminc.nopainnogain.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.ditriminc.nopainnogain.data.entities.Exercise
import com.ditriminc.nopainnogain.data.entities.MuscleGroup
import com.ditriminc.nopainnogain.data.entities.TrainingDay
import com.ditriminc.nopainnogain.data.entities.TrainingSet

data class DayWithMuscleGroups(
    @Embedded val trainingDay: TrainingDay,
    @Relation(
        parentColumn = "id",
        entityColumn = "day_id"
    )
    val muscleGroups: List<MuscleGroup>
)

data class GroupWithExercises(
    @Embedded val muscleGroup: MuscleGroup,
    @Relation(
        parentColumn = "id",
        entityColumn = "group_id"
    )
    val exercisesList: List<Exercise>
)

data class ExerciseWithTrainingSets(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "id",
        entityColumn = "exercise_id"
    )
    val trainingSetList: List<TrainingSet>
)