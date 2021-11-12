package com.waslabrowser.footballapptask.di

import android.content.Context
import androidx.room.Room
import com.waslabrowser.data.local.FixtureDao
import com.waslabrowser.data.local.FixtureDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): FixtureDatabase {
        return Room.databaseBuilder(
            appContext,
            FixtureDatabase::class.java,
            "footballApp.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideFixtureDao(appDatabase: FixtureDatabase): FixtureDao {
        return appDatabase.fixtureDao()
    }
}