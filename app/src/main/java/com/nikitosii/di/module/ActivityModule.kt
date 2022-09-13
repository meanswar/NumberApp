package com.nikitosii.di.module

import com.nikitosii.flow.factdetails.FactDetailsActivity
import com.nikitosii.flow.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeFactDetailsActivity(): FactDetailsActivity
}