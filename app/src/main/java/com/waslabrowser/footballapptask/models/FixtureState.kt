package com.waslabrowser.footballapptask.models

data class FixtureState(
    val isLoading: Boolean,
    val error: Error?,
    val matches: List<Any>?
)
