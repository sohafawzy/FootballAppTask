package com.waslabrowser.footballapptask.ui.clickListeners

import com.waslabrowser.domain.models.Match

interface IMatchClickListeners {
    fun onFavClicked(match:Match)
}