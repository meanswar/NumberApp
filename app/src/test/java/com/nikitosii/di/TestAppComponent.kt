package com.nikitosii.di

import com.nikitosii.di.modules.TestAppModule
import com.nikitosii.domain.usecase.GetFactUseCaseTest
import com.nikitosii.domain.usecase.GetRandomFactUseCaseTest
import com.nikitosii.domain.usecase.InsertFactUseCaseTest
import dagger.Component
import dagger.android.AndroidInjectionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
@Component(
    modules = [
        TestAppModule::class,
        AndroidInjectionModule::class
    ]
)
interface TestAppComponent {
    @Component.Builder
    interface Builder {

        fun build(): TestAppComponent
    }

    fun inject(useCaseTest: GetRandomFactUseCaseTest)
    fun inject(useCaseTest: GetFactUseCaseTest)
    fun inject(useCaseTest: InsertFactUseCaseTest)
}