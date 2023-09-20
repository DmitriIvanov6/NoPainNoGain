package com.ditriminc.nopainnogain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ditriminc.nopainnogain.ui.theme.NoPainNoGainTheme
import com.ditriminc.nopainnogain.ui.views.MainScreen
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
   //       deleteDatabase("NoPainNoGain.db")//todo remove before start
        setContent {
            NoPainNoGainTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen();
                }
            }
        }
    }
}

