package com.ditriminc.nopainnogain.ui.views

import android.app.Activity
import android.app.RemoteAction
import android.media.Image
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ditriminc.nopainnogain.R
import com.ditriminc.nopainnogain.data.entities.TrainingSet
import com.ditriminc.nopainnogain.ui.viewmodels.ExerciseViewModel


val columnWidth = 90.dp
const val REACTION_TIRED = 1
const val REACTION_NORMAL = 1
const val REACTION_FRESH = 1


data class ReactionItem(
    val img: Image,
    val isSelected: Boolean,
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseView(exerciseViewModel: ExerciseViewModel = viewModel()) {
    val uiState by exerciseViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val activity = context as Activity
    val intent = activity.intent
    Log.e("EXVIEW", "id " + intent!!.getLongExtra("exerciseId", -1))

    exerciseViewModel.fetchTrainingSets(intent!!.getLongExtra("exerciseId", -1))

    LaunchedEffect(Unit) {
        //todo рассмотреть необходимость применения блока
    }

    Scaffold(topBar = {}, floatingActionButton = {
        FloatingActionButton(
            onClick = { exerciseViewModel.openAddSetDialog() },
            containerColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(60.dp),
            shape = CircleShape
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "add new")
        }
    }) { paddingValues ->
        Box(
            modifier = Modifier.padding(
                bottom = paddingValues.calculateBottomPadding(),
                top = paddingValues.calculateTopPadding()
            )
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                ExerciseHeader()
                TableHeader()
                TrainingSetBlock(
                    trainingList = uiState.previousResultList, isPreviousTraining = true
                )
                BoxDivider()
                TrainingSetBlock(
                    trainingList = uiState.currentResultList, isPreviousTraining = false
                )
                SaveTrainingButton(exerciseViewModel)
            }
        }
    }

    if (uiState.showAddSetDialog.value) {
        OpenTrainingResultDialog(
            exerciseViewModel, uiState.showAddSetDialog
        )
    }

    if (uiState.showApproveDialog.value) {
        CustomAlertDialog(
            onDismissRequest = { exerciseViewModel.closeApproveDialog() },
            onConfirmation = {
                exerciseViewModel.saveCurrentTrainingSets()
                exerciseViewModel.closeApproveDialog()
            },
            dialogTitle = "Сохранить тренировку?",
            dialogText = "Вы уверены, что закончили на сегодня c этим упражнением?",
            confirmText = stringResource(R.string.save),
            cancelText = stringResource(R.string.cancel)
        )
    }

}

//@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentBlock(
    openDialog: MutableState<Boolean> = mutableStateOf(false),
    commentToSave: MutableState<String> = mutableStateOf("")
) {
    val text = remember {
        mutableStateOf("")
    }

    val reaction = remember {
        mutableStateOf(0)
    }

    Dialog(onDismissRequest = {/*todo launch method*/ }) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(3.dp))
                .wrapContentHeight()
                .background(Color.White)
                .width(300.dp)
                .padding(5.dp)
        ) {
            Column(
                Modifier
                    .wrapContentSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    OutlinedTextField(
                        value = text.value,
                        onValueChange = { text.value = it },
                        label = { Text(text = stringResource(R.string.enter_your_comment)) },
                        supportingText = { Text(text = stringResource(R.string.exercise_commentblock_supporting_text)) },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(MaterialTheme.colorScheme.background)

                        .padding(vertical = 5.dp)
                        .padding(end = 5.dp), horizontalArrangement = Arrangement.End

                ) {

                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(vertical = 5.dp)
                        .padding(end = 5.dp), horizontalArrangement = Arrangement.End

                ) {
                    TextButton(onClick = {
                        openDialog.value = false
                    }, Modifier.padding(end = 5.dp)) {
                        Text(
                            stringResource(R.string.cancel),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    TextButton(onClick = {
                        openDialog.value = false
                        commentToSave.value = text.value
                    }) {
                        Text(
                            stringResource(R.string.save),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun TrainingSetBlock(trainingList: ArrayList<TrainingSet>, isPreviousTraining: Boolean) {
    LazyColumn(modifier = Modifier.wrapContentHeight()) {
        itemsIndexed(items = trainingList, itemContent = { index, item ->
            ResultListItem(item, index + 1, isPreviousTraining)
            if (index < trainingList.size - 1) {
                Divider()
            }
        })
    }
}

@Composable
fun SaveTrainingButton(exerciseViewModel: ExerciseViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.surface)
            .padding(end = 5.dp), Alignment.BottomEnd
    ) {
        Button(
            onClick = { exerciseViewModel.openApproveDialog() },
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(20)
                )
        ) {
            Text(
                text = stringResource(R.string.save_training),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun BoxDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primary)
            .height(10.dp)
    )
}


@Composable
fun ExerciseHeader(exerciseName: String = "Title") {
    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = exerciseName, color = MaterialTheme.colorScheme.onPrimary)

    }

}


//todo сократить циклом ?
@Composable
fun ResultListItem(item: TrainingSet, index: Int, previousTraining: Boolean) {
    var backGroundColor = MaterialTheme.colorScheme.secondary
    if (!previousTraining) {
        backGroundColor = MaterialTheme.colorScheme.tertiary
    }

    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(backGroundColor),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            Modifier
                .height(40.dp)
                .width(columnWidth)
                .background(backGroundColor)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .background(backGroundColor),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = index.toString())
            }
        }
        Box(
            Modifier
                .height(40.dp)
                .width(columnWidth)
                .background(backGroundColor)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .background(backGroundColor),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = item.reps.toString())
            }

        }
        Box(
            Modifier
                .height(40.dp)
                .width(columnWidth)
                .background(backGroundColor)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .background(backGroundColor),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = item.weight.toString())
            }

        }
        Box(
            Modifier
                .height(40.dp)
                .width(columnWidth)
                .background(backGroundColor)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .background(backGroundColor),
                horizontalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.edit_20), contentDescription = "edit"
                )
            }
        }
    }
}


//todo сократить циклом
@Composable
fun TableHeader() {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .border(1.dp, color = Color.Black),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            Modifier
                .height(20.dp)
                .width(columnWidth)
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondary),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = "№")
            }

        }
        Box(
            Modifier
                .height(20.dp)
                .width(columnWidth)
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondary),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = stringResource(id = R.string.reps))
            }

        }
        Box(
            Modifier
                .height(20.dp)
                .width(columnWidth)
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondary),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = stringResource(id = R.string.weight))
            }

        }
        Box(
            Modifier
                .height(20.dp)
                .width(columnWidth)
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondary),
                horizontalArrangement = Arrangement.Center,
            ) {

            }
        }
    }
}







