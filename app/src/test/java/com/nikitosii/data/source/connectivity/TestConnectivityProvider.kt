package com.nikitosii.data.source.connectivity

import android.net.NetworkCapabilities
import com.nikitosii.core.base.connectivity.ConnectivityProvider

class TestConnectivityProvider(private val testNetworkCapabilities: NetworkCapabilities) :
    ConnectivityProvider {

    override fun addListener(listener: ConnectivityProvider.ConnectivityStateListener) {
        listener.onStateChange(getNetworkState())
    }

    override fun removeListener(listener: ConnectivityProvider.ConnectivityStateListener) {
    }

    override fun getNetworkState(): ConnectivityProvider.NetworkState {
        return ConnectivityProvider.NetworkState.ConnectedState.Connected(testNetworkCapabilities)
    }
}