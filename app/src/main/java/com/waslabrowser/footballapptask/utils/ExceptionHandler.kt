package com.waslabrowser.footballapptask.utils

import com.waslabrowser.footballapptask.R
import java.net.UnknownHostException

internal object ExceptionHandler {
    fun parse(t: Throwable): Int {
        return when (t) {
            is UnknownHostException -> R.string.error_no_internet_connection
            else -> R.string.error_occured
        }
    }
}