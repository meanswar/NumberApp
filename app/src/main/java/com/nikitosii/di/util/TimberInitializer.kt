package com.nikitosii.di.util

import android.content.Context
import timber.log.Timber

class TimberInitializer : Initializer {
    override fun initialize(context: Context) {
        Timber.plant(Timber.DebugTree())
    }
}