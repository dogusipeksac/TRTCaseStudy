package com.product.trtcasecompose.core.common.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConnectivityObserver @Inject constructor(
    @ApplicationContext private val context: Context
) : ConnectivityObserver {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val status = MutableStateFlow(ConnectivityObserver.Status.Unavailable)

    override fun observe(): Flow<ConnectivityObserver.Status> = status

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            status.value = ConnectivityObserver.Status.Available
        }

        override fun onLost(network: Network) {
            status.value = ConnectivityObserver.Status.Lost
        }

        override fun onUnavailable() {
            status.value = ConnectivityObserver.Status.Unavailable
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            status.value = ConnectivityObserver.Status.Losing
        }
    }

    init {
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, callback)
    }
}
