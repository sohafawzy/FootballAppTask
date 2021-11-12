package com.waslabrowser.data.local.mappers

import com.waslabrowser.data.local.entities.MatchEntity
import com.waslabrowser.domain.models.Match

    fun Match.toEntity():MatchEntity = MatchEntity(this.id,this.utcDate,this.awayTeam,this.homeTeam,this.score,this.status,this.isFavorite)

    fun MatchEntity.toDomain(): Match = Match(this.id,this.awayTeam,this.homeTeam,this.score,this.status,this.utcDate,this.isFavorite)
