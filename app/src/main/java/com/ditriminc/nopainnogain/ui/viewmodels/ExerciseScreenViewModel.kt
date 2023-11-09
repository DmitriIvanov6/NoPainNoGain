package com.ditriminc.nopainnogain.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ditriminc.nopainnogain.data.entities.TrainingSet
import com.ditriminc.nopainnogain.data.repositories.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

data class ExerciseScreenUiState(
    val groupId: MutableState<Long> = mutableStateOf(-1),
    val exerciseNames: ArrayList<String> = ArrayList(mutableListOf("")),
    val showAddExerciseDialog: MutableState<Boolean> = mutableStateOf(false),
    val newExerciseToAddName: MutableState<String> = mutableStateOf(""),
    val selectedExerciseId: MutableState<Long> = mutableStateOf(-1L)
)

@HiltViewModel
class ExerciseScreenViewModel @Inject constructor(private val repository: ExerciseRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ExerciseScreenUiState())
    val uiState: StateFlow<ExerciseScreenUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null
    private var addNewExerciseJob: Job? = null
    private var getExerciseIdJob: Job? = null

    init {
        viewModelScope.launch { }
        Log.e("MainVM", "init")
    }


    fun fetchGroupExercises(groupId: Long) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val exerciseList = repository.getExercisesOfGroupSelected(groupId)
                _uiState.update {
                    it.copy(exerciseNames = exerciseList)
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
        getExerciseIdJob?.cancel()
        getExerciseIdJob = viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        selectedExerciseId = mutableStateOf(repository.getSelectedExerciseId(groupName)!!.id)
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

    fun addNewExercise(exerciseName: String, groupId: Long) {
        addNewExerciseJob?.cancel()
        addNewExerciseJob = viewModelScope.launch {
            repository.addExercise(
                exerciseName,
                groupId,
                ArrayList(),
                0,
                ""
            )
        }
    }
}