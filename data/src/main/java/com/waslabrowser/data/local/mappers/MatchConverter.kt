package com.waslabrowser.data.local.mappers

import androidx.room.TypeConverter
import com.google.gson.Gson

class MatchConverter {
    @TypeConverter
    fun stringToTeam(string: String): com.waslabrowser.domain.models.Team {
        return Gson().fromJson(string, com.waslabrowser.domain.models.Team::class.java)
    }

    @TypeConverter
    fun teamToString(team: com.waslabrowser.domain.models.Team): String {
        return Gson().toJson(team)
    }

    @TypeConverter
    fun stringToScore(string: String): com.waslabrowser.domain.models.Score {
        return Gson().fromJson(string, com.waslabrowser.domain.models.Score::class.java)
    }

    @TypeConverter
    fun scoreToString(score: com.waslabrowser.domain.models.Score): String {
        return Gson().toJson(score)
    }
}