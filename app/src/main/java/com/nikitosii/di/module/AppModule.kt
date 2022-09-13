package com.nikitosii.di.module

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nikitosii.core.base.connectivity.ConnectivityProvider
import com.nikitosii.di.util.NumberApp
import com.nikitosii.domain.repository.base.ChannelRecreateObserver
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Named
import javax.inject.Singleton

@FlowPreview
@ExperimentalCoroutinesApi
@Module(includes = [
    DatabaseModule::class,
    RepositoryModule::class,
    NetErrorModule::class,
    InitializerModule::class])
internal class AppModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideApp(application: Application): NumberApp = application as NumberApp

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext


    @Provides
    @Singleton
    @Named(IO_DISPATCHER)
    internal fun io(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @Named(MAIN_DISPATCHER)
    internal fun main(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    internal fun providesResources(application: Application): Resources = application.resources

    @Provides
    @Singleton
    internal fun providesChannelRecreateObserver() = ChannelRecreateObserver()

    @Provides
    @Singleton
    internal fun connectivityProvider(context: Context) =
        ConnectivityProvider.createProvider(context)

    companion object {
        const val IO_DISPATCHER = "IO"
        const val MAIN_DISPATCHER = "Main"
    }
}