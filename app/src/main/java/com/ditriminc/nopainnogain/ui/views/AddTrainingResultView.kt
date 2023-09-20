package com.ditriminc.nopainnogain.ui.views

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import com.ditriminc.nopainnogain.R
import com.ditriminc.nopainnogain.ui.viewmodels.ExerciseViewModel


@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
//todo remove default params
fun OpenTrainingResultDialog( exerciseViewModel: ExerciseViewModel,
    openDialog: MutableState<Boolean> = mutableStateOf(false),
//    weightToSave: MutableState<Int> = mutableStateOf(1),
//    repsToSave: MutableState<Int> = mutableStateOf(1)
) {
    val repsText = remember {
        mutableStateOf("")
    }
    val weightText = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Dialog(onDismissRequest = {openDialog.value = false }) {
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
                        .fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = repsText.value,
                        onValueChange = { repsText.value = it },
                        label = { Text(text = stringResource(R.string.reps)) },
                        modifier = Modifier
                            .fillMaxWidth(0.5f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = weightText.value,
                        onValueChange = { weightText.value = it },
                        label = { Text(text = stringResource(R.string.weight)) },
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .padding(end = 5.dp), horizontalArrangement = Arrangement.End

                ) {
                    TextButton(onClick = {
                        openDialog.value = false
                    }, Modifier.padding(end = 5.dp)) {
                        Text(
                            stringResource(id = R.string.cancel),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    TextButton(onClick = {
                        try {
                            exerciseViewModel.addCurrentSet(repsText.value.trim().toInt(), weightText.value.trim().toInt())
                            openDialog.value = false
                        } catch (nfe: NumberFormatException) {
                            nfe.message?.let { Log.e("NFE", it) }
                            Toast.makeText(context, "Некорректное значение", Toast.LENGTH_LONG)
                                .show()
                        }
                    }) {
                        Text(
                            stringResource(id = R.string.save),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}
