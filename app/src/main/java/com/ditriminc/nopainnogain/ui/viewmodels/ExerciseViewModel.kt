package com.ditriminc.nopainnogain.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ditriminc.nopainnogain.data.entities.TrainingSet
import com.ditriminc.nopainnogain.data.repositories.ExerciseRepository
import com.ditriminc.nopainnogain.data.repositories.TrainingSetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Date
import javax.inject.Inject


data class ExerciseUiState(
    val exerciseName: MutableState<String> = mutableStateOf(""),
    val previousResultList: ArrayList<TrainingSet> = ArrayList(),
    val currentResultList: ArrayList<TrainingSet> = ArrayList(),
    val showAddSetDialog: MutableState<Boolean> = mutableStateOf(false),
    val currentExerciseComment: MutableState<String> = mutableStateOf(""),
    val currentExerciseReaction: MutableState<Int> = mutableStateOf(-1),
    val previousExerciseComment: String = "",
    val previousExerciseReaction: Int = -1,
    val currentExerciseId: Long = 0L,
    val showApproveDialog: MutableState<Boolean> = mutableStateOf(false),
    val showCommentBlock: MutableState<Boolean> = mutableStateOf(false)
)

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val trainingSetRepository: TrainingSetRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExerciseUiState())
    val uiState: StateFlow<ExerciseUiState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null
    private var addTrainingSetJob: Job? = null

    init {
        Log.e("viewModel", "Start")
    }

    fun fetchTrainingSets(exerciseId: Long) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val currentExercise = exerciseRepository.getExerciseById(exerciseId)
                if (currentExercise != null) {
                    val previousResultList =
                        trainingSetRepository.fetchPreviousResultsList(currentExercise)
                    _uiState.update {
                        it.copy(
                            previousResultList = previousResultList,
                            currentExerciseId = exerciseId,
                            previousExerciseComment = currentExercise.previousComment ?: "",
                            previousExerciseReaction = currentExercise.previousReaction
                        )
                    }
                } else {
                    //todo обработать
                }

            } catch (ioe: IOException) {
                // Handle the error and notify the UI when appropriate.
                Log.e("fetchList", "something went wrong")
            }
        }
    }

    fun openAddSetDialog() {
        _uiState.update {
            it.copy(showAddSetDialog = mutableStateOf(true))
        }
    }


    fun saveCurrentExercise() {
        addTrainingSetJob?.cancel()
        addTrainingSetJob = viewModelScope.launch {
            exerciseRepository.addTrainingSets(_uiState.value.currentResultList,
                exerciseRepository.getExerciseById(
                    _uiState.value.currentExerciseId
                )?.apply {
                    previousComment = _uiState.value.currentExerciseComment.value
                    previousReaction = _uiState.value.currentExerciseReaction.value
                    Log.e("SAVECUREX", previousComment + previousReaction)
                })
        }
        _uiState.update {
            it.copy(
                previousResultList = uiState.value.currentResultList,
                currentResultList = ArrayList(),
                previousExerciseReaction = uiState.value.currentExerciseReaction.value,
                previousExerciseComment = uiState.value.currentExerciseComment.value
            )
        }
    }

    fun openApproveDialog() {
        _uiState.update {
            it.copy(showApproveDialog = mutableStateOf(true))
        }
    }

    fun closeApproveDialog() {
        _uiState.update {
            it.copy(showApproveDialog = mutableStateOf(false))
        }
    }

    //возможно заменить на тогл

    fun openCommentBlock() {
        _uiState.update {
            it.copy(showCommentBlock = mutableStateOf(true))
        }
    }

    fun closeCommentBlock() {
        _uiState.update {
            it.copy(showCommentBlock = mutableStateOf(false))
        }
    }


    fun addCurrentSet(reps: Int, weight: Int) {
        _uiState.value.currentResultList.add(
            TrainingSet(
                reps = reps,
                weight = weight,
                exerciseId = _uiState.value.currentExerciseId,
                date = Date(System.currentTimeMillis())
            )
        )
    }

    override fun onCleared() {
        Log.e("viewModel", "Stopped")
        super.onCleared()
    }
}