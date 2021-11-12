package com.waslabrowser.footballapptask.ui.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.waslabrowser.domain.interactors.AddMatchToFavUseCase
import com.waslabrowser.domain.interactors.GetAllMatchesUseCase
import com.waslabrowser.domain.interactors.GetMatchUseCase
import com.waslabrowser.domain.interactors.RemoveMatchFromFavUseCase
import com.waslabrowser.domain.models.Match
import com.waslabrowser.footballapptask.models.Error
import com.waslabrowser.footballapptask.models.FixtureState
import com.waslabrowser.footballapptask.ui.base.BaseViewModel
import com.waslabrowser.footballapptask.utils.ExceptionHandler
import com.waslabrowser.footballapptask.utils.greaterOrEqualThan
import com.waslabrowser.footballapptask.utils.toDate
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@ActivityScoped
class FixturesViewModel @Inject constructor(
    private val getAllMatchesUseCase: GetAllMatchesUseCase,
    private val addMatchToFavUseCase: AddMatchToFavUseCase,
    private val getMatchUseCase: GetMatchUseCase,
    private val removeMatchFromFavUseCase: RemoveMatchFromFavUseCase
) : BaseViewModel() {

    private var getMatchesJob: Job? = null
    private var addToFavJob:Job? = null
    private var deleteFromFavJob:Job? = null

    val fixturesViewState: LiveData<FixtureState>
        get() = _fixturesViewState

    private var _fixturesViewState = MutableLiveData<FixtureState>()

    var scrollToPosition = 0

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
        getMatchesJob?.cancel()
        getMatchesJob = launchCoroutine {
            onLoading()
            getAllMatchesUseCase.invoke().collect { matches->
                matches?.let {
                    val allMatches = checkFavoritesMatches(matches)
                    onComplete(getGroupedMatches(allMatches))
                }
            }
        }
    }

    fun favMatch(match: Match){
        addToFavJob = launch {
            if (match.isFavorite) {
                match.isFavorite = true
                addMatchToFavUseCase.invoke(match).collect ()
            }
            else removeMatchFromFavUseCase.invoke(match.id)
        }
    }

    private suspend fun checkFavoritesMatches(matches: List<Match>): List<Match> {
        return viewModelScope.async(Dispatchers.IO) {
            matches.map { match ->
                getMatchUseCase.invoke(match.id).collect {
                    it?.let {
                        match.isFavorite = true
                    }
                }
            }
            return@async matches
        }.await()
    }

    private fun getGroupedMatches(matches: List<Match>):List<Any>{
        val fixture : ArrayList<Any> = arrayListOf()
        val groupedMatches: Map<String, List<Match>> = matches.sortedByDescending { it.utcDate }.groupBy { toDate(it.utcDate) }
        val dates = groupedMatches.keys
        val currentDate = SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault()).format(Date(System.currentTimeMillis()))
        dates.forEach { date ->
            val dateMatches: List<Match>? = groupedMatches[date]
            dateMatches?.let {
                fixture.add(date)
                if (date.greaterOrEqualThan(currentDate))
                    scrollToPosition = fixture.size
                fixture.addAll(dateMatches)
            }
        }
        return fixture
    }

    private fun onComplete(matches: List<Any>) {
        _fixturesViewState.value = _fixturesViewState.value?.copy(isLoading = false, matches = matches)
    }

    private fun onLoading() {
        _fixturesViewState.value = _fixturesViewState.value?.copy(isLoading = true)
    }

    private fun onError(message: Int) {
        _fixturesViewState.value = _fixturesViewState.value?.copy(isLoading = false, error = Error(message))
    }

    override fun onCleared() {
        super.onCleared()
        getMatchesJob?.cancel()
        addToFavJob?.cancel()
        deleteFromFavJob?.cancel()
    }

}