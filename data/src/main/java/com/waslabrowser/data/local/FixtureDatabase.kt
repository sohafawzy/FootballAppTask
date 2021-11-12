package com.waslabrowser.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.waslabrowser.data.local.entities.MatchEntity

@Database(entities = [MatchEntity::class],version = 1)
abstract class FixtureDatabase : RoomDatabase(){
    abstract fun fixtureDao():FixtureDao
}