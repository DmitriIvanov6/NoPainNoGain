package com.ditriminc.nopainnogain.ui.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ditriminc.nopainnogain.ExercisesScreenActivity
import com.ditriminc.nopainnogain.R
import com.ditriminc.nopainnogain.domain.findActivity
import com.ditriminc.nopainnogain.ui.viewmodels.GroupScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupScreen(groupScreenViewModel: GroupScreenViewModel = viewModel()) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    val uiState by groupScreenViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val activity = context.findActivity()
    val intent = activity?.intent
    //todo подумать как вызывать, и нужна ли подгрузка групп
    uiState.dayId.value = intent!!.getLongExtra("dayId", -1)
    groupScreenViewModel.fetchDayGroups(uiState.dayId.value)
    Scaffold(
        topBar = {},
        floatingActionButton = {
            FloatingActionButton(
                onClick = { uiState.showAddGroupDialog.value = true },
                containerColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(60.dp),
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add new")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(
                bottom = paddingValues.calculateBottomPadding(),
                top = paddingValues.calculateTopPadding()
            )
        ) {
            Column(Modifier.padding(mediumPadding)) {
                Text(
                    text = stringResource(R.string.choose_group),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                for (group in uiState.groupNames) {
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 2.dp)
                            .height(80.dp)
                            .background(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                            .clickable {
                                if (group != "") {
                                    groupScreenViewModel.getSelectedGroupId(group)
                                }
                            }
                    ) {
                        Text(
                            text = group,
                            fontSize = 20.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
    if (uiState.showAddGroupDialog.value) {
        OpenAddDialog(
            stringResource(R.string.add_muscle_group),
            uiState.showAddGroupDialog,
            uiState.newGroupToAddName
        )
    }
    if (uiState.newGroupToAddName.value != "") {
        groupScreenViewModel.addNewGroup(
            uiState.newGroupToAddName.value,
            uiState.dayId.value
        )
        uiState.newGroupToAddName.value = ""
    }
    if (uiState.selectedGroupId.value != -1L) {
        startExerciseScreenActivity(uiState.selectedGroupId.value, context, activity)
        uiState.selectedGroupId.value = -1L
    }
}

fun startExerciseScreenActivity(groupId: Long, context: Context, activity: Activity) {
    val intent = Intent(context, ExercisesScreenActivity::class.java)
    intent.putExtra("groupId", groupId)
    activity.startActivity(intent)
}




