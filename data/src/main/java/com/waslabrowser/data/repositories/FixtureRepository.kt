package com.waslabrowser.data.repositories

import com.waslabrowser.data.local.FixtureDao
import com.waslabrowser.data.local.mappers.toDomain
import com.waslabrowser.data.local.mappers.toEntity
import com.waslabrowser.data.remote.NetworkConstants
import com.waslabrowser.data.remote.NetworkDataSource
import com.waslabrowser.data.remote.models.MatchesResponse
import com.waslabrowser.domain.models.Match
import com.waslabrowser.domain.repositories.IFixtureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class FixtureRepository(private val  networkDataSource: NetworkDataSource,private val fixtureDao: FixtureDao):IFixtureRepository {
    override suspend fun getAllMatches(): Flow<List<Match>?> = flow {
       networkDataSource.makeGetRequest<MatchesResponse>(NetworkConstants.matchesApi).collect {
          emit(it?.matches)
        }
    }

    override suspend fun getAllFavoritesMatches(): Flow<List<Match>?>  = flow {
        emit(fixtureDao.getAll()?.map { it.toDomain() })
    }

    override suspend fun getMatch(matchId: Int): Flow<Match?> = flow {
        emit(fixtureDao.getMatch(matchId)?.toDomain())
    }

    override suspend fun addMatchToFav(match: Match):Flow<Long> = flow {
        emit(fixtureDao.addMatch(match.toEntity()))
    }

    override suspend fun removeMatchFromFav(matchId: Int) {
        fixtureDao.deleteMatch(matchId)
    }
}