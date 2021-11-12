package com.waslabrowser.domain.interactors

import com.waslabrowser.domain.models.Match
import com.waslabrowser.domain.repositories.IFixtureRepository
import kotlinx.coroutines.flow.Flow

class GetAllMatchesUseCase(private val fixtureRepository: IFixtureRepository) {
    suspend fun invoke(): Flow<List<Match>?> = fixtureRepository.getAllMatches()
}