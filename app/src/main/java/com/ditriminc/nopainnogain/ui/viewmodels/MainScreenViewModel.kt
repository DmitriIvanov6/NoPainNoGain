package com.ditriminc.nopainnogain.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ditriminc.nopainnogain.data.repositories.MainScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


data class MainScreenUiState(
    val dayNames: ArrayList<String> = ArrayList(mutableListOf(" ")),
    val showAddDayDialog: MutableState<Boolean> = mutableStateOf(false),
    val newDayToAddName: MutableState<String> = mutableStateOf(""),
    val selectedDayId: MutableState<Long> = mutableStateOf(-1L)
)

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val repository: MainScreenRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState: StateFlow<MainScreenUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null
    private var addNewDayJob: Job? = null
    private var getDayIdJob: Job? = null

    fun fillDayValues() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        dayNames = repository.getTrainingDays()
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

    fun addNewDay(dayName: String) {
        Log.e("ADDNEWDAY", "HERE")
        addNewDayJob?.cancel()
        addNewDayJob = viewModelScope.launch {
            repository.addTrainingDay(dayName) //todo проверить, как будет работать, возможно реализовать колбэки
        }
    }

    fun getSelectedDayId(dayName: String) {
        getDayIdJob?.cancel()
        getDayIdJob = viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        selectedDayId = mutableStateOf(repository.getSelectedDayId(dayName)!!.id)
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

    //remove
    override fun onCleared() {
        Log.e("MainVM", "cleared")
        super.onCleared()
    }


}





