package com.waslabrowser.domain.repositories

import com.waslabrowser.domain.models.Match
import kotlinx.coroutines.flow.Flow

interface IFixtureRepository {
    suspend fun getAllMatches(): Flow<List<Match>?>

    suspend fun getAllFavoritesMatches(): Flow<List<Match>?>

    suspend fun getMatch(matchId: Int): Flow<Match?>

    suspend fun addMatchToFav(match: Match):Flow<Long>

    suspend fun removeMatchFromFav(matchId: Int)
}