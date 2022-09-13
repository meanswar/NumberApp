package com.nikitosii.di.module

import android.content.res.Resources
import com.nikitosii.core.base.channel.ErrorHandler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetErrorModule {
    @Singleton
    @Provides
    fun provideErrorHandler(resources: Resources) = ErrorHandler(resources)
}