package com.nikitosii.flow.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitosii.domain.base.WorkResult
import kotlinx.coroutines.*
import timber.log.Timber

abstract class BaseViewModel(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val uiDispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    fun ioToUnit(io: suspend () -> Unit) = ioToUi(io, {})

    fun <T> ioToUi(io: suspend () -> T, ui: suspend (T) -> Unit, error: (suspend (error: Throwable) -> Unit)? = null) =
        viewModelScope.launch(ioDispatcher) {
            try {
                val result = io()
                withContext(uiDispatcher) {
                    ui(result)
                }
            } catch (e: Exception) {
                Timber.e(e)
                error?.invoke(e)
            }
        }

    fun <T> ioToUiWorkData(
        io: suspend () -> T,
        ui: suspend (WorkResult<T>) -> Unit,
        also: (suspend (T) -> Unit)? = null
    ) = viewModelScope.launch(uiDispatcher) {
        ui(WorkResult.loading())
        withContext(ioDispatcher) {
            val result = try {
                WorkResult.success(io().also {
                    also?.invoke(it)
                })
            } catch (e: Exception) {
                Timber.e(e)
                WorkResult.error<T>(e.message ?: "", exception = e)
            }
            withContext(uiDispatcher) {
                ui(result)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancel()
    }
}