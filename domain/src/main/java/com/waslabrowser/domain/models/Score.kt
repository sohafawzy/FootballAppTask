package com.waslabrowser.domain.models

data class Score(
    val duration: String?,
    val extraTime: Time?,
    val fullTime: Time?,
    val halfTime: Time?,
    val winner: String?
)