package com.nikitosii.di

import android.app.Application
import com.nikitosii.di.module.ActivityModule
import com.nikitosii.di.module.AppModule
import com.nikitosii.di.module.NetworkModule
import com.nikitosii.di.module.ViewModelModule
import com.nikitosii.di.util.NumberApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidInjectionModule::class,
        ActivityModule::class,
        NetworkModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<NumberApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(satisfyerApp: NumberApp)
}