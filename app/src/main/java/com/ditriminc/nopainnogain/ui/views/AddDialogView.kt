package com.ditriminc.nopainnogain.ui.views

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ditriminc.nopainnogain.R

/** Add new element dialog (new Excercise, training day or muscle group)*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenAddDialog(
    title: String,
    openDialog: MutableState<Boolean>,
    entityNameToSave: MutableState<String>
) {
    val text = remember {
        mutableStateOf(" ")
    }

    Dialog(onDismissRequest = { openDialog.value = false }) {
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
                        value = text.value,
                        onValueChange = { text.value = it },
                        label = { Text(text = title) },
                        modifier = Modifier
                            .fillMaxWidth()
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
                            stringResource(R.string.cancel),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    TextButton(onClick = {
                        openDialog.value = false
                        entityNameToSave.value = text.value
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

