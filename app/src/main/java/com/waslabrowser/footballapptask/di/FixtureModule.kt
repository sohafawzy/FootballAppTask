package com.waslabrowser.footballapptask.di

import androidx.lifecycle.ViewModel
import com.waslabrowser.data.local.FixtureDao
import com.waslabrowser.data.remote.ApiService
import com.waslabrowser.data.remote.NetworkDataSource
import com.waslabrowser.data.repositories.FixtureRepository
import com.waslabrowser.domain.interactors.*
import com.waslabrowser.domain.repositories.IFixtureRepository
import com.waslabrowser.footballapptask.ui.home.viewmodels.FavoritesViewModel
import com.waslabrowser.footballapptask.ui.home.viewmodels.FixturesViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ActivityComponent::class)
object FixtureModule {

    @Provides
    fun provideFixtureRepository(networkDataSource: NetworkDataSource,fixtureDao: FixtureDao
    ): IFixtureRepository = FixtureRepository(networkDataSource, fixtureDao)

    @Provides
    fun provideGetAllMatchesUseCase(fixtureRepository: IFixtureRepository): GetAllMatchesUseCase =
        GetAllMatchesUseCase(fixtureRepository)
    @Provides
    fun provideGetAllFavoritesMatchesUseCase(fixtureRepository: IFixtureRepository): GetAllFavoritesMatchesUseCase =
        GetAllFavoritesMatchesUseCase(fixtureRepository)
    @Provides
    fun provideAddMatchToFavUseCase(fixtureRepository: IFixtureRepository): AddMatchToFavUseCase =
        AddMatchToFavUseCase(fixtureRepository)
    @Provides
    fun provideGetMatchUseCase(fixtureRepository: IFixtureRepository): GetMatchUseCase=
        GetMatchUseCase(fixtureRepository)
    @Provides
    fun provideRemoveMatchFromFavUseCase(fixtureRepository: IFixtureRepository): RemoveMatchFromFavUseCase=
        RemoveMatchFromFavUseCase(fixtureRepository)

    @Provides
    fun provideFixtureViewModel(getAllMatchesUseCase: GetAllMatchesUseCase,
                                addMatchToFavUseCase: AddMatchToFavUseCase,
                                getMatchUseCase: GetMatchUseCase,
                                removeMatchFromFavUseCase: RemoveMatchFromFavUseCase): FixturesViewModel =
        FixturesViewModel(getAllMatchesUseCase,addMatchToFavUseCase,getMatchUseCase,removeMatchFromFavUseCase)

    @Provides
    fun provideFavouriteViewModel(getAllFavoritesMatchesUseCase: GetAllFavoritesMatchesUseCase,
                                  addMatchToFavUseCase: AddMatchToFavUseCase,
                                  removeMatchFromFavUseCase: RemoveMatchFromFavUseCase): FavoritesViewModel =
        FavoritesViewModel(getAllFavoritesMatchesUseCase,addMatchToFavUseCase,removeMatchFromFavUseCase)
}