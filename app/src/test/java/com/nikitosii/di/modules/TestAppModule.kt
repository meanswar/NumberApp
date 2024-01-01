package com.nikitosii.di.modules

import android.content.Context
import android.content.res.Resources
import android.net.NetworkCapabilities
import com.nikitosii.TestConstants
import com.nikitosii.core.base.connectivity.ConnectivityProvider
import com.nikitosii.data.source.connectivity.TestConnectivityProvider
import com.nikitosii.di.module.AppModule
import com.nikitosii.di.module.RepositoryModule
import com.nikitosii.domain.repository.base.ChannelRecreateObserver
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.lenient
import org.mockito.Mockito.mock
import javax.inject.Named
import javax.inject.Singleton

@FlowPreview
@ExperimentalCoroutinesApi
@Module(
    includes = [
        RepositoryModule::class,
        TestNetworkModule::class,
        TestDataBaseModule::class
    ]
)
class TestAppModule {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val mockContext = mock(Context::class.java)
    private val mockResources = mock(Resources::class.java)
    private val mockNetworkCapabilities = mock(NetworkCapabilities::class.java)

    init {
        lenient().`when`(mockContext.getString(anyInt(), any())).thenReturn(TestConstants.ANY_TEXT)
        lenient()
            .`when`(mockNetworkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
            .thenReturn(true)
    }

    @Provides
    @Singleton
    @Named(AppModule.IO_DISPATCHER)
    internal fun io(): CoroutineDispatcher = testCoroutineDispatcher

    @Provides
    @Singleton
    @Named(AppModule.MAIN_DISPATCHER)
    internal fun main(): CoroutineDispatcher = testCoroutineDispatcher

    @Provides
    @Singleton
    @Named(TEST_DISPATCHER)
    internal fun test(): TestCoroutineDispatcher = testCoroutineDispatcher

    @Provides
    @Singleton
    internal fun providesContext(): Context = mockContext

    @Provides
    @Singleton
    internal fun providesResources(): Resources = mockResources

    @Provides
    @Singleton
    internal fun providesChannelRecreateObserver() = ChannelRecreateObserver()

    @Provides
    @Singleton
    internal fun connectivityProvider(networkCapabilities: NetworkCapabilities): ConnectivityProvider =
        TestConnectivityProvider(networkCapabilities)

    @Provides
    @Singleton
    internal fun networkCapabilities(): NetworkCapabilities = mockNetworkCapabilities

    companion object {
        const val TEST_DISPATCHER = "TEST"
    }
}