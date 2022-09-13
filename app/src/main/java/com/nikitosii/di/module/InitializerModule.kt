package com.nikitosii.di.module

import com.nikitosii.di.util.Initializer
import com.nikitosii.di.util.TimberInitializer
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class InitializerModule {
    @Provides
    @IntoSet
    fun initTimber(): Initializer = TimberInitializer()
}