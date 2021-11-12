package com.waslabrowser.data.local.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.waslabrowser.data.local.mappers.MatchConverter
import com.waslabrowser.domain.models.Score
import com.waslabrowser.domain.models.Team

@Entity(tableName = "match")
@TypeConverters(MatchConverter::class)
class MatchEntity(
    @NonNull
    @PrimaryKey val id: Int,
    val utcDate: String?,
    val awayTeam: Team,
    val homeTeam: Team,
    val score: Score?,
    val status: String,
    var isFavorite: Boolean = false
)