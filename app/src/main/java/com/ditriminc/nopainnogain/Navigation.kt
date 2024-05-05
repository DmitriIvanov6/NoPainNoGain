package com.ditriminc.nopainnogain

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ditriminc.nopainnogain.ui.viewmodels.ExerciseScreenViewModel
import com.ditriminc.nopainnogain.ui.viewmodels.ExerciseViewModel
import com.ditriminc.nopainnogain.ui.viewmodels.GroupScreenViewModel
import com.ditriminc.nopainnogain.ui.viewmodels.MainScreenViewModel
import com.ditriminc.nopainnogain.ui.views.ExerciseScreen
import com.ditriminc.nopainnogain.ui.views.ExerciseView
import com.ditriminc.nopainnogain.ui.views.GroupScreen
import com.ditriminc.nopainnogain.ui.views.MainScreen

@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
            MainScreen(navController, mainScreenViewModel = mainScreenViewModel)
        }
        composable("groups/{dayId}") {
            val dayId = it.arguments?.getString("dayId") ?: "-1"
            val groupScreenViewModel = hiltViewModel<GroupScreenViewModel>();
            GroupScreen(dayId = dayId.toLong(), navController = navController, groupScreenViewModel)
        }

        composable("exercises/{groupId}") {
            val groupId = it.arguments?.getString("groupId") ?: "-1"
            val exerciseScreenViewModel = hiltViewModel<ExerciseScreenViewModel>()
            ExerciseScreen(groupId.toLong(), navController, exerciseScreenViewModel)
        }
        composable("exercise/{exerciseId}") {
            val exerciceId = it.arguments?.getString("exerciseId") ?: "-1"
            val exerciseViewModel = hiltViewModel<ExerciseViewModel>()
            ExerciseView(exerciceId.toLong(), exerciseViewModel)
        }

    }
}