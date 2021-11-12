package com.waslabrowser.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.waslabrowser.data.remote.NetworkDataSource
import com.waslabrowser.data.repositories.FixtureRepository
import com.waslabrowser.domain.repositories.IFixtureRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FixtureRepositoryTest : BaseTest() {
    private lateinit var fixRepository: IFixtureRepository

    @Before
    override fun setup() {
        super.setup()
        fixRepository = FixtureRepository(NetworkDataSource(apiService),fixtureDao)
    }

    @Test
    fun getMatches() {
        runBlocking {
            val matchesFlow = fixRepository.getAllMatches()
            matchesFlow.collect { Truth.assertThat(it).isNotEmpty() }
        }
    }

    @Test
    fun getAllFav(){
        runBlocking(Dispatchers.IO) {
            fixRepository.addMatchToFav(MockData.match).collect()
            val favorites = fixRepository.getAllFavoritesMatches()
            favorites.collect { favs ->
                Truth.assertThat(favs).isEqualTo(listOf(MockData.match))
            }
        }
    }

    @Test
    fun deleteFav() {
        runBlocking(Dispatchers.IO) {
            val match = MockData.match
            // Given we save favorite to the database
            fixRepository.addMatchToFav(match).collect()

            // When we delete favorite from db
            fixRepository.removeMatchFromFav(match.id)
            fixRepository.getAllFavoritesMatches().collect { favs ->
                Truth.assertThat(favs).isEmpty()
            }
        }
    }
}