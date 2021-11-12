package com.waslabrowser.domain.interactors

import com.waslabrowser.domain.repositories.IFixtureRepository

class RemoveMatchFromFavUseCase(private val fixtureRepository: IFixtureRepository) {
    suspend fun invoke(matchId: Int) = fixtureRepository.removeMatchFromFav(matchId)
}