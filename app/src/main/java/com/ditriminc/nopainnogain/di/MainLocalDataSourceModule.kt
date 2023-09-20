package com.ditriminc.nopainnogain.di

import android.content.Context
import androidx.room.Room
import com.ditriminc.nopainnogain.data.datasourse.AppDatabase
import com.ditriminc.nopainnogain.data.datasourse.MainLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainLocalDataSourceModule {

//    @Provides
//    fun provideManLocalDataSourceModule(@ApplicationContext appContext: Context): MainLocalDataSource {
//        return MainLocalDataSource(appContext) //todo посмотреть внимательно, как это работает, есть ли в этом необходимость
//    }

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext appContext: Context) = Room.databaseBuilder(
        appContext, AppDatabase::class.java, "NoPainNoGain.db"
    ).build()

    @Singleton
    @Provides
    fun provideTrainingDayDao(db: AppDatabase) = db.trainingDayDao()

    @Singleton
    @Provides
    fun provideMuscleGroupDao(db: AppDatabase) = db.muscleGroupDao()

    @Singleton
    @Provides
    fun provideExerciseDao(db: AppDatabase) = db.exerciseDao()

    @Singleton
    @Provides
    fun provideTrainingSetDao(db: AppDatabase) = db.trainingSetDao()

}