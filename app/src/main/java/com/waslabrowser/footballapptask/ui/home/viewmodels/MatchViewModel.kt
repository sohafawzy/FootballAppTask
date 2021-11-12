package com.waslabrowser.footballapptask.ui.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.waslabrowser.domain.models.Match
import com.waslabrowser.footballapptask.utils.toHour

class MatchViewModel :ViewModel(){
    val matchHasStarted: MutableLiveData<Boolean> = MutableLiveData()
    private val _awayTeam = MutableLiveData<String>()
    val awayTeam: LiveData<String> get() = _awayTeam

    private val _awayScore = MutableLiveData<String>()
    val awayScore: LiveData<String> get() = _awayScore

    private val _homeTeam = MutableLiveData<String>()
    val homeTeam: LiveData<String> get() = _homeTeam

    private val _homeScore = MutableLiveData<String>()
    val homeScore: LiveData<String> get() = _homeScore

    private val _matchStatus = MutableLiveData<String>()
    val matchStatus: LiveData<String> get() = _matchStatus

    private val _matchTime = MutableLiveData<String>()
    val matchTime: LiveData<String> get() = _matchTime

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite : LiveData<Boolean> get() = _isFavorite

    fun bind(match: Match) {
        _awayTeam.value = match.awayTeam.name
        _homeTeam.value = match.homeTeam.name
        _matchStatus.value = match.status
        _awayScore.value = match.score?.fullTime?.awayTeam.toString()
        _homeScore.value = match.score?.fullTime?.homeTeam.toString()
        _isFavorite.value = match.isFavorite

        when (match.status) {
            "FINISHED" -> {
                matchHasStarted.value = true
            }
            else -> {
                matchHasStarted.value = false
                if (!match.utcDate.isNullOrEmpty())
                    _matchTime.value = toHour(match.utcDate)
            }
        }
    }

    fun updateFav(){
        _isFavorite.value = isFavorite.value?.not()
    }

}