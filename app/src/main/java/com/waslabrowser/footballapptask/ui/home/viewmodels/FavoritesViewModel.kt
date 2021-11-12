package com.waslabrowser.footballapptask.ui.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.waslabrowser.domain.interactors.*
import com.waslabrowser.domain.models.Match
import com.waslabrowser.footballapptask.models.Error
import com.waslabrowser.footballapptask.models.FixtureState
import com.waslabrowser.footballapptask.ui.base.BaseViewModel
import com.waslabrowser.footballapptask.utils.ExceptionHandler
import com.waslabrowser.footballapptask.utils.greaterOrEqualThan
import com.waslabrowser.footballapptask.utils.toDate
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FavoritesViewModel(
    private val getAllFavoritesMatchesUseCase: GetAllFavoritesMatchesUseCase,
    private val addMatchToFavUseCase: AddMatchToFavUseCase,
    private val removeMatchFromFavUseCase: RemoveMatchFromFavUseCase
) : BaseViewModel() {

    private var getFavMatchesJob: Job? = null
    private var favJob : Job? = null

    val fixturesViewState: LiveData<FixtureState>
        get() = _fixturesViewState

    private var _fixturesViewState = MutableLiveData<FixtureState>()

    override val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            val message = ExceptionHandler.parse(exception)
            onError(message)
        }

    init {
        _fixturesViewState.value =
            FixtureState(
                isLoading = false,
                error = null,
                matches = null
            )
    }

    fun getMatches() {
        getFavMatchesJob?.cancel()
        getFavMatchesJob = launch {
            getAllFavoritesMatchesUseCase.invoke().collect { matches->
                matches?.let {
                    val groupedMatches = getGroupedMatches(matches)
                    launchCoroutine {
                        onComplete(groupedMatches)
                    }
                }
            }
        }
    }

    fun favMatch(match: Match){
        favJob = launch {
            if (match.isFavorite) {
                match.isFavorite = true
                addMatchToFavUseCase.invoke(match).collect ()
            }
            else removeMatchFromFavUseCase.invoke(match.id)
        }
    }

    private fun getGroupedMatches(matches: List<Match>):List<Any>{
        val fixture : ArrayList<Any> = arrayListOf()
        val groupedMatches: Map<String, List<Match>> = matches.sortedByDescending { it.utcDate }.groupBy { toDate(it.utcDate) }
        val dates = groupedMatches.keys
        dates.forEach { date ->
            val dateMatches: List<Match>? = groupedMatches[date]
            dateMatches?.let {
                fixture.add(date)
                fixture.addAll(dateMatches)
            }
        }
        return fixture
    }

    private fun onComplete(matches: List<Any>) {
        _fixturesViewState.value = _fixturesViewState.value?.copy(isLoading = false, matches = matches)
    }


    private fun onError(message: Int) {
        _fixturesViewState.value = _fixturesViewState.value?.copy(isLoading = false, error = Error(message))
    }

    override fun onCleared() {
        super.onCleared()
        getFavMatchesJob?.cancel()
        favJob?.cancel()
    }

}