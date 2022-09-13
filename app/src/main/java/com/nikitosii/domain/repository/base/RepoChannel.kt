package com.nikitosii.domain.repository.base

import com.nikitosii.core.base.connectivity.ConnectivityProvider
import com.nikitosii.core.base.channel.Status
import com.nikitosii.util.ext.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext
import com.nikitosii.core.base.connectivity.ConnectivityProvider.NetworkState.*

@FlowPreview
@ExperimentalCoroutinesApi
fun <T : Any> repoChannel(
    io: CoroutineDispatcher,
    connectivityProvider: ConnectivityProvider,
    recreateObserver: ChannelRecreateObserver? = null,
    init: RepoChannel<T>.() -> Unit
): Lazy<RepoChannel<T>> {

    return object : Lazy<RepoChannel<T>> {
        private var repoChannel: RepoChannel<T>? = null

        override val value: RepoChannel<T>
            get() {
                if (repoChannel == null) {
                    repoChannel = RepoChannel(io, connectivityProvider)
                    recreateObserver?.observe(repoChannel!!)
                    init(repoChannel!!)
                    repoChannel!!.refresh()
                }
                return repoChannel!!
            }

        override fun isInitialized() = repoChannel != null

    }
}

@FlowPreview
@ExperimentalCoroutinesApi
class RepoChannel<T : Any>(
    private val io: CoroutineDispatcher,
    private val connectivityProvider: ConnectivityProvider
) : CoroutineScope, ConnectivityProvider.ConnectivityStateListener {

    override val coroutineContext: CoroutineContext = io

    private var channel = ConflatedBroadcastChannel<Status<T>>()

    private var isRefreshing: AtomicBoolean = AtomicBoolean(false)

    private var refresh = false

    val flow: Flow<Status<T>>
        get() {
            if (refresh) {
                refresh()
                refresh = false
            }
            val data = channel.asFlow().flowOn(io)
            return data
        }

    private var storageConfig: StorageConfig<T>? = null
    private lateinit var networkConfig: NetworkConfig<T>
    private var refreshAfterNetworkConnected = false

    init {
        connectivityProvider.addListener(this)
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        when (state) {
            NotConnectedState -> {
                Timber.d("Repo in non connected state")
            }
            is ConnectedState -> {
                if (refreshAfterNetworkConnected && state.hasInternet) {
                    Timber.d("Repo in connected state, having a task to refresh...")
                    refreshOnlyNetwork()
                }
            }
        }
    }

    fun storageConfig(init: StorageConfig<T>.() -> Unit): StorageConfig<T> {
        storageConfig = StorageConfig()
        init(storageConfig as StorageConfig<T>)
        return storageConfig as StorageConfig<T>
    }

    fun networkConfig(init: NetworkConfig<T>.() -> Unit): NetworkConfig<T> {
        networkConfig = NetworkConfig()
        init(networkConfig)
        return networkConfig
    }

    fun refreshOnlyNetwork() {
        launch(io) {
            internalRefreshOnlyNetwork(true)
        }
    }

    fun refresh() {
        launch(io) {
            val data = storageConfig?.get
            val data2 = data?.invoke()
            val data3 = data2?.asRefreshing()
            data3?.sendToChannel(channel)
            internalRefreshOnlyNetwork(false)
        }
    }

    fun refreshOnlyLocal() {
        launch(io) {
            storageConfig?.get?.invoke()
                ?.asUpToDate()
                ?.sendToChannel(channel)
        }
    }

    fun recreateChannel() {
        refresh = true
    }

    private suspend fun internalRefreshOnlyNetwork(setRefreshingBeforeCall: Boolean) {
        if (isRefreshing.get()) return
        isRefreshing.set(true)
        refreshAfterNetworkConnected = when (connectivityProvider.getNetworkState()) {
            NotConnectedState -> {
                channel.setAsOnlyLocal()
                isRefreshing.set(false)
                true
            }
            is ConnectedState -> try {
                if (setRefreshingBeforeCall)
                    channel.setAsRefreshing()
                networkConfig.get().run {
                    storageConfig?.save?.invoke(this)
                    this.asUpToDate().sendToChannel(channel)
                    isRefreshing.set(false)
                }
                false
            } catch (e: Exception) {
                channel.setAsOnlyLocal()
                isRefreshing.set(false)
                Timber.e(e)
                true
            }
        }
    }
}

class StorageConfig<T> {
    var save: (suspend (T) -> Unit)? = null
    var get: (suspend () -> T)? = null
}

class NetworkConfig<T> {
    lateinit var get: (suspend () -> T)
}

class ChannelRecreateObserver {
    private val observers = mutableListOf<RepoChannel<*>?>()

    fun observe(repoChannel: RepoChannel<*>) {
        observers.add(repoChannel)
    }

    fun recreateChannel() {
        observers.forEach { it?.recreateChannel() }
    }
}