package com.nikitosii.core.base.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi
import com.nikitosii.core.base.connectivity.impl.ConnectivityProviderImpl
import com.nikitosii.core.base.connectivity.impl.ConnectivityProviderLegacyImpl

interface ConnectivityProvider {
    interface ConnectivityStateListener {
        fun onStateChange(state: NetworkState)
    }

    fun addListener(listener: ConnectivityStateListener)
    fun removeListener(listener: ConnectivityStateListener)

    fun getNetworkState(): NetworkState

    @Suppress("MemberVisibilityCanBePrivate", "CanBeParameter")
    sealed class NetworkState {
        object NotConnectedState : NetworkState()

        sealed class ConnectedState(val hasInternet: Boolean) : NetworkState() {

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            data class Connected(val capabilities: NetworkCapabilities) : ConnectedState(
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            )

            @Suppress("DEPRECATION")
            data class ConnectedLegacy(val networkInfo: NetworkInfo) : ConnectedState(
                networkInfo.isConnectedOrConnecting
            )
        }
    }

    companion object {
        fun createProvider(context: Context): ConnectivityProvider {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ConnectivityProviderImpl(cm)
            } else {
                ConnectivityProviderLegacyImpl(context, cm)
            }
        }
    }
}