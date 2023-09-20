package com.ditriminc.nopainnogain.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ditriminc.nopainnogain.data.repositories.GroupScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


data class MuscleGroupUiState(
    val dayId: MutableState<Long> = mutableStateOf(-1),
    val groupNames: ArrayList<String> = ArrayList(mutableListOf(" ")),
    val showAddGroupDialog: MutableState<Boolean> = mutableStateOf(false),
    val newGroupToAddName: MutableState<String> = mutableStateOf(""),
    val selectedGroupId: MutableState<Long> = mutableStateOf(-1L)
)

@HiltViewModel
class GroupScreenViewModel @Inject constructor(private val repository: GroupScreenRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(MuscleGroupUiState())
    val uiState: StateFlow<MuscleGroupUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null
    private var addNewGroupJob: Job? = null
    private var getGroupIdJob: Job? = null

    init {
        viewModelScope.launch { }
        Log.e("MainVM", "init")
    }


    fun fetchDayGroups(dayId: Long) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val groupList = repository.getGroupOfDaySelected(dayId)
                _uiState.update {
                    it.copy(groupNames = groupList)
                }
            } catch (ioe: IOException) {
                // Handle the error and notify the UI when appropriate.
//                _uiState.update {
//                    val messages = getMessagesFromThrowable(ioe)
//                    it.copy(userMessages = messages)
//                }
                Log.e("fetchList", "something went wrong")
            }
        }


    }

    //todo возможно фиксировать айди дня, тогда нет необходимости в уникальном нейминге
    fun getSelectedGroupId(groupName: String) {
        getGroupIdJob?.cancel()
        getGroupIdJob = viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        selectedGroupId = mutableStateOf(repository.getSelectedGroupId(groupName)!!.id)
                    )
                }
            } catch (ioe: IOException) {
                //todo Handle the error and notify the UI when appropriate.
//                _uiState.update {
//                    val messages = getMessagesFromThrowable(ioe)
//                    it.copy(userMessages = messages)
//                }
                Log.e("getList", "something went wrong")
            }
        }
    }

    fun addNewGroup(groupName: String, dayId: Long) {
        Log.e("ADDNEWGroup", "HERE");
        addNewGroupJob?.cancel()
        addNewGroupJob = viewModelScope.launch {
            repository.addGroup(
                groupName,
                dayId
            ) //todo проверить, как будет работать, возможно реализовать колбэки
        }
    }


}

