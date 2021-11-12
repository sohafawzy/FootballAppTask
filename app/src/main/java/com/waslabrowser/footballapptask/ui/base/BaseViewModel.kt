package com.waslabrowser.footballapptask.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {

    abstract val coroutineExceptionHandler: CoroutineExceptionHandler

    protected fun launchCoroutine(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(coroutineExceptionHandler) {
            block()
        }
    }

    protected fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            block()
        }
    }
}