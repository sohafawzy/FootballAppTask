package com.waslabrowser.data.remote.models

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.waslabrowser.domain.models.Competition
import com.waslabrowser.domain.models.Match
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class MatchesResponse(
    val competition: Competition,
    val count: Int,
    val matches: List<Match>
)