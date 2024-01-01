package com.nikitosii.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitosii.di.ViewModelKey
import com.nikitosii.di.ViewModelProviderFactory
import com.nikitosii.flow.factdetails.FactDetailsViewModel
import com.nikitosii.flow.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FactDetailsViewModel::class)
    fun bindFactDetailsViewModel(viewModel: FactDetailsViewModel): ViewModel
}