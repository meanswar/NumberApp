package com.nikitosii.di.module

import com.nikitosii.api.NumberApi
import com.nikitosii.core.base.channel.ErrorHandler
import com.nikitosii.core.base.connectivity.ConnectivityProvider
import com.nikitosii.core.database.dao.NumberFactDao
import com.nikitosii.domain.repository.NumberFactRepository
import com.nikitosii.domain.repository.base.ChannelRecreateObserver
import com.nikitosii.domain.repository.impl.NumberFactRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Named
import javax.inject.Singleton

@Module
@ExperimentalCoroutinesApi
@FlowPreview
object RepositoryModule {

    @Provides
    @Singleton
    internal fun provideNumberFactRepo(
        api: NumberApi,
        errorHandler: ErrorHandler,
        dao: NumberFactDao,
        connectivityProvider: ConnectivityProvider,
        @Named(AppModule.IO_DISPATCHER) io: CoroutineDispatcher,
        channelRecreateObserver: ChannelRecreateObserver
    ): NumberFactRepository = NumberFactRepositoryImpl(
        api,
        errorHandler,
        dao,
        connectivityProvider,
        io,
        channelRecreateObserver
    )
}