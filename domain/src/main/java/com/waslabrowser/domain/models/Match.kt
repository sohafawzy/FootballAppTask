package com.waslabrowser.domain.models

data class Match(
    val id: Int,
    val awayTeam: Team,
    val homeTeam: Team,
    val score: Score?,
    val status: String,
    val utcDate: String?,
    var isFavorite: Boolean = false

)