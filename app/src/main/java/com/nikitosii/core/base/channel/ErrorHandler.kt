package com.nikitosii.core.base.channel

import android.content.res.Resources
import timber.log.Timber
import java.lang.Exception

class ErrorHandler(private val resources: Resources) {

    suspend fun <T> runWithErrorHandler(block: suspend () -> T): T {
        try {
            return block()
        } catch (e: Exception) {
            Timber.e(e)
            throw e
        }
    }
}