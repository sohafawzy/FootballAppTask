package com.waslabrowser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.waslabrowser.data.local.entities.MatchEntity
@Dao
interface FixtureDao {
    @Query("SELECT * FROM `match` order by utcDate DESC")
    fun getAll(): List<MatchEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMatch(match: MatchEntity):Long

    @Query("SELECT * FROM `match` where id = :matchId")
    fun getMatch(matchId: Int?): MatchEntity?

    @Query("DELETE FROM `match` where id = :matchId")
    fun deleteMatch(matchId: Int?)
}