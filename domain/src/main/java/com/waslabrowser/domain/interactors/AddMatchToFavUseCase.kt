package com.waslabrowser.domain.interactors

import com.waslabrowser.domain.models.Match
import com.waslabrowser.domain.repositories.IFixtureRepository

class AddMatchToFavUseCase(private val fixtureRepository: IFixtureRepository) {
    suspend fun invoke(match: Match) = fixtureRepository.addMatchToFav(match)
}