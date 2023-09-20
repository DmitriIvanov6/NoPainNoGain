package com.ditriminc.nopainnogain.data.repositories

import androidx.compose.ui.res.colorResource
import androidx.room.withTransaction
import com.ditriminc.nopainnogain.data.dao.ExerciseDao
import com.ditriminc.nopainnogain.data.dao.TrainingSetDao
import com.ditriminc.nopainnogain.data.datasourse.AppDatabase
import com.ditriminc.nopainnogain.data.entities.Exercise
import com.ditriminc.nopainnogain.data.entities.TrainingSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val trainingSetDao: TrainingSetDao,
    private val db: AppDatabase
) {

    suspend fun addExercise(name: String, groupId: Long, lastResultIds: ArrayList<Long>) {
        //todo watch callbacks and add transactional
        withContext(Dispatchers.IO) {
            if (exerciseDao.findByName(name) == null) {
                exerciseDao.insertAll(
                    Exercise(
                        name = name,
                        groupId = groupId,
                        previousResultsIds = ArrayList()
                    )
                )
            } else {
                //todo implement
            }
        }

    }



    suspend fun getExercisesOfGroupSelected(groupId: Long): ArrayList<String> {
        val groupWithExercises = exerciseDao.getGroupWithExercises(groupId)
        val exerciseNameList = ArrayList<String>()
        for (exercise in groupWithExercises.exercisesList) {
            exerciseNameList.add(exercise.name ?: "")
        }
        return exerciseNameList
    }

    suspend fun getSelectedExerciseId(exerciseName: String): Exercise? {
        return withContext(Dispatchers.IO) {
            exerciseDao.findByName(exerciseName)
        }
    }

    suspend fun getExerciseById(id: Long): Exercise? {
        return exerciseDao.findById(id)
    }

    suspend fun addTrainingSets(trainingSet: ArrayList<TrainingSet>, currentExercise: Exercise?) {
        db.withTransaction {
            currentExercise!!.previousResultsIds.clear()
            val setIDs = trainingSetDao.insertAll(trainingSet)
            currentExercise.previousResultsIds.addAll(setIDs)
            exerciseDao.update(currentExercise)
        }
    }

}