package com.waslabrowser.data

import com.waslabrowser.domain.models.Match
import com.waslabrowser.domain.models.Score
import com.waslabrowser.domain.models.Team
import com.waslabrowser.domain.models.Time

internal object MockData {
    val match = Match(
        1, Team(1, "Liverpool"), Team(2, "Arsenal"), Score(
            "REGULAR",
            Time(null, null), Time(2, 1), Time(2, 1), "Liverpool"
        ),
        "Finished", "2021-11-19T12:00:00Z", false
    )
}