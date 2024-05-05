package com.ditriminc.nopainnogain.data.repositories

import com.ditriminc.nopainnogain.data.dao.MuscleGroupDao
import com.ditriminc.nopainnogain.data.entities.MuscleGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GroupScreenRepository @Inject constructor(private val muscleGroupDao: MuscleGroupDao) {

    suspend fun addGroup(groupName: String, dayId: Long) {
        //todo watch callbacks and add transactional
        withContext(Dispatchers.IO) {
            if (muscleGroupDao.findByName(groupName) == null) {
                muscleGroupDao.insertAll(MuscleGroup(name = groupName, dayId = dayId))
            } else {
                //todo implement
            }
        }

    }

    suspend fun getGroupOfDaySelected(dayId: Long): ArrayList<String> {
        val dayWithGroups = muscleGroupDao.getDaysWithGroups(dayId)
        val groupNameList = ArrayList<String>()
        for (group in dayWithGroups?.muscleGroups ?: emptyList()) {
            groupNameList.add(group.name ?: "")
        }
        return groupNameList
    }

    suspend fun getSelectedGroupId(groupName: String): MuscleGroup? {
        return withContext(Dispatchers.IO) {
            muscleGroupDao.findByName(groupName)
        }
    }
}