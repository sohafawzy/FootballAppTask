package com.waslabrowser.domain.interactors

import com.waslabrowser.domain.models.Match
import com.waslabrowser.domain.repositories.IFixtureRepository
import kotlinx.coroutines.flow.Flow

class GetMatchUseCase(private val fixtureRepository: IFixtureRepository) {
    suspend fun invoke(matchId: Int): Flow<Match?> = fixtureRepository.getMatch(matchId)
}