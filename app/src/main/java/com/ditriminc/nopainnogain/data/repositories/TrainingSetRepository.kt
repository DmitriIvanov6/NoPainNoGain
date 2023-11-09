package com.ditriminc.nopainnogain.data.repositories

import android.util.Log
import androidx.room.Transaction
import com.ditriminc.nopainnogain.data.dao.TrainingSetDao
import com.ditriminc.nopainnogain.data.entities.Exercise
import com.ditriminc.nopainnogain.data.entities.TrainingSet
import javax.inject.Inject

class TrainingSetRepository @Inject constructor(private val trainingSetDao: TrainingSetDao) {

    suspend fun fetchPreviousResultsList(exercise: Exercise): ArrayList<TrainingSet> {
        val prevTrainingSets: ArrayList<TrainingSet> = ArrayList()
        val previousResults = exercise.previousResultsIds
        return if (previousResults.isNotEmpty()) {
            for (prevId in exercise.previousResultsIds) {
                val set = trainingSetDao.findById(prevId)
                if (set != null) {
                    prevTrainingSets.add(set)
                }
            }
            prevTrainingSets
        } else {
            Log.e("HERE", "empty")
            prevTrainingSets
        }
    }



}