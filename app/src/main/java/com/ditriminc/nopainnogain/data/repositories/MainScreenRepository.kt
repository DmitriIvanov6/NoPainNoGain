package com.ditriminc.nopainnogain.data.repositories

import com.ditriminc.nopainnogain.data.dao.TrainingDayDao
import com.ditriminc.nopainnogain.data.datasourse.MainLocalDataSource
import com.ditriminc.nopainnogain.data.entities.TrainingDay
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


@ViewModelScoped
class MainScreenRepository @Inject constructor(private val trainingDayDao: TrainingDayDao) {

    suspend fun getTrainingDays(): ArrayList<String> {
        val dayNames = ArrayList<String>();
        val days = trainingDayDao.getAll()
        for (day in days) {
            dayNames.add(day.name ?: "")
        }
        return dayNames
    }


    suspend fun addTrainingDay(dayName: String) {
        //todo watch callbacks and add transactional
        withContext(Dispatchers.IO) {

            if (trainingDayDao.findByName(dayName) == null) {
                trainingDayDao.insertAll(TrainingDay(name = dayName))
            } else {
//                todo implement
            }
        }
    }
    suspend fun getSelectedDayId(dayName: String) : TrainingDay? {
        return withContext(Dispatchers.IO) {
            trainingDayDao.findByName(dayName)
        }
    }



}