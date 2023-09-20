package com.ditriminc.nopainnogain

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ditriminc.nopainnogain.data.datasourse.AppDatabase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class NoPainNoGainApp : Application()